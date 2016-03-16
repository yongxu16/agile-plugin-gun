package fileutils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void testFileUtils() {
		// File file1 = new File(".\\aaa\\test.txt");
		File file1 = new File("D:\\aaa\\test.txt");
		String str1 = file1.getParent();
		String str2 = file1.getAbsolutePath();
		String str3 = file1.getName();
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(str3);
	}

	@Test
	public void testFileWrite() throws Exception {
		File file1 = new File("D:\\aaa\\test.txt");
		FileUtils.writeStringToFile(file1, "ABCDEFG", "UTF-8", true);
	}
}
