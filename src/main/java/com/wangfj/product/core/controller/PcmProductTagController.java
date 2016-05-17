package com.wangfj.product.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wangfj.product.core.controller.support.PcmShoppeProductQueryPara;
import com.wangfj.product.core.controller.support.PcmSkuQueryPara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.core.utils.StringUtils;
import com.wangfj.product.core.controller.support.LabelSkuPageQueryPara;
import com.wangfj.product.core.controller.support.ProductPagePara;
import com.wangfj.product.maindata.domain.vo.LabelSkuPageQueryDto;
import com.wangfj.product.maindata.domain.vo.ProductPageDto;
import com.wangfj.product.maindata.domain.vo.SkuPageDto;
import com.wangfj.product.maindata.service.intf.IPcmProductTagService;
import com.wangfj.util.Constants;

@Controller
@RequestMapping(value = "/productTag", produces = "application/json;charset=utf-8")
public class PcmProductTagController extends BaseController {

	@Autowired
	private IPcmProductTagService productTagService;

	/**
	 * 按条件分页查询SKU信息
	 * 
	 * @Methods Name selectBaseSkuPage
	 * @Create In 2015年8月12日 By zhangxy
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/selectBaseSkuPageByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectBaseSkuPageByPara(@RequestBody LabelSkuPageQueryPara para,
			HttpServletRequest request) {
		LabelSkuPageQueryDto pageDto = new LabelSkuPageQueryDto();
		org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
		if (StringUtils.isBlank(para.getSpuCode())) {
			pageDto.setSpuCode(null);
		}
		if (StringUtils.isBlank(para.getSkuCode())) {
			pageDto.setSkuCode(null);
		}
		if (StringUtils.isBlank(para.getSpuCode())) {
			pageDto.setSpuCode(null);
		}
		if (StringUtils.isBlank(para.getColorSid())) {
			pageDto.setColorSid(null);
		}
		if (StringUtils.isBlank(para.getColorName())) {
			pageDto.setColorName(null);
		}
		if (StringUtils.isBlank(para.getSpuSid())) {
			pageDto.setSpuSid(null);
		}
		if (StringUtils.isBlank(para.getModelCode())) {
			pageDto.setModelCode(null);
		}
		if (StringUtils.isBlank(para.getBrandGroupCode())) {
			pageDto.setBrandGroupCode(null);
		}
		if (StringUtils.isBlank(para.getSpuSale())) {
			pageDto.setSpuSale(null);
		}
		if (StringUtils.isBlank(para.getSkuSale())) {
			pageDto.setSkuSale(null);
		}
		if (StringUtils.isBlank(para.getProType())) {
			pageDto.setProType(null);
		}
		if (StringUtils.isBlank(para.getIndustryCondition())) {
			pageDto.setIndustryCondition(null);
		}
		if (para.getProActiveBit() != null) {
			pageDto.setProActiveBit(para.getProActiveBit());
		}
		Page<SkuPageDto> page = productTagService.selectSkuPage(pageDto);
		if (page.getList() != null && page.getList().size() > 0) {
			return ResultUtil.creComSucResult(page);
		} else {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		}
	}

	/**
	 * 按条件 分页 查询专柜商品基础信息列表
	 * 
	 * @Methods Name selectBaseProPageByPara
	 * @Create In 2015年8月4日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectBaseProPageByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectBaseProPageByPara(@RequestBody ProductPagePara para,
			HttpServletRequest request) {
		String isAddTag = para.getIsAddTag();
		String tagSid = para.getTagSid();
		ProductPageDto pageDto = new ProductPageDto();
		org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
		Page<ProductPageDto> page;
		try {
			page = productTagService.selectBaseProPageByPara(pageDto, isAddTag, tagSid);
			if (page.getList() != null && page.getList().size() > 0) {
				for (int i = 0; i < page.getList().size(); i++) {
					if ("WFJ".equals(page.getList().get(i).getField2())) {
						page.getList().get(i).setSupplierCode("WFJ");
						page.getList().get(i).setSupplierName("WFJ");
					}
					if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z1_STR.equals(page.getList().get(i)
							.getOperateMode())) {
						page.getList().get(i).setOperateMode("0");
					} else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z2_STR.equals(page.getList()
							.get(i).getOperateMode())) {
						page.getList().get(i).setOperateMode("1");
					} else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z3_STR.equals(page.getList()
							.get(i).getOperateMode())) {
						page.getList().get(i).setOperateMode("2");
					} else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z4_STR.equals(page.getList()
							.get(i).getOperateMode())) {
						page.getList().get(i).setOperateMode("3");
					} else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z5_STR.equals(page.getList()
							.get(i).getOperateMode())) {
						page.getList().get(i).setOperateMode("4");
					}
				}
				return ResultUtil.creComSucResult(page);
			} else {
				return ResultUtil.creComErrorResult(
						ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
						ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
			}
		} catch (Exception e) {
			return ResultUtil.creComErrorResult(null, e.getMessage());
		}
	}

	/**
	 * 批量导入专柜商品与促销标签的关系
	 *
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addShoppeProductTagList" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addShoppeProductTagList(@RequestBody PcmShoppeProductQueryPara para) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "productTag/addShoppeProductTagList.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 批量导入商品(SKU)与关键字的关系
	 *
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addSkuTagList" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String addSkuTagList(@RequestBody PcmSkuQueryPara para) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "productTag/addSkuTagList.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 活动标签挂专柜商品
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveProductTag" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String saveProductTag(@RequestBody Map<String, Object> param) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "productTag/saveProductTag.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(param));

		return response;
	}

	/**
	 * 删除活动标签挂专柜商品
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/deleteProductTag" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String deleteProductTag(@RequestBody Map<String, Object> param) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "productTag/deleteProductTag.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(param));

		return response;
	}
}
