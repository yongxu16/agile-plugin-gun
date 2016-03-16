package org.agile4j.plugin.gun;

import java.util.ArrayList;
import java.util.List;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.constant.ModelObjectConstant;
import org.agile4j.plugin.gun.helper.ExcelHelper;
import org.agile4j.plugin.gun.java.GunJava;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.junit.Before;
import org.junit.Test;

public class GunJavaTest {
	
	private GunJava gunJava;

	@Before
	public void before() {
		gunJava = new GunJava();
	}
	
	/**
	 * 保存单个变更对象
	 */
//	@Test
	public void initFileTest01() {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setGunType(GunConstant.JAVA_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		gunJava.initFile(vo);
	}
	
	/**
	 * 保存多个变更对象
	 */
//	@Test
	public void initFileTest02() {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setGunType(GunConstant.JAVA_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("java11");
		List<GenPluginEntity> gpeList = new ArrayList<GenPluginEntity>();
		gpeList.add(vo);
		gunJava.initFile(gpeList);
	}

	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =1 的字段)
	 */
//	@Test
	public void createTarFile01() {
		gunJava.createTarFile() ;
	}
	
	/**
	 * 对自定义路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =1 的字段)
	 */
//	@Test
	public void createTarFile02() {
		gunJava.createTarFile("E:\\gun_plugin_test\\ac\\Test.java") ;
	}
	
	/**
	 *  对默认路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile03() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.JAVA_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		gunJava.createTarFile(vo) ;
	}
	
	/**
	 *  对自定义路径下文件 生成新的目标文件，包含指定条件
	 */
//	@Test
	public void createTarFile04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.JAVA_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		gunJava.createTarFile(vo, "E:\\gun_plugin_test\\ac\\Test.java") ;
	}
	
	@Test
	public void newJavaParseTest() {
		List<GenPluginEntity> list = getGenPluginEntityList() ;
//		List<GenPluginEntity> list = ExcelHelper.loadData("src/main/resources/gun-data.xlsx") ;
		
		String createTarFileUrl = "E:\\gun_plugin_test\\Book.java" ;
		gunJava.createTarFile(list, createTarFileUrl) ;
	}
	
	private List<GenPluginEntity> getGenPluginEntityList() {
		List<GenPluginEntity> list = new ArrayList<>() ;
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcField("title");
		vo.setTarField("biaoti");
		list.add(vo) ;
		vo = new GenPluginEntity();
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcField("price");
		vo.setTarField("jiage");
		list.add(vo) ;
		vo = new GenPluginEntity();
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcField("author");
		vo.setTarField("zuozhe");
		list.add(vo) ;
		vo = new GenPluginEntity();
		vo.setGunType(ModelObjectConstant.TABLES_FLAG);
		vo.setSrcField("author");
		vo.setTarField("xieshuderen");
		list.add(vo) ;
		return list ;
	}
}
