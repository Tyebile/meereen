package com.tyebile.meereen.authorization.api.simple.builder;

import com.tyebile.meereen.authorization.api.builder.AuthenticationBuilder;
import com.tyebile.meereen.authorization.api.builder.AuthenticationBuilderFactory;
import com.tyebile.meereen.authorization.api.builder.DataAccessConfigBuilderFactory;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public class SimpleAuthenticationBuilderFactory implements AuthenticationBuilderFactory {

    private DataAccessConfigBuilderFactory dataBuilderFactory;

    public SimpleAuthenticationBuilderFactory(DataAccessConfigBuilderFactory dataBuilderFactory) {
        this.dataBuilderFactory = dataBuilderFactory;
    }

    @Override
    public AuthenticationBuilder create() {
        return new SimpleAuthenticationBuilder(dataBuilderFactory);
    }
}
