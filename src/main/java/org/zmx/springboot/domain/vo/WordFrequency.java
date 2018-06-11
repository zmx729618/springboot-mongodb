package org.zmx.springboot.domain.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * 词频以及ipset的
 * @author zhangwenchao
 * @date  2018/6/7
 *
 */
public class WordFrequency {
	
	private Long frequency;
	 
	private Set<String> ipSet ;
	

	public WordFrequency(Long frequency, Set<String> ipSet) {
		super();
		this.frequency = frequency;
		this.ipSet = ipSet;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Set<String> getIpSet() {
		return ipSet;
	}

	public void setIpSet(Set<String> ipSet) {
		this.ipSet = ipSet;
	}
	 
	 

}
