package org.zmx.springboot.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zmx.springboot.SpringbootMongodbApplication;
import org.zmx.springboot.UserRepository;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMongodbApplication.class)
public class ApplicationTests {



	@Autowired
	private UserRepository userRepository;


		
	

}
