package org.zmx.springboot;

import org.springframework.data.annotation.Id;

public class User {    
	
    @Id
    private String id;

    private String username;
    
    private Integer age;



	public User(String id, String username, Integer age) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", age=" + age + "]";
	}
	
	
    
    

}
