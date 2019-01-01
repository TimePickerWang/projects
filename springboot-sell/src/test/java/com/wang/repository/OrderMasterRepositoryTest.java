package com.wang.repository;

import com.wang.models.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
	@Autowired
	private OrderMasterRepository orderMasterRepository;

	private final static String OPENID = "123456";

	@Test
	public void saveTest() {
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId("1234568");
		orderMaster.setBuyerName("WJF");
		orderMaster.setBuyerPhone("123456789123");
		orderMaster.setBuyerAddress("南山");
		orderMaster.setBuyerOpenid(OPENID);
		orderMaster.setOrderAmount(new BigDecimal(2.5));
		orderMaster.setCreateTime(new Date());
		orderMaster.setUpdateTime(new Date());

		OrderMaster result = orderMasterRepository.save(orderMaster);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByBuyerOpenid() {
		PageRequest pageRequest = new PageRequest(0, 2);

		Page<OrderMaster> list = orderMasterRepository.findByBuyerOpenid(OPENID, pageRequest);
		Assert.assertNotEquals(0, list.getTotalElements());
	}
}