package com.wang.service.Impl;

import com.wang.models.ProductCategory;
import com.wang.repository.ProductCategoryRepository;
import com.wang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Create by wangjf
 * Date 2018/12/28 19:48
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		return productCategoryRepository.findOne(categoryId);
	}

	@Override
	public List<ProductCategory> findAll() {
		return productCategoryRepository.findAll();
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryRepository.save(productCategory);
	}
}
