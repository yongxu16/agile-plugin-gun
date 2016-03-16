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

import org.agile4j.plugin.gun.model.SqlEntity;
import org.agile4j.plugin.gun.utils.ExcelUtils;
import org.agile4j.plugin.gun.xml.namesql.Delete;
import org.agile4j.plugin.gun.xml.namesql.DemoSqls;
import org.agile4j.plugin.gun.xml.namesql.DynamicSelect;
import org.agile4j.plugin.gun.xml.namesql.Insert;
import org.agile4j.plugin.gun.xml.namesql.ParseNamesqlUtil;
import org.agile4j.plugin.gun.xml.namesql.Select;
import org.agile4j.plugin.gun.xml.namesql.Sqls;
import org.agile4j.plugin.gun.xml.namesql.Update;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamesqlTest {

	private static final Logger logger = LoggerFactory.getLogger(NamesqlTest.class);

	@org.junit.Test
	public void testDeploy() throws Exception {
		// String xmlFilePath =
		// URLDecoder.decode(Test.class.getResource("/").getPath(), "UTF-8");
		// xmlFilePath = xmlFilePath +
		// "/core/at/namedsql/loan/LnAcctBusi.nsql.xml" ;
		String xmlFilePath = "D://HYX_JAVA_EE/ZTE/app/lttsv7/apps";
		Map<String, Sqls> sqlMap = deploy(new File(xmlFilePath));
		List<SqlEntity> voList = parseEntity(sqlMap);

		LinkedHashMap<String, String> propertyHeaderMap = new LinkedHashMap<>();
		propertyHeaderMap.put("core", "core");
		propertyHeaderMap.put("namedsql", "namedsql");
		propertyHeaderMap.put("sqlsName", "sqlsName"); // 直接获取Student中的sexName，而不是sex
		propertyHeaderMap.put("selectId", "selectId");
		propertyHeaderMap.put("selectName", "selectName");
		propertyHeaderMap.put("count", "count");

		try {
			XSSFWorkbook ex = ExcelUtils.generateXlsxWorkbook("sheet2", propertyHeaderMap, voList);
			OutputStream out = new FileOutputStream("F://银行核心-sql整理.xlsx");
			ex.write(out);
			logger.info("导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<SqlEntity> parseEntity(Map<String, Sqls> sqlMap) {
		List<SqlEntity> voList = new ArrayList<>();
		Map<String, DemoSqls> map = new HashMap<>();
		DemoSqls demoSql = null;
		for (Entry<String, Sqls> entry : sqlMap.entrySet()) {
			String path = entry.getKey();
			String core;
			if (path.contains("ltts-web-ops")) {
				core = "ltts-web-ops";
			} else if (path.contains("aplt")) {
				core = "aplt";
			} else {
				if (path.contains("busi")) {
					core = path.substring(path.indexOf("busi") + 5, path.indexOf("busi") + 7);
				} else {
					core = "jar";
				}

			}

			String fileName = path.substring(path.lastIndexOf("\\")).substring(1);
			demoSql = new DemoSqls();
			demoSql.setSql(entry.getValue());
			demoSql.setFileName(fileName);
			demoSql.setCore(core);
			map.put(fileName, demoSql);
		}

		for (Entry<String, DemoSqls> entry : map.entrySet()) {
			DemoSqls demoSql1 = entry.getValue();
			Sqls sql = demoSql1.getSql();
			if (sql != null && sql.getDynamicSelectOrSelectOrInsert() != null) {
				for (Object obj : sql.getDynamicSelectOrSelectOrInsert()) {
					SqlEntity vo = new SqlEntity();
					vo.setCore(demoSql1.getCore());
					vo.setNamedsql(demoSql1.getFileName());
					vo.setSqlsName(sql.getLongname());
					vo.setCount(1);

					if (obj instanceof DynamicSelect) {
						DynamicSelect dsel = (DynamicSelect) obj;
						vo.setSelectId(dsel.getId());
						vo.setSelectName(StringUtils.isEmpty(dsel.getLongname()) ? "null" : dsel.getLongname());

					}
					if (obj instanceof Select) {
						Select sel = (Select) obj;
						vo.setSelectId(sel.getId());
						vo.setSelectName(StringUtils.isEmpty(sel.getLongname()) ? "null" : sel.getLongname());
					}
					if (obj instanceof Insert) {
						Insert ins = (Insert) obj;
						vo.setSelectId(ins.getId());
						vo.setSelectName(StringUtils.isEmpty(ins.getLongname()) ? "null" : ins.getLongname());
					}
					if (obj instanceof Update) {
						Update up = (Update) obj;
						vo.setSelectId(up.getId());
						vo.setSelectName(StringUtils.isEmpty(up.getLongname()) ? "null" : up.getLongname());
					}
					if (obj instanceof Delete) {
						Delete del = (Delete) obj;
						vo.setSelectId(del.getId());
						vo.setSelectName(StringUtils.isEmpty(del.getLongname()) ? "null" : del.getLongname());
					}
					voList.add(vo);
				}

			}
		}
		return voList;
	}

	public Map<String, Sqls> deploy(File... files) {
		Set<String> filePathSet = new HashSet<String>();
		// List<Sqls> sqlList = new ArrayList<> () ;
		Map<String, Sqls> map = new HashMap<>();
		if (files != null && files.length > 0) {
			for (File innerFile : files) {
				if (innerFile.exists() && innerFile.isFile()) {// 文件对象存在
					filePathSet.add(innerFile.getAbsolutePath());
				} else if (innerFile.exists() && innerFile.isDirectory()) {
					Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] { "nsql.xml" }, true);
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
						Sqls sql = ParseNamesqlUtil.getSqlsConfig(xmlFilePath);
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
