package cn.sf_soft.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.sf_soft.common.model.PageModel;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import cn.sf_soft.basedata.model.BaseSettlementTypes;
import cn.sf_soft.basedata.model.SysFlags;

/**
 * 公用的DAO
 * 
 * @author king
 * @create 2013-4-25下午03:53:03
 */
public interface BaseDao {

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public Serializable save(Object entity);

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public boolean delete(Object entity);

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public boolean update(Object entity);

	/**
	 * 根据条件查询
	 * 
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(DetachedCriteria dc);

	/**
	 * 根据条件分页查询
	 * 
	 * @param dc
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(DetachedCriteria dc, int pageNo, int pageSize);

	public <T> T load(Class<T> entityClass, Serializable id);

	@SuppressWarnings("rawtypes")
	public List queryWithCache(String hql);

	public <T> T get(Class<T> entityClass, Serializable id);

	/**
	 * execute SQL
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List executeSQLQuery(String sql);

	public List<Map<String, Object>> getMapBySQL(String sql,
			Map<String, Object> params);

	public <T> List<T> getEntityBySQL(String sql, Class<T> entity,
			Map<String, Object> params);

	/**
	 * 得到外置命名查询的SQL语句，此方法将查询的字段名或表名作为参数传递<br>
	 * 此方法主要为了实现查询语句 的字段名等作为参数传递
	 * 
	 * @param queryName
	 *            外置命名查询的名称
	 * @param filedNames
	 *            占位符的名称
	 * @param filedValues
	 *            字段名或表名，或其他字符串类型参数的值
	 * @return
	 * @throws Exception
	 */
	public String getQueryStringByName(String queryName, String[] filedNames,
			String[] filedValues);

	/**
	 * Execute a named query binding a number of values to "?" parameters in the
	 * query string. A named query is defined in a Hibernate mapping file.
	 */
	public List findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values);

	/***
	 * 获取设置选项的值
	 * 
	 * @param optionNo
	 * @return
	 */
	public String getOptionValue(final String optionNo);

	/**
	 * 获取SysFlags表中{meaning:code}键值对
	 * 
	 * @param fieldNo
	 * @return
	 */
	public Map<String, Short> getMeanAndCodeMapBySysFlagFieldNo(String fieldNo);

	/**
	 * 根据field_no和code获得sys_flags
	 */
	public SysFlags getSysFlagsByFieldNoAndCode(String fieldNo, Short code);

	/**
	 * 根据typeid获得BaseSettlementTypes
	 */
	public BaseSettlementTypes getBaseSettlementTypesByTypeId(String typeId);

	/**
	 * hql 查询
	 * @param hql
	 * @param values
	 * @return
	 */
	List<?> findByHql(String hql, Object... values);

	/**
	 * hql 查询 PageModel
	 * @param hql
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageModel findByHql(String hql , int page, int pageSize);

	/**
	 * sql 查询 PageModel
	 * @param sql
	 * @param entryClass
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageModel findBySql(String sql, Class entryClass , int page, int pageSize);

	String mapToFilterString(Map<String, Object> filter, String alias);

	void saveMapToObject(Map<String, Object> jsonData, Object obj);

	String underline2Camel(String line, boolean smallCamel);

	String camel2Underline(String line);
	/**
	 * flush
	 */
	void flush();


	/**
	 * 获得当前的session
	 * @return
	 */
	Session getCurrentSession();

	/**
	 * 查询hql并将结果转换为PageModel
	 * <p>注意：查询熟悉必须后面有as重命名，否则返回的Map中key会是数字；正确写法SELECT stationId as stationId,entryType as entryType,documentId as documentId, documentNo as documentNo, subDocumentNo as documentNo  from FinanceDocumentEntries f where f.documentNo= 'BPOS18050006' </p>
	 * @param hql
	 * @param values
	 * @return
	 */
	PageModel<Object> listInHql(String hql, Object... values);

	/**
	 *  查询hql并将结果转换为PageModel
	 * 	<p>注意：查询熟悉必须后面有as重命名，否则返回的Map中key会是数字；正确写法SELECT stationId as stationId,entryType as entryType,documentId as documentId, documentNo as documentNo, subDocumentNo as documentNo  from FinanceDocumentEntries f where f.documentNo= 'BPOS18050006' </p>
	 * <p>如果countHql为空，接口将自动拼装countHql（不保证执行正确）</p>
	 * @param hql
	 * @param countHql
	 * @param pageNo
	 * @param linesPerPage
	 * @param values
	 * @return
	 */
	PageModel<Object> listInHql (String hql, String countHql, int pageNo, int linesPerPage, Object... values);

	PageModel<Object> listInSql(String sql, Map<String, Object> params);


	/**
	 * 查询hql并将结果转换为PageModel
	 * 	<p>如果countSql为空，接口将自动拼装countSql（不保证执行正确）</p>
	 * @param sql
	 * @param countSql
	 * @param pageNo
	 * @param linesPerPage
	 * @param params
	 * @return
	 */
	PageModel<Object> listInSql (String sql, String countSql, int pageNo, int linesPerPage, Map<String, Object> params);

	Map<String, Object> toMap(Object model);

	List<List<Object>> findBySql(final String sql, final Object... parms);

	<T> T findUniqueBySql(final String sql, final Object... parms);

	<E> List<E> findByNewSession(String hql, Object... value);

     List<List<Map<String, Object>>> findProcedureMulti(final String sql, final Object[] params);

     List<Map<String, Object>> findProcedure(final String sql, final Object[] params);

	 void findProcedureWithoutReturn(final String queryString, final Object[] params);

}
