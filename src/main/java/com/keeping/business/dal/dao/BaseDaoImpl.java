package com.keeping.business.dal.dao;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.keeping.business.dal.pagination.ReflectUtil;

public abstract class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {
	  
	  protected String queryAll;
	  protected String queryByPK;
	  protected String queryByCompositePK;
	  protected String query;
	  protected String queryOne;
	  protected String count;
	  protected String queryNativePage;
	  protected String queryUnique;
	  protected String updateByPK;
	  protected String update;
	  protected String delete;
	  protected String deleteByPK;
	  protected String add;
	  protected String queryByParamMap;
	  protected String countByParamMap;
	  protected String entityClassName;
	  private Class<T> entityClass;

	  public void initialize()
	    throws Exception
	  {
	    initSqlName();
	  }

	  private void initSqlName()
	  {
	    this.entityClass = ReflectUtil.getSuperClassGenericType(getClass());
	    this.entityClassName = this.entityClass.getSimpleName();

	    this.queryAll = MessageFormat.format("{0}.queryAll", new Object[] { this.entityClassName });
	    this.queryByPK = MessageFormat.format("{0}.queryByPK", new Object[] { this.entityClassName });
	    this.queryByCompositePK = MessageFormat.format("{0}.queryByCompositePK", new Object[] { this.entityClassName });
	    this.query = MessageFormat.format("{0}.query", new Object[] { this.entityClassName });
	    this.queryOne = MessageFormat.format("{0}.queryOne", new Object[] { this.entityClassName });
	    this.count = MessageFormat.format("{0}.count", new Object[] { this.entityClassName });
	    this.queryNativePage = MessageFormat.format("{0}.queryNativePage", new Object[] { this.entityClassName });
	    this.queryUnique = MessageFormat.format("{0}.queryUnique", new Object[] { this.entityClassName });
	    this.updateByPK = MessageFormat.format("{0}.updateByPK", new Object[] { this.entityClassName });
	    this.update = MessageFormat.format("{0}.update", new Object[] { this.entityClassName });
	    this.delete = MessageFormat.format("{0}.delete", new Object[] { this.entityClassName });
	    this.deleteByPK = MessageFormat.format("{0}.deleteByPK", new Object[] { this.entityClassName });
	    this.add = MessageFormat.format("{0}.add", new Object[] { this.entityClassName });
	    this.queryByParamMap = MessageFormat.format("{0}.queryByParamMap", new Object[] { this.entityClassName });
	    this.countByParamMap = MessageFormat.format("{0}.countByParamMap", new Object[] { this.entityClassName });
	  }

	  public int insertData(T o)
	  {
	    return getSqlSession().insert(this.add, o);
	  }

	  public int deleteDataByPK(Object o)
	  {
	    return getSqlSession().delete(this.deleteByPK, o);
	  }

	  public int deleteData(T o)
	  {
	    return getSqlSession().delete(this.delete, o);
	  }

	  public int updateData(T o)
	  {
	    return getSqlSession().update(this.update, o);
	  }

	  public int updateDataByPK(T o)
	  {
	    return getSqlSession().update(this.updateByPK, o);
	  }

	  public Object queryObjectByPK(Object o)
	  {
	    return getSqlSession().selectOne(this.queryByPK, o);
	  }

	  public int queryForInt(T o)
	  {
	    return ((Integer)getSqlSession().selectOne(this.count, o)).intValue();
	  }

	  public List<T> queryForListAll()
	  {
	    return getSqlSession().selectList(this.queryAll);
	  }

	  public List<T> queryForList(T o, int offSet, int maxRow)
	  {
	    return getSqlSession().selectList(this.query, o, new RowBounds(offSet, maxRow));
	  }

//	  public PaginationBean<T> queryForListByPagination(T o, Page page)
//	  {
//	    int totalRecords = ((Integer)getSqlSession().selectOne(this.count, o)).intValue();
//	    PaginationBean pageHolder = new PaginationBean(page, totalRecords);
//	    List resultList = queryForList(o, (pageHolder.getCurrentPage() - 1) * pageHolder.getPageSize(), pageHolder.getPageSize());
//	    pageHolder.setPageList(resultList);
//	    return pageHolder;
//	  }

	  public int batchInsertData(List<T> ts)
	  {
	    int i = 0;
	    for (Object t : ts) {
	      insertData((T) t);
	      if (i % 50 == 0) {
	        flushSession();
	      }
	      i++;
	    }
	    flushSession();
	    return i;
	  }

	  public int batchUpdateData(List<T> ts)
	  {
	    int i = 0;
	    for (Object t : ts) {
	      updateDataByPK((T) t);
	      if (i % 50 == 0) {
	        flushSession();
	      }
	      i++;
	    }
	    flushSession();
	    return i;
	  }

//	  public PaginationBean<T> queryForListByPagination(Map<String, Object> map, Page page)
//	  {
//	    int totalRecords = ((Integer)getSqlSession().selectOne(this.countByParamMap, map)).intValue();
//	    PaginationBean pageHolder = new PaginationBean(page, totalRecords);
//	    List resultList = getSqlSession().selectList(this.queryByParamMap, map, new RowBounds(pageHolder.getBeginIndex(), pageHolder.getPageSize()));
//	    pageHolder.setPageList(resultList);
//	    return pageHolder;
//	  }

	  public T queryObject(T o)
	  {
	    return getSqlSession().selectOne(this.queryOne, o);
	  }

	  public List<T> queryForList(T o)
	  {
	    return getSqlSession().selectList(this.query, o);
	  }

	  public void flushSession()
	  {
	    getSqlSession().flushStatements();
	  }
}
