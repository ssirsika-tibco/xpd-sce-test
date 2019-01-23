package com.tibco.bx.debug.common.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessContainer extends ProcessElement {

	public ProcessContainer(String instanceId, String name, String id,
			ProcessContainer parent) {
		super(instanceId, name, id, parent);
	}

	private List<ProcessElement> elements = new ArrayList<ProcessElement>();

	public List<ProcessElement> getElements() {
		return elements;
	}

	public ProcessElement getDescendant(String instanceId) {
		List<ProcessElement> children = getElements();
		for (ProcessElement child : children) {
			if (child.getInstanceId().equals(instanceId)) {
				return child;
			} else if (child instanceof ProcessContainer) {
				ProcessContainer container = (ProcessContainer) child;
				ProcessElement element = container.getDescendant(instanceId);
				if (element != null) {
					return element;
				}
			}
		}
		return null;
	}

}
