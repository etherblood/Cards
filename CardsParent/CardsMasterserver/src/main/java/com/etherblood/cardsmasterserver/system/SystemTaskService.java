package com.etherblood.cardsmasterserver.system;

import com.etherblood.cardsmasterserver.network.connections.DefaultAuthentication;
import com.etherblood.cardsmasterserver.users.UserRoles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 * @author Philipp
 */
@Service
public class SystemTaskService {
    @Value("${system.threads}")
    private int threadCount;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    private ExecutorService executor;
    private final DefaultAuthentication systemAuthentication = new DefaultAuthentication(DefaultAuthentication.SYSTEM_PRINCIPAL, null, UserRoles.SYSTEM);
    
    @PreAuthorize("permitAll")
    @TransactionalEventListener(fallbackExecution=true)
    public void onSystemTaskRequest(final SystemTaskEvent task) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                SecurityContextHolder.getContext().setAuthentication(systemAuthentication);
                try {
                    eventPublisher.publishEvent(task.getEvent());
                } catch(Exception e) {
                    e.printStackTrace(System.err);
                } finally {
                    SecurityContextHolder.clearContext();
                }
            }
        });
    }
    
    @PostConstruct
    @PreAuthorize("denyAll")
    public void init() {
        executor = Executors.newFixedThreadPool(threadCount);
    }
    
    @PreDestroy
    @PreAuthorize("denyAll")
    public void cleanup() {
        executor.shutdown();
    }
    
}
