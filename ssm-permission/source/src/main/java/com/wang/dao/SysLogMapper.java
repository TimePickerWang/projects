package com.wang.dao;

import com.wang.beans.PageQuery;
import com.wang.beans.PageResult;
import com.wang.dto.SearchLogDto;
import com.wang.model.SysLog;
import com.wang.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysLogWithBLOBs record);

	int insertSelective(SysLogWithBLOBs record);

	SysLogWithBLOBs selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysLogWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

	int updateByPrimaryKey(SysLog record);

	int countBySearchDto(@Param("dto") SearchLogDto dto);

	List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}