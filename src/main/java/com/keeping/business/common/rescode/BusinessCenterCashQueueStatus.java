package com.keeping.business.common.rescode;

public enum BusinessCenterCashQueueStatus {

	CASHQUEUE_STATUS_MODIFYING(0, "modifying"),
	CASHQUEUE_STATUS_START(1, "start"),
	CASHQUEUE_STATUS_CANCEL(3, "cancel"),
	CASHQUEUE_STATUS_FINISH(2, "finish");
	
	private Integer id;
	private String status;
	
	private BusinessCenterCashQueueStatus(Integer id, String status){
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
