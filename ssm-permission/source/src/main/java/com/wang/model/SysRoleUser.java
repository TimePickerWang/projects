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
public class SysRoleUser {
	private Integer id;

	private Integer roleId;

	private Integer userId;

	private String operator;

	private Date operateTime;

	private String operateIp;

}