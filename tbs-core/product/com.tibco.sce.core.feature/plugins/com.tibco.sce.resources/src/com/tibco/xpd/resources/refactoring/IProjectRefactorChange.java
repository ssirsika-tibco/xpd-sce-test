/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import org.eclipse.ltk.core.refactoring.Change;

/**
 * Interface to be implemented by a {@link Change} to provide refactor change
 * preview during the project lifecycle refactoring.
 * 
 * @author njpatel
 * 
 */
public interface IProjectRefactorChange {
    /**
     * Hint to refactor the id
     */
    public static final int REFACTOR_ID = 1;

    /**
     * Hint to refactor the version
     */
    public static final int REFACTOR_VERSION = 1 << 1;

    /**
     * Hint to refactor the status
     */
    public static final int REFACTOR_STATUS = 1 << 2;

    /**
     * Hint to refactor the global destinations
     */
    public static final int REFACTOR_DESTINATIONS = 1 << 3;

    /**
     * Hint to refactor all project details
     */
    public static final int REFACTOR_ALL = 0xF;

    /**
     * Get the refactor hint set in this change provider.
     * 
     * @return
     */
    public int getRefactorHint();

    /**
     * Get the new project change arguments.
     * 
     * @return
     */
    public ProjectVersionRefactoringArguments getChangeArguments();

    /**
     * Get the current description of the values that are going to be refactored
     * by this change. This will be displayed in the left hand side of the
     * compare view. Subclasses may override if they wish to tailor the default
     * description.
     * 
     * @return preview description of the current value
     */
    public PreviewDescription getCurrentValue();

    /**
     * Get the refactored description of the values that are going to be
     * refactored by this change. This will be displayed in the right-hand side
     * of the compare view. Subclasses may override if they wish to tailor the
     * default description.
     * 
     * @return preview description of the refactored values.
     */
    public PreviewDescription getRefactoredValue();

}
