package com.tensquare.qa.controller;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {
	@Autowired
	private ProblemService problemService;

	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Problem problem) {
		problemService.add(problem);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id) {
		problem.setId(id);
		problemService.update(problem);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		problemService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 根据标签ID查询最新问题列表
	 */
	@RequestMapping(value = "/newlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
	public Result findNewListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
		Page<Problem> pageList = problemService.newList(labelId, page, size);
		PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
		return new Result(true, StatusCode.OK, "查询成功", pageResult);
	}

	/**
	 * 根据标签ID查询热门问题列表
	 */
	@RequestMapping(value = "/hotlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
	public Result findHotListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
		Page<Problem> pageList = problemService.hotList(labelId, page, size);
		PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
		return new Result(true, StatusCode.OK, "查询成功", pageResult);
	}

	/**
	 * 根据标签ID查询等待回答列表
	 */
	@RequestMapping(value = "/waitlist/{labelId}/{page}/{size}", method = RequestMethod.GET)
	public Result findWaitListByLabelId(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
		Page<Problem> pageList = problemService.waitList(labelId, page, size);
		PageResult<Problem> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
		return new Result(true, StatusCode.OK, "查询成功", pageResult);
	}
}
