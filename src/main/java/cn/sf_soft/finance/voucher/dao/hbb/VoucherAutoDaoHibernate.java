package cn.sf_soft.finance.voucher.dao.hbb;

import java.sql.CallableStatement;
import java.sql.Connection;

import javax.sql.DataSource;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.voucher.dao.VoucherAutoDao;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author scs@sf-soft.cn
 * @date 2015-5-18 上午9:46:13
 */
@Repository("voucherAutoDao")
public class VoucherAutoDaoHibernate extends BaseDaoHibernateImpl implements
		VoucherAutoDao {
	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VoucherAutoDaoHibernate.class);

	@SuppressWarnings("deprecation")
	public boolean generateVoucherByProc(String sql, String tno, boolean isSAB,
			String userID, String documentNo) {

		try {
			SessionFactory sessionFactory = this.getSessionFactory();
			DataSource dataSource = SessionFactoryUtils.getDataSource(sessionFactory);
			Session session = sessionFactory.getCurrentSession();

			session.flush();

			Connection connection = dataSource.getConnection();
			CallableStatement cstmt = connection
					.prepareCall("{ ?=call dbo.SP_GenerateVoucher(?,?,?,?,?,?)}");
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

			cstmt.setString(2, sql);
			logger.info("sql:" + sql);
			cstmt.setString(3, tno);
			logger.info("tno:" + tno);
			cstmt.setBoolean(4, isSAB);
			logger.info("isSAB:" + isSAB);
			cstmt.setString(5, userID);
			logger.info("userID:" + userID);
			cstmt.setString(6, documentNo);
			logger.info("documentNo:" + documentNo);
			cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);

			cstmt.execute();
			logger.info("RETURN STATUS: " + cstmt.getInt(1));
			logger.info("error message: " + cstmt.getString(7));
			if (cstmt.getInt(1) != 1000) {
				throw new ServiceException(cstmt.getString(7));
			}
			cstmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("服务器内部错误");
		}
		return true;
	}
}
