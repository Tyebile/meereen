package com.tyebile.meereen.authorization.api.simple;

import lombok.*;
import com.tyebile.meereen.authorization.api.Permission;
import com.tyebile.meereen.authorization.api.access.DataAccessConfig;

import java.util.Collections;
import java.util.Set;

/**
 * @author zhouhao
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePermission implements Permission {

    private static final long serialVersionUID = 7587266693680162184L;

    private String id;

    private Set<String> actions;

    private Set<DataAccessConfig> dataAccesses;


    public Set<String> getActions() {
        if (actions == null) {
            actions = Collections.emptySet();
        }
        return actions;
    }

    public Set<DataAccessConfig> getDataAccesses() {
        if (dataAccesses == null) {
            dataAccesses = Collections.emptySet();
        }
        return dataAccesses;
    }
}
