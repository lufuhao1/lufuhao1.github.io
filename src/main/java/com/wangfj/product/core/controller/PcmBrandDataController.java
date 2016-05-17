package com.wangfj.product.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.brand.domain.vo.PcmBrandCateDto;
import com.wangfj.product.brand.service.intf.IPcmBrandCategoryService;
import com.wangfj.product.brand.service.intf.IPcmBrandInnerService;
import com.wangfj.product.core.controller.support.PcmBrandCatePara;

/**
 * 商品导入终端--由主数据获取品牌信息
 * 
 * @Class Name PcmBrandsController
 * @Author wangx
 * @Create In 2015-8-10
 */
@Controller
@RequestMapping(value = "/pcmInnerBrandData")
public class PcmBrandDataController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PcmBrandDataController.class);

	@Autowired
	private IPcmBrandInnerService brandInnerService;

	@Autowired
	private IPcmBrandCategoryService brandCateService;

	String pcm_admin = PropertyUtil.getSystemUrl("pcm-admin");

	@ResponseBody
	@RequestMapping(value = "/getBrandCateInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> getBrandCateInfo(@RequestBody PcmBrandCatePara para) {
		PcmBrandCateDto dto = new PcmBrandCateDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> brandCateInfoList = brandCateService.getBrandCateInfo(dto);
		return ResultUtil.creComSucResult(brandCateInfoList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addBrandCateInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String addBrandCateInfo(@RequestBody @Valid List<PcmBrandCatePara> paraList) {
		String response = HttpUtil.doPost(pcm_admin + "pcmInnerBrandData/addBrandCateInfo.htm",
				JsonUtil.getJSONString(paraList));
		return response;
	}
}
