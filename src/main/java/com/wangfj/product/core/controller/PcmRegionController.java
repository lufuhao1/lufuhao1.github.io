package com.wangfj.product.core.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import com.wangfj.product.core.controller.support.PcmRegionPara;
import com.wangfj.product.core.controller.support.PcmRegionQueryPara;
import com.wangfj.product.organization.domain.vo.PcmRegionQueryDto;
import com.wangfj.product.organization.domain.vo.PcmRegionResultDto;
import com.wangfj.product.organization.service.intf.IPcmRegionService;

@Controller
@RequestMapping(value = "/region", produces = "application/json;charset=utf-8")
public class PcmRegionController extends BaseController {

	@Autowired
	private IPcmRegionService regionService;

	/**
	 * 添加行政区域
	 *
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/addRegion", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String addRegion(@RequestBody @Valid PcmRegionPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/region/addRegion.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;

	}

	/**
	 * 修改行政区域
	 *
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/modifyRegion", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String modifyRegion(@RequestBody @Valid PcmRegionPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/region/modifyRegion.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;

	}

	/**
	 * 分页查询行政区域
	 *
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/findPageRegion", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findPageRegion(@RequestBody PcmRegionQueryPara para) {

		PcmRegionQueryDto dto = new PcmRegionQueryDto();

		BeanUtils.copyProperties(para, dto);

		Page<PcmRegionResultDto> pageRegion = regionService.findPageRegion(dto);

		return ResultUtil.creComSucResult(pageRegion);

	}

	/**
	 * 按条件查询行政区域
	 *
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/findListRegion", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findListRegion(@RequestBody PcmRegionQueryPara para) {

		PcmRegionQueryDto dto = new PcmRegionQueryDto();

		BeanUtils.copyProperties(para, dto);

		List<PcmRegionResultDto> regionList = regionService.findListRegion(dto);

		return ResultUtil.creComSucResult(regionList);

	}

}
