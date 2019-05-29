package com.tensquare.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * Create by wangjf
 * Date 2019/5/28 17:22
 */
@SpringBootApplication
public class SpitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpitApplication.class);
	}

	@Bean
	public IdWorker idWorkker() {
		return new IdWorker(1, 1);
	}
	
}
