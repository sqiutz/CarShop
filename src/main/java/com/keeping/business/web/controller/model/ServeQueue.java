package com.keeping.business.web.controller.model;

import java.sql.Timestamp;


public class ServeQueue extends BaseQueue{
    
    private Integer elapseTime;
	
    private Timestamp modifyTime;
    
    private Integer delayTime;
    
    private Integer hour;
    
    private Integer minute;
    
    private String jobType;

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getElapseTime() {
		return elapseTime;
	}

	public void setElapseTime(Integer elapseTime) {
		this.elapseTime = elapseTime;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
    
    
}
