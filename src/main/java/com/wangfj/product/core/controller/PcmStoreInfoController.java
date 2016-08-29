package com.wangfj.product.core.controller;

import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.framework.base.page.Page;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmStoreInfoQueryPara;
import com.wangfj.product.organization.domain.vo.PcmStoreInfoQueryDto;
import com.wangfj.product.organization.domain.vo.PcmStoreInfoResultDto;
import com.wangfj.product.organization.service.intf.IPcmStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 门店信息
 * Created by wangxuan on 2016-08-29 0029.
 */
@Controller
@RequestMapping(value = {"/storeInfo"}, produces = {"application/json;charset=utf-8"})
public class PcmStoreInfoController extends BaseController {

    @Autowired
    private IPcmStoreInfoService storeInfoService;

    /**
     * 分页查询门店信息
     *
     * @param para
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/findPageStoreInfo"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findPageStoreInfo(@RequestBody PcmStoreInfoQueryPara para) {
        PcmStoreInfoQueryDto queryDto = new PcmStoreInfoQueryDto();
        org.springframework.beans.BeanUtils.copyProperties(para, queryDto);

        Page<PcmStoreInfoResultDto> pageStoreInfo = storeInfoService.findPageStoreInfo(queryDto);
        return ResultUtil.creComSucResult(pageStoreInfo);
    }

    /**
     * 查询门店信息List
     *
     * @param para
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/findListStoreInfo"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> findListStoreInfo(@RequestBody PcmStoreInfoQueryPara para) {
        PcmStoreInfoQueryDto queryDto = new PcmStoreInfoQueryDto();
        org.springframework.beans.BeanUtils.copyProperties(para, queryDto);
        List<PcmStoreInfoResultDto> listStoreInfo = storeInfoService.findListStoreInfo(queryDto);
        return ResultUtil.creComSucResult(listStoreInfo);
    }


}
