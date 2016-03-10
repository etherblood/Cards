package com.etherblood.cardsmatch.cardgame;

import com.etherblood.cardsmatch.cardgame.components.ComponentAlias;
import com.etherblood.cardsmatch.cardgame.components.misc.NameComponent;
import com.etherblood.entitysystem.data.EntityComponent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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

    private static final String ALIASES_PATH = "/META-INF/" + ComponentAlias.class.getName() + ".txt";
    private final List<EntityTemplate> templates = new ArrayList<>();
    private static final HashMap<String, Class> ALIASES;

    static {
        ALIASES = new HashMap<>();
        for (Class aliasClass : readClasses(ALIASES_PATH)) {
            ComponentAlias alias = (ComponentAlias) aliasClass.getAnnotation(ComponentAlias.class);
            ALIASES.put(alias.name(), aliasClass);
        }
    }

    private static Class[] readClasses(String path) {
        ArrayList<Class> classList = new ArrayList<>();
        Scanner scanner = new Scanner(TemplateLoader.class.getResourceAsStream(path), StandardCharsets.UTF_8.name());
        while (scanner.hasNext()) {
            String className = scanner.nextLine();
            try {
                classList.add(Class.forName(className));
            } catch (ClassNotFoundException ex) {
                System.err.println("class " + className + " not found");
                ex.printStackTrace(System.err);
            }
        }
        return classList.toArray(new Class[classList.size()]);
    }

    public List<EntityTemplate> read(String pathName) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(TemplateLoader.class.getClassLoader().getResourceAsStream(pathName));
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChild("templates").getChildren();
            for (Element templateElement : list) {
                String name = templateElement.getAttributeValue("name");
                EntityTemplate template = new EntityTemplate();
                template.add(new NameComponent(name));
                template.setCollectible(templateElement.getChild("collectible") != null);
                Element componentsElement = templateElement.getChild("components");
                if (componentsElement != null) {
                    for (Element component : componentsElement.getChildren()) {
                        try {
                            template.add(parse(component));
                        } catch (Exception ex) {
                            ex.printStackTrace(System.out);
                        }
                    }
                }
                Element childsElement = templateElement.getChild("childs");
                if (childsElement != null) {
                    for (Element child : childsElement.getChildren()) {
                        template.addChild(child.getText());
                    }
                }
                templates.add(template);
            }
        } catch (IOException | JDOMException ex) {
            ex.printStackTrace(System.out);
        }
        return templates;
    }

    private EntityComponent parse(Element componentElement) throws Exception {
        Class componentClass = ALIASES.get(componentElement.getName());
        Constructor[] constructors = componentClass.getConstructors();
        if (constructors.length != 1) {
            throw new RuntimeException("only components with single constructor are supported");
        }
        Constructor constructor = constructors[0];
        Class[] argTypes = constructor.getParameterTypes();
        Object[] args = new Object[argTypes.length];
        int i = 0;
        for (Attribute attribute : componentElement.getAttributes()) {
            args[i] = parseValue(argTypes[i], attribute.getValue());
            i++;
        }
        return (EntityComponent) constructor.newInstance(args);
    }

//    private EntityComponent parseEmptyConstructor(Element componentElement) throws Exception {
//        Class<?> componentClass = ALIASES.get(componentElement.getName());
//        Constructor<?> constructor = componentClass.getConstructor();
//        EntityComponent component = (EntityComponent) constructor.newInstance();
//        for (Attribute attribute : componentElement.getAttributes()) {
//            Field field = componentClass.getField(attribute.getName());
//            field.set(component, parseValue(field.getType(), attribute.getValue()));
//        }
//        return component;
//    }
    
    private Object parseValue(Class type, String value) throws IllegalArgumentException, IllegalAccessException {
        if (type == int.class) {
            return Integer.parseInt(value);
        } else if (type == long.class) {
            return Long.parseLong(value);
        } else if (type == String.class) {
            return value;
        }
        throw new UnsupportedOperationException(type.getName());
    }
}
