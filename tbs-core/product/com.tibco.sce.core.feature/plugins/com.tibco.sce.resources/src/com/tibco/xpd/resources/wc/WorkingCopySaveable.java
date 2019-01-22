/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Saveable class for working copy.
 * <p>
 * Copy form Savable javadoc:
 * <p>
 * A <code>Saveable</code> represents a unit of saveability, e.g. an editable
 * subset of the underlying domain model that may contain unsaved changes.
 * Different workbench parts (editors and views) may present the same saveables
 * in different ways. This interface allows the workbench to provide more
 * appropriate handling of operations such as saving and closing workbench
 * parts. For example, if two editors sharing the same saveable with unsaved
 * changes are closed simultaneously, the user is only prompted to save the
 * changes once for the shared saveable, rather than once for each editor.
 * <p>
 * Workbench parts that work in terms of saveables should implement
 * {@link ISaveablesSource}.
 * </p>
 * 
 * @see ISaveablesSource
 * 
 */
public class WorkingCopySaveable extends Saveable {

    /**
     * Working copy instance.
     */
    private final WorkingCopy wc;

    /**
     * The Constructor.
     * 
     * @param wc
     */
    public WorkingCopySaveable(WorkingCopy wc) {
        this.wc = wc;
    }

    /**
     * @see org.eclipse.ui.Saveable#doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     *            propgress monitor
     * @throws CoreException
     *             when it cannot progress
     */
    @Override
    public void doSave(IProgressMonitor monitor) throws CoreException {
        if (monitor != null) {
            String msg =
                    String.format(Messages.AbstractWorkingCopy_savingFile_shortdesc,
                            new Object[] { getName() });
            monitor.setTaskName(msg);
        }
        try {
            wc.save();
        } catch (IOException e) {
            throw new CoreException(new Status(Status.ERROR,
                    XpdResourcesPlugin.ID_PLUGIN, Status.OK, e.getMessage(), e));
        }
    }

    /**
     * Savables are equals if the are in the same working copy.
     * 
     * @param object
     *            obj
     * @return equals from the working copy
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        final WorkingCopySaveable other = (WorkingCopySaveable) object;
        if (wc == null) {
            if (other.wc != null)
                return false;
        } else if (!wc.equals(other.wc))
            return false;
        return true;
    }

    /**
     * @return hashCode from the WorkingCopy.
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((wc == null) ? 0 : wc.hashCode());
        return result;
    }

    /**
     * @see org.eclipse.ui.Saveable#getImageDescriptor()
     * @return image desciptor or null
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        // If the working copy is loaded the get
        // image from the model
        if (wc.isLoaded()) {
            /*
             * Get image from file rather than root element as that is what
             * we're saving.
             */
            IResource iResource = wc.getEclipseResources().get(0);
            if (iResource != null) {

                IWorkbenchAdapter workbenchAdapter =
                        (IWorkbenchAdapter) iResource
                                .getAdapter(IWorkbenchAdapter.class);

                if (workbenchAdapter != null) {
                    return workbenchAdapter.getImageDescriptor(iResource);

                }
            }
            return WorkingCopyUtil.getImageDescriptor(wc.getRootElement());

        }
        return null;
    }

    /**
     * @see org.eclipse.ui.Saveable#getName()
     * 
     * @return working copy name
     */
    @Override
    public String getName() {
        return wc.getName();
    }

    /**
     * @see org.eclipse.ui.Saveable#getToolTipText()
     * 
     * @return tooltip
     */
    @Override
    public String getToolTipText() {
        List<IResource> eclipseResources = wc.getEclipseResources();
        return eclipseResources.size() > 0 ? eclipseResources.get(0)
                .getFullPath().toOSString() : ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.Saveable#isDirty()
     * 
     * @return is working copy dirty?
     */
    @Override
    public boolean isDirty() {
        return wc.isWorkingCopyDirty();
    }

    /**
     * Returns working copy associated with this sevable.
     * 
     * @return working copy associated with this sevable.
     */
    public WorkingCopy getWorkingCopy() {
        return wc;
    }

}