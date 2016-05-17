package test.com.wangfj.product.controller;

import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmShoppeSupplierQueryPara;

public class TestPcmShoppeSupplierController {

	@Test
	public void test() {

	}

	@Test
	public void findShoppeSupplierInfoByParam() {

		PcmShoppeSupplierQueryPara para = new PcmShoppeSupplierQueryPara();
		para.setShopCode("21011");
		para.setShoppeCode("2101100089");

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/shoppeSupplier/findShoppeSupplierInfoByParam.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		System.out.println(doPost);

	}

}
