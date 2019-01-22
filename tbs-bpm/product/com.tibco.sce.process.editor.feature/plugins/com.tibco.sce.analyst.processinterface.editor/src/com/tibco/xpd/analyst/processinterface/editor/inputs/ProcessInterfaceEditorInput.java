/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.inputs;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Package;

/**
 * Editor Input for Process Interface Form Editor
 * 
 * @author rsomayaj
 */
public class ProcessInterfaceEditorInput implements IEditorInput,
		IEditingDomainProvider, Adapter {

	private final ProcessInterface processInterface;

	private final WorkingCopy workingCopy;

	private Notifier target;

	private IPersistableElement persistableElement = null;

	/**
	 * @param packageEditor
	 * @param editingDomain
	 * @param processInterface
	 */
	public ProcessInterfaceEditorInput(WorkingCopy workingCopy,
			ProcessInterface processInterface) {
		this.workingCopy = workingCopy;
		this.processInterface = processInterface;

		// listen to the process container, to find when
		// process is removed
		processInterface.eContainer().eAdapters().add(this);
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {

		// Return the persistable element
		if (persistableElement == null) {
			if (processInterface != null && workingCopy != null) {
				persistableElement = ProcessInterfaceEditorPersist
						.getPersistableElement((IFile) workingCopy
								.getEclipseResources().get(0), processInterface);
			}
		}

		return persistableElement;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		// Ravi- add getPackage on ProcessInterface
		com.tibco.xpd.xpdl2.Package pck = ProcessInterfaceUtil
				.getProcessInterfacePackage(processInterface);

		if (pck == null) {
			// it will be never visible to the user, process can have no
			// parent during multi-step undo operation
			return "Process Interface"; //$NON-NLS-1$
		}
		return WorkingCopyUtil.getText(processInterface);
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return workingCopy.getEclipseResources().get(0).getFullPath()
				.toString()
				+ "/" + WorkingCopyUtil.getText(processInterface); //$NON-NLS-1$
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) {
			// most cases
			return true;
		}
		if (o instanceof ProcessInterfaceEditorInput) {
			return ((ProcessInterfaceEditorInput) o).processInterface
					.equals(processInterface);
		}
		return false;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return false;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {

		if (adapter == EObject.class) {
			return processInterface;
		} else if (adapter == WorkingCopy.class) {
			return workingCopy;
		}
		return null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * @return workflow process assiociated with this EditorInput
	 */
	public ProcessInterface getProcessInterface() {
		return processInterface;
	}

	/**
	 * @return Returns the editingDomain.
	 */
	public EditingDomain getEditingDomain() {
		return workingCopy.getEditingDomain();
	}

	public WorkingCopy getWorkingCopy() {
		return workingCopy;
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification n) {
		if (n.isTouch()) {
			return;
		}
		switch (n.getEventType()) {
		case Notification.REMOVE:
			if (n.getOldValue() == processInterface) {
				closeEditor();
			}
			break;
		case Notification.REMOVE_MANY:
			Collection c = (Collection) n.getNewValue();
			for (Iterator iter = c.iterator(); iter.hasNext();) {
				if (processInterface == iter.next()) {
					closeEditor();
					break;
				}
			}
			break;
		}
	}

	/**
	 * 
	 */
	private void closeEditor() {
		IWorkbenchWindow[] ww = PlatformUI.getWorkbench().getWorkbenchWindows();
		out: for (int i = 0; i < ww.length; i++) {
			IWorkbenchPage[] ps = ww[i].getPages();
			for (int j = 0; j < ps.length; j++) {
				IEditorReference[] es = ps[j].getEditorReferences();
				for (int k = 0; k < es.length; k++) {
					IEditorPart part = (IEditorPart) es[k].getPart(false);
					if (part != null && part.getEditorInput() == this) {
						part.getSite().getPage().closeEditor(part, false);
						break out;
					}
				}
			}
		}
		workingCopy.getAttributes().put(processInterface, null);
		target.eAdapters().remove(this);
		target = null;
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#getTarget()
	 */
	public Notifier getTarget() {
		return target;
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	public void setTarget(Notifier newTarget) {
		target = newTarget;
	}

	/**
	 * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return false;
	}

}