package com.tibco.bx.debug.bpm.ui.presentation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.ui.IDebugEditorPresentation;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.core.DebugBPMActivator;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.bpm.ui.figures.AnnotationFigure;
import com.tibco.bx.debug.common.model.ProcessLink;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.presentation.AbstractModelPresentation;
import com.tibco.bx.debug.ui.util.DecoratorUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;
import com.tibco.xpd.processwidget.figures.SequenceFlowFigure;
import com.tibco.xpd.processwidget.parts.BaseConnectionEditPart;
import com.tibco.xpd.processwidget.parts.BaseFlowNodeEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;

public class BxModelPresentation extends AbstractModelPresentation implements IDebugEditorPresentation,
		PropertyChangeListener {

	Map<String, Process> processMap = new HashMap<String, Process>();
	
	public BxModelPresentation() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.
	 * IEditorInput, java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		return ProcessDiagramEditor.EDITOR_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		if (element instanceof BxBreakpoint) {
			return getEditorInputForBreakpoint((BxBreakpoint) element);
		} else if (element instanceof IBxStackFrame) {
			IBxStackFrame bxStackFrame = (IBxStackFrame) element;
			return getEditorInputForStackFrame(bxStackFrame);
		} else if (element instanceof IBxThread) {
			return getEditorInputForBxThread((IBxThread) element);
		}
		return null;
	}

	private Process getProcess(String processId) {
		Process process = processMap.get(processId);
		if (process == null) {
			process = (Process) ProcessUtil.getProcess(processId, DebugBPMActivator.BPM_MODEL_TYPE);
			processMap.put(processId, process);
		}
		return process;
	}

	private IEditorInput getEditorInputForBxThread(IBxThread thread) {
		Process process = getProcess(thread.getProcessElement().getIndex());
		if (process == null)
			return null;
		WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(process);
		return workingCopy != null ? new ProcessEditorInput(workingCopy, process) : null;
	}

	private IEditorInput getEditorInputForStackFrame(IBxStackFrame element) {
		IThread thread = element.getThread();
		if (thread != null) {
			return getEditorInputForBxThread((IBxThread) thread);
		}
		return null;
	}

	private IEditorInput getEditorInputForBreakpoint(BxBreakpoint bxBreakpoint) {
		String location = bxBreakpoint.getMarker().getAttribute(IMarker.LOCATION, "");//$NON-NLS-1$

		IResource xpdlFile = bxBreakpoint.getMarker().getResource();
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
		if (workingCopy != null && workingCopy.getRootElement() != null) {
			EObject modelRoot = workingCopy.getRootElement();
			EObject object = modelRoot.eResource().getEObject(location);
			if (object instanceof Activity) {
				Process process = ((Activity) object).getProcess();
				return new ProcessEditorInput(workingCopy, process);
			}
		}

		return null;
	}

	@Override
	public Image getImage(Object element) {
		Image image = null;
		if (element instanceof IBxDebugTarget) {
			image = DebugUIActivator.getRegisteredImage(((IBxDebugTarget) element).getModelType());
		} else if (element instanceof IBxThread) {
			image = BxModelUIUtil.getImageForThread((IBxThread) element);
		} else if (element instanceof IBxStackFrame) {
			image = BxModelUIUtil.getImageForStackframe((IBxStackFrame) element);
			image = DecoratorUtil.addCurrentDecorator((IBxStackFrame) element, image);
		} else if (element instanceof BxBreakpoint) {
			image = BxModelUIUtil.getImageForBreakpoint((BxBreakpoint) element);
		} else {
			image = super.getImage(element);
		}
		return image;
	}


	public boolean addAnnotations(final IEditorPart editorPart, final IStackFrame frame) {
		getStandardDisplay().syncExec(new Runnable() {
			public void run() {
				synchronized (editorPart) {

					ProcessWidget widget = (ProcessWidget) editorPart.getAdapter(ProcessWidget.class);
					IBxStackFrame bxStackFrame = null;
					if (frame instanceof IBxThread) {
						bxStackFrame = ((BxThread) frame).getCurrent();
					} else if (frame instanceof IBxStackFrame) {
						bxStackFrame = (IBxStackFrame) frame;
					}
					if (bxStackFrame != null) {
						ProcessVisibleNode node = (ProcessVisibleNode) bxStackFrame.getProcessElement();
						if (!node.getType().equals("track")) { //$NON-NLS-1$
							List<Activity> list = getActivities(getProcess(((IBxThread) bxStackFrame.getThread())
									.getProcessElement().getIndex()));

							// clear highlight path
							resetSequenceFlow(widget);

							// high light current path
							highLightActivity(widget, bxStackFrame, list);

							// update annotations under the parent track
							EObject selectedTask = updateAnnotations(widget, bxStackFrame);
							if (editorPart instanceof IGotoEObject) {
								((IGotoEObject) editorPart).gotoEObject(true, new EObject[] { selectedTask });
							}
						} else {// track
							resetSequenceFlow(widget);
							updateAnnotations(widget, bxStackFrame);
							highLightTrack(widget, bxStackFrame);
						}
					}
				}
			}
		});
		return true;
	}

	private EObject updateAnnotations(ProcessWidget widget, IBxStackFrame selectedStackFrame) {
		IBxThread thread = (IBxThread) ((IBxStackFrame) selectedStackFrame).getThread();
		Process process = getProcess(thread.getProcessElement().getIndex());
		if (process == null)
			return null;
		EObject selectedTask = null;
		List<Activity> activities = getActivities(process);
		IBxStackFrame currentStackFrame = null;
		AbstractGraphicalEditPart currentEditPart = null;
		for (Activity activity : activities) {
			AbstractGraphicalEditPart editPart = getEditPartForTask(widget, activity);
			boolean found = false;
			for (IBxStackFrame sf : thread.getAllStackFrames()) {
				String activityId = sf.getProcessElement().getIndex();
				if (activityId.equals(activity.getId())) {
					if (selectedTask == null && sf == selectedStackFrame)
						selectedTask = activity;
					if (sf.getBreakWhen() != null) {
						currentStackFrame = sf;
						currentEditPart = editPart;
					} else {
						setAnnotation(editPart, sf);
					}
					found = true;
				}
			}
			if (found) {
				continue;
			}
			resetAnnotation(editPart);
		}
		if (currentStackFrame != null)
			setAnnotation(currentEditPart, currentStackFrame);
		return selectedTask;
	}

	private List<Activity> getActivities(Process process) {
		List<Activity> result = new ArrayList<Activity>();
		List<Activity> activities = process.getActivities();
		result.addAll(activities);
		for (Activity a : activities) {
			BlockActivity blockActivity = a.getBlockActivity();
			if (blockActivity != null) {
				getActivitiesFrom(process, blockActivity, result);
			}
		}
		return result;
	}

	private void getActivitiesFrom(Process process, BlockActivity a, List<Activity> results) {
		String setId = a.getActivitySetId();
		ActivitySet activitySet = process.getActivitySet(setId);
		List<Activity> activities = activitySet.getActivities();
		results.addAll(activities);
		for (Activity act : activities) {
			BlockActivity blockActivity = act.getBlockActivity();
			if (blockActivity != null) {
				getActivitiesFrom(process, blockActivity, results);
			}
		}
	}

	private boolean isActivityRunning(IBxStackFrame bxStackFrame) {
		ProcessVisibleNode node = (ProcessVisibleNode) bxStackFrame.getProcessElement();
		if (ProcessVisibleNode.State.WAITING.equals(node.getState())) {
			return true;
		} else if (ProcessVisibleNode.State.STARTING.equals(node.getState())) {
			return true;
		} else {
			return false;
		}
	}

	private AbstractGraphicalEditPart getEditPartForTask(ProcessWidget widget, EObject task) {
		if (task != null) {
			Map editPartRegistry = widget.getGraphicalViewer().getEditPartRegistry();
			return (AbstractGraphicalEditPart) editPartRegistry.get(task);
		}
		return null;
	}

	private void setAnnotation(AbstractGraphicalEditPart editPart, IBxStackFrame stackFrame) {
		IFigure base = editPart.getFigure();
		IFigure annotationContainer = LayerManager.Helper.find(editPart).getLayer(
				ProcessWidgetConstants.EXTENSIONS_LAYER);
		List children = annotationContainer.getChildren();
		boolean isSuspended = stackFrame.getThread().isSuspended();
		boolean isCurrent = isActivityRunning(stackFrame);
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			IFigure element = (IFigure) iter.next();
			if (element instanceof AnnotationFigure) {
				AnnotationFigure annotation = (AnnotationFigure) element;
				if (annotation.getBase() == base) {
					synchronized (annotation) {
						BreakWhen breakWhen = isSuspended ? stackFrame.getBreakWhen() : null;
						annotation.setCurrentStatus(breakWhen);
						annotation.calculateBounds();
					}
					synchronized (base) {
						base.setBackgroundColor(isCurrent ? getGreenColor() : getNormalColorByPart(editPart));
					}

				}
			}
		}
	}

	private void highLightTrack(ProcessWidget widget, IBxStackFrame trackFrame) {
		List<ProcessLink> links = getLinks(trackFrame);
		Map registry = widget.getGraphicalViewer().getEditPartRegistry();
		Iterator iterator = registry.values().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof SequenceFlowEditPart) {
				SequenceFlowEditPart editPart = (SequenceFlowEditPart) next;
				Transition model = (Transition) editPart.getModel();
				String xpdlId = model.getId();
				for (ProcessLink l : links) {
					if (model.getFrom().equals(l.getFrom().getIndex()) && model.getTo().equals(l.getTo().getIndex())) {
						SequenceFlowFigure figure = (SequenceFlowFigure) editPart.getFigure();
						synchronized (figure) {
							figure.setForegroundColor(getGreenColor());
							figure.setHighlight(true);
						}
						break;
					}
				}
			}
		}
	}

	private List<ProcessLink> getLinks(IBxStackFrame trackFrame) {
		List<ProcessLink> ret = new ArrayList<ProcessLink>();
		for (IStackFrame child : ((IBxThread) trackFrame.getThread()).getAllStackFrames()) {
			if (child instanceof IBxStackFrame) {
				ProcessVisibleNode node = (ProcessVisibleNode) ((IBxStackFrame) child).getProcessElement();
				ProcessLink[] links = node.getLinks();
				for (int i = 0; i < links.length; i++) {
					if (trackFrame.getInstanceId().startsWith(links[i].getInstanceId())) {
						ret.add(links[i]);
					}
				}
			}
		}
		return ret;
	}

	private void highLightActivity(ProcessWidget widget, IBxStackFrame stackFrame, List<Activity> activities) {
		String activityId = stackFrame.getProcessElement().getIndex();
		Iterator<Activity> ai = activities.iterator();
		EObject task = null;
		while (ai.hasNext()) {
			Activity next = ai.next();
			if (activityId.equals(next.getId())) {
				task = next;
				break;
			}
		}
		if (task == null) {
			return;
		}

		AbstractGraphicalEditPart editPart = getEditPartForTask(widget, task);

		List<BaseConnectionEditPart> targetConnections = editPart.getTargetConnections();
		ProcessVisibleNode node = (ProcessVisibleNode) stackFrame.getProcessElement();
		IBxThread thread = (IBxThread) stackFrame.getThread();
		for (ProcessLink link : node.getLinks()) {
			IBxStackFrame from = thread.getStackFrame(link.getFrom().getInstanceId());
			highLightActivity(widget, from, activities);
			String linkId = link.getIndex();

			for (BaseConnectionEditPart connection : targetConnections) {
				Object object = connection.getModel();
				if (object instanceof Transition) {
					Transition model = (Transition) object;
					SequenceFlowFigure sequenceFlowFigure = (SequenceFlowFigure) connection.getFigure();
					if (model.getId().equals(linkId)) {
						synchronized (sequenceFlowFigure) {
							sequenceFlowFigure.setForegroundColor(getGreenColor());
							sequenceFlowFigure.setHighlight(true);
						}
					}
				}

			}
		}
		return;
	}

	private void resetSequenceFlow(ProcessWidget widget) {
		Map registry = widget.getGraphicalViewer().getEditPartRegistry();
		Iterator iterator = registry.values().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof SequenceFlowEditPart) {
				SequenceFlowEditPart editPart = (SequenceFlowEditPart) next;
				SequenceFlowFigure figure = (SequenceFlowFigure) editPart.getFigure();
				synchronized (figure) {
					figure.setForegroundColor(getNormalColorByPart((SequenceFlowEditPart) next));
					figure.setHighlight(false);
				}
			}
		}
		EmulationCacheManager.getDefault().refresh();
	}

	public void removeAnnotations(IEditorPart editorPart, IThread thread) {
		synchronized (editorPart) {
			ProcessWidget widget = (ProcessWidget) editorPart.getAdapter(ProcessWidget.class);
			final Map editPartRegistry = widget.getGraphicalViewer().getEditPartRegistry();
			Runnable runnable = new Runnable() {
				public void run() {
					Iterator iterator = editPartRegistry.keySet().iterator();
					while (iterator.hasNext()) {
						Object element = editPartRegistry.get(iterator.next());
						if (element instanceof BaseFlowNodeEditPart) {
							final AbstractGraphicalEditPart editPart = (AbstractGraphicalEditPart) element;

							resetAnnotation(editPart);
						} else if (element instanceof SequenceFlowEditPart) {
							SequenceFlowEditPart editPart = (SequenceFlowEditPart) element;
							SequenceFlowFigure figure = (SequenceFlowFigure) editPart.getFigure();
							synchronized (figure) {
								figure.setForegroundColor(getNormalColorByPart((SequenceFlowEditPart) element));
								figure.setHighlight(false);
							}
						}
					}
					EmulationCacheManager.getDefault().refresh();
				}
			};
			getStandardDisplay().syncExec(runnable);//
			return;
		}
	}

	private void resetAnnotation(AbstractGraphicalEditPart editPart) {

		IFigure annotationContainer = LayerManager.Helper.find(editPart).getLayer(
				ProcessWidgetConstants.EXTENSIONS_LAYER);
		List children = annotationContainer.getChildren();
		IFigure base = editPart.getFigure();
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			IFigure element = (IFigure) iter.next();
			if (element instanceof AnnotationFigure && ((AnnotationFigure) element).getBase() == base) {
				AnnotationFigure annotation = (AnnotationFigure) element;
				synchronized (annotation) {
					annotation.setCurrentStatus(null);
					annotation.calculateBounds();
				}
				IFigure figure = annotation.getBase();
				synchronized (figure) {
					figure.setBackgroundColor(getNormalColorByPart(editPart));
				}
				return;
			}
		}
	}

	public static IBreakpoint findBreakPoint(String taskId) {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for (IBreakpoint breakpoint : breakpoints) {
			try {
				String location = (String) breakpoint.getMarker().getAttribute(IMarker.LOCATION);
				if (taskId.equals(location))
					return breakpoint;
			} catch (CoreException e) {
				DebugBPMUIActivator.log(e);
			}
		}
		return null;
	}

	private Color getNormalColorByPart(EditPart part) {
		if (part instanceof TaskEditPart) {
			TaskEditPart taskEditPart = (TaskEditPart) part;
			TaskAdapter adapter = taskEditPart.getActivityAdapter();
			return ProcessWidgetColors.getInstance().getGraphicalNodeColor(adapter,
					taskEditPart.getFillColorIDForPartType()).getColor();
		} else if (part instanceof EventEditPart) {
			EventEditPart eventEditPart = (EventEditPart) part;
			EventAdapter adapter = eventEditPart.getEventAdapter();
			return ProcessWidgetColors.getInstance().getGraphicalNodeColor(adapter,
					eventEditPart.getFillColorIDForPartType()).getColor();
		} else if (part instanceof GatewayEditPart) {
			GatewayEditPart gatewayEditPart = (GatewayEditPart) part;
			GatewayAdapter adapter = gatewayEditPart.getGatewayAdapter();
			return ProcessWidgetColors.getInstance().getGraphicalNodeColor(adapter,
					gatewayEditPart.getFillColorIDForPartType()).getColor();
		} else if (part instanceof SequenceFlowEditPart) {
			SequenceFlowEditPart sequenceFlowEditPart = (SequenceFlowEditPart) part;
			SequenceFlowAdapter adapter = sequenceFlowEditPart.getSequenceFlowAdapter();
			return ProcessWidgetColors.getInstance().getGraphicalNodeColor(adapter,
					sequenceFlowEditPart.getLineColorIDForPartType()).getColor();
		}
		return null;
	}

	// used to store the running line
	private SequenceFlowFigure sequenceFlowFigure = null;
	private Object sign = 0;

	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(DebugCoreActivator.LINK_BACK_P)) {
			return;
		}
		final Object newValue = evt.getNewValue();
		final Object oldValue = evt.getOldValue();
		getStandardDisplay().syncExec(new Runnable() {
			public void run() {
				synchronized (sign) {
					if (sequenceFlowFigure != null) {
						synchronized (sequenceFlowFigure) {
							sequenceFlowFigure.setForegroundColor(getBlueColor());
							sequenceFlowFigure.setHighlight(false);
						}
					}
					if (newValue == null || oldValue == null) {
						return;
					}

					Process process = getProcess(((IBxThread) ((IBxStackFrame) newValue).getThread())
							.getProcessElement().getIndex());
					if (process == null) {
						return;
					}
					String toID = ((IBxStackFrame) newValue).getProcessElement().getIndex();
					String fromID = ((IBxStackFrame) oldValue).getProcessElement().getIndex();
					List<Activity> activities = getActivities(process);
					IEditorInput editorInput = getEditorInputForElement(toID, process, activities);
					if (editorInput == null) {
						return;
					}
					IEditorPart editorPart = getEditor(editorInput, ProcessDiagramEditor.EDITOR_ID);
					if (editorPart == null) {
						return;
					}
					ProcessWidget widget = (ProcessWidget) editorPart.getAdapter(ProcessWidget.class);

					EmulationCacheManager.getDefault().refresh();

					highLightLink(activities, widget, fromID, toID);
				}

			}

		});
	}

	private void highLightLink(List<Activity> activities, ProcessWidget widget, String fromID, String toID) {
		Iterator<Activity> ai = activities.iterator();
		EObject task = null;
		while (ai.hasNext()) {
			Activity next = ai.next();
			if (toID.equals(next.getId())) {
				task = next;
				break;
			}
		}
		if (task == null) {
			return;
		}

		AbstractGraphicalEditPart editPart = getEditPartForTask(widget, task);
		List<BaseConnectionEditPart> targetConnections = editPart.getTargetConnections();

		for (BaseConnectionEditPart connection : targetConnections) {
			Object object = connection.getModel();
			if (object instanceof Transition) {
				Transition model = (Transition) object;
				if (model.getFrom().equals(fromID)) {
					widget.getGraphicalViewer().reveal(connection);
					sequenceFlowFigure = (SequenceFlowFigure) connection.getFigure();
					synchronized (sequenceFlowFigure) {
						sequenceFlowFigure.setForegroundColor(getGreenColor());
						sequenceFlowFigure.setHighlight(true);
					}
					break;
				}
			}
		}
	}

	/**
	 * if the opening editor's EditorInput is the input, return it, otherwise
	 * return null;
	 */
	private static IEditorPart getEditor(IEditorInput input, String id) {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null || activeWorkbenchWindow.getActivePage() == null) {
			return null;
		}
		IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
		if (!page.getWorkbenchWindow().getWorkbench().isClosing()) {
			IEditorPart editorPart = page.getActiveEditor();
			if (editorPart != null) {
				IEditorInput editorInput = editorPart.getEditorInput();
				if (input.equals(editorInput)) {
					return editorPart;
				}
			}
		}
		return null;
	}

	private IEditorInput getEditorInputForElement(String xpdlId, Process process, List<Activity> activities) {
		WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(process);
		if (workingCopy != null) {
			for (Activity activity : activities) {
				if (xpdlId.equals(activity.getId())) {
					return new ProcessEditorInput(workingCopy, process);
				}
			}
		}
		return null;
	}
}