/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.nativeservices.documentoperationsservice.CaseDocumentOperationsHelpUtiliy;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.CMISQueryOperator;
import com.tibco.xpd.xpdExtension.CaseDocFindOperations;
import com.tibco.xpd.xpdExtension.CaseDocumentQueryExpression;
import com.tibco.xpd.xpdExtension.DocumentOperation;
import com.tibco.xpd.xpdExtension.FindByQueryOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to remove 'Value' attribute from the
 * {@link CaseDocumentQueryExpression}.
 * 
 * @author aprasad
 * @since 16-Sep-2014
 */
public class RemoveQueryExpressionValueResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return Command to remove 'Value' field from the
     *         {@link CaseDocumentQueryExpression}.
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {

            DocumentOperation documentOperation =
                    CaseDocumentOperationsHelpUtiliy
                            .getDocumentOperation((Activity) target);

            CaseDocFindOperations caseDocFindOperations =
                    documentOperation.getCaseDocFindOperations();

            if (caseDocFindOperations != null) {
                FindByQueryOperation findByQueryOperation =
                        caseDocFindOperations.getFindByQueryOperation();

                if (findByQueryOperation != null) {

                    EList<CaseDocumentQueryExpression> caseDocumentQueryExpressions =
                            findByQueryOperation
                                    .getCaseDocumentQueryExpression();

                    if (caseDocumentQueryExpressions != null
                            && !caseDocumentQueryExpressions.isEmpty()) {

                        CompoundCommand cmd = new CompoundCommand();
                        for (CaseDocumentQueryExpression caseDocumentQueryExpression : caseDocumentQueryExpressions) {

                            if (CMISQueryOperator.NULL
                                    .equals(caseDocumentQueryExpression
                                            .getOperator())
                                    || CMISQueryOperator.NOT_NULL
                                            .equals(caseDocumentQueryExpression
                                                    .getOperator())) {

                                /* Remove Value if it is set */

                                if (caseDocumentQueryExpression
                                        .getProcessDataField() != null
                                        && !caseDocumentQueryExpression
                                                .getProcessDataField()
                                                .isEmpty()) {
                                    cmd.append(SetCommand
                                            .create(editingDomain,
                                                    caseDocumentQueryExpression,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getCaseDocumentQueryExpression_ProcessDataField(),
                                                    null));
                                }
                            }
                        }
                        if (!cmd.getCommandList().isEmpty()) {
                            return cmd;
                        }
                    }
                }
            }
        }

        return null;

    }

}
