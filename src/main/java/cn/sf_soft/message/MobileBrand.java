package cn.sf_soft.message;

import cn.sf_soft.vehicle.purchase.model.SaleStatus;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/29 11:28
 * @Description:
 */
public enum MobileBrand {

    HUAWEI("huawei"),
    MI("mi"),
    IOS("ios"),
    VIVO("vivo"),
    OPPO("oppo"),
    OTHER("other");

    public String code;
    MobileBrand(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static MobileBrand codeOf(String code){
        if(null == code) {
            return OTHER;
        }
        if("huawei".equals(code)){
            return HUAWEI;
        }else if("mi".equals(code)){
            return MI;
        }else if("ios".equals(code)){
            return IOS;
        }else if("vivo".equals(code)){
            return VIVO;
        }else if("oppo".equals(code)){
            return OPPO;
        }else {
            return OTHER;
        }
    }
}
