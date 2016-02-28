/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.annotations;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 *
 * @author Philipp
 */
@SupportedAnnotationTypes("com.jme3.network.serializing.Serializable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SerializationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            System.out.println(annotation);
            ArrayList<Element> elements = new ArrayList<>();
            for (Element e : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (e.getKind() == ElementKind.CLASS || e.getKind() == ElementKind.ENUM) {
                    elements.add(e);
                } else {
                    System.err.println(e.getSimpleName() + " has @" + annotation.getQualifiedName() + " but is not a class or enum.");
                }
            }
            if (!process(elements, annotation)) {
                return false;
            }
        }
        return true;
    }

    private boolean process(List<Element> elements, TypeElement annotation) {
        ArrayList<TypeElement> types = new ArrayList<>();
        for (Element element : elements) {
            TypeElement type = (TypeElement) element;
            types.add(type);
        }
        Collections.sort(types, new Comparator<TypeElement>() {
            @Override
            public int compare(TypeElement o1, TypeElement o2) {
                return o1.getQualifiedName().toString().compareToIgnoreCase(o2.getQualifiedName().toString());
            }
        });
        try {
            final Filer filer = processingEnv.getFiler();
            FileObject fo = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/" + annotation.getQualifiedName() + ".txt");
            try (BufferedWriter writer = new BufferedWriter(fo.openWriter())) {
                if (!types.isEmpty()) {
                    writer.append(types.get(0).getQualifiedName().toString());
                }
                for (int i = 1; i < types.size(); i++) {
                    writer.newLine();
                    writer.append(types.get(i).getQualifiedName().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }
}
