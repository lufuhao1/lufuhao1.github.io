package com.wangfj.product.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.product.core.controller.support.SaveShoppeProductPara;
import com.wangfj.product.core.controller.support.SaveShoppeProductParaDs;
import com.wangfj.product.core.controller.support.SaveSkuPara;
import com.wangfj.product.maindata.service.intf.IValidProductService;
import com.wangfj.util.mq.PublishDTO;

/**
 * 添加商品
 * 
 * @Class Name SaveProductController
 * @Author zhangxy
 * @Create In 2015年8月24日
 */
@Controller
@RequestMapping("/saveProduct")
public class SaveProductController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SaveProductController.class);
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IValidProductService validProductService;

	/**
	 * 插入商品基本信息(SKU)
	 * 
	 * @Methods Name saveProduct
	 * @Create In 2015年8月24日 By zhangxy
	 * @param SaveSkuPara
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/saveProductSku", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String saveProductSku(@RequestBody @Valid SaveSkuPara data, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/saveProduct/saveProductSku.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(data));
		return response;
	}

	List<PublishDTO> sidList = null;

	/**
	 * 插入一条专柜商品
	 * 
	 * @Methods Name saveShoppeProduct
	 * @Create In 2015年8月24日 By zhangxy
	 * @param SaveShoppeProductPara
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/saveShoppeProduct", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String saveShoppeProduct(@RequestBody @Valid SaveShoppeProductPara data,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/saveProduct/saveShoppeProduct.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(data));
		return response;
	}

	/**
	 * 插入一条专柜商品
	 * 
	 * @Methods Name saveShoppeProductDs
	 * @Create In 2015年8月24日 By zhangxy
	 * @param SaveShoppeProductParaDs
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/saveShoppeProductDs", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String saveShoppeProductDs(@RequestBody @Valid SaveShoppeProductParaDs data,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/saveProduct/saveShoppeProductDs.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(data));
		return response;
	}
}
