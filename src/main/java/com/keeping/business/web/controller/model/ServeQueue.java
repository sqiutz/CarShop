package com.keeping.business.web.controller.model;

import java.sql.Timestamp;
import java.util.Date;


public class ServeQueue extends BaseQueue{
    
    private Integer elapseTime;
	
    private Timestamp modifyTime;
    
    private Integer delayTime;
    
    private Integer hour;
    
    private Integer minute;
    
    private String jobType;
    
	private Integer additionTime;
	
	private String roofNum;
	
	private Date promiseTime;
	
	private Integer isWarrant;
	
	private Integer isSubContract;

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

	public Integer getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(Integer additionTime) {
		this.additionTime = additionTime;
	}

	public String getRoofNum() {
		return roofNum;
	}

	public void setRoofNum(String roofNum) {
		this.roofNum = roofNum;
	}

	public Date getPromiseTime() {
		return promiseTime;
	}

	public void setPromistTime(Date promistTime) {
		this.promiseTime = promistTime;
	}

	public Integer getIsWarrant() {
		return isWarrant;
	}

	public void setIsWarrant(Integer isWarrant) {
		this.isWarrant = isWarrant;
	}

	public Integer getIsSubContract() {
		return isSubContract;
	}

	public void setIsSubContract(Integer isSubContract) {
		this.isSubContract = isSubContract;
	}
    
    
}
