package test.com.wangfj.product.service;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangfj.core.utils.CacheUtils;
import com.wangfj.core.utils.RedisUtil;
import com.wangfj.core.utils.ResultUtil;
import com.wangfj.product.demo.domain.dto.UpdateUsersDto;
import com.wangfj.product.demo.domain.entity.Users;
import com.wangfj.product.demo.service.intf.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestUserService {

	@Autowired
	public UserService userService;
	// @Autowired
	// protected MongoTemplate mongoTemplate;
	@Autowired
	protected CacheUtils utils;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Test
	public void test() {
		// updateUser();
		// insertUser();
		insert();
		// get();
		// del();
	}

	public void updateUser() {
		final UpdateUsersDto para = new UpdateUsersDto();
		para.setName("ko");
		para.setSid(2);

		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {

				// 你们的功能放在这里
				System.out.println("------------------------------------");
				userService.updateUser(para);
				System.out.println(para.getSid());
				System.out.println("=====================================");
			}
		});
	}

	public void insertUser() {
		Users para = new Users();
		para.setName("hello");
		para.setSid(2);
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 你们的功能放在这里

			}
		});
	}

	@Test
	public void insert() {
		redisUtil.set("hello", "kongxs");
		// for (int i = 0; i < 200; i++) {
		// redisUtil.set("hu" + i, "kongxs");
		// }

	}

	public void get() {
		// redisUtil.set("hello","kongxs");
		int a = 0;
		for (int i = 0; i < 20000; i++) {
			String str = redisUtil.get("hh" + i, "111");
			if (StringUtils.isNotBlank(str)) {
				a++;
			}
		}
		System.out.println(a);

	}

	public void del() {
		// redisUtil.set("hello","kongxs");
		int a = 0;
		for (int i = 0; i < 20000; i++) {
			redisUtil.del("hh" + i);
		}
		// System.out.println(a);

	}

	@Test
	public void del1() {

		Set<String> ss = redisUtil.hkeys("pcm_selectCateGory");

		for (String s : ss) {
			redisUtil.delHSet("pcm_selectCateGory", s);
		}

		System.out.println(ss.toString());

	}

	@Test
	public void del1Test() {
		if (utils.cacheFlag) {
			redisUtil.set("testdel", "1111");
			boolean flag = redisUtil.del("testdel");
			System.out.println(flag);
		}
	}
}
