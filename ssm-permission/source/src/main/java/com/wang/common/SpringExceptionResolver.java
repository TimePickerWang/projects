package com.wang.common;

import com.wang.exception.ParamException;
import com.wang.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by wangjf
 * Date 2019/1/3 11:50
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
		String url = request.getRequestURL().toString();
		String defaultMsg = "System error";
		ModelAndView mv = null;
		JsonData result = null;

		// .json .page页面请求
		// 要求项目中所有请求json数据，都是用.json结尾
		if (url.endsWith(".json")) {
			if (e instanceof PermissionException || e instanceof ParamException) {
				result = JsonData.fail(e.getMessage());
			} else {
				log.error("unkonw json excepton, url:" + url, e);
				result = JsonData.fail(defaultMsg);
			}
			mv = new ModelAndView("jsonView", result.toMap());
		} else if (url.endsWith(".page")) { // 要求项目中所有请求page页面，都是用.page结尾
			log.error("unkonw page excepton, url:" + url, e);
			result = JsonData.fail(defaultMsg);
			mv = new ModelAndView("exception", result.toMap());
		} else {
			log.error("unkonw excepton, url:" + url, e);
			result = JsonData.fail(defaultMsg);
			mv = new ModelAndView("jsonView", result.toMap());
		}
		return mv;
	}
}
