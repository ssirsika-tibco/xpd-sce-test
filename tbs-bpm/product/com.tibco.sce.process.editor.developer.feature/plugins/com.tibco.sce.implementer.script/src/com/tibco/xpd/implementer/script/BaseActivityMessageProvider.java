/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension;

/**
 * A task or an event that can be implemented with a message should provide an
 * adapter to this interface. This provides a standard access mechanism to
 * message implementation, and parameter mapping. The input and output message
 * details should be returned by the appropriate methods. ( e.g. A message
 * result from an end event will have an output message and a null input
 * message, a message trigger for a start event will have an input message and
 * null output message.) Note that this adapter does not extend the EMF adapter
 * interface because it it intended to be used in XPD property sections, which
 * handle their own notifications.
 *
 *
 * @author jarciuch
 * @since 7 Apr 2015
 */
public interface BaseActivityMessageProvider {

    /**
     * Get Message for input details.
     * 
     * @param act
     *            The activity to adapt.
     * @return A message from the activity (may be null).
     */
    public abstract Message getMessageIn(Activity act);

    /**
     * Get message for output detaiols.
     * 
     * @param act
     *            The activity to adapt.
     * @return A message from the activity (may be null).
     */
    public abstract Message getMessageOut(Activity act);

    /**
     * Get implementation enumerated type for the activity.
     * 
     * @param act
     *            The activity to adapt.
     * @return The implementation type.
     */
    public abstract ImplementationType getImplementation(Activity act);

    /**
     * Command to set the implementation type.
     * 
     * @param ed
     *            Editing domain.
     * @param act
     *            Activity to amend.
     * @param newImplType
     *            New value.
     * @return A command to set the value.
     */
    public abstract Command getSetImplementationCommand(EditingDomain ed,
            Activity act, ImplementationType newImplType);

    /**
     * Get the containing item.
     * 
     * @param act
     *            The activity that the message belongs to.
     * @return The object within the activity which contains the message.
     */
    public abstract EObject getMessageContainer(Activity act);

    /**
     * Whether this activity requires inbound data mapping to its message.
     * 
     * @param act
     *            Activity to chekc.
     * @return Whether this activity requires inbound data mapping.
     */
    public abstract boolean hasMappingIn(Activity act);

    /**
     * Whether this activity requires outbound data mapping to its message.
     * 
     * @param act
     *            Activity to check.
     * @return Whether this activity requires outbound data mapping.
     */
    public abstract boolean hasMappingOut(Activity act);

    /**
     * Return subset of dataset that are referenced by the activity.
     * 
     * @param activity
     *            Activity to search within.
     * @param dataSet
     *            Data to search for.
     * @return Sub set of data that is referenced.
     * @see IFieldResolverExtension
     */
    public abstract Set<ProcessRelevantData> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet);

    /**
     * Return command for replacing ids of data references.
     * 
     * @param editingDomain
     *            Domain for editing.
     * @param activity
     *            Activity to search/replace within.
     * @param idMap
     *            Ids to replace.
     * @return Command to do replace.
     * @see IFieldResolverExtension
     */
    public abstract Command getSwapDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap);

    /**
     * Return command for renaming of data references.
     * 
     * @param editingDomain
     *            Domain for editing.
     * @param activity
     *            Activity to search/replace within.
     * @param nameMap
     *            Names to change.
     * @return Command to do rename.
     * @see IFieldResolverExtension
     */
    public abstract Command getSwapDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap);

    /**
     * Return command that deletes referecnes to given data field / formal
     * parameter from the given activity.
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return
     */
    public abstract Command getDeleteDataReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data);

}