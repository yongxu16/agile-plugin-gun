package org.agile4j.plugin.gun.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.IGunFile;
import org.agile4j.plugin.gun.service.impl.GenPluginEntityServiceImpl;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.agile4j.plugin.gun.xml.type.ComplexType;
import org.agile4j.plugin.gun.xml.type.Element;
import org.agile4j.plugin.gun.xml.type.Schema;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Type文件替换
 * 
 * @author hanyx
 * @since
 */
public class GunType implements IGunFile {

	private static final Logger LOGGER = LoggerFactory.getLogger(GunType.class);

	private Map<String, Schema> typeMap;
	private GenPluginEntityServiceImpl genPluginEntityServiceImpl = new GenPluginEntityServiceImpl();

	public GunType() {
		typeMap = new HashMap<>();
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
		typeMap = ParserUtil.deplogyType(new File(initLoadUrl));
		genPluginEntityServiceImpl.batcheInsertGenPluginEntity(getGenPluginList());
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
		if (MapUtils.isNotEmpty(typeMap)) {
			for (Map.Entry<String, Schema> entry : typeMap.entrySet()) {
				Schema schema = entry.getValue();
				if (CollectionUtils.isNotEmpty(schema.getDescriptionOrComplexType())) {
					for (Object obj : schema.getDescriptionOrComplexType()) {
						if (obj instanceof ComplexType) {
							ComplexType complexType = (ComplexType) obj ;
							if (CollectionUtils.isNotEmpty(complexType.getElementOrDescriptionOrOdbindexes())) {
								for (Object elementOrDescriptionOrOdbindexes : complexType.getElementOrDescriptionOrOdbindexes()) {
									if (elementOrDescriptionOrOdbindexes instanceof Element) {
										Element element = (Element) elementOrDescriptionOrOdbindexes ;
										GenPluginEntity vo = new GenPluginEntity();
										vo.setGunType(GunConstant.TYPESQL_TYPE);
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
				}
			}
		}
		return genPluginEntityList;
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
		List<GenPluginEntity> genPluginEntityList = genPluginEntityServiceImpl.getGenPluginEntityListByType(GunConstant.TYPESQL_TYPE);
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
				Map<String, String> tarFieldMap = getUniqTarField(genPluginEntityList);
				Map<String, Schema> schemaMap = ParserUtil.deplogyType(new File(createTarFileUrl));
				if (MapUtils.isNotEmpty(schemaMap)) {
					for (Map.Entry<String, Schema> schemaEntry : schemaMap.entrySet()) {
						Schema schema = schemaEntry.getValue();
						if (CollectionUtils.isNotEmpty(schema.getDescriptionOrComplexType())) {
							for (Object obj : schema.getDescriptionOrComplexType()) {
								if (obj instanceof ComplexType) {
									ComplexType complexType = (ComplexType) obj;
									if (CollectionUtils.isNotEmpty(complexType.getElementOrDescriptionOrOdbindexes())) {
										for (Object elementOrDescriptionOrOdbindexes : complexType.getElementOrDescriptionOrOdbindexes()) {
											if (elementOrDescriptionOrOdbindexes instanceof Element) {
												Element element = (Element) elementOrDescriptionOrOdbindexes;
												for (GenPluginEntity gpe : genPluginEntityList) {
													if (StringUtils.equals(complexType.getId(), gpe.getSrcParent())) {
														String orldTableId = complexType.getId();
														String key = orldTableId + "_" + element.getId();
														if (tarFieldMap.containsKey(key)) {
															element.setId(tarFieldMap.get(key));
														}
													}
												}
											}
										}
									}
								}
							}
						}
						File file = new File(schemaEntry.getKey());
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
	}
}