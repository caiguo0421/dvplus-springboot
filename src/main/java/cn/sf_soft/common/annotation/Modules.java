package cn.sf_soft.common.annotation;
/**
 * 系统功能模块
 * @创建人 LiuJin
 * @创建时间 2014-8-26 上午11:40:43
 * @修改人 
 * @修改时间
 */
public class Modules {
	
	/**
	 * 车辆业务功能模块
	 * @创建人 LiuJin
	 * @创建时间 2014-8-26 上午11:42:02
	 * @修改人 
	 * @修改时间
	 */
	public final class Vehicle{
		/**
		 * 车辆库存浏览
		 */
		public final static String STOCK_BROWSE = "107010"; 
	}
	
	/**
	 * 配件功能模块
	 * @创建人 LiuJin
	 * @创建时间 2014-8-26 上午11:43:54
	 * @修改人 
	 * @修改时间
	 */
	public final class Parts{
		public final static String STOCK_BROWSE = "158510";

		/**
		 * 配件盘存调整
		 */
		public final static String PART_STOCKING="158580";
	}
	
	public final class Finance{
		/**
		 * 资金查询
		 */
		public final static String FUNDS_QUERY = "403060";
	}
}
