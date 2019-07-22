package cn.sf_soft.office.postaudit.model;

import java.util.List;

/**
 * 事后审核初始化数据
 * @author king
 * @create 2013-9-25上午11:09:11
 */
public class PostAuditInitData {

	private List<String> handleResult;

	public List<String> getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(List<String> handleResult) {
		this.handleResult = handleResult;
	}

	@Override
	public String toString() {
		return "PostAuditInitData [handleResult=" + handleResult + "]";
	}
	
	
}
