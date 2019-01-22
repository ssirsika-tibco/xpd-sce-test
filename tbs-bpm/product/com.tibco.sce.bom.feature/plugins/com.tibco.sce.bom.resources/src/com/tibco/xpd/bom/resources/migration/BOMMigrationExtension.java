/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.resources.migration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * 
 * Representation of the BOM Migration extension element
 * 
 * 
 * @author rgreen
 * 
 */
public class BOMMigrationExtension {

    IConfigurationElement element;

    private String EXTENSION_POINT = "migration";

    private String ATT_VERSION = "version";

    private String ATT_CLASS = "class";

    private String ATT_NAME = "name";

    private String ATT_ID = "id";

    private static final Logger LOG =
            BOMResourcesPlugin.getDefault().getLogger();

    public BOMMigrationExtension(IConfigurationElement elem) {
        element = elem;
    }

    public String getId() {
        return element.getAttribute(ATT_ID);
    }

    public String getName() {
        return element.getAttribute(ATT_NAME);
    }

    public String getVersion() {

        return element.getAttribute(ATT_VERSION);
    }

    public Object getExecutableExtension() {
        Object exec = null;

        try {
            exec = element.createExecutableExtension(ATT_CLASS);
        } catch (CoreException e) {
            String message =
                    String
                            .format(Messages.BOMMigrationExtension_CannotFindClass_message,
                                    getId());
            LOG.error(e, message);
        }

        return exec;
    }
}
