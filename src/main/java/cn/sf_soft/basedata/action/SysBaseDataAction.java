package cn.sf_soft.basedata.action;

import cn.sf_soft.basedata.model.BasePartType;
import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysBaseDataService;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.user.model.SysUsers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统基础数据
 * @创建人 LiuJin
 * @创建时间 2014-8-26 下午4:08:52
 * @修改人 
 * @修改时间
 */
public class SysBaseDataAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5407056762837313021L;
	
	@Autowired
	private SysBaseDataService baseDataService;

	public String getSysStations(){
		setResponseData(baseDataService.getSysStations());
		return SUCCESS;
	}
	
	public String getBasePartsType(){
		setResponseData(baseDataService.getBasePartsType());
		return SUCCESS;
	}

	public String getDepartments(){
		setResponseData(baseDataService.getDepartments());
		return SUCCESS;
	}
	
	public String getSysBaseDatas(){
		List<SysStations> sysStations = baseDataService.getSysStations();
		List<BasePartType> basePartsTypes = baseDataService.getBasePartsType();
		Map<String, Object> sysBaseDatas = new HashMap<String, Object>(2);
		sysBaseDatas.put("sysStations", sysStations);
		sysBaseDatas.put("basePartsTypes", basePartsTypes);
		setResponseData(sysBaseDatas);
		return SUCCESS;
	}
}
