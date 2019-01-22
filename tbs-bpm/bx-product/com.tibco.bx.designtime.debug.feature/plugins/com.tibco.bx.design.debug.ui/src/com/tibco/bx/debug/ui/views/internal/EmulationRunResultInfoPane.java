package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.EmulationLauncherView;
import com.tibco.bx.debug.ui.views.IBatchRunListener;
import com.tibco.bx.emulation.core.invoke.IEmulationRunnerListener;
import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationRunResultInfoPane extends Composite implements ISelectionChangedListener, IEmulationRunnerListener, IBatchRunListener,
		IDebugTargetChangedListener {

	private FormToolkit toolkit;
	private EmulationStatusLine statusLine = null;

	private Composite client;
	private List<EmulationStatusLine> statusLines;

	Section processTreeG = null;

	public EmulationRunResultInfoPane(FormToolkit toolkit, Composite parent, int style) {
		super(parent, style);
		this.toolkit = toolkit;
		statusLines = new ArrayList<EmulationStatusLine>();
		setLayout(new GridLayout(1, false));

		processTreeG = toolkit.createSection(this, Section.DESCRIPTION | Section.TITLE_BAR
		// |
				// Section.TWISTIE | Section.EXPANDED
				);
		processTreeG.setText(Messages.getString("EmulationRunResultInfoPane.processSection.label")); //$NON-NLS-1$
		processTreeG.setLayoutData(new GridData(GridData.FILL_BOTH));
		processTreeG.setLayout(new GridLayout(1, false));
		client = toolkit.createComposite(processTreeG, SWT.NONE);
		client.setLayoutData(new GridData(GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 1;
		client.setLayout(gridLayout);
		processTreeG.setClient(client);
		toolkit.adapt(this);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();

		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			Object selectedItem = ((IStructuredSelection) selection).getFirstElement();
			if (selectedItem instanceof ProcessNode) {
			}
		}
	}

	@Override
	public void updateExecutionStatus(final int eventType, final String message, final Object data) {
		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				if (BxDebugEvent.DISCONNECTED == eventType) {
					reset();
				} else if (statusLine != null && !statusLine.isDisposed()) {
					statusLine.updateExecutionStatus(eventType, message, data);
				}
			}

		});
	}

	private EmulationStatusLine createNewStatusLine(IProcessInstanceController processController, IProcessTabPaneCreator create) {
		EmulationStatusLine newStatusLine = new EmulationStatusLine(toolkit, client, SWT.NONE, EmulationLauncherView.ID, processController);

		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = ((GridLayout) client.getLayout()).numColumns;
		newStatusLine.setLayoutData(layoutData);
		toolkit.adapt(newStatusLine);
		return newStatusLine;
	}

	public void reset() {
		for (EmulationStatusLine line : statusLines) {
			line.dispose();
		}
		client.pack(true);
		processTreeG.layout();
		reflow();
	}

	@Override
	public void beforeRunNode(final IProcessInstanceController processController, final IProcessTabPaneCreator create) {

		UIJob uiJob = new UIJob("") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				EmulationStatusLine newLine = createNewStatusLine(processController, create);
				statusLines.add(newLine);
				statusLine = newLine;
				newLine.pack();
				processTreeG.layout();
				reflow();

				return Status.OK_STATUS;
			}
		};
		uiJob.schedule();
		try {
			uiJob.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleError(final IStatus error) {
		statusLine.getDisplay().syncExec(new Runnable() {
			public void run() {
				statusLine.updateExecutionStatus(BxDebugEvent.FAULT, error.getMessage(), null);
				statusLine.setRunningStatus(error);
			}
		});
	}

	@Override
	public void postRunNode() {

	}

	protected void reflow() {
		Composite c = this;
		while (c != null) {
			c.setRedraw(false);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				break;
			}
		}
		c = this;
		while (c != null) {
			c.layout(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				((SharedScrolledComposite) c).reflow(true);
				break;
			}
		}
		c = this;
		while (c != null) {
			c.setRedraw(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				break;
			}
		}
	}

	@Override
	public void selectionChanged(BxDebugTarget event) {
		if (event == null) {
			reset();
		}
	}

}
