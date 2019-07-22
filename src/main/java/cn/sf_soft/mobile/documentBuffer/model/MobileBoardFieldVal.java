package cn.sf_soft.mobile.documentBuffer.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 域值
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "mobile_board_field_val")
public class MobileBoardFieldVal implements java.io.Serializable {

	private static final long serialVersionUID = -1758758576160480425L;
	private Long objId;
	private Long fieldId;
	private MobileBoardField field;
	private String popedomId;
	private String value;
	

	// Constructors
	public MobileBoardFieldVal() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "obj_id", unique = true, nullable = false)
	public Long getObjId() {
		return this.objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	
	@Column(name = "field_id", insertable = false,updatable =false)
	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", nullable = false)
	public MobileBoardField getField() {
		return field;
	}

	public void setField(MobileBoardField field) {
		this.field = field;
	}

	@Column(name = "popedom_id")
	public String getPopedomId() {
		return popedomId;
	}

	public void setPopedomId(String popedomId) {
		this.popedomId = popedomId;
	}

	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
