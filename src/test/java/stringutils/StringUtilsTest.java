package stringutils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testString() {
		String src = "hello world java hello world java" ;
		String tar = StringUtils.replaceEach(src, new String[]{"worl","world"}, new String[]{"123","hanyx"}) ;
		System.out.println(tar);
		
	}
}
