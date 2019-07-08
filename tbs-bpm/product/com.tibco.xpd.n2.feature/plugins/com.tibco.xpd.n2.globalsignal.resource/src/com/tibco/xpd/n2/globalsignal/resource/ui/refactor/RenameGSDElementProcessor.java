/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

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
import org.eclipse.ltk.core.refactoring.resource.Resources;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.ui.refactor.XPDRenameArguments;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rename processor for GSD elements. The processor will rename the element and
 * load rename participants if references should be renamed as well.
 * 
 * @author sajain
 * @since May 12, 2015
 */
public class RenameGSDElementProcessor extends RenameProcessor {

    /**
     * GSD element being renamed.
     */
    private EObject element;

    /**
     * New name for the GSD element.
     */
    private String newElementName;

    /**
     * Display name if the element is payload data (display names don't apply
     * for global signals).
     */
    private String newDisplayName;

    /**
     * Boolean flag to indicate if we've to update references or not.
     */
    private boolean updateReferences;

    /**
     * Rename arguments.
     */
    private XPDRenameArguments renameArguments; // set after
                                                // checkFinalConditions

    /**
     * Creates a new rename resource processor.
     * 
     * @param eObject
     *            the resource to rename.
     */
    public RenameGSDElementProcessor(EObject element) {

        if (element == null) {

            throw new IllegalArgumentException(
                    Messages.RenameGSDElementProcessor_ElementDoesNotExistException_Text);
        }

        this.element = element;
        this.renameArguments = null;
        this.updateReferences = true;

        if (element instanceof PayloadDataField) {

            PayloadDataField pdf = (PayloadDataField) element;

            /*
             * Initialize new name
             */
            setNewElementName(pdf.getName());

            /*
             * Initialize display name
             */
            setNewDisplayElementName(Xpdl2ModelUtil.getDisplayName(pdf));

        } else if (element instanceof GlobalSignal) {

            GlobalSignal gs = (GlobalSignal) element;

            /*
             * Initialize new name
             */
            setNewElementName(gs.getName());
        }
    }

    /**
     * Returns the resource this processor was created on.
     * 
     * @return The resource to rename.
     */
    public EObject getEObject() {

        return element;
    }

    /**
     * Returns the new name of the GSD element.
     * 
     * @return New name of the GSD element.
     */
    public String getNewElementName() {

        return newElementName;
    }

    /**
     * Get old name of the GSD element.
     * 
     * @return Old name of the GSD element.
     */
    public String getOldElementName() {

        if (getEObject() instanceof GlobalSignal) {

            return ((GlobalSignal) getEObject()).getName();

        } else if (getEObject() instanceof PayloadDataField) {

            return ((PayloadDataField) getEObject()).getName();
        }

        return null;
    }

    /**
     * Get old diplay name or label of the GSD element (which would be payload
     * only as global signals don't have labels).
     * 
     * @return Old diplay name or label of the GSD element (which would be
     *         payload only as global signals don't have labels).
     */
    public String getOldElementDisplayName() {

        if (getEObject() instanceof PayloadDataField) {

            return Xpdl2ModelUtil
                    .getDisplayName((PayloadDataField) (getEObject()));
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

        if (newName != null) {

            newElementName = newName;
        }
    }

    /**
     * Returns the display element name.
     * 
     * @return The display element name.
     */
    public String getNewElementDisplayName() {

        return newDisplayName;
    }

    /**
     * Sets the new resource name.
     * 
     * @param newName
     *            The new resource name.
     */
    public void setNewDisplayElementName(String displayName) {

        newDisplayName = displayName;
    }

    /**
     * Returns <code>true</code> if the refactoring processor also updates
     * references, <code>false</code> otherwise
     * 
     * @return <code>true</code> if the refactoring processor also updates
     *         references, <code>false</code> otherwise
     */
    public boolean isUpdateReferences() {

        return updateReferences;
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

        this.updateReferences = updateReferences;
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
                .getFile(element)));
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

            renameArguments =
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
     *            The name to validate
     * 
     * @return The resulting status of the validation
     */
    public RefactoringStatus validateNewElementName(String newName) {

        if (newName == null) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameNotNull);

        } else if (newName.equals("")) {//$NON-NLS-1$

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameNotEmpty);

        } else if (getOldElementName() != null
                && newName.equals(getOldElementName())) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameInvalid);

        } else if (!NameUtil.isValidName(newName, false)) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameCanOnlyContainAlphanumericCharactersAndUnderScores);

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
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameNotNull);

        } else if (newName.equals("") || newLabel.equals("")) {//$NON-NLS-1$ //$NON-NLS-2$

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameNotEmpty);

        } else if (getOldElementName() != null
                && (newName.equals(getOldElementName()) && newLabel
                        .equals(getOldElementDisplayName()))) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameInvalid);

        } else if (!NameUtil.isValidName(newName, false)) {

            return RefactoringStatus
                    .createFatalErrorStatus(Messages.RenameGSDElementProcessor_NewNameCanOnlyContainAlphanumericCharactersAndUnderScores);

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

        return new Object[] { element };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * getIdentifier()
     */
    @Override
    public String getIdentifier() {

        return "com.tibco.xpd.n2.globalsignal.resource.ui.renameGSDElementProcessor"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * getProcessorName()
     */
    @Override
    public String getProcessorName() {
        return Messages.RenameGSDElementProcessor_RenameGSDElements;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
     * isApplicable()
     */
    @Override
    public boolean isApplicable() {

        return element != null;
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
                element,
                renameArguments,
                new String[] { XpdConsts.PROJECT_NATURE_ID },
                sharedParticipants);
    }

}
