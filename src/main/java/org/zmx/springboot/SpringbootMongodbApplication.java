package org.zmx.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@SpringBootApplication
public class SpringbootMongodbApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringbootMongodbApplication.class, args);
		
		
	}

	@Override
	public void run(String... arg0) throws Exception {
		
	    // save a couple of customers
		userRepository.save(new User("11111334aaq", "xiaoming", 12));
		userRepository.save(new User("12333335aaq", "xiaowang", 12));
		
		      
	    // fetch all customers
	    System.out.println("Customers found with findAll():");
	    System.out.println("-------------------------------");
	    for (User u : userRepository.findAll()) {
	      System.out.println(u);
	    }
	 
		
	
		
	}

}*/

@SpringBootApplication
public class SpringbootMongodbApplication{
	
	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringbootMongodbApplication.class, args);
		
		
	}



}

