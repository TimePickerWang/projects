package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;


	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findSearch(searchMap));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Enterprise enterprise) {
		enterpriseService.add(enterprise);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Enterprise enterprise, @PathVariable String id) {
		enterprise.setId(id);
		enterpriseService.update(enterprise);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		enterpriseService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 查询热门企业
	 */
	@RequestMapping(value = "/search/hotlist", method = RequestMethod.GET)
	public Result hotlist() {
		return new Result(true, StatusCode.OK, "查询成功", enterpriseService.hotlist());
	}
}
