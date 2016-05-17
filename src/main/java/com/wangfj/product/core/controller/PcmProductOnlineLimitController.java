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
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmProductOnlineLimitPara;
import com.wangfj.product.core.controller.support.PcmProductOnlineLimitQueryPara;
import com.wangfj.product.limit.domain.vo.PcmProductOnlineLimitQueryDto;
import com.wangfj.product.limit.domain.vo.PcmProductOnlineLimitResultDto;
import com.wangfj.product.limit.service.intf.IPcmProductOnlineLimitService;

@Controller
@RequestMapping(value = "/productOnlineLimit", produces = "application/json;charset=utf-8")
public class PcmProductOnlineLimitController extends BaseController {

	@Autowired
	private IPcmProductOnlineLimitService productOnlineLimitService;

	@RequestMapping(value = "/addProductLimitList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String addProductLimitList(@RequestBody List<PcmProductOnlineLimitPara> paraList) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/productOnlineLimit/addProductLimitList.htm";
		String doPost = HttpUtil.doPost(url, JsonUtil.getJSONString(paraList));
		return doPost;

	}

	@RequestMapping(value = "/modifyProductLimit", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String modifyProductLimit(@RequestBody PcmProductOnlineLimitPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/productOnlineLimit/modifyProductLimit.htm";
		String doPost = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return doPost;

	}

	@RequestMapping(value = "/findPageProductLimitInfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findPageProductLimitInfo(
			@RequestBody PcmProductOnlineLimitQueryPara para) {

		PcmProductOnlineLimitQueryDto dto = new PcmProductOnlineLimitQueryDto();
		BeanUtils.copyProperties(para, dto);
		Page<PcmProductOnlineLimitResultDto> page = productOnlineLimitService
				.findPageProductLimitInfo(dto);

		return ResultUtil.creComSucResult(page);

	}

}
