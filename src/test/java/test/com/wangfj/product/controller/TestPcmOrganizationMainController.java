package test.com.wangfj.product.controller;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.wangfj.core.utils.HttpUtil;
import com.wangfj.core.utils.JsonUtil;
import com.wangfj.product.core.controller.support.PcmOrgPara;
import com.wangfj.product.core.controller.support.SelectPcmOrganizationPara;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 组织机构测试
 *
 * @Class Name TestPcmOrganizationMainController
 * @Author wangx
 * @Create In 2015-8-18
 */
public class TestPcmOrganizationMainController {

    private static final Logger logger = LoggerFactory.getLogger(TestPcmOrganizationMainController.class);

    @Test
    public void testBase64() {
        String organizationCode = "21011";
        try {
            byte[] encodeBase64 = Base64.encodeBase64(organizationCode.getBytes("UTF-8"));
            System.out.println(new String(encodeBase64));//门店标识
        } catch (UnsupportedEncodingException e) {
            logger.info("添加门店生成门店标识获得门店编码字节码异常" + e.getMessage());
        }
    }


    @Test
    public void saveOrUpdateOrg() {

        PcmOrgPara para = new PcmOrgPara();

//		para.setOrganizationType(0);
        para.setOrganizationType(3);
        para.setGroupSid(1L);
        para.setOrganizationCode("21011");
        para.setOrganizationName("百货大楼");
//		para.setOrganizationStatus(0);
        para.setParentSid("12");
        // para.setStoreType(0);
        // para.setStoreType(2);
        para.setActionCode("U");

//        para.setPostCode("2109312");
        para.setBank("中国建设银行");

        String jsonString = JsonUtil.getJSONString(para);
        System.out.println(jsonString);
        String response = HttpUtil.doPost(
                "http://127.0.0.1:8043/pcm-admin-sdc/organization/saveOrUpdateOrg.htm",
                JsonUtil.getJSONString(para));
        System.out.println(response);
    }

    /**
     * 查询所有组织机构的部分信息
     *
     * @Methods Name findListOrgPart
     * @Create In 2015-8-20 By wangxuan void
     */
    @Test
    public void findListOrgPart() {

        SelectPcmOrganizationPara para = new SelectPcmOrganizationPara();

        // para.setOrganizationType("0");
        para.setOrganizationType(3);
        // para.setCurrentPage(1);
        // para.setCurrentPage("2");
        // para.setOrganizationCode("21011");
        // para.setOrganizationName("北京");
        // para.setOrganizationStatus(0);
        // para.setPageSize(10);
        // para.setParentSid("");
        // para.setStoreType(0);
        // para.setStoreType("2");

        String response = HttpUtil.doPost(
                "http://127.0.0.1:8043/pcm-admin-sdc/organization/findListOrgPart.htm",
                JsonUtil.getJSONString(para));
        System.out.println(response);
    }

    /**
     * 查询所有组织机构信息
     *
     * @Methods Name findListOrganization
     * @Create In 2015-8-20 By wangxuan void
     */
    @Test
    public void findListOrganization() {

        SelectPcmOrganizationPara para = new SelectPcmOrganizationPara();

        // para.setOrganizationType("0");
        para.setOrganizationType(3);
        // para.setCurrentPage(1);
        // para.setCurrentPage("2");
        // para.setOrganizationCode("21011");
        // para.setOrganizationName("北京");
        // para.setOrganizationStatus(0);
        // para.setPageSize(10);
        // para.setParentSid("");
        // para.setStoreType(0);
        // para.setStoreType("2");

        String response = HttpUtil.doPost(
                "http://127.0.0.1:8043/pcm-admin-sdc/organization/findListOrganization.htm",
                JsonUtil.getJSONString(para));
        System.out.println(response);
    }

    /**
     * 分页查找测试
     *
     * @Methods Name findPageOrganization
     * @Create In 2015-8-18 By wangx void
     */
    @Test
    public void findPageOrganization() {

        SelectPcmOrganizationPara para = new SelectPcmOrganizationPara();

        // para.setOrganizationType("0");
        para.setOrganizationType(3);
        para.setCurrentPage(1);
        // para.setCurrentPage("2");
        // para.setOrganizationCode("21011");
        para.setOrganizationName("北京");
        para.setOrganizationStatus(0);
        para.setPageSize(10);
        para.setParentSid("");
        para.setStoreType(0);
        // para.setStoreType("2");

        String response = HttpUtil.doPost(
                "http://127.0.0.1:8081/pcm-admin-sdc/organization/findPageOrganization.htm",
                JsonUtil.getJSONString(para));
        System.out.println(response);
    }

}
