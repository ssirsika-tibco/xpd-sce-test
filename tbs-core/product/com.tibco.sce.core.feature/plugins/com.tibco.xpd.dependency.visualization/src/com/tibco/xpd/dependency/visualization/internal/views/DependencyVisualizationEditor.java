/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.dependency.visualization.internal.views;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Stack;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;

import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationActivator;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationEditorImageProvider;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationEditorPreferencesManager;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationUtils;
import com.tibco.xpd.dependency.visualization.internal.Messages;
import com.tibco.xpd.dependency.visualization.internal.analysis.GraphCyclesAnalyser;
import com.tibco.xpd.dependency.visualization.internal.layout.ExtendedHorizontalShift;
import com.tibco.xpd.dependency.visualization.internal.views.GraphVisualizationForm.DependencyVisualizationGraphViewer;
import com.tibco.xpd.dependency.visualization.internal.views.provider.BaseDependencyGraphViewerProvider;
import com.tibco.xpd.dependency.visualization.internal.views.provider.XPDGraphVisualizationLabelProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.popup.QuickSearchPopupAction;

/**
 * Editor to show relationships between the resources. 
 *
 * @author ssirsika
 * @since 26-Nov-2015
 */
@SuppressWarnings("restriction")
public class DependencyVisualizationEditor extends EditorPart implements IZoomableWorkbenchPart {

	/**
	 *
	 * This action handles 'Show All In Workspace' button functionality.
	 * @author ssirsika
	 * @since 17-Dec-2015
	 */
	private final class ShowAllInWorkspaceAction extends Action {
		/**
		 * 
		 */
		public ShowAllInWorkspaceAction() {
			super("", AS_CHECK_BOX); //$NON-NLS-1$
		}

		/**
		 * @see org.eclipse.jface.action.Action#run()
		 *
		 */
		@Override
		public void run() {
			handleShowAllInWorkpsaceButton(isChecked());
		}
	}

	/**
	 * Custom zoom action which handles zooming functionality for the editor.
	 * @author ssirsika
	 * @since 01-Dec-2015
	 */
	class DependencyVisualizationEditorZoomAction extends Action implements IMenuCreator {
		private Menu menu;
		private ZoomManager zoomManager;

		public DependencyVisualizationEditorZoomAction() {
			super("", AS_DROP_DOWN_MENU); //$NON-NLS-1$
			setText(Messages.DependencyVisualizationEditor_ZoomActionText);
			setDescription(Messages.DependencyVisualizationEditor_ZoomActionDescription);
			setToolTipText(Messages.DependencyVisualizationEditor_ZoomActionTooltip);
			setMenuCreator(this);
			zoomManager = ((DependencyVisualizationGraphViewer) viewer).getZoomManager();
			setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_ZOOM);
		}

		/**
		 * @see org.eclipse.jface.action.IMenuCreator#dispose()
		 *
		 */
		@Override
		public void dispose() {
			if (menu != null && !menu.isDisposed()) {
				menu.dispose();
				menu = null;
			}
		}

		/**
		 * Zoom to passed zoom depth
		 * @param zoom zoom depth
		 */
		private void doZoom(String zoom) {
			if (zoomManager != null) {
				zoomManager.setZoomAsText(zoom);
			}
		}

		/**
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
		 *
		 * @param parent
		 * @return
		 */
		@Override
		public Menu getMenu(Control parent) {
			menu = new Menu(parent);
			populateMenu();
			return menu;
		}

		/**
		 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
		 *
		 * @param parent
		 * @return
		 */
		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		private void populateMenu() {
			menu.setEnabled(false);
			if (zoomManager == null) {
				return;
			}
			String[] zoomLevels = zoomManager.getZoomLevelsAsText();
			MenuItem[] oldItems = menu.getItems();
			for (int i = 0; i < oldItems.length; i++) {
				if (oldItems[i].getData() == this) {
					oldItems[i].dispose();
				}
			}
			for (int i = 0; i < zoomLevels.length; i++) {
				MenuItem item = new MenuItem(menu, SWT.RADIO);
				item.setText(zoomLevels[i]);
				item.setData(this);
				item.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						MenuItem source = (MenuItem) e.getSource();
						doZoom(source.getText());
					}
				});
			}
			String zoom = zoomManager.getZoomAsText();
			MenuItem[] items = menu.getItems();
			for (int i = 0; i < items.length; i++) {
				MenuItem item = items[i];
				if (item.getData() == this) {
					item.setSelection(false);
					if (zoom.equalsIgnoreCase(item.getText())) {
						item.setSelection(true);
					}
				}
			}
			menu.setEnabled(true);
		}

		/**
		 * @see org.eclipse.jface.action.Action#runWithEvent(org.eclipse.swt.widgets.Event)
		 *
		 * @param event
		 */
		@Override
		public void runWithEvent(Event event) {
			if (event.widget instanceof ToolItem) {
				ToolItem toolItem = (ToolItem) event.widget;
				Control control = toolItem.getParent();
				Menu menu = getMenu(control);

				org.eclipse.swt.graphics.Rectangle bounds = toolItem.getBounds();
				Point topLeft = new Point(bounds.x, bounds.y + bounds.height);
				menu.setLocation(control.toDisplay(topLeft));
				menu.setVisible(true);
			}
		}
	}

	class ShowReferencedResourceAction extends Action {

		public ShowReferencedResourceAction() {
			super("", AS_CHECK_BOX); //$NON-NLS-1$
			setText(Messages.DependencyVisualizationEditor_HighlightReferencedResourcesText);
			setDescription(Messages.DependencyVisualizationEditor_HighlightReferencedResourcesDescription);
			setToolTipText(Messages.DependencyVisualizationEditor_HighlightReferencedResourcesToolTip);
			setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_HIGHLIGHT_DEPENDENCIES);
		}

		/*
		 * @see Action#actionPerformed
		 */
		@Override
		public void run() {
			handleShowReferencedResources(isChecked());
		}
	}

	class ShowReferencingResourceAction extends Action {
		public ShowReferencingResourceAction() {
			super("", AS_CHECK_BOX); //$NON-NLS-1$
			setText(Messages.DependencyVisualizationEditor_HighlightReferencingResourcesText);
			setDescription(Messages.DependencyVisualizationEditor_HighlightReferencingResourcesDescription);
			setToolTipText(Messages.DependencyVisualizationEditor_HighlightReferencingResourcesToolTip);
			setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_HIGHLIGHT_DEPENDENCTS);
		}

		/*
		 * @see Action#actionPerformed
		 */
		@Override
		public void run() {
			handleShowReferencingResources(isChecked());
		}
	}

	class ShowUnrelatedNodesAction extends Action {
		public ShowUnrelatedNodesAction() {
			super("", AS_CHECK_BOX); //$NON-NLS-1$
			setText(Messages.DependencyVisualizationEditor_ShowUnrealtedNodeActionText);
			setDescription(Messages.DependencyVisualizationEditor_ShowUnrealtedNodeActionDescription);
			setToolTipText(Messages.DependencyVisualizationEditor_ShowUnrealtedNodeActionTooltip);
			setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_SHOW_UNRELATED);
		}

		/*
		 * @see Action#actionPerformed
		 */
		@Override
		public void run() {
			handleShowUnrelatedButton(isChecked());
		}
	}

	private FormToolkit toolKit = null;
	private ScrolledForm form = null;
	private GraphViewer viewer;
	private Action showReferencedResourceAction;
	private Action showReferencingResourceAction;
	private Action showUnrelatedNodesAction;
	private Action focusAction;
	private Action dependencyViewZoomAction;
	private Action historyAction;
	private Action forwardAction;
	private Stack<Object> historyStack;
	private Stack<Object> forwardStack;
	private QuickSearchPopupAction quickSearchPopupAction;
	private Object currentNode = null;
	private XPDGraphVisualizationLabelProvider currentLabelProvider;
	private XPDGraphVisualizationLabelProvider contentProvider;

	private GraphVisualizationForm visualizationForm;

	private Action resetLayoutAction;

	private Action showAllInWorkspaceAction;

	public final static String EDITOR_ID = "com.tibco.xpd.dependency.visualization.editorID"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	public DependencyVisualizationEditor() {
		historyStack = new Stack<Object>();
		forwardStack = new Stack<Object>();
	}

	/**
	 * This is a callback that will allow us to create the viewer
	 */
	@Override
	public void createPartControl(Composite parent) {

		toolKit = new FormToolkit(parent.getDisplay());
		visualizationForm = new GraphVisualizationForm(parent, toolKit, this);
		viewer = visualizationForm.getGraphViewer();
		form = visualizationForm.getForm();
		contentProvider = new BaseDependencyGraphViewerProvider(viewer, null);
		currentLabelProvider = contentProvider;
		viewer.setContentProvider((IGraphEntityContentProvider) contentProvider);
		viewer.setLabelProvider(currentLabelProvider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
//		viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		GraphCyclesAnalyser.resetCyclicNodesData();
		GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
		viewer.setLayoutAlgorithm(new CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] { new DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), new ExtendedHorizontalShift(LayoutStyles.NONE) }));

		FontData fontData = Display.getCurrent().getSystemFont().getFontData()[0];
		fontData.height = 42;

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Object selectedElement = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (selectedElement instanceof EntityConnectionData) {
					return;
				}
				DependencyVisualizationEditor.this.selectionChanged(selectedElement);
			}
		});

		// double click listener
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() < 1) {
					return;
				}
				Object selectedElement = selection.getFirstElement();
				contentProvider.handleDoubleClick(selectedElement);
			}

		});

		makeActions();
		hookContextMenu();
		fillToolBar();
		selectionChanged(null);
		viewer.refresh();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param monitor
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing as this is read only editor
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 *
	 */
	@Override
	public void doSaveAs() {
		// do nothing as this is read only editor
	}

	/**
	 * Add the items to the context menu
	 * 
	 * @param manager
	 */
	private void fillContextMenu(IMenuManager manager) {
		ISelection selection = viewer.getSelection();
		manager.add(new Separator());
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object firstElement = structuredSelection.getFirstElement();
			if (!(firstElement instanceof EntityConnectionData)) {
				if (structuredSelection.size() > 0) {
					makeFocusAction(((IStructuredSelection) viewer.getSelection()).getFirstElement());
					manager.add(focusAction);
				}
				manager.add(quickSearchPopupAction);
				manager.add(new Separator());
				manager.add(historyAction);
				manager.add(forwardAction);
				manager.add(new Separator());
				manager.add(resetLayoutAction);
			}
		}
	}

	/**
	 * Add the actions to the tool bar
	 * 
	 * @param toolBarManager
	 * @param bars 
	 */
	private void fillLocalToolBar(IToolBarManager toolBarManager, IActionBars bars) {
		toolBarManager.add(quickSearchPopupAction);
		bars.setGlobalActionHandler(ActionFactory.FIND.getId(), quickSearchPopupAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(showAllInWorkspaceAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(resetLayoutAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(showReferencedResourceAction);
		toolBarManager.add(showReferencingResourceAction);
		toolBarManager.add(showUnrelatedNodesAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(historyAction);
		toolBarManager.add(forwardAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(dependencyViewZoomAction);
	}

	/**
	 * Set the toolbar
	 */
	private void fillToolBar() {
		IActionBars bars = getEditorSite().getActionBars();
		if (bars != null) {
			fillLocalToolBar(form.getToolBarManager(), bars);
			IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
			menuService.populateContributionManager((ContributionManager) form.getToolBarManager(), "popup:formsToolBar"); //$NON-NLS-1$
			form.getToolBarManager().update(true);
		}
	}

	/**
	 * Focus on passed 'resource'. 
	 * @param resource
	 * @param recordHistory {@link Boolean} specifies whether to record the history or not.
	 */
	private void focusOn(Object resource, boolean recordHistory) {
		viewer.setFilters(new ViewerFilter[] {});
		viewer.setInput(resource);
		visualizationForm.setFocusedNodeName(getName(resource));
		setPartName(getName(resource) + Messages.DependencyVisualizationEditor_DepecdencyVisualizationEditorNameLastPart);
		Iterator nodes = viewer.getGraphControl().getNodes().iterator();
		Graph graph = viewer.getGraphControl();
		Dimension centre = new Dimension(graph.getBounds().width / 2, graph.getBounds().height / 2);
		while (nodes.hasNext()) {
			GraphNode node = (GraphNode) nodes.next();
			if (node.getLocation().x <= 1 && node.getLocation().y <= 1) {
				node.setLocation(centre.width, centre.height);
			}
		}
		if (currentNode != null && recordHistory && currentNode != resource) {
			historyStack.push(currentNode);
			historyAction.setEnabled(true);
		}
		currentNode = resource;
		GraphCyclesAnalyser.resetCyclicNodesData();
		GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
		viewer.setSelection(new StructuredSelection(resource));
		selectionChanged(resource);
		((DependencyVisualizationEditorInput) getEditorInput()).setSelection((IStructuredSelection) viewer.getSelection());
	}

	/**
	 * @return {@link GraphViewer} object
	 */
	public GraphViewer getGraphViewer() {
		return viewer;
	}

	/**
	 * Return name of passed Object 'o' using label provider
	 * @param o 
	 * @return {@link String} name
	 */
	private String getName(Object o) {
		return currentLabelProvider.getText(o);
	}

	/**
	 * @see org.eclipse.zest.core.viewers.IZoomableWorkbenchPart#getZoomableViewer()
	 *
	 * @return
	 */
	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	/**
	 *  Handle Show All in Workspace button.
	 * @param enable 
	 */
	public void handleShowAllInWorkpsaceButton(final boolean enable) {
		StructuredSelection selection = ((StructuredSelection) viewer.getSelection());
		if (selection != null) {
			IProgressService progressService = PlatformUI.getWorkbench().getProgressService();
			try {
				progressService.runInUI(PlatformUI.getWorkbench().getProgressService(), new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask(Messages.DependencyVisualizationEditor_ShowAllInWorkspaceProgressMessage, 10);
						contentProvider.setShowAllInWorkspace(enable);
						monitor.worked(2);
						viewer.refresh();
						GraphCyclesAnalyser.resetCyclicNodesData();
						GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
						monitor.worked(2);
						StructuredSelection internalSelection = ((StructuredSelection) viewer.getSelection());
						selectionChanged(internalSelection.getFirstElement());
						viewer.applyLayout();
						monitor.worked(2);
						DependencyVisualizationEditorPreferencesManager.storeShowAllInWorkspacePreferenceValue(enable);
						Viewport viewport = viewer.getGraphControl().getViewport();
						monitor.worked(2);
						viewer.reveal(internalSelection.getFirstElement());
						monitor.done();
					}
				}, ResourcesPlugin.getWorkspace().getRoot());
			} catch (InvocationTargetException | InterruptedException e) {
				DependencyVisualizationActivator.logError(Messages.DependencyVisualizationEditor_ShowAllInWorkspaceErrorMessage, e);
			}
		}

	}

	/**
	 * Handles show referenced resources action
	 * @param enable
	 */
	void handleShowReferencedResources(boolean enable) {
		StructuredSelection selection = ((StructuredSelection) viewer.getSelection());
		if (selection != null) {
			currentLabelProvider.setReferencedResourcesDependencies(enable);
			viewer.setSelection(selection);
			selectionChanged(selection.getFirstElement());
			DependencyVisualizationEditorPreferencesManager.storeHighlightReferencedResourcesPreferenceValue(enable);
		}
	}

	/**
	 * Handles show referencing resources action
	 * @param enable
	 */
	void handleShowReferencingResources(boolean enable) {
		StructuredSelection selection = ((StructuredSelection) viewer.getSelection());
		if (selection != null) {
			currentLabelProvider.setReferencingResourcesDependencies(enable);
			viewer.setSelection(selection);
			selectionChanged(selection.getFirstElement());
			DependencyVisualizationEditorPreferencesManager.storeHighlightReferencingResourcesPreferenceValue(enable);
		}
	}

	/**
	 * @param checked button checked status
	 */
	public void handleShowUnrelatedButton(boolean checked) {
		StructuredSelection selection = ((StructuredSelection) viewer.getSelection());
		if (selection != null) {
			contentProvider.setFilterUnrelatedItems(!checked);
			viewer.refresh();
			GraphCyclesAnalyser.resetCyclicNodesData();
			GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
			viewer.setSelection(selection);
			selectionChanged(selection.getFirstElement());
			viewer.applyLayout();
			DependencyVisualizationEditorPreferencesManager.storeShowUnrelatedNodesPreferenceValue(checked);
			Viewport viewport = viewer.getGraphControl().getViewport();
			// move to right most bottom. This workaround is for fixing selection decoration disappearance issue.
			viewport.setViewLocation(viewport.getBounds().getBottomRight());
			viewer.reveal(selection.getFirstElement());
		}
	}

	/**
	 * Creates the context menu.
	 */
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		fillContextMenu(menuMgr);

		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 *
	 * @param site
	 * @param input
	 * @throws PartInitException
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 *
	 * @return always return false as this is read only editor.
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 *
	 * @return always return false as this is read only editor.
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Create action instances
	 */
	private void makeActions() {
		quickSearchPopupAction = new QuickSearchPopupAction(Messages.DependencyVisualizationEditor_FocusOnResourceActionName);
		quickSearchPopupAction.setText(Messages.DependencyVisualizationEditor_FocusOnActionText);
		quickSearchPopupAction.setToolTipText(Messages.DependencyVisualizationEditor_FocusOnActionTooltip);
		quickSearchPopupAction.setEnabled(true);

		historyAction = new Action() {
			@Override
			public void run() {
				if (historyStack.size() > 0) {
					Object o = historyStack.pop();
					forwardStack.push(currentNode);
					forwardAction.setEnabled(true);
					focusOn(o, false);
					DependencyVisualizationUtils.showInProjectExplorerView(o, 0);
					if (historyStack.size() <= 0) {
						historyAction.setEnabled(false);
					}
				}
			}
		};
		historyAction.setText(Messages.DependencyVisualizationEditor_BackActionText);
		historyAction.setToolTipText(Messages.DependencyVisualizationEditor_BackActionToolTip);
		historyAction.setEnabled(false);
		historyAction.setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_BACKWARD_ENABLED);
		historyAction.setId("history"); //$NON-NLS-1$

		forwardAction = new Action() {
			@Override
			public void run() {
				if (forwardStack.size() > 0) {
					Object o = forwardStack.pop();
					focusOn(o, true);
					DependencyVisualizationUtils.showInProjectExplorerView(o, 0);
					if (forwardStack.size() <= 0) {
						forwardAction.setEnabled(false);
					}
				}
			}
		};

		forwardAction.setText(Messages.DependencyVisualizationEditor_ForwardActionText);
		forwardAction.setToolTipText(Messages.DependencyVisualizationEditor_ForwardActionToolTip);
		forwardAction.setEnabled(false);
		forwardAction.setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_FORWARD_ENABLED);

		showReferencedResourceAction = new ShowReferencedResourceAction();
		showReferencedResourceAction.setChecked(DependencyVisualizationEditorPreferencesManager.getHighlightReferencedResourcesPreferenceValue());
		showReferencingResourceAction = new ShowReferencingResourceAction();
		showReferencingResourceAction.setChecked(DependencyVisualizationEditorPreferencesManager.getHighlightReferencingResourcesPreferenceValue());
		showUnrelatedNodesAction = new ShowUnrelatedNodesAction();
		showUnrelatedNodesAction.setChecked(DependencyVisualizationEditorPreferencesManager.getShowUnrelatedNodesPreferenceValue());

		resetLayoutAction = new Action() {
			@Override
			public void run() {
				StructuredSelection selection = ((StructuredSelection) viewer.getSelection());
				if (selection != null && !selection.isEmpty()) {
					viewer.setSelection(new StructuredSelection());
					viewer.refresh();
					GraphCyclesAnalyser.resetCyclicNodesData();
					GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
					viewer.setSelection(selection);
					selectionChanged(selection.getFirstElement());
					viewer.applyLayout();
				} else {
					viewer.refresh();
					GraphCyclesAnalyser.resetCyclicNodesData();
					GraphCyclesAnalyser.analyzeCycles(viewer.getGraphControl());
					selectionChanged(null);
					// second time refresh is required to remove connection 'error' markers of cyclic dependencies. 
					viewer.refresh();
					viewer.applyLayout();
				}
			}
		};
		resetLayoutAction.setToolTipText(Messages.DependencyVisualizationEditor_RefreshActionTooltip);
		resetLayoutAction.setText(Messages.DependencyVisualizationEditor_RefreshActionText);
		resetLayoutAction.setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_RESETL_LAYOUT);
		resetLayoutAction.setEnabled(true);

		dependencyViewZoomAction = new DependencyVisualizationEditorZoomAction();
		dependencyViewZoomAction.setEnabled(true);

		showAllInWorkspaceAction = new ShowAllInWorkspaceAction();

		showAllInWorkspaceAction.setToolTipText(Messages.DependencyVisualizationEditor_ShowAllInWorkspaceActionTooltip);
		showAllInWorkspaceAction.setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_SHOW_ALL_IN_WORKSPACE);
		showAllInWorkspaceAction.setEnabled(true);
		showAllInWorkspaceAction.setChecked(DependencyVisualizationEditorPreferencesManager.getShowAllInWorkspacePreferenceValue());
	}

	/**
	 * Create the make focus on current selection action
	 * 
	 * @param objectToFocusOn
	 */
	private void makeFocusAction(final Object objectToFocusOn) {
		focusAction = new Action() {
			@Override
			public void run() {
				focusOn(objectToFocusOn, true);
				DependencyVisualizationUtils.showInProjectExplorerView(objectToFocusOn, 0);
				forwardStack.clear();
				forwardAction.setEnabled(false);
			}
		};
		focusAction.setText(String.format(Messages.DependencyVisualizationEditor_FocusActionText, getName(objectToFocusOn)));
		focusAction.setToolTipText(Messages.DependencyVisualizationEditor_FocusActionTooltip);
		focusAction.setImageDescriptor(DependencyVisualizationEditorImageProvider.DESC_FOCUS);
	}

	/**
	 * Handle the select changed. This will update the viewer whenever a selection
	 * occurs.
	 * 
	 * @param selectedItem
	 */
	private void selectionChanged(Object selectedItem) {
		currentLabelProvider.setCurrentSelection(currentNode, selectedItem);
		viewer.update(viewer.getNodeElements(), null);
		viewer.update(viewer.getConnectionElements(), null);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		// do nothing
	}

	/**
	 *  Update the input to the viewer. This will internally focus on 
	 *  passed selection and refresh the viewer. Note that this method 
	 *  will not record the history.
	 *  @param selection update input with particular selection
	 */
	public void updateInput(IStructuredSelection selection) {
		if (selection != null) {
			Object o = selection.getFirstElement();
			if (currentNode != o) {
				updateInput(o, false);
				historyStack.clear();
				historyAction.setEnabled(false);
			}
		}
	}

	/**
	 * Update the input to the viewer. This will internally focus on 
	 *  passed selection and refresh the viewer. Depending on 'recordHostory' flag
	 *  it will record the user action history.
	 * @param input should be instance of {@link IResource}
	 * @param recordHistory record history if passed 'true'. Pass 'false', if do not want to record history. 
	 */
	public void updateInput(Object input, boolean recordHistory) {
		if (input instanceof IResource && !viewer.getControl().isDisposed()) {
			IResource resource = (IResource) input;
			focusOn(resource, recordHistory);
			forwardStack.clear();
			forwardAction.setEnabled(false);
		}
	}

	/**
	 * Dispose the form
	 */
	@Override
	public void dispose() {
		form.dispose();
		super.dispose();
	}

}