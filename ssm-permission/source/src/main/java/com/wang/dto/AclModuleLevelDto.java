package com.wang.dto;

import com.google.common.collect.Lists;
import com.wang.model.SysAclModule;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class AclModuleLevelDto extends SysAclModule {

	private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

	private List<AclDto> aclList = Lists.newArrayList();

	public static AclModuleLevelDto adapt(SysAclModule aclModule) {
		AclModuleLevelDto dto = new AclModuleLevelDto();
		BeanUtils.copyProperties(aclModule, dto);
		return dto;
	}
}
