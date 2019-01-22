/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.gen.extensions;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.workflow.ConfigurationException;

/**
 * Helper class for BOM oAW extensions.
 * <p>
 * <i>Created: 3 Jun 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class BOMExtensionHelper {

    public static void importTypesLibrary(Package model, String libraryURI,
            String libraryName) {
        final URI uri = URI.createURI(libraryURI);
        ResourceSet resourceSet = (model.eResource() != null)
                && (model.eResource().getResourceSet() != null) ? model
                .eResource().getResourceSet() : new ResourceSetImpl();
        final Resource r = resourceSet.createResource(uri);
        try {
            r.load(null);
        } catch (final IOException e) {
            throw new ConfigurationException(e);
        }
        if (r.getContents() == null) {
            throw new ConfigurationException(
                    "Empty content: (library expected) : " + libraryURI); //$NON-NLS-1$
        }
        Package umlPackage = (Package) EcoreUtil.getObjectByType(r
                .getContents(), UMLPackage.Literals.PACKAGE);

        model.createPackageImport(umlPackage);
    }

    public static void test(TypedElement e, Model m) {
        System.out.println(e);
        // Class c; ((Property)c.getOwnedAttributes()).getTy
    }

    public static void main(String[] args) {
        System.out.println(String.class.isAssignableFrom(Object.class));
        System.out.println(Object.class.isAssignableFrom(String.class));
        System.out.println(String.class.isInstance(Object.class));
        System.out.println(Object.class.isInstance(String.class));
    }
}
