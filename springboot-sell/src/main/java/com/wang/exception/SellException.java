package com.wang.exception;

import com.wang.enums.ResultEnum;

/**
 * Create by wangjf
 * Date 2018/12/30 18:27
 */
public class SellException extends RuntimeException {
	private Integer code;

	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}

	public SellException(Integer code, String message) {
		super(message);
		this.code = code;
	}

}
