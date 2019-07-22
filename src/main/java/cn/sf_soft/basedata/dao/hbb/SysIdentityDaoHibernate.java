package cn.sf_soft.basedata.dao.hbb;

import org.springframework.stereotype.Repository;
import cn.sf_soft.basedata.dao.SysIdentityDao;
import cn.sf_soft.basedata.model.SysIdentities;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;

@Repository("sysIdentiryDao")
public class SysIdentityDaoHibernate extends BaseDaoHibernateImpl
		implements SysIdentityDao {

	/**
	 * 根据表名获取表的自增长ID，并将其ID增长之后更新至表中
	 * @param tableName
	 * @return
	 */
	public int getIdentityAndIncrementUpdate(String tableName) {
		SysIdentities identity = (SysIdentities)getHibernateTemplate().find("FROM SysIdentities s WHERE s.tableName=?", tableName).get(0);
		int id = identity.getNextNumber();
		identity.setNextNumber(id + identity.getIncrement());
		getHibernateTemplate().save(identity);
		return id;
	}

}
