package com.wang.service;

import com.google.common.collect.Lists;
import com.wang.beans.LogType;
import com.wang.common.LogProperties;
import com.wang.common.RequestHolder;
import com.wang.dao.SysLogMapper;
import com.wang.dao.SysRoleUserMapper;
import com.wang.dao.SysUserMapper;
import com.wang.model.SysLogWithBLOBs;
import com.wang.model.SysRoleUser;
import com.wang.model.SysUser;
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
 * Date 2019/1/12 21:21
 */
@Service
public class SysRoleUserService {
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	public List<SysUser> getListByRoleId(int roleId) {
		List<Integer> userIdlist = sysRoleUserMapper.getUserIdListByRoleId(roleId);
		if (CollectionUtils.isEmpty(userIdlist)) {
			return Lists.newArrayList();
		}
		return sysUserMapper.getByIdList(userIdlist);
	}


	public void changeRoleUsers(int roleId, List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return;
		}
		// 获取已经分配该角色的用户
		List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
		if (CollectionUtil.isListEqual(originUserIdList, userIdList)) {
			return; // 已分配该角色的用户和传进来的用户相同
		}
		updateRoleUsers(roleId, userIdList);
		saveRoleUserLog(roleId, originUserIdList, userIdList);
	}

	@Transactional
	public void updateRoleUsers(int roleId, List<Integer> userIdList) {
		sysRoleUserMapper.deleteByRoleId(roleId); // 删除之前角色已分配的所有用户

		List<SysRoleUser> roleUserList = Lists.newArrayList();
		for (Integer userId : userIdList) {
			SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator(RequestHolder.getCurrentUser().getUsername())
					.operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
			roleUserList.add(roleUser);
		}
		sysRoleUserMapper.batchInsert(roleUserList);
	}

	// 保存操作记录
	private void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
		SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
		sysLog.setType(LogType.TYPE_ROLE_USER);
		sysLog.setTargetId(roleId);
		LogProperties.setSysLogProperties(sysLog, before, after);
		sysLogMapper.insertSelective(sysLog);
	}
}
