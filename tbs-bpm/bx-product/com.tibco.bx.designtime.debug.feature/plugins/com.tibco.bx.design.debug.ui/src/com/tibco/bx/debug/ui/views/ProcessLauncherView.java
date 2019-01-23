package com.tibco.bx.debug.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.AbstractInvokeActionProxy;
import com.tibco.bx.debug.ui.actions.InvokeAction;
import com.tibco.bx.debug.ui.views.internal.ComboxContributionItem;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.IToolBarChangeListener;
import com.tibco.bx.debug.ui.views.internal.InvokeProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.ProcessDetailPane;
import com.tibco.bx.debug.ui.views.internal.ProcessInstanceController;
import com.tibco.bx.debug.ui.views.internal.ProcessInvokePane;
import com.tibco.bx.debug.ui.views.internal.TargetManager;
import com.tibco.bx.emulation.bpm.core.util.EmulationBPMUtil;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;

public class ProcessLauncherView extends AbstractEmulationView implements IToolBarChangeListener, IAdaptable {

	private ComboxContributionItem contributionItem;
	private ProcessDetailPane processDetailPane;
	private Form form;
	private ProcessInvokePane invokePane;
	private ToolBarManager toolBarManager;
	private InvokeAction invokeAction;
	private ActionContributionItem dropDownContributionItem;
	private DropDownAction dropDownAction;
	private List<IProcessTabPane> processTabPanes;

	public ProcessLauncherView() {
		super();
		invokeAction = new InvokeAction();
		invokeAction.setText(Messages.getString("ProcessLauncherView_Invoke")); //$NON-NLS-1$
		processTabPanes = new ArrayList<IProcessTabPane>();
	}

	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		toolkit = new FormToolkit(parent.getDisplay());
		tabFolder = new CTabFolder(parent, SWT.TOP);
		tabFolder.setLayout(new GridLayout());
		tabFolder.setSimple(false);
		CTabItem item0 = new CTabItem(tabFolder, SWT.NONE);
		item0.setText(Messages.getString("ProcessLauncherView_Main")); //$NON-NLS-1$
		form = toolkit.createForm(tabFolder);
		item0.setControl(form);
		tabFolder.setSelection(item0);

		form.setText(Messages.getString("ProcessLauncherView_ProcessLauncher")); //$NON-NLS-1$
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginHeight = 2;
		gridLayout.verticalSpacing = 0;
		form.getBody().setLayout(gridLayout);
		toolkit.decorateFormHeading(form);
		form.setToolBarVerticalAlignment(SWT.CENTER);

		processDetailPane = new ProcessDetailPane(form.getBody(), SWT.NONE, toolkit);
		// init bxTarget
		processDetailPane.setTarget(TargetManager.getDefault().getCurrentTarget());
		TargetManager.getDefault().setCurrentTarget(processDetailPane.getTarget());
		toolkit.adapt(processDetailPane);
		processDetailPane.setLayoutData(new GridData(GridData.FILL_BOTH));
		gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		processDetailPane.setLayout(gridLayout);
		processDetailPane.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));

		invokePane = new ProcessInvokePane(form.getBody(), toolkit, SWT.NONE, this, getViewSite());
		invokePane.setLayoutData(new GridData(GridData.FILL_BOTH));
		toolkit.adapt(invokePane);
		processDetailPane.addDebugTargetChangedListener(invokePane);

		createToolBar(form);

		processDetailPane.getSelectionProvider().addSelectionChangedListener(invokeAction);
		processDetailPane.getSelectionProvider().addSelectionChangedListener(contributionItem);
		contributionItem.addSelectionChangedListener(invokePane);
		contributionItem.addSelectionChangedListener(invokeAction);

		TargetManager.getDefault().addDebugTargetChangedListener(invokePane);
		DebugUITools.getDebugContextManager().getContextService(getSite().getWorkbenchWindow()).addDebugContextListener(
				processDetailPane);
		updateViewPartToolBar();
		getViewSite().getActionBars().updateActionBars();
	}

	public void setFocus() {
		processDetailPane.refresh();
	}

	public void dispose() {
		DebugUITools.getDebugContextManager().getContextService(
				getSite().getWorkbenchWindow()).removeDebugContextListener(
				processDetailPane);
		toolkit.dispose();
		super.dispose();
	}

	@Override
	public void updateToolBar(boolean isSoapPane) {
		toolBarManager
				.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		toolBarManager.appendToGroup(IWorkbenchActionConstants.MB_ADDITIONS,
				dropDownContributionItem);
		toolBarManager.add(new Separator());
		dropDownAction.createMenu();
		toolBarManager.update(true);
		updateViewPartToolBar();
	}

	/*
	 * to initialize or update ActionDelegates
	 */
	protected void createToolBar(Form form) {
		toolBarManager = (ToolBarManager) form.getToolBarManager();
		dropDownAction = new DropDownAction();
		dropDownContributionItem = new ActionContributionItem(dropDownAction);
		contributionItem = new ComboxContributionItem(Messages
				.getString("ProcessLauncherView_Item"), new ViewerFilter() { //$NON-NLS-1$

					@Override
					public boolean select(Viewer viewer, Object parentElement,
							Object element) {
						/*
						 * TODO if(element instanceof EObject &&
						 * ProcessUtil.isWebServiceImplementationActivity
						 * ((EObject)element)){
						 * //StartEvent,IntermediateEvent,ReceiveTask return
						 * true; } return false;
						 */
						return true;
					}

				});
		toolBarManager.add(contributionItem);
		updateToolBar(false);
	}

	public boolean isAvailable() {
		return !(processDetailPane == null
				|| processDetailPane.getSelectionProvider() == null || ((Viewer) processDetailPane
				.getSelectionProvider()).getControl().isDisposed());
	}

	@Override
	public boolean isSoapTypeMode() {
		return true;
	}

	@Override
	public IProcessTabPane createIProcessTabPane() {
		EObject startActivity = invokePane.getStartActivity();
		String soapRequest = invokePane.getRequestSoapMessage();
		ProcessInstanceController controller = new ProcessInstanceController(startActivity);
		boolean isSoapType = ProcessUtil.isWebServiceImplementationActivity(startActivity);
		controller.setInvokeType(isSoapType);
		if (!isSoapType) {
			Object inputObject = invokePane.getInputEditViewer().getInput();
			if (inputObject != null) {
				if (!EmulationBPMUtil.isValidInOutElement((IInOutElement) inputObject)) {
					MessageDialog.openInformation(null,Messages.getString("ProcessLauncherView.debugModeDialog.title"), //$NON-NLS-1$
									Messages.getString("ProcessLauncherView.debugModeDialog.message")); //$NON-NLS-1$
					return null;
				}
				controller.setInputElement(((IInOutElement) inputObject).clone());
			}
		} else {
			Input input = EmulationFactory.eINSTANCE.createInput();
			input.setId(ProcessUtil.getElementId(startActivity));
			input.setName(ProcessUtil.getDisplayName(startActivity));
			controller.setInputElement(EmulationUtil.createInputElement(input, startActivity, processDetailPane.getTarget().getModelType()));
		}
		InvokeProcessTabPane processTabPane = new InvokeProcessTabPane(toolkit,
				tabFolder, SWT.NONE, isSoapType, soapRequest);

		toolkit.adapt(processTabPane);
		processTabPane.setController(controller);

		CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setControl(processTabPane);
		item.setText(Messages.getString("ProcessLauncherView_Instance")); //$NON-NLS-1$
		item.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				((CTabItem) e.getSource()).getControl().dispose();
				updateViewPartToolBar();
				return;
			}

		});
		processTabPanes.add(processTabPane);

		tabFolder.setSelection(item);
		return processTabPane;
	}

	@Override
	public Viewer getViewer() {
		return (Viewer) processDetailPane.getSelectionProvider();
	}

	class DropDownAction extends Action {
		private Menu menu = null;

		public DropDownAction() {
			super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
			setImageDescriptor(DebugUIActivator
					.getRegisteredImageDescriptor(DebugUIActivator.IMG_RUN_EN));
			setDisabledImageDescriptor(DebugUIActivator
					.getRegisteredImageDescriptor(DebugUIActivator.IMG_RUN_DIS));
			setToolTipText(Messages
					.getString("ProcessLauncherView_Run_ToolTip")); //$NON-NLS-1$
		}

		public void createMenu() {
			Shell shell = tabFolder.getShell();
			if (shell == null)
				return;// the view is not displayed
			menu = new Menu(shell);

			for (AbstractInvokeActionProxy proxyAction : invokePane
					.getInvokeActions()) {
				proxyAction.setProxy(invokeAction);
				proxyAction.setProcessTabPaneCreator(ProcessLauncherView.this);

				LaunchMenuItem menuItem = new LaunchMenuItem(proxyAction, menu,
						SWT.NONE);
				menuItem.setText(proxyAction.getText());
			}

			setEnabled(menu.getItemCount() > 0);
		}

		@Override
		public void run() {

		}

		@Override
		public void runWithEvent(Event event) {
			if (event.detail == SWT.ARROW) {
				// Position the menu below and vertically aligned with the the
				// drop down tool button.
				final ToolBar toolBar = ((ToolBarManager) dropDownContributionItem
						.getParent()).getControl();

				Rectangle toolItemBounds = ((ToolItem) dropDownContributionItem
						.getWidget()).getBounds();
				Point point = toolBar.toDisplay(new Point(toolItemBounds.x,
						toolItemBounds.y));
				menu.setLocation(point.x, point.y + toolItemBounds.height);
				menu.setVisible(true);
			} else {
				// Main area of drop down tool item selected.
				// call the first ParameterInvokeActionProxy
				Event itemEvent = new Event();
				itemEvent.time = event.time;
				itemEvent.widget = menu.getItem(0);
				menu.getItem(0).notifyListeners(SWT.Selection, itemEvent);
			}
		}

		class LaunchMenuItem {
			private Action action;
			MenuItem menuItem;

			public LaunchMenuItem(Action action, Menu parent, int style) {
				menuItem = new MenuItem(parent, style);
				this.action = action;

				menuItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						menu.setVisible(false);
						LaunchMenuItem.this.action.run();
					}

				});
			}

			public void setText(String text) {
				menuItem.setText(text);
			}

			public MenuItem getMenuItem() {
				return menuItem;
			}
		}
	}

	@Override
	public BxDebugTarget getTarget() {
		return TargetManager.getDefault().getCurrentTarget();
	}

	public IProcessTabPane getProcessInstanceController(String activeId,
			EObject startActivity) {
		CTabItem[] items = tabFolder.getItems();
		for (int i = 1; i < items.length; i++) {
			IProcessTabPane tabPane = ((IProcessTabPane) items[i].getControl());
			IProcessInstanceController controller = tabPane.getController();
			if (controller.getProcessInstanceId().equals(activeId)) {
				tabFolder.setSelection(items[i]);
				return tabPane;
			}
		}
		return null;
	}
}
