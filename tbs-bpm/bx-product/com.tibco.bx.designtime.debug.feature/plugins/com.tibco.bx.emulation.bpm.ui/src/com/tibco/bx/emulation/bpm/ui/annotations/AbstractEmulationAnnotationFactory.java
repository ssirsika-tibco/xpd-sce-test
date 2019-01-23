package com.tibco.bx.emulation.bpm.ui.annotations;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
public abstract class  AbstractEmulationAnnotationFactory implements AnnotationFactory{
	protected Set<AnnotationListener> listenerSet = new HashSet<AnnotationListener>();
	protected Color redColor;
	protected Color orangeColor;
	protected Color greenColor;
	public AbstractEmulationAnnotationFactory() {
		redColor = getColor(200,0,0);
		greenColor = getColor(0,200,0);
		orangeColor = getColor(255,128,0);
	}

	public void disposeFactory() {
		redColor.dispose();
		greenColor.dispose();
		orangeColor.dispose();
	}
	public void registerListener(AnnotationListener listener) {
		Object model = listener.getModelObject();
		if(model instanceof Activity || model instanceof Transition){
			listenerSet.add(listener);
		}
	}

	public void unregisterListener(AnnotationListener listener) {
		listenerSet.remove(listener);
		listener.removeAnnotations();
	}

	protected void modifyAnnotation(final AnnotationListener listener) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (!display.isDisposed()) {
			display.syncExec(new Runnable() {
				public void run() {
					listener.removeAnnotations();
					listener.createAnnotations();
				}
			});
		}
    }
	
	protected Process getProcess(Object model){
		if(model instanceof Activity){
			return ((Activity)model).getProcess();
		}else if(model instanceof Transition){
			return ((Transition)model).getProcess();
		} 
		return null;
	}

	protected Color getColor(int red, int green, int blue) {
		RGB rgb = new RGB(red,green,blue);
		Color result = null;
            if (PlatformUI.isWorkbenchRunning()) {
            	result= new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
            } else {
            	result= new Color(Display.getDefault(), rgb);
            }

        return result;
    }

	
	protected Color getNormalColorByPart(EditPart part){
    	if (part instanceof TaskEditPart) {
    		TaskEditPart taskEditPart=(TaskEditPart) part;
        	TaskAdapter adapter =taskEditPart.getActivityAdapter();
        	return ProcessWidgetColors.getInstance().getGraphicalNodeColor(
                    adapter, taskEditPart.getLineColorIDForPartType()).getColor();
    	}else if (part instanceof EventEditPart) {
    		EventEditPart eventEditPart=(EventEditPart) part;
        	EventAdapter adapter =eventEditPart.getEventAdapter();
        	return ProcessWidgetColors.getInstance().getGraphicalNodeColor(
                    adapter, eventEditPart.getLineColorIDForPartType()).getColor();
    	}else if (part instanceof SequenceFlowEditPart){
    		SequenceFlowEditPart sequenceFlowEditPart=(SequenceFlowEditPart) part;
    		SequenceFlowAdapter adapter =sequenceFlowEditPart.getSequenceFlowAdapter();
        	return ProcessWidgetColors.getInstance().getGraphicalNodeColor(
                    adapter, sequenceFlowEditPart.getLineColorIDForPartType()).getColor();
    	}
    	return null;
	}
}
