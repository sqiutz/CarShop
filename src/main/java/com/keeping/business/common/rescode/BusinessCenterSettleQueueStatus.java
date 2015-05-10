package com.keeping.business.common.rescode;

public enum BusinessCenterSettleQueueStatus {

	SETTLEQUEUE_STATUS_SETTLE(0, "settling"),
	SETTLEQUEUE_STATUS_START(1, "start"),
	SETTLEQUEUE_STATUS_CANCEL(3, "cancel"),
	SETTLEQUEUE_STATUS_FINISH(2, "finish");
	
	private Integer id;
	private String status;
	
	private BusinessCenterSettleQueueStatus(Integer id, String status){
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
