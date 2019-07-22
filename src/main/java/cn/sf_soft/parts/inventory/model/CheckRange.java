package cn.sf_soft.parts.inventory.model;

/**
 * 盘点范围-DTO
 * @author caigx
 *
 */
public class CheckRange  implements java.io.Serializable{

    /**
     * 配件仓库ID
     */
    private String warehouseId;

    /**
     * 站点ID
     */
    private String stationId;

    /**
     * 库位
     */
    private String depositPosition;

    /**
     * 抽盘品种数
     */
    private Long checkNum;


    /**
     * 出库日期(开始)
     */
    private String beginDate;

    /**
     * 出库日期(结束)
     */
    private String  endDate;



    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDepositPosition() {
        return depositPosition;
    }

    public void setDepositPosition(String depositPosition) {
        this.depositPosition = depositPosition;
    }

    public Long getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Long checkNum) {
        this.checkNum = checkNum;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
