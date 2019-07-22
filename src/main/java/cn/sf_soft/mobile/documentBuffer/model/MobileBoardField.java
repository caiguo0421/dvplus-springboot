package cn.sf_soft.mobile.documentBuffer.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 面板域
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "mobile_board_field", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "board_id", "field_code" }),
		@UniqueConstraint(columnNames = { "board_id", "sn" }) })
public class MobileBoardField implements java.io.Serializable {

	private static final long serialVersionUID = 3866008952373532927L;
	private Long objId;
	private Long boardId;
	private MobileBoardBuffered board;
	private Long subobjectBoardId;
	private MobileBoardBuffered subobjectBoard;
	private Short sn;
	private String fieldCode;
	private Boolean dynamic = false;
	private Short fieldTypeNo;
	//private String value;
	
	private List<MobileBoardFieldVal> fieldVals = new ArrayList<MobileBoardFieldVal>(0);

	

	// Constructors

	public MobileBoardField() {
	}
	
	public MobileBoardField(MobileBoardBuffered board,String fieldCode,short sn){
		this.board = board;
		this.fieldCode = fieldCode;
		this.sn = sn;
		
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

	@Column(name = "board_id", insertable = false,updatable = false)
	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@Column(name = "subobject_board_id", insertable = false,updatable = false)
	public Long getSubobjectBoardId() {
		return subobjectBoardId;
	}

	public void setSubobjectBoardId(Long subobjectBoardId) {
		this.subobjectBoardId = subobjectBoardId;
	}
	

	@Column(name = "sn", nullable = false)
	public Short getSn() {
		return this.sn;
	}

	public void setSn(Short sn) {
		this.sn = sn;
	}

	@Column(name = "field_code", nullable = false, length = 100)
	public String getFieldCode() {
		return this.fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	@Column(name = "dynamic")
	public Boolean getDynamic() {
		return this.dynamic;
	}

	public void setDynamic(Boolean dynamic) {
		this.dynamic = dynamic;
	}

	@Column(name = "field_type_no")
	public Short getFieldTypeNo() {
		return this.fieldTypeNo;
	}

	public void setFieldTypeNo(Short fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}

//	@Column(name = "value")
//	public String getValue() {
//		return this.value;
//	}
//
//	public void setValue(String value) {
//		this.value = value;
//	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "subobject_board_id",nullable=true)
	public MobileBoardBuffered getSubobjectBoard() {
		return subobjectBoard;
	}

	public void setSubobjectBoard(MobileBoardBuffered subobjectBoard) {
		this.subobjectBoard = subobjectBoard;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	public MobileBoardBuffered getBoard() {
		return board;
	}

	public void setBoard(MobileBoardBuffered board) {
		this.board = board;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "field")
	public List<MobileBoardFieldVal> getFieldVals() {
		return fieldVals;
	}

	public void setFieldVals(List<MobileBoardFieldVal> fieldVals) {
		this.fieldVals = fieldVals;
	}

}
