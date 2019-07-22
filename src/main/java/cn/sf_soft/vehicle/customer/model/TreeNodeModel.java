package cn.sf_soft.vehicle.customer.model;
/**
 * 树形节点的model
 * @author cw
 * @date 2014-9-12 下午4:12:08
 */
public class TreeNodeModel {
	 private String selId;
	 private String name;
	 private String parentId;
	 public TreeNodeModel(){
		 
	 }
	 public TreeNodeModel(String selId, String name, String parentId) {
		super();
		this.selId = selId;
		this.name = name;
		this.parentId = parentId;
	}
	public String getSelId() {
		return selId;
	}
	public void setSelId(String selId) {
		this.selId = selId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	 
	 
}
