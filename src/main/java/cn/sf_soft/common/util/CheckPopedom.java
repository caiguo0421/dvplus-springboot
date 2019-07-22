package cn.sf_soft.common.util;

import cn.sf_soft.user.model.SysUsers;

public class CheckPopedom {

	public static boolean checkPopedom(SysUsers user,String popedom){
		boolean flag = false;
		for(String s : user.getPopedomIds()){
			if(s.equals(popedom)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
