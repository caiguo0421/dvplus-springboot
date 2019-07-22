package cn.sf_soft.common.dao;

import java.sql.Timestamp;
import java.util.List;

import cn.sf_soft.office.approval.model.FinanceDocumentEntries;

/**
 * 费用分录表hibernate接口
 * 
 * @author minggo
 * @created 2012-12-13下午03:48:10 刘金
 *          修改于2013/09/09(insertFinanceDocumentEntries方法异常往上抛)
 */
public interface FinanceDocumentEntriesDao extends BaseDao {
	/**
	 * insert cost entry list information
	 * 
	 * @param financeDocumentEntries
	 * @return
	 */
	public boolean insertFinanceDocumentEntries(
			FinanceDocumentEntries financeDocumentEntries);

	/**
	 * According to utility bill, inquires the entry list if there is data
	 * 
	 * @param documentNo
	 * @return
	 */
	public FinanceDocumentEntries getDocumentEntriesByDocumentNo(
			String documentNo);

	/**
	 * Update cost entry table records
	 * 
	 * @param financeDocumentEntries
	 * @return
	 */
	public boolean updateFinanceDocumentEntries(
			FinanceDocumentEntries financeDocumentEntries);

	/**
	 * 根据分录ID得到单据分录
	 * 
	 * @param entryId
	 * @return
	 */
	public FinanceDocumentEntries getDocumentEntriesByEntryId(String entryId);

	/**
	 * 根据documentId找到单据分录
	 * @param documentId
	 * @return
	 */
	public List<FinanceDocumentEntries> getDocumentEntriesByDocumentId(String documentId);
	
	/**
	 * 查找单据分录
	 * @param documentNo
	 * @param documentType
	 * @return
	 */
	public  List<FinanceDocumentEntries> getEntryListByDocumentNoAndType(String documentNo,String documentType);

	
	/**
	 * 将新行添加到单据分录表
	 * @param _sStationID 站点标识(空时默认为当前登录用户的默认站点)
	 * @param _nEntryProperty 分录性质(1:计入往来, 2:可以结算,4:需要发票, 8:完成发票, 16:可以冲抵)
	 * @param _bytEntryType 分录类型(10:应收, 15:应银, 20:实付, 25:销付,30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)
	 * @param _sDocumentType 单据类型
	 * @param _sDocumentID 单据标识
	 * @param _sObjectID 对象标识
	 * @param _sObjectNo 对象编码
	 * @param _sObjectName 对象名称
	 * @param _bytAmountType 款项类型(10:预收款, 20:应收款, 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付, 85:应付保证金, 88:应付备用金, 90:其它支出)
	 * @param _mDocumentAmount 单据金额
	 * @param _sDocumentNo 单据编号(空时默认为单据标识)
	 * @param _sSubDocumentNo 次单据编号(空时默认为单据标识)
	 * @param _dtArapTime 应收应付时间
	 */
	boolean insertEntryEx(String _sStationID, int _nEntryProperty, short _bytEntryType, String _sDocumentType,
			String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName, short _bytAmountType,
			double _mDocumentAmount, String _sDocumentNo, String _sSubDocumentNo, Timestamp _dtArapTime);

	/**
	 * 将新行添加到单据分录表
	 * @param _sStationID 站点标识(空时默认为当前登录用户的默认站点)
	 * @param _nEntryProperty 分录性质(1:计入往来, 2:可以结算,4:需要发票, 8:完成发票, 16:可以冲抵)
	 * @param _bytEntryType 分录类型(10:应收, 15:应银, 20:实付, 25:销付,30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)
	 * @param _sDocumentType 单据类型
	 * @param _sDocumentID 单据标识
	 * @param _sObjectID 对象标识
	 * @param _sObjectNo 对象编码
	 * @param _sObjectName 对象名称
	 * @param _bytAmountType 款项类型(10:预收款, 20:应收款, 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付, 85:应付保证金, 88:应付备用金, 90:其它支出)
	 * @param _mDocumentAmount 单据金额
	 * @param _sDocumentNo 单据编号(空时默认为单据标识)
	 * @param _sSubDocumentNo 次单据编号(空时默认为单据标识)
	 * @param _dtArapTime 应收应付时间
	 * @param summary Summary
	 */
	boolean insertEntryEx(String _sStationID, int _nEntryProperty, short _bytEntryType, String _sDocumentType,
						  String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName, short _bytAmountType,
						  double _mDocumentAmount, String _sDocumentNo, String _sSubDocumentNo, Timestamp _dtArapTime, String summary);


	boolean insertEntry(String _sStationID, int _nEntryProperty, short _bytEntryType,
			String _sDocumentType, String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName,
			short _bytAmountType, double _mDocumentAmount, String _sDocumentNo, String _sSubDocumentNo);

	boolean insertEntryEx(String _sStationID, int _nEntryProperty, short _bytEntryType,
			String _sDocumentType, String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName,
			short _bytAmountType, double _mDocumentAmount);

	boolean InsertEntry(String _sStationID, int _nEntryProperty, short _bytEntryType,
			String _sDocumentType, String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName,
			short _bytAmountType, double _mDocumentAmount, short _bytAmountKind, double _mAmount);

	boolean insertEntry(String _sStationID, int _nEntryProperty, short _bytEntryType,
			String _sDocumentType, String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName,
			short _bytAmountType, double _mDocumentAmount);

	boolean insertEntry(String _sStationID, int _nEntryProperty, short _bytEntryType,
			String _sDocumentType, String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName,
			short _bytAmountType, double _mDocumentAmount, Timestamp _dtArapTime, String _sSummary,
			String _sDocumentNo, String _sSubDocumentNo, int _bytAmountKind, double _mAmount, String _sAccountID,
			String _sUserID, String _sUserNo, String _sUserName, String _sDepartmentID, String _sDepartmentNo,
			String _sDepartmentName);

	



}
