package cn.sf_soft.user.model;

import java.util.Map;

/**
 * 手机认证初始化时加载的信息
 * @author cw
 * @creation 
 */
public class MobileAttestationInitData {

	private SysComputerKey mobileKey;
	private Map<String, String> stationIds;
	public SysComputerKey getMobileKey() {
		return mobileKey;
	}
	public void setMobileKey(SysComputerKey mobileKey) {
		this.mobileKey = mobileKey;
	}
	public Map<String, String> getStationIds() {
		return stationIds;
	}
	public void setStationIds(Map<String, String> stationIds) {
		this.stationIds = stationIds;
	}
	@Override
	public String toString() {
		return "MobileAttestationInitData [mobileKey=" + mobileKey
				+ ", stationIds=" + stationIds + "]";
	}
}
