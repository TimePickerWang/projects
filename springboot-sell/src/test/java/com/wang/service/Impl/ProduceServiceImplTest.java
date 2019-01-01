package com.wang.service.Impl;

import com.wang.enums.ProductStatusEnum;
import com.wang.models.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceServiceImplTest {
	@Autowired
	private ProductServiceImpl productService;

	@Test
	public void findOne() {
		ProductInfo productInfo = productService.findOne("123456");
		Assert.assertNotNull("123456", productInfo.getProductId());
	}

	@Test
	public void findUpAll() {
		List<ProductInfo> list = productService.findUpAll();
		Assert.assertNotEquals(0, list.size());
	}

	@Test
	public void findAll() {
		PageRequest request = new PageRequest(0, 2);
		Page<ProductInfo> productInfoPage = productService.findAll(request);
//		System.out.println(productInfoPage.getTotalElements());
		Assert.assertNotEquals(0, productInfoPage.getTotalElements());
	}

	@Test
	public void save() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123457");
		productInfo.setProductName("皮蛋粥");
		productInfo.setProductPrice(new BigDecimal(3.0));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("很好喝的粥");
		productInfo.setProductIcon("http://xxx.jpg");
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		productInfo.setCategoryType(2);

		ProductInfo result = productService.save(productInfo);
		Assert.assertNotNull(result);
	}
}