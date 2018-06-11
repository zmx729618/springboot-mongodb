package org.zmx.springboot.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @description 日志文件工具类----用于获取前1天、前7天、前30天的日志文件。
 * @author zhangwenchao
 * @date 2018/6/5
 */
public class LogFileUtils {
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0
     * @description 根据日志目录和日志文件前缀获取最近三个月的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastTreeMonthLogFiles(String logPath, String logFilePrefix){
    	 
    	YearMonth yearMonth1 = YearMonth.now();
    	YearMonth yearMonth2=  yearMonth1.plusMonths(-1L);
    	YearMonth yearMonth3=  yearMonth1.plusMonths(-2L);
    	File dir = new File(logPath);
    	File[] files  = dir.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return  name.startsWith(logFilePrefix+"-"+yearMonth1)
					  ||name.startsWith(logFilePrefix+"-"+yearMonth2)
					  ||name.startsWith(logFilePrefix+"-"+yearMonth3);
			}
		});
        return Arrays.asList(files);
    	
    }
    
    
    /**
     * 获取logpath目录下最近三个月的搜索log文件
     * @param logPath
     * @return
     */
    public static List<File> getLastTreeMonthLogFiles(String logPath){
    	
    	return getLastTreeMonthLogFiles(logPath,"search-citicpub-zxsy-api.log");
    	
    }
    
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前一天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastOneDayLogFiles(String logPath, String logFilePrefix){
    	LocalDate localDate = LocalDate.now();
    	List<File> fileList = new ArrayList<File>();    	
		localDate = localDate.plusDays(-1);
		File file = new File(logPath+"/"+logFilePrefix+"-"+localDate+".0.log");
		Objects.requireNonNull(file);
		fileList.add(file);
        return fileList;
    	
    }
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前一天的日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastOneDayLogFiles(String logPath){
		 return  getLastOneDayLogFiles(logPath,"search-citicpub-zxsy-api.log");    	
    }
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前7天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastSevenDayLogFiles(String logPath, String logFilePrefix){

    	LocalDate localDate = LocalDate.now();
    	List<File> fileList = new ArrayList<File>();
    	for(int i=0;i<7;i++){
    		localDate = localDate.plusDays(-1);
    		File file = new File(logPath+"/"+logFilePrefix+"-"+localDate+".0.log");
    		Objects.requireNonNull(file);
    		fileList.add(file);
    	}
    	
        return fileList;
    	
    }
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前7天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastSevenDayLogFiles(String logPath){    
        return getLastSevenDayLogFiles(logPath, "search-citicpub-zxsy-api.log");	
    }
    
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前30天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastThirtyDayLogFiles(String logPath, String logFilePrefix){
    	LocalDate localDate = LocalDate.now();
    	List<File> fileList = new ArrayList<File>();
    	for(int i=0;i<30;i++){
    		localDate = localDate.plusDays(-1);
    		File file = new File(logPath+"/"+logFilePrefix+"-"+localDate+".0.log");
    		Objects.requireNonNull(file);
    		fileList.add(file);
    	}    
        return fileList;    	
    }
    
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前30天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @return
     */
    public static List<File> getLastThirtyDayLogFiles(String logPath){ 	
        return getLastThirtyDayLogFiles(logPath,"search-citicpub-zxsy-api.log");    	
    }
    
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前lastDays天的所有日志文件。
     * @param logPath   目录
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @param lastDays     天数
     * @return
     */
    public static List<File> getLogFilesForLastDays(String logPath, String logFilePrefix, Integer lastDays){

    	LocalDate localDate = LocalDate.now();
    	List<File> fileList = new ArrayList<File>();
    	for(int i=0;i<lastDays;i++){
    		localDate = localDate.plusDays(-1);
    		File file = new File(logPath+"/"+logFilePrefix+"-"+localDate+".0.log");
    		Objects.requireNonNull(file);
    		fileList.add(file);
    	}    	
        return fileList;    	
    }
    
    
    /**
     * 日志文件名称格式：search-citicpub-zxsy-api.log-2018-05-24.0.log
     * 根据日志目录和日志文件前缀获取前lastDays天的所有日志文件。
     * @param logPath
     * @param logFilePrefix  search-citicpub-zxsy-api.log
     * @param lastDays     天数
     * @return
     */
    public static List<File> getLogFilesForLastDays(String logPath, Integer lastDays){

        return getLogFilesForLastDays(logPath,"search-citicpub-zxsy-api.log",lastDays);
    	
    }
    
    
    
    
    public static void main(String[] args) {
  
    	
    	LocalDate localDate = LocalDate.now();
    	
    	for(int i=0;i<7;i++){
    		localDate = localDate.plusDays(-1);
    		
    		System.out.println("D:/log"+"/"+"search-citicpub-zxsy-api.log"+"-"+localDate+".0.log");
    		
    	}
	}
    
    
    
    
    
    

}
