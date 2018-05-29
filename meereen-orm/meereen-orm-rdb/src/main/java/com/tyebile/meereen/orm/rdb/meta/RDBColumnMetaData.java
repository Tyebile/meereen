package com.tyebile.meereen.orm.rdb.meta;

import com.tyebile.meereen.orm.core.ValueConverter;
import com.tyebile.meereen.orm.core.meta.AbstractColumnMetaData;
import com.tyebile.meereen.orm.core.meta.ColumnMetaData;
import com.tyebile.meereen.orm.rdb.meta.converter.DefaultValueConverter;

import java.io.Serializable;
import java.sql.JDBCType;

public class RDBColumnMetaData extends AbstractColumnMetaData implements ColumnMetaData, Serializable, Cloneable, Comparable<RDBColumnMetaData> {
    private static final DefaultValueConverter DEFAULT_VALUE_CONVERTER = new DefaultValueConverter();

    public RDBColumnMetaData() {
    }

    public RDBColumnMetaData(String name, Class javaType, String dataType, JDBCType jdbcType) {
        this.name = name;
        this.javaType = javaType;
        this.dataType = dataType;
        this.jdbcType = jdbcType;
    }

    private String dataType;

    /**
     * 长度
     *
     * @since 1.1
     */
    private int length;

    /**
     * 精度
     *
     * @since 1.1
     */
    private int precision;

    /**
     * 小数位数
     *
     * @since 1.1
     */
    private int scale;

    /**
     * 是否不能为空
     */
    private boolean notNull;

    /**
     * 是否主键
     */
    private boolean primaryKey;

    /**
     * 列定义
     *
     * @since 3.0
     */
    private String columnDefinition;

    private JDBCType jdbcType;

    private int sortIndex;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    @SuppressWarnings("all")
    public RDBTableMetaData getTableMetaData() {
        return super.getTableMetaData();
    }

    public String getFullName() {
        return tableMetaData == null ? getName() : tableMetaData.getName() + "." + getName();
    }

    public String getFullAliasName() {
        return tableMetaData == null ? getAlias() : tableMetaData.getAlias() + "." + getAlias();
    }

    public JDBCType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JDBCType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    @Override
    public ValueConverter getValueConverter() {
        if (valueConverter == null) {
            valueConverter = DEFAULT_VALUE_CONVERTER;
        }
        return super.getValueConverter();
    }

    @Override
    public int compareTo(RDBColumnMetaData o) {
        return Integer.compare(sortIndex, o.getSortIndex());
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isNotNull() {
        if (!notNull && isPrimaryKey()) {
            notNull = true;
        }
        return notNull;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    @SuppressWarnings("all")
    public RDBColumnMetaData clone() {
        RDBColumnMetaData column = new RDBColumnMetaData();
        column.name = name;
        column.alias = alias;
        column.comment = comment;
        column.javaType = javaType;
        column.jdbcType = jdbcType;
        column.dataType = dataType;
        column.properties = properties;
        column.optionConverter = optionConverter;
        column.tableMetaData = tableMetaData;
        column.sortIndex = sortIndex;
        column.length = length;
        column.scale = scale;
        column.precision = precision;
        column.notNull = notNull;
        column.primaryKey = primaryKey;
        column.columnDefinition = columnDefinition;
        column.defaultValue=defaultValue;
        column.valueConverter=valueConverter;
        return column;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", comment='" + comment + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
