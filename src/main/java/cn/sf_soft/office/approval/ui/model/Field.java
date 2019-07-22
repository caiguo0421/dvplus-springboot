package cn.sf_soft.office.approval.ui.model;

import java.io.Serializable;

import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;

public class Field  implements Serializable{
	
	private static final long serialVersionUID = 7851063042995884883L;

	//类型
	protected AppBoardFieldType itemType;

	public AppBoardFieldType getItemType() {
		return itemType;
	}

	public void setItemType(AppBoardFieldType itemType) {
		this.itemType = itemType;
	}

}
