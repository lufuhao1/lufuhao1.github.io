package test.com.wangfj.product.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.wfj.platform.util.zookeeper.discovery.SpringMvcServiceProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.product.brand.domain.entity.PcmBrand;
import com.wangfj.product.brand.service.intf.IPcmBrandService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPcmBrandServiceImpl {

    @Autowired
    private IPcmBrandService pcmBrandService;

    @Autowired
    private SpringMvcServiceProvider provider;

    @Test
    public void test() {

        Gson gson = new Gson();
        String json = "{\n" +
                "  \"data\": {\n" +
                "    \"currentPage\": 1,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"count\": 1,\n" +
                "    \"pages\": 1,\n" +
                "    \"start\": 0,\n" +
                "    \"limit\": 10,\n" +
                "    \"list\": [\n" +
                "      {\n" +
                "        \"sid\": 15,\n" +
                "        \"supplyName\": \"20160311测试供应商代销*D\",\n" +
                "        \"postcode\": null,\n" +
                "        \"city\": null,\n" +
                "        \"country\": null,\n" +
                "        \"zone\": null,\n" +
                "        \"shopRegion\": null,\n" +
                "        \"address\": null,\n" +
                "        \"phone\": null,\n" +
                "        \"fax\": null,\n" +
                "        \"email\": null,\n" +
                "        \"lastOptUser\": null,\n" +
                "        \"lastOptDates\": null,\n" +
                "        \"lastOptDateStr\": null,\n" +
                "        \"shopSid\": \"21011\",\n" +
                "        \"supplyCode\": \"000868K\",\n" +
                "        \"supplyType\": null,\n" +
                "        \"status\": null,\n" +
                "        \"shortName\": null,\n" +
                "        \"businessPattern\": \"1\",\n" +
                "        \"street\": null,\n" +
                "        \"orgCode\": null,\n" +
                "        \"industry\": null,\n" +
                "        \"bizCertificateNo\": null,\n" +
                "        \"taxType\": null,\n" +
                "        \"taxNumbe\": null,\n" +
                "        \"bank\": null,\n" +
                "        \"bankNo\": null,\n" +
                "        \"registeredCapital\": null,\n" +
                "        \"enterpriseProperty\": null,\n" +
                "        \"businessCategory\": null,\n" +
                "        \"legalPerson\": null,\n" +
                "        \"legalPersonIcCode\": null,\n" +
                "        \"legalPersonContact\": null,\n" +
                "        \"agent\": null,\n" +
                "        \"agentIcCode\": null,\n" +
                "        \"agentContact\": null,\n" +
                "        \"contact\": null,\n" +
                "        \"contactTitle\": null,\n" +
                "        \"contactIcCode\": null,\n" +
                "        \"contactWay\": null,\n" +
                "        \"businessScope\": null,\n" +
                "        \"keySupplier\": null,\n" +
                "        \"taxRates\": 17,\n" +
                "        \"taxRateStr\": \"17.000000\",\n" +
                "        \"inOutCity\": null,\n" +
                "        \"admissionDate\": null,\n" +
                "        \"returnSupply\": null,\n" +
                "        \"joinSite\": null,\n" +
                "        \"apartOrder\": null,\n" +
                "        \"dropship\": null,\n" +
                "        \"erpSupplierCode\": null,\n" +
                "        \"zlyFlag\": null,\n" +
                "        \"zzxxhcFlag\": null,\n" +
                "        \"field1\": null,\n" +
                "        \"field2\": null,\n" +
                "        \"field3\": null,\n" +
                "        \"actionCode\": null,\n" +
                "        \"zflg\": null\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"success\": \"true\"\n" +
                "}";
        String toJson = gson.toJson(json);
        System.out.println(toJson);

//        try {
//            String serviceAddress = provider.provideServiceAddress("pcm-item-query");
//            System.out.println(serviceAddress);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void deleteBrand() {

        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("brandSid", "1");
        // paraMap.put("brandSid", 1);

        Integer count = pcmBrandService.deleteBrand(paraMap);
        System.out.println(count);

    }

    @Test
    public void updatePcmBrand() {

        PcmBrand brand = new PcmBrand();

        brand.setBrandSid("1");
        brand.setBrandName("ad");
        brand.setShopType(0);
        brand.setSpell("adi");

        Integer count = pcmBrandService.updatePcmBrand(brand);
        System.out.println(count);

    }

}
