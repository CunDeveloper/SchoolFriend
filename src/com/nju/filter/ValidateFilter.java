package com.nju.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.nju.util.Constant;
import com.nju.util.Validate;
import com.nju.validate.UserContentRules;
import com.nju.validate.ValidateController;

/**
 * Servlet Filter implementation class ValidateFilter
 */
@WebFilter(filterName="ValidateFilter",urlPatterns="/*",asyncSupported = true)
public class ValidateFilter extends BaseFilter {
	
    public ValidateFilter() {
      
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		
		if (isRight(path,request)) {
			chain.doFilter(request, response);
		}else{
			PrintWriter out = response.getWriter();
			out.print("参数不符合要求");
			out.flush();
			out.close();
		}
	}
 
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("validate init ok");
	}
	
	private boolean isContainEmpty(final ServletRequest request){
		Enumeration<String> emu = request.getParameterNames();
		while (emu.hasMoreElements()) {
			String para = emu.nextElement();
			logger.info(para);
			if(Validate.isEmpty(request.getParameter(para))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean isRightUserPublishContent(final ServletRequest request) {
		UserContentRules rules = ValidateController.getUserContentRules();
		Enumeration<String> emu = request.getParameterNames();
		boolean result = true;
		while (emu.hasMoreElements()) {
			String para = emu.nextElement();
			switch(para){
				case Constant.USER_ID:{
					result = Validate.isRight(rules.getUser_id(),request.getParameter(Constant.USER_ID));
					break;
				}
				case Constant.LABLE:{
					result = Validate.isRight(rules.getLable(),request.getParameter(Constant.LABLE));
					break;
				}
		    }
			if(!result){
				break;
			} 
		}
		return result;
	}
	
	private boolean  isRight(final String path,final ServletRequest request){
		switch(path){
		case Constant.XUE_XIN_AUTH_CONTROLLER:
			return true;
		case Constant.FILE_UPLOAD_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.FILE_UPLOAD_USER_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.FILE_UPLOAD_USER_UPDATE_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.PRAISE_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.PUBLISH_TEXT_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.USER_COMMENT_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.USER_CONTENT_CONTROLLER:
			return isRightUserPublishContent(request);
		case Constant.USER_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.USER_INFO_CONTROLLER:
			return !isContainEmpty(request);
		case Constant.USER_PUBLISH_CONTENT_CONTROLLER:
			return !isContainEmpty(request);
		}
		return true;
	}

}
