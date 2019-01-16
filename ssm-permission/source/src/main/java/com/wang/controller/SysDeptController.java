package com.wang.controller;

import com.wang.common.JsonData;
import com.wang.dto.DeptLevelDto;
import com.wang.param.DeptParam;
import com.wang.service.SysDeptService;
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
 * Date 2019/1/3 17:31
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysTreeService sysTreeService;

	@RequestMapping("/tree.json")
	@ResponseBody
	public JsonData tree() {
		List<DeptLevelDto> dtoList = sysTreeService.deptTree();
		return JsonData.success(dtoList);
	}

	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData saveDept(DeptParam param) {
		sysDeptService.save(param);
		return JsonData.success();
	}

	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData updateDept(DeptParam param) {
		sysDeptService.update(param);
		return JsonData.success();
	}

	@RequestMapping("/dept.page")
	public ModelAndView page() {
		return new ModelAndView("dept");
	}

	@RequestMapping("/delete.json")
	@ResponseBody
	public JsonData delete(@RequestParam("id") int id) {
		sysDeptService.delete(id);
		return JsonData.success();
	}
}
