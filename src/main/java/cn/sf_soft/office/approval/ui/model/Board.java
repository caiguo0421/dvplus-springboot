package cn.sf_soft.office.approval.ui.model;

import java.io.Serializable;
import java.util.List;

public class Board implements Serializable{
	
	//可关
	private boolean collapsable = true;
	
	//缺省是否展开
	private boolean defaultExpanded = false;
	
	//主面板标
	//空：不显示面板标题行
	private String mainBoardTitle;
	
	//明细面板标题
	//缺省和mainPanneltitle
	private String detailBoardTitle;
	
	//字段数据
	private List<Field> fields;
	
	//主对象面
	private Board parent;
	
	//是否是子对象，为了与Board区分
	private boolean isSubObject = true;
	
	//是否是主对象
	private boolean isRoot = false;
	

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

	public String getMainBoardTitle() {
		return mainBoardTitle;
	}

	public void setMainBoardTitle(String mainBoardTitle) {
		this.mainBoardTitle = mainBoardTitle;
	}

	public String getDetailBoardTitle() {
		return detailBoardTitle;
	}

	public void setDetailBoardTitle(String detailBoardTitle) {
		this.detailBoardTitle = detailBoardTitle;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Board getParent() {
		return parent;
	}

	public void setParent(Board parent) {
		this.parent = parent;
	}

	public boolean isSubObject() {
		return isSubObject;
	}

	public void setSubObject(boolean isSubObject) {
		this.isSubObject = isSubObject;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
}
