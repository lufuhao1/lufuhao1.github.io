package test.com.wangfj.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.brand.domain.vo.SelectPcmBrandPageDto;
import com.wangfj.product.core.controller.support.PcmBrandPara;
import com.wangfj.product.core.controller.support.PcmBrandRelationPara;
import com.wangfj.product.core.controller.support.SelectPcmBrandPagePara;

/**
 * 品牌测试
 * 
 * @Class Name TestPcmBrandController
 * @Author wangx
 * @Create In 2015-8-7
 */
public class TestPcmBrandController {

	@Test
	public void testDemo() {

		String str = "123456789";
		System.out.println(str.substring(2));

	}

	@Test
	public void test() {


	}

	/**
	 * 批量添加门店品牌与集团品牌的关系
	 * 
	 * @Methods Name addRelationList
	 * @Create In 2015-9-15 By wangxuan void
	 */
	@Test
	public void addRelationList() {

		List<PcmBrandRelationPara> brandRelationParaList = new ArrayList<PcmBrandRelationPara>();

		PcmBrandRelationPara brandRelationPara1 = new PcmBrandRelationPara();
		brandRelationPara1.setFromSystem("PCM");
		brandRelationPara1.setSid("7");
		brandRelationPara1.setParentSid("1");

		brandRelationParaList.add(brandRelationPara1);

		PcmBrandRelationPara brandRelationPara2 = new PcmBrandRelationPara();
		brandRelationPara2.setFromSystem("PCM");
		brandRelationPara2.setSid("8");
		brandRelationPara2.setParentSid("1");

		brandRelationParaList.add(brandRelationPara2);

		String jsonString = JsonUtil.getJSONString(brandRelationParaList);
		System.out.println(brandRelationParaList);
		String response = HttpUtil
				.doPost("http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/addRelationList.htm",
						jsonString);
		System.out.println(response);

	}

	/**
	 * 查询某集团品牌下的门店品牌
	 * 
	 * @Methods Name findListBrandByParentSid
	 * @Create In 2015-8-10 By wangx void
	 */
	@Test
	public void findListBrandByParentSid() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("sid", "1");
		// paraMap.put("sid", "-1");

		String jsonString = JsonUtil.getJSONString(paraMap);
		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findListBrandByParentSid.htm",
				jsonString);
		System.out.println(response);

	}

	/**
	 * 根据门店的sid和集团品牌的sid查询门店品牌
	 * 
	 * @Methods Name getListBrandByShopSidAndParentSid
	 * @Create In 2015-9-21 By wangxuan void
	 */
	@Test
	public void getListBrandByShopSidAndParentSid() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		// paraMap.put("parentSid", "10");
		paraMap.put("shopSid", "25");
		paraMap.put("shopCode", "21016");

		String jsonString = JsonUtil.getJSONString(paraMap);
		String response = HttpUtil
				.doPost("http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/getListBrandByShopSidAndParentSid.htm",
						jsonString);
		System.out.println(response);

	}

	@Test
	public void getListBrandByShopSidAndSkuSid() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("shopSid", "32");
		paraMap.put("skuSid", "1000000000902");

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/getListBrandByShopSidAndSkuSid.htm";
		String jsonString = JsonUtil.getJSONString(paraMap);
		String response = HttpUtil.doPost(url, jsonString);
		System.out.println(response);

	}

	@Test
	public void getPageBrandFromShopBrandRelation() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("parentSid", "10");
		paraMap.put("shopSid", "149");
		// paraMap.put("shopCode", "21016");

		String jsonString = JsonUtil.getJSONString(paraMap);
		String url = "http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/getPageBrandFromShopBrandRelation.htm";
		String response = HttpUtil.doPost(url, jsonString);
		System.out.println(response);

	}

	/**
	 * 查询所有没有关联集团品牌的门店品牌
	 * 
	 * @Methods Name findListBrandWithoutRelation
	 * @Create In 2015-9-14 By wangxuan void
	 */
	@Test
	public void findListBrandWithoutRelation() {

		String response = HttpUtil
				.doPost("http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findListBrandWithoutRelation.htm",
						JsonUtil.getJSONString(null));
		System.out.println(response);

	}

	/**
	 * 查询某集团品牌下的门店品牌（带分页）
	 * 
	 * @Methods Name findPageBrandByGroupBrandSid
	 * @Create In 2015-8-10 By wangx void
	 */
	@Test
	public void findPageBrandByParentSid() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("sid", "1");

		// paraMap.put("currentPage", 1);
		// paraMap.put("pageSize", 1);

		String jsonString = JsonUtil.getJSONString(paraMap);
		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findPageBrandByParentSid.htm",
				jsonString);
		System.out.println(response);

	}

	/**
	 * 删除门店品牌
	 * 
	 * @Methods Name deleteBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void deleteBrand() {

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("sid", "1");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/deleteBrand.htm",
				JsonUtil.getJSONString(paraMap));
		System.out.println(response);
	}

	/**
	 * 创建单个门店品牌
	 * 
	 * @Methods Name addPcmBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void addPcmBrand() {

		PcmBrandPara pcmBrandPara = new PcmBrandPara();
		pcmBrandPara.setFromSystem("PCM");

		pcmBrandPara.setBrandName("坚果青色");
		pcmBrandPara.setBrandType(1);
		pcmBrandPara.setShopType("0");
		// pcmBrandPara.setBrandNameEn("Phone-Red");
		// pcmBrandPara.setParentSid("1");

		// pcmBrandPara.setSpell("拼音");
		pcmBrandPara.setSpell("jianguo");

		pcmBrandPara.setBrandcorp("品牌公司");
		pcmBrandPara.setBrandDesc("品牌描述");
		pcmBrandPara.setBrandNameSecond("品牌第二个名字");
		pcmBrandPara.setBrandpic1("图片1");
		pcmBrandPara.setBrandpic2("图片2");
		pcmBrandPara.setOptRealName("操作人");
		pcmBrandPara.setOptUserSid(1L);
		pcmBrandPara.setPhotoBlacklistBit(1L);
		pcmBrandPara.setPictureUrl("图片路径");
		pcmBrandPara.setBrandDesc("品牌描述");
		pcmBrandPara.setBrandSpecialty("品牌特点");
		pcmBrandPara.setBrandSuitability("适合人群");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/addPcmBrand.htm",
				JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);
	}

	/**
	 * 创建单个门店品牌
	 * 
	 * @Methods Name createPcmBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void createPcmBrand() {

		PcmBrandPara pcmBrandPara = new PcmBrandPara();
		pcmBrandPara.setFromSystem("PCM");

		pcmBrandPara.setBrandName("品牌名称100");
		// pcmBrandPara.setBrandType(1);
		pcmBrandPara.setShopSid("1");
		pcmBrandPara.setShopType("0");

		// pcmBrandPara.setSpell("拼音");
		pcmBrandPara.setSpell("pinyin");

		pcmBrandPara.setBrandcorp("品牌公司");
		pcmBrandPara.setBrandDesc("品牌描述");
		pcmBrandPara.setBrandNameSecond("品牌第二个名字");
		pcmBrandPara.setBrandpic1("图片1");
		pcmBrandPara.setBrandpic2("图片2");
		pcmBrandPara.setBrandType(0);
		pcmBrandPara.setOptRealName("操作人");
		pcmBrandPara.setOptUserSid(1L);
		pcmBrandPara.setPhotoBlacklistBit(1L);
		pcmBrandPara.setPictureUrl("图片路径");

		pcmBrandPara.setBrandDesc("品牌描述");
		pcmBrandPara.setBrandSpecialty("品牌特点");
		pcmBrandPara.setBrandSuitability("适合人群");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/createPcmBrand.htm",
				JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);
	}

	/**
	 * 门店品牌分页查询
	 * 
	 * @Methods Name findBrandForPage
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void findBrandForPage() {

		SelectPcmBrandPagePara pcmBrandPara = new SelectPcmBrandPagePara();

		pcmBrandPara.setFromSystem("PCM");

		// pcmBrandPara.setCurrentPage(2);
		// pcmBrandPara.setPageSize(1);
		pcmBrandPara.setBrandName("a");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findBrandForPage.htm",
				JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);

	}

	/**
	 * 品牌分页查询
	 * 
	 * @Methods Name findPageBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void findPageBrand() {

		SelectPcmBrandPagePara pcmBrandPara = new SelectPcmBrandPagePara();

		pcmBrandPara.setFromSystem("PCM");

		// pcmBrandPara.setSid(1);

		// pcmBrandPara.setCurrentPage(2);
		// pcmBrandPara.setPageSize(100);
		// pcmBrandPara.setBrandName("坚果");
		// pcmBrandPara.setBrandName("双安");

		// pcmBrandPara.setBrandType(1);

		String url = "http://127.0.0.1:8043/pcm-admin-sdc/pcmAdminBrand/findPageBrand.htm";
		String response = HttpUtil.doPost(url, JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);

	}

	/**
	 * 品牌查询
	 * 
	 * @Methods Name findListBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void findListBrand() {

		// SelectPcmBrandPagePara pcmBrandPara = new SelectPcmBrandPagePara();
		//
		// pcmBrandPara.setFromSystem("PCM");
		// pcmBrandPara.setBrandType(0);
		// String str = "{'brandType':'0', 'fromSystem':'PCM'}";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("brandType", 0);
		paramMap.put("fromSystem", "PCM");

		// pcmBrandPara.setCurrentPage(2);
		// pcmBrandPara.setPageSize(1);
		// pcmBrandPara.setBrandName("a");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findListBrand.htm",
				JsonUtil.getJSONString(paramMap));
		System.out.println(response);

	}

	/**
	 * 分页查询门店品牌及其集团品牌
	 * 
	 * @Methods Name findPageBrandAndBrandGroup
	 * @Create In 2015-8-17 By wangx void
	 */
	@Test
	public void findPageBrandAndBrandGroup() {

		SelectPcmBrandPagePara pcmBrandPara = new SelectPcmBrandPagePara();

		pcmBrandPara.setFromSystem("PCM");

		pcmBrandPara.setCurrentPage(1);
		pcmBrandPara.setPageSize(10);
		// pcmBrandPara.setBrandName("a");
		// pcmBrandPara.setSid(8L);

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/findPageBrandAndBrandGroup.htm",
				JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);

	}

	/**
	 * 修改门店品牌
	 * 
	 * @Methods Name updatePcmBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void updatePcmBrand() {

		PcmBrandPara pcmBrandPara = new PcmBrandPara();
		pcmBrandPara.setFromSystem("PCM");

		pcmBrandPara.setSid(14L);

		pcmBrandPara.setBrandName("品牌名称1");

		pcmBrandPara.setPictureUrl("图片路径1");
		pcmBrandPara.setBrandcorp("品牌公司");
		pcmBrandPara.setBrandDesc("品牌描述");
		pcmBrandPara.setBrandNameSecond("品牌第二个名字");
		pcmBrandPara.setBrandpic1("图片1");
		pcmBrandPara.setBrandpic2("图片2");
		pcmBrandPara.setBrandType(0);
		pcmBrandPara.setOptRealName("操作人");
		pcmBrandPara.setOptUserSid(1L);
		pcmBrandPara.setPhotoBlacklistBit(1L);
		// pcmBrandPara.setShopType(0);
		pcmBrandPara.setSpell("pinyin");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/updatePcmBrand.htm",
				JsonUtil.getJSONString(pcmBrandPara));
		System.out.println(response);

	}

	/**
	 * 修改门店品牌
	 * 
	 * @Methods Name updateBrand
	 * @Create In 2015-8-18 By wangx void
	 */
	@Test
	public void updateBrand() {

		PcmBrandPara brandPara = new PcmBrandPara();
		brandPara.setFromSystem("PCM");

		brandPara.setSid(30L);
		brandPara.setShopType("0");
		brandPara.setBrandType(1);

		brandPara.setBrandName("品牌名称1");

		brandPara.setPictureUrl("图片路径1");
		brandPara.setBrandcorp("品牌公司");
		brandPara.setBrandDesc("品牌描述");
		brandPara.setBrandNameSecond("品牌第二个名字");
		brandPara.setBrandpic1("图片1");
		brandPara.setBrandpic2("图片2");
		brandPara.setOptRealName("操作人");
		brandPara.setOptUserSid(1L);
		brandPara.setPhotoBlacklistBit(1L);
		brandPara.setSpell("pinyin");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/updateBrand.htm",
				JsonUtil.getJSONString(brandPara));
		System.out.println(response);

	}

	/**
	 * 修改门店品牌与集团品牌的关系
	 * 
	 * @Methods Name updatePcmBrand
	 * @Create In 2015-8-7 By wangx void
	 */
	@Test
	public void updateRelation() {

		PcmBrandRelationPara brandRelationPara = new PcmBrandRelationPara();
		brandRelationPara.setFromSystem("PCM");

		brandRelationPara.setSid("14");
		brandRelationPara.setParentSid("11");
		// brandRelationPara.setParentSid("1");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/updateRelation.htm",
				JsonUtil.getJSONString(brandRelationPara));
		System.out.println(response);

	}

	/**
	 * 删除门店品牌与集团品牌的关系
	 * 
	 * @Methods Name deleteRelation
	 * @Create In 2015-8-18 By wangx void
	 */
	@Test
	public void deleteRelation() {

		Map<String, Object> para = new HashMap<String, Object>();
		para.put("sid", "99");

		String response = HttpUtil.doPost(
				"http://127.0.0.1:8081/pcm-admin-sdc/pcmAdminBrand/deleteRelation.htm",
				JsonUtil.getJSONString(para));
		System.out.println(response);

	}

}
