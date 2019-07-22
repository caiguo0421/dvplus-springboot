package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.sf_soft.office.approval.service.impl.BaseApproveProcess;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.oa.notification.model.VwApproveDocumentsNotification;
import cn.sf_soft.office.approval.dao.ApproveDocumentDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 审批单据DAO
 * 
 * @author king
 */
@Repository("approveDocumentDaoHibernate")
public  class ApproveDocumentDaoHibernate extends BaseDaoHibernateImpl implements ApproveDocumentDao {

	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApproveDocumentDaoHibernate.class);
	private SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");

	// 102025 配件采购计划 20161124 ,'151010'
	// 102025 消贷征信审批 20171130 ,'101810'
	// 102025 通用审批 20171130 ,'359030'
	// private static String MODULE_IDS = "('102065','351010', '353510', '355510','355520','355530', '355540', '356510','401010','401020','403020','403025','404520','402330','355525','409010','409020','354910','355527','351530','355522','107017','101522','107030','102025','101803','101002')";
	public static String MODULE_IDS = "('151010', '359030', '102065','351010', '353510', '355510','355520','355530', '355540', '356510','401010','401020','403020','403025','404520','402330','355525','409010','409020','354910','355527','351530','355522','107017','101522','107030','102025','101803','101002', '158580','103020')";

    @Resource(name="approveManager")
    private Map<String, BaseApproveProcess> approveManager;

    /**
     * 初始化设置MODULE_IDS
     */
    @PostConstruct
    public void init(){
        if(null != approveManager && !approveManager.isEmpty()){
            List<String> list = new ArrayList<>(approveManager.size());
            for(String key : approveManager.keySet()){
                list.add("'"+key+"'");
            }
            MODULE_IDS = "("+StringUtils.join(list, ",")+")";
        }
    }

	@SuppressWarnings({ "unchecked" })
	public List<VwOfficeApproveDocuments> getPendingMatters(final String userId, String documentNo, String submitUserName, String moduleId) {
		if (userId == null)
			return null;
		StringBuffer whereSql = buildHibernateConditionStringBuffer("d", documentNo, moduleId);
		return (List<VwOfficeApproveDocuments>) getHibernateTemplate().find(
				"FROM VwOfficeApproveDocuments d " + whereSql.toString() + " AND d.approverId like ? AND (d.status = ? OR d.status = ?) and d.moduleId IN"
						+ MODULE_IDS, new Object[] { '%'+userId+'%', (short) 20, (short) 30 });
	}

	/**
	 * 根据审批单据的Id得到审批单据的详细信息
	 */
	@SuppressWarnings("rawtypes")
	public ApproveDocuments getApproveDocumentsById(String documentId) {
		return getHibernateTemplate().get(ApproveDocuments.class, documentId);
	}

	/**
	 * 修改审批单据
	 */
	@SuppressWarnings("rawtypes")
	public boolean updateApproveDocument(ApproveDocuments approveDocument) {
		try {
			getHibernateTemplate().update(approveDocument);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @deprecated use
	 *             {@link #findApprovedMattersByProperties(String, String, String, Date, Date, int, int)}
	 *             根据用户ID分页得到其审批过的单据（已办流程）
	 */
	@Deprecated
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ApproveDocuments> getApprovedMatters(final String userId, final int pageNo, final int pageSize) {
		List<ApproveDocuments> document = (List<ApproveDocuments>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session) throws HibernateException {
						return session
								.createQuery(
										"FROM ApproveDocuments d WHERE d.documentId IN(SELECT p.documentId FROM ApproveDocumentsPoints p WHERE p.approverId = :userId) AND d.moduleId IN"
												+ MODULE_IDS).setParameter("userId", userId)
								.setFirstResult(pageNo * pageSize).setMaxResults(pageSize).list();
					}
				});
		return document;
	}

	/**
	 * 根据用户ID得到由其提交的，且正在审批中的单据（我在待办）
	 */
	@SuppressWarnings({ "unchecked" })
	public List<VwOfficeApproveDocuments> getMyApprovingMatters(final String userId,  String documentNo, String submitUserName, String moduleId) {
		StringBuffer whereSql = buildHibernateConditionStringBuffer("d", documentNo, moduleId);
		return (List<VwOfficeApproveDocuments>) getHibernateTemplate().find(
				"FROM VwOfficeApproveDocuments d " + whereSql.toString() + " AND d.userId = ? AND d.status < 50 AND d.status > 10 AND d.moduleId IN"
						+ MODULE_IDS, userId);
	}
	
	//获得所有的待审事宜，ios推送需要
	@Override
	@SuppressWarnings("unchecked")
	public List<VwApproveDocumentsNotification> getAllApprovingMatters(){
		return (List<VwApproveDocumentsNotification>) getHibernateTemplate().find("from VwApproveDocumentsNotification d  where not exists (select 1 from OaNotificationA4sApproval b  where  b.approvalPointId = d.oadpId) AND d.moduleId IN"+MODULE_IDS);
	}

	// 老的已审单据查询
	@SuppressWarnings({ "unchecked" })
	public List<ApproveDocuments<?>> findApprovedMattersByProperties(String userId, String documentNo,
			String submitUserName, Date beginTieme, Date endTime, int pageNo, int pageSize) {

		String sql = "document_id in(select p.document_id from office_approve_documents_points p where p.approver_id = ?) and module_id in "
				+ MODULE_IDS;
		DetachedCriteria dc = DetachedCriteria.forClass(VwOfficeApproveDocuments.class);
		if (documentNo != null && documentNo.length() > 0)
			dc.add(Restrictions.like("documentNo", "%" + documentNo + "%"));
		if (submitUserName != null && submitUserName.length() > 0)
			dc.add(Restrictions.eq("userName", submitUserName));
		if (beginTieme != null)
			dc.add(Restrictions.ge("submitTime", beginTieme));
		if (endTime != null)
			dc.add(Restrictions.le("submitTime", endTime));
		dc.add(Restrictions.sqlRestriction(sql, userId, new StringType()));
		return (List<ApproveDocuments<?>>) getHibernateTemplate().findByCriteria(dc, pageNo * pageSize, pageSize);
	}

	// 新的已审单据查询
	@Override
	@SuppressWarnings("unchecked")
	public List<VwOfficeApproveDocuments> findApprovedMatters(String userId, String documentNo, String submitUserName, String moduleId,
			Date beginTime, Date endTime, int pageNo, int pageSize) {

		StringBuffer whereSql = buildConditionAppendStringBuffer("a", null, documentNo, submitUserName, moduleId, beginTime, endTime);

		String orderBySql = " ORDER BY tmp.last_approve_time DESC ";
		Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery("findApprovedMatters");
		String queryString = namedQuery.getQueryString();
		String sql = queryString + " WHERE 1 = 1 " + whereSql.toString() + orderBySql;
//		logger.debug("审批-已审事宜 :approverId：" + userId + "\r\nsql:" + sql);

		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addEntity(VwOfficeApproveDocuments.class).setParameter("approverId", userId, new StringType());
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<VwOfficeApproveDocuments> list = query.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.sf_soft.office.approval.dao.ApproveDocumentDao#getApproveDocumentsByNo
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public ApproveDocuments getApproveDocumentsByNo(String doucumentNo) {
		List<ApproveDocuments<?>> list = (List<ApproveDocuments<?>>) getHibernateTemplate().find(
				"FROM ApproveDocuments d WHERE d.documentNo = ? ", doucumentNo);
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	// 我的已审
	// @SuppressWarnings("unchecked")
	// @Override
	// public List<ApproveDocuments<?>> getMyApprovedMatters(String userId,
	// String documentNo, String submitUserName,
	// Date beginTieme, Date endTime, int pageNo, int pageSize) {
	// // 同意(50)或不同意(5)
	// String sql =
	// "user_id=? AND (status = 50 OR status = 5) AND module_id in "
	// + MODULE_IDS;
	// DetachedCriteria dc =
	// DetachedCriteria.forClass(VwOfficeApproveDocuments.class);
	// if (documentNo != null && documentNo.length() > 0)
	// dc.add(Restrictions.like("documentNo", "%" + documentNo + "%"));
	// if (submitUserName != null && submitUserName.length() > 0)
	// dc.add(Restrictions.eq("userName", submitUserName));
	// if (beginTieme != null)
	// dc.add(Restrictions.ge("submitTime", beginTieme));
	// if (endTime != null)
	// dc.add(Restrictions.le("submitTime", endTime));
	// dc.add(Restrictions.sqlRestriction(sql, userId, new StringType()));
	// return (List<ApproveDocuments<?>>)
	// getHibernateTemplate().findByCriteria(dc, pageNo * pageSize, pageSize);
	// }

	// 我的已审
	@SuppressWarnings("unchecked")
	@Override
	public List<VwOfficeApproveDocuments> getMyApprovedMatters(String userId, String documentNo, String submitUserName, String moduleId,
			Date beginTime, Date endTime, int pageNo, int pageSize) {
		// 同意(50)或不同意(5)
		StringBuffer whereSql = buildConditionAppendStringBuffer("a", userId, documentNo, submitUserName, moduleId, beginTime, endTime);

		String orderBySql = " ORDER BY tmp.last_approve_time DESC ";
		Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery("findMyApprovedMatters");
		String queryString = namedQuery.getQueryString();
		String sql = queryString + whereSql.toString() + orderBySql;
		logger.debug("审批-我的已审 sql:" + sql);
		
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addEntity(VwOfficeApproveDocuments.class);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		List<VwOfficeApproveDocuments> list = query.list();
		return list;
	}

	@Override
	public List<Map<String,Object>> getApproveModuleList(){
		String sql = "select module_id as id, item_text as title from  sys_menu where module_id IN " + MODULE_IDS;
		return getMapBySQL(sql, null);
	}

	//根据moduleId查找模块名称
	@Override
	public String getModuleNameByModuleId(String moduleId) {
		String sql = "select item_text from  sys_menu where module_id =:moduleId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moduleId", moduleId);
		List<Map<String, Object>> menus = getMapBySQL(sql, params);
		if(menus!=null && menus.size()>0){
			return menus.get(0).get("item_text").toString();
		}
		
		return null;
	}

	private String buildModuleCondition(String moduleIdKey, String moduleId){
		if (MODULE_IDS != null && MODULE_IDS.length() > 0) {
			if(moduleId != null && moduleId.length()>0 && MODULE_IDS.indexOf("'"+moduleId+"'") > 0){
				return " AND " + moduleIdKey + " = '" + moduleId + "'";
			}else{
				return " AND " + moduleIdKey + " IN " + MODULE_IDS;
			}
		} else if(moduleId != null && moduleId.length() > 0){
			return " AND " + moduleIdKey + " = '" + moduleId + "'";
		}else{
			return " ";
		}
	}

	private StringBuffer buildConditionAppendStringBuffer(String prefix, String userId, String documentNo, String submitUserName, String moduleId,
														  Date beginTime, Date endTime){
		if(prefix == null || prefix.length() == 0){
			prefix = "";
		}else{
			prefix = prefix + ".";
		}
		StringBuffer whereSql = new StringBuffer();

		if(userId != null && userId.length() > 0) {
			whereSql.append(" AND " + prefix+ "user_id = '" + userId + "' ");
		}

		whereSql.append(buildModuleCondition("module_id", moduleId));

		if (documentNo != null && documentNo.length() > 0) {
			whereSql.append(" AND " + prefix+ "document_no LIKE '%" + documentNo + "%' ");
		}

		if (submitUserName != null && submitUserName.length() > 0) {
			whereSql.append(" AND " + prefix+ "user_name LIKE '%" + submitUserName + "%' ");
		}

		if (beginTime != null) {
			whereSql.append(" AND " + prefix+ "DATEDIFF(DAY, '" + spf.format(beginTime) + "', " + prefix + "submit_time) >= 0  ");
		}

		if (endTime != null) {
			whereSql.append(" AND " + prefix+ "DATEDIFF(DAY, '" + spf.format(endTime) + "', " + prefix + "submit_time) <= 0  ");
		}

		return whereSql;
	}

	private StringBuffer buildHibernateConditionStringBuffer(String prefix, String documentNo, String moduleId){
		if(prefix == null || prefix.length() == 0){
			prefix = "";
		}else{
			prefix = prefix + ".";
		}
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");

		whereSql.append(buildModuleCondition("moduleId", moduleId));

		if (documentNo != null && documentNo.length() > 0) {
			whereSql.append(" AND " + prefix+ "documentNo LIKE '%" + documentNo + "%' ");
		}

		return whereSql;
	}

}
