package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

	@Query(value = "SELECT * FROM tb_problem JOIN tb_pl ON tb_problem.id = tb_pl.problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
	public Page<Problem> newList(String labelId, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem JOIN tb_pl ON tb_problem.id = tb_pl.problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
	public Page<Problem> hotList(String labelId, Pageable pageable);

	@Query(value = "SELECT * FROM tb_problem JOIN tb_pl ON tb_problem.id = tb_pl.problemid AND labelid = ? AND reply = 0 ORDER BY createtime", nativeQuery = true)
	public Page<Problem> waitList(String labelId, Pageable pageable);
}
