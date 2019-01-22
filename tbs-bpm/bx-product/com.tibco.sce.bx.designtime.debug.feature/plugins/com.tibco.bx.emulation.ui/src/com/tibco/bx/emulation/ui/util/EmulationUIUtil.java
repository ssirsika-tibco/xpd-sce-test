package com.tibco.bx.emulation.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.runtime.ITestDelegate;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.bx.emulation.ui.editor.IDiagramEditorHandler;
import com.tibco.bx.emulation.ui.editor.IDiagramEditorInput;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationUIUtil {

	public static boolean canCopy(Collection collection) {
		EmulationElement first = null;
		boolean isProcessNodes = false;
		int inputNum = 0, outputNum = 0;
		List<String> ids = new ArrayList<String>();
		String containerId = null;
		if (collection.isEmpty())
			return false;

		for (Object emulationElement : collection) {
			if (emulationElement instanceof EmulationElement && ((EmulationElement) emulationElement).eContainer() != null) {// does
				if (first == null) {
					first = (EmulationElement) emulationElement;
					if (first instanceof ProcessNode) {
						isProcessNodes = true;
					} else {
						Object container = ((EmulationElement) emulationElement).eContainer();
						if (!(container instanceof NamedElement))
							return false;
						containerId = ((NamedElement) ((EmulationElement) emulationElement).eContainer()).getId();
						if (emulationElement instanceof Input) {
							inputNum++;
						} else if (emulationElement instanceof Output) {
							outputNum++;
						} else if (emulationElement instanceof NamedElement) {
							ids.add(((NamedElement) emulationElement).getId());
						}
					}
				} else if (isProcessNodes && !(emulationElement instanceof ProcessNode) || !isProcessNodes
						&& (emulationElement instanceof ProcessNode)
						|| WorkingCopyUtil.getProjectFor((EmulationElement) emulationElement) != WorkingCopyUtil.getProjectFor(first)) {
					return false;
				} else if (!isProcessNodes && ((NamedElement) ((EmulationElement) emulationElement).eContainer()).getId().equals(containerId)) {
					if (emulationElement instanceof Input) {
						if (++inputNum > 1)
							return false;
					} else if (emulationElement instanceof Output) {
						if (++outputNum > 1)
							return false;
					} else if (emulationElement instanceof NamedElement) {
						if (ids.contains(((NamedElement) emulationElement).getId()))
							return false;
						else
							ids.add(((NamedElement) emulationElement).getId());
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean canDrop(List list, Object target) {
		List<EmulationElement> elements = new ArrayList<EmulationElement>();
		for (Object object : list) {
			if (object instanceof EmulationElement) {
				elements.add((EmulationElement) object);
			} else {
				return false;
			}
		}
		EmulationElement first = elements.get(0);
		if ((first instanceof ProcessNode) && target instanceof IFile
				&& EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(((IFile) target).getFileExtension())
				|| (target instanceof ProcessNode) && first.eContainer() != target) {
			return canCopy(elements);
		}
		return false;
	}

	public static boolean canPaste(Collection<EmulationElement> collection, EmulationElement container) {

		return false;
	}

	public static EmulationData getEmulationData(Object selectedObject) {
		BxDebugTarget debugTarget = null;
		if (selectedObject != null) {
			if (selectedObject instanceof ILaunch) {
				debugTarget = getFirstProcessDebugTarget((ILaunch) selectedObject);
			} else if (selectedObject instanceof IDebugElement) {
				IDebugTarget target = ((IDebugElement) selectedObject).getDebugTarget();
				if (debugTarget instanceof BxDebugTarget && !debugTarget.isDisconnected() && !debugTarget.isTerminated()) {
					debugTarget = (BxDebugTarget) target;
				} else {
					debugTarget = getFirstProcessDebugTarget(target.getLaunch());
				}
			}
		}
		if (debugTarget != null && !debugTarget.isTerminated()) {
			ITestDelegate delegate = ((ITestDelegate) ((BxDebugTarget) debugTarget).getTestDelegate());
			return delegate == null ? null : (EmulationData) delegate.getAdapter(EmulationData.class);
		}
		return null;
	}

	private static ProcessNode getCurrentProcessNode(EObject element) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			IWorkbenchPage page = workbenchWindow.getActivePage();
			if (page != null) {
				IEditorPart editorPart = page.getActiveEditor();
				IEditorInput input = editorPart.getEditorInput();
				if (input instanceof IDiagramEditorInput) {
					return ((IDiagramEditorInput) input).getProcessNode();
				} else {
					EObject process = null;
					if (ProcessUtil.isProcess(element)) {
						process = element;
					} else {
						process = ProcessUtil.getProcess(element);
					}
					return EmulationUtil.getProcessNodeFromCache(ProcessUtil.getElementId(process));
				}
			}
		}
		return null;
	}

	private static BxDebugTarget getFirstProcessDebugTarget(ILaunch launch) {
		IDebugTarget[] targets = launch.getDebugTargets();
		for (IDebugTarget debugTarget : targets) {
			if (debugTarget instanceof BxDebugTarget && !debugTarget.isDisconnected() && !debugTarget.isTerminated()) {
				return (BxDebugTarget) debugTarget;
			}
		}
		return null;
	}

	public static BxThread getBxThread(Object selectedObject) {
		if (selectedObject != null) {
			if (selectedObject instanceof BxThread) {
				return (BxThread) selectedObject;
			} else if (selectedObject instanceof IBxStackFrame) {
				return (BxThread) ((IBxStackFrame) selectedObject).getThread();
			}
		}
		return null;
	}

	private static Map<String, IDiagramEditorHandler> handlerMap = new HashMap<String, IDiagramEditorHandler>();// modelType:IDiagramEditorHandler
	private static Map<String, String> originalEditorIdMap = new HashMap<String, String>();// modelType:originalEditorId
	private static Map<String, String> emulationDiagramEditorIdMap = new HashMap<String, String>();// modelType:emulationDiagramEditorId
	private static final String EXTENSION_EMULATION_DIAGRAM_EDITORS_ID = EmulationUIActivator.PLUGIN_ID + ".emulationDiagramEditors"; //$NON-NLS-1$
	static {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_EMULATION_DIAGRAM_EDITORS_ID);
		for (IConfigurationElement element : elements) {
			try {
				String modelType = element.getAttribute("id");//$NON-NLS-1$
				IDiagramEditorHandler factory = (IDiagramEditorHandler) element.createExecutableExtension("editorHandlerClass");//$NON-NLS-1$
				handlerMap.put(modelType, factory);
				originalEditorIdMap.put(modelType, element.getAttribute("originalEditorId"));//$NON-NLS-1$
				emulationDiagramEditorIdMap.put(modelType, element.getAttribute("emulationDiagramEditorId"));//$NON-NLS-1$
			} catch (Exception e) {
				EmulationCoreActivator.log(e);
				continue;
			}
		}
	}

	public static IEditorPart openEmulationDiagramEditor(NamedElement element) {
		ProcessNode processNode = null;
		if (element instanceof ProcessNode) {
			processNode = (ProcessNode) element;
		} else if (element instanceof Testpoint || element instanceof Assertion || element instanceof Input || element instanceof Output
				|| element instanceof IntermediateInput || element instanceof MultiInput) {
			processNode = (ProcessNode) element.eContainer();
		}
		IDiagramEditorHandler handler = handlerMap.get(processNode.getModelType());
		IEditorInput editorInput = handler.createEmulationDiagramEditorInput(processNode);
		return openEditor(editorInput, emulationDiagramEditorIdMap.get(processNode.getModelType()));

	}

	public static IEditorPart openOriginalEditor(NamedElement element) {
		ProcessNode processNode = null;
		if (element instanceof ProcessNode) {
			processNode = (ProcessNode) element;
		} else {
			processNode = (ProcessNode) element.eContainer();
		}
		IDiagramEditorHandler handler = handlerMap.get(processNode.getModelType());
		IEditorInput editorInput = handler.createOriginalEditorInput(processNode.getId(), processNode.getModelType());
		return openEditor(editorInput, originalEditorIdMap.get(processNode.getModelType()));
	}

	public static IEditorPart openOriginalEditor(String processId, String modelType) {
		IDiagramEditorHandler handler = handlerMap.get(modelType);
		IEditorInput editorInput = handler.createOriginalEditorInput(processId, modelType);
		return openEditor(editorInput, originalEditorIdMap.get(modelType));
	}

	public static void goToEmulationElement(IEditorPart editorPart, NamedElement element) {
		ProcessNode processNode = null;
		EObject gotoElement = null;
		if (element instanceof ProcessNode) {
			processNode = (ProcessNode) element;
			gotoElement = ProcessUtil.getProcess(processNode.getId(), processNode.getModelType());
		} else if (element instanceof Testpoint || element instanceof Input || element instanceof Output) {
			processNode = (ProcessNode) element.eContainer();
			gotoElement = ProcessUtil.getActivity(processNode.getId(), element.getId(), processNode.getModelType());
		} else if (element instanceof Assertion) {
			processNode = (ProcessNode) element.eContainer();
			gotoElement = ProcessUtil.getLink(processNode.getId(), element.getId(), processNode.getModelType());
		} else if (element instanceof IntermediateInput) {
			processNode = (ProcessNode) element.eContainer();
			gotoElement = ProcessUtil.getActivity(processNode.getId(), element.getId(), processNode.getModelType());
		} else if (element instanceof MultiInput) {
			processNode = (ProcessNode) element.eContainer();
			gotoElement = ProcessUtil.getActivity(processNode.getId(), element.getId(), processNode.getModelType());
		}
		IDiagramEditorHandler handler = handlerMap.get(processNode.getModelType());
		handler.goToEObject(editorPart, gotoElement);
	}

	/**
	 * Opens an editor in the workbench and returns the editor that was opened
	 * or <code>null</code> if an error occurred while attempting to open the
	 * editor.
	 */
	private static IEditorPart openEditor(IEditorInput input, String id) {
		IEditorPart[] editor = new IEditorPart[] { null };
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null || activeWorkbenchWindow.getActivePage() == null) {
			return null;
		}
		IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
		if (!page.getWorkbenchWindow().getWorkbench().isClosing()) {
			try {
				editor[0] = page.openEditor(input, id, false, IWorkbenchPage.MATCH_ID | IWorkbenchPage.MATCH_INPUT);
			} catch (PartInitException e) {
				EmulationUIActivator.log(e);
			}
		}
		return editor[0];
	}

	/**
	 * @param element
	 *            could be process, activity, link
	 * @return ProcessNode, Testpoint, Input, Output, IntermediateInput,
	 *         Assertion
	 */
	public static NamedElement getCurrentEmulationElement(EObject element, EClass emulationClass) {
		ProcessNode processNode = getCurrentProcessNode(element);
		if (processNode != null) {
			String elementId = ProcessUtil.getElementId(element);
			if (processNode.getId().equals(elementId)) {
				return processNode;
			} else if (emulationClass == (EmulationPackage.eINSTANCE.getInput())) {
				Input input = processNode.getInput();
				if (input != null && input.getId().equals(elementId)) {
					return input;
				} else {
					return null;
				}
			} else if (emulationClass == (EmulationPackage.eINSTANCE.getOutput())) {
				Output output = processNode.getOutput();
				if (output != null && output.getId().equals(elementId)) {
					return output;
				} else {
					return null;
				}
			} else if (emulationClass == (EmulationPackage.eINSTANCE.getTestpoint())) {
				return EmulationUtil.getTestpointById(processNode, elementId);
			} else if (emulationClass == (EmulationPackage.eINSTANCE.getAssertion())) {
				return EmulationUtil.getAssertionById(processNode, elementId);
			} else if (emulationClass == (EmulationPackage.eINSTANCE.getIntermediateInput())) {
				List<IntermediateInput> list = EmulationUtil.getIntermediateInputs(processNode, elementId);
				if (list.size() > 0) {
					return list.get(0);
				}
			}
		}
		return null;
	}

	/**
	 * @param element
	 *            could be process, activity, link
	 * @return ProcessNode, Testpoint, Input, Output, IntermediateInput,
	 *         Assertion
	 */
	public static NamedElement getCurrentEmulationElement(EObject element) {
		ProcessNode processNode = getCurrentProcessNode(element);
		if (processNode != null) {
			String id = ProcessUtil.getElementId(element);
			if (processNode.getId().equals(id)) {
				return processNode;
			}
			Input input = processNode.getInput();
			if (input != null && input.getId().equals(id)) {
				return input;
			}
			Output output = processNode.getOutput();
			if (output != null && output.getId().equals(id)) {
				return output;
			}
			Testpoint testpoint = EmulationUtil.getTestpointById(processNode, id);
			if (testpoint != null) {
				return testpoint;
			}
			List<IntermediateInput> list = EmulationUtil.getIntermediateInputs(processNode, id);
			if (list.size() > 0) {
				return list.get(0);
			}
			Assertion assertion = EmulationUtil.getAssertionById(processNode, id);
			if (assertion != null) {
				return assertion;
			}
		}
		return null;
	}

}
