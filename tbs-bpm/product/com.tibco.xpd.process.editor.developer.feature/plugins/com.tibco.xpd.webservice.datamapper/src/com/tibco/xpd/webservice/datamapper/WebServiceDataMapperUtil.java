/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.webservice.datamapper;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class to store WebService DataMapper specific utilities.
 * 
 * @author ssirsika
 * @since 16-Feb-2016
 */
public class WebServiceDataMapperUtil {

    /**
     * If target contributor id is same as contributor ID of
     * {@link WSCorrelationDataMapperContentContributor}, then mapping has
     * target of type Correlation Data(field).
     * 
     * @param dataMapping
     * @return return true if passed dataMapping has target as correlation data
     */
    public static boolean isMappedToCorrelationData(DataMapping dataMapping) {
        Object targetAttributeName =
                Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetContributorId());
        return WebServiceDataMapperConstants.CORRELATION_DATAMAPPER_CONTENT_CONTRIBUTOR_ID
                .equals(targetAttributeName);
    }
}
