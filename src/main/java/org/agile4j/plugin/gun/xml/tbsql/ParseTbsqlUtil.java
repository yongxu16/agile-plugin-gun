package org.agile4j.plugin.gun.xml.tbsql;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;

public class ParseTbsqlUtil {

	private static final String XSD_PATH = "/org/agile4j/plugin/gun/xml/tbsql/tbsql.xsd";

	public static Schema getTbsConfig(String xmlFilePath) throws Exception {
		Schema tbs = null;
		InputStream is = null ;
		try {
			is = ParseTbsqlUtil.class.getResourceAsStream(XSD_PATH) ;
			JAXBContext context = JAXBContext.newInstance(Schema.class);
			Unmarshaller shaller = context.createUnmarshaller();
			javax.xml.transform.Source schemaSource = new StreamSource(is);
			shaller.setSchema(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(schemaSource));
			JAXBElement<Schema> root = shaller.unmarshal(new StreamSource(new File(xmlFilePath)), Schema.class);
			tbs = root.getValue();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return tbs;
	}

}
