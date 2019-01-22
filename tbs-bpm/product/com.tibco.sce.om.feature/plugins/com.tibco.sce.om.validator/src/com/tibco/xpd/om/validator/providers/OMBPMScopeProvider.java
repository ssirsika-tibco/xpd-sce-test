/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * This scope provider checks whether the "ActiveMatrix BPM" is set as the
 * destination env. on the resource's project.
 * <p>
 * NOTE: Only the {@link OrgModel}, {@link OrgElement} and {@link Attribute}
 * type objects will be returned as affected objects. Also, if a {@link Group}'s
 * name or display name changes then all <code>Group</code>s in the model will
 * also be returned as affected elements.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class OMBPMScopeProvider implements IScopeProvider {

    private static final String BPM_GLOBAL_DEST_ID =
            "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {

        if (item.getWorkingCopy() instanceof OMWorkingCopy) {
            OMWorkingCopy wc = (OMWorkingCopy) item.getWorkingCopy();
            if (isActiveMatrixBPMProject(getProject(wc))
                    && doValidate(item.getObjects())) {
                return getAffectedObject(item.getWorkingCopy());
            }
        }

        return new ArrayList<EObject>(0);
    }

    /**
     * Check if this scope provider needs to validate.
     * 
     * @param objects
     * @return <code>true</code> if the affected items contains an object that
     *         this scope provider is interested in validating.
     */
    private boolean doValidate(Collection<EObject> objects) {
        if (objects != null) {
            for (EObject eo : objects) {
                if (shouldInclude(eo)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get all affected elements from the given {@link WorkingCopy}.
     * 
     * @param workingCopy
     * @return
     */
    private List<EObject> getAffectedObject(WorkingCopy workingCopy) {
        List<EObject> objs = new ArrayList<EObject>();
        if (workingCopy != null && workingCopy.getRootElement() != null) {

            if (shouldInclude(workingCopy.getRootElement())) {
                objs.add(workingCopy.getRootElement());
            }

            TreeIterator<EObject> contents =
                    workingCopy.getRootElement().eAllContents();

            while (contents.hasNext()) {
                EObject eo = contents.next();

                if (shouldInclude(eo) && !objs.contains(eo)) {
                    objs.add(eo);
                }
            }
        }

        return objs;
    }

    /**
     * Check if this element should be included in the "to validate" items.
     * 
     * @param eo
     * @return
     */
    private boolean shouldInclude(EObject eo) {
        return eo instanceof OrgElement || eo instanceof Attribute
                || eo instanceof OrgModel;
    }

    /**
     * Check if the given project's destination env is set to ActiveMatrix BPM.
     * 
     * @param project
     * @return
     */
    private boolean isActiveMatrixBPMProject(IProject project) {
        if (project != null) {
            if (GlobalDestinationUtil.isGlobalDestinationEnabled(project,
                    BPM_GLOBAL_DEST_ID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the working copy resource's {@link IProject} container.
     * 
     * @param wc
     * @return
     */
    private IProject getProject(WorkingCopy wc) {
        if (wc != null && wc.getEclipseResources() != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                return resource.getProject();
            }
        }
        return null;
    }
}
