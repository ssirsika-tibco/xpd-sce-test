/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal.views.provider;

import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.viewers.ISelfStyleProvider;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationEditorPreferencesManager;
import com.tibco.xpd.dependency.visualization.internal.Messages;

/**
 * Abstract Label provider which is used for decorating the graph relationships.
 *
 * @author ssirsika
 * @since 01-Dec-2015
 */
public abstract class AbstractVisualizationLabelProvider implements XPDGraphVisualizationLabelProvider, IConnectionStyleProvider, IEntityStyleProvider, ISelfStyleProvider {

	public Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	public Color GRAY = new Color(Display.getDefault(), 128, 128, 128);
	public Color LIGHT_GRAY = new Color(Display.getDefault(), 220, 220, 220);
	public Color BLACK = new Color(Display.getDefault(), 0, 0, 0);
	public Color RED = new Color(Display.getDefault(), 255, 0, 0);
	public Color DARK_RED = new Color(Display.getDefault(), 230, 0, 0);
	public Color DARK_ORANGE = new Color(Display.getDefault(), 255, 165, 0);
	public Color LIGHT_ORANGE = new Color(Display.getDefault(), 255, 215, 0);
	public Color LIGHT_GREEN = new Color(Display.getDefault(), 96, 255, 96);
	public Color BLUE = new Color(Display.getDefault(), 0, 0, 255);

	private Object selected = null;
	protected Object rootNode = null;
	private HashSet<EntityConnectionData> directRelationships = new HashSet<EntityConnectionData>();
	private HashSet directDependentNodes = new HashSet();
	private HashSet<EntityConnectionData> cyclicRelationships = new HashSet<EntityConnectionData>();
	private HashSet cyclicNodes = new HashSet();
	private HashSet<EntityConnectionData> indirectRelationships = new HashSet<EntityConnectionData>();
	private HashSet indirectDependenctNodes = new HashSet();
	private Color disabledColor = null;
	private GraphViewer viewer;

	private Color rootColor = null;
	private boolean referencingResourceDependencies = DependencyVisualizationEditorPreferencesManager.getHighlightReferencingResourcesPreferenceValue();
	private boolean referencedResourceDependencies = DependencyVisualizationEditorPreferencesManager.getHighlightReferencedResourcesPreferenceValue();

	/**
	 * Create a new Abstract Visualization Label Provider
	 * 
	 * @param viewer
	 * @param currentLabelProvider
	 *            The current label provider (or null if none is present). This
	 *            is used to maintain state between the old one and the new one.
	 */
	public AbstractVisualizationLabelProvider(GraphViewer viewer, AbstractVisualizationLabelProvider currentLabelProvider) {
		this.viewer = viewer;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		//do nothing
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// do nothing
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getColor(java.lang.Object)
	 *
	 * @param rel
	 * @return
	 */
	@Override
	public Color getColor(Object rel) {
		if (directRelationships.contains(rel)) {
			return DARK_BLUE;
		} else if (indirectRelationships.contains(rel)) {
			return BLUE;
		} else if (cyclicRelationships.contains(rel)) {
			return DARK_RED;
		}
		return LIGHT_GRAY;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getConnectionStyle(java.lang.Object)
	 *
	 * @param rel
	 * @return
	 */
	@Override
	public int getConnectionStyle(Object rel) {
		if (directRelationships.contains(rel) || indirectRelationships.contains(rel)) {
			return ZestStyles.CONNECTIONS_DASH | ZestStyles.CONNECTIONS_DIRECTED;
		}
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getHighlightColor(java.lang.Object)
	 *
	 * @param rel
	 * @return
	 */
	@Override
	public Color getHighlightColor(Object rel) {
		return DARK_BLUE;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getNodeHighlightColor(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Color getNodeHighlightColor(Object entity) {
		return null;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getLineWidth(java.lang.Object)
	 *
	 * @param rel
	 * @return
	 */
	@Override
	public int getLineWidth(Object rel) {
		if (directRelationships.contains(rel)) {
			return 1;
		}
		if (cyclicRelationships.contains(rel)) {
			return 2;
		}
		return 1;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderColor(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Color getBorderColor(Object entity) {
		if (cyclicNodes.contains(entity)) {
			return DARK_RED;
		}
		if (this.selected != null) {
			if (entity == this.selected) {
				// If this is the selected node return no colour. The default
				// selected colour is fine.
				return BLACK;
			} else if (directDependentNodes.contains(entity)) {
				// If this entity is directly connected to the selected entity
				return BLACK;
			} else if (indirectDependenctNodes.contains(entity)) {
				return BLACK;
			} else if (entity == this.rootNode) {
				return BLACK;
			} else {
				return LIGHT_GRAY;
			}

		}

		return BLACK;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderHighlightColor(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Color getBorderHighlightColor(Object entity) {
		if (entity == this.rootNode) {
			return BLACK;
		}
		return null;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBorderWidth(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public int getBorderWidth(Object entity) {
		if (cyclicNodes.contains(entity)) {
			return 2;
		} /*else if (entity == this.rootNode) {
			return 2;
			} else if (entity == this.selected || indirectDependenctNodes.contains(entity) || directDependentNodes.contains(entity)) {
			return 1;
			}*/
		return 0;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getBackgroundColour(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Color getBackgroundColour(Object entity) {

		if (entity == this.selected) {
			return viewer.getGraphControl().DEFAULT_NODE_COLOR;
		} else if (directDependentNodes.contains(entity)) {
			return DARK_ORANGE;
		} else if (indirectDependenctNodes.contains(entity)) {
			return LIGHT_ORANGE;
		} /*else if (entity == this.rootNode) {
			if (rootColor == null) {
				rootColor = LIGHT_GREEN;
			}
			return rootColor;
			}*/else {
			return getDisabledColor();
		}
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#getForegroundColour(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public Color getForegroundColour(Object entity) {
		if (this.selected != null) {
			if (entity == this.selected) {
				// If this is the selected node return no colour. The default
				// selected colour is fine.
				return BLACK;
			} else if (directDependentNodes.contains(entity)) {
				// If this entity is directly connected to the selected entity
				return BLACK;
			} else if (indirectDependenctNodes.contains(entity)) {
				return BLACK;
			} else {
				return GRAY;
			}
		}
		return BLACK;
	}

	protected Object getSelected() {
		return selected;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IEntityStyleProvider#fisheyeNode(java.lang.Object)
	 * Specify that fish eye style node is supported by returning true.
	 * @param entity
	 * @return
	 */
	@Override
	public boolean fisheyeNode(Object entity) {
		return true;
	}

	/**
	 * Sets the current selection
	 * 
	 * @param root
	 * @param currentSelection
	 */
	@Override
	public void setCurrentSelection(Object root, Object currentSelection) {

		for (Iterator iter = directRelationships.iterator(); iter.hasNext();) {
			EntityConnectionData entityConnectionData = (EntityConnectionData) iter.next();
			viewer.unReveal(entityConnectionData);
		}

		this.rootNode = root;
		this.selected = null;

		this.selected = currentSelection;

		directRelationships.clear();
		directDependentNodes.clear();
		cyclicRelationships.clear();
		cyclicNodes.clear();
		indirectRelationships.clear();
		indirectDependenctNodes.clear();
		calculateInterestingRelationships(directRelationships, directDependentNodes, indirectRelationships, indirectDependenctNodes, cyclicRelationships, cyclicNodes);
		for (Iterator iter = directRelationships.iterator(); iter.hasNext();) {
			Object entityConnectionData = iter.next();
			viewer.reveal(entityConnectionData);
		}
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		directRelationships.clear();
		directDependentNodes.clear();
		cyclicRelationships.clear();
		cyclicNodes.clear();
		indirectRelationships.clear();
		indirectDependenctNodes.clear();
	}

	/**
	 * Subclass should implement and populate the passed {@link HashSet} 
	 * @param directRelationships interesting relationships ({@link EntityConnectionData}) to be highlighted. These are direct relationships (dependent/depends) with selected node.
	 * @param directDependentNodes directly related nodes to be highlighted
	 * @param indirectRelationships indirect relationships to be highlighted of type {@link EntityConnectionData}
	 * @param indirectNodes indirect nodes with respect to selected node
	 * @param cyclicRelationships cyclic relationships of type {@link EntityConnectionData} 
	 * @param cyclicNodes nodes which are part of cycles in the graph
	 */
	protected abstract void calculateInterestingRelationships(HashSet<EntityConnectionData> directRelationships, HashSet directDependentNodes, HashSet<EntityConnectionData> indirectRelationships, HashSet indirectNodes, HashSet<EntityConnectionData> cyclicRelationships, HashSet cyclicNodes);

	/**
	 * Creates a colour for disabled lines.
	 * 
	 * @return
	 */
	private Color getDisabledColor() {
		if (disabledColor == null) {
			disabledColor = new Color(Display.getDefault(), new RGB(247, 251, 255));
		}
		return disabledColor;
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IConnectionStyleProvider#getTooltip(java.lang.Object)
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public IFigure getTooltip(Object entity) {
		if (cyclicRelationships.contains(entity)) {
			return new Label(Messages.AbstractVisualizationLabelProvider_CyclicDependConnectionLabel);
		}
		return null;
	}

	/**
	 * @return the rootNode
	 */
	@Override
	public Object getRootNode() {
		return rootNode;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider#setReferencingResourcesDependencies(boolean)
	 *
	 * @param reverseBundleDependencies
	 */
	@Override
	public void setReferencingResourcesDependencies(boolean referencingResourceDependencies) {
		this.referencingResourceDependencies = referencingResourceDependencies;
	}

	/**
	 * @return
	 */
	public boolean isReferencingResourceDependencies() {
		return referencingResourceDependencies;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider#setReferencedResourcesDependencies(boolean)
	 *
	 * @param option
	 */
	@Override
	public void setReferencedResourcesDependencies(boolean option) {
		this.referencedResourceDependencies = option;
	}

	/**
	 * @return the referencedResourceDependencies
	 */
	public boolean isReferencedResourceDependencies() {
		return referencedResourceDependencies;
	}

	/**
	 * Styles a connection
	 */
	@Override
	public void selfStyleConnection(Object element, GraphConnection connection) {
		if (cyclicRelationships.contains(element)) {
			connection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
		} else {
			Image image = connection.getImage();
			connection.setImage(null);
			if (image != null) {
				image.dispose();
			}
		}

	}

	/**
	 * Styles a node
	 */
	@Override
	public void selfStyleNode(Object element, GraphNode node) {
		// do nothing
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (this.disabledColor != null) {
			this.disabledColor.dispose();
			this.disabledColor = null;
		}
		if (this.rootColor != null) {
			this.rootColor.dispose();
			this.rootColor = null;
		}
		BLACK.dispose();
		BLUE.dispose();
		RED.dispose();
		GRAY.dispose();
		DARK_BLUE.dispose();
		DARK_ORANGE.dispose();
		DARK_RED.dispose();
		LIGHT_GRAY.dispose();
		LIGHT_GREEN.dispose();
		LIGHT_ORANGE.dispose();
	}

}