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
import com.wangfj.product.core.controller.support.PcmShoppeSupplierQueryPara;
import com.wangfj.product.supplier.domain.vo.PcmShoppeSupplierQueryDto;
import com.wangfj.product.supplier.domain.vo.PcmShoppeSupplierResultDto;
import com.wangfj.product.supplier.service.intf.IPcmSupplyShoppeService;

@Controller
@RequestMapping(value = "/shoppeSupplier", produces = "application/json;charset=utf-8")
public class PcmShoppeSupplierController extends BaseController {

	@Autowired
	private IPcmSupplyShoppeService supplyShoppeService;

	/**
	 * 根据(门店&&(专柜||供应商))查询专柜及供应商的信息
	 * 
	 * @Methods Name findShoppeSupplierInfoByParam
	 * @Create In 2015-12-16 By wangxuan
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findShoppeSupplierInfoByParam", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findShoppeSupplierInfoByParam(
			@RequestBody PcmShoppeSupplierQueryPara para) {

		PcmShoppeSupplierQueryDto dto = new PcmShoppeSupplierQueryDto();
		BeanUtils.copyProperties(para, dto);

		List<PcmShoppeSupplierResultDto> list = supplyShoppeService
				.findShoppeSupplierInfoByParam(dto);

		return ResultUtil.creComSucResult(list);
	}

}
