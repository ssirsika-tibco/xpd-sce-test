/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.refactoring;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ParticipantManager;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.internal.core.refactoring.Resources;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.refactor.XPDRenameArguments;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * A rename processor for Bom elements. The processor will rename the element
 * and load rename participants if references should be renamed as well.
 * 
 * @author mtorres
 * 
 */
public class RenameBOMElementProcessor extends RenameProcessor {

    /**
     * BOM element being renamed.
     */
    private NamedElement fElement;

    /**
     * New name for the GSD element.
     */
    private String fNewElementName;

    /**
     * Display name/label.
     */
    private String newDisplayName;

    /**
     * Boolean flag to indicate if we've to update references or not.
     */
    private boolean fUpdateReferences;

    /**
     * Rename arguments.
     */
    private XPDRenameArguments fRenameArguments; // set after
                                                 // checkFinalConditions

    /**
     * Creates a new rename resource processor.
     * 
     * @param eObject
     *            the resource to rename.
     */
    public RenameBOMElementProcessor(NamedElement element) {
        if (element == null) {
            throw new IllegalArgumentException(
                    "eObject must not be null and must exist"); //$NON-NLS-1$
        }

        fElement = element;
        fRenameArguments = null;
        fUpdateReferences = true;
        setNewElementName(element.getName()); // Initialize new name
        setNewElementDisplayName(PrimitivesUtil.getDisplayLabel(element)); // Initialize
                                                                           // display
                                                                           // name
    }

    /**
     * Returns the resource this processor was created on
     * 
     * @return the resource to rename
     */
    public EObject getEObject() {
        return fElement;
    }

    /**
     * Returns the new element name
     * 
     * @return the new element name
     */
    public String getNewElementName() {
        return fNewElementName;
    }

    public String getOldElementName() {
        if (getEObject() instanceof NamedElement) {
            return ((NamedElement) getEObject()).getName();
        }
        return null;
    }

    /**
     * Get old diplay name or label of the BOM element.
     * 
     * @return Get old diplay name or label of the BOM element.
     */
    public String getOldElementDisplayName() {

        if (getEObject() instanceof NamedElement) {

            return PrimitivesUtil
                    .getDisplayLabel((NamedElement) (getEObject()));
        }

        return null;
    }

    /**
     * Sets the new element name
     * 
     * @param newName
     *            the new element name
     */
    public void setNewElementName(String newName) {
        Assert.isNotNull(newName);
        fNewElementName = newName;
    }

    /**
     * Returns the display element name
     * 
     * @return the display element name
     */
    public String getNewElementDisplayName() {
        return newDisplayName;
    }

    /**
     * Sets the new resource name
     * 
     * @param newName
     *            the new resource name
     */
    public void setNewElementDisplayName(String displayName) {
        newDisplayName = displayName;
    }

    /**
     * Returns <code>true</code> if the refactoring processor also updates
     * references
     * 
     * @return <code>true</code> if the refactoring processor also updates
     *         references
     */
    public boolean isUpdateReferences() {
        return fUpdateReferences;
    }

    /**
     * Specifies if the refactoring processor also updates references. The
     * default behaviour is to update references.
     * 
     * @param updateReferences
     *            <code>true</code> if the refactoring processor should also
     *            updates references
     */
    public void setUpdateReferences(boolean updateReferences) {
        fUpdateReferences = updateReferences;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
            throws CoreException {
        return RefactoringStatus.create(Resources.checkInSync(WorkingCopyUtil
                .getFile(fElement)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor,
     * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     */
    @Override
    public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws CoreException {
        pm.beginTask("", 1); //$NON-NLS-1$
        try {
            fRenameArguments =
                    new XPDRenameArguments(getNewElementName(),
                            isUpdateReferences(), getOldElementDisplayName(),
                            getNewElementDisplayName());
            return new RefactoringStatus();
        } finally {
            pm.done();
        }
    }

    /**
     * Validates if the a name is valid. This method does not change the name
     * settings on the refactoring. It is intended to be used in a wizard to
     * validate user input.
     * 
     * @param newName
     *            the name to validate
     * @return returns the resulting status of the validation
     */
    public RefactoringStatus validateNewElementName(String newName) {
        if (newName == null) {
            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameNotNull1);
        } else if (newName.equals("")) {//$NON-NLS-1$
            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameNotEmpty1);
        } else if (getOldElementName() != null
                && newName.equals(getOldElementName())) {
            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameInvalid1);
        }
        return new RefactoringStatus();
    }

    /**
     * Validates if the a name is valid and either label or name has changed.
     * This method does not change the name settings on the refactoring. It is
     * intended to be used in a wizard to validate user input.
     * 
     * @param newName
     *            The name to validate
     * @param newLabel
     *            The label to validate
     * 
     * @return The resulting status of the validation
     */
    public RefactoringStatus validateNewElementName(String newName,
            String newLabel) {

        if (newName == null || newLabel == null) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameNotNull1);

        } else if (newName.equals("") || newLabel.equals("")) {//$NON-NLS-1$ //$NON-NLS-2$

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameNotEmpty1);

        } else if (getOldElementName() != null
                && (newName.equals(getOldElementName()) && newLabel
                        .equals(getOldElementDisplayName()))) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameBOMElementProcessor_NewNameInvalid1);

        }

        return new RefactoringStatus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * createChange(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * getElements()
     */
    @Override
    public Object[] getElements() {
        return new Object[] { fElement };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return "com.tibco.xpd.bom.resources.ui.refactoring.renameBOMElementProcessor"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * getProcessorName()
     */
    @Override
    public String getProcessorName() {
        return Messages.RenameBOMElementProcessor_RenameBOMElements2;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * isApplicable()
     */
    @Override
    public boolean isApplicable() {
        return fElement != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus,
     * org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
     */
    @Override
    public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
            SharableParticipants sharedParticipants) throws CoreException {
        return ParticipantManager.loadRenameParticipants(status,
                this,
                fElement,
                fRenameArguments,
                new String[] { XpdConsts.PROJECT_NATURE_ID },
                sharedParticipants);
    }

}
