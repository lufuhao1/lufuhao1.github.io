package com.wangfj.product.core.controller;

import java.util.List;
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
import com.wangfj.core.utils.StringUtils;
import com.wangfj.product.core.controller.support.PcmChannelPara;
import com.wangfj.product.organization.domain.vo.PcmChannelDto;
import com.wangfj.product.organization.service.intf.IPcmChannelService;

/**
 * 渠道Controller
 * 
 * @Class Name PcmChannelController
 * @Author wangxuan
 * @Create In 2015-8-20
 */
@Controller
@RequestMapping(value = "/pcmAdminChannel", produces = "application/json; charset=utf-8")
public class PcmChannelController extends BaseController {

	@Autowired
	private IPcmChannelService channelService;

	/**
	 * 分页查找
	 * 
	 * @Methods Name findPageChannel
	 * @Create In 2015-8-20 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findPageChannel", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findPageChannel(@RequestBody Map<String, Object> para,
			HttpServletRequest request) {

		Page<PcmChannelDto> page = channelService.findPageChannel(para);

		return ResultUtil.creComSucResult(page);

	}

	/**
	 * 查询渠道
	 * 
	 * @Methods Name findListChannel
	 * @Create In 2015-9-8 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findListChannel", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findListChannel(@RequestBody Map<String, Object> para,
			HttpServletRequest request) {

		List<PcmChannelDto> channelDtoList = channelService.findListChannel(para);

		return ResultUtil.creComSucResult(channelDtoList);

	}

	/**
	 * 根据sid查询渠道
	 * 
	 * @Methods Name findChannelBySid
	 * @Create In 2015-8-20 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findChannelBySid", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findChannelBySid(@RequestBody Map<String, Object> para,
			HttpServletRequest request) {

		PcmChannelDto channelDto = null;
		String sid = para.get("sid") + "";
		if (StringUtils.isNotEmpty(sid)) {
			channelDto = channelService.findChannelBySid(Long.parseLong(sid));
		}

		if (channelDto != null) {
			return ResultUtil.creComSucResult(channelDto);
		} else {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
		}

	}

	/**
	 * 添加渠道
	 * 
	 * @Methods Name addChannel
	 * @Create In 2015-8-20 By nzf
	 * @param channelPara
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/addChannel", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addChannel(@RequestBody @Valid PcmChannelPara channelPara,
			HttpServletRequest request) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminChannel/addChannel.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(channelPara));
		return response;

	}

	/**
	 * 修改渠道
	 * 
	 * @Methods Name upateChannel
	 * @Create In 2015-8-21 By nzf
	 * @param channelPara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/upateChannel", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String upateChannel(@RequestBody @Valid PcmChannelPara channelPara,
			HttpServletRequest request) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminChannel/upateChannel.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(channelPara));
		return response;

	}

	/**
	 * 禁用渠道
	 * 
	 * @Methods Name deleteChannel
	 * @Create In 2015-8-20 By wangxuan
	 * @param sid
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/deleteChannel", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteChannel(@RequestBody String sid, HttpServletRequest request) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminChannel/deleteChannel.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(sid));
		return response;

	}

	/**
	 * 根据SPU（编码）查询渠道
	 *
	 * @Methods Name findChannelBySPUPara
	 * @Create In 2016-3-1 By wangxuan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findChannelBySPUPara", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findChannelBySPUPara(@RequestBody Map<String, Object> para,
			HttpServletRequest request) {

		List<PcmChannelDto> list = channelService.findChannelBySPUPara(para);

		return ResultUtil.creComSucResult(list);

	}

}
