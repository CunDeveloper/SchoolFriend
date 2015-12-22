package com.nju.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class CharSetFilter
 */
@WebFilter(filterName="CharSetFilter",urlPatterns="/*",asyncSupported = true)
public class CharSetFilter extends BaseFilter {
    public CharSetFilter() {
      
    }
    
	public void destroy() {
		 
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
		 logger.info("response charset set ok");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		 logger.info("char set init ok");
	}

}
