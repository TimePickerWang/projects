package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by wangjf
 * Date 2019/4/16 18:38
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
	@Autowired
	private LabelService labelService;

	/**
	 * 查询全部列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
	}

	/**
	 * 根据ID查询标签
	 */
	@RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
	public Result findById(@PathVariable String labelId) {
		return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
	}

	/**
	 * 增加标签
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result save(@RequestBody Label label) {
		labelService.save(label);
		return new Result(true, StatusCode.OK, "添加成功");
	}

	/**
	 * 修改标签
	 */
	@RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
	public Result update(@PathVariable String labelId, @RequestBody Label label) {
		label.setId(labelId);
		labelService.update(label);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除标签
	 */
	@RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
	public Result deleteById(@PathVariable String labelId) {
		labelService.deleteById(labelId);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Label label) {
		List<Label> list = labelService.findSearch(label);
		return new Result(true, StatusCode.OK, "查询成功", list);
	}

	/**
	 * 条件+分页查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
		Page<Label> pageDate = labelService.pageQuery(label, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageDate.getTotalElements(), pageDate.getContent()));
	}


}
