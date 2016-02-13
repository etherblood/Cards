package com.etherblood.cardsmatch.cardgame;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Philipp
 */
public class TemplateSet {
    private final Map<String, EntityTemplate> templates;

    public TemplateSet(Map<String, EntityTemplate> templates) {
        this.templates = templates;
    }
    public EntityTemplate getTemplate(String template) {
        return templates.get(template);
    }
    public String[] getTemplateNames() {
        String[] names = new String[templates.size()];
        templates.keySet().toArray(names);
        return names;
    }
    public String[] getCollectableTemplateNames() {
        ArrayList<String> names = new ArrayList<>();
        for (String name : templates.keySet()) {
            if(templates.get(name).isCollectible()) {
                names.add(name);
            }
        }
        return names.toArray(new String[names.size()]);
    }
}
