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

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.AddLinkAssociationsType;
import com.tibco.xpd.xpdExtension.CaseAccessOperationsType;
import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.CreateCaseOperationType;
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

    /*
     * Javascript to update an array case object from an array local data: // 1:
     * Case reference field name // 2: Local data field name
     */
    private static final String UPDATE_ARRAY_METHOD = "bpm.caseData.updateAllByRef(%1$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to update a case object from a local data field: // 1: Case
     * reference field name // 2: Local data field name.
     */
    // private static final String UPDATE_METHOD = "%1$s.update%2$s(%3$s);";
    // //$NON-NLS-1$
    private static final String UPDATE_METHOD = "bpm.caseData.updateByRef(%1$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete an array case object : // 2: Case reference field
     * name
     */
    private static final String DELETE_METHOD = "bpm.caseData.deleteByRef(%1$s);"; //$NON-NLS-1$

    /*
     * Javascript to delete a case object: // 1: Case reference field name // 2:
     * Case class name.<p> XPD-6151 : call to method
     * Process.checkIfSafeToDeleteCase(caseRef) will be added prior to this
     * script to help with the safe deletion of case class.
     */

    private static final String DELETE_IN_BIZPROCESS_METHOD =
            "bpm.process.checkIfSafeToDeleteCase(%1$s);\nbpm.caseData.deleteByRef(%1$s);"; //$NON-NLS-1$

    /*
     * XPD-6810: Different delete method for pageflow as cannot do
     * checkIfSafeToDeleteCase() in pageflows etc.
     */
    // private static final String DELETE_IN_PAGEFLOW_METHOD =
    // "%1$s.delete%2$s();"; //$NON-NLS-1$

    /*
     * Javascript to link a case object to an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field name
     */
    private static final String LINK_METHOD = "bpm.caseData.link(%1$s,%3$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to link a case object to an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field names
     */
    private static final String LINK_ARRAY_METHOD = "bpm.caseData.linkAll(%1$s,%3$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to unlink a case object from an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field name
     */
    private static final String UNLINK_METHOD = "bpm.caseData.unlink(%1$s,%3$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to unlink a case object from an associated case : // 1: Case
     * reference field name // 2: association property name // 3: Link case
     * reference field names
     */
    private static final String UNLINK_ARRAY_METHOD = "bpm.caseData.unlinkAll(%1$s,%3$s,%2$s);"; //$NON-NLS-1$

    /*
     * Javascript to create a case reference from a local data field: // 1: Case
     * reference field name // 2: Case Type name // 3: Local data field name
     */
    private static final String CREATE_METHOD = "%1$s = bpm.caseData.create(%3$s,'%2$s');"; //$NON-NLS-1$

    /*
     * Javascript to create case references from local data fields: // 1: Case
     * reference field name // 2: Case Type name // 3: Local data field name
     */
    private static final String CREATE_ALL_METHOD = "%1$s = bpm.caseData.createAll(%3$s,'%2$s');"; //$NON-NLS-1$

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
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(globalDataTask);
            if (taskType == TaskType.SERVICE_LITERAL) {
                String extensionId = TaskObjectUtil.getTaskImplementationExtensionId(globalDataTask);

                if (TaskImplementationTypeDefinitions.GLOBAL_DATA.equals(extensionId)) {
                    Implementation impl = globalDataTask.getImplementation();
                    if (impl instanceof Task) {
                        GlobalDataOperation op = (GlobalDataOperation) getExtendedModel(((Task) impl).getTaskService(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_GlobalDataOperation());

                        if (op != null) {
                            CaseReferenceOperationsType caseRefOp = op.getCaseReferenceOperations();

                            if (caseRefOp != null) {
                                return getCaseReferenceOperationJavaScript(globalDataTask, caseRefOp);
                            } else {
                                CaseAccessOperationsType accessOperations = op.getCaseAccessOperations();

                                if (accessOperations != null) {
                                    return getCaseAccessOperationJavascript(globalDataTask, accessOperations);
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
    private static EObject getExtendedModel(TaskService taskService, EReference documentRootRef) {
        EObject model = null;

        if (taskService != null && documentRootRef != null) {
            // Get email model
            model = taskService.getOtherElement(documentRootRef.getName());

        } else {
            throw new NullPointerException("Parameter to getExtendedModel is null."); //$NON-NLS-1$
        }

        return model;
    }

    /**
     * Get the javascript for the Case Reference operation.
     * 
     * @param globalDataTask
     * @param caseRefOp
     */
    private static String getCaseReferenceOperationJavaScript(Activity globalDataTask,
            CaseReferenceOperationsType caseRefOp) {
        String caseRefFieldName = caseRefOp.getCaseRefField();

        if (caseRefFieldName != null) {
            String caseRefFieldAccessor = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + caseRefFieldName; //$NON-NLS-1$
            ProcessRelevantData caseRefField = findProcessRelevantData(caseRefFieldName, globalDataTask);
            Class caseClass = null;
            if (caseRefField != null) {
                if (caseRefField.getDataType() instanceof RecordType) {
                    ExternalReference ref = null;
                    EList<Member> member = ((RecordType) caseRefField.getDataType()).getMember();
                    if (!member.isEmpty()) {
                        ref = member.get(0).getExternalReference();
                    }
                    if (ref != null) {
                        caseClass = getClass(ref, WorkingCopyUtil.getProjectFor(caseRefField));
                    }
                }
            }

            if (caseRefField != null && caseClass != null) {
                UpdateCaseOperationType update = caseRefOp.getUpdate();
                if (update != null) {
                    /*
                     * Update operation
                     */
                    String fromFieldPath =
                            ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + update.getFromFieldPath(); //$NON-NLS-1$
                    if (caseRefField.isIsArray()) {
                        return getScript(UPDATE_ARRAY_METHOD, caseRefFieldAccessor, fromFieldPath);
                    } else {
                        return getScript(UPDATE_METHOD, caseRefFieldAccessor, fromFieldPath);
                    }
                } else if (caseRefOp.getDelete() != null) {
                    if (caseRefField.isIsArray()) {
                        return getScript(DELETE_METHOD, caseRefFieldAccessor);
                    } else {
                        /*
                         * Delete operation
                         */

                        /*
                         * XPD-6810: Different delete method for pageflow as
                         * cannot do checkIfSafeToDeleteCase() in pageflows etc.
                         */
                        if (Xpdl2ModelUtil.isBusinessProcess(globalDataTask.getProcess())) {
                            return getScript(DELETE_IN_BIZPROCESS_METHOD, caseRefFieldAccessor);
                        } else {
                            return getScript(DELETE_METHOD, caseRefFieldAccessor);
                        }
                    }
                } else if (caseRefOp.getAddLinkAssociations() != null) {
                    AddLinkAssociationsType linkAssociations = caseRefOp.getAddLinkAssociations();

                    String addCaseRefField = linkAssociations.getAddCaseRefField();
                    String addCaseRefAccessor = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + addCaseRefField; //$NON-NLS-1$
                    ProcessRelevantData caseLinkRefField = findProcessRelevantData(addCaseRefField, globalDataTask);

                    String linkAssociationAccessor = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." //$NON-NLS-1$
                            + capitalize(linkAssociations.getAssociationName());
                    if (caseLinkRefField != null && caseLinkRefField.isIsArray()) {
                        return getScript(LINK_ARRAY_METHOD,
                                caseRefFieldAccessor,
                                linkAssociationAccessor,
                                addCaseRefAccessor);
                    } else {
                        return getScript(LINK_METHOD,
                                caseRefFieldAccessor,
                                linkAssociationAccessor,
                                addCaseRefAccessor);
                    }
                } else if (caseRefOp.getRemoveLinkAssociations() != null) {
                    RemoveLinkAssociationsType linkAssociations = caseRefOp.getRemoveLinkAssociations();
                    String addCaseRefField = linkAssociations.getRemoveCaseRefField();
                    String addCaseRefAccessor = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + addCaseRefField; //$NON-NLS-1$
                    ProcessRelevantData caseLinkRefField = findProcessRelevantData(addCaseRefField, globalDataTask);

                    String linkAssociationAccessor = ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." //$NON-NLS-1$
                            + capitalize(linkAssociations.getAssociationName());
                    if (caseLinkRefField != null && caseLinkRefField.isIsArray()) {
                        return getScript(UNLINK_ARRAY_METHOD,
                                caseRefFieldAccessor,
                                linkAssociationAccessor,
                                addCaseRefAccessor);
                    } else {
                        return getScript(UNLINK_METHOD,
                                caseRefFieldAccessor,
                                linkAssociationAccessor,
                                addCaseRefAccessor);
                    }
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
            return Character.toUpperCase(assocName.charAt(0)) + assocName.substring(1);
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
            return (Class) ConceptUtil.getComplexDataTypeClassfier(
                    new ComplexDataTypeReference(ref.getLocation(), ref.getXref(), ref.getNamespace()),
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
    private static String getCaseAccessOperationJavascript(Activity globalDataTask,
            CaseAccessOperationsType accessOperations) {
        ExternalReference extRef = accessOperations.getCaseClassExternalReference();

        if (extRef != null) {
            Class caseClass = getClass(extRef, WorkingCopyUtil.getProjectFor(globalDataTask));

            if (caseClass != null) {
                CreateCaseOperationType create = accessOperations.getCreate();
                if (create != null) {
                    String caseRefFieldName = create.getToCaseRefField();
                    String caseRefFieldAccessor =
                            ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + caseRefFieldName; //$NON-NLS-1$
                    ProcessRelevantData caseRefField = findProcessRelevantData(caseRefFieldName, globalDataTask);
                    String caseClassName = caseClass.getQualifiedName().replace("::", "."); //$NON-NLS-1$//$NON-NLS-2$
                    String localDataAccessor =
                            ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + "." + create.getFromFieldPath(); //$NON-NLS-1$
                    if (caseRefField != null && caseRefField.isIsArray()) {
                        return getScript(CREATE_ALL_METHOD,
                                caseRefFieldAccessor,
                                caseClassName,
                                localDataAccessor);
                    } else {
                        return getScript(CREATE_METHOD,
                                caseRefFieldAccessor,
                                caseClassName,
                                localDataAccessor);
                    }
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
            if (param == null || (param instanceof String && ((String) param).isEmpty())) {
                return null;
            }
        }
        return String.format(scriptPattern, params);
    }

    /**
     * Find the process data field with the given name within the scope if this
     * activity.
     * 
     * @param name
     * @param activity
     * @return
     */
    private static ProcessRelevantData findProcessRelevantData(String name, Activity activity) {
        if (name != null) {
            List<ProcessRelevantData> relevantData = ProcessDataUtil.getProcessRelevantData(activity);
            for (ProcessRelevantData data : relevantData) {
                if (name.equals(data.getName())) {
                    return data;
                }
            }
        }
        return null;
    }

}
