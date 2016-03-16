package org.agile4j.plugin.gun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.utils.ExcelUtils;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.agile4j.plugin.gun.xml.GunServiceType;
import org.agile4j.plugin.gun.xml.tbsql.Field;
import org.agile4j.plugin.gun.xml.tbsql.Schema;
import org.agile4j.plugin.gun.xml.tbsql.Table;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

public class GunServiceTypeTest {

	private static final Logger LOGGER = LogManager.getLogger(GunServiceTypeTest.class) ;
	private GunServiceType gunServiceType ;
	
	@Before
	public void before() {
		gunServiceType = new GunServiceType() ;
	}
	
	/**
	 * 默认路径下解析出来的文件数据保存到表中
	 */
//	@Test
	public void initFileTest01() {
		gunServiceType.initFile();
	}

	/**
	 * 指定路径下解析出来的文件数据保存到表中
	 */
	@Test
	public void initFileTest02() {
		gunServiceType.initFile("E:\\gun_plugin_test\\ac");
	}

	/**
	 * 保存单个变更对象
	 */
//	@Test
	public void initFileTest03() {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		gunServiceType.initFile(vo);
	}

	/**
	 * 保存多个变更对象
	 */
//	@Test
	public void initFileTest04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("suny");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		List<GenPluginEntity> gpeList = new ArrayList<GenPluginEntity>();
		gpeList.add(vo);
		gunServiceType.initFile(gpeList);
	}

	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
//	@Test
	public void createTarFile01() {
		gunServiceType.createTarFile() ;
	}
	
	/**
	 * 自定义路径下文件 生成新的目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
	@Test
	public void createTarFile02() {
		gunServiceType.createTarFile("E:\\gun_plugin_test\\ac") ;
	}

	@Test
	public void createTarFile03() {
		String xmlUrl= "E:\\gun_plugin_test\\ac\\src\\main\\resources\\servicetype\\fdmg" ;
		String excleUrl= "src/main/resources/gun-servicetype-data1.xlsx" ;	// FmScxc.serviceType.xml 中数据
		gunServiceType.createTarFile(xmlUrl,excleUrl) ;
	}
	
	@Test
	public void createTarFile04() {
		String xmlUrl= "E:\\gun_plugin_test\\ac\\" ;
		String excleUrl= "src/main/resources/gun-servicetype-data2.xlsx" ;
		gunServiceType.createTarFile(xmlUrl,excleUrl) ;
	}
	
	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile05() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		gunServiceType.createTarFile(vo) ;
	}

	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile06() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("kfaa_jytozz");
//		vo.setSrcField("java");
		gunServiceType.createTarFile(vo,"E:\\gun_plugin_test\\FaBusiAccounting.tables.xml") ;
	}
	
	/**
	 * 导出重复的字段
	 */
//	@Test
	public void equalsIgnoreTest() {
		String filePath = "D:\\HYX_JAVA_EE\\ZTE\\app\\lttsv7" ;
		Map<String, Schema> schemaMap = ParserUtil.deplogyTable(new File(filePath));
		
		Map<String, Set<String>> map = new HashMap<>() ;
		if (MapUtils.isNotEmpty(schemaMap)) {
			for (Map.Entry<String, Schema> schemaEntry : schemaMap.entrySet()) {
				Schema schema = schemaEntry.getValue();
				if (CollectionUtils.isNotEmpty(schema.getTable())) {
					for (Table table : schema.getTable()) {
						if (table.getFields() != null) {
							if (CollectionUtils.isNotEmpty(table.getFields().getField())) {
								for (Field field : table.getFields().getField()) {
									String key = field.getId();
									String commn = field.getLongname() ;
									Set<String> subSet = map.get(key) ;
									if (subSet == null) {
										subSet = new HashSet<>() ;
										map.put(key, subSet) ;
									}
									subSet.add(commn) ;
								}
							}
						}
					}
				}
			}
		}
		
		Map<String, Set<String>> subMap = new HashMap<>() ;
		for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
			if (entry.getValue().size() != 1) {
				subMap.put(entry.getKey(), entry.getValue()) ;
			}
		}
		
		List<DistincVo> pojoList = new ArrayList<>() ;
		for (Map.Entry<String, Set<String>> entry : subMap.entrySet()) {
			for (String longname : entry.getValue()) {
				DistincVo vo  = new DistincVo() ;
				vo.setField(entry.getKey());
				vo.setLongname(longname);
				vo.setCount("1");
				pojoList.add(vo) ;
			}
		}
		
		if (CollectionUtils.isNotEmpty(pojoList)) {
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<>();
			propertyHeaderMap.put("field", "field");
			propertyHeaderMap.put("longname", "longname");
			propertyHeaderMap.put("count", "count");

			String excelPath = "src/main/resources/field-data.xlsx" ;
			try (OutputStream out = new FileOutputStream(excelPath)) {
				XSSFWorkbook ex = ExcelUtils.generateXlsxWorkbook("gun_data", propertyHeaderMap, pojoList);
				ex.write(out);
				LOGGER.debug("导出成功！");
			} catch (Exception e) {
				LOGGER.error("save Data failur", e);
				throw new RuntimeException(e);
			}
		}
	}
	
}

