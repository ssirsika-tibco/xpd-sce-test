package com.tibco.xpd.dependency.visualization.internal.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.internal.GraphLabel;

import com.tibco.xpd.dependency.visualization.internal.views.GraphVisualizationForm.FeedbackLayer;

/**
 * Extended {@link Graph} to fix the bug due to which notification was not generated upon selection 
 * on empty canvas.
 * @author ssirsika
 * @since 03-Dec-2015
 */
public class DependencyVisualizationGraphControl extends Graph {

	class MousesSelectionSupport implements MouseListener {

		Graph graph = null;

		MousesSelectionSupport(Graph graph) {
			this.graph = graph;
		}

		@Override
		public void mouseDoubleClicked(org.eclipse.draw2d.MouseEvent me) {
			//do nothing
		}

		/**
		 * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
		 *
		 * @param me
		 */
		@Override
		public void mousePressed(org.eclipse.draw2d.MouseEvent me) {
			Point mousePoint = new Point(me.x, me.y);
			getRootLayer().translateToRelative(mousePoint);

			if ((me.getState() & SWT.MOD3) == 0) {
				boolean hasSelection = getSelection().size() > 0;
				IFigure figureUnderMouse = getFigureAt(mousePoint.x, mousePoint.y);
				getRootLayer().translateFromParent(mousePoint);

				if (figureUnderMouse != null) {
					figureUnderMouse.getParent().translateFromParent(mousePoint);
				}
				// If the figure under the mouse is the canvas, and CTRL/CMD is
				// not being held down, then select nothing
				if (figureUnderMouse == null || figureUnderMouse == DependencyVisualizationGraphControl.this) {
					if ((me.getState() & SWT.MOD1) == 0) {
						graph.setSelection(new GraphItem[0]);
						if (hasSelection) {
							fireWidgetSelectedEvent(null);
							hasSelection = false;
						}
					}
					return;
				}
			}
		}

		@Override
		public void mouseReleased(org.eclipse.draw2d.MouseEvent me) {
			// Set bounds of selection figures. This will make sure that selection figure will be shown back after resizing editor or dragging figure. 
			//TODO : If we can recalculate the need to show vertical and horizontal scroll bar then following code may not be needed. Whenever user drags item 
			// from canvas outside the bound of window and then bring it back, selection decoration disappears. To solve this issue, we are setting bounds of
			// selection figure again
			Point mousePoint = new Point(me.x, me.y);
			IFigure figureUnderMouse = getFigureAt(mousePoint.x, mousePoint.y);
			if (figureUnderMouse != null && figureUnderMouse instanceof GraphLabel) {
				List children = getRootLayer().getChildren();
				for (Object object : children) {
					if (object instanceof FeedbackLayer) {
						((FeedbackLayer) object).invalidate();
						((FeedbackLayer) object).repaint();
					}
				}
			}
		}

		/**
		 * TODO Currently not being used. Remove later if no issue related to flicker found.
		 * Find the {@link GraphItemFocusFigure} which is contains inside passed bounds. This way we can find out if currently selected graph item
		 * is same as focused item. This is implemented to reduce the flicker of focused figure happening when user selects any node different from
		 * currently focused node.   
		 * @param contents children of {@link FeedbackLayer}
		 * @param bounds bounds of selected item
		 * @return  return the {@link GraphItemFocusFigure} if passed bound contains GraphItemFocusFigure otherwise null.
		 */
		/*
		private GraphItemFocusFigure findGraphItemFocusFigureInBounds(List contents, Rectangle bounds) {
		for (Object object : contents) {
			if (object instanceof GraphItemFocusFigure) {
				if (bounds.contains(((GraphItemFocusFigure) object).getBounds())) {
					return (GraphItemFocusFigure) object;
				}
			}
		}
		return null;
		}*/
	}

	private List<SelectionListener> selectionListeners = new ArrayList<SelectionListener>();

	/**
	 * @param parent
	 * @param style
	 */
	public DependencyVisualizationGraphControl(Composite parent, int style) {
		super(parent, style);
		MousesSelectionSupport listener = new MousesSelectionSupport(this);
		this.getLightweightSystem().getRootFigure().addMouseListener(listener);
	}

	/**
	 * This adds a listener to the set of listeners that will be called when a
	 * selection event occurs.
	 * 
	 * @param selectionListener
	 */
	@Override
	public void addSelectionListener(SelectionListener selectionListener) {
		super.addSelectionListener(selectionListener);
		if (!selectionListeners.contains(selectionListener)) {
			selectionListeners.add(selectionListener);
		}
	}

	@Override
	public org.eclipse.swt.graphics.Point computeSize(int hint, int hint2, boolean changed) {
		return new org.eclipse.swt.graphics.Point(0, 0);
	}

	/**
	 * Notify selection listeners about the 'item' selection.
	 * @param item selected item
	 */
	private void fireWidgetSelectedEvent(Item item) {
		Event swtEvent = new Event();
		swtEvent.item = item;
		swtEvent.widget = this;
		notifySelectionListeners(new SelectionEvent(swtEvent));
	}

	/**
	 * @param event {@link SelectionEvent}
	 */
	private void notifySelectionListeners(SelectionEvent event) {
		for (SelectionListener selectionListener : selectionListeners) {
			selectionListener.widgetSelected(event);
		}
	}

	/**
	 * @see org.eclipse.zest.core.widgets.Graph#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		selectionListeners.clear();
	}
}