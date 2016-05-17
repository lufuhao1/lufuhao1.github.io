package test.com.wangfj.product.persistence.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.product.organization.domain.entity.PcmShoppe;
import com.wangfj.product.organization.domain.vo.PcmShoppeResultDto;
import com.wangfj.product.organization.domain.vo.SelectPcmShoppeDto;
import com.wangfj.product.organization.persistence.PcmShoppeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestShoppeMapper {

	@Autowired
	public PcmShoppeMapper pcmShoppeMapper;

	@Test
	public void testShoppeMapper() {
		PcmShoppe record = new PcmShoppe();
		pcmShoppeMapper.insertSelective(record);
	}

	@Test
	public void selectShoppeListByParam() {

		SelectPcmShoppeDto dto = new SelectPcmShoppeDto();

		dto.setSid(25L);

		List<PcmShoppeResultDto> shoppeList = pcmShoppeMapper.selectShoppeListByParam(dto);
		System.out.println(shoppeList);

	}

	@Test
	public void selectListByParam() {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("shoppeCode", "2101100001");
		List<PcmShoppe> list = pcmShoppeMapper.selectListByParam(paramMap);
		System.out.println(list);
	}

}
