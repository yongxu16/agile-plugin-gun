package jaxbtest;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.agile4j.plugin.gun.xml.tbsql.ParseTbsqlUtil;
import org.agile4j.plugin.gun.xml.tbsql.Schema;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class JaxbTest {

	private static final String SRC_XML_PATH = "E:\\gun_plugin_test\\GlAudit.tables.xml";
	private static final String TAR_XML_PATH = "E:\\gun_plugin_test\\Marshaller_GlAudit.tables.xml";

	@Test
	public void beanToXML() throws Exception {
		Schema schema = ParseTbsqlUtil.getTbsConfig(SRC_XML_PATH);
		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint() ;
			outputFormat.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileOutputStream(TAR_XML_PATH), outputFormat) ;
			
			JAXBContext context = JAXBContext.newInstance(Schema.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(schema, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
