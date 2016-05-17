package com.wangfj.product.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.maindata.domain.entity.pcmPropackimgUrl;
import com.wangfj.product.maindata.service.intf.IPcmPropackimgUrlService;

@Controller
@RequestMapping(value = "/proPackimgUrl", produces = "application/json;charset=utf-8")
public class PcmPropackimgUrlController {
	
	@Autowired
	private IPcmPropackimgUrlService propPackimgUrlService;
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/savePackimgUrl" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String saveProductTag(@RequestBody Map<String, Object> param) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "proPackimgUrl/savePackimgUrl.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(param));

		return response;
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getAllListBySpuAndColor" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getAllListBySpuAndColor(@RequestBody Map<String, Object> param) {

		pcmPropackimgUrl pack = new pcmPropackimgUrl();
		if(param.get("spuCode") != null){
			pack.setSkuSid(param.get("spuCode").toString());
		}
		if(param.get("colorSid") != null){
			pack.setColorCode(param.get("colorSid").toString());
		}
		List<pcmPropackimgUrl> list = propPackimgUrlService.getAllListBySpuAndColor(pack);

		return ResultUtil.creComSucResult(list);
	}
}
