package com.tyebile.meereen.orm.rdb.render.support.simple;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import com.tyebile.meereen.orm.core.DefaultValue;
import com.tyebile.meereen.orm.core.param.InsertParam;
import com.tyebile.meereen.orm.rdb.executor.BindSQL;
import com.tyebile.meereen.orm.rdb.executor.SQL;
import com.tyebile.meereen.orm.rdb.meta.RDBColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.RDBTableMetaData;
import com.tyebile.meereen.orm.rdb.render.Sql;
import com.tyebile.meereen.orm.rdb.render.SqlAppender;
import com.tyebile.meereen.orm.rdb.render.SqlRender;
import com.tyebile.meereen.orm.rdb.render.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SimpleInsertSqlRender implements SqlRender<InsertParam> {
    PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected SimpleSQL createSingleSql(RDBTableMetaData metaData, Object data, Object param, String valueExpressionPrefix) {
        Dialect dialect = metaData.getDatabaseMetaData().getDialect();
        SqlAppender appender = new SqlAppender();
        List<String> columns = new ArrayList<>();
        List<String> valuesExpression = new ArrayList<>();
        Map<String, Object> mapValue = new HashMap<>();

        metaData.getColumns().forEach(column -> {
            Object value = null;
            String propertyName = null;
            try {
                value = propertyUtils.getProperty(data, propertyName = column.getAlias());
            } catch (Exception e) {
                try {
                    value = propertyUtils.getProperty(data, propertyName = column.getName());
                } catch (Exception ignore) {
                    //ignore
                }
            }
            if (value == null) {
                DefaultValue defaultValue = column.getDefaultValue();
                if (defaultValue != null) {
                    value = defaultValue.get();
                    if (!(value instanceof Sql)) {
                        try {
                            //回填数据
                            propertyUtils.setProperty(data, propertyName, value);
                        } catch (Exception ignore) {
                            logger.warn("set property error", ignore);
                        }
                    }
                }
                if (logger.isInfoEnabled() && value != null)
                    logger.info("{}将使用默认值:{}", propertyName, value);
            }
            if (value != null && column.getValueConverter() != null) {
                Object new_value = column.getValueConverter().getData(value);
                if (column.getOptionConverter() != null) {
                    new_value = column.getOptionConverter().converterData(new_value);
                }
                if (value != new_value && !value.equals(new_value)) {
                    if (logger.isDebugEnabled())
                        logger.debug("{} 转换value:{}为:{}", propertyName, value, new_value);
                    value = new_value;
                }
            }
            if (null == value) {
                return;
            }
            mapValue.put(propertyName, value);

            columns.add(dialect.buildColumnName(null, column.getName()));
            if (value instanceof Sql) {
                valuesExpression.add(((Sql) value).getSql());
            } else {
                valuesExpression.add(getParamString(valueExpressionPrefix, propertyName, column).toString());
            }

        });
        appender.add("INSERT INTO ", metaData.getName(), " (")
                .add(String.join(",", columns.toArray(new String[columns.size()])), ")VALUES(")
                .add(String.join(",", valuesExpression.toArray(new String[valuesExpression.size()])), ")");

        return new SimpleSQL(appender.toString(), mapValue);
    }


    @Override
    public SQL render(RDBTableMetaData metaData, InsertParam param) {
        Object data = Objects.requireNonNull(param.getData(), "param can not be null!");
        if (data == null) throw new NullPointerException();

        List<Object> datas = null;
        if (data instanceof Collection) {
            datas = new ArrayList<>(((Collection) data));
        } else if (data instanceof Object[]) {
            datas = Arrays.asList(((Object[]) data));
        }
        if (datas == null) {
            SimpleSQL simpleSQL = createSingleSql(metaData, data, param, "data.");
            param.setData(simpleSQL.getParams());
            simpleSQL.setParams(param);
            return simpleSQL;
        } else {
            SimpleSQL firstSql = createSingleSql(metaData, datas.get(0), param, "data[0].");
            List<BindSQL> bindSQLS = new ArrayList<>();
            List<Object> newParam = new ArrayList<>();
            newParam.add(firstSql.getParams());
            for (int i = 1; i < datas.size(); i++) {
                SimpleSQL sql = createSingleSql(metaData, datas.get(i), param, "data[" + i + "].");
                newParam.add(sql.getParams());
                sql.setParams(param);
                bindSQLS.add(new BindSQL(sql));
            }
            firstSql.setBindSQLs(bindSQLS);
            param.setData(newParam);
            firstSql.setParams(param);
            return firstSql;
        }
    }

    protected SqlAppender getParamString(String prefix, String paramName, RDBColumnMetaData rdbColumnMetaData) {
        return new SqlAppender().add("#{", prefix, paramName, "}");
    }
}
