package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VwFinanceGuarantorsDao;
import cn.sf_soft.office.approval.model.VwFinanceGuarantors;
/**
 * 担保人担保额的视图
 * @author king
 * @create 2013-2-27下午04:15:47
 */
@Repository("vwFinanceGuarantorDao")
public class VwFinanceGuarantorsDaoHibernate extends BaseDaoHibernateImpl
		implements VwFinanceGuarantorsDao {
	private static final Logger log = LoggerFactory
	.getLogger(VwFinanceGuarantorsDaoHibernate.class);
	
	
	@SuppressWarnings("unchecked")
	public List<VwFinanceGuarantors> findByProperty(String propertyName,
			Object value) {
		log.debug("finding VwFinanceGuarantors instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from VwFinanceGuarantors as model where model."
					+ propertyName + "= ?";
			return (List<VwFinanceGuarantors>) getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public void update(VwFinanceGuarantors vwFinanceGuarantors) {
		log.debug("update vwFinanceGuarantors");
		try {
			getHibernateTemplate().update(vwFinanceGuarantors);
			log.debug("update vwFinanceGuarantors successful");
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error("update vwFinanceGuarantors failed", e);
			throw e;
		}

	}

}
