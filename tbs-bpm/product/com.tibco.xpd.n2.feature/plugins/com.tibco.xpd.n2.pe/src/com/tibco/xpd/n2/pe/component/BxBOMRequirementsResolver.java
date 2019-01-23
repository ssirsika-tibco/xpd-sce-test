package com.tibco.xpd.n2.pe.component;

import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;

/**
 * BOM requirements resolver specifically for BX capability.
 * 
 * @author nwilson
 * @since 7 Jul 2015
 */
public class BxBOMRequirementsResolver extends BOMRequirementsResolver {

    /**
     * @see com.tibco.xpd.n2.pe.component.BOMRequirementsResolver#getFactoryCapabilityId()
     * 
     * @return BX cpability ID.
     */
    @Override
    protected String getFactoryCapabilityId() {
        return BxCompositeCoreConstants.CAPABILITY_ID;
    }
}
