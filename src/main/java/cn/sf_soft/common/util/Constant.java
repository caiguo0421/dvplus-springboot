package cn.sf_soft.common.util;

import javax.persistence.Id;

/**
 * defined all constant
 * 
 * @author king
 */
public class Constant {

	/**
	 * @author king
	 * @create 2012-12-8下午05:21:12
	 */
	public final class Attribute {
		public static final String SESSION_TERMINAL_INFO = "terminal_info";
		public static final String SESSION_USER = "user";
		public static final String TOKEN ="token";
		/*public static final String JPUSH_ID = "jpushId";*/
		public static final String OS_TYPE ="os_type";
	}

	/**
	 * Contains some Constants will be used in soap protocol
	 * 
	 * @author king
	 */
	public final class Soap {
		public static final String NAMESPACE = "http://interfaces.service.sf_soft.cn"; // the
																						// WebService
																						// NameSpace
		public static final String SOAP_AUTH_HEADE = "SOAP_AUTH"; // the name of
																	// SOAP
																	// header,and
																	// the
																	// WebService's
																	// Client
																	// defined
																	// should be
																	// same with
																	// this
		public static final String ACCESS_TOKEN = "AccessToken"; // the name of
																	// HTTP
																	// header,and
																	// the
																	// WebService's
																	// Client
																	// defined
																	// should be
																	// same with
																	// this
		public static final String TERMINAL_INFO = "TerminalInfo";
	}

	/**
	 * the status of document
	 * 
	 * @author king
	 */
	public final class DocumentStatus {
		public static final short DISAGREE = 5; // 不同意
		public static final short IN_MAKING = 10;// 制单中
		public static final short SUBMITED = 20;// 已提交
		public static final short APPROVEING = 30;// 审批中
		public static final short APPROVED = 40;// 已审批
		public static final short AGREED = 50;// 已同意
		public static final short CONFIREMED = 60;// 已确认
		public static final short REVOKED = 70;// 已撤销
		public static final short OBSOLETED = 80;// 已作废
		public static final short TERMINATED = 85;// 已终止
		public static final short HAS_RED = 90;// 已冲红
	}

	public enum ApproveStatus {
		/**
		 * 审批中
		 */
		APPROVING,
		/**
		 * 最后一级审批，即即将变成已同意状态
		 */
		LAST_APPROVE;
	}

	/**
	 * 请款类型
	 * 
	 * @author minggo
	 */
	public final class LoanRequestType {
		public static final short LOAN_TYPE_PREPARED_PAYMENT = 10;// 备用金
		public static final short LOAN_TYPE_OTHERS = 99;// 其他
	}

	/**
	 * 金额类型
	 * 
	 * @author king
	 * 
	 */
	public final class AmountType {

		public static final short ADVANCE_RECEIVED = 10;// 预收款
		public static final short ACCOUNT_RECEIVABLE = 20;// 应收款
		public static final short SALES_DEPOSIT = 25;// 销售订金
		public static final short OTHER_RECEIVABLE = 30;// 其它应收
		public static final short MARGIN_RECEIVABLE = 35;// 应收保证金
		public static final short RECEIVABLE_BYJ = 38;// 应收备用金
		public static final short OTHER_REVENUE = 40;// 其它收入
		public static final short ADVANCE_PAYMENT = 60;// 预付款
		public static final short PURCHASING_ADVANCE_PAYMENT = 65;// 采购预付款
		public static final short ACCOUNT_PAYABLE = 70;// 应付款
		public static final short PURCHASING_DEPOSIT = 75;// 采购订金
		public static final short MARGIN_REQUIREMENT = 85;// 应付保证金
		public static final short NEED_PAY_PAYMENT = 88;// 应付备用金
		public static final short NEED_PAY_OTHERS = 80;// 其他应付
		public static final short OTHERS_PAYMENT = 90;// 其他支出
	}

	/**
	 * 费用分录表中需要的常量 数值需要按位计算（2多少次方）如果是6 就表示2+4可以结算和需要发票
	 * 
	 * @author minggo
	 * 
	 */
	public final class DocumentEntries {
		// <费用分录表中的分录性质>
		public static final int ENTRIES_PROPERTY_INCLUDED_IN_CURRENT = 1;// 计入往来
		public static final int ENTRIES_PROPERTY_SETTLE_ACCOUNTS = 2;// 可以结算
		public static final int ENTRIES_PROPERTY_NEED_INVOICE = 4;// 需要发票
		public static final int ENTRIES_PROPERTY_COMPLETED_INVOICE = 8;// 完成发票
		public static final int ENTRIES_PROPERTY_CAN_CHARGE_AGAINST = 16;// 可以冲抵
		// public static final int ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT =
		// 14;// 2+4+8
		// modify bu shichunshan 2015/11/26
		public static final int ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT = 31;// 2+4+8
		public static final int ENTRIES_PROPERTY_INSTALMENT = 19;
		// ....

		// <费用分录表中的分录类型>
		public static final short ENTRIES_TYPE_NEED_INCOME = 10;// 应收
		public static final short ENTRIES_TYPE_NEED_SILVER = 15;// 应银
		public static final short ENTRIES_TYPE_ACTUALLY_PAID = 20;// 实付
		public static final short ENTRIES_TYPE_SELL_PAY = 25;// 销付
		public static final short ENTRIES_TYPE_TRANSFER_OUT = 30;// 转出
		public static final short ENTRIES_TYPE_NEED_PAY = 60;// 应付
		public static final short ENTRIES_TYPE_NEED_APPLAY = 65;// 应请
		public static final short ENTRIES_TYPE_ACTUALLY_INCOME = 70;// 实收
		public static final short ENTRIES_TYPE_SELL_INCOME = 75;// 销收
		public static final short ENTRIES_TYPE_TRANSFER_INCOME = 80;// 转入
		// ...

		// <费用分录表费用单据类型>
		// public static final String DOCUMENT_TYPE_FINANCE_LOAN_REQUESTS =
		// "借款-员工借款";
		// public static final String DOCUMENT_TYPE_REIMBUSEMENTS = "费用-一般报销";
		// public static final String DOCUMENT_TYPE_EXPENSE_VEHICLE_REIMBURSE =
		// "费用-车辆报销";
		// public static final String DOCUMENT_TYPE_EXPENSE_SERVICE_REIMBURSE =
		// "费用-维修报销";
		// public static final String DOCUMENT_TYPE_EXPENSE_ASSETS_REIMBURSE =
		// "费用-资产报销";
		// public static final String DOCUMENT_TYPE_ASSETS_PURCHASE_PLAN =
		// "资产-采购计划";
		// public static final String DOCUMENT_TYPE_ASSETS_PURCHASE_DEPOSIT =
		// "资产-采购订金";
		public static final String DOCUMENT_TYPE_SUPPLIES_PURCHASE_PLAN = "用品-采购计划";
		// public static final String DOCUMENT_TYPE_PAYMENT_REQUEST = "请款-业务请款";
		// public static final String DOCUMENT_TYPE_VEHICLE_SALE_ACTIVITY =
		// "车辆-营销活动";
		// public static final String DOCUMENT_TYPE_INTERNAL_AUDITS = "审计-内部审计";
		// public static final String DOCUMENT_TYPE_VEHICLE_MOVE_REIMBURSE =
		// "费用-车辆移调报销";
		// public static final String
		// DOCUMENT_TYPE_ACCOUNT_RECEIVABLE_INSTALMENT = "分期-应收账款";
	}

	/**
	 * 系统标志
	 * 
	 * @author king
	 * @create 2012-12-13上午09:52:41
	 */
	public final class SysFlags {
		public static final short PAY_TYPE_ADVANCE_PAYMENT = 0; // 预付款
		public static final short PAY_TYPE_ACCOUNT_PAYABLE = 1; // 应付款

		public static final short APPROVE_MODE_ASSIGN = 20;// 审批方式为指定审批人
		public static final short APPROVE_MODE_PRINCIPAL = 10;// 审批方式为部门负责人
		public static final short APPROVE_AGENT_CONFIRMED = 60;// 审批代理已确认

		public static final short TASK_STATUS_IN_MAKING = 10;// 制单中
		public static final short TASK_STATUS_TO_WORK = 20;// 待派工
		public static final short TASK_STATUS_REPAIRING = 30;// 在修中
		public static final short TASK_STATUS_COMPLETED = 40;// 已完工
		public static final short TASK_STATUS_FOR_ACCOUNT = 50;// 待结算
		public static final short TASK_STATUS_FINSHED_ACCOUNT = 60;// 已结算
		public static final short TASK_STATUS_EXPENSE_ACCOUNT = 250;// 报销单
		public static final short TASK_STATUS_OBSOLETED = 255;// 已作废

	}

	public enum ResultCode {
		/**
		 * 成功
		 */
		RET_SUCC(0, "成功"),
		/**
		 * 参数错误
		 */
		RET_PARAM_ERR(1, "参数错误"),
		/**
		 * 鉴权失败
		 */
		RET_NO_POPEDOM(2, "鉴权失败"),
		/**
		 * 服务器内部错误
		 */
		RET_SERVER_ERR(3, "服务器内部错误"),
		/**
		 * 不可操作
		 */
		RET_OPERATE_UNAVAILABLE(4, "不可操作");

		private final int code;
		private final String message;

		public String getMessage() {
			return message;
		}

		public int getCode() {
			return code;
		}

		ResultCode(int code, String message) {
			this.code = code;
			this.message = message;
		}

	}

	/**
	 * 定义请求结果的状态码和提示信息
	 * 
	 * @author king
	 * @create 2013-1-19下午03:26:49
	 */
	public enum ApproveResultCode {
		// 审批
		APPROVE_SUCCESS(100, "审批成功"),
		APPROVE_ERROR_CONDITION_INVALID(101, "审批失败:审批点条件数据无效"),
		APPROVE_ERROR_DOCUMENT_NOT_EXIST(102, "审批失败:单据不存在!"),
		APPROVE_ERROR_DOCUMENT_STATUS_INVALID(103, "审批失败:单据处于不可审批状态"),
		APPROVE_ERROR_NO_PRIVILEGE(104, "审批失败:您没有权限审批该单据"),
		APPROVE_ERROR_CHARGE_WAS_REIMBURSED(105, "审批失败:该费用已报销过"),
		APPROVE_ERROR_VEHICLE_CONTRACT_INVLID(106, "审批失败:该费用所对应的销售合同中的车辆未经过审批"),
		APPROVE_ERROR_SYSTEM_NOT_SUPPORT_MODULE(107, "审批失败:系统暂不支持该模块的审批"),
		APPROVE_ERROR_CONDITION_TYPE_NOT_SUPPORT(108, "审批失败:服务端还未加入审批点的审批条件值类型的支持"),
		APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL(109, "审批失败:单据分录插入失败"),
		APPROVE_ERROR_MUDULE_NOT_SUPPORT_DATACHECK(110, "审批失败:服务端该模块审批不支持检查数据的合法性"),
		APPROVE_ERROR_REQUEST_IN_DOCUMENT_INVALID(118, "审批失败:对应的资金调入申请单已作废"),
		APPROVE_ERROR_REQUEST_IN_HAS_REQUEST_OUT(119, "审批失败：资金调入申请单已做调出申请"),
		APPROVE_ERROR_DOCUMENT_ENTRY_NOT_FOUND(126, "审批失败：找不到业务单据信息"),
		APPROVE_ERROR_WRITE_OFF_AMOUNT_INVALID(127, "审批失败：销账金额大于可销金额"),
		APPROVE_ERROR_DOCUMENT_ENTRY_UPDATE_FAIL(128, "审批失败：更改业务单据出错"),
		APPROVE_ERROR_ASSERT_REIMBURSE_DOC_NOT_FOUND(129, "审批失败：找不到资产维修单或资产维修单已作废"),
		APPROVE_DATA_CHECKED_PASS(130, "数据检查通过"),
		APPROVE_CHECKED_DATA_CHANGED(8131, "数据发生更改，请重新刷新"),
		APPROVE_DISGREE(200, "审批不同意"),
		SYSTEM_ERROR(902, "服务端出现错误"),
		APPROVE_ERROR_NO_POINTS(131,"审批失败:找不到审批单据审批点"),
		OTHER_KNOWN_ERROR(901, "其他已知错误");

		private final int code;
		private String message;

		public String getMessage() {
			return message;
		}

		public int getCode() {
			return code;
		}

		public void setMessage(String message){ this.message = message; }

		ApproveResultCode(int code, String message) {
			this.code = code;
			this.message = message;
		}

	}

	// 返回给PC客户端的类型
	public enum ReturnPCResult {
		// 审批
		SUCCESS(0, "成功"), FAILED(8, "失败"), ;
		// 返回码
		private final int code;
		// 返回消息
		private final String message;

		public String getMessage() {
			return message;
		}

		public int getCode() {
			return code;
		}

		ReturnPCResult(int code, String message) {
			this.code = code;
			this.message = message;
		}

	}

	// OS类型，分为PC端和手机端 caigx 20140420
	public enum OSType {
		MOBILE(0, "手机"), PC(10, "PC"), ;
		// 返回码
		private final int code;
		// 返回消息
		private final String message;

		public String getMessage() {
			return message;
		}

		public int getCode() {
			return code;
		}

		OSType(int code, String message) {
			this.code = code;
			this.message = message;
		}
	}

	/**
	 * 审批条件值的数据类型
	 * 
	 * @author king
	 * @create 2012-12-12下午04:05:04
	 */
	public final class ApproveConditionType {
		/**
		 * 十进制数
		 */
		public final static String DECIMAL = "Decimal";
		public final static String SELECTION = "Selection";
		public final static String STRING = "String";
		public final static String DATETIME = "DateTime";
	}
}
