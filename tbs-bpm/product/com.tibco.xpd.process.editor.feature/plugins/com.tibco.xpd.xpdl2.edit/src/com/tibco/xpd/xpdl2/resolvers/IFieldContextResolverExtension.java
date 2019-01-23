/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.Set;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;

/**
 * This extension to the {@link IFieldResolverExtension} allows the contributing
 * resolver to return the identification of the 'context' in which a field is
 * referenced.
 * 
 * @author aallway
 * @since 19 Jun 2012
 */
public interface IFieldContextResolverExtension extends IFieldResolverExtension {

    /**
     * Given a set of data fields / formal parameters return the set of those
     * data fields / formal parameters that are referenced by parts of activity
     * model that the extension knows about.
     * 
     * @param activity
     * @return Set of data references (each with a context in which they are
     *         used) string id context in which they are used.
     */
    Set<ProcessDataReferenceAndContexts> getDataReferences(Activity activity,
            Set<ProcessRelevantData> dataSet);

    /**
     * Given a set of data fields / formal parameters return the set of those
     * data fields / formal parameters that are referenced by parts of activity
     * model that the extension knows about.
     * 
     * @param activity
     * @return Set of data fields from given set that are referenced.
     */
    Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet);

}
