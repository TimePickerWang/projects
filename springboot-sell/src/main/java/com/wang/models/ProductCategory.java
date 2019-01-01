package com.wang.models;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Create by wangjf
 * Date 2018/12/28 14:19
 */
@Entity
@Data
public class ProductCategory {
	// 类目id
	@Id
	@GeneratedValue
	private Integer categoryId;
	// 类目名字
	private String categoryName;
	// 类目编号
	private Integer categoryType;

	public ProductCategory() {
	}

	public ProductCategory(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}
}
