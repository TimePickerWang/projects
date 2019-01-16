package com.wang.service;

import com.google.common.base.Joiner;
import com.wang.beans.CacheKeyConstants;
import com.wang.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * Create by wangjf
 * Date 2019/1/15 18:36
 */
@Service
@Slf4j
public class SysCacheService {
	@Resource(name = "redisPool")
	private RedisPool redisPool;


	public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {
		saveCache(toSavedValue, timeoutSeconds, prefix, null);
	}

	public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys) {
		if (toSavedValue == null) {
			return;
		}
		ShardedJedis shardedJedis = null;
		try {
			String cacheKey = generateCacheKey(prefix, keys);
			shardedJedis = redisPool.instance();
			shardedJedis.setex(cacheKey, timeoutSeconds, toSavedValue); // 设置缓存
		} catch (Exception e) {
			log.error("save cache exception, perfix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
		} finally {
			redisPool.safeClose(shardedJedis);
		}
	}

	// 获取缓存
	public String getFromCache(CacheKeyConstants prefix, String... keys) {
		ShardedJedis shardedJedis = null;
		String key = generateCacheKey(prefix, keys);
		try {
			shardedJedis = redisPool.instance();
			String value = shardedJedis.get(key);
			return value;
		} catch (Exception e) {
			log.error("get from cache exception, perfix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
			return null;
		} finally {
			redisPool.safeClose(shardedJedis);
		}
	}

	// 生成key
	private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
		String key = prefix.name();
		if (keys != null && keys.length > 0) {
			key += "_" + Joiner.on("_").join(keys);
		}
		return key;
	}


}
