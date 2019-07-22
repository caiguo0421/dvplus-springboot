package cn.sf_soft.mobile.documentBuffer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 面板数据
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "mobile_board_buffered")
public class MobileBoardBuffered implements java.io.Serializable {

	private static final long serialVersionUID = -1758758576160480425L;
	private Long objId;
//	private String classCode;
//	private String objectId;
	
	private String docTitle;
//	private String baseBoardTitle;
//	private String detailBoardTitle;
//	private String slaveBoardTitle;
	
	private List<MobileBoardField> fields = new ArrayList<MobileBoardField>(0);
	private List<MobileBoardTitle> titles = new ArrayList<MobileBoardTitle>(0);
	
	private MobileDocumentBuffered document;

	// Constructors
	public MobileBoardBuffered() {
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

//	@Column(name = "class_code", length = 40)
//	public String getClassCode() {
//		return this.classCode;
//	}
//
//	public void setClassCode(String classCode) {
//		this.classCode = classCode;
//	}
//	
//	@Column(name = "object_id", length = 40)
//	public String getObjectId() {
//		return objectId;
//	}
//
//	public void setObjectId(String objectId) {
//		this.objectId = objectId;
//	}


	@Column(name = "doc_title")
	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

//	@Column(name = "base_board_title")
//	public String getBaseBoardTitle() {
//		return baseBoardTitle;
//	}
//
//	public void setBaseBoardTitle(String baseBoardTitle) {
//		this.baseBoardTitle = baseBoardTitle;
//	}
//
//	@Column(name = "slave_board_title")
//	public String getSlaveBoardTitle() {
//		return slaveBoardTitle;
//	}
//
//	public void setSlaveBoardTitle(String slaveBoardTitle) {
//		this.slaveBoardTitle = slaveBoardTitle;
//	}
//
//	@Column(name = "detail_board_title")
//	public String getDetailBoardTitle() {
//		return this.detailBoardTitle;
//	}
//
//	public void setDetailBoardTitle(String detailBoardTitle) {
//		this.detailBoardTitle = detailBoardTitle;
//	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "board")
	public List<MobileBoardField> getFields() {
		return this.fields;
	}

	public void setFields(List<MobileBoardField> mobileBoardFieldsForBoardId) {
		this.fields = mobileBoardFieldsForBoardId;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "board")
	public MobileDocumentBuffered getDocument() {
		return document;
	}

	public void setDocument(MobileDocumentBuffered document) {
		this.document = document;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "board")
	public List<MobileBoardTitle> getTitles() {
		return titles;
	}

	public void setTitles(List<MobileBoardTitle> titles) {
		this.titles = titles;
	}

	
}
