package com.wangfj.product.core.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmAUTagPara;
import com.wangfj.product.core.controller.support.PcmAUTagsPara;
import com.wangfj.product.core.controller.support.PcmTagPara;
import com.wangfj.product.maindata.domain.vo.PcmTagDto;
import com.wangfj.product.maindata.service.intf.IPcmTagService;

@Controller
@RequestMapping(value = "/pcmTag", produces = "application/json;charset=utf-8")
public class PcmTagController extends BaseController{

	@Autowired
	private IPcmTagService pcmTagService;
	
	/**
	 * 分页查询
	 * @param para
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/findPagePcmTag" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> findPagePcmTag(@RequestBody @Valid PcmTagPara para,
			HttpServletRequest request) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tagName", para.getTagName());
		paramMap.put("tagType", para.getTagType());
		if(para.getBeginDate() != null && para.getBeginDate() != ""){
			paramMap.put("beginDate", format.parse(para.getBeginDate()));
		}
		if(para.getEndDate() != null && para.getEndDate() != ""){
			paramMap.put("endDate", format.parse(para.getEndDate()));
		}
		paramMap.put("currentPage", para.getCurrentPage());
		paramMap.put("pageSize", para.getPageSize());

		Page<PcmTagDto> pageOrg = pcmTagService.selectTag(paramMap);

		return ResultUtil.creComSucResult(pageOrg);
	}
	
	/**
	 * 添加和修改活动标签
	 * @param para
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveOrUpdatePcmTag" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String saveOrUpdatePcmTag(@RequestBody @Valid PcmAUTagPara para,
			HttpServletRequest request) throws Exception {

		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "pcmTag/saveOrUpdatePcmTag.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));

		return response;
	}
	
	/**
	 * 添加和修改活动标签-批量
	 * @param para
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/saveOrUpdatePcmTags" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String saveOrUpdatePcmTags(@RequestBody @Valid PcmAUTagsPara para,
			HttpServletRequest request) throws Exception {

		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "pcmTag/saveOrUpdatePcmTags.htm";

		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));

		return response;
	}
	/**
	 * 根据条件查询tag列表
	 * @param para
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "/findPcmTagList" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> findPcmTagList(@RequestBody @Valid PcmTagPara para,
			HttpServletRequest request) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(para.getTagName() != null && para.getTagName() != ""){
			paramMap.put("tagName", para.getTagName());
		}
		if(para.getShoppeProSid() != null && para.getShoppeProSid() != ""){
			paramMap.put("shoppeProSid", para.getShoppeProSid());
		}
		if(para.getBeginDate() != null && para.getBeginDate() != ""){
			paramMap.put("beginDate", format.parse(para.getBeginDate()));
		}
		if(para.getEndDate() != null && para.getEndDate() != ""){
			paramMap.put("endDate", format.parse(para.getEndDate()));
		}
		if(para.getCurrentPage() != null){
			paramMap.put("currentPage", para.getCurrentPage());
		}
		if(para.getPageSize() != null){
			paramMap.put("pageSize", para.getPageSize());
		}

		List<PcmTagDto> tagList = pcmTagService.selectTagByParam(paramMap);

		return ResultUtil.creComSucResult(tagList);
	}
}
