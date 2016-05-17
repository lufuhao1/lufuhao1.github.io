package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.framework.exception.BleException;
import com.wangfj.core.utils.*;
import com.wangfj.product.brand.domain.entity.PcmBrand;
import com.wangfj.product.brand.domain.vo.PcmBrandDto;
import com.wangfj.product.brand.domain.vo.PcmBrandPartDto;
import com.wangfj.product.brand.domain.vo.PcmShopBrandDto;
import com.wangfj.product.brand.domain.vo.SelectPcmBrandPageDto;
import com.wangfj.product.brand.service.intf.IPcmBrandService;
import com.wangfj.product.core.controller.support.PcmBrandPara;
import com.wangfj.product.core.controller.support.PcmBrandRelationPara;
import com.wangfj.product.core.controller.support.PcmShopBrandPara;
import com.wangfj.product.core.controller.support.SelectPcmBrandPagePara;
import com.wangfj.product.core.controller.util.*;
import com.wangfj.util.Constants;
import com.wfj.platform.util.zookeeper.discovery.SpringMvcServiceProvider;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理Controller
 *
 * @Class Name PcmBrandController
 * @Author wangx
 * @Create In 2015年7月28日
 */
@Controller
@RequestMapping(value = "/pcmAdminBrand", produces = "application/json; charset=utf-8")
public class PcmBrandController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PcmBrandController.class);

    @Autowired
    private SpringMvcServiceProvider provider;

    @Autowired
    private IPcmBrandService brandService;

    /**
     * 创建品牌
     *
     * @param brandPara
     * @param request
     * @return String
     * @Methods Name addPcmBrand
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/addPcmBrand", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addPcmBrand(@RequestBody @Valid PcmBrandPara brandPara, HttpServletRequest request) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/addPcmBrand.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(brandPara));
        return response;

    }

    /**
     * 修改品牌
     *
     * @param brandPara
     * @param request
     * @return String
     * @Methods Name updateBrand
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/updateBrand", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateBrand(@RequestBody @Valid PcmBrandPara brandPara, HttpServletRequest request) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/updateBrand.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(brandPara));
        return response;

    }

    /**
     * 分页查询品牌
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findPageBrand
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/findPageBrand", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findPageBrand(@RequestBody SelectPcmBrandPagePara para,
                                             HttpServletRequest request) {

        SelectPcmBrandPageDto dto = new SelectPcmBrandPageDto();

        String brandName = para.getBrandName();
        String brandSid = para.getBrandSid();
        Integer brandType = para.getBrandType();
        Integer currentPage = para.getCurrentPage();
        Integer pageSize = para.getPageSize();
        String parentSid = para.getParentSid();
        Integer shopType = para.getShopType();
        Long sid = para.getSid();
        String spell = para.getSpell();

        if (StringUtils.isNotEmpty(brandName)) {
            dto.setBrandName(brandName);
        }

        if (StringUtils.isNotEmpty(brandSid)) {
            dto.setBrandSid(brandSid);
        }

        if (brandType != null) {
            dto.setBrandType(brandType);
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

        if (StringUtils.isNotEmpty(parentSid)) {
            dto.setParentSid(Long.parseLong(parentSid));
        }

        if (shopType != null) {
            dto.setShopType(shopType);
        }

        if (sid != null) {
            dto.setSid(sid);
        }

        if (StringUtils.isNotEmpty(spell)) {
            dto.setSpell(spell);
        }

        Page<PcmBrandDto> page = brandService.findPageBrand(dto);

        return ResultUtil.creComSucResult(page);
    }

    /**
     * 查询品牌（模糊）
     *
     * @param brandPagePara
     * @param request
     * @return Map<String,Object>
     * @Methods Name findListBrand
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/findListBrand", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findListBrand(@RequestBody SelectPcmBrandPagePara brandPagePara,
                                             HttpServletRequest request) {

        SelectPcmBrandPageDto selectDto = new SelectPcmBrandPageDto();

        try {
            BeanUtils.copyProperties(selectDto, brandPagePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (brandPagePara.getSid() == null) {
            selectDto.setSid(null);
        }
        if (brandPagePara.getBrandType() == null) {
            selectDto.setBrandType(null);
        }
        if (brandPagePara.getShopType() == null) {
            selectDto.setShopType(null);
        }
        if (brandPagePara.getParentSid() == null) {
            selectDto.setParentSid(null);
        }

        List<PcmBrandDto> brandDtoList = new ArrayList<PcmBrandDto>();
        try {
            brandDtoList = brandService.findListBrand(selectDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(brandDtoList);
    }

    /**
     * 查询品牌部分信息（模糊）
     *
     * @param brandPagePara
     * @return Map<String,Object>
     * @Methods Name findListBrand
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/findListBrandPartInfo", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findListBrandPartInfo(
            @RequestBody SelectPcmBrandPagePara brandPagePara) {
        SelectPcmBrandPageDto selectDto = new SelectPcmBrandPageDto();
        org.springframework.beans.BeanUtils.copyProperties(brandPagePara, selectDto);
        Page<PcmBrandPartDto> page = brandService.findListBrandPartInfo(selectDto);
        return ResultUtil.creComSucResult(page);
    }

    /**
     * 添加/修改门店品牌与集团品牌的关系
     *
     * @param pcmBrandRelationPara
     * @param request
     * @return String
     * @Methods Name updateRelation
     * @Create In 2015-8-12 By wangx
     */
    @RequestMapping(value = "/updateRelation", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updateRelation(@RequestBody @Valid PcmBrandRelationPara pcmBrandRelationPara,
                                 HttpServletRequest request) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/updateRelation.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmBrandRelationPara));
        return response;

    }

    /**
     * 批量添加门店品牌与集团品牌的关系
     *
     * @param brandRelationParaList
     * @param request
     * @return String
     * @Methods Name addRelationList
     * @Create In 2015-9-14 By wangxuan
     */
    @RequestMapping(value = "/addRelationList", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addRelationList(
            @RequestBody @Valid List<PcmBrandRelationPara> brandRelationParaList,
            HttpServletRequest request) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/addRelationList.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(brandRelationParaList));
        return response;

    }

    /**
     * 删除门店品牌与集团品牌的关系
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name deleteRelation
     * @Create In 2015-8-18 By wangx
     */
    @RequestMapping(value = "/deleteRelation", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteRelation(@RequestBody Map<String, Object> para, HttpServletRequest request) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/deleteRelation.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;

    }

    /**
     * 分页查询门店品牌及其集团品牌
     *
     * @param brandPagePara
     * @param request
     * @return Map<String,Object>
     * @Methods Name findPageBrandAndBrandGroup
     * @Create In 2015-8-17 By wangx
     */
    @RequestMapping(value = "/findPageBrandAndBrandGroup", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findPageBrandAndBrandGroup(
            @RequestBody @Valid SelectPcmBrandPagePara brandPagePara, HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("brandType", Constants.PUBLIC_1);

        if (brandPagePara.getSid() != null) {
            paramMap.put("sid", brandPagePara.getSid());
        }
        if (brandPagePara.getShopType() != null) {
            paramMap.put("shopType", brandPagePara.getShopType());
        }
        if (brandPagePara.getStatus() != null) {
            paramMap.put("status", brandPagePara.getStatus());
        }
        if (brandPagePara.getCurrentPage() != null) {
            paramMap.put("currentPage", brandPagePara.getCurrentPage());
        } else {
            paramMap.put("currentPage", 1);
        }
        if (brandPagePara.getPageSize() != null) {
            paramMap.put("pageSize", brandPagePara.getPageSize());
        } else {
            paramMap.put("pageSize", 10);
        }
        if (StringUtils.isNotEmpty(brandPagePara.getBrandSid())) {
            paramMap.put("brandSid", brandPagePara.getBrandSid());
        }
        if (StringUtils.isNotEmpty(brandPagePara.getBrandName())) {
            paramMap.put("brandName", brandPagePara.getBrandName());
        }
        if (StringUtils.isNotEmpty(brandPagePara.getSpell())) {
            paramMap.put("spell", brandPagePara.getSpell());
        }

        Page<PcmBrandDto> page = brandService.findPageBrandAndBrandGroup(paramMap);

        return ResultUtil.creComSucResult(page);
    }

    /**
     * 删除门店品牌
     *
     * @param paraMap
     * @return String
     * @Methods Name deleteBrand
     * @Create In 2015-8-5 By wangx
     */
    @RequestMapping(value = "/deleteBrand", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteBrand(@RequestBody Map<String, Object> paraMap) {

        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/pcmAdminBrand/deleteBrand.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(paraMap));
        return response;

    }

    /**
     * 查询某集团品牌下的门店品牌（带分页）
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findPageBrandByGroupBrandSid
     * @Create In 2015-8-10 By wangx
     */
    @RequestMapping(value = "/findPageBrandByParentSid", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findPageBrandByParentSid(@RequestBody Map<String, Object> para,
                                                        HttpServletRequest request) {

        Page<PcmBrandDto> pageBrand = new Page<PcmBrandDto>();
        SelectPcmBrandPageDto pageDto = new SelectPcmBrandPageDto();

        String currentPage = para.get("currentPage") + "";
        String pageSize = para.get("pageSize") + "";
        if (StringUtils.isNotEmpty(currentPage)) {
            pageDto.setCurrentPage(Integer.parseInt(currentPage));
        } else {
            pageDto.setCurrentPage(1);
        }
        if (StringUtils.isNotEmpty(pageSize)) {
            pageDto.setPageSize(Integer.parseInt(pageSize));
        } else {
            pageDto.setPageSize(10);
        }

        String sid = para.get("sid") + "";
        if (StringUtils.isNotEmpty(sid)) {
            pageDto.setParentSid(Long.parseLong(sid));
            pageDto.setBrandType(Constants.PUBLIC_1);
            pageBrand = brandService.findPageBrand(pageDto);
        } else {
            logger.error("查询某集团品牌下的门店品牌时传入的集团品牌sid不能为空");
            throw new BleException(
                    ComErrorCodeConstants.ErrorCode.BRANDGROUP_SID_IS_NULL.getErrorCode(),
                    ComErrorCodeConstants.ErrorCode.BRANDGROUP_SID_IS_NULL.getMemo());
        }

        return ResultUtil.creComSucResult(pageBrand);
    }

    /**
     * 查询某集团品牌下的门店品牌
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findListBrandByParentSid
     * @Create In 2015-9-14 By wangxuan
     */
    @RequestMapping(value = "/findListBrandByParentSid", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findListBrandByParentSid(@RequestBody Map<String, Object> para,
                                                        HttpServletRequest request) {

        String sid = para.get("sid") + "";
        List<PcmBrandDto> brandList = new ArrayList<PcmBrandDto>();
        if (StringUtils.isNotEmpty(sid)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("parentSid", Long.parseLong(sid));
            paramMap.put("brandType", Constants.PUBLIC_1);
            try {
                brandList = brandService.findListBrand(paramMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("查询某集团品牌下的门店品牌时传入的集团品牌sid不能为空");
            throw new BleException(
                    ComErrorCodeConstants.ErrorCode.BRANDGROUP_SID_IS_NULL.getErrorCode(),
                    ComErrorCodeConstants.ErrorCode.BRANDGROUP_SID_IS_NULL.getMemo());
        }

        return ResultUtil.creComSucResult(brandList);
    }

    /**
     * 查询所有没有关联集团品牌的门店品牌
     *
     * @param request
     * @return Map<String,Object>
     * @Methods Name findListBrandWithoutRelation
     * @Create In 2015-9-14 By wangxuan
     */
    @RequestMapping(value = "/findListBrandWithoutRelation", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> findListBrandWithoutRelation(@RequestBody HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 没有关联集团品牌的门店品牌parentSid为0
        paramMap.put("parentSid", new Long(Constants.PUBLIC_0));
        paramMap.put("brandType", Constants.PUBLIC_1);
        List<PcmBrandDto> brandList = new ArrayList<PcmBrandDto>();
        try {
            brandList = brandService.findListBrand(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(brandList);
    }

    /**
     * 根据门店的sid和集团品牌的sid查询门店品牌
     *
     * @param shopBrandPara
     * @param request
     * @return Map<String,Object>
     * @Methods Name getListBrandByShopSidAndParentSid
     * @Create In 2015-9-21 By wangxuan
     */
    @RequestMapping(value = "/getListBrandByShopSidAndParentSid", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getListBrandByShopSidAndParentSid(
            @RequestBody PcmShopBrandPara shopBrandPara, HttpServletRequest request) {

        String parentSid = shopBrandPara.getParentSid();
        String shopSid = shopBrandPara.getShopSid();
        String shopCode = shopBrandPara.getShopCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(parentSid)) {
            paramMap.put("parentSid", Long.parseLong(parentSid));
        }
        if (StringUtils.isNotEmpty(shopSid)) {
            paramMap.put("shopSid", shopSid);
        }
        if (StringUtils.isNotEmpty(shopCode)) {
            paramMap.put("shopCode", shopCode);
        }

        List<PcmBrandDto> brandList = new ArrayList<PcmBrandDto>();
        try {
            brandList = brandService.getListBrandByShopSidAndParentSid(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(brandList);
    }

    /**
     * 根据门店查询门店品牌
     *
     * @param shopBrandPara
     * @return Map<String,Object>
     * @Methods Name getPageBrandFromShopBrandRelation
     * @Create In 2015-12-16 By wangxuan
     */
    @RequestMapping(value = "/getPageBrandFromShopBrandRelation", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getPageBrandFromShopBrandRelation(
            @RequestBody PcmShopBrandPara shopBrandPara) {

        PcmShopBrandDto dto = new PcmShopBrandDto();
        org.springframework.beans.BeanUtils.copyProperties(shopBrandPara, dto);
        Page<PcmBrandDto> page = brandService.getPageBrandFromShopBrandRelation(dto);

        return ResultUtil.creComSucResult(page);
    }

    /**
     * 根据skuSid和门店sid查询门店品牌
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name getListBrandByShopSidAndSkuSid
     * @Create In 2015-11-19 By wangxuan
     */
    @RequestMapping(value = "/getListBrandByShopSidAndSkuSid", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getListBrandByShopSidAndSkuSid(@RequestBody Map<String, Object> para) {

        String shopSid = para.get("shopSid") + "";
        String skuSid = para.get("skuSid") + "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(shopSid)) {
            paramMap.put("shopSid", shopSid.trim());
        }
        if (StringUtils.isNotEmpty(skuSid)) {
            paramMap.put("skuSid", skuSid.trim());
        }

        List<PcmBrand> brandList = brandService.getListBrandByShopSidAndSkuSid(paramMap);
        return ResultUtil.creComSucResult(brandList);
    }

    /**
     * 从搜索查询集团品牌
     *
     * @param para
     * @return String
     * @Methods Name queryBrandGroupListByName
     * @Create In 2016-03-29 By wangxuan
     */
    @RequestMapping(value = "/queryBrandGroupListByName", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public String queryBrandGroupListByName(@RequestBody Map<String, Object> para) {

        String prefix = para.get("prefix") + "";
        Map<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(prefix)) {
            paramMap.put("prefix", prefix.trim());
        }
        try {
            String serviceAddress = provider.provideServiceAddress("pcm-list-brand");
//            String json = HttpUtil.HttpGetByUtfNoMenthod(serviceAddress, "", paramMap);
            String json = com.wangfj.product.core.controller.util.HttpClientUtil.doPost(serviceAddress, paramMap, "UTF-8");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            para.clear();
            para.put(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), "从搜索查询集团品牌获取地址失败！");
            return para.toString();
        }
    }


}
