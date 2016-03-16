package org.agile4j.plugin.gun.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.helper.ExcelHelper;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.IGunFile;
import org.agile4j.plugin.gun.service.impl.GenPluginEntityServiceImpl;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.agile4j.plugin.gun.xml.tbsql.Field;
import org.agile4j.plugin.gun.xml.tbsql.Index;
import org.agile4j.plugin.gun.xml.tbsql.Schema;
import org.agile4j.plugin.gun.xml.tbsql.Table;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表文件替换
 * 
 * @author hanyx
 * @since
 */
public class GunTable implements IGunFile {

	private static final Logger LOGGER = LoggerFactory.getLogger(GunTable.class);

	private Map<String, Schema> tbMap;
	private GenPluginEntityServiceImpl genPluginEntityServiceImpl = new GenPluginEntityServiceImpl();

	public GunTable() {
		tbMap = new HashMap<>();
	}

	/**
	 * 默认路径下解析出来的文件数据保存到表中
	 * 
	 * @return
	 */
	public void initFile() {
		initFile(GunHelper.getInitLoadUrl());
	}

	/**
	 * 指定路径下解析出来的文件数据保存到表中
	 * 
	 * @return
	 */
	public void initFile(String initLoadUrl) {
		tbMap = ParserUtil.deplogyTable(new File(initLoadUrl));
		List<GenPluginEntity> genPluginEntityList = getGenPluginList() ;
//		genPluginEntityServiceImpl.batcheInsertGenPluginEntity(genPluginEntityList);
		saveExcel(genPluginEntityList);
	}
	
	public void initFile(String initLoadUrl, String excelUrl) {
		tbMap = ParserUtil.deplogyTable(new File(initLoadUrl));
		List<GenPluginEntity> genPluginEntityList = getGenPluginList() ;
//		genPluginEntityServiceImpl.batcheInsertGenPluginEntity(genPluginEntityList);
		saveExcel(genPluginEntityList,excelUrl);
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
	 * 
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

	private List<GenPluginEntity> getGenPluginList() {
		List<GenPluginEntity> genPluginEntityList = new ArrayList<>();
		if (MapUtils.isNotEmpty(tbMap)) {
			for (Map.Entry<String, Schema> entry : tbMap.entrySet()) {
				Schema schema = entry.getValue();
				if (CollectionUtils.isNotEmpty(schema.getTable())) {
					for (Table table : schema.getTable()) {
						if (CollectionUtils.isNotEmpty(table.getFields().getField())) {
							for (Field field : table.getFields().getField()) {
								GenPluginEntity vo = new GenPluginEntity();
								vo.setGunType(GunConstant.TABLE_TYPE);
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
		}
		return genPluginEntityList;
	}
	
	private void saveExcel(List<GenPluginEntity> list) {
		ExcelHelper.saveData(list);
	}

	private void saveExcel(List<GenPluginEntity> list, String excelUrl) {
		ExcelHelper.saveData(list,excelUrl);
	}
	
	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 * 
	 * @return
	 */
	public boolean createTarFile() {
		return createTarFile(GunHelper.getCreateTarXmlFileUrl());
	}

	/**
	 * 自定义路径下文件 生成新的目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 * 
	 * @param createTarFileUrl
	 * @return
	 */
	public boolean createTarFile(String createTarFileUrl) {
//		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByType(ConfigConstant.TABLE_TYPE);
		List<GenPluginEntity> genPluginEntityList = ExcelHelper.loadData() ;
		return createTarFile(genPluginEntityList, createTarFileUrl);
	}

	public boolean createTarFile(String createTarFileUrl, String excelUrl) {
//		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByType(ConfigConstant.TABLE_TYPE);
		List<GenPluginEntity> genPluginEntityList = ExcelHelper.loadData(excelUrl) ;
		return createTarFile(genPluginEntityList, createTarFileUrl);
	}

	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 * @return
	 */
	public boolean createTarFile(GenPluginEntity genPluginEntity) {
		return createTarFile(genPluginEntity, GunHelper.getCreateTarXmlFileUrl());
	}
	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 * 
	 * @param genPluginEntity
	 * @return
	 */
	public boolean createTarFile(GenPluginEntity genPluginEntity, String createTarFileUrl) {
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByCondition(genPluginEntity);
		return createTarFile(genPluginEntityList, createTarFileUrl);
	}

	private boolean createTarFile(List<GenPluginEntity> genPluginEntityList, String createTarFileUrl) {
		try {
			if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
				Map<String, String> tarFieldMap = getUniqTarField(genPluginEntityList) ;
				Map<String, Schema> schemaMap = ParserUtil.deplogyTable(new File(createTarFileUrl));
				if (MapUtils.isNotEmpty(schemaMap)) {
					for (Map.Entry<String, Schema> schemaEntry : schemaMap.entrySet()) {
						Schema schema = schemaEntry.getValue();
						if (CollectionUtils.isNotEmpty(schema.getTable())) { 
							for (Table table : schema.getTable()) {// table
								for (GenPluginEntity gpe : genPluginEntityList) {
									if (StringUtils.equals(table.getId(), gpe.getSrcParent())) {
										String orldTableId = table.getId() ;
										table.setId(gpe.getTarParent());
										if (table.getFields() != null) {	
											if (CollectionUtils.isNotEmpty(table.getFields().getField())) {
												for (Field field : table.getFields().getField()) {
													String key = orldTableId +"_"+field.getId() ;
													if (tarFieldMap.containsKey(key)) {
														field.setId(tarFieldMap.get(key));
													}
												}
											}
										}
										if (table.getIndexes() != null) {	// indexes
											if (CollectionUtils.isNotEmpty(table.getIndexes().getIndex())) {
												for (Index index : table.getIndexes().getIndex()) {
													String str = StringUtils.deleteWhitespace(index.getFields()) ;
													String[] fields =  StringUtils.split(str, ",") ;
													List<String> indexList = new ArrayList<>() ;
													for (String field : fields) {
														String key = orldTableId +"_"+field ;
														if (tarFieldMap.containsKey(key)) {
															indexList.add(tarFieldMap.get(key)) ;
														}
													}
													String newFields = StringUtils.join(indexList, ",");
													index.setFields(newFields);
												}
											}
										}
										if (table.getOdbindexes() != null) {
											if (CollectionUtils.isNotEmpty(table.getOdbindexes().getIndex())) {
												for (Index index : table.getOdbindexes().getIndex()) {
													String str = StringUtils.strip(index.getFields());
													String[] fields =  StringUtils.split(str, " ") ;
													List<String> indexList = new ArrayList<>() ;
													for (String field : fields) {
														String key = orldTableId +"_"+field ;
														if (tarFieldMap.containsKey(key)) {
															indexList.add(tarFieldMap.get(key)) ;
														}
													}
													String newFields = StringUtils.join(indexList, " ");
													index.setFields(newFields);
												}
											}
										}
										if (table.getDbSequence() != null) {
											// to do
										}
										
									}
								}
							}
							
						}
						
						File file = new File (schemaEntry.getKey());
						String tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
						ParserUtil.replaceXmlField(tarFilePath, schema);
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private Map<String, String> getUniqTarField(List<GenPluginEntity> genPluginEntityList) {
		Map<String, String> tarFieldMap = new HashMap<>() ;
		for (GenPluginEntity vo : genPluginEntityList) {
			String key = vo.getSrcParent() + "_" + vo.getSrcField() ;
			tarFieldMap.put(key, vo.getTarField()) ;
		}
		return tarFieldMap ;
	}

	public static void main(String[] args) {
		// test 1
		// String xmlFilePath = "E:\\gun_plugin_test\\GlAudit.tables.xml";
		// GunTable gunTable = new GunTable(xmlFilePath);
		// gunTable.initTableXml();

		// test 2
		// GunTable gunTable = new GunTable();
		// GenPluginEntity vo = new GenPluginEntity(ConfigConstant.XML_TYPE,
		// "kglb_sjchop");
		// gunTable.createTarFile(vo);
		String str = " java java " ;
		LOGGER.debug(StringUtils.strip(str));
	}
}