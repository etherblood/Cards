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
@SupportedAnnotationTypes("com.jme3.network.serializing.Serializable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SerializationProcessor1 extends ExtractClassesProcessor {

    private static final String packageName = "generatedSerializable1";
    private static final String className = "Serializables1";

    /**
     * public for ServiceLoader
     */
    public SerializationProcessor1() {
        super(com.jme3.network.serializing.Serializable.class, packageName, className);
    }
}
