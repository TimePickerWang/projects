package com.wang.param;

import lombok.Data;

@Data
public class SearchLogParam {

	private Integer type;

	private String beforeSeg;

	private String afterSeg;

	private String operator;

	private String fromTime;

	private String toTime;
}
