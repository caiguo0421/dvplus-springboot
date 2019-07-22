package cn.sf_soft.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.user.dao.MobileAttestationdDao;
import cn.sf_soft.user.model.SysComputerKey;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.MobileAttestationService;
@Service("mobileAtteStationService")
public class MobileAttestationServiceImpl implements MobileAttestationService {
	@Autowired
	@Qualifier("mobileAtteStationDao")
	private MobileAttestationdDao dao;
	
	public void setDao(MobileAttestationdDao dao) {
		this.dao = dao;
	}
	public Map<String, String> getSysStations(){
		Map<String, String> map=new HashMap<String, String>();
		List<SysStations> station=dao.getSysStations();
		for(SysStations s:station){
			map.put(s.getStationName(),s.getStationId());
		}
		return map;
	}
	public List<SysUsers> getUser(String userName){
		return dao.getUser(userName);
	}
	public List<SysComputerKey> getComputerKeyByKey(String computerKey){
		return dao.getComputerKeyByKey(computerKey);
	}
	public void saveMobileAttestation(SysComputerKey ck){
		 dao.saveMobileAttestation(ck);
	}
	public void update(SysComputerKey ck) {
		dao.updateMobileAttestation(ck);
		
	}
}
