package cn.sf_soft.office.approval.ui.model;

public class BoardTitle {

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 可关闭
	 */
	private boolean collapsable = true;
	
	
	/**
	 * 缺省是否展开
	 */
	private boolean defaultExpanded = true;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isCollapsable() {
		return collapsable;
	}
	public void setCollapsable(boolean collapsable) {
		this.collapsable = collapsable;
	}
	public boolean isDefaultExpanded() {
		return defaultExpanded;
	}
	public void setDefaultExpanded(boolean defaultExpanded) {
		this.defaultExpanded = defaultExpanded;
	}
	
	
	
}
