package org.agile4j.plugin.gun.constant;

/**
 * 提供相关配置项常量
 * 
 * @author hanyx
 */
public interface GunConstant {
	String CONFIG_FILE = "config.properties";
	String JDBC_DRIVER = "jdbc.driver";
	String JDBC_URl = "jdbc.url";
	String JDBC_USERNAME = "jdbc.username";
	String JDBC_PASSWORD = "jdbc.password";
	
	String TABLE_TYPE = "0";
	String JAVA_TYPE = "3";
	String NAMESQL_TYPE = "1";
	String TYPESQL_TYPE = "2";
	String DEFUALT_TAR_URL_CODE = "tar_" ;
	
	String TAR_FILE_FORMAT = "tar.file.format" ;
	String INIT_LOAD_URL = "init.load.url" ;
	String CREATE_TAR_XML_FILE_URL = "create.tar.xml.file.url" ;
	String CREATE_TAR_JAVA_FILE_URL = "create.tar.java.file.url" ;
	String EXCEL_FILE_URL = "excel.file.url" ;
	String FILE_REPLACE_TYPE = "file.replace.type" ;
	String FILE_NEW_PATH_TYPE = "0" ;
	String FILE_OLD_PATH_TYPE = "1" ;
}
