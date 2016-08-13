package com.wangfj.product.core.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangfj.core.cache.RedisVo;
import com.wangfj.core.framework.base.controller.BaseController;
import com.wangfj.core.utils.CacheUtils;
import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.core.utils.PropertyConfigurer;
import com.wangfj.core.utils.PropertyUtil;
import com.wangfj.core.utils.RedisUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.core.controller.support.CdnPara;
import com.wangfj.product.stocks.service.intf.IPcmLockAttributeService;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	@Autowired
	private IPcmLockAttributeService pcmLockAttributeService;
	@Autowired
	protected CacheUtils utils;
	@Autowired
	private RedisUtil redisUtil;

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@RequestMapping("/reset")
	@ResponseBody
	public Map<String, Object> reset() throws Exception {
		pcmLockAttributeService.resetAttribute();
		return ResultUtil.creComSucResult("");
	}

	@RequestMapping("/test")
	@ResponseBody
	public Map<String, Object> test() throws Exception {
		return ResultUtil.creComSucResult("");
	}

	@RequestMapping("/getCache")
	@ResponseBody
	public String getCache(@RequestBody RedisVo para) throws Exception {
		String result = "";
		if (StringUtils.isNotBlank(para.getField())) {
			result = redisUtil.getHSet(para.getField(), para.getKey());
		} else {
			result = redisUtil.get(para.getKey(), "false");
		}
		return result;
	}

	@RequestMapping("/delCache")
	@ResponseBody
	public String delCache(@RequestBody RedisVo para) throws Exception {
		String result = "";
		if (StringUtils.isNotBlank(para.getField())) {
			long value = redisUtil.delHSet(para.getField(), para.getKey());
			result = String.valueOf(value);
		} else {
			boolean flag = redisUtil.del(para.getKey());
			result = String.valueOf(flag);
		}
		return result;
	}

	/**
	 * 批量删缓存 例如 pcm_getPrice*
	 * 
	 * @Methods Name redisSpuCMSSHopperInfo
	 * @Create In 2016年6月14日 By kongqf
	 * @param para
	 *            void
	 */
	@RequestMapping("/batdelCache")
	@ResponseBody
	public void redisSpuCMSSHopperInfo(@RequestBody RedisVo para) {
		List<String> list = redisUtil.getKeys(para.getKey());
		for (String str : list) {
			logger.warn(str + ":" + redisUtil.del(str));
		}
	}

	/**
	 * 根据key批量删除缓存
	 * 
	 * @Methods Name batchCleanCache
	 * @Create In 2016年8月13日 By kongqf
	 * @param para
	 *            void
	 */
	@RequestMapping("/batchCleanCache")
	@ResponseBody
	public String batchCleanCache(@RequestBody List<String> keys) {
		Map<String, String> resultMap = new HashMap<String, String>();
		boolean delResult = false;
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				delResult = false;
				String value = redisUtil.get(key, "0000");
				if (!"0000".equals(value)) {
					delResult = redisUtil.del(key);
					resultMap.put(key, "delResult:" + delResult);
				}
			}
		}
		return JsonUtil.getJSONString(resultMap);
	}

	/**
	 * 清理价格缓存
	 * 
	 * @Methods Name clearpricercache
	 * @Create In 2015年9月17日 By kongqf
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/clearcache", method = RequestMethod.POST)
	@ResponseBody
	public String clearPricerCache(@RequestBody RedisVo para) {
		long flag = CacheUtils.expire(para.getField(), 1);
		return String.valueOf(flag);
	}

	@RequestMapping(value = "/cacheDelTest", method = RequestMethod.POST)
	@ResponseBody
	public String cacheDelTest(@RequestBody RedisVo para) {
		// if (utils.cacheFlag) {
		boolean setFlag = redisUtil.set("testdel", "1111");
		boolean delFlag = redisUtil.del("testdel");
		System.out.println("setFlag:" + setFlag);
		System.out.println("delFlag:" + delFlag);
		// }

		return utils.cacheFlag + "";
	}

	@RequestMapping(value = "/flushCdn", method = RequestMethod.POST)
	@ResponseBody
	public String flushCdn(@RequestBody CdnPara cdnPara) {

		String varnishIp = PropertyUtil.getSystemUrl("varniship");
		String result = HttpUtil.doPost(varnishIp, cdnPara.getFlushPath());
		if (result.contains("successful")) {
			String order = PropertyUtil.getSystemUrl("orderStr");
			// String order="python /opt/cdn/cdnapi.py dir ";
			String infoStr = "";
			Process proc;
			try {
				proc = Runtime.getRuntime().exec(order + cdnPara.getFlushPath());
				BufferedReader in = new BufferedReader(
						new InputStreamReader(proc.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					infoStr = infoStr + line;
				}
				in.close();
				proc.waitFor();
				if (infoStr.contains("success")) {
					return "true";
				} else {
					logger.error(infoStr);
					return "false";
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "false";
			}
		} else {
			return "false";
		}

	}
}
