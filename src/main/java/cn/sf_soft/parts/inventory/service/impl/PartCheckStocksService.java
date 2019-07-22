package cn.sf_soft.parts.inventory.service.impl;

import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.finance.voucher.service.VoucherAuto;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.service.impl.PartCheckStockApprove;
import cn.sf_soft.parts.inventory.dao.hbb.PartCheckStocksDao;
import cn.sf_soft.parts.inventory.model.CheckRange;
import cn.sf_soft.parts.inventory.model.PartCheckStocks;
import cn.sf_soft.parts.inventory.model.PartCheckStocksDetail;
import cn.sf_soft.parts.stockborwse.model.PartStocks;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service("partCheckStockService")
public class PartCheckStocksService {

    @Autowired
    private PartCheckStocksDao dao;
    @Autowired
    private VoucherAuto voucherAuto;
    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    private PartCheckStockApprove partCheckStockApprove;

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartCheckStocksService.class);

    public void setDao(PartCheckStocksDao dao) {
        this.dao = dao;
    }

    public void setVoucherAuto(VoucherAuto voucherService) {
        this.voucherAuto = voucherService;
    }


    public PageModel<PartCheckStocks> getPartCheckStock(List<String> stationIds, JsonObject condition, String orderBy, int pageNo, int pageSize) {
        return dao.getPartCheckStock(stationIds, condition, orderBy, pageNo, pageSize);
    }

    public PartCheckStocks getPartCheckStockById(String documentNo){
        return dao.getPartCheckStockById(documentNo);
    }

    public List<PartCheckStocksDetail> getPartCheckStockDetail(String pcsNo, JsonObject condition, String orderBy) {
        return dao.getPartCheckStockDetail(pcsNo, condition, orderBy);
    }

    public Map<String, Object> getRangeInitData(List<String> stationIds) {
        Map<String, Object> resultMap = new HashMap<>(2);
        if (stationIds != null && stationIds.size() > 0) {
            String stationSql = "SELECT　a.station_id, a.station_name from dbo.sys_stations a WHERE a.station_id in ('" + StringUtils.join(stationIds, "','") + "')";
            resultMap.put("stations", dao.listInSql(stationSql, null).getData());

            String partWarehouseSql = "SELECT a.warehouse_id,a.warehouse_no,a.warehouse_name,a.warehouse_type,a.warehouse_status,a.station_id " + "FROM dbo.base_part_warehouses a WHERE a.station_id in ('" + StringUtils.join(stationIds, "','") + "')";
            resultMap.put("partWarehouses", dao.listInSql(partWarehouseSql, null).getData());
        }
        return resultMap;
    }

    public PartCheckStocks genPartCheckStocks(CheckRange range, List<String> stationIds) {
        SysUsers user = HttpSessionStore.getSessionUser();

        PartCheckStocks partCheckStocks = new PartCheckStocks();
        String pcsNo = sysCodeService.createSysCodeRules("PCS_NO", user.getDefaulStationId());
        partCheckStocks.setDocumentNo(pcsNo);
        partCheckStocks.setStationId(user.getDefaulStationId());
        partCheckStocks.setCreateTime(new Timestamp(System.currentTimeMillis()));
        partCheckStocks.setCreator(user.getUserFullName());
        partCheckStocks.setCreatorNo(user.getUserNo());
        partCheckStocks.setCreatorUnitName(user.getInstitution().getUnitName());
        partCheckStocks.setCreatorUnitNo(user.getInstitution().getUnitNo());
        partCheckStocks.setStatus(Constant.DocumentStatus.IN_MAKING);
        partCheckStocks.setPcsPrice(0.00D);
        genPartCheckStocksDetail(partCheckStocks, range, stationIds);

        dao.save(partCheckStocks);

        return partCheckStocks;
    }

    /**
     * 生成盘存单-明细
     *
     * @param partCheckStocks
     * @param range
     */
    private void genPartCheckStocksDetail(PartCheckStocks partCheckStocks, CheckRange range, List<String> stationIds) {
        String sql = "SELECT %s c.cost_ref,a.cost,a.carriage,a.quantity,a.deposit_position,a.stock_id,\n" +
                "c.part_id,c.part_no,c.part_name,c.part_mnemonic,c.producing_area,c.part_type,c.spec_model,c.applicable_model,\n" +
                "c.color,c.material,c.unit,b.warehouse_id,b.warehouse_name, CASE WHEN isnull(a.quantity,0) > 0 \n" +
                "THEN convert(DECIMAL(19,2),round((isnull(a.stock_total_cost,0) / a.quantity),2)) ELSE c.cost_ref END AS stock_cost_new" +
                " FROM part_stocks AS a \n"
                + " LEFT JOIN base_part_warehouses AS b ON a.warehouse_id = b.warehouse_id\n"
                + " LEFT JOIN base_part_catalogs AS c ON a.part_id = c.part_id\n"
                + " LEFT JOIN base_suppliers AS d ON c.default_supplier_id = d.supplier_id\n"
                + " WHERE a.is_active = 1 %s ORDER BY newid()";

        List<String> condition = new ArrayList<>();
        if (StringUtils.isBlank(range.getStationId()) || "0".equals(range.getStationId())) {
            condition.add("a.station_id in ('" + StringUtils.join(stationIds, "','") + "')");
        } else {
            condition.add("a.station_id ='" + range.getStationId() + "'");
        }

        if (StringUtils.isNotBlank(range.getWarehouseId()) && !"0".equals(range.getWarehouseId())) {
            condition.add("a.warehouse_id ='" + range.getWarehouseId() + "'");
        }

        if (StringUtils.isNotBlank(range.getDepositPosition())) {
            String[] postionArray = range.getDepositPosition().split(",");
            if (postionArray != null && postionArray.length > 0) {
                List<String> positionCondition = new ArrayList<>(postionArray.length);
                for (String position : postionArray) {
                    positionCondition.add("a.deposit_position LIKE '%" + position + "%'");
                }

                condition.add("( " + StringUtils.join(positionCondition, " OR ") + " )");
            }
        }

        if (StringUtils.isNotBlank(range.getBeginDate())) {
            condition.add("DATEDIFF(day,'" + range.getBeginDate() + "',a.last_pos_time)>=0");
        }

        if (StringUtils.isNotBlank(range.getEndDate())) {
            condition.add("DATEDIFF(day,'" + range.getEndDate() + "',a.last_pos_time)<=0");
        }

        String codition = "";
        if (condition.size() > 0) {
            codition = " AND " + StringUtils.join(condition, " AND ");
        }
        String topN = "";
        if (range.getCheckNum() != null && range.getCheckNum() > 0) {
            topN = "top " + range.getCheckNum() + " ";
        }


        sql = String.format(sql, topN, codition);

        logger.debug(sql);

        List<Object> resultList = dao.listInSql(sql, null).getData();
        if (resultList == null || resultList.size() == 0) {
            throw new ServiceException("生成盘存单失败：根据条件查不到配件数据");
        }

        List<PartCheckStocksDetail> partsCheckDetails = partCheckStocks.getPartCheckStockDetails();
        if (partsCheckDetails == null) {
            partsCheckDetails = new ArrayList<>();
            partCheckStocks.setPartCheckStockDetails(partsCheckDetails);
        }

        for (Object result : resultList) {
            Map<String, Object> resultMap = (Map<String, Object>) result;

            PartCheckStocksDetail stocksDetail = new PartCheckStocksDetail();
            stocksDetail.setPcsDetailId(UUID.randomUUID().toString());
            stocksDetail.setDocumentNo(partCheckStocks.getDocumentNo());
            stocksDetail.setCost((Double) resultMap.get("stock_cost_new"));
            stocksDetail.setCarriage((Double) resultMap.get("carriage"));
            stocksDetail.setQuantityStock((Double) resultMap.get("quantity"));
            stocksDetail.setQuantityFact((Double) resultMap.get("quantity"));
            stocksDetail.setDepositPosition((String) resultMap.get("deposit_position"));
            stocksDetail.setStockId((String) resultMap.get("stock_id"));
            stocksDetail.setPartId((String) resultMap.get("part_id"));
            stocksDetail.setPartNo((String) resultMap.get("part_no"));
            stocksDetail.setPartName((String) resultMap.get("part_name"));
            stocksDetail.setPartMnemonic((String) resultMap.get("part_mnemonic"));
            stocksDetail.setProducingArea((String) resultMap.get("producing_area"));
            stocksDetail.setPartType((String) resultMap.get("part_type"));
            stocksDetail.setSpecModel((String) resultMap.get("spec_model"));
            stocksDetail.setApplicableModel((String) resultMap.get("applicable_model"));
            stocksDetail.setColor((String) resultMap.get("color"));
            stocksDetail.setMaterial((String) resultMap.get("material"));
            stocksDetail.setUnit((String) resultMap.get("unit"));
            stocksDetail.setWarehouseName((String) resultMap.get("warehouse_name"));
            stocksDetail.setWarehouseId((String) resultMap.get("warehouse_id"));

            BigDecimal quantity = toBigDecimal(stocksDetail.getQuantityFact());
            if(quantity.compareTo(BigDecimal.ZERO) == 0){
                Double nowPrice = dao.getNowPrice(stocksDetail.getStockId(), stationIds);
                if(null != nowPrice){
                    stocksDetail.setCost(nowPrice);
                }else{
                    double costRef = Tools.objToDouble(resultMap.get("cost_ref"));
                    stocksDetail.setCost(costRef);
                }
                stocksDetail.setCheckTotalCost(0.00d);
            }else{
                BigDecimal cost = toBigDecimal(stocksDetail.getCost());
                stocksDetail.setCheckTotalCost(toDouble(cost.multiply(quantity)));
            }
            dao.save(stocksDetail);
            partsCheckDetails.add(stocksDetail);
        }
    }


    private BigDecimal toBigDecimal(Number number){
        if(null == number){
            return BigDecimal.ZERO;
        }
        if(number instanceof BigDecimal){
            return (BigDecimal)number;
        }
        return new BigDecimal(number.toString());
    }

    private Double toDouble(BigDecimal number){
        if(null == number){
            return 0.00d;
        }
        return number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public ResponseMessage checkPartStock(PartCheckStocks newPartCheckStock) {
        List<PartCheckStocksDetail> details = newPartCheckStock.getPartCheckStockDetails();
        ResponseMessage result = new ResponseMessage();
        if(null != details && !details.isEmpty()){
            PartCheckStocks partCheckStock = null;
            try {
                SysUsers user = HttpSessionStore.getSessionUser();
                partCheckStock = dao.get(PartCheckStocks.class, newPartCheckStock.getDocumentNo());
                if(partCheckStock.getStatus() > Constant.DocumentStatus.IN_MAKING){
                    result.setData(null);
                    result.setRet(ResponseMessage.RET_SERVER_ERR);
                    result.setMsg(String.format("盘点单(%s)当前状态(%s)不允许修改", partCheckStock.getDocumentNo(), partCheckStock.getStatus()));
                    return result;
                }
                partCheckStock.setPcsPrice(newPartCheckStock.getPcsPrice());
                partCheckStock.setModifier(user.getUserFullName());
                partCheckStock.setModifyTime(new Timestamp(System.currentTimeMillis()));
                // 校验
                List<PartCheckStocksDetail> errPartResult = new ArrayList<PartCheckStocksDetail>();

                errPartResult.addAll(checkQuantityChanged(details));
                if (errPartResult.size() > 0) {

                    for(PartCheckStocksDetail d: errPartResult){
                        dao.update(d);
                    }
                    result.setData(errPartResult);
                    result.setRet(ResponseMessage.RET_SUCC);
                    result.setErrcode(5);
                    result.setMsg("部分配件库存数量已发生变化，请更新该配件信息");
                    return result;
                }

                for (PartCheckStocksDetail d : details) {
                    PartCheckStocksDetail detail1 = dao.get(PartCheckStocksDetail.class, d.getPcsDetailId());
                    if(null == detail1){
                        throw new ServiceException(String.format("配件(%s,%s)不存在",d.getPartNo(), d.getPartName()));
                    }
                    detail1.setDepositPosition(d.getDepositPosition());
                    detail1.setQuantityFact(d.getQuantityFact());
                    detail1.setCheckStatus(d.getCheckStatus());
                    detail1.setCheckTime(d.getCheckTime());
                    detail1.setCheckUserNo(d.getCheckUserNo());
                    detail1.setCheckUserName(d.getCheckUserName());
                    detail1.setRemark(d.getRemark());

                    BigDecimal quantity = toBigDecimal(detail1.getQuantityFact());
                    BigDecimal cost = toBigDecimal(detail1.getCost());
                    detail1.setCheckTotalCost(toDouble(cost.multiply(quantity)));
                    dao.update(detail1);
                }
                dao.update(partCheckStock);
            }catch (Exception ex){
                logger.error("保存盘存单明细出错", ex);
                result.setData(null);
                result.setRet(ResponseMessage.RET_SERVER_ERR);
                result.setMsg("保存盘存单出错！");
                return result;
            }
            result.setData(partCheckStock);
            result.setRet(ResponseMessage.RET_SUCC);
            result.setMsg("保存成功！");
            return result;
        }else{
            result.setData(null);
            result.setRet(ResponseMessage.RET_PARAM_ERR);
            result.setMsg("没有可保存的数据！");
            return result;
        }
    }

    public PartCheckStocks submit(String documentNo, String unitId, String remark) {
        if(StringUtils.isEmpty(documentNo)){
            throw new ServiceException("必须提供盘存单号");
        }
        if(StringUtils.isEmpty(unitId)){
            throw new ServiceException("必须提供部门");
        }
        PartCheckStocks partCheckStock = dao.get(PartCheckStocks.class, documentNo);
        if(null == partCheckStock){
            throw new ServiceException(String.format("盘存单号%s无效", documentNo));
        }
        if(partCheckStock.getStatus() < Constant.DocumentStatus.SUBMITED){
            SysUnits unit = dao.get(SysUnits.class, unitId);
            if(null == unit){
                logger.error(String.format("找不到指定的部门（%s）",unitId));
                throw new ServiceException("找不到指定的部门");
            }
            SysUsers user = HttpSessionStore.getSessionUser();
            partCheckStock.setUserId(user.getUserId());
            partCheckStock.setUserNo(user.getUserNo());
            partCheckStock.setUserName(user.getUserName());
            partCheckStock.setSubmitStationId(user.getDefaulStationId());
            partCheckStock.setSubmitStationName(user.getStationName());
            partCheckStock.setSubmitTime(new Timestamp(System.currentTimeMillis()));
            partCheckStock.setDepartmentId(unit.getUnitId());
            partCheckStock.setDepartmentNo(unit.getUnitNo());
            partCheckStock.setDepartmentName(unit.getUnitName());
            partCheckStock.setRemark(remark);
            dao.save(partCheckStock);
            String modifyTime = null != partCheckStock.getModifyTime() ? TimestampUitls.format(partCheckStock.getModifyTime(), "yyyy-MM-dd HH:mm:ss.SSS") : null;
            ApproveResult approveResult = partCheckStockApprove.submitRecord(true, partCheckStock.getDocumentNo(), "158580", remark, modifyTime, Constant.OSType.MOBILE);
            if(approveResult.getRtnCode() != 0 && StringUtils.isNotEmpty(approveResult.getErrMsg())){
                throw new ServiceException(approveResult.getErrMsg());
            }
            return partCheckStock;
        }else{
            throw new ServiceException(String.format("盘存单无法提交(状态码：%s)", partCheckStock.getStatus()));
        }

    }

    private List<PartCheckStocksDetail> checkQuantityChanged(
            List<PartCheckStocksDetail> details) {
        List<PartCheckStocksDetail> errPart = new ArrayList<PartCheckStocksDetail>();
        for (PartCheckStocksDetail detail : details) {
            PartStocks part = getPartStocksById(detail.getStockId());
            Double quantityStock = detail.getQuantityStock();
            Double quantity = part.getQuantity();
            if (quantityStock.compareTo(quantity) != 0) {
                detail.setQuantityStockNow(quantity);
                errPart.add(detail);
                continue;
            }
        }
        return errPart;
    }

    private PartStocks getPartStocksById(String id) {
        return dao.get(PartStocks.class, id);
    }




}
