package com.keeping.business.web.controller.model;

import java.util.Date;

public class OrderObject {

	private String queueNumber;
	
	private String registerNumber;
	
	private Date now;
	
	private Integer status;
	
	private Integer isBook;

	public String getQueueNumber() {
		return queueNumber;
	}

	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
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
	
	
}
