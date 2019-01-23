package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.ShowProcessSummaryAction;
import com.tibco.bx.emulation.core.common.IActivityElement;

public class ProcessTabPane extends Form implements IProcessTabPane {

	private ProcessParameterDisplayPane parameterDisplayPane;
	private SOAPMessageDisplayPane messageDisplayPane;
	private Composite layerPane;
	private boolean isParameterPaneDisplayed = true;
	private StackLayout layout;
	private IProcessInstanceController controller;
	private String paneState;

	IHyperlinkListener listener = new HyperlinkAdapter() {
		@Override
		public void linkActivated(HyperlinkEvent e) {
			ShowProcessSummaryAction processSummary = new ShowProcessSummaryAction(controller);
			processSummary.execute();
		}
	};

	public ProcessTabPane(FormToolkit toolkit, Composite parent, int style, boolean isSoapType, String soapRequest) {
		super(parent, style);
		setBackground(toolkit.getColors().getBackground());
		setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		setFont(JFaceResources.getHeaderFont());
		getBody().setLayout(new FillLayout());

		layerPane = toolkit.createComposite(getBody());
		layout = new StackLayout();
		layerPane.setLayout(layout);

		toolkit.decorateFormHeading(this);
		setToolBarVerticalAlignment(SWT.CENTER);

		parameterDisplayPane = new ProcessParameterDisplayPane(layerPane, SWT.NONE, toolkit);
		toolkit.adapt(parameterDisplayPane);
		messageDisplayPane = new SOAPMessageDisplayPane(layerPane, SWT.NONE, toolkit, soapRequest);
		toolkit.adapt(messageDisplayPane);

		layout.topControl = isSoapType ? messageDisplayPane : parameterDisplayPane;
		isParameterPaneDisplayed = !isSoapType;
		createToolBar();
	}

	@Override
	public void setController(IProcessInstanceController controller) {
		this.controller = controller;
		if (controller instanceof ProcessInstanceController)
			((ProcessInstanceController) controller).setProcessTabPane(this);
	}

	@Override
	public void refresh() {
		UIJob updateInput = new UIJob(Messages.getString("ProcessTabPane.refreshUIJob.label")) { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				Object value = null;
				if ((value = controller.getData4Show(IProcessInstanceController.PARA_INPUT)) != null) {
					parameterDisplayPane.getInputParameterView().setInput((IActivityElement) value);
				}
				if ((value = controller.getData4Show(IProcessInstanceController.PARA_OUTPUT)) != null) {
					IActivityElement element = ProcessDataUtil.handleInOutElement((IActivityElement) value);
					parameterDisplayPane.getOutputParameterView().setInput(element);
				}
				if ((value = controller.getData4Show(IProcessInstanceController.SOAP_INPUT)) != null) {
					messageDisplayPane.setRequestValue((String) value);
				}
				if ((value = controller.getData4Show(IProcessInstanceController.SOAP_OUTPUT)) != null) {
					messageDisplayPane.setResponseValue((String) value);
				}

				if (!ProcessInstance.State.COMPLETED.equals(getPaneState()) && !ProcessInstance.State.CANCELLED.equals(getPaneState())
						&& !ProcessInstance.State.FAILED.equals(getPaneState())) {
					updateFormText();
				}

				return Status.OK_STATUS;
			}

		};
		updateInput.setSystem(true);
		updateInput.schedule();
	}

	@Override
	public IProcessInstanceController getController() {
		return controller;
	}

	protected void createToolBar() {
		List<Action> actions = getActionsForToolbar();
		for (Object action : actions) {
			if (action instanceof Action) {
				getToolBarManager().add((IAction) action);
			} else if (action instanceof ContributionItem) {
				getToolBarManager().add((ContributionItem) action);
			}
		}
		getToolBarManager().update(true);
	}

	protected List<Action> getActionsForToolbar() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new SwitchToSoapPaneAction("")); //$NON-NLS-1$
		actions.add(new SwitchToParameterPaneAction("")); //$NON-NLS-1$
		return actions;
	}

	private void updateFormText() {
		EObject process = controller.getProcess();
		String processInstanceId = controller.getProcessInstanceId();
		if (isDisposed())
			return;
		if (process != null && processInstanceId != null) {
			String formLabel = ProcessUtil.getDisplayName(process) + " [" + processInstanceId + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			String state = controller.getThread() == null ? "" : controller.getThread().getState().toLowerCase();//$NON-NLS-1$
			setPaneState(state);
			if (state != null && !"".equals(state)) { //$NON-NLS-1$
				state = state.substring(0, 1).toUpperCase() + state.substring(1, state.length());
			}
			setText(formLabel);
			setMessage(state, getMessageStatus());
			ProcessTabPane.this.addMessageHyperlinkListener(listener);
			Composite parent = getParent();
			if (parent instanceof CTabFolder) {
				CTabItem[] items = ((CTabFolder) parent).getItems();
				for (int i = 0; i <= items.length; i++) {
					if (items[i].getControl() == ProcessTabPane.this) {
						items[i].setText(ProcessUtil.getDisplayName(process));
						items[i].setToolTipText(formLabel + " " + state); //$NON-NLS-1$
						break;
					}
				}
			}
		}
	}

	private int getMessageStatus() {
		int kind = controller.getStatus();
		int type = IMessageProvider.NONE;
		switch (kind) {
		case BxDebugEvent.FAULT:
			type = IMessageProvider.ERROR;
			break;
		case BxDebugEvent.TERMINATED:
			type = IMessageProvider.WARNING;
			break;
		case BxDebugEvent.SUSPENDED:
			String eventMessage = controller.getErrorMessage();
			if (eventMessage != null) {
				type = IMessageProvider.ERROR;
				break;
			}

		case BxDebugEvent.COMPLETED:
		case BxDebugEvent.RESUMED:
		case BxDebugEvent.ON_ENTRY:
		case BxDebugEvent.ON_EXIT:
			type = IMessageProvider.INFORMATION;
			break;
		case BxDebugEvent.DISCONNECTED:
			type = IMessageProvider.WARNING;
			break;
		default:
			type = IMessageProvider.INFORMATION;
			break;
		}
		return type;
	}

	@Override
	public void dispose() {
		controller.dispose();
		super.dispose();
	}

	class SwitchToSoapPaneAction extends Action {
		public SwitchToSoapPaneAction(String text) {
			super(text, IAction.AS_RADIO_BUTTON);
			setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_LAUNCHER_SOAP_VIEW));
			setToolTipText(Messages.getString("ProcessTabPane.soapTabPane.label")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			layout.topControl = messageDisplayPane;
			if (isChecked()) {
				isParameterPaneDisplayed = true;
			}
			layerPane.layout();
		}

		@Override
		public boolean isChecked() {
			return !isParameterPaneDisplayed;
		}
	}

	class SwitchToParameterPaneAction extends Action {

		public SwitchToParameterPaneAction(String text) {
			super(text, IAction.AS_RADIO_BUTTON);
			setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_LAUNCHER_PARAM_VIEW));
			setToolTipText(Messages.getString("ProcessTabPane.paraTabPane.label")); //$NON-NLS-1$
		}

		@Override
		public void run() {
			layout.topControl = parameterDisplayPane;
			if (isChecked()) {
				isParameterPaneDisplayed = false;
			}
			layerPane.layout();
		}

		@Override
		public boolean isChecked() {
			return isParameterPaneDisplayed;
		}
	}

	public void handleRequestMessage(final String request) {
		UIJob updateSoapJob = new UIJob(Messages.getString("ProcessTabPane.handleRequestUIJob.label")) { //$NON-NLS-1$

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				messageDisplayPane.setResponseValue(request);
				monitor.done();
				// need update toolbar
				return Status.OK_STATUS;
			}

		};

		updateSoapJob.setSystem(true);
		updateSoapJob.schedule();
	}

	public void handleResponseMessage(final String response) {
		UIJob updateSoapJob = new UIJob(Messages.getString("ProcessTabPane.handleResponseUIJob.label")) { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				messageDisplayPane.setRequestValue(response);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		updateSoapJob.setSystem(true);
		updateSoapJob.schedule();
	}

	public String getPaneState() {
		return paneState;
	}

	public void setPaneState(String paneState) {
		this.paneState = paneState;
	}

}
