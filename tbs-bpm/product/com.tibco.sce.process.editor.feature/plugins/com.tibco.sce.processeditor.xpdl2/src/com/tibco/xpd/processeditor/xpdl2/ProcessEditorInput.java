/*
 ** 
 **  MODULE:             $RCSfile: ProcessEditorInput.java $ 
 **                      $Revision: 1.9 $ 
 **                      $Date: 2005/04/11 12:58:43Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ProcessEditorInput.java $ 
 **    Revision 1.9  2005/04/11 12:58:43Z  wzurek 
 **    Revision 1.8  2005/03/08 13:06:25Z  wzurek 
 **    Revision 1.7  2005/02/18 17:30:20Z  wzurek 
 **    Revision 1.6  2005/01/25 10:37:30Z  WojciechZ 
 **    changes: proper error mesage when XPDL is not valid, close processes editors when close package editor, direct edit also works in response for F2 key 
 **    Revision 1.5  2005/01/11 17:05:14Z  WojciechZ 
 **    work in progress 
 **    Revision 1.4  2004/12/17 18:18:56Z  WojciechZ 
 **    work in progress 
 **    Revision 1.3  2004/12/03 15:48:02Z  WojciechZ 
 **    work in progress 
 **    Revision 1.2  2004/12/02 17:15:41Z  WojciechZ 
 **    work in progress 
 **    Revision 1.1  2004/11/29 10:30:59Z  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.processeditor.xpdl2;

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
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Editor Input for BPMN Process Editor
 * 
 * @author WojciechZ
 */
public class ProcessEditorInput implements IEditorInput,
        IEditingDomainProvider, Adapter {

    private final Process process;

    private final WorkingCopy workingCopy;

    private Notifier target;

    private IPersistableElement persistableElement = null;

    /**
     * @param packageEditor
     * @param editingDomain
     * @param process
     */
    public ProcessEditorInput(WorkingCopy workingCopy, Process process) {
        this.workingCopy = workingCopy;
        this.process = process;

        // listen to the process container, to find when
        // process is removed
        process.getPackage().eAdapters().add(this);
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getPersistable()
     */
    @Override
    public IPersistableElement getPersistable() {

        // Return the persistable element
        if (persistableElement == null) {
            if (process != null && workingCopy != null) {
                persistableElement =
                        ProcessEditorPersist
                                .getPersistableElement((IFile) workingCopy
                                        .getEclipseResources().get(0), process);
            }
        }

        return persistableElement;
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getName()
     */
    @Override
    public String getName() {
        Package pck = process.getPackage();

        if (pck == null) {
            // it will be never visible to the user, process can have no
            // parent during multi-step undo operation
            return "Process"; //$NON-NLS-1$
        }

        if (pck == null) {
            // it will be never visible to the user, process can have no
            // parent during multi-step undo operation
            return "Process"; //$NON-NLS-1$
        }
        return WorkingCopyUtil.getText(process);
    }

    /**
     * @see org.eclipse.ui.IEditorInput#getToolTipText()
     */
    @Override
    public String getToolTipText() {
        return workingCopy.getEclipseResources().get(0).getFullPath()
                .toString()
                + "/" + WorkingCopyUtil.getText(process); //$NON-NLS-1$
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
        if (o instanceof ProcessEditorInput) {
            return ((ProcessEditorInput) o).process.equals(process);
        }
        return false;
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
            return process;
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
    public Process getProcess() {
        return process;
    }

    /**
     * @return Returns the editingDomain.
     */
    @Override
    public EditingDomain getEditingDomain() {
        return workingCopy.getEditingDomain();
    }

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
        if (process.eResource() == null) {
            /*
             * XPD-1128: beter way of detecting Process element removed from
             * model!
             */
            closeEditor();
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
                    final IEditorPart part = (IEditorPart) es[k].getPart(false);
                    if (part != null && part.getEditorInput() == this) {
                        /*
                         * When the process is removed from a non UI thread this
                         * throws an "Invalid Thread Access" Exception
                         */
                        if (Display.getCurrent() == null) {
                            Display.getDefault().asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    part.getSite().getPage()
                                            .closeEditor(part, false);

                                }
                            });
                        } else {
                            /* Handle normally when UI thread is Available. */
                            part.getSite().getPage().closeEditor(part, false);
                        }
                        break out;
                    }
                }
            }
        }
        workingCopy.getAttributes().put(process, null);
        target.eAdapters().remove(this);
        target = null;
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