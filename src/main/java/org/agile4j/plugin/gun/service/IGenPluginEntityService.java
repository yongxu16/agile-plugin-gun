package org.agile4j.plugin.gun.service;

import java.util.List;

import org.agile4j.plugin.gun.model.GenPluginEntity;

/**
 * IGenPluginEntity 接口
 * 
 * @author hanyx
 * @since
 */
public interface IGenPluginEntityService {

	List<GenPluginEntity> getGenPluginEntityListByType(String type);

	List<GenPluginEntity> getGenPluginEntityListByCondition(GenPluginEntity vo);

	GenPluginEntity getGenPluginEntityByUniq(GenPluginEntity vo) ;

	boolean updateOrCreateGenPluginEntity(GenPluginEntity vo);

	boolean insertGenPluginEntity(GenPluginEntity genPluginEntity);

	boolean deleteGenPluginEntity(GenPluginEntity genPluginEntity);

	boolean batcheInsertGenPluginEntity(List<GenPluginEntity> genPluginEntityList);

	boolean batcheDelGenPluginEntity(List<GenPluginEntity> genPluginEntityList);

	boolean batcheUpdateOrCreateGenPluginEntity(List<GenPluginEntity> gpeList);

	GenPluginEntity getGenPluginEntity(String id);

	GenPluginEntity getGenPluginEntityBySrcField(String srcField);

	GenPluginEntity updateGenPluginEntity(GenPluginEntity vo);

	boolean delteGenPluginEntityBySrcParent(String srcParent);

	boolean delteGenPluginEntityBySrcParentList(List<String> srcParentList);
}
