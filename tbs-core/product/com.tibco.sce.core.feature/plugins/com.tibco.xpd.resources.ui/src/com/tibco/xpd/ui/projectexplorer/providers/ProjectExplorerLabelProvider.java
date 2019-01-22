/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil.PresentationType;

/**
 * Common label provider for the Project Explorer. This will provide text and
 * image for <code>EObject</code> and <code>IResource</code> objects. This class
 * may be subclassed to handle other object types.
 * 
 * @author njpatel
 */
public class ProjectExplorerLabelProvider extends LabelProvider implements
        ICommonLabelProvider {

    private final ILabelProvider provider = new WorkbenchLabelProvider();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.ICommonLabelProvider#init(org.eclipse.ui.navigator
     * .ICommonContentExtensionSite)
     */
    public void init(ICommonContentExtensionSite aConfig) {

        // Do nothing
    }

    @Override
    public String getText(Object element) {
        String text = null;
        /*
         * If the element passed in is a structured selection then get the
         * object from it. This can be the case when the Properties view is
         * acquiring the text for the title
         */
        if (element instanceof IStructuredSelection) {
            IStructuredSelection selection = (IStructuredSelection) element;

            element = selection.getFirstElement();
        }

        if (element instanceof SpecialFolder) {
            text = getSpecialFolderText((SpecialFolder) element);
        } else if (element instanceof EObject) {
            text = WorkingCopyUtil.getText((EObject) element);

        } else if (element instanceof IResource) {
            text = provider.getText(element);

            if (element instanceof IFile) {
                // If this file has a working copy and it's dirty then mark the
                // file
                // as dirty
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy((IResource) element);

                if (wc != null && wc.isWorkingCopyDirty()) {
                    // Show file as dirty in the tree
                    text = "* " + text; //$NON-NLS-1$
                }
            }
        }

        return text != null ? text : super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        /*
         * If the element passed in is a structured selection then get the
         * object from it. This can be the case when the Properties view is
         * acquiring the image for the title
         */
        if (element instanceof IStructuredSelection) {
            IStructuredSelection selection = (IStructuredSelection) element;

            element = selection.getFirstElement();
        }

        if (element instanceof EObject) {
            return WorkingCopyUtil.getImage((EObject) element);

        } else if (element instanceof IResource) {
            return provider.getImage(element);
        }

        return super.getImage(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IMementoAware#restoreState(org.eclipse.ui.IMemento
     * )
     */
    public void restoreState(IMemento aMemento) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IMementoAware#saveState(org.eclipse.ui.IMemento)
     */
    public void saveState(IMemento aMemento) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IDescriptionProvider#getDescription(java.lang
     * .Object)
     */
    public String getDescription(Object anElement) {

        if (anElement instanceof EObject) {
            /*
             * XPD-1140: If resource is read only then indicate that in property
             * tab title
             */
            EObject eObject = (EObject) anElement;
            String description = WorkingCopyUtil.getMetaText(eObject);

            Resource eResource = eObject.eResource();

            if (eResource != null) {
                EditingDomain editingDomain =
                        WorkingCopyUtil.getEditingDomain(eObject);

                if (editingDomain != null) {
                    if (editingDomain.isReadOnly(eResource)) {
                        description = description + " " + Messages.ProjectExplorerLabelProvider_ReadOnly_label; //$NON-NLS-1$
                    }
                }
            }

            return description;

        } else if (anElement instanceof IAdaptable) {
            IResource res =
                    (IResource) ((IAdaptable) anElement)
                            .getAdapter(IResource.class);

            if (res != null) {
                return res.getName() + " - " //$NON-NLS-1$
                        + res.getProjectRelativePath().toString();
            }
        }

        return null;
    }

    /**
     * Get the label for the special folder. If the special folder presentation
     * is set to project level then the full path of the folder will be set as
     * text (the text value set in the eobject). Otherwise, the folder name will
     * be returned.
     * 
     * @param sf
     * @return
     */
    private String getSpecialFolderText(SpecialFolder sf) {
        String text = null;

        if (sf != null) {
            if (SpecialFolderPresentationUtil.getPreferenceValue() == PresentationType.PROJECTLEVEL) {
                // Project level presentation so get the text from the eobject
                text = WorkingCopyUtil.getText(sf);
            } else {
                if (sf.getFolder() != null) {
                    // Get the name of folder as the text
                    text = sf.getFolder().getName();
                }
            }
        }

        return text;
    }

}
