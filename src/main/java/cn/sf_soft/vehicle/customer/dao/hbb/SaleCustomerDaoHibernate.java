package cn.sf_soft.vehicle.customer.dao.hbb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.util.CheckPopedom;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SaleCustomerDao;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.BaseVehicleVisitorsBack;
import cn.sf_soft.vehicle.customer.model.CustomerMaintenanceWorkgroup;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicle;
import cn.sf_soft.vehicle.customer.model.LastPresellVisitors;
import cn.sf_soft.vehicle.customer.model.ObjectOfPlace;
import cn.sf_soft.vehicle.customer.model.OjbectNameForCheck;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsForCheck;
import cn.sf_soft.vehicle.customer.model.TreeNodeModel;
import cn.sf_soft.vehicle.customer.model.UserMaintenanceWorkgroups;
import cn.sf_soft.vehicle.customer.model.UserUnitRelation;
import cn.sf_soft.vehicle.customer.model.VehicleType;

/**
 * 
 * @Title: 意向客户的dao
 * @date 2013-12-11 上午10:07:53 
 * @author cw
 */
@Repository("saleCustomerDao")
public class SaleCustomerDaoHibernate extends  BaseDaoHibernateImpl implements SaleCustomerDao{
	//private ThreadLocal<Map<String,String>> SqlVisitorMap = new ThreadLocal<Map<String,String>>();
	
	private ThreadLocal<String> multiClueTL = new ThreadLocal<String>();
	private ThreadLocal<String> sqlVisitorsTL = new ThreadLocal<String>();
	private ThreadLocal<String> sqlVisitors2TL = new ThreadLocal<String>();
	private ThreadLocal<String> sqlMasterTL = new ThreadLocal<String>();
	private ThreadLocal<String> conditionVTL = new ThreadLocal<String>();
	private ThreadLocal<String> conditionMTL = new ThreadLocal<String>();
	
	public void initData(){
		String sqlVisitors = "SELECT * FROM (SELECT a.*,b.meaning AS visit_addr_meaning, "+
				"c.meaning AS buy_type_meaning,d.meaning AS visit_result_meaning, "+
				/*e.meaning AS clue_type_meaning,*/"f.meaning AS last_visit_result_meaning,"+
	            "(SELECT  TOP (1) real_back_time FROM presell_visitors_back WHERE visitor_no  = a.visitor_no "+
	            "ORDER BY  real_back_time DESC)last_visit_time   "+
				"FROM presell_visitors a "+
				"LEFT JOIN (SELECT code,meaning FROM sys_flags WHERE field_no='visit_addr') b ON a.visit_addr=b.code "+
				"LEFT JOIN (SELECT code,meaning FROM sys_flags WHERE field_no='vs_buy_type') c ON a.buy_type=c.code "+
				"LEFT JOIN (SELECT code,meaning FROM sys_flags WHERE field_no='visit_result') d ON a.visit_result=d.code "+
				/*"LEFT JOIN (SELECT code,meaning FROM sys_flags WHERE field_no='clue_type') e ON a.clue_type=e.code  "+*/
				"LEFT JOIN (SELECT code,meaning FROM sys_flags WHERE field_no='visit_result') f ON a.last_visit_result=f.code  "+
				") presell_visitors WHERE 1=1    ";
		
		String sqlVisitors2 = "";
		String sqlMaster = "";
		String conditionV = "";
	    String conditionM = "";
	    String multiClue = "";//多线索跟进
	    sqlVisitorsTL.set(sqlVisitors);
	    sqlVisitors2TL.set(sqlVisitors2);
	    sqlMasterTL.set(sqlMaster);
	    conditionVTL.set(conditionV);
	    conditionMTL.set(conditionM);
		multiClueTL.set(multiClue);
		
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * 查找当前用户关联的部门
	 */
	public List<String> getCurrentUserUnitRelation (String userId){
		List<UserUnitRelation> list = findByNamedQueryAndNamedParam("getCurrentUserUnitRelation", new String[]{"userId"}, new String[]{userId});
		List<String> lst = new ArrayList<String>();
		for(UserUnitRelation u:list){
			lst.add(u.getUnitRelation());
		}
		return lst;
	}
	/**
	 * 获取当前部门下的所有人员ID
	 */
	@SuppressWarnings({ "unchecked" })
    private String GetSellerIds(String userId){
    	List<String> list = findByNamedQueryAndNamedParam("getSellerIds", new String[]{"userId"}, new String[]{userId});
    	
    	StringBuilder sellerIds = new StringBuilder();
    	if(list.size() > 0){
			for(String s:list){
				sellerIds.append("'"+s+"'"+",");
			}
			sellerIds.deleteCharAt(sellerIds.length() - 1);
    	}
    	return sellerIds.toString();
    	//return list.toArray();
    }
	/**
	 * 是否允许多线索
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllowMultiClue(){
		List<String> list = findByNamedQueryAndNamedParam("getAllowMultiClue", null, null);
		return list;
	}
	/**
	 * 是否销售组长
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getIsGroupLeader (String userId){
		List<String> list = findByNamedQueryAndNamedParam("getIsGroupLeader", new String[]{"userId"}, new String[]{userId});
		return list;
	}
	/**
	 * 初始化权限判断
	 * @param user
	 */
	@SuppressWarnings("unused")
	public  void initPopedom(SysUsers user){
		List<String> leaderList = getIsGroupLeader(user.getUserId());
		//boolean check = CheckPopedom.checkPopedom(user,"10201020");//跨部门检查
		//boolean checkDep = CheckPopedom.checkPopedom(user,"10201023");//部门检查
		boolean check = CheckPopedom.checkPopedom(user,"10201220");//跨部门检查更新为10201220
		boolean checkDep = CheckPopedom.checkPopedom(user,"10201210");//部门检查更新为10201210
		boolean backCs = CheckPopedom.checkPopedom(user,"10201030");//客户回访
		boolean isGroupLeader = leaderList.size()>0;
		boolean allowMultiClue = getAllowMultiClue().get(0).equals("允许");
		initData();
		String sqlVisitors = sqlVisitorsTL.get()==null?"":sqlVisitorsTL.get();
		String multiClue = multiClueTL.get()==null?"":multiClueTL.get();
		String sqlVisitors2 = sqlVisitors2TL.get()==null?"":sqlVisitors2TL.get();
		String sqlMaster = sqlMasterTL.get()==null?"":sqlMasterTL.get();
		String conditionV = conditionVTL.get()==null?"":conditionVTL.get();
		String conditionM = conditionMTL.get()==null?"":conditionMTL.get();
		
		if(allowMultiClue == false){
			multiClue += "( SELECT TOP ( 1 ) "+
                             "   seller_id "+
                    "  FROM      presell_visitors n "+
                    "  WHERE     n.visitor_id = a.object_id "+
                      "         AND ( n.visit_result IS NULL "+
                        "              OR n.visit_result = '' ) "+
                  "  )";
		}else{
			multiClue += "NULL";
		}
		//没有跨部门检查，不是销售组长，没有客服回访权限，没有部门检查权限
		if(!(check || isGroupLeader || backCs || checkDep)){
			sqlVisitors2 += "AND ((creator = '"+user.getUserName()+'('+user.getUserNo()+')'+"' OR seller_id='"+user.getUserId()+"')[where])";
            sqlMaster = "AND (sel_id is null OR (creator='"+user.getUserName()+'('+user.getUserNo()+')'+"' OR sel_id='"+user.getUserId()+"'[where]))";
          
			List<String> lst = getCurrentUserUnitRelation(user.getUserId());
			//如果当前用户是配件部的则允许看到有购买配件意向的线索,10销售部，13消贷部，15运输部，20配件部
            //30维修部，40客服部，50办公室，60财务部
			if(lst.contains("20")){
				conditionV += "OR ( isnull(part_flag,0)=1 )";
			}
			  //如果当前用户是维修部的则允许看到有维修意向的线索
			if(lst.contains("30")){
				conditionV += "OR ( isnull(service_flag,0)=1 )";
			}
			//如果当前用户是客服部的则允许看到有购买保险意向的线索
			if(lst.contains("40")){
				conditionV += "OR ( isnull(insurance_flag,0)=1 )";
			}
			//如果当前用户是消贷部的则允许看到做消贷意向的线索
			if(lst.contains("13")){
				conditionV += "OR ( isnull(buy_type,10)=20 )";
			}
			sqlVisitors2 =  sqlVisitors2.replace("[where]", conditionV);
			conditionM += "OR object_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+")t )";
			sqlMaster = sqlMaster.replace("[where]", conditionM);
		}
		//只有组长的权限
		if(!check  && !backCs && !checkDep && isGroupLeader){
			String swId = leaderList.get(0);
			sqlVisitors2 += "AND ((creator = '"+user.getUserName()+'('+user.getUserNo()+')'+"' OR seller_id='"+user.getUserId()+"')[where])";
			 sqlMaster += "AND (sel_id is null OR (creator='"+user.getUserName()+'('+user.getUserNo()+')'+"' OR sel_id='"+user.getUserId()+"'[where]))";
			conditionV += " OR (creator IN (SELECT user_name + '(' + user_no + ')' AS creator FROM sys_users  "
						+"	WHERE user_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id = '" + swId + "')) "
						+"	OR (  " 
                		+"	seller_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id = '" + swId + "')) "
                			+"	)";
			sqlVisitors2 =  sqlVisitors2.replace("[where]", conditionV);
			conditionM += "OR object_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+")t )";
			sqlMaster = sqlMaster.replace("[where]", conditionM);
		}
		 //有部门检查权限且有组长权限时
		if(!check  && !backCs && checkDep && isGroupLeader){
			String swId = leaderList.get(0);
			sqlVisitors2 += "AND ((creator = '"+user.getUserName()+'('+user.getUserNo()+')'+"' OR seller_id='"+user.getUserId()+"')[where])";
			sqlMaster += "AND (sel_id is null OR (creator='"+user.getUserName()+'('+user.getUserNo()+')'+"' OR sel_id='"+user.getUserId()+"'[where]))";
			String sellerIds = GetSellerIds(user.getUserId());
			conditionV += "OR (creator IN (SELECT user_name + '(' + user_no + ')' AS creator FROM sys_users  "
						+"	WHERE user_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id = '" + swId + "')) "
						+"  OR ( "
						+"	seller_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id = '" + swId + "')) "
						+"   OR  seller_id IN (" + sellerIds + ") "
						+"	)";
			sqlVisitors2 = sqlVisitors2.replace("[where]", conditionV);
			conditionM += "OR object_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+")t )";
			sqlMaster = sqlMaster.replace("[where]", conditionM);
		}
		//只有部门权限时
		if(!check  && !backCs && checkDep && !isGroupLeader){
			sqlVisitors2 += "AND ((creator = '"+user.getUserName()+'('+user.getUserNo()+')'+"' OR seller_id='"+user.getUserId()+"')[where])";
			sqlMaster += "AND (sel_id is null OR (creator='"+user.getUserName()+'('+user.getUserNo()+')'+"' OR sel_id='"+user.getUserId()+"'[where]))";
			//找出当前部门下的销售员
			 String sellerIds = GetSellerIds(user.getUserId());  
			conditionV += "OR  seller_id IN (" + sellerIds + ") ";
			sqlVisitors2 = sqlVisitors2.replace("[where]", conditionV);
			conditionM += "OR object_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+")t )";
			sqlMaster = sqlMaster.replace("[where]", conditionM);
		}
		sqlVisitorsTL.set(sqlVisitors);
	    sqlVisitors2TL.set(sqlVisitors2);
	    sqlMasterTL.set(sqlMaster);
	    conditionVTL.set(conditionV);
	    conditionMTL.set(conditionM);
		multiClueTL.set(multiClue);
	}


	/**
	 * 获得用户信息
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> getSaleCustomer(final SysUsers user,final String nameOrMobile){
		return (List<CustomerVO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				String sql = null;
				initPopedom(user);
				String sqlVisitors = sqlVisitorsTL.get()==null?"":sqlVisitorsTL.get();
				String multiClue = multiClueTL.get()==null?"":multiClueTL.get();
				String sqlVisitors2 = sqlVisitors2TL.get()==null?"":sqlVisitors2TL.get();
				String sqlMaster = sqlMasterTL.get()==null?"":sqlMasterTL.get();
				String conditionV = conditionVTL.get()==null?"":conditionVTL.get();
				String conditionM = conditionMTL.get()==null?"":conditionMTL.get();
				
				try {
				 String def = "";
				 String findbyconditions="";
				 if("".equals(nameOrMobile) || nameOrMobile ==null){
						//def = "AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE  visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE real_back_time is NULL AND (DATEDIFF(day, plan_back_time, GETDATE()) >=0 AND  DATEDIFF(day, plan_back_time, GETDATE()) <=7)))";
					 Calendar c = Calendar.getInstance();
					 if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=0 AND DATEDIFF(day,plan_back_time,getdate())<=7))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-1 AND DATEDIFF(day,plan_back_time,getdate())<=0))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-6 AND DATEDIFF(day,plan_back_time,getdate())<=1))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-5 AND DATEDIFF(day,plan_back_time,getdate())<=2))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-4 AND DATEDIFF(day,plan_back_time,getdate())<=3))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-3 AND DATEDIFF(day,plan_back_time,getdate())<=4))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
						 def += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-2 AND DATEDIFF(day,plan_back_time,getdate())<=5))";
					 }
				 }else{
					 findbyconditions = "OR object_id NOT IN (SELECT distinct isnull(visitor_id,'') FROM presell_visitors) OR sel_id IS NULL ";
				 }
				String sqlMasterChird = "AND (object_id IN (SELECT visitor_id FROM presell_visitors WHERE ( visit_result is null AND visitor_id IN ( SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+") t) ) "+
                                     " OR  (  datediff(d,create_time,GETDATE())=0 AND visitor_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+") t)))"+findbyconditions+" )"+def;
				if("".equals(nameOrMobile) || nameOrMobile ==null){
					sqlMasterChird = sqlMasterChird.replaceAll("OR clue_type=20","");
				}
				sqlMaster += sqlMasterChird;
				sql = getQueryStringByName("getSaleCustomers", new String[]{"allowMultiClue", "SqlMaster"},new String[]{multiClue, sqlMaster});
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				sqlVisitors2 = "";
				sqlMaster = "";
				conditionM = "";
				conditionV = "";
				multiClue = "";
				sqlVisitorsTL.set(sqlVisitors);
			    sqlVisitors2TL.set(sqlVisitors2);
			    sqlMasterTL.set(sqlMaster);
			    conditionVTL.set(conditionV);
			    conditionMTL.set(conditionM);
				multiClueTL.set(multiClue);
				String parmVal = "%" + (nameOrMobile == null ? "":nameOrMobile) + "%";
				return session.createSQLQuery(sql)
						.addEntity(CustomerVO.class)
						.setParameter("nameOrMobile", parmVal)
						.list();
			}
		});
	}
	
	/**
	 * 快捷查询用户信息 如今日活动，明日活动等
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> getSaleCustomerForShortCut(final SysUsers user,final String identifier){
		return (List<CustomerVO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				initPopedom(user);
				String sqlVisitors = sqlVisitorsTL.get()==null?"":sqlVisitorsTL.get();
				String multiClue = multiClueTL.get()==null?"":multiClueTL.get();
				String sqlVisitors2 = sqlVisitors2TL.get()==null?"":sqlVisitors2TL.get();
				String sqlMaster = sqlMasterTL.get()==null?"":sqlMasterTL.get();
				String conditionV = conditionVTL.get()==null?"":conditionVTL.get();
				String conditionM = conditionMTL.get()==null?"":conditionMTL.get();
				try {
				 
				 sqlMaster += "AND last_seller is not null AND object_id IN (SELECT visitor_id FROM (" + sqlVisitors+sqlVisitors2 + ") t)";
				 //今日活动
				 if("today".equals(identifier)){
					 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())=0))";
				 }
				 //明日活动
				 else if("tomorrow".equals(identifier)){
					 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())=-1))";
				 }
				 //当周活动
				 else if("week".equals(identifier)){
					 Calendar c = Calendar.getInstance();
					 if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=0 AND DATEDIFF(day,plan_back_time,getdate())<=7))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-1 AND DATEDIFF(day,plan_back_time,getdate())<=0))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-6 AND DATEDIFF(day,plan_back_time,getdate())<=1))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-5 AND DATEDIFF(day,plan_back_time,getdate())<=2))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-4 AND DATEDIFF(day,plan_back_time,getdate())<=3))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-3 AND DATEDIFF(day,plan_back_time,getdate())<=4))";
					 }else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
						 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())>=-2 AND DATEDIFF(day,plan_back_time,getdate())<=5))";
					 }
				 }
				 //当月活动
				 else if("month".equals(identifier)){
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					 Calendar c = Calendar.getInstance();
					 int year = c.get(Calendar.YEAR);
					 int month = c.get(Calendar.MONTH)+1;
					 c.setTime(c.getTime());
					 c.add(Calendar.MONTH,1);
						//int day = c.get(Calendar.DATE);
					 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE plan_back_time>='" + year +"-"+month +"-1"+ "' AND plan_back_time<'" + sdf.format(c.getTime())+ "'))";
				 }
				 //所有意向
				 else if("allYX".equals(identifier)){
					 //SqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE clue_type='10')";
					 sqlMaster += "  AND object_id IN (SELECT visitor_id FROM dbo.presell_visitors WHERE visit_result IS NULL)  ";
				 }
				 //H:3天内成交
				 //A:15天内成交
				 //O:现场成交
				 //B:一个月内成交
				 else if("H".equals(identifier) || "A".equals(identifier) || "O".equals(identifier) || "B".equals(identifier)){
					 sqlMaster += " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_level Like'" + identifier + "')";
				 }
				 
				 //待检查回访
				 else if("noBack".equals(identifier)){
					 sqlMaster += "    AND object_id IN ( SELECT  visitor_id    FROM presell_visitors WHERE  visitor_no IN ( "+
                           		  " SELECT visitor_no FROM dbo.presell_visitors_back WHERE  check_content IS NULL "+
                               	  "  AND (DATEDIFF(day, plan_back_time, GETDATE()) >=0 AND  DATEDIFF(day, plan_back_time, GETDATE()) <=7)))     ";
				 }
				 //sql = getQueryStringByName("getSaleCustomers", new String[]{"SqlMaster","nameOrMobile"},new String[]{SqlMaster,"'%%'"});
				 sql = getQueryStringByName("getSaleCustomers", new String[]{"allowMultiClue","SqlMaster","nameOrMobile"},new String[]{multiClue,sqlMaster,"'%%'"});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				sqlVisitors2 = "";
				sqlMaster = "";
				conditionM = "";
				conditionV = "";
				multiClue = "";
				sqlVisitorsTL.set(sqlVisitors);
			    sqlVisitors2TL.set(sqlVisitors2);
			    sqlMasterTL.set(sqlMaster);
			    conditionVTL.set(conditionV);
			    conditionMTL.set(conditionM);
				multiClueTL.set(multiClue);
				return session.createSQLQuery(sql)
						.addEntity(CustomerVO.class)
						.list();
			}
		});
	}
	
	
	
	
	
	
	
	
	/**
	 * 获得线索信息
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsVO> getSaleClue(final SysUsers user,final String visitorId){
		initPopedom(user);
		String sqlVisitors = sqlVisitorsTL.get()==null?"":sqlVisitorsTL.get();
		String multiClue = multiClueTL.get()==null?"":multiClueTL.get();
		String sqlVisitors2 = sqlVisitors2TL.get()==null?"":sqlVisitors2TL.get();
		String sqlMaster = sqlMasterTL.get()==null?"":sqlMasterTL.get();
		String conditionV = conditionVTL.get()==null?"":conditionVTL.get();
		String conditionM = conditionMTL.get()==null?"":conditionMTL.get();
		String sql = "AND visitor_id=:visitorId  ORDER BY presell_visitors.create_time DESC";
		//SqlVisitors2 += sql;
		List<PresellVisitorsVO> list = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlVisitors+sqlVisitors2+sql).addEntity(PresellVisitorsVO.class).setParameter("visitorId", visitorId).list();
		sqlVisitors2 = "";
		sqlMaster = "";
		conditionM = "";
		conditionV = "";
		multiClue = "";
		sqlVisitorsTL.set(sqlVisitors);
	    sqlVisitors2TL.set(sqlVisitors2);
	    sqlMasterTL.set(sqlMaster);
	    conditionVTL.set(conditionV);
	    conditionMTL.set(conditionM);
		multiClueTL.set(multiClue);
		return list;
	}
	/**
	 * 获取用户的保有车辆
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerRetainVehicle> getCustomerRetainVehicle (String objectId){
		List<CustomerRetainVehicle> list = findByNamedQueryAndNamedParam("getCustomerRetainVehicle", new String[]{"customerId"}, new String[]{objectId});
		return list;
	}
	/**
	 * 获取某条线索所有回访信息
	 * @param visitorNo 线索no 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsBack> getPresellVisitorsBack (String visitorNo){
		List<PresellVisitorsBack> list = (List<PresellVisitorsBack>) getHibernateTemplate().find("FROM PresellVisitorsBack p WHERE p.visitorNo=?", visitorNo);
		return list;
	}
	/**
	 * 获取某条线索需要记录的回访信息
	 * @param visitorNo 线索no 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsBack> getRegisterPresellVisitorsBack (String visitorNo){
		List<PresellVisitorsBack> list = (List<PresellVisitorsBack>) getHibernateTemplate().find("FROM PresellVisitorsBack p WHERE p.visitorNo=? AND p.realBackTime IS NULL", visitorNo);
		return list;
	}
	/**
	 * 获得某客户有待回访的销售线索
	 * @param visitorNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsVO> getRegisterSaleClue (String visitorId,String userId){
		List<PresellVisitorsVO> list = findByNamedQueryAndNamedParam("getRegisterSaleClue", new String[]{"visitorId","userId"}, new String[]{visitorId,userId});
		return list;
	}
	
	/**
	 * 获取客户信息总记录数
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getSaleCustomerTotalCount(final SysUsers user,final String nameOrMobile){
		return (List<Integer>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				String sqlVisitors = sqlVisitorsTL.get()==null?"":sqlVisitorsTL.get();
				String multiClue = multiClueTL.get()==null?"":multiClueTL.get();
				String sqlVisitors2 = sqlVisitors2TL.get()==null?"":sqlVisitors2TL.get();
				String sqlMaster = sqlMasterTL.get()==null?"":sqlMasterTL.get();
				String conditionV = conditionVTL.get()==null?"":conditionVTL.get();
				String conditionM = conditionMTL.get()==null?"":conditionMTL.get();
				try {
				initPopedom(user);
				sqlMaster += "AND (object_id IN (SELECT visitor_id FROM presell_visitors WHERE (AND visit_result is null AND visitor_id IN ( SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+") t) ) "+
                                     " OR  (  datediff(d,create_time,GETDATE())=0 AND visitor_id IN (SELECT visitor_id FROM ("+sqlVisitors+sqlVisitors2+") t)))) AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE real_back_time is NULL AND DATEDIFF(DAY, plan_back_time, GETDATE()) >= 0))";
				sql = getQueryStringByName("getSaleCustomersTotalCount", new String[]{"SqlMaster","nameOrMobile"},new String[]{sqlMaster,"'%"+nameOrMobile+"%'"});
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				sqlVisitors2 = "";
				sqlMaster = "";
				conditionM = "";
				conditionV = "";
				sqlVisitorsTL.set(sqlVisitors);
			    sqlVisitors2TL.set(sqlVisitors2);
			    sqlMasterTL.set(sqlMaster);
			    conditionVTL.set(conditionV);
			    conditionMTL.set(conditionM);
				multiClueTL.set(multiClue);
				return session.createSQLQuery(sql).list();
			}
		});
	}
	/**
	 * 获取成交机会
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BaseVehicleVisitorsBack> getVisitorLevel (String stationId){
		List<BaseVehicleVisitorsBack> list = findByNamedQueryAndNamedParam("getVisitorLevel", new String[]{"stationId"}, new String[]{stationId});
		return list;
	}
	/**
	 * 获取回访方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getBackWay (){
		List<String> list = findByNamedQueryAndNamedParam("getBackWay", null,null);
		return list;
	}
	/**
	 * 获取回访目的
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPurpose (){
		List<String> list = findByNamedQueryAndNamedParam("getPurpose", null,null);
		return list;
	}
	/**
	 * 获取成败原因
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRreson (){
		List<String> list = findByNamedQueryAndNamedParam("getRreson", null,null);
		return list;
	}
	/**
	 * 获取成交结果
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFlags> getVisitResult(){
		List<SysFlags> list = findByNamedQueryAndNamedParam("getVisitResult", null,null);
		return list;
	}
	
	/**
	 * 获取客户价值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getObjectProperty(){
		List<String> list = findByNamedQueryAndNamedParam("getObjectProperty", null,null);
		return list;
	}
	/**
	 * 获取民族
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getNation(){
		List<String> list = findByNamedQueryAndNamedParam("getNation", null,null);
		return list;
	}
	/**
	 * 获取行业
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getProfession(){
		List<String> list = findByNamedQueryAndNamedParam("getProfession", null,null);
		return list;
	}
	/**
	 * 获取职位
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPosition(){
		List<String> list = findByNamedQueryAndNamedParam("getPosition", null,null);
		return list;
	}
	/**
	 * 获取区域
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getArea(){
		List<String> list = findByNamedQueryAndNamedParam("getArea", null,null);
		return list;
	}
	/**
	 *  客户性质
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFlags> getObjectNature(){
		List<SysFlags> list = findByNamedQueryAndNamedParam("getObjectNature", null,null);
		return list;
	}
	/**
	 * 获取证件类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCertificateType(){
		List<String> list = findByNamedQueryAndNamedParam("getCertificateType", null,null);
		return list;
	}
	/**
	 * 获取保有车辆_品牌
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVehicleTrademark(){
		List<String> list = findByNamedQueryAndNamedParam("getVehicleTrademark", null,null);
		return list;
	}
	/**
	 * 获取保有车辆_品系
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleType> getVehicleStrain(){
		List<VehicleType> list = findByNamedQueryAndNamedParam("getVehicleStrain", null,null);
		//List<BaseOthers> list = getb
		return list;
	}
	/**
	 * 获取购车用途
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPurchaseUse(){
		List<String> list = findByNamedQueryAndNamedParam("getPurchaseUse", null,null);
		return list;
	}
	/**
	 * 运输距离
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDistance(){
		List<String> list = findByNamedQueryAndNamedParam("getDistance", null,null);
		return list;
	}
	/**
	 * 上牌吨位
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTonnage(){
		List<String> list = findByNamedQueryAndNamedParam("getTonnage", null,null);
		return list;
	}
	/**
	 * 实际载重
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFactLoad(){
		List<String> list = findByNamedQueryAndNamedParam("getFactLoad", null,null);
		return list;
	}
	/**
	 * 接触地点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFlags> getVisitAddr(){
		List<SysFlags> list = findByNamedQueryAndNamedParam("getVisitAddr", null,null);
		return list;
	}
	/**
	 * 验证电话号码是否唯一
	 * @param mobile 电话号码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getObjectForMobile(String mobile){
		List<String> list = (List<String>) getHibernateTemplate().find("SELECT u.objectId from InterestedCustomers u where u.mobile=?",mobile);
		return list;
	}
	/**
	 * 根据Id获得用户信息
	 * @param userId 用户Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> getObjectById(String userId){
		List<CustomerVO> list = (List<CustomerVO>) getHibernateTemplate().find("SELECT * from InterestedCustomers u where u.objectId=?",userId);
		return list;
	}
	/**
	 * 添加客户信息
	 * @param object 客户信息
	 */
	public void saveCustomer(CustomerVO object){
		getHibernateTemplate().save(object);
	}
	/**
	 * 添加保有车辆信息  
	 * @param vehicle 保有车辆
	 */
	public void saveCustomerRetainVehicle(CustomerRetainVehicle vehicle){
		getHibernateTemplate().save(vehicle);
	}
	/**
	 * 删除保有车辆
	 * @param vehicle
	 */
	public void delCustomerRetainVehicle(CustomerRetainVehicle vehicle){
		getHibernateTemplate().delete(vehicle);
	}
	/**
	 * 添加客户线索信息
	 * @param presellVisitors 线索信息
	 */
	public void savePresellVisitors(PresellVisitorsVO presellVisitors){
		getHibernateTemplate().save(presellVisitors);
	}
	/**
	 * 添加线索回访计划信息
	 * @param presellVisitorsBack 回访信息
	 */
	public void savePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack){
		getHibernateTemplate().save(presellVisitorsBack);
	}
	/**
	 * 添加战败客户信息
	 * @param presellVisitorsFail 战败客户信息
	 */
	public void savePresellVisitorsFail(PresellVisitorsFail presellVisitorsFail){
		getHibernateTemplate().save(presellVisitorsFail);
	}
	/**
	 * 根据站点获取车辆型号
	 * @param stationId 
	 * @param vehicleName 车辆名称
	 * @param vehicleVno  车辆型号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleType> getVehicleType (final String stationId,final String vehicleVno,final String vehicleName){
		return (List<VehicleType>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				String hql ="";
				try {
				if(vehicleName != null){
					hql = " AND vehicle_name LIKE '%"+vehicleName+"%' ";
				}
				if(vehicleVno != null){
					hql = " AND vehicle_vno LIKE '%"+vehicleVno+"%'";
				}
				
				sql = getQueryStringByName("getVehicleType", new String[]{"hql"},new String[]{hql});
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql).addEntity(VehicleType.class).list();
			}
		});
	/*	List<VehicleType> list = findByNamedQueryAndNamedParam("getVehicleType", new String[]{"vehicleVno","vehicleName"}, new String[]{"%"+"%","%"+vehicleName+"%"});
		return list;*/
	}
	/**
	 * 意向颜色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVehicleColor(String stationId){
		List<String> list = findByNamedQueryAndNamedParam("getVehicleColor", new String[]{"stationId"},new String[]{"%"+stationId+"%"});
		return list;
	}
	/**
	 * 意向车名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleType> getVehicleName(){
		List<VehicleType> list = findByNamedQueryAndNamedParam("getVehicleName",null,null);
		return list;
	}
	/**
	 * 关注重点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAttentionEmphases(){
		List<String> list = findByNamedQueryAndNamedParam("getAttentionEmphases", null,null);
		return list;
	}
	/**
	 * 购车方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFlags> getBuyType(){
		List<SysFlags> list = findByNamedQueryAndNamedParam("getBuyType", null,null);
		return list;
	}
	/**
	 *来访方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVisitorMode(){
		List<String> list = findByNamedQueryAndNamedParam("getVisitorMode", null,null);
		return list;
	}
	/**
	 * 拜访方式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVisitMode(){
		List<String> list = findByNamedQueryAndNamedParam("getVisitMode", null,null);
		return list;
	}
	/**
	 * 了解渠道
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getKnowWay(){
		List<String> list = findByNamedQueryAndNamedParam("getKnowWay", null,null);
		return list;
	}
	/**
	 * 销售网点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDeliveryLocus(){
		List<String> list = findByNamedQueryAndNamedParam("getDeliveryLocus", null,null);
		return list;
	}
	/**
	 * 验证前用户所在的部门是否还有未跟进完成的线索，有则不允许再次建立线索
	 * @param userId
	 * @param visitorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsVO> getUnfinishedClueForDepartment(String userId,String visitorId){
		List<PresellVisitorsVO> list = findByNamedQueryAndNamedParam("getUnfinishedClueForDepartment",new String[]{"userId","visitorId"},new String[]{userId,visitorId});
		return list;
	}
	/**
	 *  查询最后一次销售线索 新增线索时需要上一次的回访结果 
	 * @param visitorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LastPresellVisitors> getLastVisitResult(String visitorId){
		List<LastPresellVisitors> list =  findByNamedQueryAndNamedParam("getLastVisitResult",new String[]{"visitorId"},new String[]{visitorId});
		return list;
	}
	/**
	 *  验证有没有未回访的有效线索 
	 * @param visitorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getValidClueForNoBack(String visitorId){
		List<String> list = findByNamedQueryAndNamedParam("getValidClueForNoBack",new String[]{"visitorId"},new String[]{visitorId});
		return list;
	}
	/**
	 *  验证是否有未完成的回访计划 
	 * @param visitorId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getClueForNoBack(String visitorId){
		List<String> list = findByNamedQueryAndNamedParam("getClueForNoBack",new String[]{"visitorId"},new String[]{visitorId});
		return list;
	}
	/**
	 * 查询客户名称，
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> checkObjectNameIsUpdate(String objectId){	
		List<CustomerVO> list = (List<CustomerVO>) getHibernateTemplate().find("SELECT new InterestedCustomers(o.objectName, o.shortName) from InterestedCustomers o where o.objectId = ?",objectId);
		return list;
	}
	/**
	 *  修改客户信息
	 * @param object
	 */
	public void updateObject(CustomerVO object){
		getHibernateTemplate().update(object);
	}
	/**
	 * 验证客户是否存在有效线索
	 * @param visitorId 用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsVO> checkValidClue(String visitorId){	
		List<PresellVisitorsVO> list = (List<PresellVisitorsVO>) getHibernateTemplate().find("SELECT new PresellVisitors(p.visitorNo) from PresellVisitors p where p.visitorId = ?  AND p.clueType =20",visitorId);
		return list;
	}
	/**
	 * 验证线索是否可以修改
	 * @param visitorNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PresellVisitorsForCheck> getClueForUpdate(final String visitorNo){
		//List<PresellVisitors> list = getHibernateTemplate().find("SELECT p.visitResult,p.sellerId,p.clueType from PresellVisitors p where p.visitorNo = ?",visitorNo);
		//List<PresellVisitors> list = findByNamedQueryAndNamedParam("getClueForUpdate",new String[]{"visitorNo"},new String[]{visitorNo});
		return (List<PresellVisitorsForCheck>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
				sql = getQueryStringByName("getClueForUpdate", new String[]{"visitorNo"},new String[]{"'"+visitorNo+"'"});
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql).addEntity(PresellVisitorsForCheck.class).list();
			}
		});
	}
	/**
	 * 验证计划是否有被回访
	 * @param backId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getClueBackRealTime(String backId){
		List<String> list = (List<String>) getHibernateTemplate().find("SELECT p.realBackTime from PresellVisitorsBack p where p.backId = ?",backId);
		return list;
	}
	/**
	 * 验证计划是否有被检查
	 * @param backId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCheckBackContext(String backId){
		List<String> list = (List<String>) getHibernateTemplate().find("SELECT p.checkContent from PresellVisitorsBack p where p.backId = ?",backId);
		return list;
	}
	
	/**
	 * 查询保有车辆
	 * @param selfId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerRetainVehicle> getCustomerRetainVehicleById(String selfId){	
		List<CustomerRetainVehicle> list = findByNamedQueryAndNamedParam("getCustomerRetainVehicleById",new String[]{"selfId"},new String[]{selfId});
		//List<CustomerRetainVehicle> list = getHibernateTemplate().find("SELECT TOP(1)* from CustomerRetainVehicle p where p.selfId = ? ",selfId);
		return list;
	}
	/**
	 * 检查客户是否有过消费 
	 * @param objectId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFinanceDocumentEntries(String objectId){
		List<String> list = (List<String>) getHibernateTemplate().find("SELECT f.entryId from FinanceDocumentEntries f where f.objectId = ? ",objectId);
		return list;
	}
	/**
	 * 修改保有车辆信息
	 * @param vehicle
	 */
	public void updateCustomerRetainVehicle(CustomerRetainVehicle vehicle){
		getHibernateTemplate().update(vehicle);
	}
	/**
	 * 修改销售线索信息
	 * @param presellVisitors
	 */
	public void updatePresellVisitors(PresellVisitorsVO presellVisitors){
		getHibernateTemplate().update(presellVisitors);
	}
	/**
	 * 修改回访信息,也就是对线索做回访
	 * @param presellVisitors
	 */
	public void updatePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack){
		getHibernateTemplate().update(presellVisitorsBack);
	}


//	/**
//	 *  获取 用户编码的当前编码
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<String> getAutoNoOfObject(){
//		List<String> list = findByNamedQueryAndNamedParam("getAutoNoOfObject",null,null);
//		return list;
//	}
	/**
	 *  验证客户名称合法性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OjbectNameForCheck> getObjectNameIsRight(String objectName,String objectId,String certificateNo,String mobile,String shortName,Short objectNature){
		
		List<OjbectNameForCheck> list ;
		
		if(objectNature == 20){
			//个人
			list = findByNamedQueryAndNamedParam("getObjectNameIsRightOfPersonal",new String[]{"objectName","mobile","shortName","certificateNo","objectId"},new String[]{objectName,mobile,shortName,certificateNo,objectId});
		}else{
			//单位
			list = findByNamedQueryAndNamedParam("getObjectNameIsRightOfUnit",new String[]{"objectName","shortName","certificateNo","objectId"},new String[]{objectName,shortName,certificateNo,objectId});
		}
		
		return list;
	}
	/**
	 *  查询用户是否存在维系小组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserMaintenanceWorkgroups> getUserMaintenanceWorkgroups(String userId,String stationId){
		List<UserMaintenanceWorkgroups> list = findByNamedQueryAndNamedParam("getUserMaintenanceWorkgroups",new String[]{"userId","stationId"},new String[]{userId,stationId});
		return list;
	}
	/**
	 * 添加客户维系组
	 * @param object 客户信息
	 */
	public void saveCustomerMaintenanceWorkgroup(CustomerMaintenanceWorkgroup workgroup){
		getHibernateTemplate().save(workgroup);
	}
	/**
	 *  获得省市区的基础数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ObjectOfPlace> getObjectOfPlace(){
		List<ObjectOfPlace> list = findByNamedQueryAndNamedParam("getObjectOfPlace",null,null);
		return list;
	}
	/**
	 *  获得维系小组的基础数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BaseMaintenanceWorkgroups> getMaintenanceWorkgroups(String stationId){
		List<BaseMaintenanceWorkgroups> list = findByNamedQueryAndNamedParam("getMaintenanceWorkgroups",new String[]{"stationId"},new String[]{"%"+stationId+"%"});
		return list;
	}
}
