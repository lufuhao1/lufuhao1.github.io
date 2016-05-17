package test.com.wangfj.product.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmRegionPara;
import com.wangfj.product.core.controller.support.PcmRegionQueryPara;

public class TestPcmRegionController {

	@Test
	public void findPageRegion() {

		// PcmRegionQueryPara para = new PcmRegionQueryPara();
		//
		// // para.setRegionName("北京");
		// para.setRegionLevel(0);

		Map<String, Object> para = new HashMap<String, Object>();
		para.put("fromSystem", "PCM");
		para.put("regionLevel", 0);
		para.put("regionName", "北京");

		String json = JsonUtil.getJSONString(para);
		String url = "http://127.0.0.1:8043/pcm-admin-sdc/region/findPageRegion.htm";
		String response = HttpUtil.doPost(url, json);

		System.out.println(response);

	}

	@Test
	public void findListRegion() {

		PcmRegionQueryPara para = new PcmRegionQueryPara();

		// para.setRegionName("市");
		para.setRegionName("省");
		// para.setRegionName("北京");
		para.setRegionLevel(0);

		String json = JsonUtil.getJSONString(para);
		String url = "http://127.0.0.1:8043/pcm-admin-sdc/region/findListRegion.htm";
		String response = HttpUtil.doPost(url, json);

		System.out.println(response);

	}

	@Test
	public void addRegion() {

		PcmRegionPara para = new PcmRegionPara();

		para.setFromSystem("PCM");
		para.setRegionCode("1283");
		para.setRegionName("旧金山");
		para.setParentId(0L);
		para.setRegionNameEn("San Francisco");
		para.setRegionShortnameEn("San Francisco");
		para.setRegionLevel(0);
		para.setRegionOrder(0);

		String json = JsonUtil.getJSONString(para);
		String url = "http://127.0.0.1:8043/pcm-admin-sdc/region/addRegion.htm";
		String response = HttpUtil.doPost(url, json);

		System.out.println(response);

	}

	@Test
	public void modifyRegion() {

		PcmRegionPara para = new PcmRegionPara();

		para.setFromSystem("PCM");
		para.setSid(1006L);
		para.setRegionCode("1283");
		para.setRegionName("旧金山1");
		para.setParentId(1L);
		para.setRegionNameEn("San Francisco");
		para.setRegionShortnameEn("San Francisco");
		para.setRegionLevel(0);
		para.setRegionOrder(0);

		String json = JsonUtil.getJSONString(para);
		String url = "http://127.0.0.1:8043/pcm-admin-sdc/region/modifyRegion.htm";
		String response = HttpUtil.doPost(url, json);

		System.out.println(response);

	}

}
