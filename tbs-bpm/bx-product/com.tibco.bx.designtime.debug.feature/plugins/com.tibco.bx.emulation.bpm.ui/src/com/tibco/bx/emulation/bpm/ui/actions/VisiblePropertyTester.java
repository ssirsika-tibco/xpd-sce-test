package com.tibco.bx.emulation.bpm.ui.actions;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.bpm.core.util.BPMProcessUtil;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.views.internal.TargetManager;
import com.tibco.bx.emulation.bpm.ui.util.EmulationBPMUIUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;

public class VisiblePropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		Process process = null;
		if (receiver instanceof SequenceFlowEditPart) {
			ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
			Transition transition = (Transition) ((SequenceFlowEditPart) receiver)
					.getModel();
			process = transition.getProcess();
			if (processNode != null) {
				return checkAssertion(processNode, transition, false, property);
			} else {
				processNode = EmulationUtil.getProcessNodeFromCache(process
						.getId());
				boolean inCache = EmulationCacheManager.getDefault()
						.getCurrentEmulationData() != null;
				return checkAssertion(processNode, transition, inCache,
						property);
			}
		} else if (receiver instanceof EventEditPart) {
			ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
			Activity model = (Activity) ((EventEditPart) receiver).getModel();
			process = model.getProcess();
			Event event = ((Activity) model).getEvent();
			if (processNode != null && event instanceof StartEvent) {
				return checkInput(processNode, model, false, property);
			} else if (processNode != null
					&& event instanceof IntermediateEvent
					&& ((IntermediateEvent) event).getTrigger() == TriggerType.MESSAGE_LITERAL) {
				return checkIntermediateInput(processNode, model, false,
						property)
						|| checkTestpoint(processNode, model, false, property);
			} else if (processNode != null && event instanceof EndEvent) {
				// TODO if it's an end activity
			} else {
				processNode = EmulationUtil.getProcessNodeFromCache(process
						.getId());
				boolean inCache = EmulationCacheManager.getDefault()
						.getCurrentEmulationData() != null;
				if (event instanceof StartEvent) {
					return false;// checkInput(processNode, model, inCache,
									// property);
				} else if (event instanceof IntermediateEvent
						&& ((IntermediateEvent) event).getTrigger() == TriggerType.MESSAGE_LITERAL) {
					return checkTestpoint(processNode, model, inCache, property);
				} else if (event instanceof EndEvent) {
					// TODO if it's an end activity
				}
			}
		} else if (receiver instanceof TaskEditPart) {
			ProcessNode processNode = EmulationBPMUIUtil.getProcessNode();
			Activity model = (Activity) ((TaskEditPart) receiver).getModel();
			Implementation implementation = model.getImplementation();
			if (processNode != null) {
				// in edit status
				if (implementation instanceof Task
						&& ((Task) implementation).getTaskReceive() != null) {
					// it's a receive task
					if (model.getIncomingTransitions().size() == 0) { // start
																		// activity
						return checkInput(processNode, model, false, property);
					} else { // intermediate activity
						return checkIntermediateInput(processNode, model,
								false, property)
								|| checkTestpoint(processNode, model, false,
										property);
					}
				} else if (implementation instanceof Task
						&& ((Task) implementation).getTaskSend() != null) {
					if (model.getOutgoingTransitions().size() != 0)// not an end
																	// activity
						return checkTestpoint(processNode, model, false,
								property);
					// TODO if it's an end activity
				} else {
					return checkTestpoint(processNode, model, false, property);
				}
			} else {
				boolean inCache = EmulationCacheManager.getDefault()
						.getCurrentEmulationData() != null;
				processNode = EmulationUtil.getProcessNodeFromCache(model
						.getProcess().getId());
				if (implementation instanceof Task
						&& ((Task) implementation).getTaskReceive() != null) {
					if (model.getIncomingTransitions().size() == 0) { // start
																		// activity
						return checkInput(processNode, model, inCache, property);
					} else { // intermediate activity
						return checkTestpoint(processNode, model, inCache,
								property);
					}
				} else if (implementation instanceof Task
						&& ((Task) implementation).getTaskSend() != null) {
					if (model.getOutgoingTransitions().size() != 0) { // not an
																		// end
																		// activity
						return checkTestpoint(processNode, model, inCache,
								property);
					}
					// TODO if it's an end activity
				} else {
					return checkTestpoint(processNode, model, inCache, property);
				}
			}
		}
		// TODO emulation flag
		/*
		 * else if (pid.equals(EmulationPerspective.ID)){ if(model instanceof
		 * Activity){ //TODO can't add flag on Receive task ExtendedAttribute
		 * attribute = EmulationUtil.getEmulationFlagAttribute((Activity)model);
		 * if("addFlag".equals(property) && attribute == null){ return true;
		 * }else if("removeFlag".equals(property) && attribute != null){ return
		 * true; } } }
		 */
		return false;
	}

	private boolean checkInput(ProcessNode processNode, Activity activity, boolean inCache, String property) {
		Process process = activity.getProcess();
		if ("addInput".equals(property) || "removeInput".equals(property)) { //$NON-NLS-1$ //$NON-NLS-2$
			if (processNode != null && !inCache) {// in editing mode
				List<MultiInput> multiInputs = EmulationUtil.getMultiInputs(processNode, activity.getId());
				Input input = processNode.getInput();
				if (input == null) {
					if (!multiInputs.isEmpty()) {
						MultiInput inputs = multiInputs.get(0);
						if (inputs.getId().equals(activity.getId())) {
							return "removeInput".equals(property); //$NON-NLS-1$
						}
					}
					return "addInput".equals(property); //$NON-NLS-1$
				} else if (input != null) {
					if (input.getId().equals(activity.getId())) {
						return "removeInput".equals(property); //$NON-NLS-1$
					}
					if (!multiInputs.isEmpty()) {
						MultiInput inputs = multiInputs.get(0);
						if (inputs.getId().equals(activity.getId())) {
							return "removeInput".equals(property); //$NON-NLS-1$
						}
					} else {
						return "addInput".equals(property);
					}
				}
			}
		}
		return false;
	}

	private boolean checkIntermediateInput(ProcessNode processNode,
			Activity activity, boolean inCache, String property) {
		// Event trigger type is TriggerType.MESSAGE_LITERAL and in editing mode
		Process process = activity.getProcess();
		if (ProcessUtil.isPageflow(process)) {
			return false;
		} else {
			if (("addIntermediateInput".equals(property) || "removeIntermediateInput".equals(property)) && !inCache) { //$NON-NLS-1$ //$NON-NLS-2$
				Event event = activity.getEvent();
				Implementation implementation = activity.getImplementation();
				if (event != null) {
					TriggerResultMessage resultMessage = ((IntermediateEvent) event)
							.getTriggerResultMessage();
					CatchThrow catchThrow = resultMessage == null ? null
							: resultMessage.getCatchThrow();
					if (CatchThrow.CATCH == catchThrow) {
						List<IntermediateInput> iList = EmulationUtil
								.getIntermediateInputs(processNode, activity
										.getId());
						Testpoint testpoint = EmulationUtil.getTestpointById(
								processNode, activity.getId());
						Testpoint testpoint2 = null;
						Activity responseActivity = BPMProcessUtil
								.getResponseActivity(activity);
						if (responseActivity != null) {
							testpoint2 = EmulationUtil.getTestpointById(
									processNode, responseActivity.getId());
						}
						if ("addIntermediateInput".equals(property) && testpoint == null && testpoint2 == null) { //$NON-NLS-1$
							return true;
						} else if ("removeIntermediateInput".equals(property) && !iList.isEmpty()) { //$NON-NLS-1$
							return true;
						}
					} else {
						// Throw
					}
				} else if (implementation != null) {
					if (implementation instanceof Task
							&& ((Task) implementation).getTaskReceive() != null) {
						if (activity.getIncomingTransitions().size() != 0) {
							List<IntermediateInput> iList = EmulationUtil
									.getIntermediateInputs(processNode,
											activity.getId());
							Testpoint testpoint = EmulationUtil
									.getTestpointById(processNode, activity
											.getId());
							Testpoint testpoint2 = null;
							Activity responseActivity = BPMProcessUtil
									.getResponseActivity(activity);
							if (responseActivity != null) {
								testpoint2 = EmulationUtil.getTestpointById(
										processNode, responseActivity.getId());
							}
							if ("addIntermediateInput".equals(property) && testpoint == null && testpoint2 == null) { //$NON-NLS-1$
								return true;
							} else if ("removeIntermediateInput".equals(property) && !iList.isEmpty()) { //$NON-NLS-1$
								return true;
							}
						}
					}
				}

			}
			return false;
		}
	}

	private boolean checkTestpoint(ProcessNode processNode, Activity activity,
			boolean inCache, String property) {
		Process process = activity.getProcess();
		if (ProcessUtil.isPageflow(process)) {
			return false;
		} else {
			if ("removeTestpoint".equals(property) || "addTestpoint".equals(property)) { //$NON-NLS-1$ //$NON-NLS-2$
				if (processNode != null) {
					Testpoint testPoint = EmulationUtil.getTestpointById(
							processNode, activity.getId());
					List<IntermediateInput> intermediateInputs = EmulationUtil
							.getIntermediateInputs(processNode, activity
									.getId());
					if ("removeTestpoint".equals(property) && testPoint != null) { //$NON-NLS-1$
						return true;
					}
					Activity RequestActivity = BPMProcessUtil
							.getRequestActivity(activity);
					if (RequestActivity == null) {
						return "addTestpoint".equals(property) && testPoint == null && intermediateInputs.size() == 0; //$NON-NLS-1$
					} else {
						List<IntermediateInput> intermediateInputs2 = EmulationUtil
								.getIntermediateInputs(processNode,
										RequestActivity.getId());
						return "addTestpoint".equals(property) && intermediateInputs2.size() == 0; //$NON-NLS-1$
					}

				} else if (processNode == null && inCache
						&& isDebugModel(process)) {
					return "addTestpoint".equals(property); //$NON-NLS-1$
				}
			}
			return false;
		}

	}

	private boolean checkAssertion(ProcessNode processNode,
			Transition transition, boolean inCache, String property) {
		Process process = transition.getProcess();
		if (ProcessUtil.isPageflow(process)) {
			return false;
		} else {
			if (processNode != null) {
				Assertion assertion = EmulationUtil.getAssertionById(
						processNode, transition.getId());
				if ("addAssertion".equals(property) && assertion == null) { //$NON-NLS-1$
					return true;
				} else if ("removeAssertion".equals(property) && assertion != null) { //$NON-NLS-1$
					return true;
				}
			} else if (processNode == null && inCache) {
				return "addAssertion".equals(property); //$NON-NLS-1$
			}
			return false;
		}
	}

	private boolean isDebugModel(Process process) {
		String processId = process.getId();
		List<IBxDebugTarget> targets = TargetManager.getDefault().getDebugTargets();
		try {
			for (IBxDebugTarget target : targets) {
				if(target.getProcessListing() != null){
					ProcessTemplate[] templates = target.getProcessListing().getProcessTemplates();
					for (ProcessTemplate template : templates) {
						if (template.getProcessId().equals(processId)) {
							return !target.isDisconnected() || !target.isTerminated();
						}
					}
				}
				
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}

}