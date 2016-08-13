package test.com.wangfj.product.persistence.stocks;

import com.wangfj.product.stocks.domain.entity.PcmStockLockRecord;
import com.wangfj.product.stocks.persistence.PcmStockLockRecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangxuan on 2016-08-10 0010.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPcmStockLockRecordMapper {

    @Autowired
    private PcmStockLockRecordMapper stockLockRecordMapper;

    @Test
    public void deleteByBillsNo() {

        String billsNo = "210110000050171011-1";
        PcmStockLockRecord psr = new PcmStockLockRecord();
        psr.setBillsNo(billsNo);
//        int i = stockLockRecordMapper.deleteByBillsNo(billsNo);
        int i = stockLockRecordMapper.deleteByBillsNo(psr.getBillsNo());
        System.out.println("=========================" + i);
    }


}
