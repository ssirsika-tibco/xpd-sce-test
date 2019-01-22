package com.tibco.bx.debug.ui.views.internal;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.AbstractInvokeActionProxy;
import com.tibco.bx.emulation.ui.common.TestpointEditViewer;

public class ProcessInvokePane extends Composite implements
        ISelectionChangedListener,  IDebugTargetChangedListener {
    
    private SOAPMessageInvokePane soapMessageInvokePane;
    private ProcessParameterInvokePane parameterInvokePane;
    private StackLayout stackLayout;
    //private FormToolkit toolkit;
    private IToolBarChangeListener toolBarChangeListener;
    private BxDebugTarget target;
    private EObject startActivity;
    private IViewSite viewSite;
    
    public ProcessInvokePane(Composite parent,FormToolkit toolkit, int style , 
    						 IToolBarChangeListener toolBarChangeListener , 
    						 IViewSite viewSite) {
        super(parent, style);
        //this.toolkit = toolkit;
        this.viewSite = viewSite;
        stackLayout = new StackLayout();
        this.toolBarChangeListener = toolBarChangeListener;
        setLayout(stackLayout);
        createInvokePane(toolkit,toolBarChangeListener);
    }

    private void createInvokePane(FormToolkit toolkit , IToolBarChangeListener toolBarChangeListener) {
       soapMessageInvokePane = new SOAPMessageInvokePane(toolBarChangeListener,this,SWT.NONE,toolkit ,viewSite); 
       parameterInvokePane = new ProcessParameterInvokePane(toolBarChangeListener,this,SWT.NONE,toolkit , viewSite);
       stackLayout.topControl = parameterInvokePane;
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
    	
        if(event.getSelection().isEmpty()  || ! (event.getSelection() instanceof StructuredSelection)) {
        	stackLayout.topControl = parameterInvokePane;
            parameterInvokePane.clear();
            soapMessageInvokePane.clear();
            toolBarChangeListener.updateToolBar(false);
            layout();
            return;
        }
       StructuredSelection selectItem = (StructuredSelection) event.getSelection(); 
       startActivity = (EObject) selectItem.getFirstElement();
       
       try {
           EObject process = ProcessUtil.getProcess(startActivity);
           if(process != null) {
               //startActivity = Xpdl2ModelUtil.getFirstActivity(template);
               stackLayout.topControl = ProcessUtil.isWebServiceImplementationActivity(startActivity)? soapMessageInvokePane : parameterInvokePane;
               ((AbstractInvokePane)stackLayout.topControl).setStartActivity(startActivity);
               ((ISelectionChangedListener)stackLayout.topControl).selectionChanged(event);
               
               //soapMessageInvokePane.setStartActivity(startActivity);
               //parameterInvokePane.setStartActivity(startActivity);
               //soapMessageInvokePane.selectionChanged(event);
               //parameterInvokePane.selectionChanged(event);
               
               toolBarChangeListener.updateToolBar(false);
           }else {
               MessageDialog.openWarning(this.getShell(), Messages.getString("ProcessInvokePane.messageDialog.title"), Messages.getString("ProcessInvokePane.messageDialog.message")); //$NON-NLS-1$ //$NON-NLS-2$
               stackLayout.topControl = parameterInvokePane;
               parameterInvokePane.clear();
               soapMessageInvokePane.clear();
               toolBarChangeListener.updateToolBar(false);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           this.layout();
       }
    }

    public EObject getStartActivity() {
        return startActivity;
    }
    
    @Override
    public void selectionChanged(BxDebugTarget event) {
        this.target = event;
        soapMessageInvokePane.setTarget(this.target);
        parameterInvokePane.setTarget(this.target);
    }

    public List<AbstractInvokeActionProxy> getInvokeActions(){
        return ((AbstractInvokePane)stackLayout.topControl).getInvokeActions();
    }

    public TestpointEditViewer getInputEditViewer() {
        return parameterInvokePane.getInputEditViewer();
    }

    public String getRequestSoapMessage() {
        return soapMessageInvokePane.getRequestSoapMessage();
    }
    
}
