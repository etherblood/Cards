package com.etherblood.cardsmasterserver.logging;

import com.etherblood.logging.DefaultLogger;
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
    private DefaultLogger logger;
    
    @PostConstruct
    public void init() throws FileNotFoundException, IOException {
        logger = new DefaultLogger(new PrintWriter(new File(logsPath + dateFormat.format(new Date()) + "_logs.txt")));
    }
    
    @PreDestroy
    public void cleanup() {
        logger.getWriter().close();
    }
    
    public Logger getLogger(Class serviceClass) {
        //TODO: return wrapper which also logs 'serviceClass'
        return logger;
    }
}
