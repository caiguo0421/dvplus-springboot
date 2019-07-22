package cn.sf_soft.report.dao.hbb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.FittingsReportDao;
import cn.sf_soft.report.model.FittingsOutStockReport;
import cn.sf_soft.report.model.FittingsPutInReport;

import javax.rmi.CORBA.Util;

/**
 * 
 * @Title: 配件统计报表
 * @date 2013-8-2 上午10:28:56
 * @author cw
 */
@Repository("fittingsReportDao")
public class FittingsReportDaoHibernate extends
		BaseDaoHibernateImpl implements FittingsReportDao {
	// 配件出库统计报表
	@SuppressWarnings({ "unchecked" })
	public List<FittingsOutStockReport> getFittingsOutStockReport(
			final String dateTime, final String endTime,
			final String analyseType, final List<String> stationIds) {
		return (List<FittingsOutStockReport>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String sql = getQueryStringByName(
								"fittingsOutStockReportData",
								new String[] { "analyseType" },
								new String[] { analyseType });
						return session
								.createSQLQuery(sql)
								.addEntity(FittingsOutStockReport.class)
								.setParameterList("stationIds", stationIds,
										new StringType())
								.setString("beginTime", dateTime)
								.setString("endTime", endTime).list();
					}
				});
	}

	// 配件入库统计报表
	@SuppressWarnings({ "unchecked" })
	public List<FittingsPutInReport> getFittingPutInReport(
			final String beginTime, final String endTime,
			final String analyseType, final List<String> stationIds) {
		return (List<FittingsPutInReport>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String sql = getQueryStringByName(
								"fittingsPutInReportData",
								new String[] { "analyseType" },
								new String[] { analyseType });

						return session
								.createSQLQuery(sql)
								.addEntity(FittingsPutInReport.class)
								.setParameterList("stationIds", stationIds,
										new StringType())
								.setString("beginTime", beginTime)
								.setString("endTime", endTime).list();
					}
				});
	}

	@Override
	public List<Map<String, Object>> getStockOutReport(List<String> stationIds, String unitName, String beginTime, String endTime) {
		return getMapBySQL(" SELECT  station_name,--站点\n" +
				"\t\tpos_type_meaning, --出库类型\n" +
				"\t\tCASE WHEN ISNULL(SUM(pos_quantity), 0) = 0 THEN 0\n" +
				"        ELSE SUM(pos_price * pos_quantity * pos_agio)\n" +
				"        END AS income, --收入\n" +
				"        SUM(cost * pos_quantity) AS cost, --成本\n" +
				"        CASE WHEN ISNULL(SUM(pos_quantity), 0) = 0 THEN 0\n" +
				"        ELSE SUM((pos_price * pos_agio - cost) * pos_quantity)\n" +
				"        END AS profit --利润\n" +
				"FROM    (SELECT station_id, station_name, pos_type, pos_type_meaning,\n" +
				"                pos_price, pos_quantity, pos_agio, cost, carriage, part_id,\n" +
				"                approve_time, balance_time, part_stat_type, customer_id,\n" +
				"                warehouse_id, full_id, part_no, part_name\n" +
				"         FROM   vw_stat_posdetail\n" +
				"\t\t WHERE ((pos_type=3 AND pos_type_meaning='维修出库')OR pos_type <>3)\n" +
				( (stationIds != null && stationIds.size()>0) ? ("\t\t\tAND station_id IN ('" + StringUtils.join(stationIds, "','") + "') --‘站点’查找条件\n") : " ") +
				( (unitName != null && unitName.length() > 0) ? ("\t\t\tAND creator_unit_name='" + unitName + "' --‘部门’查找条件\n" ) : "") +
				( (beginTime != null && beginTime.length() > 0) ? ("\t\t\tAND approve_time >= '" +  beginTime + "'\n") : "" ) +
				( (endTime != null && endTime.length() > 0) ? ("\t\t\tAND approve_time <= '" +  endTime + "' --‘审批日期’查找条件\n") : "" ) +
				"        ) AS a\n" +
				"WHERE   1 = 1\n" +
				"GROUP BY station_name,pos_type_meaning\n" +
				"ORDER BY station_name,pos_type_meaning;", null);
	}

	@Override
	public List<Map<String, Object>> getStockInReport(List<String> stationIds, String unitName, String beginTime, String endTime) {
		return getMapBySQL("SELECT  station_name, --站点\n" +
				"        pis_type_meaning,--入库类型\n" +
				"        SUM(pis_price * pis_quantity) AS income, --入库金额\n" +
				"        COUNT(DISTINCT a.pis_no) AS frequency, --频次\n" +
				"        COUNT(DISTINCT part_id) AS description--种类\t\t\n" +
				"FROM    vw_stat_pisdetail AS a\n" +
				"WHERE   pis_type IS NOT NULL\n" +
				"        AND a.station_id IS NOT NULL\n" +
				( (stationIds != null && stationIds.size()>0) ? ("\t\t\tAND station_id IN ('" + StringUtils.join(stationIds, "','") + "') --‘站点’查找条件\n") : " ") +
				( (unitName != null && unitName.length() > 0) ? ("\t\t\tAND creator_unit_name='" + unitName + "' --‘部门’查找条件\n" ) : "") +
				( (beginTime != null && beginTime.length() > 0) ? ("\t\t\tAND approve_time >= '" +  beginTime + "'\n") : "" ) +
				( (endTime != null && endTime.length() > 0) ? ("\t\t\tAND approve_time <= '" +  endTime + "' --‘审批日期’查找条件\n") : "" ) +
				"GROUP BY station_id, station_name, pis_type_meaning\n" +
				"ORDER BY station_id, station_name, pis_type_meaning;", null);
	}

	@Override
	public List<Map<String, Object>> getFixedReport(List<String> stationIds, String unitName, String beginTime, String endTime, String dateType) {
		String sql = "SELECT  station_name, --站点\n" +
				"\t\treceiver, --接车员\n" +
				"\t\tSUM(m) AS total_income,--产值\n" +
				"       COUNT(DISTINCT (vehicle_vin + '_' + CONVERT(VARCHAR(100),isnull( balance_time,enter_time), 23))) \n" +
				"        AS enter_count --台次\n" +
				"FROM    vw_stat_taskdetail_income\n" +
				"WHERE   1 = 1\n" +
				( (stationIds != null && stationIds.size()>0) ? ("\t\t\tAND station_id IN ('" + StringUtils.join(stationIds, "','") + "') --‘站点’查找条件\n") : " ") +
				( (unitName != null && unitName.length() > 0) ? ("\t\t\tAND creator_unit_name='" + unitName + "' --‘部门’查找条件\n" ) : "");
		if(dateType.equals("check")) {
			sql += ((beginTime != null && beginTime.length() > 0) ? ("\t\t\tAND balance_time >= '" + beginTime + "'\n") : "") +
					((endTime != null && endTime.length() > 0) ? ("\t\t\tAND balance_time <= '" + endTime + "' --‘结算时间’查找条件\n") : "");
		}else{
			sql += ((beginTime != null && beginTime.length() > 0) ? ("\t\t\tAND enter_time >= '" + beginTime + "'\n") : "") +
					((endTime != null && endTime.length() > 0) ? ("\t\t\tAND enter_time <= '" + endTime + "' --‘进厂时间’查找条件\n") : "");
		}
		sql +=	"GROUP BY station_id, station_name, receiver;";

		return getMapBySQL(sql, null);
	}

	@Override
	public List<Map<String, Object>> getProductionValueTrade(List<String> stationIds, String unitName, String beginTime, String endTime, String dateUnit) {
		String dateDisplay;
		if(dateUnit.equals("date")){
			dateDisplay = " CONVERT(NVARCHAR(12),balance_time,23) ";
		}else{
			dateDisplay = " CONVERT(NVARCHAR(7),balance_time,121) ";
		}
		return getMapBySQL("SELECT " + dateDisplay + " balance_time,--日期\n" +
				"\t\tSUM(m) AS total_income,--产值\n" +
				"        COUNT(DISTINCT (vehicle_vin + '_' + CONVERT(VARCHAR(100), balance_time, 23)))\n" +
				"        AS enter_count --台次\n" +
				"FROM    vw_stat_taskdetail_income\n" +
				"WHERE   1 = 1 AND balance_time IS NOT NULL \n" +
				( (stationIds != null && stationIds.size()>0) ? ("\t\t\tAND station_id IN ('" + StringUtils.join(stationIds, "','") + "') --‘站点’查找条件\n") : " ") +
				( (unitName != null && unitName.length() > 0) ? ("\t\t\tAND creator_unit_name='" + unitName + "' --‘部门’查找条件\n" ) : "") +
				( (beginTime != null && beginTime.length() > 0) ? ("\t\t\tAND balance_time >= '" +  beginTime + "'\n") : "" ) +
				( (endTime != null && endTime.length() > 0) ? ("\t\t\tAND balance_time <= '" +  endTime + "' --‘审批日期’查找条件\n") : "" ) +
				"GROUP BY " + dateDisplay + " ORDER BY " + dateDisplay + " DESC;" ,null);
	}
}
