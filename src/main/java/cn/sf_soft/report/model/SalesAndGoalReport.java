package cn.sf_soft.report.model;

/**
 * Created by henry on 17-5-10.
 */
public class SalesAndGoalReport {
    private String stationId;
    private String stationName;
    private Integer salesVolumeMonth;
    private Integer salesVolumeYear;
    private Integer targetYear;
    private Double contributionRate;
    private Double completionRate;
    private Integer rankingYear;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getSalesVolumeMonth() {
        return salesVolumeMonth;
    }

    public void setSalesVolumeMonth(Integer salesVolumeMonth) {
        this.salesVolumeMonth = salesVolumeMonth;
    }

    public Integer getSalesVolumeYear() {
        return salesVolumeYear;
    }

    public void setSalesVolumeYear(Integer salesVolumeYear) {
        this.salesVolumeYear = salesVolumeYear;
    }

    public Integer getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(Integer targetYear) {
        this.targetYear = targetYear;
    }

    public Double getContributionRate() {
        return contributionRate;
    }

    public void setContributionRate(Double contributionRate) {
        this.contributionRate = contributionRate;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Integer getRankingYear() {
        return rankingYear;
    }

    public void setRankingYear(Integer rankingYear) {
        this.rankingYear = rankingYear;
    }
}
