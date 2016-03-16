package org.agile4j.plugin.gun.model;

import java.io.Serializable;

public class TbEntity implements Serializable{

	private static final long serialVersionUID = 6108004642328789079L;
	
	private String core ;
	private String schema ;
	private String schemaName; 
	private String tableId ;
	private String tableName ;
	private String field ;
	private Integer count ;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
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
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public String toString() {
		return "TbEntity [core=" + core + ", schema=" + schema + ", schemaName=" + schemaName + ", tableId=" + tableId + ", tableName=" + tableName + "]";
	}
	
}
