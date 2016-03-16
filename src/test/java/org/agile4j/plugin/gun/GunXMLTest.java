package org.agile4j.plugin.gun;

import java.util.ArrayList;
import java.util.List;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.xml.GunXml;
import org.junit.Before;
import org.junit.Test;

/**
 * ModelObject 解析
 * @author hanyx
 * @since
 */
public class GunXMLTest {

	private GunXml gunXml ;
	
	@Before
	public void before() {
		gunXml = new GunXml() ;
	}
	
	/**
	 * 默认路径下解析出来的文件数据保存到表中
	 * init.load.url=E:\\gun_plugin_test\\modelobject
	 */
//	@Test
	public void initFileTest01() {
		
		gunXml.initFile(ModelObjectConstant.TABLES_FLAG);
	}

	/**
	 * 指定路径下解析出来的文件数据保存到表中
	 */
//	@Test
	public void initFileTest02() {
		gunXml.initFile(ModelObjectConstant.TABLES_FLAG,"E:\\gun_plugin_test\\");
	}

	/**
	 * 保存单个变更对象
	 */
//	@Test
	public void initFileTest03() {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		gunXml.initFile(vo);
	}

	/**
	 * 保存多个变更对象
	 */
//	@Test
	public void initFileTest04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcParent("suny");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		List<GenPluginEntity> gpeList = new ArrayList<GenPluginEntity>();
		gpeList.add(vo);
		gunXml.initFile(gpeList);
	}

	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
//	@Test
	public void createTarFile01() {
		gunXml.createTarFile(ModelObjectConstant.TABLES_FLAG) ;
	}
	
	/**
	 * 自定义路径下文件 生成新的目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
	@Test
	public void createTarFile02() {
		String excelUrl = "src/main/resources/gun-table-data.xlsx" ;
		String xmlUrl = "E:\\gun_plugin_test\\FmManage.tables.xml" ;
		gunXml.createTarFile(ModelObjectConstant.TABLES_FLAG, xmlUrl, excelUrl) ;
		
	}

	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile03() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		gunXml.createTarFile(ModelObjectConstant.TABLES_FLAG, vo) ;
	}

	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TABLE_TYPE);
		vo.setSrcParent("kfmp_scxcfl");
//		vo.setSrcField("java");
		gunXml.createTarFile(ModelObjectConstant.TABLES_FLAG, vo,"E:\\gun_plugin_test\\FmManage.tables.xml") ;
	}
	
	

}
