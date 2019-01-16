package com.wang.service;

import com.google.common.base.Preconditions;
import com.wang.beans.LogType;
import com.wang.beans.PageQuery;
import com.wang.beans.PageResult;
import com.wang.common.LogProperties;
import com.wang.common.RequestHolder;
import com.wang.dao.*;
import com.wang.dto.SearchLogDto;
import com.wang.exception.ParamException;
import com.wang.model.*;
import com.wang.param.SearchLogParam;
import com.wang.util.BeanValidator;
import com.wang.util.IpUtil;
import com.wang.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/15 16:37
 */
@Service
public class SysLogService {
	@Autowired
	private SysLogMapper sysLogMapper;
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysAclModuleMapper sysAclModuleMapper;
	@Autowired
	private SysAclMapper sysAclMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysRoleAclService sysRoleAclService;


	// 查询操作记录
	public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery pageQuery) {
		BeanValidator.check(param);
		SearchLogDto dto = new SearchLogDto();
		dto.setType(param.getType());
		if (StringUtils.isNotBlank(param.getBeforeSeg())) {
			dto.setBeforeSeg("%" + param.getBeforeSeg() + "%");
		}
		if (StringUtils.isNotBlank(param.getAfterSeg())) {
			dto.setAfterSeg("%" + param.getAfterSeg() + "%");
		}
		if (StringUtils.isNotBlank(param.getOperator())) {
			dto.setOperator("%" + param.getOperator() + "%");
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotBlank(param.getFromTime())) {
				dto.setFromTime(sdf.parse(param.getFromTime()));
			}
			if (StringUtils.isNotBlank(param.getToTime())) {
				dto.setToTime(sdf.parse(param.getToTime()));
			}
		} catch (Exception e) {
			throw new ParamException("日期格式错误，正确格式为：yyyy-MM-dd HH:mm:ss");
		}

		int count = sysLogMapper.countBySearchDto(dto);
		if (count > 0) {
			List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto, pageQuery);
			return PageResult.<SysLogWithBLOBs>builder().data(logList).total(count).build();
		}
		return PageResult.<SysLogWithBLOBs>builder().build();
	}


	// 还原某一操作(仅能还原更新操作)
	public void recover(int logId) {
		SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(logId); // 查询操作记录
		Integer targetId = sysLog.getTargetId();
		Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
		switch (sysLog.getType()) {
			case LogType.TYPE_DEPT:
				SysDept deptBefore = sysDeptMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(deptBefore, "待还原的部门不存在");
				if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
					throw new ParamException("新增和删除操作不能还原");
				}
				SysDept deptAfter = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysDept>() {
				});
				deptAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
				deptAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
				deptAfter.setOperateTime(new Date());
				sysDeptMapper.updateByPrimaryKeySelective(deptAfter);
				saveDeptLog(deptBefore, deptAfter);
				break;
			case LogType.TYPE_USER:
				SysUser userBefore = sysUserMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(userBefore, "待还原的用户不存在");
				if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
					throw new ParamException("新增和删除操作不能还原");
				}
				SysUser userAfter = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysUser>() {
				});
				userAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
				userAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
				userAfter.setOperateTime(new Date());
				sysUserMapper.updateByPrimaryKeySelective(userAfter);
				saveUserLog(userBefore, userAfter);
				break;
			case LogType.TYPE_ACL_MODULE:
				SysAclModule aclModuleBefore = sysAclModuleMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(aclModuleBefore, "待还原的权限模块不存在");
				if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
					throw new ParamException("新增和删除操作不能还原");
				}
				SysAclModule aclModuleAfter = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
				});
				aclModuleAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
				aclModuleAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
				aclModuleAfter.setOperateTime(new Date());
				sysAclModuleMapper.updateByPrimaryKeySelective(aclModuleAfter);
				saveAclModuleLog(aclModuleBefore, aclModuleAfter);
				break;
			case LogType.TYPE_ACL:
				SysAcl aclBefore = sysAclMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(aclBefore, "待还原的权限点不存在");
				if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
					throw new ParamException("新增和删除操作不能还原");
				}
				SysAcl aclAfter = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
				});
				aclAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
				aclAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
				aclAfter.setOperateTime(new Date());
				sysAclMapper.updateByPrimaryKeySelective(aclAfter);
				saveAclLog(aclBefore, aclAfter);
				break;
			case LogType.TYPE_ROLE:
				SysRole roleBefore = sysRoleMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(roleBefore, "待还原的角色不存在");
				if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
					throw new ParamException("新增和删除操作不能还原");
				}
				SysRole roleAfter = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
				});
				roleAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
				roleAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
				roleAfter.setOperateTime(new Date());
				sysRoleMapper.updateByPrimaryKeySelective(roleAfter);
				saveRoleLog(roleBefore, roleAfter);
				break;
			case LogType.TYPE_ROLE_ACL:
				SysRole aclRole = sysRoleMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(aclRole, "角色不存在");
				sysRoleAclService.changeRoleAcls(targetId, JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
				}));
				break;
			case LogType.TYPE_ROLE_USER:
				SysRole aclUser = sysRoleMapper.selectByPrimaryKey(targetId);
				Preconditions.checkNotNull(aclUser, "角色不存在");
				sysRoleAclService.changeRoleAcls(targetId, JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
				}));
				break;
		}
	}


	/*---------------------保存对各个模块的操作记录--------------------*/

	public void saveDeptLog(SysDept before, SysDept after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_DEPT);
		sysLog.setTargetId(after == null ? before.getId() : after.getId());
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}

	public void saveUserLog(SysUser before, SysUser after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_USER);
		sysLog.setTargetId(after == null ? before.getId() : after.getId());
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}

	public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_ACL_MODULE);
		sysLog.setTargetId(after == null ? before.getId() : after.getId());
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}

	public void saveAclLog(SysAcl before, SysAcl after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_ACL);
		sysLog.setTargetId(after == null ? before.getId() : after.getId());
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}

	public void saveRoleLog(SysRole before, SysRole after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_ROLE);
		sysLog.setTargetId(after == null ? before.getId() : after.getId());
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}

}
