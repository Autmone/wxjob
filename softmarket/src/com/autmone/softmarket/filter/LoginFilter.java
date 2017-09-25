package com.autmone.softmarket.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.autmone.softmarket.util.Constants;
import com.autmone.softmarket.util.TokenUtil;
import com.autmone.softmarket.vo.TokenDto;

/**
 * 登录过滤器
 * 
 * @author zhuwenjun 20140506
 * 
 */
public class LoginFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (!(req instanceof HttpServletRequest)) {
			chain.doFilter(req, response);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String token = request.getParameter("token");

		if (token == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies == null || cookies.length == 0) {
				chain.doFilter(req, response);
				return;
			}
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (Constants.TOKEN_COOKIE_NAME.equals(cookie.getName())) {
						token = cookie.getValue();
						break;
					}
				}
			}
		}

		if (token == null) {
			chain.doFilter(req, response);
			return;
		}

		TokenDto dto = TokenUtil.parseToken(token);
		if (dto.getResult() == TokenUtil.ERROR_TOKEN) {
			chain.doFilter(req, response);
			return;
		}

		if (dto.getResult() == TokenUtil.TIMEOUT_TOKEN) {
			chain.doFilter(req, response);
			return;
		}

		request.setAttribute("user_id", dto.getUserId());
		request.setAttribute("user_type", dto.getUserType());
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
