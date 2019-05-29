package com.tensquare.article.controller;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", articleService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", articleService.findById(id));
	}

	/**
	 * 分页+多条件查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Article article) {
		articleService.add(article);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Article article, @PathVariable String id) {
		article.setId(id);
		articleService.update(article);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		articleService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 审核
	 */
	@RequestMapping(value = "/examine/{id}", method = RequestMethod.PUT)
	public Result examine(@PathVariable String id) {
		articleService.updateState(id);
		return new Result(true, StatusCode.OK, "审核成功！");
	}

	/**
	 * 点赞
	 */
	@RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
	public Result updateThumbup(@PathVariable String id) {
		articleService.updateThumbup(id);
		return new Result(true, StatusCode.OK, "点赞成功");
	}

}
