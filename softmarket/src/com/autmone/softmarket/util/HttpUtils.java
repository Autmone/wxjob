package com.autmone.softmarket.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpUtils {

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

	public String sendPost(String url, Object xmlObj) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        
		//post请求返回结果
		HttpClient httpClient = new DefaultHttpClient();
        //httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");//解决中文乱码
        String result = null;
        HttpPost httpPost = new HttpPost(url);
     // 使用POST方式提交数据  
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        
        //HttpMethod method = getPostMethod(url,postDataXML);
															
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, Charset.forName("UTF-8"));
        postEntity.setContentEncoding("UTF-8");
        postEntity.setContentType("text/xml;charset=utf-8");
        httpPost.setEntity(postEntity);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        //httpPost.addHeader("Content-Type", "text/xml;charset=utf-8");
        //httpPost.setEntity(postEntity);
        
        //log_info.info("executing request" + httpPost.getRequestLine());

        try {
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
        	if (statusCode != 200) {
        		httpPost.abort();
        		throw new RuntimeException("HttpClient,error status code :" + statusCode);
        	}
        	HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	httpPost.abort();
        }
        return result;
    }
    
	/**
	 * httpClient   get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String executeGet(String url) {  
  
		String result = "";
		
		HttpClient httpClient = new DefaultHttpClient();
		if(url.toLowerCase().startsWith("https")) {
			httpClient = WebClientDevWrapper.wrapClient(httpClient);
		}
		//HttpPost httpPost = new HttpPost(url);
		HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
		
        try {
        	HttpResponse response = httpClient.execute(httpGet);
//        	int statusCode = response.getStatusLine().getStatusCode();
//        	if (statusCode != 200) {
//        		httpGet.abort();
//        		throw new RuntimeException("HttpClient,error status code :" + statusCode);
//        	}
        	HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("---------------------   get 测试返回的数据是  ---------------  "+result);
           
        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	httpGet.abort();
        }
        return result;
    }  
	
	/**
	 * httpClient   带cookie的get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String executeGet(String url,String cookie) {  
  
		String result = "";
		
		HttpClient httpClient = new DefaultHttpClient();
		if(url.toLowerCase().startsWith("https")) {
			httpClient = WebClientDevWrapper.wrapClient(httpClient);
		}
		//HttpPost httpPost = new HttpPost(url);
		HttpGet httpGet = new HttpGet(url);

	    if(!Util.isEmpty(cookie)) {
	    	httpGet.setHeader("Cookie", cookie);
	    }
		RequestConfig requestConfig = null;
		requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
	    httpGet.setConfig(requestConfig);
		
        try {
        	HttpResponse response = httpClient.execute(httpGet);
//        	int statusCode = response.getStatusLine().getStatusCode();
//        	if (statusCode != 200) {
//        		httpGet.abort();
//        		throw new RuntimeException("HttpClient,error status code :" + statusCode);
//        	}
        	HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("---------------------   get 测试返回的数据是  ---------------  "+result);
//            log_info.info("返回的数据是："+result);

        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	httpGet.abort();
        }
        return result;
    }

	/**
	 * httpClient   post请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String executePost(String url,String data) {  
  
		String result = "";
		
		HttpClient httpClient = new DefaultHttpClient();
		if(url.toLowerCase().startsWith("https")) {
			httpClient = WebClientDevWrapper.wrapClient(httpClient);
		}
		//HttpPost httpPost = new HttpPost(url);
		HttpPost httpPost = new HttpPost(url); 
		StringEntity postEntity = new StringEntity(data, Charset.forName("UTF-8"));
        postEntity.setContentEncoding("UTF-8");
        postEntity.setContentType("text/xml;charset=utf-8");
        httpPost.setEntity(postEntity);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
        	HttpResponse response = httpClient.execute(httpPost);
//        	int statusCode = response.getStatusLine().getStatusCode();
//        	if (statusCode != 200) {
//        		httpPost.abort();
//        		throw new RuntimeException("HttpClient,error status code :" + statusCode);
//        	}
        	HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("---------------------   post 测试返回的数据是  ---------------  "+result);
            
        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	httpPost.abort();
        }
        return result;
    }  
	/**
	 * httpClient   post请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String executePostForm(String url,List<NameValuePair> formList) {  
  
		String result = "";
		UrlEncodedFormEntity data = null;
		try {
			data = new UrlEncodedFormEntity(formList, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HttpClient httpClient = new DefaultHttpClient();
		if(url.toLowerCase().startsWith("https")) {
			httpClient = WebClientDevWrapper.wrapClient(httpClient);
		}
		//HttpPost httpPost = new HttpPost(url);
		HttpPost httpPost = new HttpPost(url); 
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
//		StringEntity postEntity = new StringEntity(data, Charset.forName("UTF-8"));
//        postEntity.setContentEncoding("UTF-8");
//        postEntity.setContentType("text/xml;charset=utf-8");
        httpPost.setEntity(data);
        try {
        	HttpResponse response = httpClient.execute(httpPost);
//        	int statusCode = response.getStatusLine().getStatusCode();
//        	if (statusCode != 200) {
//        		httpPost.abort();
//        		throw new RuntimeException("HttpClient,error status code :" + statusCode);
//        	}
        	HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("---------------------   post 测试返回的数据是  ---------------  "+result);
            
        } catch (ConnectionPoolTimeoutException e) {
        	e.printStackTrace();
        } catch (ConnectTimeoutException e) {
        	e.printStackTrace();
        } catch (SocketTimeoutException e) {
        	e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	httpPost.abort();
        }
        return result;
    }  
    
	/**
	 * 把json转换为from提交需要的List<NameValuePair>
	 * @param jsonObject
	 * @return
	 */
	public static List<NameValuePair> jsonToFormList(JSONObject jsonObject){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Entry<String, Object>> set= jsonObject.entrySet();
		for(Entry<String, Object> en : set){
			formparams.add(new BasicNameValuePair(en.getKey(),String.valueOf(en.getValue())));
		}
		return formparams;
	}
}
