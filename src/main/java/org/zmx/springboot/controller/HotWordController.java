package org.zmx.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zmx.springboot.domain.HotWordIndex;
import org.zmx.springboot.service.HotWordInfoMongoService;
/**
 * @description 热词分析API
 * @author zhangwenchao
 * @date 2018-06-08
 */
@RestController
@RequestMapping("/hotword")
public class HotWordController {
	
	private Logger log = LoggerFactory.getLogger(HotWordController.class); 
	
	@Autowired
	private HotWordInfoMongoService HotWordInfoMongoServiceImpl;
	
	/**
	 * @description: 分析日志并将分析结果存储到mongodb中
	 * @author zhangwenchao
	 * @return 
	 */
	@GetMapping(value="/analyse")
	public String analysHotWord(){
		log.info("客户端调用开始...");
		try {
			List<HotWordIndex> countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestOneDay();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			
		    countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestSevenDays();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			
			countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestThirtyDays();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			
			log.info("客户端调用完成");	
			return "ok";
		} catch (Exception e) {
			log.error("分析昨日搜索日志出错！", e);			
			throw new RuntimeException("分析昨日搜索日志出错!");
		}
		
	}
	
	
	/**
	 * @description 从mongo中获取热词的分析结果
	 * @author zhangwenchao
	 * @param type  1 :前1日;  7:前7日;  30:前30日;
	 * @param date  代表要分析日志的日期，为null或者为"",即为当前日
	 * @return
	 */
	@GetMapping(value="/mongo/getHotword")
	public List<HotWordIndex> getHotword(@RequestParam Byte type, @RequestParam(required=false) String date){
		log.info("客户端调用开始...");
		try {
			List<HotWordIndex> countHotWords =  HotWordInfoMongoServiceImpl.findHotWordIndex(type,date);
			log.info("客户端调用完成");	
			return countHotWords;
		} catch (Exception e) {
			log.error("获取热词分析结果异常", e);			
			throw new RuntimeException("获取热词分析结果异常");
		}
		
	}
		
	
	/**
	 * 分析昨日（前一天）搜索日志并存入到数据库中
	 * @return
	 */
	@GetMapping(value="/analyseOneDay")
	public List<HotWordIndex> analysHotWordLatestOneDay(){
		
		try {
			List<HotWordIndex> countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestOneDay();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			return countHotWords;
		} catch (Exception e) {
			log.error("分析昨日搜索日志出错！", e);			
			throw new RuntimeException("分析昨日搜索日志出错!");
		}
		
	}
	
	/**
	 * 分析最近一个星期（前7天）的搜索日志并存入到数据库中
	 * @return
	 */
	@GetMapping(value="/analyseSevenDay")
	public List<HotWordIndex> analysHotWordLatestSevenDay(){
		
		try {
			List<HotWordIndex> countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestSevenDays();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			return countHotWords;
		} catch (Exception e) {
			log.error("分析昨日搜索日志出错！", e);			
			throw new RuntimeException("分析昨日搜索日志出错!");
		}
		
	}
	
	
	/**
	 * 分析最近一个星期（前7天）的搜索日志并存入到数据库中
	 * @return
	 */
	@GetMapping(value="/analyseThirtyDay")
	public List<HotWordIndex> analysHotWordLatestThirtyDay(){
		try {
			List<HotWordIndex> countHotWords = HotWordInfoMongoServiceImpl.countHotWordLatestThirtyDays();
			HotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			return countHotWords;
		} catch (Exception e) {
			log.error("分析昨日搜索日志出错！", e);			
			throw new RuntimeException("分析昨日搜索日志出错!");
		}
		
	}
	
	
	@GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
	
	
	

}
