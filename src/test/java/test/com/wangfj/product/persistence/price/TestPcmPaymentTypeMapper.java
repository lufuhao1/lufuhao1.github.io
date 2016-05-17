package test.com.wangfj.product.persistence.price;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.product.price.domain.vo.PcmPaymentOrganDto;
import com.wangfj.product.price.domain.vo.SelectPaymentDto;
import com.wangfj.product.price.persistence.PcmPaymentTypeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestPcmPaymentTypeMapper {

	@Autowired
	public PcmPaymentTypeMapper paymentTypeMapper;

	@Test
	public void test() {

	}

	@Test
	public void selectShopPaymentTypeListCount() {

		SelectPaymentDto selectPaymentDto = new SelectPaymentDto();
		// selectPaymentDto.setStatus("1");
		Integer count = paymentTypeMapper.selectShopPaymentTypeListCount(selectPaymentDto);

		System.out.println(count);

	}

	@Test
	public void selectShopPaymentTypeListByParam() {

		SelectPaymentDto selectPaymentDto = new SelectPaymentDto();
		selectPaymentDto.setStatus("1");
		List<PcmPaymentOrganDto> list = paymentTypeMapper
				.selectShopPaymentTypeListByParam(selectPaymentDto);

		System.out.println(list);

	}

}
