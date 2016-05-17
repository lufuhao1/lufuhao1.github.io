package test.com.wangfj.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmDictPara;

public class TestPcmDictController {

	@Test
	public void test() {

	}

	@Test
	public void saveDictInfo() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("code", "1234");
		paraMap.put("name", "变动");
		paraMap.put("pid", 1L);

		// PcmDictPara para = new PcmDictPara();
		// para.setCode("123");
		// para.setName("变动");
		// para.setPid(1L);

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/dict/saveDictInfo.htm";
		// String url = "http://localhost:8083/pcm-admin/dict/saveDictInfo.htm";
		String jsonString = JsonUtil.getJSONString(paraMap);
		System.out.println("para:" + jsonString);
		String response = HttpUtil.doPost(url, jsonString);
		System.out.println("response:" + response);

	}
}
