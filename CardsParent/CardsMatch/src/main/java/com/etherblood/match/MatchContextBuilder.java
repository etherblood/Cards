package com.etherblood.match;

import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class MatchContextBuilder {
    private final ArrayList<Object> beans = new ArrayList<>();
    private final ArrayList<Object> hiddenBeans = new ArrayList<>();
    
    public void addBean(Object bean) {
        beans.add(bean);
    }
    public void addHiddenBean(Object bean) {
        hiddenBeans.add(bean);
    }
    
    public MatchContext build() {
        MatchContext context = new MatchContext(beans);
        context.populateAll(hiddenBeans);
        return context;
    }
}
