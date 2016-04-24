package com.etherblood.logging;

/**
 *
 * @author Philipp
 */
public enum LogLevel {
    NEVER(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), ALWAYS(5);
    
    private final int level;

    private LogLevel(int level) {
        this.level = level;
    }

    int getLevel() {
        return level;
    }
}
