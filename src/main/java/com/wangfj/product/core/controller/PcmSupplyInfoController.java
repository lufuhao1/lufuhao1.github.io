package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.*;
import com.wangfj.product.core.controller.support.PcmSupplyInfoInAdminPara;
import com.wangfj.product.core.controller.support.PcmSupplyInfoQueryPara;
import com.wangfj.product.core.controller.support.SelectPcmSupplyInfoPagePara;
import com.wangfj.product.core.controller.support.SelectPcmSupplyInfoPara;
import com.wangfj.product.supplier.domain.vo.PcmSupplyInfoDto;
import com.wangfj.product.supplier.domain.vo.PcmSupplyInfoPartDto;
import com.wangfj.product.supplier.domain.vo.PcmSupplyInfoQueryDto;
import com.wangfj.product.supplier.domain.vo.PcmSupplyInfoResultDto;
import com.wangfj.product.supplier.service.intf.IPcmSupplyInfoService;
import com.wfj.platform.util.zookeeper.discovery.SpringMvcServiceProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商信息管理
 *
 * @Class Name PcmSupplyInfoController
 * @Author wangx
 * @Create In 2015-8-8
 */
@Controller
@RequestMapping(value = "/pcmAdminSupplyInfo", produces = "application/json;charset=utf-8")
public class PcmSupplyInfoController extends BaseController {

    @Autowired
    private IPcmSupplyInfoService supplyInfoService;

    @Autowired
    private SpringMvcServiceProvider provider;

    /**
     * 从搜索查询供应商列表
     *
     * @param para
     * @return String
     * @Methods Name findListSupplierBySearch
     * @Create In 2016-04-01 By wangxuan
     */
    @RequestMapping(value = "/findListSupplierBySearch", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public String findListSupplierBySearch(@RequestBody Map<String, Object> para) {

        String prefix = para.get("prefix") + "";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(prefix)) {
            paramMap.put("prefix", prefix.trim());
        }
        String storeCode = para.get("storeCode") + "";
        if (StringUtils.isNotEmpty(storeCode)) {
            paramMap.put("storeCode", storeCode.trim());
        }
        try {
            String serviceAddress = provider.provideServiceAddress("pcm-list-supplier");
            if (StringUtils.isNotEmpty(serviceAddress)) {
                String json = HttpUtil.HttpGetByUtfNoMenthod(serviceAddress, "", paramMap);
                return json;
            } else {
                para.clear();
                para.put(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), "从搜索查询供应商获取地址失败！");
                return para.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            para.clear();
            para.put(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), "从搜索查询供应商获取地址失败！");
            return para.toString();
        }
    }

    /**
     * 查询专柜的供应商信息
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findListSupplierByShoppeParam
     * @Create In 2015-12-11 By wangxuan
     */
    @RequestMapping(value = "/findListSupplierByShoppeParam", method = {RequestMethod.POST,
            RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findListSupplierByShoppeParam(
            @RequestBody PcmSupplyInfoQueryPara para) {

        PcmSupplyInfoQueryDto dto = new PcmSupplyInfoQueryDto();
        BeanUtils.copyProperties(para, dto);
        List<PcmSupplyInfoResultDto> dtoList = supplyInfoService.findListSupplierByShoppeParam(dto);

        return ResultUtil.creComSucResult(dtoList);
    }

    /**
     * 查询供应商(页面查询)
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findListSupplier
     * @Create In 2015-12-8 By wangxuan
     */
    @RequestMapping(value = "/findListSupplier", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findListSupplier(@RequestBody PcmSupplyInfoQueryPara para) {

        PcmSupplyInfoQueryDto dto = new PcmSupplyInfoQueryDto();
        BeanUtils.copyProperties(para, dto);
        List<PcmSupplyInfoPartDto> dtoList = supplyInfoService.findListSupplier(dto);

        return ResultUtil.creComSucResult(dtoList);
    }

    /**
     * 查询供应商（条件可以加门店的sid）
     *
     * @param supplyInfoPagePara
     * @param request
     * @return Map<String,Object>
     * @Methods Name findListSullyInfoFuzzy
     * @Create In 2015-8-21 By wangxuan
     */
    @RequestMapping(value = "/findListSullyInfoFuzzy", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> findListSullyInfoFuzzy(
            @RequestBody SelectPcmSupplyInfoPagePara supplyInfoPagePara, HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        String bizCertificateNo = supplyInfoPagePara.getBizCertificateNo();
        String currentPage = supplyInfoPagePara.getCurrentPage();
        String keySupplier = supplyInfoPagePara.getKeySupplier();
        String orgCode = supplyInfoPagePara.getOrgCode();
        String pageSize = supplyInfoPagePara.getPageSize();
        String shopSid = supplyInfoPagePara.getShopSid();
        String shopSid_ = supplyInfoPagePara.getShopSid_();
        String sid = supplyInfoPagePara.getSid();
        String status = supplyInfoPagePara.getStatus();
        String supplyCode = supplyInfoPagePara.getSupplyCode();
        String supplyName = supplyInfoPagePara.getSupplyName();
        String supplyType = supplyInfoPagePara.getSupplyType();

        if (StringUtils.isNotEmpty(bizCertificateNo)) {
            paramMap.put("bizCertificateNo", bizCertificateNo);
        }
        if (StringUtils.isNotEmpty(currentPage)) {
            paramMap.put("currentPage", currentPage);
        }
        if (StringUtils.isNotEmpty(keySupplier)) {
            paramMap.put("keySupplier", keySupplier);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            paramMap.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(pageSize)) {
            paramMap.put("pageSize", pageSize);
        }
        if (StringUtils.isNotEmpty(shopSid)) {
            paramMap.put("shopSid", shopSid);
        }
        if (StringUtils.isNotEmpty(shopSid_)) {
            paramMap.put("shopSid_", shopSid_);
        }
        if (StringUtils.isNotEmpty(sid)) {
            paramMap.put("sid", sid);
        }
        if (StringUtils.isNotEmpty(status)) {
            paramMap.put("status", status);
        }
        if (StringUtils.isNotEmpty(supplyCode)) {
            paramMap.put("supplyCode", supplyCode);
        }
        if (StringUtils.isNotEmpty(supplyName)) {
            paramMap.put("supplyName", supplyName);
        }
        if (StringUtils.isNotEmpty(supplyType)) {
            paramMap.put("supplyType", supplyType);
        }

        List<PcmSupplyInfoDto> supplyInfoDtoList = new ArrayList<PcmSupplyInfoDto>();
        try {
            supplyInfoDtoList = supplyInfoService.findListSullyInfoFuzzy(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(supplyInfoDtoList);
    }

    /**
     * 查询供应商(多条件，模糊)
     *
     * @param supplyInfoPara
     * @param request
     * @return Map<String,Object>
     * @Methods Name findListSupplyInfo
     * @Create In 2015-9-8 By wangxuan
     */
    @RequestMapping(value = "/findListSupplyInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findListSupplyInfo(
            @RequestBody SelectPcmSupplyInfoPara supplyInfoPara, HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        String bizCertificateNo = supplyInfoPara.getBizCertificateNo();
        String currentPage = supplyInfoPara.getCurrentPage();
        String keySupplier = supplyInfoPara.getKeySupplier();
        String orgCode = supplyInfoPara.getOrgCode();
        String pageSize = supplyInfoPara.getPageSize();
        String shopSid = supplyInfoPara.getShopSid();
        String sid = supplyInfoPara.getSid();
        String status = supplyInfoPara.getStatus();
        String supplyCode = supplyInfoPara.getSupplyCode();
        String supplyName = supplyInfoPara.getSupplyName();
        String supplyType = supplyInfoPara.getSupplyType();

        if (StringUtils.isNotEmpty(bizCertificateNo)) {
            paramMap.put("bizCertificateNo", bizCertificateNo);
        }
        if (StringUtils.isNotEmpty(currentPage)) {
            paramMap.put("currentPage", currentPage);
        }
        if (StringUtils.isNotEmpty(keySupplier)) {
            paramMap.put("keySupplier", keySupplier);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            paramMap.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(pageSize)) {
            paramMap.put("pageSize", pageSize);
        }
        if (StringUtils.isNotEmpty(shopSid)) {
            paramMap.put("shopSid", shopSid);
        }
        if (StringUtils.isNotEmpty(sid)) {
            paramMap.put("sid", sid);
        }
        if (StringUtils.isNotEmpty(status)) {
            paramMap.put("status", status);
        }
        if (StringUtils.isNotEmpty(supplyCode)) {
            paramMap.put("supplyCode", supplyCode);
        }
        if (StringUtils.isNotEmpty(supplyName)) {
            paramMap.put("supplyName", supplyName);
        }
        if (StringUtils.isNotEmpty(supplyType)) {
            paramMap.put("supplyType", supplyType);
        }

        List<PcmSupplyInfoDto> supplyInfoDtoList = new ArrayList<PcmSupplyInfoDto>();
        try {
            supplyInfoDtoList = supplyInfoService.findListSupplyInfo(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(supplyInfoDtoList);
    }

    /**
     * 分页查询供应商(模糊)
     *
     * @param supplyInfoPagePara
     * @param request
     * @return Map<String,Object>
     * @Methods Name findPageSullyInfoFuzzy
     * @Create In 2015-8-14 By wangx
     */
    @RequestMapping(value = "/findPageSullyInfoFuzzy", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> findPageSullyInfoFuzzy(
            @RequestBody @Valid SelectPcmSupplyInfoPagePara supplyInfoPagePara,
            HttpServletRequest request) {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        String bizCertificateNo = supplyInfoPagePara.getBizCertificateNo();
        String currentPage = supplyInfoPagePara.getCurrentPage();
        String keySupplier = supplyInfoPagePara.getKeySupplier();
        String orgCode = supplyInfoPagePara.getOrgCode();
        String pageSize = supplyInfoPagePara.getPageSize();
        String shopSid = supplyInfoPagePara.getShopSid();
        String sid = supplyInfoPagePara.getSid();
        String status = supplyInfoPagePara.getStatus();
        String supplyCode = supplyInfoPagePara.getSupplyCode();
        String supplyName = supplyInfoPagePara.getSupplyName();
        String supplyType = supplyInfoPagePara.getSupplyType();
        String businessPattern = supplyInfoPagePara.getBusinessPattern();
        String apartOrder = supplyInfoPagePara.getApartOrder();

        if (StringUtils.isNotEmpty(bizCertificateNo)) {
            paramMap.put("bizCertificateNo", bizCertificateNo);
        }
        if (StringUtils.isNotEmpty(currentPage)) {
            paramMap.put("currentPage", currentPage);
        }
        if (StringUtils.isNotEmpty(keySupplier)) {
            paramMap.put("keySupplier", keySupplier);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            paramMap.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(pageSize)) {
            paramMap.put("pageSize", pageSize);
        }
        if (StringUtils.isNotEmpty(shopSid)) {
            paramMap.put("shopSid", shopSid);
        }
        if (StringUtils.isNotEmpty(sid)) {
            paramMap.put("sid", sid);
        }
        if (StringUtils.isNotEmpty(status)) {
            paramMap.put("status", status);
        }
        if (StringUtils.isNotEmpty(supplyCode)) {
            paramMap.put("supplyCode", supplyCode);
        }
        if (StringUtils.isNotEmpty(supplyName)) {
            paramMap.put("supplyName", supplyName);
        }
        if (StringUtils.isNotEmpty(supplyType)) {
            paramMap.put("supplyType", supplyType);
        }
        if (StringUtils.isNotEmpty(businessPattern)) {
            paramMap.put("businessPattern", businessPattern);
        }
        if (StringUtils.isNotEmpty(apartOrder)) {
            paramMap.put("apartOrder", apartOrder);
        }

        Page<PcmSupplyInfoDto> pageSupplyInfoDto = new Page<PcmSupplyInfoDto>();
        try {
            pageSupplyInfoDto = supplyInfoService.findPageSullyInfoFuzzy(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(pageSupplyInfoDto);
    }

    /**
     * 供应商分页查询(非模糊)
     *
     * @param paramMap
     * @return Map<String,Object>
     * @Methods Name findPageSupplyInfo
     * @Create In 2015-8-14 By wangx
     */
    @RequestMapping(value = "/findPageSupplyInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> findPageSupplyInfo(@RequestBody Map<String, Object> paramMap) {

        Page<PcmSupplyInfoDto> pageSupplyInfoDto = new Page<PcmSupplyInfoDto>();
        try {
            pageSupplyInfoDto = supplyInfoService.findPageSullyInfo(paramMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ResultUtil.creComSucResult(pageSupplyInfoDto);
    }

    /**
     * 添加单个供应商信息
     *
     * @param supplyInfoPara
     * @return String
     * @Methods Name addSupplyInfo
     * @Create In 2015-8-8 By wangx
     */
    @RequestMapping(value = "/addSupplyInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addSupplyInfo(@RequestBody @Valid PcmSupplyInfoInAdminPara supplyInfoPara) {

        String url = PropertyUtil.getSystemUrl("pcm-admin")
                + "/pcmAdminSupplyInfo/addSupplyInfo.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(supplyInfoPara));
        return response;

    }

    /**
     * 更新供应商信息
     *
     * @return String
     * @Methods Name updateSupplyInfo
     * @Create In 2015年7月30日 By wangxiang
     */
    @RequestMapping(value = "/updateSupplyInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateSupplyInfo(@RequestBody PcmSupplyInfoInAdminPara supplyInfoPara) {

        String url = PropertyUtil.getSystemUrl("pcm-admin")
                + "/pcmAdminSupplyInfo/updateSupplyInfo.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(supplyInfoPara));
        return response;

    }

    /**
     * 供应商信息删除(状态的变更)
     *
     * @param para
     * @return String
     * @Methods Name updateSupplyInfoStatus
     * @Create In 2015-8-17 By wangx
     */
    @RequestMapping(value = "/updateSupplyInfoStatus", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateSupplyInfoStatus(@RequestBody Map<String, Object> para) {

        String url = PropertyUtil.getSystemUrl("pcm-admin")
                + "/pcmAdminSupplyInfo/updateSupplyInfoStatus.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;

    }

}
