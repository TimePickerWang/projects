package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create by wangjf
 * Date 2019/5/28 18:28
 */
public interface SpitDao extends MongoRepository<Spit, String> {

	public Page<Spit> findByParentid(String parentid, Pageable pageable);

}
