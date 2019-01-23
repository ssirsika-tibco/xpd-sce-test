/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.resources.refactoring;

import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;

/**
 * 
 * @author mtorres
 * 
 */
public class WSMappingBOMReferenceChangePreviewViewer extends AbstractReferenceChangePreviewViewer {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ltk.ui.refactoring.IChangePreviewViewer#setInput(org.eclipse
     * .ltk.ui.refactoring.ChangePreviewViewerInput)
     */
    public void setInput(ChangePreviewViewerInput input) {
        if (input != null && getViewer() != null
                && !getViewer().getControl().isDisposed()) {
            Change change = input.getChange();
            if (change instanceof WSMappingBOMReferenceChange) {
                WSMappingBOMReferenceChange refactorChange = (WSMappingBOMReferenceChange) change;

                getViewer().setInput(new DiffNode(refactorChange.getCurrentValue(),
                        refactorChange.getRefactoredValue()));
            }
        }
    }
}
