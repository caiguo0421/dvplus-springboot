package cn.sf_soft.finance.voucher.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplate;
import cn.sf_soft.finance.voucher.model.AcctVoucherW;

public interface AcctVoucherTemplateDao extends BaseDao {

	/**
	 * 根据模板编号获取模板信息
	 * @param tno
	 * @return
	 */
	public AcctVoucherTemplate getTemplateByNo(String tno);
	/**
	 * 获取模板的帐套凭证字信息
	 * @param tno		模板编号
	 * @param companyId 帐套ID
	 * @return
	 */
	public AcctVoucherW getAcctVoucherWWithTnoAndCompanyId(String tno, int companyId);
}
