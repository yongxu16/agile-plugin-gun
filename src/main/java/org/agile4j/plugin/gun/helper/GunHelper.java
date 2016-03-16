package org.agile4j.plugin.gun.helper;

import java.util.Properties;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agle4j.framework.utils.PropsUtil;


/**
 * 属性文件助手类
 * @author hanyx
 *
 */
public final class GunHelper {
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(GunConstant.CONFIG_FILE) ;
	
	
	public static String getTarFileFormat() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.TAR_FILE_FORMAT) ;
	}
	
	public static String getInitLoadUrl() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.INIT_LOAD_URL) ;
	}
	
	public static String getCreateTarXmlFileUrl() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.CREATE_TAR_XML_FILE_URL) ;
	}
	
	public static String getCreateTarJavaFileUrl() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.CREATE_TAR_JAVA_FILE_URL) ;
	}
	
	public static String getExcelPath() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.EXCEL_FILE_URL) ;
	}
	
	public static String getFileReplaceType() {
		return PropsUtil.getString(CONFIG_PROPS, GunConstant.FILE_REPLACE_TYPE) ;
	}
	
}
