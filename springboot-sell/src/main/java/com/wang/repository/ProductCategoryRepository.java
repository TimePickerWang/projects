package com.wang.repository;

import com.wang.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/28 14:28
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
