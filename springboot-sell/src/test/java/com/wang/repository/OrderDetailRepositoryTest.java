package com.wang.repository;

import com.wang.models.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Test
	public void saveTest() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDetailId("123456789");
		orderDetail.setOrderId("1111111");
		orderDetail.setProductIcon("http://xxxx.jpg");
		orderDetail.setProductId("12345");
		orderDetail.setProductName("皮蛋粥");
		orderDetail.setProductPrice(new BigDecimal(5.5));
		orderDetail.setProductQuantity(10);

		OrderDetail result = orderDetailRepository.save(orderDetail);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByOrderIdTest() {
		List<OrderDetail> list = orderDetailRepository.findByOrderId("1111111");
		Assert.assertNotEquals(0, list.size());
	}

}