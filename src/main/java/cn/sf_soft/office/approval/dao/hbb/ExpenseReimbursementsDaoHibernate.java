package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.ExpenseReimbursementsDao;
import cn.sf_soft.office.approval.model.ExpenseReimbursementAdditional;
import cn.sf_soft.office.approval.model.ExpenseReimbursements;
import cn.sf_soft.office.approval.model.ExpenseReimbursementsApportionments;
/**
 * 费用报销的hibernate操作
 * @author minggo
 * @created 2012-12-16
 */
@Repository("expenseReimbursementsDao")
public class ExpenseReimbursementsDaoHibernate extends BaseDaoHibernateImpl implements ExpenseReimbursementsDao{
	/**
	 * 得到费用报销单据的详情以及单据对应的费用明细和分摊部门，并根据费用类型和分摊部门来加载预算和已报金额<br>
	 * by king 2013-03-14
	 */
	public ExpenseReimbursements getDocumentDetail(String documentNo, boolean showAdditional) {
		ExpenseReimbursements expense =  (ExpenseReimbursements) getHibernateTemplate().get(ExpenseReimbursements.class, documentNo);
		//客户端老版本数据问题
		expense.setAdditionalInfo(new ArrayList<ExpenseReimbursementAdditional>());
		return expense;
	}
	/**
	 * The more fine to submit an expense account details
	 */
	public boolean updateExpenseReimbursements(final ExpenseReimbursements expenseReimbursements) {
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					    session.saveOrUpdate(expenseReimbursements);
		           return true;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public ExpenseReimbursementsApportionments getApportionments(
			String documentNo) {
		List<ExpenseReimbursementsApportionments> result = (List<ExpenseReimbursementsApportionments>) getHibernateTemplate()
		.find("from ExpenseReimbursementsApportionments e left join fetch e.chargeDetail where e.documentNo=?",documentNo);
		if(result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

}
