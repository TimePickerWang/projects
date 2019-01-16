package com.wang.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * Create by wangjf
 * Date 2019/1/15 18:27
 */
@Service("redisPool")
@Slf4j
public class RedisPool {
	@Resource(name = "shardedJedisPool")
	private ShardedJedisPool shardedJedisPool;

	//获取实例
	public ShardedJedis instance() {
		return shardedJedisPool.getResource();
	}

	// 释放链接
	public void safeClose(ShardedJedis shardedJedis) {
		try {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		} catch (Exception e) {
			log.error("return redis resource exception", e);
		}
	}

}
