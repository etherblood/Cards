package com.etherblood.cardsjmeclient.match.cards;

import com.etherblood.cardsjmeclient.match.cards.images.ImageData;
import com.etherblood.cardsjmeclient.match.cards.images.ImageFactory;
import java.awt.Rectangle;
import java.io.IOException;
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
                    ImageData data = new ImageData();
                    data.setName(name);
                    String path = image.getChildText("path");
                    data.setPath(path);
                    String url = image.getChildText("url");
                    data.setUrl(url);
                    Element view = image.getChild("view");
                    if(view != null) {
                        Rectangle rect = new Rectangle();
                        rect.x = Integer.parseInt(view.getChildText("x"));
                        rect.y = Integer.parseInt(view.getChildText("y"));
                        rect.width = Integer.parseInt(view.getChildText("width"));
                        rect.height = Integer.parseInt(view.getChildText("height"));
                        data.setView(rect);
                    }
                    ImageFactory.getInstance().register(data);
                }
                
                Element audio = element.getChild("audio");
                if(audio != null) {
                    registerAudio(audio, "summon", name);
                    registerAudio(audio, "death", name);
                    registerAudio(audio, "intro", name);
                    registerAudio(audio, "attack", name);
                }
            }
        } catch (IOException | JDOMException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void registerAudio(Element audioElement, String audioTrigger, String cardName) {
        Element triggerElement = audioElement.getChild(audioTrigger);
        if(triggerElement != null) {
            String path = triggerElement.getChildText("path");
            String url = triggerElement.getChildText("url");
             try {
                if (path != null) {
//                    AudioFactory.INSTANCE.registerSound(cardName + audioTrigger, path);
                } else if (url != null) {
//                    AudioFactory.INSTANCE.registerSound(cardName + audioTrigger, new URL(url));
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
