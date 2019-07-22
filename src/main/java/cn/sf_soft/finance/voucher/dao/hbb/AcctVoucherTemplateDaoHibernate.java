package cn.sf_soft.finance.voucher.dao.hbb;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.voucher.dao.AcctVoucherTemplateDao;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplate;
import cn.sf_soft.finance.voucher.model.AcctVoucherW;

/**
 * 凭证模板
 * @author liujin
 *
 */
@Repository("acctVoucherTemplateDao")
public class AcctVoucherTemplateDaoHibernate extends
		BaseDaoHibernateImpl implements AcctVoucherTemplateDao {

	/**
	 * 根据模板编号获取模板信息
	 * @param tno
	 * @return
	 */
	public AcctVoucherTemplate getTemplateByNo(String tno){
		return (AcctVoucherTemplate) getHibernateTemplate().find("FROM AcctVoucherTemplate a where a.tno = ?", tno).get(0);
	}
	
	/**
	 * 获取模板的帐套凭证字信息
	 * @param tno		模板编号
	 * @param companyId 帐套ID
	 * @return
	 */
	public AcctVoucherW getAcctVoucherWWithTnoAndCompanyId(final String tno, final int companyId){
		return (AcctVoucherW) getHibernateTemplate().execute(new HibernateCallback() {
			public AcctVoucherW doInHibernate(Session session)
					throws HibernateException {
				return (AcctVoucherW) session.createSQLQuery("SELECT  b.* FROM acct_voucher_template_w a	INNER JOIN acct_voucher_w b ON a.tvoucher_w_id = b.tvoucher_w_id WHERE  a.tno = ? AND b.tcompany_id = ?")
					   .addEntity(AcctVoucherW.class)
					    .setString(0, tno)
					   .setInteger(1, companyId)
					   .list()
					   .get(0);
			}
		});
	}
}
