package cn.sf_soft.vehicle.purchase.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.vehicle.purchase.service.VehiclePurchaseOrderService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 采购订单的Action
 * @author caigx
 */
public class PurchaseOrderAction extends BaseAction {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseOrderAction.class);

    private String jsonData;

    private String documentNo;

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Autowired
    private VehiclePurchaseOrderService vehiclePurchaseOrderService;

    /**
     * 保存采购订单
     * @return
     */
    @Access(pass = true)
    public String savePurchaseOrder() {
        logger.debug(String.format("采购订单保存 OS_TYPE:%s,接收报文：%s", HttpSessionStore.getSessionOs(), jsonData));
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Map<String, Object>>> rtnData = vehiclePurchaseOrderService.savePurchaseOrder(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }

    /**
     * 终止数量
     * @return
     */
    @Access(pass = true)
    public String abort(){
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        String documentNo = GsonUtil.getAsString(jsonObject, "documentNo");
        if(StringUtils.isEmpty(documentNo)){
            throw new ServiceException("采购订单号不能为空");
        }
        String modifyTime = GsonUtil.getAsString(jsonObject, "modifyTime");
        if(StringUtils.isEmpty(modifyTime)){
            throw new ServiceException("modifyTime不能为空");
        }
        Number abortQuantity = GsonUtil.getAsNumber(jsonObject, "abortQuantity");
        if(null == abortQuantity){
            throw new ServiceException("终止数量不能为空");
        }

        Map<String,List<Map<String, Object>>> rtnData = vehiclePurchaseOrderService.abort(documentNo, modifyTime, abortQuantity.intValue());
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     *采购订单参考数据--审批时显示
     * @return
     */
    @Access(pass = true)
    public String getOrderReference(){
        Map<String, List<Map<String, Object>>> rtnData = vehiclePurchaseOrderService.getOrderReference(documentNo);
        setResponseData(rtnData);
        return SUCCESS;
    }

}
