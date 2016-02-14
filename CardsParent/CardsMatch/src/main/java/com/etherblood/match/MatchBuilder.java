package com.etherblood.match;

import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class MatchBuilder {
    private final ArrayList<Object> beans = new ArrayList<>();
    
    public void addBean(Object bean) {
        beans.add(bean);
    }
    
    public MatchContext build() {
        return new MatchContext(beans);
    }
}
