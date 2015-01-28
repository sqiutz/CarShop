package com.keeping.business.web.controller.model;

import java.sql.Timestamp;
import java.util.Date;

public class Order {
	
	private Integer id;
	
	private Integer customerId;

	private Integer status;
	
	private String statusValue;
	
    private Timestamp createTime;
    
    private Timestamp startTime;
    
    private Timestamp endTime;
    
    private String registerNum;
    
    private String roofNum;
    
    private String queueNum;
    
    private String bookNum;
    
    private Date promiseTime;
    
    private Date bookTime;
    
    private Integer estimationTime;
    
    private Integer isBook;
    
    private Date assignDate;
    
    private String userName;
    
    private String mobilePhone;
    
    private String jobType;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}

	public String getRoofNum() {
		return roofNum;
	}

	public void setRoofNum(String roofNum) {
		this.roofNum = roofNum;
	}

	public String getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(String queueNum) {
		this.queueNum = queueNum;
	}

	public Date getPromiseTime() {
		return promiseTime;
	}

	public void setPromiseTime(Date promiseTime) {
		this.promiseTime = promiseTime;
	}

	public Integer getEstimationTime() {
		return estimationTime;
	}

	public void setEstimationTime(Integer estimationTime) {
		this.estimationTime = estimationTime;
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
    
}
