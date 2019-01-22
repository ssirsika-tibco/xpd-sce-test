/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.Map;

/**
 * Listener that gets called when the user re-generates Unique Ids. Usually the
 * Unique Ids are re-generated during copy paste of Xpdl elements or when user
 * selects to re-generate the ids as they are duplicates.
 * <p>
 * Gives a chance to the extending classes to update the references to the new
 * generated Ids via the {@link #updateReassignedIdReferences(Collection, Map)}
 * <p>
 * The extending classes do not need to deal with firing/executing Commands
 * because the elements passed to
 * {@link #updateReassignedIdReferences(Collection, Map)} are mere copy of
 * existing elements and hence the implementers just need to set the value of
 * the element/attribute they wish to update.
 * 
 * @author kthombar
 * @since Apr 15, 2015
 */
public abstract class AbstractUniqueIdsReassignedListener {

    /**
     * Gives a chance to the implementers to update the references to the new
     * generated IDs.
     * <p>
     * We do not need to deal with firing/executing Commands because the
     * elements passed 'copyOfElementsToFixReferencesFor' are mere copy of
     * existing elements and hence the implementers just need to set the value
     * of the element/attribute they wish to update.
     * 
     * @param copyOfElementsToFixReferencesFor
     *            the copy of elements whose Unique ID's are regenerated.
     * @param oldIdToNewEojectMap
     *            the map of old ID to the element with the new generated ID.
     */
    abstract public void updateReassignedIdReferences(
            Collection copyOfElementsToFixReferencesFor, Map oldIdToNewEojectMap);
}
