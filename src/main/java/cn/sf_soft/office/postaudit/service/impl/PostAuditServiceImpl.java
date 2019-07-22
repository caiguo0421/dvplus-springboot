package cn.sf_soft.office.postaudit.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.dao.BaseOthersDao;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.office.postaudit.model.OfficeAuditEntries;
import cn.sf_soft.office.postaudit.model.PostAuditInitData;
import cn.sf_soft.office.postaudit.model.PostAuditSearchCriteria;
import cn.sf_soft.office.postaudit.service.PostAuditService;
import cn.sf_soft.user.model.SysUsers;
/**
 * 事后审核
 * @author king
 * @create 2013-9-25上午11:24:59
 */
@Service("postAuditService")
public class PostAuditServiceImpl implements PostAuditService {
	@Autowired
	@Qualifier("baseDao")
	private BaseDao dao;
	@Autowired
	@Qualifier("baseOtherDao")
	private BaseOthersDao baseOthersDao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public void setBaseOthersDao(BaseOthersDao baseOthersDao) {
		this.baseOthersDao = baseOthersDao;
	}

	@SuppressWarnings("unchecked")
	public List<OfficeAuditEntries> getPendingAuditEntriesByProperties(
			int pageNo, int pageSize, PostAuditSearchCriteria searchCriteria) {
		DetachedCriteria dc = createCriteria(searchCriteria);
		dc.add(Restrictions.isNull("approver"));
		return dao.findByCriteria(dc, pageNo, pageSize);
	}
	
	/**
	 * 根据条件分页查询事后审核<b>已审</b>单据
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeAuditEntries> getApprovedEntryByProperties(int pageNo,
			int pageSize, PostAuditSearchCriteria searchCriteria) {
		DetachedCriteria dc = createCriteria(searchCriteria);
		dc.add(Restrictions.isNotNull("approver"));
		return dao.findByCriteria(dc, pageNo, pageSize);
	}

	private DetachedCriteria createCriteria(
			PostAuditSearchCriteria searchCriteria) {
		DetachedCriteria dc = DetachedCriteria
				.forClass(OfficeAuditEntries.class);
		String documentNo = searchCriteria.getDocumentNo();
		String operator = searchCriteria.getOperator();
		Date beginDate = null;
		Date endDate = null;
		try {
			if(searchCriteria.getBeginTime() != null && searchCriteria.getBeginTime().length() > 0)
				beginDate = TimestampUitls.formatDate2(searchCriteria.getBeginTime());
			if(searchCriteria.getEndTime() != null && searchCriteria.getEndTime().length() > 0)
				endDate = TimestampUitls.formatDate2(searchCriteria.getEndTime());
		} catch (ParseException e) {
			throw new ServiceException("查询时间参数错误");
		}
		if (documentNo != null && documentNo.length() > 0)
			dc.add(Restrictions.like("documentNo", "%" + documentNo + "%"));
		if (operator != null && operator.length() > 0)
			dc.add(Restrictions.like("operator", operator + "%"));
		if (beginDate != null)
			dc.add(Restrictions.ge("operateTime", beginDate));
		if (endDate != null)
			dc.add(Restrictions.le("operateTime", endDate));

		dc.add(Restrictions.in("stationId", searchCriteria.getStationIds()));
		return dc;
	}

	/**
	 * 处理单据
	 */
	public OfficeAuditEntries handleAuditEntries(String entryId, String handleResult,
			String handleOpinion, SysUsers user) {
		OfficeAuditEntries entries = (OfficeAuditEntries) dao.load(
				OfficeAuditEntries.class, entryId);
		if (entries == null) {
			throw new ServiceException("单据未找到");
		}
		if (entries.getApprover() != null && entries.getApprover().length() > 0) {
			throw new ServiceException("已审批的单据不能处理");
		}
		if(entries.getHandler1() != null){
			//单据已经处理过
			return entries;
		}
		List<String> result = baseOthersDao.getDataByTypeNo("OFFICE_AUDIT_RESULT");
		if (result != null && result.contains(handleResult)) {
			if(handleOpinion == null)
				handleOpinion = "";
			entries.setHandleOpinion(handleOpinion + "(来自移动终端)");
			entries.setHandleResult(handleResult);
			Timestamp ts = new Timestamp(new Date().getTime());
			String userName = user.getUnitName() + "(" + user.getUserNo() + ")";
			entries.setHandleTime(ts);
			entries.setHandler1(userName);
			dao.update(entries);
		} else {
			throw new ServiceException("处理结果无效");
		}
		return entries;
	}

	/**
	 * 审批单据
	 */
	public OfficeAuditEntries approveAuditEntries(String entryId, String handleResult, String handleOpinion, String approveOpinion, SysUsers user) {
		OfficeAuditEntries entries = null;
		if(handleResult != null){
			//如果填了处理结果，(一键审批)
			entries = handleAuditEntries(entryId, handleResult, handleOpinion, user);
		}else{
			entries = (OfficeAuditEntries) dao.load(OfficeAuditEntries.class, entryId);
			if(entries.getHandler1() == null || entries.getHandler1().length() == 0){
				throw new ServiceException("未处理的单据不能审批");
			}
		}
		if (entries == null) {
			throw new ServiceException("单据未找到");
		}
		if(approveOpinion == null){
			approveOpinion = "";
		}
		entries.setApproveOpinion(approveOpinion + "(来自移动终端)");
		entries.setApprover(user.getUnitName() + "(" + user.getUserNo() + ")");
		entries.setApproveTime(new Timestamp(new Date().getTime()));
		dao.update(entries);
		return entries;
	}

	public PostAuditInitData getInitData() {
		List<String> handleResult = baseOthersDao.getDataByTypeNo("OFFICE_AUDIT_RESULT");
		PostAuditInitData initData = new PostAuditInitData();
		initData.setHandleResult(handleResult);
		return initData;
	}


}
