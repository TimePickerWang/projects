package com.wang.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by wangjf
 * Date 2018/12/28 11:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

	@Test
	public void test1() {
		String name = "wjf";
		String password = "123456";
		log.debug("debug...");
		log.info("info...name:{},password:{}", name, password);
		log.error("error...");
	}


}
