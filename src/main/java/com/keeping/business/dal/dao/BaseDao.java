package com.keeping.business.dal.dao;

import java.util.List;

public abstract interface BaseDao <T>{
	 public abstract int insertData(T paramT);

	  public abstract int batchInsertData(List<T> paramList);

	  public abstract int batchUpdateData(List<T> paramList);

	  public abstract int deleteDataByPK(Object paramObject);

	  public abstract int deleteData(T paramT);

	  public abstract int updateData(T paramT);

	  public abstract int updateDataByPK(T paramT);

	  public abstract Object queryObjectByPK(Object paramObject);

	  public abstract int queryForInt(T paramT);

	  public abstract List<T> queryForListAll();

	  public abstract List<T> queryForList(T paramT, int paramInt1, int paramInt2);

//	  public abstract PaginationBean<T> queryForListByPagination(T paramT, Page paramPage);

	  public abstract T queryObject(T paramT);

	  public abstract List<T> queryForList(T paramT);

	  public abstract void flushSession();

//	  public abstract PaginationBean<T> queryForListByPagination(Map<String, Object> paramMap, Page paramPage);
}
