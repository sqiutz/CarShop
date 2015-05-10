package com.keeping.business.web.controller.model;

import java.util.Date;

public class OrderObject {
	
	private String queueNumber;
	
	private String registerNum;
	
	private Date now;
	
	private Integer status;
	
	private Integer isBook;
	
    private Date assignDate;
    
    private String userName;
    
    private String mobilePhone;
    
    private String jobType;
    
    private Date bookStartTime;
    
    private Date bookEndTime;
    
    private Date beginDate;
    
    private Date endDate;
    
    private String comment;
    
    private Integer groupid;
    
    private String express;

	public String getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}

	public Date getBookStartTime() {
		return bookStartTime;
	}

	public void setBookStartTime(Date bookStartTime) {
		this.bookStartTime = bookStartTime;
	}

	public Date getBookEndTime() {
		return bookEndTime;
	}

	public void setBookEndTime(Date bookEndTime) {
		this.bookEndTime = bookEndTime;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
	
}
