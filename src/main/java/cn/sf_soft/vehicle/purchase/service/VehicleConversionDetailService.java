package cn.sf_soft.vehicle.purchase.service;

import cn.jiguang.common.utils.StringUtils;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.df.model.VehicleDfSapDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/28 16:28
 * @Description:
 */
@Service
public class VehicleConversionDetailService extends BaseService<VehicleConversionDetail> {

    private static final String DOCUMENT_TYPE = "车辆-在途改装";

    @Autowired
    protected FinanceDocumentEntriesDao financeDocumentEntriesDao;// 单据分录

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(VehicleConversionDetail.class, VehicleConversionDetailService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleConversionDetail> entityProxy) {
        if(entityProxy.getOperation() == Operation.CREATE){
            EntityProxy<VehicleDfSapDelivery> master = entityProxy.getMaster();
            entityProxy.getEntity().setVocdId(master.getEntity().getSelfId());
        }
    }

    @Override
    public void execute(EntityProxy<VehicleConversionDetail> entityProxy) {
        VehicleConversionDetail entity = entityProxy.getEntity();
        //如果是确认改装项目，则创建改装分录
        boolean deleteFlag = false;
        if(entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.UPDATE){
            ConversionStatus status = ConversionStatus.valueOf(entity.getStatus());
            ConversionStatus oriStatus = ConversionStatus.UNCONFIRMED;
            if(entityProxy.getOperation() == Operation.UPDATE) {
                oriStatus = ConversionStatus.valueOf(entityProxy.getOriginalEntity().getStatus());
            }
            if(status.compareTo(oriStatus) != 0 && status.compareTo(ConversionStatus.CONFIRMED) == 0) {
                this.validate(entityProxy);
                EntityProxy<VehicleDfSapDelivery> master = entityProxy.getMaster();
                String underpanNo = master.getEntity().getUnderpanNo();
                List<FinanceDocumentEntries> financeDocumentEntries = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(entity.getConversionDetailId(), DOCUMENT_TYPE);
                if (null == financeDocumentEntries || financeDocumentEntries.isEmpty()) {
                    SysUsers user = HttpSessionStore.getSessionUser();
                    financeDocumentEntriesDao.insertEntryEx(user.getDefaulStationId(), 5, (short) 65, DOCUMENT_TYPE,
                            entity.getConversionDetailId(), entity.getSupplierId(), entity.getSupplierNo(), entity.getSupplierName(),
                            (short) 70, entity.getItemCost().doubleValue(), underpanNo, underpanNo, entity.getFuturePayDate());
                } else {
                    FinanceDocumentEntries entries = null;
                    if (null != financeDocumentEntries && financeDocumentEntries.size() > 1) {
                        for (int i = 0; i < financeDocumentEntries.size(); i++) {
                            if (i == 0) {
                                entries = financeDocumentEntries.get(i);
                            } else {
                                financeDocumentEntriesDao.delete(financeDocumentEntries.get(i));
                            }
                        }
                    }
                    if (null != entries) {
                        entries.setDocumentNo(underpanNo);
                        entries.setSubDocumentNo(underpanNo);
                        entries.setDocumentAmount(entity.getItemCost().doubleValue());
                        entries.setLeftAmount(entity.getItemCost().doubleValue());
                        financeDocumentEntriesDao.updateFinanceDocumentEntries(entries);
                    }

                }
            }else if(status.compareTo(oriStatus) != 0 && status.compareTo(ConversionStatus.UNCONFIRMED) == 0){  //反确认则删除分录
                deleteFlag = true;
            }
        }else if(entityProxy.getOperation() == Operation.DELETE){
            deleteFlag = true;
        }
        if(deleteFlag){ //删除分录
            List<FinanceDocumentEntries> financeDocumentEntries = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(entity.getConversionDetailId(), DOCUMENT_TYPE);
            if(null != financeDocumentEntries && !financeDocumentEntries.isEmpty()){
                for (FinanceDocumentEntries entries : financeDocumentEntries) {
                    financeDocumentEntriesDao.delete(entries);
                }
            }
        }
    }

    /**
     * 数据校验，仅在确认时校验
     * @param entityProxy
     */
    private void validate(EntityProxy<VehicleConversionDetail> entityProxy){
        if(entityProxy.getOperation() ==Operation.CREATE || entityProxy.getOperation() ==Operation.UPDATE){
            VehicleConversionDetail item = entityProxy.getEntity();
            if(StringUtils.isEmpty(item.getItemId())){
                throw new ServiceException("改装项目不能为空");
            }
            if(StringUtils.isEmpty(item.getItemNo())){
                throw new ServiceException("改装项目编码不能为空");
            }
            if(StringUtils.isEmpty(item.getItemName())){
                throw new ServiceException("改装项目名称不能为空");
            }
            if(StringUtils.isEmpty(item.getSupplierId())){
                throw new ServiceException(String.format("改装项目(%s)中供应商不能为空",item.getItemName()));
            }
            if(StringUtils.isEmpty(item.getSupplierNo())){
                throw new ServiceException(String.format("改装项目(%s)中供应商编码不能为空",item.getItemName()));
            }
            if(StringUtils.isEmpty(item.getSupplierName())){
                throw new ServiceException(String.format("改装项目(%s)中供应商名称不能为空",item.getItemName()));
            }
            if(null == item.getItemCost()){
                throw new ServiceException(String.format("改装项目(%s)中改装金额不能为空",item.getItemName()));
            }
            if(null == item.getConversionTime()){
                throw new ServiceException(String.format("改装项目(%s)中改装日期不能为空",item.getItemName()));
            }
            if(null == item.getFuturePayDate()){
                throw new ServiceException(String.format("改装项目(%s)中预计付款日期不能为空",item.getItemName()));
            }

            //如果提货单没有在途确认，禁止确认改装项目
            ConversionStatus status = ConversionStatus.valueOf(item.getStatus());
            if(status == ConversionStatus.CONFIRMED){
                EntityProxy<VehicleDfSapDelivery> master = entityProxy.getMaster();
                VehicleDfSapDelivery delivery = master.getEntity();
                if(null == delivery.getComfirmStatus() || delivery.getComfirmStatus() == 0){
                    throw new ServiceException("提货单未在途确认，禁止确认改装项目");
                }

            }
        }
    }
}
