/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal.views;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.internal.ZestRootLayer;

import com.tibco.xpd.dependency.visualization.internal.Messages;
import com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider;

/**
 * This class encapsulates the process of creating the form view in {@link DependencyVisualizationEditor}
 */
@SuppressWarnings("restriction")
class GraphVisualizationForm {

	private static String Dependency_Analysis = Messages.GraphVisualizationForm_EditorFormLabel;
	private ScrolledForm form;
	private FormToolkit toolkit;
	private ManagedForm managedForm;
	private GraphViewer viewer;
	private SashForm sash;

	/**
	 * Creates the form.
	 * 
	 * @param toolKit
	 * @return
	 */
	GraphVisualizationForm(Composite parent, FormToolkit toolkit, DependencyVisualizationEditor view) {
		this.toolkit = toolkit;
		form = this.toolkit.createScrolledForm(parent);
		managedForm = new ManagedForm(this.toolkit, this.form);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 4;
		form.getBody().setLayout(layout);

		this.toolkit.decorateFormHeading(this.form.getForm());
		createSash(form.getBody());
	}

	/**
	 * Set the node name for focused graph node.
	 * @param nodeName
	 */
	public void setFocusedNodeName(String nodeName) {
		form.setText(Dependency_Analysis + ": " + nodeName); //$NON-NLS-1$
		form.reflow(true);
	}

	/**
	 * Creates the sashform to separate the graph from the controls.
	 * 
	 * @param parent
	 */
	private void createSash(Composite parent) {
		sash = new SashForm(parent, SWT.NONE);
		this.toolkit.paintBordersFor(parent);

		createGraphSection(sash);
		sash.setWeights(new int[] { 10 });
	}

	/**
	 *
	 * DependencyVisualization Graph Viewer.
	 * @author ssirsika
	 * @since 01-Dec-2015
	 */
	public class DependencyVisualizationGraphViewer extends GraphViewer {

		public DependencyVisualizationGraphViewer(Composite parent, int style) {
			super(parent, style);
			Graph graph = new DependencyVisualizationGraphControl(parent, style);
			setControl(graph);
		}

		/**
		 * @see org.eclipse.zest.core.viewers.GraphViewer#getZoomManager()
		 *
		 * @return
		 */
		@Override
		public ZoomManager getZoomManager() {
			return super.getZoomManager();
		}
	}

	/**
	 * Creates the section of the form where the graph is drawn
	 * 
	 * @param parent
	 */
	private void createGraphSection(Composite parent) {
		Section section = this.toolkit.createSection(parent, Section.TITLE_BAR);
		viewer = new DependencyVisualizationGraphViewer(section, SWT.NONE);
		addSelectionLayer(viewer.getGraphControl());

		//Following workaround to get the Anti-aliasing working.
		Listener[] listeners = viewer.getControl().getListeners(SWT.Paint);
		for (int i = 0; i < listeners.length; i++) {
			viewer.getControl().removeListener(SWT.Paint, listeners[i]);
		}
		viewer.getControl().addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.gc.setAntialias(SWT.ON);
				event.gc.setTextAntialias(SWT.ON);
			}
		});
		for (int i = 0; i < listeners.length; i++) {
			viewer.getControl().addListener(SWT.Paint, listeners[i]);
		}
		//end of workaround	
		section.setClient(viewer.getControl());
	}

	/**
	 * Add custom selection (feedback) layer to show different type of selections. 
	 * @param graphControl
	 */
	private void addSelectionLayer(Graph graphControl) {
		FreeformLayer feedbackLayer = new FeedbackLayer();
		ExtendedFreeformLayout freeformLayout = new ExtendedFreeformLayout();
		feedbackLayer.setLayoutManager(freeformLayout);
		GraphItemSelectionFigure graphItemSelectionFigure = new GraphItemSelectionFigure();
		graphItemSelectionFigure.setBounds(new Rectangle(0, 0, 1, 1));
		graphItemSelectionFigure.setBackgroundColor(ColorConstants.black);
		feedbackLayer.add(graphItemSelectionFigure);
		GraphItemFocusFigure graphItemFocusFigure = new GraphItemFocusFigure();
		graphItemFocusFigure.setBounds(new Rectangle(0, 0, 1, 1));
		graphItemFocusFigure.setBackgroundColor(ColorConstants.black);
		feedbackLayer.add(graphItemFocusFigure);
		graphControl.getRootLayer().add(feedbackLayer, "XPDSelection", 1); //$NON-NLS-1$
		List children = graphControl.getRootLayer().getChildren();
		for (Object child : children) {
			if (child instanceof ZestRootLayer) {
			    // add listener to ZestRootLayer to receive notification related to any layout change.
				((ZestRootLayer) child).addLayoutListener(new ZestRootLayerLayoutListener(feedbackLayer));
			}
		}
	}

	/**
	 * Gets the currentGraphViewern
	 * 
	 * @return
	 */
	public GraphViewer getGraphViewer() {
		return viewer;
	}

	/**
	 * Gets the form we created.
	 */
	public ScrolledForm getForm() {
		return form;
	}

	public ManagedForm getManagedForm() {
		return managedForm;
	}

	class FeedbackLayer extends FreeformLayer {
		FeedbackLayer() {
			setEnabled(true);
		}
	}

	/**
	 *
	 * Dotted rectangle figure to depict current selection 
	 * @author ssirsika
	 * @since 07-Dec-2015
	 */
	public class GraphItemSelectionFigure extends Figure {

		/**
		 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void paintFigure(Graphics graphics) {
			Graph graphControl = getGraphViewer().getGraphControl();
			if (graphControl == null || !graphControl.isVisible()) {
				return;
			}
			Rectangle bounds = getBounds().getCopy();
			graphics.setXORMode(true);
			graphics.setForegroundColor(ColorConstants.black);
			graphics.setBackgroundColor(ColorConstants.black);
			graphics.setLineStyle(Graphics.LINE_DASH);
			graphics.setLineDash(new int[] { 5, 3 });
			graphics.setLineWidth(1);
			graphics.setAntialias(SWT.ON);

			graphics.drawRoundRectangle(new Rectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1), 8, 8);
		}

		/**
		 * @see org.eclipse.draw2d.Figure#findFigureAt(int, int, org.eclipse.draw2d.TreeSearch)
		 *
		 * @param x
		 * @param y
		 * @param search
		 * @return
		 */
		@Override
		public IFigure findFigureAt(int x, int y, TreeSearch search) {
			return null;
		}

		/**
		 * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
		 *
		 * @param wHint
		 * @param hHint
		 * @return
		 */
		@Override
		public Dimension getPreferredSize(int wHint, int hHint) {
			return new Dimension(50, 30);
		}
		
	}

	/**
	 * Focused Rectangle figure to depict focused node (or graph item)
	 *
	 * @author ssirsika
	 * @since 07-Dec-2015
	 */
	public class GraphItemFocusFigure extends Figure {

		/**
		 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
		 */
		@Override
		protected void paintFigure(Graphics graphics) {
			Graph graphControl = getGraphViewer().getGraphControl();
			if (graphControl == null || !graphControl.isVisible()) {
				return;
			}
			Rectangle bounds = getBounds().getCopy();
			graphics.pushState();

			graphics.setXORMode(true);
			graphics.setForegroundColor(FigureUtilities.darker(ColorConstants.black));
			graphics.setBackgroundColor(ColorConstants.black);
			graphics.setLineStyle(Graphics.LINE_SOLID);
			graphics.setAntialias(SWT.ON);
			graphics.setLineWidth(2);

			graphics.drawRoundRectangle(new Rectangle(bounds.x + 4, bounds.y + 4, bounds.width - 8, bounds.height - 8), 8, 8);
			graphics.drawLine(bounds.x + ((bounds.width) / 2), bounds.y, bounds.x + ((bounds.width) / 2), bounds.y + 4);
			graphics.drawLine(bounds.x + ((bounds.width) / 2), bounds.y + bounds.height - 4, bounds.x + ((bounds.width) / 2), bounds.y + bounds.height);
			graphics.drawLine(bounds.x, bounds.y + (bounds.height / 2), bounds.x + 4, bounds.y + +(bounds.height / 2));
			graphics.drawLine(bounds.x + bounds.width - 4, bounds.y + (bounds.height / 2), bounds.x + bounds.width, bounds.y + +(bounds.height / 2));
			graphics.popState();

			graphics.pushState();
			graphics.setForegroundColor(ColorConstants.white);
			graphics.setLineStyle(Graphics.LINE_SOLID);
			graphics.setAntialias(SWT.ON);
			graphics.setLineWidth(2);

			graphics.drawLine(bounds.x + ((bounds.width) / 2) - 5, bounds.y + 4, bounds.x + ((bounds.width) / 2) - 1, bounds.y + 4);
			graphics.drawLine(bounds.x + ((bounds.width) / 2) + 1, bounds.y + 4, bounds.x + ((bounds.width) / 2) + 5, bounds.y + 4);

			graphics.drawLine(bounds.x + ((bounds.width) / 2) - 5, bounds.y + bounds.height - 4, bounds.x + ((bounds.width) / 2) - 1, bounds.y + bounds.height - 4);
			graphics.drawLine(bounds.x + ((bounds.width) / 2) + 1, bounds.y + bounds.height - 4, bounds.x + ((bounds.width) / 2) + 5, bounds.y + bounds.height - 4);

			graphics.drawLine(bounds.x + 4, bounds.y + ((bounds.height) / 2) - 3, bounds.x + 4, bounds.y + ((bounds.height) / 2) - 1);
			graphics.drawLine(bounds.x + 4, bounds.y + ((bounds.height) / 2) + 1, bounds.x + 4, bounds.y + ((bounds.height) / 2) + 3);

			graphics.drawLine(bounds.x + bounds.width - 4, bounds.y + ((bounds.height) / 2) - 3, bounds.x + bounds.width - 4, bounds.y + ((bounds.height) / 2) - 1);
			graphics.drawLine(bounds.x + bounds.width - 4, bounds.y + ((bounds.height) / 2) + 1, bounds.x + bounds.width - 4, bounds.y + ((bounds.height) / 2) + 3);

			graphics.popState();
		}

		/**
		* @see org.eclipse.draw2d.Figure#findFigureAt(int, int, org.eclipse.draw2d.TreeSearch)
		*
		* @param x
		* @param y
		* @param search
		* @return
		*/
		@Override
		public IFigure findFigureAt(int x, int y, TreeSearch search) {
			return null;
		}
	}

	/**
	 * This layout sets the location of {@link GraphItemSelectionFigure}
	 * and {@link GraphItemFocusFigure} according to currently selected 
	 * graph item and focused item respectively.
	 *
	 * @author ssirsika
	 * @since 11-Jan-2016
	 */
	private class ExtendedFreeformLayout extends FreeformLayout {
		@Override
		public void layout(IFigure parent) {
			Graph graphControl = getGraphViewer().getGraphControl();
			if (graphControl == null || !graphControl.isVisible()) {
				return;
			}
			Iterator children = parent.getChildren().iterator();
			IFigure f;
			while (children.hasNext()) {
				f = (IFigure) children.next();
				if (f instanceof GraphItemSelectionFigure) {
					List selection = graphControl.getSelection();
					if (!selection.isEmpty()) {
						boolean isRootNodeSelected = false;
						Object selectedObj = selection.get(0);
						if (selectedObj instanceof GraphNode) {
							GraphNode gn = (GraphNode) selectedObj;
							IContentProvider contentProvider = getGraphViewer().getContentProvider();
							if (contentProvider instanceof XPDGraphVisualizationLabelProvider) {
								Object rootNode = ((XPDGraphVisualizationLabelProvider) contentProvider).getRootNode();
								GraphItem graphItem = getGraphViewer().findGraphItem(rootNode);
								if (graphItem == gn) {
									isRootNodeSelected = true;
								}
							}
							if (isRootNodeSelected) {
								f.setVisible(true);
								f.setLocation(gn.getLocation().getTranslated(-8, -8));
								f.setSize(gn.getSize().expand(16, 16));
							} else {
								f.setVisible(true);
								f.setLocation(gn.getLocation().getTranslated(-4, -4));
								f.setSize(gn.getSize().expand(8, 8));
							}
						}
					} else {
						f.setVisible(false);
					}
				} else if (f instanceof GraphItemFocusFigure) {
					IContentProvider contentProvider = getGraphViewer().getContentProvider();
					if (contentProvider instanceof XPDGraphVisualizationLabelProvider) {
						Object rootNode = ((XPDGraphVisualizationLabelProvider) contentProvider).getRootNode();
						GraphItem graphItem = getGraphViewer().findGraphItem(rootNode);
						if (graphItem instanceof GraphNode) {
							GraphNode graphNode = (GraphNode) graphItem;
							f.setLocation(graphNode.getLocation().getTranslated(-4, -4));
							f.setSize(graphNode.getSize().expand(8, 8));
						}
					}
				}
			}
		}

	}

	/**
	 * This layout listener to listen to any changes happening in {@link ZestRootLayer}
	 *
	 * @author ssirsika
	 * @since 11-Jan-2016
	 */
	private class ZestRootLayerLayoutListener implements LayoutListener {

		private final FreeformLayer feedbackLayer;

		/**
		 * @param feedbackLayer 
		 * 
		 */
		public ZestRootLayerLayoutListener(FreeformLayer feedbackLayer) {
			this.feedbackLayer = feedbackLayer;

		}

		/**
		 * @see org.eclipse.draw2d.LayoutListener#invalidate(org.eclipse.draw2d.IFigure)
		 *
		 * @param container
		 */
		@Override
		public void invalidate(IFigure container) {
			// invalidate feedback layer to update the selection decorations
		    feedbackLayer.invalidate();
		}

		/**
		 * @see org.eclipse.draw2d.LayoutListener#layout(org.eclipse.draw2d.IFigure)
		 *
		 * @param container
		 * @return
		 */
		@Override
		public boolean layout(IFigure container) {
			return false;
		}

		/**
		 * @see org.eclipse.draw2d.LayoutListener#postLayout(org.eclipse.draw2d.IFigure)
		 *
		 * @param container
		 */
		@Override
		public void postLayout(IFigure container) {
			// do nothing
		}

		/**
		 * @see org.eclipse.draw2d.LayoutListener#remove(org.eclipse.draw2d.IFigure)
		 *
		 * @param child
		 */
		@Override
		public void remove(IFigure child) {
			// do nothing
		}

		/**
		 * @see org.eclipse.draw2d.LayoutListener#setConstraint(org.eclipse.draw2d.IFigure, java.lang.Object)
		 *
		 * @param child
		 * @param constraint
		 */
		@Override
		public void setConstraint(IFigure child, Object constraint) {
			// do nothing
		}

	}
}
