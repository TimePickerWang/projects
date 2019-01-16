package com.wang.dao;

import com.wang.beans.PageQuery;
import com.wang.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysAcl record);

	int insertSelective(SysAcl record);

	SysAcl selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysAcl record);

	int updateByPrimaryKey(SysAcl record);

	int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

	int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

	List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") Integer aclModuleId, @Param("page") PageQuery page);

	List<SysAcl> getAll();

	List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

	List<SysAcl> getByUrl(@Param("url") String url);
}