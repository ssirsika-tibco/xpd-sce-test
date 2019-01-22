package com.tibco.xpd.xpdl2.resolvers;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Data field / formal parameter reference resolver class for
 * FieldOrParamReferenceResolver extension point.
 * 
 * @author aallway
 * 
 */
public interface IFieldResolverExtension {

    /**
     * Given a set of data fields / formal parameters return the set of those
     * data fields / formal parameters that are referenced by parts of activity
     * model that the extension knows about.
     * 
     * @param activity
     * @return Set of data fields from given set that are referenced.
     */
    Set<ProcessRelevantData> getActivityDataReferences(Activity activity,
            Set<ProcessRelevantData> dataSet);

    /**
     * Given a set of data fields / formal parameters return the set of those
     * data fields / formal parameters that are referenced by parts of
     * transition model that the extendion knows about.
     * 
     * @param activity
     * @return Set of data fields from given set that are referenced.
     */
    Set<ProcessRelevantData> getTransitionDataReferences(Transition transition,
            Set<ProcessRelevantData> dataSet);

    /**
     * Given a map of old data field / formal Parameter id's to new id's; return
     * the command that will swap any references by Id to those fields made in
     * the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param activity
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapActivityDataIdReferencesCommand(EditingDomain editingDomain,
            Activity activity, Map<String, String> idMap);

    /**
     * Given a map of old data field / formal Parameter id's to new id's; return
     * the command that will swap any references by Id to those fields made in
     * the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param transition
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap);

    /**
     * Given a map of old data field / formal Parameter namess to new names;
     * return the command that will swap any references by name to those fields
     * made in the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param activity
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap);

    /**
     * Given a map of old data field / formal Parameter names to new names;
     * return the command that will swap any references by Name to those fields
     * made in the parts of the model known by this extension.
     * <p>
     * Return <b>null</b> if no replacements are required.
     * 
     * @param editingDomain
     * @param transition
     * @param idMap
     * @return Command or null if no replacements to make
     */
    Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap);

    /**
     * Given a data field / formal parameter - return command to delete
     * references to it if required.
     * <p>
     * Return <b>null</b> if no changes are required.
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return Command or null if no changes to make
     */
    Command getDeleteDataFromActivityCommand(EditingDomain editingDomain,
            Activity activity, ProcessRelevantData data);

    /**
     * Given a data field / formal parameter - return command to delete
     * references to it if required.
     * <p>
     * Return <b>null</b> if no changes are required.
     * 
     * @param editingDomain
     * @param transition
     * @param data
     * @return Command or null if no changes to make
     */
    Command getDeleteDataFromTransitionCommand(EditingDomain editingDomain,
            Transition transition, ProcessRelevantData data);

}
