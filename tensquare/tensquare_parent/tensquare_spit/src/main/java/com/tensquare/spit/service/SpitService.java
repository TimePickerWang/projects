package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/5/28 18:38
 */
@Service
public class SpitService {
	@Autowired
	private SpitDao spitDao;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private MongoTemplate mongoTemplate;


	/**
	 * 查询全部记录
	 */
	public List<Spit> findAll() {
		return spitDao.findAll();
	}

	/**
	 * 根据主键查询实体
	 */
	public Spit findById(String id) {
		return spitDao.findById(id).get();
	}

	/**
	 * 增加
	 */
	public void add(Spit spit) {
		spit.set_id(idWorker.nextId() + ""); //主键值
		spitDao.save(spit);
	}

	/**
	 * 修改
	 */
	public void update(Spit spit) {
		spitDao.save(spit);
	}

	/**
	 * 删除
	 */
	public void deleteById(String id) {
		spitDao.deleteById(id);
	}

	public Page<Spit> findByParentid(String parentid, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return spitDao.findByParentid(parentid, pageable);
	}

	/**
	 * 点赞
	 */
	public void updateThumbup(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is("id"));
		Update update = new Update();
		update.inc("thumbup", 1);
		mongoTemplate.updateFirst(query, update, "spit");
	}


	/**
	 * 发布吐槽（或吐槽评论）
	 */
	public void addSpit(Spit spit) {
		spit.set_id(idWorker.nextId() + "");
		spit.setPublishtime(new Date()); // 发布日期
		spit.setVisits(0); // 浏览量
		spit.setShare(0); // 分享数
		spit.setThumbup(0); // 点赞数
		spit.setComment(0); // 回复数
		spit.setState("1"); // 状态
		// 如果存在上级评论
		if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
			Update update = new Update();
			update.inc("comment", 1);
			mongoTemplate.updateFirst(query, update, "spit");
		}
		spitDao.save(spit);
	}

}
