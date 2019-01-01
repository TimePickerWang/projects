package com.wang.dto;

import lombok.Data;

/**
 * Create by wangjf
 * Date 2018/12/30 21:11
 */
@Data
public class CartDTO {
	// 商品id
	private String productId;

	// 数量
	private Integer productQuantity;

	public CartDTO(String productId, Integer productQuantity) {
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
}
