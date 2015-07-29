package com.keeping.business.web.controller.model;

import java.util.Date;
import java.util.List;

public class OrderPerDayDetail {

	private Date date;
	
	private List<OrderPerPerson> orderPerPersons;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<OrderPerPerson> getOrderPerPersons() {
		return orderPerPersons;
	}

	public void setOrderPerPersons(List<OrderPerPerson> orderPerPersons) {
		this.orderPerPersons = orderPerPersons;
	}
}
