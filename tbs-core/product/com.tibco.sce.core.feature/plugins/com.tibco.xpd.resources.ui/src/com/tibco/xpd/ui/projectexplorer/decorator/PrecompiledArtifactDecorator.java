/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.tibco.xpd.resources.precompile.PreCompileContributorManager;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Project explorer label decorator for Pre-compiled projects and artifacts.
 * 
 * @author aallway
 * @since 5 Jun 2015
 */
public class PrecompiledArtifactDecorator extends LabelDecorator {

    private ImageData precompileOverlayImageData;

    private Map<Image, Image> imageRegistry = new HashMap<Image, Image>();

    /* un-comment this if decoration is required on special folders */
    // private boolean specialFolderHasPrecompileRes = false;

    /** label provider listeners */
    private List<ILabelProviderListener> listeners =
            new ArrayList<ILabelProviderListener>();

    /**
     * 
     */
    public PrecompiledArtifactDecorator() {

        precompileOverlayImageData =
                XpdResourcesUIActivator
                        .getImageDescriptor("icons/overlay/preCompile.png").getImageData(); //$NON-NLS-1$
    }

    /**
     * 
     * @param element
     * @return <code>true</code> if element is pre-compiled
     */
    private boolean isPrecompiled(Object element) {
        /*
         * Check whether the project is pre-compiled before bothering the
         * contributors to find for the pre-compiled resources.
         */
        IProject project = null;
        IResource resource = null;
        SpecialFolder sf = null;

        /*
         * un-comment this and the field declaration above if decoration is
         * required on special folders
         */
        // specialFolderHasPrecompileRes = false;

        if (element instanceof IProject) {

            project = (IProject) element;
        } else if (element instanceof IResource) {

            resource = (IResource) element;
            project = resource.getProject();

        } else if (element instanceof SpecialFolder) {

            sf = (SpecialFolder) element;
            project = sf.getProject();
        }

        if (project != null && ProjectUtil.isPrecompiledProject(project)) {

            if (element instanceof IProject) {

                /* Job done, it's specifically a pre-compiled project */
                return true;

                /* un-comment this if decoration is required on special folders */

                // } else if (null != sf) {
                //
                // /*
                // * Find if the special folder has any pre-compiled resources
                // */
                // specialFolderHasPrecompileRes = findPrecompiledRes(sf);
                // return specialFolderHasPrecompileRes;

            } else if (null != resource) {

                /*
                 * Check if the resource is a pre-compiled resource.
                 */
                boolean isPrecompiledSourceRes =
                        PreCompileContributorManager.getInstance()
                                .isPrecompiledSourceArtefact(resource);
                if (isPrecompiledSourceRes) {

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Go thru the resources under the given special folder and find if there is
     * any pre-compiled resource. Stop the search on first encounter of a
     * pre-compile resource
     * 
     * @param sf
     * @return <code>true</code> if a pre-compiled resource is found under the
     *         given special folder <code>false</code> otherwise
     */
    /* un-comment this if decoration is required on special folders */

    // private boolean findPrecompiledRes(SpecialFolder sf) {
    //
    // IFolder resource = sf.getFolder();
    // try {
    //
    // resource.accept(new IResourceVisitor() {
    //
    // @Override
    // public boolean visit(IResource resource) throws CoreException {
    //
    // /*
    // * Check if the resource is a pre-compiled resource.
    // */
    // boolean isPrecompiledSourceRes =
    // PreCompileContributorManager.getInstance()
    // .isPrecompiledSourceArtefact(resource);
    // if (isPrecompiledSourceRes) {
    //
    // specialFolderHasPrecompileRes = true;
    // return false;
    // }
    // return true;
    // }
    // });
    // } catch (CoreException e) {
    // }
    // return specialFolderHasPrecompileRes;
    // }

    /**
     * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
     *      java.lang.Object)
     * 
     * @param image
     * @param element
     * @return
     */
    @Override
    public Image decorateImage(Image image, Object element) {

        if (image != null) {

            if (isPrecompiled(element)) {

                Image cachedImage = imageRegistry.get(image);
                if (cachedImage != null) {

                    return cachedImage;
                }
                Image overlayImage =
                        new OverlayImageDescriptor(image.getImageData(),
                                precompileOverlayImageData).createImage();

                imageRegistry.put(image, overlayImage);
                return overlayImage;
            }
        }
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String,
     *      java.lang.Object)
     * 
     * @param text
     * @param element
     * @return
     */
    @Override
    public String decorateText(String text, Object element) {

        if (isPrecompiled(element)) {

            return text
                    + " " //$NON-NLS-1$
                    + Messages.PrecompiledArtifactDecorator_label_decoration_text;
        }
        return text;
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {

        if (!listeners.contains(listener)) {

            listeners.add(listener);
        }
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     * 
     * @param element
     * @param property
     * @return
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {

        return false;
    }

    /**
     * Fire refresh on all listeners.
     * 
     * @param toRefresh
     *            elements to refresh or null in order to refresh all elements
     */
    protected void fireRefresh(List<Object> toRefresh) {

        LabelProviderChangedEvent event =
                new LabelProviderChangedEvent(this, toRefresh == null ? null
                        : toRefresh.toArray());

        for (ILabelProviderListener lst : listeners) {

            lst.labelProviderChanged(event);
        }
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
     * .jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {

        listeners.remove(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#decorateImage(org.eclipse.swt.graphics.Image,
     *      java.lang.Object, org.eclipse.jface.viewers.IDecorationContext)
     * 
     * @param image
     * @param element
     * @param context
     * @return
     */
    @Override
    public Image decorateImage(Image image, Object element,
            IDecorationContext context) {

        return decorateImage(image, element);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#decorateText(java.lang.String,
     *      java.lang.Object, org.eclipse.jface.viewers.IDecorationContext)
     * 
     * @param text
     * @param element
     * @param context
     * @return
     */
    @Override
    public String decorateText(String text, Object element,
            IDecorationContext context) {

        return decorateText(text, element);
    }

    /**
     * @see org.eclipse.jface.viewers.LabelDecorator#prepareDecoration(java.lang.Object,
     *      java.lang.String, org.eclipse.jface.viewers.IDecorationContext)
     * 
     * @param element
     * @param originalText
     * @param context
     * @return
     */
    @Override
    public boolean prepareDecoration(Object element, String originalText,
            IDecorationContext context) {

        return false;
    }

}
