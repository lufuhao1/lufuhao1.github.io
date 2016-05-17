package com.wangfj.product.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.category.domain.entity.PcmCategoryValuesDict;
import com.wangfj.product.category.service.intf.ICategoryValuesDictService;
import com.wangfj.product.core.controller.support.CategoryValueDictPara;

@Controller
@RequestMapping(value = "categoryValuesDict")
public class PcmCategoryValuesDictController extends BaseController {

	@Autowired
	private ICategoryValuesDictService valueService;

	@ResponseBody
	@RequestMapping(value = "/bw/selectValueDictByPropSid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String selectValueDictByPropSid(CategoryValueDictPara valuePara) {
		String propSid = valuePara.getSid();
		List<PcmCategoryValuesDict> list = valueService.selectValueDictByPropSid(propSid);
		return JsonUtil.getJSONString(list);
	}
}
