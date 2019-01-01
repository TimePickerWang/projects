package com.wang.repository;

import com.wang.models.ProductInfo;
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
public class ProductInfoRepositoryTest {
	@Autowired
	private ProductInfoRepository productInfoRepository;

	@Test
	public void savaTest() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123456");
		productInfo.setProductName("酱香饼");
		productInfo.setProductPrice(new BigDecimal(3.0));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("味道棒极了");
		productInfo.setProductIcon("http://xxx.jpg");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(2);

		ProductInfo result = productInfoRepository.save(productInfo);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByProductStatusTest() {
		List<ProductInfo> list = productInfoRepository.findByProductStatus(0);
		Assert.assertNotEquals(0, list.size());
	}


}