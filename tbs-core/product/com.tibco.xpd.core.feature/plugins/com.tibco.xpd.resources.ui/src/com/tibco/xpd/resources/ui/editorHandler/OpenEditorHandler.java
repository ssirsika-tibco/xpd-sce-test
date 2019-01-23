/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.ui.editorHandler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Provides access to the open editor extension point.
 * <p>
 * <strong><u>NOTE: THIS IS FOR INTERNAL USE ONLY.</u></strong>
 * </p>
 * 
 * @author njpatel
 */
public class OpenEditorHandler {

    private static final String EXT_NAME = "openEditorHandler"; //$NON-NLS-1$

    private List<IOpenEditorHandler> handlers;

    private static final OpenEditorHandler INSTANCE = new OpenEditorHandler();

    protected OpenEditorHandler() {
        try {
            handlers = loadExtensions();
        } catch (CoreException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e,
                    "Problems loading the openEditorHandler extensions"); //$NON-NLS-1$
        }
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return
     */
    public static final OpenEditorHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Open and select the object in its editor.
     * 
     * @param object
     * @return <code>true</code> if the object has an editor and was opened,
     *         <code>false</code> if the object does not have representation in
     *         the editor.
     */
    public boolean openEditorFor(Object object) {

        if (object != null) {
            for (IOpenEditorHandler handler : handlers) {
                if (handler.canHandle(object)) {
                    if (handler.openEditor(object)) {
                        return true;
                    }
                    // If the handler fails to open the editor then try another
                    // handler.
                }
            }
        }

        return false;
    }

    /**
     * Load the open editor handlers.
     * 
     * @return
     * @throws CoreException
     */
    private List<IOpenEditorHandler> loadExtensions() throws CoreException {
        List<IOpenEditorHandler> handlers = new ArrayList<IOpenEditorHandler>();

        IConfigurationElement[] elements =
                Platform
                        .getExtensionRegistry()
                        .getConfigurationElementsFor(XpdResourcesUIActivator.ID,
                                EXT_NAME);
        for (IConfigurationElement element : elements) {
            Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
            if (ext instanceof IOpenEditorHandler) {
                handlers.add((IOpenEditorHandler) ext);
            }
        }

        return handlers;
    }

}
