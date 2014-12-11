package com.keeping.business.common.rescode;

public enum BusinessCenterModifyQueueStatus {

	MODIFYQUEUE_STATUS_MODIFYING(0, "modifying"),
	MODIFYQUEUE_STATUS_START(1, "start"),
	MODIFYQUEUE_STATUS_HOLD(2, "hold"),
	MODIFYQUEUE_STATUS_FINISH(3, "finish");
	
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
