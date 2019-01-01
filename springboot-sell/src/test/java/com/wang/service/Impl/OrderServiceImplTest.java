package com.wang.service.Impl;

import com.wang.dto.OrderDTO;
import com.wang.enums.OrderStatusEnum;
import com.wang.enums.PayStatusEnum;
import com.wang.models.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
	@Autowired
	private OrderServiceImpl orderService;

	private static final String BUYER_OPENID = "123456";

	private static final String ORDER_ID = "1546179305525956665";

	@Test
	public void create() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName("WJF");
		orderDTO.setBuyerAddress("南山");
		orderDTO.setBuyerPhone("12345678912");
		orderDTO.setBuyerOpenid(BUYER_OPENID);

		// 购物车
		List<OrderDetail> orderDetailList = new ArrayList<>();
		OrderDetail o1 = new OrderDetail();
		o1.setProductId("123456");
		o1.setProductQuantity(1);

		OrderDetail o2 = new OrderDetail();
		o1.setProductId("123457");
		o1.setProductQuantity(2);

		orderDetailList.add(o1);
		orderDetailList.add(o2);
		orderDTO.setOrderDetailList(orderDetailList);

		OrderDTO result = orderService.create(orderDTO);
//		log.info("【创建订单】result={}", result);
		Assert.assertNotNull(result);
	}

	@Test
	public void findOne() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		log.info("【查询单个订单】result={}", orderDTO);
		Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());
	}

	@Test
	public void findList() {
		PageRequest pageRequest = new PageRequest(0, 2);
		Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageRequest);
		Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
	}

	@Test
	public void cancle() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.cancle(orderDTO);
		Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
	}

	@Test
	public void finish() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.finish(orderDTO);
		Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	}

	@Test
	public void paid() {
		OrderDTO orderDTO = orderService.findOne(ORDER_ID);
		OrderDTO result = orderService.paid(orderDTO);
		Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	}
}