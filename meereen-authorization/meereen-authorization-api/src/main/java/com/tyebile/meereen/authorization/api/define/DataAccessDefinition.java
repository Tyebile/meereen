package com.tyebile.meereen.authorization.api.define;


import java.io.Serializable;

/**
 *
 * @author zhouhao
 * @see com.tyebile.meereen.authorization.api.annotation.RequiresDataAccess
 */
public interface DataAccessDefinition extends Serializable {

    String getController();

    String getIdParameterName();

    Phased getPhased();

}
