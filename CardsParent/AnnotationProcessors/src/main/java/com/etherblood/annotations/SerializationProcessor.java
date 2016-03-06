package com.etherblood.annotations;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
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
            if (!process(roundEnv.getElementsAnnotatedWith(annotation), annotation)) {
                return false;
            }
        }
        return true;
    }

    private boolean process(Set<? extends Element> elements, TypeElement annotation) {
        ArrayList<TypeElement> types = new ArrayList<>();
        for (Element element : elements) {
            types.add((TypeElement) element);
        }
        Collections.sort(types, new Comparator<TypeElement>() {
            @Override
            public int compare(TypeElement o1, TypeElement o2) {
                return o1.getQualifiedName().toString().compareTo(o2.getQualifiedName().toString());
            }
        });
        try {
            final Filer filer = processingEnv.getFiler();
            FileObject fo = filer.createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/" + annotation.getQualifiedName() + ".txt");
            try (BufferedWriter writer = new BufferedWriter(fo.openWriter())) {
                for (int i = 0; i < types.size(); i++) {
                    if(i != 0) {
                        writer.newLine();
                    }
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