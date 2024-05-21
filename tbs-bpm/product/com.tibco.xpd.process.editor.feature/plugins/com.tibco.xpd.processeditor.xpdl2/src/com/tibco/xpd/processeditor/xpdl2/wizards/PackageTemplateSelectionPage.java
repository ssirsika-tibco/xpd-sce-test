/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.newproject.BusinessProcessAssetConfig;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplate;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplateChildElement;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ParticipantUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.XPDDestPasteCommand;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramUtils;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.processwidget.adapters.CopyPasteScope;
import com.tibco.xpd.processwidget.adapters.ProcessPasteCommand.PasteCancelledByUserException;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Package template selection page
 * 
 * @author njpatel
 */
public class PackageTemplateSelectionPage extends WizardPage implements
        ISelectionChangedListener, IAssetWizardPage, VerifyListener {

    private Button chkUseTemplate;

    private TreeWithImagePreviewComposite templateViewer;

    protected CLabel displayLabel;

    private Text display;

    protected CLabel nameLabel;

    private Text name;

    public static final String PLUGIN_FRAGMENT_ID =
            "com.tibco.xpd.processeditor.fragments"; //$NON-NLS-1$

    private Object configurationObject;

    private Process processObject;

    private static final String DEFAULT_POOL_NAME =
            Messages.PackageTemplateSelectionPage_DefaultPoolName_label;

    private static final String DEFAULT_LANE_NAME =
            Messages.PackageTemplateSelectionPage_DefauleLaneName_label;

    private String processToOpenName;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * List to contain images that are displayed in the table viewer. This
     * images will have to be disposed.
     */
    private List<Image> images = new ArrayList<Image>();

    private Object templateElements;

    private IFragment initialFragment;

    protected boolean nameValid = true;

    protected boolean displayNameValid = true;

    protected String err = null;;

    private HashMap<String, String> destinationEnvs;

    private boolean isPageflow = false;

    private boolean isBusinessService = false;

    private boolean isCaseService = false;

    private boolean isServiceProcess = false;

    /*
     * XPD-2978: resolve the pageflow reference from business process user task
     * 
     * Each time package template elements are added to a target package, we
     * store the map returned by reassignUniqueIds() which is useful when doping
     * final tidy ups.
     */
    private Map<Package, Map<String, EObject>> perTargetPkgOldIdToEObjectMap =
            new HashMap<Package, Map<String, EObject>>();

    /**
     * Default constructor
     */
    public PackageTemplateSelectionPage() {
        super("packageTemplateSelection"); //$NON-NLS-1$

        // configuration object or add it to the package being created-
        setTitle(Messages.PackageTemplateSelectionPage_TITLE);
        setMessage(Messages.PackageTemplateSelectionPage_MESSAGE);

		/* Sid ACE-7330: Show correct wizard banner icon for asset type. */
		setImageDescriptor(Xpdl2ProcessEditorPlugin.imageDescriptorFromPlugin(Xpdl2ProcessEditorPlugin.ID,
				"icons/wizards/NewPackageWizard.png"));

		createProcess();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        PlatformUI
                .getWorkbench()
                .getHelpSystem()
                .setHelp(container,
                        "com.tibco.xpd.process.analyst.doc.CreatePack3"); //$NON-NLS-1$

        Group processNameGroup = new Group(container, SWT.NONE);
        processNameGroup
                .setText(Messages.PackageTemplateSelectionPage_ProcessDetailsGroups_label);
        processNameGroup.setLayout(new GridLayout(2, false));
        processNameGroup.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Add checkbox
        chkUseTemplate = new Button(processNameGroup, SWT.CHECK);
        chkUseTemplate.setText(Messages.PackageTemplateSelectionPage_2);
        chkUseTemplate.setSelection(true);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 2;
        chkUseTemplate.setLayoutData(gridData);
        chkUseTemplate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // Update the template viewer enablement status
                // Ravi-Need to harden validation.
                if (chkUseTemplate.getSelection()) {
                    if (name.getText().equals("")) { //$NON-NLS-1$
                        setErrorMessage(Messages.PackageTemplateSelectionPage_ProcessNameNotSetError_msg);
                        setPageComplete(false);
                    }
                    // Enable the templateViewer and set the current selected
                    // item as the selection
                    templateViewer.setEnabled(true);
                    name.setEnabled(true);
                    // Get the current selection
                    // processCreate = true;
                    createProcess();
                } else {
                    // Disable the templateViewer and set the selected node to
                    // null
                    templateViewer.setEnabled(false);
                    name.setEnabled(false);
                    processObject = null;
                    // processCreate = false;
                }
            }
        });
        // Process Display Name label
        displayLabel = new CLabel(processNameGroup, SWT.NONE);
        displayLabel.setText(Messages.NamedElementSection_displayNameLabel);
        gridData = new GridData();
        displayLabel.setLayoutData(gridData);

        display = new Text(processNameGroup, SWT.BORDER);
        display.setText(Messages.PackageTemplateSelectionPage_ProcessDefault_value);

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.grabExcessHorizontalSpace = true;
        display.setLayoutData(gridData);

        // Process Name label
        nameLabel = new CLabel(processNameGroup, SWT.NONE);
        nameLabel.setText(Messages.NamedElementSection_nameLabel);
        gridData = new GridData();
        nameLabel.setLayoutData(gridData);

        name = new Text(processNameGroup, SWT.BORDER);
        name.setText(Messages.PackageTemplateSelectionPage_ProcessDefault_value);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.grabExcessHorizontalSpace = true;
        name.setLayoutData(gridData);

        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            nameLabel.setVisible(false);
            name.setVisible(false);
        }

        display.addVerifyListener(this);
        name.addVerifyListener(this);

        updateProcessNameName();

        // Add table viewer
        createTemplateViewer(processNameGroup);
        setControl(container);
    }

    /**
     * 
     */
    private void updateProcessNameName() {
        if (getPreviousPage() instanceof PackageInformationPage) {
            String packageFileName =
                    ((PackageInformationPage) getPreviousPage())
                            .getPackageName();
            String nameText =
                    packageFileName
                            + "-" //$NON-NLS-1$
                            + Messages.PackageTemplateSelectionPage_ProcessDefault_value;
            display.setText(nameText);
            name.setText(NameUtil.getInternalName(nameText, false));
            ((PackageInformationPage) getPreviousPage()).getTxtPackageName()
                    .addListener(SWT.Modify, new Listener() {
                        @Override
                        public void handleEvent(Event event) {
                            String packageFileName =
                                    ((PackageInformationPage) getPreviousPage())
                                            .getTxtPackageName().getText();
                            String text =
                                    packageFileName
                                            + "-" //$NON-NLS-1$
                                            + Messages.PackageTemplateSelectionPage_ProcessDefault_value;
                            display.setText(text);
                            name.setText(NameUtil.getInternalName(text, false));
                        }
                    });
        }
    }

    private void createTemplateViewer(Composite composite) {
        /* XPD-2673: Wait for fragment initialise to finish. */
        waitForFragmentInitialisation();

        ProcessTemplateProvider templateProvider =
                new ProcessTemplateProvider(TemplateProviderIdentifier.PACKAGE) {
                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
                     * 
                     * @param interfaceGroup
                     * @return
                     */
                    @Override
                    protected List getApplicableInterfaces(
                            ProcessInterfaceGroup interfaceGroup) {

                        /*
                         * XPD-7298: While creating a new package, by default
                         * Business Process gets created , hence only Process
                         * Interfaces are applicable to be shown in the
                         * template.
                         */
                        List<Object> processInterfaces =
                                new ArrayList<Object>();

                        List children = interfaceGroup.getChildren();

                        for (Object object : children) {

                            if (object instanceof ProcessInterface) {

                                if (Xpdl2ModelUtil
                                        .isProcessInterface((ProcessInterface) object)) {
                                    processInterfaces.add(object);
                                }
                            }
                        }
                        return processInterfaces;
                    }

                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#isApplicableInterfaceType(java.lang.String)
                     * 
                     * @param type
                     * @return
                     */
                    @Override
                    protected boolean isApplicableInterfaceType(
                            ProcessResourceItemType processResourceType) {
                        /*
                         * XPD-7298: While creating a new package, by default
                         * Business Process gets created , hence we should show
                         * only Process Interfaces(NOT SERVICE PROCESS
                         * INTERFACES) in the template.
                         */
                        return ProcessResourceItemType.PROCESSINTERFACE
                                .equals(processResourceType);
                    }
                };

        if (getWizard() instanceof NewPackageWizard) {
            NewPackageWizard newPackageWizard = (NewPackageWizard) getWizard();
            Object selection = newPackageWizard.getSelection();
            if (selection instanceof IStructuredSelection) {
                templateProvider
                        .setSelectedObject(((IStructuredSelection) selection)
                                .getFirstElement());

            }
        }

        if (composite != null) {
            templateViewer =
                    new TreeWithImagePreviewComposite(composite,
                            templateProvider, SWT.NONE);

            GridData gridData = new GridData(GridData.FILL_BOTH);
            gridData.horizontalSpan = 2;

            templateViewer.setLayoutData(gridData);

            templateViewer.addSelectionChangedListener(this);
            if (templateViewer.getSelection() != null) {
                if (templateViewer.getSelection() instanceof TreeSelection) {
                    updateSelection(((TreeSelection) templateViewer
                            .getSelection()).getFirstElement());
                }
            }
        }
    }

    /**
     * XPD-2673: Wait for fragment initialise to finish.
     */
    protected void waitForFragmentInitialisation() {
        final Job[] fragmentsInitialiseJobs =
                Job.getJobManager()
                        .find(FragmentsActivator.FRAGMENTS_INITIALISE_JOB_FAMILY);

        if (fragmentsInitialiseJobs != null
                && fragmentsInitialiseJobs.length > 0) {

            /* Should only ever be one job! */
            try {
                IRunnableWithProgress runnable = new IRunnableWithProgress() {

                    @Override
                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {
                        monitor.beginTask(Messages.PackageTemplateSelectionPage_WaitingForFragmentsInitialise_message,
                                IProgressMonitor.UNKNOWN);

                        monitor.worked(1);

                        fragmentsInitialiseJobs[0].join();

                        monitor.done();

                    }
                };

                ProgressMonitorDialog monitorDialog =
                        new ProgressMonitorDialog(getShell());
                try {
                    monitorDialog.run(true, false, runnable);
                } catch (InvocationTargetException e) {
                    Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
                }

            } catch (InterruptedException e) {
                Xpdl2ProcessEditorPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                "Interrupted exception caught waiting for fragments initialise."); //$NON-NLS-1$
            }
        }
    }

    @Override
    public void dispose() {
        // Dispose off the images
        for (Image img : images) {
            img.dispose();
        }
        images.clear();

        if (templateViewer != null)
            templateViewer.dispose();

        super.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
     * org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {

        if (event.getSelection() instanceof IStructuredSelection) {
            IStructuredSelection selection =
                    (IStructuredSelection) event.getSelection();
            // Get the first selection
            updateSelection(selection.getFirstElement());
        }
    }

    /**
     * Update the current selection to <i>ext</i>. This will set the selected
     * wizard node to the wizard in the extension.
     * 
     * @param ext
     */
    private void updateSelection(Object selectedObject) {
        if (selectedObject instanceof IFragment) {
            IFragment templateElement = (IFragment) selectedObject;
            templateElements =
                    Xpdl2ProcessorUtil.getFragmentDropObjects(templateElement
                            .getLocalizedData());
            destinationEnvs =
                    Xpdl2ProcessorUtil.getDestinationEnvs(templateElement);
            processToOpenName = null;
            isPageflow = Xpdl2ProcessorUtil.isPageflow(templateElement);
            isBusinessService =
                    Xpdl2ProcessorUtil.isBusinessService(templateElement);
            isCaseService = Xpdl2ProcessorUtil.isCaseService(templateElement);
            isServiceProcess =
                    Xpdl2ProcessorUtil.isServiceProcess(templateElement);
            enableNameLabel();
        } else if (selectedObject instanceof ProcessInterface) {
            templateElements = selectedObject;
            processToOpenName = null;
            enableNameLabel();
        } else if (selectedObject instanceof PackageTemplate) {
            templateElements = selectedObject;
            processToOpenName = null;
            disableNameLabel();
        } else if (selectedObject instanceof PackageTemplateChildElement) {
            templateElements =
                    ((PackageTemplateChildElement) selectedObject).getParent();
            processToOpenName =
                    ((Process) ((PackageTemplateChildElement) selectedObject)
                            .getAdapter(EObject.class)).getName();
            disableNameLabel();
        } else {
            templateElements = null;
            processToOpenName = null;
            enableNameLabel();
        }
    }

    private void disableNameLabel() {
        if (name != null) {
            name.setEnabled(false);
            if (name.getText() == null
                    || (name.getText() != null && name.getText().length() < 1)) {
                updateProcessNameName();
            }
        }
        if (display != null) {
            display.setEnabled(false);
        }
    }

    private void enableNameLabel() {
        if (name != null) {
            name.setEnabled(true);
        }
        if (display != null) {
            display.setEnabled(true);
        }
    }

    @Override
    public void init(Object config) {
        this.configurationObject = config;
        createProcess();
        getInitialSelection();
        if (initialFragment != null) {
            updateSelection(initialFragment);
        }
    }

    @Override
    public void updateConfiguration() {
        if (processObject != null
                && configurationObject instanceof BusinessProcessAssetConfig) {
            BusinessProcessAssetConfig config =
                    (BusinessProcessAssetConfig) configurationObject;
            config.setProcess(processObject);
            if (config.getXpdl2Package() != null) {
                Package xpdl2Package = config.getXpdl2Package();
                xpdl2Package.getProcesses().add(processObject);
                String nameText;
                String displayName;
                if (name != null) {
                    nameText = name.getText();
                    displayName = display.getText();
                } else {
                    displayName =
                            xpdl2Package.getName()
                                    + "-" //$NON-NLS-1$
                                    + Messages.PackageTemplateSelectionPage_ProcessDefault_value;
                    nameText = NameUtil.getInternalName(displayName, false);
                }
                processObject.setName(nameText);
                Xpdl2ModelUtil.setOtherAttribute(processObject,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        displayName);
                if (templateElements != null) {
                    addTemplateElements(xpdl2Package, processObject);

                } else {
                    initializeProcess(processObject);
                }

                /* MR 39946 - add project destinations to Process */
                /*
                 * moved this to fixFinalPackage to add destination to each
                 * process if a package template is selected in the wizard (a
                 * package template would have more than one process)
                 */
                // addProjectDestinationsToProcess(processObject);

                /*
                 * XPD-745: fix final package for message start event endpoint
                 * location references. add project destination to process(es)
                 * selected. and user task referencing any pageflow
                 */
                fixFinalPackage(xpdl2Package, config.getPackageFileName());

                config.setProcessToOpen(processToOpenName);
            }
        }
    }

    /**
     * @param xpdl2Package
     * @param xpdlFileName
     */
    private void fixFinalPackage(Package xpdl2Package, String xpdlFileName) {

        Map<String, EObject> oldProcessIdToNewProcessMap =
                perTargetPkgOldIdToEObjectMap.get(xpdl2Package);

        for (Process process : xpdl2Package.getProcesses()) {
            /**
             * if it is a business service pageflow, then fix the default
             * category
             */
            if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                String projectName = getProjectName();
                if (null != projectName) {
                    String defaultCategory =
                            Xpdl2ModelUtil
                                    .getBusinessServiceDefaultCategory(projectName,
                                            xpdl2Package.getName());
                    Xpdl2ModelUtil.setOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory(),
                            defaultCategory);
                }
            }
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity act : activities) {
                // SID XPD-745 - use xpdl FILE name not package name for wsdl.
                String wsdlFileName;
                int i = xpdlFileName.lastIndexOf("."); //$NON-NLS-1$
                if (i >= 0) {
                    wsdlFileName = xpdlFileName.substring(0, i);
                } else {
                    wsdlFileName = xpdlFileName;
                }

                wsdlFileName += ".wsdl"; //$NON-NLS-1$

                fixGeneratedWsdlLocation(act, wsdlFileName);

                /*
                 * XPD-2978: resolve the pageflow reference from business
                 * process user task
                 */
                if (act.getImplementation() instanceof Task
                        && ((Task) act.getImplementation()).getTaskUser() != null) {

                    /*
                     * ExtendedAttribute bpmJspTask is deprecated so now we must
                     * also update the form URI.
                     */
                    FormImplementation fi =
                            TaskObjectUtil.getUserTaskFormImplementation(act);

                    if (fi != null) {
                        String formURI = fi.getFormURI();
                        if (formURI != null && formURI.length() > 0) {
                            String newURI =
                                    updateFormURI(xpdlFileName,
                                            oldProcessIdToNewProcessMap,
                                            formURI);
                            if (newURI != null) {
                                fi.setFormURI(newURI);
                            }
                        }
                    }

                    /*
                     * Then deal with the deprecated (but still supported for
                     * now @ v3.2) Ext Attr
                     */
                    ExtendedAttribute extAtt =
                            TaskObjectUtil.getExtendedAttributeByName(act,
                                    TaskObjectUtil.USER_TASK_ATTR);
                    if (extAtt != null) {
                        String jspURL = extAtt.getValue();
                        String newURI =
                                updateFormURI(xpdlFileName,
                                        oldProcessIdToNewProcessMap,
                                        jspURL);

                        if (newURI != null) {
                            extAtt.setValue(newURI);
                        }
                    }
                }
            }

            /*
             * SID XPD-745: If there is a process api participant then fix it.
             */
            fixApiParticipantName(process);
            /* add destination to process */
            addProjectDestinationsToProcess(process);

        }

        return;
    }

    /**
     * @param xpdlFileName
     * @param oldProcessIdToNewProcessMap
     * @param formURI
     * @return
     */
    private String updateFormURI(String xpdlFileName,
            Map<String, EObject> oldProcessIdToNewProcessMap, String formURI) {
        String newURI = null;

        int idPart = formURI.lastIndexOf("#"); //$NON-NLS-1$
        if (idPart >= 0) {
            String id = formURI.substring(idPart + 1);

            EObject refdObject = oldProcessIdToNewProcessMap.get(id);
            if (refdObject instanceof Process) {

                URI uri1 = URI.createURI(xpdlFileName); //$NON-NLS-1$
                uri1 = uri1.appendFragment(((Process) refdObject).getId());

                newURI = uri1.toString();

            }
        }
        return newURI;
    }

    /**
     * Change the name of the process API particiapnt for process (if there is
     * one, to correcct defaut name for it.
     * 
     * @param process
     */
    private void fixApiParticipantName(Process process) {
        Participant apiParticipant =
                Xpdl2ModelUtil.getProcessApiActivityParticipant(process);
        if (apiParticipant != null && process.getPackage() != null) {
            String particLabel =
                    ParticipantUtil
                            .getDefaultParticipantLabelForProcessApi(Xpdl2ModelUtil
                                    .getDisplayNameOrName(process),
                                    Xpdl2ModelUtil.getDisplayNameOrName(process
                                            .getPackage()));

            String particName =
                    ParticipantUtil
                            .getDefaultParticipantNameForProcessApi(process
                                    .getName());

            Xpdl2ModelUtil
                    .setOtherAttribute(apiParticipant,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            particLabel);
            apiParticipant.setName(particName);
        }

        return;
    }

    /**
     * @param act
     * @param string
     */
    private void fixGeneratedWsdlLocation(Activity act, String wsdlFileName) {
        /*
         * SID XPD-745 - have to assume that any biz service send task in a
         * template will be a reference to a wsdl generated by the package in
         * template - as well as generated request activity or reply activity.
         */
        if (ReplyActivityUtil.isReplyActivity(act)
                || Xpdl2ModelUtil.isGeneratedRequestActivity(act)
                || WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(act)) {

            WebServiceOperation wso =
                    Xpdl2ModelUtil.getWebServiceOperation(act);
            PortTypeOperation pto = Xpdl2ModelUtil.getPortTypeOperation(act);
            if (null != wso) {
                Service ws = wso.getService();

                ExternalReference wsdlLocationRef =
                        getServiceEndPointLocation(ws);
                ExternalReference ptoLocationRef =
                        getServiceEndPointLocation(pto);

                // replace external reference in the
                // WebServciceOperation and PortTypeOperation
                if (null != wsdlLocationRef
                        && null != wsdlLocationRef.getLocation()) {
                    wsdlLocationRef.setLocation(wsdlFileName);
                }

                if (null != ptoLocationRef
                        && null != ptoLocationRef.getLocation()) {
                    ptoLocationRef.setLocation(wsdlFileName);
                }
            }

        }

        return;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Service/EndPoint/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(Service ws) {
        if (ws != null) {
            EndPoint endPoint = ws.getEndPoint();
            if (endPoint != null) {
                ExternalReference externalReference =
                        endPoint.getExternalReference();
                if (externalReference != null) {
                    return externalReference;
                }
            }
        }

        return null;
    }

    /**
     * @param ws
     * @return The external reference location from
     *         Activity/xpdExt:PortTypeOperation/ExternalReference.
     */
    private static ExternalReference getServiceEndPointLocation(
            PortTypeOperation portTypeOperation) {

        if (portTypeOperation != null) {
            ExternalReference externalReference =
                    portTypeOperation.getExternalReference();

            if (externalReference != null) {
                return externalReference;
            }
        }

        return null;
    }

    private void addProjectDestinationsToProcess(Process process) {
        Command command = null;
        CompoundCommand cCmd = new CompoundCommand();
        TransactionalEditingDomain editingDomain =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        ProjectDetails projectDetails = getProjectDetails();
        if (null != projectDetails) {
            Destinations globalDestinations =
                    projectDetails.getGlobalDestinations();
            if (globalDestinations != null) {
                EList<Destination> destinations =
                        globalDestinations.getDestination();
                if (destinations.size() > 0) {
                    for (Destination destination : destinations) {
                        String type = destination.getType();
                        command =
                                DestinationUtil
                                        .getSetDestinationEnabledCommand(editingDomain,
                                                process,
                                                type,
                                                true);
                        cCmd.append(command);
                    }
                } else {
                    IProject project = WorkingCopyUtil.getProjectFor(process);
                    command =
                            DestinationUtil
                                    .getSetDestinationEnabledCommand(editingDomain,
                                            process,
                                            UserInfoUtil
                                                    .getProjectPreferences(project)
                                                    .getDestination(),
                                            true);
                }
            }
            if (!cCmd.isEmpty() && cCmd.canExecute()) {
                editingDomain.getCommandStack().execute(cCmd);
            }
        }

    }

    public ProjectDetails getProjectDetails() {
        IWizard wizard = super.getWizard();
        if (wizard instanceof IProjectDetailsProvider) {
            IProjectDetailsProvider provider = (IProjectDetailsProvider) wizard;
            ProjectDetails projectDetails = provider.getProjectDetails();
            return projectDetails;
        }
        return null;
    }

    public String getProjectName() {
        IWizard wizard = super.getWizard();
        if (wizard instanceof IProjectDetailsProvider) {
            IProjectDetailsProvider provider = (IProjectDetailsProvider) wizard;
            return provider.getProjectName();
        }
        return null;
    }

    public Object getTemplateElements() {
        return templateElements;
    }

    public void addTemplateElements(Package processPkg, Process process) {

        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();

        if (templateElements instanceof Collection) {
            Collection<EObject> elementsToAdd =
                    (Collection<EObject>) templateElements;
            CopyPasteScope scope =
                    Xpdl2ProcessDiagramUtils.getCopyPasteScope(elementsToAdd);
            EObject targetObject = null;

            Pool pool = getRelevantPool(process);
            Point startPoint = null;
            Lane lane = null;

            if (pool.getLanes().isEmpty())
                lane = createLane(pool);
            else
                lane = pool.getLanes().get(0);

            if (scope.getCopyScope() == CopyPasteScope.COPY_ACTIVITIES_AND_ARTIFACTS) {
                targetObject = lane;
                startPoint = getStartingPoint(process, elementsToAdd);
            }
            if (scope.getCopyScope() == CopyPasteScope.COPY_LANES) {
                targetObject = pool;
                pool.getLanes().remove(lane);
                startPoint = new Point(20, 40);
            }
            if (scope.getCopyScope() == CopyPasteScope.COPY_POOLS) {
                targetObject = process;
                process.getPackage().getPools().remove(pool);
                startPoint = new Point(20, 40);
            }
            if (isPageflow) {

                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_XpdModelType(),
                        XpdModelType.PAGE_FLOW);
            }
            if (isBusinessService) {

                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PublishAsBusinessService(),
                        Boolean.TRUE);

            }
            if (isCaseService) {

                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_IsCaseService(),
                        Boolean.TRUE);

            }

            if (isServiceProcess) {

                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_XpdModelType(),
                        XpdModelType.SERVICE_PROCESS);
                ServiceProcessConfiguration serviceProcessConfiguration =
                        XpdExtensionFactory.eINSTANCE
                                .createServiceProcessConfiguration();
                serviceProcessConfiguration.setDeployToPageflowRuntime(false);
                serviceProcessConfiguration.setDeployToProcessRuntime(true);

                Xpdl2ModelUtil.setOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ServiceProcessConfiguration(),
                        serviceProcessConfiguration);
            }

            if (startPoint != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.PackageTemplateSelectionPage_AddTemplateElements_shortdesc);
                cmd.append(Xpdl2ProcessDiagramUtils
                        .getAddDiagramObjectsCommand(ed,
                                process,
                                targetObject,
                                startPoint,
                                elementsToAdd,
                                null,
                                false,
                                true));
                XPDDestPasteCommand destinationPasteCommand =
                        new XPDDestPasteCommand(ed, process, destinationEnvs);
                if (destinationPasteCommand.canExecute()) {
                    cmd.append(destinationPasteCommand);
                }

                if (cmd.canExecute()) {
                    try {
                        cmd.execute();
                    } catch (PasteCancelledByUserException e) {
                        LOG.info("Paste Cancelled by User"); //$NON-NLS-1$
                    }

                } else {
                    LOG.error("PackageTemplateSelectionPage: Command cannot execute"); //$NON-NLS-1$
                }
            }
        } else if (templateElements instanceof ProcessInterface) {
            ProcessInterface processInterface =
                    (ProcessInterface) templateElements;
            ImplementedInterface implementedInterface =
                    XpdExtensionFactory.eINSTANCE.createImplementedInterface();

            WorkingCopy workingCopyOfInterface =
                    WorkingCopyUtil.getWorkingCopyFor(processInterface);
            WorkingCopy workingCopyOfContainer =
                    WorkingCopyUtil.getWorkingCopyFor(processPkg);

            if (workingCopyOfInterface != null
                    && workingCopyOfInterface != workingCopyOfContainer) {
                IFile packageFile =
                        (IFile) workingCopyOfInterface.getEclipseResources()
                                .get(0);
                String fileNameWithoutExtension = null;
                if (packageFile != null) {
                    String fileName = packageFile.getName();
                    fileNameWithoutExtension =
                            fileName.substring(0, fileName.indexOf('.'));

                }

                implementedInterface.setPackageRef(fileNameWithoutExtension);

                ExternalPackage externalInterfacePackage =
                        Xpdl2WorkingCopyImpl
                                .createExternalPackage(workingCopyOfInterface);

                processPkg.getExternalPackages().add(externalInterfacePackage);

            }
            implementedInterface
                    .setProcessInterfaceId(processInterface.getId());
            Xpdl2ModelUtil.setOtherElement(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ImplementedInterface(),
                    implementedInterface);

            Lane lane =
                    ImplementInterfaceUtil
                            .getRelevantLane(ImplementInterfaceUtil
                                    .getRelevantPool(process, null), null);
            Point initialPoint = new Point(60, 60);
            /*
             * Setting the positions on the start method while creating a
             * process from a Process Interface.
             */
            List<StartMethod> startMethods = processInterface.getStartMethods();
            CompoundCommand cmd = new CompoundCommand();
            for (StartMethod startMethod : startMethods) {
                Command addImplementedStartMethodCommand =
                        ImplementInterfaceUtil
                                .getAddImplementedStartMethodCommand(ed,
                                        process,
                                        startMethod,
                                        initialPoint,
                                        true);
                if (addImplementedStartMethodCommand != null) {
                    cmd.append(addImplementedStartMethodCommand);
                }
                initialPoint.y = initialPoint.y + 120;

            }
            if (startMethods.size() > 3) {
                NodeGraphicsInfo ngi = Xpdl2ModelUtil.getNodeGraphicsInfo(lane);

                ngi.setHeight(80 + startMethods.size() * 80);
            }

            initialPoint = new Point(180, 120);
            List<IntermediateMethod> intermediateMethods =
                    processInterface.getIntermediateMethods();

            for (IntermediateMethod intermediateMethod : intermediateMethods) {
                cmd.append(ImplementInterfaceUtil
                        .getAddImplementedIntermediateMethodCommand(ed,
                                process,
                                intermediateMethod,
                                initialPoint,
                                true));

                initialPoint.x = initialPoint.x + 100;

                /* expand width if more intermediate events. */
            }
            if (cmd.canExecute()) {
                cmd.execute();
            }
            /* expand width if more intermediate events. */
        } else if (templateElements instanceof PackageTemplate) {
            PackageTemplate pkgTemplate = (PackageTemplate) templateElements;
            pkgTemplate.addTemplateElementsToPackage(pkgTemplate, processPkg);

            if (!processPkg.getProcesses().isEmpty()) {
                processObject = processPkg.getProcesses().get(0);
                if (configurationObject instanceof BusinessProcessAssetConfig) {
                    BusinessProcessAssetConfig businessProcessAssetConfig =
                            (BusinessProcessAssetConfig) configurationObject;
                    businessProcessAssetConfig.setProcess(null);
                }
            }
            /*
             * XPD-2978: resolve the pageflow reference from business process
             * user task
             */
            perTargetPkgOldIdToEObjectMap.putAll(pkgTemplate
                    .getPerTargetPkgOldIdToEObjectMap());
        }

    }

    /**
     * This method calculates y co-ordinate of the template being pasted into
     * the process. It also set the lane height appropriately in case the
     * template elements extend over it.
     */
    private Point getStartingPoint(Process process, Collection templateElements) {
        Point startPoint = new Point(20, 20);
        double height = 0, h = 0;
        for (Iterator iterator = templateElements.iterator(); iterator
                .hasNext();) {
            EObject object = (EObject) iterator.next();
            if (object instanceof GraphicalNode) {
                GraphicalNode graphicalNode = (GraphicalNode) object;
                NodeGraphicsInfo ngi =
                        Xpdl2ModelUtil.getNodeGraphicsInfo(graphicalNode);
                if (ngi != null && ngi.getCoordinates() != null) {
                    h = ngi.getCoordinates().getYCoordinate() + ngi.getHeight();
                    if (h > height)
                        height = h;
                }
            }
        }

        if (height > 300) {
            Pool pool = getRelevantPool(process);

            NodeGraphicsInfo ngi =
                    Xpdl2ModelUtil
                            .getNodeGraphicsInfo((pool.getLanes().get(0)));
            if (ngi != null) {
                ngi.setHeight(height + 30);
            }

        } else {
            startPoint.x = 40;
            startPoint.y = 100;
        }
        return startPoint;
    }

    private Pool getRelevantPool(Process process) {
        Package pkg = process.getPackage();
        if (pkg != null) {
            List<Pool> pools = pkg.getPools();
            for (Pool pool : pools)
                if (pool.getProcessId().equals(process.getId()))
                    return pool;
        }

        Pool pool =
                ElementsFactory.createPool(DEFAULT_POOL_NAME, process.getId());
        pkg.getPools().add(pool);
        return pool;
    }

    public Process getProcess() {
        if (display != null && name != null && processObject != null) {
            final String[] displayText = new String[] { null };
            // Accessing UI component
            getShell().getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    processObject.setName(name.getText());
                    displayText[0] = display.getText();
                }
            });
            Xpdl2ModelUtil
                    .setOtherAttribute(processObject,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            displayText[0]);
        }
        return processObject;
    }

    private void createProcess() {
        if (processObject == null) {
            processObject = Xpdl2Factory.eINSTANCE.createProcess();
            ProcessHeader ph = Xpdl2Factory.eINSTANCE.createProcessHeader();

            Description description =
                    Xpdl2Factory.eINSTANCE.createDescription();
            ph.setDescription(description);

            processObject.setProcessHeader(ph);

        }
    }

    private Lane createLane(Pool pool) {
        Lane lane = Xpdl2Factory.eINSTANCE.createLane();
        NodeGraphicsInfo gNode =
                Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);
        gNode.setHeight(300);

        pool.getLanes().add(lane);
        lane.setName(NameUtil.getInternalName(DEFAULT_LANE_NAME, false));
        Xpdl2ModelUtil.setOtherAttribute(lane,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                DEFAULT_LANE_NAME);
        return lane;
    }

    public void initializeProcess(Process process) {
        Pool pool = getRelevantPool(process);
        createLane(pool);
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem()
                .displayHelp("com.tibco.xpd.process.analyst.doc.CreatePack3"); //$NON-NLS-1$      
    }

    private IFragmentCategory getProcessRootCategory() {
        try {
            return FragmentsActivator.getDefault()
                    .getRootCategory(getPluginFragmentId());
        } catch (CoreException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @return
     */
    protected String getPluginFragmentId() {
        return PLUGIN_FRAGMENT_ID;
    }

    public void getInitialSelection() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /* XPD-2673: Wait for fragment initialise to finish. */
                waitForFragmentInitialisation();
                IFragmentCategory rootCategory = getProcessRootCategory();
                initialFragment = getDefaultFragment(rootCategory);
            }

        };

        Display display = null;
        Shell shell = getShell();
        if (shell != null) {
            display = shell.getDisplay();
        }
        if (display == null) {
            display = Display.getDefault();
        }
        if (display != null) {

            display.syncExec(runnable);
        }

        return;
    }

    /**
     * @param rootCategory
     * @return
     */
    private IFragment getDefaultFragment(IFragmentCategory category) {
        if (category != null) {
            for (IFragmentElement fragElement : category.getChildren()) {
                if (fragElement instanceof IFragmentCategory) {
                    IFragment defaultFragment =
                            getDefaultFragment((IFragmentCategory) fragElement);
                    if (defaultFragment != null) {
                        return defaultFragment;
                    }
                } else {
                    Package fragmentPackage =
                            Xpdl2ProcessorUtil
                                    .getFragmentPackage((IFragment) fragElement);
                    if (fragmentPackage != null
                            && ProcessTemplateProvider.DEFAULT_DISPLAY_FRAGMENT_ID
                                    .equals(fragmentPackage.getId())) {
                        return (IFragment) fragElement;
                    }

                }
            }
        }

        return null;
    }

    @Override
    public void verifyText(VerifyEvent event) {
        boolean beforeNameValid = nameValid;
        boolean beforeDisplayNameValid = displayNameValid;

        Text textControl = ((Text) event.widget);
        if (textControl == name) {
            String nameText =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);
            verifyName(nameText);
        } else if (textControl == display) {
            String text =
                    textControl.getText(0, event.start - 1)
                            + event.text
                            + textControl.getText(event.end,
                                    textControl.getCharCount() - 1);

            verifyDisplayName(text);
        }

        boolean complete = nameValid && displayNameValid;
        if (complete) {
            err = null;
        }
        setPageComplete(complete);
        setErrorMessage(err);

        if (beforeDisplayNameValid != displayNameValid
                || beforeNameValid != nameValid) {
            // Force layout on whole section to ensure that there is room for
            // error icon
            getShell().layout(true, true);
        }
    }

    /**
     * @param text
     */
    private void verifyDisplayName(String text) {
        displayNameValid = true;

        updateNameFromDisplayName(text);

        if (text.length() == 0) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LabelEmpty;
        } else if (!text.trim().equals(text)) {
            displayNameValid = false;
            err = Messages.NamedElementSection_LeadingTrailingSpaces1;
        }
        if (!displayNameValid) {
            displayLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            displayLabel.setToolTipText(err);
            displayLabel.setLayoutData(new GridData());
            displayLabel.getParent().layout();
        } else {
            displayLabel.setImage(null);
            displayLabel.setToolTipText(""); //$NON-NLS-1$
            displayLabel.setLayoutData(new GridData());
            displayLabel.getParent().layout();
        }
    }

    protected void updateNameFromDisplayName(String text) {
        if (name.getText().equals(NameUtil.getInternalName(display.getText(),
                false))) {
            String nameText = NameUtil.getInternalName(text, false);
            name.setText(nameText);
            verifyName(nameText);
        }
    }

    protected void verifyName(String nameText) {
        nameValid = true;
        if (nameText == null || nameText.length() == 0) {
            nameValid = false;
            err = Messages.NamedElementSection_NameEmpty;
        } else if (!NameUtil.isValidName(nameText, true)) {
            nameValid = false;
            err = Messages.NamedElementSection_invalidNameErrorMessage;
        }
        if (!nameValid) {
            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            nameLabel.setToolTipText(err);
            nameLabel.setLayoutData(new GridData());
            nameLabel.getParent().layout();
        } else {
            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$
            nameLabel.setLayoutData(new GridData());
            nameLabel.getParent().layout();
        }

    }

    public String getProcessToOpen() {
        if (processToOpenName == null) {
            processToOpenName = processObject.getName();
        }
        return processToOpenName;
    }
}