package com.keeping.business.dal.model;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderDo {

	private Integer status;
	
    private Timestamp createTime;
    
    private Timestamp startTime;
    
    private Timestamp endTime;
    
    private String registerNum;
    
    private String roofNum;
    
    private String queueNum;
    
    private String bookNum;
    
    private Date promiseTime;
    
    private Date estimationTime;

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

	public Date getEstimationTime() {
		return estimationTime;
	}

	public void setEstimationTime(Date estimationTime) {
		this.estimationTime = estimationTime;
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}
}
