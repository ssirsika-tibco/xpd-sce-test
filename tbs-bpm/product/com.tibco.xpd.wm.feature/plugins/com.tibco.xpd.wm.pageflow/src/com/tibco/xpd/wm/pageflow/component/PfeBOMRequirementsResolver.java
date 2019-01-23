package com.tibco.xpd.wm.pageflow.component;

import com.tibco.xpd.n2.pe.component.BOMRequirementsResolver;

/**
 * BOM requirements resolver specifically for PFE capability.
 * 
 * @author nwilson
 * @since 7 Jul 2015
 */
public class PfeBOMRequirementsResolver extends BOMRequirementsResolver {

    /**
     * @see com.tibco.xpd.n2.pe.component.BOMRequirementsResolver#getFactoryCapabilityId()
     * 
     * @return The PFE capability ID.
     */
    @Override
    protected String getFactoryCapabilityId() {
        return PFEUtil.PFE_REQUIRED_CAPABILITY_ID;
    }
}
