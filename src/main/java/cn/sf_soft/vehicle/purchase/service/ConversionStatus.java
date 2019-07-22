package cn.sf_soft.vehicle.purchase.service;

/**
 * @Auther: chenbiao
 * @Date: 2018/9/28 10:18
 * @Description:
 */
public enum ConversionStatus {
    UNCONFIRMED((short) 0, "未确认"),
    CONFIRMED((short) 2, "已确认"),
    FORBID((short) 4, "已作废");

    private final short code;
    private final String name;

    public String getName() {
        return name;
    }

    public short getCode() {
        return code;
    }

    ConversionStatus(short code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ConversionStatus valueOf(Short status){
        if(null == status){
            return ConversionStatus.UNCONFIRMED;
        }
        switch (status){
            case (short)0:
                return ConversionStatus.UNCONFIRMED;
            case (short)2:
                return ConversionStatus.CONFIRMED;
            case (short)4:
                return ConversionStatus.FORBID;
        }
        return null;
    }
}
