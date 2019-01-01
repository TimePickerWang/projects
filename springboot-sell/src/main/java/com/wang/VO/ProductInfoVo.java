package com.wang.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * Create by wangjf
 * Date 2018/12/29 15:18
 */
@Data
public class ProductInfoVo {

	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPrice;

	@JsonProperty("description")
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;

}
