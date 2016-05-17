package com.wangfj.product.core.controller;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.*;
import com.wangfj.product.core.controller.support.PcmShoppeAUPara;
import com.wangfj.product.core.controller.support.PcmShoppePara;
import com.wangfj.product.core.controller.support.PcmShoppeQueryPara;
import com.wangfj.product.core.controller.support.SelectShoppePara;
import com.wangfj.product.organization.domain.vo.*;
import com.wangfj.product.organization.service.intf.IPcmShoppeService;
import com.wangfj.util.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
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
 * 专柜管理
 *
 * @Class Name PcmShoppeController
 * @Author yedong
 * @Create In 2015年7月9日
 */
@Controller
@RequestMapping(value = "/shoppe", produces = "application/json;charset=utf-8")
public class PcmShoppeMainController extends BaseController {

    @Autowired
    private IPcmShoppeService pcmShoppeService;

    /**
     * 专柜数据条件查询
     *
     * @param map
     * @return String
     * @Methods Name findShoppeByParamFromPcm
     * @Create In 2015年7月14日 By yedong
     */
    @RequestMapping("/findShoppeByParamFromPcm")
    @ResponseBody
    public String findShoppeByParamFromPcm(@RequestBody Map<String, Object> map) {
        Map<String, Object> shoppeByParam = new HashMap<String, Object>();

        Page<PushCounterDto> page = new Page<PushCounterDto>();
        if (map.get("pageSize") != null) {
            page.setPageSize((Integer) map.get("pageSize"));
        }
        if (map.get("currentPage") != null) {
            page.setCurrentPage((Integer) map.get("currentPage"));
        }

        shoppeByParam.put("shoppeCode", map.get("code"));
        shoppeByParam.put("shoppeName", map.get("name"));
        shoppeByParam.put("orgCode", map.get("storeCode"));
        shoppeByParam.put("supplyCode", map.get("supplierErpCode"));
        shoppeByParam.put("floorCode", map.get("floorCode"));
        shoppeByParam.put("floorName", map.get("floorName"));
        shoppeByParam.put("shoppeGroup", map.get("counterGroup"));
        shoppeByParam.put("industryName", map.get("businessType"));
        shoppeByParam.put("shoppeType", map.get("counterType"));
        shoppeByParam.put("goodManageType", map.get("counterInventoryType"));
        shoppeByParam.put("shippingPoint", map.get("counterShippingPoint"));
        shoppeByParam.put("refCounter", map.get("refCounter"));
        shoppeByParam.put("shoppeStatus", map.get("counterStatus"));

        Integer count = pcmShoppeService.getCountByParam(shoppeByParam);

        page.setCount(count);
        shoppeByParam.put("start", page.getStart());
        shoppeByParam.put("limit", page.getLimit());

        List<Map<String, Object>> pushCounter = pcmShoppeService
                .findShoppeByParamFromPcm(shoppeByParam);

        List<PushCounterDto> list = new ArrayList<PushCounterDto>();

        for (int i = Constants.PUBLIC_0; i < pushCounter.size(); i++) {
            PushCounterDto dto = new PushCounterDto();
            Map<String, Object> shoppeMap = new HashMap<String, Object>();
            shoppeMap.put("code", pushCounter.get(i).get("shoppeCode"));
            shoppeMap.put("name", pushCounter.get(i).get("shoppeName"));
            shoppeMap.put("storeCode", pushCounter.get(i).get("orgCode"));
            shoppeMap.put("supplierErpCode", pushCounter.get(i).get("supplyCode"));
            shoppeMap.put("floorCode", pushCounter.get(i).get("floorCode"));
            shoppeMap.put("floorName", pushCounter.get(i).get("floorName"));
            shoppeMap.put("counterGroup", pushCounter.get(i).get("shoppeGroup"));
            shoppeMap.put("businessType", pushCounter.get(i).get("industryName"));
            shoppeMap.put("counterType", pushCounter.get(i).get("shoppeType"));
            shoppeMap.put("counterInventoryType", pushCounter.get(i).get("goodManageType"));
            shoppeMap.put("counterShippingPoint", pushCounter.get(i).get("shippingPoint"));
            shoppeMap.put("refCounter", pushCounter.get(i).get("refCounter"));
            shoppeMap.put("counterStatus", pushCounter.get(i).get("shoppeStatus"));
            shoppeMap.put("isNegInventory", pushCounter.get(i).get("negIiveStock"));

            try {
                BeanUtils.copyProperties(dto, shoppeMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            list.add(dto);
        }
        return JsonUtil.getJSONString(list);
    }

    @ResponseBody
    @RequestMapping("/deleteShoppeByParamFrom")
    public String deleteShoppeByParamFrom(@RequestBody @Valid PcmShoppePara para,
                                          HttpServletRequest request) {
        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/shoppe/deleteShoppeByParamFrom.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 添加专柜
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name saveShoppe
     * @Create In 2015-12-4 By wangxuan
     */
    @ResponseBody
    @RequestMapping(value = "/addShoppe", method = {RequestMethod.POST, RequestMethod.GET})
    public String addShoppe(@RequestBody @Valid PcmShoppeAUPara para, HttpServletRequest request) {
        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/shoppe/addShoppe.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 修改专柜
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name modifyShoppe
     * @Create In 2015-12-4 By wangxuan
     */
    @ResponseBody
    @RequestMapping(value = "/modifyShoppe", method = {RequestMethod.POST, RequestMethod.GET})
    public String modifyShoppe(@RequestBody @Valid PcmShoppeAUPara para, HttpServletRequest request) {
        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/shoppe/modifyShoppe.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 根据sid查询专柜信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findShoppeBySid
     * @Create In 2015年7月30日 By wuxiong
     */
    @ResponseBody
    @RequestMapping("/findShoppeBySid")
    public Map<String, Object> findShoppeBySid(@RequestBody @Valid PcmShoppePara para,
                                               HttpServletRequest request) {
        PcmGetShoppeDto dto = new PcmGetShoppeDto();
        try {
            BeanUtils.copyProperties(dto, para);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        PcmGetShoppeDto shoppeDto = pcmShoppeService.selectShoppeSid(dto.getSid());
        return ResultUtil.creComSucResult(shoppeDto);
    }

    /**
     * 条件查询分页显示专柜信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findShoppeFromPCM
     * @Create In 2015年7月30日 By wuxiong
     */
    @ResponseBody
    @RequestMapping("/findShoppeFromPCM")
    public Map<String, Object> findShoppeFromPCM(@RequestBody SelectShoppePara para,
                                                 HttpServletRequest request) {
        Page<PcmGetShoppeDto> pageorg = new Page<PcmGetShoppeDto>();
        // pageorg.setCurrentPage(para.getCurrentPage());
        // pageorg.setPageSize(para.getPageSize());
        PcmGetShoppeDto dto = new PcmGetShoppeDto();
        try {
            BeanUtils.copyProperties(dto, para);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Page<HashMap<String, Object>> pageShoppe = pcmShoppeService.selectPageShoppe(dto, pageorg);
        return ResultUtil.creComSucResult(pageShoppe);
    }

    /**
     * 分页查询
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findPageShoppe
     * @Create In 2015-8-24 By niuzhifan
     */
    @RequestMapping(value = "/findPageShoppe", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findPageShoppe(@RequestBody PcmShoppeQueryPara para,
                                              HttpServletRequest request) {

        SelectPcmShoppeDto dto = new SelectPcmShoppeDto();
        org.springframework.beans.BeanUtils.copyProperties(para, dto);

        Page<PcmShoppeResultDto> page = pcmShoppeService.findPageShoppe(dto);

//        //查询集货仓
//        List<PcmShoppeResultDto> resultList = page.getList();
//        String url = PropertyUtil.getSystemUrl("shoppe.shoppeShippingPoint_url");
//        // 查询参数
//        if (resultList != null && !resultList.isEmpty()) {
//            for (PcmShoppeResultDto resultDto : resultList) {
//                String shoppeShippingPoint = resultDto.getShoppeShippingPoint();
//                if (StringUtils.isNotEmpty(shoppeShippingPoint)) {
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("StoreCode", resultDto.getShopCode());
//                    String json = HttpUtil.doPost(url, JsonUtil.getJSONString(map));
//                    if (StringUtils.isNotEmpty(json)) {
//                        JSONObject fromObject = JSONObject.fromObject(json);
//                        String status = (String) fromObject.get("status");
//                        if ("true".equals(status)) {
//                            List<Map<String, Object>> warehouseList = (List<Map<String, Object>>) fromObject
//                                    .get("warehouses");
//                            if (warehouseList != null && !warehouseList.isEmpty()) {
//                                for (Map<String, Object> hashMap : warehouseList) {
//                                    String warehouse_id = hashMap.get("warehouse_id") + "";
//                                    if (shoppeShippingPoint.equals(warehouse_id)) {
//                                        resultDto.setShoppeShippingPointName(hashMap
//                                                .get("warehouse_name") + "");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return ResultUtil.creComSucResult(page);
    }

    /**
     * 查询专柜List
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findListShoppe
     * @Create In 2016-2-1 By wangxuan
     */
    @RequestMapping(value = "/findShoppeList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findShoppeList(@RequestBody PcmShoppeQueryPara para) {

        SelectPcmShoppeDto dto = new SelectPcmShoppeDto();

        org.springframework.beans.BeanUtils.copyProperties(para, dto);

        List<PcmShoppeResultDto> shoppeList = pcmShoppeService.findShoppeList(dto);

        return ResultUtil.creComSucResult(shoppeList);
    }

    /**
     * 根据门店和供应商查询专柜（页面添加专柜商品）
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name findListShoppeForAddShoppeProduct
     * @Create In 2015-12-21 By wangxuan
     */
    @RequestMapping(value = "/findListShoppeForAddShoppeProduct", method = {RequestMethod.GET,
            RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> findListShoppeForAddShoppeProduct(
            @RequestBody PcmShoppeQueryPara para) {

        SelectPcmShoppeDto dto = new SelectPcmShoppeDto();
        org.springframework.beans.BeanUtils.copyProperties(para, dto);

        List<PcmShoppeResultDto> list = pcmShoppeService.findListShoppeForAddShoppeProduct(dto);

        return ResultUtil.creComSucResult(list);
    }

    /**
     * 查询专柜List(专柜商品换专柜)
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findListShoppe
     * @Create In 2015-10-16 By wangxuan
     */
    @RequestMapping(value = "/findListShoppe", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findListShoppe(@RequestBody PcmShoppeQueryPara para,
                                              HttpServletRequest request) {

        SelectPcmShoppeDto dto = new SelectPcmShoppeDto();

        org.springframework.beans.BeanUtils.copyProperties(para, dto);

        // 正常的专柜
        dto.setShoppeStatus(Constants.PCMSHOPPE_SHOPPE_STATUS_1);
        List<PcmShoppeResultDto> shoppeList = pcmShoppeService.findShoppeList(dto);

        return ResultUtil.creComSucResult(shoppeList);
    }

    /**
     * 移动工作台调用主数据获取专柜信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name findShoppeInfo
     * @Create In 2015-8-30 By niuzhifan
     */
    @ResponseBody
    @RequestMapping("/findShoppeInfo")
    public Map<String, Object> findShoppeInfo(@RequestBody PcmShoppePara para,
                                              HttpServletRequest request) {
        PcmGetShoppeDto dto = new PcmGetShoppeDto();
        try {
            BeanUtils.copyProperties(dto, para);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        List<PcmShoppeErpDto> reDto = pcmShoppeService.getShoppeInfo(dto);
        return ResultUtil.creComSucResult(reDto);
    }

    /**
     * 专柜状态变更
     *
     * @create in nzf
     */
    @ResponseBody
    @RequestMapping("/updateStatusInfo")
    public String updateStatusInfo(@RequestBody PcmShoppePara para, HttpServletRequest request) {
        String url = PropertyUtil.getSystemUrl("pcm-admin") + "/shoppe/updateStatusInfo.htm";
        String response = HttpUtil.doPost(url, JsonUtil.getJSONString(para));
        return response;
    }
}
