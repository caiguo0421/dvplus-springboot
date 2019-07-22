package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.JdbcDao;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.model.PageModel;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: caigx
 * @Date: 2018/6/30 10:29
 * @Description: pc查询服务
 */
@Service("queryService")
public class QueryService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(QueryService.class);

    private static final String table_Names = "tableNames";

    private static final String sqls = "sqls";

    @Autowired
    private BaseDaoHibernateImpl baseDao;

    @Autowired
    private JdbcDao jdbcDao;

    /**
     * 移植原pc端的getData接口
     *
     * @param jo_param
     */
    public Map<String, PageModel<Object>> getData(JsonObject jo_param) {
        long stamp = System.currentTimeMillis();
        if (jo_param.get(table_Names) == null || jo_param.get(table_Names).isJsonNull()
                || StringUtils.isBlank(jo_param.get(table_Names).getAsString())) {
            throw new ServiceException(String.format("getData接口出错：参数%s为空", table_Names));
        }

        if (jo_param.get(sqls) == null || jo_param.get(sqls).isJsonNull()
                || StringUtils.isBlank(jo_param.get(sqls).getAsString())) {
            throw new ServiceException(String.format("getData接口出错：参数%s为空", sqls));
        }

        String[] tableNameArray = jo_param.get(table_Names).getAsString().split(";");
        String[] sqlsArray = jo_param.get(sqls).getAsString().split(";");
        if (tableNameArray.length != sqlsArray.length) {
            throw new ServiceException(String.format("getData接口出错：参数%s和%s的数量不一致", table_Names, sqls));
        }


        Map<String, PageModel<Object>> rtnMap = new HashMap<>();
        for (int i = 0; i < tableNameArray.length; i++) {
            String tableName = tableNameArray[i];
            String sql = sqlsArray[i];

            PageModel<Object> reuslt = jdbcDao.listInSql(sql, 0, 0);
            rtnMap.put(tableName, reuslt);
        }

        logger.debug(String.format("getData耗时%d ms", (System.currentTimeMillis() - stamp)));
        return rtnMap;
    }
}
