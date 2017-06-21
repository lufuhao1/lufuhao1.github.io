package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.constants.ComErrorCodeConstants.ErrorCode;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.framework.exception.BleException;
import com.wangfj.core.utils.*;
import com.wangfj.product.common.service.impl.PcmExceptionLogService;
import com.wangfj.product.core.controller.support.*;
import com.wangfj.product.maindata.domain.entity.PcmProduct;
import com.wangfj.product.maindata.domain.entity.PcmProductTypeDict;
import com.wangfj.product.maindata.domain.entity.PcmShoppeProduct;
import com.wangfj.product.maindata.domain.vo.*;
import com.wangfj.product.maindata.service.intf.IPcmProDetailService;
import com.wangfj.product.maindata.service.intf.IPcmProductService;
import com.wangfj.product.maindata.service.intf.IPcmProductTypeDictService;
import com.wangfj.product.maindata.service.intf.IPcmShoppeProductService;
import com.wangfj.util.Constants;
import com.wangfj.util.mq.PublishDTO;
import com.wfj.search.utils.zookeeper.discovery.SpringWebMvcServiceProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class PcmProductController extends BaseController {
	
    @Autowired   //##(原来就有)
    private IPcmShoppeProductService proService;
    @Autowired
    private IPcmProductService spuService;
    @Autowired
    private IPcmProDetailService skuService;
    @Autowired
    private PcmExceptionLogService pcmExceptionLogService;
    @Autowired
    private IPcmProductTypeDictService proTypeService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private SpringWebMvcServiceProvider provider;

    List<PublishDTO> sidList = null;

    String pcm_admin = PropertyUtil.getSystemUrl("pcm-admin");

    /**
     * 按条件查询专柜商品基本信息及统计分类
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name 按条件查询专柜商品基本信息及统计分类
     * @Create In 2016年04月14日 By wangxuan
     */
    @RequestMapping(value = "/findShoppeProductAndCategoryByPara", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> findShoppeProductAndCategoryByPara(@RequestBody ProductPagePara para,
                                                                  HttpServletRequest request) {
        ProductPageDto pageDto = new ProductPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        List<ProductPageDto> resultDtoList = null;
        try {
            resultDtoList = proService.findShoppeProductAndCategoryByPara(pageDto);
            return ResultUtil.creComSucResult(resultDtoList);

        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }

    }

    /**
     * 根据专柜商品参数查询专柜商品
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name selectListByParam
     * @Create In 2016年04月14日 By wangxuan
     */
    @RequestMapping(value = "/selectListByParam", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectListByParam(@RequestBody PcmShoppeProductPara para,
                                                 HttpServletRequest request) {
        PcmShoppeProduct product = new PcmShoppeProduct();
        org.springframework.beans.BeanUtils.copyProperties(para, product);
        List<PcmShoppeProduct> resultList = null;
        try {
            resultList = proService.selectShoppeProductInfo(product);
            return ResultUtil.creComSucResult(resultList);
        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }
    }

    /**
     * 按条件查询单个专柜商品信息（优化）
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name selectShoppeProductByPara
     * @Create In 2016年03月28日 By wangxuan
     */
    @RequestMapping(value = "/selectShoppeProductByPara", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectShoppeProductByPara(@RequestBody ProductPagePara para,
                                                         HttpServletRequest request) {
        ProductPageDto pageDto = new ProductPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        ProductPageDto resultDto = null;
        try {
            resultDto = proService.selectShoppeProductByPara(pageDto);
            if (resultDto != null) {
                if ("WFJ".equals(resultDto.getField2())) {
                    resultDto.setSupplierCode("WFJ");
                    resultDto.setSupplierName("WFJ");
                }
                if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z1_STR.equals(resultDto.getOperateMode())) {
                    resultDto.setOperateMode("0");
                } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z2_STR.equals(resultDto
                        .getOperateMode())) {
                    resultDto.setOperateMode("1");
                } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z3_STR.equals(resultDto
                        .getOperateMode())) {
                    resultDto.setOperateMode("2");
                } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z4_STR.equals(resultDto
                        .getOperateMode())) {
                    resultDto.setOperateMode("3");
                } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z5_STR.equals(resultDto
                        .getOperateMode())) {
                    resultDto.setOperateMode("4");
                }
            }
            return ResultUtil.creComSucResult(resultDto);

        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }

    }

    /**
     * 专柜商品导出Excel 查询总数量
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name getShoppeProductToExcelCount
     * @Create In 2016年04月05日 By wangxuan
     */
    @RequestMapping(value = "/getShoppeProductToExcelCount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> getShoppeProductToExcelCount(@RequestBody ProductPagePara para,
                                                            HttpServletRequest request) {
        ProductPageDto pageDto = new ProductPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        Page<ProductPageDto> page;
        try {
            page = proService.getShoppeProductToExcelCount(pageDto);
            return ResultUtil.creComSucResult(page);
        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }

    }

    /**
     * 专柜商品导出Excel 查询
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name getShoppeProductToExcel
     * @Create In 2016年04月05日 By wangxuan
     */
    @RequestMapping(value = "/getShoppeProductToExcel", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> getShoppeProductToExcel(@RequestBody ProductPagePara para,
                                                       HttpServletRequest request) {
        ProductPageDto pageDto = new ProductPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        Page<ProductPageDto> page;
        try {
            page = proService.getShoppeProductToExcel(pageDto);
            if (page.getList() != null && page.getList().size() > 0) {
                for (int i = 0; i < page.getList().size(); i++) {
                    if ("WFJ".equals(page.getList().get(i).getField2())) {
                        page.getList().get(i).setSupplierCode("WFJ");
                        page.getList().get(i).setSupplierName("WFJ");
                    }
                    if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z1_STR.equals(page.getList().get(i)
                            .getOperateMode())) {
                        page.getList().get(i).setOperateMode("0");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z2_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("1");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z3_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("2");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z4_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("3");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z5_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("4");
                    }
                }
                return ResultUtil.creComSucResult(page);
            } else {
                return ResultUtil.creComErrorResult(
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
            }
        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }

    }

    /**
     * 从搜索查询专柜商品列表
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name selectShoppeProductPageByParaFromSearch
     * @Create In 2016年04月06日 By wangxuan
     */
    @RequestMapping(value = "/selectShoppeProductPageByParaFromSearch", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String selectShoppeProductPageByParaFromSearch(@RequestBody SearchShoppeProductQueryPara para,
                                                          HttpServletRequest request) {
        if (para.getCurrentPage() == null) {
            para.setCurrentPage(1);
        }
        if (para.getPageSize() == null) {
            para.setPageSize(10);
        }
        try {
            String serviceAddress = provider.provideServiceAddress("pcm-item-query").orNull();
            // TODO if serviceAddress is null
            SearchShoppeProductQueryDto dto = new SearchShoppeProductQueryDto();
            BeanUtils.copyProperties(para, dto);
            String json = HttpUtil.doPost(serviceAddress, JsonUtil.getJSONString(dto));
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.clear();
            paramMap.put(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), "从搜索查询专柜商品列表获取地址失败！");
            return paramMap.toString();
        }
    }

    /**
     * 按条件 分页 查询专柜商品基础信息列表(优化)
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name selectBaseProPageByPara
     * @Create In 2015年8月4日 By zhangxy
     */
    @RequestMapping(value = "/selectBaseProPageByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectBaseProPageByPara(@RequestBody ProductPagePara para,
                                                       HttpServletRequest request) {
        ProductPageDto pageDto = new ProductPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        // JSONObject jsono = new JSONObject();
        // if (para.getBarCode() != null) {
        // Map<String, Object> map = new HashMap<String, Object>();
        // map.put(Constants.PCMBARCODE_CODE_TYPE_ZE_STR, para.getBarCode());
        // pageDto.setSupplierIntBarCode(map);
        // }
        Page<ProductPageDto> page;
        try {
            page = proService.selectBaseProPageByParaOptimization(pageDto);
            if (page.getList() != null && page.getList().size() > 0) {
                for (int i = 0; i < page.getList().size(); i++) {
                    if ("WFJ".equals(page.getList().get(i).getField2())) {
                        page.getList().get(i).setSupplierCode("WFJ");
                        page.getList().get(i).setSupplierName("WFJ");
                    }
                    if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z1_STR.equals(page.getList().get(i)
                            .getOperateMode())) {
                        page.getList().get(i).setOperateMode("0");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z2_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("1");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z3_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("2");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z4_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("3");
                    } else if (Constants.PCMERPPRODUCT_PRODUCT_TYPE_Z5_STR.equals(page.getList()
                            .get(i).getOperateMode())) {
                        page.getList().get(i).setOperateMode("4");
                    }
                }
                return ResultUtil.creComSucResult(page);
            } else {
                return ResultUtil.creComErrorResult(
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
            }
        } catch (Exception e) {
            return ResultUtil.creComErrorResult(null, e.getMessage());
        }

    }

    /**
     * 根据SKUSID查询专柜商品
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name selectProPageBySku
     * @Create In 2015年9月21日 By zhangxy
     */
    @RequestMapping(value = "/selectProPageBySku", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectProPageBySku(@RequestBody ShoppeProductPara para) {
        ShoppeProductDto dto = new ShoppeProductDto();
        if (para.getSid() != null) {
            dto.setSid(para.getSid());
        }
        if (StringUtils.isNotBlank(para.getSkuSid())) {
            dto.setSkuSid(para.getSkuSid());
        }
        if (StringUtils.isNotBlank(para.getProductCode())) {
            dto.setProductCode(para.getProductCode());
        }
        if (StringUtils.isNotBlank(para.getProductName())) {
            dto.setProductName(para.getProductName());
        }
        if ("N".equals(para.getIsSale())) {
            dto.setIsSale("1");
        } else if ("Y".equals(para.getIsSale())) {
            dto.setIsSale("0");
        } else {
            dto.setIsSale(null);
        }
        dto.setCurrentPage(para.getCurrentPage());
        dto.setPageSize(para.getPageSize());
        dto.setStart(para.getStart());
        dto.setLimit(para.getLimit());
        Page<ShoppeProductDto> page = proService.selectProPageBySku(dto);
        return ResultUtil.creComSucResult(page);
    }

    /**
     * 根据SKUSID查询专柜商品(页面缓存)
     *
     * @param para
     * @return Map<String,Object>
     * @Methods Name selectProPageCache
     * @Create In 2015年9月21日 By zhangxy
     */
    @RequestMapping(value = "/selectProPageCache", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectProPageCache(@RequestBody ShoppeProductPara para) {
        ShoppeProductDto dto = new ShoppeProductDto();
        if (para.getSid() != null) {
            dto.setSid(para.getSid());
        }
        if (StringUtils.isNotBlank(para.getSkuSid())) {
            dto.setSkuSid(para.getSkuSid());
        }
        if (StringUtils.isNotBlank(para.getProductCode())) {
            dto.setProductCode(para.getProductCode());
        }
        if (StringUtils.isNotBlank(para.getProductName())) {
            dto.setProductName(para.getProductName());
        }
        if ("N".equals(para.getIsSale())) {
            dto.setIsSale("1");
        } else {
            dto.setIsSale("0");
        }
        dto.setCurrentPage(para.getCurrentPage());
        dto.setPageSize(para.getPageSize());
        dto.setStart(para.getStart());
        dto.setLimit(para.getLimit());
        ProPageDto proDto = proService.selectProPageCache("proPage", dto);
        Page<ShoppeProductDto> page = proDto.getPage();
        return ResultUtil.creComSucResult(page);
    }

    /**
     * 按条件分页查询SPU信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name selectBaseSpuPage
     * @Create In 2015年8月12日 By zhangxy
     */
    @RequestMapping(value = "/selectBaseSpuPageByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectBaseSpuPageByPara(@RequestBody SpuPagePara para,
                                                       HttpServletRequest request) {
        SpuPageDto pageDto = new SpuPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        Page<SpuPageDto> page = spuService.selectSpuPage(pageDto);
        if (page.getList() != null && page.getList().size() > 0) {
            return ResultUtil.creComSucResult(page);
        } else {
            return ResultUtil.creComErrorResult(
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
        }
    }


    /**
     * 按条件分页查询SKU信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name selectBaseSkuPage
     * @Create In 2015年8月12日 By zhangxy
     */
    @RequestMapping(value = "/selectBaseSkuPageByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectBaseSkuPageByPara(@RequestBody SkuPagePara para,
                                                       HttpServletRequest request) {
        SkuPageDto pageDto = new SkuPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        if (StringUtils.isBlank(para.getSpuCode())) {
            pageDto.setSpuCode(null);
        }
        if (StringUtils.isBlank(para.getSkuCode())) {
            pageDto.setSkuCode(null);
        }
        if (StringUtils.isBlank(para.getColorSid())) {
            pageDto.setColorSid(null);
        }
        if (StringUtils.isBlank(para.getColorName())) {
            pageDto.setColorName(null);
        }
        if (StringUtils.isBlank(para.getSpuSid())) {
            pageDto.setSpuSid(null);
        }
        if (StringUtils.isBlank(para.getModelCode())) {
            pageDto.setModelCode(null);
        }
        if (StringUtils.isBlank(para.getBrandGroupCode())) {
            pageDto.setBrandGroupCode(null);
        }
        if (StringUtils.isBlank(para.getSpuSale())) {
            pageDto.setSpuSale(null);
        }
        if (StringUtils.isBlank(para.getSkuSale())) {
            pageDto.setSkuSale(null);
        }
        if (StringUtils.isBlank(para.getProType())) {
            pageDto.setProType(null);
        }
        if (StringUtils.isBlank(para.getIndustryCondition())) {
            pageDto.setIndustryCondition(null);
        }
        if (para.getProActiveBit() != null) {
            pageDto.setProActiveBit(para.getProActiveBit());
        }
        Page<SkuPageDto> page = skuService.selectSkuPage(pageDto);
        if (page.getList() != null && page.getList().size() > 0) {
            return ResultUtil.creComSucResult(page);
        } else {
            return ResultUtil.creComErrorResult(
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
        }
    }

    /**
     * 按条件分页查询SKU信息(页面缓存)
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name selectSkuPageCache
     * @Create In 2015年8月12日 By zhangxy
     */
    @RequestMapping(value = "/selectSkuPageCache", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectSkuPageCache(@RequestBody SkuPagePara para,
                                                  HttpServletRequest request) {
        SkuPageDto pageDto = new SkuPageDto();
        org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
        if (StringUtils.isBlank(para.getSpuCode())) {
            pageDto.setSpuCode(null);
        }
        if (StringUtils.isBlank(para.getSkuCode())) {
            pageDto.setSkuCode(null);
        }
        if (StringUtils.isBlank(para.getSpuCode())) {
            pageDto.setSpuCode(null);
        }
        if (StringUtils.isBlank(para.getColorSid())) {
            pageDto.setColorSid(null);
        }
        if (StringUtils.isBlank(para.getColorName())) {
            pageDto.setColorName(null);
        }
        if (StringUtils.isBlank(para.getSpuSid())) {
            pageDto.setSpuSid(null);
        }
        if (StringUtils.isBlank(para.getModelCode())) {
            pageDto.setModelCode(null);
        }
        if (StringUtils.isBlank(para.getBrandGroupCode())) {
            pageDto.setBrandGroupCode(null);
        }
        if (StringUtils.isBlank(para.getSpuSale())) {
            pageDto.setSpuSale(null);
        }
        if (StringUtils.isBlank(para.getSkuSale())) {
            pageDto.setSkuSale(null);
        }
        if (StringUtils.isBlank(para.getProType())) {
            pageDto.setProType(null);
        }
        if (StringUtils.isBlank(para.getIndustryCondition())) {
            pageDto.setIndustryCondition(null);
        }
        SkuPageCacheDto spcd = skuService.selectSkuPageCache("skuPage", pageDto);
        Page<SkuPageDto> page = spcd.getPage();
        if (page.getList() != null && page.getList().size() > 0) {
            return ResultUtil.creComSucResult(page);
        } else {
            return ResultUtil.creComErrorResult(
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                    ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
        }
    }

    /**
     * 按条件查询单个SPU信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name getBaseSpuByPara
     * @Create In 2015年8月12日 By zhangxy
     */
    @RequestMapping(value = "/getBaseSpuByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> getBaseSpuByPara(@RequestBody SpuPagePara para,
                                                HttpServletRequest request) {
        if (para.getSpuCode() != null || para.getSid() != null) {
            SpuPageDto pageDto = new SpuPageDto();
            org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
            Page<SpuPageDto> page = spuService.selectSpuPage(pageDto);
            if (page.getList() != null && page.getList().size() > 0) {
                return ResultUtil.creComSucResult(page.getList().get(0));
            } else {
                return ResultUtil.creComErrorResult(
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
            }
        } else {
            return ResultUtil.creComErrorResult(ErrorCode.PARA_NORULE_ERROR.getErrorCode(),
                    ErrorCode.PARA_NORULE_ERROR.getMemo());
        }
    }

    /**
     * 按条件查询单个SKU信息
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name getBaseSkuByPara
     * @Create In 2015年8月12日 By zhangxy
     */
    @RequestMapping(value = "/getBaseSkuByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> getBaseSkuByPara(@RequestBody SkuPagePara para,
                                                HttpServletRequest request) {
        if (para.getSkuCode() != null || para.getSid() != null) {
            SkuPageDto pageDto = new SkuPageDto();
            org.springframework.beans.BeanUtils.copyProperties(para, pageDto);
            Page<SkuPageDto> page = skuService.selectSkuPage(pageDto);
            if (page.getList() != null && page.getList().size() > 0) {
                return ResultUtil.creComSucResult(page.getList().get(0));
            } else {
                return ResultUtil.creComErrorResult(
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode(),
                        ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
            }
        } else {
            return ResultUtil.creComErrorResult(ErrorCode.PARA_NORULE_ERROR.getErrorCode(),
                    ErrorCode.PARA_NORULE_ERROR.getMemo());
        }
    }

    /**
     * 产品更换工业分类
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name updateProductCategory
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/updateProductCategory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateProductCategory(@RequestBody @Valid UpdateProCatePara para,
                                                     HttpServletRequest request) {
        ProductCondDto dto = new ProductCondDto();
        BeanUtils.copyProperties(para, dto);
        try {
            spuService.updateProductCategory(dto);
        } catch (BleException e) {
            return ResultUtil.creComErrorResult(e.getCode(), e.getMessage());
        }
        return ResultUtil.creComSucResult("");
    }

    /**
     * 产品更换统计分类
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name updateProductCategory
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/updateStatCategory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateStatCategory(@RequestBody @Valid UpdateProCatePara para,
                                     HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateStatCategory.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品换管理分类
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name updateProductCategory
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/updateManagerCategory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateManagerCategory(@RequestBody @Valid UpdateProCatePara para,
                                        HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateManagerCategory.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品换专柜
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name updateProductShoppe
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/updateProductShoppe", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateProductShoppe(@RequestBody @Valid UpdateProShoppePara para,
                                      HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateProductShoppe.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品换色码（特性）/尺寸码（规格）
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name updateColorStan
     * @Create In 2015年8月9日 By yedong
     */
    @RequestMapping(value = "/updateProColorStan", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateProColorStan(@RequestBody @Valid UpdateProColorStanPara para,
                                     HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateProColorStan.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * SKU换色码（特性）/尺寸码（规格）规则说明
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name updateSkuColorStan
     * @Create In 2015年9月9日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/updateSkuColorStan", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String updateSkuColorStan(@RequestBody @Valid UpdateProColorStanPara para,
                                     HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateSkuColorStan.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * SKU换色码（特性）/尺寸码（规格) 校验
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name updateSkuColorStan
     * @Create In 2015年9月9日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/validUpdateSkuColorStan", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String validUpdateSkuColorStan(@RequestBody @Valid UpdateProColorStanPara para,
                                          HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/validUpdateSkuColorStan.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品换款
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name changeProductSku
     * @Create In 2015年9月10日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/changeProductSku", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String changeProductSku(@RequestBody @Valid UpdateProColorStanPara para,
                                   HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/changeProductSku.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * SKU换款
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name changeProductSku
     * @Create In 2015年9月10日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/changeProductSkuBySKU", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String changeProductSkuBySKU(@RequestBody @Valid UpdateProColorStanPara para,
                                        HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/changeProductSkuBySKU.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * SKU换款 校验
     *
     * @param para
     * @param request
     * @return Map<String,Object>
     * @Methods Name changeProductSku
     * @Create In 2015年9月10日 By yedong
     */
    @ResponseBody
    @RequestMapping(value = "/validChangeProductSkuBySKU", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String validChangeProductSkuBySKU(@RequestBody @Valid UpdateProColorStanPara para,
                                             HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/validChangeProductSkuBySKU.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品换扣率码
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name updateProductRateCode
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/updateProductRateCode", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateProductRateCode(@RequestBody @Valid UpdateProRateCodePara para,
                                        HttpServletRequest request) {
        String response = HttpUtil.doPost(pcm_admin + "product/updateProductRateCode.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 专柜商品挂促销扣率码
     *
     * @param para
     * @param request
     * @return String
     * @Methods Name savePromotionRate
     * @Create In 2015年8月5日 By zhangxy
     */
    @RequestMapping(value = "/savePromotionRate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> savePromotionRate(@Valid SaveProRatePara para,
                                                 HttpServletRequest request) {
        String result = null;
        ProductCondDto dto = new ProductCondDto();
        BeanUtils.copyProperties(para, dto);
        int i = spuService.savePromotionRate(dto);
        if (i == 0) {
            result = "失败";
        } else if (i == 1) {
            return ResultUtil.creComSucResult("");
        } else if (i == 2) {
            result = "专柜商品不存在";
        } else if (i == 3) {
            result = "促销扣率码不正确";
        } else if (i == 4) {
            result = "促销扣率码已存在";
        }
        return ResultUtil.creComErrorResult(String.valueOf(i), result);
    }

    /**
     * 查询商品类型
     *
     * @param para
     * @return String
     * @Methods Name selectProductType
     * @Create In 2015年9月11日 By zhangxy
     */
    @RequestMapping(value = "/selectProductType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectProductType(@RequestBody ProductTypePara para) {
        PcmProductTypeDict dto = new PcmProductTypeDict();
        BeanUtils.copyProperties(para, dto);
        List<PcmProductTypeDict> list = proTypeService.selectProductType(dto);
        return ResultUtil.creComSucResult(list);
    }

    /**
     * 修改商品基本属性
     *
     * @param dto
     * @Methods Name updateProductInfo
     * @Create In 2015年10月20日 By zhangxy
     */
    @RequestMapping(value = "/UpdateProductInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String UpdateProductInfo(@RequestBody UpdateProductInfoPara para) {
        String response = HttpUtil.doPost(pcm_admin + "product/UpdateProductInfo.htm",
                JsonUtil.getJSONString(para));
        return response;
    }

    /**
     * 修改多个专柜商品状态
     *
     * @param dto
     * @Methods Name UpdateProductStatusInfo
     * @Create In 2015年10月20日 By zhangdongliang
     */
    @RequestMapping(value = "/UpdateProductStatusInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String UpdateProductStatusInfo(@RequestBody Map<String, Object> paramMap) {
        String response = HttpUtil.doPost(pcm_admin + "product/UpdateProductStatusInfo.htm",
                JsonUtil.getJSONString(paramMap));
        return response;
    }

    /**
     * SKU启用状态修改
     *
     * @param entity
     * @return boolean
     * @Methods Name proDetailDisable
     * @Create In 2015年11月3日 By yedong
     */
    @RequestMapping(value = "/proDetailDisable", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String proDetailDisable(@RequestBody Map<String, Object> paramMap) {
        String response = HttpUtil.doPost(pcm_admin + "product/proDetailDisable.htm",
                JsonUtil.getJSONString(paramMap));
        return response;
    }

    /**
     * SKU启用状态修改
     *
     * @param entity
     * @return boolean
     * @Methods Name proDetailDisable
     * @Create In 2015年11月3日 By yedong
     */
    @RequestMapping(value = "/proDetailSell", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String proDetailSell(@RequestBody Map<String, Object> paramMap) {
        String response = HttpUtil.doPost(pcm_admin + "product/proDetailSell.htm",
                JsonUtil.getJSONString(paramMap));
        return response;
    }

    /**
     * 根据spu编码查询spu
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/getSpuproBySpu", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getSpuproBySpu(@RequestBody Map<String, Object> paramMap) {
        List<PcmProduct> spuList = spuService.getListProByParam(paramMap);
        return JsonUtil.getJSONString(spuList);
    }

    /**
     * 根据spu编码修改spu长短描述
     *
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/editSpuproBySpu", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String editSpuproBySpu(@RequestBody Map<String, Object> paramMap) {
        String response = HttpUtil.doPost(pcm_admin + "product/editSpuproBySpu.htm",
                JsonUtil.getJSONString(paramMap));
        return response;
    }
    
    /**
     * OMS通过专柜商品从pcm批量查询商品信息  ***********OPS*********
     */
    
}
