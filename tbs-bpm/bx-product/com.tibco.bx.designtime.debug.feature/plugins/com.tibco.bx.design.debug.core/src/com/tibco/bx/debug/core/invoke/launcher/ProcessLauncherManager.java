package com.tibco.bx.debug.core.invoke.launcher;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.launching.JMSProcessLauncher;
import com.tibco.bx.debug.core.launching.PageflowProcessLauncher;
import com.tibco.bx.debug.core.launching.SOAPProcessLauncher;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.util.ProcessUtil;

public class ProcessLauncherManager {

	public final static ProcessLauncherManager instance = new ProcessLauncherManager();

	private Map<EObject, IProcessLauncher> processLauncherMap;

	private final static String BPM_TYPE = "bpm"; //$NON-NLS-1$
	public final static String PAGEFLOW_TYPE = "Pageflow"; //$NON-NLS-1$


	private ProcessLauncherManager() {
		super();
		processLauncherMap = new HashMap<EObject, IProcessLauncher>();
	}

	public IProcessLauncher getProcessLauncher(EObject startActivity, ProcessTemplate processTemplate, IBxProcessListing processListing,
			String endpointUrl) throws CoreException {
		IProcessLauncher processLauncher = processLauncherMap.get(startActivity);
		if (processLauncher == null) {
			processLauncher = createLauncher(startActivity, processTemplate, processListing, endpointUrl);
			processLauncherMap.put(startActivity, processLauncher);
		} else {
			ProcessTemplate template = processLauncher.getProcessTemplate();
			if (!template.equals(processTemplate)) {
				processLauncher = createLauncher(startActivity, processTemplate, processListing, endpointUrl);
				processLauncherMap.put(startActivity, processLauncher);
			}
		}
		return processLauncher;
	}

	private IProcessLauncher createLauncher(EObject startActivity, ProcessTemplate processTemplate, IBxProcessListing processListing,
			String endpointUrl) throws CoreException {
		String modeType = processListing.getConnection().getModelType();
		if (BPM_TYPE.equals(modeType)) {
			if (ProcessUtil.isWebServiceImplementationActivity(startActivity)) {
				// is Web
				return new SOAPProcessLauncher(startActivity, processListing, processTemplate, endpointUrl);
			} else {
				// is JMS, only for BPM
				return new JMSProcessLauncher(startActivity, processTemplate,processListing);
			}
		} else if (PAGEFLOW_TYPE.equals(modeType)) {
			return new PageflowProcessLauncher(startActivity, processTemplate,processListing);
		}
		return null;
	}
	
	public void removeLaunchers() {
		processLauncherMap.clear();
	}

}
