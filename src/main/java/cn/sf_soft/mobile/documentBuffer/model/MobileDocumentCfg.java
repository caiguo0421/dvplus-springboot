package cn.sf_soft.mobile.documentBuffer.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mobile_document_cfg")
public class MobileDocumentCfg implements java.io.Serializable {

	private static final long serialVersionUID = -907731487517299984L;
	private Integer objId;
	private Integer syClassId;
	private String moduleId;
	private Short docTypeNo;
	private String bufferCalcClass;
	private String bufferCalcObject;
	private Long versionNo;
	
	// Constructors
	public MobileDocumentCfg() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "obj_id", unique = true, nullable = false)
	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	@Column(name = "sy_class_id")
	public Integer getSyClassId() {
		return this.syClassId;
	}

	public void setSyClassId(Integer syClassId) {
		this.syClassId = syClassId;
	}

	@Column(name = "module_id")
	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "buffer_calc_class")
	public String getBufferCalcClass() {
		return this.bufferCalcClass;
	}

	public void setBufferCalcClass(String bufferCalcClass) {
		this.bufferCalcClass = bufferCalcClass;
	}

	@Column(name = "buffer_calc_object")
	public String getBufferCalcObject() {
		return this.bufferCalcObject;
	}

	public void setBufferCalcObject(String bufferCalcObject) {
		this.bufferCalcObject = bufferCalcObject;
	}

	@Column(name = "version_no")
	public Long getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "doc_type_no")
	public Short getDocTypeNo() {
		return docTypeNo;
	}

	public void setDocTypeNo(Short docTypeNo) {
		this.docTypeNo = docTypeNo;
	}

}
