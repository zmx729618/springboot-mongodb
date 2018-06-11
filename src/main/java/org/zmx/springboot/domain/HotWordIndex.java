package org.zmx.springboot.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="hotword")
public class HotWordIndex implements Serializable{


	private static final long serialVersionUID = -238786533770592273L;

	/**
	 * uid
	 */
    @Id
    private String uid;

    /**
     * 热词
     */
    private String hotWord;
    
    
    /**
     * 词频
     */
    private Long frequency;
    
    
    /**
     * 人数
     */
    private Long userCount;
    
    /**
     * 时间跨度
     */
    private Integer periodSpan;
    
    
    /**
     * 类型：1,7,30
     */
    private Byte type;
    
    
    /**
     * 分析日志:分析日志的日期
     */
    private  String  analyDate;
    
    
    /**
     * 创建时间：
     */
    private  Date  createDate;


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getHotWord() {
		return hotWord;
	}


	public void setHotWord(String hotWord) {
		this.hotWord = hotWord;
	}


	public Long getFrequency() {
		return frequency;
	}


	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}


	public Long getUserCount() {
		return userCount;
	}


	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}


	public String getAnalyDate() {
		return analyDate;
	}


	public void setAnalyDate(String analyDate) {
		this.analyDate = analyDate;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getPeriodSpan() {
		return periodSpan;
	}


	public void setPeriodSpan(Integer periodSpan) {
		this.periodSpan = periodSpan;
	}


	public Byte getType() {
		return type;
	}


	public void setType(Byte type) {
		this.type = type;
	}
    

    

	
	
	
	

}
