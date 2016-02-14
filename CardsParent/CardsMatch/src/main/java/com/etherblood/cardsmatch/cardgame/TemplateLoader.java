package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.entitysystem.data.EntityComponent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Philipp
 */
public class TemplateLoader {
        List<EntityTemplate> templates = new ArrayList<>();
        HashMap<String, String> aliases = new HashMap<>();
    public List<EntityTemplate> read(String pathName) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = (Document) builder.build(TemplateLoader.class.getClassLoader().getResourceAsStream(pathName));
            Element rootNode = document.getRootElement();
            
            Element aliasElements = rootNode.getChild("aliases");
            for (Element alias : aliasElements.getChildren()) {
                aliases.put(alias.getAttributeValue("key"), alias.getAttributeValue("value"));
            }
            
            List<Element> list = rootNode.getChild("templates").getChildren();
            for (Element templateElement : list) {
                String name = templateElement.getAttributeValue("name");
                EntityTemplate template = new EntityTemplate();
                template.add(new NameComponent(name));
                template.setCollectible(templateElement.getChild("collectible") != null);
                Element componentsElement = templateElement.getChild("components");
                if(componentsElement != null) {
                    for (Element component : componentsElement.getChildren()) {
                        try {
                            template.add(parse(component));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
                Element childsElement = templateElement.getChild("childs");
                if(childsElement != null) {
                    for (Element child : childsElement.getChildren()) {
                        template.addChild(child.getText());
                    }
                }
                templates.add(template);
            }
        } catch (IOException | JDOMException ex) {
            System.out.println(ex);
        }
        return templates;
    }
    
    private EntityComponent parse(Element componentElement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, IllegalArgumentException, InvocationTargetException {
        String componentClassName = aliases.get(componentElement.getName());
        if(componentClassName == null) {
            componentClassName = componentElement.getName();
        }
        Class<?> componentClass = ClassLoader.getSystemClassLoader().loadClass(componentClassName);
        Constructor<?> constructor = componentClass.getConstructors()[0];
        Class[] argTypes = constructor.getParameterTypes();
        Object[] args = new Object[argTypes.length];
        int i = 0;
        for (Attribute attribute : componentElement.getAttributes()) {
            args[i] = parseValue(argTypes[i], attribute.getValue());
            i++;
        }
        return (EntityComponent) constructor.newInstance(args);
    }
    
    private Object parseValue(Class type, String value) throws IllegalArgumentException, IllegalAccessException {
        if(type == int.class) {
            return Integer.parseInt(value);
        } else if(type == long.class) {
            return Long.parseLong(value);
        } else if(type == String.class) {
            return value;
        }
        throw new UnsupportedOperationException(type.getName());
    }
}
