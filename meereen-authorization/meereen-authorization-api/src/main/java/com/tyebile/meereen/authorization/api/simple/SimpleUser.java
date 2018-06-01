package com.tyebile.meereen.authorization.api.simple;

import lombok.*;
import com.tyebile.meereen.authorization.api.User;

/**
 * @author zhouhao
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleUser implements User {

    private static final long serialVersionUID = 2194541828191869091L;

    private String id;

    private String username;

    private String name;
    
    private String type;
}
