package cn.sf_soft.common.dao.hbb;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigDecimal;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.DefaultTransformerAdapter;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.sf_soft.basedata.model.BaseSettlementTypes;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.dao.BaseDao;


/**
 * 公用的DAO
 *
 * @author king
 * @create 2013-4-25下午03:52:38
 */
@Repository("baseDao")
public class BaseDaoHibernateImpl extends HibernateDaoSupport implements BaseDao {


    protected Gson gson = new GsonBuilder()
            .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .setExclusionStrategies(new GsonExclutionStrategy()).create();


    public static final DefaultTransformerAdapter DEFAULT_TRANSFORMER_ADAPTER = DefaultTransformerAdapter.INSTANCE;

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseDaoHibernateImpl.class);

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }



    @Override
    public boolean delete(Object entity) throws DataAccessException {
        // try {
        getHibernateTemplate().delete(entity);

        // } catch (Exception e) {
        // e.printStackTrace();
        // return false;
        // }
        return true;
    }

    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<?> findByCriteria(DetachedCriteria dc) {
        List<Object> result = (List<Object>) getHibernateTemplate().findByCriteria(dc);
        return result;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public List findByCriteria(DetachedCriteria dc, int pageNo, int pageSize) {
        return getHibernateTemplate().findByCriteria(dc, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Serializable save(Object entity) {
        return getHibernateTemplate().save(entity);
    }

    @Override
    public boolean update(Object entity) {
        getHibernateTemplate().saveOrUpdate(entity);
        return true;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> T load(Class<T> entityClass, Serializable id) {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public List<Object> queryWithCache(final String hql) {

        return (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery(hql).setCacheable(true).list();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List executeSQLQuery(final String sql) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return session.createSQLQuery(sql).list();
            }
        });
    }

    /**
     * @SuppressWarnings("unchecked") public List<Map<String, Object>>
     * getDataByProc(final String sql,final
     * String documentNo) {
     * <p>
     * ResultSet rs = null; Connection conn =
     * null; CallableStatement proc = null;
     * return getHibernateTemplate().execute( new
     * HibernateCallback<List<Map<String,
     * Object>>>() {
     * <p>
     * public List<Map<String, Object>>
     * doInHibernate( Session session) throws
     * HibernateException, SQLException {
     * <p>
     * ResultSet rs = null; Connection conn =
     * null; CallableStatement proc = null;
     * List<Map<String,Object>> bsrList = new
     * ArrayList<Map<String，Object>(); try {
     * <p>
     * conn = SessionFactoryUtils.getDataSource(
     * session.getSessionFactory())
     * .getConnection(); proc = conn
     * .prepareCall(
     * "{call enterprise_stat_report(?,?) }");
     * <p>
     * proc.setString(0, sql); proc.setString(1,
     * documentNo); SQLServerTypes.Cur
     * proc.registerOutParameter(3,
     * OracleTypes.CURSOR); proc.execute(); rs =
     * (ResultSet) proc.getObject(3);
     * <p>
     * <p>
     * while (rs != null && rs.next()) {
     * <p>
     * } } catch (Exception e) {
     * e.printStackTrace(); throw new
     * RuntimeException(e.getMessage()); }
     * finally { try { if (null != rs) {
     * rs.close(); if (null != proc) {
     * proc.close(); }
     * <p>
     * if (null != conn) { conn.close(); } } }
     * catch (Exception ex) {
     * <p>
     * } } return bsrList; } }); }
     **/
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getMapBySQL(final String sql, final Map<String, Object> params) {
        return (List<Map<String, Object>>) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (params != null) {
                    Set<String> keySet = params.keySet();
                    for (String key : keySet) {
                        try {
                            Object val = params.get(key);
                            if (val instanceof Collection) {
                                query.setParameterList(key, (Collection) val);
                            } else if (val instanceof Object[]) {
                                query.setParameterList(key, (Object[]) val);
                            } else {
                                query.setParameter(key, val);
                            }
                        } catch (Exception e) {
                            logger.warn(String.format("getMapBySQL设置参数出现问题：%s", e));
                        }
                    }
                }
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                return query.list();
            }
        });

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getEntityBySQL(final String sql, final Class<T> entity, final Map<String, Object> params) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public List<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(entity);
                if (params != null) {
                    Set<String> keySet = params.keySet();
                    for (String key : keySet) {
                        query.setParameter(key, params.get(key));
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public String getQueryStringByName(String queryName, String[] filedNames, String[] filedValues) {
        Query query = getSessionFactory().getCurrentSession().getNamedQuery(queryName);
        String[] para = query.getNamedParameters();
        String queryString = query.getQueryString();

        if (filedNames != null && filedValues != null) {
            if (filedNames.length != filedValues.length) {
                throw new RuntimeException("字段名参数长度与字段值参数长度不一致");
            }
            for (int i = 0; i < filedNames.length; i++) {
                boolean flag = false;
                for (int j = 0; j < para.length; j++) {
                    if (filedNames[i].equals(para[j])) {
                        queryString = queryString.replace(":" + filedNames[i], filedValues[i]);
                        flag = true;
                        continue;
                    }
                }
                if (!flag) {
                    throw new RuntimeException("不存在名为" + filedNames[i] + "的参数");
                }
            }
        }
        return queryString;
    }

    @Override
    public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
    }

    /***
     * 获取设置选项的值
     *
     * @param optionNo
     * @return
     */
    @Override
    public String getOptionValue(final String optionNo) {
        List<String> values = getHibernateTemplate().execute(new HibernateCallback<List<String>>() {

            @Override
            @SuppressWarnings("unchecked")
            public List<String> doInHibernate(Session session) throws HibernateException {
                return session.createSQLQuery("SELECT option_value FROM sys_options WHERE option_no=?")
                        .setString(0, optionNo).list();
            }
        });
        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    /**
     * 获取SysFlags表中{meaning:code}键值对
     *
     * @param fieldNo
     * @return
     */
    @Override
    public Map<String, Short> getMeanAndCodeMapBySysFlagFieldNo(String fieldNo) {
        @SuppressWarnings("unchecked")
        List<SysFlags> sysFlags = (List<SysFlags>) getHibernateTemplate().find("FROM SysFlags f WHERE f.fieldNo=?",
                fieldNo);
        Map<String, Short> map = new HashMap<String, Short>(sysFlags.size());
        for (SysFlags f : sysFlags) {
            map.put(f.getMeaning(), f.getCode());
        }
        return map;
    }

    @Override
    public SysFlags getSysFlagsByFieldNoAndCode(String fieldNo, Short code) {
        @SuppressWarnings("unchecked")
        List<SysFlags> sysFlags = (List<SysFlags>) getHibernateTemplate().find(
                "from SysFlags f where f.fieldNo=? and f.code=?", fieldNo, code);
        if (null != sysFlags && sysFlags.size() > 0) {
            return sysFlags.get(0);
        }
        return null;
    }

    @Override
    public BaseSettlementTypes getBaseSettlementTypesByTypeId(String typeId) {
        @SuppressWarnings("unchecked")
        List<BaseSettlementTypes> baseSettlementTypes = (List<BaseSettlementTypes>) getHibernateTemplate().find(
                "from BaseSettlementTypes f where f.typeId=?", typeId);
        if (null != baseSettlementTypes && baseSettlementTypes.size() > 0) {
            return baseSettlementTypes.get(0);
        }
        return null;
    }


    @Override
    public List<?> findByHql(String hql, Object... values) {
        return getHibernateTemplate().find(hql, values);
    }

    @Override
    public PageModel findByHql(String hql, int page, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        ScrollableResults scroll = query.scroll();
        scroll.last();
        int total = scroll.getRowNumber() + 1;
        query.setFirstResult(Math.max((page - 1) * pageSize, 0));
        if (pageSize > 0) {
            query.setMaxResults(pageSize);
        }
        PageModel result = new PageModel(query.list(), page, pageSize, total);
        return result;
    }

    @Override
    public PageModel findBySql(String sql, Class entryClass, int page, int pageSize) {
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql); //.addEntity(entryClass);

        ScrollableResults scroll = query.scroll();
        scroll.last();
        int total = scroll.getRowNumber() + 1;
        query.setFirstResult(Math.max((page - 1) * pageSize, 0));
        if (pageSize > 0) {
            query.setMaxResults(pageSize);
        }
        PageModel result = new PageModel(query.setResultTransformer(DEFAULT_TRANSFORMER_ADAPTER).list(), page, pageSize, total);
        return result;
    }

    @Override
    public String mapToFilterString(Map<String, Object> filter, String alias) {
        if (filter == null || filter.size() == 0) {
            return " ( 1 = 1 ) ";
        }
        if (alias == null) {
            alias = "";
        } else {
            alias = alias + ".";
        }

        String regEx = "(.+)__(\\w+)$";
        Pattern pattern = Pattern.compile(regEx);
        List<String> split = new ArrayList<String>(filter.size());
        for (Map.Entry<String, Object> item : filter.entrySet()) {
            Matcher m = pattern.matcher(item.getKey());
            String key;
            if (m.find()) {
                key = alias + m.group(1);
                String type = m.group(2);
                switch (type) {
                    case "isnull":
                        split.add(key + " IS NULL");
                        break;
                    case "notnull":
                        split.add(key + " IS NOT NULL");
                        break;
                    case "like":
                        split.add(key + " LIKE '%" + item.getValue().toString() + "%'");
                        break;
                    case "eq":
                        split.add(key + " = '" + item.getValue().toString() + "'");
                        break;
                    case "ne":
                        split.add(key + " <> '" + item.getValue().toString() + "'");
                        break;
                    case "gt":
                        split.add(key + " > '" + item.getValue().toString() + "'");
                        break;
                    case "gte":
                        split.add(key + " >= '" + item.getValue().toString() + "'");
                        break;
                    case "lt":
                        split.add(key + " < '" + item.getValue().toString() + "'");
                        break;
                    case "lte":
                        split.add(key + " <= '" + item.getValue().toString() + "'");
                        break;
                    case "in":
                        split.add(key + " IN " + item.getValue().toString());
                        break;
                    case "keyword":
                        String[] fields = StringUtils.split(key.substring(0, key.length() - 1), ",");
                        List<String> orList = new ArrayList<>(fields.length);
                        for (String field : fields) {
                            orList.add(field + " LIKE '%" + item.getValue().toString() + "%'");
                        }
                        split.add("(" + StringUtils.join(orList, " OR ") + ")");
                        break;
                }
            } else {
                key = alias + item.getKey();
                split.add(key + " = '" + item.getValue().toString() + "'");
            }
        }
        return " ( " + StringUtils.join(split, " AND ") + " ) ";
    }

    public void saveMapToObject(Map<String, Object> jsonData, Object obj) {
        Class objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        for (String key : jsonData.keySet()) {
            String methodName = "set" + underline2Camel(key, false);
            try {
                for (Method fun : methods) {
                    if (!fun.getName().equals(methodName)) {
                        continue;
                    }

                    Class[] params = fun.getParameterTypes();
                    if (params[0].equals(Double.class) || params[0].equals(double.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()));
                    } else if (params[0].equals(Integer.class) || params[0].equals(int.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).intValue());
                    } else if (params[0].equals(Short.class) || params[0].equals(short.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).shortValue());
                    } else if (params[0].equals(Float.class) || params[0].equals(float.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).floatValue());
                    } else if (params[0].equals(BigDecimal.class)) {
                        fun.invoke(obj, BigDecimal.valueOf(new Double(jsonData.get(key).toString()).floatValue()));
                    } else if (params[0].equals(Timestamp.class)) {
                        String value = jsonData.get(key).toString();
                        if (value.length() == 10) {
                            value += " 00:00:00";
                        }
                        fun.invoke(obj, Timestamp.valueOf(value));
                    } else if (params[0].equals(Boolean.class) || params[0].equals(boolean.class)) {
                        fun.invoke(obj, Boolean.valueOf(jsonData.get(key).toString()));
                    } else {
                        fun.invoke(obj, jsonData.get(key).toString());
                    }
                    break;
                }
            } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                String message = "提交数据 '" + key + "':'"
                        + (obj == null ? "NULL" : obj.toString())
                        + "' 格式有误:" + e.getMessage();
                logger.info(message);
                e.printStackTrace();
                throw new ServiceException(message);
            }
        }
    }

    @Override
    public Session getCurrentSession() {
        return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
    }


    @Override
    public PageModel<Object> listInHql(String hql, Object... values) {
        return listInHql(hql, null, 0, 0, values);
    }

    @Override
    public PageModel<Object> listInHql(String hql, String countHql, int pageNo, int linesPerPage, Object... values) {
        PageModel pageModel = null;
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; ++i) {
                query.setParameter(i, values[i]);
            }
        }

        //判断分页
        if (pageNo > 0 && linesPerPage > 0) {
            query.setFirstResult((pageNo - 1) * linesPerPage);
            query.setMaxResults(linesPerPage);
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        pageModel = new PageModel(list);

        //分页
        if (pageNo > 0 && linesPerPage > 0) {
            pageModel.setPage(pageNo);
            pageModel.setPerPage(linesPerPage);
            //如果countHql为空，则自己拼接countSql
            countHql = StringUtils.isBlank(countHql) ? getCountHql(hql) : countHql;
            try {
                // 查询总记录条数
                Long resultCount = getResultCount(countHql, values);
                pageModel.setTotalSize(resultCount);
            } catch (Exception ex) {
                throw new ServiceException("ListInHql无法获得总记录数", ex);
            }
        }

        return pageModel;
    }

    private String getCountHql(String hql) {
        Pattern p = Pattern.compile("(\\w|\\.|\\s)(?i)FROM(\\w|\\.|\\s)");
        Matcher m = p.matcher(hql);
        while (m.find()) {
            return String.format("SELECT COUNT(*) %s", hql.substring(m.start()));
        }
        throw new ServiceException("无法转换为countHql，请检查hql是否正确：" + hql);
    }

    private Long getResultCount(String hql, Object... values) {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Long) query.uniqueResult();
    }

    @Override
    public PageModel<Object> listInSql(String sql, Map<String, Object> params) {
        return listInSql(sql, null, 0, 0, params);
    }

    @Override
    public PageModel<Object> listInSql(String sql, String countSql, int pageNo, int linesPerPage, Map<String, Object> params) {
        PageModel pageModel = null;
        SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        if (null != params) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object val = params.get(key);
                if (val instanceof Collection) {
                    query.setParameterList(key, (Collection) val);
                } else if (val instanceof Object[]) {
                    query.setParameterList(key, (Object[]) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        }

        if (pageNo > 0 && linesPerPage > 0) {
            query.setFirstResult((pageNo - 1) * linesPerPage);
            query.setMaxResults(linesPerPage);
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        pageModel = new PageModel(list);


        if (pageNo > 0 && linesPerPage > 0) {
            pageModel.setPage(pageNo);
            pageModel.setPerPage(linesPerPage);

            //如果countHql为空，则自己拼接countSql
            countSql = StringUtils.isBlank(countSql) ? String.format("SELECT COUNT(*) AS _row_conunt_ FROM (%s) AS _count_sql_", sql) : countSql;
            try {
                // 查询总记录条数
                Long resultCount = getResultCountForSql(countSql, params);
                pageModel.setTotalSize(resultCount);
            } catch (Exception ex) {
                throw new ServiceException("ListInSql无法获得总记录数", ex);
            }

        }

        return pageModel;
    }

    private Long getResultCountForSql(String countSql, Map<String, Object> params) {
        SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(countSql);
        if (params != null) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                Object val = params.get(key);
                if (val instanceof Collection) {
                    query.setParameterList(key, (Collection) val);
                } else if (val instanceof Object[]) {
                    query.setParameterList(key, (Object[]) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        }

        return Long.valueOf(query.uniqueResult().toString());
    }


    public String underline2Camel(String line, boolean smallCamel) {
        return DefaultTransformerAdapter.underline2Camel(line, smallCamel);
    }

    public String camel2Underline(String line) {
        return DefaultTransformerAdapter.camel2Underline(line);
    }


    @Override
    public Map<String, Object> toMap(Object model) {
        return gson.fromJson(gson.toJson(model), HashMap.class);
    }

    @Override
    public List<List<Object>> findBySql(final String sql,
                                        final Object... parms) {
        return getHibernateTemplate().executeWithNativeSession(
                new HibernateCallback<List<List<Object>>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public List<List<Object>> doInHibernate(Session session)
                            throws HibernateException {
                        SQLQuery query = session.createSQLQuery(sql);
                        if (parms != null) {
                            for (int i = 0; i < parms.length; i++) {
                                query.setParameter(i, parms[i]);
                            }
                        }
                        query.setResultTransformer(Transformers.TO_LIST);
                        return query.list();
                    }
                });
    }

    @Override
    public <E> E findUniqueBySql(final String sql, final Object... parms) {
        Object obj = getHibernateTemplate().executeWithNativeSession(
                new HibernateCallback<List<E>>() {
                    @Override
                    public List<E> doInHibernate(Session session)
                            throws HibernateException {
                        SQLQuery query = session.createSQLQuery(sql);
                        if (parms != null) {
                            for (int i = 0; i < parms.length; i++) {
                                query.setParameter(i, parms[i]);
                            }
                        }
                        query.setResultTransformer(Transformers.TO_LIST);
                        return (List<E>) query.uniqueResult();
                    }
                });
        if (null == obj) {
            return null;
        } else {
            return ((List<E>) obj).get(0);
        }
    }


    /**
     * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
     *
     * @param queryString
     * @param params
     */
    public void findProcedureWithoutReturn(final String queryString, final Object[] params) {
        try {
            Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            session.doWork(new Work() {
                public void execute(Connection conn) throws SQLException {
                    CallableStatement call = conn.prepareCall(queryString);
                    if (null != params) {
                        for (int i = 0; i < params.length; i++) {
                            call.setObject(i + 1, params[i]);
                        }
                    }
                    call.execute();
                    call.close();
                }
            });
        } catch (Exception e) {
            throw new ServiceException("调用存储过程出错", e);
        }
    }

    /**
     * 通过存储过程查询(单结果集)
     *
     * @param sql    查询sql
     * @param params 参数
     * @return
     */
    @Override
    public List<Map<String, Object>> findProcedure(final String sql, final Object[] params) {
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {
            Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            session.doWork(new Work() {
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs = null;
                    ResultSet rs = null;
                    cs = conn.prepareCall(sql);
                    for (int i = 0; i < params.length; i++) {
                        cs.setObject(i + 1, params[i]);// 设置参数
                    }
                    rs = cs.executeQuery();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int colCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        for (int i = 1; i <= colCount; i++) {
                            String colName = metaData.getColumnName(i);
                            map.put(colName, rs.getObject(colName));
                        }
                        result.add(map);
                    }
                    close(cs, rs);
                }
            });
        } catch (Exception e) {
            throw new ServiceException("调用存储过程出错", e);
        }

        return result;
    }


    /**
     * 通过存储过程查询(多结果集)
     *
     * @param sql    查询sql
     * @param params 参数
     * @return
     */
    @Override
    public List<List<Map<String, Object>>> findProcedureMulti(final String sql, final Object[] params) {
        final List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
        try {
            Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
            session.doWork(new Work() {
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs = null;
                    ResultSet rs = null;
                    cs = conn.prepareCall(sql);
                    for (int i = 0; i < params.length; i++) {
                        cs.setObject(i + 1, params[i]);
                    }
                    boolean hadResults = cs.execute();
                    ResultSetMetaData metaData = null;
                    while (hadResults) {// 遍历结果集
                        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();// 用于装该结果集的内容
                        rs = cs.getResultSet();// 获取当前结果集
                        metaData = rs.getMetaData();
                        int colCount = metaData.getColumnCount();// 获取当前结果集的列数
                        while (rs.next()) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            for (int i = 1; i <= colCount; i++) {
                                String colName = metaData.getColumnName(i);
                                map.put(colName, rs.getObject(colName));
                            }
                            rsList.add(map);
                        }
                        result.add(rsList);
                        close(null, rs);// 遍历完一个结果集，将其关闭
                        hadResults = cs.getMoreResults();// 移到下一个结果集
                    }
                    close(cs, rs);
                }
            });
            return result;
        } catch (Exception e) {
            throw new ServiceException("调用存储过程出错", e);
        }
    }


    private void close(CallableStatement cs, ResultSet rs) {
        try {
            if (cs != null) {
                cs.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <E> List<E> findByNewSession(String hql, Object... value) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        try {
            Query query = session.createQuery(hql);
            if (null != value && value.length > 0) {
                for (int i = 0; i < value.length; i++) {
                    query.setParameter(i, value[i]);
                }
            }
            return query.list();
        } finally {
            if (null != session && session.isOpen()) {
                session.close();
            }
        }
    }
}
