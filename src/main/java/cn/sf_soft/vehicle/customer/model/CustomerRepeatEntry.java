package cn.sf_soft.vehicle.customer.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 意向客户重复
 */
public class CustomerRepeatEntry {

    /**
     * 客户名称
     */
    public static final short CODE_OBJECT_NAME = 100;

    /**
     * 电话
     */
    public static final short CODE_MOBILE = 200;

    /**
     * 简称
     */
    public static final short CODE_SHORT_NAME = 300;

    /**
     * 证件号
     */
    public static final short CODE_CER_NO = 400;


    private Short code;

    private String reason;

    private Map<String, Object> reasonItem;

    private String msg;

    //是否需要加载客户
    private boolean needLoadCustomer = true;

    //客户ID
    private String objectId;


    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, Object> getReasonItem() {
        return reasonItem;
    }

    public void setReasonItem(Map<String, Object> reasonItem) {
        this.reasonItem = reasonItem;
    }

    public boolean getNeedLoadCustomer() {
        return needLoadCustomer;
    }

    public void setNeedLoadCustomer(boolean needLoadCustomer) {
        this.needLoadCustomer = needLoadCustomer;
    }

    /**
     * @param showErrorMsg true:显示错误信息,false:显示加载信息
     * @return
     */
    public String getMsg(boolean showErrorMsg) {
        if (StringUtils.isNotEmpty(msg)) {
            return msg;
        }
        if (code == null || reasonItem == null) {
            return null;
        }

        String maintainerItem = "";
        if (StringUtils.isBlank((String) reasonItem.get("maintainer_name"))) {
            maintainerItem = "目前没有人维系";
        } else {
            maintainerItem = String.format("由[%s]维系", reasonItem.get("maintainer_name"));
        }

        switch (code) {
            case CODE_OBJECT_NAME:
                msg = String.format("客户名称重复，%s %s", maintainerItem, showErrorMsg ? "" : "，您需要加载该客户信息吗?");
                break;
            case CODE_MOBILE:
                if (showErrorMsg) {
                    if (needLoadCustomer) {
                        msg = String.format("系统中已存在电话号码相同的客户[%s]，%s", reasonItem.get("object_name"), maintainerItem);
                    } else {
                        msg = "";//不提示错误信息
                    }
                } else {
                    msg = String.format("系统中已存在电话号码相同的客户[%s]，%s，%s", reasonItem.get("object_name"), maintainerItem, needLoadCustomer ? "您需要加载该客户信息吗？" : "是否继续保存？");
                }
                break;
            case CODE_SHORT_NAME:
                msg = String.format("客户简称不允许重复！\n" +
                                "客户名称：%s\n" +
                                "客户简称：%s\n" +
                                "联系电话：%s\n" +
                                "证件号码：%s\n" +
                                "建档人：%s\n" +
                                "%s", reasonItem.get("object_name"), reasonItem.get("short_name"), reasonItem.get("mobile"), reasonItem.get("certificate_no"), reasonItem.get("creator"),
                        showErrorMsg ? "" : "您需要加载该客户的信息吗?");
                break;
            case CODE_CER_NO:
                msg = String.format("证件号码不允许重复！\n" +
                                "客户名称：%s\n" +
                                "客户简称：%s\n" +
                                "联系电话：%s\n" +
                                "证件号码：%s\n" +
                                "建档人：%s\n" +
                                "%s", reasonItem.get("object_name"), reasonItem.get("short_name"), reasonItem.get("mobile"), reasonItem.get("certificate_no"), reasonItem.get("creator"),
                        showErrorMsg ? "" : "您需要加载该客户的信息吗?");

                break;
            default:
                break;
        }

        return msg;
    }


    public String getObjectId() {
        if (!needLoadCustomer || reasonItem == null) {
            return null;
        }
        return (String) reasonItem.get("object_id");
    }


    public boolean isEmpty() {
        return code == null;
    }


}
