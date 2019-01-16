package com.wang.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wang.common.JsonData;
import com.wang.model.SysUser;
import com.wang.param.RoleParam;
import com.wang.service.*;
import com.wang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysTreeService sysTreeService;
	@Autowired
	private SysRoleAclService sysRoleAclService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("role.page")
	public ModelAndView page() {
		return new ModelAndView("role");
	}

	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData saveRole(RoleParam param) {
		sysRoleService.save(param);
		return JsonData.success();
	}

	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateRole(RoleParam param) {
		sysRoleService.update(param);
		return JsonData.success();
	}

	@RequestMapping("/list.json")
	@ResponseBody
	public JsonData list() {
		return JsonData.success(sysRoleService.getAll());
	}


	@RequestMapping("/roleTree.json")
	@ResponseBody
	public JsonData roleTree(@RequestParam("roleId") int roleId) {
		return JsonData.success(sysTreeService.roleTree(roleId));
	}


	@RequestMapping("/changeAcls.json")
	@ResponseBody
	public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
		List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
		sysRoleAclService.changeRoleAcls(roleId, aclIdList);
		return JsonData.success();
	}


	@RequestMapping("/users.json")
	@ResponseBody
	public JsonData users(@RequestParam("roleId") int roleId) {
		// 已分配该角色的用户
		List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
		// 所有用户
		List<SysUser> allUserList = sysUserService.getAll();
		// 未分配该角色的用户
		List<SysUser> unselectedUserList = Lists.newArrayList();

		Set<Integer> selectedUserIdSet = selectedUserList.stream().map(SysUser::getId).collect(Collectors.toSet());
		for (SysUser sysUser : allUserList) {
			if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
				unselectedUserList.add(sysUser);
			}
		}
		Map<String, List<SysUser>> map = Maps.newHashMap();
		map.put("selected", selectedUserList);
		map.put("unselected", unselectedUserList);
		return JsonData.success(map);
	}


	@RequestMapping("/changeUsers.json")
	@ResponseBody
	public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
		List<Integer> userIdList = StringUtil.splitToListInt(userIds);
		sysRoleUserService.changeRoleUsers(roleId, userIdList);
		return JsonData.success();
	}


}
