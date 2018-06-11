package org.zmx.springboot.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zmx.springboot.domain.HotWordIndex;
import org.zmx.springboot.service.HotWordInfoMongoService;

@Component
@EnableScheduling
public class HotWordAnalysisTask {
	
	private Logger log = LoggerFactory.getLogger(HotWordAnalysisTask.class); 
	
	@Autowired
	private HotWordInfoMongoService hotWordInfoMongoServiceImpl;
	
	//每天凌晨15分执行一次
	@Scheduled(cron="0 15 0 * * ?") 	
	public void  executeHotWordAnalysis(){
		log.info("分析热词任务开始:开始时间:{},分析日期:{}",LocalDateTime.now(),LocalDate.now());
		try {
			List<HotWordIndex> countHotWords = hotWordInfoMongoServiceImpl.countHotWordLatestOneDay();
			hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			
			countHotWords = hotWordInfoMongoServiceImpl.countHotWordLatestSevenDays();
			hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			
			countHotWords = hotWordInfoMongoServiceImpl.countHotWordLatestThirtyDays();
			hotWordInfoMongoServiceImpl.loadHotWordInfo2ES(countHotWords);
			log.info("分析热词任务完成:完成时间:{},分析日期:{}",LocalDateTime.now(),LocalDate.now());
		} catch (Exception e) {
			log.error("分析热词出现异常！",e);
			e.printStackTrace();
		}
		
	}
 
}
