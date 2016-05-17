package com.wangfj.product.core.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmChangePriceLimitPara;
import com.wangfj.product.core.controller.support.PcmPriceLimitPara;
import com.wangfj.product.price.domain.vo.PcmChangePriceLimitDto;
import com.wangfj.product.price.service.intf.IPcmChangePriceLimitService;

@Controller
@RequestMapping(value = "/priceLimit", produces = "application/json;charset=utf-8")
public class PcmPriceLimitController extends BaseController {

	@Autowired
	private IPcmChangePriceLimitService pcmChangePriceLimitService;

	@ResponseBody
	@RequestMapping(value = { "/findPagePriceLimit" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> findPriceLimit(@RequestBody @Valid PcmChangePriceLimitPara para,
			HttpServletRequest request) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("currentPage", para.getCurrentPage());
		paramMap.put("pageSize", para.getPageSize());
		paramMap.put("shopCode", para.getShopCode());

		Page<PcmChangePriceLimitDto> pageOrg = pcmChangePriceLimitService.selectPageList(paramMap);

		return ResultUtil.creComSucResult(pageOrg);
	}

	/**
	 * 查询所有已经添加门店阀值的门店sid
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findAllShopSidFromPriceLimit" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String findAllShopSidFromPriceLimit() {

		String shopsSid = pcmChangePriceLimitService.selectAllShopSidFromPriceLimit();

		return "{\"shopsSid\" : \"" + shopsSid + "\"}";
	}

	/**
	 * 增加/修改门店阀值信息
	 * 
	 * @Methods Name uploadOrganizationByParamFromErp
	 * @Create In 2015年7月28日 By wuxiong
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdatePriceLimit", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String saveOrUpdatePriceLimit(@RequestBody @Valid PcmPriceLimitPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "priceLimit/saveOrUpdatePriceLimit.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));

		return response;
	}
}
