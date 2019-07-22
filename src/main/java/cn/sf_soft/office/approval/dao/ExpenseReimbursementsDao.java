package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.ExpenseReimbursements;
import cn.sf_soft.office.approval.model.ExpenseReimbursementsApportionments;
/**
 * 费用报销（公司日常费用  如：手机通信费）
 * @author minggo
 *
 */
public interface ExpenseReimbursementsDao{
	/**
	 * According to documents, inquires the expense detailed list and specification
	 * @param documentNo
	 * @param showAdditional TODO
	 * @return 
	 */
	public ExpenseReimbursements getDocumentDetail(String documentNo, boolean showAdditional);
	/**
	 * According to the expense detailed list of information
	 * @param expenseReimbursements
	 * @return
	 */
	public boolean updateExpenseReimbursements(ExpenseReimbursements expenseReimbursements);
	/**
	 * According to the expense single number query to cost detail
	 * @param documentNo
	 * @return
	 */
	public ExpenseReimbursementsApportionments getApportionments(String documentNo);
}
