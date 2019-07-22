package cn.sf_soft.basedata.dao.hbb;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;
import cn.sf_soft.basedata.dao.BaseOthersDao;
import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;

/**
 * 基础资料－其他资料
 * @author liujin
 *
 */
@Repository("baseOtherDao")
public class BaseOthersDaoHibernateImpl extends BaseDaoHibernateImpl  implements BaseOthersDao {

	public List<String> getDataByTypeNo(final String typeNo) {
		List<String> list = (List<String>) getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
			
			@SuppressWarnings("unchecked")
			public List<String> doInHibernate(Session session) throws HibernateException{
				return session.createQuery("select b.data from BaseOthers b where b.typeNo=?")
					.setCacheable(true)
					.setString(0, typeNo)
					.list();
			}
		});
		return list;
	}

	public List<String> getDataByTypeNo(final String typeNo, final String stationId){
		List<String> list = (List<String>) getHibernateTemplate().execute(new HibernateCallback<List<String>>() {
			
			@SuppressWarnings("unchecked")
			public List<String> doInHibernate(Session session) throws HibernateException{
				return session.createQuery("SELECT b.data FROM BaseOthers b where b.typeNo=? and (b.stationId IS NULL OR b.stationId LIKE :stationId)")
					.setCacheable(true)
					.setString(0, typeNo)
					.setString("stationId", "%"+stationId+"%")
					.list();
			}
		});
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<BaseOthers> getBaseOtherByTypeNo(String typeNo) {
		List<BaseOthers> list = (List<BaseOthers>) getHibernateTemplate().find("SELECT new BaseOthers(dataId, data, parentId) FROM BaseOthers b where b.typeNo = ?", typeNo);
		return list;
	}
	@SuppressWarnings({ "unchecked" })
	public List<BaseOthers> getBaseOtherByTypeNoAndStationId(final String typeNo,
			final String stationId) {
		return getHibernateTemplate().execute(new HibernateCallback<List<BaseOthers>>() {

			public List<BaseOthers> doInHibernate(Session session)
					throws HibernateException {
				return session.createQuery("SELECT new BaseOthers(dataId, data, parentId) FROM BaseOthers b where b.typeNo=? and (b.stationId IS NULL OR b.stationId LIKE :stationId)")
						.setCacheable(true)
						.setString("stationId", "%"+stationId+"%")
						.setString(0, typeNo)
						.list();
			}
		});
	}
	
}
