/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal;

import org.eclipse.osgi.util.NLS;

/**
 *
 *
 * @author ssirsika
 * @since 03-Dec-2015
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tibco.xpd.dependency.visualization.internal.messages"; //$NON-NLS-1$
	public static String AbstractVisualizationLabelProvider_CyclicDependConnectionLabel;
	public static String DependencyVisualizationEditor_BackActionText;
	public static String DependencyVisualizationEditor_BackActionToolTip;
	public static String DependencyVisualizationEditor_DepecdencyVisualizationEditorNameLastPart;
	public static String DependencyVisualizationEditor_FocusActionText;
	public static String DependencyVisualizationEditor_FocusActionTooltip;
	public static String DependencyVisualizationEditor_FocusOnActionText;
	public static String DependencyVisualizationEditor_FocusOnActionTooltip;
	public static String DependencyVisualizationEditor_FocusOnResourceActionName;
	public static String DependencyVisualizationEditor_ForwardActionText;
	public static String DependencyVisualizationEditor_ForwardActionToolTip;
	public static String DependencyVisualizationEditor_HighlightReferencedResourcesDescription;
	public static String DependencyVisualizationEditor_HighlightReferencedResourcesText;
	public static String DependencyVisualizationEditor_HighlightReferencedResourcesToolTip;
	public static String DependencyVisualizationEditor_HighlightReferencingResourcesDescription;
	public static String DependencyVisualizationEditor_HighlightReferencingResourcesText;
	public static String DependencyVisualizationEditor_HighlightReferencingResourcesToolTip;
	public static String DependencyVisualizationEditor_RefreshActionText;
	public static String DependencyVisualizationEditor_RefreshActionTooltip;
	public static String DependencyVisualizationEditor_ShowAllInWorkspaceActionTooltip;
	public static String DependencyVisualizationEditor_ShowAllInWorkspaceErrorMessage;
	public static String DependencyVisualizationEditor_ShowAllInWorkspaceProgressMessage;
	public static String DependencyVisualizationEditor_ShowUnrealtedNodeActionDescription;
	public static String DependencyVisualizationEditor_ShowUnrealtedNodeActionText;
	public static String DependencyVisualizationEditor_ShowUnrealtedNodeActionTooltip;
	public static String DependencyVisualizationEditor_ZoomActionDescription;
	public static String DependencyVisualizationEditor_ZoomActionText;
	public static String DependencyVisualizationEditor_ZoomActionTooltip;
	public static String DependencyVisualizationEditorInput_EditorInputName;
	public static String DependencyVisualizationEditorInput_EditorInputTooltip;
	public static String DependencyVisualizationUtils_ErrorReadingExtensionPointMessage;
	public static String DependencyVisualizationUtils_ProjectExplorerOpeningErrorMessage;
	public static String DependencyVisualizationUtils_ProjectNatureErrorMessage;
	public static String GraphVisualizationForm_EditorFormLabel;
	public static String VisualizeDependenciesCommandHandler_ErrorOpeningEditorMessage;
	public static String XPDDependencyViewerContributor_ErrorOpeningFileInEditorMessage;
	public static String XPDDependencyViewerContributor_ErrorReadingReferrencedProjectsMessage;
	public static String XPDDependencyViewerContributor_ErrorReadingWorkspaceMessage;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
