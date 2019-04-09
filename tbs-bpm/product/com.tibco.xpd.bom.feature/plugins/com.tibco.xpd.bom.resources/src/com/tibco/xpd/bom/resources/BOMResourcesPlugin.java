package com.tibco.xpd.bom.resources;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.uml2.uml.Type;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class BOMResourcesPlugin extends AbstractUIPlugin {

    // BOM version
    /*
     * XPD-5058: BOM migration for latest UML version
     * 
     * Following upgrade of UML models to the latest UML2 Version 3.0.0 for
     * eclipse 3.7 platform, the BOM models should be migrated to refer to this
     * latest UML2 version. Updating the BOM model version will trigger save
     * action which saves the UML namespace from the loaded BOM model, which is
     * the latest. No explicit extension is required. The same case is for UML2
     * version moving to 5.0.0 in eclipse 4.7 (hance the version was moved
     * another notch - to 5).
     * 
     * Sid ACE-467 - bumped BOM format version to 6 to exnsure migrations from
     * BPM to CE envs are done.
     */
    public static final String BOM_VERSION = "6"; //$NON-NLS-1$

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.bom.resources"; //$NON-NLS-1$

    // BOM file extension
    public static final String BOM_FILE_EXTENSION = "bom"; //$NON-NLS-1$

    // Special folder kind
    public static final String BOM_SPECIAL_FOLDER_KIND = "bom"; //$NON-NLS-1$

    public static final String BOM_INDEXER_ID =
            "com.tibco.xpd.bom.resources.indexing.bomIndexer"; //$NON-NLS-1$

    public static final String INDEXER_ATTRIBUTE_CASE_OR_GLOBAL_TYPE =
            "CaseOrGlobalType"; //$NON-NLS-1$

    public static final String INDEXER_ATTRIBUTE_IMAGE_URI = "image_uri"; //$NON-NLS-1$

    public static final String REQ_FRAGMENT_DRAG = "fragmentsDrag"; //$NON-NLS-1$

    /** Default name of the generated BOM folder. */
    public static final String GENERATED_BOM_SF_NAME =
            Messages.BOMResourcesPlugin_GeneratedBomSFName_label;

    public static String ModelEannotationMetaSource = "model.metadata"; //$NON-NLS-1$

    public static String ModelEannotationMetaSource_author = "author"; //$NON-NLS-1$

    public static String ModelGlobalDataProfile_attribute_Tag = "Tag"; //$NON-NLS-1$

    public static String ModelGlobalDataProfile_attribute_Searchable =
            "Searchable"; //$NON-NLS-1$

    public static String ModelGlobalDataProfile_Fetching_BatchSize =
            "batchSize"; //$NON-NLS-1$

    public static String ModelGlobalDataProfile_case_documentation_enabled =
            "CaseDocumentationEnabled"; //$NON-NLS-1$

    public static String ModelGlobalDataProfile_CaseClass_Summary =
            "summaryAttributes"; //$NON-NLS-1$

    public static String ModelEannotationMetaSource_datecreated = "created"; //$NON-NLS-1$

    public static String ModelEannotationMetaSource_version = "version"; //$NON-NLS-1$

    public static String ModelEannotationMetaSource_checksum = "checksum"; //$NON-NLS-1$

    public static String ElementEannotationMetaSource = "element.metadata"; //$NON-NLS-1$

    public static String ElementEannotationMetaSource_TopLevelElement = "TLE"; //$NON-NLS-1$

    public static String BOM_TRUE_FLAG = "true"; //$NON-NLS-1$

    public static String BOM_FALSE_FLAG = "false"; //$NON-NLS-1$

    public static final String ENUM_LIT_DOMAIN_ANNOTATION_SOURCE = "domain"; //$NON-NLS-1$

    /** The (inline) schema validation (in wsdl files) marker type. */
    public static final String WSDL_SCHEMA_MARKER_TYPE =
            "com.tibco.xpd.bom.xsdtransform.inlineSchemaValidationMarker"; //$NON-NLS-1$

    /**
     * moved this constant from bom/xsdtransform/Activator to avoid dependency
     * of bom.modeler.custom plugin on bom.xsdtransform plugin
     */
    public final static String PATHMAP_XSDNOTATION_PROFILE =
            "pathmap://XSD_NOTATION_TYPES/XsdNotation.profile.uml"; //$NON-NLS-1$

    public static final String BOS_PREFERENCES_ID =
            "com.tibco.xpd.bos.ui.preferences.BosPreferencePage"; //$NON-NLS-1$

    public static final String P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION =
            "keepNamespaceFileExtension"; //$NON-NLS-1$

    /**
     * Parameter key that can be used to set the default values of an element
     * being created using the <code>CreateElementRequest</code>.
     */
    public static final class CREATE_ELEMENT_REQUEST_PARAM {

        /**
         * Set default name of the element. Expected value is
         * <code>String</code>.
         */
        public static final String NAME = "default_name"; //$NON-NLS-1$

        /**
         * Set the default type of the element (e.g. property type). Expected
         * value is {@link Type}.
         */
        public static final String TYPE = "type"; //$NON-NLS-1$

        /**
         * Set the Stereotype of the element.
         */
        public static final String STEREOTYPE = "stereotype"; //$NON-NLS-1$

        /**
         * Set the default navigability for associations. Expected value is
         * <code>boolean</code>. If set to <code>false</code> then the
         * navigability will be set from source to target, otherwise it will
         * default to both ways.
         */
        public static final String SOURCE_IS_NAVIGABLE = "sourceNavigable"; //$NON-NLS-1$
    }

    // The shared instance
    private static BOMResourcesPlugin plugin;

    /**
     * The constructor
     */
    public BOMResourcesPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
     * BundleContext )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
     * BundleContext )
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
    public static BOMResourcesPlugin getDefault() {
        return plugin;
    }

    /**
     * Get the BOM indexer service.
     * 
     * @return BOM indexer service
     */
    public BOMIndexerService getIndexerService() {
        return BOMIndexerService.getInstance();
    }

    /**
     * Plugin logger.
     * 
     * @return Logger
     */
    public Logger getLogger() {
        return LoggerFactory.createLogger(PLUGIN_ID);
    }

}
