package com.keeping.business.web.controller.model;


public class ModifyQueue extends BaseQueue{

	private String jobType;
	
	private Integer isWarrant;
	
	private Integer isSubContract;
	
	private String technician;

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
    
}
