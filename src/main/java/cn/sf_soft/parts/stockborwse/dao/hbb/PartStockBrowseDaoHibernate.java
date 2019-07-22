package cn.sf_soft.parts.stockborwse.dao.hbb;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.parts.stockborwse.dao.PartStockBrowseDao;
import cn.sf_soft.parts.stockborwse.model.PartStockSearchCriteria;
import cn.sf_soft.parts.stockborwse.model.PartStockStatistical;
import cn.sf_soft.parts.stockborwse.model.VwPartStocks;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.DoubleType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆库存浏览
 * 
 * @创建人 LiuJin
 * @创建时间 2014-8-20 下午5:14:24
 * @修改人
 * @修改时间
 */
@Repository
public class PartStockBrowseDaoHibernate extends BaseDaoHibernateImpl
		implements PartStockBrowseDao {

	@Override
	@SuppressWarnings("unchecked")
	public PartStockStatistical getPartStockStatistical(
			PartStockSearchCriteria condition) {

		// 总数
		List<Double> totalcount = (List<Double>) getHibernateTemplate()
				.findByCriteria(
						createCriteria(condition).setProjection(
								Projections.sum("quantity")));

		String[]col = {"q"};
		Type[]t = {new DoubleType()};
		// 金额
		List<Double> totalcost = (List<Double>) getHibernateTemplate()
				.findByCriteria(
						createCriteria(condition).setProjection(
								Projections.sqlProjection("sum(cost*quantity) as q", col, t)));

		PartStockStatistical statistical = new PartStockStatistical();

		statistical.setTotalCost(totalcost.get(0) == null ? 0 : totalcost.get(0));

		statistical.setTotalCount(totalcount.get(0) == null ? 0 : totalcount.get(0).longValue());

		return statistical;
	}

	private DetachedCriteria createCriteria(PartStockSearchCriteria criterial) {
		DetachedCriteria dc = DetachedCriteria.forClass(VwPartStocks.class);
		if (criterial != null) {

			String partVno = criterial.getPartNo();
			String partName = criterial.getPartName();
			String partType = criterial.getPartType();
			String warehouseId = criterial.getWarehouseId();
			boolean flag = false;
			// short.class.
			if (partVno != null && partVno.length() > 0) {
				dc.add(Restrictions.eq("partNo", partVno));
				flag = true;
			}
			if (partName != null && partName.length() > 0) {
				dc.add(
						Restrictions.or(
								Restrictions.like("partName", "%"+partName+"%"),
								Restrictions.like("partNo", "%"+partName+"%")
						)
				);
				flag = true;
			}
			if (partType != null && partType.length() > 0) {
				dc.add(Restrictions.eq("partType", partName));
				flag = true;
			}
			// 仓库id
			if (warehouseId != null && warehouseId.length() > 0) {
				dc.add(Restrictions.in("warehouseId",
						getSearchCondtionFromStr(warehouseId)));
				flag = true;
			}
			dc.add(Restrictions.in("stationId", criterial.getStationIds()));
			// modify by shichunshan 2015/12/3
			// if (flag == false) {
			// 如果没有任何条件，则加载状态为库存中的数据
			// dc.add(Restrictions.lt("status", (short) 3));
			// }
		}

		return dc;
	}

	/**
	 * add bu shichunshan 从字符串中提取多选的查询条件
	 */
	private Object[] getSearchCondtionFromStr(String str) {
		return str.split(",");
	}
}
