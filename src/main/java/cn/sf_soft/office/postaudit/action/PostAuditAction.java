package cn.sf_soft.office.postaudit.action;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.office.postaudit.model.OfficeAuditEntries;
import cn.sf_soft.office.postaudit.model.PostAuditInitData;
import cn.sf_soft.office.postaudit.model.PostAuditSearchCriteria;
import cn.sf_soft.office.postaudit.service.PostAuditService;
import cn.sf_soft.user.model.SysUsers;

/**
 * 事后审核
 * 
 * @author king
 * @create 2013-9-18下午4:43:00
 */
public class PostAuditAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 526781071083724498L;
	private PostAuditService postAuditService;
	private String entryId;
	private String handleResult;
	private String handleOpinion;
	private String approveOpinion;

	public void setPostAuditService(PostAuditService postAuditService) {
		this.postAuditService = postAuditService;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public void setHandleOpinion(String handleOpinion) {
		this.handleOpinion = handleOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	//得到事后审核 待审事项
	@Access(pass=true)
	public String getPendingAuditEntries() {
		PostAuditSearchCriteria criteria = null;
		try {
			criteria = initCriteria();
		} catch (Exception e) {
			e.printStackTrace();
			return showErrorMsg("参数错误:" + searchCriteria);
		}
		List<OfficeAuditEntries> list = postAuditService.getPendingAuditEntriesByProperties(pageNo, pageSize, criteria);
		setResponseData(list);
		return SUCCESS;
	}
	
	//得到事后审核 已审事项
	@Access(pass=true)
	public String getApprovedAuditEntries(){
		PostAuditSearchCriteria criteria = null;
		try {
			criteria = initCriteria();
		} catch (Exception e) {
			e.printStackTrace();
			return showErrorMsg("参数错误:" + searchCriteria);
		}
		List<OfficeAuditEntries> list = postAuditService.getApprovedEntryByProperties(pageNo, pageSize, criteria);
		setResponseData(list);
		return SUCCESS;
	}
	
	//得到初始化数据
	@Access(pass=true)
	public String getInitData(){
		PostAuditInitData initData = postAuditService.getInitData();
		setResponseData(initData);
		return SUCCESS;
	}
	
	//处理单据
	@Access(needPopedom=AccessPopedom.Offices.POST_AUDITS_HANDLE)
	public String handleEntry(){
		SysUsers curUser = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		OfficeAuditEntries entry = postAuditService.handleAuditEntries(entryId, handleResult, handleOpinion, curUser);
		setResponseData(entry);
		return SUCCESS;
	}
	
	//审批单据
	@Access(needPopedom=AccessPopedom.Offices.POST_AUDIT_APPROVE)
	public String approveEntry(){
		SysUsers curUser = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		OfficeAuditEntries entry = postAuditService.approveAuditEntries(entryId, handleResult,handleOpinion, approveOpinion, curUser);
		setResponseData(entry);
		return SUCCESS;
	}
	
	//初始化查询条件
	private PostAuditSearchCriteria initCriteria(){
		PostAuditSearchCriteria criteria = gson.fromJson(searchCriteria, PostAuditSearchCriteria.class);
		if(criteria == null)
			criteria = new PostAuditSearchCriteria();
		criteria.setStationIds(stationIds);
		return criteria;
	}
}
