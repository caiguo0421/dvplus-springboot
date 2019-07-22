package cn.sf_soft.common.model;

import com.google.gson.annotations.Expose;

/**
 * 服务端返回的数据,一般情况下，只需定义ret、msg、data的值即可
 * @author king
 * @creation 2013-5-8
 * @param <T>
 */
public class ResponseMessage<T> {

	/**
	 * 成功
	 */
	public static final short RET_SUCC 			= 0;
	/**
	 * 参数错误
	 */
	public static final short RET_PARAM_ERR 	= 1;
	/**
	 * 鉴权失败
	 */
	public static final short RET_NO_POPEDOM 	= 2;
	/**
	 * 服务器内部错误
	 */
	public static final short RET_SERVER_ERR 	= 3;
	/**
	 * 不可操作
	 */
	public static final short RET_OPERATE_UNAVAILABLE = 4;
	
	/**	返回错误码
	 */
	@Expose
	private short ret;
	/**
	 * 二级错误码：0:正常
	 */
	private int errcode;
	/**
	 * 响应信息 ，正常时为“ok”，或者错误信息
	 */
	@Expose
	private String msg;
	/**
	 * 分页加载数据时，当前是第几页，起始值为1
	 */
	@Expose
	private int pageNo;
	/**
	 * 分页加载数据时，数据的总条数
	 */
	@Expose
	private long totalSize;
	/**
	 * 分页加载数据时，每页数据的条数
	 */
	@Expose
	private int pageSize;
	@Expose
	private T data;
	/**
	 * 附加信息，根据实际情况可传递额外的一些数据
	 */
	@Expose
	private Object attachedObj;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getRet() {
		return ret;
	}
	public void setRet(short ret) {
		this.ret = ret;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public T getData() {
		return data;
	}
	
	
	public void setData(T data) {
		this.data = data;
	}
	
	public ResponseMessage(){
		this.ret = 0;
		this.errcode = 0;
		this.msg = "ok";
	}
	
	
	/**
	 * @param ret
	 * @param errcode
	 * @param msg
	 */
	public ResponseMessage(Short ret,int errcode, String msg) {
		super();
		this.ret = ret;
		this.errcode = errcode;
		this.msg = msg;
	}
	public ResponseMessage(String msg){
		this.ret = 0;
		this.errcode = 0;
		this.msg = msg;
	}
	public ResponseMessage(T t){
		this.data = t;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Object getAttachedObj() {
		return attachedObj;
	}
	public void setAttachedObj(Object attachedObj) {
		this.attachedObj = attachedObj;
	}
	@Override
	public String toString() {
		return "ResponseMessage [ret=" + ret + ", errcode=" + errcode
				+ ", msg=" + msg + ", pageNo=" + pageNo + ", totalSize="
				+ totalSize + ", pageSize=" + pageSize + ", data=" + data
				+ ", attachedObj=" + attachedObj + "]";
	}
	
}
