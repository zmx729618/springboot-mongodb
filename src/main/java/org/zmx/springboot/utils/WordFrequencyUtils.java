package org.zmx.springboot.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.zmx.springboot.domain.vo.WordFrequency;

/**
 * @description 词频统计的工具类，用于分析用户输入搜索关键词日志
 * @author 张文超
 * @date   2018/5/31
 *
 */
public class WordFrequencyUtils {
	
	/**
	 * @Description 分析日志-获得高频词汇  
	 * @日志格式: 2018-05-24 17:32:39:090 |  113.5.3.255 | aa8eb6e2-5241-45bf-9763-7f509936fd60 | "苏菲的世界"
	 * @Date 2018/5/31 11:30
	 * @Param [URL 文件url]
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static Map<String, Long> getVocabularyFrequencyNotAnalyer(URL url) throws Exception {	   
		   	 
	    return getVocabularyFrequencyNotAnalyer(new File(url.getPath()));
	}
	
	
	
	/**
	 * @Description 分析日志-获得高频词汇 
	 * @日志格式 2018-05-24 17:32:39:090 |  113.5.3.255 | aa8eb6e2-5241-45bf-9763-7f509936fd60 | "苏菲的世界"
	 * @Date  2018/3/9 11:30
	 * @Param [File file]----日志文件
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static Map<String, Long> getVocabularyFrequencyNotAnalyer(File file) throws Exception {	   
		Objects.requireNonNull(file);	   
	    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
	    Map<String, Long> map = new HashMap<>();
	    String str="";
	    while ((str = in.readLine()) != null) {
	    	String[] logLine  = str.split("\\s+\\|\\s+");
	        String userInput = StringUtils.substring(logLine[3], 1, logLine[3].length()-1);
	        if(StringUtils.isNotEmpty(userInput)){	        	
	    		String[] keys =  userInput.split("\\s+");
	    		for(String key : keys){
	    			System.out.println(key);
	    			map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);
	    		}	        	
	        }
	    }
	    in.close();
	    return map;
	}
	
	
	/**
	 * @Description 分析日志-获得高频词汇 
	 * @日志格式 2018-05-24 17:32:39:090 |  113.5.3.255 | aa8eb6e2-5241-45bf-9763-7f509936fd60 | "苏菲的世界"
	 * @Date  2018/3/9 11:30
	 * @Param [File file]----日志文件
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static Map<String, Long> getVocabularyFrequencyNotAnalyer(List<File> fileList) throws Exception {	
		Map<String, Long> map = new HashMap<>();
		for(File file: fileList){			
			Objects.requireNonNull(file);	   
		    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		    String str="";
		    while ((str = in.readLine()) != null) {
		    	String[] logLine  = str.split("\\s+\\|\\s+");
		    	String userInput = null;
		    	if(logLine!=null && logLine.length>3){
		    		userInput = StringUtils.substring(logLine[3], 1, logLine[3].length()-1);
		    	}		        
		        if(StringUtils.isNotEmpty(userInput)){	        	
		    		String[]  keys =   userInput.split("\\s+");
		    		for(String key : keys){		    			
		    			map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);
		    		}		        	
		        }
		    }
		    in.close();
		}
	    return map;
	}
	
	
	
	/**
	 * 按词频高低进行排序
	 * @param map 已经分过词的map<String ,Long>
	 * @return
	 */
	public static List<Map.Entry<String, Long>> mapSort(Map<String, Long> map) {

	    List<Map.Entry<String, Long>> list = new ArrayList<>(map.entrySet());
	    //通过比较器来实现排序
	    //降序排序
	    list.sort((e1, e2) -> (int)(e2.getValue() - e1.getValue()));	   
	    return list;
	}
	
	
	
	
	/**
	 * 统计词频并排序（逆序）
	 * @param fileList
	 * @return
	 * @throws Exception
	 */
	public static List<Map.Entry<String, Long>> getVocabularyFrequencyWithSort(List<File> fileList) throws Exception{
		
		Map<String, Long> map =  getVocabularyFrequencyNotAnalyer(fileList);
		
		return mapSort(map);
	}
	
	
	/**
	 * @Description 分析日志-获得高频词汇 
	 * @日志格式 2018-05-24 17:32:39:090 |  113.5.3.255 | aa8eb6e2-5241-45bf-9763-7f509936fd60 | "苏菲的世界"
	 * @Date  2018/3/9 11:30
	 * @Param [File file]----日志文件
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static Map<String, WordFrequency> getVFAndUFNotAnalyer(List<File> fileList) throws Exception {	
		Map<String, WordFrequency> map = new HashMap<String, WordFrequency>();
		for(File file: fileList){			
			Objects.requireNonNull(file);	   
		    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		    String str="";
		    while ((str = in.readLine()) != null) {
		    	analyseLogOneLine(map, str);
		    }
		    in.close();
		}
	    return map;
	}


    /**
     * 解析一行日志，存放到map中并返回
     * @param map
     * @param str
     * @return
     */
	private static Map<String, WordFrequency> analyseLogOneLine(Map<String, WordFrequency> map, String str) {
		String[] logLine  = str.split("\\s+\\|\\s+");
		String userInput = null;
		String ip=null;
		if(logLine!=null && logLine.length>3){
			ip= logLine[1].trim();
			userInput = StringUtils.substring(logLine[3], 1, logLine[3].length()-1);
		}		        
		if(StringUtils.isNotEmpty(userInput)){	        	
			String[]  keys =   userInput.split("\\s+");
			for(String key : keys){		    					    			
				if(map.containsKey(key)){
					map.get(key).setFrequency(map.get(key).getFrequency()+1);
					map.get(key).getIpSet().add(ip);
				}else{
					Set<String> ifSet = new HashSet<String>();
					ifSet.add(ip);
					WordFrequency wf = new WordFrequency(1L,ifSet);
					map.put(key,wf);
				}
			}		        	
		}
		return map;
	}
	
	
	/**
	 * 按词频高低进行排序
	 * @param map 已经分过词的map<String ,Long>
	 * @return
	 */
	public static List<Map.Entry<String, WordFrequency>> mapVFAndUFSort(Map<String, WordFrequency> map) {

	    List<Map.Entry<String, WordFrequency>> list = new ArrayList<>(map.entrySet());
	    //通过比较器来实现排序
	    //降序排序
	    list.sort((e1, e2) -> (int)(e2.getValue().getFrequency() -e1.getValue().getFrequency()));	   
	    return list;
	}
	
	
	
	/**
	 * 统计词频并排序（逆序）
	 * @param fileList
	 * @return
	 * @throws Exception
	 */
	public static List<Map.Entry<String, WordFrequency>> getVFAndUFWithSort(List<File> fileList) throws Exception{
		
		Map<String, WordFrequency> map =  getVFAndUFNotAnalyer(fileList);
		
		return mapVFAndUFSort(map);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @Description 将关键字写入到文件
	 * @Date 2018/3/9 11:30
	 * @Param [URL 要写入的文件]
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static void writeHighFrequencyVocabularyToDic(URL url,String keyWord) throws Exception {
	    	
		Objects.requireNonNull(url);	   	
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream (new File(url.getPath()),true), "UTF-8"));
	    bw.newLine();
	    bw.write(keyWord);
	    System.out.println("写入成功！");
	    bw.close();
	   
	}
	
	
	
	/**
	 * @Description 将关键字写入到文件
	 * @Date 2018/3/9 11:30
	 * @Param [URL 要写入的文件]
	 * @Return java.util.Map<java.lang.String, java.lang.Integer>
	 * @Throws Exception
	 */
	public static void writeHighFrequencyVocabularyToDic(File file,String keyWord) throws Exception {
	    	
		Objects.requireNonNull(file);	   	
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream (file,true), "UTF-8"));
	    bw.newLine();
	    bw.write(keyWord);
	    System.out.println("写入成功！");
	    bw.close();
	   
	}
	
	
	

	
	

	
	
	
	public static void main(String[] args) throws Exception {
		
		
/*		 String  text = "在这篇文章中，我将对Map的遍历方式做一个对比和总结，将分别从JAVA8之前和JAVA8做一个遍历方式的对比，亲测可行";
		 Map<String, Integer>  map = getHighFrequencyVocabulary4Text(text);
		 
		 map.keySet().forEach(key -> System.out.println("" + key + " : " + map.get(key)));		 
		 
		 System.out.println("-----------------------------------------------");
		 
		 List<Map.Entry<String, Integer>> list = mapSort(map);
		
		 list.forEach(e -> System.out.println(""+e.getKey()+" : "+e.getValue()) );
		 
		 System.out.println("=================================================="); 
		 
		 String fileName = "file:///D:/log/search-citicpub-zxsy-api.log-2018-05-24.0.log";
		 
		 URL url = new URL(fileName);
		 
		 Map<String, Integer>  map2 = getHighFrequencyVocabulary4FileNotAnalyer(url);
		 
		 map2.keySet().forEach(key -> System.out.println("" + key + " : " + map2.get(key)));		 
		 
		 System.out.println("-----------------------------------------------");
		 
		 List<Map.Entry<String, Integer>> list2 = mapSort(map2);
		
		 list2.forEach(e -> System.out.println(""+e.getKey()+" : "+e.getValue()) );*/
		 
		 
		// System.out.println("=================================================="); 
	    // URL url2 = new URL("file:///D:/apache-tomcat-8.0.52/webapps/ROOT/hotwords.dic");
		// writeHighFrequencyVocabularyToDic(url2, "未来简史");
		 
		 
		 String str1 = "java判断是否quan为汉字";
	     String str2 = "全为汉字";
	     String reg = "[\\u4e00-\\u9fa5]+";
	     boolean result1 = str1.matches(reg);//false  
	     boolean result2 = str2.matches(reg);//true 
		 System.out.println(result1+">>>>>>>"+result2);
		 
	}
	
	

}
