/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardCategory;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

import com.tibco.xpd.importexport.ImportExportGeneratorConsts;
import com.tibco.xpd.importexport.imports.custom.CustomImportWizard;
import com.tibco.xpd.rcp.RCPActivator;

/**
 * Helper class that provides information on registered import wizards.
 * 
 * @author njpatel
 * 
 */
public final class ImportWizardRegistry {

    private Collection<IWizardDescriptor> wizardDescriptors;

    private final Map<IWizardDescriptor, Properties> propertiesMap;

    /**
     * Properties to read from the plugin.properties file of a custom import
     * wizard plug-in.
     */
    public enum CustomImportProperty {
        NAME(ImportExportGeneratorConsts.PROP_WIZARD_NAME), //
        OUTPUT_FILE_EXT(ImportExportGeneratorConsts.PROP_OUTPUT_FILE_EXT), //
        XSL(ImportExportGeneratorConsts.PROP_XSL),
        // comma-separate list of kinds
        SPECIAL_FOLDER_KINDS(
                ImportExportGeneratorConsts.PROP_SPECIALFOLDER_FILTER),
        // comma-separate list of extensions
        INPUT_FILE_EXT(ImportExportGeneratorConsts.PROP_FILE_EXT_FILTER);

        private final String id;

        private CustomImportProperty(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    private static final ImportWizardRegistry INSTANCE =
            new ImportWizardRegistry();

    private ImportWizardRegistry() {
        // Private constructor
        propertiesMap = new HashMap<IWizardDescriptor, Properties>();
    }

    /**
     * Get the singleton instance of this registry.
     * 
     * @return
     */
    public static ImportWizardRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Get all custom import wizards.
     * 
     * @return
     */
    public Collection<IWizardDescriptor> getImportWizards() {
        if (wizardDescriptors == null) {
            IWizardRegistry wizardRegistry =
                    PlatformUI.getWorkbench().getImportWizardRegistry();

            wizardDescriptors = getWizards(wizardRegistry.getRootCategory());
        }
        return wizardDescriptors;
    }

    /**
     * Get a property value from the contribution.
     * 
     * @param descriptor
     * @param prop
     * @return
     * @throws IOException
     */
    public String getProperty(IWizardDescriptor descriptor,
            CustomImportProperty prop) throws IOException {
        if (descriptor != null) {
            Properties properties = propertiesMap.get(descriptor);
            if (properties != null) {
                return properties.getProperty(prop.getId());
            }
        }
        return null;
    }

    /**
     * Get the import wizards that extend the {@link CustomImportWizard} class.
     * 
     * @param category
     * @return
     */
    private List<IWizardDescriptor> getWizards(IWizardCategory category) {
        List<IWizardDescriptor> descriptors =
                new ArrayList<IWizardDescriptor>();

        for (IWizardDescriptor descriptor : category.getWizards()) {
            try {
                IWorkbenchWizard wizard = descriptor.createWizard();
                if (wizard instanceof CustomImportWizard) {
                    descriptors.add(descriptor);
                    Properties props =
                            ((CustomImportWizard) wizard).getImportProperties();
                    // Get the properties of the custom wizard
                    if (props != null) {
                        propertiesMap.put(descriptor, props);
                    }
                }
            } catch (CoreException e) {
                RCPActivator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Error in accessing class of import wizard '%s'.", //$NON-NLS-1$
                                        descriptor.getId()));
            }
        }

        for (IWizardCategory cat : category.getCategories()) {
            descriptors.addAll(getWizards(cat));
        }

        return descriptors;
    }

}
