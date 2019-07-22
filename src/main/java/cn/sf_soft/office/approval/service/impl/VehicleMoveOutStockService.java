package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.apply.model.VehicleApplyTranslate;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import cn.sf_soft.vehicle.out.model.VehicleOutStockDetail;
import cn.sf_soft.vehicle.out.model.VehicleOutStocks;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 车辆移库出库
 *
 * @author caigx
 */
@Service("vehicleMoveOutStockService")
public class VehicleMoveOutStockService extends BaseApproveProcess {

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10302020";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleMoveOutStockService.class);

    /**
     * 站内移库是否需要确认
     */
    private static final String VEHICLE_TRANSFER_CONFIRM = "VEHICLE_TRANSFER_CONFIRM";

    @Autowired
    private SysCodeRulesService sysCodeRulesService;

    @Autowired
    private VehicleTranslateOutService vehicleTranslateOutService;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", documentNo);
        if (outStockList == null || outStockList.size() == 0) {
            throw new ServiceException("未找到出库单：" + documentNo);
        }
        VehicleOutStocks stock = outStockList.get(0);
        SysStations inStation = dao.get(SysStations.class, stock.getTargetStationId());
        if (inStation != null) {
            stock.setInStationName(inStation.getStationName());
        }
        SysStations outStation = dao.get(SysStations.class, stock.getStationId());
        if (outStation != null) {
            stock.setOutStationName(outStation.getStationName());
        }

        List<VehicleOutStockDetail> stockDetails = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", stock.getOutStockNo());

        Set<VehicleOutStockDetail> chargeDetail = new HashSet<>();
        if (stockDetails != null && stockDetails.size() > 0) {
            for (VehicleOutStockDetail detail : stockDetails) {
                chargeDetail.add(detail);
            }
        }
        stock.setChargeDetail(chargeDetail);

        return stock;
    }


    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        if (outStockList == null || outStockList.size() == 0) {
            throw new ServiceException("未找到出库单：" + approveDocument.getDocumentNo());
        }
        VehicleOutStocks outStock = outStockList.get(0);
        List<VehicleOutStockDetail> details = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", outStock.getOutStockNo());
        if (details == null || details.size() == 0) {
            throw new ServiceException("没有出库单明细");
        }

        for (VehicleOutStockDetail detail : details) {
            //判断库存状态是否一致
            vehicleTranslateOutService.vehicleStockStatusIsFit(detail, true);
            if (approveStatus == Constant.ApproveStatus.LAST_APPROVE) {
                //判断是否存在未审批的成本转移单据
                vehicleTranslateOutService.validateCostTranfer(detail);
            }
        }
        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }


    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        SysUsers user = HttpSessionStore.getSessionUser();
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        VehicleOutStocks outStock = outStockList.get(0);
        List<VehicleOutStockDetail> details = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", outStock.getOutStockNo());
        //设置状态
        Timestamp now = new Timestamp(System.currentTimeMillis());
        outStock.setApproveStatus((byte) 1);
        outStock.setApprover(user.getUserFullName());
        outStock.setApproveTime(now);
        outStock.setModifyTime(now);
        //同一站点
        if (StringUtils.equals(outStock.getStationId(), outStock.getTargetStationId()) && !"是".equals(getSysOptionVal(VEHICLE_TRANSFER_CONFIRM, outStock.getStationId()))) {
            outStock.setConfirmMan(user.getUserFullName());
            outStock.setConfirmTime(new Timestamp(System.currentTimeMillis()));
            outStock.setConfirmStatus((byte) 1);
            for (VehicleOutStockDetail detail : details) {
                //如果已建立出库单的车辆仓库不一致时，更新其仓库
                if (!StringUtils.equals(detail.getWarehouseId(), outStock.getCustomerId())) {
                    detail.setWarehouseId(outStock.getCustomerId());
                    detail.setWarehouseName(outStock.getCustomerName());
                }

                VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
                if (stocks != null) {
                    stocks.setWarehouseId(outStock.getCustomerId());
                    stocks.setWarehouseName(outStock.getCustomerName());
                    stocks.setStationId(outStock.getTargetStationId());
                }
                //同步更新仓库
                List<VehicleConversion> conversionList = (List<VehicleConversion>) dao.findByHql("FROM VehicleConversion WHERE status IN (10,20,30,40,50)  AND vehicleId = ?", detail.getVehicleId());
                for (VehicleConversion conversion : conversionList) {
                    if (!StringUtils.equals(conversion.getWarehouseId(), outStock.getCustomerId())) {
                        conversion.setWarehouseId(outStock.getCustomerId());
                    }
                }

                List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) dao.findByHql("FROM VehicleSaleContractDetail WHERE approveStatus IN (0,1,2,20) AND vehicleId = ? ", detail.getVehicleId());
                for (VehicleSaleContractDetail contractDetail : contractDetailList) {
                    if (!StringUtils.equals(contractDetail.getWarehouseId(), outStock.getCustomerId())) {
                        contractDetail.setWarehouseId(outStock.getCustomerId());
                        contractDetail.setWarehouseName(outStock.getCustomerName());
                    }
                }
            }
        } else {
            for (VehicleOutStockDetail detail : details) {
                VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
                if (stocks != null) {
                    stocks.setStatus((short) 3); //出库
                    stocks.setVatNo(null);
                    stocks.setVatStationId(null);
                }
            }
        }
        createMoveInStock(outStock);
        return super.onLastApproveLevel(approveDocument, comment);
    }


    /**
     * 生成入库单
     *
     * @param outStock
     */
    public void createMoveInStock(VehicleOutStocks outStock) {
        SysUsers user = HttpSessionStore.getSessionUser();
        List<VehicleOutStockDetail> details = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", outStock.getOutStockNo());

        //因为移库只操作同一财务中心的仓库所以不用产生费用。
        short iApproveStatus = 0;
        //byte btPayType = 1;//付款方式
        short btPayType = 2;//不需要付款
        //byte btPaymentStatus = 0;
        short btPaymentStatus = 2;//不需要请款
        String sApprover = "";
        String sVisNo;
        if (StringUtils.equals(outStock.getStationId(), outStock.getTargetStationId())) {
            if ("否".equals(getSysOptionVal(VEHICLE_TRANSFER_CONFIRM, outStock.getStationId()))) {
                iApproveStatus = 1;
                sApprover = user.getUserFullName();
            } else {
                iApproveStatus = 0;
            }

            btPayType = 2;//不需要付款
            btPaymentStatus = 2;//不需要请款
            sVisNo = sysCodeRulesService.createSysCodeRules("VIM_NO", user.getDefaulStationId());

        } else {
            sVisNo = sysCodeRulesService.createSysCodeRules("VIM_NO", user.getDefaulStationId());
        }


        VehicleInStocks inStocks = new VehicleInStocks();
        inStocks.setInStockNo(sVisNo);
        inStocks.setApproveStatus(iApproveStatus);
        inStocks.setStationId(outStock.getTargetStationId());
        inStocks.setWarehouseName(outStock.getCustomerName());
        inStocks.setWarehouseId(outStock.getCustomerId());
        inStocks.setSourceStationId(outStock.getStationId());
        inStocks.setInStockType((short) 2);
        inStocks.setSupplierId(outStock.getStationId());
        inStocks.setSupplierNo(outStock.getStationId());
        SysStations outStation = dao.get(SysStations.class, outStock.getStationId());
        if (outStation != null) {
            inStocks.setSupplierName(outStation.getStationName());
        }
        inStocks.setPayType(btPayType);
        inStocks.setInStockCount(outStock.getOutStockCount());
        inStocks.setInStockMoney(Tools.toDouble(outStock.getOutStockMoney()));
        inStocks.setContractNo(outStock.getOutStockNo());
        inStocks.setSupplierType((short) 4);
        inStocks.setPaymentStatus(btPaymentStatus);
        inStocks.setInStockTime(new Timestamp(System.currentTimeMillis()));
        inStocks.setCreator(user.getUserFullName());
        inStocks.setCreateTime(inStocks.getInStockTime());
        inStocks.setRefOutStocksNo(outStock.getOutStockNo());

        if (StringUtils.equals(outStock.getStationId(), outStock.getTargetStationId()) && !"是".equals(getSysOptionVal(VEHICLE_TRANSFER_CONFIRM, outStock.getStationId()))) {
            inStocks.setIdentifier(user.getUserName());
            inStocks.setApprover(sApprover);
            inStocks.setApproveTime(inStocks.getInStockTime());
        }

        for (VehicleOutStockDetail outStockDetail : details) {

            VehicleInStockDetail inStockDetail = new VehicleInStockDetail();
            inStockDetail.setInStockDetailId(UUID.randomUUID().toString());
            inStockDetail.setInStockNo(sVisNo);
            inStockDetail.setVehicleId(outStockDetail.getVehicleId());
            inStockDetail.setVnoId(outStockDetail.getVnoId());
            inStockDetail.setVehicleSalesCode(outStockDetail.getVehicleSalesCode());
            inStockDetail.setVehicleVno(outStockDetail.getVehicleVno());
            inStockDetail.setVehicleName(outStockDetail.getVehicleName());
            inStockDetail.setVehicleStrain(outStockDetail.getVehicleStrain());
            inStockDetail.setVehicleColor(outStockDetail.getVehicleColor());
            inStockDetail.setVehicleVin(outStockDetail.getVehicleVin());
            inStockDetail.setVehicleEngineType(outStockDetail.getVehicleEngineType());
            inStockDetail.setVehicleEngineNo(outStockDetail.getVehicleEngineNo());
            inStockDetail.setVehicleEligibleNo(outStockDetail.getVehicleEligibleNo());
            inStockDetail.setVehicleOutFactoryTime(outStockDetail.getVehicleOutFactoryTime());
            inStockDetail.setVehicleCost(Tools.toDouble(outStockDetail.getVehicleCost()));
            inStockDetail.setVehicleCarriage(Tools.toDouble(outStockDetail.getVehicleCarriage()));
            inStockDetail.setConversionAmount(Tools.toDouble(outStockDetail.getModifiedFee()));
            inStockDetail.setVehicleQuantity(outStockDetail.getVehicleQuantity());
            inStockDetail.setDriveRoomNo(outStockDetail.getDriveRoomNo());

            VehicleStocks stocks = dao.get(VehicleStocks.class, inStockDetail.getVehicleId());
            if (stocks != null) {
                inStockDetail.setVehicleSaleDocuments(stocks.getVehicleSaleDocuments());
            }
            dao.save(inStockDetail);
        }

        dao.save(inStocks);

    }


    public String getSysOptionVal(String optionNo, String stationId) {
        String val = "";
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(optionNo, stationId);
        if (options != null && options.size() > 0) {
            val = options.get(0).getOptionValue();
        }
        return val;
    }

}
