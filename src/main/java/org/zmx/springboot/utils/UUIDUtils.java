package org.zmx.springboot.utils;

import java.util.UUID;

/**
 * @description 生成UUID的工具类
 * @author zhangwenchao
 * @date 2018/6/8
 *
 */
public class UUIDUtils {
    
	/**
	 * 获取UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	

}
