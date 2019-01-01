package com.wang.service.Impl;

import com.wang.dto.OrderDTO;
import com.wang.enums.ResultEnum;
import com.wang.exception.SellException;
import com.wang.service.BuyerService;
import com.wang.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by wangjf
 * Date 2018/12/31 20:38
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
	@Autowired
	private OrderService orderService;


	private OrderDTO checkOrderOwner(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			return null;
		}
		// 判断是否是自己的订单
		if (!orderDTO.getBuyerOpenid().equals(openid)) {
			log.error("【查询订单】订单的openid不一致.openid={}, orderDTO={}", openid, orderDTO);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDTO;
	}


	@Override
	public OrderDTO findOrderOne(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
	}


	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openid, orderId);
		if (orderDTO == null) {
			log.error("【取消订单】查不到该订单, orderId={}", orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancle(orderDTO);
	}

}
