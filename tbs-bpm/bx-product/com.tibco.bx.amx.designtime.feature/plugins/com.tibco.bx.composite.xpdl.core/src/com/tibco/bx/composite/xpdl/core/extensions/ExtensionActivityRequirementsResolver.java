package com.tibco.bx.composite.xpdl.core.extensions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.amf.sca.model.componenttype.CapabilityType;
import com.tibco.amf.sca.model.componenttype.ComponentTypeFactory;
import com.tibco.amf.sca.model.componenttype.RequiredCapability;
import com.tibco.amf.sca.model.componenttype.Requirements;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.bx.composite.core.extensions.IRequirementsResolver;
import com.tibco.bx.composite.core.util.BxCompositeCoreConstants;
import com.tibco.bx.composite.core.util.BxCompositeCoreUtil;
import com.tibco.bx.composite.xpdl.core.extensions.internal.ExtensionDependenciesMap;
import com.tibco.bx.model.service.ServiceImplementation;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;


public class ExtensionActivityRequirementsResolver implements IRequirementsResolver {

	public void addImplementationRequirements(Requirements requirements, Implementation implementation) {
		BxServiceImplementation bxImplementation = (BxServiceImplementation) implementation;
		ServiceImplementation serviceModel = bxImplementation.getServiceModel();
		if (serviceModel == null) {
			return;
		}
		String moduleName = serviceModel.getModuleName();
		if (moduleName == null || moduleName.equals("")) {
			return;
		}
		IFile xpdlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(moduleName));
		WorkingCopy workingCopy = XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
		if (workingCopy == null || !(workingCopy.getRootElement() instanceof Package)) {
			return;
		}

		createFactoryCapability(requirements.getRequiredCapabilities());
		
		EList<Process> processes = ((Package) workingCopy.getRootElement()).getProcesses();
		for (com.tibco.bx.core.model.Process process:serviceModel.getProcesses()) {
			for (Process p : processes) {
				if (p.getName().equals(process.getProcessName())) {
					List<Activity> activities = p.getActivities();
					for (Activity a : activities) {
						handleActivity(a, requirements, implementation);
					}
					break;
				}
			}
		}
	}

	protected void handleActivity(Activity activity, Requirements requirements, Implementation implementation) {
		if (activity.getImplementation() instanceof Task) {
			Task task = (Task) activity.getImplementation();
			findExtensionRequirements(task, requirements, implementation);
		}
		
		if (activity.getImplementation() instanceof SubFlow) {
			findSubFlowRequirements(activity, requirements, implementation);
		}
		
		// handle activity set
		BlockActivity blockActivity = activity.getBlockActivity();
		if (blockActivity != null) {
			String setId = blockActivity.getActivitySetId();
			ActivitySet activitySet = activity.getProcess().getActivitySet(setId);
			List<Activity> activities = activitySet.getActivities();
			for (Activity act : activities) {
				handleActivity(act, requirements, implementation);
			}
		}
	}

	protected void findExtensionRequirements(Task task, Requirements requirements, Implementation implementation) {
		if (task.getTaskService() != null) {
			TaskService taskService = task.getTaskService();
			ImplementationType implementationType = taskService.getImplementation();
			if (implementationType == null) {
				return;
			}

			if (implementationType.getValue() == ImplementationType.OTHER) {
				FeatureMap featureMap = taskService.getOtherElements();
				FeatureMap.ValueListIterator it = featureMap.valueListIterator();
				while (it.hasNext()) {
					EObject ext = (EObject) it.next();
					Class<?> instanceClass = ext.eClass().getInstanceClass();
					ExtensionDependenciesMap.getInstance().getExtensionRequirements(instanceClass, requirements);
				}
			}
		} else if (task.getTaskUser() != null) {
			ExtensionDependenciesMap.getInstance().getExtensionRequirements(TaskUser.class, requirements);
		} else if (task.getTaskManual() != null) {
			ExtensionDependenciesMap.getInstance().getExtensionRequirements(TaskManual.class, requirements);
		} else if (task.getTaskScript() != null) {
			ExtensionDependenciesMap.getInstance().getExtensionRequirements(TaskScript.class, requirements);
		}
	}

	protected void findSubFlowRequirements(Activity activity, Requirements requirements, Implementation implementation) {
		SubFlow subFlow = (SubFlow) activity.getImplementation();
		if (subFlow.getPackageRefId() == null) {
			//same package -- no package dependency
            return;
		}
        EObject procOrIfc = TaskObjectUtil.getSubProcessOrInterface(activity);
    	//only handle non-dynamic invocations
        if (procOrIfc instanceof Process) {
	        addRequiredCapability(requirements, (Process)procOrIfc);
        }
	}

	/**
	 * creates factory capability if one does not already exist and adds it to the requiredCapabilities
	 * @param requiredCapabilities
	 * @return created (or found) factory capability
	 */
	protected RequiredCapability createFactoryCapability(List<RequiredCapability> requiredCapabilities) {
		RequiredCapability factoryRc = null;
		for (RequiredCapability rc : requiredCapabilities) {
			if (BxCompositeCoreConstants.CAPABILITY_ID.equals(rc.getId())) {
				factoryRc = rc;
				break;
			}
		}
		if (factoryRc == null) {
			factoryRc = BxCompositeCoreUtil.createBxRequiredCapability();
			requiredCapabilities.add(factoryRc);
		}
		return factoryRc;
	}
	
	/**
	 * adds required capability for a given process to the requirements
	 * @param requirements
	 * @param pkg
	 */
	protected void addRequiredCapability(Requirements requirements, Process process) {
		Package pkg = Xpdl2ModelUtil.getPackage(process);
		String filePath = pkg.eResource().getURI().toPlatformString(true);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
		String capabilityId = XPDLUtils.getPackageCapabilityId(file.getProject(), pkg);
		String versionRange = XPDLUtils.getPackageVersionRange(pkg);
		
		RequiredCapability rcForSubProcess = ComponentTypeFactory.eINSTANCE.createRequiredCapability();
		rcForSubProcess.setId(capabilityId);
		rcForSubProcess.setType(CapabilityType.CUSTOM);
		rcForSubProcess.setVersion(versionRange);
		
		List<RequiredCapability> requiredCapabilities = requirements.getRequiredCapabilities();
		
		RequiredCapability findCapabilityWithId = BxCompositeCoreUtil.findCapabilityWithId(requiredCapabilities, capabilityId);				
		if (findCapabilityWithId == null) {
			RequiredCapability factoryRc = createFactoryCapability(requirements.getRequiredCapabilities());
            requiredCapabilities.add(rcForSubProcess);
            factoryRc.getWiths().add(rcForSubProcess);
        }
	}
}
