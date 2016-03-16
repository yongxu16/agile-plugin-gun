package org.agile4j.plugin.gun.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.xml.namesql.Sqls;
import org.agile4j.plugin.gun.xml.servicetype.ParseServiceTypeUtil;
import org.agile4j.plugin.gun.xml.servicetype.ServiceType;
import org.agile4j.plugin.gun.xml.tbsql.ParseTbsqlUtil;
import org.agile4j.plugin.gun.xml.tbsql.Schema;
import org.agile4j.plugin.gun.xml.type.ParseTypeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sunline.ltts.frw.model.ModelObject;
import cn.sunline.ltts.frw.model.util.FileModelFile;
import cn.sunline.ltts.frw.model.util.ModelFactoryUtil;
import cn.sunline.ltts.frw.model.util.ModelFile;


/**
 * 文件解析工具
 * 
 * @author hanyx
 * @since
 */
public final class ParserUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParserUtil.class);

	public static Map<String, Schema> deplogyTable(File... files) {
		Map<String, Schema> map = new HashMap<>();
		if (files != null && files.length > 0) {
			try {
				for (File innerFile : files) {
					if (innerFile.exists() && innerFile.isFile()) {// 文件对象存
						String xmlFilePath = innerFile.getAbsolutePath();
						Schema sql = ParseTbsqlUtil.getTbsConfig(xmlFilePath);
						map.put(xmlFilePath, sql);
					} else if (innerFile.exists() && innerFile.isDirectory()) {
						Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "tables.xml" }, true);
						if (CollectionUtils.isNotEmpty(filesCollection)) {
							for (File file : filesCollection) {
								String xmlFilePath = file.getAbsolutePath();
								Schema sql = ParseTbsqlUtil.getTbsConfig(xmlFilePath);
								map.put(xmlFilePath, sql);
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("deplogy Table failur", e);
				throw new RuntimeException(e);
			}
		}
		return map;
	}
	
	public static Map<String, ServiceType> deplogyServiceType(File... files) {
		Map<String, ServiceType> map = new HashMap<>();
		if (files != null && files.length > 0) {
			try {
				for (File innerFile : files) {
					if (innerFile.exists() && innerFile.isFile()) {// 文件对象存
						String xmlFilePath = innerFile.getAbsolutePath();
						ServiceType serviceType = ParseServiceTypeUtil.getServcieTypeConfig(xmlFilePath);
						map.put(xmlFilePath, serviceType);
					} else if (innerFile.exists() && innerFile.isDirectory()) {
						Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "serviceType.xml" }, true);
						if (CollectionUtils.isNotEmpty(filesCollection)) {
							for (File file : filesCollection) {
								String xmlFilePath = file.getAbsolutePath();
								ServiceType serviceType = ParseServiceTypeUtil.getServcieTypeConfig(xmlFilePath);
								map.put(xmlFilePath, serviceType);
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("deplogy service type failur", e);
				throw new RuntimeException(e);
			}
		}
		return map;
	}
	
	public static Map<String, ModelObject> deploy(String type , File...files) {
		Map<String, ModelObject> map = new HashMap<>();
		if (files != null && files.length > 0) {
			for (File innerFile : files) {
				if (innerFile.exists() && innerFile.isFile()) {// 文件对象存
					String xmlFilePath = innerFile.getAbsolutePath();
					ModelObject modelObject = ModelFactoryUtil.parse((ModelFile) new FileModelFile(innerFile));
					map.put(xmlFilePath, modelObject); 
				} else if (innerFile.exists() && innerFile.isDirectory()) {
					String fileType  ;
					switch (type) {
					case ModelObjectConstant.TABLES_FLAG:
						fileType = ModelObjectConstant.TABLES_CODE ;
						break;
					case ModelObjectConstant.NAMEDSQL_FLAG:
						fileType = ModelObjectConstant.NAMEDSQL_CODE ;
						break;
					case ModelObjectConstant.TYPE_FLAG:
						fileType = ModelObjectConstant.TYPE_CODE ;
						break;
					case ModelObjectConstant.SERVICETYPE_FLAG:
						fileType = ModelObjectConstant.SERVICETYPE_CODE ;
						break;
					default:
						return null;
					}
					Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { fileType }, true);
					if (filesCollection.size() > 0 && filesCollection != null) {
						for (File file : filesCollection) {
							String xmlFilePath = file.getAbsolutePath();
							ModelObject modelObject = ModelFactoryUtil.parse((ModelFile) new FileModelFile(file));
							map.put(xmlFilePath, modelObject);
						}
					}
				}
			}
		}
		return map ;
	}
	
	public static Map<String, Sqls> deployNsql(File... files) {
		Map<String, Sqls> map = new HashMap<>();
		if (files != null && files.length > 0) {
			try {
				for (File innerFile : files) {
					if (innerFile.exists() && innerFile.isFile()) {// 文件对象存
						String xmlFilePath = innerFile.getAbsolutePath();
						Sqls sql = org.agile4j.plugin.gun.xml.namesql.ParseNamesqlUtil.getSqlsConfig(xmlFilePath);
						map.put(xmlFilePath, sql);
					} else if (innerFile.exists() && innerFile.isDirectory()) {
						Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "nsql.xml" }, true);
						if (CollectionUtils.isNotEmpty(filesCollection)) {
							for (File file : filesCollection) {
								String xmlFilePath = file.getAbsolutePath();
								Sqls sql = org.agile4j.plugin.gun.xml.namesql.ParseNamesqlUtil.getSqlsConfig(xmlFilePath);
								map.put(xmlFilePath, sql);
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("deplogy Nsql failur", e);
				throw new RuntimeException(e);
			}
		}
		return map;
	}
	
	public static Map<String, org.agile4j.plugin.gun.xml.type.Schema> deplogyType(File... files) {
		Map<String, org.agile4j.plugin.gun.xml.type.Schema> map = new HashMap<>();
		if (files != null && files.length > 0) {
			try {
				for (File innerFile : files) {
					if (innerFile.exists() && innerFile.isFile()) {// 文件对象存
						String xmlFilePath = innerFile.getAbsolutePath();
						org.agile4j.plugin.gun.xml.type.Schema sql = ParseTypeUtil.getTypesConfig(xmlFilePath);
						map.put(xmlFilePath, sql);
					} else if (innerFile.exists() && innerFile.isDirectory()) {
						Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "c_schema.xml" }, true);
						if (CollectionUtils.isNotEmpty(filesCollection)) {
							for (File file : filesCollection) {
								String xmlFilePath = file.getAbsolutePath();
								org.agile4j.plugin.gun.xml.type.Schema sql = ParseTypeUtil.getTypesConfig(xmlFilePath);
								map.put(xmlFilePath, sql);
							}
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("deplogy Type failur", e);
				throw new RuntimeException(e);
			}
		}
		return map;
	}

	public static <T> void replaceXmlField(String path, T t) {
		XMLWriter writer = null;
		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(path), outputFormat);
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(t, writer);
		} catch (Exception e) {
			LOGGER.error("replace xml field failure", e);
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
