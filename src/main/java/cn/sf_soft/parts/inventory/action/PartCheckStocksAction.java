package cn.sf_soft.parts.inventory.action;

import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.annotation.Modules;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.parts.inventory.model.CheckRange;
import cn.sf_soft.parts.inventory.model.PartCheckStocks;
import cn.sf_soft.parts.inventory.model.PartCheckStocksDetail;
import cn.sf_soft.parts.inventory.service.impl.PartCheckStocksService;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ModuleAccess(moduleId = Modules.Parts.PART_STOCKING)
public class PartCheckStocksAction extends BaseAction {

	private PartCheckStocksService service;
	private String checkRange;

	private String orderBy;

	private JsonObject condition;

	private String documentNo;

	public void setService(PartCheckStocksService service) {
		this.service = service;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setCondition(String conditionStr) {
		try {
			condition = gson.fromJson(conditionStr, JsonObject.class);
		}catch (Exception e){
			throw new ServiceException("提交数据不合法");
		}
	}

	public void setCheckRange(String checkRange) {
		this.checkRange = checkRange;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	private String parameter;
	public void setParameter(String parameter){
		this.parameter = parameter;
	}

	/**
	 * 获取盘存调整单
	 * @return
	 */
	@Access(pass = true)
	public String getPartCheckStockList() {
		PageModel<PartCheckStocks> list = service.getPartCheckStock(this.stationIds, condition, orderBy, pageNo, pageSize);
		setResponseCommonData(list);
		return SUCCESS;
	}

	/**
	 * 获取盘点单信息，不包含明细
	 * @return
	 */
	@Access(pass = true)
	public String getPartCheckStockById(){
		PartCheckStocks partCheckStocks = service.getPartCheckStockById(this.documentNo);
		setResponseData(partCheckStocks);
		return SUCCESS;
	}

	/**
	 * 获取盘存单明细
	 * @return
	 */
    @Access(pass = true)
    public String getPartCheckStockDetail() {
        List<PartCheckStocksDetail> list = service
                .getPartCheckStockDetail(documentNo, condition, orderBy);
        setResponseData(list);
        return SUCCESS;
    }

	/**
	 * 盘点范围-初始化数据
	 * @return
	 */
	@Access(pass = true)
	public String getRangeInitData(){
		Map<String,Object> result = service.getRangeInitData(stationIds);
		setResponseData(result);
		return SUCCESS;
	}


	/**
	 * 生成盘存单
	 * @return
	 */
	@Access(pass = true)
	public String genPartCheckStocks(){
		CheckRange range = gson.fromJson(checkRange, CheckRange.class);
		setResponseData(service.genPartCheckStocks(range, stationIds));
		return SUCCESS;
	}

	/**
	 * 盘点
	 */
	@Access(pass = true)
	public String checkPartStock(){
		PartCheckStocks partCheckStock = gson.fromJson(this.parameter, PartCheckStocks.class);
		ResponseMessage<List<PartCheckStocksDetail>> responseMessage = service.checkPartStock(partCheckStock);
		setResponseMessageData(responseMessage);
		return SUCCESS;
	}

	/**
	 * 提交盘存单
	 * @return
	 */
	@Access(pass = true)
	public String submit() {
		Map<String, String> param = gson.fromJson(this.parameter, HashMap.class);
		String pcsNo = param.get("documentNo");
		String unitId = param.get("unitId");
		String remark = param.get("remark");
		PartCheckStocks partCheckStock = service.submit(pcsNo, unitId, remark);
		setResponseData(partCheckStock);
		return SUCCESS;
	}

}
