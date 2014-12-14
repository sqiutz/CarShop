package com.keeping.business.web.controller.model;

import java.sql.Date;

public class EstimationTime {

	private Date time;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer estimationTime;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getEstimationTime() {
		return estimationTime;
	}

	public void setEstimationTime(Integer estimationTime) {
		this.estimationTime = estimationTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
