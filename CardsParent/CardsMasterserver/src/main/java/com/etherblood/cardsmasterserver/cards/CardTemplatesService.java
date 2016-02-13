package com.etherblood.cardsmasterserver.cards;

import com.etherblood.cardsmatch.cardgame.DefaultTemplateSetFactory;
import com.etherblood.cardsmatch.cardgame.TemplateSet;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philipp
 */
@Service
public class CardTemplatesService {
    @Value("${cards.templates.path}")
    private String templatesPath;
    private TemplateSet templates;
    private String[] collectibleNames;

    @PostConstruct
    public void init() {
        templates = new DefaultTemplateSetFactory().createEntityTemplates(templatesPath);
        collectibleNames = templates.getCollectableTemplateNames();
    }
    
    public TemplateSet getAll() {
        return templates;
    }

    public String[] getCollectibles() {
        return collectibleNames;
    }

}
