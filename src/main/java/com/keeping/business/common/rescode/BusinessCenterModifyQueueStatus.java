package com.keeping.business.common.rescode;

public enum BusinessCenterModifyQueueStatus {

	MODIFYQUEUE_STATUS_MODIFYING(0, "modifying"),
	MODIFYQUEUE_STATUS_HOLD(1, "hold"),
	MODIFYQUEUE_STATUS_SEND(2, "send");
	
	private Integer id;
	private String status;
	
	private BusinessCenterModifyQueueStatus(Integer id, String status){
		this.id = id;
		this.status = status;
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getStatus(){
		return status;
	}
}
