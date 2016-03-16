package org.agile4j.plugin.gun.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.helper.ExcelHelper;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.impl.GenPluginEntityServiceImpl;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sunline.ltts.frw.model.ModelObject;
import cn.sunline.ltts.frw.model.db.Field;
import cn.sunline.ltts.frw.model.db.Table;
import cn.sunline.ltts.frw.model.dm.Element;
import cn.sunline.ltts.frw.model.dm.Schema;
import cn.sunline.ltts.frw.model.dm.internal.DefaultComplexType;
import cn.sunline.ltts.frw.model.sql.SqlGroup;
import cn.sunline.ltts.frw.model.sql.Statement;

/**
 * 基于 baes版本 ModelObject进行替换
 * @author hanyx
 * @since
 */
public class GunXml {
	private static final Logger LOGGER = LoggerFactory.getLogger(GunXml.class);

	private Map<String, ModelObject> modelObjectMap ;
	private GenPluginEntityServiceImpl genPluginEntityServiceImpl = new GenPluginEntityServiceImpl();

	public GunXml() {
		modelObjectMap = new HashMap<>() ;
	}
	
	/**
	 * 根据要替换类型，对默认路径下解析出来的文件数据保存到表中
	 * 
	 * @return
	 */
	public void initFile(String type) {
		initFile(type, GunHelper.getInitLoadUrl());
	}
	
	/**
	 * 根据要替换类型，指定路径下解析出来的文件数据保存到表中
	 * 
	 * @return
	 */
	public void initFile(String type, String initLoadUrl) {
		File file = new File(initLoadUrl) ;
		modelObjectMap = ParserUtil.deploy(type, file);
		genPluginEntityServiceImpl.batcheInsertGenPluginEntity(getGenPluginList(type));
	}
	
	/**
	 * 保存单个变更对象
	 * 
	 * @return
	 */
	public void initFile(GenPluginEntity gpe) {
		if (StringUtils.isNotEmpty(gpe.getGunType())) {
			genPluginEntityServiceImpl.updateOrCreateGenPluginEntity(gpe);
		}
	}

	/**
	 * 保存多个变更对象
	 * @return
	 */
	public void initFile(List<GenPluginEntity> gpeList) {
		List<GenPluginEntity> newGpeList = new ArrayList<GenPluginEntity> () ;
		for (GenPluginEntity vo : gpeList) {
			if (StringUtils.isNotEmpty(vo.getGunType())) {
				newGpeList.add(vo) ;
			}
		}
		genPluginEntityServiceImpl.batcheUpdateOrCreateGenPluginEntity(newGpeList);
	}
	
	private List<GenPluginEntity> getGenPluginList(String type) {
		List<GenPluginEntity> genPluginEntityList = new ArrayList<>();
		if (MapUtils.isNotEmpty(modelObjectMap)) {
			for (Map.Entry<String, ModelObject> entry : modelObjectMap.entrySet()) {
				switch (type) {
				case ModelObjectConstant.TABLES_FLAG:
					initTableFile(genPluginEntityList, type, entry);
					break;
				case ModelObjectConstant.NAMEDSQL_FLAG:
//					gunNamedsql(genPluginEntityList, type, entry);
					break;
				case ModelObjectConstant.TYPE_FLAG:
					gunType(genPluginEntityList, type, entry);
					break;
				default:
					break;
				}
			}
		}
		return genPluginEntityList;
	}
	
	private void gunType(List<GenPluginEntity> genPluginEntityList, String type, Map.Entry<String, ModelObject> entry) {
		Schema schema = (Schema) entry.getValue();
		if (CollectionUtils.isNotEmpty(schema.getTypes())) {
			for (Object elementType : schema.getTypes()) {
				DefaultComplexType complexType = (DefaultComplexType) elementType ;
				if (CollectionUtils.isNotEmpty(complexType.getElements())) {
					for (Element element : complexType.getElements()) {
						GenPluginEntity vo = new GenPluginEntity();
						vo.setGunType(ModelObjectConstant.TABLES_FLAG);
						vo.setSrcParent(complexType.getId());
						vo.setSrcField(element.getId());
						vo.setTarParent(complexType.getId());
						vo.setTarField(element.getId());
						genPluginEntityList.add(vo);
					}
				}
			}
		}
	}
	private void initTableFile(List<GenPluginEntity> genPluginEntityList, String type, Map.Entry<String, ModelObject> entry) {
		Schema schema = (Schema) entry.getValue();
		if (CollectionUtils.isNotEmpty(schema.getTypes())) {
			for (Object elementType : schema.getTypes()) {
				Table table = (Table) elementType ;
				if (CollectionUtils.isNotEmpty(table.getFields())) {
					for (Field field : table.getFields()) {
						GenPluginEntity vo = new GenPluginEntity();
						vo.setGunType(ModelObjectConstant.TABLES_FLAG);
						vo.setSrcParent(table.getId());
						vo.setSrcField(field.getId());
						vo.setTarParent(table.getId());
						vo.setTarField(field.getId());
						genPluginEntityList.add(vo);
					}
				}
			}
		}
	}
	private void initNamedsqlFile(List<GenPluginEntity> genPluginEntityList, String type, Map.Entry<String, ModelObject> entry) {
		SqlGroup sqlGroup = (SqlGroup) entry.getValue();
		if (CollectionUtils.isNotEmpty(sqlGroup.getSqls())) {
			for (Statement statement : sqlGroup.getSqls()) {
			}
		}
	}
	
	
	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的 的字段)
	 * 
	 * @return
	 */
	public boolean createTarFile(String type) {
		return createTarFile(type, GunHelper.getCreateTarXmlFileUrl());
	}
	
	/**
	 * 自定义路径下文件 生成新的目标文件 (替换规则是所有表中存在的的字段)
	 * 
	 * @param createTarFileUrl
	 * @return
	 */
	public boolean createTarFile(String type, String createTarFileUrl) {
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByType(type);
		return createTarFile(type, genPluginEntityList, createTarFileUrl);
	}
	
	public boolean createTarFile(String type, String createTarFileUrl, String excelUrl) {
		List<GenPluginEntity> genPluginEntityList = ExcelHelper.loadData(excelUrl) ;
		return createTarFile(type, genPluginEntityList, createTarFileUrl);
	}
	
	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 * @return
	 */
	public boolean createTarFile(String type, GenPluginEntity genPluginEntity) {
		return createTarFile(type, genPluginEntity, GunHelper.getCreateTarXmlFileUrl());
	}
	
	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 * @return
	 */
	public boolean createTarFile(String type, GenPluginEntity genPluginEntity, String createTarFileUrl) {
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByCondition(genPluginEntity);
		return createTarFile(type, genPluginEntityList, createTarFileUrl);
	}
	
	private boolean createTarFile(String type, List<GenPluginEntity> genPluginEntityList, String createTarFileUrl) {
		try {
			if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
				Map<String, String> tarFieldMap = getUniqTarField(genPluginEntityList) ;
				Map<String, ModelObject> modelObjectMap = ParserUtil.deploy(type, new File(createTarFileUrl));
				if (MapUtils.isNotEmpty(modelObjectMap)) {
					for (Map.Entry<String, ModelObject> modelObjectEntry : modelObjectMap.entrySet()) {
						switch (type) {
						case ModelObjectConstant.TABLES_FLAG:
							gunTalbeTarFile(type, modelObjectEntry, genPluginEntityList, tarFieldMap);
							break;
//						case ModelObjectConstant.NAMEDSQL_FLAG:
//							break;
						case ModelObjectConstant.TYPE_FLAG:
							gunTypeTarFile(type, modelObjectEntry, genPluginEntityList, tarFieldMap);
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("create Tar File failure", e);
			throw new RuntimeException(e) ;
		}
		return true;
	}
	
	private void gunTalbeTarFile(String type, Map.Entry<String, ModelObject> modelObjectEntry,List<GenPluginEntity> genPluginEntityList , Map<String, String> tarFieldMap) throws Exception {
		Schema schema = (Schema) modelObjectEntry.getValue() ;
		if (CollectionUtils.isNotEmpty(schema.getTypes())) {
			for (Object elementType : schema.getTypes()) {
				for (GenPluginEntity gpe : genPluginEntityList) {
					Table table = (Table) elementType ;
					if (StringUtils.equals(table.getId(), gpe.getSrcParent())) {
						String orldTableId = table.getId() ;
						if (CollectionUtils.isNotEmpty(table.getFields())) {
							table.setId(gpe.getTarParent());
							for (Field field : table.getFields()) {
								String key = orldTableId +"_"+field.getId() ;
								if (tarFieldMap.containsKey(key)) {
									field.setId(tarFieldMap.get(key));
								}
							}
						}
					}
				}
			}
		}
		File file = new File (modelObjectEntry.getKey());
		String tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
		ParserUtil.replaceXmlField(tarFilePath, schema);
	}
	
	private void gunTypeTarFile(String type, Map.Entry<String, ModelObject> modelObjectEntry,List<GenPluginEntity> genPluginEntityList , Map<String, String> tarFieldMap) throws Exception {
		Schema schema = (Schema) modelObjectEntry.getValue() ;
		if (CollectionUtils.isNotEmpty(schema.getTypes())) {
			for (Object elementType : schema.getTypes()) {
				for (GenPluginEntity gpe : genPluginEntityList) {
					Table table = (Table) elementType ;
					if (StringUtils.equals(table.getId(), gpe.getSrcParent())) {
						String orldTableId = table.getId() ;
						if (CollectionUtils.isNotEmpty(table.getFields())) {
							table.setId(gpe.getTarParent());
							for (Field field : table.getFields()) {
								String key = orldTableId +"_"+field.getId() ;
								if (tarFieldMap.containsKey(key)) {
									field.setId(tarFieldMap.get(key));
								}
							}
						}
					}
				}
			}
		}
		
		File file = new File (modelObjectEntry.getKey());
		String tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
		ParserUtil.replaceXmlField(tarFilePath, schema);
	}
	
	private Map<String, String> getUniqTarField(List<GenPluginEntity> genPluginEntityList) {
		Map<String, String> tarFieldMap = new HashMap<>() ;
		for (GenPluginEntity vo : genPluginEntityList) {
			String key = vo.getSrcParent() + "_" + vo.getSrcField() ;
			tarFieldMap.put(key, vo.getTarField()) ;
		}
		return tarFieldMap ;
	}
}
