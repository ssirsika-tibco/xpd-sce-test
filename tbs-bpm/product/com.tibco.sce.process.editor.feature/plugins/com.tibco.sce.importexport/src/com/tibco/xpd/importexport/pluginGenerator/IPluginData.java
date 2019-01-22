/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.io.File;

import com.tibco.xpd.importexport.ImportExportGeneratorConsts;

/**
 * Interface to be implemented by the class that will provide all the plug-in /
 * wizard information that the <code>{@link PluginGenerator}</code> will use to
 * generate an import/export wizard plug-in.
 * 
 * @author njpatel
 * 
 */
public interface IPluginData {

    /*
     * Expected properties in the custom import/export plugin.properties file,
     * to be replaced by the input provided in the UI when generating the custom
     * wizard
     */
    public static final String PROP_WIZARD_ID =
            ImportExportGeneratorConsts.PROP_WIZARD_ID;

    public static final String PROP_WIZARD_NAME =
            ImportExportGeneratorConsts.PROP_WIZARD_NAME;

    public static final String PROP_WIZARD_DESCRIPTION =
            ImportExportGeneratorConsts.PROP_WIZARD_DESCRIPTION;

    public static final String PROP_OUTPUT_FILE_EXT =
            ImportExportGeneratorConsts.PROP_OUTPUT_FILE_EXT;

    public static final String PROP_XSL = ImportExportGeneratorConsts.PROP_XSL;

    public static final String PROP_ICON =
            ImportExportGeneratorConsts.PROP_ICON;

    public static final String PROP_EXPORT_FOLDER = "wizard.exportFolder"; //$NON-NLS-1$

    public static final String PROP_WIZARD_CATEGORY = "wizard.category"; //$NON-NLS-1$

    public static final String PROP_SPECIALFOLDER_FILTER =
            ImportExportGeneratorConsts.PROP_SPECIALFOLDER_FILTER;

    public static final String PROP_FILE_EXT_FILTER =
            ImportExportGeneratorConsts.PROP_FILE_EXT_FILTER;

    /**
     * Get the plug-in data provider.
     * 
     * @return
     */
    public IPluginDataProvider getPluginDataProvider();

    /**
     * Get the wizard data provider.
     * 
     * @return
     */
    public IWizardDataProvider getWizardDataProvider();

    /**
     * Get the wizard tree viewer filter provider.
     * 
     * @return
     */
    public ITreeViewerFilterProvider getTreeViewerFilterProvider();

    /**
     * Interface to be implemented by the class that will provide the plugin
     * data to the <code>PluginGenerator</code> to generate a new import/export
     * wizard plugin.
     * 
     * @author njpatel
     * 
     */
    public interface IPluginDataProvider {

        /**
         * Get the ID of the plug-in.
         * 
         * @return ID
         */
        public String getPluginId();

        /**
         * Get the name of the plug-in.
         * 
         * @return Name
         */
        public String getPluginName();

        /**
         * Get the version of the plug-in
         * 
         * @return Version
         */
        public String getPluginVersion();

        /**
         * Get the vendor of the plug-in.
         * 
         * @return Vendor
         */
        public String getPluginVendor();

    }

    /**
     * Interface to be implemented by a class that will provide the wizard data
     * to the <code>PluginGenerator</code> to generate a new import/export
     * wizard plugin.
     * 
     * @author njpatel
     * 
     */
    public interface IWizardDataProvider {
        /**
         * Get the title of the wizard
         * 
         * @return Title.
         */
        public String getWizardTitle();

        /**
         * Get the description of the wizard
         * 
         * @return Description, <code>null</code> if no description required.
         */
        public String getWizardDescription();

        /**
         * Get the file extension of the output file.
         * 
         * @return File extension without leading extension separator.
         */
        public String getOutputFileExt();

        /**
         * Get the category in which to add the import/export wizard.
         * 
         * @return Category ID, <code>null</code> if not to be added into any
         *         existing category.
         */
        public String getWizardCategoryId();

        /**
         * Get the XSLT file that will be used to transform the resources being
         * imported/exported.
         * 
         * @return XSLT file
         */
        public File getXsltFile();

        /**
         * Get the schema (DTD / XSD) that the XSLT requires.
         * 
         * @return Schema file, <code>null</code> if not required.
         */
        public File getSchemaFile();

        /**
         * Get the icon file. This will be the icon used in the import/export
         * listing.
         * 
         * @return Icon file, <code>null</code> if not required.
         */
        public File getWizardIconFile();

        /*
         * Extra data for export wizards only
         */

        /**
         * Get the export workspace folder that the exported file will be added
         * to if project destination is used in the wizard.
         * 
         * @return Export workspace folder name
         */
        public String getExportWorkspaceFolder();

    }

    /**
     * Interface to be implemented by a class that will provide the wizard tree
     * viewer filter data to the <code>PluginGenerator</code> to generate a new
     * import/export wizard plugin.
     * 
     * @author njpatel
     * 
     */
    public interface ITreeViewerFilterProvider {
        /**
         * Get the special folder kinds that the tree view in the import/export
         * folder should show.
         * 
         * @return Special folder kind string, comma delimeted. Returns
         *         <code>null</code> if all special folders should be displayed,
         *         or an empty string if all special folders should be filtered
         *         out.
         */
        public String getTreeViewSpecialFolderFilter();

        /*
         * Export wizard only
         */
        /**
         * Get the file extension filter string that will be used to filter the
         * files shown in the tree view in the export wizard.
         * 
         * @return File extension filters, comma delimeted and without leading
         *         extension separator. Returns <code>null</code> if no file
         *         extension filtering is required.
         */
        public String getTreeViewFileExtensionFilter();
    }
}
