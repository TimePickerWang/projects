package com.wang.controller;

import com.google.common.collect.Maps;
import com.wang.beans.PageQuery;
import com.wang.common.JsonData;
import com.wang.model.SysRole;
import com.wang.param.AclParam;
import com.wang.service.SysAclService;
import com.wang.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {
	@Autowired
	private SysAclService sysAclService;
	@Autowired
	private SysRoleService sysRoleService;


	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData saveAclModule(AclParam param) {
		sysAclService.save(param);
		return JsonData.success();
	}

	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateAclModule(AclParam param) {
		sysAclService.update(param);
		return JsonData.success();
	}

	@RequestMapping("/page.json")
	@ResponseBody
	public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
		return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
	}


	@RequestMapping("acls.json")
	@ResponseBody
	public JsonData acls(@RequestParam("aclId") int aclId) {// 已分配某一权限点的角色和用户
		Map<String, Object> map = Maps.newHashMap();
		List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
		map.put("roles", roleList);
		map.put("users", sysRoleService.getUserListByRoleList(roleList));
		return JsonData.success(map);
	}

}
