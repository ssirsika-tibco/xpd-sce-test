/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This manages the preferences related to Dependency Visualisation Editor.
 *
 * @author ssirsika
 * @since 03-Dec-2015
 */
public class DependencyVisualizationEditorPreferencesManager extends AbstractPreferenceInitializer {

	private final static IPreferenceStore prefStore = DependencyVisualizationActivator.getDefault().getPreferenceStore();
	public static final String HIGHLIGHT_REFERENCED_RESOURCES = "highlight.referenced.resources"; //$NON-NLS-1$
	public static final String HIGHLIGHT_REFERENCING_RESOURCES = "highlight.referencing.resources"; //$NON-NLS-1$
	public static final String SHOW_UNRELATED_NODES = "show.unrealted.nodes"; //$NON-NLS-1$
	public static final String SHOW_ALL_IN_WORKSPACE = "show.all.in.workspace"; //$NON-NLS-1$

	/**
	 * Returns the current value of the boolean-valued preference HIGHLIGHT_REFERENCED_RESOURCES.
	 *
	 * @return the boolean-valued preference
	 */

	public static boolean getHighlightReferencedResourcesPreferenceValue() {
		return prefStore.getBoolean(HIGHLIGHT_REFERENCED_RESOURCES);
	}

	/**
	 * Returns the current value of the boolean-valued preference HIGHLIGHT_REFERENCING_RESOURCES.
	 *
	 * @return the boolean-valued preference
	 */
	public static boolean getHighlightReferencingResourcesPreferenceValue() {
		return prefStore.getBoolean(HIGHLIGHT_REFERENCING_RESOURCES);
	}

	/**
	 * Returns the current value of the boolean-valued preference SHOW_UNRELATED_NODES.
	 *
	 * @return the boolean-valued preference
	 */
	public static boolean getShowUnrelatedNodesPreferenceValue() {
		return prefStore.getBoolean(SHOW_UNRELATED_NODES);
	}

	/**
	 * Returns the current value of the boolean-valued preference SHOW_ALL_IN_WORKSPACE.
	 *
	 * @return the boolean-valued preference
	 */

	public static boolean getShowAllInWorkspacePreferenceValue() {
		return prefStore.getBoolean(SHOW_ALL_IN_WORKSPACE);
	}

	/**
	 * Store preference value for HIGHLIGHT_REFERENCED_RESOURCES
	 * @param value boolean
	 */
	public static void storeHighlightReferencedResourcesPreferenceValue(boolean value) {
		prefStore.setValue(HIGHLIGHT_REFERENCED_RESOURCES, value);
	}

	/**
	 * Store preference value for HIGHLIGHT_REFERENCING_RESOURCES
	 * @param value boolean
	 */
	public static void storeHighlightReferencingResourcesPreferenceValue(boolean value) {
		prefStore.setValue(HIGHLIGHT_REFERENCING_RESOURCES, value);
	}

	/**
	 * Store preference value for SHOW_UNRELATED_NODES
	 * @param value boolean
	 */
	public static void storeShowUnrelatedNodesPreferenceValue(boolean value) {
		prefStore.setValue(SHOW_UNRELATED_NODES, value);
	}

	/**
	 * Store preference value for SHOW_ALL_IN_WORKSPACE
	 * @param value boolean
	 */
	public static void storeShowAllInWorkspacePreferenceValue(boolean value) {
		prefStore.setValue(SHOW_ALL_IN_WORKSPACE, value);
	}

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 *
	 */
	@Override
	public void initializeDefaultPreferences() {
		prefStore.setDefault(DependencyVisualizationEditorPreferencesManager.HIGHLIGHT_REFERENCED_RESOURCES, true);
		prefStore.setDefault(DependencyVisualizationEditorPreferencesManager.HIGHLIGHT_REFERENCING_RESOURCES, true);
		prefStore.setDefault(DependencyVisualizationEditorPreferencesManager.SHOW_UNRELATED_NODES, false);
		prefStore.setDefault(DependencyVisualizationEditorPreferencesManager.SHOW_ALL_IN_WORKSPACE, false);
	}

}
