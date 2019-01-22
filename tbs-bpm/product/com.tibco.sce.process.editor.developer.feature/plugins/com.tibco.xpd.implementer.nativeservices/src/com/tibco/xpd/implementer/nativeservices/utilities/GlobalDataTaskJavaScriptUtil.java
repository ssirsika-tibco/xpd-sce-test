/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.utilities;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CompositeIdentifierType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
import com.tibco.xpd.xpdExtension.DeleteByCaseIdentifierType;
import com.tibco.xpd.xpdExtension.DeleteByCompositeIdentifiersType;
import com.tibco.xpd.xpdExtension.GlobalDataOperation;
import com.tibco.xpd.xpdExtension.RemoveLinkAssociationsType;
import com.tibco.xpd.xpdExtension.UpdateCaseOperationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Helper class to generate JavaScript for the Global Data service task.
 * 
 * @author njpatel
 * @since 10 Jan 2014
 */
public class GlobalDataTaskJavaScriptUtil {

    private static final String CAC_PREFIX = "cac_"; //$NON-NLS-1$

    /*
     * Javascript to update an array case object from an array local data: //
     * 1:CAC name // 2: Case reference field name // 3: Local data field name
     */
    private static final String UPDATE_ARRAY_METHOD =
            "%1$s.update(%2$s, %3$s);"; //$NON-NLS-1$

    /*
     * Javascript to update a case object from a local data field: // 1: Case
     * reference field name // 2: Case class name // 3: Local data field name.
     */
    private static final String UPDATE_METHOD = "%1$s.update%2$s(%3$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete an array case object : // 1:CAC name // 2: Case
     * reference field name
     */
    private static final String DELETE_ARRAY_METHOD = "%1$s.deleteRefs(%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete a case object: // 1: Case reference field name // 2:
     * Case class name.<p> XPD-6151 : call to method
     * Process.checkIfSafeToDeleteCase(caseRef) will be added prior to this
     * script to help with the safe deletion of case class.
     */

    private static final String DELETE_IN_BIZPROCESS_METHOD =
            "Process.checkIfSafeToDeleteCase(%1$s);\n%1$s.delete%2$s();"; //$NON-NLS-1$

    /*
     * XPD-6810: Different delete method for pageflow as cannot do
     * checkIfSafeToDeleteCase() in pageflows etc.
     */
    private static final String DELETE_IN_PAGEFLOW_METHOD =
            "%1$s.delete%2$s();"; //$NON-NLS-1$

    /*
     * Javascript to link a case object to an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field name
     */
    private static final String LINK_METHOD = "%1$s.link%2$s(%3$s);"; //$NON-NLS-1$

    /*
     * Javascript to unlink a case object from an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field name
     */
    private static final String UNLINK_METHOD = "%1$s.unlink%2$s(%3$s);"; //$NON-NLS-1$

    /*
     * Javascript to create a case reference from a local data field: // 1: Case
     * reference field name // 2: CAC name // 3: Local data field name
     */
    private static final String CREATE_METHOD = "%1$s = %2$s.create(%3$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete a case objects by case id(s): // 1: Case reference
     * field name // 1: CAC name // 2: association name //3: Local data field
     * name
     */
    private static final String DELETE_BY_CASEID_METHOD =
            "%1$s.deleteBy%2$s(%3$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete a case objects by composite case id(s): // 1: CAC
     * name // 2: comma-separated param list (data fields providing values for
     * the case identifiers
     */
    private static final String DELETE_BY_COMPOSITECASEID_METHOD =
            "%1$s.deleteByCompositeIdentifier(%2$s);"; //$NON-NLS-1$

    /**
     * Get the javascript for the operation defined in the provided global data
     * task.
     * 
     * @param globalDataTask
     * @return javascript string or <code>null</code> if any of the required
     *         information is missing in the activity.
     */
    public static String getGlobalDataTaskJavaScript(Activity globalDataTask) {
        if (globalDataTask != null) {
            TaskType taskType =
                    TaskObjectUtil.getTaskTypeStrict(globalDataTask);
            if (taskType == TaskType.SERVICE_LITERAL) {
                String extensionId =
                        TaskObjectUtil
                                .getTaskImplementationExtensionId(globalDataTask);

                if (TaskImplementationTypeDefinitions.GLOBAL_DATA
                        .equals(extensionId)) {
                    Implementation impl = globalDataTask.getImplementation();
                    if (impl instanceof Task) {
                        GlobalDataOperation op =
                                (GlobalDataOperation) getExtendedModel(((Task) impl)
                                        .getTaskService(),
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_GlobalDataOperation());

                        if (op != null) {
                            CaseReferenceOperationsType caseRefOp =
                                    op.getCaseReferenceOperations();

                            if (caseRefOp != null) {
                                return getCaseReferenceOperationJavaScript(globalDataTask,
                                        caseRefOp);
                            } else {
                                CaseAccessOperationsType accessOperations =
                                        op.getCaseAccessOperations();

                                if (accessOperations != null) {
                                    return getCaseAccessOperationJavascript(globalDataTask,
                                            accessOperations);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the given extended model from the <code>TaskService</code> object.
     * This assumes that there will be only one extended model in the
     * <b>##OTHER</b> of the <code>TaskService</code> object.
     * 
     * @param taskService
     *            - Get extended model from this object
     * @param documentRootRef
     *            - The extended model's document root reference
     * @return Extended model <code>EObject</code> if found, <b>null</b>
     *         otherwise.
     */
    private static EObject getExtendedModel(TaskService taskService,
            EReference documentRootRef) {
        EObject model = null;

        if (taskService != null && documentRootRef != null) {
            // Get email model
            model = taskService.getOtherElement(documentRootRef.getName());

        } else {
            throw new NullPointerException(
                    "Parameter to getExtendedModel is null."); //$NON-NLS-1$
        }

        return model;
    }

    /**
     * Get the javascript for the Case Reference operation.
     * 
     * @param globalDataTask
     * @param caseRefOp
     */
    private static String getCaseReferenceOperationJavaScript(
            Activity globalDataTask, CaseReferenceOperationsType caseRefOp) {
        String caseRefFieldName = caseRefOp.getCaseRefField();

        if (caseRefFieldName != null) {
            ProcessRelevantData caseRefField =
                    findProcessRelevantData(caseRefFieldName, globalDataTask);
            Class caseClass = null;
            if (caseRefField != null) {
                if (caseRefField.getDataType() instanceof RecordType) {
                    ExternalReference ref = null;
                    EList<Member> member =
                            ((RecordType) caseRefField.getDataType())
                                    .getMember();
                    if (!member.isEmpty()) {
                        ref = member.get(0).getExternalReference();
                    }
                    if (ref != null) {
                        caseClass =
                                getClass(ref,
                                        WorkingCopyUtil
                                                .getProjectFor(caseRefField));
                    }
                }
            }

            if (caseRefField != null && caseClass != null) {
                UpdateCaseOperationType update = caseRefOp.getUpdate();
                if (update != null) {
                    /*
                     * Update operation
                     */
                    String fromFieldPath = update.getFromFieldPath();
                    if (caseRefField.isIsArray()) {
                        return getScript(UPDATE_ARRAY_METHOD,
                                computeCACName(caseClass),
                                caseRefFieldName,
                                fromFieldPath);
                    } else {
                        return getScript(UPDATE_METHOD,
                                caseRefFieldName,
                                caseClass.getName(),
                                fromFieldPath);
                    }
                } else if (caseRefOp.getDelete() != null) {
                    if (caseRefField.isIsArray()) {
                        return getScript(DELETE_ARRAY_METHOD,
                                computeCACName(caseClass),
                                caseRefFieldName);
                    } else {
                        /*
                         * Delete operation
                         */

                        /*
                         * XPD-6810: Different delete method for pageflow as
                         * cannot do checkIfSafeToDeleteCase() in pageflows etc.
                         */
                        if (Xpdl2ModelUtil.isBusinessProcess(globalDataTask
                                .getProcess())) {
                            return getScript(DELETE_IN_BIZPROCESS_METHOD,
                                    caseRefFieldName,
                                    caseClass.getName());
                        } else {
                            return getScript(DELETE_IN_PAGEFLOW_METHOD,
                                    caseRefFieldName,
                                    caseClass.getName());
                        }
                    }
                } else if (caseRefOp.getAddLinkAssociations() != null) {
                    AddLinkAssociationsType linkAssociations =
                            caseRefOp.getAddLinkAssociations();

                    return getScript(LINK_METHOD,
                            caseRefFieldName,
                            capitalize(linkAssociations.getAssociationName()),
                            linkAssociations.getAddCaseRefField());
                } else if (caseRefOp.getRemoveLinkAssociations() != null) {
                    RemoveLinkAssociationsType linkAssociations =
                            caseRefOp.getRemoveLinkAssociations();

                    return getScript(UNLINK_METHOD,
                            caseRefFieldName,
                            capitalize(linkAssociations.getAssociationName()),
                            linkAssociations.getRemoveCaseRefField());
                }

            }
        }

        return null;
    }

    /**
     * Capitalize the given string.
     * 
     * @param assocName
     * @return
     */
    private static String capitalize(String assocName) {
        if (assocName != null && !assocName.isEmpty()) {
            return Character.toUpperCase(assocName.charAt(0))
                    + assocName.substring(1);
        }
        return null;
    }

    /**
     * Get the Class referenced from the external reference
     * 
     * @param ref
     * @param project
     * @return
     */
    private static Class getClass(ExternalReference ref, IProject project) {
        if (ref != null && project != null) {
            return (Class) ConceptUtil
                    .getComplexDataTypeClassfier(new ComplexDataTypeReference(
                            ref.getLocation(), ref.getXref(), ref
                                    .getNamespace()),
                            project);
        }
        return null;
    }

    /**
     * Get the case access operation javascript.
     * 
     * @param globalDataTask
     * @param accessOperations
     * @return
     */
    private static String getCaseAccessOperationJavascript(
            Activity globalDataTask, CaseAccessOperationsType accessOperations) {
        ExternalReference extRef =
                accessOperations.getCaseClassExternalReference();

        if (extRef != null) {
            Class caseClass =
                    getClass(extRef,
                            WorkingCopyUtil.getProjectFor(globalDataTask));

            if (caseClass != null) {
                CreateCaseOperationType create = accessOperations.getCreate();
                if (create != null) {
                    return getScript(CREATE_METHOD,
                            create.getToCaseRefField(),
                            computeCACName(caseClass),
                            create.getFromFieldPath());
                } else if (accessOperations.getDeleteByCaseIdentifier() != null) {
                    DeleteByCaseIdentifierType deleteByCaseId =
                            accessOperations.getDeleteByCaseIdentifier();

                    return getScript(DELETE_BY_CASEID_METHOD,
                            computeCACName(caseClass),
                            capitalize(deleteByCaseId.getIdentifierName()),
                            deleteByCaseId.getFieldPath());
                } else if (accessOperations.getDeleteByCompositeIdentifiers() != null
                        && accessOperations.getDeleteByCompositeIdentifiers()
                                .getCompositeIdentifier().size() > 1) {
                    DeleteByCompositeIdentifiersType deleteType =
                            accessOperations.getDeleteByCompositeIdentifiers();

                    StringBuffer paramList = new StringBuffer();
                    EList<CompositeIdentifierType> identifiers =
                            deleteType.getCompositeIdentifier();
                    for (int idx = 0; idx < identifiers.size(); idx++) {
                        if (idx > 0) {
                            paramList.append(","); //$NON-NLS-1$
                        }

                        String fieldPath = identifiers.get(idx).getFieldPath();
                        if (fieldPath == null || fieldPath.isEmpty()) {
                            // Can't create javascript as data is missing
                            return null;
                        }

                        paramList.append(fieldPath);
                    }

                    return getScript(DELETE_BY_COMPOSITECASEID_METHOD,
                            computeCACName(caseClass),
                            paramList.toString());
                }
            }
        }
        return null;
    }

    /**
     * Checks that none of the params are <code>null</code> or empty before
     * returning the formatted script.
     * 
     * @param scriptPattern
     * @param params
     * @return
     */
    private static String getScript(String scriptPattern, Object... params) {
        for (Object param : params) {
            if (param == null
                    || (param instanceof String && ((String) param).isEmpty())) {
                return null;
            }
        }
        return String.format(scriptPattern, params);
    }

    /**
     * COPIED FROM CaseAccessJsClass.java
     * 
     * @param caseClass
     * @return String
     */
    private static String computeCACName(Class caseClass) {

        // FIXGlobalData use BDS API to get CAC name
        if (BOMGlobalDataUtils.isCaseClass(caseClass)) {
            StringBuffer cacClassName =
                    new StringBuffer(CAC_PREFIX
                            + caseClass.getQualifiedName().replace('.', '_')
                                    .replace("::", "_")); //$NON-NLS-1$ //$NON-NLS-2$

            return cacClassName.toString();
        }
        return null;
    }

    /**
     * Find the process data field with the given name within the scope if this
     * activity.
     * 
     * @param name
     * @param activity
     * @return
     */
    private static ProcessRelevantData findProcessRelevantData(String name,
            Activity activity) {
        if (name != null) {
            List<ProcessRelevantData> relevantData =
                    ProcessDataUtil.getProcessRelevantData(activity);
            for (ProcessRelevantData data : relevantData) {
                if (name.equals(data.getName())) {
                    return data;
                }
            }
        }
        return null;
    }

}
