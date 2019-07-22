package cn.sf_soft.office.approval.dto;

import java.sql.Timestamp;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ResultCode;

/**
 * @Description: 审批成功返回给桌面客户端的数据
 * @author ShiChunshan
 * @date 2015-4-13 上午10:22:08
 * @version V1.0
 */
public class ApproveResult {

	private int rtnCode;
	private String errMsg;
	// private int approveCode;
	// private String approveMsg;
	private short statusNo;
	private String statusName;
	private Timestamp approvedTime;
	private String nextUserID;
	private String nextUserNo;
	private String nextUserName;
	private ApproveResultCode approveResultCode;

	/**
	 * 
	 */
	public ApproveResult() {
		super();
	}

	public ApproveResult(ResultCode resultCode) {
		super();
		this.rtnCode = (short) resultCode.getCode();
		this.errMsg = resultCode.getMessage();
	}

	public ApproveResult(ApproveResultCode approveResultCode) {
		super();
		if (approveResultCode == ApproveResultCode.APPROVE_SUCCESS) {
			this.rtnCode = 0;
			this.errMsg = null;
		} else {
			this.rtnCode = (short) approveResultCode.getCode();
			this.errMsg = approveResultCode.getMessage();
		}

		this.approveResultCode = approveResultCode;
	}

	/**
	 * @param rtnCode
	 * @param errMsg
	 * @param approveCode
	 * @param approveMsg
	 * @param statusNo
	 * @param statusName
	 * @param approvedTime
	 * @param nextUserID
	 * @param nextUserNo
	 * @param nextUserName
	 */
	public ApproveResult(int rtnCode, String errMsg, int approveCode,
			String approveMsg, short statusNo, String statusName,
			Timestamp approvedTime, String nextUserID, String nextUserNo,
			String nextUserName) {
		super();
		this.rtnCode = rtnCode;
		this.errMsg = errMsg;
		this.statusNo = statusNo;
		this.statusName = statusName;
		this.approvedTime = approvedTime;
		this.nextUserID = nextUserID;
		this.nextUserNo = nextUserNo;
		this.nextUserName = nextUserName;
	}

	/**
	 * @return the rtnCode
	 */
	public int getRtnCode() {
		return rtnCode;
	}

	/**
	 * @param rtnCode
	 *            the rtnCode to set
	 */
	public void setRtnCode(int rtnCode) {
		this.rtnCode = rtnCode;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg
	 *            the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the statusNo
	 */
	public short getStatusNo() {
		return statusNo;
	}

	/**
	 * @param statusNo
	 *            the statusNo to set
	 */
	public void setStatusNo(short statusNo) {
		this.statusNo = statusNo;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the approvedTime
	 */
	public Timestamp getApprovedTime() {
		return approvedTime;
	}

	/**
	 * @param timestamp
	 *            the approvedTime to set
	 */
	public void setApprovedTime(Timestamp timestamp) {
		this.approvedTime = timestamp;
	}

	/**
	 * @return the nextUserID
	 */
	public String getNextUserID() {
		return nextUserID;
	}

	/**
	 * @param nextUserID
	 *            the nextUserID to set
	 */
	public void setNextUserID(String nextUserID) {
		this.nextUserID = nextUserID;
	}

	/**
	 * @return the nextUserNo
	 */
	public String getNextUserNo() {
		return nextUserNo;
	}

	/**
	 * @param nextUserNo
	 *            the nextUserNo to set
	 */
	public void setNextUserNo(String nextUserNo) {
		this.nextUserNo = nextUserNo;
	}

	/**
	 * @return the nextUserName
	 */
	public String getNextUserName() {
		return nextUserName;
	}

	/**
	 * @param nextUserName
	 *            the nextUserName to set
	 */
	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}

	public ApproveResultCode getApproveResultCode() {
		return approveResultCode;
	}

}
