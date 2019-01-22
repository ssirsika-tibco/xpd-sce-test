/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * Class Definition Reader that reads from UML model and provides the list of
 * class and methods from a given model (for eg: UML Model, BOM)
 * 
 * Doesn't have much to do with JS specifically.
 * 
 * @author rsomayaj
 * 
 */
public interface JsClassDefinitionReader {

    /**
     * This will return all the classes read from the model
     * 
     * @return
     */
    List<JsClass> getSupportedClasses();

    List<String> getSupportedClassNames();

    boolean isSupportedClass(String className);

    JsClass getJsClass(String className);

    Image getIcon();

}
