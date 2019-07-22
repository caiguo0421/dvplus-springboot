package cn.sf_soft.office.approval.ui.model;

import java.io.Serializable;

/**
 * 关闭按钮
 * @author lenovo
 *
 */
public class CollapseButton implements Serializable{

	//在主面板中显示
	private boolean shownOnBaseBoard;
	
	//在明细面板中显示
	private boolean shownOnDetailBoard;
	
	//在从面板中显示
	private boolean shownOnSlaveBoard;

	public CollapseButton(){
		super();
	}
	
	/**
	 * 
	 * @param shownOnBaseBoard  在主面板中显示
	 * @param shownOnDetailBoard 在明细面板中显示
	 * @param shownOnSlaveBoard 在从面板中显示
	 */
	public CollapseButton(boolean shownOnBaseBoard, boolean shownOnDetailBoard, boolean shownOnSlaveBoard) {
		super();
		this.shownOnBaseBoard = shownOnBaseBoard;
		this.shownOnDetailBoard = shownOnDetailBoard;
		this.shownOnSlaveBoard = shownOnSlaveBoard;
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
	
}
