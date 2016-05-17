package com.wangfj.product.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import com.wangfj.product.core.controller.support.PcmPaymentOrganPara;
import com.wangfj.product.core.controller.support.PcmPaymentTypePara;
import com.wangfj.product.core.controller.support.QueryPaymentTypePara;
import com.wangfj.product.core.controller.support.QueryStorePaymentTypePara;
import com.wangfj.product.price.domain.vo.PcmPaymentInfoDto;
import com.wangfj.product.price.domain.vo.PcmPaymentOrganDto;
import com.wangfj.product.price.domain.vo.PcmShopPaymentInfoDto;
import com.wangfj.product.price.domain.vo.SelectPaymentDto;
import com.wangfj.product.price.domain.vo.SelectPaymentTypeDto;
import com.wangfj.product.price.service.intf.IPcmPaymentTypeService;
import com.wangfj.util.Constants;

@Controller
@RequestMapping(value = "/pcmpayment")
public class PcmPaymentController extends BaseController {

	@Autowired
	private IPcmPaymentTypeService pcmPaymentTypeService;

	/**
	 * 新增支付方式
	 * 
	 * @Methods Name savePaymentType
	 * @Create In 2015年8月10日 By kongqf
	 * @param request
	 * @param pcmPaymentTypePara
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/createPaymentType", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String savePaymentType(HttpServletRequest request,
			@RequestBody @Valid PcmPaymentTypePara pcmPaymentTypePara) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmpayment/createPaymentType.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmPaymentTypePara));
		return response;
	}

	/**
	 * 门店添加支付方式
	 * 
	 * @Methods Name savePcmPaymentOrgan
	 * @Create In 2015年8月10日 By kongqf
	 * @param request
	 * @param pcmPaymentOrganPara
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/createPaymentOrgan", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String savePcmPaymentOrgan(HttpServletRequest request,
			@RequestBody @Valid List<PcmPaymentOrganPara> pcmPaymentOrganParaList) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmpayment/createPaymentOrgan.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmPaymentOrganParaList));
		return response;
	}

	/**
	 * 删除支付方式
	 * 
	 * @Methods Name delPaymentType
	 * @Create In 2015年8月10日 By kongqf
	 * @param request
	 * @param pcmPaymentTypeDelPara
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/delPaymentType", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String delPaymentType(HttpServletRequest request,
			@RequestBody PcmPaymentTypePara pcmPaymentTypePara) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmpayment/delPaymentType.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmPaymentTypePara));
		return response;
	}

	/**
	 * 修改支付方式名称
	 * 
	 * @Methods Name updatePcmPaymentType
	 * @Create In 2015年10月8日 By kongqf
	 * @param request
	 * @param pcmPaymentTypePara
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/updatePcmPaymentType", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updatePcmPaymentType(HttpServletRequest request,
			@RequestBody @Valid PcmPaymentTypePara pcmPaymentTypePara) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/pcmpayment/updatePcmPaymentType.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmPaymentTypePara));
		return response;
	}

	/**
	 * 删除门店支付方式
	 * 
	 * @Methods Name delPcmPaymentOrgan
	 * @Create In 2015年8月10日 By kongqf
	 * @param request
	 * @param pcmPaymentOrganPara
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/delPaymentOrgan", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String delPcmPaymentOrgan(HttpServletRequest request,
			@RequestBody @Valid PcmPaymentOrganPara para) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmpayment/delPaymentOrgan.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 根据门店信息查询门店的支付方式
	 * 
	 * @Methods Name queryStorePaymentType
	 * @Create In 2015年8月10日 By kongqf
	 * @param request
	 * @param queryStorePaymentType
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryStorePaymentTypePage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryStorePaymentTypePage(HttpServletRequest request,
			@RequestBody QueryStorePaymentTypePara queryStorePaymentType) {
		SelectPaymentDto selectPaymentDto = new SelectPaymentDto();
		try {
			BeanUtils.copyProperties(selectPaymentDto, queryStorePaymentType);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Page<PcmPaymentOrganDto> pageDto = new Page<PcmPaymentOrganDto>();
		pageDto = pcmPaymentTypeService.selectShopPaymentTypeList(selectPaymentDto);

		if (pageDto == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(pageDto);
		}
	}

	/**
	 * 根据门店查询门店下的一级支付介质
	 * 
	 * @Methods Name query1PaymentTypeByShopSid
	 * @Create In 2015年9月29日 By kongqf
	 * @param request
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/query1PaymentTypebyshopsid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> query1PaymentTypeByShopSid(HttpServletRequest request,
			@RequestBody QueryPaymentTypePara para) {
		SelectPaymentTypeDto selectPaymentDto = new SelectPaymentTypeDto();
		try {
			BeanUtils.copyProperties(selectPaymentDto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Page<PcmPaymentInfoDto> pageDto = new Page<PcmPaymentInfoDto>();
		pageDto = pcmPaymentTypeService.select1PaymentTypeList(selectPaymentDto);

		if (pageDto == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(pageDto);
		}
	}

	/**
	 * 根据门店查询门店下的一级支付介质
	 * 
	 * @Methods Name query1PaymentTypeByShopSid
	 * @Create In 2015年9月29日 By kongqf
	 * @param request
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/query2PaymentTypebyshopsid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> query2PaymentTypeByShopSid(HttpServletRequest request,
			@RequestBody QueryPaymentTypePara para) {
		SelectPaymentTypeDto selectPaymentDto = new SelectPaymentTypeDto();
		try {
			BeanUtils.copyProperties(selectPaymentDto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Page<PcmPaymentInfoDto> pageDto = new Page<PcmPaymentInfoDto>();
		pageDto = pcmPaymentTypeService.select2PaymentTypeList(selectPaymentDto);

		if (pageDto == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(pageDto);
		}
	}

	/**
	 * 根据门店信息查询门店所关联的支付方式列表（分页）
	 * 
	 * @Methods Name queryPaymentTypePage
	 * @Create In 2015年8月11日 By kongqf
	 * @param request
	 * @param queryPaymentType
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryPaymentTypePage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryPaymentTypePage(HttpServletRequest request,
			@RequestBody QueryPaymentTypePara queryPaymentType) {
		SelectPaymentTypeDto selectPaymentTypeDto = new SelectPaymentTypeDto();
		Page<PcmShopPaymentInfoDto> PcmPaymentInfoDtoList = new Page<PcmShopPaymentInfoDto>();
		try {
			BeanUtils.copyProperties(selectPaymentTypeDto, queryPaymentType);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		PcmPaymentInfoDtoList = pcmPaymentTypeService
				.selectPaymentTypeListByShopSid(selectPaymentTypeDto);

		if (PcmPaymentInfoDtoList == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(PcmPaymentInfoDtoList);
		}
	}

	/**
	 * 支付方式查询
	 * 
	 * @Methods Name queryPaymentTypeList
	 * @Create In 2015年9月9日 By kongqf
	 * @param request
	 * @param queryPaymentType
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryPaymentTypeInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryPaymentTypeList(HttpServletRequest request,
			@RequestBody QueryPaymentTypePara queryPaymentType) {

		SelectPaymentTypeDto selectPaymentTypeDto = new SelectPaymentTypeDto();
		Page<PcmPaymentInfoDto> PcmPaymentInfoDtoList = new Page<PcmPaymentInfoDto>();
		try {
			BeanUtils.copyProperties(selectPaymentTypeDto, queryPaymentType);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		PcmPaymentInfoDtoList = pcmPaymentTypeService
				.selectPaymentTypeListByParam(selectPaymentTypeDto);

		if (PcmPaymentInfoDtoList == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(PcmPaymentInfoDtoList);
		}
	}

	/**
	 * 查询门店可添加的支付方式
	 * 
	 * @Methods Name queryNotPaymentTypeListByShopSid
	 * @Create In 2015年9月15日 By kongqf
	 * @param request
	 * @param queryPaymentType
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryNotPaymentTypeInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> queryNotPaymentTypeListByShopSid(HttpServletRequest request,
			@RequestBody QueryPaymentTypePara queryPaymentType) {

		SelectPaymentTypeDto selectPaymentTypeDto = new SelectPaymentTypeDto();
		Page<PcmPaymentInfoDto> PcmPaymentInfoDtoList = new Page<PcmPaymentInfoDto>();
		try {
			BeanUtils.copyProperties(selectPaymentTypeDto, queryPaymentType);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		PcmPaymentInfoDtoList = pcmPaymentTypeService
				.selecNotPaymentTypeListByShopSid(selectPaymentTypeDto);

		if (PcmPaymentInfoDtoList == null) {
			return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
		} else {
			return ResultUtil.creComSucResult(PcmPaymentInfoDtoList);
		}
	}
}
