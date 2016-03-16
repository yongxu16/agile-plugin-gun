package org.agile4j.plugin.gun;

import java.util.ArrayList;
import java.util.List;

import org.agile4j.plugin.gun.constant.GunConstant;
import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.xml.GunTable;
import org.junit.Before;
import org.junit.Test;

public class GunTypeTest {

	private GunTable gunTable ;
	
	@Before
	public void before() {
		gunTable = new GunTable() ;
	}
	
	/**
	 * 默认路径下解析出来的文件数据保存到表中
	 */
	@Test
	public void initFileTest01() {
		gunTable.initFile();
	}

	/**
	 * 指定路径下解析出来的文件数据保存到表中
	 */
	@Test
	public void initFileTest02() {
		gunTable.initFile("E:\\gun_plugin_test\\");
	}

	/**
	 * 保存单个变更对象
	 */
	@Test
	public void initFileTest03() {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setGunType(GunConstant.TYPESQL_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		gunTable.initFile(vo);
	}

	/**
	 * 保存多个变更对象
	 */
	@Test
	public void initFileTest04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TYPESQL_TYPE);
		vo.setSrcParent("suny");
		vo.setSrcField("java");
		vo.setTarParent("hanyx001");
		vo.setTarField("oracle");
		List<GenPluginEntity> gpeList = new ArrayList<GenPluginEntity>();
		gpeList.add(vo);
		gunTable.initFile(gpeList);
	}

	/**
	 * 对默认路径下文件生成目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
	@Test
	public void createTarFile01() {
		gunTable.createTarFile() ;
	}
	
	/**
	 * 自定义路径下文件 生成新的目标文件 (替换规则是所有表中存在的GUN_TYPE =0 的字段)
	 */
	@Test
	public void createTarFile02() {
		gunTable.createTarFile("E:\\gun_plugin_test\\FmManage.tables.xml") ;
	}

	/**
	 * 对默认路径下文件 生成新的目标文件，包含指定条件
	 */
	@Test
	public void createTarFile03() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TYPESQL_TYPE);
		vo.setSrcParent("hanyx");
		vo.setSrcField("java");
		gunTable.createTarFile(vo) ;
	}

	/**
	 * 对自定义路径下文件 生成新的目标文件，包含指定条件
	 */
	@Test
	public void createTarFile04() {
		GenPluginEntity vo = new GenPluginEntity();
		vo.setGunType(GunConstant.TYPESQL_TYPE);
		vo.setSrcParent("kfmp_scxcfl");
//		vo.setSrcField("java");
		gunTable.createTarFile(vo,"E:\\gun_plugin_test\\FmManage.tables.xml") ;
	}
	
	

}
