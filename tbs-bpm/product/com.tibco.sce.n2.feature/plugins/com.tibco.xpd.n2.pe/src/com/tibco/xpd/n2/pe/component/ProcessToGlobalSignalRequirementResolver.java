/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.component;

import org.eclipse.emf.common.util.EList;

import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.n2.globalsignal.deploy.requirementresolver.AbstractXpdlToGlobalSignalRequirementResolver;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Gsd required capability requirement resolver for process component.
 * 
 * 
 * @author kthombar
 * @since Mar 19, 2015
 */
public class ProcessToGlobalSignalRequirementResolver extends
        AbstractXpdlToGlobalSignalRequirementResolver {

    /**
     * @see com.tibco.xpd.n2.globalsignal.deploy.requirementresolver.AbstractXpdlToGlobalSignalRequirementResolver#isApplicableProcess(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     * @return
     */
    @Override
    protected boolean isApplicableProcess(Process process) {
        /*
         * return true of the passed process is a business process.
         */
        return Xpdl2ModelUtil.isBusinessProcess(process)
                || ProcessInterfaceUtil.isProcessEngineServiceProcess(process);

    }

    /**
     * @see com.tibco.xpd.n2.globalsignal.deploy.requirementresolver.AbstractXpdlToGlobalSignalRequirementResolver#getOrCreateFactoryCapability(org.eclipse.emf.common.util.EList)
     * 
     * @param existingRequiredCapabilities
     * @return
     */
    @Override
    protected RequiredCapability getOrCreateFactoryCapability(
            EList<RequiredCapability> existingRequiredCapabilities) {

        RequiredCapability factoryRc = null;
        for (RequiredCapability rc : existingRequiredCapabilities) {
            if (BxCompositeCoreConstants.CAPABILITY_ID.equals(rc.getId())) {
                /*
                 * found RC factory
                 */
                factoryRc = rc;
                break;
            }
        }
        if (factoryRc == null) {
            /*
             * If no BX RC Factory found then create one.
             */
            factoryRc = BxCompositeCoreUtil.createBxRequiredCapability();
            existingRequiredCapabilities.add(factoryRc);
        }
        return factoryRc;
    }
}
