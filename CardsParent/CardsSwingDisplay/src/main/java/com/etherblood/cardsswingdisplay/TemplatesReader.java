package com.etherblood.cardsswingdisplay;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Philipp
 */
public class TemplatesReader {

    public final static TemplatesReader INSTANCE = new TemplatesReader();

    public void read(String rootPath) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document document = (Document) builder.build(TemplatesReader.class.getClassLoader().getResourceAsStream(rootPath + "templates.xml"));
            Element rootNode = document.getRootElement();
            Element templatesNode = rootNode.getChild("templates");
            List<Element> list = templatesNode.getChildren("template");
            for (Element element : list) {
                String name = element.getAttributeValue("name");
                Element image = element.getChild("image");
                if(image != null) {
                    String path = image.getChildText("path");
                    String url = image.getChildText("url");
                    try {
                        if (path != null) {
                            ImageFactory.INSTANCE.registerPath(name, path);
                        } else if (url != null) {
                            ImageFactory.INSTANCE.registerUrl(name, url);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                
                Element audio = element.getChild("audio");
                if(audio != null) {
                    registerAudio(rootPath, audio, "summon", name);
                    registerAudio(rootPath, audio, "death", name);
                    registerAudio(rootPath, audio, "intro", name);
                    registerAudio(rootPath, audio, "attack", name);
                }
            }
        } catch (IOException | JDOMException ex) {
            System.out.println(ex);
        }
    }

    private void registerAudio(String rootPath, Element audioElement, String audioTrigger, String cardName) {
        Element triggerElement = audioElement.getChild(audioTrigger);
        if(triggerElement != null) {
            String path = triggerElement.getChildText("path");
            String url = triggerElement.getChildText("url");
             try {
                if (path != null) {
                    AudioFactory.INSTANCE.registerSound(cardName + audioTrigger, new File(rootPath + path));
                } else if (url != null) {
                    AudioFactory.INSTANCE.registerSound(cardName + audioTrigger, new URL(url));
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
