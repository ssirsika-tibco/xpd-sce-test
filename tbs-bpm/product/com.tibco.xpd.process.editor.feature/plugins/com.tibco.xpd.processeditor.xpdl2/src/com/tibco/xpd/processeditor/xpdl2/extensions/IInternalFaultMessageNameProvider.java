/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.Set;

import com.tibco.xpd.xpdl2.Activity;

/**
 * IInternalFaultMessageNameProvider
 * <p>
 * <b> For internal use only!</b> This interface allows the contribution of
 * fault message name list for picking in throw end error event property sheet
 * in process analyst feature.
 * 
 * @author aallway
 * @since 3.3 (25 Nov 2009)
 */
public interface IInternalFaultMessageNameProvider {

    /**
     * @param webServiceActivity
     * 
     * @return The fault message names available for the operation referenced by
     *         the given web service related activity.
     */
    Set<String> getFaultMessageNames(Activity webServiceActivity);

}
