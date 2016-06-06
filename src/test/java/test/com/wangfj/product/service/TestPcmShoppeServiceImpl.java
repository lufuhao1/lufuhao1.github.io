package test.com.wangfj.product.service;

import java.util.HashMap;
import java.util.Map;

import com.wangfj.product.organization.domain.vo.PcmShoppeResultDto;
import com.wangfj.product.organization.domain.vo.SelectPcmShoppeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.product.organization.service.intf.IPcmShoppeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPcmShoppeServiceImpl {

    @Autowired
    private IPcmShoppeService shoppeService;

    @Test
    public void findShoppeForSAPERPImport() {
        SelectPcmShoppeDto dto = new SelectPcmShoppeDto();
//        dto.setShopCode("D001");
//        dto.setSupplyCode("0007000091");
//        dto.setSupplyCode("0004500000");

        dto.setShopCode("21011");
        dto.setSupplyCode("0008660");
        PcmShoppeResultDto shoppe = shoppeService.findShoppeForSAPERPImport(dto);
        System.out.println(shoppe);
    }

    @Test
    public void generateShoppeCode() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("sid", "149");
        paramMap.put("shopCode", "40001");
        String code = shoppeService.generateShoppeCode(paramMap);
        System.out.println(code);
    }

    @Test
    public void generateEShoppeCode() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopCode", "D001");
        paramMap.put("shoppeCodeStart", "2");
        String code = shoppeService.generateEShoppeCode(paramMap);
        System.out.println(code);
    }

    @Test
    public void generateEBusinessShoppeCode() {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("shopCode", "D001");
        String code = shoppeService.generateEBusinessShoppeCode(paramMap);
        System.out.println(code);
    }

}
