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
 * 面板标题
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "mobile_board_title")
public class MobileBoardTitle implements java.io.Serializable {

	private static final long serialVersionUID = -1758758576160480425L;
	private Long objId;
	private Long boardId;
	private MobileBoardBuffered board;
	private String popedomId;
	private String baseBoardTitle;
	private String detailBoardTitle;
	private String slaveBoardTitle;

	// Constructors
	public MobileBoardTitle() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id", nullable = false)
	public MobileBoardBuffered getBoard() {
		return board;
	}

	public void setBoard(MobileBoardBuffered board) {
		this.board = board;
	}
	
	@Column(name = "popedom_id")
	public String getPopedomId() {
		return popedomId;
	}

	public void setPopedomId(String popedomId) {
		this.popedomId = popedomId;
	}


	@Column(name = "base_board_title")
	public String getBaseBoardTitle() {
		return baseBoardTitle;
	}

	public void setBaseBoardTitle(String baseBoardTitle) {
		this.baseBoardTitle = baseBoardTitle;
	}

	@Column(name = "slave_board_title")
	public String getSlaveBoardTitle() {
		return slaveBoardTitle;
	}

	public void setSlaveBoardTitle(String slaveBoardTitle) {
		this.slaveBoardTitle = slaveBoardTitle;
	}

	@Column(name = "detail_board_title")
	public String getDetailBoardTitle() {
		return this.detailBoardTitle;
	}

	public void setDetailBoardTitle(String detailBoardTitle) {
		this.detailBoardTitle = detailBoardTitle;
	}

	
	
}
