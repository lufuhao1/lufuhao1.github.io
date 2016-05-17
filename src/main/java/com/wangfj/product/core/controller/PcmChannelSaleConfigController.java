package com.wangfj.product.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmChannelSaleConfigQueryPara;
import com.wangfj.product.organization.domain.vo.PcmChannelSaleConfigQueryDto;
import com.wangfj.product.organization.domain.vo.PcmChannelSaleConfigResultDto;
import com.wangfj.product.organization.service.intf.IPcmChannelSaleConfigService;

@Controller
@RequestMapping(value = { "/channelSaleConfig" }, produces = "application/json;charset=utf-8")
public class PcmChannelSaleConfigController extends BaseController {

	@Autowired
	private IPcmChannelSaleConfigService channelSaleConfigService;

	/**
	 * 根据专柜sid查询渠道可售
	 * 
	 * @Methods Name findListChannelSaleConfig
	 * @Create In 2015-11-25 By wangxuan
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findListChannelSaleConfig", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findListChannelSaleConfig(
			@RequestBody PcmChannelSaleConfigQueryPara para) {

		PcmChannelSaleConfigQueryDto dto = new PcmChannelSaleConfigQueryDto();

		BeanUtils.copyProperties(para, dto);

		List<PcmChannelSaleConfigResultDto> list = channelSaleConfigService
				.findListByShoppeParam(dto);

		return ResultUtil.creComSucResult(list);
	}

	/**
	 * 根据专柜商品编码查询渠道可售
	 * 
	 * @Methods Name findListChannelSaleByShoppeProSid
	 * @Create In 2015-11-25 By wangxuan
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findListChannelSaleByShoppeProSid")
	@ResponseBody
	public Map<String, Object> findListChannelSaleByShoppeProSid(
			@RequestBody PcmChannelSaleConfigQueryPara para) {

		PcmChannelSaleConfigQueryDto dto = new PcmChannelSaleConfigQueryDto();

		BeanUtils.copyProperties(para, dto);

		List<PcmChannelSaleConfigResultDto> list = channelSaleConfigService
				.findListByShoppeProParam(dto);

		return ResultUtil.creComSucResult(list);

	}

}
