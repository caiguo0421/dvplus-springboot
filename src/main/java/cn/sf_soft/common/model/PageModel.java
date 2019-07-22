package cn.sf_soft.common.model;

import java.util.List;

/**
 * 
 * @创建人 LiuJin
 * @创建时间 2014-8-20 下午4:43:22
 * @修改人 
 * @修改时间
 */
public class PageModel<T> {
	/**
	 * 当前页码
	 */
	private int page;
	/**
	 * 每页记录条数
	 */
	private int perPage;
	/**
	 * 总页数
	 */
	private long totalPage;
	/**
	 * 总记录数
	 */
	private long totalSize;

	private List<T> data;

	private List<Column>columns;

	public PageModel(){

	}

	
	public PageModel(List<T> data){
		setData(data);
	}
	
	public PageModel(List<T> data, int page, int perPage, int totalSize){
		setData(data);
		setPage(page);
		setPerPage(perPage);
		setTotalSize(totalSize);
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public long getTotalPage() {
		return totalPage;
	}
	
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
		if(perPage > 0){
			//计算总页数
			this.totalPage = (totalSize + perPage - 1) / perPage;
		}
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
		if(page == 0){
			//不分页时，totalSize就是data的size
			this.totalSize = data.size();
		}
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
