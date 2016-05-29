package com.etherblood.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FormattedLogsWriterImpl implements FormattedLogsWriter {
    private static final String NULL_STRING = "null";
    private static final String PLACEHOLDER = "{}";
    private static final int PLACEHOLDER_LENGTH = PLACEHOLDER.length();
    private final SimpleDateFormat dateFormat;
    private final PrintWriter writer;

    public FormattedLogsWriterImpl(PrintWriter writer) {
        this(writer, new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS Z"));
    }
    public FormattedLogsWriterImpl(PrintWriter writer, SimpleDateFormat dateFormat) {
        this.writer = writer;
        this.dateFormat = dateFormat;
    }

    @Override
    public void log(LogLevel level, String message, Object[] arguments) throws IOException {
        append(writer, new Date());
        writer.append(' ');
        writer.append(Thread.currentThread().getName());
        writer.append(' ');
        writer.append(level.toString());
        writer.append(": ");
        append(writer, message, arguments);
        writer.append(System.lineSeparator());
        writer.flush();
    }
    
    protected void append(PrintWriter writer, String message, Object[] params) throws IOException {
        if(params == null || params.length == 0) {
            writer.append(message);
        } else {
            int index = 0;
            for (Object param : params) {
                int next = message.indexOf(PLACEHOLDER, index);
                if(next == -1) {
                    break;
                }
                if(next != index) {
                    writer.append(message, index, next);
                }
                index = next + PLACEHOLDER_LENGTH;
                append(writer, param);
            }
        }
    }

    protected void append(PrintWriter writer, Object object) throws IOException {
        if(object instanceof Date) {
            writer.append(dateFormat.format((Date)object));
        } else if(object instanceof Throwable) {
            ((Throwable)object).printStackTrace(writer);
        } else {
            writer.append(Objects.toString(object, NULL_STRING));
        }
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public PrintWriter getWriter() {
        return writer;
    }

}
