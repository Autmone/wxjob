package com.autmone.softmarket.util;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.autmone.softmarket.vo.TokenDto;

/**
 * 访问令牌工具类
 * 
 * @author zhuwenjun 20140430
 * 
 */
public class TokenUtil {

	/** log4j */
	private static final Logger log = Logger.getLogger("base");

	/** 私钥 */
	private static final String PRIVATE_KEY = Constants.TOKEN_PRIVATE_KEY;

	/** token超时时长，单位:毫秒 */
	private static final long TOKEN_TIMEOUT_INTERVAL = Constants.TOKEN_TIMEOUT;

	/** 有效token */
	public static final int VALIDATE_TOKEN = 0;
	/** 错误token */
	public static final int ERROR_TOKEN = 1;
	/** 过期token */
	public static final int TIMEOUT_TOKEN = 2;
	

	/**
	 * 构造token
	 * @return
	 */
	public static String buildToken(String userType, int userId) {

		long unixTimestamp = System.currentTimeMillis() / 1000;
		int confusionCode = getConfusionCode();

		// 用于计算MD5的字符串
		StringBuilder md5 = new StringBuilder();
		md5.append(unixTimestamp);
		md5.append(userId);
		md5.append(userType);
		md5.append(PRIVATE_KEY);

		// 整个token
		StringBuilder token = new StringBuilder();
		token.append(userType);
		token.append(unixTimestamp);
		token.append(confusionCode);
		token.append(userId);
		token.append(MD5.MD5Encode(md5.toString()));
		return token.toString();
	}

	/**
	 * 获得混淆码
	 * 
	 * @return
	 */
	private static int getConfusionCode() {
		Random ran = new Random();
		return ran.nextInt(10);
	}
	

	/**
	 * 解析token
	 * 
	 * @param token
	 * @return
	 */
	public static TokenDto parseToken(String token) {
		// 令牌 = 用户类型(1) + UNIX时间戳(10) + 混淆码(1) + 用户id(N) + MD5(UNIX时间戳(10) +
		// 用户id(N) + 用户类型(1) +私钥(N)) + partnerId(N) + 混淆码(1) + 混淆码(1)
		TokenDto dto = new TokenDto();
		if (Util.isEmpty(token)) {
			log.error("token为空:" + token);
			dto.setResult(ERROR_TOKEN);
			return dto;
		}

		if (token.length() < 45) {
			log.error("token长度错误:" + token);
			dto.setResult(ERROR_TOKEN);
			return dto;
		}
		
		String userType = token.substring(0, 1);
		String unixTimestamp = token.substring(1, 11);
		String userId = token.substring(12, token.length() - 32);
		String md5Str = token.substring(token.length() - 32, token.length());
		
		//验证ID是否为整数
		if(!Util.isNumeric(userId)) {
			log.error("token验证错误:" + token + ",userId:" + userId );
			dto.setResult(ERROR_TOKEN);
			return dto;
		}
		
		// 用于计算MD5的字符串
		StringBuilder sbMd5 = new StringBuilder();
		sbMd5.append(unixTimestamp);
		sbMd5.append(userId);
		sbMd5.append(userType);
		sbMd5.append(PRIVATE_KEY);
		String myMd5 = MD5.MD5Encode(sbMd5.toString());
		if (!md5Str.equals(myMd5)) {
			log.error("token验证错误:" + token);
			dto.setResult(ERROR_TOKEN);
			return dto;
		}

		long loginTime = Long.parseLong(unixTimestamp) * 1000;
		long now = System.currentTimeMillis();

		if (loginTime > now) {
			log.error(String.format(
					"token时间错误,登录时间比系统时间还大，登录时间=%s,系统时间=%s,token=%s",
					loginTime, now, token));
			dto.setResult(ERROR_TOKEN);
			return dto;
		}

		int customerId = Integer.parseInt(userId);
		long tokenTimeoutInterval = TOKEN_TIMEOUT_INTERVAL;
//		// 如果是顶级客户的，那么超时时间变短，10分钟
//		if (customerId == CommonConstants.TOP_CUSTOMER_ID) {
//			//tokenTimeoutInterval = 10 * 60 * 1000;
//		}
		dto.setTime(new Date(Long.parseLong(unixTimestamp) * 1000));
		dto.setUserId(customerId);
		dto.setResult(VALIDATE_TOKEN);
		dto.setUserType(userType);

		if (now - loginTime > tokenTimeoutInterval) {
			log.error("token超时:" + token + ",time="
					+ new Date(loginTime)
					+ ",customerId=" + customerId);
			dto.setResult(TIMEOUT_TOKEN);
			return dto;
		}

		
		return dto;
	}
	
	

}
