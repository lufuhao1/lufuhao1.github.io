package com.wangfj.product.core.controller;

import com.wangfj.core.constants.ComErrorCodeConstants;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.brand.service.intf.IPcmBrandCommonService;
import com.wangfj.product.core.controller.support.PcmBrandValidationPara;
import com.wangfj.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxuan on 2016-03-23 0023.
 */
@Controller
@RequestMapping(value = {"/brandCommon"}, produces = {"application/json; charset=utf-8"})
public class PcmBrandCommonController extends BaseController {

    @Autowired
    private IPcmBrandCommonService brandCommonService;

    /**
     * 品牌校验
     *
     * @param para
     * @return
     */
    @RequestMapping(value = {"/validateBrand"})
    @ResponseBody
    public Map<String, Object> validateBrand(@RequestBody PcmBrandValidationPara para) {
        Integer brandType = para.getBrandType();
        if (brandType != null) {
            Boolean existence = null;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("brandName", para.getBrandName());
            if (brandType == Constants.PUBLIC_0) {
                existence = brandCommonService.isBrandGroupExistence(paramMap);
            }
            if (brandType == Constants.PUBLIC_1) {
                Integer shopType = para.getShopType();
                paramMap.put("shopType", shopType);
                existence = brandCommonService.isBrandExistence(paramMap);
            }
            return ResultUtil.creComSucResult("");
        } else {
            return ResultUtil.creComErrorResult(ComErrorCodeConstants.ErrorCode.PARA_EMPTY_ERROR.getErrorCode(), "没有传入brandType！");
        }
    }

}
