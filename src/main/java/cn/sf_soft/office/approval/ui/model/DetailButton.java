package cn.sf_soft.office.approval.ui.model;

public class DetailButton {

	// 在主面板中显示
	private boolean shownOnBaseBoard;

	// 在从面板中显示
	private boolean shownOnSlaveBoard;

	/**
	 * 点击从面板明细链接后的显示方式。 default：true：显示明细面板； false：显示基本面板
	 */
	private boolean detailBoardShown = true;
	
	public DetailButton(){
		super();
	}

	public DetailButton(boolean shownOnBaseBoard, boolean shownOnSlaveBoard, boolean detailBoardShown) {
		super();
		this.shownOnBaseBoard = shownOnBaseBoard;
		this.shownOnSlaveBoard = shownOnSlaveBoard;
		this.detailBoardShown = detailBoardShown;
	}
	
	

	public DetailButton(boolean shownOnBaseBoard, boolean shownOnSlaveBoard) {
		super();
		this.shownOnBaseBoard = shownOnBaseBoard;
		this.shownOnSlaveBoard = shownOnSlaveBoard;
	}



	public boolean isShownOnBaseBoard() {
		return shownOnBaseBoard;
	}

	public void setShownOnBaseBoard(boolean shownOnBaseBoard) {
		this.shownOnBaseBoard = shownOnBaseBoard;
	}

	public boolean isShownOnSlaveBoard() {
		return shownOnSlaveBoard;
	}

	public void setShownOnSlaveBoard(boolean shownOnSlaveBoard) {
		this.shownOnSlaveBoard = shownOnSlaveBoard;
	}

	public boolean isDetailBoardShown() {
		return detailBoardShown;
	}

	public void setDetailBoardShown(boolean detailBoardShown) {
		this.detailBoardShown = detailBoardShown;
	}

}
