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
import com.wangfj.product.core.controller.support.PcmBusinessTypeDictPara;
import com.wangfj.product.organization.domain.entity.PcmBusinessTypeDict;
import com.wangfj.product.organization.domain.vo.PcmBusinessTypeDictDto;
import com.wangfj.product.organization.service.intf.IPcmBusinessTypeDictService;

/**
 * 经营方式字典controller
 * 
 * @Class Name BusinessTypeDictController
 * @Author zhangxy
 * @Create In 2015年7月29日
 */
@Controller
@RequestMapping("/businessTypeDict")
public class BusinessTypeDictController extends BaseController {
	@Autowired
	IPcmBusinessTypeDictService businessTypeService;

	/**
	 * 新增一条经营方式
	 * 
	 * @Methods Name saveBusinessTypeDict
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/saveBusinessTypeDict", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveBusinessTypeDict(@RequestBody @Valid PcmBusinessTypeDictPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/businessTypeDict/saveBusinessTypeDict.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 修改一条经营方式
	 * 
	 * @Methods Name updateBusinessTypeDict
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/updateBusinessTypeDict", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateBusinessTypeDict(@RequestBody @Valid PcmBusinessTypeDictPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/businessTypeDict/updateBusinessTypeDict.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 删除一条经营方式
	 * 
	 * @Methods Name deleteBusinessTypeDict
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/deleteBusinessTypeDict", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteBusinessTypeDict(@RequestBody @Valid PcmBusinessTypeDictPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/businessTypeDict/deleteBusinessTypeDict.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 查询经营方式
	 * 
	 * @Methods Name selectBusinessTypeDict
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectBusinessTypeDict", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectBusinessTypeDict(
			@RequestBody @Valid PcmBusinessTypeDictPara para, HttpServletRequest request) {
		PcmBusinessTypeDictDto dto = new PcmBusinessTypeDictDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);
		Page<PcmBusinessTypeDict> page = businessTypeService.selectBusinessTypeDict(dto);
		if (page.getList() != null && page.getList().size() > 0) {
			return ResultUtil.creComSucResult(page.getList());
		} else {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		}
	}
}
