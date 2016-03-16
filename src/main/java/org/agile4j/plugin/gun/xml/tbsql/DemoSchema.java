package org.agile4j.plugin.gun.xml.tbsql;

import java.io.Serializable;

public class DemoSchema implements Serializable {

	private static final long serialVersionUID = 6438499632219571504L;
	
	private Schema schema ;
	private String core ;
	private String fileName ;
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
