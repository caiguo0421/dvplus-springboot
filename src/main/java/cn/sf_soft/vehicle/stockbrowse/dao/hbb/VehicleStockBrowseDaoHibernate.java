package cn.sf_soft.vehicle.stockbrowse.dao.hbb;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import cn.sf_soft.vehicle.stockbrowse.dao.VehicleStockBrowseDao;
import cn.sf_soft.vehicle.stockbrowse.model.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆库存浏览
 *
 * @创建人 LiuJin
 * @创建时间 2014-8-20 下午5:14:24
 * @修改人
 * @修改时间
 */
@Repository
public class VehicleStockBrowseDaoHibernate extends BaseDaoHibernateImpl
        implements VehicleStockBrowseDao {

    final static short STATUS_OUTED = 3;// 已出库
    final static short STATUS_SELLED = 2;// 已销售
    final static short STATUS_ORDERED = 1;// 已订购
    final static short STATUS_IN_STOCK = 0;// 库存中

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    //车辆库存的moduleId
    private static final String MODULE_ID_STOCK_BROWSE = "107010";

    /**
     * 获取基础数据
     */
    @SuppressWarnings("unchecked")
    @Override
    public VehicleStockInitBaseData getInitBaseData() {
        VehicleStockInitBaseData baseData = new VehicleStockInitBaseData();
        //车辆库存浏览的站点id取MODULE_ID_STOCK_BROWSE的站点范围
        SysUsers user = HttpSessionStore.getSessionUser();
        String[] stationIds = userService.getModuleStationId(user, MODULE_ID_STOCK_BROWSE);

        // 获取仓库信息
        //去掉warehouse_id为空的记录
        Query query = baseDao.getCurrentSession()
                .createSQLQuery(
                        "SELECT DISTINCT warehouse_id, warehouse_name FROM vehicle_stocks WHERE ISNULL(warehouse_id,'')<>'' AND ISNULL(warehouse_name,'')<>'' AND station_id IN :stationIds ORDER BY warehouse_name")
                .setParameterList("stationIds", stationIds);
        List<Object[]> warehouseList = query.list();
        if (warehouseList != null && warehouseList.size() > 0) {
            Map<String, String> map = new HashMap(warehouseList.size());
            for (Object[] obj : warehouseList) {
                map.put((String) obj[1], (String) obj[0]);
            }
            baseData.setWarehouses(map);
        }
        // 获取车辆名称
        query = baseDao.getCurrentSession()
                .createSQLQuery("SELECT DISTINCT vehicle_name FROM vehicle_stocks WHERE vehicle_name IS NOT NULL ORDER BY vehicle_name");
        List<String> vehicleNames = query.list();
        baseData.setVehicleNames(vehicleNames
                .toArray(new String[vehicleNames.size()]));
        // 获取车辆型号
        query = baseDao.getCurrentSession()
                .createSQLQuery("SELECT DISTINCT vehicle_vno FROM  vehicle_stocks WHERE vehicle_vno IS NOT NULL AND vehicle_vno <> '' ORDER BY vehicle_vno");
        List<String> vehicleVnos = query.list();
        baseData.setVehicleVnos(vehicleVnos
                .toArray(new String[vehicleVnos.size()]));
        // 获取车辆颜色
        query = baseDao.getCurrentSession()
                .createSQLQuery("SELECT DISTINCT vehicle_color FROM  vehicle_stocks ORDER BY vehicle_color");
        List<String> vehicleColors = query.list();
        baseData.setVehicleColors(vehicleColors
                .toArray(new String[vehicleColors.size()]));
        // 获取车辆VIN吗前缀
        query = baseDao.getCurrentSession()
                .createQuery("SELECT b.data from BaseOthers b WHERE b.typeNo='VIN_PREFIX'");
        List<String> vehicleVinPrefix = query.list();
        baseData.setVinPrefix(vehicleVinPrefix
                .toArray(new String[vehicleVinPrefix.size()]));
        // 获取车辆仓库信息
        query = baseDao.getCurrentSession().createQuery(
                "SELECT new BaseVehicleWarehouse(warehouseId, warehouseName, stationId) FROM BaseVehicleWarehouse b WHERE b.stationId IN :stationIds ORDER BY b.stationId, b.warehouseName")
                .setParameterList("stationIds", stationIds);
        baseData.setVehicleWarehouse(query.list());
        // 获取库存状态
        query = baseDao.getCurrentSession()
                .createQuery("SELECT b.meaning from SysFlags b WHERE b.fieldNo = 'vs_status'");
        List<String> stockStatus = query.list();
        baseData.setStatusMean(stockStatus
                .toArray(new String[stockStatus.size()]));
        // add by shichunshan
        // 付款类型
        query = baseDao.getCurrentSession()
                .createQuery("SELECT b.code,b.meaning from SysFlags b WHERE b.fieldNo = 'vi_pay_type' order by b.code");
        List<Object[]> viPayTypeList = query.list();
        if (viPayTypeList != null && viPayTypeList.size() > 0) {
            Map<Short, String> map = new HashMap();
            for (Object[] obj : viPayTypeList) {
                map.put((short) obj[0], obj[1].toString());
            }
            // map.put((short) 0, "所有");
            baseData.setViPayTypes(map);
        }

        // 车辆品系
        query = baseDao.getCurrentSession()
                .createSQLQuery("SELECT self_id,common_name FROM base_vehicle_name WHERE common_type='车辆品系'  AND (status IS NULL OR status = 1) ORDER BY common_name");
        List<Object[]> vehicleStrains = query.list();
        if (vehicleStrains != null && vehicleStrains.size() > 0) {
            Map<String, String> map = new HashMap();
            for (Object[] obj : vehicleStrains) {
                map.put(obj[0].toString(), obj[1].toString());
            }
            baseData.setVehicleStrains(map);
        }

        // 车型简称
        query = baseDao.getCurrentSession().createSQLQuery("SELECT DISTINCT short_name FROM dbo.base_vehicle_model_catalog WHERE short_name IS NOT NULL AND short_name<>''");
        List<String> vehicleCatalogShortNames = query.list();
        baseData.setVehicleCatalogShortNames(vehicleCatalogShortNames.toArray(new String[vehicleNames.size()]));


        //车型类型
        PageModel<Object> vehicleKinds = baseDao.listInHql("SELECT b.code AS code,b.meaning as meaning from SysFlags b WHERE b.fieldNo = 'vehicle_kind' order by b.code");
        baseData.setVehicleKinds(vehicleKinds.getData());

        return baseData;
    }

    /**
     * 根据条件分页查询车辆库存
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageModel<VwVehicleStock> getVehicleStockByCondition(
            VehicleStockSearchCriteria condition, int pageNo, int pageSize) {
        DetachedCriteria dc = createCriteria(condition);
        dc.addOrder(Order.asc("stationId"));
        dc.addOrder(Order.asc("warehouseName"));
        dc.addOrder(Order.asc("vehicleName"));
        dc.addOrder(Order.asc("status"));

        List<VwVehicleStock> list = (List<VwVehicleStock>) getHibernateTemplate()
                .findByCriteria(dc, (pageNo - 1) * pageSize, pageSize);
        PageModel<VwVehicleStock> pageModel = new PageModel<VwVehicleStock>(
                list);
        pageModel.setPage(pageNo);
        pageModel.setPerPage(pageSize);
        pageModel.setTotalSize(getResultSize(condition));
        return pageModel;
    }

    private String buildVehicleStockSqlCondition(VehicleStockSearchCriteria condition) {
        List<String> conditionArray = new ArrayList<String>();
        conditionArray.add("1 = 1");

        if (condition != null) {
//            List<String> stationIds = condition.getStationIds();
            //车辆库存浏览的站点id取MODULE_ID_STOCK_BROWSE的站点范围
            SysUsers user = HttpSessionStore.getSessionUser();
            String[] stationIds = userService.getModuleStationId(user, MODULE_ID_STOCK_BROWSE);
            String vehicleVno = condition.getVehicleVno();
            String vehicleColor = condition.getVehicleColor();
            String vehicleVin = condition.getVehicleVin();
            String vehicleStatus = condition.getVehicleStatus();
            String warehouseId = condition.getWareHouseId();
            List<String> vehicleVnos = condition.getVehicleVnos();
            // short vehiclePayType = condition.getVehiclePayType();
            int stockAgeMin = condition.getStockAgeMin();
            int stockAgeMax = condition.getStockAgeMax();

            String vehicleCatalogShortName = condition.getVehicleCatalogShortName();//车型简称
            String vehicleStrain = condition.getVehicleStrain(); //品系
            Short viPayType = condition.getViPayType();//付款方式
            Integer[] wirteOffFlag = condition.getWirteOffFlag();//销账状态
            Integer payDayMin = condition.getPayDayMin();//距监控到期天数
            Integer payDayMax = condition.getPayDayMax();//距监控到期天数
            String vehicleName = condition.getVehicleName();
            String filterStr = condition.getFilterStr();//筛选条件

            List<Short> vehicleKind = condition.getVehicleKind();//车型类型
            String storageAgeType = condition.getStorageAgeType();

            if (StringUtils.isNotBlank(vehicleCatalogShortName)) {
                //MessageFormat.format 中 单引号要用两个单引号转义
                conditionArray.add(MessageFormat.format("a.vehice_catalog_short_name LIKE ''%{0}%'' ", vehicleCatalogShortName));
            }


            if (StringUtils.isNotEmpty(vehicleStrain)) {
                conditionArray.add(MessageFormat.format("a.vehicle_strain LIKE  ''%{0}%''", vehicleStrain));
            }

            if (viPayType != null) {
                conditionArray.add(MessageFormat.format("a.vi_pay_type = {0}", viPayType));
            }

            if (wirteOffFlag != null && wirteOffFlag.length > 0) {
                conditionArray.add("ISNULL(a.write_off_flag , 0) IN (" + StringUtils.join(wirteOffFlag, ",") + ")");
            }

            if (payDayMin != null) {
                //SELECT DATEDIFF(day,'2008-12-30','2008-12-29') AS DiffDate  -1
                conditionArray.add(MessageFormat.format("DATEDIFF(day,a.future_pay_date,getdate())>={0}", payDayMin));

            }

            if (payDayMax != null) {
                conditionArray.add(MessageFormat.format("DATEDIFF(day,a.future_pay_date,getdate())<={0}", payDayMax));
            }

            if (StringUtils.isNotEmpty(filterStr)) {
                conditionArray.add(MessageFormat.format("(a.vehicle_vno LIKE  ''%{0}%'' OR a.vehicle_vin LIKE  ''%{0}%'' OR a.vehicle_name  LIKE  ''%{0}%'')", filterStr));
            }

            if (vehicleKind != null && vehicleKind.size() > 0) {
                conditionArray.add("a.vehicle_kind IN (" + StringUtils.join(vehicleKind, ",") + ")");
            }


            if (stationIds != null && stationIds.length > 0) {
                conditionArray.add("a.station_id IN ('" + StringUtils.join(stationIds, "','") + "')");
            }

            if (vehicleVno != null && vehicleVno.length() > 0) {
                conditionArray.add("a.vehicle_vno LIKE '%" + vehicleVno + "%'");
            }

            if (vehicleName != null && vehicleName.length() > 0) {
                conditionArray.add("a.vehicle_name LIKE '%" + vehicleName + "%'");
            }

            if (vehicleColor != null && vehicleColor.length() > 0) {
                conditionArray.add("a.vehicle_color LIKE '%" + vehicleColor + "%'");
            }
            if (vehicleVin != null && vehicleVin.length() > 0) {
                conditionArray.add("a.vehicle_vin LIKE '%" + vehicleVin + "%'");
            }

            if (vehicleVnos != null && vehicleVnos.size() > 0) {
                conditionArray.add("a.vehicle_vno IN ('" + StringUtils.join(vehicleVnos, "','") + "')");
            }

            // add by shichunshan 车辆状态
            if (vehicleStatus != null && vehicleStatus.length() > 0) {
                conditionArray.add("a.realStatus IN ('" + StringUtils.join(vehicleStatus, "','") + "')");
            } else {
                conditionArray.add("a.realStatus NOT IN ('已出库')");
            }
            // add by shichunshan 仓库id
            if (warehouseId != null && warehouseId.length() > 0) {
                conditionArray.add("a.warehouse_id IN ('" + StringUtils.join(warehouseId, "','") + "')");
            }

            if (stockAgeMin > 0) {
                if ("storageAgeInvoice".equals(storageAgeType)) {
                    //开票库龄
                    conditionArray.add("a.invoice_age >= '" + stockAgeMin + "'");
                } else {
                    conditionArray.add("a.stock_age >= '" + stockAgeMin + "'");
                }
            }
            if (stockAgeMax > 0) {
                if ("storageAgeInvoice".equals(storageAgeType)) {
                    //开票库龄
                    conditionArray.add("a.invoice_age <= '" + stockAgeMax + "'");
                } else {
                    conditionArray.add("a.stock_age <= '" + stockAgeMax + "'");
                }
            }

        }
        return " " + StringUtils.join(conditionArray, " AND ") + " ";
    }

    /*@Override
    public List<Map<String, Object>> getVehicleStock(VehicleStockSearchCriteria condition, int pageNo, int pageSize) {
        String conditionString = buildVehicleStockSqlCondition(condition);

        String sql = "SELECT * FROM ( " +
                "SELECT  *, \n" +
                "ROW_NUMBER() OVER (" + getVehicleStockOderBy(condition) + ") as row_id\n" +
                "FROM    ( SELECT    a.status,\n" +  // --库存状态(0:未订购，1:已订购，3:已出库),
                "                    a.realStatus, --库存状态\n" +
                "                    a.stock_age,--库龄 \n" +
                "                    a.invoice_age,--开票库龄\n" +
                "                    a.vehicle_id,--车辆ID\n" +
                "                    a.vehicle_vin,--VIN码\n" +
                "                    a.vno_id,--车型ID\n" +
                "                    a.vehicle_sales_code,--销售代号\n" +
                "                    a.vehicle_vno,--车型\n" +
                "                    a.vehicle_name,--车辆名称\n" +
                "                    a.vehicle_strain,--车辆品系\n" +
                "                    a.vehicle_color,--车型颜色\n" +
                "                    a.vehicle_engine_type,--发动机型号\n" +
                "                    a.vehicle_engine_no,--发动机编码\n" +
                "                    a.vehicle_eligible_no,--合格证号\n" +
                "                    a.vehicle_out_factory_time,--出厂时间\n" +
                "                    a.vehicle_cost,--车辆成本\n" +
                "                    a.vehicle_carriage,--车辆运费\n" +
                "                    a.vi_pay_type,\n" +
                "                    a.vi_pay_type_meaning,--付款类型\n" +
                "                    a.in_stock_no,--入库单号\n" +
                "                    a.in_stock_time,--入库时间\n" +
                "                    a.in_stock_type,--入库类型\n" +
                "\t\t\t\t\ta.inStockType,--入库类型\n" +
                "                    a.warehouse_name,--仓库名称\n" +
                "                    a.warehouse_id,--仓库ID\n" +
                "                    a.station_id,--站点ID\n" +
                "                    a.station_name,--站点名称\n" +
                "                    a.supplier_id,--供应商ID\n" +
                "                    a.supplier_name,--供应商名称\n" +
                "                    a.carry_no,--运输单号\n" +
                "                    a.sale_contract_no,--销售单号\n" +
                "                    a.sale_contract_code,--合同单号\n" +
                "                    a.customer_id,--客户ID\n" +
                "                    a.customer_name,--客户名称\n" +
                "                    a.seller_id,--销售员ID\n" +
                "                    a.seller,--销售员\n" +
                "                    a.out_stock_no,--出库单号\n" +
                "                    a.out_stock_time,--出库时间\n" +
                "                    a.out_stock_type,--出库类型\n" +
                "\t\t\t\t\ta.outStockType,--出库类型\n" +
                "                    a.vehicle_price,--销售价格\n" +
                "                    a.vehicle_quantity,--车辆数量\n" +
                "                    a.delivery_locus,--销售网点\n" +
                "                    a.conversion_status,--改装状态\n" +
                "\t\t\t\t\ta.conversion_status_meaning,--改装状态\n" +
                "                    a.vehicle_vno_new,--新车辆型号\n" +
                "                    a.vehicle_vin_new,--新VIN码\n" +
                "                    a.profit_return,--返利金额\n" +
                "                    a.modified_fee,--改装费用\n" +
                "                    a.move_stock_charge,--移调费用\n" +
                "                    a.other_cost,--其他成本\n" +
                "                    a.invoice_no,--发票号码\n" +
                "                    a.invoice_amount,--发票金额\n" +
                "                    a.invoice_date,--开票日期\n" +
                "                    a.drive_room_no,--驾驶室号\n" +
                "                    a.future_pay_date,--预计付款日期\n" +
                "                    a.write_off_date,--销账日期\n" +
                "                    a.write_off_flag,--是否已销账\n" +
                "                    a.write_off_flag_meaning,--是否已销账meaning\n" +
                "                    a.write_off_no,--销账单号\n" +
                "                    a.scanning_out_stocks,--GPS出库日期 \n" +
                "                    a.vehicle_sale_documents,--SAP订单号\n" +
                "                    a.sap_contract_no,    --SAP合同号  \n" +
                "                    a.speed_ratio,--速比\n" +
                "                    a.tire_type,--轮胎型号\n" +
                "                    a.underpan_no,--底盘号\n" +
                "                    a.gps_location,--GPS位置信息\n" +
                "                    a.container_spec,--货箱规格\n" +
                "                    a.vehicle_kind,--车型类型\n" +
                "                    a.vehicle_kind_meaning,\n" +
                "                    (case when a.min_sale_price is not null and a.min_sale_price>0 then a.min_sale_price else ISNULL(f.price_sale, c.price_sale) end) AS vehicle_sale_price_reference,--最低限价\n" +
                "                    b.buy_type_meaning, --购车方式\n" +
                "                    b.plan_deliver_time, --计划交车日期\n" +
                "                    c.short_name as vehice_catalog_short_name, --车型简称\n" +
                "                    vcs.certificate_time,\n" +
                "                    b.real_deliver_time--实际交车日期       \n" +
                "          FROM      dbo.vw_vehicle_stock a\n" +
                "          LEFT JOIN (SELECT vehicle_id,MAX(certificate_time) AS certificate_time  FROM dbo.vehicle_certificate_stocks GROUP BY vehicle_id) vcs ON vcs.vehicle_id = a.vehicle_id\n" +
                "          LEFT JOIN base_vehicle_model_catalog c ON a.vno_id = c.self_id\n" +
                "          LEFT JOIN base_vehicle_model_catalog_price AS f ON a.vno_id = f.parent_id \n" +
                "\t\t\t\t\tAND ( f.station_id IS NULL OR a.station_id = f.station_id )\n" +
                "          LEFT JOIN ( SELECT a.contract_no, b.vehicle_id, a.buy_type, \n" +
                "\t\t\t\t\t\tc.meaning AS buy_type_meaning, b.plan_deliver_time, b.real_deliver_time\n" +
                "                      FROM dbo.vehicle_sale_contracts a\n" +
                "                      LEFT JOIN dbo.vehicle_sale_contract_detail b ON a.contract_no = b.contract_no\n" +
                "                      LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) AS c ON a.buy_type = c.code\n" +
                "                      WHERE     a.contract_status <> 3 AND a.contract_status <> 4 AND b.approve_status IN ( 0, 1, 2 ) ) b \n" +
                "\t\t\t\t\t  ON a.sale_contract_no = b.contract_no AND a.vehicle_id = b.vehicle_id\n" +
                "          WHERE  a.in_stock_no IS NOT NULL AND a.in_stock_no<>'' ) a\n" +
                "WHERE  vehicle_vin NOT LIKE '$%' AND  " + conditionString +
                ") r WHERE " +
                " r.row_id > " + (pageNo - 1) * pageSize +
                " AND " +
                " r.row_id <= " + (pageNo) * pageSize;
        return baseDao.getMapBySQL(sql, null);
    }*/


    /**
     * 设置排序字段
     *
     * @return
     */
    private String getVehicleStockOderBy(VehicleStockSearchCriteria condition) {
        String orderBy = "a.invoice_age";
        String desc = "desc";

        if (condition != null && "storageAgeType".equals(condition.getStorageAgeType())) {
            orderBy = "a.stock_age";
        }

        if (condition != null && "asc".equalsIgnoreCase(condition.getOrderBy())) {
            desc = "asc";
        }

        return String.format("ORDER BY %s %s", orderBy, desc);

    }

    /*@Override
    public long getVehicleStockCount(VehicleStockSearchCriteria condition) {
        String conditionString = buildVehicleStockSqlCondition(condition);
        String sql = "SELECT  COUNT(*) AS c \n" +
                "FROM    ( SELECT    a.status,\n" +  //--库存状态(0:未订购，1:已订购，3:已出库),
                "                    a.realStatus, --库存状态\n" +
                "                    a.stock_age,--库龄 \n" +
                "                    a.invoice_age,--开票库龄\n" +
                "                    a.vehicle_id,--车辆ID\n" +
                "                    a.vehicle_vin,--VIN码\n" +
                "                    a.vno_id,--车型ID\n" +
                "                    a.vehicle_sales_code,--销售代号\n" +
                "                    a.vehicle_vno,--车型\n" +
                "                    a.vehicle_name,--车辆名称\n" +
                "                    a.vehicle_strain,--车辆品系\n" +
                "                    a.vehicle_color,--车型颜色\n" +
                "                    a.vehicle_engine_type,--发动机型号\n" +
                "                    a.vehicle_engine_no,--发动机编码\n" +
                "                    a.vehicle_eligible_no,--合格证号\n" +
                "                    a.vehicle_out_factory_time,--出厂时间\n" +
                "                    a.vehicle_cost,--车辆成本\n" +
                "                    a.vehicle_carriage,--车辆运费\n" +
                "                    a.vi_pay_type,\n" +
                "                    a.vi_pay_type_meaning,--付款类型\n" +
                "                    a.in_stock_no,--入库单号\n" +
                "                    a.in_stock_time,--入库时间\n" +
                "                    a.in_stock_type,--入库类型\n" +
                "\t\t\t\t\ta.inStockType,--入库类型\n" +
                "                    a.warehouse_name,--仓库名称\n" +
                "                    a.warehouse_id,--仓库ID\n" +
                "                    a.station_id,--站点ID\n" +
                "                    a.station_name,--站点名称\n" +
                "                    a.supplier_id,--供应商ID\n" +
                "                    a.supplier_name,--供应商名称\n" +
                "                    a.carry_no,--运输单号\n" +
                "                    a.sale_contract_no,--销售单号\n" +
                "                    a.sale_contract_code,--合同单号\n" +
                "                    a.customer_id,--客户ID\n" +
                "                    a.customer_name,--客户名称\n" +
                "                    a.seller_id,--销售员ID\n" +
                "                    a.seller,--销售员\n" +
                "                    a.out_stock_no,--出库单号\n" +
                "                    a.out_stock_time,--出库时间\n" +
                "                    a.out_stock_type,--出库类型\n" +
                "\t\t\t\t\ta.outStockType,--出库类型\n" +
                "                    a.vehicle_price,--销售价格\n" +
                "                    a.vehicle_quantity,--车辆数量\n" +
                "                    a.delivery_locus,--销售网点\n" +
                "                    a.conversion_status,--改装状态\n" +
                "\t\t\t\t\ta.conversion_status_meaning,--改装状态\n" +
                "                    a.vehicle_vno_new,--新车辆型号\n" +
                "                    a.vehicle_vin_new,--新VIN码\n" +
                "                    a.profit_return,--返利金额\n" +
                "                    a.modified_fee,--改装费用\n" +
                "                    a.move_stock_charge,--移调费用\n" +
                "                    a.other_cost,--其他成本\n" +
                "                    a.invoice_no,--发票号码\n" +
                "                    a.invoice_amount,--发票金额\n" +
                "                    a.invoice_date,--开票日期\n" +
                "                    a.drive_room_no,--驾驶室号\n" +
                "                    a.future_pay_date,--预计付款日期\n" +
                "                    a.write_off_date,--销账日期\n" +
                "                    a.write_off_flag,--是否已销账\n" +
                "                    a.write_off_flag_meaning,--是否已销账meaning\n" +
                "                    a.write_off_no,--销账单号\n" +
                "                    a.scanning_out_stocks,--GPS出库日期 \n" +
                "                    a.vehicle_sale_documents,--SAP订单号\n" +
                "                    a.sap_contract_no,    --SAP合同号  \n" +
                "                    a.speed_ratio,--速比\n" +
                "                    a.tire_type,--轮胎型号\n" +
                "                    a.underpan_no,--底盘号\n" +
                "                    a.gps_location,--GPS位置信息\n" +
                "                    a.container_spec,--货箱规格\n" +
                "                    a.vehicle_kind,--车型类型\n" +
                "                    a.vehicle_kind_meaning,\n" +
                "                    ISNULL(f.price_sale, c.price_sale) AS vehicle_sale_price_reference,--最低限价\n" +
                "                    b.buy_type_meaning, --购车方式\n" +
                "                    b.plan_deliver_time, --计划交车日期\n" +
                "                    c.short_name as vehice_catalog_short_name, --车型简称\n" +
                "                    b.real_deliver_time--实际交车日期       \n" +
                "          FROM      dbo.vw_vehicle_stock a\n" +
                "          LEFT JOIN base_vehicle_model_catalog c ON a.vno_id = c.self_id\n" +
                "          LEFT JOIN base_vehicle_model_catalog_price AS f ON a.vno_id = f.parent_id \n" +
                "\t\t\t\t\tAND ( f.station_id IS NULL OR a.station_id = f.station_id )\n" +
                "          LEFT JOIN ( SELECT a.contract_no, b.vehicle_id, a.buy_type, \n" +
                "\t\t\t\t\t\tc.meaning AS buy_type_meaning, b.plan_deliver_time, b.real_deliver_time\n" +
                "                      FROM dbo.vehicle_sale_contracts a\n" +
                "                      LEFT JOIN dbo.vehicle_sale_contract_detail b ON a.contract_no = b.contract_no\n" +
                "                      LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) AS c ON a.buy_type = c.code\n" +
                "                      WHERE     a.contract_status <> 3 AND a.contract_status <> 4 AND b.approve_status IN ( 0, 1, 2 ) ) b \n" +
                "\t\t\t\t\t  ON a.sale_contract_no = b.contract_no AND a.vehicle_id = b.vehicle_id\n" +
                "          WHERE a.in_stock_no IS NOT NULL AND a.in_stock_no<>'') a\n" +
                "WHERE  vehicle_vin NOT LIKE '$%' AND  " + conditionString + ";";
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        return Long.parseLong(result.get(0).get("c").toString());
    }*/

    private String buildOnWayVehicleCondition(VehicleStockSearchCriteria condition, String onWayType) {
        List<String> conditionArray = new ArrayList<String>();
        if ("onWay".equals(onWayType)) {
            conditionArray.add("a.invoiced_onway_qty >0"); //在途
        } else if ("order".equals(onWayType)) {
            conditionArray.add("a.unbilled_qty >0"); //订单
        } else {
            conditionArray.add("1 = 1");
        }

        if (condition != null) {
//            List<String> stationIds = condition.getStationIds();
            //车辆库存浏览的站点id取MODULE_ID_STOCK_BROWSE的站点范围
            SysUsers user = HttpSessionStore.getSessionUser();
            String[] stationIds = userService.getModuleStationId(user, MODULE_ID_STOCK_BROWSE);
            String vehicleVno = condition.getVehicleVno();
            String filterStr = condition.getFilterStr();
            String color = condition.getVehicleColor();
            String vehicleStrain = condition.getVehicleStrain();

            if (stationIds != null && stationIds.length > 0) {
                conditionArray.add("a.station_id IN ('" + StringUtils.join(stationIds, "','") + "')");
            }

            if (vehicleVno != null && vehicleVno.length() > 0) {
                conditionArray.add("a.vehicle_vno LIKE '%" + vehicleVno + "%'");
            }

            if (StringUtils.isNotEmpty(color)) {
                conditionArray.add("a.vehicle_color = '" + color + "'");
            }

            if (StringUtils.isNotEmpty(filterStr)) {
                conditionArray.add("(a.vehicle_vno LIKE '%" + filterStr + "%' OR a.underpan_no LIKE '%" + filterStr + "%')");
            }

            if (StringUtils.isNotEmpty(vehicleStrain)) {
                conditionArray.add(MessageFormat.format("a.vehicle_strain LIKE  ''%{0}%''", vehicleStrain));
            }

        }
        return " " + StringUtils.join(conditionArray, " AND ") + " ";
    }

    public PageModel<Map<String, Object>> getVehicleStock(VehicleStockSearchCriteria condition, int page, int perPage) {
        String conditionString = buildVehicleStockSqlCondition(condition);
        String baseSql = getCurrentSession().getNamedQuery("getVehicleStock").getQueryString();
        baseSql += " and ";
        baseSql += conditionString;

        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(1) FROM (" + baseSql + ") base_tmp";
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));

        result.setPage(page);
        result.setPerPage(perPage);
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String order = "asc";
        if(null != condition && StringUtils.isNotEmpty(condition.getOrderBy())){
            order = condition.getOrderBy();
        }
        String buildSql = baseSql + " order by vehicle_vno " + order + pageSql;
        result.setData(getMapBySQL(buildSql, null));
        return result;
    }

    @Override
    public PageModel<Map<String, Object>> getOnWayVehicle(VehicleStockSearchCriteria condition, String onWayType, int page, int perPage) {
        String conditionStr = buildOnWayVehicleCondition(condition, onWayType);
        String baseSql = getCurrentSession().getNamedQuery("getOnWayVehicle").getQueryString();
        baseSql += " where ";
        baseSql += conditionStr;

        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(1) FROM (" + baseSql + ") base_tmp";
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));

        result.setPage(page);
        result.setPerPage(perPage);
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = baseSql + " order by vehicle_vno " + pageSql;
        result.setData(getMapBySQL(buildSql, null));
        return result;
    }

    /*@Override
    public List<Map<String, Object>> getOnWayVehicle(VehicleStockSearchCriteria condition, String onWayType, int pageNo, int pageSize) {
        String conditionStr = buildOnWayVehicleCondition(condition, onWayType);

        String sql = "SELECT * FROM ( " +
                "SELECT  *, \n" +
                "ROW_NUMBER() OVER (ORDER BY a.start_date) as row_id\n" +
                "FROM    (SELECT '在途' as realStatus,vmc.vehicle_strain, d.station_id, st.station_name, ISNULL (NULLIF(b.color, ''), b.color_name) AS vehicle_color,\n" +
                "\t\t\t  b.color, b.color_name, c.vehicle_vno_id, c.vehicle_vno,\n" +
                "\t\t\t  ISNULL (vmcp.price_sale, vmc.price_sale) AS price_sale, a.*, e.apply_type, e.sap_order_no,e.sap_create_date,\n" +
                "\t\t\t  c.transport_to,\n" +
                "\t\t\t  ( SELECT STUFF (\n" +
                "\t\t\t\t\t   ( SELECT DISTINCT ',' + lv.underpan_no\n" +
                "\t\t\t\t\t\t FROM ( SELECT b.sap_contract_no, a.underpan_no\n" +
                "\t\t\t\t\t\t\t\tFROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t\t\t\t   INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t\t\t\t\tWHERE ISNULL (a.finish_status, 0) = 0\n" +
                "\t\t\t\t\t\t\t\t\tAND a.underpan_no NOT LIKE 'DEL_%' ) lv\n" +
                "\t\t\t\t\t\t WHERE a.contract_no = lv.sap_contract_no\n" +
                "\t\t\t\t\t   FOR XML PATH ('')), 1, 1, '')) underpan_no,\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t   THEN 0\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t   THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END\n" +
                "\t\t\t  END AS unbilled_qty, --可开票\n" +
                "\t\t\t  CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END AS invoiced_onway_qty --已开票在途\n" +
                "\t   FROM dbo.vehicle_DF_sap_contract a\n" +
                "\t   LEFT JOIN dbo.vehicle_DF_purchase_order b ON b.purchase_order_no = a.purchase_order_no\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply_detail c ON b.apply_order_no = c.order_no\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND b.work_state_audit = c.work_state_audit\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND ISNULL (c.detail_status, '') <> 'CRM删除'\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply d ON c.order_no = d.order_no\n" +
                "\t   LEFT JOIN dbo.sys_stations st ON d.station_id = st.station_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog vmc ON c.vehicle_vno_id = vmc.self_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog_price vmcp ON c.vehicle_vno_id = vmcp.parent_id\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   AND vmcp.station_id = d.station_id\n" +
                "\t   LEFT JOIN ( SELECT *\n" +
                "\t\t\t\t   FROM ( SELECT a.sap_contract_no, a.sap_order_no,b.create_date AS sap_create_date,\n" +
                "\t\t\t\t\t\t\t\t CASE WHEN a.apply_type IN ( '监控车', '一次性监控' ) THEN '监控' ELSE '现款' END AS apply_type,\n" +
                "\t\t\t\t\t\t\t\t ROW_NUMBER () OVER ( PARTITION BY a.sap_contract_no ORDER BY a.sap_contract_no ) AS rowNum\n" +
                "\t\t\t\t\t\t  FROM dbo.vehicle_DF_invoice_apply a\n" +
                "\t\t\t\t\t\t  LEFT JOIN vehicle_DF_sap_order b ON a.sap_order_no=b.sap_order_no \n" +
                "\t\t\t\t\t\t  WHERE a.status NOT LIKE '%拒绝%'\n" +
                "\t\t\t\t\t\t\t  AND a.status NOT LIKE '%作废%' ) a\n" +
                "\t\t\t\t   WHERE rowNum = 1 ) e ON e.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.finish_status = 1\n" +
                "\t\t\t\t\t\t\t OR a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f ON f.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f1 ON f1.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT a.sap_contract_no, COUNT (*) invoice_qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_invoice_apply a\n" +
                "\t\t\t\t   WHERE a.status IN ( '开票完成' )\n" +
                "\t\t\t\t   GROUP BY a.sap_contract_no ) g ON g.sap_contract_no = a.contract_no\n" +
                "\t   WHERE d.station_id IS NOT NULL\n" +
                "\t\t   AND a.refuse_reason = '有效'\n" +
                "\t\t   AND ( CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t\t  THEN 0\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t\t  THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t 0\n" +
                "\t\t\t\t END\n" +
                "\t\t\t\t END > 0\n" +
                "\t\t\t\t   OR CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t\t\t  ELSE\n" +
                "\t\t\t\t\t  0\n" +
                "\t\t\t\t\t  END > 0 )\n" +
                "\t\t   AND b.status NOT LIKE '%作废%'\n" +
                "\t\t   AND b.status NOT LIKE '%终止%'\n" +
                "\t\t   AND b.status NOT LIKE '%拒绝%') a\n" +
                "WHERE " + conditionStr +
                ") r WHERE " +
                " r.row_id > " + (pageNo - 1) * pageSize +
                " AND " +
                " r.row_id <= " + (pageNo) * pageSize;
        return baseDao.getMapBySQL(sql, null);
    }*/

    /*@Override
    public long getOnWayVehicleCount(VehicleStockSearchCriteria condition, String onWayType) {
        String conditionStr = buildOnWayVehicleCondition(condition, onWayType);
        String sql = "SELECT   COUNT(*) AS c\n" +
                "FROM    ( SELECT d.station_id, st.station_name, ISNULL (NULLIF(b.color, ''), b.color_name) AS vehicle_color,\n" +
                "\t\t\t  b.color, b.color_name, c.vehicle_vno_id, c.vehicle_vno,\n" +
                "\t\t\t  ISNULL (vmcp.price_sale, vmc.price_sale) AS price_sale, a.*, e.apply_type, e.sap_order_no,\n" +
                "\t\t\t  c.transport_to,\n" +
                "\t\t\t  ( SELECT STUFF (\n" +
                "\t\t\t\t\t   ( SELECT DISTINCT ',' + lv.underpan_no\n" +
                "\t\t\t\t\t\t FROM ( SELECT b.sap_contract_no, a.underpan_no\n" +
                "\t\t\t\t\t\t\t\tFROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t\t\t\t   INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t\t\t\t\tWHERE ISNULL (a.finish_status, 0) = 0\n" +
                "\t\t\t\t\t\t\t\t\tAND a.underpan_no NOT LIKE 'DEL_%' ) lv\n" +
                "\t\t\t\t\t\t WHERE a.contract_no = lv.sap_contract_no\n" +
                "\t\t\t\t\t   FOR XML PATH ('')), 1, 1, '')) underpan_no,\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t   THEN 0\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t   THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END\n" +
                "\t\t\t  END AS unbilled_qty, --可开票\n" +
                "\t\t\t  CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END AS invoiced_onway_qty --已开票在途\n" +
                "\t   FROM dbo.vehicle_DF_sap_contract a\n" +
                "\t   LEFT JOIN dbo.vehicle_DF_purchase_order b ON b.purchase_order_no = a.purchase_order_no\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply_detail c ON b.apply_order_no = c.order_no\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND b.work_state_audit = c.work_state_audit\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND ISNULL (c.detail_status, '') <> 'CRM删除'\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply d ON c.order_no = d.order_no\n" +
                "\t   LEFT JOIN dbo.sys_stations st ON d.station_id = st.station_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog vmc ON c.vehicle_vno_id = vmc.self_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog_price vmcp ON c.vehicle_vno_id = vmcp.parent_id\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   AND vmcp.station_id = d.station_id\n" +
                "\t   LEFT JOIN ( SELECT *\n" +
                "\t\t\t\t   FROM ( SELECT sap_contract_no, sap_order_no,\n" +
                "\t\t\t\t\t\t\t\t CASE WHEN apply_type IN ( '监控车', '一次性监控' ) THEN '监控' ELSE '现款' END AS apply_type,\n" +
                "\t\t\t\t\t\t\t\t ROW_NUMBER () OVER ( PARTITION BY sap_contract_no ORDER BY sap_contract_no ) AS rowNum\n" +
                "\t\t\t\t\t\t  FROM dbo.vehicle_DF_invoice_apply\n" +
                "\t\t\t\t\t\t  WHERE status NOT LIKE '%拒绝%'\n" +
                "\t\t\t\t\t\t\t  AND status NOT LIKE '%作废%' ) a\n" +
                "\t\t\t\t   WHERE rowNum = 1 ) e ON e.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.finish_status = 1\n" +
                "\t\t\t\t\t\t\t OR a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f ON f.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f1 ON f1.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT a.sap_contract_no, COUNT (*) invoice_qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_invoice_apply a\n" +
                "\t\t\t\t   WHERE a.status IN ( '开票完成' )\n" +
                "\t\t\t\t   GROUP BY a.sap_contract_no ) g ON g.sap_contract_no = a.contract_no\n" +
                "\t   WHERE d.station_id IS NOT NULL\n" +
                "\t\t   AND a.refuse_reason = '有效'\n" +
                "\t\t   AND ( CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t\t  THEN 0\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t\t  THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t 0\n" +
                "\t\t\t\t END\n" +
                "\t\t\t\t END > 0\n" +
                "\t\t\t\t   OR CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t\t\t  ELSE\n" +
                "\t\t\t\t\t  0\n" +
                "\t\t\t\t\t  END > 0 )\n" +
                "\t\t   AND b.status NOT LIKE '%作废%'\n" +
                "\t\t   AND b.status NOT LIKE '%终止%'\n" +
                "\t\t   AND b.status NOT LIKE '%拒绝%' ) a\n" +
                "WHERE " + conditionStr;
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        return Long.parseLong(result.get(0).get("c").toString());
    }*/

    @SuppressWarnings("rawtypes")
    private long getResultSize(VehicleStockSearchCriteria criterial) {
        DetachedCriteria dc = createCriteria(criterial);
        dc.setProjection(Projections.rowCount());
        List count = getHibernateTemplate().findByCriteria(dc);
        return (Long) count.get(0);
    }

    /**
     * 获取出入口历史
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleStockHistory> getVehicleStockHistory(String vehicleId) {
        return (List<VehicleStockHistory>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("vehicleStockHistory",
                        "vehicleId", vehicleId);
    }

    /**
     * 获取改装明细
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getVehicleConversionDetail(
            String vehicleId) {
        String baseSql = getCurrentSession().getNamedQuery("vehicleConversionDetail").getQueryString();
        Map<String, Object> parm = new HashMap();
        parm.put("vehicleId", vehicleId);
        return getMapBySQL(baseSql, parm);
    }

    @Override
    @SuppressWarnings("unchecked")
    public VehicleStockStatistical getVehicleStockStatistical(
            VehicleStockSearchCriteria condition) {
        String conditionString = buildVehicleStockSqlCondition(condition);

        String sql = "SELECT  COUNT(*) as totalCount,\n"
                + "SUM(case when (tmp.status=1) then 1 WHEN (tmp.status=2) then 1 ELSE 0 END) as orderCount,\n"
                + "SUM(case  when (tmp.status) = 0 then 1  ELSE 0 END ) as sellableCount,\n"
                + "SUM(tmp.vehicle_cost) as totalCost FROM" +
                " ( " +
                "SELECT  * \n" +
                "FROM    ( SELECT    a.status,\n" +  // --库存状态(0:未订购，1:已订购，3:已出库),
                "                    a.realStatus, --库存状态\n" +
                "                    a.stock_age,--库龄\n" +
                "                    a.invoice_age,--开票库龄\n" +
                "                    a.vehicle_id,--车辆ID\n" +
                "                    a.vehicle_vin,--VIN码\n" +
                "                    a.vno_id,--车型ID\n" +
                "                    a.vehicle_sales_code,--销售代号\n" +
                "                    a.vehicle_vno,--车型\n" +
                "                    a.vehicle_name,--车辆名称\n" +
                "                    a.vehicle_strain,--车辆品系\n" +
                "                    a.vehicle_color,--车型颜色\n" +
                "                    a.vehicle_engine_type,--发动机型号\n" +
                "                    a.vehicle_engine_no,--发动机编码\n" +
                "                    a.vehicle_eligible_no,--合格证号\n" +
                "                    a.vehicle_out_factory_time,--出厂时间\n" +
                "                    a.vehicle_cost,--车辆成本\n" +
                "                    a.vehicle_carriage,--车辆运费\n" +
                "                    a.vi_pay_type,\n" +
                "                    a.vi_pay_type_meaning,--付款类型\n" +
                "                    a.in_stock_no,--入库单号\n" +
                "                    a.in_stock_time,--入库时间\n" +
                "                    a.in_stock_type,--入库类型\n" +
                "\t\t\t\t\ta.inStockType,--入库类型\n" +
                "                    a.warehouse_name,--仓库名称\n" +
                "                    a.warehouse_id,--仓库ID\n" +
                "                    a.station_id,--站点ID\n" +
                "                    a.station_name,--站点名称\n" +
                "                    a.supplier_id,--供应商ID\n" +
                "                    a.supplier_name,--供应商名称\n" +
                "                    a.carry_no,--运输单号\n" +
                "                    a.sale_contract_no,--销售单号\n" +
                "                    a.sale_contract_code,--合同单号\n" +
                "                    a.customer_id,--客户ID\n" +
                "                    a.customer_name,--客户名称\n" +
                "                    a.seller_id,--销售员ID\n" +
                "                    a.seller,--销售员\n" +
                "                    a.out_stock_no,--出库单号\n" +
                "                    a.out_stock_time,--出库时间\n" +
                "                    a.out_stock_type,--出库类型\n" +
                "\t\t\t\t\ta.outStockType,--出库类型\n" +
                "                    a.vehicle_price,--销售价格\n" +
                "                    a.vehicle_quantity,--车辆数量\n" +
                "                    a.delivery_locus,--销售网点\n" +
                "                    a.conversion_status,--改装状态\n" +
                "\t\t\t\t\ta.conversion_status_meaning,--改装状态\n" +
                "                    a.vehicle_vno_new,--新车辆型号\n" +
                "                    a.vehicle_vin_new,--新VIN码\n" +
                "                    a.profit_return,--返利金额\n" +
                "                    a.modified_fee,--改装费用\n" +
                "                    a.move_stock_charge,--移调费用\n" +
                "                    a.other_cost,--其他成本\n" +
                "                    a.invoice_no,--发票号码\n" +
                "                    a.invoice_amount,--发票金额\n" +
                "                    a.invoice_date,--开票日期\n" +
                "                    a.drive_room_no,--驾驶室号\n" +
                "                    a.future_pay_date,--预计付款日期\n" +
                "                    a.write_off_date,--销账日期\n" +
                "                    a.write_off_flag,--是否已销账\n" +
                "                    a.write_off_flag_meaning,--是否已销账meaning\n" +
                "                    a.write_off_no,--销账单号\n" +
                "                    a.scanning_out_stocks,--GPS出库日期 \n" +
                "                    a.vehicle_sale_documents,--SAP订单号\n" +
                "                    a.sap_contract_no,    --SAP合同号  \n" +
                "                    a.speed_ratio,--速比\n" +
                "                    a.tire_type,--轮胎型号\n" +
                "                    a.underpan_no,--底盘号\n" +
                "                    a.gps_location,--GPS位置信息\n" +
                "                    a.container_spec,--货箱规格\n" +
                "                    a.vehicle_kind,--车型类型\n" +
                "                    a.vehicle_kind_meaning,\n" +
                "                    ISNULL(f.price_sale, c.price_sale) AS vehicle_sale_price_reference,--最低限价\n" +
                "                    b.buy_type_meaning, --购车方式\n" +
                "                    b.plan_deliver_time, --计划交车日期\n" +
                "                    c.short_name as vehice_catalog_short_name, --车型简称\n" +
                "                    vcs.certificate_time,\n" +
                "                    b.real_deliver_time--实际交车日期       \n" +
                "          FROM      dbo.vw_vehicle_stock a\n" +
                "          LEFT JOIN (SELECT vehicle_id,MAX(certificate_time) AS certificate_time  FROM dbo.vehicle_certificate_stocks GROUP BY vehicle_id) vcs ON vcs.vehicle_id = a.vehicle_id\n" +
                "          LEFT JOIN base_vehicle_model_catalog c ON a.vno_id = c.self_id\n" +
                "          LEFT JOIN base_vehicle_model_catalog_price AS f ON a.vno_id = f.parent_id \n" +
                "\t\t\t\t\tAND ( f.station_id IS NULL OR a.station_id = f.station_id )\n" +
                "          LEFT JOIN ( SELECT a.contract_no, b.vehicle_id, a.buy_type, \n" +
                "\t\t\t\t\t\tc.meaning AS buy_type_meaning, b.plan_deliver_time, b.real_deliver_time\n" +
                "                      FROM dbo.vehicle_sale_contracts a\n" +
                "                      LEFT JOIN dbo.vehicle_sale_contract_detail b ON a.contract_no = b.contract_no\n" +
                "                      LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) AS c ON a.buy_type = c.code\n" +
                "                      WHERE     a.contract_status <> 3 AND a.contract_status <> 4 AND b.approve_status IN ( 0, 1, 2 ) ) b \n" +
                "\t\t\t\t\t  ON a.sale_contract_no = b.contract_no AND a.vehicle_id = b.vehicle_id ) a\n" +
                "WHERE  vehicle_vin NOT LIKE '$%' AND  " + conditionString +
                ") tmp ";

        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        VehicleStockStatistical statistical = new VehicleStockStatistical();

        if (result == null || result.size() == 0) {
            statistical.setOrderCount(0L);
            statistical.setTotalCost(0);
            statistical.setTotalCount(0);
            statistical.setVendibleCount(0);
        } else {
            statistical.setOrderCount(result.get(0).get("orderCount") == null ? 0L
                    : Long.valueOf(String.valueOf(result.get(0).get("orderCount"))));
            statistical.setTotalCost(result.get(0).get("totalCost") == null ? 0D
                    : Double.valueOf(String.valueOf(result.get(0).get("totalCost"))));
            statistical.setTotalCount(result.get(0).get("totalCount") == null ? 0L
                    : Long.valueOf(String.valueOf(result.get(0).get("totalCount"))));
            statistical.setVendibleCount(result.get(0).get("sellableCount") == null ? 0L :
                    Long.valueOf(String.valueOf(result.get(0).get("sellableCount"))));
        }

        return statistical;
    }

    @Override
    public Map<String, Object> getOnWayVehicleStockStatistical(VehicleStockSearchCriteria condition, String onWayType) {
        String conditionStr = this.buildOnWayVehicleCondition(condition, onWayType);
        String sql = "SELECT SUM(unbilled_qty+invoiced_onway_qty) AS vehicle_count,\n" +
                " SUM(price_sale) AS vehicle_price, \n" +
                "SUM(unbilled_qty) AS unbilled_qty, \n" +
                "SUM(invoiced_onway_qty) AS invoiced_onway_qty \n" +
                "FROM ( SELECT '在途' as realStatus,vmc.vehicle_strain, d.station_id, st.station_name, ISNULL (NULLIF(b.color, ''), b.color_name) AS vehicle_color,\n" +
                "\t\t\t  b.color, b.color_name, c.vehicle_vno_id, c.vehicle_vno,\n" +
                "\t\t\t  ISNULL (vmcp.price_sale, vmc.price_sale) AS price_sale, a.*, e.apply_type, e.sap_order_no,\n" +
                "\t\t\t  c.transport_to,\n" +
                "\t\t\t  ( SELECT STUFF (\n" +
                "\t\t\t\t\t   ( SELECT DISTINCT ',' + lv.underpan_no\n" +
                "\t\t\t\t\t\t FROM ( SELECT b.sap_contract_no, a.underpan_no\n" +
                "\t\t\t\t\t\t\t\tFROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t\t\t\t   INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t\t\t\t\tWHERE ISNULL (a.finish_status, 0) = 0\n" +
                "\t\t\t\t\t\t\t\t\tAND a.underpan_no NOT LIKE 'DEL_%' ) lv\n" +
                "\t\t\t\t\t\t WHERE a.contract_no = lv.sap_contract_no\n" +
                "\t\t\t\t\t   FOR XML PATH ('')), 1, 1, '')) underpan_no,\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t   THEN 0\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t   THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END\n" +
                "\t\t\t  END AS unbilled_qty, --可开票\n" +
                "\t\t\t  CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t  ELSE\n" +
                "\t\t\t  0\n" +
                "\t\t\t  END AS invoiced_onway_qty --已开票在途\n" +
                "\t   FROM dbo.vehicle_DF_sap_contract a\n" +
                "\t   LEFT JOIN dbo.vehicle_DF_purchase_order b ON b.purchase_order_no = a.purchase_order_no\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply_detail c ON b.apply_order_no = c.order_no\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND b.work_state_audit = c.work_state_audit\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t   AND ISNULL (c.detail_status, '') <> 'CRM删除'\n" +
                "\t   LEFT JOIN dbo.vehicle_demand_apply d ON c.order_no = d.order_no\n" +
                "\t   LEFT JOIN dbo.sys_stations st ON d.station_id = st.station_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog vmc ON c.vehicle_vno_id = vmc.self_id\n" +
                "\t   LEFT JOIN dbo.base_vehicle_model_catalog_price vmcp ON c.vehicle_vno_id = vmcp.parent_id\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   AND vmcp.station_id = d.station_id\n" +
                "\t   LEFT JOIN ( SELECT *\n" +
                "\t\t\t\t   FROM ( SELECT sap_contract_no, sap_order_no,\n" +
                "\t\t\t\t\t\t\t\t CASE WHEN apply_type IN ( '监控车', '一次性监控' ) THEN '监控' ELSE '现款' END AS apply_type,\n" +
                "\t\t\t\t\t\t\t\t ROW_NUMBER () OVER ( PARTITION BY sap_contract_no ORDER BY sap_contract_no ) AS rowNum\n" +
                "\t\t\t\t\t\t  FROM dbo.vehicle_DF_invoice_apply\n" +
                "\t\t\t\t\t\t  WHERE status NOT LIKE '%拒绝%'\n" +
                "\t\t\t\t\t\t\t  AND status NOT LIKE '%作废%' ) a\n" +
                "\t\t\t\t   WHERE rowNum = 1 ) e ON e.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.finish_status = 1\n" +
                "\t\t\t\t\t\t\t OR a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f ON f.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\t\t\t  INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\t\t\t   WHERE ( a.underpan_no LIKE 'DEL_%' )\n" +
                "\t\t\t\t   GROUP BY b.sap_contract_no ) f1 ON f1.sap_contract_no = a.contract_no\n" +
                "\t   LEFT JOIN ( SELECT a.sap_contract_no, COUNT (*) invoice_qty\n" +
                "\t\t\t\t   FROM dbo.vehicle_DF_invoice_apply a\n" +
                "\t\t\t\t   WHERE a.status IN ( '开票完成' )\n" +
                "\t\t\t\t   GROUP BY a.sap_contract_no ) g ON g.sap_contract_no = a.contract_no\n" +
                "\t   WHERE d.station_id IS NOT NULL\n" +
                "\t\t   AND a.refuse_reason = '有效'\n" +
                "\t\t   AND ( CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0)\n" +
                "\t\t\t\t\t  THEN 0\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t CASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                "\t\t\t\t\t  THEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t ELSE\n" +
                "\t\t\t\t 0\n" +
                "\t\t\t\t END\n" +
                "\t\t\t\t END > 0\n" +
                "\t\t\t\t   OR CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0) > 0\n" +
                "\t\t\t\t\t\t   THEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\t\t THEN ISNULL (a.quantity, 0)\n" +
                "\t\t\t\t\t\t\t\tELSE\n" +
                "\t\t\t\t\t\t\t\tISNULL (g.invoice_qty, 0)\n" +
                "\t\t\t\t\t\t\t\tEND - ISNULL (f.qty, 0)\n" +
                "\t\t\t\t\t  ELSE\n" +
                "\t\t\t\t\t  0\n" +
                "\t\t\t\t\t  END > 0 )\n" +
                "\t\t   AND b.status NOT LIKE '%作废%'\n" +
                "\t\t   AND b.status NOT LIKE '%终止%'\n" +
                "\t\t   AND b.status NOT LIKE '%拒绝%') a\n" +
                "WHERE " + conditionStr;
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        return result.get(0);
    }

    private DetachedCriteria createCriteria(VehicleStockSearchCriteria criterial) {
        DetachedCriteria dc = DetachedCriteria.forClass(VwVehicleStock.class);
        if (criterial != null) {
            String vehicleVno = criterial.getVehicleVno();
            String vehicleName = criterial.getVehicleName();
            String vehicleColor = criterial.getVehicleColor();
            String vehicleVin = criterial.getVehicleVin();
            String vehicleStatus = criterial.getVehicleStatus();
            String warehouseId = criterial.getWareHouseId();
            short vehiclePayType = criterial.getVehiclePayType();
            int stockAgeMin = criterial.getStockAgeMin();
            int stockAgeMax = criterial.getStockAgeMax();
            boolean flag = false;
            // short.class.
            if (vehicleVno != null && vehicleVno.length() > 0) {
                dc.add(Restrictions.eq("vehicleVno", vehicleVno));
                flag = true;
            }
            if (vehicleName != null && vehicleName.length() > 0) {
                dc.add(Restrictions.eq("vehicleName", vehicleName));
                flag = true;
            }
            if (vehicleColor != null && vehicleColor.length() > 0) {
                dc.add(Restrictions.eq("vehicleColor", vehicleColor));
                flag = true;
            }
            if (vehicleVin != null && vehicleVin.length() > 0) {
                dc.add(Restrictions.like("vehicleVin", "%" + vehicleVin + "%"));
                flag = true;
            }
            // add by shichunshan 付款类型
            if (vehiclePayType > 0) {
                dc.add(Restrictions.eq("viPayType", vehiclePayType));
                flag = true;
            }
            // add by shichunshan 车辆状态
            if (vehicleStatus != null && vehicleStatus.length() > 0) {
                // dc.add(Restrictions.eq("realStatus", vehicleStatus));
                dc.add(Restrictions.in("realStatus",
                        getSearchCondtionFromStr(vehicleStatus)));
                flag = true;
            } else if (vehicleStatus == null) {
                dc.add(Restrictions.not(Restrictions.eq("realStatus",
                        "已出库")));
                flag = true;
            }
            // add by shichunshan 仓库id
            if (warehouseId != null && warehouseId.length() > 0) {
                // dc.add(Restrictions.eq("warehouseId", warehouseId));
                dc.add(Restrictions.in("warehouseId",
                        getSearchCondtionFromStr(warehouseId)));
                flag = true;
            }
            if (stockAgeMin > 0) {
                dc.add(Restrictions.ge("stockAge", stockAgeMin));
                flag = true;
            }
            if (stockAgeMax > 0) {
                dc.add(Restrictions.le("stockAge", stockAgeMax));
                flag = true;
            }
            if (criterial.getStationIds() != null) {
                dc.add(Restrictions.in("stationId", criterial.getStationIds()));
            }
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
