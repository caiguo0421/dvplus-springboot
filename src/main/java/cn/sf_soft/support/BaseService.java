package cn.sf_soft.support;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.TimeUtil;
import cn.sf_soft.common.util.TimestampUitls;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public abstract class BaseService<E> implements Command<E> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("DVPlusService");

    /**
     * 缺省转换器
     */
    private ParameterConverter converter;

    @Autowired
    private BaseDao baseDao;

    public ParameterConverter getDefaultConvert() {
        if (null == this.converter) {
            converter = ApplicationUtil.getBean("parameterDefaultConvert");
        }
        return this.converter;
    }

    public void setDefaultConvert(ParameterConverter converter) {
        this.converter = converter;
    }

    protected List<EntityProxy<?>> convert(JsonObject parameter) {
        logger.debug("执行输入参数转换");
        List<EntityProxy<?>> entityProxies = getDefaultConvert().convert(parameter, this);
        return entityProxies;
    }

    /**
     * 保存
     */
    public List<EntityProxy<?>> save(JsonObject parameter) {
        List<EntityProxy<?>> entityProxies = this.convert(parameter);
        //执行before excute
        beforeExecuteProxy(entityProxies);
        logger.debug("beforeExecuteProxy 完成");
        //遍历执行excute
        executeProxy(entityProxies);
        logger.debug("executeProxy 完成");
        //遍历保存
        saveProxy(entityProxies);
        logger.debug("saveProxy 完成");

        return entityProxies;
    }

    /**
     * 执行beforeExecute，由上到下
     *
     * @param entityProxies
     */
    private void beforeExecuteProxy(List<EntityProxy<?>> entityProxies) {
        if (null != entityProxies && !entityProxies.isEmpty()) {
            for (EntityProxy<?> entityProxy : entityProxies) {
                entityProxy.getService().beforeExecute((EntityProxy<Object>) entityProxy);
                if (entityProxy.hasSlave()) {
                    for (int i = 0; i < entityProxy.getSlaveNames().length; i++) {
                        List<EntityProxy<?>> list = ((EntityProxyImpl) entityProxy).getSlaves(entityProxy.getSlaveNames()[i]);
                        this.beforeExecuteProxy(list);
                    }
                }
            }
        }
    }

    /**
     * 执行execute，由下到上
     *
     * @param entityProxies
     */
    private void executeProxy(List<EntityProxy<?>> entityProxies) {
        if (null != entityProxies && !entityProxies.isEmpty()) {
            for (EntityProxy<?> entityProxy : entityProxies) {
                if (entityProxy.hasSlave()) {
                    for (int i = 0; i < entityProxy.getSlaveNames().length; i++) {
                        List<EntityProxy<?>> list = ((EntityProxyImpl) entityProxy).getSlaves(entityProxy.getSlaveNames()[i]);
                        this.executeProxy(list);
                    }
                }
                entityProxy.getService().execute((EntityProxy<Object>) entityProxy);
            }
        }
    }

    /**
     * 保存，由上到下
     *
     * @param entityProxies
     */
    private void saveProxy(List<EntityProxy<?>> entityProxies) {
        if (null != entityProxies && !entityProxies.isEmpty()) {
            for (EntityProxy<?> entityProxy : entityProxies) {
                if(!entityProxy.isReadOnly()) {
                    if (entityProxy.getOperation() == Operation.DELETE) {
                        baseDao.delete(entityProxy.getEntity());
                    } else if (entityProxy.getOperation() == Operation.CREATE) {
                        baseDao.save(entityProxy.getEntity());
                    } else {
                        baseDao.update(entityProxy.getEntity());
                    }
                }

                if (entityProxy.hasSlave()) {
                    for (int i = 0; i < entityProxy.getSlaveNames().length; i++) {
                        List<EntityProxy<?>> list = ((EntityProxyImpl) entityProxy).getSlaves(entityProxy.getSlaveNames()[i]);
                        saveProxy(list);
                    }
                }
            }
        }
    }


    /**
     * 校验数据版本
     *
     * @param modifyTime
     * @param oriModifyTime
     */
    protected void validateModifyTime(Timestamp modifyTime, Timestamp oriModifyTime) {
        if (modifyTime == null || oriModifyTime == null) {
            return;
        }
        if (Math.abs(modifyTime.getTime() - oriModifyTime.getTime()) > 500) {
            throw new ServiceException("数据已被他人修改，请刷新后再试");
        }
    }

    protected void validateModifyTime(String modifyTime, Timestamp oriModifyTime) {
        if (StringUtils.isEmpty(modifyTime) && oriModifyTime == null) {
            return;
        }
        Timestamp curModifyTime = null;
        if(StringUtils.isNotEmpty(modifyTime)) {
            try {
                curModifyTime = new Timestamp(TimestampUitls.formatDate(modifyTime).getTime());
            } catch (ParseException e) {
                throw new ServiceException(String.format("modifyTime(%s)格式错误", modifyTime));
            }
        }
        validateModifyTime(curModifyTime, oriModifyTime);
    }

}
