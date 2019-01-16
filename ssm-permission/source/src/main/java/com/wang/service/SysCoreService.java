package com.wang.service;

import com.google.common.collect.Lists;
import com.wang.beans.CacheKeyConstants;
import com.wang.common.RequestHolder;
import com.wang.dao.SysAclMapper;
import com.wang.dao.SysRoleAclMapper;
import com.wang.dao.SysRoleUserMapper;
import com.wang.model.SysAcl;
import com.wang.model.SysUser;
import com.wang.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create by wangjf
 * Date 2019/1/12 13:37
 */
@Service
public class SysCoreService {
	@Autowired
	private SysAclMapper sysAclMapper;
	@Autowired
	private SysRoleUserMapper sysRoleUserMapper;
	@Autowired
	private SysRoleAclMapper sysRoleAclMapper;
	@Autowired
	private SysCacheService sysCacheService;


	// 查询当前用户所有权限点的列表
	public List<SysAcl> getCurrentUserAclList() {
		int userId = RequestHolder.getCurrentUser().getId();
		return getUserAclList(userId);
	}


	// 查询当前角色已分配的权限点列表
	public List<SysAcl> getRoleAclList(Integer roleId) {
		List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
		if (CollectionUtils.isEmpty(aclIdList)) {
			return Lists.newArrayList();
		}
		return sysAclMapper.getByIdList(aclIdList);
	}


	// 查询某个用户所有权限点的列表
	public List<SysAcl> getUserAclList(Integer userId) {
		if (isSuperAdmin()) {
			return sysAclMapper.getAll();
		}
		// 获取该用户的所有角色id
		List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return Lists.newArrayList();
		}
		// 获取用户的所有角色对应的权限点id
		List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIdList);
		if (CollectionUtils.isEmpty(userAclIdList)) {
			return Lists.newArrayList();
		}
		return sysAclMapper.getByIdList(userAclIdList);
	}


	// 判断是否是超级用户
	private boolean isSuperAdmin() {
		// 通过邮箱里面是否含有"admin"来判断是否是超级用户
		SysUser sysUser = RequestHolder.getCurrentUser();
		return sysUser.getMail().contains("admin");
	}


	// 判断用户是否有请求权限
	public boolean hasUrlAcl(String url) {
		if (isSuperAdmin()) {
			return true;
		}
		List<SysAcl> aclList = sysAclMapper.getByUrl(url); // 获取所有有请求权限且有效的权限点
		if (CollectionUtils.isEmpty(aclList)) { // 该请求无有效的权限分配则返回true
			return true;
		}

		// 当前用户所有的权限点
		List<SysAcl> userAclList = getCurrentUserAclList();
		// 从redis缓存中取得当前用户权限点,用该方法需启动redis, 否则会报错
//		List<SysAcl> userAclList = getCurrentUserAclListFromCache();

		Set<Integer> userAclIdSet = userAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

		// 规则：只要有一个权限点有权限，那么我们就认为有访问权限
		for (SysAcl acl : aclList) {
			// 判断一个用户是否具有某个权限点的访问权限
			if (userAclIdSet.contains(acl.getId())) {// 权限点有效且用户有该权限点
				return true;
			}
		}
		return false;
	}


	// 从redis缓存中取得当前用户权限点
	private List<SysAcl> getCurrentUserAclListFromCache() {
		int userId = RequestHolder.getCurrentUser().getId();
		String userIdStr = String.valueOf(userId);
		String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, userIdStr);// 从缓冲中获取数据
		if (StringUtils.isBlank(cacheValue)) {
			List<SysAcl> aclList = getCurrentUserAclList();  // 缓存中不存在查数据库
			if (CollectionUtils.isNotEmpty(aclList)) {
				// 对查询结果进行缓存
				sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, userIdStr);
			}
			return aclList;
		}
		return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
		});
	}
}
