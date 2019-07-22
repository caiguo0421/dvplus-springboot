package cn.sf_soft.basedata.model;

import com.google.gson.annotations.Expose;



/**
 * SysStations entity. @author MyEclipse Persistence Tools
 */

public class SysStations  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -4589256913045934471L;
	// Fields    
	@Expose
     private String stationId;
	@Expose
     private String stationName;
    
	private String stationGroup;
     private String sameFinanceAll;
     private String sameFinance;
     private String differentFinance;
     private String partPurchaseDefault;


    // Constructors

   
	/** default constructor */
    public SysStations() {
    }

	/** minimal constructor */
    public SysStations(String stationId, String stationName, String stationGroup, String sameFinanceAll, String sameFinance) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationGroup = stationGroup;
        this.sameFinanceAll = sameFinanceAll;
        this.sameFinance = sameFinance;
    }
    
    /** full constructor */
    public SysStations(String stationId, String stationName, String stationGroup, String sameFinanceAll, String sameFinance, String differentFinance) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationGroup = stationGroup;
        this.sameFinanceAll = sameFinanceAll;
        this.sameFinance = sameFinance;
        this.differentFinance = differentFinance;
    }

   
    // Property accessors

    public String getStationId() {
        return this.stationId;
    }
    
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return this.stationName;
    }
    
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationGroup() {
        return this.stationGroup;
    }
    
    public void setStationGroup(String stationGroup) {
        this.stationGroup = stationGroup;
    }

    public String getSameFinanceAll() {
        return this.sameFinanceAll;
    }
    
    public void setSameFinanceAll(String sameFinanceAll) {
        this.sameFinanceAll = sameFinanceAll;
    }

    public String getSameFinance() {
        return this.sameFinance;
    }
    
    public void setSameFinance(String sameFinance) {
        this.sameFinance = sameFinance;
    }

    public String getDifferentFinance() {
        return this.differentFinance;
    }
    
    public void setDifferentFinance(String differentFinance) {
        this.differentFinance = differentFinance;
    }
    
    public String getPartPurchaseDefault() {
		return partPurchaseDefault;
	}

	public void setPartPurchaseDefault(String partPurchaseDefault) {
		this.partPurchaseDefault = partPurchaseDefault;
	}

   


    @Override
   	public String toString() {
   		return "SysStations [stationId=" + stationId + ", stationName="
   				+ stationName + ", stationGroup=" + stationGroup
   				+ ", sameFinanceAll=" + sameFinanceAll + ", sameFinance="
   				+ sameFinance + ", differentFinance=" + differentFinance + "]";
   	}

	

}
