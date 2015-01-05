package com.keeping.business.dal.dao;

import java.util.List;

import com.keeping.business.dal.model.ModifyQueueDo;

public interface ModifyQueueDao extends BaseDao<ModifyQueueDo>{

	public List<ModifyQueueDo> queryByModifyQueuestep(Integer step);
	
	public void addModifyQueue(ModifyQueueDo modifyQueueDo);
	
	public List<ModifyQueueDo> queryByUseridAndStep(ModifyQueueDo modifyQueueDo);  //
	
	public void updateModifyQueue(ModifyQueueDo modifyQueueDo);
	
	public ModifyQueueDo queryByModifyQueueid(Integer id);
	
	public List<Integer> getAllWorkers(ModifyQueueDo modifyQueueDo);
	
	public List<ModifyQueueDo> queryByModifyQueueUserId(ModifyQueueDo modifyQueueDo);

}
