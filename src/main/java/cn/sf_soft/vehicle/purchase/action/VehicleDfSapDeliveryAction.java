package cn.sf_soft.vehicle.purchase.action;

import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.vehicle.purchase.service.VehicleDfSapDeliveryService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/2 16:41
 * @Description: 车辆采购跟踪
 */
public class VehicleDfSapDeliveryAction extends BaseAction {

    @Autowired
    private VehicleDfSapDeliveryService vehicleDfSapDeliveryService;

    private String jsonData;

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    /**
     * 保存车辆采购合同
     * @return
     */
    @Access(pass = true)
    public String save() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = vehicleDfSapDeliveryService.saveDelivery(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }
}
