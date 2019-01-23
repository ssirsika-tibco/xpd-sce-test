/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.decisions.internal.util;

import com.tibco.amf.dependency.osgi.OsgiFactory;
import com.tibco.amf.dependency.osgi.RequiredFeature;
import com.tibco.amf.dependency.osgi.VersionRange;

/**
 * Decisions N2 Daa Utility class
 * 
 * @author mtorres
 * 
 */
public final class N2DecisionsNamingUtils {

    /**
     * Decision Extension Product Feature
     */
    private static final String AMX_DECISION_EXTENSION_PRODUCT_FEATURE =
            "com.tibco.adec.dt.product.feature"; //$NON-NLS-1$
    
    /**
     * upper version range for the amx bpm model product feature
     */
    private static final String UPPER_RANGE = "2.0.0"; //$NON-NLS-1$

    /**
     * lower version range for the amx bpm model product feature
     */
    private static final String LOWER_RANGE = "1.0.0"; //$NON-NLS-1$


    /**
     * Decisions Extension product feature to support HA deployment (SDA-310).
     * 
     * @return
     */
    public static RequiredFeature getDecisionsExtensionPF() {
        RequiredFeature decExtProductFeature =
                OsgiFactory.eINSTANCE.createRequiredFeature();
        decExtProductFeature.setName(AMX_DECISION_EXTENSION_PRODUCT_FEATURE);
        VersionRange versionRange = OsgiFactory.eINSTANCE.createVersionRange();
        versionRange.setLower(LOWER_RANGE);
        versionRange.setLowerIncluded(true);
        versionRange.setUpper(UPPER_RANGE);
        versionRange.setUpperIncluded(false);
        decExtProductFeature.setRange(versionRange);
        return decExtProductFeature;
    }

}