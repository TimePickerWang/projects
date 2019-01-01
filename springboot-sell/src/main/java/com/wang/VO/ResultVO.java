package com.wang.VO;

import lombok.Data;

/**
 * Create by wangjf
 * Date 2018/12/29 13:54
 */
@Data
public class ResultVO<T> {
	// 错误码
	private Integer code;

	// 提示信息
	private String msg;

	// 具体内容
	private T data;


}
