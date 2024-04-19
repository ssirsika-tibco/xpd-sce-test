/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.processscriptlibrary.resource.editor.input;

import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;

/**
 * Editor Input for PSL Script Editor
 * 
 * @author ssirsika
 */
public class ProcessScriptLibraryEditorInput implements IEditorInput,
        IEditingDomainProvider, Adapter {

	private final Activity		activity;

    private final WorkingCopy workingCopy;

    private Notifier target;

    private IPersistableElement persistableElement = null;

    /**
	 * Constructor for creating a new ProcessScriptLibraryEditorInput.
	 * 
	 * @param aWorkingCopy
	 *            Working copy
	 * @param aActivity
	 *            {@link Activity} which represents the PSL function.
	 */
	public ProcessScriptLibraryEditorInput(WorkingCopy aWorkingCopy, Activity aActivity)
	{
        this.workingCopy = aWorkingCopy;
        this.activity = aActivity;

		/*
		 * listen to the activity container, to find when activity is removed
		 */
		activity.eContainer().eAdapters().add(this);
    }

	/**
	 * Constructor for creating a new ProcessScriptLibraryEditorInput.
	 * 
	 * @param aPslFile
	 *            The PSL file for which the editor input is being created.
	 * @throws IllegalArgumentException
	 *             If the provided PSL file is not valid or if the working copy is invalid.
	 */
	public ProcessScriptLibraryEditorInput(IFile aPslFile)
	{
		workingCopy = WorkingCopyUtil.getWorkingCopy(aPslFile);

		if (workingCopy == null)
		{
			throw new IllegalArgumentException("Not a valid PSL file"); //$NON-NLS-1$
		}


		Package pkg = (Package) workingCopy.getRootElement();

		if (pkg == null)
		{
			throw new IllegalArgumentException("Invalid working copy"); //$NON-NLS-1$
		}

		this.activity = pkg.getProcesses().get(0).getActivities().get(0);

		/*
		 * listen to the activity container, to find when activity is removed
		 */
		activity.eContainer().eAdapters().add(this);
	}
    /**
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    @Override
    public IPersistableElement getPersistable() {

		// Return the persistable element
		if (persistableElement == null)
		{
			if (activity != null && workingCopy != null)
			{
				persistableElement = ProcessScriptLibraryEditorPersist
						.getPersistableElement((IFile) workingCopy.getEclipseResources().get(0), activity);
			}
		}

		return persistableElement;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    @Override
    public String getName() {
        return WorkingCopyUtil.getText(activity);
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    @Override
    public String getToolTipText() {
        return workingCopy.getEclipseResources().get(0).getFullPath()
                .toString()
                + "/" + WorkingCopyUtil.getText(activity); //$NON-NLS-1$
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            // most cases
            return true;
        }
        if (o instanceof ProcessScriptLibraryEditorInput) {
            return ((ProcessScriptLibraryEditorInput) o).activity.equals(activity);
        }
        return false;
    }

    /**
	 * @see java.lang.Object#hashCode()
	 *
	 * @return
	 */
	@Override
	public int hashCode()
	{
		return Objects.hashCode(activity) + 999;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
    @Override
    public boolean exists() {
        boolean exists = false;
        if (workingCopy != null) {
            exists = workingCopy.isExist();
        }
        return exists;
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @Override
    public Object getAdapter(Class adapter) {

        if (adapter == EObject.class) {
            return activity;
        } else if (adapter == WorkingCopy.class) {
            return workingCopy;
        }
        return null;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
		return null;
    }

    /**
     * @return workflow process assiociated with this EditorInput
     */
	public Activity getActivity()
	{
        return activity;
    }

    /**
     * @return Returns the editingDomain.
     */
    @Override
    public EditingDomain getEditingDomain() {
        return workingCopy.getEditingDomain();
    }

	/**
	 * @return
	 */
    public WorkingCopy getWorkingCopy() {
        return workingCopy;
    }

    /**
     * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification n) {
        if (n.isTouch()) {
            return;
        }
        if (activity.eResource() == null) {
            /*
             * XPD-1128: beter way of detecting Process element removed from
             * model!
             */
            closeEditor();
        }
    }

    /**
	 * Close editor
	 */
    private void closeEditor() {
        IWorkbenchWindow[] ww = PlatformUI.getWorkbench().getWorkbenchWindows();
        out: for (int i = 0; i < ww.length; i++) {
            IWorkbenchPage[] ps = ww[i].getPages();
            for (int j = 0; j < ps.length; j++) {
                IEditorReference[] es = ps[j].getEditorReferences();
                for (int k = 0; k < es.length; k++) {
                    final IEditorPart part = (IEditorPart) es[k].getPart(false);
                    if (part != null && part.getEditorInput() == this) {
                        runCloseEditor(part);
                        break out;
                    }
                }
            }
        }
        workingCopy.getAttributes().put(activity, null);
        target.eAdapters().remove(this);
        target = null;
    }

	/**
	 * @param part
	 */
	private void runCloseEditor(final IEditorPart part)
	{
		/*
		 * When the process is removed from a non UI thread this
		 * throws an "Invalid Thread Access" Exception
		 */
		if (Display.getCurrent() == null) {
			Display.getDefault().asyncExec(() -> closeEditorAsync(part));
		} else {
		    /* Handle normally when UI thread is Available. */
			closeEditorAsync(part);
		}
	}

	private void closeEditorAsync(IEditorPart editorPart)
	{
		editorPart.getSite().getPage().closeEditor(editorPart, false);
	}
    /**
     * @see org.eclipse.emf.common.notify.Adapter#getTarget()
     */
    @Override
    public Notifier getTarget() {
        return target;
    }

    /**
     * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
     */
    @Override
    public void setTarget(Notifier newTarget) {
        target = newTarget;
    }

    /**
     * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
     */
    @Override
    public boolean isAdapterForType(Object type) {
        return false;
    }

}