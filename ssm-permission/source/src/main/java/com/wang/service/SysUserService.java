package com.wang.service;

import com.google.common.base.Preconditions;
import com.wang.beans.PageQuery;
import com.wang.beans.PageResult;
import com.wang.common.RequestHolder;
import com.wang.dao.SysUserMapper;
import com.wang.exception.ParamException;
import com.wang.model.SysUser;
import com.wang.param.UserParam;
import com.wang.util.BeanValidator;
import com.wang.util.IpUtil;
import com.wang.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/1/7 16:16
 */
@Service
public class SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysLogService sysLogService;


	// 检查参数是否合法
	private void doCheck(UserParam param) {
		BeanValidator.check(param);
		if (checkTelephoneExist(param.getTelephone(), param.getId())) {
			throw new ParamException("电话已被占用");
		}
		if (checkEmailExist(param.getMail(), param.getId())) {
			throw new ParamException("邮箱已被占用");
		}
	}

	private boolean checkEmailExist(String mail, Integer userId) {
		return sysUserMapper.countByMail(mail, userId) > 0;
	}

	private boolean checkTelephoneExist(String telephone, Integer userId) {
		return sysUserMapper.countByTelephone(telephone, userId) > 0;
	}

	/**
	 * 生成密码的方案是首先随机生成8~10位由数字和字母组成的密码，然后将生成的
	 * 密码通过邮件发送给用户,最后将密码通过MD5进行加密并存到数据库。
	 * 这里为了方便直接取密码为“12345678”
	 */
	public void save(UserParam param) {
		doCheck(param); // 检查参数是否合法
//		String password = PasswordUtil.randomPassword();  // TODO
		String password = "12345678";
		String encryptedPassword = MD5Util.encrypt(password);

		SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
				.password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
		setUserProperties(user);

		// TODO: Email the user's password.
		sysUserMapper.insertSelective(user);
		sysLogService.saveUserLog(null, user);
	}

	public void update(UserParam param) {
		doCheck(param); // 检查参数是否合法
		SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "待更新的用户不存在");

		SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
				.deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
		setUserProperties(after);

		sysUserMapper.updateByPrimaryKeySelective(after);
		sysLogService.saveUserLog(before, after);
	}

	// 设置用户的其余字段
	private void setUserProperties(SysUser sysUser) {
		sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysUser.setOperateTime(new Date());
	}


	public SysUser findByKeyword(String keyword) {
		return sysUserMapper.findByKeyWord(keyword);
	}


	public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
		BeanValidator.check(pageQuery);
		int count = sysUserMapper.countByDeptId(deptId);
		PageResult<SysUser> pageResult = new PageResult();
		if (count > 0) {
			List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);
			pageResult.setTotal(count);
			pageResult.setData(list);
		}
		return pageResult;
	}

	public List<SysUser> getAll() {
		return sysUserMapper.getAll();
	}
}
