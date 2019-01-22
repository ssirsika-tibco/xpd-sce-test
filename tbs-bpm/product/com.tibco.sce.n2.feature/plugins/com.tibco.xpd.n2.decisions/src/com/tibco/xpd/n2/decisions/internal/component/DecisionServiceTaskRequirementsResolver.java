/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.component;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.xpd.n2.decisions.internal.util.N2DecisionsNamingUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * 
 * This class contributes to the requirements file for the daa to include
 * the required features by the Decision Service Task Extension Activity
 * 
 * @author mtorres
 * 
 */
public class DecisionServiceTaskRequirementsResolver implements IRequirementsResolver {

    @Override
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {
        if (!(implementation instanceof BxServiceImplementation)) {
            return;
        }
        BxServiceImplementation bxImplementation =
                (BxServiceImplementation) implementation;
        ServiceImplementation serviceModel = bxImplementation.getServiceModel();
        if (serviceModel == null) {
            return;
        }
        String moduleName = serviceModel.getModuleName();
        if (moduleName == null || moduleName.equals("")) {
            return;
        }
        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(moduleName));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null && wc.getRootElement() instanceof Package) {
            Package xpdPackage = (Package) wc.getRootElement();
            if (DecisionFlowUtil.hasDecisionServiceTasks(xpdPackage)) {
                RequiredFeature decExtPF =
                        N2DecisionsNamingUtils.getDecisionsExtensionPF();
                requirements.getFeatureDependencies().add(decExtPF);
            }
        }
    }
    

}
