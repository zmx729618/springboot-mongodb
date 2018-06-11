package org.zmx.springboot.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.zmx.springboot.dao.HotWordRepository;
import org.zmx.springboot.domain.HotWordIndex;
import org.zmx.springboot.domain.vo.WordFrequency;
import org.zmx.springboot.service.HotWordInfoMongoService;
import org.zmx.springboot.utils.LogFileUtils;
import org.zmx.springboot.utils.ShellUtils;
import org.zmx.springboot.utils.UUIDUtils;
import org.zmx.springboot.utils.WordFrequencyUtils;

@Service
public class HotWordInfoMongoServiceImpl implements HotWordInfoMongoService{
	    
	    @Value("${logging.host}")
	    private String host;
	    
	    @Value("${logging.port}")
	    private int port;
	    
	    @Value("${loging.server.name}")
	    private String user;
	    
	    @Value("${loging.server.password}")
	    private String passwd;

	    @Value("${logging.path}")
	    private String logPath;
	    
	    
	    @Autowired
	    private HotWordRepository hotWordRepository;
	    
	    
	    @Autowired
	    private MongoTemplate mongoTemplate;
	    
	    
	    /**
	     * 统计前一天的关键词词频，取排名前三十的词
	     * @return
	     * @throws Exception
	     */
	    @Override
	    public List<HotWordIndex>  countHotWordLatestOneDay() throws Exception{
	    	//获取文件
	    	List<File> logFiles = LogFileUtils.getLastOneDayLogFiles(logPath);	  	    	
	    	Map<String, WordFrequency> cmdAndgetVFAndUF = new HashMap<String, WordFrequency>();
	    	//读取远程问价并分析日志
	    	if(logFiles!=null && logFiles.size()>0){		    		
	    		  cmdAndgetVFAndUF = ShellUtils.execCmdAndgetVFAndUF(cmdAndgetVFAndUF, logPath, logFiles, user, passwd, host, port);		
	    	}
	    	//排序
	    	List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.mapVFAndUFSort(cmdAndgetVFAndUF);	    	
	    	List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys,(byte)1);
	    	
	    	//List<Map.Entry<String, Long>> vocabularyFrequencys = WordFrequencyUtils.getVocabularyFrequencyWithSort(logFiles);
	    	//List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.getVFAndUFWithSort(logFiles);
	    	//List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys);	    	
	    	return hotwords;

	    }



	    
	    
	    /**
	     * 统计前7天的关键词词频，取排名前三十的词
	     * @return
	     * @throws Exception
	     */
	    @Override
	    public List<HotWordIndex>  countHotWordLatestSevenDays() throws Exception{
	    	
	    	List<File> logFiles = LogFileUtils.getLastSevenDayLogFiles(logPath);
	    	Map<String, WordFrequency> cmdAndgetVFAndUF = new HashMap<String, WordFrequency>();
	    	//读取远程问价并分析日志
	    	if(logFiles!=null && logFiles.size()>0){		    		
	    		  cmdAndgetVFAndUF = ShellUtils.execCmdAndgetVFAndUF(cmdAndgetVFAndUF,logPath, logFiles, user, passwd, host, port);		
	    	}
	    	//排序
	    	List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.mapVFAndUFSort(cmdAndgetVFAndUF);	    	
	    	List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys,(byte)7);
	    	
	    	//List<Map.Entry<String, Long>> vocabularyFrequencys = WordFrequencyUtils.getVocabularyFrequencyWithSort(logFiles);
	    	//List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.getVFAndUFWithSort(logFiles);	    
	    	//List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys);
	    	
	    	return hotwords;
	    }



	    
	    
	    /**
	     * 统计前三十天的关键词词频，取排名前三十的词
	     * @return
	     * @throws Exception
	     */
	    @Override
	    public List<HotWordIndex>  countHotWordLatestThirtyDays() throws Exception{
	    	
	    	List<File> logFiles = LogFileUtils.getLastThirtyDayLogFiles(logPath);	    		
	    	Map<String, WordFrequency> cmdAndgetVFAndUF = new HashMap<String, WordFrequency>();
	    	//读取远程问价并分析日志
	    	if(logFiles!=null && logFiles.size()>0){		    		
	    		 cmdAndgetVFAndUF = ShellUtils.execCmdAndgetVFAndUF(cmdAndgetVFAndUF,logPath, logFiles, user, passwd, host, port);		
	    	}
	    	//排序
	    	List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.mapVFAndUFSort(cmdAndgetVFAndUF);	    	
	    	List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys,(byte)30);
	    	
	    	//List<Map.Entry<String, Long>> vocabularyFrequencys = WordFrequencyUtils.getVocabularyFrequencyWithSort(logFiles);
	    	//List<Map.Entry<String, WordFrequency>> vocabularyFrequencys = WordFrequencyUtils.getVFAndUFWithSort(logFiles);
	    	//List<HotWordIndex> hotwords = getHotWords(vocabularyFrequencys);
	    	
	    	return hotwords;
	    }
	    
	    /**
	     * 对统计词进行热词、词频、人数、类型转换的方法
	     * @param vocabularyFrequencys
	     * @return
	     */
		private List<HotWordIndex> getHotWords(List<Map.Entry<String, WordFrequency>> vocabularyFrequencys, Byte type) {
			List<HotWordIndex> hotwords = new ArrayList<HotWordIndex>();
	    	
	    	for(int i=0;i<30&&i<vocabularyFrequencys.size();i++){
	    		Map.Entry<String, WordFrequency> vf = vocabularyFrequencys.get(i);
	    		HotWordIndex hotWordIndex = new HotWordIndex();
	    		
	    		hotWordIndex.setUid(UUIDUtils.getUUID());
	    		hotWordIndex.setHotWord(vf.getKey());
	    		hotWordIndex.setFrequency(vf.getValue().getFrequency());
	    		hotWordIndex.setUserCount((long)vf.getValue().getIpSet().size());
	    		hotWordIndex.setPeriodSpan((int)type);
	    		hotWordIndex.setType(type);
	    		hotWordIndex.setAnalyDate(LocalDate.now().toString());
	    		hotWordIndex.setCreateDate(new Date());
	    		
	    		hotwords.add(hotWordIndex);
	    	}
			return hotwords;
		}
	    
	    
	    /**
	     * 
	     * @param vocabularyFrequencys
	     * @return
	     */
		private List<HotWordIndex> getHotWordsOnlyFrequency(List<Map.Entry<String, Long>> vocabularyFrequencys, Byte type) {
			List<HotWordIndex> hotwords = new ArrayList<HotWordIndex>();
	    	
	    	for(int i=0;i<30&&i<vocabularyFrequencys.size();i++){
	    		Map.Entry<String, Long> vf = vocabularyFrequencys.get(i);
	    		HotWordIndex hotWordIndex = new HotWordIndex();
	    		
	    		hotWordIndex.setUid(UUIDUtils.getUUID());
	    		hotWordIndex.setHotWord(vf.getKey());
	    		hotWordIndex.setFrequency(vf.getValue());
	    		hotWordIndex.setPeriodSpan((int)type);
	    		hotWordIndex.setType(type);
	    		hotWordIndex.setAnalyDate(LocalDate.now().toString());
	    		hotWordIndex.setCreateDate(new Date());
	    		hotwords.add(hotWordIndex);
	    	}
			return hotwords;
		}
	    
	    
	    
	    
	    
		/**
		 * @description 将热词存入mongo中
		 * @author zhangwenchao
		 * @date 2018/6/4
		 */
	    @Override
	    public void loadHotWordInfo2ES(List<HotWordIndex> hotWordIndexList){
	    	
	    	hotWordIndexList.forEach(e->loadHotWordInfo2ES(e));
	    	
	    }
	    
		/**
		 * @description 将热词存入mongo中
		 * @author zhangwenchao
		 * @date 2018/6/4
		 */
	    @Override
		public void loadHotWordInfo2ES(HotWordIndex hotWordIndex){
	    	
	    	 hotWordRepository.save(hotWordIndex);

	    }
	    
	    /**
	     * 
	     * @param type  1 :昨日;  7:前7日;  30:前30日;
	     * @param dateStr 格式yyyy-MM-dd 
	     * @return
	     */
	    @Override
	    public List<HotWordIndex> findHotWordIndex(Byte type,String date){	
	    	if(StringUtils.isEmpty(date)){
	    		LocalDate localDate =  LocalDate.now();
	    		date = localDate.toString();
	    	}
	    	
	    	Criteria criteria  = Criteria.where("type").is(type).and("analyDate").is(date);	    	
	    	return mongoTemplate.find(new Query(criteria), HotWordIndex.class, "hotword");
	    }
	    
	
	    

}
