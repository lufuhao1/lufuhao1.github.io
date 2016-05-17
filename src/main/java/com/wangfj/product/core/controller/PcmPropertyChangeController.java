package com.wangfj.product.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.product.maindata.service.intf.IPcmPropertyChangeService;

@Controller
@RequestMapping("/propertyChange")
public class PcmPropertyChangeController {

	@Autowired
	IPcmPropertyChangeService propertyService;

	String pcm_admin = PropertyUtil.getSystemUrl("pcm-admin");

	@ResponseBody
	@RequestMapping(value = "/changeGroupShoppe", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String changeGroupShoppe(@RequestBody Map<String, Object> paramMap,
			HttpServletRequest request) {
		String response = HttpUtil.doPost(pcm_admin + "/propertyChange/changeGroupShoppe.htm",
				JsonUtil.getJSONString(paramMap));
		return response;
	}

	@RequestMapping(value = "/changeGroupCategory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String changeGroupCategory(@RequestBody Map<String, Object> paramMap,
			HttpServletRequest request) {
		String response = HttpUtil.doPost(pcm_admin + "/propertyChange/changeGroupCategory.htm",
				JsonUtil.getJSONString(paramMap));
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/changeGroupBrands", produces = "application/json;charset=utf-8")
	public String changeGroupBrands(@RequestBody Map<String, Object> paramMap,
			HttpServletRequest request) throws Exception {
		String response = HttpUtil.doPost(pcm_admin + "/propertyChange/changeGroupBrands.htm",
				JsonUtil.getJSONString(paramMap));
		return response;
	}
}
