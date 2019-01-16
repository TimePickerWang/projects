package com.wang.model;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysDept {
	private Integer id;

	private String name;

	private Integer parentId;

	private String level;

	private Integer seq;

	private String remark;

	private String operator;

	private Date operateTime;

	private String operateIp;

}