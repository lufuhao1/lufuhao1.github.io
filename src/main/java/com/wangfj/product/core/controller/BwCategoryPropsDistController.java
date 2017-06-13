package com.wangfj.product.core.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.category.domain.entity.PcmCategory;
import com.wangfj.product.category.domain.entity.PcmCategoryPropsDict;
import com.wangfj.product.category.domain.vo.CategoryPropsDictVO;
import com.wangfj.product.category.domain.vo.PcmPropsDictsDto;
import com.wangfj.product.category.service.intf.ICategoryPropValuesService;
import com.wangfj.product.category.service.intf.ICategoryPropsDictService;
import com.wangfj.product.category.service.intf.ICategoryService;
import com.wangfj.product.category.service.intf.ICategoryValuesDictService;
import com.wangfj.product.core.controller.support.CategoryPropsDictPara;

@Controller
@RequestMapping("/propsdictcontroller")
public class BwCategoryPropsDistController extends BaseController{

	@Autowired
	private ICategoryPropsDictService ssdCategoryPropsDictService;

	@Autowired
	private ICategoryValuesDictService ssdCategoryValuesDictService;

	@Autowired
	private ICategoryPropValuesService ssdCategoryPropValuesService;

	@Autowired
	private ICategoryService categoryService;

	/**
	 * 属性字典查询
	 * 
	 * @Methods Name list
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param model
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propsdictList", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String list(@RequestBody CategoryPropsDictPara propsPara) {
		JSONObject jsons = new JSONObject();
		PcmCategoryPropsDict scp = new PcmCategoryPropsDict();
		// 获取参数
		String propsName = propsPara.getPropsName();
		String propsDesc = propsPara.getPropsDesc();
		String channelSid = propsPara.getChannelSid();
		Integer limit = propsPara.getLimit();
		Integer start = propsPara.getStart();
		if (!(propsName == null || "".equals(propsName))) {
			if(propsName.indexOf("%") != -1 || propsName.indexOf("_") != -1){
				String newPropsName = propsName.replaceAll("%", "\\\\%");	
				if(propsName.indexOf("_") != -1){
					newPropsName = newPropsName.replaceAll("_", "\\\\_");
				}
				scp.setPropsName(newPropsName);
			}else{
				scp.setPropsName(propsName);
			}
		}
		if (!(propsDesc == null || "".equals(propsDesc))) {
			scp.setPropsDesc(propsDesc);
		}
		if (!(channelSid == null || "".equals(channelSid))) {
			scp.setChannelSid(Long.valueOf(channelSid));
		}

		// String channelSid = LoadProperties.readValue("channel.WEB");
		scp.setStart(start);
		scp.setPageSize(limit);
		int total = this.ssdCategoryPropsDictService.selectPageLikeTotal(scp);
		List lists = this.ssdCategoryPropsDictService.selectPage(scp);
		int pageCount = total % limit == 0 ? total / limit : (total / limit + 1);
		jsons.put("list", lists);
		jsons.put("pageCount", pageCount);
		return jsons.toString();
	}
	
	/**
	 * 属性字典查询全部
	 * 
	 * @Methods Name list
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param model
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propsdictAllList", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String allList(@RequestBody CategoryPropsDictPara propsPara) {
		JSONObject jsons = new JSONObject();
		PcmCategoryPropsDict scp = new PcmCategoryPropsDict();
		// 获取参数
		String propsName = propsPara.getPropsName();
		String propsDesc = propsPara.getPropsDesc();
		String channelSid = propsPara.getChannelSid();
		if (!(propsName == null || "".equals(propsName))) {
			if(propsName.indexOf("%") != -1 || propsName.indexOf("_") != -1){
				String newPropsName = propsName.replaceAll("%", "\\\\%");	
				if(propsName.indexOf("_") != -1){
					newPropsName = newPropsName.replaceAll("_", "\\\\_");
				}
				scp.setPropsName(newPropsName);
			}else{
				scp.setPropsName(propsName);
			}
		}
		if (!(propsDesc == null || "".equals(propsDesc))) {
			scp.setPropsDesc(propsDesc);
		}
		if (!(channelSid == null || "".equals(channelSid))) {
			scp.setChannelSid(Long.valueOf(channelSid));
		}
		
		List lists = this.ssdCategoryPropsDictService.selectAll(scp);
		jsons.put("list", lists);
		jsons.put("scusse", true);
		return jsons.toString();
	}

	/**
	 * 属性添加/修改
	 * 
	 * @Methods Name add
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param m
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return
	 * @throws UnsupportedEncodingException
	 *             Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propsdictAdd", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String add(Model m, HttpServletRequest request,
			 CategoryPropsDictPara propsPara) throws UnsupportedEncodingException {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+"propsdictcontroller/bw/propsdictAdd.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(propsPara));
		return response;
	}

	/**
	 * 根据sid 查询属性信息
	 * 
	 * @Methods Name edit
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param m
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propsdictEdit", method = { RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> edit(Model m, HttpServletRequest request,
			HttpServletResponse response, CategoryPropsDictPara propsPara) {
		PcmCategoryPropsDict scpd = this.ssdCategoryPropsDictService.get(Long.valueOf(propsPara
				.getSid()));
		JSONObject json = new JSONObject();
		json.put("propsName", scpd.getPropsName());
		json.put("propsDesc", scpd.getPropsDesc());
		json.put("propsCode", scpd.getPropsCode());
		json.put("status", scpd.getStatus());
		json.put("channelSid", scpd.getChannelSid());

		return ResultUtil.creComSucResult(json);
	}

	/**
	 * 删除属性（修改属性状态）
	 * 
	 * @Methods Name del
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param m
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propsdictDel", method = { RequestMethod.POST, RequestMethod.GET },produces = "application/json; charset=utf-8")
	public String del(Model m, HttpServletRequest request,
			 CategoryPropsDictPara propsPara) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "propsdictcontroller/bw/propsdictDel.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(propsPara));
		return response;
		
	}

	/**
	 * 通过品类id查询状态为可用的属性信息
	 * 
	 * @Methods Name comboxlist
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param model
	 * @param request
	 * @param response
	 * @param propsPara
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/propscomboxList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String comboxlist(Model model, HttpServletRequest request, HttpServletResponse response,
			CategoryPropsDictPara propsPara) {
		// 查询条件是全渠道和当前分类的sid对应的渠道
		PcmCategory sc = this.categoryService.get(Long.valueOf(propsPara.getSid()));
		JSONArray jsons = new JSONArray();
		CategoryPropsDictVO scp = new CategoryPropsDictVO();
		List<String> str = new ArrayList<String>();
		if (sc.getChannelSid() != 0) {
			str.add("0");
			str.add(String.valueOf(sc.getChannelSid()));
		} else {
			str.add("0");
		}
		scp.setChannelSid(str);
		scp.setStatus(1L);
		// 通过属性名称模糊查询
		scp.setPropsName(propsPara.getPropsName());
		scp.setIsStockPriceControls(propsPara.getIsStockPriceControls());
		List<PcmCategoryPropsDict> lists = this.ssdCategoryPropsDictService
				.selectListInChannelSid(scp);
		for (PcmCategoryPropsDict scpd : lists) {
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(scpd);
			jsons.add(json);
		}

		return jsons.toString();
	}

	/**
	 * 根据parentsid查询属性字典信息
	 * 
	 * @Methods Name selectPropsDictByParentSid
	 * @Create In 2015年8月13日 By duanzhaole
	 * @param parentSid
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/selectPropsDictByParentSid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Map<String, Object> selectPropsDictByParentSid(@RequestBody PcmPropsDictsDto catePara) {

		Page<PcmPropsDictsDto> pageparam = new Page<PcmPropsDictsDto>();
		pageparam.setPageSize(catePara.getPageSize());
		pageparam.setCurrentPage(catePara.getCurrenPage());
		Page<PcmCategoryPropsDict> listprop = this.ssdCategoryPropsDictService
				.selectPropsDictByParentSid(catePara, pageparam);
		return ResultUtil.creComSucResult(listprop);
	}
}
