package beanutilstest;

import java.util.Map;

import org.agile4j.plugin.gun.model.GenPluginEntity;
import org.agle4j.framework.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class BeanUtilsTest {

	@Test
	public void testBeanUtilse() throws Exception {
		GenPluginEntity vo = new GenPluginEntity() ;
		vo.setId(StringUtil.getUUID());
		vo.setSrcParent("aaa");
		vo.setSrcField("bbb");
		Map map = BeanUtils.describe(vo) ;
		System.out.println(JSON.toJSONString(map)); ;
	}
}
