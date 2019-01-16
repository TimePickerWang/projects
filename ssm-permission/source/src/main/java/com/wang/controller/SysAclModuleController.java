package com.wang.controller;

import com.wang.common.JsonData;
import com.wang.dto.AclModuleLevelDto;
import com.wang.param.AclModuleParam;
import com.wang.service.SysAclModuleService;
import com.wang.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/9 21:44
 */

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {
	@Autowired
	private SysAclModuleService sysAclModuleService;
	@Autowired
	private SysTreeService sysTreeService;

	@RequestMapping("/tree.json")
	@ResponseBody
	public JsonData tree() {
		List<AclModuleLevelDto> dtoList = sysTreeService.aclModuleTree();
		return JsonData.success(dtoList);
	}

	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData saveAclModule(AclModuleParam param) {
		sysAclModuleService.save(param);
		return JsonData.success();
	}

	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateAclModule(AclModuleParam param) {
		sysAclModuleService.update(param);
		return JsonData.success();
	}

	@RequestMapping("/acl.page")
	public ModelAndView page() {
		return new ModelAndView("acl");
	}

	@RequestMapping("/delete.json")
	@ResponseBody
	public JsonData delete(@RequestParam("id") int id) {
		sysAclModuleService.delete(id);
		return JsonData.success();
	}
}
