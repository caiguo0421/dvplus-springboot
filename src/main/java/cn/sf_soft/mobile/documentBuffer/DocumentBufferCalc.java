package cn.sf_soft.mobile.documentBuffer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardFieldVal;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentCfg;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;
import cn.sf_soft.user.model.SysUsers;

public abstract class DocumentBufferCalc {

	@Autowired
	@Qualifier("baseDao")
	protected BaseDao dao;
	
	final public static int collapseLines = 10;
	static Map<String, Long> lockedDocuments = new HashMap<String, Long>();
	static private boolean lockedFlag = false;
	static final private int waitMilliSeconds = 10000;
	static final private int collapseThreshold = 10;
	
	static final protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final protected SimpleDateFormat sdfWithMilli = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DocumentBufferCalc.class);

	public JsonObject getDocumentBuffered(String moduleId, String documentNo){
		return null;
	}
	public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId, String documentNo){
		return null;
	}
	/*
	 * 获取业务单据数据版本号
	 */
	public String getBufferCalcVersion(){
		return null;
	}
	public String getBusiBillVersion(MobileDocumentBuffered doc){
		return null;
	}
	
	protected void computeStaticFields(MobileDocumentBuffered doc){
	}
	protected  void computeDynamicFields(MobileDocumentBuffered doc){
	}
	
	/*
	 * return	true: 有访问权限；false：无访问权限
	 */
	protected  boolean getA4sAccessCtrl(String popedomId){
		SysUsers user = HttpSessionStore.getSessionUser();
		return user.getPopedomIds().contains(popedomId);
	}
	
	
	synchronized static private boolean lock(){
		if(lockedFlag){
			return false;
		}else{
			lockedFlag = true;
			return true;
		}
	}
	
	synchronized static private void unlock(){
		lockedFlag = false;
	}
	
	protected boolean lockDocument(String moduleId, String documentNo){
		return lockDocument(moduleId, documentNo, waitMilliSeconds);
	}
	
	
	protected boolean lockDocument(String moduleId, String documentNo, int waitMilliSeconds){
		if(waitMilliSeconds<1)
			waitMilliSeconds = 100;
		else
			waitMilliSeconds = 20000;
		long start = System.currentTimeMillis();
		do{
			if(lock()){
				try{
					if(!lockedDocuments.containsKey(moduleId+":"+documentNo)){
						lockedDocuments.put(moduleId+":"+documentNo, System.currentTimeMillis());
						return true;
					}
				}finally{
					unlock();
				}
			}
			long now = System.currentTimeMillis();
			if((now-start)<waitMilliSeconds){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
			}else{
				return false;
			}
		} while (true);
	}
	
	protected void unlockDocument(String moduleId, String documentNo){
		lockedDocuments.remove(moduleId+":"+documentNo);
	}
	
	protected Gson gson = new GsonBuilder().registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
			.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
			.setExclusionStrategies(new GsonExclutionStrategy()).create();

	protected JsonObject board2Json(MobileBoardBuffered board) {
		if (null == board)
			return null;
		MobileBoardTitle title = getBoardTitle(board);
		JsonParser jParser = new JsonParser();
		JsonObject jo = new JsonObject();
		if (null != board.getDocTitle()) {
			try {
				JsonElement je = jParser.parse(board.getDocTitle());
				if (null == je)
					throw new ServiceException("文档标题配置有错误");
				jo.add("docTitle", je);
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				throw new ServiceException("文档标题配置有错误", e);
			}
		}
		if (null != title.getBaseBoardTitle()) {
			try {
				JsonElement je = jParser.parse(title.getBaseBoardTitle());
				if (null == je)
					throw new ServiceException("基本面板标题配置有错误");
				jo.add("baseBoardTitle", je);
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				throw new ServiceException("基本面板标题配置有错误", e);
			}
		}
		if (null != title.getDetailBoardTitle()) {
			try {
				JsonElement je = jParser.parse(title.getDetailBoardTitle());
				if (null == je)
					throw new ServiceException("明细面板标题配置有错误");
				jo.add("detailBoardTitle", je);
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				throw new ServiceException("明细面板标题配置有错误", e);
			}
		}
		if (null != title.getSlaveBoardTitle()) {
			try {
				JsonElement je = jParser.parse(title.getSlaveBoardTitle());
				if (null == je)
					throw new ServiceException("从面板标题配置有错误");
				jo.add("slaveBoardTitle", je);
			} catch (ServiceException e) {
				throw e;
			} catch (Exception e) {
				throw new ServiceException("从面板标题配置有错误", e);
			}
		}
		List<MobileBoardField> fields = board.getFields();
		if (null != fields && fields.size() > 0) {
			jo.add("fields", fields2Json(fields));
		}
		return jo;
	}
	
	private  MobileBoardTitle getBoardTitle(MobileBoardBuffered board){
		if(board==null)
			return null;
		MobileBoardTitle noPopedomTitle = null;//无权限的title
		if(board.getTitles()!=null){
			for(MobileBoardTitle title:board.getTitles()){
				if(StringUtils.isEmpty(title.getPopedomId())){
					noPopedomTitle = title;
				}else{
					if(getA4sAccessCtrl(title.getPopedomId())){
						return title;
					}
				}
			}
		}
		return noPopedomTitle;
	}

	protected JsonArray fields2Json(List<MobileBoardField> fields) {
		if (null == fields || fields.size() == 0)
			return null;
		JsonParser jParser = new JsonParser();
		JsonArray ja = new JsonArray();
		for (int i = 0; i < fields.size(); i++) {
			MobileBoardField f = fields.get(i);
			MobileBoardFieldVal val = getFieldVal(f);
			short typeNo = f.getFieldTypeNo();
			if(AppBoardFieldType.Property.getCode() == typeNo || AppBoardFieldType.Tail.getCode() == typeNo){
				if(val==null)
					continue;
				ja.add(jParser.parse(val.getValue()));
			}else if(AppBoardFieldType.Attachment.getCode() == typeNo){
				if(val ==null)
					continue;
				JsonElement je = jParser.parse(val.getValue());
				JsonObject jo = je.getAsJsonObject();
				jo.addProperty("itemType", AppBoardFieldType.Attachment.getText());
				ja.add(jo);
			}else if(AppBoardFieldType.Subobject.getCode() == typeNo){
				JsonObject jo = board2Json(f.getSubobjectBoard());
				if (null != jo) {
					jo.addProperty("itemType", AppBoardFieldType.Subobject.getText());
					ja.add(jo);
				}
			}else if(AppBoardFieldType.Row.getCode() == typeNo){
				JsonObject jo = board2Json(f.getSubobjectBoard());
				if (null != jo) {
					jo.addProperty("itemType", AppBoardFieldType.Row.getText());
					ja.add(jo);
				}
			}else if(AppBoardFieldType.Label.getCode() == typeNo){
				if(val ==null)
					continue;
				
				JsonElement je = jParser.parse(val.getValue());
				JsonObject jo = je.getAsJsonObject();
				jo.add("board", board2Json(f.getSubobjectBoard()));
				ja.add(jo);
			}else {
				throw new ServiceException("在解析文档时，遇到系统不支持的域类型："+typeNo);
			}
		}
		return ja;
	}
	
	private MobileBoardFieldVal getFieldVal(MobileBoardField f) {
		if (f == null)
			return null;
		MobileBoardFieldVal noPopedomVal = null;
		if (f.getFieldVals() != null) {
			for (MobileBoardFieldVal val : f.getFieldVals()) {
				if (StringUtils.isEmpty(val.getPopedomId())) {
					noPopedomVal = val;
				} else {
					if (getA4sAccessCtrl(val.getPopedomId())) {
						return val;
					}
				}
			}
		}
		return noPopedomVal;
	}
	

	/*
	 * 检查缓存文档版本，以决定是否需要重新计算静态缓存数据
	 */
	public boolean isNeedToCompute(MobileDocumentBuffered doc){
		if(this.getBufferCalcVersion()==null || 
				!this.getBufferCalcVersion().equals(doc.getBufferCalcVersion()) ||
				this.getBusiBillVersion(doc)==null || 
				!this.getBusiBillVersion(doc).equals(doc.getBusiBillVersion())){
			return true;
		}else{
			return false;
		}
	}


	
	/**
	 * 保存Property
	 * @param board
	 * @param fieldCode
	 * @param property
	 * @param sn
	 * @param f
	 */
	// deprecated,	20161221
	protected MobileBoardField saveProperty (MobileBoardBuffered board, String fieldCode, short sn,
			Property p) {
		return createProperty(board, fieldCode, sn, p.getShownOnBaseBoard(), p.getShownOnDetailBoard(), p.getShownOnSlaveBoard(), p.getLabel(),
				p.getValue(), p.getLabelColour(), p.getLabelBackground(), p.getValueColour(), p.getValueBackground(), p.getBackground(), null);
	}
	

	
	/**
	 * 创建 BoardTitle,不含popedomId
	 * @param board
	 * @param baseBoardTitle
	 * @param detailBoardTitle
	 * @param slaveBoardTitle
	 * @return
	 */
	protected MobileBoardTitle createBoardTitle(MobileBoardBuffered board,String baseBoardTitle,String detailBoardTitle,String slaveBoardTitle){
		return createBoardTitle( board, baseBoardTitle, detailBoardTitle, slaveBoardTitle, null);
	}
	
	
	/**
	 * 创建 BoardTitle
	 * @param board
	 * @param baseBoardTitle
	 * @param detailBoardTitle
	 * @param slaveBoardTitle
	 * @param popedomId
	 * @return 
	 */
	protected MobileBoardTitle createBoardTitle(MobileBoardBuffered board,String baseBoardTitle,String detailBoardTitle,String slaveBoardTitle,String popedomId){
		MobileBoardTitle boardTitle = org.springframework.beans.BeanUtils.instantiate(MobileBoardTitle.class);
		boardTitle.setBaseBoardTitle(baseBoardTitle);
		boardTitle.setDetailBoardTitle(detailBoardTitle);
		boardTitle.setSlaveBoardTitle(slaveBoardTitle);
		boardTitle.setPopedomId(popedomId);
		boardTitle.setBoard(board);
		
		board.getTitles().add(boardTitle);
		return boardTitle;
	}
	
	
	/**
	 * 加入 MobileBoardField中
	 * @param field
	 * @param shownOnBaseBoard
	 * @param shownOnDetailBoard
	 * @param shownOnSlaveBoard
	 * @param label
	 * @param value
	 * @param labelColour
	 * @param labelBackground
	 * @param valueColour
	 * @param valueBackground
	 * @param background
	 * @param popedomId
	 * @return
	 */
	protected MobileBoardField addFieldVal(MobileBoardField field, Boolean shownOnBaseBoard,
			Boolean shownOnDetailBoard, Boolean shownOnSlaveBoard, String label, String value, Integer labelColour,
			Integer labelBackground, Integer valueColour, Integer valueBackground, Integer background, String popedomId) {
		
		JsonObject jf = createPropertyJsonObject(shownOnBaseBoard,shownOnDetailBoard,shownOnSlaveBoard,label,value,labelColour,labelBackground,valueColour,valueBackground,background);

		MobileBoardFieldVal fieldVal = org.springframework.beans.BeanUtils.instantiate(MobileBoardFieldVal.class);
		fieldVal.setPopedomId(popedomId);
		fieldVal.setValue(jf.toString());
		field.getFieldVals().add(fieldVal);

		return field;
	}
	
	
	protected MobileBoardField createProperty(MobileBoardBuffered board, String fieldCode, short sn,
			Boolean shownOnBaseBoard, Boolean shownOnDetailBoard, Boolean shownOnSlaveBoard, String label,
			String value, Integer labelColour, Integer labelBackground, Integer valueColour, Integer valueBackground,
			Integer background) {
		return createProperty(board, fieldCode, sn, shownOnBaseBoard, shownOnDetailBoard, shownOnSlaveBoard, label,
				value, labelColour, labelBackground, valueColour, valueBackground, background, null);
	}
	

	protected MobileBoardField createProperty(MobileBoardBuffered board, String fieldCode, short sn,
			Boolean shownOnBaseBoard,
			Boolean shownOnDetailBoard,
			Boolean shownOnSlaveBoard,
			String label,
			String value,
			Integer labelColour,
			Integer labelBackground,
			Integer valueColour,
			Integer valueBackground,
			Integer background,
			String popedomId
			) {
		JsonObject jf = createPropertyJsonObject(shownOnBaseBoard,shownOnDetailBoard,shownOnSlaveBoard,label,value,labelColour,labelBackground,valueColour,valueBackground,background);
		
		MobileBoardField field = org.springframework.beans.BeanUtils.instantiate(MobileBoardField.class);
		field.setBoard(board);
		field.setFieldCode(fieldCode);
		field.setFieldTypeNo(AppBoardFieldType.Property.getCode());
		field.setSn(sn);
		
		MobileBoardFieldVal fieldVal = org.springframework.beans.BeanUtils.instantiate(MobileBoardFieldVal.class);
		fieldVal.setPopedomId(popedomId);
		fieldVal.setValue(jf.toString());
		fieldVal.setField(field);
		field.getFieldVals().add(fieldVal);
		
		board.getFields().add(field);
		return field;
	}

	protected MobileBoardField createAttachment(MobileBoardBuffered board, String fieldCode, short sn,
											  Boolean shownOnBaseBoard, Boolean shownOnDetailBoard, Boolean shownOnSlaveBoard, String label,
											  String value, Integer labelColour, Integer labelBackground, Integer valueColour, Integer valueBackground,
											  Integer background) {
		MobileBoardField field =  createProperty(board, fieldCode, sn, shownOnBaseBoard, shownOnDetailBoard, shownOnSlaveBoard, label,
				value, labelColour, labelBackground, valueColour, valueBackground, background, null);
		field.setFieldTypeNo(AppBoardFieldType.Attachment.getCode());
		return field;
	}
	
	/**
	 * 创建property的json对象
	 * @param shownOnBaseBoard
	 * @param shownOnDetailBoard
	 * @param shownOnSlaveBoard
	 * @param label
	 * @param value
	 * @param labelColour
	 * @param labelBackground
	 * @param valueColour
	 * @param valueBackground
	 * @param background
	 * @return
	 */
	public static JsonObject createPropertyJsonObject(Boolean shownOnBaseBoard, Boolean shownOnDetailBoard,
			Boolean shownOnSlaveBoard, String label, String value, Integer labelColour, Integer labelBackground,
			Integer valueColour, Integer valueBackground, Integer background) {
		JsonObject jf = new JsonObject();
		jf.addProperty("itemType", AppBoardFieldType.Property.getText());
		if (null != shownOnSlaveBoard && shownOnSlaveBoard) {
			jf.addProperty("shownOnSlaveBoard", shownOnSlaveBoard);
			shownOnBaseBoard = true;
		}
		if (null != shownOnBaseBoard && shownOnBaseBoard) {
			jf.addProperty("shownOnBaseBoard", shownOnBaseBoard);
			shownOnDetailBoard = true;
		}
		if (null != shownOnDetailBoard && shownOnDetailBoard)
			jf.addProperty("shownOnDetailBoard", shownOnDetailBoard);
		if (null != label)
			jf.addProperty("label", label);
		if (null != value)
			jf.addProperty("value", value);
		if (null != labelColour)
			jf.addProperty("labelColour", labelColour);
		if (null != labelBackground)
			jf.addProperty("labelBackground", labelBackground);
		if (null != valueColour)
			jf.addProperty("valueColour", valueColour);
		if (null != valueBackground)
			jf.addProperty("valueBackground", valueBackground);
		if (null != background)
			jf.addProperty("background", background);
		return jf;
	}
	
	
	
	
	/**
	 * 创建Label，popedomId为null
	 * @param board
	 * @param sn
	 * @param shownOnBaseBoard
	 * @param shownOnDetailBoard
	 * @param shownOnSlaveBoard
	 * @param label
	 * @param labelColour
	 * @param labelBackground
	 * @param background
	 * @param detailBoardShown
	 * @param lableBoard
	 * @return
	 */
	protected MobileBoardField createLable(MobileBoardBuffered board, short sn, Boolean shownOnBaseBoard,
			Boolean shownOnDetailBoard, Boolean shownOnSlaveBoard, String label, Integer labelColour,
			Integer labelBackground, Integer background, Boolean detailBoardShown, MobileBoardBuffered lableBoard) {
		return createLable(board, sn, shownOnBaseBoard, shownOnDetailBoard, shownOnSlaveBoard, label, labelColour,
				labelBackground, background, detailBoardShown, lableBoard, null);
	}
	
	
	
	protected MobileBoardField createLable(MobileBoardBuffered board, short sn,
			Boolean shownOnBaseBoard,
			Boolean shownOnDetailBoard,
			Boolean shownOnSlaveBoard,
			String label,
			Integer labelColour,
			Integer labelBackground,
			Integer background,
			Boolean detailBoardShown,
			MobileBoardBuffered lableBoard,
			String popedomId) {
		JsonObject jf = new JsonObject();
		jf.addProperty("itemType", AppBoardFieldType.Label.getText());
		if(null!=shownOnSlaveBoard && shownOnSlaveBoard){
			jf.addProperty("shownOnSlaveBoard", shownOnSlaveBoard);
			shownOnBaseBoard = true;
		}
		if(null!=shownOnBaseBoard && shownOnBaseBoard){
			jf.addProperty("shownOnBaseBoard", shownOnBaseBoard);
			shownOnDetailBoard = true;
		}
		if(null!=shownOnDetailBoard && shownOnDetailBoard)
			jf.addProperty("shownOnDetailBoard", shownOnDetailBoard);
		if(null!=label)
			jf.addProperty("label", label);
		if(null!=labelColour)
			jf.addProperty("labelColour", labelColour);
		if(null!=labelBackground)
			jf.addProperty("labelBackground", labelBackground);
		if(null!=background)
			jf.addProperty("background", background);
		if(null!=detailBoardShown)
			jf.addProperty("detailBoardShown", detailBoardShown);
		MobileBoardField field = org.springframework.beans.BeanUtils.instantiate(MobileBoardField.class);
		field.setSubobjectBoard(lableBoard);
		field.setBoard(board);
		field.setFieldTypeNo(AppBoardFieldType.Label.getCode());
		field.setFieldCode("label");
		field.setSn(sn);
		
		MobileBoardFieldVal fieldVal = org.springframework.beans.BeanUtils.instantiate(MobileBoardFieldVal.class);
		fieldVal.setPopedomId(popedomId);
		fieldVal.setValue(jf.toString());
		fieldVal.setField(field);
		field.getFieldVals().add(fieldVal);
		
		board.getFields().add(field);
		return field;
	}

	protected MobileBoardField createTail(MobileBoardBuffered board, short sn) {
		return createTail(board, sn,null, null, null, false, false, true);
	}
	
	protected MobileBoardField createTail(MobileBoardBuffered board, short sn,
			Boolean detailShownOnBaseBoard,
			Boolean detailShownOnSlaveBoard) {
		return createTail(board, sn,
				null, null, null, detailShownOnBaseBoard, detailShownOnSlaveBoard, true);
	}
	protected MobileBoardField createTail(MobileBoardBuffered board, short sn,
			Boolean detailShownOnBaseBoard,
			Boolean detailShownOnSlaveBoard,
			Boolean detailBoardShown) {
		return createTail(board, sn,
				null, null, null, detailShownOnBaseBoard, detailShownOnSlaveBoard, detailBoardShown);
	}
	
	protected MobileBoardField createTail(MobileBoardBuffered board, short sn, Boolean collapseShownOnBaseBoard,
			Boolean collapseShownOnDetailBoard, Boolean collapseShownOnSlaveBoard, Boolean detailShownOnBaseBoard,
			Boolean detailShownOnSlaveBoard, Boolean detailBoardShown) {
		return createTail(board, sn, collapseShownOnBaseBoard, collapseShownOnDetailBoard, collapseShownOnSlaveBoard,
				detailShownOnBaseBoard, detailShownOnSlaveBoard, detailBoardShown, null);
	}
	
	
	protected MobileBoardField createTail(MobileBoardBuffered board, short sn,
			Boolean collapseShownOnBaseBoard,
			Boolean collapseShownOnDetailBoard,
			Boolean collapseShownOnSlaveBoard,
			Boolean detailShownOnBaseBoard,
			Boolean detailShownOnSlaveBoard,
			Boolean detailBoardShown,
			String popedomId) {
		if(null==collapseShownOnBaseBoard && sn>=collapseThreshold){
			collapseShownOnBaseBoard = true;
		}
		if(null==collapseShownOnDetailBoard && sn>=collapseThreshold){
			collapseShownOnDetailBoard = true;
		}
		if(null==collapseShownOnSlaveBoard && sn>=collapseThreshold){
			collapseShownOnSlaveBoard = true;
		}
		JsonObject jo1 = new JsonObject();
		boolean flag1 = false;
		if(null!=collapseShownOnBaseBoard && collapseShownOnBaseBoard){
			jo1.addProperty("shownOnBaseBoard", collapseShownOnBaseBoard);
			flag1 = true;
		}
		if(null!=collapseShownOnDetailBoard && collapseShownOnDetailBoard){
			jo1.addProperty("shownOnDetailBoard", collapseShownOnDetailBoard);
			flag1 = true;
		}
		if(null!=collapseShownOnSlaveBoard && collapseShownOnSlaveBoard){
			jo1.addProperty("shownOnSlaveBoard", collapseShownOnSlaveBoard);
			flag1 = true;
		}
		
		JsonObject jo2 = new JsonObject();
		boolean flag2 = false;
		if(null!=detailShownOnBaseBoard && detailShownOnBaseBoard){
			jo2.addProperty("shownOnBaseBoard", detailShownOnBaseBoard);
			flag2 = true;
		}
		if(null!=detailShownOnSlaveBoard && detailShownOnSlaveBoard){
			jo2.addProperty("shownOnSlaveBoard", detailShownOnSlaveBoard);
			flag2 = true;
		}
		if(!flag1 && !flag2)
			return null;
		JsonObject jf = new JsonObject();
		jf.addProperty("itemType", AppBoardFieldType.Tail.getText());
		if(flag1){
			jf.add("collapseButton", jo1);
		}
		if(flag2){
			if(null!=detailBoardShown){
				jo2.addProperty("detailBoardShown", detailBoardShown);
			}else{
				jo2.addProperty("detailBoardShown", true);
			}
			jf.add("detailButton", jo2);
		}

		MobileBoardField field = org.springframework.beans.BeanUtils.instantiate(MobileBoardField.class);
		field.setBoard(board);
		field.setFieldTypeNo(AppBoardFieldType.Tail.getCode());
		field.setFieldCode("tail");
		field.setSn(sn);
		
		MobileBoardFieldVal fieldVal = org.springframework.beans.BeanUtils.instantiate(MobileBoardFieldVal.class);
		fieldVal.setPopedomId(popedomId);
		fieldVal.setValue(jf.toString());
		fieldVal.setField(field);
		
		field.getFieldVals().add(fieldVal);
		
		board.getFields().add(field);
		return field;
	}
	
	protected void createSubObjectField(MobileBoardBuffered board, String fieldCode, AppBoardFieldType property, short sn,MobileBoardBuffered subobjectBoard ) {
		MobileBoardField field = org.springframework.beans.BeanUtils.instantiate(MobileBoardField.class);
		field.setBoard(board);
		field.setFieldCode(fieldCode);
		field.setFieldTypeNo(property.getCode());
		field.setSn(sn);
		field.setSubobjectBoard(subobjectBoard);
		
		//加入到filelds中
		List<MobileBoardField> filelds = board.getFields();
		filelds.add(field);
	}
	

	protected <T> String formatDiff(T newValue,T oldValue){
		long newLong, oldLong;
		if(newValue instanceof Double){
			newLong = Math.round((Double)newValue);
		}else if(newValue instanceof Float){
			newLong = Math.round((Float)newValue);
		}else if(newValue==null){
			newLong = 0L;
		}else {
			newLong =(long)(Object)newValue;
		}
		if(oldValue instanceof Double){
			oldLong = Math.round((Double)oldValue);
		}else if(oldValue instanceof Float){
			oldLong = Math.round((Float)oldValue);
		}else if(oldValue==null){
			oldLong = 0L;
		}else {
			oldLong = (long)(Object)oldValue;
		}

		if((newLong-oldLong)==0){
			return String.format("%1$,d", newLong);
		}else{
			return String.format("%1$,d【%2$,d %3$+,d】", newLong, oldLong, newLong-oldLong);
		}
	}
	protected <T> boolean compareEqual(T newValue,T oldValue){
		long newLong, oldLong;
		if(newValue instanceof Double){
			newLong = Math.round((Double)newValue);
		}else if(newValue instanceof Float){
			newLong = Math.round((Float)newValue);
		}else if(newValue==null){
			newLong = 0L;
		}else {
			newLong = (long)(Object)newValue;
		}
		if(oldValue instanceof Double){
			oldLong = Math.round((Double)oldValue);
		}else if(oldValue instanceof Float){
			oldLong = Math.round((Float)oldValue);
		}else if(oldValue==null){
			oldLong = 0L;
		}
		else {
			oldLong =(long)(Object)oldValue;
		}
		if((newLong-oldLong)==0){
			return true;
		}else{
			return false;
		}
	}
}
