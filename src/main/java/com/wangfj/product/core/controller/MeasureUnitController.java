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
import com.wangfj.product.core.controller.support.MeasureUnitPara;
import com.wangfj.product.maindata.domain.entity.PcmMeasureUnitDict;
import com.wangfj.product.maindata.domain.vo.PcmMeasureUnitDto;
import com.wangfj.product.maindata.service.intf.IPcmMeasureUnitDictService;

/**
 * 计量单位controller
 * 
 * @Class Name MeasureUnitController
 * @Author zhangxy
 * @Create In 2015年7月29日
 */
@Controller
@RequestMapping("/measureUnit")
public class MeasureUnitController extends BaseController {
	@Autowired
	IPcmMeasureUnitDictService measureService;

	/**
	 * 新增一条单位
	 * 
	 * @Methods Name saveMeasureUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/saveMeasureUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveMeasureUnit(@RequestBody @Valid MeasureUnitPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/measureUnit/saveMeasureUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 修改一条单位
	 * 
	 * @Methods Name updateMeasureUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/updateMeasureUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateMeasureUnit(@RequestBody @Valid MeasureUnitPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/measureUnit/updateMeasureUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 删除一条单位
	 * 
	 * @Methods Name deleteMeasureUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/deleteMeasureUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteMeasureUnit(@RequestBody @Valid MeasureUnitPara para,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/measureUnit/deleteMeasureUnit.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 查询计量单位
	 * 
	 * @Methods Name selectMeasureUnit
	 * @Create In 2015年7月29日 By zhangxy
	 * @param para
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectMeasureUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectMeasureUnit(@RequestBody @Valid MeasureUnitPara para,
			HttpServletRequest request) {
		PcmMeasureUnitDto dto = new PcmMeasureUnitDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);
		dto.setCurrentPage(para.getCurrentPage());
		dto.setPageSize(para.getPageSize());
		dto.setStart(para.getStart());
		dto.setLimit(para.getLimit());
		Page<PcmMeasureUnitDict> page = measureService.selectMeasureUnit(dto);
		if (page.getList() != null && page.getList().size() > 0) {
			return ResultUtil.creComSucResult(page.getList());
		} else {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		}
	}
}
