/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.wc;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.TransactionalWorkingCopyImpl;

/**
 * UML working copy.
 * 
 * @author njpatel
 * 
 */
public class UMLWorkingCopy extends TransactionalWorkingCopyImpl {

    /**
     * UML working copy.
     * 
     * @param resources
     */
    public UMLWorkingCopy(List<IResource> resources) {
        super(resources, UMLPackage.eINSTANCE);
    }

}
