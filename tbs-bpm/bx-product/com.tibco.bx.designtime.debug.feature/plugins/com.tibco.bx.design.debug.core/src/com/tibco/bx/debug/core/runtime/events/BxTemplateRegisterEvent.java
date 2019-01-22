package com.tibco.bx.debug.core.runtime.events;

public class BxTemplateRegisterEvent extends BxRuntimeEvent{

	String processId;
	String processName;
	String moduleName;
	String moduleVersion;
	public BxTemplateRegisterEvent(String processId, String processName,
			String moduleName, String moduleVersion) {
		super(null, EventType.TemplateRegistration);
		this.processId = processId;
		this.processName = processName;
		this.moduleName = moduleName;
		this.moduleVersion = moduleVersion;
	}
	public String getProcessId() {
		return processId;
	}
	public String getProcessName() {
		return processName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getModuleVersion() {
		return moduleVersion;
	}
}
