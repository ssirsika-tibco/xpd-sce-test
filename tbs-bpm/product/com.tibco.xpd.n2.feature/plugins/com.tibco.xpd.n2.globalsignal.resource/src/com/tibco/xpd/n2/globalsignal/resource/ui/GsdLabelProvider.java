/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider;

/**
 * Label provider for Global Signal Definition objects.
 * 
 * @author sajain
 * @since Feb 3, 2015
 */
public class GsdLabelProvider extends ProjectExplorerLabelProvider {

    /** The image cache. */
    private final ImageCache cache;

    /**
     * Constructor.
     */
    public GsdLabelProvider() {
        // Set up image cache
        cache =
                GsdResourcePlugin.getDefault()
                        .getImageCache();
    }

    /**
     * @param element
     *            The element to get the image for.
     * @return The image.
     * @see com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider#
     *      getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;

        if (element instanceof IStructuredSelection) {

            IStructuredSelection selection = (IStructuredSelection) element;

            if (selection.size() == 1) {

                element = selection.getFirstElement();
            }
        }

        if (element instanceof IFile
                && "gsd".equalsIgnoreCase(((IFile) element).getFileExtension())) { //$NON-NLS-1$

            image = cache.getImage(ImageCache.FILE);

        } else if (element instanceof GlobalSignal
                || element instanceof PayloadDataField) {

            image = WorkingCopyUtil.getImage((EObject) element);
		}
		/*
		 * Sid ACE-8054 Need to handle navigator group since INavigatorGroup contribution used to be handdled under XPDL
		 * content but that has been removed now as clashed with PSL file
		 */
		else if (element instanceof INavigatorGroup)
		{
			// Get the navigator group image
			return ((INavigatorGroup) element).getImage();
        }


        return image != null ? image : super.getImage(element);
    }

    /**
     * @param element
     *            The element to get the text for.
     * @return The text.
     * @see com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider#
     *      getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {

        if (element instanceof GlobalSignal) {

            return WorkingCopyUtil.getText((GlobalSignal) element);

        } else if (element instanceof PayloadDataField) {

            return WorkingCopyUtil.getText((PayloadDataField) element);

        } else if (element instanceof INavigatorGroup) {

            return ((INavigatorGroup) element).getText();

        }

        return super.getText(element);
    }

    /*
     * @see
     * com.tibco.xpd.ui.projectexplorer.providers.ProjectExplorerLabelProvider
     * #getDescription(java.lang.Object)
     */
    @Override
    public String getDescription(Object element) {

        return super.getDescription(element);
    }

}
