package com.tibco.xpd.resources.ui.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/* 
 * Moved from 'com.tibco.xpd.processwidget.popup.actions' to reuse it in the core feature.
 * 
 */

public class ImagePicker extends BaseObjectPicker {

    public Set<String> imageFileExtensions;

    /**
     * Constructor
     * 
     * @param parent
     *            Shell
     * @param resBeingEdited
     *            The resource being edited. This resource will be excluded from
     *            the picker.
     */
    public ImagePicker(Shell parent) {
        super(parent);

        imageFileExtensions = getImageFileExtensions();

        setAllowMultiple(false);
        setTitle(Messages.ImagePicker_Title);
        setMessage(Messages.ImagePicker_Message);
        setEmptyListMessage(Messages.ImagePicker_EmptyListMessage);

        /*
         * Add filter to exclude system folders/files, the file being edited and
         * EObjects
         */
        addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                boolean select = false;

                // Allow special folders
                if (element instanceof SpecialFolder) {
                    select = true;

                } else {
                    IResource res = null;

                    if (element instanceof IResource) {
                        res = (IResource) element;
                    } else if (element instanceof IAdaptable) {
                        res =
                                (IResource) ((IAdaptable) element)
                                        .getAdapter(IResource.class);
                    }

                    if (res != null && !res.getName().startsWith(".")) { //$NON-NLS-1$

                        if (res instanceof IFile) {
                            IPath path = res.getProjectRelativePath();

                            String ext = path.getFileExtension();

                            if (imageFileExtensions.contains(ext)) {
                                select = true;
                            }
                        } else if (res instanceof IContainer) {
                            select = true;
                        }
                    }
                }

                return select;
            }

        });

        // Set the sorter - special folders should be shown before standard
        // folders
        setSorter(new ViewerSorter() {

            private int SPECIAL_FOLDER = 0;

            private int FOLDER = 1;

            private int OTHER = 2;

            @Override
            public int category(Object element) {
                int cat = OTHER;

                if (element instanceof SpecialFolder) {
                    cat = SPECIAL_FOLDER;
                } else if (element instanceof IFolder) {
                    cat = FOLDER;
                }

                return cat;
            }
        });

        // Set the validator - only allow files to be selected
        setValidator(new ISelectionStatusValidator() {

            @Override
            public IStatus validate(Object[] selection) {

                if (selection != null && selection.length == 1) {
                    boolean valid = false;

                    if (selection[0] instanceof IFile) {
                        valid = true;
                    } else if (selection[0] instanceof IAdaptable) {
                        valid =
                                (((IAdaptable) selection[0])
                                        .getAdapter(IFile.class) != null);
                    }

                    if (valid) {
                        return new Status(IStatus.OK,
                                XpdResourcesUIActivator.ID, IStatus.OK, "", //$NON-NLS-1$
                                null);
                    }
                }

                return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                        IStatus.OK,
                        Messages.ImagePicker_SelectValidImage_message, null);
            }

        });
    }

    public static Set<String> getImageFileExtensions() {
        Set<String> exts = new HashSet<String>();

        exts.add("jpg"); //$NON-NLS-1$
        exts.add("ico"); //$NON-NLS-1$
        exts.add("bmp"); //$NON-NLS-1$
        exts.add("png"); //$NON-NLS-1$
        exts.add("gif"); //$NON-NLS-1$

        return exts;
    }
}
