package com.keeping.business.dal.model;

import java.sql.Date;
import java.sql.Timestamp;

public class ModifyQueueDo {
	
	private Integer id;

	private Integer step;
	
    private Timestamp createTime;
    
    private Timestamp startTime;
    
    private Timestamp endTime;
  
    private Integer userId;
    
    private Integer orderId;  
    
    private String jobType;
    
    private Integer isWarrant;
     
    private Integer isSubContract;
    
    private String technician;
    
    private Date assignTime;
    
    private Integer additionTime;
    
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

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

	public Integer getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(Integer additionTime) {
		this.additionTime = additionTime;
	}

}
