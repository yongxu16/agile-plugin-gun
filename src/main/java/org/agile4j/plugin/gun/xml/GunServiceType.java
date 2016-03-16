package org.agile4j.plugin.gun.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.helper.ExcelHelper;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.IGunFile;
import org.agile4j.plugin.gun.service.impl.GenPluginEntityServiceImpl;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.agile4j.plugin.gun.xml.servicetype.Field;
import org.agile4j.plugin.gun.xml.servicetype.Fields;
import org.agile4j.plugin.gun.xml.servicetype.Input;
import org.agile4j.plugin.gun.xml.servicetype.Output;
import org.agile4j.plugin.gun.xml.servicetype.Service;
import org.agile4j.plugin.gun.xml.servicetype.ServiceType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servcie type 文件替换
 * 
 * @author hanyx
 * @since
 */
public class GunServiceType implements IGunFile {

	private static final Logger LOGGER = LoggerFactory.getLogger(GunServiceType.class);

	private Map<String, ServiceType> serviceTypeMap;
	private GenPluginEntityServiceImpl genPluginEntityServiceImpl = new GenPluginEntityServiceImpl();
	private static Map<String,List<String>> errorMap = new HashMap<>() ;
	
	public GunServiceType() {
		serviceTypeMap = new HashMap<>();
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
		serviceTypeMap = ParserUtil.deplogyServiceType(new File(initLoadUrl));
		List<GenPluginEntity> genPluginEntityList = getGenPluginList() ;
//		genPluginEntityServiceImpl.batcheInsertGenPluginEntity(genPluginEntityList);
		saveExcel(genPluginEntityList);
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
		if (MapUtils.isNotEmpty(serviceTypeMap)) {
			for (Map.Entry<String, ServiceType> entry : serviceTypeMap.entrySet()) {
				ServiceType serviceType = entry.getValue();
				
				if (CollectionUtils.isNotEmpty(serviceType.getDescriptionOrService())) {
					for (Object descriptionOrService : serviceType.getDescriptionOrService()) {
						if (descriptionOrService instanceof Service) {
							Service service = (Service) descriptionOrService;
							if (service.getInterface() != null) {
								Input input = service.getInterface().getInput();
								if (input != null) {
									for (Object fieldOrFields : input.getFieldOrFields()) {
										if (fieldOrFields instanceof Field) {
											Field field = (Field) fieldOrFields;
											GenPluginEntity vo = new GenPluginEntity();
											vo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
											vo.setSrcParent(service.getId());
											vo.setSrcField(field.getId());
											vo.setTarParent(service.getId());
											vo.setTarField(field.getId());
											genPluginEntityList.add(vo);
										}

										if (fieldOrFields instanceof Fields) {
											Fields fields = (Fields) fieldOrFields;
											for (Object obj : fields.getFieldOrFields()) {
												if (obj instanceof Field) {
													Field field = (Field) obj;
													field.getId();
													GenPluginEntity vo = new GenPluginEntity();
													vo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
													vo.setSrcParent(service.getId());
													vo.setSrcField(field.getId());
													vo.setTarParent(service.getId());
													vo.setTarField(field.getId());
													genPluginEntityList.add(vo);
												}
												
												if (obj instanceof Fields) {
													Fields subFields = (Fields) obj;
													for (Object subObj : subFields.getFieldOrFields()) {
														if (subObj instanceof Field) {
															Field subfield = (Field) subObj;
															subfield.getId();
															GenPluginEntity subVo = new GenPluginEntity();
															subVo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
															subVo.setSrcParent(service.getId());
															subVo.setSrcField(subfield.getId());
															subVo.setTarParent(service.getId());
															subVo.setTarField(subfield.getId());
															genPluginEntityList.add(subVo);
														}
													}
												}
											}
										}
									}
								}

								Output output = service.getInterface().getOutput();
								if (output != null) {
									for (Object fieldOrFields : output.getFieldOrFields()) {
										if (fieldOrFields instanceof Field) {
											Field field = (Field) fieldOrFields;
											GenPluginEntity vo = new GenPluginEntity();
											vo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
											vo.setSrcParent(service.getId());
											vo.setSrcField(field.getId());
											vo.setTarParent(service.getId());
											vo.setTarField(field.getId());
											genPluginEntityList.add(vo);
										}

										if (fieldOrFields instanceof Fields) {
											Fields fields = (Fields) fieldOrFields;
											for (Object obj : fields.getFieldOrFields()) {
												if (obj instanceof Field) {
													Field field = (Field) obj;
													field.getId();
													GenPluginEntity vo = new GenPluginEntity();
													vo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
													vo.setSrcParent(service.getId());
													vo.setSrcField(field.getId());
													vo.setTarParent(service.getId());
													vo.setTarField(field.getId());
													genPluginEntityList.add(vo);
												}
												
												if (obj instanceof Fields) {
													Fields subFields = (Fields) obj;
													for (Object subObj : subFields.getFieldOrFields()) {
														if (subObj instanceof Field) {
															Field subfield = (Field) subObj;
															subfield.getId();
															GenPluginEntity subVo = new GenPluginEntity();
															subVo.setGunType(ModelObjectConstant.SERVICETYPE_FLAG);
															subVo.setSrcParent(service.getId());
															subVo.setSrcField(subfield.getId());
															subVo.setTarParent(service.getId());
															subVo.setTarField(subfield.getId());
															genPluginEntityList.add(subVo);
														}
													}
												}
											}
										}
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
	
	public void saveExcel(List<GenPluginEntity> list) {
		ExcelHelper.saveData(list,"src/main/resources/gun-servicetype-data2.xlsx");
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
		List<GenPluginEntity> genPluginEntityList = ExcelHelper.loadData("src/main/resources/gun-servicetype-data.xlsx") ;
		return createTarFile(genPluginEntityList, createTarFileUrl);
	}

	public boolean createTarFile(String createTarFileUrl, String excelUrl) {
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
				Map<String, ServiceType> serviceTypeMap = ParserUtil.deplogyServiceType(new File(createTarFileUrl));
				if (MapUtils.isNotEmpty(serviceTypeMap)) {
					for (Map.Entry<String, ServiceType> serviceTypeEntry : serviceTypeMap.entrySet()) {
						ServiceType serviceType = serviceTypeEntry.getValue();
						if (CollectionUtils.isNotEmpty(serviceType.getDescriptionOrService())) {
							for (Object descriptionOrService : serviceType.getDescriptionOrService()) {
								if (descriptionOrService instanceof Service) {
									Service service = (Service) descriptionOrService;
									for (GenPluginEntity gpe : genPluginEntityList) {
										if (StringUtils.equals(service.getId(), gpe.getSrcParent())) {
											String orldServiceId = service.getId();
											service.setId(gpe.getTarParent());
											if (service.getInterface() != null) {
												Input input = service.getInterface().getInput(); // Input
												if (input != null) {
													for (Object fieldOrFields : input.getFieldOrFields()) {
														if (fieldOrFields instanceof Field) {
															Field field = (Field) fieldOrFields;
															String fieldKey = orldServiceId + "_" + field.getId();
															if (tarFieldMap.containsKey(fieldKey)) {
																field.setId(tarFieldMap.get(fieldKey));
															}
														}

														if (fieldOrFields instanceof Fields) {
															Fields fields = (Fields) fieldOrFields;
															for (Object obj : fields.getFieldOrFields()) {
																if (obj instanceof Field) {
																	Field field = (Field) obj;
																	String fieldKey = orldServiceId + "_" + field.getId();
																	if (tarFieldMap.containsKey(fieldKey)) {
																		field.setId(tarFieldMap.get(fieldKey));
																	}
																}
																
																if (obj instanceof Fields) {
																	Fields subfields = (Fields) obj;
																	for (Object subObjects : subfields.getFieldOrFields()) {
																		if (subObjects instanceof Field) {
																			Field subObject = (Field) subObjects ;
																			String fieldKey = orldServiceId + "_" + subObject.getId();
																			if (tarFieldMap.containsKey(fieldKey)) {
																				subObject.setId(tarFieldMap.get(fieldKey));
																			}
																		}
																	}
																}
															}
														}
													}
												}

												Output output = service.getInterface().getOutput(); // Output
												if (output != null) {
													for (Object fieldOrFields : output.getFieldOrFields()) {
														if (fieldOrFields instanceof Field) {
															Field field = (Field) fieldOrFields;
															String fieldKey = orldServiceId + "_" + field.getId();
															if (tarFieldMap.containsKey(fieldKey)) {
																field.setId(tarFieldMap.get(fieldKey));
															}
														}

														if (fieldOrFields instanceof Fields) {
															Fields fields = (Fields) fieldOrFields;
															for (Object obj : fields.getFieldOrFields()) {
																if (obj instanceof Field) {
																	Field field = (Field) obj;
																	String fieldKey = orldServiceId + "_" + field.getId();
																	if (tarFieldMap.containsKey(fieldKey)) {
																		field.setId(tarFieldMap.get(fieldKey));
																	}
																}
																
																if (obj instanceof Fields) {
																	Fields subfields = (Fields) obj;
																	for (Object subObjects : subfields.getFieldOrFields()) {
																		if (subObjects instanceof Field) {
																			Field subObject = (Field) subObjects ;
																			String fieldKey = orldServiceId + "_" + subObject.getId();
																			if (tarFieldMap.containsKey(fieldKey)) {
																				subObject.setId(tarFieldMap.get(fieldKey));
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						
						File file = new File (serviceTypeEntry.getKey());
						String tarFilePath ;
						if (GunConstant.FILE_NEW_PATH_TYPE.equals(GunHelper.getFileReplaceType())) {
							tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
						} else {
							tarFilePath = file.getAbsolutePath(); // 目标文件路径
						}
						ParserUtil.replaceXmlField(tarFilePath, serviceType);
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
	
//	private Map<String, String> getUniqTarFieldBySrcField(List<GenPluginEntity> genPluginEntityList) {
//		Map<String, String> tarFieldMap = new HashMap<>() ;
//		for (GenPluginEntity vo : genPluginEntityList) {
//			String key = vo.getSrcField() ;
//			if (tarFieldMap.containsKey(key)) {
//				tarFieldMap.remove(key) ;
//			} else {
//				tarFieldMap.put(key, vo.getTarField()) ;
//			}
//		}
//		return tarFieldMap ;
//	}

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