package com.wang.service;

import com.wang.models.ProductCategory;

import java.util.List;

/**
 * 类目
 * Create by wangjf
 * Date 2018/12/28 19:45
 */
public interface CategoryService {
	ProductCategory findOne(Integer categoryId);

	List<ProductCategory> findAll();

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);
}
