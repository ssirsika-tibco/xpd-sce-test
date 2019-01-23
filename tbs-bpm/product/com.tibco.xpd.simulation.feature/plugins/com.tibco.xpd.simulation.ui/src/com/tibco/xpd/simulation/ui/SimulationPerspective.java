package com.tibco.xpd.simulation.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SimulationPerspective implements IPerspectiveFactory {

    /**
     * XPD Perspective ID
     */
    public final static String ID = "com.tibco.xpd.simulation.perspective"; //$NON-NLS-1$

    /**
     * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
     */
    public void createInitialLayout(IPageLayout layout) {

        layout.addShowViewShortcut("com.tibco.xpd.xpdl.editor.details.view"); //$NON-NLS-1$		
        layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$
        layout.addShowViewShortcut("org.eclipse.ui.views.ProgressView"); //$NON-NLS-1$
        layout.addPerspectiveShortcut("com.tibco.modeling.perspective"); //$NON-NLS-1$
        layout.addPerspectiveShortcut("com.tibco.xpd.simulation.perspective"); //$NON-NLS-1$

        // views - standard workbench
        layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        layout
                .addShowViewShortcut("org.eclipse.ui.cheatsheets.views.CheatSheetView"); //$NON-NLS-1$
        layout.addShowViewShortcut("org.eclipse.gef.ui.palette_view"); //$NON-NLS-1$

        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$

        // views and editors
        String editorArea = layout.getEditorArea();

        // Top left: Resource Navigator view.
        IFolderLayout topLeft = layout.createFolder("topLeft", //$NON-NLS-1$
                IPageLayout.LEFT, 0.25f, editorArea);
        topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$
        topLeft.addView(IPageLayout.ID_RES_NAV);

        // Bottom left: outline & details
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", //$NON-NLS-1$
                IPageLayout.BOTTOM, 0.65f, "topLeft"); //$NON-NLS-1$
        bottomLeft
                .addView("com.tibco.xpd.simulation.ui.views.SimulationControlView"); //$NON-NLS-1$
        bottomLeft.addView(IPageLayout.ID_OUTLINE);

        // Bottom: problems, raw xpdl
        IFolderLayout bottom = layout.createFolder("bottom", //$NON-NLS-1$
                IPageLayout.BOTTOM, 0.65f, editorArea); //$NON-NLS-1$
        bottom
                .addView("com.tibco.xpd.simulation.ui.views.SimulationResultsView"); //$NON-NLS-1$
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

        layout.addActionSet("org.eclipse.debug.ui.launchActionSet"); //$NON-NLS-1$
    }

}
