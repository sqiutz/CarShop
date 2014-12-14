package com.keeping.business.web.controller.model;

import java.sql.Timestamp;


public class ServeQueue extends BaseQueue{
    
    private Integer elapseTime;
	
    private Timestamp modifyTime;

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getElapseTime() {
		return elapseTime;
	}

	public void setElapseTime(Integer elapseTime) {
		this.elapseTime = elapseTime;
	}
    
    
}
