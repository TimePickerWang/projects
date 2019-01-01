package com.wang.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
	WAIT(0, "等待支付"),
	SUCCESS(1, "支付成功");

	private Integer code;

	private String massage;

	PayStatusEnum(Integer code, String massage) {
		this.code = code;
		this.massage = massage;
	}
}
