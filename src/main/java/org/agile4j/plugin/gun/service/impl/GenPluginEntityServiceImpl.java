package org.agile4j.plugin.gun.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agile4j.plugin.gun.service.IGenPluginEntityService;
import org.agle4j.framework.helper.DatabaseHelper;
import org.agle4j.framework.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码生成服务类
 * 
 * @author hanyx
 * @since
 */
public class GenPluginEntityServiceImpl implements IGenPluginEntityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenPluginEntityServiceImpl.class);
	private static final String SEL_SQL = "SELECT ID, GUN_TYPE AS gunType, SRC_PARENT AS srcParent, SRC_FIELD AS srcField, TAR_PARENT AS tarParent, TAR_FIELD AS tarField FROM GEN_PLUGIN " ;
	
	@Override
	public List<GenPluginEntity> getGenPluginEntityListByType(String type) {
		String sql = SEL_SQL + "WHERE GUN_TYPE = ? ";
		List<GenPluginEntity> list = DatabaseHelper.queryEntityList(GenPluginEntity.class, sql, type);
		return list;
	}
	
	@Override
	public List<GenPluginEntity> getGenPluginEntityListByCondition(GenPluginEntity vo) {
		List<Object> objList = new ArrayList<>() ;
		String sql = SEL_SQL + "WHERE 1=1 ";
		if (StringUtils.isNotEmpty(vo.getGunType())) {
			sql = sql + "AND GUN_TYPE = ? " ;
			objList.add(vo.getGunType()) ;
		}
		if (StringUtils.isNotEmpty(vo.getSrcParent())) {
			sql = sql + "AND SRC_PARENT = ? " ;
			objList.add(vo.getSrcParent()) ;
		}
		if (StringUtils.isNotEmpty(vo.getSrcField())) {
			sql = sql + "AND SRC_FIELD = ? " ;
			objList.add(vo.getSrcField()) ;
		}
		List<GenPluginEntity> list = DatabaseHelper.queryEntityList(GenPluginEntity.class, sql, objList.toArray());
		return list;
	}
	
	@Override
	public GenPluginEntity getGenPluginEntityByUniq(GenPluginEntity vo) {
		String sql = SEL_SQL + "WHERE GUN_TYPE = ? AND SRC_PARENT = ? AND SRC_FIELD = ? ";
		vo = DatabaseHelper.queryEntity(GenPluginEntity.class, sql, vo.getGunType(), vo.getSrcParent(),vo.getSrcField());
		return vo;
	}
	
	@Override
	public boolean batcheUpdateOrCreateGenPluginEntity(List<GenPluginEntity> gpeList) {
		if (CollectionUtils.isNotEmpty(gpeList)) {
			for (GenPluginEntity vo : gpeList) {
				return updateOrCreateGenPluginEntity(vo) ;
			}
		}
		return false;
	}
	
	@Override
	public boolean updateOrCreateGenPluginEntity(GenPluginEntity vo) {
		GenPluginEntity genPluginEntity = getGenPluginEntityByUniq(vo) ;
		if (genPluginEntity != null) {
			vo.setId(genPluginEntity.getId());
			return updateGenPluginEntity(vo) != null ;
		} else {
			return insertGenPluginEntity(vo) ;
		}
	}
	
	@Override
	public boolean insertGenPluginEntity(GenPluginEntity genPluginEntity) {
		genPluginEntity.setId(StringUtils.isNotEmpty(genPluginEntity.getId()) ? genPluginEntity.getId() : StringUtil.getUUID());
		Map<String, Object> fieldMap = parse2Map(genPluginEntity);
		return DatabaseHelper.insertEntity(GenPluginEntity.class, fieldMap);
	}

	@Override
	public boolean deleteGenPluginEntity(GenPluginEntity genPluginEntity) {
		return DatabaseHelper.deleteEntity(GenPluginEntity.class, genPluginEntity.getId());
	}

	@Override
	public boolean batcheInsertGenPluginEntity(List<GenPluginEntity> genPluginEntityList) {
		if (CollectionUtils.isNotEmpty(genPluginEntityList)) {
			Set<String> set = new HashSet<>();
			for (GenPluginEntity vo : genPluginEntityList) {
				if (StringUtils.isNotEmpty(vo.getSrcParent())) {
					set.add(vo.getSrcParent());
				}
			}
			delteGenPluginEntityBySrcParentList(new ArrayList<>(set)) ;
			for (GenPluginEntity vo : genPluginEntityList) {
				if (!insertGenPluginEntity(vo)) {
					return false;
				}
			}
		} 
		return true;
	}

	@Override
	public boolean batcheDelGenPluginEntity(List<GenPluginEntity> genPluginEntityList) {
		for (GenPluginEntity vo : genPluginEntityList) {
			if (!deleteGenPluginEntity(vo)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public GenPluginEntity getGenPluginEntity(String id) {
		String sql = SEL_SQL + "WHERE ID = ? ";
		GenPluginEntity vo = DatabaseHelper.queryEntity(GenPluginEntity.class, sql, id);
		return vo;
	}

	@Override
	public GenPluginEntity getGenPluginEntityBySrcField(String srcField) {
		String sql = SEL_SQL + "WHERE SRC_FIELD = ? ";
		GenPluginEntity vo = DatabaseHelper.queryEntity(GenPluginEntity.class, sql, srcField);
		return vo;
	}

	@Override
	public GenPluginEntity updateGenPluginEntity(GenPluginEntity vo) {
		Map<String, Object> fieldMap = parse2Map(vo);
		DatabaseHelper.updateEntity(GenPluginEntity.class, vo.getId(), fieldMap);
		return vo ;
	}

	private Map<String, Object> parse2Map(GenPluginEntity genPluginEntity) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotEmpty(genPluginEntity.getId())) {
			map.put("id", genPluginEntity.getId());
		}
		if (StringUtils.isNotEmpty(genPluginEntity.getGunType())) {
			map.put("gun_type", genPluginEntity.getGunType());
		}
		if (StringUtils.isNotEmpty(genPluginEntity.getSrcParent())) {
			map.put("src_parent", genPluginEntity.getSrcParent());
		}
		if (StringUtils.isNotEmpty(genPluginEntity.getSrcField())) {
			map.put("src_field", genPluginEntity.getSrcField());
		}
		if (StringUtils.isNotEmpty(genPluginEntity.getTarParent())) {
			map.put("tar_parent", genPluginEntity.getTarParent());
		}
		if (StringUtils.isNotEmpty(genPluginEntity.getTarField())) {
			map.put("tar_field", genPluginEntity.getTarField());
		}
		return map;
	}

	@Override
	public boolean delteGenPluginEntityBySrcParent(String srcParent) {
		String sql = "DELETE FROM GEN_PLUGIN WHERE SRC_PARENT=? ";
		return DatabaseHelper.executeUpdate(sql, srcParent) == 1;
	}

	@Override
	public boolean delteGenPluginEntityBySrcParentList(List<String> srcParentList) {
		if (CollectionUtils.isNotEmpty(srcParentList)) {
			for (String srcParent : srcParentList) {
				delteGenPluginEntityBySrcParent(srcParent);
			}
		}
		return true;
	}

}
