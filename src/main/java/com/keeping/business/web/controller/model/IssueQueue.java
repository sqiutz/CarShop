package com.keeping.business.web.controller.model;

import java.util.Date;


public class IssueQueue extends BaseQueue{

	private String jobType;
	
	private String technician;
	
	private Date assignTime;
	
	private Float additionTime;
	
	private Date promistTime;
	
	private Date assignDate;
	
	private Integer issuerId;
	
	private User issuer;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getTechnician() {
		return technician;
	}

	public void setTechnician(String technician) {
		this.technician = technician;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public Float getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(Float additionTime) {
		this.additionTime = additionTime;
	}

	public Date getPromistTime() {
		return promistTime;
	}

	public void setPromistTime(Date promistTime) {
		this.promistTime = promistTime;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Integer getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(Integer issuerId) {
		this.issuerId = issuerId;
	}

	public User getIssuer() {
		return issuer;
	}

	public void setIssuer(User issuer) {
		this.issuer = issuer;
	}

    
}
