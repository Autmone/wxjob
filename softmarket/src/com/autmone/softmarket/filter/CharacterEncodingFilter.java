package com.autmone.softmarket.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharacterEncodingFilter implements Filter {

	/**
	 * 编码格式
	 */
	private String encoding;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(req instanceof HttpServletRequest)) {
			chain.doFilter(req, response);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) req;
		request.setCharacterEncoding(this.encoding);
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		chain.doFilter(req, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
	}

}
