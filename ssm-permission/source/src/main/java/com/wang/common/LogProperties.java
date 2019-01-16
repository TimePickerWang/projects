package com.wang.common;

import com.wang.model.SysLogWithBLOBs;
import com.wang.util.IpUtil;
import com.wang.util.JsonMapper;

import java.util.Date;

/**
 * Create by wangjf
 * Date 2019/1/16 20:36
 */
public class LogProperties {

	public static <T> void setSysLogProperties(SysLogWithBLOBs sysLog, T before, T after) {
		sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
		sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
		sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
		sysLog.setOperateId(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
		sysLog.setOperateTime(new Date());
		sysLog.setStatus(1);
	}
}
