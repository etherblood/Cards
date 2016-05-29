package com.etherblood.logging;

import java.io.IOException;


public class DefaultLogger implements Logger {
    private final LogLevel minimumLogLevel;
    private final FormattedLogsWriter formatter;

    public DefaultLogger(FormattedLogsWriter formatter) {
        this(formatter, LogLevel.INFO);
    }

    public DefaultLogger(FormattedLogsWriter formatter, LogLevel minimumLogLevel) {
        this.formatter = formatter;
        this.minimumLogLevel = minimumLogLevel;
        log(LogLevel.ALWAYS, "LogLevel was set to {}", this.minimumLogLevel);
    }
    
    @Override
    public void log(LogLevel level, String message) {
        log(level, message, (Object[])null);
    }

    @Override
    public void log(LogLevel level, Object obj) {
        log(level, "{}", obj);
    }

    @Override
    public final synchronized void log(LogLevel level, String message, Object... arguments) {
        if(acceptsLogLevel(level)) {
            try {
                formatter.log(level, message, arguments);
            } catch (IOException ex) {
                System.err.println(ex);
                ex.printStackTrace(System.err);
            }
        }
    }


    @Override
    public boolean acceptsLogLevel(LogLevel level) {
        return level.getLevel() >= minimumLogLevel.getLevel();
    }

    public LogLevel getMinimumLogLevel() {
        return minimumLogLevel;
    }

    public FormattedLogsWriter getFormatter() {
        return formatter;
    }

}
