package cn.sf_soft.common.annotation;
/**
 * 定义了各模块的权限ID
 * @author king
 * @create 2013-7-19下午3:48:27
 */
public class AccessPopedom {
	/**
	 * 报表模块相关权限
	 * @author king
	 * @create 2013-7-19下午3:48:10
	 */
	public final class ReportForm{
		/**
		 * 销量统计
		 */
		public static final String VEHICLE_SALE = "80101020";
		/**
		 * 销量日报
		 */
		public static final String VEHICLE_SALE_DAY = "80101010";
		/**
		 * 交付报表
		 */
		public static final String VEHICLE_DELIVERY = "80101030";
		/**
		 * 维修日报与时间段报表
		 */
		public static final String MAINTAIN_REPORT = "80102010";
		
		/**
		 * 维修日进场台次/产值曲线
		 */
		public static final String MAINTAIN_DAILY_LINE_REPORT = "80102020";
		/**
		 * 三包索赔类别分析
		 */
		public static final String MAINTAIN_SANBAO_CLAIM_REPORT = "80102030";
		
		/**
		 * 维修索赔日报
		 */
		public static final String MAINTAIN_CLAIM_DAILY_REPORT = "80102040";

		/**
		 * 产值走势
		 */
		public static final String MAINTAIN_TREND = "80102050";

		/**
		 * 配件日报
		 */
		public static final String FITTING_DAILY_REPORT = "80103010";
		/**
		 * 配件入库
		 */
		public static final String FITTING_IN_STOCK_REPORT = "80103020";
		
		/**
		 * 配件出库
		 */
		public static final String FITTING_OUT_STOCK_REPORT = "80103030";

		/**
		 * 收款统计(账户)
		 */
		public static final String CUSTOMER_VALUE = "80104010";

		/**
		 * 收款统计(账户) 重复？
		 */
		public static final String FINANCE_GATHERING = "80104010";
		/**
		 * 付款统计(账户)
		 */
		public static final String FINANCE_PAYMENT = "80104020";
		/**
		 * 收款统计(收款方式)
		 */
		public static final String FINANCE_GATHERING_PAYMODE = "80104030";
		/**
		 * 收款统计(付款方式)
		 */
		public static final String FINANCE_PAYMENT_PAYMODE = "80104040";

		/**
		 * 销售回访 重复?
		 */
		public static final String SALE_CALL_BACK = "80105010";

		/**
		 * 水平事业
		 */
		public static final String PERIPHERY_VALUE = "80105010";
		/**
		 * 维修回访
		 */
		public static final String SERVICE_CALL_BACK = "80105020";

		/**
		 * 首页报表功能
		 */
		public static final String DASHBOARD_REPORT = "80106010";

		/**
		 * 首页报表功能 维修台次
		 */
		public static final String DASHBOARD_REPORT_MAINTAIN_COUNT = "80106014";

		/**
		 * 首页报表功能 维修总额
		 */
		public static final String DASHBOARD_REPORT_MAINTAIN_MONEY = "80106015";

		/**
		 * 首页报表功能 整车订购
		 */
		public static final String DASHBOARD_REPORT_VEHICLE_ORDER_COUNT = "80106011";

		/**
		 * 首页报表功能 整车销售
		 */
		public static final String DASHBOARD_REPORT_VEHICLE_SALE_COUNT = "80106012";

		/**
		 * 首页报表功能 配件销售
		 */
		public static final String DASHBOARD_REPORT_PART_SALE_MONEY = "80106013";

		/**
		 * 购险统计(按供应商+保单类型)
		 */
		public static final String INSURANCE_PURCHASE_SUPPLIER_TYPE = "80106010";
		/**
		 * 购险统计(按险种类别+保单类型)
		 */
		public static final String INSURANCE_PURCHASE_CATEGORY_TYPE = "80106020";
		/**
		 * 办公统计
		 * 资产入库统计（入库类型）
		 */
		public static final String OFFICE_ASSET_IN_TPYE = "80107010";
		/**
		 * 办公统计
		 * 资产出库统计（出库类型）
		 */
		public static final String OFFICE_ASSET_OUT_TPYE = "80107020";
		/**
		 * 办公统计
		 * 用品入库统计（入库类型）
		 */
		public static final String OFFICE_THINGS_IN_TPYE = "80107010";
		/**
		 * 办公统计
		 * 用品出库统计（出库类型）
		 */
		public static final String OFFICE_THINGS_OUT_TPYE = "80107020";	
	}
	
	/**
	 * 库存浏览
	 * @author king
	 * @create 2013-8-22下午2:35:56
	 */
	public final class StockBrowse{
		/**
		 * 车辆库存浏览
		 */
		public static final String VEHICLE_STOCK_BROWSE = "80101040";
		
		/**
		 * 配件库存浏览
		 */
		public static final String PART_STOCK_BROWSE = "80103040";
	}
	
	 /**
	  * 
	  * @Title: 意向客户
	  * @date 2013-12-26 下午04:04:07 
	  * @author cw
	  */
	public final class SaleCustomer{
			/**
			 * 查看
			 */
			public static final String CUSTOMER_SELECT = "10201005";
			
			/**
			 *跟进
			 */
			public static final String CUSTOMER_FOLLOW = "10201010";
			/**
			 *调整
			 */
			public static final String CUSTOMER_RECTIFY = "10201025";
			/**
			 *计划时间调整
			 */
			public static final String CUSTOMER_PLAN_TIME = "10201027";
			/**
			 *部门检查
			 */
			public static final String CUSTOMER_CHECK = "10201210";
			/**
			 *跨部门检查
			 */
			public static final String CUSTOMER_CHECKDEP = "10201220";
			/**
			 * 客服回访
			 */
			public static final String CUSTOMER_SERVICE_BACK= "10201030";
			/**
			 * 短信
			 */
			public static final String MESSAGE = "10201040";
			/**
			 * 导出
			 */
			public static final String EXPORT = "10201050";

			 /**
			  * 查看我的客户
			  */
			public static final String MY_CUSTOMER =  "80104020";

			//取消改权限
//			 /**
//			  * 查看全部客户
//			  */
//			 public static final String ALL_CUSTOMER =  "80104021";
//
//			 /**
//			  *  部门查看权限
//			  */
//			 public static final String DEPARTMENT_CUSTOMER = "80104024";
			public static final String ALL_CUSTOMER =  "10201040";

			 /**
			  * 查看公海客户
			  */
			 public static final String PUBLIC_CUSTOMER =  "80104022";

			 /**
			  * 设置维系人
			  */
			 public static final String EDIT_MAINTENANCE =  "80104023";

	 }

	/**
	 * 配件库存盘存调整
	 * @author cw
	 * @date 2014-4-14 上午10:16:30
	 */
	public final class PartCheckStocksPopedoms{
		/**
		 * 制单
		 */
		public static final String NEW_PART_CHECK_STOCKS_DOCUMENT = "15858010";
		/**
		 * 盘盈审批
		 */
		public static final String PART_CHECK_STOCKS_APPROVE_PROFIT = "15858020";
		/**
		 * 盘亏审批
		 */
		public static final String PART_CHECK_STOCKS_APPROVE_DEFICIT = "15858030";
		/**
		 * 配件库存选配件
		 */
		public static final String PART_CHECK_STOCKS_SELECT_PART = "15858090";
	}
	/**
	 * 办公模块相关权限
	 * @author king
	 * @create 2013-9-25下午3:33:31
	 */
	public final class Offices{
		/**
		 * 审批
		 */
		public static final String APPROVAL_MOBILE = "80107010";

		/**
		 * 查看待审事宜
		 */
		public static final String APPROVAL = "35601000";
		
		/**
		 * 处理事后审核单据
		 */
		public static final String POST_AUDITS_HANDLE = "35655010";
		/**
		 * 审批事后审核单据
		 */
		public static final String POST_AUDIT_APPROVE = "35655020";
	}
	
	public final class FinanceDocument{
		/**
		 * 资金账户浏览
		 */
		public static final String FUNDS_ACCOUNTS = "80108010";

		/**
		 *  账款查询
		 */
		public static final String FUNDS_PAYMENT = "80108020";
		
	}
	
	
	/**
	 * 全局通用-车辆
	 * @author caigx
	 * @create 2016-12-21 
	 */
	public final class GeneralVehicle {
		/**
		 * 查看车辆成本
		 */
		public static final String CHECK_VEHICLE_COST = "00101000";
	}
}
