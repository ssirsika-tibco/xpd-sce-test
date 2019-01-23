/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormText;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker.ProcessPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ImplementInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesFolder;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesItem;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesSection;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ProcessReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process properties
 * 
 * @author wzurek
 * 
 */
public class ProcessPropertySection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private static final String PROCESS_INTERFACE_SELECTION =
            "PROCESS_INTERFACE_SELECTION"; //$NON-NLS-1$

    private static final String TEMPLATEDATA = "TEMPLATE_DATA"; //$NON-NLS-1$

    private Color defaultTextColor = null;

    private Button inlineCheckbox = null;

    private ObjectReferencesSection referencesSection = null;

    private TreeWithImagePreviewComposite templateViewer;

    private Object templateElements;

    private static final String INTERFACE_LINK = "Interface.Link"; //$NON-NLS-1$

    private CLabel labelInterfaceLocation;

    private Text txtInterfaceLocation;

    private CLabel labelInterfaceName;

    private Text txtInterfaceName;

    private Button browseButton;

    private Button clearButton;

    private ScrolledComposite scrolledContainer;

    private Composite root;

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandablesContainer;

    /**
     * Attribute to store the type of the process.
     */
    private Object procType;

    /**
     * String to identify Business process type.
     */
    private static final String BUSINESS_PROCESS_TYPE = "$BusinessProcessType$"; //$NON-NLS-1$

    public final static String INLINE_SUBPROCESS_SECTION = "inlineSubProcess"; //$NON-NLS-1$

    public final static String PROCESS_INTERFACE_SELECTION_SECTION =
            "processInterface"; //$NON-NLS-1$

    public final static String REFERENCES_SECTION = "referencesSection"; //$NON-NLS-1$

    /**
     * Process properties
     */
    public ProcessPropertySection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
        setMinimumHeight(SWT.DEFAULT);
        procType = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = toolkit.createComposite(scrolledContainer);

        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.verticalSpacing = 10;
        gridLayout.marginLeft = 3;
        root.setLayout(gridLayout);

        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            // Expandable section header preferences.
            String sectPrefId =
                    getSectionContainerType()
                            + "." + "ExpandableProcessSection"; //$NON-NLS-1$ //$NON-NLS-2$

            expandableHeaderController =
                    new ExpandableSectionStacker(sectPrefId);

            addExpandableSections(expandableHeaderController);

            expandablesContainer =
                    expandableHeaderController.createExpandableSections(root,
                            toolkit,
                            this);

            GridData gd =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                            | GridData.GRAB_HORIZONTAL);
            expandablesContainer.setLayoutData(gd);

        } else if (getSectionContainerType() == ContainerType.WIZARD) {
            processTemplateProvider = createWizardProcessTemplateProvider();

            templateViewer =
                    new TreeWithImagePreviewComposite(root,
                            processTemplateProvider, SWT.NONE) {
                        /**
                         * @see com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite#getInterfaceTemplatePreviewTitle()
                         * 
                         * @return
                         */
                        @Override
                        protected String getInterfaceTemplatePreviewTitle() {

                            return getProcInterfaceTemplatePreviewTitle();
                        }
                    };
            GridData gridData = new GridData(GridData.FILL_BOTH);
            gridData.horizontalSpan = 2;
            templateViewer.setLayoutData(gridData);
            templateViewer.addSelectionChangedListener(listener);
            if (templateViewer.getSelection() != null) {
                listener.selectionChanged(new SelectionChangedEvent(
                        templateViewer.getTreeViewer(), templateViewer
                                .getSelection()));
            }

        }

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        return scrolledContainer;
    }

    /**
     * 
     * @return the title that should be displayed in the Interface Preview
     *         Table.
     */
    protected String getProcInterfaceTemplatePreviewTitle() {
        return Messages.TreeWithImagePreviewComposite_DescribeInterfaceTemplate_shortmsg;
    }

    /**
     * @return template provider for processes in new process wizard
     */
    protected ProcessTemplateProvider createWizardProcessTemplateProvider() {
        return new ProcessTemplateProvider((Package) getInputContainer(),
                TemplateProviderIdentifier.PROCESS) {
            /**
             * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
             * 
             * @param interfaceGroup
             * @return
             */
            @Override
            protected List getApplicableInterfaces(
                    ProcessInterfaceGroup interfaceGroup) {

                List<Object> processInterfaces = new ArrayList<Object>();

                List children = interfaceGroup.getChildren();
                /*
                 * XPD-7298: New Process creation wizard should only list
                 * process interface templates.
                 */
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
                 * XPD-7298: New Process creation wizard should only list
                 * process interface templates.
                 */
                return ProcessResourceItemType.PROCESSINTERFACE
                        .equals(processResourceType);
            }
        };
    }

    /**
     * @param expandableHeaderController2
     */
    protected void addExpandableSections(
            ExpandableSectionStacker headerController) {

        headerController.addSection(PROCESS_INTERFACE_SELECTION_SECTION,
                Messages.ProcessPropertySection_ProcessInterfaceSection_title,
                SWT.DEFAULT,
                false,
                false);

        headerController.addSection(INLINE_SUBPROCESS_SECTION,
                Messages.ProcessPropertySection_InlineSubProcessSection_title,
                SWT.DEFAULT,
                false,
                false);

        headerController.addSection(REFERENCES_SECTION,
                Messages.ProcessPropertySection_ReferencesSection_title,
                40,
                true,
                true);

    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {

        if (INLINE_SUBPROCESS_SECTION.equals(sectionId)) {
            return createInlineSubProcessControls(toolkit, container);
        } else if (PROCESS_INTERFACE_SELECTION_SECTION.equals(sectionId)) {
            return createProcessInterfaceControls(toolkit, container);
        } else if (REFERENCES_SECTION.equals(sectionId)) {
            return createReferencesControls(toolkit, container);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point sz = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(sz);
    }

    /**
     * @param toolkit
     * @param parent
     */
    private Composite createReferencesControls(XpdFormToolkit toolkit,
            Composite sectionContainer) {
        Composite parent = toolkit.createComposite(sectionContainer);
        GridLayout gridLayout = new GridLayout(1, false);
        parent.setLayout(gridLayout);

        referencesSection = new ObjectReferencesSection();

        Composite comp = referencesSection.createControls(parent, toolkit);
        comp.setLayoutData(new GridData(GridData.FILL_BOTH));

        return parent;
    }

    /**
     * @param toolkit
     * @param parent
     */
    protected Composite createProcessInterfaceControls(XpdFormToolkit toolkit,
            Composite sectionContainer) {

        Composite interfaceComposite =
                toolkit.createComposite(sectionContainer);

        GridLayout ifcLayout = new GridLayout(4, false);
        ifcLayout.marginBottom = ifcLayout.marginHeight;
        ifcLayout.marginHeight = 0;
        ifcLayout.marginWidth = 0;
        interfaceComposite.setLayout(ifcLayout);

        FormText implementsInterface =
                toolkit.createFormText(interfaceComposite,
                        true,
                        "implements-interface"); //$NON-NLS-1$

        String implementsProcessInterface =
                Messages.ProcessPropertySection_ImplementProcessIfc_label;
        String openInterface =
                Messages.ProcessPropertySection_OpenIfcLink_label;

        String implementsText =
                "<form><p>%1$s(<a href = '%2$s'>%3$s</a>)</p></form>"; //$NON-NLS-1$
        implementsInterface.setText(String.format(implementsText,
                implementsProcessInterface,
                INTERFACE_LINK,
                openInterface), true, false);
        GridData gridData = new GridData();
        gridData.horizontalSpan = 4;
        implementsInterface.setLayoutData(gridData);
        manageControl(implementsInterface);

        labelInterfaceLocation =
                toolkit.createCLabel(interfaceComposite,
                        Messages.ProcessPropertySection_InterfaceLocation_label,
                        SWT.NONE);
        labelInterfaceLocation.setLayoutData(new GridData());

        txtInterfaceLocation = toolkit.createText(interfaceComposite, "", //$NON-NLS-1$
                "interface-location"); //$NON-NLS-1$
        txtInterfaceLocation.setEditable(false);
        txtInterfaceLocation.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        browseButton = toolkit.createButton(interfaceComposite, "...", //$NON-NLS-1$
                SWT.PUSH,
                "browse"); //$NON-NLS-1$
        gridData = new GridData();
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        browseButton.setLayoutData(gridData);

        manageControl(browseButton);

        clearButton =
                toolkit.createButton(interfaceComposite,
                        Messages.ProcessPropertySection_ClearButton_label,
                        SWT.PUSH,
                        "clear"); //$NON-NLS-1$

        gridData = new GridData();
        gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
        clearButton.setLayoutData(gridData);

        manageControl(clearButton);

        labelInterfaceName =
                toolkit.createCLabel(interfaceComposite,
                        Messages.ProcessPropertySection_InterfaceName_label,
                        SWT.NONE);
        labelInterfaceName.setLayoutData(new GridData());

        txtInterfaceName = toolkit.createText(interfaceComposite, "", //$NON-NLS-1$
                "interface-name"); //$NON-NLS-1$
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        txtInterfaceName.setEditable(false);
        txtInterfaceName.setLayoutData(gridData);

        return interfaceComposite;
    }

    /**
     * @param toolkit
     * @param parent
     */
    protected Composite createInlineSubProcessControls(XpdFormToolkit toolkit,
            Composite sectionContainer) {
        Composite parent = toolkit.createComposite(sectionContainer);
        GridLayout gridLayout = new GridLayout(1, false);
        parent.setLayout(gridLayout);

        inlineCheckbox =
                toolkit.createButton(parent,
                        Messages.ProcessPropertySection_InlineSubProcess_CheckBox_button,
                        SWT.CHECK,
                        "inline-checkbox"); //$NON-NLS-1$
        inlineCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        inlineCheckbox
                .setToolTipText(Messages.ProcessPropertySection_InlineSubProcess_longdesc);
        manageControl(inlineCheckbox);
        return parent;
    }

    private ISelectionChangedListener listener =
            new ISelectionChangedListener() {
                HashMap<String, String> destinationEnvs = null;

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    if (event.getSelection() != null
                            && event.getSelection() instanceof IStructuredSelection) {
                        IStructuredSelection structuredSelection =
                                (IStructuredSelection) event.getSelection();
                        if (structuredSelection != null
                                && structuredSelection.getFirstElement() instanceof IFragment) {

                            IFragment fragment =
                                    (IFragment) structuredSelection
                                            .getFirstElement();
                            templateElements =
                                    Xpdl2ProcessorUtil
                                            .getFragmentDropObjects(fragment
                                                    .getLocalizedData());
                            destinationEnvs =
                                    Xpdl2ProcessorUtil
                                            .getDestinationEnvs(fragment);
                        } else if (structuredSelection.getFirstElement() != null
                                && structuredSelection.getFirstElement() instanceof ProcessInterface) {
                            templateElements =
                                    structuredSelection.getFirstElement();
                        }

                        else {
                            templateElements = null;
                        }

                        if (getWorkingCopy() != null) {
                            getWorkingCopy().getAttributes().put(TEMPLATEDATA,
                                    templateElements);

                            if (templateElements instanceof ProcessInterface) {
                                ProcessInterface pi =
                                        (ProcessInterface) templateElements;
                                if (pi != null) {
                                    // Enable destination environments for those
                                    // of the process interface
                                    Set<String> enabledDestinations =
                                            DestinationUtil
                                                    .getEnabledGlobalDestinations(pi);
                                    DestinationUtil
                                            .addPassedDestinationToProcess(enabledDestinations,
                                                    (Process) getInput());

                                }
                            } else if (destinationEnvs != null) {
                                Set<String> enabledDestinations =
                                        new HashSet<String>();
                                for (String dest : destinationEnvs.keySet()) {
                                    enabledDestinations.add(destinationEnvs
                                            .get(dest));
                                }
                                DestinationUtil
                                        .addPassedDestinationToProcess(enabledDestinations,
                                                (Process) getInput());
                            }
                        }
                    }
                }
            };

    private ProcessTemplateProvider processTemplateProvider;

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.BaseXpdSection#doRefresh()
     */
    @Override
    public void doRefresh() {
        Process process = (Process) getInput();
        if (process != null) {

            if (null != procType) {

                if (hasProcessTypeChanged(process)) {

                    /*
                     * If process type is changed, then refresh tabs and exit
                     * from doRefresh() after setting the process type flag.
                     */
                    /*
                     * Sid XPD-77-97 During debug I noticed that for THIS
                     * section, refreshTabs() doesn't ultimately refresh THIS
                     * section (just all the others), this is because whilst
                     * we're in THIS section it's 'ignoredEvents' flag is set
                     * which will prevent us from re-entering this section.
                     * 
                     * Therefore we need to asynch-exec this so that we're not
                     * doing it whilst inside the doRefresh() method.
                     */
                    setProcessTypeFlag(process);
                    Display.getCurrent().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            refreshTabs();
                        }
                    });
                    return;
                }

            } else {

                /*
                 * Else simply set the process type flag.
                 */
                setProcessTypeFlag(process);

            }

            if (inlineCheckbox != null) {
                Object value =
                        Xpdl2ModelUtil.getOtherAttribute(process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InlineSubProcess());
                if (value instanceof Boolean
                        && ((Boolean) value).booleanValue() == true) {
                    inlineCheckbox.setSelection(true);
                } else {
                    inlineCheckbox.setSelection(false);
                }
            }

            loadProcessReferences();
            if (txtInterfaceLocation != null && txtInterfaceName != null) {
                Image labelLocationIcon = null;
                Color txtLocationColor = defaultTextColor;

                ProcessInterface implementedProcessInterface =
                        ProcessInterfaceUtil
                                .getImplementedProcessInterface(process);
                if (implementedProcessInterface != null) {
                    ProcessInterface processInterface =
                            implementedProcessInterface;
                    txtInterfaceLocation
                            .setText(getInterfaceLocationText(process));
                    txtInterfaceName.setText(processInterface.getName());

                } else {
                    if (ProcessInterfaceUtil
                            .getImplementedProcessInterfaceId(process) != null) {
                        txtInterfaceLocation
                                .setText(Messages.Process_UnresolvedReferences_message);
                        txtInterfaceName
                                .setText(Messages.Process_UnresolvedReferences_message);

                        txtLocationColor = ColorConstants.lightGray;
                        labelLocationIcon =
                                Xpdl2UiPlugin.getDefault().getImageRegistry()
                                        .get(Xpdl2UiPlugin.IMG_ERROR);

                    } else {
                        txtInterfaceLocation.setText(""); //$NON-NLS-1$
                        txtInterfaceName.setText(""); //$NON-NLS-1$
                    }
                }

                if (txtLocationColor != txtInterfaceLocation.getForeground()) {
                    txtInterfaceLocation.setForeground(txtLocationColor);
                    txtInterfaceName.setForeground(txtLocationColor);
                }

                if (labelInterfaceLocation.getImage() != labelLocationIcon) {
                    labelInterfaceLocation.setImage(labelLocationIcon);
                    labelInterfaceLocation.setLayoutData(new GridData());
                    labelInterfaceLocation.getParent().layout();

                    labelInterfaceName.setImage(labelLocationIcon);
                    labelInterfaceName.setLayoutData(new GridData());
                    labelInterfaceName.getParent().layout();
                }

            }
            // XPD-3833 Hide "Inline Sub-Process" part of process properties
            // general tab unless process has iProcess Destination selected

            boolean iProcessDestinationEnable =
                    ProcessDestinationUtil
                            .isIProcessDestinationSelected(process);
            if (expandableHeaderController != null) {
                if (iProcessDestinationEnable) {
                    expandableHeaderController
                            .setSectionVisible(INLINE_SUBPROCESS_SECTION, true);
                } else {
                    expandableHeaderController
                            .setSectionVisible(INLINE_SUBPROCESS_SECTION, false);
                }
            }
        }
        if (templateViewer != null) {
            if (getInputContainer() != null
                    && processTemplateProvider.getXpdl2Package() == null) {
                // This
                processTemplateProvider
                        .setXpdl2Package((Package) getInputContainer());
                templateViewer.getTreeViewer()
                        .setInput(processTemplateProvider);
                templateViewer.setSelection(processTemplateProvider
                        .getInitialSelection());
            }
            if (getWorkingCopy() != null
                    && getWorkingCopy().getAttributes()
                            .containsKey(PROCESS_INTERFACE_SELECTION)) {
                templateViewer.setSelection(getWorkingCopy().getAttributes()
                        .get(PROCESS_INTERFACE_SELECTION));
                getWorkingCopy().getAttributes()
                        .remove(PROCESS_INTERFACE_SELECTION);

            } else {
                templateViewer.setEnabled(true);
            }
        }

    }

    /**
     * Return <code>true</code> if the specified process type is changed from
     * what we had in 'procType', <code>false</code> otherwise.
     * 
     * @param process
     *            Process to be examined.
     * 
     * @return <code>true</code> if the specified process type is changed from
     *         what we had in 'procType', <code>false</code> otherwise.
     */
    private boolean hasProcessTypeChanged(Process process) {

        Object procTypeAttribute =
                Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_XpdModelType());

        if (procTypeAttribute == null) {

            /*
             * XpdModelType would be null for Business processes so compare with
             * our internal 'is business process' flag
             */

            if (!procType.equals(BUSINESS_PROCESS_TYPE)) {
                return true;
            } else {
                /*
                 * Sid XPD-7797: Once we've decided that process model is
                 * 'business process' then don't want to fall thru the method to
                 * other checks (which will return trye because
                 * BUSINESS_PROCESS_TYPE will be compared against null and
                 * return true.
                 */
                return false;
            }

        }

        if (!procType.equals(procTypeAttribute)) {

            return true;

        }

        return false;
    }

    /**
     * Set the class level attribute 'procType' value according to the current
     * process type.
     * 
     * @param process
     */
    private void setProcessTypeFlag(Process process) {

        Object procTypeAttribute =
                Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_XpdModelType());

        if (null == procTypeAttribute) {

            /*
             * XpdModelType would be null for Business processes.
             */

            procType = BUSINESS_PROCESS_TYPE;

        } else {

            procType = procTypeAttribute;
        }
    }

    private String getInterfaceLocationText(Process process) {
        String locationText = null;
        ProcessInterface processInterface =
                ProcessInterfaceUtil.getImplementedProcessInterface(process);
        if (WorkingCopyUtil.getWorkingCopyFor(processInterface) == WorkingCopyUtil
                .getWorkingCopyFor(process))
            locationText = Messages.ProcessPropertySection_SamePackage_value;
        else {
            WorkingCopy wc =
                    WorkingCopyUtil.getWorkingCopyFor(processInterface);

            locationText =
                    wc.getEclipseResources().get(0).getProjectRelativePath()
                            .toString();
        }
        return locationText;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        loadProcessReferences();

        // For new process - Reset default name to something unique.
        // Ensure name is unique at the very start as we now check for
        // uniqueness we don't want user to be shown an error by default!
        Process process = (Process) getInput();
        if (process != null && process.eContainer() == null) {
            // String baseName = process.getName();
            // String finalName = baseName;

            EObject container = getInputContainer();
            if (container instanceof Package) {
                String baseName =
                        ((Package) container).getName()
                                + "-" + Messages.PackageTemplateSelectionPage_ProcessDefault_value; //$NON-NLS-1$
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayNameProcess((Package) container,
                                process,
                                finalName) != null
                        || Xpdl2ModelUtil
                                .getDuplicateProcess((Package) container,
                                        process,
                                        NameUtil.getInternalName(finalName,
                                                false)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                process.setName(NameUtil.getInternalName(finalName, false));
                Xpdl2ModelUtil.setOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
                /*
                 * XPD-6766: Check if it is a wizard and then call the selection
                 * changed on the listener. This will enable the default
                 * selected fragment data to be available for the creation of a
                 * new process when the xpdl/process package selection changes
                 * (clicking on back button from new process wizard or
                 * File->New->Other process type)
                 */
                if (getSectionContainerType() == ContainerType.WIZARD) {

                    if (null != templateViewer
                            && null != templateViewer.getSelection()) {

                        listener.selectionChanged(new SelectionChangedEvent(
                                templateViewer.getTreeViewer(), templateViewer
                                        .getSelection()));
                    }
                }
            }
        }

    }

    private void loadProcessReferences() {
        if (referencesSection != null) {
            ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

            // Map of process object to the process folder for it.
            HashMap<Process, ObjectReferencesFolder> processFolders =
                    new HashMap<Process, ObjectReferencesFolder>();
            // Map of process object to the activity folder for it.
            HashMap<Process, ObjectReferencesFolder> activityFolders =
                    new HashMap<Process, ObjectReferencesFolder>();

            Process process = (Process) getInput();
            if (process != null) {
                // Fetch all the references to process in this package.
                Set<EObject> referenceObjects =
                        Xpdl2ProcessReferenceResolver
                                .getReferencingObjects(process, false);

                // Now add all the items into list.
                // Creating a tree of process/activity folders.
                for (EObject obj : referenceObjects) {

                    if (obj instanceof Activity) {
                        Activity act = (Activity) obj;

                        // Add folder for process if necessary.
                        Process proc = act.getProcess();
                        if (proc != null) {
                            // Create the folder for this process if we haven't
                            // already.
                            ObjectReferencesFolder procFolder =
                                    processFolders.get(proc);
                            if (procFolder == null) {
                                procFolder = new ObjectReferencesFolder(proc);
                                processFolders.put(proc, procFolder);
                            }

                            // Create the activity folder for this process if
                            // not exists.
                            ObjectReferencesFolder actFolder =
                                    activityFolders.get(proc);
                            if (actFolder == null) {
                                actFolder =
                                        new ObjectReferencesFolder(
                                                Messages.ProcessPropertySection_TaskEventsFolder_value,
                                                ir.get(Xpdl2UiPlugin.IMG_TASKDRAWER));

                                activityFolders.put(proc, actFolder);

                                procFolder.addChild(actFolder);
                            }

                            actFolder.addChild(ObjectReferencesItem.create(act,
                                    getSite()));
                        }
                    } else {
                        continue;
                    }
                }

            }

            // Sort the list by process and set the content of tree viewer.
            List<Object> sortList =
                    new ArrayList<Object>(processFolders.size());
            sortList.addAll(processFolders.values());

            Collections.sort(sortList, new Comparator<Object>() {

                @Override
                public int compare(Object o1, Object o2) {
                    ObjectReferencesFolder f1 = (ObjectReferencesFolder) o1;
                    ObjectReferencesFolder f2 = (ObjectReferencesFolder) o2;
                    return f1.getName().compareTo(f2.getName());
                }
            });

            // sortList
            // .add(ObjectReferencesItem
            // .createWarningItem(Messages.
            // ProcessPropertySection_ReferencesToProcessExistWarning_longmsg));

            referencesSection.setContent(sortList);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.BaseXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        Process process = (Process) getInput();

        EditingDomain ed = getEditingDomain();
        if (inlineCheckbox != null && obj == inlineCheckbox) {
            Boolean inline = new Boolean(inlineCheckbox.getSelection());

            CompoundCommand cmd = new CompoundCommand();
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_InlineSubProcess(),
                    inline ? inline : null));
            if (inline) {
                cmd.setLabel(Messages.ProcessPropertySection_SetInlineSubProcess_menu);
            } else {
                cmd.setLabel(Messages.ProcessPropertySection_UnsetInlineSubProcess_menu);
            }

            return cmd;

        } else {
            ProcessInterface currentInterface =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);

            if (obj == browseButton
                    || (INTERFACE_LINK.equals(obj) && currentInterface == null)) {

                ProcessInterface newInterface =
                        getProcessInterfaceFromPicker(process);

                /*
                 * if OK was pressed and the selected process interface was not
                 * the same as that implemented by the process.
                 */
                if (newInterface != null && newInterface != currentInterface) {

                    /*
                     * USe a command that will not execute each individual
                     * command until execution time (in case we remove multiple
                     * things separately from the same list.
                     */
                    Command cmd =
                            ImplementInterfaceUtil
                                    .addImplementedProcessInterfaceCommand(ed,
                                            process,
                                            newInterface);
                    return cmd;
                }

            } else if (INTERFACE_LINK.equals(obj)) {
                if (currentInterface != null) {
                    revealObject(currentInterface);
                }

            } else if (obj == clearButton) {

                /*
                 * USe a command that will not execute each individual command
                 * until execution time (in case we remove multiple things
                 * separately from the same list.
                 */

                LateExecuteCompoundCommand cmd =
                        new LateExecuteCompoundCommand();

                if (ImplementInterfaceUtil
                        .appendRemoveImplementedInterfaceCommand(ed,
                                process,
                                cmd)) {
                    return cmd;
                }
            }
        }
        return null;
    }

    public TreeWithImagePreviewComposite getTemplateViewer() {
        return templateViewer;
    }

    private WorkingCopy getWorkingCopy() {
        return getInputContainer() != null ? WorkingCopyUtil
                .getWorkingCopyFor(getInputContainer()) : null;
    }

    /**
     * Get the process interface from a process interface picker displayed to
     * user
     * 
     * @param Picker
     *            message
     * 
     * @return
     */
    private ProcessInterface getProcessInterfaceFromPicker(Process process) {

        ProcessFilterPicker picker = null;

        if (Xpdl2ModelUtil.isServiceProcess(process)) {

            picker =
                    new ProcessFilterPicker(getSite().getShell(),
                            ProcessPickerType.SERVICEPROCESSINTERFACE, false);
        } else {

            picker =
                    new ProcessFilterPicker(getSite().getShell(),
                            ProcessPickerType.PROCESSINTERFACE, false);
        }

        if (null != picker) {

            ProcessInterface curSel =
                    ProcessInterfaceUtil
                            .getImplementedProcessInterface(process);
            if (curSel != null) {

                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            EObject result =
                    ProcessUIUtil.getResultFromPicker(picker, getSite()
                            .getShell(), process);

            if (result instanceof ProcessInterface) {

                return (ProcessInterface) result;
            }
        }

        return null;
    }

    public boolean revealObject(NamedElement processInterface) {
        try {
            IConfigurationElement facConfig =
                    XpdResourcesUIActivator
                            .getEditorFactoryConfigFor(processInterface);
            String editorID = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            EditorInputFactory f =
                    (EditorInputFactory) facConfig
                            .createExecutableExtension("factory"); //$NON-NLS-1$
            IEditorInput input = f.getEditorInputFor(processInterface);
            page.openEditor(input, editorID);
            return true;
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {
            Process process = (Process) eo;

            /*
             * Do not include process properties for task library or pageflow
             * process properties.
             */
            if ((XpdlFileContentPropertyTester.isXpdlFileContent(process))
                    || NewBusinessProcessWizard.NEW_BUSINESSPROCESS_PROCESS_ID
                            .equals(process.getId())) {
                if (!Xpdl2ModelUtil.isPageflowOrSubType(process)
                        && !Xpdl2ModelUtil.isServiceProcess(process)
                        && !Xpdl2ModelUtil.isTaskLibrary(process)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean ret = false;

        ret = super.shouldRefresh(notifications);
        if (!ret) {
            // Check for add / delete of process interface - so that if
            // undo/redo deleted interface then we will refresh.
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    switch (notification.getEventType()) {
                    case Notification.ADD:
                        if (notification.getNewValue() instanceof ProcessInterface
                                || notification.getNewValue() instanceof Package) {
                            ret = true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (notification.getOldValue() instanceof ProcessInterface
                                || notification.getOldValue() instanceof Package) {
                            ret = true;
                        }
                        break;
                    }

                }
            }
        }

        return ret;
    }

}
