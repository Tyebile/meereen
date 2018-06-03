package com.tyebile.meereen.logging.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.tyebile.meereen.logging.api.AccessLoggerInfo;

@AllArgsConstructor
@Getter
public class AccessLoggerAfterEvent {

    private AccessLoggerInfo logger;
}
