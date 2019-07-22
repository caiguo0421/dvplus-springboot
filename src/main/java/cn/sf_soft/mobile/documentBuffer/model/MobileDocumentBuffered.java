package cn.sf_soft.mobile.documentBuffer.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * App文档缓存数据
 * @author caigx
 *
 */
@Entity
@Table(name = "mobile_document_buffered")
public class MobileDocumentBuffered implements java.io.Serializable {

	private static final long serialVersionUID = -907731487517299984L;
	private Long objId;
	private Integer documentCfgId;
	private String busiBillId;
	private String busiBillVersion;
	private String bufferCalcVersion;
	private Timestamp computeTime;
	private Long boardId;
	private Long versionNo;
	
	private MobileBoardBuffered board;
	private MobileDocumentCfg mobileDocumentCfg;
	private Object busiBill;
	private Object[] relatedObjects;

	// Constructors

	public MobileDocumentBuffered() {
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

	@Column(name = "busi_bill_id")
	public String getBusiBillId() {
		return this.busiBillId;
	}

	public void setBusiBillId(String busiBillId) {
		this.busiBillId = busiBillId;
	}

	@Column(name = "buffer_calc_version", nullable = false)
	public String getBufferCalcVersion() {
		return this.bufferCalcVersion;
	}

	public void setBufferCalcVersion(String bufferCalcVersion) {
		this.bufferCalcVersion = bufferCalcVersion;
	}

	@Column(name = "compute_time")
	public Timestamp getComputeTime() {
		return this.computeTime;
	}

	public void setComputeTime(Timestamp computeTime) {
		this.computeTime = computeTime;
	}

	@Column(name = "board_id", insertable = false,updatable = false)
	public Long getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@Column(name = "version_no")
	public Long getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "busi_bill_version")
	public String getBusiBillVersion() {
		return busiBillVersion;
	}

	public void setBusiBillVersion(String busiBillVersion) {
		this.busiBillVersion = busiBillVersion;
	}

	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "board_id")
	public MobileBoardBuffered getBoard() {
		return board;
	}

	public void setBoard(MobileBoardBuffered board) {
		this.board = board;
	}

	@Column(name = "document_cfg_id", insertable = false, updatable = false)
	public Integer getDocumentCfgId() {
		return documentCfgId;
	}

	public void setDocumentCfgId(Integer documentCfgId) {
		this.documentCfgId = documentCfgId;
	}

	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "document_cfg_id")
	public MobileDocumentCfg getMobileDocumentCfg() {
		return mobileDocumentCfg;
	}

	public void setMobileDocumentCfg(MobileDocumentCfg mobileDocumentCfg) {
		this.mobileDocumentCfg = mobileDocumentCfg;
	}

	@Transient
	public Object getBusiBill() {
		return busiBill;
	}

	public void setBusiBill(Object busiBill) {
		this.busiBill = busiBill;
	}

	@Transient
	public Object[] getRelatedObjects() {
		return relatedObjects;
	}

	public void setRelatedObjects(Object[] relatedObjects) {
		this.relatedObjects = relatedObjects;
	}
}
