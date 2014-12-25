package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.CounterDo;

public interface CounterDao{
	
	public CounterDo queryByKey(String key);
	
	public List<CounterDo> queryAll();
	
	public void addCounter(CounterDo counterDo);
	
	public void modifyCounter(CounterDo counterDo);

}
