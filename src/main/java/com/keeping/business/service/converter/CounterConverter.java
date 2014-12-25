package com.keeping.business.service.converter;

import com.keeping.business.dal.model.CounterDo;
import com.keeping.business.web.controller.model.Counter;

public class CounterConverter {

	/**
     * CounterDo==>Counter 
     */
    public static Counter getCounter(CounterDo counterDo) {
        if (counterDo == null) {
            return null;
        }
        Counter counter = new Counter();
        counter.setId(counterDo.getId());
        counter.setName(counterDo.getName());
        counter.setIsBook(counterDo.getIsBook());
        
        return counter;
    }
    
    public static CounterDo getCounterDo(Counter counter) {
    	if(null == counter) {
    		return null;
    	}
    	CounterDo counterDo = new CounterDo();
    	counterDo.setId(counter.getId());
    	counterDo.setName(counter.getName());
    	counterDo.setIsBook(counter.getIsBook());
    
    	return counterDo;
    }
}
