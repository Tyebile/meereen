package com.tyebile.meereen.orm.core;

import com.tyebile.meereen.orm.core.param.Param;

import java.sql.SQLException;

public interface Delete extends Conditional<Delete>, TriggerSkipSupport<Delete> {
    Delete setParam(Param param);

    int exec() throws SQLException;
}
