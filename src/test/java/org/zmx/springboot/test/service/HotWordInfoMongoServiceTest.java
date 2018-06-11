package org.zmx.springboot.test.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.zmx.springboot.SpringbootMongodbApplication;
import org.zmx.springboot.domain.HotWordIndex;
import org.zmx.springboot.service.HotWordInfoMongoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMongodbApplication.class)
public class HotWordInfoMongoServiceTest {
	
	@Autowired
	private HotWordInfoMongoService hotWordInfoMongoServiceImpl;
	
	
    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
    
	
	@Test
	public void testCountHotWordLatestOneDay() throws Exception{
		
		List<HotWordIndex> hotWordCounts = hotWordInfoMongoServiceImpl.countHotWordLatestOneDay();
		System.out.println("热词 | 词频 | 人数");
		hotWordCounts.forEach(e->System.out.println(e.getHotWord()+" | "+e.getFrequency()+"|"+e.getUserCount()));
		hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(hotWordCounts);
	}
	
	
	@Test
	public void testCountHotWordLatestSevenDays() throws Exception{
		
		List<HotWordIndex> hotWordCounts = hotWordInfoMongoServiceImpl.countHotWordLatestSevenDays();
		System.out.println("热词 | 词频 | 人数");
		hotWordCounts.forEach(e->System.out.println(e.getHotWord()+" | "+e.getFrequency()+"|"+e.getUserCount()));
		hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(hotWordCounts);
	}
	
	
	@Test
	public void testCountHotWordLatestThirtyDays() throws Exception{
		
		List<HotWordIndex> hotWordCounts = hotWordInfoMongoServiceImpl.countHotWordLatestThirtyDays();
		System.out.println("热词 | 词频 | 人数");
		hotWordCounts.forEach(e->System.out.println(e.getHotWord()+" | "+e.getFrequency()+"|"+e.getUserCount()));
		hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(hotWordCounts);
	}
	
	
    /**
     * 
     * @param type  1 :昨日;  7:前7日;  30:前30日;
     * @param dateStr  
     * @return
     */
	@Test
    public void findHotWordIndex(){
		List<HotWordIndex> findHotWordIndex = hotWordInfoMongoServiceImpl.findHotWordIndex((byte)30,null);
		System.out.println("热词 | 词频 | 人数");
		findHotWordIndex.forEach(e->System.out.println(e.getHotWord()+" | "+e.getFrequency()+"|"+e.getUserCount()));
    }

}
