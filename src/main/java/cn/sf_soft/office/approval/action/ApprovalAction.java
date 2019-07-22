package cn.sf_soft.office.approval.action;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.common.util.Constant.OSType;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocumentSearchCriteria;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.office.approval.service.impl.BaseApproveProcess;
import cn.sf_soft.user.model.SysUsers;
import com.google.gson.JsonSyntaxException;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 审批流程
 *
 * @author king
 * @create 2013-9-29上午10:16:54
 */
public class ApprovalAction extends BaseAction {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    /**
     *
     */
    private static final long serialVersionUID = -8198198062561424625L;

    @Resource(name = "approveManager")
    private Map<String, BaseApproveProcess> approveManager;

    @Autowired
    private SysLogsDao sysLogsDao;


    private boolean agree;
    private String documentId;
    private String comment;
    private String moduleId;
    private String documentNo;
    private ApprovalService approvalService;
    private String modifyTime;
    private String filename;
    private String filepath;
    private String pics; //审批点 图片
    private String fileUrls; //审批点 附件

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public void setApprovalService(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    // 审批单据 移动端
    @Access(pass = true)
    public String approveDocument() {
        long time1 = System.currentTimeMillis();
        if (null == comment) {
            comment = "";
        }

        Map<String, Object> extraData = new HashMap<String, Object>(3);
        extraData.put("pics", pics);
        extraData.put("fileUrls", fileUrls);

        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveResult approveResult = approvalService.approveDocumentWithExtraData(user, agree, documentId, comment, modifyTime,
                null, extraData, OSType.MOBILE);
        ApproveResultCode resultCode = approveResult.getApproveResultCode();
        ResponseMessage<Object> responseMessage = null;
        if (resultCode != null) {
            // 审批成功
            if (ApproveResultCode.APPROVE_SUCCESS == resultCode) {
                responseMessage = new ResponseMessage<Object>("审批成功");
            }
            // 审批不同意
            else if (ApproveResultCode.APPROVE_DISGREE == resultCode) {
                responseMessage = new ResponseMessage<Object>("审批成功");
            }
            // 数据版本改变
            else if (ApproveResultCode.APPROVE_CHECKED_DATA_CHANGED == resultCode) {
                responseMessage = new ResponseMessage<Object>(ResponseMessage.RET_SERVER_ERR, resultCode.getCode(),
                        resultCode.getMessage());
            }// 其它错误
            else {
                responseMessage = new ResponseMessage<Object>(ResponseMessage.RET_SERVER_ERR, resultCode.getCode(),
                        resultCode.getMessage());
            }
        } else {
            throw new ServiceException(approveResult.getErrMsg());
        }

        setResponseMessageData(responseMessage);
        String description = String.format("审批单据：%s,documentId:%s,documentNo:%s", (System.currentTimeMillis() - time1), documentId, documentNo);
        this.addSysLog("操作耗时", description);

        return SUCCESS;
    }

    /**
     * @Title: approveDocumentByPC
     * @Description:PC端审批入口
     * @add by ShiChunshan
     * @since 2015年4月30日09:41:01
     */
    // 审批单据 桌面端
    @Access(pass = true)
    public String approveDocumentByPC() {
        long time1 = System.currentTimeMillis();
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveResult approveResult = approvalService.approveDocument(user, agree, documentId, comment, modifyTime,
                documentNo, OSType.PC);
        logger.info(String.format("%s PC端审批回文,返回码：%s,错误信息：%s,审批状态编码：%s,审批状态名称：%s,下一级审批人ID：%s,下一级审批人No：%s,下一级审批人名字：%s",
                StringUtils.isEmpty(documentNo) ? documentId : documentNo, approveResult.getRtnCode(),
                approveResult.getErrMsg(), approveResult.getStatusNo(), approveResult.getStatusName(),
                approveResult.getNextUserID(), approveResult.getNextUserNo(), approveResult.getNextUserName()));
        setResponseCommonData(approveResult);
        String description = String.format("审批单据：%s,documentId:%s,documentNo:%s", (System.currentTimeMillis() - time1), documentId, documentNo);
        this.addSysLog("操作耗时", description);
        return SUCCESS;
    }

    /**
     * PC端 提交单据
     *
     * @return
     */
    @Access(pass = true)
    public String submitRecordByPC() {
        long time1 = System.currentTimeMillis();
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveResult approveResult = approvalService.submitRecord(user, documentNo, moduleId, agree, comment, modifyTime, OSType.PC);
        setResponseCommonData(approveResult);
        String description = String.format("提交单据：%s,documentNo:%s,moduleId:%s", (System.currentTimeMillis() - time1), documentNo, moduleId);
        this.addSysLog("操作耗时", description);
        return SUCCESS;
    }


    /**
     * 移动端 提交单据
     *
     * @return
     */
    @Access(pass = true)
    public String submitRecordByMobile() {
        long time1 = System.currentTimeMillis();
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveResult approveResult = approvalService.submitRecord(user, documentNo, moduleId, agree, comment, modifyTime, OSType.MOBILE);
        setResponseCommonData(approveResult);
        String description = String.format("提交单据：%s,documentNo:%s,moduleId:%s", (System.currentTimeMillis() - time1), documentNo, moduleId);
        this.addSysLog("操作耗时", description);
        return SUCCESS;
    }


    /**
     * 撤销
     *
     * @return
     */
    @Access(pass = true)
    public String revokeRecord() {
        Object rtnObj = approvalService.revokeRecord(documentNo, moduleId, modifyTime);
        setResponseData(rtnObj);
        return SUCCESS;
    }


    /**
     * 作废
     *
     * @return
     */
    @Access(pass = true)
    public String forbidRecord() {
        Object rtnObj = approvalService.forbidRecord(documentNo, moduleId, modifyTime);
        setResponseData(rtnObj);
        return SUCCESS;
    }

    // 得到单据明细信息
    @Access(pass = true)
    public String getDocumentDetail() {
        long time1 = System.currentTimeMillis();
        BaseApproveProcess approveProcessor = approveManager.get(moduleId);
        if (approveProcessor == null) {
            throw new ServiceException("暂不支持该模块的审批");
        }
        if (approveProcessor instanceof DocumentBufferService) {
            setResponseData(((DocumentBufferService) approveProcessor).getDocumentBuffered(moduleId, documentNo));
        } else {
            setResponseData(approveProcessor.getDocumentDetail(documentNo));
        }

        String description = String.format("查询审批单据明细：%s,documentNo:%s,moduleId:%s", (System.currentTimeMillis() - time1), documentNo, moduleId);
        this.addSysLog("操作耗时", description);

        return SUCCESS;
    }

    private void addSysLog(String logType, String descrption) {
        sysLogsDao.addSysLog(logType, "审批", descrption);
    }

    // 得到单据审批历史
    @Access(pass = true)
    public String getDocumentHistory() {
        List<ApproveDocumentsPoints> history = approvalService.getDocumentHistory(documentId, moduleId);
        setResponseData(history);
        return SUCCESS;
    }

    // 得到待审事宜
    @Access(pass = true)
    public String getPendingMatters() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        String userId = user.getUserId();
        ApproveDocumentSearchCriteria criteria = null;
        try {
            criteria = initCriteria();
        } catch (Exception e) {
            return showErrorMsg("查询参数错误:" + searchCriteria);
        }
        List<VwOfficeApproveDocuments> list = approvalService.getPendingMatters(userId, criteria);
        setResponseData(list);
        return SUCCESS;
    }

    // 得到“我的待审”
    @Access(pass = true)
    public String getMyApprovingDocument() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveDocumentSearchCriteria criteria = null;
        try {
            criteria = initCriteria();
        } catch (Exception e) {
            return showErrorMsg("查询参数错误:" + searchCriteria);
        }
        List<VwOfficeApproveDocuments> list = approvalService.getMyApprovingMatters(user.getUserId(), criteria);
        setResponseData(list);
        return SUCCESS;
    }

    /**
     * 我的已审
     *
     * @return
     * @author caigx
     */
    @Access(pass = true)
    public String getMyApprovedDocument() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveDocumentSearchCriteria criteria = null;
        try {
            criteria = initCriteria();
        } catch (Exception e) {
            return showErrorMsg("查询参数错误:" + searchCriteria);
        }
        List<VwOfficeApproveDocuments> list = approvalService.getMyApprovedMatters(user.getUserId(), criteria, pageNo,
                pageSize);
        setResponseData(list);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getInitData() {
        Map<String, List> response = new HashMap<String, List>(1);
        response.put("moduleList", approvalService.getApproveModuleList());
        setResponseData(response);
        return SUCCESS;
    }

    // 根据条件得到“已审事宜”
    @Access(pass = true)
    public String getApprovedDocumentByCriteria() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        ApproveDocumentSearchCriteria criteria = null;
        try {
            criteria = initCriteria();
        } catch (Exception e) {
            return showErrorMsg("查询参数错误:" + searchCriteria);
        }
        List<VwOfficeApproveDocuments> list = approvalService.getApprovedMattersByProperties(user.getUserId(), criteria, pageNo, pageSize);
        setResponseData(list);
        return SUCCESS;
    }

    /**
     * 初始化搜索条件，将json格式的搜索条件反序列化为对象
     *
     * @return
     * @throws JsonSyntaxException
     */
    private ApproveDocumentSearchCriteria initCriteria() {
        ApproveDocumentSearchCriteria criteria = new ApproveDocumentSearchCriteria();

        criteria = gson.fromJson(searchCriteria, ApproveDocumentSearchCriteria.class);
        return criteria;
    }

    @Access(pass = true)
    public String getAttachment() throws IOException {
        HttpServletResponse response = (HttpServletResponse)
                ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest)
                ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);

        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.reset();

        response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
        // response.setContentType("application/octet-stream");
        response.setContentType("application/octet-stream");
        response.addHeader("Connection", "close");
        InputStream is = getFileByte(filepath);
        int count = 0;
        int pics = 0;
        byte[] buffer = new byte[1024 * 32];
        List<byte[]> pool = new ArrayList<byte[]>();
        List<Integer> sizes = new ArrayList<Integer>();
        while ((pics = is.read(buffer)) >= 0) {
            count += pics;
            sizes.add(pics);
            pool.add(buffer);
            buffer = new byte[1024 * 32];
        }
//		response.addHeader("Content-Length", count.toString());
        response.setContentLength(count);
        for (int i = 0; i < sizes.size(); i++) {
            toClient.write(pool.get(i), 0, sizes.get(i));
        }
//		while((pics = is.read(buffer)) >= 0) {
//			toClient.write(buffer, 0, (int) pics);
//		}
        is.close();
        // response.addHeader("Content-Length", String.valueOf(count));

        toClient.flush();
        toClient.close();
//		response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//		response.addHeader("Content-Length", "" + file.length());
        return SUCCESS;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    InputStream getFileByte(String path) {
        String basePath = this.config.getApplicationConfig("attachment.path");
        if (basePath.substring(0, 6).toLowerCase().equals("ftp://")) {
            URL u = null;
            try {
                String ftpUrl = basePath + new String(path.getBytes("GBK"));
                u = new URL(ftpUrl);
                return u.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File file = new File(basePath + path);
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
