package cn.sf_soft.oa.message.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.oa.action.OaAction;
import cn.sf_soft.oa.message.model.OaMessage;
import cn.sf_soft.oa.message.model.OaMessageNotification;


@Service("oaMessageService")
public class OaMessageService {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OaAction.class);
	
	@Autowired
	@Qualifier("baseDao")
	protected BaseDao dao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseMessage known(Integer notificationId, Short msgTypeNo) {
		logger.debug(String.format("调用known接口,notificationId:%d,msgTypeNo:%d", notificationId,msgTypeNo));
		String userNo = "";
		//先通过 notificationId查找
		if(notificationId!=null){
			OaMessageNotification oaMessageNotification = dao.get(OaMessageNotification.class, notificationId);
			if(oaMessageNotification!=null){
				userNo = oaMessageNotification.getMsg().getUserNo();
				msgTypeNo = oaMessageNotification.getMsg().getMsgTypeNo();
			}
		}
		
		if(StringUtils.isEmpty(userNo)){
			userNo = HttpSessionStore.getSessionUser().getUserNo();
		}
		
		//该用户同类型的消息都设置成已阅读
		List<OaMessage>  msgList = (List<OaMessage>) dao.findByHql("from OaMessage where userNo = ? and msgTypeNo = ? and known = false", userNo,msgTypeNo);
		for(OaMessage msg:msgList){
			msg.setKnown(true);
			dao.update(msg);
		}
		return new ResponseMessage();
	}

	

}
