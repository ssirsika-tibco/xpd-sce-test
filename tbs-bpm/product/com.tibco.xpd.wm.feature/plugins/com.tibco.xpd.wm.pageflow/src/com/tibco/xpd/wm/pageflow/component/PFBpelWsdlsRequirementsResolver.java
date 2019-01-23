/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.List;

import com.tibco.amf.dependency.osgi.ImportPackage;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.bx.composite.core.extensions.BpelWsdlsRequirementsResolver;

/**
 * @author kupadhya
 * 
 */
public class PFBpelWsdlsRequirementsResolver extends
        BpelWsdlsRequirementsResolver {

    @Override
    protected RequiredCapability addBpelRequiredCapability(
            List<RequiredCapability> requiredCapabilities) {
        RequiredCapability requiredCapability =
                PFEUtil.createPFCRequiredCapability();
        return requiredCapability;
    }

    protected void addPackageImports(List<ImportPackage> packageImports) {
        super.addPackageImports(packageImports);
        packageImports.add(PFEUtil.getPfeImportPackage());
    }

}
