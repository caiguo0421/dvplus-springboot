package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/15 14:02
 * @Description:
 */
@Service
public class LoanBudgetDetailsService implements Command<VehicleLoanBudgetDetails> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanBudgetDetailsService.class);

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(LoanBudgetDetailsService.class);
    }

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private LoanBudgetService loanBudgetService;


    @Override
    public EntityRelation getEntityRelation() {
        return null;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleLoanBudgetDetails> entityProxy) {
        validateRecord(entityProxy);
    }

    private void validateRecord(EntityProxy<VehicleLoanBudgetDetails> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        EntityProxy<VehicleLoanBudget> budgetEntityProxy = entityProxy.getMaster().getMaster();
        VehicleLoanBudget budget = budgetEntityProxy.getEntity();
        VehicleLoanBudgetDetails budgetDetail = entityProxy.getEntity();

        if (StringUtils.isEmpty(budgetDetail.getVnoId())) {
            throw new ServiceException("车辆明细中，车型型号不能为空");
        }
        if (Tools.toDouble(budgetDetail.getVehiclePriceTotal()) <= 0.00D) {
            throw new ServiceException("车辆明细中，车辆售价必须大于0");
        }

        if (Tools.toDouble(budgetDetail.getLoanAmount()) <= 0.00D) {
            throw new ServiceException("车辆明细中，车辆贷款必须大于0");
        }

        //非合同消费贷款
        if (StringUtils.isEmpty(budget.getSaleContractNo())) {
            if (Tools.toDouble(budgetDetail.getLoanAmount()) > Tools.toDouble(budgetDetail.getVehiclePriceTotal())) {
                throw new ServiceException("车辆明细中，车辆贷款不能大于车辆售价");
            }
        }
        if (Tools.toDouble(budgetDetail.getAgentAmount()) < 0.00D) {
            throw new ServiceException("车辆明细中，代理贷款不能小于0");
        }

        //消贷预算单为未审批时
        if (Tools.toShort(budget.getStatus(), (short) 10) < 20) {
            if (budgetDetail.getRateType() == null) {
                throw new ServiceException("车辆明细中，计息方式不能为空");
            }

            if (Tools.toInt(budgetDetail.getPeriodNumber()) <= 0) {
                throw new ServiceException("车辆明细中，期数必须大于0");
            }

            if (Tools.toBigDecimal(budgetDetail.getMonthPay()).compareTo(BigDecimal.ZERO) < 0) {
                throw new ServiceException("车辆明细中，月付款不能小于0");
            }
        }

        if (Tools.toShort(budget.getLoanMode(), (short) 10) == 20 && StringUtils.isEmpty(budgetDetail.getVehicleVin())) {
            throw new ServiceException("车辆明细中，当为代理消贷时车辆VIN不能为空");
        }

        if (StringUtils.isNotEmpty(budgetDetail.getSaleContractDetailId())) {
            VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, budgetDetail.getSaleContractDetailId());
            if (detail != null && Tools.toBigDecimal(detail.getVehiclePriceTotal()).compareTo(Tools.toBigDecimal(budgetDetail.getVehiclePriceTotal())) != 0) {
                throw new ServiceException("车辆明细中的车辆售价在销售合同中已经发生变化，不允许保存");
            }
        }

        if (StringUtils.isNotEmpty(budget.getSaleContractNo())) {
            //正常贷款
            VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, budgetDetail.getSaleContractDetailId());
            double mChargeIncome = Tools.toDouble(detail.getChargeIncome());
            if (Tools.toDouble(budgetDetail.getLoanAmount()) > Tools.toDouble(budgetDetail.getVehiclePriceTotal()) - mChargeIncome) {
                throw new ServiceException(String.format("车辆明细中，车辆贷款不能大于车辆售价与合同费用之差(%s)", Tools.toDouble(budgetDetail.getVehiclePriceTotal()) - mChargeIncome));
            }

            String sql = "SELECT a.vehicle_vin,a.document_no FROM dbo.vehicle_loan_budget_details a\n" + "LEFT JOIN vehicle_loan_budget b ON a.document_no=b.document_no\n" + "WHERE a.status IN (0,1) AND b.status<=50 AND a.sale_contract_detail_id =:contractDetailId\n" + "AND a.self_id <>:selfId";
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("contractDetailId", budgetDetail.getSaleContractDetailId());
            params.put("selfId", budgetDetail.getSelfId());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                Map<String, Object> result = list.get(0);
                throw new ServiceException(String.format("该预算单已有车辆明细在预算单【%s】中做了预算，请勿对同一台车进行重复预算", result.get("document_no")));
            }

            boolean m_bPNeedSVCApproved = "是".equals(loanBudgetService.getSysOptionValue(LoanBudgetService.VEHICLE_BUDGET_NEED_SVC_APPROVED));
            if (m_bPNeedSVCApproved) {
                if (Tools.toShort(detail.getApproveStatus()) != 1 && Tools.toShort(detail.getApproveStatus()) != 2) {
                    throw new ServiceException("预算明细对应的销售明细未审批");
                }
            }
            if (Tools.toShort(detail.getApproveStatus()) != 0 && Tools.toShort(detail.getApproveStatus()) != 1 && Tools.toShort(detail.getApproveStatus()) != 20) {
                throw new ServiceException("预算明细对应的销售明细的状态不正确");
            }

            //判断销售合同的车辆VIN是否与当前明细的一致
            if (!StringUtils.equals(budgetDetail.getVnoId(), detail.getVnoId())) {
                throw new ServiceException("销售合同明细的车型已发生变化，请傻笑数据后重试");
            }

            if (!StringUtils.equals(budgetDetail.getVehicleVin(), detail.getVehicleVin())) {
                throw new ServiceException("销售合同明细的VIN已发生变化，请傻笑数据后重试");
            }

            if (!StringUtils.equals(budgetDetail.getBulletinId(), detail.getVnoIdNew())) {
                throw new ServiceException("销售合同明细的数据已发生变化，请傻笑数据后重试");
            }
            if (!StringUtils.equals(budgetDetail.getBulletinNo(), detail.getVehicleVnoNew())) {
                throw new ServiceException("销售合同明细的数据已发生变化，请傻笑数据后重试");
            }

            if (Tools.toBigDecimal(budgetDetail.getVehiclePriceTotal()).compareTo(Tools.toBigDecimal(detail.getVehiclePriceTotal())) != 0) {
                throw new ServiceException("销售合同明细的车辆售价已发生变化，请傻笑数据后重试");
            }
        }
        //判断VIN是否做过预算单
        out:
        if (StringUtils.isNotEmpty(budgetDetail.getVehicleVin())) {
            String sql = "select * from secondhand_vehicle_stocks where vehicle_vin = :vehicleVin";
            Map<String, Object> params = new HashMap<>(2);
            params.put("vehicleVin", budgetDetail.getVehicleVin());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                //如果当前车辆是二手车则不验证
                break out;
            }

             sql = "SELECT a.vehicle_vin,b.document_no,a.loan_amount_all,c.loan_amount AS finish_amount\n" + "FROM dbo.vw_vehicle_loan_budget_details a \n" + "INNER JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no \n" + "LEFT JOIN (SELECT budget_detail_id,SUM(loan_amount) loan_amount \n" + "FROM dbo.vw_vehicle_loan_contracts_vehicles a\n" + "LEFT JOIN dbo.vehicle_loan_contracts b ON b.slc_no = a.slc_no\n" + "WHERE b.status=35\n" + "GROUP BY budget_detail_id) c ON a.self_id=c.budget_detail_id\n" + "WHERE b.status<=50 \n" + "AND a.vehicle_vin =:vehicleVin AND b.document_no<>:documentNo";
            params = new HashMap<>(2);
            params.put("vehicleVin", budgetDetail.getVehicleVin());
            params.put("documentNo", budget.getDocumentNo());

             list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                for (Map<String, Object> result : list) {
                    if (Tools.toBigDecimal((Double) result.get("loan_amount_all")).compareTo(Tools.toBigDecimal((Double) result.get("finish_amount"))) == 0) {
                        //已经结束贷款的不算
                        continue;
                    } else {
                        throw new ServiceException(String.format("车辆%s已做过预算，预算单号为%s", budgetDetail.getVehicleVin(), result.get("document_no")));
                    }
                }
            }
        }

        double loanAmount = Tools.toDouble(budgetDetail.getLoanAmount());
        double agentAmount = Tools.toDouble(budgetDetail.getAgentAmount());
        double chargeLoanAmount = getChargeLoanAmount(entityProxy);
        String sql = "SELECT a.budget_detail_id,SUM(a.loan_amount) loan_amount FROM dbo.vehicle_loan_contracts_vehicles a\n" + "INNER JOIN dbo.vehicle_loan_contracts b ON a.slc_no=b.slc_no\n" + "WHERE a.budget_detail_id =:budgetDetailId AND b.status<>40 AND b.status<>50 GROUP BY  a.budget_detail_id";
        Map<String, Object> params = new HashMap<>(2);
        params.put("budgetDetailId", budgetDetail.getSelfId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            double loanContractAmount = Tools.toDouble((Number) (list.get(0).get("loan_amount")));
            if (loanContractAmount > loanAmount + agentAmount + chargeLoanAmount) {
                throw new ServiceException(String.format("车辆 %s 的车辆贷款+费用贷款+代理贷款之和(%s)小于该车已建立贷款合同的贷款金额(%s)", budgetDetail.getVehicleVin(), loanAmount + agentAmount + chargeLoanAmount, loanContractAmount));
            }

        }

    }

    private double getChargeLoanAmount(EntityProxy<VehicleLoanBudgetDetails> entityProxy) {
        double val = 0.00D;
        List<EntityProxy<VehicleLoanBudgetCharge>> budgetChargeProxies = entityProxy.getSlaves(VehicleLoanBudgetCharge.class.getSimpleName());
        if (budgetChargeProxies != null && budgetChargeProxies.size() > 0) {
            for (EntityProxy<VehicleLoanBudgetCharge> chargeEntityProxy : budgetChargeProxies) {
                if (chargeEntityProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleLoanBudgetCharge budgetCharge = chargeEntityProxy.getEntity();
                if (Tools.toByte(budgetCharge.getStatus()) == 10 || Tools.toByte(budgetCharge.getStatus()) == 20) {
                    val += Tools.toDouble(budgetCharge.getLoanAmount());
                }
            }
        }

        return val;
    }

    @Override
    public void execute(EntityProxy<VehicleLoanBudgetDetails> entityProxy) {

    }
}
