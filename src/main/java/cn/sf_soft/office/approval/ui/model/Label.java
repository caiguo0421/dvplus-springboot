package cn.sf_soft.office.approval.ui.model;

import java.io.Serializable;

import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;

public class Label extends  Field  {
	
	//在主面板中显示
	private boolean shownOnBaseBoard ;
	//在明细面板中显示
	private boolean shownOnDetailBoard;
	//在从面板中显示
	private boolean shownOnSlaveBoard;
	// 标签
	private String label;

	// 标签文本颜色
	private Integer labelColour;

	// 标签背景
	private Integer labelBackground;

	// 行背景色
	private Integer background;

	// 显示“显示明
	//private boolean displayDetailButton;

	private Board board;

	public Label(){
		this.itemType = AppBoardFieldType.Label;
	}
	
	public Label(String label) {
		this.itemType = AppBoardFieldType.Label;
		this.label = label;
	}

	public boolean isShownOnBaseBoard() {
		return shownOnBaseBoard;
	}

	public void setShownOnBaseBoard(boolean shownOnBaseBoard) {
		this.shownOnBaseBoard = shownOnBaseBoard;
	}

	public boolean isShownOnDetailBoard() {
		return shownOnDetailBoard;
	}

	public void setShownOnDetailBoard(boolean shownOnDetailBoard) {
		this.shownOnDetailBoard = shownOnDetailBoard;
	}

	public boolean isShownOnSlaveBoard() {
		return shownOnSlaveBoard;
	}

	public void setShownOnSlaveBoard(boolean shownOnSlaveBoard) {
		this.shownOnSlaveBoard = shownOnSlaveBoard;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getLabelColour() {
		return labelColour;
	}

	public void setLabelColour(Integer labelColour) {
		this.labelColour = labelColour;
	}

	public Integer getLabelBackground() {
		return labelBackground;
	}

	public void setLabelBackground(Integer labelBackground) {
		this.labelBackground = labelBackground;
	}

	public Integer getBackground() {
		return background;
	}

	public void setBackground(Integer background) {
		this.background = background;
	}

//	public boolean isDisplayDetailButton() {
//		return displayDetailButton;
//	}
//
//	public void setDisplayDetailButton(boolean displayDetailButton) {
//		this.displayDetailButton = displayDetailButton;
//	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	

}
