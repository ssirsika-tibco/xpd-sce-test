/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.casedata;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * Data resolver for Case Signal Data type event.
 * 
 * @author sajain
 * @since Mar 19, 2015
 */
public class CaseDataSignalDataFieldResolver implements
        IFieldContextResolverExtension {

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getActivityDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        TriggerResultSignal signal = getTriggerResultSignal(activity);

        if (signal != null) {

            /*
             * Return all case signal data references.
             */
            return getCaseDataSignalDataReferences(signal, dataSet);
        }

        return null;
    }

    /**
     * Get TriggerResultSignal object from signal event activity.
     * 
     * @param act
     * @return TriggerResultSignal object from signal event activity.
     */
    private TriggerResultSignal getTriggerResultSignal(Activity act) {

        /*
         * Get TriggerResultSignal.
         */
        Event event = act.getEvent();

        if (event != null) {

            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                return signal;
            }
        }

        return null;
    }

    /**
     * Return data references made from case data signal event.
     * 
     * @param triggerSignalResult
     *            Signal event's trigger signal node.
     * @param dataSet
     *            Data set.
     * 
     * @return data references made from case data signal event.
     */
    private Set<ProcessRelevantData> getCaseDataSignalDataReferences(
            TriggerResultSignal triggerSignalResult,
            Set<ProcessRelevantData> dataSet) {

        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        String name = triggerSignalResult.getName();

        if (name != null && name != "") { //$NON-NLS-1$

            /*
             * Check for field name references in the given text and add to
             * result accordingly.
             */

            addDataReferences(name, dataSet, result);

        }

        return result;
    }

    /**
     * Check for field name references in the given text.
     * 
     * @param str
     * @param dataSet
     */
    private void addDataReferences(String str,
            Set<ProcessRelevantData> dataSet, Set<ProcessRelevantData> result) {

        if (str != null) {

            for (ProcessRelevantData data : dataSet) {

                String lookFor = data.getName();

                if (str.equals(lookFor)) {

                    result.add(data);
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getTransitionDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessRelevantData> getTransitionDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {

        /*
         * Nothing to do here.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapActivityDataIdReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> idMap) {

        /*
         * Nothing to do here.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataIdReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param idMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataIdReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> idMap) {

        /*
         * Nothing to do here.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {

        TriggerResultSignal signal = getTriggerResultSignal(activity);

        if (signal != null) {

            return getSwapCaseDataSignalDataReferences(editingDomain,
                    signal,
                    nameMap);
        }

        return null;
    }

    /**
     * Swap the field name references provided in the given map in all the case
     * data signal internals.
     * 
     * @param editingDomain
     * @param triggerResultSignal
     * @param nameMap
     * 
     * @return Command to swap the field name references provided in the given
     *         map in all the case data signal internals.
     */
    private Command getSwapCaseDataSignalDataReferences(
            EditingDomain editingDomain,
            TriggerResultSignal triggerResultSignal, Map<String, String> nameMap) {

        CompoundCommand cmd = new CompoundCommand();

        String name = triggerResultSignal.getName();

        /*
         * Proceed only if signal name exists, no need to add null.
         */
        if (name != null && name != "") { //$NON-NLS-1$

            /*
             * Proceed only if the name map has the signal name.
             */
            if (nameMap.get(name) != null) {

                Command swapRefCmd =
                        getSwapReferenceCommand(editingDomain,
                                name,
                                triggerResultSignal,
                                Xpdl2Package.eINSTANCE
                                        .getTriggerResultSignal_Name(),
                                nameMap);

                if (swapRefCmd != null) {

                    /*
                     * Append command to the result cmd to swap all the field
                     * names given in the map to new names in the given string.
                     */
                    cmd.append(swapRefCmd);
                }
            }
        }

        /*
         * Only return commands if we have any.
         */
        if (cmd.getCommandList().size() > 0) {
            return cmd;
        }
        return null;
    }

    /**
     * Append command to the result cmd to swap all the field names given in the
     * map to new names in the given string.
     * 
     * @param editingDomain
     * @param cmd
     * @param str
     * @param owner
     * @param feature
     * @param nameMap
     */
    private Command getSwapReferenceCommand(EditingDomain editingDomain,
            String str, EObject owner, EStructuralFeature feature,
            Map<String, String> nameMap) {

        if (str != null && str.length() != 0) {

            String newStr = str;
            Set<Entry<String, String>> eset = nameMap.entrySet();

            /*
             * Replace all occurrences of all data field names to map.
             */
            for (Entry entry : eset) {

                String replaceVal = (String) entry.getValue();
                String lookFor = (String) entry.getKey();
                String replaceWith;

                if (replaceVal != null && replaceVal.length() > 0) {

                    replaceWith = replaceVal;

                } else {

                    replaceWith = ""; //$NON-NLS-1$

                }

                if (newStr.equals(lookFor)) {

                    newStr = replaceWith;
                    break;

                } else if (newStr.startsWith(lookFor
                        + ConceptPath.CONCEPTPATH_SEPARATOR)) {

                    /*
                     * Replace the first segment of the path.
                     */
                    newStr =
                            replaceWith
                                    + newStr.substring(newStr
                                            .indexOf(ConceptPath.CONCEPTPATH_SEPARATOR));
                    break;
                }

            }

            /*
             * If the result is different, create the command to replace the
             * text.
             */
            if (!newStr.equals(str)) {

                if (newStr.length() == 0) {

                    newStr = null; // Setting to nullstr should delete item.
                }

                return (SetCommand
                        .create(editingDomain, owner, feature, newStr));
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapTransitionDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition, java.util.Map)
     * 
     * @param editingDomain
     * @param transition
     * @param nameMap
     * @return
     */
    @Override
    public Command getSwapTransitionDataNameReferencesCommand(
            EditingDomain editingDomain, Transition transition,
            Map<String, String> nameMap) {

        /*
         * Nothing to do here.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromActivityCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param activity
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromActivityCommand(
            EditingDomain editingDomain, Activity activity,
            ProcessRelevantData data) {

        /*
         * Nothing to do here.
         */

        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getDeleteDataFromTransitionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Transition,
     *      com.tibco.xpd.xpdl2.ProcessRelevantData)
     * 
     * @param editingDomain
     * @param transition
     * @param data
     * @return
     */
    @Override
    public Command getDeleteDataFromTransitionCommand(
            EditingDomain editingDomain, Transition transition,
            ProcessRelevantData data) {

        /*
         * Nothing to do here.
         */
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Set<ProcessRelevantData> dataReferences =
                getActivityDataReferences(activity, dataSet);

        if (dataReferences != null) {

            Set<ProcessDataReferenceAndContexts> dataRefAndContexts =
                    new HashSet<ProcessDataReferenceAndContexts>();

            for (ProcessRelevantData data : dataReferences) {
                dataRefAndContexts.add(new ProcessDataReferenceAndContexts(
                        data,
                        DataReferenceContext.CONTEXT_ACTIVITY_IMPLEMENTATION));
            }

            return dataRefAndContexts;
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Transition,
     *      java.util.Set)
     * 
     * @param transition
     * @param dataSet
     * @return
     */
    @Override
    public Set<ProcessDataReferenceAndContexts> getDataReferences(
            Transition transition, Set<ProcessRelevantData> dataSet) {

        /*
         * Nothing to do here.
         */
        return null;
    }

}