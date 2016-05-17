/**
 * @Probject Name: pcm-core
 * @Path: com.wangfj.product.EfutureERP.controller.categoryCategoryInfoController.java
 * @Create By duanzhaole
 * @Create In 2015年7月27日 下午2:56:04
 * TODO
 */
package com.wangfj.product.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.category.domain.entity.PcmCategory;
import com.wangfj.product.category.domain.vo.PcmAddCategoryDto;
import com.wangfj.product.category.domain.vo.PcmProDetailDto;
import com.wangfj.product.category.domain.vo.PcmProductDto;
import com.wangfj.product.category.domain.vo.SelectCategoryParamDto;
import com.wangfj.product.category.service.intf.ICategoryService;
import com.wangfj.product.category.service.intf.ISCategoryService;
import com.wangfj.product.common.domain.vo.PcmExceptionLogDto;
import com.wangfj.product.common.service.intf.IPcmExceptionLogService;
import com.wangfj.product.constants.StatusCodeConstants.StatusCode;
import com.wangfj.product.core.controller.support.CategoryPara;
import com.wangfj.product.core.controller.support.PcmSelectCategoryPara;
import com.wangfj.product.maindata.domain.vo.PcmDictVersionDto;
import com.wangfj.product.maindata.service.intf.IPcmDictVersionService;
import com.wangfj.util.AjaxMessageVO;
import com.wangfj.util.Constants;

/**
 * 品类信息基本操作
 * 
 * @Class Name CategoryInfoController
 * @Author duanzhaole
 * @Create In 2015年7月27日
 */
@Controller
@RequestMapping("/categoryinfocontroller")
public class CategoryInfoController extends BaseController{
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private ISCategoryService ssdcategoryService;
	@Autowired
	private IPcmDictVersionService pcmVersion;
	@Autowired
	private IPcmExceptionLogService exceptionLogService;

	/**
	 * 控制品类维护界面的加载
	 * 
	 * @Methods Name getAllCategory
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param model
	 * @param request
	 * @param response
	 * @param catePara
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/bw/getAllCateory", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String getAllCategory(Model model, HttpServletRequest request,
			HttpServletResponse response, CategoryPara catePara) {
		String id = catePara.getId();
		String channelSid = catePara.getChannelSid();
		JSONArray jsons = new JSONArray();
		List<PcmCategory> list = null;
		if (id == null || "".equals(id)) {
			if (!"".equals(channelSid) || null != channelSid) {
				list = this.ssdcategoryService.getByParentSidAndChannelSid("0", Long.valueOf(2),
						null, null);
			} else {
				list = this.ssdcategoryService.getByParentSidAndChannelSid("0",
						Long.valueOf(channelSid), null, null);
			}
		} else {
			// SsdCategory s = this.categoryService.get(Long.valueOf(id));
			list = this.ssdcategoryService.getByParentSidAndChannelSid(id.toString(),
					Long.valueOf(2), null, null);
		}
		for (PcmCategory cat : list) {
			JSONObject json = new JSONObject();
			json.put("id", cat.getCategorySid());
			json.put("text", cat.getName());
			json.put("categoryStatus", cat.getStatus());
			json.put("categoryIsDisplay", cat.getIsDisplay());
			if (cat.getIsParent() == 1) {
				json.put("state", "closed");
			} else {
				json.put("state", "open");
			}
			jsons.add(json);
		}
		return jsons.toString();
	}

	/**
	 * 根据sid查询品类信息
	 * 
	 * @Methods Name getCategoryInfo
	 * @Create In 2015年7月27日 By duanzhaole
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/getCategoryInfo", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> getCategoryInfo(PcmSelectCategoryPara cate,
			HttpServletRequest request) {

		PcmAddCategoryDto catedto = new PcmAddCategoryDto();
		try {
			catedto = categoryService.getCategoryBySid(cate.getSid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.creComSucResult(catedto);
	}

	/**
	 * 通过条件查询品类信息
	 * 
	 * @Methods Name selectByParam
	 * @Create In 2015年7月27日 By duanzhaole
	 * @param cate
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/selectCategoryList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectCategoryList(@RequestBody PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		Page<SelectCategoryParamDto> pageparam = new Page<SelectCategoryParamDto>();
		pageparam.setPageSize(catepara.getPageSize());
		pageparam.setCurrentPage(catepara.getCurrenPage());
		SelectCategoryParamDto catedto = new SelectCategoryParamDto();
		BeanUtils.copyProperties(catepara, catedto);
		Page<SelectCategoryParamDto> pagelist = categoryService.selectListByParam(catedto,
				pageparam);

		return ResultUtil.creComSucResult(pagelist);
	}

	/**
	 * 添加品类基本信息
	 * 
	 * @Methods Name updateCategoryByParam
	 * @Create In 2015年7月28日 By duanzhaole
	 * @param catepara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/addCategoryByParam", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addCategoryByParam( PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "categoryinfocontroller/addCategoryByParam.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(catepara));
		return response;
		
	}

	/**
	 * 修改品类基本信息
	 * 
	 * @Methods Name updateCategoryByParam
	 * @Create In 2015年7月28日 By duanzhaole
	 * @param catepara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/updateCategoryByParam", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	@Deprecated
	public String updateCategoryByParam(PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "categoryinfocontroller/updateCategoryByParam.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(catepara));
		return response;
	}

	/**
	 * 通过管理分类的sid查询专柜商品信息
	 * 
	 * @Methods Name selectShoppeByCateSid
	 * @Create In 2015年8月6日 By duanzhaole
	 * @param catepara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/selectShoppeByCateSid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectShoppeByCateSid(@RequestBody PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		Page<HashMap<String, Object>> pageparam = new Page<HashMap<String, Object>>();
		pageparam.setPageSize(catepara.getPageSize());
		pageparam.setCurrentPage(catepara.getCurrenPage());
		SelectCategoryParamDto catedto = new SelectCategoryParamDto();
		// catepara.setSid(175L);
		BeanUtils.copyProperties(catepara, catedto);
		Page<HashMap<String, Object>> pagelist = categoryService.selectShoppeByCateSid(catedto,
				pageparam);
		return ResultUtil.creComSucResult(pagelist);
	}

	/**
	 * 通过工业sid查询spu信息
	 * 
	 * @Methods Name selectSPUByCateSid
	 * @Create In 2015年8月4日 By duanzhaole
	 * @param catepara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/selectSPUByCateSid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSPUByCateSid(PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		PcmProductDto catedto = new PcmProductDto();
		// catepara.setSid(177L);
		BeanUtils.copyProperties(catepara, catedto);
		Page<PcmProductDto> pagelist = categoryService.selectSPUByCategorySid(catedto.getSid());
		return ResultUtil.creComSucResult(pagelist);
	}

	/**
	 * 通过工业sid查询sku信息
	 * 
	 * @Methods Name selectSKUByCateSid
	 * @Create In 2015年8月4日 By duanzhaole
	 * @param catepara
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/selectSKUByCateSid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSKUByCateSid(PcmSelectCategoryPara catepara,
			HttpServletRequest request) {
		PcmProDetailDto catedto = new PcmProDetailDto();
		// catepara.setSid(177L);
		BeanUtils.copyProperties(catepara, catedto);
		Page<PcmProDetailDto> pagelist = categoryService.selectSKUByCategorySid(catedto.getSid());
		return ResultUtil.creComSucResult(pagelist);
	}

	/**
	 * 控制品类的删除,当品类下有子品类时,该品类不能删除
	 * 
	 * @param m
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/category/del", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String del(Model m, HttpServletRequest request,
			 PcmSelectCategoryPara catepara) {
		String url = PropertyUtil.getSystemUrl("pcm-admin")
				+ "categoryinfocontroller/category/del.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(catepara));
		return response;
	}

}
