package com.wang.dto;

import lombok.Data;

import java.util.Date;

/**
 * Create by wangjf
 * Date 2019/1/16 11:40
 */
@Data
public class SearchLogDto {

	private Integer type;

	private String beforeSeg;

	private String afterSeg;

	private String operator;

	private Date fromTime;//yyyy-MM-dd HH:mm:ss

	private Date toTime;
}
