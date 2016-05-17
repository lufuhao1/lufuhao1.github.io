package com.wangfj.product.core.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.constants.ComErrorCodeConstants.ErrorCode;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PrctureInfoPara;
import com.wangfj.product.maindata.domain.entity.PcmPictureUrl;
import com.wangfj.product.maindata.domain.vo.PcmPictureDto;
import com.wangfj.product.maindata.service.intf.IPcmProductPictureService;

@Controller
@RequestMapping("productPrcture")
public class ProductPictureController {
	@Autowired
	private IPcmProductPictureService ppService;

	/**
	 * 根据产品编码,色系,(是否原图)查询商品图片
	 * 
	 * @Methods Name queryPrctureInfoByPara
	 * @Create In 2015年10月14日 By zhangxy
	 * @param para
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPrctureInfoByPara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> queryPrctureInfoByPara(@RequestBody PrctureInfoPara para) {
		if (StringUtils.isBlank(para.getColor()) || StringUtils.isBlank(para.getSpuCode())) {
			return ResultUtil.creComErrorResult(ErrorCode.PARA_NORULE_ERROR.getErrorCode(),
					ErrorCode.PARA_NORULE_ERROR.getMemo());
		}
		PcmPictureDto dto = new PcmPictureDto();
		dto.setSkuSid(para.getSpuCode());
		dto.setColorCode(para.getColor());
		dto.setIfDelete(para.getIfDelete());
		dto.setIsThumbnail(para.getIsThumbnail());
		dto.setIsModel(para.getIsModel());
		List<PcmPictureUrl> list = ppService.queryPrctureInfoByPara(dto);
		return ResultUtil.creComSucResult(list);
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPrctureInfoByPara1", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> queryPrctureInfoByPara1(@RequestBody PrctureInfoPara para) {
		PcmPictureDto dto = new PcmPictureDto();
		dto.setSkuSid(para.getSpuCode());
		dto.setColorCode(para.getColor());
		dto.setIfDelete(para.getIfDelete());
		dto.setIsThumbnail(para.getIsThumbnail());
		dto.setIsModel(para.getIsModel());
		List<PcmPictureUrl> list = ppService.queryPrctureInfoByPara(dto);
		return ResultUtil.creComSucResult(list);
	}

	/**
	 * 根据产品编码,查询商品色系
	 * 
	 * @Methods Name queryColorBySpu
	 * @Create In 2015年10月14日 By zhangxy
	 * @param para
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/queryColorBySpu", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> queryColorBySpu(@RequestBody PrctureInfoPara para) {
		if (StringUtils.isBlank(para.getSpuCode())) {
			return ResultUtil.creComErrorResult(ErrorCode.PARA_NORULE_ERROR.getErrorCode(),
					ErrorCode.PARA_NORULE_ERROR.getMemo());
		}
		List<Map<String, Object>> list = ppService.queryColorBySpu(para.getSpuCode());
		return ResultUtil.creComSucResult(list);
	}

	/**
	 * 根据产品编码,色系 返回最大图片序号
	 * 
	 * @Methods Name queryColorBySpu
	 * @Create In 2015年10月14日 By zhangxy
	 * @param para
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/getSortByOara", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> getSortByOara(@RequestBody PrctureInfoPara para) {
		if (StringUtils.isBlank(para.getColor()) || StringUtils.isBlank(para.getSpuCode())) {
			return ResultUtil.creComErrorResult(ErrorCode.PARA_NORULE_ERROR.getErrorCode(),
					ErrorCode.PARA_NORULE_ERROR.getMemo());
		}
		PcmPictureDto dto = new PcmPictureDto();
		dto.setSkuSid(para.getSpuCode());
		dto.setColorCode(para.getColor());
		dto.setIfDelete(para.getIfDelete());
		dto.setIsThumbnail(para.getIsThumbnail());
		Integer sort = ppService.getSortByOara(dto);
		return ResultUtil.creComSucResult(sort);
	}
}
