package org.agile4j.plugin.gun.model;

import java.io.Serializable;

public class SqlEntity implements Serializable {
	private static final long serialVersionUID = 291243602854460569L;
	
	private String core ;
	private String namedsql ;
	private String sqlsName ;
	private String selectId ;
	private String selectName ;
	private Integer count ;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getNamedsql() {
		return namedsql;
	}
	public void setNamedsql(String namedsql) {
		this.namedsql = namedsql;
	}
	public String getSqlsName() {
		return sqlsName;
	}
	public void setSqlsName(String sqlsName) {
		this.sqlsName = sqlsName;
	}
	public String getSelectId() {
		return selectId;
	}
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	@Override
	public String toString() {
		return "SqlEntity [core=" + core + ", namedsql=" + namedsql + ", sqlsName=" + sqlsName + ", selectId=" + selectId + ", selectName=" + selectName + "]";
	}
}
