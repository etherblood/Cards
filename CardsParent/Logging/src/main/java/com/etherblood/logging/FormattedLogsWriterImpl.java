package com.etherblood.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FormattedLogsWriterImpl implements FormattedLogsWriter {
    private static final String NULL_STRING = "null";
    private final String PLACEHOLDER = "{}";
    private final int PLACEHOLDER_LENGTH = PLACEHOLDER.length();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS Z");
    
    @Override
    public void append(PrintWriter writer, String message, Object[] params) throws IOException {
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

    @Override
    public void append(PrintWriter writer, Object object) throws IOException {
        if(object instanceof Date) {
            writer.append(dateFormat.format((Date)object));
        } else if(object instanceof Throwable) {
            ((Throwable)object).printStackTrace(writer);
        } else {
            writer.append(Objects.toString(object, NULL_STRING));
        }
    }

}
