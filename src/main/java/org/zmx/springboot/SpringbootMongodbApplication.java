package org.zmx.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootMongodbApplication{
	
	private static Logger log = LoggerFactory.getLogger(SpringbootMongodbApplication.class);
	
	
	public static void main(String[] args) {
	
		log.info("服务启动中...");	
		
		SpringApplication.run(SpringbootMongodbApplication.class, args);
		
		log.info("服务已启动！");	
	}



}

