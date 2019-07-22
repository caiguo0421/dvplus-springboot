package cn.sf_soft.office.approval.ui.model;

import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;

public class Property extends Field {

	private static final long serialVersionUID = -2106924191090869992L;

	// 标签
	private String label;

	// 值
	private String value;

	// 显示方式
	//private int displayManner;

	// 标签文本颜色
	private Integer labelColour;

	// 标签背景
	private Integer labelBackground;

	// 值文本颜
	private Integer valueColour;

	// 值背景色
	private Integer valueBackground;

	// 行背景色
	private Integer background;
	
	//在主面板中显示
	private boolean shownOnBaseBoard;
	
	//在明细面板中显示
	private boolean shownOnDetailBoard;
	
	//在从面板中显示
	private boolean shownOnSlaveBoard;


	public boolean getShownOnBaseBoard() {
		return shownOnBaseBoard;
	}

	public void setShownOnBaseBoard(boolean shownOnBaseBoard) {
		this.shownOnBaseBoard = shownOnBaseBoard;
	}

	public boolean getShownOnDetailBoard() {
		return shownOnDetailBoard;
	}

	public void setShownOnDetailBoard(boolean shownOnDetailBoard) {
		this.shownOnDetailBoard = shownOnDetailBoard;
	}

	public boolean getShownOnSlaveBoard() {
		return shownOnSlaveBoard;
	}

	public void setShownOnSlaveBoard(boolean shownOnSlaveBoard) {
		this.shownOnSlaveBoard = shownOnSlaveBoard;
		if(shownOnSlaveBoard){ //slave<=Base<=Detail
			this.shownOnBaseBoard = true;
			this.shownOnDetailBoard = true;
		}
	}

	public Integer getValueBackground() {
		return valueBackground;
	}

	public Property() {
		this.itemType = AppBoardFieldType.Property;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

//	public int getDisplayManner() {
//		return displayManner;
//	}
//
//	public void setDisplayManner(int displayManner) {
//		this.displayManner = displayManner;
//	}

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

	public Integer getValueColour() {
		return valueColour;
	}

	public void setValueColour(Integer valueColour) {
		this.valueColour = valueColour;
	}

	public Integer getValueBackgaound() {
		return valueBackground;
	}

	public void setValueBackground(Integer valueBackground) {
		this.valueBackground = valueBackground;
	}

	public Integer getBackground() {
		return background;
	}

	public void setBackground(Integer background) {
		this.background = background;
	}
}
