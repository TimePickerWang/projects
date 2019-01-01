package com.wang.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wang.models.OrderDetail;
import com.wang.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/30 17:32
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
	// 订单id
	private String orderId;

	// 买家名字
	private String buyerName;

	// 买家电话
	private String buyerPhone;

	// 买家地址
	private String buyerAddress;

	// 买家微信openid
	private String buyerOpenid;

	// 订单总金额
	private BigDecimal orderAmount;

	// 点单状态,默认0新下单
	private Integer orderStatus;

	// 支付状态,默认0未支付
	private Integer payStatus;

	// 创建时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	// 更新时间
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	private List<OrderDetail> orderDetailList;
}
