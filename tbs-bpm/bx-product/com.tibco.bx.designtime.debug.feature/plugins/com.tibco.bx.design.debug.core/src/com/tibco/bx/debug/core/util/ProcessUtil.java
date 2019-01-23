package com.tibco.bx.debug.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.runtime.IProcessModelHandler;
import com.tibco.bx.debug.core.ws.finder.WSProperties;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Process;

public class ProcessUtil {

	private static final String EXTENSION_PROCESS_MODEL_HANDLER_ID = DebugCoreActivator.PLUGIN_ID + ".processModelHandlers"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String FILE_EXTENSION = "fileExtension"; //$NON-NLS-1$
	private static Map<String, IProcessModelHandler> modelTypeMap = null;// modelType:handler
	private static Map<IProcessModelHandler, String> fileExtensionMap = null;// handler:fileExtension
	private static Map<Class, IProcessModelHandler> classMap = new HashMap<Class, IProcessModelHandler>();
	private static final String BPM_MODEL_TYPE = "bpm"; //$NON-NLS-1$
	private static final String PAGEFLOW_MODEL_TYPE = "Pageflow"; //$NON-NLS-1$

	static {
		fileExtensionMap = new HashMap<IProcessModelHandler, String>();
		modelTypeMap = new HashMap<String, IProcessModelHandler>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_PROCESS_MODEL_HANDLER_ID);
		for (IConfigurationElement element : elements) {
			try {
				String modelType = element.getAttribute(ID);
				String extension = element.getAttribute(FILE_EXTENSION);
				IProcessModelHandler handler = (IProcessModelHandler) element.createExecutableExtension(CLASS);
				modelTypeMap.put(modelType, handler);
				fileExtensionMap.put(handler, extension);
				classMap.put(handler.getProcessClass(), handler);
			} catch (Exception e) {
				DebugCoreActivator.log(e);
				continue;
			}
		}
	}

	public static EObject getProcess(EObject activity) {
		while (true) {
			EObject parent = activity.eContainer();
			if (parent != null) {
				if (isProcess(parent)) {
					return parent;
				} else {
					return getProcess(parent);
				}
			} else {
				return null;
			}
		}
	}

	public static EObject[] getProcesses(IFile file) {
		String ext = file.getFileExtension();
		if (ext != null) {
			for (Iterator iterator = fileExtensionMap.keySet().iterator(); iterator.hasNext();) {
				IProcessModelHandler handler = (IProcessModelHandler) iterator.next();
				if (ext.equals(fileExtensionMap.get(handler)))
					return handler.getProcesses(file);
			}
		}
		return new EObject[0];
	}

	public static boolean isProcess(EObject object) {
		return classMap.keySet().contains(object.eClass().getInstanceClass());
	}

	public static boolean isProcessFile(IResource resource) {
		return fileExtensionMap.containsValue(resource.getFileExtension());
	}

	/**
	 * @param element
	 *            is Process, Activity, Link
	 * 
	 *            if select Pool or Lane, element will be null
	 */
	public static String getElementId(EObject element) {
		if (element != null) {
			if (isProcess(element)) {
				return classMap.get(element.eClass().getInstanceClass()).getElementId(element);
			} else if (getProcess(element) != null) {
				return classMap.get(getProcess(element).eClass().getInstanceClass()).getElementId(element);
			}
		}
		return null;
	}

	/**
	 * @param element
	 *            is Process, Activity, Link
	 */
	public static String getElementName(EObject element) {
		if (isProcess(element)) {
			return classMap.get(element.eClass().getInstanceClass()).getElementId(element);
		}
		return classMap.get(getProcess(element).eClass().getInstanceClass()).getElementName(element);
	}

	public static EObject getProcess(String processId, String modelType) {
		return modelTypeMap.get(modelType).getProcess(processId);
	}

	public static EObject getActivity(String processId, String activityId, String modelType) {
		EObject process = getProcess(processId, modelType);
		return modelTypeMap.get(modelType).getActivity(process, activityId);
	}

	public static EObject getActivity(EObject process, String activityId) {
		return classMap.get(process.eClass().getInstanceClass()).getActivity(process, activityId);
	}

	public static EObject getLink(String processId, String linkId, String modelType) {
		EObject process = getProcess(processId, modelType);
		return classMap.get(process.eClass().getInstanceClass()).getLink(process, linkId);
	}

	public static EObject getLink(EObject process, String linkId) {
		return classMap.get(process.eClass().getInstanceClass()).getLink(process, linkId);
	}

	public static boolean isIntermediateEvent(EObject activity) {
		return classMap.get(getProcess(activity).eClass().getInstanceClass()).isIntermediateEvent(activity);
	}

	public static boolean isWebServiceImplementationActivity(EObject startActivity) {
		return classMap.get(getProcess(startActivity).eClass().getInstanceClass()).isWebServiceImplementationActivity(startActivity);
	}

	public static WSProperties getWSProperties(EObject startActivity) {
		return classMap.get(getProcess(startActivity).eClass().getInstanceClass()).getWSProperties(startActivity);
	}

	public static String getWebServiceUri(EObject startActivity) {
		return classMap.get(getProcess(startActivity).eClass().getInstanceClass()).getWebServiceUri(startActivity);
	}

	public static EObject[] getCanStartActivities(EObject currentProcess) {
		return classMap.get(currentProcess.eClass().getInstanceClass()).getCanStartActivities(currentProcess);
	}

	public static String getDisplayName(EObject element) {
		if (element == null) {
			return "";
		}
		Class elementClass = element.eClass().getInstanceClass();
		if (classMap.containsKey(elementClass)) {// element is a process
			return classMap.get(elementClass).getDisplayName(element);
		} else {
			return classMap.get(getProcess(element).eClass().getInstanceClass()).getDisplayName(element);
		}
	}

	public static List<EObject> getAllParms(EObject process) {
		return classMap.get(process.eClass().getInstanceClass()).getAllVariables(process);
	}

	public static String getVariableTypeName(EObject variable) {
		return classMap.get(getProcess(variable).eClass().getInstanceClass()).getVariableTypeName(variable);
	}

	public static String getVariableName(EObject variable) {
		return classMap.get(getProcess(variable).eClass().getInstanceClass()).getVariableName(variable);
	}

	public static boolean isArrayVariable(EObject variable) {
		return classMap.get(getProcess(variable).eClass().getInstanceClass()).isArrayVariable(variable);
	}

	public static boolean isEndActivity(EObject activity) {
		return classMap.get(getProcess(activity).eClass().getInstanceClass()).isEndActivity(activity);
	}

	public static String getModelType(EObject element) {
		if (isProcess(element)) {
			return isPageflow((Process) element) ? PAGEFLOW_MODEL_TYPE : BPM_MODEL_TYPE;
		}
		return null;
	}

	public static boolean isPageflow(EObject element) {
		if (isProcess(element)) {
			FeatureMap map = ((Process) element).getOtherAttributes();
			int size = map.size();
			for (int i = 0; i < size; i++) {
				EStructuralFeature feature = map.get(i).getEStructuralFeature();
				if (feature.getName().equals("xpdModelType")) {//$NON-NLS-1$
					return "PageFlow".equals(((XpdModelType) map.getValue(i)).getLiteral());//$NON-NLS-1$
				}
			}
		}
		return false;
	}
	
	public static boolean isPublishedAsBusinessService(EObject element){
		if (isProcess(element)) {
			FeatureMap map = ((Process) element).getOtherAttributes();
			int size = map.size();
			for (int i = 0; i < size; i++) {
				EStructuralFeature feature = map.get(i).getEStructuralFeature();
				if (feature.getName().equals("publishAsBusinessService")) {//$NON-NLS-1$
					return ((Boolean) map.getValue(i)).booleanValue();
				}
			}
		}
		return false;
	}
	
	public static List<EObject> filterOutDataField(List<EObject> pList) {
		List<EObject> retList = new ArrayList<EObject>();
		for (EObject eObj : pList) {
			if (!(eObj instanceof DataField)) {
				retList.add(eObj);
			}
		}
		return retList;
	}

}
