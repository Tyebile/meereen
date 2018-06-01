/*
 *  Copyright 2016 http://www.hswebframework.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package com.tyebile.meereen.authorization.api.listener;

import com.tyebile.meereen.authorization.api.listener.event.AuthorizationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@link ApplicationEventPublisher}
 * @author zhouhao
 */
@Deprecated
public class AuthorizationListenerDispatcher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    private Map<Class<? extends AuthorizationEvent>, List<AuthorizationListener>> listenerStore = new HashMap<>();

    public <E extends AuthorizationEvent> void addListener(Class<E> eventClass, AuthorizationListener<E> listener) {
        listenerStore.computeIfAbsent(eventClass, (k) -> new LinkedList<>())
                .add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends AuthorizationEvent> int doEvent(Class<E> eventType, E event) {
        eventPublisher.publishEvent(event);
//        List<AuthorizationListener<E>> store = (List) listenerStore.get(eventType);
//        if (null != store) {
//            store.forEach(listener -> listener.on(event));
//            return store.size();
//        }
        return 1;
    }

    @SuppressWarnings("unchecked")
    public <E extends AuthorizationEvent> int doEvent(E event) {
        eventPublisher.publishEvent(event);
        return 1;
        //return doEvent((Class<E>) event.getClass(), event);
    }
}
