package cn.sf_soft.finance.payment.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.finance.payment.service.impl.PaymentRequestService;
import cn.sf_soft.office.approval.model.ApproveDocuments;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRequestAction extends BaseAction {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PaymentRequestAction.class);

    @Autowired
    private PaymentRequestService paymentRequestService;

    private String jsonData;
    private String filter;


    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
    public void setFilter(String filter) {
        this.filter = filter;
    }


    /**
     * 获取初始数据
     * @return
     */
    @Access(pass = true)
    public String getInitData(){
        setResponseData(paymentRequestService.getInitData());
        return SUCCESS;
    }


    /**
     * 业务单据数据
     * @return
     */
    @Access(pass = true)
    public String listDocumentEntry(){
        HashMap filter_map = gson.fromJson(filter, HashMap.class);
        PageModel<Object> pageModel = paymentRequestService.listDocumentEntry(filter_map,pageNo, pageSize);
        ResponseMessage<Object> respMsg = new ResponseMessage();
        respMsg.setPageNo(pageModel.getPage());
        respMsg.setPageSize(pageModel.getPerPage());
        respMsg.setTotalSize(pageModel.getTotalSize());
        respMsg.setData(pageModel.getData());
        setResponseMessageData(respMsg);
        return SUCCESS;
    }


    /**
     * 业务请款提交
     * @return
     */
    @Access(pass = true)
    public String submitRequest() throws ParseException {
        List<ApproveDocuments> list = paymentRequestService.submitRequest(getPostJson());
        setResponseData(list);
        return SUCCESS;
    }


    private Map<String, Object> getPostJson(){
        try {
            HashMap map = gson.fromJson(jsonData, HashMap.class);
            return map;
        }catch (Exception e){
            throw new ServiceException("提交数据不合法");
        }
    }
}
