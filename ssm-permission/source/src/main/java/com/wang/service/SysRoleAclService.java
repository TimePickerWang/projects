package com.wang.service;

import com.google.common.collect.Lists;
import com.wang.beans.LogType;
import com.wang.common.LogProperties;
import com.wang.common.RequestHolder;
import com.wang.dao.SysLogMapper;
import com.wang.dao.SysRoleAclMapper;
import com.wang.model.SysLogWithBLOBs;
import com.wang.model.SysRoleAcl;
import com.wang.util.CollectionUtil;
import com.wang.util.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/12 21:13
 */
@Service
public class SysRoleAclService {
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;
	@Autowired
	private SysLogMapper sysLogMapper;


	public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
		if (CollectionUtils.isEmpty(aclIdList)) {
			return;
		}
		// 获取角色之前已分配的权限点列表
		List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
		if (CollectionUtil.isListEqual(originAclIdList, aclIdList)) {
			return;// 已经分配好的权限点和传进来的权限点相同
		}
		updateRoleAcls(roleId, aclIdList);
		saveRoleAclLog(roleId, originAclIdList, aclIdList);
	}

	@Transactional
	public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
		sysRoleAclMapper.deleteByRoleId(roleId); // 删除之前角色已分配的所有权限点

		List<SysRoleAcl> roleAclList = Lists.newArrayList();
		for (Integer aclId : aclIdList) {
			SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getUsername())
					.operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
			roleAclList.add(roleAcl);
		}
		sysRoleAclMapper.batchInsert(roleAclList);
	}

	// 保存操作记录
	private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_ROLE_ACL);
		sysLog.setTargetId(roleId);
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}


}
