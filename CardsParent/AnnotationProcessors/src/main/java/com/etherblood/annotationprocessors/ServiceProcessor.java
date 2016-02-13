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
 * @author Philipp
 */
@SupportedAnnotationTypes("processors.FactoryInstance")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ServiceProcessor extends ExtractClassesProcessor {

    private static final String packageName = "generatedFactoryInstances";
    private static final String className = "GeneratedInstancesClass";

    /**
     * public for ServiceLoader
     */
    public ServiceProcessor() {
        super(FactoryInstance.class, packageName, className);
    }
}
