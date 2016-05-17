package test.com.wangfj.product.controller;

import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmChannelSaleConfigQueryPara;

public class TestPcmChannelSaleConfigController {

	@Test
	public void findListChannelSaleConfig() {

		PcmChannelSaleConfigQueryPara para = new PcmChannelSaleConfigQueryPara();

		// para.setShoppeProSid(2101101140L);
		para.setShoppeProSid(2101100098L);

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/channelSaleConfig/findListChannelSaleConfig.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		System.out.println(doPost);

	}

	@Test
	public void findListChannelSaleByShoppeProSid() {

		PcmChannelSaleConfigQueryPara para = new PcmChannelSaleConfigQueryPara();

		para.setShoppeProCode("10000024");

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/channelSaleConfig/findListChannelSaleByShoppeProSid.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		System.out.println(doPost);

	}
}
