/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.utils;

import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * 
 * 
 * @author mtorres
 * @since Dec 7, 2012
 */
public class RCPPropertySectionHelper {
    /**
     * Returns a contributor id for an ITabbedPropertySheetPageContributor
     * 
     * @param obj
     * @return
     */
    public static String getContributorId(Object obj) {
        String id = "com.tibco.xpd.rcp.properties"; //$NON-NLS-1$
        if (obj instanceof Process || obj instanceof Package
                || obj instanceof ProcessInterface) {
            id = "com.tibco.xpd.processeditor.xpdl2.propertyContributor"; //$NON-NLS-1$
        } else if (obj instanceof Model) {
            id = "com.tibco.xpd.bom.modeler.diagram"; //$NON-NLS-1$
        } else if (obj instanceof OrgModel || obj instanceof Organization) {
            id = "com.tibco.xpd.om.modeler.diagram"; //$NON-NLS-1$
        }
        return id;
    }
}
