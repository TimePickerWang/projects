package com.wang.dto;

import com.wang.model.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AclDto extends SysAcl {

	// 是否要默认选中
	private boolean checked = false;

	// 是否有权限操作
	private boolean hasAcl = false;

	public static AclDto adapt(SysAcl acl) {
		AclDto dto = new AclDto();
		BeanUtils.copyProperties(acl, dto);
		return dto;
	}
}
