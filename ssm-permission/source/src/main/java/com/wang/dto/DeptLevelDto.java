package com.wang.dto;

import com.google.common.collect.Lists;
import com.wang.model.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/3 18:26
 */
@Data
public class DeptLevelDto extends SysDept {

	private List<DeptLevelDto> deptList = Lists.newArrayList();

	public static DeptLevelDto adapt(SysDept dept) {
		DeptLevelDto dto = new DeptLevelDto();
		BeanUtils.copyProperties(dept, dto);
		return dto;
	}
}
