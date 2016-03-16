package org.agile4j.plugin.gun.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.IGunFile;
import org.agile4j.plugin.gun.service.impl.GenPluginEntityServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 导出java文件
 * 
 * @author hanyx
 * @since
 */
public class GunJava implements IGunFile {

	private static final Logger LOGGER = LoggerFactory.getLogger(GunJava.class);

	private GenPluginEntityServiceImpl genPluginEntityServiceImpl = new GenPluginEntityServiceImpl();

	private static Map<String, List<String>> errorMap = new HashMap<>();
	
	@Override
	public void initFile(GenPluginEntity gpe) {
		genPluginEntityServiceImpl.updateOrCreateGenPluginEntity(gpe) ;
	}

	@Override
	public void initFile(List<GenPluginEntity> gpeList) {
		genPluginEntityServiceImpl.batcheUpdateOrCreateGenPluginEntity(gpeList) ;
	}

	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =1 的字段)
	 */
	@Override
	public boolean createTarFile() {
		return createTarFile(GunHelper.getCreateTarJavaFileUrl());
	}

	/**
	 * 对自定义路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =1 的字段)
	 */
	@Override
	public boolean createTarFile(String createTarFileUrl) {
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByType(GunConstant.JAVA_TYPE);
		return createTarFile(genPluginEntityList, createTarFileUrl);
	}

	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 */
	@Override
	public boolean createTarFile(GenPluginEntity genPluginEntity) {
		return createTarFile(genPluginEntity, GunHelper.getCreateTarJavaFileUrl());
	}

	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 */
	@Override
	public boolean createTarFile(GenPluginEntity genPluginEntity, String createTarFileUrl) {
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByCondition(genPluginEntity);
		return createTarFile(genPluginEntityList, createTarFileUrl);

	}

	public boolean createTarFile(List<GenPluginEntity> genPluginEntityList, File...files) {
		if (files.length == 0) {
			return false ;
		} else {
			for (File urlFile : files) {
				Collection<File> collection = new ArrayList<>();
	
				if (urlFile.exists() && urlFile.isFile()) {
					collection.add(urlFile);
				} else if (urlFile.exists() && urlFile.isDirectory()) {
					collection = FileUtils.listFiles(urlFile, new String[] { "java" }, true);
				}
				if (CollectionUtils.isNotEmpty(collection)) {
					for (File file : collection) {
						try {
							String srcFileStr = FileUtils.readFileToString(file, "UTF-8");
							if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
								List<GenPluginEntity> newList = groupByPluginEntity(genPluginEntityList);
								modifyTbEntity(newList);
								List<String> srcList = new ArrayList<>();
								List<String> tarList = new ArrayList<>();
								for (GenPluginEntity vo : newList) {
									srcList.add(vo.getSrcField());
									tarList.add(vo.getTarField());
								}
								String tarFileStr = StringUtils.replaceEach(srcFileStr, srcList.toArray(new String[0]), tarList.toArray(new String[0]));
								
								String tarFilePath ;
								if (GunConstant.FILE_NEW_PATH_TYPE.equals(GunHelper.getFileReplaceType())) {
									tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
								} else {
									tarFilePath = file.getAbsolutePath(); // 目标文件路径
								}
								FileUtils.writeStringToFile(new File(tarFilePath), tarFileStr, "UTF-8", false); // 生成目标文件
								printErrorMap(); // 打印异常日志
							}
						} catch (IOException e) {
							LOGGER.error("update java failur", e);
							throw new RuntimeException(e);
						}
					}
				}
			}
			return true;
		}
	}
	
	public boolean createTarFile(List<GenPluginEntity> genPluginEntityList, String... fileUrls) {
		File[] files = new File[fileUrls.length] ;
		for (int i = 0; i < fileUrls.length; i++) {
			files[i] = new File(fileUrls[i]) ;
		}
		createTarFile(genPluginEntityList, files) ;
		return true ;
	}
	
	/**
	 * 筛选出重复命名的替换字段
	 * @param genPluginEntityList
	 * @return
	 */
	private List<GenPluginEntity> groupByPluginEntity(List<GenPluginEntity> genPluginEntityList) {
		Map<String,List<String>> map = new HashMap<>() ;	 // 01.要组装的Map
		if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
			for (GenPluginEntity vo : genPluginEntityList) {
				String groupId = vo.getSrcField() ;
				List<String> subList = map.get(groupId) ;
				if (subList == null) {
					subList = new ArrayList<>() ;
					map.put(groupId, subList) ;
				}
				subList.add(vo.getTarField()) ;
			}
		}
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {	// 记录重复赋值的替换字段
			if (entry.getValue().size() != 1) {
				errorMap.put(entry.getKey(), entry.getValue()) ;
			}
		}
		List<GenPluginEntity> newList = new ArrayList<>() ;
		for (GenPluginEntity vo : genPluginEntityList) { // 删除重复字段对象
			if (MapUtils.isNotEmpty(errorMap)) {
				if (!errorMap.containsKey(vo.getSrcField())) {
					newList.add(vo) ;
				}
			}
		}
		return newList ;
	}
	
	/**
	 * 当替换的是表类型时添加set，get字段
	 * @param genPluginEntityList
	 */
	private void modifyTbEntity(List<GenPluginEntity> genPluginEntityList) {
		if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
			List<GenPluginEntity> subList = new ArrayList<>() ;
			for (GenPluginEntity vo : genPluginEntityList) {
				if (vo.getGunType().equals(ModelObjectConstant.TABLES_FLAG)) {
					String srcSetField = "set" + upperFirstStr(vo.getSrcField()) ;
					String srcGetField = "get" + upperFirstStr(vo.getSrcField()) ;
					String tarSetField = "set" + upperFirstStr(vo.getTarField()) ;
					String tarGetField = "get" + upperFirstStr(vo.getTarField()) ;
					GenPluginEntity newVo = new GenPluginEntity() ;
					newVo.setGunType(vo.getGunType());
					newVo.setSrcField(srcSetField);
					newVo.setTarField(tarSetField);
					subList.add(newVo) ;
					newVo = new GenPluginEntity() ;
					newVo.setGunType(vo.getGunType());
					newVo.setSrcField(srcGetField);
					newVo.setTarField(tarGetField);
					subList.add(newVo) ;
				}
			}
			genPluginEntityList.addAll(subList) ;
		}
	}
	
	private String upperFirstStr(String str) {
		return str.replaceFirst(str.substring(0,1), str.substring(0,1).toUpperCase()) ;
	}
	
	private void printErrorMap() {
		if (MapUtils.isNotEmpty(errorMap)) {
			LOGGER.error("error field: " + JSON.toJSONString(errorMap));
		}
	}
	
	public static void main(String[] args) {
		File file = new File("E:\\gun_plugin_test\\Book.java") ;
		String tarFilePath1 = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
		String tarFilePath2 = file.getAbsolutePath(); // 目标文件路径
		LOGGER.debug(tarFilePath1);
		LOGGER.debug(tarFilePath2);
	}
}
