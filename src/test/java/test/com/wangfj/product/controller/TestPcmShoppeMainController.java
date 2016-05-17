package test.com.wangfj.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmShoppeAUPara;
import com.wangfj.product.core.controller.support.PcmShoppeQueryPara;
import com.wangfj.product.core.controller.support.SelectPcmShoppePara;
import com.wangfj.product.organization.domain.vo.PcmShoppeAUDto;

public class TestPcmShoppeMainController {

	@Test
	public void copy() {

		List<PcmShoppeAUPara> paraList = new ArrayList<PcmShoppeAUPara>();
		PcmShoppeAUPara para = new PcmShoppeAUPara();
		para.setGroupSid(303L);
		para.setShopSid(306L);

		para.setShoppeName("按集团分专柜测试");

		paraList.add(para);

		for (int i = 0; i < paraList.size(); i++) {
			PcmShoppeAUDto dto = new PcmShoppeAUDto();
			BeanUtils.copyProperties(para, dto);
			System.out.println(dto);
		}

		PcmShoppeAUDto dto = new PcmShoppeAUDto();
		BeanUtils.copyProperties("{groupSid=303, shopSid=306, shoppeName=按集团分专柜测试}", dto);
		System.out.println(dto);

	}

	@Test
	public void addShoppe() {

		List<PcmShoppeAUPara> paraList = new ArrayList<PcmShoppeAUPara>();
		PcmShoppeAUPara para = new PcmShoppeAUPara();
		para.setGroupSid(303L);
		para.setShopSid(306L);

		para.setShoppeName("按集团分专柜测试");
		para.setFromSystem("PCM");

		paraList.add(para);

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/shoppe/addShoppe.htm";
		// String url = "http://127.0.0.1:8083/pcm-admin/shoppe/addShoppe.htm";
		// String url =
		// "http://127.0.0.1:8083/pcm-admin/shoppe/addShoppeList.htm";
		// String response = HttpUtil.doPost(url,
		// JsonUtil.getJSONString(paraList));
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		System.out.println(response);

	}

	@Test
	public void findPageShoppe() {

		SelectPcmShoppePara para = new SelectPcmShoppePara();

		// para.setShoppeName("鸿星尔克");

		// para.setShoppeName("天津化装欧莱雅专柜");
		String response = HttpUtil.doPost(
				"http://127.0.0.1:8043/pcm-admin-sdc/shoppe/findPageShoppe.htm",
				JsonUtil.getJSONString(para));
		System.out.println(response);

	}

	@Test
	public void findListShoppe() {

		SelectPcmShoppePara para = new SelectPcmShoppePara();

		// para.setShoppeName("宝姿");
		// para.setShopSid("21");

		// para.setShoppeName("天津化装欧莱雅专柜");
		String response = HttpUtil.doPost(
				"http://127.0.0.1:8043/pcm-admin-sdc/shoppe/findListShoppe.htm",
				JsonUtil.getJSONString(para));
		System.out.println(response);

	}

	@Test
	public void findListShoppeForAddShoppeProduct() {

		PcmShoppeQueryPara para = new PcmShoppeQueryPara();

		para.setShopSid(21L);
		para.setSupplySid("42");

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/shoppe/findListShoppeForAddShoppeProduct.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		System.out.println(response);

	}

}
