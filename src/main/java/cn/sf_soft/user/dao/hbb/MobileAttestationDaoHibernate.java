package cn.sf_soft.user.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.user.dao.MobileAttestationdDao;
import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;
/**
 * 
 * @author cw
 */
@Repository("mobileAtteStationDao")
public class MobileAttestationDaoHibernate extends BaseDaoHibernateImpl implements MobileAttestationdDao {

	/**
	 * 得到所有的站点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysStations> getSysStations(){
		return getHibernateTemplate().loadAll(SysStations.class);
	}
	/**
	 * 根据姓名 查看是否有这个user存在
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUsers> getUser(String userName){
		List<SysUsers> user = (List<SysUsers>) getHibernateTemplate().find("SELECT u.userId from SysUsers u where u.userName=?", userName);
		return user;
//		return findByNamedQueryAndNamedParam("getuser", new String[]{"userName"}, new String[]{userName});
	}
	/**
	 * 检查手机是否认证过
	 * @param computerKey
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public List<SysComputerKey> checkComputerKey(String computerKey){
//		List<SysComputerKey> key = getHibernateTemplate().find("from SysConputerKey k where k.computerKey=?", computerKey);
//		return key;
//		return findByNamedQueryAndNamedParam("checkComputerKey", new String[]{"computerKey"}, new String[]{computerKey});
//	}
	/**
	 * 添加手机认证等待审批
	 */
	public void saveMobileAttestation(SysComputerKey ck){
		getHibernateTemplate().save(ck);
	}
	/**
	 * 修改手机认证状态
	 */
	public void updateMobileAttestation(SysComputerKey ck){
		getHibernateTemplate().saveOrUpdate(ck);
	}
	
	@SuppressWarnings("unchecked")
	public List<SysComputerKey> getComputerKeyByKey(String computerKey) {
		List<SysComputerKey> key = (List<SysComputerKey>) getHibernateTemplate().find("from SysComputerKey k where k.computerKey=?", computerKey);
		return key;
	}
}
