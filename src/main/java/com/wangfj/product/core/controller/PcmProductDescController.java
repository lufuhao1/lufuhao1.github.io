package com.wangfj.product.core.controller;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmProductDescAUPara;
import com.wangfj.product.core.controller.support.PcmProductDescQueryPara;
import com.wangfj.product.maindata.domain.vo.PcmProductDescDto;
import com.wangfj.product.maindata.domain.vo.PcmProductDescQueryDto;
import com.wangfj.product.maindata.service.intf.IPcmProductDescService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxuan on 2016-02-16 0016. 精包装管理Controller
 */
@Controller
@RequestMapping(value = { "/productDesc" }, produces = { "application/json;charset=utf-8" })
public class PcmProductDescController extends BaseController {

	@Autowired
	private IPcmProductDescService productDescService;

	/**
	 * 查询List
	 * 
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findListByParam" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> findListByParam(@RequestBody PcmProductDescQueryPara para) {

		PcmProductDescQueryDto dto = new PcmProductDescQueryDto();
		BeanUtils.copyProperties(para, dto);
		List<PcmProductDescDto> list = productDescService.findListByParam(dto);
		return ResultUtil.creComSucResult(list);

	}

	/**
	 * 分页查询
	 * 
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findPageByParam" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object> findPageByParam(@RequestBody PcmProductDescQueryPara para) {

		PcmProductDescQueryDto dto = new PcmProductDescQueryDto();
		BeanUtils.copyProperties(para, dto);
		Page<PcmProductDescDto> page = productDescService.findPageByParam(dto);
		return ResultUtil.creComSucResult(page);

	}

	/**
	 * 添加
	 * 
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addProductDesc" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String addProductDesc(@RequestBody PcmProductDescAUPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/productDesc/addProductDesc.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		return doPost;
	}

	/**
	 * 修改商品描述
	 * 
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/modifyProductDesc" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String modifyProductDesc(@RequestBody PcmProductDescAUPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin") + "/productDesc/modifyProductDesc.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		return doPost;
	}

	/**
	 * 添加或修改
	 * 
	 * @param para
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/addOrModifyProductDesc" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addOrModifyProductDesc(@RequestBody PcmProductDescAUPara para) {

		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "/productDesc/addOrModifyProductDesc.htm";
		String json = JsonUtil.getJSONString(para);
		String doPost = HttpUtil.doPost(url, json);
		return doPost;
	}

}
