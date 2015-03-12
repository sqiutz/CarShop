package com.keeping.business.web.controller.model;

import java.util.Date;


public class ModifyQueue extends BaseQueue{

	private String jobType;
	
	private Float jobtypeTime;
	
	private Integer isWarrant;
	
	private Integer isSubContract;
	
	private String technician;
	
	private Date assignTime;
	
	private Float additionTime;
	
	private String roofNum;
	
	private Date promistTime;
	
	private Date assignDate;
	
	private Float load;
	
	private Integer modifierId;
	
	private User modifier;

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getRoofNum() {
		return roofNum;
	}

	public void setRoofNum(String roofNum) {
		this.roofNum = roofNum;
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

	public Float getLoad() {
		return load;
	}

	public void setLoad(Float load) {
		this.load = load;
	}

	public Integer getModifierId() {
		return modifierId;
	}

	public void setModifierId(Integer modifierId) {
		this.modifierId = modifierId;
	}

	public Float getJobtypeTime() {
		return jobtypeTime;
	}

	public void setJobtypeTime(Float jobtypeTime) {
		this.jobtypeTime = jobtypeTime;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}
    
}
