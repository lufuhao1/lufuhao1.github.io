package test.com.wangfj.product.persistence.supplier;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.product.supplier.domain.entity.PcmSupplyShoppeRelation;
import com.wangfj.product.supplier.persistence.PcmSupplyShoppeRelationMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestPcmSupplyShoppeRelationMapper {

	@Autowired
	private PcmSupplyShoppeRelationMapper supplyShoppeRelationMapper;

	@Test
	public void selectListByParam() {

		PcmSupplyShoppeRelation record = new PcmSupplyShoppeRelation();
		// record.setSupplySid("42");
		record.setSid(1L);
		List<PcmSupplyShoppeRelation> list = supplyShoppeRelationMapper.selectListByParam(record);
		System.out.println(list);

	}

}
