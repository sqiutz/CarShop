package com.keeping.business.web.controller.converter;

import java.util.List;

import com.keeping.business.web.controller.model.Order;
import com.keeping.business.web.controller.model.ServeQueue;
import com.keeping.business.web.controller.model.User;

public class ReorgQueue {

	public static void reorgServeQueue(List<ServeQueue> baseQueues, List<User> users, List<Order> orders){
		
		if(baseQueues == null || users == null || orders == null){
			return;
		}
		
		for(int i=0; i<baseQueues.size(); i++){
			baseQueues.get(i).setUser(users.get(i));
			baseQueues.get(i).setOrder(orders.get(i));
		}
		
		return;
	}
}