package com.wang.service;

import com.google.common.base.Preconditions;
import com.wang.common.RequestHolder;
import com.wang.dao.SysDeptMapper;
import com.wang.dao.SysUserMapper;
import com.wang.exception.ParamException;
import com.wang.model.SysDept;
import com.wang.param.DeptParam;
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
 * Date 2019/1/3 17:47
 */
@Service
public class SysDeptService {
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysLogService sysLogService;


	public void save(DeptParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getParentId(), param.getName(), param.getId())) {
			throw new ParamException("同一层级下存在相同名称的部门");
		}
		SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
				.seq(param.getSeq()).remark(param.getRemark()).build();
		setDeptProperties(dept, param); // 设置部门的其余字段
		sysDeptMapper.insertSelective(dept);
		sysLogService.saveDeptLog(null, dept);
	}

	public void update(DeptParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getParentId(), param.getName(), param.getId())) {
			throw new ParamException("同一层级下存在相同名称的部门");
		}
		SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的部门不存在");

		SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
				.seq(param.getSeq()).remark(param.getRemark()).build();
		setDeptProperties(after, param);  // 设置部门的其余字段
		// 如果更新后的部门不是同一Level，原部门的所有子部门level也需要更新
		if (!after.getLevel().equals(before.getLevel())) {
			updateWithChild(before, after);
		}
		sysDeptMapper.updateByPrimaryKey(after);
		sysLogService.saveDeptLog(before, after);
	}

	@Transactional
	public void updateWithChild(SysDept before, SysDept after) {
		String newLevelPrefix = after.getLevel();
		String oldLevelPrefix = before.getLevel();
		// 取出待更新部门的所有子部门
		List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
		if (CollectionUtils.isNotEmpty(deptList)) {
			for (SysDept dept : deptList) {
				String level = dept.getLevel();
				if (level.indexOf(oldLevelPrefix) == 0) {
					// 部门的新levle
					level = newLevelPrefix + level.substring(oldLevelPrefix.length());
					dept.setLevel(level);
				}
			}
			sysDeptMapper.batchUpdateLevel(deptList);
		}
	}


	// 判断同一层级下是否存在相同名称的部门
	private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
		return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
	}


	// 设置部门的其余字段
	private void setDeptProperties(SysDept sysDept, DeptParam param) {
		SysDept dept = sysDeptMapper.selectByPrimaryKey(param.getParentId());
		String level = dept == null ? null : dept.getLevel();

		sysDept.setLevel(LevelUtil.calculateLevel(level, param.getParentId()));
		sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysDept.setOperateTime(new Date());
	}

	public void delete(int deptId) {
		SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
		Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
		if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
			throw new ParamException("当前部门下面有子部门，无法删除");
		}
		if (sysUserMapper.countByDeptId(dept.getId()) > 0) {
			throw new ParamException("当前部门下面有用户，无法删除");
		}
		sysDeptMapper.deleteByPrimaryKey(deptId);
	}


}
