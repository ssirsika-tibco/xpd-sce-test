/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.globaldataservice.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.utilities.TaskServiceExtUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * Class to resolve data references for Global Data Service Task.
 * 
 * @author sajain
 * @since Feb 14, 2014
 */
public class GlobalDataServiceTaskDataFieldResolver implements
        IFieldContextResolverExtension {

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
        /*
         * First make sure that this is a Global Data Service Task.
         */
        if (activity.getImplementation() instanceof Task) {
            TaskService taskSrvc =
                    ((Task) activity.getImplementation()).getTaskService();

            if (taskSrvc != null) {
                GlobalDataOperation globalDataOp =
                        getGlobalDataExtension(taskSrvc);

                if (globalDataOp != null) {
                    return getGlobalDataServiceTaskDataReferences(globalDataOp,
                            dataSet);
                }
            }
        }
        return null;
    }

    private Set<ProcessRelevantData> getGlobalDataServiceTaskDataReferences(
            GlobalDataOperation globalDataOp, Set<ProcessRelevantData> dataSet) {
        Set<ProcessRelevantData> result = new HashSet<ProcessRelevantData>();

        CaseAccessOperationsType caseAccessOperations =
                globalDataOp.getCaseAccessOperations();
        CaseReferenceOperationsType caseReferenceOperations =
                globalDataOp.getCaseReferenceOperations();

        if (null != caseAccessOperations) {
            if (null != caseAccessOperations.getCreate()) {
                addDataReferences(getNameFromFieldPath(caseAccessOperations
                        .getCreate().getFromFieldPath()), dataSet, result);

                addDataReferences(getNameFromFieldPath(caseAccessOperations
                        .getCreate().getToCaseRefField()), dataSet, result);
            } else if (null != caseAccessOperations.getDeleteByCaseIdentifier()) {
                addDataReferences(getNameFromFieldPath(caseAccessOperations
                        .getDeleteByCaseIdentifier().getFieldPath()),
                        dataSet,
                        result);
            } else if (null != caseAccessOperations
                    .getDeleteByCompositeIdentifiers()) {
                EList<CompositeIdentifierType> compositeIdentifier =
                        caseAccessOperations.getDeleteByCompositeIdentifiers()
                                .getCompositeIdentifier();
                for (CompositeIdentifierType eachIdentifier : compositeIdentifier) {
                    addDataReferences(getNameFromFieldPath(eachIdentifier.getFieldPath()),
                            dataSet,
                            result);
                }
            }
        } else if (null != caseReferenceOperations) {
            addDataReferences(getNameFromFieldPath(caseReferenceOperations.getCaseRefField()),
                    dataSet,
                    result);

            if (null != caseReferenceOperations.getUpdate()) {
                addDataReferences(getNameFromFieldPath(caseReferenceOperations
                        .getUpdate().getFromFieldPath()), dataSet, result);
            } else if (null != caseReferenceOperations.getAddLinkAssociations()) {
                addDataReferences(getNameFromFieldPath(caseReferenceOperations
                        .getAddLinkAssociations().getAddCaseRefField()),
                        dataSet,
                        result);
            } else if (null != caseReferenceOperations
                    .getRemoveLinkAssociations()) {
                addDataReferences(getNameFromFieldPath(caseReferenceOperations
                        .getRemoveLinkAssociations().getRemoveCaseRefField()),
                        dataSet,
                        result);
            }
        }

        return result;
    }

    /**
     * Some field values can be dot-separated paths and therefore we have to
     * take the field name to be the field ref value prior to first “.” if there
     * is one.
     * 
     * @param fieldName
     *            String which could be field path
     * @return field name
     */
    private String getNameFromFieldPath(String fieldName) {
        String[] tempStringArray = null;
        if (null != fieldName) {
            if (fieldName.contains(".")) { //$NON-NLS-1$
                tempStringArray = fieldName.split("\\."); //$NON-NLS-1$
                fieldName = tempStringArray[0];
            }
        }
        return fieldName;
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
        /*
         * First make sure that this is a Global Data Service Task.
         */
        if (activity.getImplementation() instanceof Task) {
            TaskService taskSrvc =
                    ((Task) activity.getImplementation()).getTaskService();

            if (taskSrvc != null) {
                GlobalDataOperation globalDataOp =
                        getGlobalDataExtension(taskSrvc);

                if (globalDataOp != null) {
                    return getSwapGlobalDataServiceTaskDataReferences(editingDomain,
                            globalDataOp,
                            nameMap);
                }
            }
        }
        return null;
    }

    /**
     * Swap the field name references provided in the given map in all the
     * Global Data Service Task internals.
     * 
     * @param editingDomain
     * @param globalDataOp
     * @param nameMap
     * @return
     */
    private Command getSwapGlobalDataServiceTaskDataReferences(
            EditingDomain editingDomain, GlobalDataOperation globalDataOp,
            Map<String, String> nameMap) {

        CompoundCommand cmd = new CompoundCommand();

        CaseAccessOperationsType caseAccessOperations =
                globalDataOp.getCaseAccessOperations();
        CaseReferenceOperationsType caseReferenceOperations =
                globalDataOp.getCaseReferenceOperations();

        if (null != caseAccessOperations) {
            if (null != caseAccessOperations.getCreate()) {
                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseAccessOperations.getCreate().getFromFieldPath(),
                        caseAccessOperations.getCreate(),
                        XpdExtensionPackage.eINSTANCE
                                .getCreateCaseOperationType_FromFieldPath(),
                        nameMap));

                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseAccessOperations.getCreate().getToCaseRefField(),
                        caseAccessOperations.getCreate(),
                        XpdExtensionPackage.eINSTANCE
                                .getCreateCaseOperationType_ToCaseRefField(),
                        nameMap));
            } else if (null != caseAccessOperations.getDeleteByCaseIdentifier()) {

                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseAccessOperations.getDeleteByCaseIdentifier()
                                .getFieldPath(),
                        caseAccessOperations.getDeleteByCaseIdentifier(),
                        XpdExtensionPackage.eINSTANCE
                                .getDeleteByCaseIdentifierType_FieldPath(),
                        nameMap));

            } else if (null != caseAccessOperations
                    .getDeleteByCompositeIdentifiers()) {
                EList<CompositeIdentifierType> compositeIdentifier =
                        caseAccessOperations.getDeleteByCompositeIdentifiers()
                                .getCompositeIdentifier();
                for (CompositeIdentifierType eachIdentifier : compositeIdentifier) {
                    cmd.append(getSwapReferenceCommand(editingDomain,
                            eachIdentifier.getFieldPath(),
                            eachIdentifier,
                            XpdExtensionPackage.eINSTANCE
                                    .getCompositeIdentifierType_FieldPath(),
                            nameMap));
                }
            }
        } else if (null != caseReferenceOperations) {
            cmd.append(getSwapReferenceCommand(editingDomain,
                    caseReferenceOperations.getCaseRefField(),
                    caseReferenceOperations,
                    XpdExtensionPackage.eINSTANCE
                            .getCaseReferenceOperationsType_CaseRefField(),
                    nameMap));

            if (null != caseReferenceOperations.getUpdate()) {
                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseReferenceOperations.getUpdate().getFromFieldPath(),
                        caseReferenceOperations.getUpdate(),
                        XpdExtensionPackage.eINSTANCE
                                .getUpdateCaseOperationType_FromFieldPath(),
                        nameMap));
            } else if (null != caseReferenceOperations.getAddLinkAssociations()) {
                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseReferenceOperations.getAddLinkAssociations()
                                .getAddCaseRefField(),
                        caseReferenceOperations.getAddLinkAssociations(),
                        XpdExtensionPackage.eINSTANCE
                                .getAddLinkAssociationsType_AddCaseRefField(),
                        nameMap));
            } else if (null != caseReferenceOperations
                    .getRemoveLinkAssociations()) {
                cmd.append(getSwapReferenceCommand(editingDomain,
                        caseReferenceOperations.getRemoveLinkAssociations()
                                .getRemoveCaseRefField(),
                        caseReferenceOperations.getRemoveLinkAssociations(),
                        XpdExtensionPackage.eINSTANCE
                                .getRemoveLinkAssociationsType_RemoveCaseRefField(),
                        nameMap));
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
         * XPD-7457: Saket: No need to implement this as we want the user to
         * know that there are some broken references after the've deleted
         * something.
         */
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
                    // replace the first segment of the path
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
        return null;
    }

    /**
     * Get Global Data Operation object from <code>TaskService</code>
     * 
     * @param service
     * @return Global Data Operation object from <code>TaskService</code>
     */
    private GlobalDataOperation getGlobalDataExtension(TaskService service) {
        GlobalDataOperation globalDataOp = null;

        EObject eo =
                TaskServiceExtUtil.getExtendedModel(service,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_GlobalDataOperation());

        if (eo instanceof GlobalDataOperation) {
            globalDataOp = (GlobalDataOperation) eo;
        }

        return globalDataOp;
    }

}
