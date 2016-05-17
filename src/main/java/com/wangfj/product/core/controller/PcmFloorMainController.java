package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.*;
import com.wangfj.product.core.controller.support.PcmFloorPara;
import com.wangfj.product.core.controller.support.PcmFloorQueryPara;
import com.wangfj.product.core.controller.support.PcmFloorsPara;
import com.wangfj.product.core.controller.support.SelectPcmFloorPara;
import com.wangfj.product.organization.domain.vo.PcmFloorDto;
import com.wangfj.product.organization.domain.vo.PcmFloorResultDto;
import com.wangfj.product.organization.domain.vo.PushCounterDto;
import com.wangfj.product.organization.domain.vo.SelectPcmFloorDto;
import com.wangfj.product.organization.service.intf.IPcmFloorService;
import com.wangfj.util.Constants;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "/floor" }, produces = "application/json;charset=utf-8")
public class PcmFloorMainController extends BaseController {

	@Autowired
	private IPcmFloorService pcmFloorService;

	@ResponseBody
	@RequestMapping("/findFloorByParamFromPcm")
	public String findFloorByParamFromPcm(@RequestBody Map<String, Object> paramMap) {
		List<PcmFloorDto> findFloor = null;
		Page<PushCounterDto> page = new Page<PushCounterDto>();
		if (paramMap.get("pageSize") != null) {
			page.setPageSize((Integer) paramMap.get("pageSize"));
		}
		if (paramMap.get("currentPage") != null) {
			page.setCurrentPage((Integer) paramMap.get("currentPage"));
		}

		Integer count = pcmFloorService.getCountByParam(paramMap);
		page.setCount(count);
		paramMap.put("start", page.getStart());
		paramMap.put("limit", page.getLimit());

		try {
			findFloor = pcmFloorService.findFloorByParamFromPcm(paramMap);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return JsonUtil.getJSONString(findFloor);
	}

	/**
	 * 删除
	 * 
	 * @param pcm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFloorBySidPcm")
	public String deleteFloorBySidPcm(@RequestBody @Valid PcmFloorPara pcm) throws Exception {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/floor/deleteFloorBySidPcm.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcm));
		return response;
	}

	/**
	 * 添加楼层信息
	 */
	@ResponseBody
	@RequestMapping(value = "/addFloor", method = { RequestMethod.POST, RequestMethod.GET })
	public String addFloor(@RequestBody @Valid PcmFloorPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/floor/addFloor.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 修改楼层信息
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyFloor", method = { RequestMethod.POST, RequestMethod.GET })
	public String modifyFloor(@RequestBody @Valid PcmFloorPara para, HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/floor/modifyFloor.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
		return response;
	}

	/**
	 * 通过楼层sid查询楼层信息
	 * 
	 * @Methods Name findFloorBySid
	 * @Create In 2015年7月31日 By wuxiong
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/findFloorBySid")
	public Map<String, Object> findFloorBySid(@RequestBody @Valid PcmFloorsPara para,
			HttpServletRequest request) {
		PcmFloorDto dto = new PcmFloorDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		PcmFloorDto floorDto = pcmFloorService.selectByFloorSid(dto.getSid());
		return ResultUtil.creComSucResult(floorDto);

	}

	/**
	 * 楼层信息查询
	 * 
	 * @Methods Name findFloorByParam
	 * @Create In 2015年7月27日 By wuxiong
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/findFloorByParam")
	public Map<String, Object> findFloorByParam(@RequestBody @Valid PcmFloorsPara para,
			HttpServletRequest request) {
		Page<PcmFloorDto> pagedto = new Page<PcmFloorDto>();
		pagedto.setCurrentPage(para.getCurrentPage());
		pagedto.setPageSize(para.getPageSize());
		PcmFloorDto floordto = new PcmFloorDto();
		try {
			BeanUtils.copyProperties(floordto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Page<HashMap<String, Object>> page = pcmFloorService.findFloorFromPCM(floordto, pagedto);
		return ResultUtil.creComSucResult(page);

	}

	/**
	 * 根据门店ShopSid 查询此门店下的楼层信息列表
	 * 
	 * @Methods Name getFloorsByShopSid
	 * @Create In 2015-8-24 By chengsj
	 * @param request
	 * @param response
	 * @return Map<String,Object>
	 */
	@RequestMapping("/getFloorsByShopSid")
	@ResponseBody
	public Map<String, Object> getFloorsByShopSid(@RequestBody Map<String, String> map,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(map.get("shopSid"))) {
			list = pcmFloorService.getFloorsByShopSid(Long.valueOf(map.get("shopSid")));
		} else {
			// 参数为空
			// return ResultUtil.creComErrorResult("1", "参数为空");
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.FLOOR_BY_SHOPSID_NULL.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.FLOOR_BY_SHOPSID_NULL.getMemo());
		}
		if (list.size() == Constants.PUBLIC_0) {
			return ResultUtil.creComErrorResult(
					ComErrorCodeConstants.ErrorCode.FLOOR_SELECT_FM_NULL.getErrorCode(),
					ComErrorCodeConstants.ErrorCode.FLOOR_SELECT_FM_NULL.getMemo());
		}
		return ResultUtil.creComSucResult(list);
	}

	/**
	 * 分页查询
	 * 
	 * @Methods Name findPageFloor
	 * @Create In 2015-8-25 By niuzhifan
	 * @param para
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findPageFloor", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> findPageFloor(@RequestBody SelectPcmFloorPara para,
			HttpServletRequest request) {

		SelectPcmFloorDto dto = new SelectPcmFloorDto();

		String sid = para.getSid();
		String shopSid = para.getShopSid();
		String floorName = para.getFloorName();
		String floorCode = para.getFloorCode();
		Integer currentPage = para.getCurrentPage();
		Integer pageSize = para.getPageSize();

		if (StringUtils.isNotEmpty(sid)) {
			dto.setSid(Long.parseLong(sid));
		}
		if (StringUtils.isNotEmpty(shopSid)) {
			dto.setShopSid(Long.parseLong(shopSid));
		}
		if (StringUtils.isNotEmpty(floorName)) {
			dto.setFloorName(floorName);
		}
		if (StringUtils.isNotEmpty(floorCode)) {
			dto.setFloorCode(floorCode);
		}

		if (currentPage != null) {
			dto.setCurrentPage(currentPage);
		} else {
			dto.setCurrentPage(1);
		}

		if (pageSize != null) {
			dto.setPageSize(pageSize);
		} else {
			dto.setPageSize(10);
		}

		Page<PcmFloorResultDto> page = pcmFloorService.findPageFloor(dto);

		return ResultUtil.creComSucResult(page);
	}

	/**
	 * 查询楼层List
	 * 
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/selectFloorList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> selectFloorList(@RequestBody PcmFloorQueryPara para) {

		SelectPcmFloorDto dto = new SelectPcmFloorDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);

		List<PcmFloorResultDto> list = pcmFloorService.selectFloorListByParam(dto);

		return ResultUtil.creComSucResult(list);
	}

	@RequestMapping("/getFloorsByShopCode")
	@ResponseBody
	public Map<String, Object> getFloorsByShopCode(@RequestBody PcmFloorPara para,
			HttpServletRequest request) {
		PcmFloorDto dto = new PcmFloorDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> list = pcmFloorService.selectFloorByShopCode(dto);
		return ResultUtil.creComSucResult(list);
	}
}
