package com.etherblood.logging;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Philipp
 */
public interface FormattedLogsWriter {
    void append(PrintWriter writer, String message, Object[] params) throws IOException;
    void append(PrintWriter writer, Object object) throws IOException;
}
