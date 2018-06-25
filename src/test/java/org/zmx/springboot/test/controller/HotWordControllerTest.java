package org.zmx.springboot.test.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zmx.springboot.SpringbootMongodbApplication;
import org.zmx.springboot.controller.HotWordController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMongodbApplication.class)
public class HotWordControllerTest {
	
    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
    
    
    @Test
    public void getHello() throws Exception {
    	MockMvc mvc= MockMvcBuilders.standaloneSetup(new HotWordController()).build();

        mvc.perform(MockMvcRequestBuilders.get("/hotword/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello World")));
    }
    

}
