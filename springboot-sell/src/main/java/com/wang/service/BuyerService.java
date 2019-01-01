package com.wang.service;

import com.wang.dto.OrderDTO;

/**
 * 买家
 * Create by wangjf
 * Date 2018/12/31 20:36
 */
public interface BuyerService {
	// 查询一个订单
	OrderDTO findOrderOne(String openid, String orderId);

	// 取消订单
	OrderDTO cancelOrder(String openid, String orderId);

}
