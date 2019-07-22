package cn.sf_soft.basedata.service.impl;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.dao.SysCodeRulesDao;
import cn.sf_soft.basedata.model.SysCodeRules;
import cn.sf_soft.basedata.service.SysCodeRulesService;
@Service("sysCodeRulesService")
public class SysCodeRulesServiceImpl  implements SysCodeRulesService{
	@Autowired
	private SysCodeRulesDao dao;
	
	public void setDao(SysCodeRulesDao dao) {
		this.dao = dao;
	}

	public String createSysCodeRules(String ruleNo, String stationId) {
		SysCodeRules code = dao.getSysCodeRules(ruleNo, stationId).get(0);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumIntegerDigits(code.getNumberWidth());
		nf.setGroupingUsed(false);
		Date dt = new Date();
		String no = stationId+code.getPrefix();
		SimpleDateFormat nodf = null;
		if(code.getResetMode() == 1){
			 nodf = new SimpleDateFormat("yyyy");
			if(!nodf.format(code.getAssignTime()).equals(nodf.format(dt))){
				code.setSerialNumber(1);
			} 
		}
		if(code.getResetMode() == 2){
			 nodf = new SimpleDateFormat("yyyy-MM");
			 if(!nodf.format(code.getAssignTime()).equals(nodf.format(dt))){
				code.setSerialNumber(1);
			} 
		}
		if(code.getResetMode() == 3){
			 nodf = new SimpleDateFormat("yyyy-MM-dd");
			 if(!nodf.format(code.getAssignTime()).equals(nodf.format(dt))){
				code.setSerialNumber(1);
			} 
		}
		//截取年份
		if(code.getYearFormat() == true){
			no +=  nodf.format(new Date()).replaceAll("-","");
		}else{
			String date =   nodf.format(dt).replaceAll("-","");
			no += date.substring(2, date.length());
		}
		no += nf.format(code.getSerialNumber());
		//更新编码的当前值
		updateSysCodeRules(code);
		return no;
	}

	public void updateSysCodeRules(SysCodeRules code){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		code.setSerialNumber(code.getSerialNumber()+1);
		code.setAssignTime(Timestamp.valueOf(df.format(new Date())));
		dao.update(code);
	}
}
