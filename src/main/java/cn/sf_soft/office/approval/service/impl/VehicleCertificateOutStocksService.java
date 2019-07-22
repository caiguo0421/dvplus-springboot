package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.certificate.model.VehicleCertificateHistoryInfo;
import cn.sf_soft.vehicle.certificate.model.VehicleCertificateOutStocks;
import cn.sf_soft.vehicle.certificate.model.VehicleCertificateOutStocksDetail;
import cn.sf_soft.vehicle.certificate.model.VehicleCertificateStocks;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 车辆合格证出库
 */
@Service("vehicleCertificateOutStocksService")
public class VehicleCertificateOutStocksService extends BaseApproveProcess {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleCertificateOutStocksService.class);
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10281520";

    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<VehicleCertificateOutStocks> stocksList = (List<VehicleCertificateOutStocks>) dao.findByHql("FROM VehicleCertificateOutStocks WHERE documentNo = ? ", documentNo);
        if (stocksList == null || stocksList.size() == 0) {
            throw new ServiceException("未找到出库单：" + documentNo);
        }
        VehicleCertificateOutStocks outStock = stocksList.get(0);
//        List<VehicleCertificateOutStocksDetail> detailList = (List<VehicleCertificateOutStocksDetail>) dao.findByHql("FROM VehicleCertificateOutStocksDetail WHERE outStockNo = ? ", stock.getOutStockNo());

        List<Map<String, Object>> resultList = getCertificateOutStocksDetail(outStock.getOutStockNo());
        Set<Object> chargeDetail = new HashSet<>();
        if (resultList != null && resultList.size() > 0) {
            for (Map<String, Object> item : resultList) {
                chargeDetail.add(item);
            }
        }
        outStock.setChargeDetail(chargeDetail);

        return outStock;
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        List<VehicleCertificateOutStocks> stocksList = (List<VehicleCertificateOutStocks>) dao.findByHql("FROM VehicleCertificateOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        if (stocksList == null || stocksList.size() == 0) {
            throw new ServiceException("未找到出库单：" + approveDocument.getDocumentNo());
        }
        VehicleCertificateOutStocks outStock = stocksList.get(0);
        List<VehicleCertificateOutStocksDetail> detailList = (List<VehicleCertificateOutStocksDetail>) dao.findByHql("FROM VehicleCertificateOutStocksDetail WHERE outStockNo = ? ", outStock.getOutStockNo());


        List<Map<String, Object>> resultList = getCertificateOutStocksDetail(outStock.getOutStockNo());
        for (Map<String, Object> item : resultList) {
            VehicleCertificateStocks stocks = dao.get(VehicleCertificateStocks.class, (String) item.get("certificate_id"));
            if (stocks == null) {
                throw new ServiceException(String.format("%s的合格证不在仓库中", StringUtils.isNotEmpty((String) item.get("certificate_no")) ? "合格证编号：" + item.get("certificate_no") : "车辆：" + item.get("vehicle_vin")));
            }

            if (Tools.toShort(stocks.getStatus()) == (short) 3) {
                throw new ServiceException(String.format("%s的合格证移库中", StringUtils.isNotEmpty((String) item.get("certificate_no")) ? "合格证编号：" + item.get("certificate_no") : "车辆：" + item.get("vehicle_vin")));
            }

            if (Tools.toShort(stocks.getStatus()) == (short) 1) {
                throw new ServiceException(String.format("%s的合格证已经出库", StringUtils.isNotEmpty((String) item.get("certificate_no")) ? "合格证编号：" + item.get("certificate_no") : "车辆：" + item.get("vehicle_vin")));
            }

            if (Tools.toShort(stocks.getStatus()) == (short) 2) {
                throw new ServiceException(String.format("%s的合格证已经借出", StringUtils.isNotEmpty((String) item.get("certificate_no")) ? "合格证编号：" + item.get("certificate_no") : "车辆：" + item.get("vehicle_vin")));
            }
        }


        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    private List<Map<String, Object>> getCertificateOutStocksDetail(String outStockNo) {
        String sql = "SELECT  a.*, ISNULL(b.vehicle_vno, f.vehicle_vno) AS vehicle_vno,\n" +
                "\t\t\t\t        ISNULL(b.vehicle_name, f.vehicle_name) AS vehicle_name,\n" +
                "\t\t\t\t        ISNULL(b.vehicle_strain, f.vehicleStrain) AS vehicle_strain,\n" +
                "\t\t\t\t        ISNULL(b.vehicle_color, f.color) AS vehicle_color,\n" +
                "\t\t\t\t        ISNULL(b.vehicle_kind_meaning, f.vehicle_kind_meaning) AS vehicle_kind_meaning,\n" +
                "\t\t\t\t        c.certificate_no, c.certificate_time, c.vehicle_vin, d.contract_no,\n" +
                "\t\t\t\t        e.contract_detail_id, e.real_deliver_time,\n" +
                "\t\t\t\t        e.approve_status AS contract_approve_status, c.vehicle_vin, b.*,fc.meaning AS buy_type_meaning,\n" +
                "\t\t\t\t        ( SELECT TOP 1 aa.vehicle_price_total\n" +
                "\t\t\t\t          FROM dbo.vehicle_sale_contract_detail aa\n" +
                "\t\t\t\t          WHERE aa.vehicle_id = b.vehicle_id AND aa.contract_no = b.sale_contract_no ) AS vehicle_price_total\n" +
                "\t\t\t\tFROM    dbo.vehicle_certificate_out_stocks_detail a\n" +
                "\t\t\t\tLEFT JOIN vehicle_certificate_stocks c ON a.certificate_id = c.certificate_id\n" +
                "\t\t\t\tLEFT JOIN ( SELECT  a.self_id ,a.underpan_no ,e.vehicle_vno_id ,e.vehicle_vno ,g.vehicle_name ,\n" +
                "\t\t\t\t                    g.vehicleStrain ,e.color ,g.vehicle_kind_meaning\n" +
                "\t\t\t\t            FROM    dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t            LEFT JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t            LEFT JOIN dbo.vehicle_DF_sap_contract c ON b.sap_contract_no = c.contract_no\n" +
                "\t\t\t\t            LEFT JOIN dbo.vehicle_DF_purchase_order d ON d.purchase_order_no = c.purchase_order_no\n" +
                "\t\t\t\t            LEFT JOIN vehicle_demand_apply_detail e ON d.work_state_audit = e.work_state_audit\n" +
                "\t\t\t\t            LEFT JOIN dbo.vw_vehicle_type g ON e.vehicle_vno_id = g.vno_id ) f ON c.sap_delivery_id = f.self_id\n" +
                "\t\t\t\tLEFT JOIN dbo.vw_vehicle_stock b ON c.vehicle_id = b.vehicle_id\n" +
                "\t\t\t\tLEFT JOIN dbo.vehicle_sale_contracts d ON b.sale_contract_no = d.contract_no\n" +
                "                LEFT JOIN (SELECT code ,meaning FROM sys_flags WHERE field_no = 'vs_buy_type') AS fc ON d.buy_type =fc.code \n" +
                "\t\t\t\tLEFT JOIN dbo.vehicle_sale_contract_detail e ON d.contract_no = e.contract_no\n" +
                "\t\t\t\t                                                AND e.vehicle_id = b.vehicle_id\n" +
                "\t\t\t\t                                                AND e.approve_status <> 30\n" +
                "\t\t\t\tWHERE   a.out_stock_no = :outStockNo";
        Map<String, Object> params = new HashMap<>(1);
        params.put("outStockNo", outStockNo);
        List<Map<String, Object>> resultList = dao.getMapBySQL(sql, params);
        return resultList;
    }


    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        SysUsers user = HttpSessionStore.getSessionUser();

        List<VehicleCertificateOutStocks> stocksList = (List<VehicleCertificateOutStocks>) dao.findByHql("FROM VehicleCertificateOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        if (stocksList == null || stocksList.size() == 0) {
            throw new ServiceException("未找到出库单：" + approveDocument.getDocumentNo());
        }
        VehicleCertificateOutStocks outStock = stocksList.get(0);
        List<VehicleCertificateOutStocksDetail> detailList = (List<VehicleCertificateOutStocksDetail>) dao.findByHql("FROM VehicleCertificateOutStocksDetail WHERE outStockNo = ? ", outStock.getOutStockNo());

        for (VehicleCertificateOutStocksDetail detail : detailList) {
            VehicleCertificateStocks stocks = dao.get(VehicleCertificateStocks.class, detail.getCertificateId());
            if (StringUtils.equals(outStock.getOutStockType(), "2")) {
                stocks.setStatus((short) 1);
                stocks.setStockId(null);
                stocks.setStockName(null);
            } else {
                stocks.setStatus((short) 2);
                stocks.setStockId(null);
                stocks.setStockName(null);
                stocks.setObjectId(outStock.getObjectId());
                stocks.setObjectNo(outStock.getObjectNo());
                stocks.setObjectName(outStock.getObjectName());
            }

            dao.save(stocks);
            //处理历史
            VehicleCertificateHistoryInfo historyInfo = new VehicleCertificateHistoryInfo();
            historyInfo.setInfoId(UUID.randomUUID().toString());
            if (StringUtils.equals(outStock.getOutStockType(), "2")) {
                historyInfo.setInfoType("证件出库");
            } else {
                historyInfo.setInfoType("证件借出");
                historyInfo.setInfoRemark(String.format("借出人：%s", outStock.getObjectName()));
            }

            historyInfo.setInfoNo(outStock.getOutStockNo());
            historyInfo.setInfoTime(outStock.getOutStockTime());
            historyInfo.setCreator(outStock.getCreator());
            historyInfo.setCreateTime(outStock.getCreateTime());
            historyInfo.setApprover(user.getUserFullName());
            historyInfo.setApproveTime(new Timestamp(System.currentTimeMillis()));
            historyInfo.setStockId(stocks.getStockId());
            historyInfo.setCertificateId(detail.getCertificateId());
            dao.save(historyInfo);
        }
        outStock.setApprover(user.getUserFullName());
        outStock.setApproveTime(new Timestamp(System.currentTimeMillis()));
        outStock.setDocumentStatus((short) 1);

        return super.onLastApproveLevel(approveDocument, comment);
    }
}
