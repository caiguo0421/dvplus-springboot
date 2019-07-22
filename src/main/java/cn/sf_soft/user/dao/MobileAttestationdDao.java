package cn.sf_soft.user.dao;

import java.util.List;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;

public interface MobileAttestationdDao {
	/**
	 * 得到所有的站点
	 * @return
	 */
	public List<SysStations> getSysStations();
	/**
	 * 根据姓名 查看是否有这个user存在
	 * @param userName
	 * @return
	 */
	public List<SysUsers> getUser(String userName);
	/**
	 * 检查手机是否认证过
	 * @param computerKey
	 * @return
	 */
//	public List<SysComputerKey> checkComputerKey(String computerKey);
	
	/**
	 * 根据设备的唯一标识来得到设备的认证信息记录
	 * @param computerKey
	 * @return
	 */
	public List<SysComputerKey> getComputerKeyByKey(String computerKey);
	/**
	 * 添加手机认证等待审批
	 */
	public void saveMobileAttestation(SysComputerKey ck);
	/**
	 * 修改手机认证审批状态
	 * @param ck
	 */
	public void updateMobileAttestation(SysComputerKey ck);
}
