package cn.sf_soft.finance.voucher.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.voucher.dao.AcctVoucherNoDao;
import cn.sf_soft.finance.voucher.model.AcctVoucherNo;
/**
 * 凭证字号
 * @author liujin
 *
 */
@Repository("acctVoucherNoDao")
public class AcctVoucherNoDaoHibernate extends
		BaseDaoHibernateImpl implements AcctVoucherNoDao {

	/**
	 * 获取凭证字编号
	 * @param tcomanyId 	帐套ID
	 * @param voucherWId	凭证字ID
	 * @param year			会计期间-年
	 * @param month			会计期间-月
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AcctVoucherNo getVoucherNoByProperty(final int tcomanyId, final int voucherWId, final int year,
			final int month) {
		List<Object> list = (List<Object>) getHibernateTemplate().find("FROM AcctVoucherNo a where a.ttype='M' AND a.id.tcompanyId=? AND a.id.tvoucherWId=? AND a.id.tyear=? AND a.id.tmonth=?", new Object[]{tcomanyId, voucherWId, year, month});
		if(list.size() == 0){
			return null;
		}
		return (AcctVoucherNo) list.get(0);
	}

	/*
	public int getTnumberByProperty(final int tcompanyId, final int voucherWId, final int year, final int month){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@SuppressWarnings("unchecked")
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Integer> list = session.createSQLQuery("SELECT tnumber FROM acct_voucher_no where ttype='M' AND tcompany_id=? AND tvoucher_w_id=? AND tyear=? AND tmonth=?")
						.setInteger(0, tcompanyId)
						.setInteger(1, voucherWId)
						.setInteger(2, year)
						.setInteger(3, month)
						.list();
				if (list.size() == 0) {
					return -1;
				}
				return list.get(0);
			}
		});
	}*/
	
	public boolean updateNumber(final AcctVoucherNo voucherNo, final int number){
		int i = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Integer doInHibernate(Session session)
					throws HibernateException {
				return session.createSQLQuery("UPDATE acct_voucher_no set tnumber = ? WHERE tcompany_id=? AND tyear=? AND tmonth=? AND tvoucher_w_id=? AND tnumber=?")
						.setInteger(0, number)
						.setInteger(1, voucherNo.getId().getTcompanyId())
						.setInteger(2, voucherNo.getId().getTyear())
						.setInteger(3, voucherNo.getId().getTmonth())
						.setInteger(4, voucherNo.getId().getTvoucherWId())
						.setInteger(5, voucherNo.getId().getTnumber())
						.executeUpdate();
				
			}
		});
		return i==1;
	}
	
	
}
