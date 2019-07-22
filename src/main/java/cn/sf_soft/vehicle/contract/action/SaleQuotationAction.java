package cn.sf_soft.vehicle.contract.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.FileUtil;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.*;
import cn.sf_soft.vehicle.contract.service.SaleContractService;
import com.opensymphony.xwork2.ActionContext;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by henry on 2018/5/29.
 */
public class SaleQuotationAction extends BaseAction {
    private static final long serialVersionUID = -8091714193481177268L;
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractAction.class);
    @Resource
    private SaleContractService saleContractService;

    @Resource
    private ApprovalService approvalService;

    private String jsonData;
    private String orderBy;
    private String objId;
    private Boolean preview = false; //预览

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setPreview(Boolean preview) {
        this.preview = preview;
    }

    public void setObjId(String objId) { this.objId = objId; }

    private Map<String, Object> getPostJson(){
        try {
            HashMap json_map = gson.fromJson(jsonData, HashMap.class);
            return json_map;
        }catch (Exception e){
            throw new ServiceException("提交数据不合法");
        }
    }

    // Quotation

    @Access(pass = true)
    public String getList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotation.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String create(){
        Map<String, Object> data = getPostJson();
        SysUsers user = getLoginUser();
        data.put("quotationId", UUID.randomUUID().toString());
        data.put("status", 10);
        if(!data.containsKey("sellerId") || !data.containsKey("seller") || !data.containsKey("departmentId")) {
            data.put("sellerId", user.getUserId());
            data.put("seller", user.getUserName());
            data.put("departmentId", user.getLoginDepartmentId());
        }
        Timestamp now = new Timestamp(new Date().getTime());
        data.put("createTime", now);
        Object obj = saleContractService.restCreate(VehicleSaleQuotation.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String update(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotation.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String remove(){
        Map<String, Object> update = new HashMap<String, Object>(1);
        update.put("status", 30);
        Object obj = saleContractService.restUpdate(VehicleSaleQuotation.class, objId, update);
        setResponseData(obj);
        return SUCCESS;
    }

    // Insurance

    @Access(pass = true)
    public String getInsuranceList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationInsurance.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createInsurance(){
        Map<String, Object> data = getPostJson();
        data.put("selfId", UUID.randomUUID().toString());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationInsurance.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateInsurance(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationInsurance.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeInsurance(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationInsurance.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }

    // Item

    @Access(pass = true)
    public String getItemList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationItem.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createItem(){
        Map<String, Object> data = getPostJson();
        data.put("selfId", UUID.randomUUID().toString());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationItem.class,data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateItem(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationItem.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeItem(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationItem.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }


    // Charge

    @Access(pass = true)
    public String getChargeList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationCharge.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createCharge(){
        Map<String, Object> data = getPostJson();
        data.put("selfId", UUID.randomUUID().toString());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationCharge.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateCharge(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationCharge.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeCharge(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationCharge.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }

    // Present
    @Access(pass = true)
    public String getPresentList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationPresent.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createPresent(){
        Map<String, Object> data = getPostJson();
        data.put("selfId", UUID.randomUUID().toString());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationPresent.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updatePresent(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationPresent.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removePresent(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationPresent.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }

    // LoanBudget
    @Access(pass = true)
    public String getLoanBudgetList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationLoanBudget.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createLoanBudget(){
        Map<String, Object> data = getPostJson();
        String documentNo = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();

        data.put("selfId", documentNo);
        data.put("stationId", user.getLoginStationId());
        data.put("status", 10);
        data.put("creatorId", user.getUserId());
        data.put("createTime", now);
        data.put("userId", user.getUserId());
        SysUnits unit = saleContractService.getLoginDepartment();
        data.put("departmentId", unit.getUnitId());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationLoanBudget.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateLoanBudget(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationLoanBudget.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeLoanBudget(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationLoanBudget.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }


    // LoanBudgetCharge
    @Access(pass = true)
    public String getLoanBudgetChargeList(){
        PageModel page = saleContractService.restList(VehicleSaleQuotationLoanBudgetCharge.class, getPostJson(), orderBy, pageNo, pageSize);
        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access(pass = true)
    public String createLoanBudgetCharge(){
        Map<String, Object> data = getPostJson();
        String selfId = UUID.randomUUID().toString();
        Timestamp now = new Timestamp(new Date().getTime());
        SysUsers user = getLoginUser();
        data.put("selfId", selfId);
//        postJson.put("stationId", user.getDefaulStationId());
//        postJson.put("status", 10);
//        postJson.put("creatorId", user.getUserId());
        data.put("createTime", now);
        data.put("creatorId", user.getUserId());
        Object obj = saleContractService.restCreate(VehicleSaleQuotationLoanBudgetCharge.class, data);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateLoanBudgetCharge(){
        Object obj = saleContractService.restUpdate(VehicleSaleQuotationLoanBudgetCharge.class, objId, getPostJson());
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeLoanBudgetCharge(){
        Object obj = saleContractService.restRemove(VehicleSaleQuotationLoanBudgetCharge.class, objId);
        setResponseData(obj);
        return SUCCESS;
    }

    @Access(pass = true)
    public String submit(){
        //注释掉代码，报价单不能直接将数据插入到合同中-caigx 20180904
//        PageModel page = saleContractService.submitQuotation(objId);
//        setResponseCommonData(page);
        return SUCCESS;
    }

    @Access (pass = true)
    public void pdfExport() throws Exception {
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
        response.reset();

        InputStream in = null;
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        String htmlText = saleContractService.generateQuotationHtml(objId);
        if(preview){
            response.setHeader("content-type", "text/html;charset=UTF-8");
            in = new ByteArrayInputStream(htmlText.getBytes("UTF-8"));
        }else{
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("车辆报价单_" + objId + ".pdf", "UTF-8"));
            response.setContentType("application/octet-stream");
            response.addHeader("Connection", "close");
            in = FileUtil.convertHtmlToPdf(htmlText);
        }

        try {
            int count = 0;
            int pics = 0;
            byte[] buffer = new byte[1024 * 32];
            List<byte[]> pool = new ArrayList<byte[]>();
            List<Integer> sizes = new ArrayList<Integer>();
            while ((pics = in.read(buffer)) >= 0) {
                count += pics;
                sizes.add(pics);
                pool.add(buffer);
                buffer = new byte[1024 * 32];
            }
            response.setContentLength(count);
            for (int i = 0; i < sizes.size(); i++) {
                out.write(pool.get(i), 0, sizes.get(i));
            }
        }finally {
            if(null != out){
                try {
                    out.flush();
                    out.close();
                }catch (Exception e){
                    //do nothing
                }
            }

            if(null !=in){
                try {
                    in.close();
                }catch (Exception e){
                    //do nothing
                }

            }
        }
    }
}
