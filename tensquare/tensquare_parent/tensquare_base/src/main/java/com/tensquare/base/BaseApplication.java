package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * Create by wangjf
 * Date 2019/4/16 18:18
 */
@SpringBootApplication
public class BaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class);
	}

	@Bean
	public IdWorker idWorker() {
		return new IdWorker(1, 1);
	}
}
