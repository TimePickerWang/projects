package com.wang.service;

import com.wang.dto.CartDTO;
import com.wang.models.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/29 12:33
 */
public interface ProductService {

	ProductInfo findOne(String producdId);

	// 查询所有在架商品列表
	List<ProductInfo> findUpAll();

	Page<ProductInfo> findAll(Pageable pageable);

	ProductInfo save(ProductInfo productInfo);

	// 加库存
	void increaseStock(List<CartDTO> cartDTOList);

	// 减库存
	void decreaseStock(List<CartDTO> cartDTOList);

}
