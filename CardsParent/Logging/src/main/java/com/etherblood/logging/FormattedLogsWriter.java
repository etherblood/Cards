package com.etherblood.logging;

import java.io.IOException;

/**
 *
 * @author Philipp
 */
public interface FormattedLogsWriter {
    void log(LogLevel level, String message, Object[] arguments) throws IOException;
}
