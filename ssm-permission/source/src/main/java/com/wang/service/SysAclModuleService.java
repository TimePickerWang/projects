package com.wang.service;

import com.google.common.base.Preconditions;
import com.wang.common.RequestHolder;
import com.wang.dao.SysAclMapper;
import com.wang.dao.SysAclModuleMapper;
import com.wang.exception.ParamException;
import com.wang.model.SysAclModule;
import com.wang.param.AclModuleParam;
import com.wang.util.BeanValidator;
import com.wang.util.IpUtil;
import com.wang.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/9 21:50
 */

@Service
public class SysAclModuleService {
	@Autowired
	private SysAclModuleMapper sysAclModuleMapper;
	@Autowired
	private SysAclMapper sysAclMapper;
	@Autowired
	private SysLogService sysLogService;


	public void save(AclModuleParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getParentId(), param.getName(), param.getId())) {
			throw new ParamException("同一层级下存在相同名称的权限模块");
		}
		SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
				.status(param.getStatus()).remark(param.getRemark()).build();
		setAclModuleProperties(aclModule, param);// 设置权限的其余字段
		sysAclModuleMapper.insertSelective(aclModule);
		sysLogService.saveAclModuleLog(null, aclModule);
	}

	public void update(AclModuleParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getParentId(), param.getName(), param.getId())) {
			throw new ParamException("同一层级下存在相同名称的权限模块");
		}
		SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的权限模块不存在");

		SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
				.status(param.getStatus()).remark(param.getRemark()).build();
		setAclModuleProperties(after, param);  // 设置权限的其余字段
		// 如果更新后的权限模块不是同一Level，原模块的所有子模块level也需要更新.
		// 这里涉及到多种情况 TODO
		if (!after.getLevel().equals(before.getLevel())) {
			updateWithChild(before, after);
		}
		sysAclModuleMapper.updateByPrimaryKeySelective(after);
		sysLogService.saveAclModuleLog(before, after);
	}

	@Transactional
	public void updateWithChild(SysAclModule before, SysAclModule after) {
		String newLevelPrefix = after.getLevel();
		String oldLevelPrefix = before.getLevel();
		// 取出待更新权限模块的所有子权限模块
		List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
		if (CollectionUtils.isNotEmpty(aclModuleList)) {
			for (SysAclModule aclModule : aclModuleList) {
				String level = aclModule.getLevel();
				if (level.indexOf(oldLevelPrefix) == 0) {
					// 权限模块的新levle
					level = newLevelPrefix + level.substring(oldLevelPrefix.length());
					aclModule.setLevel(level);
				}
			}
			sysAclModuleMapper.batchUpdateLevel(aclModuleList);
		}
	}

	// 判断同一层级下是否存在相同名称的权限模块
	private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
		return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
	}

	// 设置权限的其余字段
	private void setAclModuleProperties(SysAclModule sysAclModule, AclModuleParam param) {
		SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(param.getParentId());
		String level = aclModule == null ? null : aclModule.getLevel();

		sysAclModule.setLevel(LevelUtil.calculateLevel(level, param.getParentId()));
		sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysAclModule.setOperateTime(new Date());
	}

	public void delete(int aclModuleId) {
		SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
		Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
		if (sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
			throw new ParamException("当前模块下面有子模块，无法删除");
		}
		if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
			throw new ParamException("当前模块下面有权限点，无法删除");
		}
		sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
	}
}
