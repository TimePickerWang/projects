package com.wang.filter;

import com.wang.common.RequestHolder;
import com.wang.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	// 用于判断某一请求是否需要用户登录，如果需要则通过判断session里的user
	// 是否为空，为空则跳到登录页面，否则将SysUser和request添加到RequestHolder（ThreadLocal）中
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		SysUser sysUser = (SysUser) req.getSession().getAttribute("user");
		if (sysUser == null) {
			String path = "/signin.jsp";
			resp.sendRedirect(path);
			return;
		}
		RequestHolder.add(sysUser);
		RequestHolder.add(req);
		filterChain.doFilter(servletRequest, servletResponse);
		return;
	}

	public void destroy() {

	}
}
