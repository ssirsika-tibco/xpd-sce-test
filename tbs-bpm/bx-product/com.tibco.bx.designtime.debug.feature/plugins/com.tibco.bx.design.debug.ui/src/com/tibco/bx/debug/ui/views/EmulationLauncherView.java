package com.tibco.bx.debug.ui.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.EmulationResetAction;
import com.tibco.bx.debug.ui.util.DebugContextUtil;
import com.tibco.bx.debug.ui.views.internal.EmulationPane;
import com.tibco.bx.debug.ui.views.internal.EmulationProcessInstanceController;
import com.tibco.bx.debug.ui.views.internal.EmulationRunResultInfoPane;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPaneCreator;
import com.tibco.bx.debug.ui.views.internal.InvokeProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.TargetManager;
import com.tibco.bx.emulation.core.invoke.EmulationRunner;
import com.tibco.bx.emulation.core.invoke.IEmulationRunnerListener;
import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationLauncherView extends AbstractEmulationView implements IDebugContextListener, IEmulationRunnerListener {

	private EmulationPane pane;
	private EmulationRunResultInfoPane runResultPane;
	private Map<ProcessNode, IProcessInstanceController> processControllers;
	// private Map<ProcessNode , ProcessNode> nodeCache;
	private BxDebugTarget target;
	private BatchRunAction batchRun;
	private EmulationResetAction reset;

	public EmulationLauncherView() {
		super();
		// vimInputModeItem = getContributionItem();
		processControllers = new HashMap<ProcessNode, IProcessInstanceController>();
		// nodeCache = new HashMap<ProcessNode , ProcessNode>();
	}

	public final static String ID = "com.tibco.bx.debug.ui.views.EmulationLauncherView"; //$NON-NLS-1$

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		toolkit = new FormToolkit(parent.getDisplay());
		tabFolder = new CTabFolder(parent, SWT.TOP);
		tabFolder.setLayout(new GridLayout());
		tabFolder.setSimple(false);

		CTabItem mainItem = new CTabItem(tabFolder, SWT.NONE);
		mainItem.setText(Messages.getString("EmulationLauncherView_Main")); //$NON-NLS-1$
		mainForm = toolkit.createScrolledForm(tabFolder);
		mainForm.setText(Messages.getString("EmulationLauncherView_Emulation")); //$NON-NLS-1$

		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		mainForm.getBody().setLayout(gridLayout);
		toolkit.decorateFormHeading(mainForm.getForm());

		createEmulationView(mainForm.getBody());
		createEmulationResultView(mainForm.getBody());

		mainItem.setControl(mainForm);
		tabFolder.setSelection(mainItem);


		batchRun = new BatchRunAction();
		mainForm.getToolBarManager().add(batchRun);
		reset = new EmulationResetAction(runResultPane, pane, this);
		mainForm.getToolBarManager().add(reset);
		mainForm.getToolBarManager().update(true);
		batchRun.addListener(runResultPane);

		setTarget(TargetManager.getDefault().getCurrentTarget());
		updateViewPartToolBar();
		DebugUITools.getDebugContextManager().getContextService(getSite().getWorkbenchWindow()).addDebugContextListener(this);
		TargetManager.getDefault().addDebugTargetChangedListener(pane);
		TargetManager.getDefault().addDebugTargetChangedListener(runResultPane);
		initialize();
	}
	
	private void initialize(){
		if(getTarget() != null){
			boolean isConnected = !getTarget().isDisconnected() || !getTarget().isTerminated();
			updateEmulationView(isConnected);
		}else{
			updateEmulationView(false);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		DebugUITools.getDebugContextManager().getContextService(getSite().getWorkbenchWindow()).removeDebugContextListener(this);
	}

	public void updateRunningStatus(String message) {
		IStatusLineManager status = getViewSite().getActionBars().getStatusLineManager();
		String[] messages = message.split(":"); //$NON-NLS-1$
		IProcessInstanceController controller = (IProcessInstanceController) findControllerByProcessNodeId(messages[0]);
		if (controller != null && controller.getProcessNode() instanceof ProcessNode) {
			message = ((ProcessNode)controller.getProcessNode()).getName() + ": " + messages[1]; //$NON-NLS-1$
		} else {
			message = null;
		}
		status.setMessage(message);
	}

	private void createEmulationResultView(Composite body) {
		runResultPane = new EmulationRunResultInfoPane(toolkit, body, SWT.WRAP);
		runResultPane.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private void createEmulationView(Composite parent) {
		pane = new EmulationPane(toolkit, parent, SWT.WRAP);
		pane.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Override
	public void setFocus() {

	}
	
	private void updateEmulationView(boolean isLaunch) {
		batchRun.setEnabled(isLaunch);
		reset.setEnabled(isLaunch);
		pane.setCheckboxTreeEnabled(isLaunch);
		
	}

	@Override
	public void debugContextChanged(DebugContextEvent event) {
		if (DebugContextUtil.hasTarget(event)) {
			BxDebugTarget newTarget = DebugContextUtil.getTargetFromDebugContextEvent(event);
			if (newTarget != null) {
				boolean isConnected = !newTarget.isDisconnected() || !newTarget.isTerminated();
				setTarget(isConnected ? newTarget : null);
				updateEmulationView(isConnected);
			} else if (newTarget == null || newTarget.isTerminated() || newTarget.isDisconnected()) {
				setTarget(null);
				processControllers.clear();
				runResultPane.reset();
				updateEmulationView(false);
			}
		}
	}

	public CTabItem createIProcessTabPane(IProcessInstanceController control) {
		if (control != null) {
			InvokeProcessTabPane processTabPane = new InvokeProcessTabPane(toolkit, tabFolder, SWT.NONE, control.isSoapType(), ""); //$NON-NLS-1$
			toolkit.adapt(processTabPane);
			processTabPane.setController(control);
			processTabPane.refresh();

			CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
			item.setControl(processTabPane);
			item.setText(control.getProcessInstanceId());
			item.addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					((CTabItem) e.getSource()).getControl().dispose();
					return;
				}

			});

			return item;
		}
		return null;
	}

	private IProcessInstanceController createProcessInstanceController(ProcessNode clonNode) {
		IProcessInstanceController controller = processControllers.get(clonNode);
		if (controller == null) {
			controller = new EmulationProcessInstanceController(clonNode);
			processControllers.put(clonNode, controller);
		}
		return controller;
	}

	@Override
	public void setSpecialInstanceTab(String instanceId) {
		CTabItem tabItem = findInstanceItemById(instanceId);
		if (tabItem != null) {
			tabFolder.setSelection(tabItem);
		} else {
			tabItem = createIProcessTabPane(findControllerByInstanceId(instanceId));
			tabFolder.setSelection(tabItem);
		}
		updateViewPartToolBar();
	}

	private IProcessInstanceController findControllerByProcessNodeId(String nodeId) {
		for (IProcessInstanceController controller : processControllers.values()) {
			if (StringUtils.equal(nodeId, ((ProcessNode)controller.getProcessNode()).getId())) {
				return controller;
			}
		}
		return null;
	}

	private IProcessInstanceController findControllerByInstanceId(String instanceId) {
		for (IProcessInstanceController controller : processControllers.values()) {
			if (StringUtils.equal(instanceId, controller.getProcessInstanceId())) {
				return controller;
			}
		}
		return null;
	}

	private void setTarget(BxDebugTarget newTarget) {
		target = newTarget;
		pane.setDebugTarget(newTarget);
		if (newTarget != null) {
			batchRun.setConnected(!newTarget.isDisconnected());
			pane.setCheckboxTreeEnabled(!newTarget.isDisconnected());
		}else{
			batchRun.setConnected(false);
			pane.setCheckboxTreeEnabled(false);
		}
	}

	public BxDebugTarget getTarget() {
		return target;
	}

	private class BatchRunAction extends Action implements IEmulationRunnerListener {
		private boolean isConnected = true;
		Job job;

		private void setConnected(boolean isConnected) {
			this.isConnected = isConnected;
		}

		private ListenerList runnerChangerListener = new ListenerList();

		public BatchRunAction() {
			setText(Messages.getString("EmulationLauncherView_RunEmulation")); //$NON-NLS-1$
			setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_RUN_EN));
		}

		public void addListener(IBatchRunListener listener) {
			runnerChangerListener.add(listener);
		}

		public void removeListener(IBatchRunListener listener) {
			runnerChangerListener.remove(listener);
		}

		private void sendBeforeRunNode(IProcessInstanceController processController, IProcessTabPaneCreator create) {
			for (int i = 0; i < runnerChangerListener.getListeners().length; i++) {
				((IBatchRunListener) runnerChangerListener.getListeners()[i]).beforeRunNode(processController, create);
			}
		}

		private void sendPostRunNode() {
			for (int i = 0; i < runnerChangerListener.getListeners().length; i++) {
				((IBatchRunListener) runnerChangerListener.getListeners()[i]).postRunNode();
			}
		};

		private void sendError(IStatus error) {
			for (int i = 0; i < runnerChangerListener.getListeners().length; i++) {
				((IBatchRunListener) runnerChangerListener.getListeners()[i]).handleError(error);
			}
		}

		@Override
		public void run() {
			final Map<ProcessNode, EmulationRunner> checkedRunner = pane.getSelectedRunner();
			final ProcessNode[] checkedProcessNode = pane.getCheckedItem();
			pane.setNodeWillRun(checkedRunner);
			runResultPane.reset();
			job = new Job(Messages.getString("EmulationLauncherView_RunEmulation")) { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					processControllers.clear();
					try {
						for (final ProcessNode processNode : checkedProcessNode) {

							if (monitor.isCanceled() || !isConnected) {
								monitor.done();
								return Status.OK_STATUS;
							}
							if (validatorNode(processNode)) {
								EmulationRunner runner = checkedRunner.get(processNode);
								runner.addEmulationRunnerListener(BatchRunAction.this);
								runner.addEmulationRunnerListener(EmulationLauncherView.this);
								runner.addEmulationRunnerListener(runResultPane);
								runner.addEmulationRunnerListener(pane);

								IProcessInstanceController processController = createProcessInstanceController(processNode);

								getTarget().addBxTheadChangedListener(null, processController);
								try {
									sendBeforeRunNode(processController, EmulationLauncherView.this);
									runner.executeEmulation(processNode, processController, monitor);
									sendPostRunNode();
								} catch (CoreException e) {
									if (e.getStatus() instanceof MultiStatus) {
										MultiStatus multiStatus = (MultiStatus) e.getStatus();
										for (IStatus status : multiStatus.getChildren()) {
											processController.setRunningStatus(status);
											sendError(status);
										}
									} else {
										processController.setRunningStatus(e.getStatus());
										sendError(e.getStatus());
									}
									monitor.done();
								} finally {
									runner.removeEmulationRunnerListener(runResultPane);
									runner.removeEmulationRunnerListener(pane);
									runner.removeEmulationRunnerListener(BatchRunAction.this);
									runner.removeEmulationRunnerListener(EmulationLauncherView.this);
									if(getTarget() !=null){
										getTarget().removeBxTheadChangedListener(processController.getProcessInstanceId(), processController);
									}
								}
							}
						}
					} finally {
						monitor.done();
						updateViewPartToolBar();
						updateEmulationPane();
					}

					return Status.OK_STATUS;
				}

			};

			job.schedule();
		}

		@Override
		public void updateExecutionStatus(int eventType, String message, Object data) {
			if (eventType == BxDebugEvent.DISCONNECTED) {
				isConnected = false;
				if (job != null) {
					job.done(Status.OK_STATUS);
				}
			}
		}
	}

	private void updateEmulationPane() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				pane.updateEmulationViewer();
			}

		});
	}

	public boolean isAvailable() {
		return true;
	}

	private boolean validatorNode(ProcessNode processNode) {
		if (processNode == null || processNode.getInput() == null || processNode.getId() == null) {
			return false;
		}
		return true;
	}

	@Override
	public Viewer getViewer() {
		return (Viewer) pane.getSelectionProvider();
	}

	@Override
	public void updateExecutionStatus(final int eventType, final String message, Object data) {
		pane.getDisplay().syncExec(new Runnable() {
			public void run() {
				if (eventType == BxDebugEvent.DISCONNECTED) {
					setTarget(null);
					processControllers.clear();
					updateRunningStatus(""); //$NON-NLS-1$
				} else {
					updateRunningStatus(message);
				}
			}
		});
	}

	@Override
	public IProcessTabPane getProcessInstanceController(String activeId,
			EObject activity) {
		// TODO Auto-generated method stub
		return null;
	}


}
