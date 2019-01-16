package com.wang.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysAclModule {
	private Integer id;

	private String name;

	private Integer parentId;

	private String level;

	private Integer seq;

	private Integer status;

	private String remark;

	private String operator;

	private Date operateTime;

	private String operateIp;

}