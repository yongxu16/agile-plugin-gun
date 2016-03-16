package ltts_test;

import java.io.File;
import java.util.Map;

import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.helper.GunHelper;
import org.agile4j.plugin.gun.utils.ParserUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import cn.sunline.ltts.frw.model.ModelObject;
import cn.sunline.ltts.frw.model.sql.SqlGroup;

public class ModelTest {
	private static final Logger LOGGER = LogManager.getLogger(ModelTest.class) ;
	private static final String FILE_PATH = "E:\\gun_plugin_test\\modelobject" ;
	
	@Test
	public void testModel() {
		File file1 = new File(FILE_PATH);
		Map<String, ModelObject> map = ParserUtil.deploy(ModelObjectConstant.NAMEDSQL_FLAG, file1);
		MapUtils.isEmpty(map) ;
		for (Map.Entry<String, ModelObject> entry : map.entrySet()) {
			LOGGER.debug(entry.getKey());
			SqlGroup sqlGroup = (SqlGroup) entry.getValue() ; 
			LOGGER.debug(entry.getValue());
		}
		
//		for (Map.Entry<String, ModelObject> entry : map.entrySet()) {
//			Schema schema = (Schema) entry.getValue() ;
//			File file = new File (entry.getKey());
//			String tarFilePath = file.getParent() + File.separator + ConfigHelper.getTarFileFormat() + file.getName(); // 目标文件路径
//			ParserUtil.replaceXmlField(tarFilePath, schema);
//		}
	}
	
	@Test
	public void testServiceType() {
		String path = FILE_PATH + "\\serivcetype" ;
		File file1 = new File(path);
		Map<String, ModelObject> map = ParserUtil.deploy(ModelObjectConstant.SERVICETYPE_FLAG, file1);
		MapUtils.isEmpty(map) ;
		for (Map.Entry<String, ModelObject> entry : map.entrySet()) {
			LOGGER.debug(entry.getKey());
			cn.sunline.ltts.frw.model.tran.service.ServiceType serviceType = (cn.sunline.ltts.frw.model.tran.service.ServiceType) entry.getValue() ; 
			LOGGER.debug(serviceType);
		}

		for (Map.Entry<String, ModelObject> entry : map.entrySet()) {
			cn.sunline.ltts.frw.model.tran.service.ServiceType serviceType = (cn.sunline.ltts.frw.model.tran.service.ServiceType) entry.getValue() ;
			File file = new File (entry.getKey());
			String tarFilePath = file.getParent() + File.separator + GunHelper.getTarFileFormat() + file.getName(); // 目标文件路径
			ParserUtil.replaceXmlField(tarFilePath, serviceType);
		}

	}

}