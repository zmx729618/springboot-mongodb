package org.zmx.springboot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.zmx.springboot.domain.vo.WordFrequency;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session; 
/**
 * @decription 执行远程shell命令，并获取结果--实现分析日志
 * @author zhangwenchao
 * @date  2018/6/8
 *
 */
public class ShellUtils {

	
	/**配置连接
	 * @param user
	 * @param passwd
	 * @param host
	 * @param post
	 * @throws Exception
	 */
	public static Session connect(String user, String passwd, String host,int post) throws Exception {
		JSch  jsch = new JSch();
		Session session = jsch.getSession(user, host, post);
	    if (session == null) {
	        throw new Exception("session is null");
	    }
	    session.setPassword(passwd);
	    java.util.Properties config = new java.util.Properties();
	    //第一次登陆
	    config.put("StrictHostKeyChecking", "no");
	    session.setConfig(config);
	    try {
	        session.connect(30000);
	    } catch (Exception e) {
	        throw new Exception("连接远程端口无效或用户名密码错误");
	    }
        return session;
	}


	/**
	 * @param command shell 命令
	 * @param user 用户名
	 * @param passwd 密码 
	 * @param host ip地址
	 * @param post 端口号
	 * @throws Exception
	 */
	public static void execCmd(String command, String user, String passwd, String host, int port) throws Exception {
	    System.out.println(command);
	    Session session= connect(user, passwd,host,port);
	    BufferedReader reader = null;
	    Channel channel = null;
	    try {
	            channel = session.openChannel("exec");
	            ((ChannelExec) channel).setCommand(command);

	            channel.setInputStream(null);
	            ((ChannelExec) channel).setErrStream(System.err);

	            channel.connect();
	            InputStream in = channel.getInputStream();
	            reader = new BufferedReader(new InputStreamReader(in));
	            String buf = null;	           
	            //返回数据
	            while ((buf = reader.readLine()) != null) {
	            	System.out.println(buf);
	            }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSchException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        channel.disconnect();
	        session.disconnect();
	    }
	}
	
	
	/**
	 * 
	 * @param logPath
	 * @param fileList
	 * @param user
	 * @param passwd
	 * @param host
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public static Map<String, WordFrequency> execCmdAndgetVFAndUF(Map<String, WordFrequency> map, String logPath, List<File> fileList, String user, String passwd, String host, int port) throws Exception {

		Session session= connect(user, passwd,host,port);	  
	    try {
			for (File f : fileList) {
				String command = "cd " + logPath + "; cat " +logPath+"/"+ f.getName();
				Channel channel = null;
				BufferedReader reader = null;
				try {
					channel = session.openChannel("exec");
					((ChannelExec) channel).setCommand(command);

					channel.setInputStream(null);
					((ChannelExec) channel).setErrStream(System.err);

					channel.connect();
					InputStream in = channel.getInputStream();
					reader = new BufferedReader(new InputStreamReader(in));
					String buf = null;
					//返回数据
					while ((buf = reader.readLine()) != null) {
						analyseLogOneLine(map, buf);
					}
				} finally {
					reader.close();
					channel.disconnect();
				}

			} 
		} finally {
			session.disconnect();
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
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		execCmd("cd /web/logs/sc_zxsy; cat search-citicpub-zxsy-api.log-2018-06-07.0.log","yunpub","Zxsy_!@#qwe","112.126.93.98",22022);
	}

}



