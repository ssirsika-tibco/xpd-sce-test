/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class DependencyVisualizationEditorImageProvider {

	//TODO : remove unwanted images after finalisation of user interface.
	private static ImageRegistry PLUGIN_REGISTRY;

	public final static String ICONS_PATH = "icons/"; //$NON-NLS-1$

	private static final String PATH_OBJ = ICONS_PATH + "obj16/"; //$NON-NLS-1$

	public static final String IMG_FORWARD_ENABLED = "forward_enabled.gif"; //$NON-NLS-1$
	public static final String IMG_BACKWARD_ENABLED = "backward_enabled.gif"; //$NON-NLS-1$
	public static final String IMG_HIGHLIGHT_DEPENDENCIES = "HighlightDepencies.png"; //$NON-NLS-1$
	public static final String IMG_HIGHLIGHT_DEPENDENTS = "HighlightDependents.png"; //$NON-NLS-1$
	public static final String IMG_FOCUS = "focus2.png"; //$NON-NLS-1$
	private static final String IMG_RESET_LAYOUT = "refresh.gif"; //$NON-NLS-1$
	private static final String IMG_SHOW_UNRELATED = "ShowUnrelated.png"; //$NON-NLS-1$
	public static final String IMG_ZOOM = "zoom.gif"; //$NON-NLS-1$
	public static final String IMG_SHOW_ALL_IN_WORKSPACE = "WholeWorkspaceDependenciesView.png"; //$NON-NLS-1$

	public static final ImageDescriptor DESC_FORWARD_ENABLED = create(PATH_OBJ, IMG_FORWARD_ENABLED);
	public static final ImageDescriptor DESC_BACKWARD_ENABLED = create(PATH_OBJ, IMG_BACKWARD_ENABLED);
	public static final ImageDescriptor DESC_HIGHLIGHT_DEPENDENCIES = create(PATH_OBJ, IMG_HIGHLIGHT_DEPENDENCIES);
	public static final ImageDescriptor DESC_HIGHLIGHT_DEPENDENCTS = create(PATH_OBJ, IMG_HIGHLIGHT_DEPENDENTS);
	public static final ImageDescriptor DESC_FOCUS = create(PATH_OBJ, IMG_FOCUS);
	public static final ImageDescriptor DESC_RESETL_LAYOUT = create(PATH_OBJ, IMG_RESET_LAYOUT);
	public static final ImageDescriptor DESC_SHOW_UNRELATED = create(PATH_OBJ, IMG_SHOW_UNRELATED);
	public static final ImageDescriptor DESC_ZOOM = create(PATH_OBJ, IMG_ZOOM);
	public static final ImageDescriptor DESC_SHOW_ALL_IN_WORKSPACE = create(PATH_OBJ, IMG_SHOW_ALL_IN_WORKSPACE);

	private static final void initialize() {
		PLUGIN_REGISTRY = DependencyVisualizationActivator.getDefault().getImageRegistry();
		manage(IMG_FORWARD_ENABLED, DESC_FORWARD_ENABLED);
		manage(IMG_BACKWARD_ENABLED, DESC_BACKWARD_ENABLED);
		manage(IMG_HIGHLIGHT_DEPENDENCIES, DESC_HIGHLIGHT_DEPENDENCIES);
		manage(IMG_HIGHLIGHT_DEPENDENTS, DESC_HIGHLIGHT_DEPENDENCTS);
		manage(IMG_FOCUS, DESC_FOCUS);
		manage(IMG_RESET_LAYOUT, DESC_RESETL_LAYOUT);
		manage(IMG_SHOW_UNRELATED, DESC_SHOW_UNRELATED);
		manage(IMG_ZOOM, DESC_ZOOM);
		manage(IMG_SHOW_ALL_IN_WORKSPACE, DESC_SHOW_ALL_IN_WORKSPACE);
	}

	private static ImageDescriptor create(String prefix, String name) {
		return ImageDescriptor.createFromURL(makeIconURL(prefix, name));
	}

	public static Image get(String key) {
		if (PLUGIN_REGISTRY == null)
			initialize();
		return PLUGIN_REGISTRY.get(key);
	}

	private static URL makeIconURL(String prefix, String name) {
		String path = "$nl$/" + prefix + name; //$NON-NLS-1$
		return FileLocator.find(DependencyVisualizationActivator.getDefault().getBundle(), new Path(path), null);
	}

	public static Image manage(String key, ImageDescriptor desc) {
		Image image = desc.createImage();
		PLUGIN_REGISTRY.put(key, image);
		return image;
	}

}
