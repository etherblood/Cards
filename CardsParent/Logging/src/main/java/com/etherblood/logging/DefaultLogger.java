package com.etherblood.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class DefaultLogger implements Logger {
    private LogLevel minimumLogLevel;
    private FormattedLogsWriter formatter = new FormattedLogsWriterImpl();
    private final PrintWriter writer;

    public DefaultLogger(PrintWriter writer) {
        this.writer = writer;
        setMinimumLogLevel(LogLevel.INFO);
    }
    
    @Override
    public void log(LogLevel level, String message) {
        log(level, message, (Object[])null);
    }

    @Override
    public void log(LogLevel level, String message, Object... arguments) {
        if(acceptsLogLevel(level)) {
            try {
                formatter.append(writer, new Date());
                writer.append(' ');
                writer.append(Thread.currentThread().getName());
                writer.append(' ');
                writer.append(level.toString());
                writer.append(' ');
                formatter.append(writer, message, arguments);
                writer.append(System.lineSeparator());
                writer.flush();
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

    public void setMinimumLogLevel(LogLevel minimumLogLevel) {
        this.minimumLogLevel = minimumLogLevel;
        log(LogLevel.ALWAYS, "LogLevel was set to {}", minimumLogLevel);
    }

    public FormattedLogsWriter getFormatter() {
        return formatter;
    }

    public void setFormatter(FormattedLogsWriter formatter) {
        this.formatter = formatter;
    }

    public PrintWriter getWriter() {
        return writer;
    }

}
