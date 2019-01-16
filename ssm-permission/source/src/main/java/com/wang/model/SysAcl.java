package com.wang.model;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Data
public class SysAcl {
	private Integer id;

	private String code;

	private String name;

	private Integer aclModuleId;

	private String url;

	private Integer type;

	private Integer status;

	private Integer seq;

	private String remark;

	private String operator;

	private Date operateTime;

	private String operateIp;

}