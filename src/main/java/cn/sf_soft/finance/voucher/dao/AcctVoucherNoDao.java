package cn.sf_soft.finance.voucher.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.finance.voucher.model.AcctVoucherNo;

public interface AcctVoucherNoDao extends BaseDao {

	/**
	 * 获取凭证字编号(ttype为'M')
	 * @param tcomanyId 	帐套ID
	 * @param voucherWId	凭证字ID
	 * @param year			会计期间-年
	 * @param month			会计期间-月
	 * @return
	 */
	public AcctVoucherNo getVoucherNoByProperty(int tcomanyId, int voucherWId, int year, int month);
	
//	public int getTnumberByProperty(int tcompanyId, int voucherWId, int year, int month);
	
	/**
	 * 更新凭证字编号
	 * @param voucherNo 
	 * @param number	要更新的voucherNo.id.tnumber的值
	 */
	public boolean updateNumber(AcctVoucherNo voucherNo, int number);
}
