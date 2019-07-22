package cn.sf_soft.vehicle.purchase.service;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/2 12:24
 * @Description:
 */
public enum ConfirmStatus {
    CONFIRMED((byte) 1, "已确认"),
    UNCONFIRMED((byte) 0, "未确认"),
    FORBID((byte) 2, "已作废");

    private final byte code;
    private final String name;

    public String getName() {
        return name;
    }

    public byte getCode() {
        return code;
    }

    ConfirmStatus(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ConfirmStatus valueOf(Byte status){
        if(null == status){
            return ConfirmStatus.UNCONFIRMED;
        }
        switch (status){
            case 0:
                return ConfirmStatus.UNCONFIRMED;
            case 1:
                return ConfirmStatus.CONFIRMED;
            case 2:
                return ConfirmStatus.FORBID;
        }
        return null;
    }
}
