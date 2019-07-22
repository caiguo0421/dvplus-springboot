package cn.sf_soft.basedata.action;

import cn.sf_soft.basedata.service.SysBaseDataService;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.util.HttpSessionStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: chenbiao
 * @Date: 2018/9/5 12:18
 * @Description:
 */
public class BaseOtherAction extends BaseAction {

    @Autowired
    private SysBaseDataService baseDataService;

    private String typeNo;
    public void setTypeNo(String typeNo){
        this.typeNo = typeNo;
    }


    @Access(pass = true)
    public String getBaseOthers(){
        String stationId = HttpSessionStore.getSessionUser().getDefaulStationId();
        setResponseData(baseDataService.getBaseOthers(typeNo, stationId));
        return SUCCESS;
    }
}
