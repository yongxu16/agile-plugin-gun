package org.agile4j.plugin.gun.xml.servicetype;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;

public class ParseServiceTypeUtil {

	private static final String XSD_PATH = "/org/agile4j/plugin/gun/xml/servicetype/servicetype.xsd";
	
	public static ServiceType getServcieTypeConfig(String xmlFilePath) throws Exception {
		ServiceType serviceType = null;
		InputStream is = null ;
		try {
			is = ParseServiceTypeUtil.class.getResourceAsStream(XSD_PATH) ;
			JAXBContext context = JAXBContext.newInstance(ServiceType.class);
			Unmarshaller shaller = context.createUnmarshaller();
			javax.xml.transform.Source schemaSource = new StreamSource(is);
			shaller.setSchema(SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema").newSchema(schemaSource));
			JAXBElement<ServiceType> root = shaller.unmarshal(new StreamSource(new File(xmlFilePath)), ServiceType.class);
			serviceType = root.getValue();
		} catch (Exception e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return serviceType;
	}

}
