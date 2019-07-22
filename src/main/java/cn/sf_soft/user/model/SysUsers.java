package cn.sf_soft.user.model;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.common.util.TimestampAdapter;
import cn.sf_soft.finance.voucher.model.AcctCompanyInfo;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class SysUsers implements java.io.Serializable {

    private static final long serialVersionUID = -3700736403106481398L;
    @Expose
    private String userId;        //用户Id
    private Short status;
    @Expose
    private String userNo;        //用户编码,也是登录名
    @Expose
    private String userName;    //用户的真实姓名
    private String password;    //用户密码
    @Expose
    private String sex;            //性别
    @Expose
    private Timestamp birthday;    //生日
    @Expose
    private String phone;


    private SysUnits institution;
    private String department;
    private Double guarantyLimit;
    private Timestamp pswdModifyTime;

    private Set<SysRoles> roles;//用户角色

    @Expose
    private String unitName;
    @Expose
    private String stationName;
    @Expose
    private String departmentName;
    @Expose
    private String departmentNo;//部门编码
    @Expose
    private List<SysStations> sysStations;//用户所管辖的站点范围

    @Expose
    private List<String> popedomIds;//用户所拥有的权限ID集合
    // Constructors
    @Expose
    private Map<String, String> moduleStations;//权限功能范围, 模块ID-站点字符串(A,B,C...)
    @Expose
    private List<String> moduleIds;//服务端支持的模块ID

    @Expose
    private String avatarUrl;

    private String jpushId;

    private String loginStationId;

    private String loginDepartmentId;


    //默认站点，和PC的规则一致，取登陆部门的站点
    private String defaulStationId;

    private List<String> reportSellerIds;

    public SysUsers() {

    }

    // Property accessors
    public SysUnits getInstitution() {
        return institution;
    }

    public void setInstitution(SysUnits institution) {
        this.institution = institution;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getUserNo() {
        return this.userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getGuarantyLimit() {
        return this.guarantyLimit;
    }

    public void setGuarantyLimit(Double guarantyLimit) {
        this.guarantyLimit = guarantyLimit;
    }


    @XmlJavaTypeAdapter(value = TimestampAdapter.class, type = Timestamp.class)
    public Timestamp getPswdModifyTime() {
        return this.pswdModifyTime;
    }

    public void setPswdModifyTime(Timestamp pswdModifyTime) {
        this.pswdModifyTime = pswdModifyTime;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setRoles(Set<SysRoles> roles) {
        this.roles = roles;
    }

    public Set<SysRoles> getRoles() {
        return roles;
    }

    public void setPopedomIds(List<String> popedomIds) {
        this.popedomIds = popedomIds;
    }

    public List<String> getPopedomIds() {
        return popedomIds;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<SysStations> getSysStations() {
        return sysStations;
    }

    public void setSysStations(List<SysStations> sysStations) {
        this.sysStations = sysStations;
    }

    @Override
    public String toString() {
        return String.format("%s(%s,%s),%s(%s,%s),defaulStationId:%s,loginStationId:%s,loginDepartmentId:%s",
                userName, userId, userNo,
                departmentName, department, departmentNo,
                defaulStationId, loginStationId, loginDepartmentId);
    }

    public List<String> getRuledStationIds() {
        List<String> ruledStationIds = new ArrayList<String>(sysStations.size());
        for (SysStations st : sysStations) {
            ruledStationIds.add(st.getStationId());
        }
        return ruledStationIds;
    }


    public String getUserFullName() {
        return userName + "(" + userNo + ")";

    }

    public AcctCompanyInfo getCompanyInfo() {
        return institution.getCompanyInfo();
    }

    public String getDepartmentNo() {
        return departmentNo;
    }


    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    /**
     * 根据模块ID获得模块的权限功能范围
     *
     * @param moduleId
     * @return
     */
    public String[] getRuledStationIdsByModuleId(String moduleId) {
        if (moduleStations != null) {
            String stationIds = moduleStations.get(moduleId);
            if (stationIds != null && stationIds.length() > 0) {
                return stationIds.split(",");
            }
        }
        return null;
    }

    public Map<String, String> getModuleStations() {
        return moduleStations;
    }

    public void setModuleStations(Map<String, String> moduleStations) {
        this.moduleStations = moduleStations;
    }

    public void setModuleIds(List<String> moduleIds) {
        this.moduleIds = moduleIds;
    }


    public boolean hasPopedom(String popedomId) {
        if (popedomId == null || popedomId.length() == 0) {
            return false;
        }
        for (String popedom : getPopedomIds()) {
            if (popedomId.equals(popedom)) {
                return true;
            }
        }
        return false;
    }

    public String getDefaulStationId() {
        if (StringUtils.isBlank(this.defaulStationId)) {
            return institution.getDefaultStation();
        }
        return this.defaulStationId;
    }

    public void setDefaulStationId(String defaulStationId) {
        this.defaulStationId = defaulStationId;
    }

    public boolean checkPassword(String input, String storage) {
        if (input.equals(storage)) {
            return true;
        }

        try {
            byte[] inputByteArray = input.getBytes("GBK");
            byte[] storageByteArray = storage.getBytes("GBK");
            if (inputByteArray.length < storageByteArray.length) {
                return false;
            }
            int length = inputByteArray.length;
            int delta = 0;
            for (int i = 0; i < length; i++) {
                if (i - delta >= storageByteArray.length) {
                    delta += length - i;
                    break;
                }
                if (inputByteArray[i] != storageByteArray[i - delta]) {
                    delta++;
                }
            }
            return inputByteArray.length == storageByteArray.length + delta;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public String getJpushId() {
        return jpushId;
    }

    public void setJpushId(String jpushId) {
        this.jpushId = jpushId;
    }

    public String getLoginStationId() {
        return loginStationId;
    }

    public void setLoginStationId(String loginStationId) {
        this.loginStationId = loginStationId;
    }

    public List<String> getReportSellerIds() {
        return reportSellerIds;
    }

    public void setReportSellerIds(List<String> reportSellerIds) {
        this.reportSellerIds = reportSellerIds;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLoginDepartmentId() {
        return loginDepartmentId;
    }

    public void setLoginDepartmentId(String loginDepartmentId) {
        this.loginDepartmentId = loginDepartmentId;
    }
}
