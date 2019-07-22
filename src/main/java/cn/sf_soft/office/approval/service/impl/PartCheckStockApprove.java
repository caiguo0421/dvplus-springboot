package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.CheckPopedom;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.parts.inventory.model.*;
import cn.sf_soft.parts.inventory.service.impl.PartCheckStocksService;
import cn.sf_soft.parts.stockborwse.model.PartStocks;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/13 16:55
 * @Description:
 */
@Service("partCheckStockApprove")
public class PartCheckStockApprove extends BaseApproveProcess {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartCheckStockApprove.class);

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "15858020";

    @Autowired
    private PartCheckStocksService partCheckStocksService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SysCodeRulesService sysCodeRulesService;

    private ThreadLocal<PartCheckStocks> partCheckStocksThreadLocal = new ThreadLocal<PartCheckStocks>();

    @Override
    public PartCheckStocks getDocumentDetail(String documentNo) {
        return partCheckStocksService.getPartCheckStockById(documentNo);
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    private static final String EXISTS_PART = "select new map(partId as partId, stockId as stockId) from PartStocks where warehouseId=? and partId=?";

    @Autowired
    private UserService userService;

    @Override
    public ApproveResult approveAgreeByPC(ApproveDocuments approveDocument, String comment, Timestamp approveTimestamp) {
        try {
            return super.approveAgreeByPC(approveDocument, comment, approveTimestamp);
        }finally {
            partCheckStocksThreadLocal.remove();
        }
    }

    /**
     * 校验库存是否存在配件
     */
    private void validateStock(PartCheckStocks partCheckStock){
        if(null == partCheckStock.getPartCheckStockDetails() || partCheckStock.getPartCheckStockDetails().isEmpty()){
            return;
        }
        List<String> stationIds = userService.getAllStationIds(HttpSessionStore.getSessionUser());
        List<String> ids = new ArrayList<>();
        for(String stationId : stationIds){
            ids.add("'"+stationId+"'");
        }
        String stationCondition = StringUtils.join(ids.toArray(new String[]{}), ",");
        for(PartCheckStocksDetail detail : partCheckStock.getPartCheckStockDetails()){
            String stockId = detail.getStockId();
            if(StringUtils.isEmpty(stockId)){
                List<Map<String, String>> partStock = (List<Map<String, String>>)baseDao.findByHql(EXISTS_PART+" and stationId in ("+stationCondition+")", detail.getWarehouseId(), detail.getPartId());
                if(null != partStock && !partStock.isEmpty()){
                    Map<String, String> map = partStock.get(0);
                    String id = map.get("stockId");
                    if(StringUtils.isNotEmpty(id)){
                        throw new ServiceException(String.format("%s(%s)原配件目录中的配件已做出库或入库，请删除该配件重新选择该配件",detail.getPartName(), detail.getPartNo()));
                    }
                }
            }
        }
    }

    private void validateApprove(PartCheckStocks partCheckStock){
        if(null == partCheckStock.getPartCheckStockDetails() || partCheckStock.getPartCheckStockDetails().isEmpty()){
            return;
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        boolean approveProfit = CheckPopedom.checkPopedom(user, "15858020");// 盘盈审批
        boolean approveDeficit = CheckPopedom.checkPopedom(user, "15858030");// 盘亏审批
        for (PartCheckStocksDetail detail : partCheckStock.getPartCheckStockDetails()) {
            BigDecimal quantity = toBigDecimal(detail.getQuantityFact()).subtract(toBigDecimal(detail.getQuantityStock()));
            if (quantity.compareTo(BigDecimal.ZERO) == 1) {
                if (approveProfit == false) {
                    throw new ServiceException("盘存调整中存在盘盈情况，需要盘盈权限！");
                }
            } else if (quantity.compareTo(BigDecimal.ZERO) == -1) {
                if (approveDeficit == false) {
                    throw new ServiceException("盘存调整中存在盘亏情况，需要盘亏权限！");
                }
            }
        }
    }

    private PartCheckStocks getPartCheckStocksById(String id){
        PartCheckStocks partCheckStock = this.getDocumentDetail(id);
        if (null == partCheckStock) {
            throw new ServiceException(String.format("单据(%s)不存在", id));
        }
        List<PartCheckStocksDetail> details = partCheckStocksService.getPartCheckStockDetail(partCheckStock.getDocumentNo(), null, null);
        partCheckStock.setPartCheckStockDetails(details);
        return partCheckStock;
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        PartCheckStocks partCheckStock = this.getPartCheckStocksById(approveDocument.getDocumentNo());
        if(approveStatus == Constant.ApproveStatus.LAST_APPROVE){
            partCheckStocksThreadLocal.set(partCheckStock);
        }
        this.validateStock(partCheckStock);
        this.validateApprove(partCheckStock);
        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    private PartStocks newPartStock(PartCheckStocks partCheckStock, PartCheckStocksDetail detail){
        PartStocks partStocks = new PartStocks();
        partStocks.setStockId(UUID.randomUUID().toString());
        partStocks.setStationId(partCheckStock.getStationId());
        partStocks.setPartId(detail.getPartId());
        partStocks.setWarehouseId(detail.getWarehouseId());
        partStocks.setIsActive(true);
        partStocks.setQuantity(0d);
        partStocks.setStockTotalCost(0d);
        partStocks.setInventoryTime(partCheckStock.getApproveTime());
        partStocks.setCost(detail.getCost());
        baseDao.update(partStocks);
        return partStocks;
    }

    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        PartCheckStocks partCheckStock = partCheckStocksThreadLocal.get();
        if(null == partCheckStock){
            partCheckStock = this.getPartCheckStocksById(approveDocument.getDocumentNo());
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        partCheckStock.setApprover(user.getUserFullName());
        partCheckStock.setApproverNo(user.getUserNo());
        partCheckStock.setApproverUnitNo(user.getDepartmentNo());
        partCheckStock.setApproverUnitName(user.getDepartmentName());
        partCheckStock.setApproveTime(now);

        List<PartInStockDetail> partIn = new ArrayList<PartInStockDetail>();
        List<PartOutStockDetail> partOut = new ArrayList<PartOutStockDetail>();
        BigDecimal totalPisPrice = BigDecimal.ZERO;// 盘赢总金额
        BigDecimal totalPosPrice = BigDecimal.ZERO;// 盘亏总金额

        List<String> stationIds = userService.getAllStationIds(HttpSessionStore.getSessionUser());
        List<String> ids = new ArrayList<>();
        for(String stationId : stationIds){
            ids.add("'"+stationId+"'");
        }
        String stationCondition = StringUtils.join(ids.toArray(new String[]{}), ",");

        List<PartCheckStocksDetail> details = partCheckStock.getPartCheckStockDetails();
        for (PartCheckStocksDetail detail : details) {
            PartStocks partStock = null;
            if(StringUtils.isNotEmpty(detail.getStockId())){
                partStock = baseDao.get(PartStocks.class, detail.getStockId());
            }else{
                String hql = "from PartStocks where stationId in ("+ stationCondition +") and warehouseId=? and partId=?";
                List<PartStocks> partStocks = (List<PartStocks>)baseDao.findByHql(hql, detail.getWarehouseId(), detail.getPartId());
                if(null != partStocks && !partStocks.isEmpty()){
                    partStock = partStocks.get(0);
                }
            }

            BigDecimal quantityFact = toBigDecimal(detail.getQuantityFact());
            BigDecimal quantityStock = toBigDecimal(detail.getQuantityStock());
            //盘盈盘亏数量
            BigDecimal resultQuantity = quantityFact.subtract(quantityStock);

            BigDecimal resultPrice = resultQuantity.multiply(toBigDecimal(detail.getCost()).add(toBigDecimal(detail.getCarriage())));
            if(resultPrice.compareTo(BigDecimal.ZERO) == -1){
                totalPosPrice = totalPosPrice.add(resultPrice);
            }else if(resultPrice.compareTo(BigDecimal.ZERO) == 1){
                totalPisPrice = totalPisPrice.add(resultPrice);
            }


            if (resultQuantity.compareTo(BigDecimal.ZERO) == 0) {
                if (partStock == null || StringUtils.isEmpty(partStock.getStockId())) {
                    partStock = newPartStock(partCheckStock, detail);
                    detail.setPartId(partStock.getStockId());
                }
                partStock.setInventoryTime(now);
                partStock.setModifyTime(now);
            }else if (resultQuantity.compareTo(BigDecimal.ZERO) == 1) {
                PartInStockDetail inStockDetail = this.getPartInDetail(partCheckStock, detail, partStock, resultQuantity);
                if(null != inStockDetail){
                    partIn.add(inStockDetail);
                }
            } else if (resultQuantity.compareTo(BigDecimal.ZERO) == -1) {
                PartOutStockDetail partOutStockDetail = this.getPartOutDetail(partCheckStock, detail, partStock, resultQuantity);
                if(null != partOutStockDetail){
                    partOut.add(partOutStockDetail);
                }
            }
            baseDao.update(detail);
        }

        baseDao.update(partCheckStock);

        // 新建入库单
        PartInStocks partInStocks = new PartInStocks();
        // 新建出库单
        PartOutStocks partOutStocks = new PartOutStocks();
        if (partOut.size() > 0) {
            partOutStocks = this.savePartOutStock(partCheckStock, partOut, totalPosPrice, user);
        }

        if (partIn.size() > 0) {
            partInStocks = this.savePartInStock(partCheckStock, partIn, totalPisPrice, user);
        }

        if (!createVoucher(partCheckStock.getDocumentNo(), partInStocks.getPisNo(),
                partOutStocks.getPosNo())) {
            throw new ServiceException("审批失败:生成凭证模板出错");
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }


    private PartInStocks savePartInStock(PartCheckStocks partCheckStock, List<PartInStockDetail> partInStockDetails, BigDecimal totalPrice, SysUsers user){
        String code = sysCodeRulesService.createSysCodeRules("PIC_NO", partCheckStock.getStationId());
        PartInStocks partInStocks = new PartInStocks();
        partInStocks.setPisNo(code);
        partInStocks.setOriginNo(partCheckStock.getDocumentNo());
        partInStocks.setStationId(partCheckStock.getStationId());
        partInStocks.setPisType((short) 15);
        partInStocks.setCheckStatus((short) 0);
        partInStocks.setIsSab(false);
        partInStocks.setCreator(partCheckStock.getCreator());
        partInStocks.setCreateTime(partCheckStock.getApproveTime());
        partInStocks.setApproveStatus((short) 1);
        partInStocks.setPisPrice(toDouble(totalPrice));
        partInStocks.setPisCarriage(0.0);
        partInStocks.setTax(0.0);
        partInStocks.setTaxPrice(0.0);
        partInStocks.setSabCarriage(false);
        partInStocks.setPartFrom("配件盘盈入库");
        partInStocks.setSupplierName("配件盘盈入库");
        partInStocks.setCreatorUnitNo(user.getDepartmentNo());
        partInStocks.setCreatorUnitName(user.getDepartmentName());
        partInStocks.setApproverUnitId(user.getDepartment());
        partInStocks.setApproverUnitNo(user.getDepartmentNo());
        partInStocks.setApproverUnitName(user.getDepartmentName());
        partInStocks.setApproverId(user.getUserId());
        partInStocks.setApproverNo(user.getUserNo());
        partInStocks.setApprover(user.getUserFullName());
        partInStocks.setApproveTime(partCheckStock.getApproveTime());
        baseDao.save(partInStocks);
        for (PartInStockDetail in : partInStockDetails) {
            in.setPisNo(code);
            baseDao.save(in);
        }
        if(logger.isDebugEnabled()){
            logger.debug(String.format("配件盘盈入库单：%s", partInStocks.getPisNo()));
        }
        return partInStocks;
    }

    private PartOutStocks savePartOutStock(PartCheckStocks partCheckStock, List<PartOutStockDetail> partOutStockDetails, BigDecimal totalPrice, SysUsers user){
        PartOutStocks partOutStocks = new PartOutStocks();
        String code = sysCodeRulesService.createSysCodeRules("POC_NO", partCheckStock.getStationId());
        partOutStocks.setPosNo(code);
        partOutStocks.setOriginNo(partCheckStock.getDocumentNo());
        partOutStocks.setStationId(partCheckStock.getStationId());
        partOutStocks.setPosType((short) 19);
        partOutStocks.setCheckStatus((short) 0);
        partOutStocks.setIsSab(false);
        partOutStocks.setCreator(partCheckStock.getCreator());
        partOutStocks.setCreateTime(partCheckStock.getApproveTime());
        partOutStocks.setPosPrice(toDouble(totalPrice));
        partOutStocks.setFactPrice(partOutStocks.getFactPrice());
        partOutStocks.setCost(partOutStocks.getFactPrice());
        partOutStocks.setCarriage(0.0);
        partOutStocks.setPartTo("配件盘亏出库");
        partOutStocks.setCustomerName("配件盘亏出库");
        partOutStocks.setCreatorUnitNo(user.getDepartmentNo());
        partOutStocks.setCreatorUnitName(user.getDepartmentName());
        partOutStocks.setApproverUnitId(user.getDepartment());
        partOutStocks.setApproverUnitNo(user.getDepartmentNo());
        partOutStocks.setApproverUnitName(user.getDepartmentName());
        partOutStocks.setApproverId(user.getUserId());
        partOutStocks.setApproverNo(user.getUserNo());
        partOutStocks.setApprover(user.getUserFullName());
        partOutStocks.setApproveTime(partCheckStock.getApproveTime());
        partOutStocks.setApproveStatus((short) 1);
        baseDao.save(partOutStocks);
        for (PartOutStockDetail out : partOutStockDetails) {
            out.setPosNo(code);
            baseDao.save(out);
        }
        if(logger.isDebugEnabled()){
            logger.debug(String.format("配件盘亏出库单：%s", partOutStocks.getPosNo()));
        }
        return partOutStocks;
    }


    private PartInStockDetail getPartInDetail(PartCheckStocks partCheckStock, PartCheckStocksDetail detail, PartStocks partStock, BigDecimal resultQuantity){
        boolean isCreate = false;
        if(null == partStock){
            if(StringUtils.isEmpty(detail.getStockId())){
                partStock = newPartStock(partCheckStock, detail);
                detail.setStockId(partStock.getStockId());
                isCreate = true;
            }
        }
        if(null == partStock){
            throw new ServiceException(String.format("找不到配件(%s,%s)", detail.getPartName(), detail.getPartNo()));
        }

        BigDecimal dIQuantity = resultQuantity;
        BigDecimal dSQuantity = toBigDecimal(partStock.getQuantity());
        BigDecimal dSStockTotalCost = toBigDecimal(partStock.getStockTotalCost());

        if(dSQuantity.compareTo(BigDecimal.ZERO) == 1){
            detail.setCost(partStock.getCost());
        }else{
            partStock.setCost(detail.getCost());
        }
        detail.setCarriage(0d);

        partStock.setQuantity(toDouble(toBigDecimal(partStock.getQuantity()).add(resultQuantity)));
        partStock.setInventoryTime(partCheckStock.getApproveTime());
        if(StringUtils.isNotEmpty(detail.getDepositPosition())){
            partStock.setDepositPosition(detail.getDepositPosition());
        }
        partStock.setModifyTime(partCheckStock.getApproveTime());

        PartInStockDetail in = new PartInStockDetail();
        in.setPisDetailId(UUID.randomUUID().toString());
        in.setStockId(detail.getStockId());
        in.setPartId(detail.getPartId());
        in.setPartNo(detail.getPartNo());
        in.setPartName(detail.getPartName());
        in.setPartMnemonic(detail.getPartMnemonic());
        in.setProducingArea(detail.getProducingArea());
        in.setSpecModel(detail.getSpecModel());
        in.setApplicableModel(detail.getApplicableModel());
        in.setPartType(detail.getPartType());
        in.setWarehouseName(detail.getWarehouseName());
        in.setPisPrice(detail.getCost());
        in.setPisCarriage(detail.getCarriage());
        in.setNoTaxPrice(detail.getCost());
        in.setPisQuantity(toDouble(resultQuantity));
        in.setQuantityRecord(detail.getQuantityFact());
        in.setQuantitySab(0.0);
        if(isCreate){
            in.setQuantityRecord(toDouble(resultQuantity));
        }else{
            in.setQuantityRecord(partStock.getQuantity());
        }

        in.setInCostRecord(detail.getCost());
        in.setNoTaxCarriage(0d);
        in.setCarriage(0d);
        in.setPartTaxMoney(toDouble(toBigDecimal(detail.getCost()).multiply(resultQuantity)));
        in.setPartNoTaxMoney(in.getPartTaxMoney());
        in.setPartTax(0d);
        in.setPartTaxRate(0d);
        in.setPartCostTax(0d);
        in.setInTotalCost(toDouble(toBigDecimal(in.getPisPrice()).multiply(dIQuantity)));
        in.setInTotalCostRecord(toDouble(dSStockTotalCost.add(toBigDecimal(in.getInTotalCost()))));

        partStock.setStockTotalCost(toDouble(dSStockTotalCost.add(toBigDecimal(in.getInTotalCost()))));
        baseDao.update(partStock);
        return in;

    }

    private PartOutStockDetail getPartOutDetail(PartCheckStocks partCheckStock, PartCheckStocksDetail detail, PartStocks partStock, BigDecimal resultQuantity){
        if(null == partStock){
            throw new ServiceException(String.format("找不到配件(%s,%s)", detail.getPartName(), detail.getPartNo()));
        }
        BigDecimal dOQuantity = BigDecimal.ZERO.subtract(resultQuantity);
        BigDecimal dSQuantity = toBigDecimal(partStock.getQuantity());
        BigDecimal dSStockTotalCost = toBigDecimal(partStock.getStockTotalCost());

        detail.setCost(partStock.getCost());
        if(dSQuantity.compareTo(BigDecimal.ZERO) == 1){
            BigDecimal carriage = toBigDecimal(partStock.getCarriage()).divide(toBigDecimal(partStock.getQuantity()), 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(resultQuantity).multiply(toBigDecimal(-1));
            detail.setCarriage(toDouble(carriage));
        }

        partStock.setQuantity(toDouble(toBigDecimal(partStock.getQuantity()).add(resultQuantity)));
        partStock.setCarriage(toDouble(toBigDecimal(partStock.getCarriage()).subtract(toBigDecimal(detail.getCarriage()))));
        partStock.setInventoryTime(partCheckStock.getApproveTime());
        if(toBigDecimal(partStock.getQuantity()).compareTo(BigDecimal.ZERO) == -1){
            throw new ServiceException(String.format("配件%s(%s)盘存后库存数量小于0，请核实并修改后再审批",detail.getPartName(), detail.getPartNo()));
        }

        PartOutStockDetail out = new PartOutStockDetail();
        out.setPosDetailId(UUID.randomUUID().toString());
        out.setStockId(detail.getStockId());
        out.setPartNo(detail.getPartNo());
        out.setPartName(detail.getPartName());
        out.setPartMnemonic(detail.getPartMnemonic());
        out.setProducingArea(detail.getProducingArea());
        out.setSpecModel(detail.getSpecModel());
        out.setApplicableModel(detail.getApplicableModel());
        out.setPartType(detail.getPartType());
        out.setWarehouseName(detail.getWarehouseName());
        out.setPartId(partStock.getPartId());
        out.setPosPrice(toDouble(toBigDecimal(detail.getCost()).add(toBigDecimal(detail.getCarriage()))));
        out.setCost(detail.getCost());
        out.setCarriage(detail.getCarriage());
        out.setCostNoTax(partStock.getCostNoTax());
        out.setPosAgio(1.0);
        out.setLargessPrice(0.0);
        out.setPosQuantity(toDouble(BigDecimal.ZERO.subtract(resultQuantity)));
        out.setQuantitySab(0.0);
        out.setQuantityRecord(partStock.getQuantity());

        BigDecimal outTotalCost = null;
        if(toBigDecimal(partStock.getQuantity()).compareTo(BigDecimal.ZERO) == 0){
            outTotalCost = dSStockTotalCost;
        }else{
            outTotalCost = dSStockTotalCost.divide(dSQuantity, 2, BigDecimal.ROUND_HALF_UP).multiply(dOQuantity);
        }
        out.setOutTotalCost(toDouble(outTotalCost));
        out.setOutTotalCostRecord(toDouble(dSStockTotalCost.subtract(outTotalCost)));

        partStock.setStockTotalCost(toDouble(dSStockTotalCost.subtract(outTotalCost)));
        return out;
    }

    public boolean createVoucher(String documentNo, String pisNo, String posNo) {
        String sql = baseDao.getQueryStringByName("partCheckStockDS",
                new String[] { "pisNo", "posNo" }, new String[] {
                        "'" + pisNo + "'", "'" + posNo + "'" });
        return voucherAuto.generateVoucherByProc(sql, "15858000", false,
                HttpSessionStore.getSessionUser().getUserId(), documentNo);
    }

    private static BigDecimal toBigDecimal(Number number){
        if(number == null){
            return BigDecimal.ZERO;
        }
        if(number instanceof BigDecimal){
            return (BigDecimal)number;
        }else{
            return new BigDecimal(number.toString());
        }
    }

    private static Double toDouble(BigDecimal number){
        if(null == number) return null;
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
