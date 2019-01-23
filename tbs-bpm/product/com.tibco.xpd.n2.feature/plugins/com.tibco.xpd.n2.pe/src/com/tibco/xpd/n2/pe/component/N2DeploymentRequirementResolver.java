/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.pe.component;

import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;

/**
 * Adds N2 specific deployment requirements to the composite.
 * 
 * @author Jan Arciuchiewicz
 */
public class N2DeploymentRequirementResolver implements IRequirementsResolver {

    /**
     * {@inheritDoc}
     */
    public void addImplementationRequirements(Requirements requirements,
            Implementation implementation) {
        // add product application deployment constraint
        requirements
                .getDeploymentConstraints()
                .add(ComponentTypeFactory.eINSTANCE.createProductApplicationDeployment());
        // Add required feature dependency
        addFeatureDependency(requirements, implementation);
    }

    /**
     * @param requirements
     * @param implementation
     */
    private void addFeatureDependency(Requirements requirements,
            Implementation implementation) {
        if (null != requirements) {
            RequiredFeature modelPF = N2PENamingUtils.getBPMITModelPF();
            requirements.getFeatureDependencies().add(modelPF);
        }

    }

}
