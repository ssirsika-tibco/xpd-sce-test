/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;

import com.tibco.xpd.n2.resources.refactoring.AbstractReferenceChangePreviewViewer;

/**
 * Viewer to show Data Mapping to Payload Data references.
 * 
 * @author sajain
 * @since May 21, 2015
 */
public class PayloadDataMappingReferenceChangePreviewViewer extends
        AbstractReferenceChangePreviewViewer {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#setInput(org.eclipse
     * .ltk.ui.refactoring.ChangePreviewViewerInput)
     */
    @Override
    public void setInput(ChangePreviewViewerInput input) {

        if (input != null && getViewer() != null
                && !getViewer().getControl().isDisposed()) {

            Change change = input.getChange();

            if (change instanceof PayloadDataMappingReferenceChange) {

                PayloadDataMappingReferenceChange refactorChange =
                        (PayloadDataMappingReferenceChange) change;

                getViewer().setInput(new DiffNode(
                        refactorChange.getCurrentValue(),
                        refactorChange.getRefactoredValue()));
            }
        }
    }
}
