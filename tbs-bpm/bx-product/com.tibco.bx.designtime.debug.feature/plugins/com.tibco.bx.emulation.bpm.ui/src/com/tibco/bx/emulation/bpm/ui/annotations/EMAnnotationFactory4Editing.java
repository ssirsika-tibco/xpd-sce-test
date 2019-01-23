package com.tibco.bx.emulation.bpm.ui.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.emulation.bpm.ui.editor.ProcessNodeEditorInput;
import com.tibco.bx.emulation.bpm.ui.editor.TestpointsEditor;
import com.tibco.bx.emulation.bpm.ui.figures.ActivityPointAnnotationFigure;
import com.tibco.bx.emulation.bpm.ui.figures.InputAnnotationFigure;
import com.tibco.bx.emulation.bpm.ui.figures.IntermediateInputAnnotationFigure;
import com.tibco.bx.emulation.bpm.ui.figures.OutputAnnotationFigure;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.provider.EmulationItemProviderAdapterFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.UniqueIdElement;
public final class EMAnnotationFactory4Editing extends AbstractEmulationAnnotationFactory implements INotifyChangedListener{
	private Map<AnnotationListener,ProcessNode> listenerMap = new HashMap<AnnotationListener, ProcessNode>();
	private IPartListener partListener = new IPartListener(){
		public void partActivated(IWorkbenchPart part) {
			if(part instanceof TestpointsEditor){
				ProcessNodeEditorInput input = (ProcessNodeEditorInput)((TestpointsEditor)part).getEditorInput();
		        ProcessNode processNode = input.getProcessNode();
		        GraphicalViewer viewer = (GraphicalViewer)((TestpointsEditor)part).getAdapter(GraphicalViewer.class);
		        for(AnnotationListener listener: listenerSet){
					if(viewer == listener.getHostEditPart().getViewer()){
						listenerMap.put(listener, processNode);
					}
				}
				updateAnnotations(processNode);
			}
		}
		public void partBroughtToTop(IWorkbenchPart part) {
		}
		public void partClosed(IWorkbenchPart part) {
			if(part instanceof TestpointsEditor){
				ProcessNodeEditorInput input = (ProcessNodeEditorInput)((TestpointsEditor)part).getEditorInput();
		        ProcessNode processNode = input.getProcessNode();
		        Collection collection = new ArrayList();
		        collection.add(EmulationPackage.eINSTANCE);
		        collection.add(IEditingDomainItemProvider.class);
		        ComposedAdapterFactory.Descriptor descriptor = EMFEditPlugin.getComposedAdapterFactoryDescriptorRegistry().getDescriptor(collection);
		        ItemProviderAdapter adapter = (ItemProviderAdapter)descriptor.createAdapterFactory().adapt(processNode, processNode.eClass().getEPackage());
				adapter.removeListener(EMAnnotationFactory4Editing.this);
				//unregister listener for AssertionChanged event
				ItemProviderAdapter assertionAdaptor =(ItemProviderAdapter)((EmulationItemProviderAdapterFactory)adapter.getAdapterFactory()).createAssertionAdapter();
				assertionAdaptor.removeListener(EMAnnotationFactory4Editing.this);
				
			}
		}
		public void partDeactivated(IWorkbenchPart arg0) {
		}
		/*
		 * to create AnnotationFigure after editor opened(non-Javadoc)
		 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
		 */
		public void partOpened(IWorkbenchPart part) {
			if(part instanceof TestpointsEditor){
				ProcessNodeEditorInput input = (ProcessNodeEditorInput)((TestpointsEditor)part).getEditorInput();
		        ProcessNode processNode = input.getProcessNode();
		        GraphicalViewer viewer = (GraphicalViewer)((TestpointsEditor)part).getAdapter(GraphicalViewer.class);
		        Collection collection = new ArrayList();
		        collection.add(EmulationPackage.eINSTANCE);
		        collection.add(IEditingDomainItemProvider.class);
		        
		        ComposedAdapterFactory.Descriptor descriptor = EMFEditPlugin.getComposedAdapterFactoryDescriptorRegistry().getDescriptor(collection);
		        ItemProviderAdapter adapter = (ItemProviderAdapter)descriptor.createAdapterFactory().adapt(processNode, EmulationPackage.eINSTANCE);
				adapter.addListener(EMAnnotationFactory4Editing.this);
				for(AnnotationListener listener: listenerSet){
					if(viewer == listener.getHostEditPart().getViewer()){
						listenerMap.put(listener, processNode);
					}
				}
				updateAnnotations(processNode);
				
				//register listener for AssertionChanged event
				ItemProviderAdapter assertionAdaptor =(ItemProviderAdapter)((EmulationItemProviderAdapterFactory)adapter.getAdapterFactory()).createAssertionAdapter();
				assertionAdaptor.addListener(EMAnnotationFactory4Editing.this);
				
			}
		}
	};
	
	public EMAnnotationFactory4Editing() {
		super();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(partListener);
	}

	@Override
	public void disposeFactory() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().removePartListener(partListener);
		super.disposeFactory();
	}
	
	@Override
	public void unregisterListener(AnnotationListener listener) {
		if( listenerMap !=null ){
			if(!listenerMap.isEmpty()){
				listenerMap.remove(listener);
			}else{
				listenerMap= null;
			}
		}
		super.unregisterListener(listener);
	}

	public IFigure createFigure(AnnotationListener listener, Object model, IFigure parent) {
		ProcessNode processNode = listenerMap.get(listener);
		if(processNode != null && model instanceof UniqueIdElement){
			String xpdlId = ((UniqueIdElement)model).getId();
			List<IntermediateInput> list = null;
			if (model instanceof Activity
					&& (EmulationUtil.getTestpointById(processNode,xpdlId) != null)) {
				return new ActivityPointAnnotationFigure(parent);
			}else if (model instanceof Activity && processNode.getInput() != null 
					&& processNode.getInput().getId().equals(xpdlId)) {
				return new InputAnnotationFigure(parent);
			}else if (model instanceof Activity && processNode.getOutput() != null 
					&& processNode.getOutput().getId().equals(xpdlId)) {
				return new OutputAnnotationFigure(parent);
			}else if(model instanceof Activity && !(EmulationUtil.getMultiInputs(processNode, xpdlId).isEmpty())){
				return new InputAnnotationFigure(parent);
			}else if (model instanceof Activity && !(list=EmulationUtil.getIntermediateInputs(processNode, xpdlId)).isEmpty()) {
				return new IntermediateInputAnnotationFigure(parent, list.size());
			}else if(model instanceof Transition){
				xpdlId = ((Transition)model).getId();
				Assertion assertion = EmulationUtil.getAssertionById(processNode, xpdlId);
				if( assertion != null){
					if(parent instanceof SequenceFlowFigure){
						parent.setForegroundColor(assertion.isAccessible()? greenColor : orangeColor);
						/*AssertionPointAnnotationFigure figure = new AssertionPointAnnotationFigure(parent);
						return figure;*/
					}
				}else{
					parent.setForegroundColor(getNormalColorByPart(listener.getHostEditPart()));
				}
			}
		}
		return null;
	}
	
	public void notifyChanged(Notification notification) {
		Object object = notification.getNotifier();
		if(object instanceof ProcessNode){
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(EmulationPackage.class);
			switch(type){
				case Notification.ADD:
				case Notification.REMOVE:
				case Notification.SET:
				switch(featureId) {
					case EmulationPackage.PROCESS_NODE__TESTPOINTS:
						updateAnnotations((ProcessNode)object);
						break;
					case EmulationPackage.PROCESS_NODE__ASSERTIONS:
						updateAnnotations((ProcessNode)object);
						break;
					case EmulationPackage.PROCESS_NODE__INPUT:
						updateAnnotations((ProcessNode)object);
						break;
					case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
						updateAnnotations((ProcessNode)object);
						break;
					case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
						updateAnnotations((ProcessNode)object);
						break;
					}
			}
		}else if(object instanceof Assertion){
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(EmulationPackage.class);
			switch(type){
				case Notification.ADD:
				case Notification.REMOVE:
				case Notification.SET:
				switch(featureId) {
					case EmulationPackage.ASSERTION__ACCESSIBLE:
						updateAnnotations(((Assertion)object).getProcessNode());
						break;
					}
			}
		}
	}
		
	private void updateAnnotations(ProcessNode processNode){
		if(listenerMap !=null){
		for(AnnotationListener listener: listenerMap.keySet()){
			if(listenerMap.get(listener) == processNode){
				modifyAnnotation(listener);
			}
		}
		}
	}
}
