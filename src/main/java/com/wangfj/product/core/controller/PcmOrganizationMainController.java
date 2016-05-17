package com.wangfj.product.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
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
import com.wangfj.product.core.controller.support.PcmOrgPara;
import com.wangfj.product.core.controller.support.PcmOrganizaPara;
import com.wangfj.product.core.controller.support.PcmOrganizationPara;
import com.wangfj.product.core.controller.support.SelectPcmOrganizationPara;
import com.wangfj.product.organization.domain.entity.PcmOrganization;
import com.wangfj.product.organization.domain.vo.PcmOrgPartInfoDto;
import com.wangfj.product.organization.domain.vo.PcmOrganizationDto;
import com.wangfj.product.organization.domain.vo.PcmOrganizationResultDto;
import com.wangfj.product.organization.domain.vo.PublishOrganizationDto;
import com.wangfj.product.organization.domain.vo.SelectPcmOrganizationDto;
import com.wangfj.product.organization.service.intf.IPcmOrganizationService;
import com.wangfj.util.Constants;

/**
 * 基础组织机构信息管理 - MQ
 * 
 * @Class Name PcmOrganizationMainController
 * @Author wuxiong
 * @Create In 2015年7月16日
 */
@Controller
@RequestMapping(value = "/organization", produces = "application/json;charset=utf-8")
public class PcmOrganizationMainController extends BaseController {

	@Autowired
	private IPcmOrganizationService pcmOrganizationService;

	/**
	 * 查询组织机构信息
	 * 
	 * @Methods Name findOrganizationByParam
	 * @Create In 2015年7月28日 By wuxiong
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping("/findOrganizationByParam")
	@ResponseBody
	public Map<String, Object> findOrganizationByParam(@RequestBody PcmOrganizationPara para,
			HttpServletRequest request) {
		PcmOrganizationDto dto = new PcmOrganizationDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		List<PcmOrganization> list = pcmOrganizationService.selectListByParamOrg(dto);
		Integer count = pcmOrganizationService.getCountByParamOrg(dto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		return ResultUtil.creComSucResult(map);
	}

	/**
	 * 查询所有门店
	 * 
	 * @Methods Name findOrganizationByType
	 * @Create In 2015年8月18日 By duanzhaole
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findOrganizationByType", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findOrganizationByType(PcmOrganizationDto para, HttpServletRequest request) {
		// PcmOrganizationDto dto = new PcmOrganizationDto();
		// org.springframework.beans.BeanUtils.copyProperties(para, dto);
		para.setOrganizationType(Constants.PUBLIC_3);
		List<PcmOrganization> list = pcmOrganizationService.selectListByParamOrg(para);
		// Integer count = pcmOrganizationService.getCountByParamOrg(dto);
		JSONArray jsons = new JSONArray();
		for (PcmOrganization organ : list) {
			JSONObject jsonobj = JSONObject.fromObject(organ);
			jsons.add(jsonobj);
		}
		return jsons.toString();
	}

	/**
	 * 分页查找
	 * 
	 * @Methods Name findPageOrganization
	 * @Create In 2015-8-18 By wangx
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findPageOrganization", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findPageOrganization(@RequestBody SelectPcmOrganizationPara para,
			HttpServletRequest request) {

		SelectPcmOrganizationDto dto = new SelectPcmOrganizationDto();

		org.springframework.beans.BeanUtils.copyProperties(para, dto);

		Page<PcmOrganizationResultDto> pageOrg = pcmOrganizationService.findPageOrganization(dto);

		return ResultUtil.creComSucResult(pageOrg);

	}

	/**
	 * 查询所有组织机构信息
	 * 
	 * @Methods Name findListOrganization
	 * @Create In 2015-8-20 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findListOrganization", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findListOrganization(@RequestBody SelectPcmOrganizationPara para,
			HttpServletRequest request) {

		SelectPcmOrganizationDto dto = new SelectPcmOrganizationDto();

		org.springframework.beans.BeanUtils.copyProperties(para, dto);

		List<PcmOrganizationResultDto> dtoList = pcmOrganizationService.findListOrganization(dto);

		return ResultUtil.creComSucResult(dtoList);

	}

	/**
	 * 查询所有组织机构的部分信息
	 * 
	 * @Methods Name findListOrgPart
	 * @Create In 2015-8-20 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findListOrgPart", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findListOrgPart(@RequestBody SelectPcmOrganizationPara para,
			HttpServletRequest request) {

		SelectPcmOrganizationDto dto = new SelectPcmOrganizationDto();

		org.springframework.beans.BeanUtils.copyProperties(para, dto);

		List<PcmOrgPartInfoDto> dtoList = pcmOrganizationService.findListOrgPartInfo(dto);

		return ResultUtil.creComSucResult(dtoList);

	}

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateOrg", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveOrUpdateOrg(@RequestBody @Valid PcmOrgPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/organization/saveOrUpdateOrg.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * @Create In 2015年7月15 by niuzhifan
	 * @param para
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteOrganizationByParam")
	public String deleteOrganizationByParam(@RequestBody @Valid PcmOrganizaPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/organization/deleteOrganizationByParam.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 根据门店sid查询所属大区下的有效门店名称
	 * 
	 * @param para
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectOrganizationByParam")
	public Map<String, Object> selectOrganizationByParam(@RequestBody PcmOrganizaPara para,
			HttpServletRequest request) {
		PublishOrganizationDto dto = new PublishOrganizationDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = pcmOrganizationService.findPcmGetShoppeDtoByShopSid(dto);
		return ResultUtil.creComSucResult(list);
	}

	/**
	 * 查询所有的门店（无条件）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectShop", produces = "application/json; charset=utf-8")
	public String selectShop() {
		List<Map<String, Object>> list = pcmOrganizationService.findListShop();
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() > 0) {
			map.put("success", true);
			map.put("list", list);
		} else {
			map.put("success", false);
			map.put("message", ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
			map.put("errorCode", ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode());
		}

		return JsonUtil.getJSONString(map);
	}

}
