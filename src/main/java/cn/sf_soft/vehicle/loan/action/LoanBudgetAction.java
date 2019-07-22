package cn.sf_soft.vehicle.loan.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.vehicle.loan.service.impl.LoanBudgetService;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消贷费用预算
 * @author caigx
 */
public class LoanBudgetAction extends BaseAction {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanBudgetAction.class);

    @Autowired
    private LoanBudgetService loanBudgetService;

    private String jsonData;

    private String documentNo;

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Access(pass = true)
    public String saveLoanBudget() {
        logger.debug(String.format("保存消贷预算单,OS_TYPE:%s,接收报文：%s", HttpSessionStore.getSessionOs(), jsonData));
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = loanBudgetService.saveLoanBudget(jsonObject);
        setResponseCommonData(rtnData);
        return SUCCESS;
    }

    /**
     * 获得合同明细
     *
     * @return
     */
    @Access(pass = true)
    public String getLoanBudgetDetail() {
        setResponseCommonData(loanBudgetService.convertReturnData(documentNo));
        return SUCCESS;
    }


    /**
     * 计算月供
     * @return
     */
    @Access(pass = true)
    public String calculationMonthPay(){
        Map<String, Object> map = getPostJson(jsonData);
        setResponseCommonData(loanBudgetService.calculationMonthPay(map));
        return SUCCESS;
    }


    /**
     * 初始值
     * @return
     */
    @Access(pass = true)
    public String getInitData(){
        setResponseCommonData(loanBudgetService.getInitData());
        return SUCCESS;
    }


    private Map<String, Object> getPostJson(String jsonString) {
        try {
            HashMap json_map = gson.fromJson(jsonData, HashMap.class);
            return json_map;
        } catch (Exception e) {
            throw new ServiceException("提交数据不合法");
        }
    }
}
