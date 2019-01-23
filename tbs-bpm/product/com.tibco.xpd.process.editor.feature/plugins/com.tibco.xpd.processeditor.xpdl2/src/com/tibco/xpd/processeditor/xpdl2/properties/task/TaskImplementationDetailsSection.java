/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.ITaskImplementationInitializer;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationElement;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationService;
import com.tibco.xpd.processeditor.xpdl2.extensions.TaskImplementationService.Implementations;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.Bindings;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.TaskReference;
import com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding.TasksBindingService;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task type service/send section
 * 
 * @author njpatel
 */
public class TaskImplementationDetailsSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    // private static final String TASKIMPLEMENTATION_EXTENSIONPOINT =
    // "taskImplementation"; //$NON-NLS-1$

    private String instrumentationPrefixName;

    private Map<String, ImplSection> implSectionsMapByName = null;

    private Map<String, ImplSection> implSectionsMapById = null;

    // The default implementation to select if one hasn't been set
    private ImplSection defaultImpl = null;

    private CCombo implTypesCombo;

    private PageBook implTypeBook;

    // Implementaiton selected before the update is made when a new item is
    // selected in
    // the impl combo
    private ImplSection previousSelectedImpl = null;

    // No implementation page for the pagebook
    private Composite noImplPage;

    /** Task Type this section is handling */
    private TaskType taskType;

    // private final Map<TaskTypeEnum,Collection<ImplSection>> genericTasks;

    private Logger log = LoggerFactory
            .createLogger(Xpdl2ProcessEditorPlugin.ID);

    /**
     * Class contains the implementation extension and the book page that
     * belongs to it in the properties view
     */
    private class ImplSection {
        public Composite page;

        public TaskImplementationElement extensionElement;

        public ImplSection(TaskImplementationElement element) {
            extensionElement = element;
            page = null;
        }

        @Override
        public String toString() {
            // If there is an extensionElement defined then get it's name
            if (extensionElement != null) {
                return extensionElement.getName();
            }

            return super.toString();
        }

        public String getId() {
            return extensionElement.getId();
        }

        public String getImplementationType() {
            return extensionElement.getImplementationType();
        }
    };

    /**
     * Task type service section
     */
    public TaskImplementationDetailsSection(TaskType taskType) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        init(taskType);

    }

    public TaskImplementationDetailsSection(TaskType taskType,
            String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
        init(taskType);
    }

    public void init(TaskType taskType) {
        this.taskType = taskType;

        // Get the extensions of the taskImplementation extension point
        implSectionsMapByName = new HashMap<String, ImplSection>();
        implSectionsMapById = new HashMap<String, ImplSection>();

        TaskImplementationService.Implementations implementations =
                TaskImplementationService.INSTANCE.getImplementations();
        for (TaskImplementationElement tiElem : implementations.getElements()) {
            TaskType elemTaskType = tiElem.getTaskType();
            // If a implementation already exists in the list
            // with the
            // same name then ignore this entry.
            // Ensure implementation is for correct task type
            if (taskType == elemTaskType
                    && !implSectionsMapById.containsKey(tiElem.getId())) {
                ImplSection serviceSection = new ImplSection(tiElem);

                implSectionsMapById.put(tiElem.getId(), serviceSection);
                implSectionsMapByName.put(tiElem.getName(), serviceSection);

                /*
                 * Determine the default implementation. The higher priority
                 * default implementation will be set as default
                 */
                if (serviceSection.extensionElement.getDefault()) {
                    if (defaultImpl == null) {
                        defaultImpl = serviceSection;
                    } else {
                        if (serviceSection.extensionElement.getPriority() > defaultImpl.extensionElement
                                .getPriority()) {
                            defaultImpl = serviceSection;
                        }
                    }
                }
            }

            // Workaround for misspelt task id.
            // There was "Web Service" task which I changed to "WebService"
            // because this information is stored in xpdl file, we have to
            // deal
            // with old task id.
            if (implSectionsMapById.containsKey("WebService")) { //$NON-NLS-1$
                ImplSection section = implSectionsMapById.get("WebService"); //$NON-NLS-1$
                implSectionsMapById.put("Web Service", section); //$NON-NLS-1$
            }
        }

        // If no default implementation was specified then set the first
        // implementation
        // available as the default
        if (defaultImpl == null && !implSectionsMapByName.isEmpty()) {
            defaultImpl = implSectionsMapByName.values().iterator().next();
        }
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        /*
         * If no service has already been selected in the model then set the
         * default impl and update the model. This will be usually the case when
         * a new impl type is added to the diagram
         */
        Activity act = getActivity();

        if (act != null) {

            // Update the relevant sections
            try {
                updateSectionInput(getCurrentSelectedImpl());
            } catch (CoreException e) {
                Logger logger =
                        Xpdl2ProcessEditorPlugin.getDefault().getLogger();
                logger.error(e);
            }
        }

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite sectionComposite = toolkit.createComposite(parent);
        toolkit.adapt(sectionComposite);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight--;
        /*
         * Sid: Noticed that service task type drop down overrun edge of
         * properties.
         */
        gridLayout.marginRight = 5;
        sectionComposite.setLayout(gridLayout);

        // If no implementations are available then don't show any controls
        if (!implSectionsMapById.isEmpty()) {

            // Impl type selection
            createLabel(sectionComposite,
                    toolkit,
                    Messages.TaskTypeServiceSection_ServiceType);

            implTypesCombo =
                    toolkit.createCCombo(sectionComposite,
                            SWT.SINGLE,
                            instrumentationPrefixName + "ServiceType"); //$NON-NLS-1$
            implTypesCombo
                    .setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                            TabbedPropertySheetWidgetFactory.TEXT_BORDER);
            implTypesCombo.setEditable(false);
            implTypesCombo.setVisibleItemCount(10);
            implTypesCombo
                    .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            // Create pagebook to hold the service type specific controls
            implTypeBook = new PageBook(sectionComposite, SWT.NONE);
            implTypeBook.setBackground(sectionComposite.getBackground());
            GridData gData = new GridData(GridData.FILL_BOTH);
            gData.horizontalSpan = 2;
            implTypeBook.setLayoutData(gData);

            // Create no service page
            noImplPage = toolkit.createComposite(implTypeBook);

            // Web service section is in the map twice because of the name
            // change.
            // Make sure we only create one page for it....
            Set<ImplSection> uniqueSections =
                    new HashSet<ImplSection>(implSectionsMapById.values());
            // For each item added to the combo add the corresponding
            // element to
            // the data
            for (ImplSection section : uniqueSections) {
                // Add service type to combo
                // implTypesCombo.add(section.toString());

                // For each impl type add the page to the book
                section.page = toolkit.createComposite(implTypeBook, SWT.NONE);
                FillLayout fillLayout = new FillLayout();
                fillLayout.marginHeight = 0;
                fillLayout.marginWidth = 0;
                section.page.setLayout(fillLayout);

                try {
                    // Create the controls for the impl section
                    section.extensionElement.getISectionExec()
                            .createControls(section.page,
                                    getPropertySheetPage());

                } catch (CoreException e) {
                    Logger logger =
                            Xpdl2ProcessEditorPlugin.getDefault().getLogger();
                    logger.error(e);
                }
            }

            manageControl(implTypesCombo);
        }

        return sectionComposite;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        TaskImplementationDetailsCompoundCommand compoundCmd = null;
        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();

        if (act != null && ed != null) {
            compoundCmd = new TaskImplementationDetailsCompoundCommand(act);

            // Handle the impl type combo
            if (obj == implTypesCombo) {
                String name = implTypesCombo.getText();
                ImplSection section = implSectionsMapByName.get(name);
                if (section != null) {
                    String id = section.getId();
                    // Check if selection in the combo was changed
                    if (!id.equals(getCurrentSelectedImplId())) {
                        Command cmd = null;
                        // Record the currently selected impl before change
                        previousSelectedImpl = getCurrentSelectedImpl();
                        try {
                            // Do the cleanup
                            if (previousSelectedImpl != null
                                    && previousSelectedImpl.extensionElement != null
                                    && previousSelectedImpl.extensionElement
                                            .getISectionExec() instanceof ITaskImplementationInitializer) {
                                cmd =
                                        ((ITaskImplementationInitializer) previousSelectedImpl.extensionElement
                                                .getISectionExec())
                                                .getCleanupCommand(act);
                                if (cmd != null) {
                                    compoundCmd.append(cmd);
                                }
                            }
                        } catch (CoreException e) {
                            Logger logger =
                                    Xpdl2ProcessEditorPlugin.getDefault()
                                            .getLogger();
                            logger.error(e);
                        }

                        // Item selection changed in the combo so update the
                        // model with the new
                        // implementation extension name
                        cmd =
                                TaskObjectUtil
                                        .getChangeTaskImplementationCommand(ed,
                                                act,
                                                taskType,
                                                id,
                                                section.getImplementationType());
                        if (cmd != null) {
                            compoundCmd.append(cmd);
                        }
                        // XPD-288
                        if (TaskImplementationTypeDefinitions.EMAIL_SERVICE
                                .equalsIgnoreCase(id)) {
                            cmd = getSetEmailTypeParticipant(ed, act);
                            if (cmd != null) {
                                compoundCmd.append(cmd);
                            }
                        }
                        // XPD-288 - end
                        try {
                            // Do the initialisation
                            if (section.extensionElement != null
                                    && section.extensionElement
                                            .getISectionExec() instanceof ITaskImplementationInitializer) {
                                compoundCmd
                                        .setTaskImplementationInitializer((ITaskImplementationInitializer) section.extensionElement
                                                .getISectionExec());
                            }
                        } catch (CoreException e) {
                            Logger logger =
                                    Xpdl2ProcessEditorPlugin.getDefault()
                                            .getLogger();
                            logger.error(e);
                        }
                    }
                }
            }
        }
        if (compoundCmd != null && compoundCmd.isEmpty()) {
            compoundCmd = null;
        }
        return compoundCmd;
    }

    /**
     * @param ed
     * @param act
     * @param taskType2
     * @param id
     * @param implementationType
     * @return
     */
    private Command getSetEmailTypeParticipant(EditingDomain ed,
            Activity activity) {
        CompoundCommand cmd = new CompoundCommand();
        Package pckg = activity.getProcess().getPackage();
        Participant participant = findEmailTypeParticipant(activity, pckg);
        if (null == participant) {
            participant = createParticipantForEmailTask(activity, pckg);
            if (null != participant) {
                cmd.append(AddCommand.create(ed, activity.getProcess()
                        .getPackage(), Xpdl2Package.eINSTANCE
                        .getParticipantsContainer_Participants(), participant));
            }
        }
        if (null != participant) {
            /*
             * set the participant in the activity performers
             */
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add(participant);
            Command perfCommand =
                    TaskObjectUtil.getSetPerformersCommand(ed,
                            activity,
                            perfs.toArray(new EObject[0]));
            if (perfCommand != null) {
                cmd.append(perfCommand);
            }
        }

        return cmd;
    }

    private Participant createParticipantForEmailTask(Activity activity,
            Package pckg) {
        Participant sysParticipant = Xpdl2Factory.eINSTANCE.createParticipant();
        String participantName =
                Messages.TaskImplementationDetailsSection_DefaultEMailSender_label;
        String uniqueDisplayNameInSet =
                Xpdl2ModelUtil.getUniqueDisplayNameInSet(participantName,
                        pckg.getParticipants(),
                        false);

        sysParticipant.setName(NameUtil.getInternalName(uniqueDisplayNameInSet,
                false));
        Xpdl2ModelUtil.setOtherAttribute(sysParticipant,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                uniqueDisplayNameInSet);

        // Set system participant type
        ParticipantTypeElem typeElem =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        typeElem.setType(ParticipantType.SYSTEM_LITERAL);
        sysParticipant.setParticipantType(typeElem);

        /* Add the participant sharedResource extension */

        ParticipantSharedResource participantSharedResource =
                XpdExtensionFactory.eINSTANCE.createParticipantSharedResource();

        EmailResource emailResource =
                XpdExtensionFactory.eINSTANCE.createEmailResource();
        emailResource
                .setInstanceName(Messages.TaskImplementationDetailsSection_EmailSharedResourceName_tokenname);
        participantSharedResource.setSharedResource(emailResource);

        Xpdl2ModelUtil.setOtherElement(sysParticipant,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource(),
                participantSharedResource);

        return sysParticipant;
    }

    /**
     * XPD-538: EMail participants are categorized when the
     * {@link ParticipantSharedResource} Email object contained object is not
     * <code>null</code>.
     * 
     * @param activity
     */
    private Participant findEmailTypeParticipant(Activity activity, Package pckg) {
        List<Participant> partics = new ArrayList<Participant>();
        partics.addAll(pckg.getParticipants());
        if (activity.getProcess().getParticipants().size() > 0) {
            partics.addAll(activity.getProcess().getParticipants());
        }
        for (Participant partic : partics) {

            ParticipantTypeElem participantType = partic.getParticipantType();
            if (participantType != null
                    && ParticipantType.SYSTEM_LITERAL.equals(participantType
                            .getType())) {

                ParticipantSharedResource participantSharedResource =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(partic,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                if (null != participantSharedResource
                        && participantSharedResource.getEmail() != null) {
                    return partic;
                }
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        Activity act = getActivity();
        boolean forceUnspecified = false;

        if (implTypesCombo != null && act != null) {
            // If controls have been disposed then unregister model
            if (implTypesCombo.isDisposed()) {
                return;
            }

            if (!implSectionsMapById.isEmpty()) {
                // Get the service (name and ServiceSection) for the service
                // currently set in the model
                String currentImplId = getCurrentSelectedImplId();

                if (currentImplId == null) {
                    // No xpdExt implementation type - use xpdl2
                    // Unspecified/WebService/Other impl type.
                    currentImplId =
                            TaskObjectUtil.getTaskTypeImplementationName(act);
                }

                // Service name will be null during transition from one service
                // type to another - at this time
                // do nothing
                if (currentImplId != null) {
                    ImplSection currentImpl =
                            implSectionsMapById.get(currentImplId);

                    if (currentImpl != null) {
                        try {
                            // If service has changed then update then hide the
                            // previous section if available and show new
                            // section
                            if (currentImpl != previousSelectedImpl) {
                                // Update the combo
                                implTypesCombo.setText(currentImpl.toString());

                                // Hide previous section
                                if (previousSelectedImpl != null) {
                                    previousSelectedImpl.extensionElement
                                            .getISectionExec()
                                            .aboutToBeHidden();
                                }

                                // Update input
                                updateSectionInput(currentImpl);

                                // Show new section
                                currentImpl.extensionElement.getISectionExec()
                                        .aboutToBeShown();
                                implTypeBook.showPage(currentImpl.page);

                                // Update previous service cache
                                previousSelectedImpl = currentImpl;
                                Display.getDefault().asyncExec(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshTabs();
                                    }
                                });
                            }
                            // Refresh the current section
                            currentImpl.extensionElement.getISectionExec()
                                    .refresh();

                        } catch (CoreException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // Hide previous section if any
                        if (previousSelectedImpl != null) {
                            try {
                                previousSelectedImpl.extensionElement
                                        .getISectionExec().aboutToBeHidden();
                                previousSelectedImpl = null;
                            } catch (CoreException e) {
                            }
                        }
                        // No current service found - this means that the
                        // implementation for the service set
                        // is not available so show blank page and update the
                        // combo
                        forceUnspecified = true;
                        implTypeBook.showPage(noImplPage);
                    }
                }
            }
            String taskTypeImplementation =
                    TaskObjectUtil.getTaskImplementationExtensionId(act);

            if (taskTypeImplementation == null) {
                taskTypeImplementation =
                        TaskObjectUtil.getTaskTypeImplementationName(act);
            }

            if (forceUnspecified) {
                /*
                 * SID SIA-44: For unrecognised extension implementation type,
                 * add it to combo box and select it
                 */
                if (taskTypeImplementation == null) {
                    taskTypeImplementation =
                            ResourceBundle
                                    .getBundle("plugin", Locale.getDefault(), getClass().getClassLoader()).getString("_UI_Unknown_feature"); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }

            reloadTasks(implTypesCombo, taskTypeImplementation);

            //
            // If this is a reply to upstream request activity then disable the
            // controls because the request activity defines the operation etc.
            //
            boolean isReply = ReplyActivityUtil.isReplyActivity(act);
            implTypesCombo.setEnabled(!isReply);

            if (isReply) {
                String implLabel = implTypesCombo.getText();

                String reqLabel =
                        ReplyActivityUtil
                                .getRequestActivityLabel(ReplyActivityUtil
                                        .getRequestActivityForReplyActivity(act));
                if (reqLabel == null || reqLabel.length() == 0) {
                    reqLabel =
                            Messages.TaskImplementationDetailsSection_Undefined_label;
                }

                implTypesCombo
                        .setText(implLabel
                                + ": " + String.format(Messages.TaskImplementationDetailsSection_SetByActivity_label, reqLabel)); //$NON-NLS-1$
            }
        }
    }

    /**
     * Update the input of all service implementation sections
     * 
     * @param currentSelectedService
     * @throws CoreException
     */
    private void updateSectionInput(ImplSection currentSelectedService)
            throws CoreException {

        // Get input model to pass to the implementation section - this will
        // be the Activity
        Object inputModel = getActivity();

        if (inputModel != null) {
            for (ImplSection section : implSectionsMapById.values()) {
                // If this is the selected service then update it's input,
                // otherwise
                // set empty selection
                if (inputModel != null && currentSelectedService != null
                        && currentSelectedService.equals(section)) {
                    section.extensionElement.getISectionExec()
                            .setInput(getPart(),
                                    new StructuredSelection(inputModel));

                } else {
                    section.extensionElement.getISectionExec()
                            .setInput(getPart(), new StructuredSelection());
                }
            }

        }
    }

    private void reloadTasks(CCombo combo, String preselect) {
        if (combo == null) {
            return;
        }

        try {
            combo.removeAll();
            Activity act = getActivity();
            if (act == null) {
                return;
            }

            // discover all tasks defined by enabled destination environments
            Process process = act.getProcess();
            Collection<String> enabledDestinations =
                    DestinationUtil.getEnabledValidationDestinations(process);

            Bindings bindings = TasksBindingService.INSTANCE.getBindings();
            boolean isPageflow = Xpdl2ModelUtil.isPageflow(process);
            Set<TaskReference> tasks =
                    bindings.getTasks(enabledDestinations, taskType);
            TaskImplementationService implService =
                    TaskImplementationService.INSTANCE;
            Implementations impls = implService.getImplementations();
            Set<TaskImplementationElement> elements = impls.getTasks(taskType);
            Set<String> idForProcessType = new HashSet<String>();
            for (TaskImplementationElement element : elements) {
                boolean showForPageflow = element.getShowForPageflow();
                if (!isPageflow || showForPageflow) {
                    idForProcessType.add(element.getId());
                }
            }

            // convert tasks to names
            Set<String> taskNames = new HashSet<String>();
            // We want to show user's selection in combo
            // even if it is not supported by selected destination
            String preselectName = null;
            if (preselect != null) {
                ImplSection section = implSectionsMapById.get(preselect);
                if (section != null) {
                    preselectName = section.toString();
                    taskNames.add(preselectName);
                } else {
                    /*
                     * SID SIA-44: For unrecognised extension implementation
                     * type, add it to combo box and select it
                     */taskNames.add(preselect);
                }
            }

            for (TaskReference reference : tasks) {
                String taskId = reference.getId();
                if (idForProcessType.contains(taskId)) {
                    ImplSection section = implSectionsMapById.get(taskId);
                    if (section != null) {
                        String taskName = section.toString();
                        taskNames.add(taskName);
                    } else {
                        log.warn("Task with id " + taskId + " doesn't match any section. Misspeled id?"); //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            }
            // add all tasks when there is no destination selected
            if (enabledDestinations.size() == 0) {
                Set<TaskImplementationElement> allTasks =
                        TaskImplementationService.INSTANCE.getImplementations()
                                .getTasks(taskType);
                for (TaskImplementationElement taskId : allTasks) {
                    if (idForProcessType.contains(taskId.getId())) {
                        ImplSection section =
                                implSectionsMapById.get(taskId.getId());
                        taskNames.add(section.toString());
                    }
                }
            }
            // fill the list. mind user's selection
            List<String> sortedTaskNames = new ArrayList<String>(taskNames);
            Collections.sort(sortedTaskNames);
            int itemIndex = 0;
            for (String taskName : sortedTaskNames) {
                combo.add(taskName);
                if (preselectName != null) {
                    if (taskName.equals(preselectName)) {
                        combo.select(itemIndex);
                    }
                } else {
                    /*
                     * SID SIA-44: For unrecognised extension implementation
                     * type, add it to combo box and select it
                     */if (taskName.equals(preselect)) {
                        combo.select(itemIndex);
                    }
                }
                itemIndex++;
            }

        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * Get the currently selected service from the model
     * 
     * @return
     */
    private ImplSection getCurrentSelectedImpl() {
        ImplSection currentService = null;
        String serviceName = getCurrentSelectedImplId();

        if (serviceName != null) {
            currentService = implSectionsMapById.get(serviceName);
        }

        return currentService;

    }

    /**
     * Get the currently selected service name from the model
     * 
     * @return
     */
    private String getCurrentSelectedImplId() {
        String serviceName = null;

        Activity act = getActivity();

        if (act != null) {
            serviceName = TaskObjectUtil.getTaskImplementationExtensionId(act);
        }

        return serviceName;
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    @Override
    public boolean select(Object toTest) {
        boolean select = super.select(toTest);
        if (select && !ProcessFeaturesUtil.isProcessDeveloperFeatureInstalled()) {
            select = false;
        }
        return select;
    }

    @Override
    public String getLocalId() {
        return "developer.implSection"; //$NON-NLS-1$
    }

    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    /**
     * This class executes the initialisation after the activity has been
     * properly created
     * 
     **/
    class TaskImplementationDetailsCompoundCommand extends CompoundCommand {

        private ITaskImplementationInitializer taskImplementationInitializer =
                null;

        private Activity activity;

        public TaskImplementationDetailsCompoundCommand(Activity activity) {
            this.activity = activity;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.CompoundCommand#execute()
         */
        @Override
        public void execute() {
            super.execute();
            if (taskImplementationInitializer != null) {
                Command cmd =
                        taskImplementationInitializer
                                .getInitialStructureCommand(this.activity);
                if (cmd != null) {
                    this.appendAndExecute(cmd);
                }
            }
        }

        /**
         * @param taskImplementationInitializer
         *            the taskImplementationInitializer to set
         */
        public void setTaskImplementationInitializer(
                ITaskImplementationInitializer taskImplementationInitializer) {
            this.taskImplementationInitializer = taskImplementationInitializer;
        }
    }

}
