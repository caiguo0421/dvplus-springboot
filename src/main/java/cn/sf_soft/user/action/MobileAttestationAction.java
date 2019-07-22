package cn.sf_soft.user.action;


import cn.sf_soft.common.Config;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.user.model.ApplyForCertificationData;
import cn.sf_soft.user.model.MobileAttestationInitData;
import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.MobileAttestationService;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cw
 * @modify liujin 2013-9-13下午3:49:39
 */
public class MobileAttestationAction extends BaseAction {

    private static final long serialVersionUID = 8882401324483525819L;
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MobileAttestationAction.class);
    private MobileAttestationService mobileAttestationService;


    @Autowired
    private Config config;

    private String mobileKey;
    private ApplyForCertificationData certificationData;

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public void setCertificationData(String certificationData) {
        try {
            this.certificationData = gson.fromJson(certificationData, ApplyForCertificationData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            addActionError("参数错误");
        }
    }

    public void setMobileAttestationService(MobileAttestationService mobileAttestationService) {
        this.mobileAttestationService = mobileAttestationService;
    }

    /**
     * 获取初始化数据
     *
     * @return
     */
    public String init() {
        MobileAttestationInitData mobiledata = new MobileAttestationInitData();
        Map<String, String> stationIds = mobileAttestationService.getSysStations();
        mobiledata.setStationIds(stationIds);
        SysComputerKey computerKey = null;
        //当isMobileAuthOn = false, 不需要手机认证时，新建 SysComputerKey，approveStatus =1  --caigx 20170106
        if (!Tools.toBoolean(config.isMobileAuthOn())) {
            computerKey = new SysComputerKey();
            computerKey.setApproveStatus((short) 1);
        } else {
            List<SysComputerKey> computerKeyList = mobileAttestationService.getComputerKeyByKey(mobileKey);
            if (computerKeyList.size() > 0) {
                computerKey = computerKeyList.get(0);
            }
        }

        mobiledata.setMobileKey(computerKey);
        setResponseData(mobiledata);
        return SUCCESS;
    }

    /**
     * 检查computerKey是否已通过
     *
     * @return
     */
    public String validateMobileKey() {
        //isAudited ：mobileKey是否审核
        boolean isAudited = false;
        SysComputerKey computerKey = null;
        if (!Tools.toBoolean(config.isMobileAuthOn())) {
            isAudited = true;
        } else {
            List<SysComputerKey> computerKeyList = mobileAttestationService.getComputerKeyByKey(mobileKey);
            if (computerKeyList != null && computerKeyList.size() > 0) {
                computerKey = computerKeyList.get(0);
                if (Tools.toShort(computerKey.getApproveStatus()) == (short) 1) {
                    isAudited = true;
                }
            }
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("isAudited", isAudited);
        result.put("sysComputerKey", computerKey);

        logger.debug(String.format("validateMobileKey,参数,mobileKey:%s,isAudited:%s,返回值：%s", mobileKey, isAudited, gson.toJson(result)));
        setResponseData(result);
        return SUCCESS;
    }


    /**
     * 添加一条手机认证信息
     *
     * @return
     */
    public String addMobileAttestationAction() {
        ResponseMessage<String> result = new ResponseMessage<String>();
        if (certificationData == null || certificationData.getApplyUser() == null
                || certificationData.getMobileKey() == null || certificationData.getApplyDept() == null) {
            result.setRet(ResponseMessage.RET_PARAM_ERR);
            result.setMsg("参数不合法");
            return SUCCESS;
        }
        List<SysUsers> user = mobileAttestationService.getUser(certificationData.getApplyUser());
        if (user == null || user.size() == 0) {
            result.setRet(ResponseMessage.RET_PARAM_ERR);
            result.setMsg("该用户不存在");

        } else {
            List<SysComputerKey> computerKey = mobileAttestationService.getComputerKeyByKey(certificationData.getMobileKey());
            if (computerKey.size() == 0) {
                SysComputerKey com = new SysComputerKey();
                com.setCpkeyId(UUID.randomUUID().toString());
                com.setComputerKey(certificationData.getMobileKey());
                com.setComputerType("手机");
                com.setStationId(certificationData.getStationId());
                com.setCreator(certificationData.getApplyUser());
                com.setUserName(certificationData.getApplyUser());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                Date dt = new Date();
                com.setCreateTime(Timestamp.valueOf(sdf.format(dt)));
                com.setUnitName(certificationData.getApplyDept());
                com.setRemark(certificationData.getApplyRemark());
                com.setApproveStatus((short) 0);//已提交

                mobileAttestationService.saveMobileAttestation(com);

                result.setMsg("已提交手机认证信息");
            } else if (computerKey.get(0).getApproveStatus() != 1 && computerKey.get(0).getApproveStatus() != 0) {
                //用户已经提交了认证，但未同意
                SysComputerKey ck = computerKey.get(0);
                ck.setStationId(certificationData.getStationId());
                ck.setCreator(certificationData.getApplyUser());
                ck.setUserName(certificationData.getApplyUser());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                Date dt = new Date();
                ck.setCreateTime(Timestamp.valueOf(sdf.format(dt)));
                ck.setUnitName(certificationData.getApplyDept());
                ck.setRemark(certificationData.getApplyRemark());
                ck.setApproveStatus((short) 0);//已提交
                mobileAttestationService.update(ck);
            }
        }
        setResponseMessageData(result);
        return SUCCESS;
    }

}
