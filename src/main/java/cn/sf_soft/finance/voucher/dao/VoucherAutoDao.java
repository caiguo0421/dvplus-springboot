package cn.sf_soft.finance.voucher.dao;

/**
 * @Description: 凭证生成操作类
 * @author scs@sf-soft.cn
 * @date 2015-5-18 上午9:41:10
 */

public interface VoucherAutoDao {
	public boolean generateVoucherByProc(String sql, String tno, boolean isSAB,
			String userID, String documentNo);
}
