/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.resources.wc.gmf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * Abstract implementation of WorkingCopy that uses GMF (transactional) editing
 * domain.
 * 
 * @see AbstractTransactionalWorkingCopy
 * 
 * @author njpatel
 */
public abstract class AbstractGMFWorkingCopy extends
        AbstractTransactionalWorkingCopy {

    /**
     * The Constructor.
     * 
     * @param resources
     *            set of resources for this working copy
     */
    public AbstractGMFWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    /**
     * Gets the <code>Diagram</code> elements in the working copy.
     * 
     * @return list of <code>Diagram</code> elements in the working copy, or
     *         empty list if none found.
     */
    public List<Diagram> getDiagrams() {
        List<Diagram> diagrams = new ArrayList<Diagram>();

        if (!isInvalidFile()) {
            EObject root = getRootElement();

            if (root != null) {
                Resource resource = root.eResource();

                if (resource != null) {
                    EList<?> contents = resource.getContents();
                    for (Object obj : contents) {
                        if (obj instanceof Diagram) {
                            diagrams.add((Diagram) obj);
                        }
                    }
                }
            }
        }

        return diagrams;
    }

    /**
     * Gets <code>Diagram</code> associated with the given URI fragment.
     * 
     * @param uriFragment
     *            id of the diagram
     * @return <code>Diagram</code> associated with <code>URI</code> fragment,
     *         or <code>null</code> if no diagram found.
     */
    public Diagram getDiagram(String uriFragment) {
        Diagram diagram = null;

        if (uriFragment != null) {

            // If the model is not loaded then do so
            if (!isLoaded()) {
                getRootElement();
            }

            Collection<Resource> resources = getResources();

            if (resources != null) {
                for (Resource resource : resources) {
                    EObject object = resource.getEObject(uriFragment);

                    if (object instanceof Diagram) {
                        diagram = (Diagram) object;
                        break;
                    }
                }
            }
        }

        return diagram;
    }

}
