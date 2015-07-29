package com.keeping.business.web.controller.model;

import java.util.List;


public class TodayModifyQueue extends BaseQueue{

	private String username;
	
	private List<ModifyQueue> modifyQueues;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ModifyQueue> getModifyQueues() {
		return modifyQueues;
	}

	public void setModifyQueues(List<ModifyQueue> modifyQueues) {
		this.modifyQueues = modifyQueues;
	}
	
	
    
}
