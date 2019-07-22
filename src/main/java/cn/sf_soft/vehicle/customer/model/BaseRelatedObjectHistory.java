package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by henry on 2017/12/28.
 */
@Entity
@Table(name = "base_related_object_history", schema = "dbo")
public class BaseRelatedObjectHistory implements Serializable {
    private String historyId;
    private String objectId;
    private String fieldName;
    private String fieldNameMeaning;
    private String valueBefore;
    private String valueAfter;
    private String modifier;
    private Timestamp modifyTime;
    private String stationId;

    @Id
    @Column(name = "history_id", nullable = false, length = 40)
    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "object_id", nullable = false, length = 40)
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "field_name", nullable = true, length = 30)
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "field_name_meaning", nullable = true, length = 10)
    public String getFieldNameMeaning() {
        return fieldNameMeaning;
    }

    public void setFieldNameMeaning(String fieldNameMeaning) {
        this.fieldNameMeaning = fieldNameMeaning;
    }

    @Basic
    @Column(name = "value_before", nullable = true, length = 60)
    public String getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore;
    }

    @Basic
    @Column(name = "value_after", nullable = true, length = 2147483647)
    public String getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(String valueAfter) {
        this.valueAfter = valueAfter;
    }

    @Basic
    @Column(name = "modifier", nullable = true, length = 20)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "modify_time", nullable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 40)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseRelatedObjectHistory that = (BaseRelatedObjectHistory) o;

        if (historyId != null ? !historyId.equals(that.historyId) : that.historyId != null) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (fieldName != null ? !fieldName.equals(that.fieldName) : that.fieldName != null) return false;
        if (fieldNameMeaning != null ? !fieldNameMeaning.equals(that.fieldNameMeaning) : that.fieldNameMeaning != null)
            return false;
        if (valueBefore != null ? !valueBefore.equals(that.valueBefore) : that.valueBefore != null) return false;
        if (valueAfter != null ? !valueAfter.equals(that.valueAfter) : that.valueAfter != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId != null ? historyId.hashCode() : 0;
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (fieldNameMeaning != null ? fieldNameMeaning.hashCode() : 0);
        result = 31 * result + (valueBefore != null ? valueBefore.hashCode() : 0);
        result = 31 * result + (valueAfter != null ? valueAfter.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        return result;
    }
}
