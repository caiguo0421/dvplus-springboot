package cn.sf_soft.common.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sf_soft.common.model.Column;
import cn.sf_soft.common.model.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;


@Repository("jdbcDao")
public class JdbcDao extends NamedParameterJdbcDaoSupport {

    private static Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    private static String innerAlias = "_inner_alias";

    // private static String outerAlias = "_outer_alias";

    private static String queryCTEName = "query"; // 分页查询CTE的名字


    /**
     * sql查询接口
     *
     * @param sql          sql语句
     * @param pageNo       页码 0或者null则不分页
     * @param linesPerPage 页码 0或者null则不分页
     * @return
     */
    public PageModel<Object> listInSql(String sql, Integer pageNo, Integer linesPerPage) {
        long totalLines = 0L;
        Object[] args = null;
        if (pageNo != null && pageNo > 0 && linesPerPage != null && linesPerPage > 0) {
            // 分页情况
            // 为了提高sql效率，只有在分页的情况下获得总行数，不分页情况下总行数默认为0
            totalLines = this.getJdbcTemplate().queryForObject(String.format("SELECT COUNT(*) FROM ( %s ) AS _count_alias", sql), Long.class);

            String queryCTE = String.format("WITH %s AS ( SELECT ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) AS row_number,* FROM (%s) AS %s)", queryCTEName, sql, innerAlias);
            sql = String.format("%s SELECT * FROM %s where row_number >? AND row_number<= ?", queryCTE, queryCTEName);
            args = new Object[]{(pageNo - 1) * linesPerPage, pageNo * linesPerPage};

        }

        PageModel<Object> model = this.getJdbcTemplate().query(sql, args, new PageModelExtractor());

        model.setPage(pageNo == null ? 0 : pageNo);
        model.setPerPage(linesPerPage == null ? 0 : linesPerPage);
        model.setTotalSize(totalLines);
        return model;
    }

    /**
     * sql执行器
     *
     * @author caigx
     */
    public class PageModelExtractor implements ResultSetExtractor<PageModel<Object>> {
        @Override
        public PageModel<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
            PageModel<Object> model = new PageModel<Object>();
            // 设置 columns
            ResultSetMetaData md = rs.getMetaData();
            List<Column> columns = new ArrayList<Column>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                //重复的列名不要放进来
                if (nameExistsInColumns(columns, md.getColumnLabel(i))) {
                    continue;
                }
                Column column = new Column();
                column.setName(md.getColumnLabel(i)); // 和md.getColumnName(i)的区别是，当有as时，他是as 后面的值
                column.setType(md.getColumnTypeName(i));
//                System.out.println(String.format("md.getColumnLabel(i)=%s,md.getColumnClassName()=%s,md.getColumnTypeName(i)=%s", md.getColumnLabel(i), md.getColumnClassName(i), md.getColumnTypeName(i)));
                columns.add(column);
            }
            model.setColumns(columns);

            List<Object> data = new ArrayList<Object>();
            while (rs.next()) {
//                List<Object> rowData = new ArrayList<Object>();
                Map<String,Object> rowData = new HashMap<>(columns.size());
                for (int i = 0; i < columns.size(); i++) {
//                    rowData.add(rs.getObject(columns.get(i).getName()));
                    rowData.put(columns.get(i).getName(),rs.getObject(columns.get(i).getName()));

                }
                data.add(rowData);
            }

            model.setData(data);
            return model;
        }
    }

    /**
     * 名字是否在集合中
     *
     * @param columns
     * @param label
     * @return
     */
    private boolean nameExistsInColumns(List<Column> columns, String label) {
        for (Column column : columns) {
            if (column.getName().equals(label)) {
                return true;
            }
        }
        return false;
    }

}
