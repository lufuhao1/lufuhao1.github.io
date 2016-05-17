package com.wangfj.product.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.maindata.domain.vo.ErpProPageDto;
import com.wangfj.product.maindata.domain.vo.ErpProductDto;
import com.wangfj.product.maindata.service.intf.IPcmErpProductService;

/**
 * ERP商品信息Controller——MQ
 * 
 * @Class Name ErpProductController
 * @Author zhangxy
 * @Create In 2015年7月17日
 */
@Controller
@RequestMapping("/erpProductPIS")
public class ErpProductController extends BaseController {

	@Autowired
	IPcmErpProductService erpProductService;

	/**
	 * 查询EPR商品信息
	 *
	 * @Methods Name findErpProductFromPcm
	 * @Create In 2015年7月17日 By zhangxy
	 * @param para
	 * @return String
	 */
	@RequestMapping(value = "/findErpProductFromPcm", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> findErpProductFromPcm(@RequestBody Map<String, Object> para) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank((String) para.get("storeCode"))) {
			paramMap.put("storeCode", para.get("storeCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("erpProductCode"))) {
			paramMap.put("productCode", para.get("erpProductCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("counterCode"))) {
			paramMap.put("shoppeCode", para.get("counterCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("supplierCode"))) {
			paramMap.put("supplyCode", para.get("supplierCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("erpSkuType"))) {
			paramMap.put("codeType", para.get("erpSkuType"));
		}
		if (StringUtils.isNotBlank((String) para.get("brandCode"))) {
			paramMap.put("groupBrandCode", para.get("brandCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("erpBrandCode"))) {
			paramMap.put("brandCode", para.get("erpBrandCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("productName"))) {
			paramMap.put("productName", para.get("productName"));
		}
		if (StringUtils.isNotBlank((String) para.get("productCategory"))) {
			paramMap.put("productCategory", para.get("productCategory"));
		}
		if (StringUtils.isNotBlank((String) para.get("productType"))) {
			paramMap.put("productType", para.get("productType"));
		}
		if (StringUtils.isNotBlank((String) para.get("specName"))) {
			paramMap.put("stanName", para.get("specName"));
		}
		if (StringUtils.isNotBlank((String) para.get("articleNum"))) {
			paramMap.put("articleNum", para.get("articleNum"));
		}
		if (StringUtils.isNotBlank((String) para.get("supplierBarcode"))) {
			paramMap.put("supplierBarcode", para.get("supplierBarcode"));
		}
		if (StringUtils.isNotBlank((String) para.get("status"))) {
			paramMap.put("proStatus", para.get("status"));
		}
		if (StringUtils.isNotBlank((String) para.get("isPromotion"))) {
			paramMap.put("isPromotion", para.get("isPromotion"));
		}
		if (StringUtils.isNotBlank((String) para.get("isAdjustPrice"))) {
			paramMap.put("isAdjustPrice", para.get("isAdjustPrice"));
		}
		if (StringUtils.isNotBlank((String) para.get("manageCategory"))) {
			paramMap.put("manageCategory", para.get("manageCategory"));
		}
		if (StringUtils.isNotBlank((String) para.get("discountLimit"))) {
			paramMap.put("discountLimit", para.get("discountLimit"));
		}
		if (StringUtils.isNotBlank((String) para.get("commissionRate"))) {
			paramMap.put("commissionRate", para.get("commissionRate"));
		}
		if (StringUtils.isNotBlank((String) para.get("originSalesUnit"))) {
			paramMap.put("salesUnit", para.get("originSalesUnit"));
		}
		if (StringUtils.isNotBlank((String) para.get("salePrice"))) {
			paramMap.put("salesPrice", para.get("salePrice"));
		}
		if (StringUtils.isNotBlank(para.get("currentPage").toString())) {
			paramMap.put("currentPage", para.get("currentPage"));
		}
		if (StringUtils.isNotBlank(para.get("pageSize").toString())) {
			paramMap.put("pageSize", para.get("pageSize"));
		}
		if (StringUtils.isNotBlank((String) para.get("start"))) {
			paramMap.put("start", para.get("start"));
		}
		if (StringUtils.isNotBlank((String) para.get("limit"))) {
			paramMap.put("limit", para.get("limit"));
		}
		Page<ErpProductDto> page = null;
		try {
			page = erpProductService.selectErpProduct(paramMap);
		} catch (Exception e) {
			ResultUtil.creComErrorResult("", e.toString());
		}
		return ResultUtil.creComSucResult(page);
	}

	/**
	 * 查询EPR商品信息
	 *
	 * @Methods Name findErpProductFromPcm
	 * @Create In 2015年7月17日 By zhangxy
	 * @param para
	 * @return String
	 */
	@RequestMapping(value = "/findErpProductFromPcmPage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> findErpProductFromPcmPage(@RequestBody Map<String, Object> para) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (para.get("cateList") != null) {
			paramMap.put("cateList", para.get("cateList"));
		}
		if (StringUtils.isNotBlank((String) para.get("storeCode"))) {
			paramMap.put("storeCode", para.get("storeCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("productCode"))) {
			paramMap.put("productCode", para.get("productCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("counterCode"))) {
			paramMap.put("shoppeCode", para.get("counterCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("supplierCode"))) {
			paramMap.put("supplyCode", para.get("supplierCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("erpSkuType"))) {
			paramMap.put("codeType", para.get("erpSkuType"));
		}
		if (StringUtils.isNotBlank((String) para.get("brandCode"))) {
			paramMap.put("groupBrandCode", para.get("brandCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("erpBrandCode"))) {
			paramMap.put("brandCode", para.get("erpBrandCode"));
		}
		if (StringUtils.isNotBlank((String) para.get("productName"))) {
			paramMap.put("productName", para.get("productName"));
		}
		if (StringUtils.isNotBlank((String) para.get("productCategory"))) {
			paramMap.put("productCategory", para.get("productCategory"));
		}
		if (StringUtils.isNotBlank((String) para.get("productType"))) {
			paramMap.put("productType", para.get("productType"));
		}
		if (StringUtils.isNotBlank((String) para.get("specName"))) {
			paramMap.put("stanName", para.get("specName"));
		}
		if (StringUtils.isNotBlank((String) para.get("articleNum"))) {
			paramMap.put("articleNum", para.get("articleNum"));
		}
		if (StringUtils.isNotBlank((String) para.get("supplierBarcode"))) {
			paramMap.put("supplierBarcode", para.get("supplierBarcode"));
		}
		if (StringUtils.isNotBlank((String) para.get("status"))) {
			paramMap.put("proStatus", para.get("status"));
		}
		if (StringUtils.isNotBlank((String) para.get("isPromotion"))) {
			paramMap.put("isPromotion", para.get("isPromotion"));
		}
		if (StringUtils.isNotBlank((String) para.get("isAdjustPrice"))) {
			paramMap.put("isAdjustPrice", para.get("isAdjustPrice"));
		}
		if (StringUtils.isNotBlank((String) para.get("manageCategory"))) {
			paramMap.put("manageCategory", para.get("manageCategory"));
		}
		if (StringUtils.isNotBlank((String) para.get("discountLimit"))) {
			paramMap.put("discountLimit", para.get("discountLimit"));
		}
		if (StringUtils.isNotBlank((String) para.get("commissionRate"))) {
			paramMap.put("commissionRate", para.get("commissionRate"));
		}
		if (StringUtils.isNotBlank((String) para.get("originSalesUnit"))) {
			paramMap.put("salesUnit", para.get("originSalesUnit"));
		}
		if (StringUtils.isNotBlank((String) para.get("salePrice"))) {
			paramMap.put("salesPrice", para.get("salePrice"));
		}
		if (StringUtils.isNotBlank(para.get("currentPage").toString())) {
			paramMap.put("currentPage", para.get("currentPage"));
		}
		if (StringUtils.isNotBlank(para.get("pageSize").toString())) {
			paramMap.put("pageSize", para.get("pageSize"));
		}
		if (StringUtils.isNotBlank((String) para.get("start"))) {
			paramMap.put("start", para.get("start"));
		}
		if (StringUtils.isNotBlank((String) para.get("limit"))) {
			paramMap.put("limit", para.get("limit"));
		}
		Page<ErpProPageDto> page = null;
		try {
			page = erpProductService.selectErpProductPage(paramMap);
		} catch (Exception e) {
			ResultUtil.creComErrorResult("", e.toString());
		}
		return ResultUtil.creComSucResult(page);
	}
}
