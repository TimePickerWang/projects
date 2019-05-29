package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Create by wangjf
 * Date 2019/5/28 18:46
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
	@Autowired
	private SpitService spitService;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findOne(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Spit spit) {
		spitService.add(spit);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Spit spit, @PathVariable String id) {
		spit.set_id(id);
		spitService.update(spit);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result deleteById(@PathVariable String id) {
		spitService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

	/**
	 * 根据上级ID查询吐槽列表（分页）
	 */
	@RequestMapping(value = "/comment/{parentId}/{page}/{size}", method = RequestMethod.GET)
	public Result findByParentid(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
		Page<Spit> pageData = spitService.findByParentid(parentId, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
	}

	/**
	 * 点赞
	 */
	@RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
	public Result updateThumbup(@PathVariable String id) {
		String userid = "123";
		//判断用户是否点过赞
		if (redisTemplate.opsForValue().get("thumbup_" + userid + "_" + id) != null) {
			return new Result(true, StatusCode.OK, "你已经点过赞了");
		} else {
			redisTemplate.opsForValue().set("thumbup_" + userid + "_" + id, 1);
			spitService.updateThumbup(id);
			return new Result(true, StatusCode.OK, "点赞成功");
		}
	}


}
