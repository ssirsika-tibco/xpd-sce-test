package com.tibco.bx.emulation.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.common.IActivityElementFactory;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.common.ITestpointElement;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

public class EmulationUtil {

	public static Testpoint getTestpointById(ProcessNode processNode, String id) {
		if (processNode == null || id == null)
			return null;
		EList<Testpoint> Testpoints = processNode.getTestpoints();
		for (Testpoint tp : Testpoints) {
			if (tp.getId().equals(id)) {
				return tp;
			}
		}
		return null;
	}

	public static Assertion getAssertionById(ProcessNode processNode, String id) {
		if (processNode == null || id == null)
			return null;
		EList<Assertion> assertions = processNode.getAssertions();
		for (Assertion tp : assertions) {
			if (tp.getId().equals(id)) {
				return tp;
			}
		}
		return null;
	}

	public static ProcessNode getProcessNodeFromCache(String id) {
		EmulationData emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
		if (emulationData == null || id == null)
			return null;
		EList<ProcessNode> processNodes = emulationData.getProcessNodes();
		for (ProcessNode pn : processNodes) {
			if (pn.getId().equals(id)) {
				return pn;
			}
		}
		return null;
	}

	public static List<IntermediateInput> getIntermediateInputs(ProcessNode processNode, String id) {
		List<IntermediateInput> list = new ArrayList<IntermediateInput>();
		if (processNode != null && id != null) {
			List<IntermediateInput> all = processNode.getIntermediateInputs();
			for (IntermediateInput intermediateInput : all) {
				if (intermediateInput.getId().equals(id)) {
					list.add(intermediateInput);
				}
			}
		}
		return list;
	}

	public static List<MultiInput> getMultiInputs(ProcessNode processNode,String id){
		List<MultiInput> list = new ArrayList<MultiInput>();
		if (processNode != null && id != null) {
			List<MultiInput> all = processNode.getMultiInputNodes();
			for(MultiInput input:all){
				if(input.getId().equals(id)){
					list.add(input);
				}
			}
		}
		return list;
	}
	
	/*
	 * get Input from EmulationCacheManager
	 */
	public static Input getInputFromCache(String processId) {
		EmulationData emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
		if (emulationData == null || processId == null)
			return null;
		EList<ProcessNode> pNodes = emulationData.getProcessNodes();
		for (ProcessNode pn : pNodes) {
			if (pn.getId().equals(processId)) {
				return pn.getInput();
			}
		}
		return null;
	}

	/*
	 * get Assertion from EmulationCacheManager
	 */
	public static Assertion getAssertionFromCache(String xpdlId) {
		EmulationData emulationData = EmulationCacheManager.getDefault().getCurrentEmulationData();
		if (emulationData == null || xpdlId == null)
			return null;
		EList<ProcessNode> processNodes = emulationData.getProcessNodes();
		for (ProcessNode pn : processNodes) {
			Assertion tp = getAssertion(pn, xpdlId);
			if (tp != null)
				return tp;
		}
		return null;
	}

	private static Assertion getAssertion(ProcessNode processNode, String id) {
		if (processNode == null || id == null)
			return null;
		EList<Assertion> assertions = processNode.getAssertions();
		for (Assertion tp : assertions) {
			if (tp.getId().equals(id)) {
				return tp;
			}
		}
		return null;
	}

	public static ProcessNode createProcessNodeWithOutput(EObject process) {
		ProcessNode processNode = EmulationFactory.eINSTANCE.createProcessNode();
		processNode.setId(ProcessUtil.getElementId(process));
		processNode.setName(ProcessUtil.getDisplayName(process));
		processNode.setModelType(ProcessUtil.getModelType(process));
		processNode.setDescription("Automatically created"); //$NON-NLS-1$

		// out put
		processNode.setOutput(EmulationFactory.eINSTANCE.createOutput());
		return processNode;
	}

	public static boolean isEmulationFolder(SpecialFolder specialFolder) {
		return specialFolder.getKind().endsWith(EmulationCoreActivator.EMULATION_FILE_EXTENSION);
	}

	public static SpecialFolder getEmulationFolder(IProject project) {
		if (project != null) {
			ProjectConfig config = XpdResourcesPlugin.getDefault().getProjectConfig(project);
			if (config != null) {
				SpecialFolders folders = config.getSpecialFolders();
				for (SpecialFolder folder : folders.getFolders()) {
					if (isEmulationFolder(folder)) {
						if (folder.getFolder() != null) {
							return folder;
						}
						{
							folders.removeFolder(folder);
							return null;
						}

					}
				}
			}
		}
		return null;
	}

	public static Map<IFile, List<ProcessNode>> getAllInputFromWorkspace() {
		Map<IFile, List<ProcessNode>> processNodes = null;
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = workspaceRoot.getProjects();
		for (int i = 0; i < projects.length; i++) {
			SpecialFolder emulationFolder = getEmulationFolder(projects[i]);

			if (emulationFolder != null) {
				if (processNodes == null) {
					processNodes = new HashMap<IFile, List<ProcessNode>>();
				}
				Map tempProcessNode = getProcessNodesFromEmFolder(emulationFolder);
				if (tempProcessNode != null && tempProcessNode.size() > 0) {
					processNodes.putAll(tempProcessNode);
				}
			}
		}
		return processNodes;
	}

	public static Map<IFile, List<ProcessNode>> getProcessNodesFromEmFolder(SpecialFolder emFolder) {
		Map<IFile, List<ProcessNode>> result = new HashMap<IFile, List<ProcessNode>>();
		IResource[] resources;
		try {
			resources = emFolder.getFolder().members();
			for (int i = 0; i < resources.length; i++) {
				if (resources[i] instanceof IFile
						&& EmulationCoreActivator.EMULATION_FILE_EXTENSION.equalsIgnoreCase(((IFile) resources[i]).getFileExtension())) {
					EmulationData emulationData = getEmulationDataFromFile((IFile) resources[i]);
					if (emulationData != null) {
						emulationData = (EmulationData) EcoreUtil.copy(emulationData);
						List<ProcessNode> processNodes = emulationData.getProcessNodes();
						List<ProcessNode> addNodes = new ArrayList<ProcessNode>();
						if(processNodes!=null && !processNodes.isEmpty()){
							for(ProcessNode node:processNodes){
								if(node.getInput()!=null){
									addNodes.add(node);
								}
							}
							if(!addNodes.isEmpty()){
								result.put((IFile) resources[i], addNodes);
							}
						}
					}
				}
			}
		} catch (CoreException e) {
			EmulationCoreActivator.log(e);
		} catch (IOException e) {
			EmulationCoreActivator.log(e);
		}

		return result;
	}

	public static EmulationData getEmulationDataFromFile(IFile emulationFile) throws IOException {
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(emulationFile);
		EObject object = workingCopy.getRootElement();
		if (object instanceof EmulationData) {
			return (EmulationData) object;
		} else {
			return null;
		}
	}

	public static Output createOutput(EObject activity, Map<String, String> variables) {
		Output output = null;
		if (activity != null) {
			// List<FormalParameter> list =
			// EmulationUtil.getOutputParms(activity.getProcess());
			output = EmulationFactory.eINSTANCE.createOutput();
			output.setId(ProcessUtil.getElementId(activity));
			output.setName(ProcessUtil.getElementName(activity));
			/*
			 * for(FormalParameter fParameter : list){ Parameter parameter =
			 * EmulationFactory.eINSTANCE.createParameter();
			 * parameter.setId(fParameter.getId());
			 * parameter.setName(fParameter.getName());
			 * parameter.setValue(variables.get(fParameter.getName()));
			 * output.getParameters().add(parameter); }
			 */
		}
		return output;
	}

	public static boolean isProcessSpecialFolder(SpecialFolder element) {
		// TODO hard coded
		return ((SpecialFolder) element).getKind().equals("processes") || ((SpecialFolder) element).getKind().equals("com.tibco.bw.process.project");
	}

	public static SpecialFolder getProcessSpecialFolder(IProject project) {
		// TODO hard coded
		ProjectConfig config = XpdResourcesPlugin.getDefault().getProjectConfig(project);
		SpecialFolders folders = config.getSpecialFolders();
		List<SpecialFolder> list = folders.getFoldersOfKind("processes");
		if (list.size() >= 0) {
			return list.get(0);
		} else {
			list = folders.getFoldersOfKind("com.tibco.bw.process.project");
			if (list.size() >= 0) {
				return list.get(0);
			}
		}
		return null;
	}

	private static Map<String, IActivityElementFactory> factoryMap = new HashMap<String, IActivityElementFactory>();// modelType:IActivityElementFactory
	private static final String EXTENSION_ACTIVITY_ELEMENT_FACTORY_ID = EmulationCoreActivator.PLUGIN_ID + ".activityElementFactories"; //$NON-NLS-1$
	static {
		synchronized (factoryMap) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ACTIVITY_ELEMENT_FACTORY_ID);
			for (IConfigurationElement element : elements) {
				try {
					String modelType = element.getAttribute("id");//$NON-NLS-1$
					IActivityElementFactory factory = (IActivityElementFactory) element.createExecutableExtension("class");//$NON-NLS-1$
					factoryMap.put(modelType, factory);
				} catch (Exception e) {
					EmulationCoreActivator.log(e);
					continue;
				}
			}
		}
	}

	public static IInOutElement createInputElement(Input input, EObject startActivity, String modelType) {
		return factoryMap.get(modelType).createInputElement(input, ProcessUtil.getProcess(startActivity), modelType);
	}

	public static IInOutElement createInputElement(Input input, ProcessNode processNode) {
		return factoryMap.get(processNode.getModelType()).createInputElement(input,
				ProcessUtil.getProcess(processNode.getId(), processNode.getModelType()), processNode.getModelType());
	}

	public static ITestpointElement createTestpointElement(Testpoint testpoint, EObject task, String modelType) {
		return factoryMap.get(modelType).createTestpointElement(testpoint, ProcessUtil.getProcess(task), modelType);
	}

	public static ITestpointElement createTestpointElement(Testpoint testpoint, ProcessNode processNode) {
		return factoryMap.get(processNode.getModelType()).createTestpointElement(testpoint,
				ProcessUtil.getProcess(processNode.getId(), processNode.getModelType()), processNode.getModelType());
	}

	public static IInOutElement createOutputElement(Output output, ProcessNode processNode) {
		return factoryMap.get(processNode.getModelType()).createOutputElement(output,
				ProcessUtil.getProcess(processNode.getId(), processNode.getModelType()), processNode.getModelType());
	}

}
