/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.decorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Decorator for XPD validation framework. It decoreate IRecources and
 * Specialfolders with error markers.
 * 
 * @author wzurek
 */
public class XPDProblemDecorator extends LabelDecorator {

    /**
     * Error images registry (local cache).
     */
    private Map<Image, Image> errorRegistry = new HashMap<Image, Image>();

    /**
     * Warning images registry (local cache).
     */
    private Map<Image, Image> warningRegistry = new HashMap<Image, Image>();

    /** error image overlay */
    private ImageData errorOvr;

    /** warning image overlay */
    private ImageData warningOvr;

    /**
     * resource changes listener. listen to changes in markers assiciated with
     * files
     */
    private IResourceChangeListener resourceListener =
            new IResourceChangeListener() {
                public void resourceChanged(IResourceChangeEvent event) {
                    IMarkerDelta[] deltas =
                            event.findMarkerDeltas(IMarker.PROBLEM, true);
                    XpdResourcesPlugin wcFact = XpdResourcesPlugin.getDefault();

                    Map<IResource, WorkingCopy> ressWcMap =
                            new HashMap<IResource, WorkingCopy>();

                    List<EObject> objects = new ArrayList<EObject>();

                    for (IMarkerDelta md : deltas) {
                        IResource res = md.getResource();
                        WorkingCopy wc = null;

                        // Only check availability / validity of working copy
                        // ONCE as isInvalidFile() repeated for EVERY marker can
                        // be very expensive when have 1000's of markers!
                        if (!ressWcMap.containsKey(res)) {
                            wc = wcFact.getWorkingCopy(res);
                            if (wc != null && !wc.isInvalidFile()
                                    && wc.isLoaded()) {
                                ressWcMap.put(res, wc);
                            } else {
                                wc = null; // Invalid!
                                ressWcMap.put(res, null);
                            }
                        } else {
                            // We've come across this resource before get it's
                            // working copy.
                            wc = ressWcMap.get(res);
                        }

                        if (wc != null) {
                            String uri = md.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
                            if (uri != null && uri.length() > 0) {
                                EObject eo = wc.resolveEObject(uri);
                                if (eo != null) {
                                    objects.add(eo);
                                }
                            }
                        }
                    }
                    List<Object> needRefresh =
                            getResourcesToRefresh(ressWcMap.keySet());
                    addEObjectMarkers(objects, needRefresh);
                    fireRefresh(needRefresh);
                }

                private void addEObjectMarkers(List<EObject> objects,
                        List<Object> needRefresh) {
                    for (EObject object : objects) {
                        EObject eo = object;
                        while (eo != null && !needRefresh.contains(eo)) {
                            needRefresh.add(eo);
                            eo = eo.eContainer();
                        }
                    }
                }
            };

    /** label provider listeners */
    private List<ILabelProviderListener> listeners =
            new ArrayList<ILabelProviderListener>();

    /**
     * The Constructor.
     */
    public XPDProblemDecorator() {
        errorOvr =
                XpdResourcesUIActivator
                        .getImageDescriptor("icons/overlay/error.gif").getImageData(); //$NON-NLS-1$
        warningOvr =
                XpdResourcesUIActivator
                        .getImageDescriptor("icons/overlay/warning.gif").getImageData(); //$NON-NLS-1$
        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(resourceListener);
    }

    /**
     * List of objects that need to be redecorated when given resource has
     * changed.
     * 
     * @param ress
     *            changed reosurces
     * @return
     */
    protected List<Object> getResourcesToRefresh(Collection<IResource> ress) {
        List<Object> result = new ArrayList<Object>();
        for (IResource res : ress) {
            IResource tres = res;
            try {
                if (res.getProject() != null
                        && res.getProject()
                                .hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    // only changes in project with XPD nature
                    while (!result.contains(tres) && tres != null) {
                        if (tres instanceof IFolder) {
                            IFolder fld = (IFolder) tres;
                            ProjectConfig pc =
                                    XpdResourcesPlugin
                                            .getDefault()
                                            .getProjectConfig(tres.getProject());
                            SpecialFolder sf =
                                    pc.getSpecialFolders().getFolder(fld);
                            if (sf != null) {
                                result.add(sf);
                                result.add(tres);
                            } else {
                                result.add(tres);
                            }
                        } else {
                            result.add(tres);
                        }
                        if (tres == tres.getParent()) {
                            tres = null;
                        } else {
                            tres = tres.getParent();
                        }
                    }
                }
            } catch (CoreException e) {
                // ignore
            }
        }
        return result;
    }

    /**
     * Decorate given image.
     */
    public Image decorateImage(Image image, Object element) {
        if (image == null) {
            return null;
        }
        IResource res = getResource(element);
        boolean hasError = false;
        boolean hasWarning = false;
        try {
            if (element instanceof XPDDecorationStatusProvider) {

                switch (((XPDDecorationStatusProvider) element)
                        .getDecorationMarker()) {
                case IMarker.SEVERITY_WARNING:
                    hasWarning = true;
                    break;
                case IMarker.SEVERITY_ERROR:
                    hasError = true;
                    break;
                }
            } else if (res != null) {
                IMarker[] markers =
                        res.findMarkers(IMarker.PROBLEM,
                                true,
                                IResource.DEPTH_INFINITE);
                if (markers != null) {
                    for (IMarker marker : markers) {
                        int priority =
                                marker.getAttribute(IMarker.SEVERITY, -1);
                        if (priority == IMarker.SEVERITY_WARNING) {
                            hasWarning = true;
                        } else if (priority == IMarker.SEVERITY_ERROR) {
                            hasError = true;
                            break;
                        }
                    }
                }
            } else if (element instanceof EObject) {
                EObject eo = (EObject) element;
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
                if (wc != null) {
                    ResourceSet set = eo.eResource().getResourceSet();
                    if (set != null) {
                        IResource er = wc.getEclipseResources().get(0);
                        int severity = getSeverity(eo, er);
                        if (severity == IMarker.SEVERITY_WARNING) {
                            hasWarning = true;
                        } else if (severity == IMarker.SEVERITY_ERROR) {
                            hasError = true;
                        }
                    }
                }
            }
        } catch (CoreException e) {
            // ignore
        }

        if (image == null || !(hasError || hasWarning)) {
            return null;
        }
        final ImageData ovr;
        Map<Image, Image> registry;
        if (hasError) {
            ovr = errorOvr;
            registry = errorRegistry;
        } else { // hasWarning
            ovr = warningOvr;
            registry = warningRegistry;
        }
        if (registry.containsKey(image)) {
            return registry.get(image);
        } else {
            Display display = PlatformUI.getWorkbench().getDisplay();

            Image retImage = createOverlayImage(display, image, ovr);

            registry.put(image, retImage);
            return retImage;
        }
    }

    /**
     * Returns severity of problems on given EObject based on markers from
     * provided resource. It compute the severity from the object and its
     * children.
     * 
     * @param eo
     * @param er
     * @return severity or '0'
     * @throws CoreException
     */
    public static int getSeverity(EObject eo, IResource er)
            throws CoreException {
        int severity = 0;
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
        severity = wc.getSeverity(eo, er);
        return severity;
    }

    private Image createOverlayImage(Device device, Image baseImage,
            ImageData overlayImage) {

        Image img =
                new OverlayImageDescriptor(baseImage.getImageData(),
                        overlayImage).createImage();

        return img;
    }

    @SuppressWarnings("unused")
    private Image old_sid_createOverlayImage(Device device, Image baseImage,
            ImageData overlayImage) {

        ImageData baseImageData = baseImage.getImageData();

        Image ovr = new Image(device, overlayImage);

        Image result =
                new Image(device, baseImageData.width, baseImageData.height);

        GC gc = new GC(result);

        // Transparent color = Almost white but not quite (less likely to make
        // real parts of icon transparent if have a not quite usual color).
        Color transparent = new Color(device, 254, 254, 255);
        gc.setBackground(transparent);
        gc.fillRectangle(0, 0, baseImageData.width, baseImageData.height);

        int transPixel = result.getImageData().getPixel(0, 0);

        gc.drawImage(baseImage, 0, 0);
        gc.dispose();

        // Make transparency.
        ImageData resData = (ImageData) result.getImageData();
        resData.transparentPixel = transPixel;

        Image transparentResult = new Image(device, resData);

        result.dispose();
        transparent.dispose();

        // Sid...
        // In order to work correctly on win2000 look and feel windows XP we
        // have to create an image (as above) then overlay the decorator into a
        // new image created with the transparency stuff already in...
        // THIS ONLY WORKS WITH GIF DECORATORS!!!!!
        // PNGs work ONLY if you don't decorate over an already decorated image!
        // GIF's work only IF YOU decorate over an already decorated image!
        Image transRes2 =
                new Image(device, baseImageData.width, baseImageData.height);

        gc = new GC(transRes2);
        gc.drawImage(transparentResult, 0, 0);

        gc.drawImage(ovr, 0, baseImageData.height - overlayImage.height);
        gc.dispose();

        ovr.dispose();
        transparentResult.dispose();

        return transRes2;
    }

    /**
     * Get resource if given object is resource or special folder.
     * 
     * @param element
     * @return
     */
    private IResource getResource(Object element) {
        if (element instanceof IResource) {
            return (IResource) element;
        }
        if (element instanceof SpecialFolder) {
            return ((SpecialFolder) element).getFolder();
        }
        return null;
    }

    /**
     * This decorator does not decorate text.
     */
    public String decorateText(String text, Object element) {
        return text;
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    public void addListener(ILabelProviderListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /*
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    public void dispose() {
        for (Image img : errorRegistry.values()) {
            img.dispose();
        }
        for (Image img : warningRegistry.values()) {
            img.dispose();
        }
        ResourcesPlugin.getWorkspace()
                .removeResourceChangeListener(resourceListener);
    }

    /*
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
     * .Object, java.lang.String)
     */
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
    public void removeListener(ILabelProviderListener listener) {
        listeners.remove(listener);
    }

    /*
     * @see
     * org.eclipse.jface.viewers.LabelDecorator#decorateImage(org.eclipse.swt
     * .graphics.Image, java.lang.Object,
     * org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public Image decorateImage(Image image, Object element,
            IDecorationContext context) {
        return decorateImage(image, element);
    }

    /*
     * @see
     * org.eclipse.jface.viewers.LabelDecorator#decorateText(java.lang.String,
     * java.lang.Object, org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public String decorateText(String text, Object element,
            IDecorationContext context) {
        return decorateText(text, element);
    }

    /*
     * @see
     * org.eclipse.jface.viewers.LabelDecorator#prepareDecoration(java.lang.
     * Object, java.lang.String, org.eclipse.jface.viewers.IDecorationContext)
     */
    @Override
    public boolean prepareDecoration(Object element, String originalText,
            IDecorationContext context) {
        return false;
    }

}
