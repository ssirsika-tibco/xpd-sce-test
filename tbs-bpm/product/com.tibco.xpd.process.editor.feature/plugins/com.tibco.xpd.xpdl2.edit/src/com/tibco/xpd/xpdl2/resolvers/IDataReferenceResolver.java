/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Interface of a reference resolver that deals with data references outside of
 * Activity / Transition. This is to support the DataReferenceResolver extension
 * element in the <code>fieldOrParamReferenceResolver</code> extension point.
 * 
 * @author aallway
 * @since 10 Oct 2013
 */
public interface IDataReferenceResolver {

    /**
     * Given a data fields / formal parameters return information about which
     * object within the given dataScopeObject reference that data and for each
     * referencing object, the set of contexts in which it is referenced.
     * 
     * @param dataScopeObject
     *            Data Container resolved to...
     * 
     *            <li>Processes in scope of data (so all process if it's pkg
     *            data or implementing proceses if it's interface data THIS is
     *            in order to save every implementation having to do this scope
     *            checking)</li>
     * 
     *            <li>Activity ONLY if the data is an activity scope data field
     *            changes</li>
     * 
     *            <li>Process Interface. Note that in the circumstance of
     *            process-interface data then the scope object may be the
     *            implementing process (so not necessarily the direct data
     *            container.</li>
     * 
     * @param data
     *            The data to be resolved.
     * 
     * @return A map whose key is an object within dataScopeObject that
     *         references the given data and whose value is the set of contexts
     *         in which the data is referenced (or <code>null</code> / empty map
     *         if no references)
     */
    Map<EObject, ProcessDataReferenceAndContexts> getDataReferences(
            EObject dataScopeObject, ProcessRelevantData data);

    /**
     * Given an old and new data field / formal Parameter <b>NAME</b>'s -;
     * return the command that will swap any references by <b>NAME</b> to the
     * field made in the parts of the model in their scope.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param dataScopeObject
     *            Data Container resolved to...
     * 
     *            <li>Processes in scope of data (so all process if it's pkg
     *            data or implementing proceses if it's interface data THIS is
     *            in order to save every implementation having to do this scope
     *            checking)</li>
     * 
     *            <li>Activity ONLY if the data is an activity scope data field
     *            changes</li>
     * 
     *            <li>Process Interface. Note that in the circumstance of
     *            process-interface data then the scope object may be the
     *            implementing process (so not necessarily the direct data
     *            container.</li>
     * @param oldName
     * @param newName
     * 
     * @return Command or <code>null</code> if no replacements to make
     */
    Command getSwapDataNameReferencesCommand(EditingDomain editingDomain,
            EObject dataScopeObject, String oldName, String newName);

    /**
     * Given a data field / formal parameter - return command to delete
     * references to it if that is required
     * <p>
     * Return <b>null</b> if no changes are required.
     * 
     * @param editingDomain
     * @param dataScopeObject
     *            Data Container resolved to...
     * 
     *            <li>Processes in scope of data (so all process if it's pkg
     *            data or implementing proceses if it's interface data THIS is
     *            in order to save every implementation having to do this scope
     *            checking)</li>
     * 
     *            <li>Activity ONLY if the data is an activity scope data field
     *            changes</li>
     * 
     *            <li>Process Interface. Note that in the circumstance of
     *            process-interface data then the scope object may be the
     *            implementing process (so not necessarily the direct data
     *            container.</li>
     * 
     * @param data
     * 
     * @return Command or null if no changes to make
     */
    Command getDeleteDataReferencesCommand(EditingDomain editingDomain,
            EObject dataScopeObject, ProcessRelevantData data);

}
