package com.keeping.business.common.rescode;

public enum BusinessCenterServeQueueStatus {

	SERVEQUEUE_STATUS_SERVING(0, "serving"),
	SERVEQUEUE_STATUS_HOLD(1, "hold"),
	SERVEQUEUE_STATUS_SEND(2, "send"),
	SERVEQUEUE_STATUS_CANCEL(3, "cancel");
	
	private Integer id;
	private String status;
	
	private BusinessCenterServeQueueStatus(Integer id, String status){
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
