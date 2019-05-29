package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

	@Modifying
	@Query(value = "UPDATE tb_article SET state = '1' WHERE id = ?", nativeQuery = true)
	public void updateState(String id);

	@Modifying
	@Query(value = "UPDATE tb_article SET thumbup = thumbup + 1 where id = ?", nativeQuery = true)
	public int updateThumbup(String id);

}
