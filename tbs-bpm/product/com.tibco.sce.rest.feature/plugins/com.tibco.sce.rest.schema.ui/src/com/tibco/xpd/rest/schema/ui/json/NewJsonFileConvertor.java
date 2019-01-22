/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.json;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.rest.schema.ui.internal.Messages;

/**
 * Adds a default root class to an empty JSON Schema file.
 * 
 * @author nwilson
 * @since 28 Jan 2015
 */
public class NewJsonFileConvertor implements JsonImportConvertor {

    /**
     * @see com.tibco.xpd.rest.schema.ui.json.JsonImportConvertor
     *      #convert(org.eclipse.emf.ecore.resource.ResourceSet,
     *      org.eclipse.uml2.uml.Package)
     * 
     * @param rs
     *            The schema resource set.
     * @param root
     *            The schema root element.
     */
    @Override
    public void convert(ResourceSet rs, Package root) {
        Class cls =
                root.createOwnedClass(Messages.JsonSchemaEditor_newClassBaseName,
                        false);
        EAnnotation annotation = root.createEAnnotation(ROOT);
        annotation.setEModelElement(cls);
    }

}
