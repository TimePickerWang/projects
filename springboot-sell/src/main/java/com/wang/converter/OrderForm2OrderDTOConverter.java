package com.wang.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.dto.OrderDTO;
import com.wang.enums.ResultEnum;
import com.wang.exception.SellException;
import com.wang.form.OrderForm;
import com.wang.models.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wangjf
 * Date 2018/12/31 16:14
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
	public static OrderDTO convert(OrderForm orderForm) {
		Gson gson = new Gson();

		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());

		List<OrderDetail> orderDetailList;
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(),
					new TypeToken<List<OrderDetail>>() {
					}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误, string={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		orderDTO.setOrderDetailList(orderDetailList);

		return orderDTO;
	}


}
