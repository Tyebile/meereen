/*
 *
 *  * Copyright 2016 http://www.hswebframework.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.tyebile.meereen.starter.spring.boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tyebile.meereen.commons.utils.ThreadLocalUtils;
import com.tyebile.meereen.commons.entity.factory.EntityFactory;
import com.tyebile.meereen.commons.entity.factory.MapperEntityFactory;
import com.tyebile.meereen.core.convert.CustomMessageConverter;
import com.tyebile.meereen.starter.spring.boot.convert.FastJsonGenericHttpMessageConverter;
import com.tyebile.meereen.starter.spring.boot.convert.FastJsonHttpMessageConverter;
import com.tyebile.meereen.starter.spring.boot.entity.EntityFactoryInitConfiguration;
import com.tyebile.meereen.starter.spring.boot.entity.EntityProperties;
import com.tyebile.meereen.starter.spring.boot.resolver.AuthorizationArgumentResolver;
import com.tyebile.meereen.starter.spring.boot.resolver.JsonParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
@Configuration
//@ComponentScan("org.hswebframework.web")
@EnableConfigurationProperties(EntityProperties.class)
@ImportAutoConfiguration(EntityFactoryInitConfiguration.class)
public class HswebAutoConfiguration {

    @Autowired
    private EntityProperties entityProperties;

    @Autowired(required = false)
    private List<CustomMessageConverter> converters;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "fastjson")
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFeatures(
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse
        );
        converter.setConverters(converters);
        return converter;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "fastjson")
    public FastJsonGenericHttpMessageConverter fastJsonGenericHttpMessageConverter(EntityFactory entityFactory) {
        JSON.DEFAULT_PARSER_FEATURE |= Feature.DisableFieldSmartMatch.getMask();
        FastJsonGenericHttpMessageConverter converter = new FastJsonGenericHttpMessageConverter();
        converter.setFeatures(
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse
        );
        converter.setConverters(converters);
        ParserConfig.global = new ParserConfig() {
            @Override
            public ObjectDeserializer getDeserializer(Type type) {
                ObjectDeserializer derializer = getDeserializers().get(type);
                if (derializer != null) {
                    return derializer;
                }
                if (type instanceof Class) {
                    Class classType = ((Class) type);
                    if (classType.isEnum()) {
                        // TODO: 2018/4/12 支持EnumDict枚举的反序列化
                        return super.getDeserializer(type);
                    }
                    checkAutoType(type.getTypeName(), ((Class) type));
                    if (Modifier.isAbstract(classType.getModifiers()) || Modifier.isInterface(classType.getModifiers())) {
                        Class realType;
                        if (entityFactory != null && (realType = entityFactory.getInstanceType(classType)) != null) {
                            return new JavaBeanDeserializer(this, realType, type);
                        }
                    } else {
                        return new JavaBeanDeserializer(this, classType);
                    }
                }

                return super.getDeserializer(type);
            }
        };

        //fastjson.parser.autoTypeAccept
        ParserConfig.global.addAccept("org.hswebframework.web.entity.");
        ParserConfig.global.addDeny("org.hswebframework.ezorm.core.param.SqlTerm");
        return converter;
    }

    @Bean
    public JsonParamResolver jsonParamResolver(FastJsonGenericHttpMessageConverter fastJsonHttpMessageConverter) {
        return new JsonParamResolver(fastJsonHttpMessageConverter);
    }

    @Bean
    public AuthorizationArgumentResolver authorizationArgumentResolver() {
        return new AuthorizationArgumentResolver();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                super.addArgumentResolvers(argumentResolvers);
                argumentResolvers.addAll(handlerMethodArgumentResolvers);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptorAdapter() {
                    @Override
                    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                        //clear thread local
                        ThreadLocalUtils.clear();
                    }
                });
            }
        };
    }

    @Bean(name = "entityFactory")
    @ConditionalOnMissingBean(EntityFactory.class)
    public MapperEntityFactory mapperEntityFactory() {
        return new MapperEntityFactory(entityProperties.createMappers());
    }

    @Bean
    @ConditionalOnBean(MapperEntityFactory.class)
    public EntityFactoryInitConfiguration entityFactoryInitConfiguration() {
        return new EntityFactoryInitConfiguration();
    }

    @ConditionalOnMissingBean(DataSource.class)
    @ConditionalOnProperty(name = "spring.datasource.type")
    static class DataSourceAutoConfiguration {
        @Bean
        @ConfigurationProperties("spring.datasource")
        public DataSource dataSource(DataSourceProperties properties) {
            return properties.initializeDataSourceBuilder().build();
        }
    }

    @Bean
    public RestControllerExceptionTranslator restControllerExceptionTranslator() {
        return new RestControllerExceptionTranslator();
    }

}
