package com.keeping.business.common.rescode;

public enum BusinessCenterOrderStatus {

	ORDER_STATUS_BOOK(0, "book"),
	ORDER_STATUS_WAIT(1, "wait"),
	ORDER_STATUS_SERVE(2, "serve"),
	ORDER_STATUS_MODIFY(3, "modify"),
	ORDER_STATUS_WASH(4, "wash"),
	ORDER_STATUS_HOLD(5, "hold"),
	ORDER_STATUS_CANCEL(6, "cancel");
	
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
	
    public static BusinessCenterOrderStatus getById(Integer id) {
        if (id == null)
            return null;
        for (BusinessCenterOrderStatus value : values()) {
            if (value.getId() == id)
                return value;
        }
        return null;
    }
}
