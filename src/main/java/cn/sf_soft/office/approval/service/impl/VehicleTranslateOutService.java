package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VehicleInStockDetail;
import cn.sf_soft.office.approval.model.VehicleInStocks;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.apply.model.VehicleApplyTranslate;
import cn.sf_soft.vehicle.out.model.VehicleOutStockDetail;
import cn.sf_soft.vehicle.out.model.VehicleOutStocks;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * 车辆调拨出库
 *
 * @author caigx
 */
@Service("vehicleTranslateOutService")
public class VehicleTranslateOutService extends BaseApproveProcess {

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10402020";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleTranslateOutService.class);

    @Autowired
    private SysCodeRulesService sysCodeRulesService;

    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", documentNo);
        if (outStockList == null || outStockList.size() == 0) {
            throw new ServiceException("未找到出库单：" + documentNo);
        }
        VehicleOutStocks stock = outStockList.get(0);
        SysStations inStation = dao.get(SysStations.class, stock.getCustomerId());
        if(inStation!=null){
            stock.setInStationName(inStation.getStationName());
        }
        SysStations outStation = dao.get(SysStations.class, stock.getStationId());
        if(outStation!=null){
            stock.setOutStationName(outStation.getStationName());
        }

        List<VehicleOutStockDetail> stockDetails = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", stock.getOutStockNo());

        Set<VehicleOutStockDetail> chargeDetail = new HashSet<>();
        if (stockDetails != null && stockDetails.size() > 0) {
            for(VehicleOutStockDetail detail : stockDetails){
                chargeDetail.add(detail);
            }
        }
        stock.setChargeDetail(chargeDetail);

        return stock;
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        if (outStockList == null || outStockList.size() == 0) {
            throw new ServiceException("未找到出库单：" + approveDocument.getDocumentNo());
        }
        VehicleOutStocks outStock = outStockList.get(0);
        List<VehicleOutStockDetail> detailSet = (List<VehicleOutStockDetail>) dao.findByHql("FROM VehicleOutStockDetail WHERE outStockNo = ? ", outStock.getOutStockNo());
        if (detailSet == null || detailSet.size() == 0) {
            throw new ServiceException("没有出库单明细");
        }

        Iterator<VehicleOutStockDetail> detailIterator = detailSet.iterator();
        while (detailIterator.hasNext()) {
            VehicleOutStockDetail detail = detailIterator.next();
            //判断库存状态是否一致
            vehicleStockStatusIsFit(detail,false);
            if (approveStatus == Constant.ApproveStatus.LAST_APPROVE) {
                //判断是否存在未审批的成本转移单据
                validateCostTranfer(detail);
            }
        }


        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }


    /**
     * 校验转移成本
     * @param detail
     */
    public void validateCostTranfer(VehicleOutStockDetail detail){
        //判断是否存在未审批的成本转移单据
        String sql = "SELECT * FROM vehicle_cost_transfer WHERE approve_status = 0 and (vehicle_id_in = :vehicleId OR vehicle_id_out =:vehicleId)";
        Map<String, Object> param = new HashMap<>();
        param.put("vehicleId", detail.getVehicleId());
        List result = dao.getMapBySQL(sql, param);
        if (result != null && result.size() > 0) {
            throw new ServiceException("车辆：" + detail.getVehicleVin() + "存在未完成的成本转移单");
        }
    }


    /**
     * 判断库存状态是否一致
     * @param detail
     * @param validateWarehouseName
     */
    public void vehicleStockStatusIsFit(VehicleOutStockDetail detail,boolean validateWarehouseName){
        //状态校验
        VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
        if (stocks == null) {
            throw new ServiceException("车辆" + detail.getVehicleVin() + "在车辆库存中不存在");
        }

        if (stocks.getStatus() != 0 && stocks.getStatus() != 1 && stocks.getStatus() != 2) {
            throw new ServiceException("车辆" + stocks.getVehicleVin() + "的库存状态不正确");
        }

        if(validateWarehouseName){
            if(!StringUtils.equals(stocks.getWarehouseName(), detail.getWarehouseName())){
                throw new ServiceException(String.format("车辆%s的仓库%s和库存中的仓库%s不一致",stocks.getVehicleVin(),detail.getWarehouseName(),stocks.getWarehouseName()));
            }
        }
    }


    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        SysUsers user = HttpSessionStore.getSessionUser();
        List<VehicleOutStocks> outStockList = (List<VehicleOutStocks>) dao.findByHql("FROM VehicleOutStocks WHERE documentNo = ? ", approveDocument.getDocumentNo());
        VehicleOutStocks outStock = outStockList.get(0);

        outStock.setStatus((short) 50);
        outStock.setApproveStatus((byte) 1);
        outStock.setApproveTime(new Timestamp(System.currentTimeMillis()));
        outStock.setApprover(user.getUserFullName());
        outStock.setModifier(user.getUserFullName());
        outStock.setModifyTime(new Timestamp(System.currentTimeMillis()));
        outStock.setApproverNo(user.getUserNo());

        //生成入库单
        VehicleInStocks inStock = new VehicleInStocks();
        String inStockNo = sysCodeRulesService.createSysCodeRules("VIT_NO", outStock.getCustomerId());
        inStock.setInStockNo(inStockNo);
        inStock.setInStockTime(new Timestamp(System.currentTimeMillis()));
        inStock.setInStockType((short) 1);
        inStock.setCreator(user.getUserFullName());
        inStock.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //入库方的站点ID保存在出库单的客户ID中
        inStock.setStationId(outStock.getCustomerId());
        inStock.setApproveStatus((short) 0);
        inStock.setSupplierType((short) 4);
        inStock.setSupplierId(outStock.getStationId());
        inStock.setSupplierNo(outStock.getStationId());
        SysStations station = dao.get(SysStations.class, outStock.getStationId());
        inStock.setSupplierName(station.getStationName());

        if(StringUtils.isNotEmpty(outStock.getVatNo())) {
            VehicleApplyTranslate applyTranslate = dao.get(VehicleApplyTranslate.class, outStock.getVatNo());
            inStock.setWarehouseId(applyTranslate == null ? null : applyTranslate.getWarehouseId());
            inStock.setWarehouseName(applyTranslate == null ? null : applyTranslate.getWarehouseName());
        }
        inStock.setInStockCount(outStock.getChargeDetail().size());
        inStock.setInStockMoney(outStock.getOutStockMoney() == null ? 0.00D : outStock.getOutStockMoney().doubleValue());
        inStock.setRefOutStocksNo(outStock.getOutStockNo());//入库单所引用的出库单号
        inStock.setCreatorUnitId(user.getDepartment());


        Set<VehicleOutStockDetail> detailSet = outStockList.get(0).getChargeDetail();
        Iterator<VehicleOutStockDetail> detailIterator = detailSet.iterator();
        while (detailIterator.hasNext()) {
            VehicleOutStockDetail detail = detailIterator.next();
            VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());

            stocks.setStatus((short) 3);
            stocks.setOutStockNo(outStock.getOutStockNo());
            stocks.setOutStockType((short) outStock.getOutStockType());
            stocks.setOutStockTime(outStock.getOutStockTime());
            stocks.setVehiclePrice(detail.getVehiclePrice() == null ? 0.00D : detail.getVehiclePrice().doubleValue());
            stocks.setVatNo(null);
            stocks.setVatStationId(null);

            //保存入库单明细
            VehicleInStockDetail inStockDetail = new VehicleInStockDetail();
            inStockDetail.setInStockDetailId(UUID.randomUUID().toString());
            inStockDetail.setVehicleId(detail.getVehicleId());
            inStockDetail.setVehicleEngineType(detail.getVehicleEngineType());
            inStockDetail.setVehicleEngineNo(detail.getVehicleEngineNo());
            inStockDetail.setVehicleVin(detail.getVehicleVin());
            inStockDetail.setVnoId(detail.getVnoId());
            inStockDetail.setVehicleSalesCode(detail.getVehicleSalesCode());
            inStockDetail.setVehicleVno(detail.getVehicleVno());
            inStockDetail.setVehicleName(detail.getVehicleName());
            inStockDetail.setVehicleStrain(detail.getVehicleStrain());
            inStockDetail.setVehicleColor(detail.getVehicleColor());
            inStockDetail.setVehicleOutFactoryTime(detail.getVehicleOutFactoryTime());
            inStockDetail.setVehicleQuantity(detail.getVehicleQuantity());
            inStockDetail.setConversionAmount(detail.getModifiedFee() == null ? 0.00D : detail.getModifiedFee().doubleValue());
            inStockDetail.setViPayType(stocks.getPayType());
            inStockDetail.setVehicleSaleDocuments(stocks.getVehicleSaleDocuments());
            inStockDetail.setFuturePayDate(stocks.getFuturePayDate());
            inStockDetail.setVehicleCost(detail.getVehicleCost() == null ? 0.00D : detail.getVehicleCost().doubleValue());
            inStockDetail.setVehiclePrice(detail.getVehiclePrice() == null ? 0.00D : detail.getVehiclePrice().doubleValue());
            inStockDetail.setVehicleCarriage(detail.getVehicleCarriage() == null ? 0.00D : detail.getVehicleCarriage().doubleValue());
            inStockDetail.setVehicleEligibleNo(detail.getVehicleEligibleNo());

            inStockDetail.setVehicleType(stocks.getVehicleType());
            inStockDetail.setInStockNo(inStock.getInStockNo());
            inStockDetail.setDriveRoomNo(detail.getDriveRoomNo());
            inStockDetail.setConversionStatus((short) detail.getConversionStatus());

            dao.save(inStockDetail);
        }


        dao.save(inStock);
        return super.onLastApproveLevel(approveDocument, comment);
    }


}
