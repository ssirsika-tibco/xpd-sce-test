package com.tibco.bx.debug.ui.views.internal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.transport.SOAPMessageBuilder;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.actions.AbstractInvokeActionProxy;
import com.tibco.bx.debug.ui.actions.SoapInvokeActionProxy;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;

public class SOAPMessageInvokePane extends AbstractInvokePane implements 
		ISelectionChangedListener , ISOAPMessageInvokePane{

    private TextViewer inputSoapViewer;
    private IToolBarChangeListener toolBarChangeListener;
    private EObject oldActivity;
    
    private Map<BxDebugTarget, List<SOAPMessageInvokedElement>> targetMap;
    
    private IUndoManager currentManager, oldManager;
    private Map<EObject, IUndoManager> undoManagerMap = new HashMap<EObject, IUndoManager>();
    private boolean isStartChanged = true;

    public SOAPMessageInvokePane(IToolBarChangeListener toolBarChangeListener, Composite parent, int style ,
    							 FormToolkit toolkit, IViewSite viewSite) {
        super(parent, style , viewSite);
        this.toolBarChangeListener = toolBarChangeListener;
        GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
        this.setLayout(gridLayout);
        createInputSoapMessageSection(this, toolkit);
        configTextViewer(inputSoapViewer);
        targetMap = new HashMap<BxDebugTarget, List<SOAPMessageInvokedElement>>();
    }
    
    public String getRequestSoapMessage() {
        return inputSoapViewer.getTextWidget().getText();
    }

    private Section createInputSoapMessageSection(Composite composite, FormToolkit toolkit) {
        Section section = toolkit.createSection(composite, Section.TITLE_BAR);
        section.setLayout(new FillLayout());
        section.setText(Messages.getString("SOAPMessageInvokePane_Rquest_Soap_Message")); //$NON-NLS-1$
        inputSoapViewer = new TextViewer(section, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = ((GridLayout)composite.getLayout()).numColumns;
        section.setLayoutData(gridData);
        section.setClient(inputSoapViewer.getControl());
        toolkit.adapt(inputSoapViewer.getControl() , true, true);
        
        createSectionToolbar(toolkit, section);
        
        return section;
    }
    
    private void createSectionToolbar(FormToolkit toolkit, Section section) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolbar = toolBarManager.createControl(section);
		final Cursor handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolbar.setCursor(handCursor);
		// Cursor needs to be explicitly disposed
		toolbar.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if ((handCursor != null) && (handCursor.isDisposed() == false)) {
					handCursor.dispose();
				}
			}
		});

		toolBarManager.add(new ReCreateAction());
		toolBarManager.update(true);
		section.setTextClient(toolbar);
	}

    private void initRequestSoapMessage(EObject startActivity) throws CoreException {
		if (getTarget() != null && targetMap.get(getTarget()) != null) {
			List<SOAPMessageInvokedElement> eList = targetMap.get(getTarget());
			String soapRequest = ""; //$NON-NLS-1$
			boolean has = false;
			for (SOAPMessageInvokedElement element : eList) {
				if (element.getStartActivity() == startActivity) {
					if (StringUtils.isNotBlank(element.getInoutElement().getSoapMessage())) {
						soapRequest = element.getInoutElement().getSoapMessage();
					} else {
						soapRequest = element.getSoapMessage().toXML();
					}
					inputSoapViewer.getDocument().set(soapRequest);
					setEndpointUrl(element.getUrl());
					has = true;
					break;
				} 
			}
			if (!has) {
				setInitSOAPRequest(startActivity, eList);
			}
		} else {
			List<SOAPMessageInvokedElement> eList = new ArrayList<SOAPMessageInvokedElement>();
			targetMap.put(getTarget(), eList);
			if (getTarget() != null && !getTarget().isDisconnected() && !getTarget().isTerminated()) {
				setInitSOAPRequest(startActivity, eList);
			}
		}
		oldActivity = startActivity;
		setTextViewerUndoManager(inputSoapViewer, currentManager);
	}
    
    private void setInitSOAPRequest(EObject activity, List<SOAPMessageInvokedElement> eList) throws CoreException {
    	try{
    		Input input = EmulationFactory.eINSTANCE.createInput();
    		input.setId(ProcessUtil.getElementId(activity));
    		input.setName(ProcessUtil.getDisplayName(activity));
    		ISOAPMessage soapMessage = SOAPMessageBuilder.instance.buildSoapMessage(activity, (IBxProcessListing) getTarget()
    				.getAdapter(IBxProcessListing.class), getEndpointUrl());
    		String message = soapMessage == null ? "" : soapMessage.toXML();//$NON-NLS-1$
    		input.setSoapMessage(message);
    		setEndpointUrl(WsdlUtil.getOperationEndpoint(soapMessage));
    		inputSoapViewer.getDocument().set(input.getSoapMessage());
    		IInOutElement inOutElement = EmulationUtil.createInputElement(input, activity, getTarget().getModelType());
    		SOAPMessageInvokedElement invokedElement = new SOAPMessageInvokedElement(activity, inOutElement, soapMessage, getEndpointUrl());
    		eList.add(invokedElement);
    	}catch(ParserConfigurationException e){
    		DebugUIActivator.log(e);
            updateStatusMessage(e.getMessage());
    	}
    }
    
    private void configTextViewer(TextViewer textViewer) {
        textViewer.setDocument(new Document());
    }

    @Override
    public void doSelectionChanged(SelectionChangedEvent event) {
       updateStatusMessage(""); //$NON-NLS-1$
       inputChange();
       IStructuredSelection selected = (IStructuredSelection) event.getSelection();
       
       if(! selected.isEmpty()) {
    	   EObject activity = getStartActivity();
    	   updateCurrentManager(activity);
    	   if( activity != null) {
        	   cacheRequestMessage(oldActivity);
               inputSoapViewer.getDocument().set(""); //$NON-NLS-1$
               toolBarChangeListener.updateToolBar(true);
               try {
                   createProxyInvokeAction(activity);
                   initRequestSoapMessage(activity);
               } catch (CoreException e) {
                   updateStatusMessage(e.getMessage());
               }
           }else {
               inputSoapViewer.getDocument().set(""); //$NON-NLS-1$
           }
       }
    }

    public void cacheRequestMessage(EObject activity) {
        if(activity != null && StringUtils.isNotBlank(inputSoapViewer.getTextWidget().getText())) {
        	if(targetMap.get(getTarget()) != null){
        		List<SOAPMessageInvokedElement> eList = targetMap.get(getTarget());
        		for(SOAPMessageInvokedElement element : eList){
        			if(element.getStartActivity() == activity){
        				IInOutElement inOutElement = element.getInoutElement();
        	        	if(inOutElement != null){
        	        		inOutElement.setSoapMessage(inputSoapViewer.getTextWidget().getText());
        	        	}
        			}
        		}
        	}
        }
    }

    private void createProxyInvokeAction(EObject startActivity) {
        if(startActiveInvokes.size() < 1) {
            SoapInvokeActionProxy soapInvoke = new SoapInvokeActionProxy(this, toolBarChangeListener);
            startActiveInvokes.add(soapInvoke);
        }
    }

    @Override
    public void inputChange() {
    	setEndpointUrl(null);
    }

    @Override
    public List<AbstractInvokeActionProxy> getInvokeActions() {
        return startActiveInvokes;
    }

    @Override
    public void clear() {
    	inputSoapViewer.getDocument().set(StringUtils.EMPTY_STRING);
    }

	@Override
	public void clearCache() {
		undoManagerMap.clear();
		targetMap.clear();
//		startActivityList.clear();
	}
	
	private void updateCurrentManager(EObject activity){
		currentManager = undoManagerMap.get(activity);
		if(oldManager != null){
			if(currentManager != oldManager){
				oldManager.disconnect();
				isStartChanged = true;
			}else{
				isStartChanged = false;
			}
		}
		if(currentManager == null){
			currentManager = new TextViewerUndoManager(10);
			undoManagerMap.put(activity, currentManager);
		}
		oldManager = currentManager;
	}
	
	public boolean isStartChanged() {
		return isStartChanged;
	}

	private void setTextViewerUndoManager(final TextViewer viewer, final IUndoManager undoManager){
		if(isStartChanged()){
			undoManager.connect(viewer);
			viewer.setUndoManager(undoManager);
		}
		viewer.getTextWidget().addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {
				if (isUndoPressed(event)) {
					undoManager.undo();
				} else if (isRedoPressed(event)) {
					undoManager.redo();
				}
			}

			@Override
			public void keyReleased(KeyEvent event) {
			}

			// Undo Ctrl+Z or Ctrl+z
			private boolean isUndoPressed(KeyEvent event) {
				return ((event.stateMask & SWT.CTRL) > 0) && (event.keyCode == 'z' || event.keyCode == 'Z');
			}

			// Redo Ctrl+Y or Ctrl+y
			private boolean isRedoPressed(KeyEvent event) {
				return ((event.stateMask & SWT.CTRL) > 0) && (event.keyCode == 'y' || event.keyCode == 'Y');
			}

		});
	}
	
	class ReCreateAction extends Action{
		public ReCreateAction() {
			super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
			setImageDescriptor(DebugUIActivator.getRegisteredImageDescriptor(DebugUIActivator.IMG_RECREATE));
			setToolTipText(Messages.getString("SOAPMessageInvokePane_RecreateReqButton")); //$NON-NLS-1$
		}
		
		@Override
		public void run() {
			inputSoapViewer.getDocument().set(recreateSOAPReqMessage(getStartActivity()));
		}
		
		private String recreateSOAPReqMessage(EObject activity) {
			String req = ""; //$NON-NLS-1$
			try {
				if (activity != null) {
					ISOAPMessage soapMessage = SOAPMessageBuilder.instance.buildSoapMessage(activity, (IBxProcessListing) getTarget().getAdapter(
							IBxProcessListing.class), getEndpointUrl());
					req = soapMessage == null ? "" : soapMessage.toXML(); //$NON-NLS-1$
				}
			} catch (CoreException e) {
//				IStatus error = new Status(IStatus.ERROR, DebugCoreActivator.PLUGIN_ID, Messages.getString("WsdlUtil.status.message")); //$NON-NLS-1$
			} catch (ParserConfigurationException e) {
				DebugUIActivator.log(e);
			}
			return req;
		}
	}
   
}
