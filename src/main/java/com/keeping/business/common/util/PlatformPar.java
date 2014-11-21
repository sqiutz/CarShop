package com.keeping.business.common.util;

import java.io.IOException;
import java.util.Properties;

public class PlatformPar {

	/**
	 * 仅支持内部静态调用，防止外部调用
	 */
	private  static Properties platformPropertyes = new Properties();	

	/**
	 * 平台编码
	 */
	public static String charset = getPropertieVal("charset");

	/**
	 * 平台编码
	 */
	public static String website = getPropertieVal("website");

	/**
	 * Session超时时间（单位：秒）
	 */
	public static int sessionTimeout = Integer.parseInt(getPropertieVal("sessionTimeout")) * 60;
	
	/**
	 * 资源服务器url地址
	 */
	public static String resourceServer = getPropertieVal("resourceServer");
	
	/**
	 * 资源服务器路径
	 */
	public static String resourceServerFolder = getPropertieVal("resourceServerFolder");
	
	/**
	 * FTP服务器上安装包路径
	 */
	public static String packFolder = getPropertieVal("packFolder");

	/**
	 * FTP服务器上图片存储路径
	 */
	public static String imgFolder = getPropertieVal("imgFolder");

	/**
	 * FTP服务器上产品相关图片路径
	 */
	public static String productImgFolder = getPropertieVal("productImgFolder");

	/**
	 * FTP服务器上产品dailybuild二维码图片根目录路径
	 */
	public static String dailyFolder = getPropertieVal("dailyFolder");

	/**
	 * FTP服务器上项目相关图片路径
	 */
	public static String projectImgFolder = getPropertieVal("projectImgFolder");

	/**
	 * FTP服务器上用户图标路径
	 */
	public static String userImgFolder = getPropertieVal("userImgFolder");

	/**
	 * FTP服务器上项目功能页面图标路径
	 */
	public static String funcPageFolder = getPropertieVal("funcPageFolder");	

	/**
	 * checkSession 目录
	 */
	public static String checkPath = getPropertieVal("checkPath");

	/**
	 * 重置密码有效时间
	 */
	public static long resetpwdTimeout = Integer.parseInt(getPropertieVal("resetpwdTimeout")) * 3600L;
	
	/**
	 * 获取ssh https的端口号
	 */
	public static int sshPort = Integer.parseInt(getPropertieVal("sshPort"));
	
	/**
	 * 获取当前ip地址
	 */
	public static String serverIp = getPropertieVal("serverIp");
	
	
	/**
	 * 取配置文件的某个配置信息
	 * @param properties	配置定义
	 * @return
	 * @throws Exception 
	 */
	public static String getPropertieVal(String properties) {
		if (platformPropertyes==null || platformPropertyes.size()==0)
			readCommonProperties();
		return platformPropertyes.getProperty(properties);
	}
	
	/**
	 * 读取配置文件到内存
	 */
	private static void readCommonProperties() {
	    try {
	    	platformPropertyes.load(PlatformPar.class.getResourceAsStream("/platform.properties"));
		} catch (IOException e) {
			System.out.println(e.toString());
		} 
	}
	
	//防止出现多个实例，仅支持静态调用
	private PlatformPar(){
		
	}

}
