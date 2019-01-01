package com.wang.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 * Create by wangjf
 * Date 2018/12/29 15:19
 */
@Data
public class ProductVo {
	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	@JsonProperty("foods")
	private List<ProductInfoVo> productInfoVoList;

}
