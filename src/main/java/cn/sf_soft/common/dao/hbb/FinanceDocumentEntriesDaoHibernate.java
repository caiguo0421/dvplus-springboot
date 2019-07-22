package cn.sf_soft.common.dao.hbb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.user.model.SysUsers;

/**
 * 费用分录表hibernate操作
 * 
 * @author minggo
 */
@Repository("financeDocumentEntriesDao")
public class FinanceDocumentEntriesDaoHibernate extends BaseDaoHibernateImpl implements FinanceDocumentEntriesDao {
	/**
	 * Insert the various cost approval entry list information
	 */
	public boolean insertFinanceDocumentEntries(final FinanceDocumentEntries financeDocumentEntries) {
		getHibernateTemplate().save(financeDocumentEntries);
		return true;
	}

	/**
	 * According to the entry list single number inquires the entry table
	 * records
	 */
	@SuppressWarnings("unchecked")
	public FinanceDocumentEntries getDocumentEntriesByDocumentNo(String documentNo) {
		String hql = "from FinanceDocumentEntries f where f.documentNo=?";
		List<FinanceDocumentEntries> list = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql, documentNo);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	

	/**
	 * Update cost entry list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean updateFinanceDocumentEntries(final FinanceDocumentEntries financeDocumentEntries) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				session.saveOrUpdate(financeDocumentEntries);
				return true;
			}
		});
		return true;
	}

	/**
	 * 根据分录ID得到单据分录
	 */
	public FinanceDocumentEntries getDocumentEntriesByEntryId(String entryId) {
		// fix bugId ADMB16010004,改load为get
		return getHibernateTemplate().get(FinanceDocumentEntries.class, entryId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceDocumentEntries> getDocumentEntriesByDocumentId(String documentId) {
		String hql = "from FinanceDocumentEntries f where f.documentId=?";
		List<FinanceDocumentEntries> list = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql, documentId);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceDocumentEntries> getEntryListByDocumentNoAndType(String documentNo, String documentType) {
		String hql = "from FinanceDocumentEntries  where documentType = ? AND documentNo = ?";
		List<FinanceDocumentEntries> list = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql, documentType,documentNo);
		return list;
	}
	
    /// <summary>
    /// 将新行添加到单据分录表。
    /// </summary>
    /// <param name="_dst">数据集。</param>
    /// <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
    /// <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
    /// 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
    /// <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
    /// 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
    /// <param name="_sDocumentType">单据类型。</param>
    /// <param name="_sDocumentID">单据标识。</param>
    /// <param name="_sObjectID">对象标识。</param>
    /// <param name="_sObjectNo">对象编码。</param>
    /// <param name="_sObjectName">对象名称。</param>
    /// <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
    /// 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
    /// 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
    /// 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
    /// <param name="_mDocumentAmount">单据金额。</param>
    /// <param name="_sDocumentNo">单据编号(空时默认为单据标识)。</param>
    /// <param name="_sSubDocumentNo">次单据编号(空时默认为单据标识)。</param>
    /// <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
    public  boolean insertEntry(String _sStationID,
        int _nEntryProperty, short _bytEntryType, String _sDocumentType,
        String _sDocumentID, String _sObjectID, String _sObjectNo,
        String _sObjectName, short _bytAmountType, double _mDocumentAmount,
        String _sDocumentNo, String _sSubDocumentNo)
    {
        return insertEntry(_sStationID, _nEntryProperty,
            _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID,
            _sObjectNo, _sObjectName, _bytAmountType, _mDocumentAmount,
            null, null, _sDocumentNo, _sSubDocumentNo, 1, 0.00D, null,
            null, null, null, null, null, null);
    }

	// / <summary>
	// / 将新行添加到单据分录表。
	// / </summary>
	// / <param name="_dst">数据集。</param>
	// / <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
	// / <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
	// / 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
	// / <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
	// / 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
	// / <param name="_sDocumentType">单据类型。</param>
	// / <param name="_sDocumentID">单据标识。</param>
	// / <param name="_sObjectID">对象标识。</param>
	// / <param name="_sObjectNo">对象编码。</param>
	// / <param name="_sObjectName">对象名称。</param>
	// / <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
	// / 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
	// / 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
	// / 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
	// / <param name="_mDocumentAmount">单据金额。</param>
	// / <param name="_sDocumentNo">单据编号(空时默认为单据标识)。</param>
	// / <param name="_sSubDocumentNo">次单据编号(空时默认为单据标识)。</param>
	// / <param name="_dtArapTime">应收应付时间。</param>
	// / <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
	public boolean insertEntryEx(String _sStationID, int _nEntryProperty, short _bytEntryType, String _sDocumentType,
			String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName, short _bytAmountType,
			double _mDocumentAmount, String _sDocumentNo, String _sSubDocumentNo, Timestamp _dtArapTime) {
		return insertEntry(_sStationID, _nEntryProperty, _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID, _sObjectNo,
				_sObjectName, _bytAmountType, _mDocumentAmount, _dtArapTime, null, _sDocumentNo, _sSubDocumentNo, 1,
				0.00D, null, null, null, null, null, null, null);
	}


	// / <summary>
	// / 将新行添加到单据分录表。
	// / </summary>
	// / <param name="_dst">数据集。</param>
	// / <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
	// / <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
	// / 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
	// / <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
	// / 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
	// / <param name="_sDocumentType">单据类型。</param>
	// / <param name="_sDocumentID">单据标识。</param>
	// / <param name="_sObjectID">对象标识。</param>
	// / <param name="_sObjectNo">对象编码。</param>
	// / <param name="_sObjectName">对象名称。</param>
	// / <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
	// / 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
	// / 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
	// / 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
	// / <param name="_mDocumentAmount">单据金额。</param>
	// / <param name="_sDocumentNo">单据编号(空时默认为单据标识)。</param>
	// / <param name="_sSubDocumentNo">次单据编号(空时默认为单据标识)。</param>
	// / <param name="_dtArapTime">应收应付时间。</param>
	// / <param name="summary">Summary。</param>
	// / <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
	public boolean insertEntryEx(String _sStationID, int _nEntryProperty, short _bytEntryType, String _sDocumentType,
								 String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName, short _bytAmountType,
								 double _mDocumentAmount, String _sDocumentNo, String _sSubDocumentNo, Timestamp _dtArapTime, String summary) {
		return insertEntry(_sStationID, _nEntryProperty, _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID, _sObjectNo,
				_sObjectName, _bytAmountType, _mDocumentAmount, _dtArapTime, summary, _sDocumentNo, _sSubDocumentNo, 1,
				0.00D, null, null, null, null, null, null, null);
	}

	 /// <summary>
    /// 将新行添加到单据分录表。
    /// </summary>
    /// <param name="_dst">数据集。</param>
    /// <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
    /// <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
    /// 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
    /// <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
    /// 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
    /// <param name="_sDocumentType">单据类型。</param>
    /// <param name="_sDocumentID">单据标识。</param>
    /// <param name="_sObjectID">对象标识。</param>
    /// <param name="_sObjectNo">对象编码。</param>
    /// <param name="_sObjectName">对象名称。</param>
    /// <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
    /// 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
    /// 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
    /// 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
    /// <param name="_mDocumentAmount">单据金额。</param>
    /// <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
    public boolean insertEntryEx( String _sStationID,
        int _nEntryProperty, short _bytEntryType, String _sDocumentType,
        String _sDocumentID, String _sObjectID, String _sObjectNo,
        String _sObjectName, short _bytAmountType, double _mDocumentAmount)
    {
        return insertEntry( _sStationID, _nEntryProperty,
            _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID,
            _sObjectNo, _sObjectName, _bytAmountType, _mDocumentAmount,
            null, null, null, null, 1, 0.00D, null, null, null, null,
            null, null, null);
    }
	
	
	
   
	 /// <summary>
    /// 将新行添加到单据分录表。
    /// </summary>
    /// <param name="_dst">数据集。</param>
    /// <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
    /// <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
    /// 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
    /// <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
    /// 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
    /// <param name="_sDocumentType">单据类型。</param>
    /// <param name="_sDocumentID">单据标识。</param>
    /// <param name="_sObjectID">对象标识。</param>
    /// <param name="_sObjectNo">对象编码。</param>
    /// <param name="_sObjectName">对象名称。</param>
    /// <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
    /// 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
    /// 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
    /// 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
    /// <param name="_mDocumentAmount">单据金额。</param>
    /// <param name="_bytAmountKind">金额种类(1:冲抵金额, 2:实收金额, 3:销账金额, 4:发票金额)。</param>
    /// <param name="_mAmount">特指种类的金额。</param>
    /// <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
    public  boolean InsertEntry(String _sStationID,
        int _nEntryProperty, short _bytEntryType, String _sDocumentType,
        String _sDocumentID, String _sObjectID, String _sObjectNo,
        String _sObjectName, short _bytAmountType, double _mDocumentAmount,
        short _bytAmountKind, double _mAmount)
    {
        return insertEntry( _sStationID, _nEntryProperty,
            _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID,
            _sObjectNo, _sObjectName, _bytAmountType, _mDocumentAmount,
            null, null, null, null, _bytAmountKind, _mAmount, null, null,
            null, null, null, null, null);
    }
	
	/// <summary>
    /// 将新行添加到单据分录表。
    /// </summary>
    /// <param name="_dst">数据集。</param>
    /// <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
    /// <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
    /// 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
    /// <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
    /// 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
    /// <param name="_sDocumentType">单据类型。</param>
    /// <param name="_sDocumentID">单据标识。</param>
    /// <param name="_sObjectID">对象标识。</param>
    /// <param name="_sObjectNo">对象编码。</param>
    /// <param name="_sObjectName">对象名称。</param>
    /// <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
    /// 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
    /// 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
    /// 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
    /// <param name="_mDocumentAmount">单据金额。</param>
    /// <returns>如果添加成功，则为 true；否则为 false。</returns>
	@Override
    public boolean insertEntry(String _sStationID,
        int _nEntryProperty, short _bytEntryType, String _sDocumentType,
        String _sDocumentID, String _sObjectID, String _sObjectNo,
        String _sObjectName, short _bytAmountType, double _mDocumentAmount)
    {
        return insertEntry( _sStationID, _nEntryProperty,
            _bytEntryType, _sDocumentType, _sDocumentID, _sObjectID,
            _sObjectNo, _sObjectName, _bytAmountType, _mDocumentAmount,
            null, null, null, null, 1, 0.00D, null, null, null, null,
            null, null, null);
    }
	

	// / <summary>
	// / 将新行添加到单据分录表。
	// / </summary>
	// / <param name="_dst">数据集。</param>
	// / <param name="_sStationID">站点标识(空时默认为当前登录用户的默认站点)。</param>
	// / <param name="_nEntryProperty">分录性质(1:计入往来, 2:可以结算,
	// / 4:需要发票, 8:完成发票, 16:可以冲抵)。</param>
	// / <param name="_bytEntryType">分录类型(10:应收, 15:应银, 20:实付, 25:销付,
	// / 30:转出, 60:应付, 65:应请, 70:实收, 75:销收, 80:转入)。</param>
	// / <param name="_sDocumentType">单据类型。</param>
	// / <param name="_sDocumentID">单据标识。</param>
	// / <param name="_sObjectID">对象标识。</param>
	// / <param name="_sObjectNo">对象编码。</param>
	// / <param name="_sObjectName">对象名称。</param>
	// / <param name="_bytAmountType">款项类型(10:预收款, 20:应收款,
	// / 25:销售订金, 30:其它应收, 35:应收保证金, 38:应收备用金, 40:其它收入,
	// / 60:预付款, 65:采购预付款, 70:应付款, 75:采购订金, 80:其它应付,
	// / 85:应付保证金, 88:应付备用金, 90:其它支出)。</param>
	// / <param name="_mDocumentAmount">单据金额。</param>
	// / <param name="_dtArapTime">应收应付时间。</param>
	// / <param name="_sSummary">摘要。</param>
	// / <param name="_sDocumentNo">单据编号(空时默认为单据标识)。</param>
	// / <param name="_sSubDocumentNo">次单据编号(空时默认为单据标识)。</param>
	// / <param name="_bytAmountKind">金额种类(1:冲抵金额, 2:实收金额, 3:销账金额,
	// 4:发票金额)。</param>
	// / <param name="_mAmount">特指种类的金额。</param>
	// / <param name="_sAccountID">资金账户标识。</param>
	// / <param name="_sUserID">用户标识。</param>
	// / <param name="_sUserNo">用户编码。</param>
	// / <param name="_sUserName">用户名称。</param>
	// / <param name="_sDepartmentID">部门标识。</param>
	// / <param name="_sDepartmentNo">部门编码。</param>
	// / <param name="_sDepartmentName">部门名称。</param>
	// / <returns>如果添加成功，则为 true；否则为 false。</returns>
	@SuppressWarnings("unchecked")
	@Override
	public boolean insertEntry(String _sStationID, int _nEntryProperty, short _bytEntryType, String _sDocumentType,
			String _sDocumentID, String _sObjectID, String _sObjectNo, String _sObjectName, short _bytAmountType,
			double _mDocumentAmount, Timestamp _dtArapTime, String _sSummary, String _sDocumentNo,
			String _sSubDocumentNo, int _bytAmountKind, double _mAmount, String _sAccountID, String _sUserID,
			String _sUserNo, String _sUserName, String _sDepartmentID, String _sDepartmentNo, String _sDepartmentName) {
		if (StringUtils.isBlank(_sDocumentType) || StringUtils.isBlank(_sDocumentID) || StringUtils.isBlank(_sObjectID)
				|| StringUtils.isBlank(_sObjectName)) {
//			logger.error("保存分录出错:关键数据为空");
//			throw new ServiceException("保存分录出错:关键数据为空");
			return false;
		}
		if (Math.abs(_mDocumentAmount) < 0.005D) {
//			logger.error("保存分录出错：单据金额小于0.5分");
//			throw new ServiceException("保存分录出错：单据金额小于0.5分");
			return false;
		}
		if (_bytAmountKind > 4) {
//			logger.error("保存分录出错：未知的金额种类：" + _bytAmountKind);
//			throw new ServiceException("保存分录出错：未知的金额种类：" + _bytAmountKind);
			return false;
		}
		if (_mAmount * _mDocumentAmount <= -0.00005D || _bytAmountKind < 4
				&& _mDocumentAmount * (_mAmount - _mDocumentAmount) >= 0.00005D) {
//			logger.error("保存分录出错：amount(" + _mAmount + ")和documentAmount(" + _mDocumentAmount + ")符号互斥");
//			throw new ServiceException("保存分录出错：amount(" + _mAmount + ")和documentAmount(" + _mDocumentAmount + ")符号互斥");
			return false;
		}

		String hql = "from FinanceDocumentEntries  where documentType = ? and  documentId = ?";
		List<FinanceDocumentEntries> entries = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql,
				_sDocumentType, _sDocumentID);
		if (entries != null && entries.size() > 0) {
//			logger.error("保存分录出错：已存在相同的分录,documentType：" + _sDocumentType + ",documentID:" + _sDocumentID);
//			throw new ServiceException("保存分录出错：已存在相同的分录,documentType：" + _sDocumentType + ",documentID:" + _sDocumentID);
			return false;
		}

		SysUsers user = HttpSessionStore.getSessionUser();

		String sStationID = (StringUtils.isBlank(_sStationID) ? user.getDefaulStationId() : _sStationID);
		String sDocumentNo = (StringUtils.isBlank(_sDocumentNo) ? _sDocumentID : _sDocumentNo);
		String sSubDocumentNo = (StringUtils.isBlank(_sSubDocumentNo) ? _sDocumentID : _sSubDocumentNo);
		String sUserID = (StringUtils.isBlank(_sUserID) ? user.getUserId() : _sUserID);
		String sUserNo = (StringUtils.isBlank(_sUserNo) ? user.getUserNo() : _sUserNo);
		String sUserName = (StringUtils.isBlank(_sUserName) ? user.getUserName() : _sUserName);
		String sDepartmentID = (StringUtils.isBlank(_sDepartmentID) ? user.getDepartment() : _sDepartmentID);
		String sDepartmentNo = (StringUtils.isBlank(_sDepartmentNo) ? user.getDepartmentNo() : _sDepartmentNo);
		String sDepartmentName = (StringUtils.isBlank(_sDepartmentName) ? user.getDepartmentName() : _sDepartmentName);
		Timestamp dt = new Timestamp(System.currentTimeMillis());

		FinanceDocumentEntries newEntry = new FinanceDocumentEntries();
		newEntry.setEntryId(UUID.randomUUID().toString());
		newEntry.setStationId(sStationID);
		newEntry.setEntryProperty(_nEntryProperty);
		newEntry.setEntryType(_bytEntryType);
		newEntry.setDocumentType(_sDocumentType);
		newEntry.setDocumentId(_sDocumentID);
		newEntry.setDocumentNo(sDocumentNo);
		newEntry.setSubDocumentNo(sSubDocumentNo);
		newEntry.setObjectId(_sObjectID);
		if (StringUtils.isNotBlank(_sObjectNo)) {
			newEntry.setObjectNo(_sObjectNo);
		}
		newEntry.setObjectName(_sObjectName);

		if (StringUtils.isNotBlank(_sSummary)) {
			newEntry.setSummary(_sSummary);
		}
		newEntry.setUserId(sUserID);
		newEntry.setUserNo(sUserNo);
		newEntry.setUserName(sUserName);
		newEntry.setDepartmentId(sDepartmentID);
		newEntry.setDepartmentNo(sDepartmentNo);
		newEntry.setDepartmentName(sDepartmentName);

		if (StringUtils.isNotBlank(_sAccountID)) {
			newEntry.setAccountId(_sAccountID);
		}
		newEntry.setAmountType(_bytAmountType);
		newEntry.setLeftAmount(_mDocumentAmount - (_bytAmountKind > 0 && _bytAmountKind < 4 ? _mAmount : 0.00D));

		newEntry.setDocumentAmount(_mDocumentAmount);
		newEntry.setDocumentTime(dt);

		if (_dtArapTime != null) {
			newEntry.setArapTime(_dtArapTime);
		}
		newEntry.setOffsetAmount((_bytAmountKind == 1 ? _mAmount : 0.00D));
		if (Math.abs(newEntry.getOffsetAmount()) >= 0.005D) {
			newEntry.setOffsetTime(dt);
		}

		newEntry.setPaidAmount((_bytAmountKind == 2 ? _mAmount : 0.00D));
		if (Math.abs(newEntry.getPaidAmount()) >= 0.005D) {
			newEntry.setPaidTime(dt);
		}
		newEntry.setWriteOffAmount((_bytAmountKind == 3 ? _mAmount : 0.00D));
		if (Math.abs(newEntry.getWriteOffAmount()) >= 0.005D) {
			newEntry.setWriteOffTime(dt);
		}
		newEntry.setInvoiceAmount((_bytAmountKind == 4 ? _mAmount : 0.00D));
		if (Math.abs(newEntry.getInvoiceAmount()) >= 0.005D) {
			newEntry.setInvoiceTime(dt);
		}
		newEntry.setUsedCredit(0.00D);

		// 保存
		try{
			getHibernateTemplate().save(newEntry);
		}catch(Exception ex){
			throw new ServiceException("保存分录出错",ex);
		}
		
		return true;
	}


}
