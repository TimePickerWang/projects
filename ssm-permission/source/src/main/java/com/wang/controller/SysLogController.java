package com.wang.controller;

import com.wang.beans.PageQuery;
import com.wang.common.JsonData;
import com.wang.param.SearchLogParam;
import com.wang.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create by wangjf
 * Date 2019/1/15 16:35
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;


	@RequestMapping("/log.page")
	public ModelAndView page() {
		return new ModelAndView("log");
	}


	// 查询操作记录
	@RequestMapping("/page.json")
	@ResponseBody
	public JsonData searchPage(SearchLogParam param, PageQuery pageQuery) {
		return JsonData.success(sysLogService.searchPageList(param, pageQuery));
	}

	@RequestMapping("/recover.json")
	@ResponseBody
	public JsonData recover(@RequestParam("id") int logId) {
		sysLogService.recover(logId);
		return JsonData.success();
	}

}
