/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Factory to create the {@link AssetWorkingCopyMigration} class for the asset
 * migration extension. This class can be used directly in the extension with
 * the following parameters to migrate working copies in a given special folders
 * of a project:
 * <ul>
 * <li><strong>kind</strong> (this is the special folder kind);</li>
 * <li><strong>fileExt</strong> (this is the file extension of the files in the
 * special folder this class should migrate;</li>
 * </ul>
 * The following is an example of how to pass the parameters to this class in
 * the extension class attribute:<br/>
 * <br/>
 * <code>com.tibco.xpd.resources.projectconfig.projectassets.util.AssetWorkingCopyMigrationFactory: -kind <i>bom</i> -fileExt <i>bom</i></code>
 * </p>
 * 
 * @author njpatel
 * @since 3.5
 * 
 */
public class AssetWorkingCopyMigrationFactory implements
        IExecutableExtensionFactory, IExecutableExtension {

    private static final String SF_PARAM = "kind"; //$NON-NLS-1$

    private static final String EXT_PARAM = "fileExt"; //$NON-NLS-1$

    private String fileExt;

    private String sfKind;

    private String extId;

    @Override
    public Object create() throws CoreException {
        if (fileExt != null && sfKind != null) {
            return new AssetWorkingCopyMigration(sfKind, fileExt);
        } else {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            String
                                    .format("Asset '%s' migration class has wrong or no parameters set.", //$NON-NLS-1$
                                            extId)));
        }
    }

    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            String[] params = ((String) data).split("\\s*-\\s*"); //$NON-NLS-1$

            if (params.length > 0) {
                for (String param : params) {
                    String[] values = param.split("\\s+"); //$NON-NLS-1$
                    if (values.length > 1) {
                        if (SF_PARAM.equals(values[0])) {
                            sfKind = values[1];
                        } else if (EXT_PARAM.equals(values[0])) {
                            fileExt = values[1];
                        }
                    }
                }
            }
        } else if (data instanceof Map<?, ?>) {
            for (Entry<?, ?> param : ((Map<?, ?>) data).entrySet()) {
                if (SF_PARAM.equals(param.getKey())) {
                    sfKind = (String) param.getValue();
                } else if (EXT_PARAM.equals(param.getKey())) {
                    fileExt = (String) param.getValue();
                }
            }
        }

        extId = config.getNamespaceIdentifier();
        if (config.getParent() instanceof IConfigurationElement) {
            String value =
                    ((IConfigurationElement) config.getParent())
                            .getAttribute("id"); //$NON-NLS-1$
            if (value != null) {
                extId = value;
            }
        }
    }

}
