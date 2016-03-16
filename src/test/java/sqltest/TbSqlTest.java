package sqltest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.agile4j.plugin.gun.model.TbEntity;
import org.agile4j.plugin.gun.utils.ExcelUtils;
import org.agile4j.plugin.gun.xml.tbsql.DemoSchema;
import org.agile4j.plugin.gun.xml.tbsql.ParseTbsqlUtil;
import org.agile4j.plugin.gun.xml.tbsql.Schema;
import org.agile4j.plugin.gun.xml.tbsql.Table;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TbSqlTest {

	private static final Logger logger = LoggerFactory.getLogger(TbSqlTest.class);

	@org.junit.Test
	public void testDeploy() throws Exception {
		// String xmlFilePath =
		// URLDecoder.decode(TestTbSql.class.getResource("/").getPath(),
		// "UTF-8");
		// xmlFilePath = xmlFilePath +
		// "/core/ac/tables/fdmg/FmManage.tables.xml" ;
		// String xmlFilePath = "D:/HYX_JAVA_EE/ZTE/app/lttsv7/apps" ;
		String xmlFilePath = "E:\\gun_plugin_test";
		Map<String, Schema> tbMap = deploy(new File(xmlFilePath));

		List<TbEntity> voList = parseEntity(tbMap);

		buildExel(voList);
	}

	private void buildExel(List<TbEntity> voList) {
		LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<>();
		propertyHeaderMap.put("core", "core");
		propertyHeaderMap.put("schema", "schema");
		propertyHeaderMap.put("schemaName", "schemaName"); // 直接获取Student中的sexName，而不是sex
		propertyHeaderMap.put("tableId", "tableId");
		propertyHeaderMap.put("tableName", "tableName");
		propertyHeaderMap.put("count", "count");

		try {
			XSSFWorkbook ex = ExcelUtils.generateXlsxWorkbook("sheet2", propertyHeaderMap, voList);
			OutputStream out = new FileOutputStream("F://银行核心-tb整理.xlsx");
			ex.write(out);
			logger.info("导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<TbEntity> parseEntity(Map<String, Schema> tbMap) {
		List<TbEntity> voList = new ArrayList<>();
		Map<String, DemoSchema> map = new HashMap<>();
		for (Entry<String, Schema> entry : tbMap.entrySet()) {
			String path = entry.getKey();
			Schema tbList = entry.getValue();
			if (tbList != null) {
				String core;
				if (path.contains("ltts-web-ops")) {
					core = "ltts-web-ops";
				} else if (path.contains("aplt")) {
					core = "aplt";
				} else if (path.contains("busi")) {
					core = path.substring(path.indexOf("busi") + 5, path.indexOf("busi") + 7);
				} else {
					core = "jar";
				}
				DemoSchema vo = new DemoSchema();
				vo.setSchema(tbList);
				String fileName = path.substring(path.lastIndexOf("\\")).substring(1);
				vo.setFileName(fileName);
				vo.setCore(core);
				map.put(fileName, vo);
			}
		}

		for (Entry<String, DemoSchema> entry : map.entrySet()) {
			DemoSchema demos = entry.getValue();
			Schema tbList = demos.getSchema();
			for (Table tb : tbList.getTable()) {
				TbEntity vo = new TbEntity();
				vo.setCore(demos.getCore());
				vo.setSchema(demos.getFileName());
				vo.setSchemaName(tbList.getLongname());
				vo.setTableId(tb.getId());
				vo.setTableName(StringUtils.isEmpty(tb.getLongname()) ? "null" : tb.getLongname());
				vo.setCount(1);
				voList.add(vo);
			}
		}
		return voList;
	}

	private Map<String, Schema> deploy(File... files) {
		Set<String> filePathSet = new HashSet<String>();
		// List<Sqls> sqlList = new ArrayList<> () ;
		Map<String, Schema> map = new HashMap<>();
		if (files != null && files.length > 0) {
			for (File innerFile : files) {
				if (innerFile.exists() && innerFile.isFile()) {// 文件对象存在
					filePathSet.add(innerFile.getAbsolutePath());
				} else if (innerFile.exists() && innerFile.isDirectory()) {
					Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "tables.xml" }, true);
					if (filesCollection != null && !filesCollection.isEmpty()) {
						for (File file : filesCollection) {
							filePathSet.add(file.getAbsolutePath());// 目录下的合规文件
						}
					}
				}
			}
			List<String> filePathList = new ArrayList<String>(filePathSet);
			if (filePathList != null && !filePathList.isEmpty()) {
				for (String xmlFilePath : filePathList) {
					try {
						Schema sql = ParseTbsqlUtil.getTbsConfig(xmlFilePath);
						map.put(xmlFilePath, sql);
						// sqlList.add(sql) ;
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}

		}
		return map;
	}
}
