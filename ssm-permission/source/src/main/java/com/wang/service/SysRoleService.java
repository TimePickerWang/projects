package com.wang.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.wang.common.RequestHolder;
import com.wang.dao.SysRoleAclMapper;
import com.wang.dao.SysRoleMapper;
import com.wang.dao.SysRoleUserMapper;
import com.wang.dao.SysUserMapper;
import com.wang.exception.ParamException;
import com.wang.model.SysRole;
import com.wang.param.RoleParam;
import com.wang.util.BeanValidator;
import com.wang.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by wangjf
 * Date 2019/1/11 17:03
 */
@Service
public class SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysLogService sysLogService;


	public void save(RoleParam roleParam) {
		BeanValidator.check(roleParam);
		if (checkExist(roleParam.getName(), roleParam.getId())) {
			throw new ParamException("角色名称已经存在");
		}
		SysRole sysRole = SysRole.builder().name(roleParam.getName()).status(roleParam.getStatus()).type(roleParam.getType())
				.remark(roleParam.getRemark()).build();
		setRoleProperties(sysRole);
		sysRoleMapper.insertSelective(sysRole);
		sysLogService.saveRoleLog(null, sysRole);
	}

	public void update(RoleParam roleParam) {
		BeanValidator.check(roleParam);
		if (checkExist(roleParam.getName(), roleParam.getId())) {
			throw new ParamException("角色名称已经存在");
		}
		SysRole before = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
		Preconditions.checkNotNull(before, "待更新的角色不存在");
		SysRole after = SysRole.builder().id(roleParam.getId()).name(roleParam.getName()).status(roleParam.getStatus())
				.type(roleParam.getType()).remark(roleParam.getRemark()).build();
		setRoleProperties(after);
		sysRoleMapper.updateByPrimaryKeySelective(after);
		sysLogService.saveRoleLog(before, after);
	}

	public List<SysRole> getAll() {
		return sysRoleMapper.getAll();
	}

	private boolean checkExist(String name, Integer id) {
		return sysRoleMapper.countByName(name, id) > 0;
	}

	private void setRoleProperties(SysRole sysRole) {
		sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysRole.setOperateTime(new Date());
	}

	// 获取某一用户的所有角色
	public List<SysRole> getRoleListByUserId(int userId) {
		List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}
		return sysRoleMapper.getByIdList(roleIdList);
	}

	// 获取所有拥有改权限点的角色列表
	public List<SysRole> getRoleListByAclId(int aclId) {
		List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}
		return sysRoleMapper.getByIdList(roleIdList);
	}

	public Object getUserListByRoleList(List<SysRole> roleList) {
		if (CollectionUtils.isEmpty(roleList)) {
			return Lists.newArrayList();
		}
		List<Integer> roleIdList = roleList.stream().map(SysRole::getId).collect(Collectors.toList());
		List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return sysUserMapper.getByIdList(userIdList);
	}
}
