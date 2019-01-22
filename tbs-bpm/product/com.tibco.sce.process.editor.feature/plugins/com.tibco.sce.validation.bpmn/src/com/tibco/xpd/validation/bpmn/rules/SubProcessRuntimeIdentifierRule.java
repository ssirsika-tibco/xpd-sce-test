/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author Miguel Torres
 */
public class SubProcessRuntimeIdentifierRule extends
        FlowContainerValidationRule {

    /** The issue id. */
    public static final String RUNTIME_IDENTIFIER_WRONG_TYPE =
            "bpmn.subProcessRuntimeIdentWrongType"; //$NON-NLS-1$

    public static final String RUNTIME_IDENTIFIER_NOT_SPECIFIED =
            "bpmn.subProcessRuntimeIdentifierNotSpecifiedRule"; //$NON-NLS-1$

    /**
     * @param flowCont
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(FlowContainer flowCont) {
        for (Object next : flowCont.getActivities()) {
            Activity activity = (Activity) next;
            Implementation implementation = activity.getImplementation();

            if (implementation instanceof SubFlow) {
                if (isProcessInterfaceInvoked(activity)) {
                    Object obj =
                            Xpdl2ModelUtil
                                    .getOtherAttribute((SubFlow) implementation,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ProcessIdentifierField());
                    checkRuntimeIdentifier(obj, activity);
                }
            }
        }
    }

    /**
     * @param obj
     *            The sub-flow.
     */
    protected void checkRuntimeIdentifier(Object obj, Activity activity) {

        if (obj instanceof String) {
            String runtimeIdentifier = (String) obj;

            ProcessRelevantData specifiedPRD =
                    getProcessRelevantData(runtimeIdentifier, activity);

            DataType dataType = null;
            boolean correctType = true;
            if (specifiedPRD != null) {
                dataType = specifiedPRD.getDataType();
            }
            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;
                if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {
                    correctType = false;
                }
            } else if (dataType instanceof DeclaredType) {
                DeclaredType declared = (DeclaredType) dataType;
                String typeId = declared.getTypeDeclarationId();
                Package pckg = Xpdl2ModelUtil.getPackage(activity);
                if (pckg != null && typeId != null) {
                    TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
                    if (typeDecl != null) {
                        if (typeDecl.getBasicType() != null) {
                            BasicType resolved = typeDecl.getBasicType();
                            if (resolved.getType()
                                    .equals(BasicTypeType.PERFORMER_LITERAL)) {
                                correctType = false;
                            }
                        }
                    }
                }
            }
            if (!correctType) {
                addIssue(RUNTIME_IDENTIFIER_WRONG_TYPE, activity);
            }
        } else if (obj == null) {
            addIssue(RUNTIME_IDENTIFIER_NOT_SPECIFIED, activity);
        }
    }

    /**
     * Returns true when the activity is invoking a process interface
     * 
     * @param activity
     * @return
     */
    protected boolean isProcessInterfaceInvoked(Activity activity) {
        EObject subproc = TaskObjectUtil.getSubProcessOrInterface(activity);
        if (subproc instanceof ProcessInterface) {
            return true;
        }
        return false;
    }

    /**
     * @param identifierField
     * @param activity
     */
    protected ProcessRelevantData getProcessRelevantData(
            String identifierField, Activity activity) {
        List<ProcessRelevantData> prdList =
                ProcessInterfaceUtil
                        .getAllAvailableRelevantDataForActivity(activity);
        for (ProcessRelevantData prd : prdList) {
            if (prd.getName().equals(identifierField)) {
                return prd;
            }
        }
        return null;
    }

}
