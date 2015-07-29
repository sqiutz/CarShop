package com.keeping.business.web.controller.model;

import java.util.Date;

public class StepObject {
	
	private String step;
	
	private Integer isBook;
	
	private Date today;
	
	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}
}
