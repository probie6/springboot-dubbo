package com.newbie.common.util;

import com.newbie.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class URLUtil {
	private URLUtil(){
		
	}
	/**key=xx&fc=ss*/
	public static final String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
	
	/**{a:1,b: 2}*/
	public static final String CONTENT_TYPE_JSON = "application/json";
	private static final String UTF_8="UTF-8";

	/**输出到前台的时候转码，防止脚本注入
	 * @param msg 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String escape(String msg){
		String temp = msg;
		temp = temp.replace("<", "&lt;").replace(">", "&gt;");
		return temp;
	}
	
	/**编码URL参数
	 * @param msg url参数
	 * @return 返回编码后的URL参数
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String msg) throws UnsupportedEncodingException{
		return URLEncoder.encode(msg,UTF_8);
	}

	/**解码URL参数
	 * @param msg url参数
	 * @return 返回编码后的URL参数
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String msg) throws UnsupportedEncodingException{
		return URLDecoder.decode(msg,UTF_8);
	}

	/**测试网络地址是否畅通
	 * @param urlStr
	 * @return
	 */
	public static boolean testPort(String urlStr){
		boolean b = false;
		try {
			URL url = new URL(urlStr);
			url.openConnection();
			b = true;
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**获取指定URL的返回信息
	 * @param urlStr URL地址
	 * @return 访问URL地址返回的信息
	 * @throws ConnectException
	 * @throws IOException
	 */
	public static String getInfo(String urlStr)throws IOException{
		return getInfo(urlStr, null, null, null);
	}

	public static String getInfo(InputStream inputStream,String charset){
		try(InputStream in = inputStream){
			InputStreamReader reader;
			if(charset != null) {
				reader = new InputStreamReader(in, charset);
			} else {
				reader = new InputStreamReader(in);
			}
			char[] buffer = new char[4096];
			int n = 0;
			StringBuilder stringBuilder = new StringBuilder();
			while (-1 != (n = reader.read(buffer))) {
				stringBuilder.append(buffer, 0, n);
			}
			return stringBuilder.toString();
		}catch (IOException e){
			log.error(e.getMessage());
		}
		return "";
	}
	/**获取指定URL的返回信息
	 * @param urlStr URL地址
	 * @param connectTimeout 链接超时
	 * @param readTimeout 读取超时
	 * @return 访问URL地址返回的信息
	 * @throws ConnectException
	 * @throws IOException
	 */
	public static String getInfo(String urlStr, Integer connectTimeout, Integer readTimeout, String charset) throws IOException{
		SSLUtil.trustAll();
		URL url = new URL(urlStr);
		try {
			URLConnection urlConnection = url.openConnection();
			if(connectTimeout != null) {
				urlConnection.setConnectTimeout(connectTimeout);
			}
			if(readTimeout != null) {
				urlConnection.setReadTimeout(readTimeout);
			}
			urlConnection.connect();
			return getInfo(urlConnection.getInputStream(),charset);

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	/**
	 * @param urlStr  服务器URL
	 * @param filePath  下周后的本地路径
	 */
	public static void getFile(String urlStr,String filePath) throws IOException {
		File fto = new File(filePath);

		if(fto.exists()){
			Files.delete(Paths.get(urlStr));
		}
		boolean f = fto.createNewFile();
		if(!f){
			log.error("创建临时文件失败！");
			return;
		}
		URL url = new URL(urlStr);
		URLConnection con = url.openConnection();
		try(OutputStream fileout = new BufferedOutputStream(new FileOutputStream(fto)); InputStream in = con.getInputStream()){
			int length = 1024;
			byte[] buffer = new byte[length];
			int flag = 0;
			while((flag=in.read(buffer, 0, length))!=-1){
				fileout.write(buffer, 0, flag);
			}
			log.info("文件接收完成！");
		} 
	}

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param params  请求参数，MAP<Sting,String>形式。
     * @return 所代表远程资源的响应结果
     */
	public static String sendPost(String url,Map<String,String> params)throws IOException{
		SSLUtil.trustAll();
        Set<Entry<String,String>> set = params.entrySet();
        StringBuilder sb = new StringBuilder("");
        // 发送请求参数
        for(Entry<String,String>entry:set){
        	sb.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        String param = sb.toString();
        param = param.substring(0,param.length()-1);
		return sendPost(url,param, CONTENT_TYPE_FORM_URL_ENCODED);
	}

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 参数类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String contentType)throws IOException {
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
		URL realUrl = new URL(url);

		String ip = System.getProperty("http.proxyHost");// 设置http访问要使用的代理服务器的地址
		String port = System.getProperty("http.proxyPort");// 设置http访问要使用的代理服务器的端口
		if(ip!=null&&ip.length()>0&&port!=null){// 创建代理服务器
			InetSocketAddress addr = new InetSocketAddress(ip,Integer.parseInt(port));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			conn = (HttpURLConnection) realUrl.openConnection(proxy);
		}else{
			conn = (HttpURLConnection) realUrl.openConnection();
		}

		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content-type", contentType == null ? CONTENT_TYPE_FORM_URL_ENCODED : contentType);
		conn.setRequestProperty("charset", UTF_8);

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		//设置超时
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(10000);
		// 获取URLConnection对象对应的输出流
		if(param != null) {
			conn.getOutputStream().write(param.getBytes(StandardCharsets.UTF_8));
		}
		InputStream inputStream;
		if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
			inputStream = conn.getInputStream();
		}else{
			inputStream = conn.getErrorStream();
		}
		// 定义BufferedReader输入流来读取URL的响应
		try(BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		}
		return result.toString();
    }
    /**
     * 发送JSON请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String sendPostJSON(String url, String json)throws IOException {
    	return sendPost(url, json, CONTENT_TYPE_JSON);
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param json  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String json)throws IOException {
    	return sendPost(url, json, CONTENT_TYPE_FORM_URL_ENCODED);
    }

	/**
	 * 获取查询参数
	 * @param url
	 * @param key
	 * @return
	 */
	public static String getQueryParam(String url, String key) {
		return getQueryParamMap(url).get(key);
	}
	/**
	 * 获取查询参数map
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static Map<String, String> getQueryParamMap(String url) {
		Map<String, String> queryStringMap = new HashMap<>();
    	if(url.contains("?")) {
			String queryString = url.substring(url.indexOf('?') + 1);
			String[] queryStringSplit = queryString.split("&");
			String[] queryStringParam;
			for (String qs : queryStringSplit) {
				queryStringParam = qs.split("=");
				try {
					String value = decode(queryStringParam[1]);
					queryStringMap.put(queryStringParam[0], value);
				} catch (UnsupportedEncodingException e) {
					//过滤掉
				}
			}
		}
		return queryStringMap;
	}

	/**
	 * 设置查询参数
	 * @param url
	 * @return
	 */
	public static String setQueryParam(String url, String key, String value) {
		String host;
		if(url.contains("?")){
			host = url.substring(0, url.indexOf('?'));
		}else{
			host = url;
		}
		Map<String, String> queryParamMap = getQueryParamMap(url);
		queryParamMap.put(key, value);
		List<String> paramList = new ArrayList<>();
		for (Entry<String, String> entry : queryParamMap.entrySet()) {
			try {
				String entryValue = encode(entry.getValue());
				paramList.add(entry.getKey() + "=" + entryValue);
			} catch (UnsupportedEncodingException e) {
				//过滤掉
			}
		}
		String queryParam = StringUtil.join(paramList, "&");
		return host + '?' + queryParam;
	}
	/**
	 * 填充参数占位符
	 * @param pattern URL 例：org/orgMgt.do?method=changeEnterprise&id={0}&name={1} ->org/orgMgt.do?method=changeEnterprise&id=123&name=456
	 * @param arguments 参数集合
	 * @return
	 */
	public static String format(String pattern, String ... arguments){
		if(StringUtil.isEmpty(pattern)){
			return pattern;
		}
		if(arguments == null || arguments.length == 0){
			return pattern;
		}
		for (int n = 0; n < arguments.length; n++) {
			if(StringUtil.isNotEmpty(arguments[n])){
				try {
					pattern = pattern.replace("{" + n + "}", URLUtil.encode(arguments[n]));
				} catch (UnsupportedEncodingException e) {
					throw new BusinessException("填充占位符失败：" + e.getMessage());
				}
			}
		}
		return pattern;
	}

	public static String clearParamsBefore(String urlParams){
		String lastUrl = urlParams;
		if(lastUrl.substring(0, 1).equals("&")){
			lastUrl = lastUrl.substring(1);
		}
		return lastUrl;
	}
	public static String clearParamsAfter(String lastUrl,String urlParams){
		if(StringUtil.isEmpty(urlParams) && lastUrl.indexOf('?')>-1){
			lastUrl = lastUrl.substring(0,lastUrl.indexOf('?'));
			lastUrl = lastUrl+"?m=startwe";
			urlParams = lastUrl;
		}
		return urlParams;
	}
	public static String clearParamsBody(String urlParams){
		String[] noParams = urlParams.trim().split("&");
		for (int n = 0; n < noParams.length; n++) {
			if (StringUtil.isEmpty(noParams[n]) || noParams[n].indexOf('=')<0) {
				continue;
			}
			String[] noParamKeyValue = noParams[n].split("=");
			if (noParamKeyValue == null || noParamKeyValue.length != 2) {
				throw new BusinessException("URL参数格式不正确：" + urlParams);
			}
			String value = noParamKeyValue[1];
			if (value.matches("\\{\\d+\\}")) {
				if (n == 0) {
					urlParams = urlParams.replace(noParams[n], "");
				} else {
					urlParams = urlParams.replace("&" + noParams[n], "");
				}
			}
		}
		return urlParams;
	}
	/**
	 * 清理无值（占位符）参数
	 * @param urlParams 带参数的url或参数url字符串，例：org/orgMgt.do?method=changeEnterprise&id={0}&name=123 ->org/orgMgt.do?method=changeEnterprise&name=123
	 * @return
	 */
	public static String clearParams(String urlParams) {
		if (StringUtil.isEmpty(urlParams)) {
			return urlParams;
		}
		String lastUrl = clearParamsBefore(urlParams);
		urlParams = lastUrl;
		// 删除没有传值的参数
		urlParams = clearParamsBody(urlParams);
		urlParams = clearParamsAfter(lastUrl, urlParams);
		return urlParams;
	}
}

