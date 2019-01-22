/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Project lifecycle refactor change provider. This will define the change
 * contributed by a participant. This change will also carry out the
 * refactoring.
 * <p>
 * If modifying a model that has a {@link WorkingCopy} use
 * <code>ProjectVersionRefactorCommandChange</code>.
 * </p>
 * 
 * @see ProjectVersionRefactorCommandChange
 * @author njpatel
 * 
 */
public abstract class ProjectVersionRefactorChange extends Change implements
        IProjectRefactorChange {

    private final String label;

    private final ProjectVersionRefactoringArguments args;

    private final int refactorHint;

    /**
     * Project version refactor change provider.
     * <p>
     * The hint can be an OR combination of the following values (or
     * {@link IProjectRefactorChange#REFACTOR_ALL REFACTOR_ALL} if refactoring
     * all values in the project details):
     * <ul>
     * <li>{@link IProjectRefactorChange#REFACTOR_ID REFACTOR_ID}</li>
     * <li>{@link IProjectRefactorChange#REFACTOR_VERSION REFACTOR_VERSION}</li>
     * <li>{@link IProjectRefactorChange#REFACTOR_STATUS REFACTOR_STATUS}</li>
     * <li>{@link IProjectRefactorChange#REFACTOR_DESTINATIONS
     * REFACTOR_DESTINATIONS}</li>
     * </ul>
     * </p>
     * 
     * @param label
     *            name of this change that will be used in the UI
     * @param changeArguments
     *            new details of the change
     * @param refactorHint
     *            hint to indicate what values from the project details will be
     *            refactored. This value will be used to compose the content of
     *            the compare view in the refactor dialog.
     */
    public ProjectVersionRefactorChange(String label,
            ProjectVersionRefactoringArguments changeArguments, int refactorHint) {
        Assert.isLegal(refactorHint > 0, "Refactor hint is not valid"); //$NON-NLS-1$
        Assert.isNotNull(changeArguments, "No project details arguments set"); //$NON-NLS-1$
        this.label = label;
        this.args = changeArguments;
        this.refactorHint = refactorHint;
    }

    /**
     * Get the current value of the given attribute.
     * <p>
     * The type will be one of the following:
     * <ul>
     * <li>{@link IProjectRefactorChange#REFACTOR_ID REFACTOR_ID} - return value
     * expected to be a <code>String</code></li>
     * <li>{@link IProjectRefactorChange#REFACTOR_VERSION REFACTOR_VERSION} -
     * return value expected to be a <code>String</code></li>
     * <li>{@link IProjectRefactorChange#REFACTOR_STATUS REFACTOR_STATUS} -
     * return value expected to be a <code>String</code></li>
     * <li>{@link IProjectRefactorChange#REFACTOR_DESTINATIONS
     * REFACTOR_DESTINATIONS} - return value expected to be a
     * <code>Collection</code> of <code>String</code>s</li>
     * </ul>
     * </p>
     * 
     * @param type
     *            attribute type
     * 
     * @return <code>String</code>, <code>Collection</code> of
     *         <code>String</code>s or <code>null</code> if no value available
     *         for the given attribute.
     */
    protected abstract Object getCurrentValue(int type);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.refactoring.IProjectRefactorChange#getRefactorHint
     * ()
     */
    public int getRefactorHint() {
        return refactorHint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.refactoring.IProjectRefactorChange#getChangeArguments
     * ()
     */
    public ProjectVersionRefactoringArguments getChangeArguments() {
        return args;
    }

    @Override
    public String getName() {
        return label;
    }

    /**
     * Default implementation does nothing. Subclasses may override to carry out
     * initialization.
     * 
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void initializeValidationData(IProgressMonitor pm) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.refactoring.IProjectRefactorChange#getCurrentValue
     * ()
     */
    public PreviewDescription getCurrentValue() {
        String id = ""; //$NON-NLS-1$
        String version = "";//$NON-NLS-1$
        String status = "";//$NON-NLS-1$
        String[] destinations = null;
        Object value;
        if (isHintSet(REFACTOR_ID, getRefactorHint())) {
            value = getCurrentValue(REFACTOR_ID);
            if (value instanceof String) {
                id = (String) value;
            }
        }
        if (isHintSet(REFACTOR_VERSION, getRefactorHint())) {
            value = getCurrentValue(REFACTOR_VERSION);
            if (value instanceof String) {
                version = (String) value;
            }
        }
        if (isHintSet(REFACTOR_STATUS, getRefactorHint())) {
            value = getCurrentValue(REFACTOR_STATUS);
            if (value instanceof String) {
                status = (String) value;
            }
        }
        if (isHintSet(REFACTOR_DESTINATIONS, getRefactorHint())) {
            value = getCurrentValue(REFACTOR_DESTINATIONS);
            List<String> dests = new ArrayList<String>();

            if (value instanceof Collection<?>) {
                for (Object item : (Collection<?>) value) {
                    if (item instanceof String) {
                        dests.add((String) item);
                    }
                }
            } else if (value instanceof String) {
                dests.add((String) value);
            }
            destinations = dests.toArray(new String[dests.size()]);
        }
        return new PreviewDescription(
                String
                        .format(Messages.ProjectVersionRefactorChange_compareView_leftSide_label,
                                getName()), getContent(id,
                        version,
                        status,
                        destinations));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.refactoring.IProjectRefactorChange#getRefactoredValue
     * ()
     */
    public PreviewDescription getRefactoredValue() {
        ProjectVersionRefactoringArguments arguments = getChangeArguments();
        return new PreviewDescription(
                String
                        .format(Messages.ProjectVersionRefactorChange_compareView_rightSide_label,
                                getName()), getContent(arguments.getId(),
                        arguments.getVersion(),
                        arguments.getStatus() != null ? arguments.getStatus()
                                .getLabel() : null,
                        arguments.getDestinations()));
    }

    /**
     * Check if the given hint value is set in the provided hint.
     * 
     * @param hint
     *            the value to test
     * @param valueToTest
     *            the attribute to test for
     * @return <code>true</code> if the hint is set, <code>false</code>
     *         otherwise.
     */
    protected boolean isHintSet(int hint, int valueToTest) {
        return (hint & valueToTest) != 0;
    }

    /**
     * Get the serialized content for the given values. This will be used to
     * provide the textual description for the compare view.
     * 
     * @param id
     * @param version
     * @param status
     * @param destinations
     * @return
     */
    private String getContent(String id, String version, String status,
            String[] destinations) {
        StringBuffer buffer = new StringBuffer();

        if (isHintSet(REFACTOR_ID, getRefactorHint())) {
            buffer.append(Messages.ProjectVersionRefactorChange_id_label);
            buffer.append("\n\t" + id != null ? id : ""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (buffer.length() > 0) {
            buffer.append("\n"); //$NON-NLS-1$
        }

        if (isHintSet(REFACTOR_VERSION, getRefactorHint())) {
            buffer.append(Messages.ProjectVersionRefactorChange_version_label);
            buffer.append("\n\t" + version != null ? version : ""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (buffer.length() > 0) {
            buffer.append("\n"); //$NON-NLS-1$
        }

        if (isHintSet(REFACTOR_STATUS, getRefactorHint())) {
            buffer.append(Messages.ProjectVersionRefactorChange_status_label);
            buffer.append("\n\t" + status != null ? status : ""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (buffer.length() > 0) {
            buffer.append("\n"); //$NON-NLS-1$
        }

        if (isHintSet(REFACTOR_DESTINATIONS, getRefactorHint())) {
            buffer
                    .append(Messages.ProjectVersionRefactorChange_destinations_label);
            if (destinations != null) {
                for (String dest : destinations) {
                    if (dest != null) {
                        buffer.append("\n\t" + dest); //$NON-NLS-1$
                    }
                }
            }
        }

        return buffer.toString();
    }
}
