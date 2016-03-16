package org.agile4j.plugin.gun.service;

import java.util.List;

import org.agile4j.plugin.gun.model.GenPluginEntity;

public interface IGunFile {
	
	/**
	 * 保存单个变更对象
	 */
	void initFile(GenPluginEntity gpe);

	/**
	 * 保存多个变更对象
	 */
	void initFile(List<GenPluginEntity> gpeList);

	boolean createTarFile();

	boolean createTarFile(String createTarFileUrl);

	boolean createTarFile(GenPluginEntity genPluginEntity);

	boolean createTarFile(GenPluginEntity genPluginEntity, String createTarFileUrl);
}
