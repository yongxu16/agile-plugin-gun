package org.agile4j.plugin.gun.xml.namesql;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;

public class ParseNamesqlUtil {

	private static final String XSD_PATH = "/org/agile4j/plugin/gun/xml/namesql/namesql.xsd";

	public static Sqls getSqlsConfig(String xmlFilePath) throws Exception {
		Sqls Sqls = null;
		InputStream is = null ;
		try {
			is = ParseNamesqlUtil.class.getResourceAsStream(XSD_PATH) ;
			JAXBContext context = JAXBContext.newInstance(Sqls.class);
			Unmarshaller shaller = context.createUnmarshaller();
			javax.xml.transform.Source schemaSource = new StreamSource(is);
			shaller.setSchema(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(schemaSource));
			JAXBElement<Sqls> root = shaller.unmarshal(new StreamSource(new File(xmlFilePath)), Sqls.class);
			Sqls = root.getValue();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return Sqls;
	}

}
