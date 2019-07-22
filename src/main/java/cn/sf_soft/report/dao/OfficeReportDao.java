package cn.sf_soft.report.dao;

import java.util.List;

import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.report.model.OfficeAssetReport;
import cn.sf_soft.report.model.SuppliesPosition;

/**
 * 
 * @Title: 办公统计
 * @date 2013-11-19 下午04:15:37 
 * @author cw
 */
public interface OfficeReportDao {

	/**
	 * 初始化出入库类型
	 * @param Type
	 * @return
	 */
	public List<SysFlags> getTypeInitData(String Type);
	/**
	 * 初始化存放位置
	 * @return
	 */
	public List<BaseOthers> getPositionInitData();
	/**
	 * 初始化用品仓库数据
	 * @return
	 */
	public List<SuppliesPosition> getSuppliesPositionInitData(List<String> stationIds);
	/**
	 * 资产出库查询（出库类别）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param posType
	 * @param depositPosition
	 * @return
	 */
	public List<OfficeAssetReport> getAssetOutReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String posType,final String depositPosition);
	/**
	 * 资产入库查询（入库类别）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param pis_type
	 * @param depositPosition
	 * @return
	 */
	public List<OfficeAssetReport> getAssetInReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pis_type,final String depositPosition);
	/**
	 * 用品入库查询（入库类型）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param pis_type 入库类型
	 * @param positionCode 用品存放仓库
	 * @return
	 */
	public List<OfficeAssetReport> getThingsInReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pis_type,final String positionCode);
	/**
	 * 用品出库查询（出库类型）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param pos_type 出库类型
	 * @param positionCode 用品存放仓库
	 * @return
	 */
	public List<OfficeAssetReport> getThingsOutReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pos_type,final String positionCode);
}
