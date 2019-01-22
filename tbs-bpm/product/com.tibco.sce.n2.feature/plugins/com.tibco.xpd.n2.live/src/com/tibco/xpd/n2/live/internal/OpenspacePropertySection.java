package com.tibco.xpd.n2.live.internal;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section to hold sash contributions for the Openspace view.
 * 
 * @author nwilson
 * @since 4 Sep 2014
 */
public class OpenspacePropertySection extends SashContributionSection {

    private IOperationHistoryListener historyListener;

    /**
     * Constructor to initialise the section to accept Server input objects.
     */
    public OpenspacePropertySection() {
        super(DeployPackage.eINSTANCE.getServer(),
                "com.tibco.n2.livedev.OpenspaceLiveViewPropertySection"); //$NON-NLS-1$
        setShowInWizard(false);

    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.SashContributionSection#createGeneralSection(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        historyListener = new IOperationHistoryListener() {

            @Override
            public void historyNotification(OperationHistoryEvent event) {
                if (OperationHistoryEvent.UNDONE == event.getEventType()
                        || OperationHistoryEvent.REDONE == event.getEventType()) {
                    refresh();
                }
            }
        };
        IWorkbench workbench = getSite().getWorkbenchWindow().getWorkbench();
        IOperationHistory operationHistory =
                workbench.getOperationSupport().getOperationHistory();
        operationHistory.addOperationHistoryListener(historyListener);
        return super.createGeneralSection(parent, toolkit);
    }

    /**
     * @see com.tibco.xpd.ui.properties.SashDividedTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        IWorkbench workbench = getSite().getWorkbenchWindow().getWorkbench();
        IOperationHistory operationHistory =
                workbench.getOperationSupport().getOperationHistory();
        operationHistory.removeOperationHistoryListener(historyListener);
        super.dispose();
    }
}
