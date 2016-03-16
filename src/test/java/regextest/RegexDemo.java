package regextest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class RegexDemo {

	private static final Logger LOGGER = LogManager.getLogger(RegexDemo.class) ;
	
	@Test
	public void testRegex() {
		String str = "as12" ;
		String regex1 = "^a\\w*" ;
		String regex2 = "!a" ;
		String regex3 = "!([^]*a[^]*)" ;
		String regex4 = "!([^]*a[^]*)" ;
		LOGGER.debug(str.matches(regex1));
	}
}
