package cn.sf_soft.basedata.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import cn.sf_soft.basedata.dao.SysCodeRulesDao;
import cn.sf_soft.basedata.model.SysCodeRules;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("sysCodeRulesDao")
public class SysCodeRulesDaoHibernate extends BaseDaoHibernateImpl  implements SysCodeRulesDao{
	
	@SuppressWarnings("deprecation")
	public List<SysCodeRules> getSysCodeRules(final String ruleNo,final String stationId) {
		@SuppressWarnings("unchecked")
		List<SysCodeRules> list = (List<SysCodeRules>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				return (session.createQuery("  FROM SysCodeRules b WHERE b.ruleNo=? AND b.stationId =? "))
					.setCacheable(true)
					.setString(0, ruleNo)
					.setString(1, stationId)
					.list();
			}
		});
		return list;
	}
	
	public  void updateSysCodeRules(SysCodeRules code) {
		getHibernateTemplate().update(code);
	}
/*	public static void main(String[] args){
		SysCodeRulesDaoHibernate dao = new SysCodeRulesDaoHibernate();
		
		List<SysCodeRules> list = dao.getSysCodeRules("PCS_NO","C");
		System.out.println(list.size());
	}*/
}
