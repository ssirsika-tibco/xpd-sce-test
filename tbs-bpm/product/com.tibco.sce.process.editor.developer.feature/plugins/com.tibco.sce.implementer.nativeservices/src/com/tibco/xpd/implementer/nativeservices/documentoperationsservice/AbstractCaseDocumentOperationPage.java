/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.documentoperationsservice;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.DocumentOperation;

/**
 * 
 * Abstract base class for Case Document Reference Operations page.
 * 
 * @author aprasad
 * @since 05-Sep-2014
 */
public abstract class AbstractCaseDocumentOperationPage extends
        AbstractDocumentOperationPage<CaseDocRefOperations> {

    /**
     * Constructor.
     * 
     * @param label
     *            of the page.
     * @param section
     *            Parent Section, inside which this Page will be Displayed.
     */
    public AbstractCaseDocumentOperationPage(String label,
            DocumentOperationsTaskServiceSection section) {
        super(label, section);
    }

    /**
     * Appends the command to initialize {@link DocumentOperation} with new
     * {@link CaseDocRefOperations}.
     * 
     * @param cmd
     *            , Command to which the new command will be appended.
     * @param caseDocOpr
     *            , {@link DocumentOperation} to initialize.
     * @return new {@link CaseDocRefOperations} instance added to the
     *         {@link DocumentOperation}.
     */
    protected CaseDocRefOperations appendSetDocumentRefOperationCommand(
            CompoundCommand cmd, DocumentOperation caseDocOpr) {

        com.tibco.xpd.xpdExtension.CaseDocRefOperations docRefOpr =
                XPD_EXT_FAC.createCaseDocRefOperations();

        cmd.append(SetCommand.create(getEditingDomain(),
                caseDocOpr,
                XPD_EXT_PKG.getDocumentOperation_CaseDocRefOperation(),
                docRefOpr));

        return docRefOpr;
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.documentoperationsservice.AbstractDocumentOperationPage#getOperation(com.tibco.xpd.xpdExtension.DocumentOperation)
     * 
     * @param docOperation
     * @return CaseDocRefOperations child of {@link DocumentOperation}, that
     *         this Page represents.Returns if {@link DocumentOperation} is
     *         null.
     */
    @Override
    protected EObject getOperation(DocumentOperation docOperation) {

        if (docOperation != null) {

            return docOperation.getCaseDocRefOperation();

        }
        return null;
    }

}
