/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.OMResourceUtil;
import com.tibco.xpd.om.resources.ui.editor.IGotoObject;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class OMResourcesUIActivator extends AbstractUIPlugin {
    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.om.resources.ui"; //$NON-NLS-1$

    // The shared instance
    private static OMResourcesUIActivator plugin;

    private Logger logger;

    /*
     * Any field whose value starts with "IMG_" or "ICON_" will be loaded into
     * image registry.
     */
    public static final String ICON_SYSTEM_ACTION_COMPONENT =
            "icons/systemActionComponent.png"; //$NON-NLS-1$

    public static final String ICON_SYSTEM_ACTION_COMPONENT_CHANGED =
            "icons/systemActionComponentChanged.png"; //$NON-NLS-1$

    public static final String ICON_SYSTEM_ACTION = "icons/systemAction.png"; //$NON-NLS-1$

    public static final String ICON_SYSTEM_ACTION_CHANGED =
            "icons/systemActionChanged.png"; //$NON-NLS-1$

    public static final String SUB_DIAGRAM_EDITOR_ID =
            "com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorID"; //$NON-NLS-1$

    /**
     * The constructor
     */
    public OMResourcesUIActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        // Register model's adapter factory.
        // OMItemProviderAdapterFactory omAdapterFactory = new
        // OMItemProviderAdapterFactory();
        // AdapterFactory af =
        // XpdResourcesPlugin.getDefault().getAdapterFactory();
        // ((ComposedAdapterFactory) af).insertAdapterFactory(omAdapterFactory);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static OMResourcesUIActivator getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        Field[] fields = this.getClass().getFields();

        for (Field field : fields) {
            String name = field.getName();

            if (name.startsWith("IMG_") || name.startsWith("ICON_")) { //$NON-NLS-1$ //$NON-NLS-2$
                if (field.getType() == String.class
                        && Modifier.isStatic(field.getModifiers())) {
                    String imagePath;
                    try {
                        imagePath = (String) field.get(this);
                        reg.put(imagePath, getImageDescriptor(imagePath));
                    } catch (Exception e) {
                    }
                }

            }
        }

    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * @param path
     * @return return image from given path (taken from
     *         {@link QuickFixToolTipConstants})
     */
    public static Image getImage(String path) {
        return getDefault().getImageRegistry().get(path);
    }

    /**
     * Open editor for the given selected object.
     * 
     * @param page
     *            current workbench page
     * @param selection
     *            object to open in the editor.
     * @throws PartInitException
     */
    public static void openEditor(IWorkbenchPage page, EObject selection)
            throws PartInitException {
        EObject objToOpen = selection;

        if (page != null && selection != null && selection.eClass() != null
                && selection.eClass().getEPackage() == OMPackage.eINSTANCE) {
            IEditorPart editorPart = null;
            while (!(objToOpen instanceof Organization || objToOpen instanceof OrgModel)) {
                objToOpen = objToOpen.eContainer();
                if (objToOpen == null) {
                    return;
                }
            }

            if (objToOpen instanceof Organization) {
                Diagram diagram = OMResourceUtil.getDiagram(objToOpen);

                if (diagram != null && diagram.eResource() != null) {
                    URI uri = EcoreUtil.getURI(diagram);

                    if (uri != null) {
                        String editorTitle =
                                ((Organization) objToOpen).getDisplayName();
                        editorPart =
                                IDE.openEditor(page,
                                        new URIEditorInput(uri, editorTitle),
                                        OMResourcesUIActivator.SUB_DIAGRAM_EDITOR_ID);

                        if (editorPart instanceof IGotoObject
                                && objToOpen != selection) {
                            ((IGotoObject) editorPart).reveal(selection);
                        }

                        objToOpen = null;
                    }
                } else {
                    objToOpen = ((Organization) objToOpen).eContainer();
                }
            }

            if (objToOpen instanceof OrgModel) {
                IFile file = WorkingCopyUtil.getFile(objToOpen);

                if (file != null) {
                    editorPart = IDE.openEditor(page, file);
                    if (editorPart instanceof IGotoObject
                            && objToOpen != selection) {
                        ((IGotoObject) editorPart).reveal(selection);
                    }
                }
            }
        }
    }

}
