package com.wangfj.product.core.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.wangfj.core.framework.base.page.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.PcmContractLogPara;
import com.wangfj.product.core.controller.support.PcmContractLogQueryPara;
import com.wangfj.product.maindata.domain.vo.ContractERPDto;
import com.wangfj.product.maindata.domain.vo.PcmContractLogDto;
import com.wangfj.product.maindata.domain.vo.PcmContractLogPartDto;
import com.wangfj.product.maindata.domain.vo.PcmContractLogQueryDto;
import com.wangfj.product.maindata.service.intf.IPcmContractLogService;

@Controller
@RequestMapping(value = "/contractLog", produces = "application/json; charset=utf-8")
public class PcmContractLogController {

	@Autowired
	private IPcmContractLogService contractLog;

	@RequestMapping(value = "/selectContractLogByParam", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> selectContractLogByParam(@RequestBody @Valid PcmContractLogPara para) {
		PcmContractLogDto dto = new PcmContractLogDto();
		try {
			BeanUtils.copyProperties(dto, para);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		ContractERPDto res = contractLog.selectContractLogByParam(dto);
		return ResultUtil.creComSucResult(res);
	}

	/**
	 * 查询要约的部分信息
	 * 
	 * @Methods Name findContractLogList
	 * @Create In 2015-12-8 By wangxuan
	 * @param para
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/findContractLogList", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findContractLogList(@RequestBody PcmContractLogQueryPara para) {

		PcmContractLogQueryDto dto = new PcmContractLogQueryDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);

		List<PcmContractLogPartDto> dtoList = contractLog.findContractLogList(dto);

		return ResultUtil.creComSucResult(dtoList);
	}

	/**
	 * 分页查询要约信息
	 * 
	 * @param para
	 * @return
	 */
	@RequestMapping(value = "/findPageContract", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findPageContract(@RequestBody PcmContractLogQueryPara para) {

		PcmContractLogQueryDto dto = new PcmContractLogQueryDto();
		org.springframework.beans.BeanUtils.copyProperties(para, dto);
		Page<PcmContractLogDto> page = contractLog.findPageContract(dto);

		return ResultUtil.creComSucResult(page);

	}

}
