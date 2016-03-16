package org.agile4j.plugin.gun.xml.namesql;

import java.io.Serializable;

public class DemoSqls implements Serializable {
	
	private static final long serialVersionUID = 4035338836248052440L;
	private Sqls sql ;
	private String fileName; 
	private String core ;
	public Sqls getSql() {
		return sql;
	}
	public void setSql(Sqls sql) {
		this.sql = sql;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	@Override
	public String toString() {
		return "DemoSqls [sql=" + sql + ", fileName=" + fileName + ", core=" + core + "]";
	}
}
