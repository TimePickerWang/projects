package com.wang.model;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysRoleAcl {
	private Integer id;

	private Integer roleId;

	private Integer aclId;

	private String operator;

	private Date operateTime;

	private String operateIp;

}