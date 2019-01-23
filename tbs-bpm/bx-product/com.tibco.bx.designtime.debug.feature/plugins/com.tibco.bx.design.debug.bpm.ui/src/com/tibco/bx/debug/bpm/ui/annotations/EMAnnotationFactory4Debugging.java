package com.tibco.bx.debug.bpm.ui.annotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.emulation.bpm.ui.annotations.AbstractEmulationAnnotationFactory;
import com.tibco.bx.emulation.bpm.ui.figures.ActivityPointAnnotationFigure;
import com.tibco.bx.emulation.bpm.ui.figures.InputAnnotationFigure;
import com.tibco.bx.emulation.bpm.ui.figures.IntermediateInputAnnotationFigure;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.IEmulationListener;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Transition;
/**
 * @author will
 *
 */
public final class EMAnnotationFactory4Debugging extends AbstractEmulationAnnotationFactory
	implements IEmulationListener, IDebugContextListener{
	private Set<AnnotationListener> validListenerList = new HashSet<AnnotationListener>();
	private List<NamedElement> eList = new ArrayList<NamedElement>();
	private IPartListener partListener = new IPartListener(){
		public void partActivated(IWorkbenchPart part) {
			partOpened(part);
		}
		public void partBroughtToTop(IWorkbenchPart part) {
		}
		public void partClosed(IWorkbenchPart part) {
		}
		public void partDeactivated(IWorkbenchPart arg0) {
		}
		/*
		 * does not add annotations to those figures which is in a TestpointsEditor
		 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
		 */
		public void partOpened(IWorkbenchPart part) {
			if(part instanceof ProcessDiagramEditor){
				updateAnnotations(EmulationCacheManager.getDefault().getCurrentEmulationData(), (ProcessDiagramEditor)part);
			}
		}
	};

	private BxThread currentThread;
	public EMAnnotationFactory4Debugging() {
		super();
		EmulationCacheManager.getDefault().addEmulationListener(this);
		registerListener();
	}

	@Override
	public void disposeFactory() {
		EmulationCacheManager.getDefault().removeEmulationListener(this);
		DebugUITools.getDebugContextManager().getContextService(PlatformUI.getWorkbench().getActiveWorkbenchWindow()).removeDebugContextListener(this);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().removePartListener(partListener);
		super.disposeFactory();
	}
	@Override
	public void unregisterListener(AnnotationListener listener) {
		validListenerList.remove(listener);
		super.unregisterListener(listener);
	}
	
	public IFigure createFigure(AnnotationListener listener, Object model, IFigure parent) {
		if(model instanceof Activity){
				String xpdlId = ((Activity)model).getId();
				List<NamedElement> list = getNamedElement(xpdlId);
				if(list.size() > 0 ){
					return createFigure(parent, list);
				}
		}else if(model instanceof Transition){
			String xpdlId = ((Transition)model).getId();
			List<NamedElement> list = getNamedElement(xpdlId);
			if(!list.isEmpty()){
				Assertion assertion = (Assertion)list.get(0);
				if(parent instanceof SequenceFlowFigure){
					if(currentThread != null){
						/*TODO String message = currentThread.getSuspendMessage();
						if(message != null && message.endsWith("["+assertion.getId()+"]")){ //$NON-NLS-1$ //$NON-NLS-2$
							((SequenceFlowFigure)parent).setForegroundColor(redColor);
							((SequenceFlowFigure)parent).setHighlight(true);
							return null;
						}*/
					}
					((SequenceFlowFigure)parent).setForegroundColor(assertion.isAccessible()? greenColor : orangeColor);
					((SequenceFlowFigure)parent).setHighlight(false);
					return null;
					/*AssertionPointAnnotationFigure figure = new AssertionPointAnnotationFigure(parent);
					return figure;*/
				}
			}else{
				parent.setForegroundColor(getNormalColorByPart(listener.getHostEditPart()));
				((SequenceFlowFigure)parent).setHighlight(false);
			}
	}else{
			parent.setForegroundColor(getNormalColorByPart(listener.getHostEditPart()));
		}
		parent.repaint();
		return null;
	}

	public void debugContextChanged(DebugContextEvent event) {
		if ((event.getFlags() & DebugContextEvent.ACTIVATED) > 0) {
			ISelection selection = event.getContext();
			if (selection instanceof IStructuredSelection) {
				Object source = ((IStructuredSelection)selection).getFirstElement();
				if(source == null && currentThread != null){
					source = currentThread.getDebugTarget();
				}
				EmulationData emulationData = EmulationUIUtil.getEmulationData(source);
				currentThread = EmulationUIUtil.getBxThread(source);
				EmulationCacheManager.getDefault().setCurrentEmulationData(emulationData);
			}
		}
	}

	public void currentEmulationDataChanged(EmulationData newData, EmulationData oldData) {
		if(newData == null)
			updateAnnotations(newData, null);
		else
			updateAnnotations(newData, null);
		
	}

	public void emulationDataChanged(EmulationData emulationData, int status) {
		updateAnnotations(emulationData, null);
	}
	
	private void updateAnnotations(EmulationData emulationData, ProcessDiagramEditor editor){
		if(editor != null){// add listener
			GraphicalViewer viewer = (GraphicalViewer)editor.getAdapter(GraphicalViewer.class);
			for(AnnotationListener listener: listenerSet){
				if(viewer == listener.getHostEditPart().getViewer()){
					validListenerList.add(listener);
				}
			}
		}
		getActivityIds(emulationData);
		for (AnnotationListener listener : validListenerList) {
			modifyAnnotation(listener);
		}	
	}
		
	private void getActivityIds(EmulationData emulationData){
		eList.clear();
		if(emulationData == null) return;
		List<ProcessNode> pList = emulationData.getProcessNodes();
		for (ProcessNode processNode : pList) {
			Input input = processNode.getInput();
			if(input != null)
				eList.add(input);
			List<Testpoint> tList = processNode.getTestpoints();
			for (Testpoint testPoint : tList) {
				eList.add(testPoint);
			}
			List<Assertion> aList = processNode.getAssertions();
			for (Assertion assertion : aList) {
				eList.add(assertion);
			}
			List<IntermediateInput> iList = processNode.getIntermediateInputs();
			for (IntermediateInput intermediateInput : iList) {
				eList.add(intermediateInput);
			}
		}
	}

	private List<NamedElement> getNamedElement(String processId){
		List<NamedElement> list = new ArrayList<NamedElement>();
		for(NamedElement element : eList){
			if(element.getId().equals(processId)){
				list.add(element);
				if(!(element instanceof IntermediateInput)){
					return list;
				}
			}
		}
		return list;
	}
	
	private IFigure createFigure(IFigure parent, List<NamedElement> list){
		if(!list.isEmpty()){
			NamedElement namedElement = list.get(0);
			if(namedElement instanceof Input){
				return new InputAnnotationFigure(parent);
			}else if(namedElement instanceof Testpoint){
				return new ActivityPointAnnotationFigure(parent);
			}else if(namedElement instanceof IntermediateInput){
				return new IntermediateInputAnnotationFigure(parent, list.size());
			}else if(namedElement instanceof MultiInput){
				return new InputAnnotationFigure(parent);
			}
		}
		return null;
	}
	
	private void registerListener(){
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (!display.isDisposed()) {
			display.syncExec(new Runnable() {
				public void run() {
					boolean windowCreated = false;
					while(!windowCreated){
						IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
						windowCreated = (window != null);
						if(windowCreated){
							DebugUITools.getDebugContextManager().getContextService(window).addDebugContextListener(EMAnnotationFactory4Debugging.this);
							window.getPartService().addPartListener(partListener);
						}
					}
				}
			});
		}
	}
}
