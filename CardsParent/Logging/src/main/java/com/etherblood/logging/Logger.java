package com.etherblood.logging;

/**
 *
 * @author Philipp
 */
public interface Logger {
    void log(LogLevel level, String message, Object... arguments);
    void log(LogLevel level, String message);
    boolean acceptsLogLevel(LogLevel level);
}
