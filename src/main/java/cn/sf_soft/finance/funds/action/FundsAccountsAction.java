package cn.sf_soft.finance.funds.action;

import java.util.List;
import java.util.Map;


import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.annotation.Modules;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.finance.funds.model.FinanceDocument;
import cn.sf_soft.finance.funds.model.FundsAccountSearchCriteriaDate;
import cn.sf_soft.finance.funds.model.FundsAccounts;
import cn.sf_soft.finance.funds.model.FundsAccountsSearchCriteria;
import cn.sf_soft.finance.funds.model.TotalCountOfFinance;
import cn.sf_soft.finance.funds.model.TotalCountOfFundsAccounts;
import cn.sf_soft.finance.funds.service.FundsAccountService;
import cn.sf_soft.user.model.SysUsers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
/**
 * 
 * @Title: 资金查询
 * @date 2013-9-23 上午09:40:22 
 * @author cw
 */
@ModuleAccess(moduleId=Modules.Finance.FUNDS_QUERY)
public class FundsAccountsAction  extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -308608828496023534L;
	private FundsAccountService fundsAccountService;
	private FundsAccountsSearchCriteria searchCriteria;
	 static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FundsAccountsAction.class);
	private String objectName;
	private String businessType;
	 
	public void setFundsAccountService(FundsAccountService fundsAccountService) {
		this.fundsAccountService = fundsAccountService;
	}
	public void setStationIds(List<String> stationIds) {
		this.stationIds = stationIds;
		if(searchCriteria != null){
			searchCriteria.setStationIds(stationIds);
		}
	}
	public void setSearchCriteria(String searchCriteria) {
		try {
			this.searchCriteria = gson.fromJson(searchCriteria, FundsAccountsSearchCriteria.class);
			if(stationIds != null){
				this.searchCriteria.setStationIds(stationIds);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addFieldError("serachCriteria", "搜索条件数据格式不合法");
		}
		
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取资金查询
	 * @return
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_ACCOUNTS)
	public String getFundsAccounts(){
		if(pageNo == 0){
			pageNo = 1;
		}
		SysUsers user= (SysUsers)ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		searchCriteria.setUserId(user.getUserId());
		logger.debug(user.getUserId());
		logger.debug(searchCriteria.getAccountName()+"-->"+searchCriteria.getAccountNo()+"-->"+searchCriteria.getAccountTypeMeaning()+"-->"+searchCriteria.getBeginTime()+"-->"+searchCriteria.getEndTime());
		
		List<FundsAccounts> list = fundsAccountService.getFundsAccountsData(searchCriteria,pageNo,pageSize);
		ResponseMessage<List<FundsAccounts>> result = new ResponseMessage<List<FundsAccounts>>(list);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setTotalSize(fundsAccountService.getFundsAccountsCount(searchCriteria));
		setResponseMessageData(result);
		return SUCCESS;
	}
	
	/**
	 * 获取出入明细
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_ACCOUNTS)
	public String getFinanceDocumentEntries(){
		if(pageNo == 0){
			pageNo = 1;
		}
		SysUsers user = (SysUsers)ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		searchCriteria.setUserId(user.getUserId());
		List<FinanceDocument> list = fundsAccountService.getFinanceDocumentEntries(searchCriteria, pageNo, pageSize);
		ResponseMessage<List<FinanceDocument>> result = new ResponseMessage<List<FinanceDocument>>(list);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setTotalSize(fundsAccountService.getFinanceDocumentEntriesCount(searchCriteria));
		setResponseMessageData(result);
		return SUCCESS;
	} 
	/**
	 * 获取资金账户查询总计
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_ACCOUNTS)
	public String getTotalCountOfFundsAccounts(){
		SysUsers user = (SysUsers)ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		searchCriteria.setUserId(user.getUserId());
		TotalCountOfFundsAccounts list = fundsAccountService.getTotalCountOfFundsAccounts(searchCriteria);
		logger.debug(list.getTotalCreditAmount()+"--->>");
		setResponseData(list);
		return SUCCESS;
	} 
	/**
	 * 获取出入明细总计
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_ACCOUNTS)
	public String getTotalCountOfFinance(){
		SysUsers user = (SysUsers)ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
		searchCriteria.setUserId(user.getUserId());
		TotalCountOfFinance list = fundsAccountService.getTotalCountOfFinance(searchCriteria);
		logger.debug(list.getTotalCreditAmount()+"--->>");
		setResponseData(list);
		return SUCCESS;
	} 
	/**
	 * 初始化查询条件
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_ACCOUNTS)
	public String getFundsAccountSearchCriteriaDate(){
		FundsAccountSearchCriteriaDate list = new FundsAccountSearchCriteriaDate();
		String[] fundsAccountType = fundsAccountService.getFundsAccountSearchCriteriaDate();
		list.setFundsAccountType(fundsAccountType);
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 查询应付款统计
	 * @return
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_PAYMENT)
	public String getPayable(){
		List<Map<String, Object>> result = fundsAccountService.getPayable(this.stationIds, this.objectName);
		setResponseData(result);
		return SUCCESS;
	}

	/**
	 * 查询应收款统计
	 * @return
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_PAYMENT)
	public String getReceivable(){
		List<Map<String, Object>> result = fundsAccountService.getReceivable(this.stationIds, this.objectName);
		setResponseData(result);
		return SUCCESS;
	}

	/**
	 * 往来走势对比
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_PAYMENT)
	public String getReceivableTrend(){
		List<Map<String, Object>> result = fundsAccountService.getReceivableTrend(this.stationIds, this.objectName, this.businessType);
		setResponseData(result);
		return SUCCESS;
	}

	/**
	 * 往来走势对比
	 */
	@Access(needPopedom=AccessPopedom.FinanceDocument.FUNDS_PAYMENT)
	public String getPayableTrend(){
		List<Map<String, Object>> result = fundsAccountService.getPayableTrend(this.stationIds, this.objectName, this.businessType);
		setResponseData(result);
		return SUCCESS;
	}
}
