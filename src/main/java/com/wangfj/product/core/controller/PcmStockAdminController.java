package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.constants.ComErrorCodeConstants.ErrorCode;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmStockChangeRecordHisPara;
import com.wangfj.product.core.controller.support.PcmStockPara;
import com.wangfj.product.core.controller.support.QueryProductStockInfoPara;
import com.wangfj.product.maindata.domain.vo.SearchShoppeProductQueryDto;
import com.wangfj.product.organization.domain.entity.PcmChannel;
import com.wangfj.product.organization.service.intf.IPcmChannelService;
import com.wangfj.product.stocks.domain.vo.*;
import com.wangfj.product.stocks.service.intf.IPcmStockChangeRecordService;
import com.wangfj.product.stocks.service.intf.IPcmStockService;
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
 * 库存管理
 *
 * @Class Name PcmStockController
 * @Author yedong
 * @Create In 2015年7月20日
 */
@Controller
@RequestMapping("/stockAdmin")
public class PcmStockAdminController extends BaseController {

    @Autowired
    private IPcmStockService pcmStockService;

    @Autowired
    private IPcmStockChangeRecordService pcmStockChangeService;

    @Autowired
    private SpringMvcServiceProvider provider;

    @Autowired
    private IPcmChannelService channelService;

    /**
     * 查询总库存（正、残次品、退货）
     *
     * @param paramMap
     * @return String
     * @Methods Name selectStockCountFromPcm
     * @Create In 2015年7月29日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/refundStockCountFromPcm", produces = "application/json; charset=utf-8")
    public Map<String, Object> refundStockCountFromPcm(@RequestBody PcmStockPara pcmStockPara) {
        PcmStockDto dto = new PcmStockDto();
        try {
            BeanUtils.copyProperties(dto, pcmStockPara);
            dto.setShoppeProSid(dto.getSupplyProductId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(dto.getShoppeProSid())) {
            Integer proSum = (int) pcmStockService.selectStockCountFromPcm(dto);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("supplyProductId", dto.getShoppeProSid());
            resultMap.put("proSum", proSum);
            return ResultUtil.creComSucResult(resultMap);
        } else {
            return ResultUtil.creComErrorResult(ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getErrorCode(),
                    ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getMemo());
        }
    }

    /**
     * 查询可售库存
     *
     * @param paramMap
     * @return String
     * @Methods Name findStockCountFromPcm
     * @Create In 2015年7月29日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/findStockCountFromPcm", produces = "application/json; charset=utf-8")
    public Map<String, Object> findStockCountFromPcm(@RequestBody PcmStockPara pcmStockPara) {
        PcmStockDto dto = new PcmStockDto();
        try {
            BeanUtils.copyProperties(dto, pcmStockPara);
            dto.setShoppeProSid(dto.getSupplyProductId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (StringUtils.isNotBlank(dto.getShoppeProSid())) {
            dto.setStockTypeSid(Constants.PCMSTOCK_TYPE_SALE);
            String shoppeProSid = dto.getShoppeProSid() + Constants.DEFAULT_CHANNEL_SID;
            if (pcmStockPara.getChannelSid() != null) {
                shoppeProSid = dto.getShoppeProSid() + pcmStockPara.getChannelSid();
            }
            Integer proSum = (int) pcmStockService.findStockCountFromPcm(shoppeProSid, dto);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("supplyProductId", dto.getShoppeProSid());
            resultMap.put("proSum", proSum);
            return ResultUtil.creComSucResult(resultMap);
        } else {
            return ResultUtil.creComErrorResult(ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getErrorCode(),
                    ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getMemo());
        }
    }

    /**
     * 批量查询
     *
     * @param paraList
     * @return String
     * @Methods Name findStockBigCountFromPcm
     * @Create In 2015年8月6日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/findStockBigCountFromPcm", produces = "application/json; charset=utf-8")
    public String findStockBigCountFromPcm(@RequestBody List<PcmStockPara> paraList) {
        List<PcmStockDto> list = new ArrayList<PcmStockDto>();
        for (int i = Constants.PUBLIC_0; i < paraList.size(); i++) {
            PcmStockDto dto = new PcmStockDto();
            PcmStockPara para = new PcmStockPara();
            try {
                BeanUtils.copyProperties(para, paraList.get(i));
                BeanUtils.copyProperties(dto, para);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            /* 专柜商品编码 */
            dto.setShoppeProSid(para.getSupplyProductId());
            dto.setStockTypeSid(Constants.PCMSTOCK_TYPE_SALE);
            list.add(dto);
        }
        List<PcmStockDto> list1 = new ArrayList<PcmStockDto>();
        if (list.size() < Constants.STOCK_IN_COUNT) {
            list1 = pcmStockService.findStockBigCountFromPcm(list);
        }
        return JsonUtil.getJSONString(list1);
    }

    /**
     * 根据专柜商品编码和渠道查询库位信息
     *
     * @param pcmStockPara
     * @return Map<String,Object>
     * @Methods Name queryShoppeProStockInfo
     * @Create In 2015年9月16日 By kongqf
     */
    @ResponseBody
    @RequestMapping(value = "/ShoppeProStockInfo", produces = "application/json; charset=utf-8")
    public Map<String, Object> queryShoppeProStockInfo(@RequestBody PcmStockPara pcmStockPara) {
        PcmStockDto dto = new PcmStockDto();
        try {
            BeanUtils.copyProperties(dto, pcmStockPara);
            dto.setShoppeProSid(dto.getSupplyProductId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (StringUtils.isNotBlank(dto.getShoppeProSid())) {
            List<PcmStockInfoDto> pcmStocks = pcmStockService.selectShoppeProStockInfo(dto);
            return ResultUtil.creComSucResult(pcmStocks);
        } else {
            return ResultUtil.creComErrorResult(ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getErrorCode(),
                    ErrorCode.STOCK_SHOPPEPROSID_IS_NULL.getMemo());
        }
    }

    /**
     * 商品库存信息查询
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
                                                     @RequestBody @Valid QueryProductStockInfoPara queryStockPara) {
        QueryProductStockInfoDto queryStockDto = new QueryProductStockInfoDto();
        Page<SelectProductStockInfoDto> pageDto = new Page<SelectProductStockInfoDto>();
        try {
            BeanUtils.copyProperties(queryStockDto, queryStockPara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        pageDto = pcmStockService.queryProductStockInfoOptimization(queryStockDto);
        if (pageDto == null) {
            return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
        } else {
            return ResultUtil.creComSucResult(pageDto);
        }
    }

    /**
     * 从搜索查询商品库存信息
     *
     * @param request
     * @param queryPricePara
     * @return Map<String,Object>
     * @Methods Name queryProductStockInfoFromSearch
     * @Create In 2016年04月13日 By wangxuan
     */
    @RequestMapping(value = "/queryProductStockInfoFromSearch", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductStockInfoFromSearch(HttpServletRequest request,
                                                               @RequestBody QueryProductStockInfoPara para) {
        Page<SelectProductStockInfoDto> pageDto = new Page<SelectProductStockInfoDto>();
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
            if (com.wangfj.core.utils.StringUtils.isNotEmpty(json)) {
                JSONObject jsonObject = JSONObject.fromObject(json);
                JSONObject page = jsonObject.getJSONObject("page");
                JSONArray list = page.getJSONArray("list");
                //从自身系统查询库存参数封装
                QueryProductStockInfoDto queryStockDto = new QueryProductStockInfoDto();
                org.springframework.beans.BeanUtils.copyProperties(para, queryStockDto);
                List<String> productCodeList = new ArrayList<String>();
                List<SelectProductStockInfoDto> productStockDtoList = new ArrayList<SelectProductStockInfoDto>();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject obj = list.getJSONObject(i);
                    String productCode = obj.get("productCode") + "";
                    productCodeList.add(productCode);//从自身系统查询库存参数封装
                }
                List<SelectProductStockInfoDto> stockList = null;
                if (productCodeList.size() > 0) {
                    queryStockDto.setProductCodeList(productCodeList);//从自身系统查询库存参数封装
                    stockList = pcmStockService.findStockInfoByProductPara(queryStockDto);
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
                    SelectProductStockInfoDto tempDto = new SelectProductStockInfoDto();
                    JSONObject obj = list.getJSONObject(i);
                    tempDto.setSid(Long.parseLong(obj.get("sid") + ""));
                    tempDto.setSkuCode(obj.get("skuCode") + "");
                    String productCode = obj.get("productCode") + "";
                    tempDto.setProductCode(productCode);
                    tempDto.setProductName(obj.get("productName") + "");
                    tempDto.setStoreName(obj.get("storeName") + "");
                    tempDto.setCounterName(obj.get("counterName") + "");
                    tempDto.setUnitCode(obj.get("unitCode") + "");
                    tempDto.setUnitName(obj.get("unitName") + "");
                    tempDto.setSupplierName(obj.get("supplierName") + "");
//                    JSONArray channels = obj.getJSONArray("channels");
//                    tempDto.setChannelName(channels.getJSONObject(0).get("channelName") + "");
//                    tempDto.setChannelSid(channels.getJSONObject(0).get("channelCode") + "");
                    if (channelList != null && channelList.size() > 0) {
                        PcmChannel channel = channelList.get(0);
                        tempDto.setChannelSid(channel.getChannelCode());
                        tempDto.setChannelName(channel.getChannelName());
                    }
                    JSONArray suppliers = obj.getJSONArray("suppliers");
                    tempDto.setSupplierName(suppliers.getJSONObject(0).get("supplierName") + "");
                    tempDto.setSupplierCode(suppliers.getJSONObject(0).get("supplierCode") + "");
                    tempDto.setBrandName(obj.get("storeBrandName") + "");
                    tempDto.setBrandCode(obj.get("storeBrandCode") + "");

                    tempDto.setSaleStock("0");
                    tempDto.setEdefectiveStock("0");
                    tempDto.setReturnStock("0");
                    tempDto.setLockedStock("0");
                    if (stockList != null && stockList.size() > 0) {
                        for (SelectProductStockInfoDto stockDto : stockList) {
                            String stockDtoProductCode = stockDto.getProductCode();
                            if (stockDtoProductCode.equals(productCode)) {
                                String saleStock = stockDto.getSaleStock();
                                if (com.wangfj.core.utils.StringUtils.isNotEmpty(saleStock)) {
                                    tempDto.setSaleStock(saleStock);
                                } else {
                                    tempDto.setSaleStock("0");
                                }
                                String edefectiveStock = stockDto.getEdefectiveStock();
                                if (com.wangfj.core.utils.StringUtils.isNotEmpty(edefectiveStock)) {
                                    tempDto.setEdefectiveStock(edefectiveStock);
                                } else {
                                    tempDto.setEdefectiveStock("0");
                                }
                                String returnStock = stockDto.getReturnStock();
                                if (com.wangfj.core.utils.StringUtils.isNotEmpty(returnStock)) {
                                    tempDto.setReturnStock(returnStock);
                                } else {
                                    tempDto.setReturnStock("0");
                                }
                                String lockedStock = stockDto.getLockedStock();
                                if (com.wangfj.core.utils.StringUtils.isNotEmpty(lockedStock)) {
                                    tempDto.setLockedStock(lockedStock);
                                } else {
                                    tempDto.setLockedStock("0");
                                }
                            }
                        }
                    }
                    productStockDtoList.add(tempDto);
                }
                pageDto.setList(productStockDtoList);
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
     * 根据条件查询商品库存信息 导出Excel 查总数量
     *
     * @param request
     * @param queryStockPara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfoExcelCount
     * @Create In 2016年04月05日 By wangxuan
     */
    @RequestMapping(value = "/queryProductPriceInfoExcelCount", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfoExcelCount(HttpServletRequest request,
                                                               @RequestBody @Valid QueryProductStockInfoPara queryStockPara) {
        QueryProductStockInfoDto queryStockDto = new QueryProductStockInfoDto();
        try {
            BeanUtils.copyProperties(queryStockDto, queryStockPara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Page<SelectProductStockInfoDto> page = pcmStockService.queryProductStockInfoExcelCount(queryStockDto);
        return ResultUtil.creComSucResult(page);
    }

    /**
     * 根据条件查询商品库存信息 导出Excel
     *
     * @param request
     * @param queryStockPara
     * @return Map<String,Object>
     * @Methods Name queryProductPriceInfoExcel
     * @Create In 2015年11月4日 By kongqf
     */
    @RequestMapping(value = "/queryProductInfoExcel", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryProductPriceInfoExcel(HttpServletRequest request,
                                                          @RequestBody @Valid QueryProductStockInfoPara queryStockPara) {
        QueryProductStockInfoDto queryStockDto = new QueryProductStockInfoDto();
        List<SelectProductStockInfoDto> proList = new ArrayList<SelectProductStockInfoDto>();
        try {
            BeanUtils.copyProperties(queryStockDto, queryStockPara);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        proList = pcmStockService.queryProductStockInfoExcel(queryStockDto);
        if (proList == null) {
            return ResultUtil.creComErrorResult(Constants.SYS_ERR_404, Constants.SYS_ERR_404_DES);
        } else {
            return ResultUtil.creComSucResult(proList);
        }
    }

    /**
     * 查询库位变动历史记录
     *
     * @param request
     * @param queryStockPara
     * @return Map<String,Object>
     * @Methods Name queryStockChangeHis
     * @Create In 2015年11月4日 By kongqf
     */
    @RequestMapping(value = "/queryStockChangeHis", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryStockChangeHis(HttpServletRequest request,
                                                   @RequestBody PcmStockChangeRecordHisPara queryStockPara) {
        PcmStockChangeRecordHisDto dto = new PcmStockChangeRecordHisDto();
        org.springframework.beans.BeanUtils.copyProperties(queryStockPara, dto);
        Page<PcmStockChangeRecordHisDto> pageList = new Page<PcmStockChangeRecordHisDto>();
        pageList = pcmStockChangeService.selectStockChangeHisRecordbyStockType(dto);

        return ResultUtil.creComSucResult(pageList);
    }
}
