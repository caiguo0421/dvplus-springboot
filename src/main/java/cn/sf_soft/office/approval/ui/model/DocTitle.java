package cn.sf_soft.office.approval.ui.model;

public class DocTitle {

	private String title;
	// json数组 目前不支持
	private String leftButtons;

	// json数组 目前不支持
	private String rightButtons;
	

	public DocTitle() {

	}

	public DocTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeftButtons() {
		return leftButtons;
	}

	public void setLeftButtons(String leftButtons) {
		this.leftButtons = leftButtons;
	}

	public String getRightButtons() {
		return rightButtons;
	}

	public void setRightButtons(String rightButtons) {
		this.rightButtons = rightButtons;
	}

}
