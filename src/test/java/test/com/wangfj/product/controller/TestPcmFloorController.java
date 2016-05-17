package test.com.wangfj.product.controller;

import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.SelectPcmFloorPara;

/**
 * 楼层测试
 * 
 * @Class Name TestPcmFloorController
 * @Author wangxuan
 * @Create In 2015-8-25
 */
public class TestPcmFloorController {

	/**
	 * 分页查询
	 * 
	 * @Methods Name findPageFloor
	 * @Create In 2015-8-25 By wangxuan void
	 */
	@Test
	public void findPageFloor() {

		SelectPcmFloorPara floorPara = new SelectPcmFloorPara();

		floorPara.setCurrentPage(1);
		floorPara.setPageSize(10);

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin/floor/findPageFloor.htm",
				JsonUtil.getJSONString(floorPara));
		System.out.println(response);

	}

}
