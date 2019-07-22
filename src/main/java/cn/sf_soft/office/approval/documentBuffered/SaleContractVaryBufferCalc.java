package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VehicleSaleContractsVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同变更-缓存数据接口
 * 
 * @author caigx
 *
 */
@Service("saleContractVaryBufferCalc")
public class SaleContractVaryBufferCalc extends ApprovalDocumentCalc {

	public int documentClassId = 10000;

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractVaryBufferCalc.class);
	private static String bufferCalcVersion = "20170102.2";

	private static String moduleId = "102025";
	
	static int contractBackground = (218*256+230)*256+192;
	static int[] vnoStatisticsBackground = new int[]{(217*256+231)*256+247, (252*256+219)*256+192};

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

	@Autowired
	private SaleContractDetailVaryBufferCacl saleContractDetailVaryBufferCacl;

	@Autowired
	private SaleContractDetailBufferCacl saleContractDetailBufferCacl;

	/*@Override
	public JsonObject getDocumentBuffered(String moduleId, String documentNo){
		try{
			long beginTime = System.currentTimeMillis();
			if(!lockDocument(moduleId, documentNo)){
				throw new ServiceException("可能有其他客户端在操作该文档，目前不能操作它。模块编号："+moduleId+"业务单号："+documentNo);
			}
			long t = System.currentTimeMillis();
			if(t-beginTime>500){
				logger.debug(String.format("等待锁定缓存文档, 审批单号：%s，花费：%d ms", documentNo,
						(System.currentTimeMillis() - beginTime)));
			}

			MobileDocumentBuffered bufferedDoc = compute(false, moduleId, documentNo);
			logger.debug(String.format("计算缓存文档, 审批单号：%s，花费：%d ms", documentNo,
					(System.currentTimeMillis() - beginTime)));
			JsonObject jo = new JsonObject();
			jo.addProperty("moduleId", moduleId);
			jo.addProperty("documentId", bufferedDoc.getBusiBillId());
			jo.addProperty("documentNo", documentNo);
			ApproveDocuments<?> approveDoc= (ApproveDocuments<?>)bufferedDoc.getBusiBill();
			jo.addProperty("stationId", approveDoc.getStationId());
			// 数据版本
			jo.addProperty("modifyTime", sdfWithMilli.format(this.getVehicleSaleContractsVary(bufferedDoc).getModifyTime()));
			jo.add("board", board2Json(bufferedDoc.getBoard()));
			return jo;
			
		}finally{
			unlockDocument(moduleId, documentNo);
		}
	}*/

	@Override
	public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId, String documentNo){
		MobileDocumentBuffered bufferedDoc = loadDocumentBuffered(moduleId, documentNo);
		
		// 设置业务单据：车辆销售合同变更单
		ApproveDocuments<?> approveDoc = (ApproveDocuments<?>)bufferedDoc.getBusiBill();
		VehicleSaleContractsVary contractsVery = dao.get(VehicleSaleContractsVary.class, approveDoc.getDocumentNo());
		if (contractsVery == null) {
			throw new ServiceException("未找到审批单号对应的合同变更单：" + approveDoc.getDocumentNo());
		}
		bufferedDoc.setRelatedObjects(new Object[] {contractsVery});
		
		if (this.isNeedToCompute(bufferedDoc)) {
			computeStaticFields(bufferedDoc);
		}
		if(!onlyStatic){
			computeDynamicFields(bufferedDoc);
		}
		return bufferedDoc;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void computeStaticFields(MobileDocumentBuffered doc) {
		if (null==doc) {
			throw new ServiceException("无法计算空文档。");
		}
		ApproveDocuments<?> approveDoc= (ApproveDocuments<?>)doc.getBusiBill();
		if (approveDoc== null) {
			throw new ServiceException("没有审批单，无法计算。");
		}
		if (!moduleId.equals(approveDoc.getModuleId())) {
			throw new ServiceException(approveDoc.getDocumentNo() + "此审批非合同变更审批单");
		}

		VehicleSaleContractsVary contractsVery = getVehicleSaleContractsVary(doc);
		if (contractsVery == null) {
			throw new ServiceException("未找到审批单号对应的合同变更单：" + approveDoc.getDocumentNo());
		}
		
		// 如果buffer_version 和 buffered_doc_version 一致,直接返回
		if (!this.isNeedToCompute(doc)) {
			return;
		}
		if(null != doc.getBoard() && null != doc.getBoard().getFields() && doc.getBoard().getFields().size()>0){
			List<MobileBoardField> fields = doc.getBoard().getFields();
			for(int i=fields.size()-1; i>=0; i--){
				dao.delete(fields.get(i));
				fields.remove(i);
			}
			dao.flush();
		}
		if(null != doc.getBoard()&& null!=doc.getBoard().getTitles() && doc.getBoard().getTitles().size()>0){
			List<MobileBoardTitle> titles = doc.getBoard().getTitles();
			for(int i =titles.size()-1;i>=0;i--){
				dao.delete(titles.get(i));
				titles.remove(i);
			}
			dao.flush();
		}

		doc.setBusiBillId(approveDoc.getDocumentId());
		doc.setBufferCalcVersion(bufferCalcVersion);
		doc.setBusiBillVersion(this.getBusiBillVersion(approveDoc));
		doc.setComputeTime(new Timestamp(System.currentTimeMillis()));// 计算时间

		// 合同总况
		MobileBoardBuffered board = doc.getBoard();
		if (null == board) {
			board = org.springframework.beans.BeanUtils.instantiate(MobileBoardBuffered.class);
			doc.setBoard(board);
			board.setDocument(doc);
		}
		
		DocTitle docTitle = new DocTitle("销售合同变更审批");
		board.setDocTitle(gson.toJson(docTitle));
		
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle("合同总况");
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);
		
		List<MobileBoardField> fields = board.getFields();
		if (null == fields) {
			fields = new ArrayList<MobileBoardField>();
			board.setFields(fields);
		}

		short sn = 1;
		// 合同号
		Property contractNo = getContractNo(contractsVery);
		if (contractNo != null) {
			saveProperty(board, "vno",   sn++, contractNo);
		}

		// 销售员
		Property seller = getSeller(contractsVery);
		if (seller != null) {
			saveProperty(board, "seller",   sn++, seller);
		}

		// 客户
		Property customerName = getCustomerName(contractsVery);
		if (customerName != null) {
			saveProperty(board, "customerName",   sn++, customerName);
		}

		boolean isFirstVehicleExpanded = true; //第一台车辆是否张开
		// 变更前合同统计
		StringBuffer buf = new StringBuffer();
		buf = new StringBuffer();
		buf.append("SELECT count(distinct m.oriVehicleVno)");	buf.append("\n");
		buf.append(", count(m.notApproved)");	buf.append("\n");
		buf.append(", count(m.notModified)");	buf.append("\n");
		buf.append(", count(m.isModified)");	buf.append("\n");
		buf.append(", count(m.isAborted)");	buf.append("\n");
		buf.append(", sum(case when m.notModified=1 or m.isModified=1 or m.isAborted=1 then d.profitPf else 0 end)");	buf.append("\n");
		buf.append(", sum(case when m.notModified=1 or m.isModified=1 or m.isAborted=1 then m.oriVehiclePriceTotal else 0 end)");	buf.append("\n");
		buf.append("from VehicleSaleContractDetailVaryMerge as m");	buf.append("\n");
		buf.append("left outer join m.vwVehicleSaleContractDetail as d");	buf.append("\n");
		buf.append("where (m.documentNo is null or m.documentNo=?) and m.contractNo = ? and m.oriVehicleVno is not null");	buf.append("\n");
		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(), 
				this.getApproveDocument(doc).getDocumentNo(), contractsVery.getContractNo());
		long vnoCountOri=0, vCountOri=0, notApproved=0;
		double profitOri=0, priceOri=0;
		if(data1!=null && data1.size()==1){
			Object[] row = (Object[])data1.get(0);
			vnoCountOri = row[0]!=null?(long)row[0]:0;
			notApproved = row[1]!=null?(long)row[1]:0;
			vCountOri = (row[2]!=null?(long)row[2]:0) + (row[3]!=null?(long)row[3]:0) + (row[4]!=null?(long)row[4]:0);
			profitOri = row[5]!=null?(double)row[5]:0;
			priceOri = row[6]!=null?(double)row[6]:0;
		}
		
		// 变更后合同统计
		buf = new StringBuffer();
		buf.append("SELECT count(distinct m.vehicleVno)");	buf.append("\n");
		buf.append(", count(m.notModified)");	buf.append("\n");
		buf.append(", count(m.isModified)");	buf.append("\n");
		buf.append(", count(m.isNew)");	buf.append("\n");
		buf.append(", count(m.isAborted)");	buf.append("\n");
		buf.append(", sum(case when m.isModified=1 or m.isNew=1 then v.profitPf");	buf.append("\n");
		buf.append("    when m.notModified=1 then d.profitPf when m.isAborted=1 then -d.profitPf else 0 end)");	buf.append("\n");
		buf.append(", sum(case when m.isModified=1 or m.isNew=1 then m.vehiclePriceTotal");	buf.append("\n");
		buf.append("    when m.notModified=1 then m.oriVehiclePriceTotal when m.isAborted=1 then -m.oriVehiclePriceTotal else 0 end)");	buf.append("\n");
		buf.append("from VehicleSaleContractDetailVaryMerge as m");	buf.append("\n");
		buf.append("left outer join m.vwVehicleSaleContractDetailVary as v");	buf.append("\n");
		buf.append("left outer join m.vwVehicleSaleContractDetail as d");	buf.append("\n");
		buf.append("where (m.documentNo is null or m.documentNo=?) and m.contractNo = ?");	buf.append("\n");
		buf.append(" and (m.notApproved is null or m.notApproved=0)");	buf.append("\n");
		data1 = (List<Object>) dao.findByHql(buf.toString(), 
				this.getApproveDocument(doc).getDocumentNo(), contractsVery.getContractNo());
		long vnoCountNew=0, vCountNew=0;
		double profitNew=0, priceNew=0;
		if(data1!=null && data1.size()==1){
			Object[] row = (Object[])data1.get(0);
			vnoCountNew = row[0]!=null?(long)row[0]:0;
			vCountNew = (row[1]!=null?(long)row[1]:0) + (row[2]!=null?(long)row[2]:0) + 
					(row[3]!=null?(long)row[3]:0);
			profitNew = row[5]!=null?(double)row[5]:0;
			priceNew = row[6]!=null?(double)row[6]:0;
		}
		// 合同统计信息
		if(vnoCountOri>1 || vnoCountNew>1){
			sn = createVnoProperties(board, null, sn, null, notApproved,  
					vnoCountOri, vCountOri, profitOri, priceOri,
					vnoCountNew, vCountNew, profitNew, priceNew);
		}
		// 车型统计信息
		if(vCountOri > vnoCountOri || vCountNew > vnoCountNew){
			isFirstVehicleExpanded = false;
			buf = new StringBuffer();
			buf.append("SELECT m.oriVehicleVno");	buf.append("\n");
			buf.append(", count(m.notApproved)");	buf.append("\n");
			buf.append(", count(m.notModified)");	buf.append("\n");
			buf.append(", count(m.isModified)");	buf.append("\n");
			buf.append(", count(m.isAborted)");	buf.append("\n");
			buf.append(", sum(case when m.notModified=1 or m.isModified=1 or m.isAborted=1 then d.profitPf else 0 end)");	buf.append("\n");
			buf.append(", sum(case when m.notModified=1 or m.isModified=1 or m.isAborted=1 then m.oriVehiclePriceTotal else 0 end)");	buf.append("\n");
			buf.append("from VehicleSaleContractDetailVaryMerge as m");	buf.append("\n");
			buf.append("left outer join m.vwVehicleSaleContractDetail as d");	buf.append("\n");
			buf.append("where (m.documentNo is null or m.documentNo=?) and m.contractNo = ? and m.oriVehicleVno is not null");	buf.append("\n");
			buf.append("group by m.oriVehicleVno order by m.oriVehicleVno");	buf.append("\n");
			data1 = (List<Object>) dao.findByHql(buf.toString(), 
					this.getApproveDocument(doc).getDocumentNo(), contractsVery.getContractNo());
			
			buf = new StringBuffer();
			buf.append("SELECT m.vehicleVno");	buf.append("\n");
			buf.append(", count(m.notModified)");	buf.append("\n");
			buf.append(", count(m.isModified)");	buf.append("\n");
			buf.append(", count(m.isNew)");	buf.append("\n");
			buf.append(", count(m.isAborted)");	buf.append("\n");
			buf.append(", sum(case when m.isModified=1 or m.isNew=1 then v.profitPf");	buf.append("\n");
			buf.append("    when m.notModified=1 then d.profitPf else 0 end)");	buf.append("\n");
			buf.append(", sum(case when m.isModified=1 or m.isNew=1 then m.vehiclePriceTotal");	buf.append("\n");
			buf.append("    when m.notModified=1 then m.oriVehiclePriceTotal else 0 end)");	buf.append("\n");
			buf.append("from VehicleSaleContractDetailVaryMerge as m");	buf.append("\n");
			buf.append("left outer join m.vwVehicleSaleContractDetailVary as v");	buf.append("\n");
			buf.append("left outer join m.vwVehicleSaleContractDetail as d");	buf.append("\n");
			buf.append("where (m.documentNo is null or m.documentNo=?) and m.contractNo = ?");	buf.append("\n");
			buf.append(" and (m.notApproved is null or m.notApproved=0)");	buf.append("\n");
			buf.append("group by m.vehicleVno order by m.vehicleVno");	buf.append("\n");
			List<Object> data2 = (List<Object>) dao.findByHql(buf.toString(), 
					this.getApproveDocument(doc).getDocumentNo(), contractsVery.getContractNo());
			int i=0, j=0, l=0;
			Object[] row;
			String vno;
			int background;
			while(i<data2.size()){
				row = (Object[])data2.get(i++);
				vno = (String)row[0];
				vCountNew = (row[1]!=null?(long)row[1]:0) + (row[2]!=null?(long)row[2]:0) + 
						(row[3]!=null?(long)row[3]:0);
				profitNew = row[5]!=null?(double)row[5]:0;
				priceNew = row[6]!=null?(double)row[6]:0;
				Integer m = findVno(vno, data1);
				if(null==m){
					notApproved = 0;
					vCountOri = 0;
					profitOri = 0;
					priceOri = 0;
				}else{
					row = (Object[])data1.get(m);
					notApproved = row[1]!=null?(long)row[1]:0;
					vCountOri = (row[2]!=null?(long)row[2]:0) + (row[3]!=null?(long)row[3]:0) + (row[4]!=null?(long)row[4]:0);
					profitOri = row[5]!=null?(double)row[5]:0;
					priceOri = row[6]!=null?(double)row[6]:0;
					data1.set(m, null);
				}
				background = vnoStatisticsBackground[l++%2];
				sn = createVnoProperties(board, background, sn, vno, notApproved,  
						0, vCountOri, profitOri, priceOri, 
						0, vCountNew, profitNew, priceNew);
			}
			while(j<data1.size()){
				row = (Object[])data1.get(j++);
				if(null==row)
					continue;
				vno = (String)row[0];
				notApproved = row[1]!=null?(long)row[1]:0;
				vCountOri = (row[2]!=null?(long)row[2]:0) + (row[3]!=null?(long)row[3]:0) + (row[4]!=null?(long)row[4]:0);
				profitOri = row[5]!=null?(double)row[5]:0;
				priceOri = row[6]!=null?(double)row[6]:0;
				background = vnoStatisticsBackground[l++%2];
				sn = createVnoProperties(board, background, sn, vno, notApproved,  
						0, vCountOri, profitOri, priceOri, 
						0, 0, 0, 0);
			}
		}

		int n = 1;
		// 变更车辆
		List<VehicleSaleContractDetailVary> contractDetailVarys = saleContractsVaryDao
				.getContractDetailVaryByDocumentNo(contractsVery.getDocumentNo());
		for (int i = 0; i < contractDetailVarys.size(); i++) {
			MobileBoardBuffered subobjectBoard = null;
			if(i==0){//第一辆车
				 subobjectBoard = saleContractDetailVaryBufferCacl.computeFields(contractDetailVarys.get(i),n,isFirstVehicleExpanded);
			}else{
				 subobjectBoard = saleContractDetailVaryBufferCacl.computeFields(contractDetailVarys.get(i),n,false);
			}
			createSubObjectField(board,"VehicleSaleContractDetailVaryList",AppBoardFieldType.Subobject,sn++,subobjectBoard);
			n++;
		}

		// 未变更车辆
		List<VehicleSaleContractDetail> contractDetails = (List<VehicleSaleContractDetail>) dao
				.findByHql(
						"from VehicleSaleContractDetail  as a where not exists (select 1 from VehicleSaleContractDetailVary as b where a.contractDetailId = b.contractDetailId and b.documentNo = ?) and a.contractNo = ? and status>=50",
						contractsVery.getDocumentNo(), contractsVery.getContractNo());
		if (contractDetails != null && contractDetails.size() > 0) {
			MobileBoardBuffered subobjectBoard = saleContractDetailBufferCacl.computeFields(contractDetails, n);
			createSubObjectField(board,"VehicleSaleContractDetailList",AppBoardFieldType.Subobject,sn++,subobjectBoard);
		}
		
		
		dao.flush();
		dao.update(doc);
	}

	@Override
	public void computeDynamicFields(MobileDocumentBuffered doc) {

	}
	
	private Integer findVno(String vno, List<Object> data){
		if(null==vno || null==data)
			return null;
		for(int i=0; i<data.size(); i++){
			if(null==data.get(i))
				continue;
			Object[] row = (Object[])data.get(i);
			if(vno.equals((String)row[0]))
				return i;
		}
		return null;
	}
	private short createVnoProperties(MobileBoardBuffered board, Integer background, short sn, 
			String vno, long notApproved,
			long vnoCountOri, long vCountOri, double profitOri, double priceOri, 
			long vnoCountNew, long vCountNew, double profitNew, double priceNew){
		if(null==vno){
			createProperty(board, "vnoCount", sn++, true, true, null, 
					"合同车型数", formatDiff(vnoCountNew, vnoCountOri),
					null, null, null, null, background);
		}else{
			createProperty(board, "vehicleVno", sn++, true, true, null, 
					"车型", vno, null, null, null, null, background);
		}
		if(notApproved!=0)
			createProperty(board, "notApproved", sn++, true, true, null, 
					"未审车辆数", String.valueOf(notApproved),
					null, null, null, null, background);
		createProperty(board, "approvedAndVarfied", sn++, true, true, null, 
				"已审车辆数", formatDiff(vCountNew, vCountOri),
				null, null, null, null, background);
		if(vCountNew>1 || vCountOri>1){
			createProperty(board, "approvedAndVarfiedPrfPer", sn++, true, true, null, 
					"  车均利润", formatDiff(vCountNew==0?0:profitNew/vCountNew, 
							vCountOri==0?0:profitOri/vCountOri),
					null, null, null, null, background,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			createProperty(board, "approvedAndVarfiedPrf", sn++, true, true, null, 
					"  总利润", formatDiff(profitNew, profitOri),
					null, null, null, null, background,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			createProperty(board, "approvedAndVarfiedPrf", sn++, true, true, null, 
					"  总金额", formatDiff(priceNew, priceOri),
					null, null, null, null, background);
		}else{
			createProperty(board, "approvedAndVarfiedPrf", sn++, true, true, null, 
					"  利润", formatDiff(profitNew, profitOri),
					null, null, null, null, background,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			createProperty(board, "approvedAndVarfiedPrf", sn++, true, true, null, 
					"  金额", formatDiff(priceNew, priceOri),
					null, null, null, null, background);
		}
		return sn;
	}

	// 合同号
	private Property getContractNo(VehicleSaleContractsVary contractsVery) {
		Property p = new Property();
		p.setLabel("合同号");
		p.setValue(contractsVery.getContractNo());
		p.setShownOnBaseBoard(true);
		p.setValueColour(Color.BLACK.getCode());

		return p;
	}

	// 销售员
	private Property getSeller(VehicleSaleContractsVary contractsVery) {
		String seller = "";
		VehicleSaleContracts saleContracts = dao.get(VehicleSaleContracts.class, contractsVery.getContractNo());
		if (saleContracts != null) {
			seller = saleContracts.getSeller();
		}

		Property p = new Property();
		p.setLabel("销售员");
		p.setValue(seller);
		p.setShownOnBaseBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 客户
	private Property getCustomerName(VehicleSaleContractsVary contractsVery) {
		String customerName = "";
		VehicleSaleContracts saleContracts = dao.get(VehicleSaleContracts.class, contractsVery.getContractNo());
		if (saleContracts != null) {
			customerName = saleContracts.getCustomerName();
		}

		Property p = new Property();
		p.setLabel("客户");
		p.setValue(customerName);
		p.setShownOnBaseBoard(true);
		p.setValueColour(Color.BLACK.getCode());

		return p;
	}

	@Override
	public String getBufferCalcVersion() {
		return bufferCalcVersion;
	}

	@Override
	public String getBusiBillVersion(MobileDocumentBuffered doc) {
		return getBusiBillVersion(this.getApproveDocument(doc));
	}
	
	public String getBusiBillVersion(ApproveDocuments<?> approveDoc) {
		if (null==approveDoc || null==approveDoc.getSubmitTime())
			throw new ServiceException("审批流程对象是空，不可以获得版本号。");
		else
			return sdf.format(approveDoc.getSubmitTime());
	}

	public ApproveDocuments<?> getApproveDocument(MobileDocumentBuffered doc) {
		ApproveDocuments<?> approveDoc;
		if(doc.getBusiBill()==null)
			throw new ServiceException("未找审批单，单号：" + doc.getBusiBillId());
		try{
			approveDoc = (ApproveDocuments<?>)(doc.getBusiBill());
		}catch(Exception e){
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return approveDoc;
	}

	public VehicleSaleContractsVary getVehicleSaleContractsVary(MobileDocumentBuffered doc) {
		VehicleSaleContractsVary bill;
		if(doc.getRelatedObjects()==null || doc.getRelatedObjects().length<1)
			throw new ServiceException("未找销售合同变更单，单号：" + doc.getBusiBillId());
		try{
			bill = (VehicleSaleContractsVary)(doc.getRelatedObjects()[0]);
		}catch(Exception e){
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return bill;
	}

	protected Date getBusiBillModifyTime(MobileDocumentBuffered bufferedDoc){
		return this.getVehicleSaleContractsVary(bufferedDoc).getModifyTime();
	}
}
