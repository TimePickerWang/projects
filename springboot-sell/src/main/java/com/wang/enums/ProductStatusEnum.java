package com.wang.enums;

import lombok.Getter;

/**
 * Create by wangjf
 * Date 2018/12/29 12:47
 */
@Getter
public enum ProductStatusEnum {

	UP(0, "在架"),
	DOWN(1, "下架");


	private Integer code;
	private String message;

	ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}}
