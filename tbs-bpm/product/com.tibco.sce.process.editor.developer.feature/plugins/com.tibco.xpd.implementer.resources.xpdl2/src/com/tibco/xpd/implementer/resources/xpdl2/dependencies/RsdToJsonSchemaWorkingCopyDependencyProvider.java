/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.dependencies;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;

/**
 * Dependency provider for RSD files to JSON Schema files.
 * 
 * @author nwilson
 * @since 19 May 2015
 */
public class RsdToJsonSchemaWorkingCopyDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return RsdWorkingCopy.class;
    }

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     *            The working copy.
     * @return A list of JSON Schema resources that this RSD resource depends
     *         on.
     */
    @Override
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Collection<IResource> resources = new HashSet<IResource>();
        if (wc != null) {
            IResource xpdlResource = wc.getEclipseResources().get(0);
            if (xpdlResource != null) {
                IProject project = xpdlResource.getProject();
                if (project != null) {
                    EditingDomain ed = wc.getEditingDomain();
                    EObject root = wc.getRootElement();
                    if (root instanceof Service) {
                        Service service = (Service) root;
                        for (Resource resource : service.getResources()) {
                            for (Method method : resource.getMethods()) {
                                addDependency(project,
                                        ed,
                                        resources,
                                        method.getRequest());
                                addDependency(project,
                                        ed,
                                        resources,
                                        method.getResponse());
                                for (Fault fault : method.getFaults()) {
                                    addDependency(project, ed, resources, fault);
                                }
                            }
                        }
                    }
                }
            }
        }
        return resources;
    }

    /**
     * Looks up the schema model element for the payload reference and adds the
     * resource to the dependencies.
     * 
     * @param project
     *            The project containing the RSD file.
     * @param ed
     *            The editing domain.
     * @param resources
     *            Resources to add to.
     * @param container
     *            The payload reference container.
     */
    private void addDependency(IProject project, EditingDomain ed,
            Collection<IResource> resources, PayloadRefContainer container) {
        if (container != null) {
            PayloadReference ref = container.getPayloadReference();
            if (ref != null) {
                JsonTypeReference typeRef =
                        JsonTypeReference.getJsonReference(ref);
                Classifier classifier = typeRef.resolveReference(ed, project);
                if (classifier != null) {
                    IResource file = WorkingCopyUtil.getFile(classifier);
                    if (file != null) {
                        resources.add(file);
                    }
                }
            }
        }
    }
}
