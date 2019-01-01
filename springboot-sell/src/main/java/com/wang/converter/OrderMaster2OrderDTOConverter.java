package com.wang.converter;

import com.wang.dto.OrderDTO;
import com.wang.models.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by wangjf
 * Date 2018/12/31 11:31
 */
public class OrderMaster2OrderDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}

	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
	}


}
