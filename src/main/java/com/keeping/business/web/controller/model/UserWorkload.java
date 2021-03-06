package com.keeping.business.web.controller.model;


import java.sql.Timestamp;
import java.util.Date;

public class UserWorkload extends BaseQueue{

	private Integer id;
	
	private Integer userId;
	
	private Integer modifyqueueId;
	
	private Float humanResource;
	
	private Float generalRepaire;
	
	private Float additionalHours;
	
	private String comment; 
	
	private Timestamp modifyTime;
	
	private Date allocatedTime;
	
	private Date assignDate;
	
	private Float totalLoad;
	
	private Integer saId;
	
	private User sa;
	
	private Integer isWarrant;
	
	private Integer isSubContract;
	
	private ModifyQueue modifyQueue;
	
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

	public Float getGeneralRepaire() {
		return generalRepaire;
	}

	public void setGeneralRepaire(Float generalRepaire) {
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

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getAllocatedTime() {
		return allocatedTime;
	}

	public void setAllocatedTime(Date allocatedTime) {
		this.allocatedTime = allocatedTime;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Float getTotalLoad() {
		return totalLoad;
	}

	public void setTotalLoad(Float totalLoad) {
		this.totalLoad = totalLoad;
	}

	public Integer getSaId() {
		return saId;
	}

	public void setSaId(Integer saId) {
		this.saId = saId;
	}

	public User getSa() {
		return sa;
	}

	public void setSa(User sa) {
		this.sa = sa;
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

	public ModifyQueue getModifyQueue() {
		return modifyQueue;
	}

	public void setModifyQueue(ModifyQueue modifyQueue) {
		this.modifyQueue = modifyQueue;
	}
	
}
