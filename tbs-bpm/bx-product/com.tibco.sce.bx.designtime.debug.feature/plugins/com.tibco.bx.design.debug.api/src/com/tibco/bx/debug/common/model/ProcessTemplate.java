/**
 * 
 */
package com.tibco.bx.debug.common.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tibco.bx.debug.operation.StarterOperation;

public class ProcessTemplate {
	
	private String processId;
	private String processName;
	private String moduleName;
	private String moduleVersion;
	private List<StarterOperation> operations;

	public ProcessTemplate(String processId, String processName, String moduleName, String moduleVersion) {
		this.processId = processId;
		this.processName = processName;
		this.moduleName = moduleName;
		this.moduleVersion = moduleVersion;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getModuleVersion() {
		return moduleVersion;
	}

	public String getProcessId() {
		return processId;
	}

	public String getProcessName() {
		return processName;
	}

	public StarterOperation[] getStarterOperations() {
		return operations != null ? operations.toArray(new StarterOperation[operations.size()]) : Collections.emptyList().toArray(
				new StarterOperation[0]);
	}

	public void addStarterOperation(StarterOperation operation) {
		if (operations == null) {
			operations = new LinkedList<StarterOperation>();
		}
		operations.add(operation);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ProcessTemplate)) {
			return false;
		}

		ProcessTemplate other = (ProcessTemplate) obj;
		return this.processId.equals(other.processId) && this.moduleName.equals(other.moduleName) && this.processName.equals(other.processName)
				&& this.moduleVersion.equals(other.moduleVersion);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + processId.hashCode();
		hash = hash * 31 + processName.hashCode();
		hash = hash * 31 + moduleName.hashCode();
		hash = hash * 31 + moduleVersion.hashCode();
		return hash;
	}

	public String getDetails() {
		return processName + " [" + moduleName + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}