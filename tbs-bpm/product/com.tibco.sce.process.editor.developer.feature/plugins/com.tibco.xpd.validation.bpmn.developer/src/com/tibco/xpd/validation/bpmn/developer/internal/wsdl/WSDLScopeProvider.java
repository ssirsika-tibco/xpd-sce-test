/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;

/**
 * Validation scope provider for WSDLs.
 * 
 * @author njpatel
 * 
 */
public class WSDLScopeProvider implements IScopeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com
     * .tibco.xpd.validation.destinations.Destination, java.lang.String,
     * com.tibco.xpd.validation.provider.IValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Set<EObject> eos = new HashSet<EObject>();

        WorkingCopy wc = item.getWorkingCopy();

        if (wc instanceof WsdlWorkingCopy && isRelevantDestinationEnabled(wc)) {
            EObject element = wc.getRootElement();

            if (element instanceof Definition) {
                Definition def = (Definition) element;
                // Add the definition itself to the items to be validated
                eos.add(def);
                EList<?> services = def.getEServices();
                if (services != null) {
                    for (Object obj : services) {
                        if (obj instanceof EObject) {
                            eos.add((EObject) obj);
                        }
                    }
                }
                EList<?> portTypes = def.getEPortTypes();
                if (portTypes != null) {
                    for (Object obj : portTypes) {
                        if (obj instanceof PortType) {
                            eos.add((EObject) obj);
                        }
                    }
                }
            }
        }

        return eos;
    }

    /**
     * Check if the BPM or iProcess global destination is enabled for the
     * project that the WorkingCopy belongs to.
     * 
     * @param wc
     * @return
     */
    private boolean isRelevantDestinationEnabled(WorkingCopy wc) {
        boolean isEnabled = false;

        List<IResource> resources = wc.getEclipseResources();
        if (resources != null && !resources.isEmpty()) {
            IProject project = resources.get(0).getProject();

            if (project != null) {
                isEnabled =
                        GlobalDestinationUtil
                                .isGlobalDestinationEnabled(project,
                                        ProcessDestinationUtil.BPM_DESTINATION_ID)
                                || GlobalDestinationUtil
                                        .isGlobalDestinationEnabled(project,
                                                ProcessDestinationUtil.IPROCESS_GLOBAL_DESTINATION)
                                || GlobalDestinationUtil
                                        .isGlobalDestinationEnabled(project,
                                                ProcessDestinationUtil.DECISIONS_DESTINATION_ID);
            }
        }

        return isEnabled;
    }

}
