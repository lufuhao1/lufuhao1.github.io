package com.wangfj.product.core.controller;

import java.util.List;
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
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmProDetailPara;
import com.wangfj.product.maindata.domain.entity.PcmProDetail;
import com.wangfj.product.maindata.service.intf.IPcmProDetailService;

@Controller
@RequestMapping(value = "/proDetail")
public class PcmProDetailController {

	@Autowired
	private IPcmProDetailService skuService;

	String pcm_admin = PropertyUtil.getSystemUrl("pcm-admin");

	@ResponseBody
	@RequestMapping(value = "/selectSkuItemByCode", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public Map<String, Object> selectSkuItemByCode(@RequestBody Map<String, Object> paramMap) {
		List<PcmProDetail> list = skuService.selectSkuItemByCode(paramMap);
		return ResultUtil.creComSucResult(list);
	}

	/**
	 * SKU换色码（特性）/尺寸码（规格）规则说明
	 * 
	 * @Methods Name updateSkuColorStan
	 * @Create In 2015年9月9日 By yedong
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/insertOrUpdateSku", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String insertOrUpdateSku(@RequestBody List<PcmProDetailPara> paraList,
			HttpServletRequest request) {
		String response = HttpUtil.doPost(pcm_admin + "proDetail/insertOrUpdateSku.htm",
				JsonUtil.getJSONString(paraList));
		return response;
	}

	/**
	 * SKU换色码（特性）/尺寸码（规格）
	 * 
	 * @Methods Name insertSkuInfo
	 * @Create In 2015年11月17日 By yedong
	 * @param paraList
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/insertSkuInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String insertSkuInfo(@RequestBody List<PcmProDetailPara> paraList,
			HttpServletRequest request) {
		String response = HttpUtil.doPost(pcm_admin + "proDetail/insertSkuInfo.htm",
				JsonUtil.getJSONString(paraList));
		return response;

	}

	/**
	 * 根据SKUSID修改SKU信息
	 * 
	 * @Methods Name updateSkuInfoBySid
	 * @Create In 2015年11月25日 By yedong
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSkuInfoBySid", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String updateSkuInfoBySid(@RequestBody PcmProDetailPara para, HttpServletRequest request) {
		String response = HttpUtil.doPost(pcm_admin + "proDetail/updateSkuInfoBySid.htm",
				JsonUtil.getJSONString(para));
		return response;
	}
}
