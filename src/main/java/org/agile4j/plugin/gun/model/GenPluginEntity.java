package org.agile4j.plugin.gun.model;

import java.io.Serializable;

import org.agile4j.plugin.gun.annotation.TableName;

@TableName("GEN_PLUGIN")
public class GenPluginEntity implements Serializable{

	private static final long serialVersionUID = -6112897143338473464L;
	private String id;
	private String gunType;
	private String srcParent;
	private String srcField;
	private String tarParent;
	private String tarField;
	
	public GenPluginEntity(){}
	public GenPluginEntity(String gunType, String srcParent) {
		this.gunType = gunType ;
		this.srcParent = srcParent ;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGunType() {
		return gunType;
	}
	public void setGunType(String gunType) {
		this.gunType = gunType;
	}
	public String getSrcParent() {
		return srcParent;
	}
	public void setSrcParent(String srcParent) {
		this.srcParent = srcParent;
	}
	public String getSrcField() {
		return srcField;
	}
	public void setSrcField(String srcField) {
		this.srcField = srcField;
	}
	public String getTarParent() {
		return tarParent;
	}
	public void setTarParent(String tarParent) {
		this.tarParent = tarParent;
	}
	public String getTarField() {
		return tarField;
	}
	public void setTarField(String tarField) {
		this.tarField = tarField;
	}
	
}
