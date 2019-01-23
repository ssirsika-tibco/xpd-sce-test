package com.tibco.bx.emulation.core.resource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.WorkingCopyDependencyProvider;

public class EMWorkingCopyDependencyProvider extends WorkingCopyDependencyProvider{
	 public Class<? extends WorkingCopy> getWorkingCopyClass()
	  {
	    return EMWorkingCopy.class;
	  }

	@Override
	public Collection<IResource> getDependencies(WorkingCopy workingCopy) {
		Set<IResource> set = new HashSet<IResource>();
		EmulationData emulationData = (EmulationData)workingCopy.getRootElement();
		if(emulationData != null){
			EList<ProcessNode> list = emulationData.getProcessNodes();
			for(ProcessNode node : list){
				String id = node.getId();
				EObject process = ProcessUtil.getProcess(id, node.getModelType());
				if(process != null){
					set.add(WorkingCopyUtil.getFile(process));
				}
				
			}
		}
		return set;
		
		
	}
	 
	 
}
