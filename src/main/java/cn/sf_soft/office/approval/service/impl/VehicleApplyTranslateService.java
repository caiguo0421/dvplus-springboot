package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.vehicle.apply.model.VehicleApplyTranslate;
import cn.sf_soft.vehicle.apply.model.VehicleApplyTranslateDetail;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/**
 * 车辆请调
 * @author caigx
 *
 */
@Service("vehicleApplyTranslateService")
public class VehicleApplyTranslateService extends BaseApproveProcess{
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10241020";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleApplyTranslateService.class);


    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        VehicleApplyTranslate  translate = dao.get(VehicleApplyTranslate.class,documentNo);
        SysStations inStation = dao.get(SysStations.class, translate.getStationId());
        if(inStation!=null){
            translate.setInStationName(inStation.getStationName());
        }
        SysStations outStation = dao.get(SysStations.class, translate.getOutStationId());
        if(outStation!=null){
            translate.setOutStationName(outStation.getStationName());
        }
        return translate;
    }


    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        VehicleApplyTranslate translate = dao.get(VehicleApplyTranslate.class,approveDocument.getDocumentNo());
        if(translate==null){
            throw new ServiceException("未找到请调单："+approveDocument.getDocumentNo());
        }
        Set<VehicleApplyTranslateDetail>  detailSet = translate.getChargeDetail();
        if(detailSet==null || detailSet.size()==0){
            throw new ServiceException("请调单明细为空");
        }

        Iterator<VehicleApplyTranslateDetail>  detailIterator = detailSet.iterator();
        while (detailIterator.hasNext()){
            VehicleApplyTranslateDetail detail = detailIterator.next();
            //状态校验
            VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
            if(stocks==null){
                throw new ServiceException("车辆"+detail.getVehicleVin()+"在车辆库存中不存在");
            }

            if(stocks.getStatus()!=0 && stocks.getStatus()!=1&& stocks.getStatus()!=2){
                throw new ServiceException("车辆"+stocks.getVehicleVin()+"的库存状态不正确");
            }
        }
        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }


    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        VehicleApplyTranslate translate = dao.get(VehicleApplyTranslate.class,approveDocument.getDocumentNo());
        Iterator<VehicleApplyTranslateDetail>  detailIterator = translate.getChargeDetail().iterator();
        while (detailIterator.hasNext()) {
            VehicleApplyTranslateDetail detail = detailIterator.next();
            VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
            stocks.setVatNo(translate.getDocumentNo());
            stocks.setVatStationId(translate.getStationId());
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }
}
