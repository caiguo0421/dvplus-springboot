package cn.sf_soft.vehicle.purchase.model;

/**
 * 实物状态
 * VehicleResources的枚举
 * @author  caigx
 */
public enum EntityStatus {
    S0((byte) 10, "订单"),
    S1((byte) 20, "在途"),
    S2((byte) 30, "在库"),
    S3((byte) 40, "出库");

    private final byte code;
    private final String name;

    public String getName() {
        return name;
    }

    public byte getCode() {
        return code;
    }

    EntityStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SaleStatus valueOf(byte status){
        switch (status){
            case 10:
                return SaleStatus.S0;
            case 20:
                return SaleStatus.S1;
            case 30:
                return SaleStatus.S2;
            case 40:
                return SaleStatus.S3;
        }
        return null;
    }
}
