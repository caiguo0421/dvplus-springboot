package cn.sf_soft.vehicle.purchase.model;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/1 14:32
 * @Description: 销售状态
 */
public enum  SaleStatus {
    S0((byte) 0, "未售"),
    S1((byte) 1, "预售"),
    S2((byte) 2, "已定"),
    S3((byte) 3, "已售");

    private final byte code;
    private final String name;

    public String getName() {
        return name;
    }

    public byte getCode() {
        return code;
    }

    SaleStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SaleStatus valueOf(byte status){
        switch (status){
            case 0:
                return SaleStatus.S0;
            case 1:
                return SaleStatus.S1;
            case 2:
                return SaleStatus.S2;
            case 3:
                return SaleStatus.S3;
        }
        return null;
    }
}
