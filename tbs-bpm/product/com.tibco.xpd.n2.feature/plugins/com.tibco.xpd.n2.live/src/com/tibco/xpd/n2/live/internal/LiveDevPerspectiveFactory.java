package com.tibco.xpd.n2.live.internal;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * Initialisation of the Live Dev perspective.
 * 
 * @author nwilson
 * @since 2 Sep 2014
 */
public class LiveDevPerspectiveFactory implements IPerspectiveFactory {

    /**
     * ID for the project explorer view.
     */
    private static final String PROJECT_EXPLORER_VIEW_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    /**
     * ID for the openspace view.
     */
    public static final String LIVE_DEV_OPENSPACE_VIEW_ID =
            "com.tibco.xpd.n2.live.openspaceView"; //$NON-NLS-1$

    /**
     * ID for the modelling perspective.
     */
    private final static String MODELING_PERSPECTIVE_ID =
            "com.tibco.modeling.perspective"; //$NON-NLS-1$

    @Override
    public void createInitialLayout(IPageLayout layout) {
        layout.addShowViewShortcut(PROJECT_EXPLORER_VIEW_ID);
        layout.addPerspectiveShortcut(Activator.LIVE_DEV_PERSPECTIVE_ID);
        layout.addPerspectiveShortcut(MODELING_PERSPECTIVE_ID);

        layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
        layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
        layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
        layout.addShowViewShortcut("org.eclipse.ui.cheatsheets.views.CheatSheetView"); //$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");//$NON-NLS-1$
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");//$NON-NLS-1$

        // views and editors
        String editorArea = layout.getEditorArea();

        // Top left: Resource Navigator view.
        IFolderLayout topLeft = layout.createFolder("topLeft", //$NON-NLS-1$
                IPageLayout.LEFT,
                0.25f,
                editorArea);
        topLeft.addView(PROJECT_EXPLORER_VIEW_ID);

        // Bottom left: outline
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", //$NON-NLS-1$
                IPageLayout.BOTTOM,
                0.70f,
                "topLeft"); //$NON-NLS-1$
        bottomLeft.addView(IPageLayout.ID_OUTLINE);

        // Bottom: problems, properties
        IFolderLayout bottom = layout.createFolder("bottom", //$NON-NLS-1$
                IPageLayout.BOTTOM,
                0.60f,
                editorArea);
        bottom.addView(IPageLayout.ID_PROP_SHEET);
        bottom.addView(IPageLayout.ID_PROBLEM_VIEW);

        // Top right: Openspace view.
        IFolderLayout topRight = layout.createFolder("topRight", //$NON-NLS-1$
                IPageLayout.RIGHT,
                0.5f,
                editorArea);
        topRight.addView(LIVE_DEV_OPENSPACE_VIEW_ID);

    }

}
