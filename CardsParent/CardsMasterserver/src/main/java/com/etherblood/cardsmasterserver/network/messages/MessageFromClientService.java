package com.etherblood.cardsmasterserver.network.messages;

import com.etherblood.cardsmasterserver.network.connections.UserConnectionService;
import com.etherblood.cardsnetworkshared.DefaultMessage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philipp
 */
@Service
public class MessageFromClientService implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private final HashMap<Class, List<UserMessageHandler>> messageHandlers = new HashMap<Class, List<UserMessageHandler>>();
    private ApplicationContext context;

    @PreAuthorize("permitAll")
    public void dispatchMessage(Object message) {
//        UserMessageHandler messageHandler = messageHandlers.get(message.getClass());
        for (UserMessageHandler messageHandler : messageHandlers.get(message.getClass())) {
            try {
                messageHandler.onMessage(message);
            } catch (Exception e) {
//            System.out.println(e);
                throw new RuntimeException("exception when dispatching " + message + " to " + messageHandler, e);
            }
        }
    }

//    @PreAuthorize("hasRole('ROLE_SYSTEM')")
//    public <T extends Message> void registerMessageHandler(Class<T> messageClass, MessageHandler<T> messageHandler) {
//        if(messageHandlers.put(messageClass, messageHandler) != null) {
//            throw new RuntimeException("MessageHandler<" + messageClass + "> was overwritten.");
//        }
//    }
    @Override
    @PreAuthorize("denyAll")
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry bdr) throws BeansException {
        try {
            for (String string : bdr.getBeanDefinitionNames()) {
                final Class loadClass = getClass().getClassLoader().loadClass(bdr.getBeanDefinition(string).getBeanClassName());
                for (final Method method : loadClass.getMethods()) {
                    if (method.isAnnotationPresent(MessageHandler.class)) {
                        final Class messageClass = method.getParameterTypes()[0];
                        getHandlers(messageClass).add(new UserMessageHandler() {
                            @Override
                            public void onMessage(Object message) throws Exception {
                                UserConnectionService connectionService = context.getBean(UserConnectionService.class);
                                Object bean = context.getBean(loadClass);
                                Object result = bean.getClass().getMethod(method.getName(), messageClass).invoke(bean, message);
                                if (result != null) {
                                    connectionService.returnMessage(new DefaultMessage(result));
                                }
                            }

                            @Override
                            public String toString() {
                                return loadClass.getName() + "." + method.getName() + "(" + messageClass.getName() + ")";
                            }
                        });
                    }
                }
            }
        } catch (NoSuchBeanDefinitionException | ClassNotFoundException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
    
    private List<UserMessageHandler> getHandlers(Class clazz) {
        List<UserMessageHandler> handlers = messageHandlers.get(clazz);
        if(handlers == null) {
            handlers = new ArrayList<>();
            messageHandlers.put(clazz, handlers);
        }
        return handlers;
    }

    @Override
    @PreAuthorize("denyAll")
    public void postProcessBeanFactory(ConfigurableListableBeanFactory clbf) throws BeansException {
    }

    @Override
    @PreAuthorize("denyAll")
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }
}
