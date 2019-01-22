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
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.actions.CloseOpenProcessEditorCommand;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.PageflowUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ResetDefaultActivityColourCommand;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider;
import com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider.TemplateProviderIdentifier;
import com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
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
public class OldPageflowPropertySection extends
        AbstractFilteredTransactionalSection {

    private static final String TEMPLATEDATA = "TEMPLATE_DATA"; //$NON-NLS-1$

    private Button publish;

    private DecoratedField category;

    private Button inlineCheckbox = null;

    private ObjectReferencesSection referencesSection = null;

    private TreeWithImagePreviewComposite templateViewer;

    private Object templateElements;

    /**
     * Process properties
     */
    public OldPageflowPropertySection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
        setMinimumHeight(SWT.DEFAULT);
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
        Composite root = toolkit.createComposite(parent);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.verticalSpacing = 10;
        gridLayout.marginLeft = 3;
        root.setLayout(gridLayout);

        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            Label businessServiceLabel = toolkit.createLabel(root, "xxxx");//$NON-NLS-1$
            businessServiceLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP,
                    false, false));
            Composite businessServices = toolkit.createComposite(root);
            businessServices.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                    true, false));
            GridLayout businessServicesLayout = new GridLayout(2, false);
            businessServicesLayout.marginHeight = 0;
            businessServicesLayout.marginWidth = 0;
            businessServices.setLayout(businessServicesLayout);

            publish =
                    toolkit.createButton(businessServices,
                            Messages.PageflowPropertySection_PubishLabel,
                            SWT.CHECK,
                            "publish-checkbox"); //$NON-NLS-1$
            publish.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
                    false, 2, 1));

            manageControl(publish);

            Label categoryLabel =
                    toolkit.createLabel(businessServices,
                            Messages.PageflowPropertySection_CategoryLabel);
            categoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
                    false));

            //
            // Add content assisted text control for referenced event entry.
            FixedValueFieldProposalProvider proposalProvider =
                    new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                        @Override
                        public Object[] getProposals() {
                            Object[] proposals = new Object[0];
                            if (((Text) category.getControl()).isEnabled()) {
                                Process process = (Process) getInput();

                                List<String> categories =
                                        PageflowUtil
                                                .getBusinessCategories(process);
                                Set<String> set =
                                        new TreeSet<String>(categories);
                                proposals = set.toArray();
                            }
                            return proposals;
                        }
                    };

            FixedValueFieldAssistHelper refTaskHelper =
                    new FixedValueFieldAssistHelper(toolkit, businessServices,
                            proposalProvider, false);

            refTaskHelper
                    .addValueChangedListener(new FixedValueFieldChangedListener() {

                        @Override
                        public void fixedValueFieldChanged(Object newValue) {
                            if (newValue instanceof String) {
                                String text = (String) newValue;
                                ((Text) category.getControl()).setText(text);
                            }
                        }

                    });

            category = refTaskHelper.getDecoratedField();

            category.getControl()
                    .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                            "BusinessCategory"); //$NON-NLS-1$
            category.getLayoutControl()
                    .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                            "assistBusinessCategory"); //$NON-NLS-1$

            category.getLayoutControl().setLayoutData(new GridData(SWT.FILL,
                    SWT.CENTER, true, false));

            category.getLayoutControl().setBackground(root.getBackground());

            manageControl((Text) category.getControl());

            Label lab = toolkit.createLabel(root, "xxxxxxxx", //$NON-NLS-1$
                    SWT.WRAP);
            lab.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

            inlineCheckbox =
                    toolkit.createButton(root,
                            Messages.ProcessPropertySection_InlineSubProcess_CheckBox_button,
                            SWT.CHECK,
                            "inline-checkbox"); //$NON-NLS-1$
            inlineCheckbox
                    .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            inlineCheckbox
                    .setToolTipText(Messages.ProcessPropertySection_InlineSubProcess_longdesc);
            manageControl(inlineCheckbox);

            lab = toolkit.createLabel(root, "xxxxxx", //$NON-NLS-1$
                    SWT.WRAP);
            lab.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

            referencesSection = new ObjectReferencesSection();

            Composite comp = referencesSection.createControls(root, toolkit);
            comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        }
        processTemplateProvider =
                new ProcessTemplateProvider((Package) getInputContainer(),
                        TemplateProviderIdentifier.PAGEFLOW) {
                    /**
                     * @see com.tibco.xpd.processeditor.xpdl2.wizards.ProcessTemplateProvider#getApplicableInterfaces(com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup)
                     * 
                     * @param interfaceGroup
                     * @return
                     */
                    @Override
                    protected List getApplicableInterfaces(
                            ProcessInterfaceGroup interfaceGroup) {

                        List<Object> processInterfaces =
                                new ArrayList<Object>();

                        List children = interfaceGroup.getChildren();
                        /*
                         * XPD-7298: New Pageflow creation wizard should only
                         * list process interface templates.
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
                         * XPD-7298: New Pageflow creation wizard should only
                         * list process interface templates.
                         */
                        return ProcessResourceItemType.PROCESSINTERFACE
                                .equals(processResourceType);
                    }
                };
        if (getSectionContainerType() == ContainerType.WIZARD) {
            templateViewer =
                    new TreeWithImagePreviewComposite(root,
                            processTemplateProvider, SWT.NONE);
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

        return root;

    }

    private final ISelectionChangedListener listener =
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
                        WorkingCopy wc = getWorkingCopy();
                        if (wc != null) {
                            wc.getAttributes().put(TEMPLATEDATA,
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

            if (inlineCheckbox != null) {
                Object value =
                        Xpdl2ModelUtil
                                .getOtherAttribute(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_PublishAsBusinessService());
                Text categoryField = (Text) category.getControl();
                if (value instanceof Boolean
                        && ((Boolean) value).booleanValue() == true) {
                    publish.setSelection(true);
                    categoryField.setEnabled(true);
                    categoryField.setEditable(true);
                    categoryField.setBackground(Display.getDefault()
                            .getSystemColor(SWT.COLOR_WHITE));
                } else {
                    publish.setSelection(false);
                    categoryField.setEnabled(false);
                    categoryField.setEditable(false);
                    categoryField.setBackground(Display.getDefault()
                            .getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
                }
                value =
                        Xpdl2ModelUtil
                                .getOtherAttribute(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_BusinessServiceCategory());
                if (value == null) {
                    value = ""; //$NON-NLS-1$
                }
                if (value instanceof String) {
                    String text = (String) value;
                    updateText(categoryField, text);
                }
                value =
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
        }

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

            EObject container = getInputContainer();
            if (container instanceof Package) {
                String procName = Xpdl2ModelUtil.getDisplayNameOrName(process);
                if (procName == null || procName.length() == 0) {
                    procName =
                            Messages.PackageTemplateSelectionPage_ProcessDefault_value;
                }

                String baseName =
                        ((Package) container).getName() + "-" + procName; //$NON-NLS-1$
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
        Command command = null;
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

            command = cmd;
        } else if (publish != null && obj == publish) {
            Boolean shouldPublish = new Boolean(publish.getSelection());

            CompoundCommand cmd = new CloseOpenProcessEditorCommand(process);
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_PublishAsBusinessService(),
                    shouldPublish ? shouldPublish : null));

            /**
             * XPD-1026: business service category should be defaulted to
             * project name / process package name when publish as business
             * service is checked
             */
            if (shouldPublish) {
                cmd.setLabel(Messages.PageflowPropertySection_SetPublishCommand);
                IProject project = WorkingCopyUtil.getProjectFor(process);
                String defaultCategory =
                        Xpdl2ModelUtil
                                .getBusinessServiceDefaultCategory(project
                                        .getName(), process.getPackage()
                                        .getName());
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory(),
                        defaultCategory));

                /**
                 * Convert any activity that has default colour for pageflow
                 * process to use default colour for business service.
                 */
                ProcessWidgetColors businessServiceColours =
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
                ProcessWidgetColors pageflowColours =
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : allActivitiesInProc) {
                    cmd.append(new ResetDefaultActivityColourCommand(ed,
                            activity, pageflowColours, businessServiceColours));
                }

            } else {
                cmd.setLabel(Messages.PageflowPropertySection_UnsetPublishCommand);
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory(),
                        null));
                /**
                 * Convert any activity that has default colour for business
                 * service to use default colour for pageflow process.
                 */
                ProcessWidgetColors businessServiceColours =
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
                ProcessWidgetColors pageflowColours =
                        ProcessWidgetColors
                                .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);

                for (Activity activity : allActivitiesInProc) {
                    cmd.append(new ResetDefaultActivityColourCommand(ed,
                            activity, businessServiceColours, pageflowColours));
                }
            }

            command = cmd;
        } else if (category != null && obj == category.getControl()) {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.PageflowPropertySection_SetBusinessCategoryCommand);
            String old =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory());
            String text = ((Text) category.getControl()).getText().trim();
            if (text != null && !text.equals(old)) {
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory(),
                        text));
                command = cmd;
            }
        }
        return command;
    }

    public TreeWithImagePreviewComposite getTemplateViewer() {
        return templateViewer;
    }

    private WorkingCopy getWorkingCopy() {
        return getInputContainer() != null ? WorkingCopyUtil
                .getWorkingCopyFor(getInputContainer()) : null;
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
        boolean ok = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Process) {
            Process process = (Process) eo;

            ok = Xpdl2ModelUtil.isPageflow(process);
        }
        return ok;
    }

}
