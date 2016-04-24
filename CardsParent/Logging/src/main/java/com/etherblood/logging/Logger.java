package com.etherblood.logging;

/**
 *
 * @author Philipp
 */
public interface Logger {
    void log(LogLevel level, String message, Object... arguments);
    void log(LogLevel level, String message);
    void log(LogLevel level, Object obj);
    boolean acceptsLogLevel(LogLevel level);
}
