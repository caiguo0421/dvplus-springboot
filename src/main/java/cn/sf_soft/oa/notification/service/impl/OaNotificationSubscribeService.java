package cn.sf_soft.oa.notification.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.oa.notification.model.OaNotificationSubscribe;
import cn.sf_soft.user.model.SysUsers;

@Service("oaNotificationSubscribeService")
public class OaNotificationSubscribeService {
	private static final int IOS_PRODUCT_ID = 100;
	private static final int ANDROID_PRODUCT_ID = 101;
	private static final int OTHER_PRODUCT_ID = 109;

	@Autowired
	@Qualifier("baseDao")
	protected BaseDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseMessage registerPushToken(String deviceId,
			String terminalInfo, String pushToken, SysUsers user,
			boolean subscribed, String brand, String osVersion) {
		Short deviceTypeNo = null;
		Integer productId = null;
		if (user == null || StringUtils.isEmpty(user.getUserNo())) {
			throw new ServiceException("注册失败：用户编号为空");
		}
		if (StringUtils.isEmpty(deviceId)) {
			throw new ServiceException("注册失败：设备Id为空");
		}
		if (StringUtils.isNotEmpty(terminalInfo)){
			if(StringUtils.containsIgnoreCase(terminalInfo, "iPhone")) {
				deviceTypeNo = 10;
				productId = IOS_PRODUCT_ID;
			}else if(StringUtils.containsIgnoreCase(terminalInfo, "Android")){
				deviceTypeNo = 10;
				productId = ANDROID_PRODUCT_ID;
			}else{
				deviceTypeNo = 10;
				productId = OTHER_PRODUCT_ID;
			}
		} else {
			throw new ServiceException("注册失败：未知的设备类型" + terminalInfo);
		}

		OaNotificationSubscribe subscribe = null;
		List<OaNotificationSubscribe> subscribeList = (List<OaNotificationSubscribe>) dao
				.findByHql(
						"from OaNotificationSubscribe where deviceId = ? AND productId = ? AND deviceTypeNo = ?",
						deviceId, productId, deviceTypeNo);
		if (subscribeList != null && subscribeList.size() > 0) {
			subscribe = subscribeList.get(0);
		} else {
			subscribe = new OaNotificationSubscribe();
			subscribe.setDeviceId(deviceId);
			subscribe.setProductId(productId);
		}

		subscribe.setDeviceTypeNo(deviceTypeNo);
		subscribe.setToken(pushToken);
		subscribe.setUserNo(user.getUserNo());
		subscribe.setSubscribed(subscribed);
		subscribe.setBrand(brand);
		subscribe.setOsVersion(osVersion);

		dao.update(subscribe);
		return new ResponseMessage();

	}

}
