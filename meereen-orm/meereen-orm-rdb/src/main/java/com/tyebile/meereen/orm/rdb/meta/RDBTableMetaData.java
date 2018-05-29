package com.tyebile.meereen.orm.rdb.meta;


import com.tyebile.meereen.orm.core.meta.AbstractTableMetaData;
import com.tyebile.meereen.orm.core.meta.TableMetaData;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 表结构定义实体
 */
public class RDBTableMetaData extends AbstractTableMetaData<RDBColumnMetaData> implements TableMetaData, Serializable, Cloneable {
    //数据库定义实体
    private Set<Correlation> correlations = new LinkedHashSet<>();
    private RDBDatabaseMetaData databaseMetaData;

    @Override
    public RDBDatabaseMetaData getDatabaseMetaData() {
        return databaseMetaData;
    }

    public void setDatabaseMetaData(RDBDatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    @Override
    public RDBColumnMetaData findColumn(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(".")) {
            String[] tmp = name.split("[.]");
            RDBTableMetaData metaData = databaseMetaData.getTableMetaData(tmp[0]);
            if (metaData == null) {
                Correlation correlation = getCorrelation(tmp[0]);
                if (correlation != null) {
                    metaData = databaseMetaData.getTableMetaData(correlation.getTargetTable());
                }
            }
            if (metaData != null) {
                RDBColumnMetaData column = metaData.findColumn(tmp[1]);
                if (null != column){
                    return column;
                }
            }
        }
        RDBColumnMetaData metaData = columnMetaDataMap.get(name);
        if (metaData == null){
            metaData = aliasColumnMetaDataMap.get(name);
        }
        return metaData;
    }

    public boolean renameColumn(String old, String newName) {
        RDBColumnMetaData oldField = columnMetaDataMap.get(old);
        if (oldField != null) {
            columnMetaDataMap.remove(old);
            columnMetaDataMap.put(newName, oldField);
            oldField.setName(newName);
            return true;
        }
        return false;
    }

    @Override
    public RDBColumnMetaData getColumn(String name) {
        RDBColumnMetaData metaData = columnMetaDataMap.get(name);
        if (metaData == null){
            metaData = columnMetaDataMap.get(name);
        }
        return metaData;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Correlation getCorrelation(String target) {
        for (Correlation correlation : correlations) {
            if (correlation.getAlias().equals(target)){
                return correlation;
            }

        }
        for (Correlation correlation : correlations) {
            if (correlation.getTargetTable().equals(target)){
                return correlation;
            }

        }
        return null;
    }


    public Correlation addCorrelation(Correlation correlation) {
        correlation.setIndex(correlations.size());
        correlations.add(correlation);
        return correlation;
    }

    public Set<Correlation> getCorrelations() {
        return correlations;
    }

    public void setCorrelations(Set<Correlation> correlations) {
        this.correlations = correlations;
    }

    @Override
    public RDBTableMetaData addColumn(RDBColumnMetaData columnMetaData) {
        return super.addColumn(columnMetaData);
    }

    @Override
    public Set<RDBColumnMetaData> getColumns() {
        return super.getColumns();
    }

    @Override
    public RDBTableMetaData clone() {
        RDBTableMetaData metaData = new RDBTableMetaData();
        metaData.setName(this.name);
        metaData.setAlias(this.alias);
        metaData.setComment(this.comment);
        metaData.setValidator(this.validator);
        metaData.setProperties(properties);
        metaData.triggerBase = triggerBase;
        correlations.forEach(correlation -> metaData.addCorrelation(correlation.clone()));
        columnMetaDataMap.values().forEach(column -> metaData.addColumn(column.clone()));
        return metaData;
    }

    @Override
    public String toString() {
        return name + " [" + alias + "]" + "(" + comment + ")";
    }
}
