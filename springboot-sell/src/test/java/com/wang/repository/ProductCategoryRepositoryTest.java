package com.wang.repository;

import com.wang.models.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Test
	public void findOneTest() {
		ProductCategory productCategory = productCategoryRepository.findOne(1);
		System.out.println(productCategory);
	}

	@Test
	@Transactional  // 测试完成不会更改数据库
	public void saveTest() {
		ProductCategory productCategory = new ProductCategory("男生最爱", 3);
		productCategoryRepository.save(productCategory);
		Assert.assertNotNull(productCategory);
	}

	@Test
	public void findByCategoryTypeInTest() {
		List<Integer> list = Arrays.asList(2, 4);
		List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(list);
		result.stream().forEach(System.out::println);
		Assert.assertNotEquals(0, result.size());
	}
}