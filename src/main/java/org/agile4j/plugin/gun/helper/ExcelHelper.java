package org.agile4j.plugin.gun.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.utils.ExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 帮助类
 * 
 * @author hanyx
 * @since
 */
public final class ExcelHelper {
	private static final Logger LOGGER = LogManager.getLogger(ExcelHelper.class);

	/**
	 * 读取默认Excel文件， 并以GenPluginEntity 集合形式返回
	 */
	public static List<GenPluginEntity> loadData() {
		return loadData(GunHelper.getExcelPath()) ;
	}

	/**
	 * 读取指定路径下Excel文件， 并以GenPluginEntity 集合形式返回
	 */
	public static List<GenPluginEntity> loadData(String excelPath) {
		List<GenPluginEntity> pojoList = new ArrayList<>();

		// 设置要读取的文件路径
		try (InputStream dataXls = new FileInputStream(excelPath)) {
			XSSFWorkbook wb = new XSSFWorkbook(dataXls);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				if (i > 0) {
					row = sheet.getRow(i);
					GenPluginEntity pojo = new GenPluginEntity();
					for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
						// 通过 row.getCell(j).toString() 获取单元格内容，
						if (j == 0) {
							pojo.setGunType(row.getCell(j).toString());
						} else if (j == 1) {
							pojo.setSrcParent(row.getCell(j).toString());
						} else if (j == 2) {
							pojo.setSrcField(row.getCell(j).toString());
						} else if (j == 3) {
							pojo.setTarParent(row.getCell(j).toString());
						} else if (j == 4) {
							pojo.setTarField(row.getCell(j).toString());
							pojoList.add(pojo);
						}
					}
				}
			}
			LOGGER.debug(pojoList);
		} catch (Exception e) {
			LOGGER.error("save Data failur", e);
			throw new RuntimeException(e);
		}

		return pojoList;
	}
	
	
	/**
	 * 把GenPluginEntity对象数据保存到 Excel文件中，默认路径
	 */
	public static void saveData(List<GenPluginEntity> pojoList) {
		saveData(pojoList, GunHelper.getExcelPath());
	}

	/**
	 * 把GenPluginEntity对象数据保存到 Excel文件中， 指定路径
	 */
	public static void saveData(List<GenPluginEntity> pojoList, String excelPath) {
		if (CollectionUtils.isNotEmpty(pojoList)) {
			LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<>();
			propertyHeaderMap.put("gunType", "GUN_TYPE");
			propertyHeaderMap.put("srcParent", "SRC_PARENT");
			propertyHeaderMap.put("srcField", "SRC_FIELD");
			propertyHeaderMap.put("tarParent", "TAR_PARENT");
			propertyHeaderMap.put("tarField", "TAR_FIELD");

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
	
	public static void main(String[] args) {
		List<GenPluginEntity> gpeList = ExcelHelper.loadData();
		ExcelHelper.saveData(gpeList);
	}
}
