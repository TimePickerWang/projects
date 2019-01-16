package com.wang.controller;

import com.google.common.collect.Maps;
import com.wang.beans.PageQuery;
import com.wang.beans.PageResult;
import com.wang.common.JsonData;
import com.wang.param.UserParam;
import com.wang.service.SysRoleService;
import com.wang.service.SysTreeService;
import com.wang.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Create by wangjf
 * Date 2019/1/7 15:51
 */
@Controller
@RequestMapping("sys/user")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysTreeService sysTreeService;
	@Autowired
	private SysRoleService sysRoleService;

	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData saveUser(UserParam param) {
		sysUserService.save(param);
		return JsonData.success();
	}

	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateUser(UserParam param) {
		sysUserService.update(param);
		return JsonData.success();
	}

	@RequestMapping("/page.json")
	@ResponseBody
	public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
		PageResult result = sysUserService.getPageByDeptId(deptId, pageQuery);
		return JsonData.success(result);
	}

	@RequestMapping("/acls.json")
	@ResponseBody
	public JsonData acls(@RequestParam("userId") Integer userId) { // 某一用户已分配的权限点和角色
		Map<String, Object> map = Maps.newHashMap();
		map.put("acls", sysTreeService.userAclTree(userId));
		map.put("roles", sysRoleService.getRoleListByUserId(userId));
		return JsonData.success(map);
	}

	// 跳到无权限访问的页面
	@RequestMapping("/noAuth.page")
	public ModelAndView noAuth() {
		return new ModelAndView("noAuth");
	}

}
