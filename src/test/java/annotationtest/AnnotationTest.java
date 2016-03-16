package annotationtest;

import org.agile4j.plugin.gun.annotation.TableName;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationTest {

	private static final Logger logger = LoggerFactory.getLogger(AnnotationTest.class) ;
	
	@Test
	public void TestTable() throws Exception{
		Class<?> clazz = Class.forName("org.agile4j.plugin.gun.model.GenPluginEntity") ;
		
		TableName tableName = clazz.getAnnotation(TableName.class) ;
		String tableAnnotion = tableName.value() ;
		
		logger.debug(tableAnnotion);
	}
}
