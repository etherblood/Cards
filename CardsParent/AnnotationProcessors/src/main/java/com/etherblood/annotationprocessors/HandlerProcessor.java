/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etherblood.annotationprocessors;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 *
 * @author phili
 */
@SupportedAnnotationTypes("processors.HandlerAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class HandlerProcessor extends ExtractClassesProcessor {

    private static final String packageName = "generatedHandlers";
    private static final String className = "GeneratedHandlerClass";

    /**
     * public for ServiceLoader
     */
    public HandlerProcessor() {
        super(HandlerAnnotation.class, packageName, className);
    }
}