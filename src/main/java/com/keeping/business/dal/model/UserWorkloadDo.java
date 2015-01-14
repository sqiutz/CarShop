package com.keeping.business.dal.model;

import java.sql.Timestamp;
import java.sql.Date;

public class UserWorkloadDo {

	private Integer id;
	
	private Integer userId;
	
	private Integer modifyqueueId;
	
	private Float humanResource;
	
	private Integer generalRepaire;
	
	private Float additionalHours;
	
	private String comment; 
	
	private Timestamp createTime;
	
	private Timestamp modifyTime;
	
	private Date assignDate;

	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private Timestamp allocatedTime;
	
	private Integer saId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getModifyqueueId() {
		return modifyqueueId;
	}

	public void setModifyqueueId(Integer modifyqueueId) {
		this.modifyqueueId = modifyqueueId;
	}

	public Float getHumanResource() {
		return humanResource;
	}

	public void setHumanResource(Float humanResource) {
		this.humanResource = humanResource;
	}

	public Integer getGeneralRepaire() {
		return generalRepaire;
	}

	public void setGeneralRepaire(Integer generalRepaire) {
		this.generalRepaire = generalRepaire;
	}

	public Float getAdditionalHours() {
		return additionalHours;
	}

	public void setAdditionalHours(Float additionalHours) {
		this.additionalHours = additionalHours;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

	public Timestamp getAllocatedTime() {
		return allocatedTime;
	}

	public void setAllocatedTime(Timestamp allocatedTime) {
		this.allocatedTime = allocatedTime;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Integer getSaId() {
		return saId;
	}

	public void setSaId(Integer saId) {
		this.saId = saId;
	}
	
}
