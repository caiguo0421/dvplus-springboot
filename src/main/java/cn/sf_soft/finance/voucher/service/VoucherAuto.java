package cn.sf_soft.finance.voucher.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.dao.SysIdentityDao;
import cn.sf_soft.common.Config;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.DataHelper;
import cn.sf_soft.finance.voucher.dao.AcctPeriodDao;
import cn.sf_soft.finance.voucher.dao.AcctVoucherNoDao;
import cn.sf_soft.finance.voucher.dao.AcctVoucherTemplateDao;
import cn.sf_soft.finance.voucher.dao.VoucherAutoDao;
import cn.sf_soft.finance.voucher.model.AcctAccount;
import cn.sf_soft.finance.voucher.model.AcctItem;
import cn.sf_soft.finance.voucher.model.AcctItemClass;
import cn.sf_soft.finance.voucher.model.AcctItemDetailH;
import cn.sf_soft.finance.voucher.model.AcctItemDetailV;
import cn.sf_soft.finance.voucher.model.AcctPeriod;
import cn.sf_soft.finance.voucher.model.AcctVoucher;
import cn.sf_soft.finance.voucher.model.AcctVoucherEntry;
import cn.sf_soft.finance.voucher.model.AcctVoucherNo;
import cn.sf_soft.finance.voucher.model.AcctVoucherNoId;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplate;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplateBusiness;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplateClass;
import cn.sf_soft.finance.voucher.model.AcctVoucherTemplateD;
import cn.sf_soft.finance.voucher.model.AcctVoucherW;
import cn.sf_soft.user.model.SysUsers;

/**
 * 自动产生凭证(机制凭证)
 * 
 * @创建人 king
 * @创建时间 2014-4-10 下午3:33:20
 * @修改人
 * @修改时间
 */
@Service("voucherAuto")
public class VoucherAuto {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VoucherAuto.class);
	@Autowired
	private AcctVoucherTemplateDao voucherTemplateDao;
	@Autowired
	private AcctPeriodDao acctPeriodDao;
	@Autowired
	private AcctVoucherNoDao voucherNoDao;
	@Autowired
	private SysIdentityDao sysIdentityDao;
	@Autowired
	private Config config;
	@Autowired
	private VoucherAutoDao voucherAutoDao;

	// -----------setter----------
	public void setVoucherTemplateDao(AcctVoucherTemplateDao voucherTemplateDao) {
		this.voucherTemplateDao = voucherTemplateDao;
	}

	public void setAcctPeriodDao(AcctPeriodDao acctPeriodDao) {
		this.acctPeriodDao = acctPeriodDao;
	}

	public void setVoucherNoDao(AcctVoucherNoDao voucherNoDao) {
		this.voucherNoDao = voucherNoDao;
	}

	public void setSysIdentityDao(SysIdentityDao sysIdentityDao) {
		this.sysIdentityDao = sysIdentityDao;
	}

	// ------------business---------
	/**
	 * 自动产生凭证的通用流程
	 * 
	 * @param dataSourceSql
	 *            数据源SQL
	 * @param templateNo
	 *            凭证模板编号
	 * @param isSAB
	 *            是否冲红
	 * @param userInfo
	 *            当前登录用户
	 * @return
	 */
	public boolean generateVoucher(String dataSourceSql, String templateNo,
			boolean isSAB, SysUsers userInfo) {
		if (!config.isAutoVoucherOn()) {
			return true;
		}

		List<Map<String, Object>> dataSource = voucherTemplateDao.getMapBySQL(
				dataSourceSql, null);

		if (dataSource == null || dataSource.size() == 0) {
			throw new ServiceException(formatTipMsg("数据源为空!"));
		}

		// 获取当前登录用户的帐套ID
		int companyId = userInfo.getCompanyInfo().getTcompanyId();

		AcctVoucherTemplate template = voucherTemplateDao
				.getTemplateByNo(templateNo);
		if (template == null) {
			throw new ServiceException(formatTipMsg("配置模版不存在!"));
		}

		// 凭证模板未启用，则直接返回TRUE
		if (!template.getTstatus()) {
			logger.info("===凭证模板未启用===");
			return true;
		}

		// 获取帐套凭证字
		AcctVoucherW voucherW = null;
		Set<AcctVoucherW> voucherWs = template.getVoucherWs();
		if (voucherWs == null) {
			throw new ServiceException(formatTipMsg("帐套凭证字没有配置!"));
		}
		for (AcctVoucherW w : voucherWs) {
			if (w.getTcompanyId() == companyId) {
				voucherW = w;
				break;
			}
		}

		if (voucherW == null) {
			throw new ServiceException(formatTipMsg("凭证字不存在!"));
		}

		Set<AcctVoucherTemplateD> templateDetails = template
				.getVoucherTemplateDetails();
		if (templateDetails == null || templateDetails.size() == 0) {
			throw new ServiceException(formatTipMsg("凭证模板未配置明细!"));
		}

		String documentNoField = templateDetails.iterator().next()
				.getTdocumnetNoField();

		String documentNo = (String) dataSource.get(0).get(documentNoField);

		// ==========生成凭证主分录========
		AcctVoucher voucher = generateVoucherEnter(template, documentNo,
				voucherW.getTvoucherWId(), userInfo);
		if (voucher == null) {
			throw new ServiceException("凭证主分录生成失败!");
		}

		// ========生成凭证明细分录========
		// 凭证主分录ID
		Integer voucherId = voucher.getTvoucherId();
		List<AcctVoucherEntry> voucherEntries = generateVoucherDetail(
				dataSourceSql, isSAB, userInfo, dataSource, template, voucherId);

		boolean succ = validateVoucherAmount(voucherEntries);
		if (!succ) {
			return false;
		}
		voucher.setVoucherEntries(voucherEntries);
		logger.debug(voucher.toString());
		Serializable s = voucherTemplateDao.save(voucher);

		// 生成业务单与财务凭证单关联关系的记录
		AcctVoucherTemplateBusiness templateBusiness = new AcctVoucherTemplateBusiness();
		templateBusiness.setTvoucherId(-voucherId);
		templateBusiness.setTno(templateNo);
		templateBusiness.setBusinessNo(documentNo);
		templateBusiness
				.setCreateTime(new Timestamp(System.currentTimeMillis()));
		logger.debug(templateBusiness.toString());
		voucherTemplateDao.save(templateBusiness);

		return s != null;
	}

	private boolean validateVoucherAmount(List<AcctVoucherEntry> voucherEntries) {
		// 计算借贷金额，classId = 7为表外科目，不参与校验
		double jieAmount = 0;
		double daiAmount = 0;
		boolean succ = true;
		for (AcctVoucherEntry entry : voucherEntries) {
			if (entry.getTclassId() != 7) {
				if (entry.getTdc() == 1) {
					jieAmount += entry.getTamount();
				} else if (entry.getTdc() == -1) {
					daiAmount += entry.getTamount();
				}
			}
		}
		if (jieAmount != daiAmount) {
			succ = false;
			throw new ServiceException(formatTipMsg("凭证借:" + jieAmount + "贷:"
					+ daiAmount + "不相等"));
		}
		return succ;
	}

	/**
	 * 
	 * @Title: generateVoucherDetail
	 * @Description: 生成凭证分录的流程，该流程中是通过拼sql语句的方式实现的
	 * @param dataSourceSql
	 *            数据源的sql
	 * @param isSAB
	 *            是否冲红
	 * @param userInfo
	 *            审批人的信息
	 * @param dataSource
	 *            数据源
	 * @param template
	 *            凭证模板
	 * @param voucherId
	 *            凭证主分录ID
	 * @return List<AcctVoucherEntry> 凭证详细分录
	 */
	private List<AcctVoucherEntry> generateVoucherDetail(String dataSourceSql,
			boolean isSAB, SysUsers userInfo,
			List<Map<String, Object>> dataSource, AcctVoucherTemplate template,
			Integer voucherId) {

		List<AcctVoucherEntry> voucherEntryList = new ArrayList<AcctVoucherEntry>();

		int torderNumber = 1;
		// 取凭证模板核算对象
		Set<AcctVoucherTemplateClass> templateClasses = template
				.getVoucherTemplateClasses();

		// 取出数据源的所有字段名称
		Set<String> dataSourceFieldNames = dataSource.get(0).keySet();

		for (AcctVoucherTemplateD templateDetail : template
				.getVoucherTemplateDetails()) {
			AcctAccount account = templateDetail.getAcctAcount();

			if (!account.getTdetail()) {
				throw new ServiceException(formatTipMsg("模板配置错误,科目["
						+ templateDetail.getTaccoutName() + "]不是明细级科目"));
			}

			String priceFieldStr = templateDetail.getTpriceField();// 金额字段名称
			String resumeFieldStr = templateDetail.getTresumeFields();// 摘要字段
			String resumeExpressionStr = templateDetail.getTresumeExpresion();// 摘要表达式
			String quantityFieldStr = templateDetail.getTquantityField();// 数量字段
			String objectNoField = templateDetail.getTobjectNoField();// 往来对象字段

			String[] resumeFields = new String[0];
			if (!isEmptyOrNull(resumeFieldStr)) {
				resumeFields = resumeFieldStr.split(",");
			}

			StringBuilder groupByField = new StringBuilder();// Group
																// By字段(凭证模板核算对象group、凭证模板明细[往来]、[摘要]字段)
			StringBuilder sumField = new StringBuilder();// SUM求和字段

			// 得到Group、SUM 字段
			getGroupAndSumField(templateClasses, templateDetail, groupByField,
					sumField);

			// 如果凭证模板明细的会计科目需要核算[数量]，则将[数量]字段添加到 sum 字段列表中
			if (account.getTquantity()) {
				if (isEmptyOrNull(quantityFieldStr)) {
					throw new ServiceException(formatTipMsg("科目["
							+ templateDetail.getTaccoutName() + "]没有配置数量字段"));
				} else {
					sumField.append(quantityFieldStr + ",");
				}
			}

			// 不包含[摘要]字段的分组条件字段(即核算对象中的group字段、凭证模板明细中的[往来]字段),用来构造生成detail_id的数组
			String groupByClassField = deleteLastChar(new StringBuilder(
					groupByField));

			// 将[摘要]字段添加到 groupBy 字段中
			for (String field : resumeFields) {
				if (groupByField.indexOf(field) == -1) {
					groupByField.append(field + ",");
				}
			}

			// 将[单位]字段添加到分组字段中
			String unitField = templateDetail.getTunitField();// 单位字段
			if (!isEmptyOrNull(unitField)) {
				groupByField.append(unitField + ",");
			}

			// Group By字段(凭证模板核算对象group、凭证模板明细[往来]、[摘要]字段)
			String groupByFieldStr = deleteLastChar(groupByField);
			// SUM 字段
			String sumFieldStr = deleteLastChar(sumField);

			// 检查数据源中是否存在 groupBy 中的字段
			String[] groupByFields = groupByFieldStr.split(",");
			for (String field : groupByFields) {
				if (!dataSourceFieldNames.contains(field)) {
					throw new ServiceException(formatTipMsg("凭证模板配置错误,列["
							+ field + "]不存在!"));
				}
			}

			// 检查数据源中是否存在 sum 中的字段
			String[] sumFields = sumFieldStr.split(",");
			StringBuilder sumSb = new StringBuilder();
			for (String field : sumFields) {
				if (!dataSourceFieldNames.contains(field)) {
					throw new ServiceException(formatTipMsg("凭证模板配置错误,列["
							+ field + "]不存在!"));
				}
				sumSb.append("SUM(" + field + ")AS " + field + ",");
			}

			// 根据group by 字段和 sum字段对数据源进行分组求和及过滤

			String filterFieldStr = templateDetail.getTfilterFields();
			String sql = "SELECT " + groupByFieldStr + ", "
					+ deleteLastChar(sumSb) + " FROM(" + dataSourceSql
					+ ")AS temp ";
			if (filterFieldStr != null && filterFieldStr.trim().length() > 0) {
				sql = sql + " WHERE " + filterFieldStr;
			}
			sql += " GROUP BY " + groupByFieldStr;
			logger.debug(sql);
			List<Map<String, Object>> groupedDataSources = voucherTemplateDao
					.getMapBySQL(sql, null);

			// 产生明细分录
			for (Map<String, Object> groupedDataSrc : groupedDataSources) {
				double price = 0;
				Object priceValue = groupedDataSrc.get(priceFieldStr);
				if (priceValue instanceof Double) {
					price = (Double) priceValue;
				} else {
					price = ((BigDecimal) groupedDataSrc.get(priceFieldStr))
							.doubleValue();
				}

				// 不需要金额为0的分录
				if (price == 0) {
					continue;
				}

				AcctVoucherEntry voucherEntry = new AcctVoucherEntry();
				voucherEntry.setTvoucherId(voucherId);// 凭证主分录ID
				voucherEntry.setTorderNumber(torderNumber++);
				voucherEntry.setTcompanyId(userInfo.getCompanyInfo()
						.getTcompanyId());
				voucherEntry.setTcurrencyId(0);
				voucherEntry.setTexchangeRate(0.0);
				voucherEntry.setTclassId(account.getTclassId());
				String resumeStr = "机制凭证";
				if (!isEmptyOrNull(resumeExpressionStr)) {
					resumeStr = resumeExpressionStr;
					if (isSAB) {
						resumeStr = "冲红" + resumeStr;
					}
					for (String fieldName : resumeFields) {
						Object value = groupedDataSrc.get(fieldName);
						resumeStr = resumeStr.replaceAll("\\[" + fieldName
								+ "\\]",
								value == null ? "未定义" : value.toString());
					}
				}
				voucherEntry.setTresume(resumeStr);
				voucherEntry.setTaccountId(account.getTaccountId());
				voucherEntry.setTdc(templateDetail.getTdc());
				if (unitField != null) {
					Object unit = groupedDataSrc.get(unitField);
					voucherEntry.setTunit(unit == null ? "" : unit.toString());
				}
				// 凭证模板明细标记为[红字]则对金额进行取反
				if (templateDetail.getTisRed()) {
					voucherEntry.setTamount(-price);
				} else {
					voucherEntry.setTamount(price);
				}
				if (account.getTquantity()) {
					Double quantity;
					Object quantityValue = groupedDataSrc.get(quantityFieldStr);
					if (quantityValue instanceof Double) {
						quantity = (Double) quantityValue;
					} else {
						quantity = ((BigDecimal) groupedDataSrc
								.get(quantityFieldStr)).doubleValue();
					}
					voucherEntry.setTquantity(quantity);
					voucherEntry.setTunitPrice(voucherEntry.getTamount()
							/ quantity);
				} else {
					voucherEntry.setTquantity(0.0);
					voucherEntry.setTunitPrice(0.0);
				}
				voucherEntry.setTamountFor(0.0);
				voucherEntry.setTsettleTypeId(0);
				voucherEntry.setTsettleNo("");
				voucherEntry.setTtransNo("");
				voucherEntry.setTdate(new Timestamp(new Date().getTime()));
				voucherEntry.setTprofitLossFlag(false);

				int detailId = generateVoucherEntryDetailId(templateClasses,
						objectNoField, groupByClassField, groupedDataSrc);

				voucherEntry.setTdetailId(detailId);
				logger.debug(voucherEntry.getTresume() + "; "
						+ account.getTfullname() + "; "
						+ (voucherEntry.getTdc() == 1 ? "借" : "贷")
						+ voucherEntry.getTamount());
				voucherEntryList.add(voucherEntry);
			}

		}
		return voucherEntryList;
	}

	/**
	 * 得到GroupBy(凭证模板核算对象标记为group的字段+凭证模板明细[往来对象])、
	 * SUM字段([金额]字段+凭证模板核算对象标记为sum的字段)
	 * 
	 * @param templateClasses
	 * @param templateDetail
	 * @param groupByField
	 * @param sumField
	 */
	private void getGroupAndSumField(
			Set<AcctVoucherTemplateClass> templateClasses,
			AcctVoucherTemplateD templateDetail, StringBuilder groupByField,
			StringBuilder sumField) {

		String objNoFieldStr = templateDetail.getTobjectNoField();// 往来对象字段名称
		String priceFieldStr = templateDetail.getTpriceField();// 金额字段名称

		if (!isEmptyOrNull(priceFieldStr)) {
			sumField.append(priceFieldStr + ",");
		}

		// 根据凭证模板明细的会计科目取该科目的核算项
		Set<AcctItemClass> itemClasses = templateDetail.getAcctAcount()
				.getItemClasses();
		for (AcctItemClass itemClass : itemClasses) {
			if (itemClass.getTitemClassId() == null) {
				continue;
			}
			AcctVoucherTemplateClass templateClass = null;
			Integer itemClassId = itemClass.getTitemClassId();
			for (AcctVoucherTemplateClass tc : templateClasses) {
				if (tc.getTclassId() == itemClassId) {
					templateClass = tc;
					break;
				}
			}

			if (templateClass == null) {
				throw new ServiceException(formatTipMsg("凭证模板的核算对象["
						+ itemClass.getTname() + "]配置错误:"));
			}

			if (templateClass.getTisGroup()) {
				if (!isEmptyOrNull(objNoFieldStr)
						&& itemClass.getTitemClassId() == 3) {
					groupByField.append(objNoFieldStr + ",");
				} else {
					groupByField.append(templateClass.getTfields() + ",");
				}
			} else if (templateClass.getTisSum()) {
				sumField.append(templateClass.getTfields() + ",");
			}
		}

	}

	/**
	 * 生成凭证主分录
	 * 
	 * @param voucherTemplate
	 *            凭证模板
	 * @param documentNo
	 *            数据源单据编号
	 * @param voucherWId
	 *            凭证字ID
	 * @param userInfo
	 *            当前登录的用户信息
	 */
	private AcctVoucher generateVoucherEnter(
			AcctVoucherTemplate voucherTemplate, String documentNo,
			int voucherWId, SysUsers userInfo) {
		int companyId = userInfo.getCompanyInfo().getTcompanyId();
		// 取会计期间
		AcctPeriod acctPeriod = acctPeriodDao
				.getCurAcctPeriodByCompanyId(companyId);
		if (acctPeriod == null) {
			throw new ServiceException(formatTipMsg("获取会计期间出错!"));
		}
		// 获取凭证字号
		int voucherNumber = getVoucherNumber(companyId, voucherWId, acctPeriod);
		if (voucherNumber == -1) {
			throw new ServiceException(formatTipMsg("获取凭证编号出错!"));
		}

		AcctVoucher voucher = new AcctVoucher();
		voucher.setTvoucherId(sysIdentityDao
				.getIdentityAndIncrementUpdate("acct_voucher"));
		voucher.setTvoucherWId(voucherWId);
		voucher.setTyear(acctPeriod.getTyear());
		voucher.setTmonth(acctPeriod.getTmonth());
		voucher.setTacctDate(new Timestamp(new Date().getTime()));
		voucher.setTcompanyId(companyId);
		voucher.setTnote("");
		voucher.setTattachment(0);
		voucher.setTpreparer(voucherTemplate.getCreatorFullName());
		voucher.setTchecker("");
		voucher.setTposter("");
		voucher.setTtranType(voucherTemplate.getTno());
		voucher.setTnumber(voucherNumber);
		voucher.setTtranNo(documentNo);
		voucher.setTenter(true);

		voucher.settCreateTime(new Timestamp(System.currentTimeMillis()));
		voucher.settValidate(true);
		voucherTemplateDao.save(voucher);
		return voucher;
	}

	/**
	 * 获取凭证字ID编号
	 * 
	 * @param companyId
	 * @param voucherWId
	 * @param acctPeriod
	 * @return 凭证字编号，-1失败
	 */
	private int getVoucherNumber(int companyId, int voucherWId,
			AcctPeriod acctPeriod) {
		AcctVoucherNo voucherNo = voucherNoDao.getVoucherNoByProperty(
				companyId, voucherWId, acctPeriod.getTyear(),
				acctPeriod.getTmonth());
		int number = -1;
		if (voucherNo == null) {
			AcctVoucherNoId id = new AcctVoucherNoId();
			id.setTcompanyId(companyId);
			id.setTmonth(acctPeriod.getTmonth());
			id.setTyear(acctPeriod.getTyear());
			id.setTvoucherWId(voucherWId);
			id.setTnumber(2);
			voucherNo = new AcctVoucherNo(id, "M");
			Serializable a = voucherNoDao.save(voucherNo);

			return a == null ? -1 : 1;
		} else {
			AcctVoucherNoId id = voucherNo.getId();
			number = id.getTnumber();
			boolean succ = voucherNoDao.updateNumber(voucherNo, number + 1);

			return succ ? number : -1;
		}

	}

	private int generateVoucherEntryDetailId(
			Set<AcctVoucherTemplateClass> templateClasses,
			String objectNoField, String groupByClassField,
			Map<String, Object> groupedDataSrc) {

		String[] arrNos = new String[15];
		if (!isEmptyOrNull(groupByClassField)) {
			String[] fieldNames = groupByClassField.split(",");
			for (String field : fieldNames) {

				if (field.trim().length() > 0) {
					AcctVoucherTemplateClass tclass = null;
					for (AcctVoucherTemplateClass tempClass : templateClasses) {
						if (tempClass.getTfields().equals(field)) {
							tclass = tempClass;
							break;
						}
					}

					if (field.equals(objectNoField)) {
						for (AcctVoucherTemplateClass tempClass : templateClasses) {
							if (tempClass.getTclassId() == 3) { // tclass_id =
																// 3是"往来"核算项
								tclass = tempClass;
								break;
							}
						}
					}

					if (tclass != null) {
						int index = tclass.getTclassId() - 1;
						arrNos[index] = (String) groupedDataSrc.get(field);
					}

				}
			}
		}
		int tdetailId = getAcctComboID(arrNos, false);
		if (tdetailId < 0) {
			throw new ServiceException(formatTipMsg("获取核算对象明细ID出错"));
		}
		return tdetailId;
	}

	/**
	 * <b>生成detail_id (acct_item_detail_h、acct_item_detail_v)</b><br>
	 * 
	 * 关于acct_item_h和acct_item_v表的说明：<br>
	 * 1、两个表存的数据是一致的，acct_item_h是横表，acct_item_v是纵表<br>
	 * 2、acct_item_h中t1,t2,t3.....t15,分别对应acct_item_class中的核算项，即t1-部门,t2-员工,t3-
	 * 往来,t4-商品<br>
	 * 3、t1,t2,t3.....t15所对应的值：-1标记代表是会计科目需要核算的项目，>0表示是其他，如凭证分录的核算项所对应的具体核算对象[
	 * acct_item]的编码<br>
	 * 
	 * @param arrNos
	 * @param isAccount
	 * @return detail_id, -100生成错误
	 */
	public int getAcctComboID(String[] arrNos, boolean isAccount) {
		if (arrNos == null) {
			return -100;
		}
		if (arrNos.length < 15) {
			String[] temp = new String[15];
			for (int i = 0; i < arrNos.length; i++) {
				temp[i] = arrNos[i];
			}
			arrNos = temp;
		}
		final String KEY_ID = "id";
		final String KEY_CLASS = "class";
		final String KEY_NO = "no";
		List<Map<String, Object>> dtb1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arrNos.length; i++) {
			String s = arrNos[i];
			if (!isEmptyOrNull(s)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(KEY_ID, -1);
				map.put(KEY_CLASS, i + 1);
				map.put(KEY_NO, s);
				dtb1.add(map);
			}
		}
		if (dtb1.size() == 0) {
			logger.error("arrNos是空数组");
			return 0;
		}
		if (!isAccount) {
			StringBuilder sbClass = new StringBuilder(
					"SELECT titem_class_id, ttype FROM acct_item_class WHERE titem_class_id IN (");
			StringBuilder sbItem = new StringBuilder(
					"SELECT * FROM acct_item WHERE ");
			for (Map<String, Object> map : dtb1) {
				sbClass.append(map.get(KEY_CLASS).toString() + ",");
				sbItem.append("(titem_class_id = "
						+ map.get(KEY_CLASS).toString() + " AND tno = '"
						+ map.get(KEY_NO).toString() + "') OR ");
			}
			deleteLastChar(sbClass);
			sbClass.append(")");
			sbItem.delete(sbItem.length() - 3, sbItem.length() - 1);

			logger.debug(sbClass.toString());
			logger.debug(sbItem.toString());

			List<AcctItemClass> acctItemClassList = voucherTemplateDao
					.getEntityBySQL(sbClass.toString(), AcctItemClass.class,
							null);
			List<AcctItem> acctItemList = voucherTemplateDao.getEntityBySQL(
					sbItem.toString(), AcctItem.class, null);
			if (acctItemClassList == null || acctItemClassList.size() == 0
					|| acctItemList == null) {
				logger.error("核算项不存在:" + sbClass.toString());
				return -100;
			}

			for (Map<String, Object> drw : dtb1) {

				// 取核算项目 select from acctItemClass where
				// titem_class_id=map.get("class");
				AcctItemClass acctItemClass = null;
				for (AcctItemClass itemClass : acctItemClassList) {
					if (drw.get(KEY_CLASS) == itemClass.getTitemClassId()) {
						acctItemClass = itemClass;
						break;
					}
				}

				if (acctItemClass == null) {
					logger.error("核算项不存在:" + acctItemClass);
					return -100;
				}

				short nType = acctItemClass.getTtype();
				// 取核算对象 select from acctItem where titem_class_id =
				// map.get("class") AND tno = map.get("no")
				AcctItem acctItem = null;
				for (AcctItem item : acctItemList) {
					if (drw.get(KEY_CLASS) == item.getTitemClassId()
							&& drw.get(KEY_NO).equals(item.getTno())) {
						acctItem = item;
						break;
					}
				}

				if (acctItem == null) {
					if (nType != 0) {
						// acct_item_class.ttype = 1:
						// 核算对象acct_item只能从已有数据中选择，如果没有该核算对象，则返回错误
						// acct_item_class.ttype = 0:
						// 核算对象可以由用户输入，如果没有该核算对象，则生成一条记录
						logger.error("核算对象不存在:titem_class_id="
								+ drw.get(KEY_CLASS) + "tno=" + drw.get(KEY_NO));
						return -100;
					}

					// 生成一条核算对象的记录
					int nextNumber = sysIdentityDao
							.getIdentityAndIncrementUpdate("acct_item");
					AcctItem newItem = new AcctItem();
					newItem.setTitemId(nextNumber);
					newItem.setTitemClassId((Integer) drw.get(KEY_CLASS));
					newItem.setTno(drw.get(KEY_NO).toString());
					newItem.setTparentId(0);
					newItem.setTlevel((short) 1);
					newItem.setTdetail(true);
					newItem.setTname(drw.get(KEY_NO).toString());
					newItem.setTused(false);
					newItem.setTid("");

					voucherTemplateDao.save(newItem);

					drw.put(KEY_ID, nextNumber);
				} else {
					drw.put(KEY_ID, acctItem.getTitemId());
				}
			}
		}

		// 查询acct_item_detail_h中是否存在符合条件的detail_id
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrNos.length; i++) {
			Integer sClass = i + 1;
			Map<String, Object> drws = null;
			for (Map<String, Object> map : dtb1) {
				if (map.get(KEY_CLASS) == sClass) {
					drws = map;
					break;
				}
			}
			if (drws == null) {
				sb.append(" AND t" + sClass + " = 0");
			} else {
				sb.append(" AND t" + sClass + " = " + drws.get(KEY_ID));
			}
		}
		sb.delete(0, 5);
		sb.insert(0, "SELECT * FROM acct_item_detail_h WHERE ");
		logger.debug(sb.toString());

		List<Map<String, Object>> acctItemDetailHs = voucherTemplateDao
				.getMapBySQL(sb.toString(), null);

		// 存在符合条件的tdetail_id则直接返回该ID，否则分别在acct_item_detail_h、acct_item_detail_v中生成相应的记录
		if (acctItemDetailHs.size() > 0) {
			return (Integer) acctItemDetailHs.get(0).get("tdetail_id");
		}
		int nextNumber = sysIdentityDao
				.getIdentityAndIncrementUpdate("acct_item_detail_h");
		AcctItemDetailH itemDetailH = new AcctItemDetailH();
		itemDetailH.setTdetailId(nextNumber);
		for (int i = 0; i < arrNos.length; i++) {
			Integer sClass = i + 1;
			Map<String, Object> drws = null;
			for (Map<String, Object> map : dtb1) {
				if (map.get(KEY_CLASS) == sClass) {
					drws = map;
					break;
				}
			}
			if (drws == null) {
				DataHelper.setFieldValue(itemDetailH, "t" + sClass, 0);
			} else {
				DataHelper.setFieldValue(itemDetailH, "t" + sClass,
						drws.get(KEY_ID));

				AcctItemDetailV itemDetailV = new AcctItemDetailV(nextNumber,
						(Integer) drws.get(KEY_CLASS),
						(Integer) drws.get(KEY_ID));

				voucherTemplateDao.save(itemDetailV);
			}
		}
		voucherTemplateDao.save(itemDetailH);
		return nextNumber;
	}

	// -------------Helper method-----------
	private String formatTipMsg(String msg) {
		return "机制凭证生成失败:" + msg;
	}

	/**
	 * 删除最后一个字符
	 * 
	 * @param sb
	 * @return
	 */
	private String deleteLastChar(StringBuilder sb) {
		if (sb == null) {
			return null;
		}
		if (sb.length() > 0) {
			return sb.deleteCharAt(sb.length() - 1).toString();
		}
		return null;
	}

	private boolean isEmptyOrNull(String s) {
		return s == null || s.trim().length() == 0;
	}

	public boolean generateVoucherByProcedure(String dataSourceSql,
			String templateNo, boolean isSAB, SysUsers userInfo) {
		if (!config.isAutoVoucherOn()) {
			return true;
		}
		// 初始化传人参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sql", dataSourceSql);
		paramMap.put("tno", templateNo);
		List<Map<String, Object>> dataSource = voucherTemplateDao.getMapBySQL(
				"{EEXEC Sp_getUsers}", null);
		logger.info("===========================返回的结果集如下===============================");
		if (null == dataSource || dataSource.isEmpty()) {
			logger.info("返回的结果集为空！");
		}
		for (Map<String, Object> map : dataSource) {
			if (null == map || map.isEmpty()) {
				logger.info("map为空");
			}
			for (String key : map.keySet()) {
				logger.info("key:" + map.get(key));
			}
		}
		if (dataSource == null || dataSource.size() == 0) {
			throw new ServiceException(formatTipMsg("数据源为空!"));
		}

		// 获取当前登录用户的帐套ID
		int companyId = userInfo.getCompanyInfo().getTcompanyId();

		AcctVoucherTemplate template = voucherTemplateDao
				.getTemplateByNo(templateNo);
		if (template == null) {
			throw new ServiceException(formatTipMsg("配置模版不存在!"));
		}

		// 凭证模板未启用，则直接返回TRUE
		if (!template.getTstatus()) {
			logger.info("===凭证模板未启用===");
			return true;
		}

		// 获取帐套凭证字
		AcctVoucherW voucherW = null;
		Set<AcctVoucherW> voucherWs = template.getVoucherWs();
		if (voucherWs == null) {
			throw new ServiceException(formatTipMsg("帐套凭证字没有配置!"));
		}
		for (AcctVoucherW w : voucherWs) {
			if (w.getTcompanyId() == companyId) {
				voucherW = w;
				break;
			}
		}

		if (voucherW == null) {
			throw new ServiceException(formatTipMsg("凭证字不存在!"));
		}

		Set<AcctVoucherTemplateD> templateDetails = template
				.getVoucherTemplateDetails();
		if (templateDetails == null || templateDetails.size() == 0) {
			throw new ServiceException(formatTipMsg("凭证模板未配置明细!"));
		}

		String documentNoField = templateDetails.iterator().next()
				.getTdocumnetNoField();

		String documentNo = (String) dataSource.get(0).get(documentNoField);

		// ==========生成凭证主分录========
		AcctVoucher voucher = generateVoucherEnter(template, documentNo,
				voucherW.getTvoucherWId(), userInfo);
		if (voucher == null) {
			throw new ServiceException("凭证主分录生成失败!");
		}

		// ========生成凭证明细分录========
		// 凭证主分录ID
		Integer voucherId = voucher.getTvoucherId();
		List<AcctVoucherEntry> voucherEntries = generateVoucherDetail(
				dataSourceSql, isSAB, userInfo, dataSource, template, voucherId);

		boolean succ = validateVoucherAmount(voucherEntries);
		if (!succ) {
			return false;
		}
		voucher.setVoucherEntries(voucherEntries);
		logger.debug(voucher.toString());
		Serializable s = voucherTemplateDao.save(voucher);

		// 生成业务单与财务凭证单关联关系的记录
		AcctVoucherTemplateBusiness templateBusiness = new AcctVoucherTemplateBusiness();
		templateBusiness.setTvoucherId(-voucherId);
		templateBusiness.setTno(templateNo);
		templateBusiness.setBusinessNo(documentNo);
		templateBusiness
				.setCreateTime(new Timestamp(System.currentTimeMillis()));
		logger.debug(templateBusiness.toString());
		voucherTemplateDao.save(templateBusiness);

		return s != null;
	}

	public boolean generateVoucherByProc(String sql, String tno, boolean isSAB,
			String userID, String documentNo) {
		if (!config.isAutoVoucherOn()) {
			return true;
		}
		return voucherAutoDao.generateVoucherByProc(sql, tno, isSAB, userID,
				documentNo);
	}
}
