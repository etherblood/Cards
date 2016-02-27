package com.etherblood.match;

import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class MatchContextBuilder {
    private final ArrayList<Object> beans = new ArrayList<>();
    private final ArrayList<Object> passiveBeans = new ArrayList<>();
    
    public void addBean(Object bean) {
        beans.add(bean);
    }
    public void addPassiveBean(Object bean) {
        passiveBeans.add(bean);
    }
    
    public MatchContext build() {
        MatchContext context = new MatchContext(beans);
        context.populateAll(passiveBeans);
        return context;
    }
}
