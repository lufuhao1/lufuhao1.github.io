package com.wangfj.product.core.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.wangfj.product.core.controller.support.PackUnitPara;
import com.wangfj.product.maindata.domain.entity.PcmPackUnitDict;
import com.wangfj.product.maindata.domain.vo.PcmPackUnitDto;
import com.wangfj.product.maindata.service.intf.IPcmPackUnitDictService;

/**
 * 包装单位controller
 * 
 * @Class Name PackUnitController
 * @Author zhangxy
 * @Create In 2015年7月29日
 */
@Controller
@RequestMapping("/packUnit")
public class PackUnitController extends BaseController {
	@Autowired
	IPcmPackUnitDictService packService;

	/**
	 * 新增一条单位
	 * 
	 * @Methods Name savePackUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/savePackUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String savePackUnit(@RequestBody @Valid PackUnitPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/packUnit/savePackUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 修改一条单位
	 * 
	 * @Methods Name updatePackUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/updatePackUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updatePackUnit(@RequestBody @Valid PackUnitPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/packUnit/updatePackUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 删除一条单位
	 * 
	 * @Methods Name deletePackUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/deletePackUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deletePackUnit(@RequestBody @Valid PackUnitPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/packUnit/deletePackUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 查询包装单位
	 * 
	 * @Methods Name selectPackUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectPackUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectPackUnit(@RequestBody @Valid PackUnitPara para,
			HttpServletRequest request) {
		PcmPackUnitDto dto = new PcmPackUnitDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);
		dto.setCurrentPage(para.getCurrentPage());
		dto.setPageSize(para.getPageSize());
		dto.setStart(para.getStart());
		dto.setLimit(para.getLimit());
		Page<PcmPackUnitDict> page = packService.selectPackUnit(dto);
		if (page.getList() != null && page.getList().size() > 0) {
			return ResultUtil.creComSucResult(page.getList());
		} else {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		}
	}
}
