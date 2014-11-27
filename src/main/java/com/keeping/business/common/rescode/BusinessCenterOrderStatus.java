package com.keeping.business.common.rescode;

public enum BusinessCenterOrderStatus {

	ORDER_STATUS_BOOK(0, "book"),
	ORDER_STATUS_WAIT(1, "wait"),
	ORDER_STATUS_SERVE(2, "serve");
	
	private Integer id;
	private String status;
	
	private BusinessCenterOrderStatus(Integer id, String status){
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
