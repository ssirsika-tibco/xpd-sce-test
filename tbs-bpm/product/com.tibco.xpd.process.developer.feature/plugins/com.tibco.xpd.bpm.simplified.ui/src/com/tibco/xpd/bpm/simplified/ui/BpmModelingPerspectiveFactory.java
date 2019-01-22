/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.bpm.simplified.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * "BPM Modeling" perspective factory. Only the essential views and shortcuts
 * are setup here (to establish basic anchors). All other configuration happens
 * in the "org.eclipse.ui.perspectiveExtensions" for this perspective.
 *
 * @author jarciuch
 * @since 21 May 2015
 */
public class BpmModelingPerspectiveFactory implements IPerspectiveFactory {

    /**
     * Perspective ID (Basic Modeling perspective)
     */
    public final static String ID = "com.tibco.xpd.bpm.modeling.perspective"; //$NON-NLS-1$

    /**
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    @Override
    public void createInitialLayout(IPageLayout layout) {

        //layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$
        layout.addPerspectiveShortcut(ID);

        // views - standard workbench
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$

        // views and editors
        String editorArea = layout.getEditorArea();

        // Top left: Resource Navigator view.
        IFolderLayout topLeft = layout.createFolder("topLeft", //$NON-NLS-1$
                IPageLayout.LEFT,
                0.25f,
                editorArea);
        topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$

        // Bottom left: outline & details
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", //$NON-NLS-1$
                IPageLayout.BOTTOM,
                0.70f,
                "topLeft"); //$NON-NLS-1$
        bottomLeft.addView(IPageLayout.ID_OUTLINE);

        // Bottom: problems, raw xpdl
        IFolderLayout bottom = layout.createFolder("bottom", //$NON-NLS-1$
                IPageLayout.BOTTOM,
                0.60f,
                editorArea);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
    }
}