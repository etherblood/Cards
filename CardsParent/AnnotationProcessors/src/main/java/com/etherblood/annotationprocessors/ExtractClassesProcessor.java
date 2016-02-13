/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.annotationprocessors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 *
 * @author Philipp
 */
public class ExtractClassesProcessor extends AbstractProcessor {

    private final Class<? extends Annotation> annotationClass;
    private final String packageName;
    private final String className;

    public ExtractClassesProcessor(Class<? extends Annotation> annotationClass, String packageName, String className) {
        this.annotationClass = annotationClass;
        this.packageName = packageName;
        this.className = className;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        
        ArrayList<Element> elements = new ArrayList<>();
        for (Element e : roundEnv.getElementsAnnotatedWith(annotationClass)) {
            if (e.getKind() == ElementKind.CLASS || e.getKind() == ElementKind.ENUM) {
                elements.add(e);
            } else {
                System.err.println(e.getSimpleName() + " has @" + annotationClass.getCanonicalName() + " but is not a class or enum.");
            }
        }
        return process(elements);
    }

    private boolean process(List<Element> elements) {
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
        BufferedWriter bw = null;
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(packageName + "." + className);

            bw = new BufferedWriter(jfo.openWriter());
            bw.append("//generated code, do not modify!".toUpperCase());
            bw.newLine();
            bw.append("//this class contains all classes with");
            bw.newLine();
            bw.append("//@" + annotationClass.getCanonicalName());
            bw.newLine();
            bw.append("//last update: " + new Date().toString());
            bw.newLine();
            bw.append("package ");
            bw.append(packageName);
            bw.append(";");
            bw.newLine();
            bw.append("public class " + className + " {");
            bw.newLine();
            bw.append("public final Class[] classes = new Class[]{");
            if (!types.isEmpty()) {
                bw.newLine();
                bw.append(types.get(0).getQualifiedName().toString() + ".class");
            }
            for (int i = 1; i < types.size(); i++) {
                bw.append(",");
                bw.newLine();
                bw.append(types.get(i).getQualifiedName().toString() + ".class");
            }
            bw.newLine();
            bw.append("};");
            bw.newLine();
            bw.append("}");
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(ExtractClassesProcessor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExtractClassesProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;
    }
}
