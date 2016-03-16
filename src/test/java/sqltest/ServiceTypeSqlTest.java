package sqltest;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.xml.servicetype.ParseServiceTypeUtil;
import org.agile4j.plugin.gun.xml.servicetype.ServiceType;
import org.agile4j.plugin.gun.xml.type.ParseTypeUtil;
import org.agile4j.plugin.gun.xml.type.Schema;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceTypeSqlTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTypeSqlTest.class);
	private static final String XSD_PATH = "/org/agile4j/plugin/gun/xml/servicetype/servicetype.xsd";

	@Test
	public void testDeploy() throws Exception {
		// String xmlFilePath =
		// URLDecoder.decode(TestTbSql.class.getResource("/").getPath(),
		// "UTF-8");
		// xmlFilePath = xmlFilePath +
		// "/core/ac/tables/fdmg/FmManage.tables.xml" ;
		 String xmlFilePath = "D:/HYX_JAVA_EE/ZTE/app/lttsv7/apps" ;
//		String xmlFilePath = "F:\\jaxb_test\\IoDpClose.c_schema.xml";
		Map<String, ServiceType> tbMap = deploy(new File(xmlFilePath));
		logger.debug(tbMap.toString());

	}

//	@Test
	public void testClassLoader() {
		InputStream is = IOUtils.toInputStream(XSD_PATH) ;
//		InputStream is = ClassUtil.getResourceAsStream(XSD_PATH) ;
		logger.debug(is.toString());
	}
	
	private Map<String, ServiceType> deploy(File... files) {
		Set<String> filePathSet = new HashSet<String>();
		// List<Sqls> sqlList = new ArrayList<> () ;
		Map<String, ServiceType> map = new HashMap<>();
		if (files != null && files.length > 0) {
			for (File innerFile : files) {
				if (innerFile.exists() && innerFile.isFile()) {// 文件对象存在
					filePathSet.add(innerFile.getAbsolutePath());
				} else if (innerFile.exists() && innerFile.isDirectory()) {
					Collection<File> filesCollection = FileUtils.listFiles(innerFile, new String[] {ModelObjectConstant.SERVICETYPE_CODE }, true);
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
						ServiceType sql = ParseServiceTypeUtil.getServcieTypeConfig(xmlFilePath);
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
