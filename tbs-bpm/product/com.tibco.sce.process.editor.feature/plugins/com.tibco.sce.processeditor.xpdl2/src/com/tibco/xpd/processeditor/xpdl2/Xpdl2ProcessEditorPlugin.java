package com.tibco.xpd.processeditor.xpdl2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcherExtensionPointHelper;
import com.tibco.xpd.processeditor.xpdl2.preferences.AutoMapRulesPreferencePage;
import com.tibco.xpd.processeditor.xpdl2.properties.script.MappingGrammarConvertorContributionManager;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.Xpdl2ProcessWidgetAdapterFactory;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.impl.ProcessWidgetImpl;
import com.tibco.xpd.processwidget.parts.EditPartUtil;
import com.tibco.xpd.processwidget.parts.ModelAdapterEditPart;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The main plugin class to be used in the desktop.
 */
public class Xpdl2ProcessEditorPlugin extends AbstractUIPlugin {

    public static final String ID = "com.tibco.xpd.processeditor.xpdl2"; //$NON-NLS-1$

    public static final String ALWAYS_SET_SOLUTIONDESIGN_CAPABILITY =
            "alwaysSetSolutionDesignCapability"; //$NON-NLS-1$

    // The shared instance.
    private static Xpdl2ProcessEditorPlugin plugin;

    private Logger logger;

    private MappingGrammarConvertorContributionManager grammarConvertorContributionManager;

    /**
     * The constructor.
     */
    public Xpdl2ProcessEditorPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        getPreferenceStore()
                .setDefault(ProcessEditorConstants.IS_SHOWING_PAGINATION, false);
        getPreferenceStore()
                .setDefault(ProcessEditorConstants.PRINT_PAGE_FIT_TYPE, 1);
        getPreferenceStore().setDefault(ProcessEditorConstants.PRINT_PAGE_ZOOM,
                1d);
        // Initializing the type matcher preferences.
        List<MappingTypeMatcher> contributions =
                MappingTypeMatcherExtensionPointHelper.getContributions();
        for (MappingTypeMatcher mappingTypeMatcher : contributions) {
            Boolean showInPreferencePage =
                    mappingTypeMatcher.getShowInPreferencePage();
            if (showInPreferencePage) {
                String typeMatcherId = mappingTypeMatcher.getId();
                getPreferenceStore()
                        .setDefault(AutoMapRulesPreferencePage.AUTOMAP_PREFIX
                                + typeMatcherId,
                                Boolean.TRUE);
            }
        }

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
    public static Xpdl2ProcessEditorPlugin getDefault() {
        return plugin;
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
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = ProcessEditorConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Create image of given process, the reciever is responible for disposing
     * the image
     * 
     * @param process
     *            process of which image should be prepared
     * @return process image
     */
    public static Image getProcessImage(Process process) {
        Shell sh = new Shell();
        sh.setVisible(false);
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(process);
        ProcessWidget widget =
                new ProcessWidgetImpl(false,
                        calculateProcessWidgetType(process));

        widget.setEditingDomain(ed);
        widget.setAdapterFactory(new Xpdl2ProcessWidgetAdapterFactory());

        /* Input must now be set before control create. */
        widget.setInput(process);
        widget.createControl(sh);

        sh.dispose();
        Image img = widget.createProcessImage();
        return img;
    }

    /**
     * Create an image for the diagram objects represented by the given list or
     * process Model Objects (activities, pools, lanes etc etc).
     * 
     * @param process
     * @param processModelObjects
     * @return
     */
    public static Image getProcessDiagramImage(com.tibco.xpd.xpdl2.Package pkg,
            String processId, Collection<EObject> processModelObjects) {

        Process process = pkg.getProcess(processId);
        if (process == null) {
            System.err
                    .println("Xpdl2ProcessEditorPlugin.getProcessDIagramImage(): Cannot locate process in package."); //$NON-NLS-1$
            return null;
        }

        //
        // Create a process widget for the given process.
        // This has the necessary editparts and figures to display the whole
        // process
        Shell sh = new Shell();
        sh.setVisible(false);
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(process);

        ProcessWidget widget =
                new ProcessWidgetImpl(false,
                        calculateProcessWidgetType(process));

        widget.setAdapterFactory(new Xpdl2ProcessWidgetAdapterFactory());
        widget.setEditingDomain(ed);
        widget.setPreferences(Xpdl2ProcessEditorPlugin.getDefault()
                .getPluginPreferences());

        /* Input must now be set before control create. */
        widget.setInput(process);
        widget.createControl(sh);

        // Make sure widget does first invisible paint job so that all figure
        // locations and boundaries and other info get set up
        widget.getGraphicalViewer().flush();

        //
        // Get a list of edit parts that match the process model objects
        //
        List<Object> pmos = new ArrayList<Object>();
        pmos.addAll(processModelObjects);

        List<EditPart> origEditParts =
                EditPartUtil.getEditPartsForModelObjects(widget
                        .getGraphicalViewer(), pmos, false);

        List<ModelAdapterEditPart> modeEditParts =
                new ArrayList<ModelAdapterEditPart>();
        for (EditPart e : origEditParts) {
            if (e instanceof ModelAdapterEditPart) {
                ModelAdapterEditPart ep = (ModelAdapterEditPart) e;

                modeEditParts.add(ep);
            }
        }

        if (modeEditParts.size() < 1) {
            System.err
                    .println("Xpdl2ProcessEditorPlugin.getProcessDIagramImage(): Cannot locate edit parts for model objects."); //$NON-NLS-1$
            return null;
        }

        //
        // Create the image from the list of edit parts associated with desired
        // diagram model objects.
        Image img1 = EditPartUtil.createImageFromEditParts(modeEditParts);

        sh.dispose();

        return img1;
    }

    public static ProcessWidgetType calculateProcessWidgetType(Process process) {

        if (Xpdl2ModelUtil.isCaseService(process)) {

            return ProcessWidgetType.CASE_SERVICE;
        } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {

            return ProcessWidgetType.BUSINESS_SERVICE;
        } else if (Xpdl2ModelUtil.isPageflow(process)) {

            return ProcessWidgetType.PAGEFLOW_PROCESS;
        } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

            return ProcessWidgetType.SERVICE_PROCESS;
        } else if (XpdlFileContentPropertyTester.isTasksFileContent(process)) {

            return ProcessWidgetType.TASK_LIBRARY;
        } else if (XpdlFileContentPropertyTester
                .isDecisionFlowFileContent(process)) {

            return ProcessWidgetType.DECISION_FLOW;
        }
        return ProcessWidgetType.BPMN_PROCESS;
    }

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(ID);
        }
        return logger;
    }

    public synchronized MappingGrammarConvertorContributionManager getMappingGrammarConvertorContributionManager() {
        if (grammarConvertorContributionManager == null) {
            grammarConvertorContributionManager =
                    new MappingGrammarConvertorContributionManager();
        }
        return grammarConvertorContributionManager;
    }
}
