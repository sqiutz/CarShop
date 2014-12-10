package com.keeping.business.common.rescode;

public enum BusinessCenterOrderStatus {

	ORDER_STATUS_BOOK(0, "book"),
	ORDER_STATUS_WAIT(1, "wait"),
	ORDER_STATUS_SERVE(2, "serve"),
	ORDER_STATUS_MODIFY(3, "modify"),
	ORDER_STATUS_HOLD(4, "hold");
	
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
