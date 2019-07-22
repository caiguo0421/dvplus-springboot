package cn.sf_soft.vehicle.purchase.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.vehicle.purchase.service.VehiclePurchaseContractService;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/1 15:42
 * @Description: 车辆采购合同
 */
public class PurchaseContractAction extends BaseAction {

    static Logger logger = LoggerFactory.getLogger(PurchaseOrderAction.class);

    @Autowired
    private VehiclePurchaseContractService vehiclePurchaseContractService;

    private String jsonData;
//    private String contractNo;
//    private String modifyTime;

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
//    public void setContractNo(String contractNo){
//        this.contractNo = contractNo;
//    }
//    public void setModifyTime(String modifyTime){ this.modifyTime = modifyTime;}
    /**
     * 保存车辆采购合同
     * @return
     */
    @Access(pass = true)
    public String save() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = vehiclePurchaseContractService.savePurchaseContract(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }

    /**
     * 车辆采购合同确认
     * @return
     */
    @Access(pass = true)
    public String confirm(){
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String contractId = GsonUtil.getAsString(jsonObject, "contractId");
        if(StringUtils.isEmpty(contractId)){
            throw new ServiceException("车辆采购合同标识不能为空");
        }
        String modifyTime = GsonUtil.getAsString(jsonObject, "modifyTime");
        if(StringUtils.isEmpty(modifyTime)){
            throw new ServiceException("modifyTime不能为空");
        }
        Map<String, List<Object>> rtnData = vehiclePurchaseContractService.confirm(contractId, modifyTime);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 车辆采购合同反确认
     * @return
     */
    @Access(pass = true)
    public String unConfirm(){
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String contractId = GsonUtil.getAsString(jsonObject, "contractId");
        if(StringUtils.isEmpty(contractId)){
            throw new ServiceException("车辆采购合同标识不能为空");
        }
        String modifyTime = GsonUtil.getAsString(jsonObject, "modifyTime");
        if(StringUtils.isEmpty(modifyTime)){
            throw new ServiceException("modifyTime不能为空");
        }
        Map<String, List<Object>> rtnData = vehiclePurchaseContractService.unConfirm(contractId, modifyTime);
        setResponseData(rtnData);
        return SUCCESS;
    }

    /**
     * 车辆采购合同作废
     * @return
     */
    @Access(pass = true)
    public String forbid(){
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String contractId = GsonUtil.getAsString(jsonObject, "contractId");
        if(StringUtils.isEmpty(contractId)){
            throw new ServiceException("车辆采购合同标识不能为空");
        }
        String modifyTime = GsonUtil.getAsString(jsonObject, "modifyTime");
        if(StringUtils.isEmpty(modifyTime)){
            throw new ServiceException("modifyTime不能为空");
        }
        Map<String, List<Object>> rtnData = vehiclePurchaseContractService.forbid(contractId, modifyTime);
        setResponseData(rtnData);
        return SUCCESS;
    }

}
