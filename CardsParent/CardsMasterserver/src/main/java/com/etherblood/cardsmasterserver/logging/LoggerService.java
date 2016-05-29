package com.etherblood.cardsmasterserver.logging;

import com.etherblood.logging.DefaultLogger;
import com.etherblood.logging.FormattedLogsWriterImpl;
import com.etherblood.logging.LogLevel;
import com.etherblood.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philipp
 */
@Service
public class LoggerService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
    @Value("${logs.path}")
    private String logsPath;
    private PrintWriter printWriter;
    
    @PostConstruct
    public void init() throws IOException {
        printWriter = new PrintWriter(new File(logsPath + dateFormat.format(new Date()) + "_logs.txt"));
    }
    
    @PreDestroy
    public void cleanup() {
        printWriter.close();
    }
    
    public Logger getLogger(Class serviceClass) {
        return new DefaultLogger(new FormattedLogsWriterImpl(printWriter){
            @Override
            public void log(LogLevel level, String message, Object[] arguments) throws IOException {
                PrintWriter writer = getWriter();
                append(writer, new Date());
                writer.append(' ');
                writer.append(serviceClass.getName());
                writer.append(' ');
                writer.append(Thread.currentThread().getName());
                writer.append(' ');
                writer.append(level.toString());
                writer.append(": ");
                append(writer, message, arguments);
                writer.append(System.lineSeparator());
                writer.flush();
            }
            
        });
    }
}
