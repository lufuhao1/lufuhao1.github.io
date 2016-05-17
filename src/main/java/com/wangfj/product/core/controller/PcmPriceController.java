package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.framework.exception.BleException;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.QueryPricePara;
import com.wangfj.product.core.controller.support.QueryProductPriceInfoPara;
import com.wangfj.product.core.controller.support.SearchShoppeProductQueryPara;
import com.wangfj.product.maindata.domain.vo.SearchShoppeProductQueryDto;
import com.wangfj.product.organization.domain.entity.PcmChannel;
import com.wangfj.product.organization.service.intf.IPcmChannelService;
import com.wangfj.product.price.domain.vo.*;
import com.wangfj.product.price.service.intf.IPcmPriceService;
import com.wangfj.util.Constants;
import com.wfj.platform.util.zookeeper.discovery.SpringMvcServiceProvider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
 * 价格信息查询
 *
 * @Class Name PcmPriceController
 * @Author kongqf
 * @Create In 2015年8月3日
 */
@Controller
@RequestMapping("/pcmprice")
public class PcmPriceController extends BaseController {

    @Autowired
    private IPcmPriceService pcmPriceService;

    @Autowired
    private SpringMvcServiceProvider provider;

    @Autowired
    private IPcmChannelService channelService;

    /**
     * 查询价格信息
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryPriceInfo
     * @Create In 2015年9月17日 By kongqf
     */
    @RequestMapping(value = "/queryPriceInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryPriceInfo(HttpServletRequest request,
                                              @RequestBody @Valid QueryPricePara queryPricePara) {
        SelectPriceDto selectPriceDto = new SelectPriceDto();
        QueryPriceDto queryPriceDto = new QueryPriceDto();
        try {
            BeanUtils.copyProperties(queryPriceDto, queryPricePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(queryPriceDto.getChannelSid())) {
            queryPriceDto.setChannelSid(Constants.DEFAULT_CHANNEL_SID);
        }
        selectPriceDto = pcmPriceService.queryPriceInfoByPara(
                queryPriceDto.getShoppeProSid() + queryPriceDto.getChannelSid(), queryPriceDto);
        if (selectPriceDto == null) {
            return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
        } else {
            return ResultUtil.creComSucResult(selectPriceDto);
        }
    }

    /**
     * 商品价格信息查询
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfo
     * @Create In 2015年9月7日 By kongqf
     */
    @RequestMapping(value = "/queryProductInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfo(HttpServletRequest request,
                                                     @RequestBody @Valid QueryProductPriceInfoPara queryPricePara) {
        QueryProductPriceInfoDto queryPriceDto = new QueryProductPriceInfoDto();
        Page<SelectProductPriceInfoDto> pageDto = new Page<SelectProductPriceInfoDto>();
        try {
            BeanUtils.copyProperties(queryPriceDto, queryPricePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        pageDto = pcmPriceService.queryProductPriceInfoOptimization(queryPriceDto);
        if (pageDto == null) {
            return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
        } else {
            return ResultUtil.creComSucResult(pageDto);
        }
    }

    /**
     * 从搜索查询商品价格信息
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfoFromSearch
     * @Create In 2016年04月12日 By wangxuan
     */
    @RequestMapping(value = "/queryProductPriceInfoFromSearch", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfoFromSearch(HttpServletRequest request,
                                                               @RequestBody QueryProductPriceInfoPara para) {
        Page<SelectProductPriceInfoDto> pageDto = new Page<SelectProductPriceInfoDto>();
        try {
            //从搜索查询专柜商品参数封装
            SearchShoppeProductQueryDto dto = new SearchShoppeProductQueryDto();
            dto.setSkuCodeStart(para.getSkuCode());
            dto.setProductCodeStart(para.getProductCode());
            dto.setStoreCode(para.getStoreCode());
            dto.setSupplierCode(para.getSupplierCode());
            dto.setCounterCode(para.getCounterCode());
            dto.setManagerCategoryCodes(para.getManagerCategoryCodes());
            Integer paraCurrentPage = para.getCurrentPage();
            if (paraCurrentPage == null) {
                dto.setCurrentPage(1);
            } else {
                dto.setCurrentPage(paraCurrentPage);
            }
            Integer paraPageSize = para.getPageSize();
            if (paraPageSize == null) {
                dto.setPageSize(10);
            } else {
                dto.setPageSize(paraPageSize);
            }
            //从搜索查询专柜商品
            String serviceAddress = provider.provideServiceAddress("pcm-item-query");
            String json = HttpUtil.doPost(serviceAddress, JsonUtil.getJSONString(dto));
            if (StringUtils.isNotEmpty(json)) {
                JSONObject jsonObject = JSONObject.fromObject(json);
                JSONObject page = jsonObject.getJSONObject("page");
                JSONArray list = page.getJSONArray("list");
                //从自身系统查询价格参数封装
                QueryProductPriceInfoDto queryPriceDto = new QueryProductPriceInfoDto();
                org.springframework.beans.BeanUtils.copyProperties(para, queryPriceDto);
                List<String> productCodeList = new ArrayList<String>();
                List<SelectProductPriceInfoDto> productPriceDtoList = new ArrayList<SelectProductPriceInfoDto>();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject obj = list.getJSONObject(i);
                    String productCode = obj.get("productCode") + "";
                    productCodeList.add(productCode);//从自身系统查询价格参数封装
                }
                List<SelectProductPriceInfoDto> priceList = null;
                if (productCodeList.size() > 0) {
                    queryPriceDto.setProductCodeList(productCodeList);//从自身系统查询价格参数封装
                    priceList = pcmPriceService.findPriceInfoByParaForShoppeProduct(queryPriceDto);
                }
                //查询渠道
                List<PcmChannel> channelList = null;
                String channelSid = para.getChannelSid();
                if (StringUtils.isNotEmpty(channelSid)) {
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("channelCode", channelSid.trim());
                    paramMap.put("status", Constants.PUBLIC_0);
                    channelList = channelService.selectListByParam(paramMap);
                }
                //返回结果数据封装
                Integer total = page.getInt("total");
                Integer pageSize = page.getInt("pageSize");
                Integer currentPage = page.getInt("currentPage");
                pageDto.setPageSize(pageSize);
                pageDto.setCurrentPage(currentPage);
                pageDto.setCount(total);
                for (int i = 0; i < list.size(); i++) {
                    SelectProductPriceInfoDto tempDto = new SelectProductPriceInfoDto();
                    JSONObject obj = list.getJSONObject(i);
                    tempDto.setSid(Long.parseLong(obj.get("sid") + ""));
                    tempDto.setSkuCode(obj.get("skuCode") + "");
                    String productCode = obj.get("productCode") + "";
                    tempDto.setProductCode(productCode);
                    tempDto.setProductName(obj.get("productName") + "");
                    tempDto.setModelCode(obj.get("modelCode") + "");
                    tempDto.setStanName(obj.get("stanName") + "");
                    tempDto.setColorName(obj.get("colorName") + "");
                    tempDto.setStoreName(obj.get("storeName") + "");
                    tempDto.setCounterName(obj.get("counterName") + "");
                    tempDto.setSupplierName(obj.get("supplierName") + "");
//                    JSONArray channels = obj.getJSONArray("channels");
//                    tempDto.setChannelName(channels.getJSONObject(0).get("channelName") + "");
//                    tempDto.setChannelSid(channels.getJSONObject(0).get("channelCode") + "");
                    if (channelList != null && channelList.size() > 0){
                        PcmChannel channel = channelList.get(0);
                        tempDto.setChannelSid(channel.getChannelCode());
                        tempDto.setChannelName(channel.getChannelName());
                    }
                    JSONArray suppliers = obj.getJSONArray("suppliers");
                    tempDto.setSupplierName(suppliers.getJSONObject(0).get("supplierName") + "");
                    tempDto.setSupplierCode(suppliers.getJSONObject(0).get("supplierCode") + "");
                    tempDto.setMarketPrice(obj.get("marketPrice") + "");
                    if (priceList != null && priceList.size() > 0) {
                        for (SelectProductPriceInfoDto priceDto : priceList) {
                            String priceDtoProductCode = priceDto.getProductCode();
                            if (priceDtoProductCode.equals(productCode)) {
                                tempDto.setSalesPrice(priceDto.getSalesPrice());
                            }
                        }
                    }
                    productPriceDtoList.add(tempDto);
                }
                pageDto.setList(productPriceDtoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.clear();
            paramMap.put(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), "查询价格时从搜索查询专柜商品列表获取地址失败！");
            return paramMap;
        }
        return ResultUtil.creComSucResult(pageDto);
    }

    /**
     * 查询专柜商品价格信息
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryShoppeProPriceInfo
     * @Create In 2015年9月11日 By kongqf
     */
    @RequestMapping(value = "/queryShoppeProPriceInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryShoppeProPriceInfo(HttpServletRequest request,
                                                       @RequestBody @Valid QueryPricePara queryPricePara) {
        QueryPriceDto queryPriceDto = new QueryPriceDto();
        try {
            BeanUtils.copyProperties(queryPriceDto, queryPricePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        List<SelectShoppeProPriceInfoDto> priceList = new ArrayList<SelectShoppeProPriceInfoDto>();
        try {
            priceList = pcmPriceService.queryShoppeProPriceInfoByShoppeProSid(queryPriceDto);
        } catch (BleException e) {
            return ResultUtil.creComErrorResult(e.getCode(), e.getMessage());
        }
        return ResultUtil.creComSucResult(priceList);
    }

    /**
     * 查询专柜商品价格信息EXCEL 查总数
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfoExcelCount
     * @Create In 2016-04-05 By wangxuan
     */
    @RequestMapping(value = "/queryProductPriceInfoExcelCount", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfoExcelCount(HttpServletRequest request, @RequestBody @Valid QueryProductPriceInfoPara queryPricePara) {
        QueryProductPriceInfoDto queryPriceDto = new QueryProductPriceInfoDto();
        try {
            BeanUtils.copyProperties(queryPriceDto, queryPricePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Page<SelectProductPriceInfoDto> page = pcmPriceService.queryProductPriceInfoExcelCount(queryPriceDto);
        return ResultUtil.creComSucResult(page);
    }

    /**
     * 查询专柜商品价格信息EXCEL
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfoExcel
     * @Create In 2016-1-19 By wangc
     */
    @RequestMapping(value = "/queryProPriceInfoExcel", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfoExcel(HttpServletRequest request,
                                                          @RequestBody @Valid QueryProductPriceInfoPara queryPricePara) {
        QueryProductPriceInfoDto queryPriceDto = new QueryProductPriceInfoDto();
        List<SelectProductPriceInfoDto> proList = new ArrayList<SelectProductPriceInfoDto>();
        try {
            BeanUtils.copyProperties(queryPriceDto, queryPricePara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        proList = pcmPriceService.queryProductPriceInfoExcel(queryPriceDto);
        if (proList == null) {
            return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
        } else {
            return ResultUtil.creComSucResult(proList);
        }
    }
}
