package cn.sf_soft.user.service;

import java.util.List;
import java.util.Map;

import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;

public interface MobileAttestationService {
	/**
	 * 得到所有的站点
	 * @return
	 */
	public Map<String, String> getSysStations();
	/**
	 * 根据姓名 查看是否有这个user存在
	 * @param userName
	 * @return
	 */
	public List<SysUsers> getUser(String userName);
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
	
	public void update(SysComputerKey ck);
}
