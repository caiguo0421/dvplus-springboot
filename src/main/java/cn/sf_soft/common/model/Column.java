/**
 * @Title: Column.java
 * @Package: cn.sff.a4s.model
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:深圳市天友软件有限公司
 * 
 * @author: caigx
 * @date: 2015-1-28 上午11:16:07
 * @version: V1.0
 */

package cn.sf_soft.common.model;

/**
 * @ClassName: Column
 * @Description: TODO
 * @author: caigx
 * @date: 2015-1-28 上午11:16:07
 *
 */

public class Column {

	public String name;
	public String type;

	public Column() {

	}

	public Column(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
