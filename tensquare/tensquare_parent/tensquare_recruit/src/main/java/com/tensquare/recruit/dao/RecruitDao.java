package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

	/**
	 * 查询最新职位列表(按创建日期降序排序)
	 */
	public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

	public List<Recruit> findTop4ByStateNotOrderByCreatetimeDesc(String state);

}
