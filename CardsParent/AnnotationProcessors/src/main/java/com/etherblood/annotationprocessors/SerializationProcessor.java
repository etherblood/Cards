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
public class SerializationProcessor extends ExtractClassesProcessor {

    private static final String packageName = "generatedSerializable";
    private static final String className = "Serializables";

    /**
     * public for ServiceLoader
     */
    public SerializationProcessor() {
        super(com.jme3.network.serializing.Serializable.class, packageName, className);
    }
}
