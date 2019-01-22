/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * XPD Perspective Factory
 * 
 * @author WojciechZ
 */
public class XPDPerspectiveFactory implements IPerspectiveFactory {

    /**
     * Main XPD perspective ID (Modeling perspective)
     */
    public final static String ID = "com.tibco.modeling.perspective"; //$NON-NLS-1$

    private final static String TEAM_SYNCHRONIZING_PERSPECTIVE_ID = "org.eclipse.team.ui.TeamSynchronizingPerspective"; //$NON-NLS-1$
    private final static String SVN_REPOSITORY_EXPLORING_PERSPECTIVE_ID = "org.tigris.subversion.subclipse.ui.svnPerspective"; //$NON-NLS-1$
    private final static String REPORT_DESIGN_PERSPECTIVE_ID = "org.eclipse.birt.report.designer.ui.ReportPerspective"; //$NON-NLS-1$

    public XPDPerspectiveFactory() {
        super();
    }

    /**
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout) {

        layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$
        layout.addPerspectiveShortcut(ID);
        layout.addPerspectiveShortcut(TEAM_SYNCHRONIZING_PERSPECTIVE_ID);
        layout.addPerspectiveShortcut(SVN_REPOSITORY_EXPLORING_PERSPECTIVE_ID);
        layout.addPerspectiveShortcut(REPORT_DESIGN_PERSPECTIVE_ID);

        // views - standard workbench
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        layout
                .addShowViewShortcut("org.eclipse.ui.cheatsheets.views.CheatSheetView"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$

        // views and editors
        String editorArea = layout.getEditorArea();

        // Top left: Resource Navigator view.
        IFolderLayout topLeft = layout.createFolder("topLeft", //$NON-NLS-1$
                IPageLayout.LEFT, 0.25f, editorArea);
        topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$

        // Bottom left: outline & details
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", //$NON-NLS-1$
                IPageLayout.BOTTOM, 0.70f, "topLeft"); //$NON-NLS-1$
        bottomLeft.addView(IPageLayout.ID_OUTLINE);

        // Bottom: problems, raw xpdl
        IFolderLayout bottom = layout.createFolder("bottom", //$NON-NLS-1$
                IPageLayout.BOTTOM, 0.60f, editorArea);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

    }
}