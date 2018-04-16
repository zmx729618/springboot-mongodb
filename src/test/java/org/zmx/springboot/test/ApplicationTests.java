package org.zmx.springboot.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zmx.springboot.SpringbootMongodbApplication;
import org.zmx.springboot.User;
import org.zmx.springboot.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMongodbApplication.class)
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

/*	@Before
	public void setUp() {
		userRepository.deleteAll();
	}*/

	@Test
	public void test() throws Exception {

		// 创建三个User，并验证User总数
		userRepository.save(new User("a11111", "didi", 30));
		userRepository.save(new User("b22222", "mama", 40));
		userRepository.save(new User("c33333", "kaka", 50));
		
		
		Assert.assertEquals(16, userRepository.findAll().size());			
		User u = userRepository.findOne("11111334aaq");			
		System.out.println(u);
			/*
			// 删除一个User，再验证User总数
			User u = userRepository.findOne("11111334aaq");
		//	userRepository.delete(u);
		//	Assert.assertEquals(2, userRepository.findAll().size());
	
			// 删除一个User，再验证User总数
			u = userRepository.findByUsername("mama");
			userRepository.delete(u);
			Assert.assertEquals(1, userRepository.findAll().size());*/
       
	}

}
