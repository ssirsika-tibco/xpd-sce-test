package com.tibco.bx.debug.ui.sourcelookup;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.sourcelookup.ISourceDisplay;
import org.eclipse.debug.ui.sourcelookup.ISourceLookupResult;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.BxThread;

public class ThreadSourceDisplayAdapter implements ISourceDisplay {

	public void displaySource(Object element, IWorkbenchPage page,
			boolean forceSourceLookup) {
		if(!(element instanceof BxThread)){
			return;
		}
		final BxThread bxThread = (BxThread) element;
		final IWorkbenchPage p = page;
		UIJob sourceLookupJob = new UIJob(Messages.getString("ThreadSourceDisplayAdapter.UIJobMessage")){ //$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor arg0) {
				ISourceLookupResult source = DebugUITools.lookupSource(bxThread, bxThread.getLaunch().getSourceLocator());
				DebugUITools.displaySource(source, p);
				return Status.OK_STATUS;
			}
		};
		sourceLookupJob.setSystem(true);
		sourceLookupJob.schedule();
	}

}
