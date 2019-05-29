package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Column;


public interface ColumnDao extends JpaRepository<Column, String>, JpaSpecificationExecutor<Column> {

}
