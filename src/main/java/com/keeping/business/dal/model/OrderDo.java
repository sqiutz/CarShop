package com.keeping.business.dal.model;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderDo {
	
	private Integer id;
	
	private Integer customerId;

	private Integer status;
	
    private Timestamp createTime;
    
    private Timestamp startTime;
    
    private Timestamp bookStartTime;
    
    private Timestamp endTime;
    
    private Timestamp bookEndTime;
    
    private String registerNum;
    
    private String roofNum;
    
    private String queueNum;
    
    private String bookNum;
    
    private Timestamp promiseTime;
    
    private Timestamp bookTime;
    
    private Integer estimationTime;
    
    private Integer isBook;
    
    private Date assignDate;
    
    private String jobType;
    
    private Date beginDate;
    
    private Date endDate;

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

	public Timestamp getPromiseTime() {
		return promiseTime;
	}

	public void setPromiseTime(Timestamp promiseTime) {
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

	public Timestamp getBookTime() {
		return bookTime;
	}

	public void setBookTime(Timestamp bookTime) {
		this.bookTime = bookTime;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Timestamp getBookStartTime() {
		return bookStartTime;
	}

	public void setBookStartTime(Timestamp bookStartTime) {
		this.bookStartTime = bookStartTime;
	}

	public Timestamp getBookEndTime() {
		return bookEndTime;
	}

	public void setBookEndTime(Timestamp bookEndTime) {
		this.bookEndTime = bookEndTime;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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
}
