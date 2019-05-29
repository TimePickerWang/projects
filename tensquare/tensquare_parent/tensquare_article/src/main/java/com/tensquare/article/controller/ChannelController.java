package com.tensquare.article.controller;

import com.tensquare.article.pojo.Channel;
import com.tensquare.article.service.ChannelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {
	@Autowired
	private ChannelService channelService;

	/**
	 * 查询全部数据
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Result findAll() {
		return new Result(true, StatusCode.OK, "查询成功", channelService.findAll());
	}

	/**
	 * 根据ID查询
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true, StatusCode.OK, "查询成功", channelService.findById(id));
	}

	/**
	 * 分页+多条件查询
	 */
	@RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
		Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
		return new Result(true, StatusCode.OK, "查询成功", new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()));
	}

	/**
	 * 根据条件查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap) {
		return new Result(true, StatusCode.OK, "查询成功", channelService.findSearch(searchMap));
	}

	/**
	 * 增加
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Result add(@RequestBody Channel channel) {
		channelService.add(channel);
		return new Result(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result update(@RequestBody Channel channel, @PathVariable String id) {
		channel.setId(id);
		channelService.update(channel);
		return new Result(true, StatusCode.OK, "修改成功");
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		channelService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

}
