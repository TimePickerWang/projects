package com.tensquare.gathering.controller;

import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {
	@Autowired
	private GatheringService gatheringService;

	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", gatheringService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", gatheringService.findById(id));
	}

	/**
	 * 分页+多条件查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", gatheringService.findSearch(searchMap));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Gathering gathering) {
		gatheringService.add(gathering);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Gathering gathering, @PathVariable String id) {
		gathering.setId(id);
		gatheringService.update(gathering);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		gatheringService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

}
