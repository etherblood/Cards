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
@SupportedAnnotationTypes("processors.EventValidator")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ValidatorProcessor extends ExtractClassesProcessor {

    private static final String packageName = "generatedValidators";
    private static final String className = "GeneratedValidatorClass";

    /**
     * public for ServiceLoader
     */
    public ValidatorProcessor() {
        super(HandlerAnnotation.class, packageName, className);
    }
}