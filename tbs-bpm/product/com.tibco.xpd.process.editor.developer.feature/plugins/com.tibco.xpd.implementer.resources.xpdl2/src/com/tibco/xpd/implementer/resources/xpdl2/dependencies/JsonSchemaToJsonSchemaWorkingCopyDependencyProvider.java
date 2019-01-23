/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.dependencies;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaWorkingCopy;

/**
 * Dependency provider for JSON Schema files to other JSON Schema files.
 * 
 * @author nwilson
 * @since 19 May 2015
 */
public class JsonSchemaToJsonSchemaWorkingCopyDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return JsonSchemaWorkingCopy.class;
    }

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     *            The working copy.
     * @return A list of JSON Schema resources that this JSON Schema resource
     *         depends on.
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Collection<IResource> resources = new HashSet<IResource>();
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                IProject project = resource.getProject();
                if (project != null) {
                    EObject root = wc.getRootElement();
                    if (root instanceof Package) {
                        Package pkg = (Package) root;
                        for (PackageableElement element : pkg
                                .getPackagedElements()) {
                            if (element instanceof org.eclipse.uml2.uml.Class) {
                                org.eclipse.uml2.uml.Class cls =
                                        (org.eclipse.uml2.uml.Class) element;
                                for (Property property : cls
                                        .getOwnedAttributes()) {
                                    Type type = property.getType();
                                    if (type != null) {
                                        IResource other =
                                                WorkingCopyUtil.getFile(type);
                                        if (!resource.equals(other)) {
                                            resources.add(other);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resources;
    }
}
