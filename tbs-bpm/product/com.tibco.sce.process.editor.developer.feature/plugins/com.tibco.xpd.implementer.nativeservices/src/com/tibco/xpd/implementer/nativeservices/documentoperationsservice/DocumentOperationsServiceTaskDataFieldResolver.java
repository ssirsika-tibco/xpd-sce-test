/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByFileNameOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.LinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.LinkSystemDocumentOperation;
import com.tibco.xpd.xpdExtension.MoveCaseDocOperation;
import com.tibco.xpd.xpdExtension.UnlinkCaseDocOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;

/**
 * Field Context Resolver for Document Operations Service Task.
 * 
 * @author aprasad
 * @since 26-Aug-2014
 */
public class DocumentOperationsServiceTaskDataFieldResolver implements
        IFieldContextResolverExtension {

    private static final XpdExtensionPackage XPD_EXT_PKG =
            XpdExtensionPackage.eINSTANCE;

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getActivityDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return data referenced By the Document Operation Task Activity.
     */
    @Override
    public Set<ProcessRelevantData> getActivityDataReferences(
            Activity activity, Set<ProcessRelevantData> dataSet) {

        Map<String, ProcessRelevantData> dataMap =
                new HashMap<String, ProcessRelevantData>();

        for (ProcessRelevantData processRelevantData : dataSet) {
            dataMap.put(processRelevantData.getName(), processRelevantData);
        }

        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        DocumentOperation documentOperation =
                CaseDocumentOperationsHelpUtiliy.getDocumentOperation(activity);

        if (documentOperation != null) {

            CaseDocFindOperations caseDocFindOperations =
                    documentOperation.getCaseDocFindOperations();

            referencedData
                    .addAll(getDataRefForFindOperations(caseDocFindOperations,
                            dataMap));

            CaseDocRefOperations caseDocRefOperation =
                    documentOperation.getCaseDocRefOperation();

            referencedData
                    .addAll(getDataRefForDocRefOperations(caseDocRefOperation,
                            dataMap));

            LinkSystemDocumentOperation linkSysDocOperation =
                    documentOperation.getLinkSystemDocumentOperation();

            referencedData
                    .addAll(getDataRefForLinkSysDocOperation(linkSysDocOperation,
                            dataMap));
        }

        return referencedData;

    }

    /**
     * Collection of {@link ProcessRelevantData} referenced by the
     * {@link CaseDocRefOperations}.
     * 
     * @param linkSysDocOperation
     * @param dataMap
     * @return Collection of {@link ProcessRelevantData} referenced by the
     *         {@link LinkSystemDocumentOperation}
     */
    private Collection<? extends ProcessRelevantData> getDataRefForLinkSysDocOperation(
            LinkSystemDocumentOperation linkSysDocOperation,
            Map<String, ProcessRelevantData> dataMap) {
        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        if (linkSysDocOperation != null) {

            /* Document Id Field */
            String docIdField = linkSysDocOperation.getDocumentId();

            collectReferencedData(dataMap, referencedData, docIdField);

            /* Case Reference Field */
            String caseRefField = linkSysDocOperation.getCaseRefField();

            collectReferencedData(dataMap, referencedData, caseRefField);

            /* Return Document Reference Field */
            String retCaseDocRefField =
                    linkSysDocOperation.getReturnCaseDocRefField();

            collectReferencedData(dataMap, referencedData, retCaseDocRefField);

        }
        return referencedData;
    }

    /**
     * Collection of {@link ProcessRelevantData} referenced by the
     * {@link CaseDocRefOperations}.
     * 
     * @param caseDocRefOperation
     * @param dataMap
     * @return Collection of {@link ProcessRelevantData} referenced by the
     *         {@link CaseDocRefOperations}.
     */
    private Collection<? extends ProcessRelevantData> getDataRefForDocRefOperations(
            CaseDocRefOperations caseDocRefOperation,
            Map<String, ProcessRelevantData> dataMap) {

        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        if (caseDocRefOperation != null) {
            /* Case Document Reference Field */
            collectReferencedData(dataMap,
                    referencedData,
                    caseDocRefOperation.getCaseDocumentRefField());

            /* Delete Operation does not contain any additional dataField */

            /* Move Operation */
            MoveCaseDocOperation moveCaseDocOperation =
                    caseDocRefOperation.getMoveCaseDocOperation();

            if (moveCaseDocOperation != null) {
                /* Source Case Reference */
                collectReferencedData(dataMap,
                        referencedData,
                        moveCaseDocOperation.getSourceCaseRefField());
                /* Target Case Reference */
                collectReferencedData(dataMap,
                        referencedData,
                        moveCaseDocOperation.getTargetCaseRefField());
            }

            /* Link Operation */
            LinkCaseDocOperation linkCaseDocOperation =
                    caseDocRefOperation.getLinkCaseDocOperation();

            if (linkCaseDocOperation != null) {
                collectReferencedData(dataMap,
                        referencedData,
                        linkCaseDocOperation.getTargetCaseRefField());
            }

            /* Unlink Operation */
            UnlinkCaseDocOperation unlinkCaseDocOperation =
                    caseDocRefOperation.getUnlinkCaseDocOperation();

            if (unlinkCaseDocOperation != null) {
                collectReferencedData(dataMap,
                        referencedData,
                        unlinkCaseDocOperation.getSourceCaseRefField());
            }

        }
        return referencedData;
    }

    /**
     * Collection of {@link ProcessRelevantData} referenced by the
     * {@link CaseDocFindOperations}.
     * 
     * @param caseDocFindOperations
     * @param dataMap
     * @return Collection of {@link ProcessRelevantData} referenced by the
     *         {@link CaseDocFindOperations}.
     */
    private Collection<? extends ProcessRelevantData> getDataRefForFindOperations(
            CaseDocFindOperations caseDocFindOperations,
            Map<String, ProcessRelevantData> dataMap) {

        Set<ProcessRelevantData> referencedData =
                new HashSet<ProcessRelevantData>();

        if (caseDocFindOperations != null) {
            /* Case Reference Field */
            String caseRefField = caseDocFindOperations.getCaseRefField();

            collectReferencedData(dataMap, referencedData, caseRefField);
            /* Return Case Doc Reference Field */
            String returnCaseDocRefsField =
                    caseDocFindOperations.getReturnCaseDocRefsField();
            collectReferencedData(dataMap,
                    referencedData,
                    returnCaseDocRefsField);

            /* Find By Name */
            FindByFileNameOperation findByFileNameOperation =
                    caseDocFindOperations.getFindByFileNameOperation();
            if (findByFileNameOperation != null) {

                collectReferencedData(dataMap,
                        referencedData,
                        findByFileNameOperation.getFileNameField());
            }

            /* Find By Query */
            FindByQueryOperation findByQueryOperation =
                    caseDocFindOperations.getFindByQueryOperation();
            if (findByQueryOperation != null
                    && findByQueryOperation.getCaseDocumentQueryExpression() != null) {

                EList<CaseDocumentQueryExpression> caseDocumentQueryExpressions =
                        findByQueryOperation.getCaseDocumentQueryExpression();

                for (CaseDocumentQueryExpression caseDocumentQueryExpression : caseDocumentQueryExpressions) {
                    /* Only Process Data Field is supposed to be checked */
                    collectReferencedData(dataMap,
                            referencedData,
                            caseDocumentQueryExpression.getProcessDataField());

                }

            }
        }

        return referencedData;
    }

    /**
     * Look up the dataMap and Collect {@link ProcessRelevantData} with name
     * dataField, into referencedDataCollection.
     * 
     * @param dataMap
     *            Map to look up for {@link ProcessRelevantData}
     * @param referencedDataCollection
     *            collection to collect the {@link ProcessRelevantData}.
     * @param dataField
     *            name to lookup the Data Field with.
     */
    private void collectReferencedData(
            Map<String, ProcessRelevantData> dataMap,
            Set<ProcessRelevantData> referencedDataCollection, String dataField) {

        ProcessRelevantData data = dataMap.get(dataField);
        if (data != null) {
            referencedDataCollection.add(data);
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
        // Not required
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
        // Not required
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
        // Not required
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldResolverExtension#getSwapActivityDataNameReferencesCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Activity, java.util.Map)
     * 
     * @param editingDomain
     * @param activity
     * @param nameMap
     * @return Command to Swap Activity Data name references for the new
     *         Activity name.
     */
    @Override
    public Command getSwapActivityDataNameReferencesCommand(
            EditingDomain editingDomain, Activity activity,
            Map<String, String> nameMap) {

        CompoundCommand cmd = new CompoundCommand();

        DocumentOperation documentOperation =
                CaseDocumentOperationsHelpUtiliy.getDocumentOperation(activity);

        if (documentOperation != null) {

            /* Find Operations */
            CaseDocFindOperations caseDocFindOperations =
                    documentOperation.getCaseDocFindOperations();

            if (caseDocFindOperations != null) {
                /* Case Reference Field */
                String caseRefField = caseDocFindOperations.getCaseRefField();

                cmd.append(getSwapNameCommand(editingDomain,
                        caseRefField,
                        nameMap,
                        caseDocFindOperations,
                        CaseDocFindOperationPage.CASE_REF_FEATURE));
                /* Return Case Doc Reference Field */
                String returnCaseDocRefsField =
                        caseDocFindOperations.getReturnCaseDocRefsField();
                cmd.append(getSwapNameCommand(editingDomain,
                        returnCaseDocRefsField,
                        nameMap,
                        caseDocFindOperations,
                        CaseDocFindOperationPage.DOC_REF_FEATURE));

                /* Find By Name */
                FindByFileNameOperation findByFileNameOperation =
                        caseDocFindOperations.getFindByFileNameOperation();
                if (findByFileNameOperation != null) {

                    cmd.append(getSwapNameCommand(editingDomain,
                            findByFileNameOperation.getFileNameField(),
                            nameMap,
                            findByFileNameOperation,
                            CaseDocFindOperationPage.FILE_NAME_FEATURE));
                }

                /* Find By Query */

                FindByQueryOperation findByQueryOperation =
                        caseDocFindOperations.getFindByQueryOperation();

                if (findByQueryOperation != null
                        && findByQueryOperation
                                .getCaseDocumentQueryExpression() != null) {

                    EList<CaseDocumentQueryExpression> caseDocumentQueryExpressions =
                            findByQueryOperation
                                    .getCaseDocumentQueryExpression();

                    for (CaseDocumentQueryExpression caseDocumentQueryExpression : caseDocumentQueryExpressions) {

                        cmd.append(getSwapNameCommand(editingDomain,
                                caseDocumentQueryExpression
                                        .getProcessDataField(),
                                nameMap,
                                caseDocumentQueryExpression,
                                XPD_EXT_PKG
                                        .getCaseDocumentQueryExpression_ProcessDataField()));

                    }

                }
            }
            /* Case Document Reference Operations */
            CaseDocRefOperations caseDocRefOperation =
                    documentOperation.getCaseDocRefOperation();

            if (caseDocRefOperation != null) {
                /* Case Document Reference Field */
                cmd.append(getSwapNameCommand(editingDomain,
                        caseDocRefOperation.getCaseDocumentRefField(),
                        nameMap,
                        caseDocRefOperation,
                        XPD_EXT_PKG
                                .getCaseDocRefOperations_CaseDocumentRefField()));

                /*
                 * Delete Operation does not contain any additional dataField
                 */

                /* Move Operation */
                MoveCaseDocOperation moveCaseDocOperation =
                        caseDocRefOperation.getMoveCaseDocOperation();

                if (moveCaseDocOperation != null) {
                    /* Source Case Reference */
                    cmd.append(getSwapNameCommand(editingDomain,
                            moveCaseDocOperation.getSourceCaseRefField(),
                            nameMap,
                            moveCaseDocOperation,
                            XPD_EXT_PKG
                                    .getMoveCaseDocOperation_SourceCaseRefField()));

                    /* Target Case Reference */
                    cmd.append(getSwapNameCommand(editingDomain,
                            moveCaseDocOperation.getTargetCaseRefField(),
                            nameMap,
                            moveCaseDocOperation,
                            XPD_EXT_PKG
                                    .getMoveCaseDocOperation_TargetCaseRefField()));
                }

                /* Link Operation */
                LinkCaseDocOperation linkCaseDocOperation =
                        caseDocRefOperation.getLinkCaseDocOperation();

                if (linkCaseDocOperation != null) {

                    cmd.append(getSwapNameCommand(editingDomain,
                            linkCaseDocOperation.getTargetCaseRefField(),
                            nameMap,
                            linkCaseDocOperation,
                            XPD_EXT_PKG
                                    .getLinkCaseDocOperation_TargetCaseRefField()));
                }

                /* Unlink Operation */
                UnlinkCaseDocOperation unlinkCaseDocOperation =
                        caseDocRefOperation.getUnlinkCaseDocOperation();

                if (unlinkCaseDocOperation != null) {

                    cmd.append(getSwapNameCommand(editingDomain,
                            unlinkCaseDocOperation.getSourceCaseRefField(),
                            nameMap,
                            unlinkCaseDocOperation,
                            XPD_EXT_PKG
                                    .getUnlinkCaseDocOperation_SourceCaseRefField()));

                }

            }

            LinkSystemDocumentOperation linkSysDocOperation =
                    documentOperation.getLinkSystemDocumentOperation();

            if (linkSysDocOperation != null) {

                /* Document Id Field */
                String docIdField = linkSysDocOperation.getDocumentId();

                cmd.append(getSwapNameCommand(editingDomain,
                        docIdField,
                        nameMap,
                        linkSysDocOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_DocumentId()));

                /* Case Reference Field */
                String caseRefField = linkSysDocOperation.getCaseRefField();

                cmd.append(getSwapNameCommand(editingDomain,
                        caseRefField,
                        nameMap,
                        linkSysDocOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_CaseRefField()));

                /* Return Document Reference Field */
                String retDocRefField =
                        linkSysDocOperation.getReturnCaseDocRefField();

                cmd.append(getSwapNameCommand(editingDomain,
                        retDocRefField,
                        nameMap,
                        linkSysDocOperation,
                        XpdExtensionPackage.eINSTANCE
                                .getLinkSystemDocumentOperation_ReturnCaseDocRefField()));
            }
        }

        /* Return only if there is a valid command */
        if (!cmd.getCommandList().isEmpty()) {
            return cmd;
        }
        return null;
    }

    /**
     * Returns Command to swap the reference of oldName with the newName.
     * returns null if the oldName and newName are same.
     * 
     * @param editingDomain
     * @param oldName
     *            old name to replace.
     * @param nameMap
     *            map of oldName and newName values.
     * @param owner
     *            Owner of the attribute.
     * @param feature
     *            feature for which the old and new values are to be swapped.
     * @return Command to Swap oldName with new Name, returns null if the
     *         oldName and newValue are same.
     */
    private Command getSwapNameCommand(EditingDomain editingDomain,
            String oldName, Map<String, String> nameMap, Object owner,
            Object feature) {

        if (oldName != null && !oldName.isEmpty()) {
            String newName = nameMap.get(oldName);
            if (newName != null && !newName.equals(oldName)) {

                return SetCommand
                        .create(editingDomain, owner, feature, newName);
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
        // Not required
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
        // On Deletion Let it complain through the validations for missing data
        // field
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
        // Not required
        return null;
    }

    /**
     * @see com.tibco.xpd.xpdl2.resolvers.IFieldContextResolverExtension#getDataReferences(com.tibco.xpd.xpdl2.Activity,
     *      java.util.Set)
     * 
     * @param activity
     * @param dataSet
     * @return Set of {@link ProcessDataReferenceAndContexts} for the given
     *         Activity.
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
        // Not required
        return null;
    }

}
