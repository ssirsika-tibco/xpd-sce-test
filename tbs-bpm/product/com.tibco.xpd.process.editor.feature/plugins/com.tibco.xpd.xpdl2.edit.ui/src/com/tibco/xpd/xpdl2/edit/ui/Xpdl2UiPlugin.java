package com.tibco.xpd.xpdl2.edit.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The main plugin class to be used in the desktop.
 */
public class Xpdl2UiPlugin extends AbstractUIPlugin {

    /**
     * Plugin ID.
     */
    public static final String PLUGIN_ID = "com.tibco.xpd.xpdl2.edit.ui"; //$NON-NLS-1$

    public static final String IMG_BUSINESS_PROCESS =
            "icons/ProcessBusiness.png"; //$NON-NLS-1$

    public static final String IMG_DECISION_FLOW = "icons/DecisionFlow.png"; //$NON-NLS-1$

    public static final String IMG_PAGEFLOW_PROCESS =
            "icons/ProcessPageflow.png"; //$NON-NLS-1$

    public static final String IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS =
            "icons/ProcessBusinessService.png"; //$NON-NLS-1$

    public static final String IMG_CASE_SERVICE_PAGEFLOW_PROCESS =
            "icons/CaseService.png"; //$NON-NLS-1$

    public static final String IMG_SERVICE_PROCESS = "icons/ServiceProcess.png"; //$NON-NLS-1$

    public static final String IMG_PROCESSPACKAGE = "icons/Package.png"; //$NON-NLS-1$

    public static final String IMG_DECISIONFLOWPACKAGE =
            "icons/DecisionFlowPackage.png"; //$NON-NLS-1$

    public static final String IMG_PROCESSINTERFACE =
            "icons/processInterface.gif"; //$NON-NLS-1$

    public static final String IMG_SERVICEPROCESSINTERFACE =
            "icons/ServiceProcessInterface.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_PARAM_IN =
            "icons/InterfaceParamIn.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_PARAM_OUT =
            "icons/InterfaceParamOut.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_PARAM_INOUT =
            "icons/InterfaceParamInOut.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_STARTMETHOD =
            "icons/StartMethod.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_STARTMETHOD_MESSAGE =
            "icons/StartMethodMessage.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_INTERMEDIATEMETHOD =
            "icons/IntermediateMethod.png"; //$NON-NLS-1$

    public static final String IMG_INTERFACE_INTERMEDIATEMETHOD_MESSAGE =
            "icons/IntermediateMethodMessage.png"; //$NON-NLS-1$

    public static final String IMG_TASKDRAWER = "icons/task_drawer_16.png"; //$NON-NLS-1$

    public static final String IMG_FLOW_CONDITIONAL =
            "icons/flow_conditional_16.png"; //$NON-NLS-1$

    public static final String IMG_FLOWDRAWER = "icons/flow_drawer.png"; //$NON-NLS-1$

    public static final String IMG_DATAFIELD_DECLAREDTYPE =
            "icons/DataFieldDeclaredType.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_IN = "icons/FormalParamIn.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_OUT = "icons/FormalParamOut.png"; //$NON-NLS-1$

    public static final String IMG_FORMALPARAM_INOUT =
            "icons/FormalParamInOut.png"; //$NON-NLS-1$

    public static final String IMG_TYPEDECLARATION_DECLAREDTYPE =
            "icons/TypeDeclDeclaredType.png"; //$NON-NLS-1$

    public static final String IMG_WARNING = "icons/warning2.gif"; //$NON-NLS-1$

    public static final String IMG_ERROR = "icons/error.png"; //$NON-NLS-1$

    public static final String IMG_TASK_GROUP = "icons/TaskGroup.gif"; //$NON-NLS-1$

    public static final String IMG_TASK_GROUP_ERROR = "icons/error.gif"; //$NON-NLS-1$

    public static final String IMG_ADD_TASK_TO_GROUP =
            "icons/AddTaskToGroup.gif"; //$NON-NLS-1$

    public static final String IMG_DELETE = "icons/delete.gif"; //$NON-NLS-1$

    public static final String IMG_DATA_FIELDS = "icons/DataField.gif"; //$NON-NLS-1$

    public static final String IMG_DATA_FIELD_PERFORMER =
            "icons/DataFieldPerformer.png"; //$NON-NLS-1$

    public static final String IMG_PARTICIPANT = "icons/Participant.gif"; //$NON-NLS-1$

    /** logger. */
    private Logger log;

    private static String[] pluginImages = new String[] { IMG_PARTICIPANT,
            IMG_BUSINESS_PROCESS, IMG_PAGEFLOW_PROCESS,
            IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS, IMG_SERVICE_PROCESS,
            IMG_CASE_SERVICE_PAGEFLOW_PROCESS, IMG_FLOW_CONDITIONAL,
            IMG_TASKDRAWER, IMG_FLOWDRAWER, IMG_DATAFIELD_DECLAREDTYPE,
            IMG_FORMALPARAM_IN, IMG_FORMALPARAM_OUT, IMG_FORMALPARAM_INOUT,
            IMG_TYPEDECLARATION_DECLAREDTYPE, IMG_WARNING, IMG_ERROR,
            IMG_PROCESSINTERFACE, IMG_SERVICEPROCESSINTERFACE,
            IMG_INTERFACE_PARAM_IN, IMG_INTERFACE_PARAM_OUT,
            IMG_INTERFACE_PARAM_INOUT, IMG_INTERFACE_STARTMETHOD,
            IMG_INTERFACE_STARTMETHOD_MESSAGE,
            IMG_INTERFACE_INTERMEDIATEMETHOD,
            IMG_INTERFACE_INTERMEDIATEMETHOD_MESSAGE, IMG_PROCESSPACKAGE,
            IMG_TASK_GROUP_ERROR, IMG_TASK_GROUP, IMG_ADD_TASK_TO_GROUP,
            IMG_DELETE, IMG_DATA_FIELDS, IMG_DATA_FIELD_PERFORMER,
            IMG_DECISIONFLOWPACKAGE, IMG_DECISION_FLOW };

    // The shared instance.
    private static Xpdl2UiPlugin plugin;

    private Logger logger;

    /**
     * The constructor.
     */
    public Xpdl2UiPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        log = LoggerFactory.createLogger(PLUGIN_ID);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static Xpdl2UiPlugin getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (int i = 0; i < pluginImages.length; i++) {
            reg.put(pluginImages[i], getImageDescriptor(pluginImages[i]));
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

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }
}
