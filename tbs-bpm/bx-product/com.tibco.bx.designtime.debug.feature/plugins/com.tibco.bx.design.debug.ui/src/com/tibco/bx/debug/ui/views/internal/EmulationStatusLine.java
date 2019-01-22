package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.ShowProcessSummaryAction;
import com.tibco.bx.emulation.core.invoke.IEmulationRunnerListener;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.views.IEmulationView;

public class EmulationStatusLine extends Composite implements IEmulationRunnerListener{

    private ImageHyperlink nodeName;
    
    private Button stopAction;
    
    private ImageHyperlink statusLabel;
    
    private String instanceId ;
    
    private BxThread bxThread;
    
    private String viewId;
    
    private IStatus runningStatus = Status.OK_STATUS;
    
    private IProcessInstanceController processController;
    //private IProcessTabPaneCreator creator;
    
    
    public void setRunningStatus(IStatus runningStatus) {
		this.runningStatus = runningStatus;
	}


	public EmulationStatusLine(FormToolkit toolkit , Composite parent, int style , 
							   String viewId, IProcessInstanceController processController 
							   ){
        super(parent, style);
        this.viewId = viewId;
        FormLayout formLayout = new FormLayout();
        this.setLayout(formLayout);
        this.processController = processController;
        
        nodeName = toolkit.createImageHyperlink(this, SWT.LEFT);
        nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_NODE));
        FormData formData = new FormData();
        formData.left = new FormAttachment(5,5);
        formData.right = new FormAttachment(40, -5);
        nodeName.setLayoutData(formData);
        
        nodeName.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
              
            	if(runningStatus.isOK()){
            		IViewPart part = DebugUIActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(EmulationStatusLine.this.viewId);
            		if(part != null && part instanceof IEmulationView ) {
            			((IEmulationView)part).setSpecialInstanceTab(instanceId);
            		}
            	}else{
            		MessageDialog.openError(null, Messages.getString("EmulationStatusLine.ErrorDialog.title"), runningStatus.getMessage()); //$NON-NLS-1$
            	}
            }
            
        });
        
        Object processNode = this.processController.getProcessNode();
		if(processNode != null && processNode instanceof ProcessNode){
        	nodeName.setText(((ProcessNode)processNode).getName());
        }
        
        statusLabel = toolkit.createImageHyperlink(this, SWT.LEFT);
        formData = new FormData();
        formData.left = new FormAttachment(nodeName, 5);
        formData.right = new FormAttachment(80, 0);
        formData.top = new FormAttachment(nodeName , 0, SWT.TOP);
        formData.bottom = new FormAttachment(nodeName,0,SWT.BOTTOM);
        statusLabel.setLayoutData(formData);
        statusLabel.addHyperlinkListener(new HyperlinkAdapter(){
        	 @Override
             public void linkActivated(HyperlinkEvent e) {
        		 if(EmulationStatusLine.this.processController.getThread() != null){
        			 ShowProcessSummaryAction processSummary = new ShowProcessSummaryAction(EmulationStatusLine.this.processController);
        			 processSummary.execute();
        		 }else{
        			 MessageDialog.openError(null, Messages.getString("EmulationStatusLine.ErrorDialog.title"), runningStatus.getMessage()); //$NON-NLS-1$
        		 }
        	 }
        });

        stopAction = toolkit.createButton(this, Messages.getString("EmulationStatusLine.stopAction.label"), SWT.FLAT); //$NON-NLS-1$
        formData = new FormData();
        formData.left = new FormAttachment(statusLabel, 5);
        formData.right = new FormAttachment(100, -5);
        formData.top = new FormAttachment(nodeName , 0, SWT.TOP);
        formData.bottom = new FormAttachment(nodeName,0,SWT.BOTTOM);
        stopAction.setLayoutData(formData);
        enableStopButton(false);
        
        stopAction.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                if(bxThread != null && bxThread.canTerminate()) {
                    try {
                        bxThread.terminate();
                        enableStopButton(false);
                    } catch (DebugException e1) {
                        DebugUIActivator.log(e1);
                    }
                }
            }
        });
    }

    private void enableStopButton(boolean isEnable) {
        stopAction.setVisible(isEnable);
    }
    
    @Override
    public void updateExecutionStatus(int eventType, String message, Object data) {
		String[] messages = message.split(":"); //$NON-NLS-1$
		if (messages.length >= 2) {
			if (BxDebugEvent.DISCONNECTED == eventType) {
				bxThread = null;
				nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_FAULT));
				enableStopButton(false);
				return;
			}
			Object processNode = processController.getProcessNode();
			String index = ((ProcessNode)processNode).getId();
			if (StringUtils.equal(messages[0], index)) {
				nodeName.setText(((ProcessNode)processNode).getName());
				statusLabel.setText(messages[1].trim());
				if (BxDebugEvent.STARTED == eventType) {
					if (data instanceof BxThread) {
						bxThread = (BxThread) data;
						instanceId = bxThread.getInstanceId();
					}
					enableStopButton(true);
				} else if (BxDebugEvent.TERMINATED == eventType) {
					bxThread = null;
					nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_TERMINATED));
					enableStopButton(false);
				} else if (BxDebugEvent.FAULT == eventType) {
					bxThread = null;
					nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_FAULT));
					enableStopButton(false);
				} else if (BxDebugEvent.COMPLETED == eventType) {
					bxThread = null;
					nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_COMPLETED));
					enableStopButton(false);
				}
			}
		} else {
			bxThread = null;
			nodeName.setImage(DebugUIActivator.getRegisteredImage(DebugUIActivator.IMG_EMULATION_FAULT));
			statusLabel.setText(BxDebugEvent.getKindLiteral(BxDebugEvent.FAULT));
		}

	}

}
