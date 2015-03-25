package com.keeping.business.web.controller.model;

import java.util.List;

public class OrderPerPerson {

	private Integer id;
	
	private Float load;
	
	private List orderList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getLoad() {
		return load;
	}

	public void setLoad(Float load) {
		this.load = load;
	}

	public List getOrderList() {
		return orderList;
	}

	public void setOrderList(List orderList) {
		this.orderList = orderList;
	}
	
	
}
