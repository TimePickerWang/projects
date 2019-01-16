package com.wang.service;

import com.google.common.base.Preconditions;
import com.wang.beans.PageQuery;
import com.wang.beans.PageResult;
import com.wang.common.RequestHolder;
import com.wang.dao.SysAclMapper;
import com.wang.exception.ParamException;
import com.wang.model.SysAcl;
import com.wang.param.AclParam;
import com.wang.util.BeanValidator;
import com.wang.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/10 21:42
 */
@Service
public class SysAclService {
	@Autowired
	private SysAclMapper sysAclMapper;
	@Autowired
	private SysLogService sysLogService;


	public void save(AclParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
			throw new ParamException("当前权限模块下面存在相同名称的权限点");
		}
		SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
				.type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
		acl.setCode(generateCode());
		setAclProperties(acl); // 设置权限点的其余属性
		sysAclMapper.insertSelective(acl);
		sysLogService.saveAclLog(null, acl);
	}

	public void update(AclParam param) {
		BeanValidator.check(param);
		if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
			throw new ParamException("当前权限模块下面存在相同名称的权限点");
		}
		SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的权限点不存在");

		SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
				.type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
		setAclProperties(after);  // 设置权限点的其余属性

		sysAclMapper.updateByPrimaryKeySelective(after);
		sysLogService.saveAclLog(before, after);
	}

	private boolean checkExist(int aclModuleId, String name, Integer id) {
		return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
	}

	// 生成唯一的code
	private String generateCode() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
	}

	private void setAclProperties(SysAcl sysAcl) {
		sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysAcl.setOperateTime(new Date());
		sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
	}

	public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
		BeanValidator.check(pageQuery);
		int count = sysAclMapper.countByAclModuleId(aclModuleId);
		if (count > 0) {
			List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
			return PageResult.<SysAcl>builder().data(aclList).total(count).build();
		}
		return PageResult.<SysAcl>builder().build();
	}
}
