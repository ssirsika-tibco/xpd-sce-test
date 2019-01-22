package com.tibco.bx.emulation.bpm.core;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.core.resource.EMWorkingCopyDependencyProvider;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class BPMEMWorkingCopyDependencyProvider extends EMWorkingCopyDependencyProvider {

	@Override
	public Collection<IResource> getDependencies(WorkingCopy workingCopy) {
		Set<IResource> set = new HashSet<IResource>();
		EmulationData emulationData = (EmulationData) workingCopy.getRootElement();
		if (emulationData != null) {
			EList<ProcessNode> list = emulationData.getProcessNodes();
			for (ProcessNode node : list) {
				String id = node.getId();
				if (node.getModelType() == null || "".equals(node.getModelType())) { //$NON-NLS-1$
					setDefaultModelType(workingCopy, node);
				}
				EObject process = ProcessUtil.getProcess(id, node.getModelType());
				if (process != null) {
					set.add(WorkingCopyUtil.getFile(process));
				}

			}
		}
		return set;
	}
	
	protected void setDefaultModelType(WorkingCopy workingCopy, final ProcessNode processNode) {
		try {
			Command cmd = new RecordingCommand((TransactionalEditingDomain) workingCopy.getEditingDomain()) {

				@Override
				protected void doExecute() {
					processNode.setModelType(EmulationBPMCoreActivator.BPM_MODEL_TYPE);
				}
			};
			workingCopy.getEditingDomain().getCommandStack().execute(cmd);
			workingCopy.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
