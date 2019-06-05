/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.EventImplementationElement;
import com.tibco.xpd.processeditor.xpdl2.extensions.EventType;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.commands.ChangeEventTriggerTypeCommand;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Base class for all event process widgets.
 * 
 * @author Sid Allway
 */
public abstract class BaseEventGeneralSection extends
        SashDividedNamedElementSection implements IActivityManagerListener {

    private SortedSet<EventImplementationElement> implementations =
            new TreeSet<EventImplementationElement>();

    private static final String IMPL_SECTION_EXTENSION_POINT_ID =
            "eventImplementation"; //$NON-NLS-1$

    protected String instrumentationPrefixName = "Event"; //$NON-NLS-1$

    private Map<EventImplementationElement, Composite> pageMap =
            new HashMap<EventImplementationElement, Composite>();

    /**
     * Implementation selected before the update is made when a new item is
     * selected in the combo.
     */
    private EventImplementationElement previousImplConfig = null;

    private PageBook implBook;

    private String nameLabelChecker;

    /**
     * Controls and data for Task Type setting.
     */
    public class EventTriggerTypeSection {
        public EventTriggerType eventTriggerType;

        public String triggerTypeTextLabel;

        public Composite page = null;

        public AbstractXpdSection section = null;

        public EventTriggerTypeSection(EventTriggerType eventTrigger,
                AbstractXpdSection section, String btnText) {
            this.eventTriggerType = eventTrigger;
            this.section = section;
            this.triggerTypeTextLabel = btnText;
        }

    }

    private ScrolledComposite generalSection;

    private Composite generalContent;

    private String triggerTypeLabelText;

    /** Combo control with trigger (or result) types. */
    private CCombo triggerTypeCombo;

    Label triggerTypeLabel;

    private EventTriggerTypeSection currEventTriggerSection = null;

    /*
     * Type of task specific controls are held in a page book (each type can
     * have its own page, we show correct one for currently selected type).
     */
    private PageBook eventTriggerTypeSpecificPageBook;

    /**
     * List of available trigger types and sections to be provided by sub
     * classes
     */
    private List<EventTriggerTypeSection> eventTriggerTypeSections;

    private Composite detailsContainer;

    private Composite emptyPage;

    private FormText solutionDesignForm;

    private boolean firstRefreshSinceSetInput = true;

    private Composite addiditonalControlsComp;

    /**
     * Constructor initialise trigger types and label.
     */
    protected BaseEventGeneralSection(EventType eventType) {
        super(Xpdl2Package.eINSTANCE.getActivity(),
                Xpdl2ProcessEditorPlugin.ID + ".BaseEventGeneralSection"); //$NON-NLS-1$

        this.triggerTypeLabelText = getTriggerTypeLabelText();
        this.eventTriggerTypeSections = getEventTriggerTypeSections();

        IExtensionRegistry er = Platform.getExtensionRegistry();

        //
        // Old process widget eventImplementation extension point is deprecated.
        // We will load contributions from processwidget and
        // processeditor.xpdl2.

        // Old processwdiget extension.
        IExtensionPoint ep = er.getExtensionPoint(ProcessWidgetPlugin.ID,
                IMPL_SECTION_EXTENSION_POINT_ID);

        IExtension[] extensions = ep.getExtensions();
        if (extensions != null) {
            for (int i = 0; i < extensions.length; i++) {
                IExtension extension = extensions[i];
                IConfigurationElement[] configElements =
                        extension.getConfigurationElements();
                for (int j = 0; j < configElements.length; j++) {
                    IConfigurationElement configElement = configElements[j];

                    // Only bother loading the implementations for the
                    // subclass's event type.
                    if (eventType.equals(EventImplementationElement
                            .getEventType(configElement))) {
                        implementations.add(
                                new EventImplementationElement(configElement));
                    }
                }
            }
        }

        // New process editor extension.
        ep = er.getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                IMPL_SECTION_EXTENSION_POINT_ID);

        extensions = ep.getExtensions();
        if (extensions != null) {
            for (int i = 0; i < extensions.length; i++) {
                IExtension extension = extensions[i];
                IConfigurationElement[] configElements =
                        extension.getConfigurationElements();
                for (int j = 0; j < configElements.length; j++) {
                    IConfigurationElement configElement = configElements[j];
                    // Only bother loading the implementations for the
                    // subclass's event type.
                    if (eventType.equals(EventImplementationElement
                            .getEventType(configElement))) {
                        implementations.add(
                                new EventImplementationElement(configElement));
                    }
                }
            }
        }

    }

    /**
     * Sub-class this to provide a list of event trigger types handled by the
     * event type and the trigger type specific Isection's that deal with them
     * 
     * @return
     */
    protected abstract List<EventTriggerTypeSection> getEventTriggerTypeSections();

    protected abstract String getTriggerTypeLabelText();

    /**
     * @return Indicator for whether to use implementation section.
     */
    private boolean isImplementationEnabled() {
        for (EventImplementationElement implElements : implementations) {
            if (currEventTriggerSection != null
                    && currEventTriggerSection.eventTriggerType
                            .equals(implElements.getTriggerResultType())) {
                if (!WorkbenchActivityHelper.filterItem(implElements)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setInput(Collection<?> items) {

        firstRefreshSinceSetInput = true;
        super.setInput(items);

        EventTriggerType eventTriggerType = null;
        Activity activity = getActivity();

        if (activity != null) {
            eventTriggerType = EventObjectUtil.getEventTriggerType(activity);
        }

        setEventTriggerTypePageInput(eventTriggerType);

        // Get input model to pass to the implementation section - this will
        // be the Activity
        if (activity != null) {
            // find the right section to pass the input to
            EventImplementationElement currentImplConfigElement =
                    getImplConfigElement(activity);

            if (currentImplConfigElement != null) {
                ISection implSection =
                        getImplSectionFromConfig(currentImplConfigElement);
                for (EventTriggerTypeSection trigTypeSect : eventTriggerTypeSections) {
                    if (trigTypeSect.eventTriggerType
                            .equals(eventTriggerType)) {
                        currEventTriggerSection = trigTypeSect;
                        eventTriggerTypeSpecificPageBook
                                .showPage(currEventTriggerSection.page);
                        eventTriggerTypeSpecificPageBook.getParent().getParent()
                                .layout(true);
                    }
                }
                setInputToImplSection(implSection);
            }
        }

    }

    /**
     * Find a configured extension to handle the given name. Returns null if
     * none found. This is used to find the value selected by the user in the
     * UI.
     * 
     * @param activity
     *            Event to find ocnfig element for.
     * @return Matching config element (or null if none found).
     */
    private EventImplementationElement getImplConfigElement(Activity activity) {
        EventTriggerType type = EventObjectUtil.getEventTriggerType(activity);

        for (EventImplementationElement element : implementations) {
            if (!WorkbenchActivityHelper.filterItem(element)
                    && type.equals(element.getTriggerResultType())) {
                IFilter filter = element.getFilterExec();
                if (filter == null
                        // Filter on underlying model object because this is
                        // what
                        // implementations will deal with.
                        || filter.select(getInput())) {
                    return element;
                }
            }
        }
        return null;
    }

    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {

        generalSection = toolkit.createScrolledComposite(parent,
                SWT.V_SCROLL | SWT.H_SCROLL);
        generalSection.setExpandHorizontal(true);
        generalSection.setExpandVertical(true);

        generalContent = toolkit.createComposite(generalSection);
        generalSection.setContent(generalContent);

        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        generalContent.setLayout(gl);

        /*
         * ABPM-911: Saket: Will need to create event controls and event type
         * specific controls separately now as we want to add a few controls
         * between them for an event subprocess start event.
         */
        Composite comp = createEventControls(generalContent, toolkit);
        /*
         * ABPM-911: Should always set layout data, wasn't done before ABPM-911.
         */
        comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // refreshLayout();
        return generalSection;
    }

    /**
     * @param parent
     * @param toolkit
     * @return Optional composite to insert betwen type selction combo and
     *         trigger type specific content.
     */
    protected Composite createAdditionalTypeControls(Composite parent,
            XpdFormToolkit toolkit) {

        return null;
    }

    protected void hideAdditionalControlsSection(boolean hide) {
        GridData data = new GridData(GridData.FILL_BOTH);
        if (hide) {
            data.heightHint = 1;
        }

        addiditonalControlsComp.setVisible(!hide);
        addiditonalControlsComp.setLayoutData(data);

        generalContent.layout(true);
    }

    /**
     * 
     */
    private void disableIfImplemented(Activity activity) {
        if (ProcessInterfaceUtil.isEventImplemented(activity)) {
            name.setEnabled(false);
            display.setEnabled(false);
            triggerTypeCombo.setEnabled(false);
        } else {
            name.setEnabled(true);
            display.setEnabled(true);
            triggerTypeCombo.setEnabled(true);
        }
    }

    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        detailsContainer = toolkit.createComposite(parent);
        detailsContainer.setLayout(new GridLayout());

        // Create controls for the extensions
        createImplControls(detailsContainer, toolkit);
        if (implBook != null) {
            implBook.setLayoutData(new GridData(GridData.FILL_BOTH));
        }

        return detailsContainer;
    }

    /**
     * Create controls for the message implementation section.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    private Control createImplControls(Composite parent,
            XpdFormToolkit toolkit) {
        implBook = new PageBook(parent, SWT.NONE);// toolkit.createPageBook(parent
        // ,
        // SWT.NONE);
        for (EventImplementationElement impl : implementations) {
            Composite page = toolkit.createComposite(implBook);
            // implBook.createPage(impl);
            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            page.setLayout(fillLayout);
            pageMap.put(impl, page);
            ISection implSection = getImplSectionFromConfig(impl);
            implSection.createControls(page, getPropertySheetPage());
        }

        // Register listener for changes to capability that affect this section.
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(this);

        return implBook;
    }

    /**
     * Get the properties section a given configuration for the message
     * implementation. Handle exception in cofiguration (This will probably
     * cause NPE downstream so log cause here).
     * 
     * @param impl
     *            Config Element.
     * @return Instance of section (or null if exception occurred).
     */
    private ISection getImplSectionFromConfig(EventImplementationElement impl) {
        ISection implSection = null;
        try {
            implSection = impl.getISectionExec();
        } catch (CoreException e) {
            Logger logger = Xpdl2ProcessEditorPlugin.getDefault().getLogger();
            logger.error(e);
        }
        return implSection;
    }

    /**
     * Create event specific controls across the top of the general sections.
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    protected Composite createEventControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite eventCmp = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 1;
        layout.marginHeight = 1;
        eventCmp.setLayout(layout);
        // gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        // gData.widthHint = WRAPPED_LABEL_WIDTH;
        // nameLabel.setLayoutData(gData);

        triggerTypeLabel = toolkit.createLabel(eventCmp, triggerTypeLabelText);

        triggerTypeCombo = toolkit.createCCombo(eventCmp,
                SWT.READ_ONLY | SWT.SINGLE,
                instrumentationPrefixName + "TriggerType"); //$NON-NLS-1$
        triggerTypeCombo.setData("name", "comboEventTriggerType"); //$NON-NLS-1$ //$NON-NLS-2$
        triggerTypeCombo.setData(
                TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                TabbedPropertySheetWidgetFactory.TEXT_BORDER);
        for (EventTriggerTypeSection trigType : eventTriggerTypeSections) {
            triggerTypeCombo.add(trigType.triggerTypeTextLabel);
            triggerTypeCombo.setData(trigType.triggerTypeTextLabel,
                    trigType.eventTriggerType);
        }
        triggerTypeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        manageControl(triggerTypeCombo);

        /*
         * Controls between combox type select and trig type specific section
         */
        Composite filler = toolkit.createComposite(eventCmp);
        GridData fillGd = new GridData();
        fillGd.heightHint = 1;
        filler.setLayoutData(fillGd);

        addiditonalControlsComp =
                createAdditionalTypeControls(eventCmp, toolkit);
        if (addiditonalControlsComp != null) {
            addiditonalControlsComp
                    .setLayoutData(new GridData(GridData.FILL_BOTH));
        } else {
            addiditonalControlsComp = toolkit.createComposite(eventCmp);
            fillGd = new GridData();
            fillGd.heightHint = 1;
            addiditonalControlsComp.setLayoutData(fillGd);
        }

        /*
         * Trigger type speciifc controls.
         */
        Composite filler2 = toolkit.createComposite(eventCmp);
        fillGd = new GridData();
        fillGd.heightHint = 1;
        filler2.setLayoutData(fillGd);

        //
        // The page book for event type specific controls
        //

        Control trigTypeCtls =
                createEventTriggerTypeControls(eventCmp, toolkit);
        trigTypeCtls.setLayoutData(new GridData(GridData.FILL_BOTH));

        //
        // The switch on solution design hyperlink.
        Composite filler3 = toolkit.createComposite(eventCmp);
        fillGd = new GridData();
        fillGd.heightHint = 1;
        filler3.setLayoutData(fillGd);

        solutionDesignForm = toolkit.createFormText(eventCmp, true);
        /*
         * Must ensure we set SOME text on FormText before we do first layout
         * else subsequent ones will do nothing!
         */
        solutionDesignForm.setText(Messages.AddSolutionDesignCapability_form,
                true,
                false);

        GridData layoutData = new GridData(
                GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END);
        solutionDesignForm.setLayoutData(layoutData);

        manageControl(solutionDesignForm);

        int minHeight = generalSection.getMinHeight();
        minHeight += triggerTypeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        minHeight +=
                addiditonalControlsComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        minHeight += solutionDesignForm.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

        generalSection.setMinHeight(minHeight + 15);

        return eventCmp;
    }

    @Override
    protected Command doGetDetailsCommand(Object obj) {
        Command cmd = null;

        Activity activity = getActivity();

        EditingDomain ed = getEditingDomain();

        if (activity != null && ed != null) {
            // Update name
            if (obj == triggerTypeCombo) {
                int selIndex = triggerTypeCombo.getSelectionIndex();
                if (selIndex >= 0) {
                    /*
                     * Sid ACE-1485 Cannot rely on selection index in combo
                     * being same index in ALL event type sections because some
                     * of the combo list is filtered out. Use the data added to
                     * combo for it instead.
                     */

                    String itemText = triggerTypeCombo.getItem(selIndex);

                    EventTriggerType newTrigType =
                            (EventTriggerType) triggerTypeCombo
                                    .getData(itemText);

                    if (newTrigType != null) {
                        EventTriggerType curTrigType =
                                EventObjectUtil.getEventTriggerType(activity);

                        if (!newTrigType.equals(curTrigType)) {
                            // Switch to different task type.
                            cmd = ChangeEventTriggerTypeCommand
                                    .create(ed, activity, newTrigType);
                        }
                    }
                }
            }
        }

        return cmd;
    }

    @Override
    protected String getNamedElementName() {
        String nameText = null;

        Activity activity = getActivity();

        if (activity != null) {
            if (ProcessInterfaceUtil.isEventImplemented(activity)
                    && ProcessInterfaceUtil
                            .getImplementedMethod(activity) != null) {
                InterfaceMethod method =
                        ProcessInterfaceUtil.getImplementedMethod(activity);
                if (method != null) {
                    nameText = method.getName();
                } else {
                    nameText =
                            Messages.BaseEventGeneralSection_UnresolvedName_label;
                }
            } else if (ProcessInterfaceUtil.isEventImplemented(activity)
                    && ProcessInterfaceUtil
                            .getImplementedErrorMethod(activity) != null) {

                InterfaceMethod interfaceMethod = null;
                ErrorMethod errorMethod = ProcessInterfaceUtil
                        .getImplementedErrorMethod(activity);

                if (errorMethod.eContainer() instanceof InterfaceMethod) {
                    interfaceMethod =
                            (InterfaceMethod) errorMethod.eContainer();
                    nameText = interfaceMethod.getName();
                } else {
                    nameText =
                            Messages.BaseEventGeneralSection_UnresolvedName_label;
                }

            } else {
                nameText = super.getNamedElementName();
            }
        }

        return nameText == null ? "" : nameText; //$NON-NLS-1$
    }

    /**
     * 
     * @return <code>true</code> if the Solution Design Form should be shown.
     */
    protected boolean shouldShowSolutionDesignForm() {
        if (ProcessFeaturesUtil.isProcessDeveloperFeatureInstalled()
                && !XpdResourcesPlugin.isRCP()) {
            /*
             * Show only if Process Dev Feature is installed and its not
             * Analyst.
             */
            Activity activity = getActivity();
            if (activity != null) {
                /*
                 * XPD-7263: Use the 'eventImplementation' extension point to
                 * decide if the Solution Design form hyperlink should be
                 * visible or not becuase if we have contributed implementation
                 * section of an activity then we would want to show the
                 * hyperlink.
                 */
                EventTriggerType type =
                        EventObjectUtil.getEventTriggerType(activity);

                EventImplementationElement implConfigElement = null;
                /*
                 * Note that we do not call the getImplConfigElement() method to
                 * get the Event Implementation Element because this method uses
                 * the plugin filter
                 * (WorkbenchActivityHelper.filterItem(element)) which would not
                 * let us check for the 'EventImplementationElement' is the
                 * solution design capability is off , because the necessary
                 * plugin would be filtered out.
                 */
                for (EventImplementationElement element : implementations) {
                    if (type.equals(element.getTriggerResultType())) {
                        IFilter filter = element.getFilterExec();
                        if (filter == null
                                // Filter on underlying model object because
                                // this is
                                // what
                                // implementations will deal with.
                                || filter.select(getInput())) {
                            implConfigElement = element;
                            break;
                        }
                    }
                }
                return implConfigElement != null;
            }
        }
        return false;
    }

    @Override
    protected void doRefreshDetailsSection() {
        if (!CapabilityUtil.isDeveloperActivityEnabled()) {
            solutionDesignForm.setText(
                    Messages.AddSolutionDesignCapability_form,
                    true,
                    false);
        } else {
            solutionDesignForm.setText(
                    Messages.RemoveSolutionDesignCapability_form,
                    true,
                    false);
        }

        if (shouldShowSolutionDesignForm()) {
            if (!solutionDesignForm.getVisible()) {
                GridData layoutData = new GridData(GridData.FILL_HORIZONTAL
                        | GridData.HORIZONTAL_ALIGN_END);
                solutionDesignForm.setLayoutData(layoutData);
                solutionDesignForm.setVisible(true);
            }
        } else {
            if (solutionDesignForm.getVisible()) {
                GridData layoutData = new GridData(GridData.FILL_HORIZONTAL
                        | GridData.HORIZONTAL_ALIGN_END);
                solutionDesignForm.setLayoutData(layoutData);
                solutionDesignForm.setVisible(false);
            }
        }
        // If controls are disposed the unregister adapter
        if (name.isDisposed()) {
            return;
        }
        Activity activity = getActivity();
        disableIfImplemented(activity);
        if (activity != null) {
            // Update the name
            if (ProcessInterfaceUtil.isEventImplemented(activity)
                    && ProcessInterfaceUtil
                            .getImplementedErrorMethod(activity) != null) {
                nameLabel.setText(
                        Messages.BaseEventGeneralSection_ImplementsErrorFor_Label);

            } else if (ProcessInterfaceUtil.isEventImplemented(activity)
                    && ProcessInterfaceUtil
                            .getImplementedMethod(activity) != null) {

                if (ReplyActivityUtil.isReplyActivity(activity)) {
                    nameLabel.setText(
                            Messages.BaseEventGeneralSection_ImplementsReplyTo_Label);
                } else {
                    nameLabel.setText(
                            Messages.BaseEventGeneralSection_ImplementsMethod_label);
                }

            } else {
                nameLabel.setText(Messages.BaseEventGeneralSection_Name_label);
            }

            if (!nameLabel.getText().equals(nameLabelChecker)) {

                nameLabel.setLayoutData(
                        new GridData(SWT.FILL, SWT.CENTER, false, false));
                nameLabel.getParent().layout();
                nameLabel.getParent().redraw();
                nameLabelChecker = nameLabel.getText();
            }

            // Update event trigger type
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);

            // Find the task type section for the latest task type.
            EventTriggerTypeSection newEventTriggerTypeSection = null;

            for (EventTriggerTypeSection trigTypeSect : eventTriggerTypeSections) {
                if (trigTypeSect.eventTriggerType.equals(eventTriggerType)) {
                    newEventTriggerTypeSection = trigTypeSect;
                    break;
                }
            }

            refreshEventTypeCombo(newEventTriggerTypeSection, activity);

            setEventTriggerTypePageInput(eventTriggerType);

            // If it has changed then reset the inputs.
            if (newEventTriggerTypeSection != null
                    && newEventTriggerTypeSection != currEventTriggerSection) {

                if (currEventTriggerSection != null
                        && currEventTriggerSection.section != null) {
                    currEventTriggerSection.section.aboutToBeHidden();
                }
                if (newEventTriggerTypeSection.section != null) {
                    newEventTriggerTypeSection.section.aboutToBeShown();
                }
                eventTriggerTypeSpecificPageBook
                        .showPage(newEventTriggerTypeSection.page);

                currEventTriggerSection = newEventTriggerTypeSection;

                refreshLayout();
                localRefreshTabs();
            }

            // Refresh whatever task type section is now current.
            if (currEventTriggerSection != null) {
                if (currEventTriggerSection.section != null) {
                    currEventTriggerSection.section.refresh();
                    refreshLayout();
                }
            }

            /*
             * XPD-4739: Saket: We have decided to deprecate Process Interface
             * Message Event capability, but still support for backward
             * compatibility. Hence we hide the event type selection controls on
             * property sheet unless the process interface event is already set
             * to message type.
             */
            InterfaceMethod implementedEvent =
                    ProcessInterfaceUtil.getImplementedMethod(activity);
            TriggerType msgLiteral =
                    com.tibco.xpd.xpdl2.TriggerType.MESSAGE_LITERAL;
            if (!triggerTypeCombo.isDisposed()) {
                if (implementedEvent != null && !(msgLiteral
                        .equals(implementedEvent.getTrigger()))) {
                    triggerTypeCombo.setVisible(false);
                    triggerTypeLabel.setVisible(false);
                } else {
                    triggerTypeCombo.setVisible(true);
                    triggerTypeLabel.setVisible(true);
                }
            }
        }
    }

    /**
     * Refresh the items in the event type combo list. This should include only
     * those types that have not been excluded by preferences PLUS the currently
     * selected event type.
     * 
     * @param currEventType
     * @param activity
     */
    private void refreshEventTypeCombo(
            EventTriggerTypeSection currentTriggerTypeSection,
            Activity activity) {
        if (currentTriggerTypeSection != null) {
            EventTriggerType currEventType =
                    currentTriggerTypeSection.eventTriggerType;

            /*
             * Only add the types available according to object exclusions
             */
            Set<ProcessEditorObjectType> excludedObjectTypes =
                    ProcessEditorConfigurationUtil
                            .getExcludedObjectTypes(activity.getProcess());

            Set<EventTriggerType> includedEventTypes =
                    new HashSet<EventTriggerType>();

            EventFlowType flowType = EventObjectUtil.getFlowType(activity);

            for (EventTriggerTypeSection ets : eventTriggerTypeSections) {
                ProcessEditorObjectType objectType = ets.eventTriggerType
                        .getProcessEditorObjectType(flowType);

                if (!excludedObjectTypes.contains(objectType)) {
                    includedEventTypes.add(ets.eventTriggerType);
                }
            }

            /* Always include the current task type. */
            includedEventTypes.add(currEventType);

            /* Check if there is a change in the list from current. */
            boolean typesChanged = false;

            if (triggerTypeCombo.getItemCount() != includedEventTypes.size()) {
                typesChanged = true;

            } else {
                for (int j = 0; j < triggerTypeCombo.getItemCount(); j++) {
                    String itemText = triggerTypeCombo.getItem(j);
                    Object itemTaskType = triggerTypeCombo.getData(itemText);

                    if (!includedEventTypes.contains(itemTaskType)) {
                        typesChanged = true;
                        break;
                    }
                }
            }

            if (typesChanged) {
                triggerTypeCombo.removeAll();

                for (EventTriggerTypeSection ets : eventTriggerTypeSections) {
                    if (includedEventTypes.contains(ets.eventTriggerType)) {
                        triggerTypeCombo.add(ets.triggerTypeTextLabel);
                        triggerTypeCombo.setData(ets.triggerTypeTextLabel,
                                ets.eventTriggerType);
                    }
                }
            }

            triggerTypeCombo
                    .setText(currentTriggerTypeSection.triggerTypeTextLabel);
            triggerTypeCombo.setSelection(new Point(0, 0));
        }
    }

    /**
     */
    @Override
    protected void doRefreshImplementationSection() {

        boolean shouldShowImplementationSection = true;

        if (isImplementationEnabled()) {

            Activity activity = getActivity();

            if (activity != null) {
                // Get new information from model.
                EventImplementationElement currentImplConfigElement =
                        getImplConfigElement(activity);

                ISection implSection = null;
                if (currentImplConfigElement != null) {
                    implSection =
                            getImplSectionFromConfig(currentImplConfigElement);
                }
                if (currentImplConfigElement == null
                        && previousImplConfig == null) {
                    showEmptyPage();
                    /* hide implementation section */
                    shouldShowImplementationSection = false;
                } else if (currentImplConfigElement != null
                        && previousImplConfig == null) {
                    setInputToImplSection(implSection);
                    /*
                     * implSection.setInput(getPart(), new StructuredSelection(
                     * inputModel));
                     */
                    implBook.showPage(pageMap.get(currentImplConfigElement));
                    // implBook.showPage(currentImplConfigElement);
                    previousImplConfig = currentImplConfigElement;
                    localRefreshTabs();
                } else if (currentImplConfigElement != null
                        && !currentImplConfigElement
                                .equals(previousImplConfig)) {
                    ISection prevSection =
                            getImplSectionFromConfig(previousImplConfig);
                    prevSection.setInput(getPart(), StructuredSelection.EMPTY);
                    /*
                     * implSection.setInput(getPart(), new StructuredSelection(
                     * inputModel));
                     */
                    setInputToImplSection(implSection);
                    implBook.showPage(pageMap.get(currentImplConfigElement));
                    // implBook.showPage(currentImplConfigElement);
                    previousImplConfig = currentImplConfigElement;
                    localRefreshTabs();
                } else if (currentImplConfigElement == null
                        && previousImplConfig != null) {
                    ISection prevSection =
                            getImplSectionFromConfig(previousImplConfig);
                    prevSection.setInput(getPart(), StructuredSelection.EMPTY);
                    showEmptyPage();
                    /* hide implementation section */
                    shouldShowImplementationSection = false;
                    previousImplConfig = currentImplConfigElement;
                    localRefreshTabs();
                } else { // not null and not changed
                    implBook.showPage(pageMap.get(currentImplConfigElement));
                    // implBook.showPage(currentImplConfigElement);
                    implSection.refresh();

                    /**
                     * XPD-7138: Removed the localRefreshTabs() call from here
                     * (added during event sub-process work) because it caused
                     * the following problem...
                     * 
                     * Import the attached project open the
                     * ProcessAsAServiceProcess, go to the Input To Process
                     * property Tab, select script for script mapping, go to
                     * script editor and type something then wait for a couple
                     * of seconds. The script control goes blank grey and the
                     * script must be reselected in mapper before the script can
                     * be edited again (at which point it will go blank again).
                     * 
                     * The cause of the problem was this call to refreshTabs()
                     * which does a setInput() on all tabs which causes loss of
                     * mapping Script selection which causes mappings script
                     * editor to go blank.
                     * 
                     * The only reason I can see for this refreshTabs() being
                     * added is that it is (was) possible to come thru here,
                     * without the implSection having changed EVENT THOUGH the
                     * event type had changed. This can happen when you change
                     * from event type with impl section (say message) to event
                     * -type without impl section (say Conditional). If you then
                     * select the same type with impl section back again (say
                     * undo) then it comes thru here even though event type
                     * changed BECAUSE we don't clear previousImplConfig when
                     * you change from event-type with impl to event type
                     * without-impl. I've now dealt with that below BUT having
                     * said all this, I still couldn't see any specific problem
                     * when I removed the refreshTabs() before I made the other
                     * change below.
                     * 
                     * localRefreshTabs();
                     */

                }
            }
        } else {
            /**
             * XPD-7138. Should really set previousImpl type to null when we've
             * swicthed to a type that doesn't have impl section (so that if we
             * switch back to that type again, we will go thru the appropriate
             * reset parts above.
             */
            previousImplConfig = null;

            /* hide implementation section */
            shouldShowImplementationSection = false;
        }

        if (shouldShowImplementationSection) {
            /* show the implementation section */
            setSashPercentToLastUserSelected();
        } else {
            /* hide implementation section */
            setSashPercent(1.0f);
        }

        firstRefreshSinceSetInput = false;
    }

    /**
     * @param implBook2
     */
    private void showEmptyPage() {
        if (implBook != null) {
            if (emptyPage == null) {
                emptyPage = createPage();
                emptyPage.setLayout(new GridLayout());
            }
            implBook.showPage(emptyPage);
        }
    }

    private Composite createPage() {
        Composite page = new Composite(implBook, SWT.NULL);
        page.setBackground(implBook.getBackground());
        page.setForeground(implBook.getForeground());
        page.setMenu(implBook.getMenu());
        return page;
    }

    /**
     * 
     */
    private void refreshLayout() {
        int pages = eventTriggerTypeSections.size();
        int tallest = 0;
        for (int i = 0; i < pages; i++) {
            EventTriggerTypeSection sect = eventTriggerTypeSections.get(i);
            Point sz = sect.page.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
            if (sz.y > tallest) {
                tallest = sz.y;
            }
        }
        tallest +=
                triggerTypeCombo.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y;
        tallest += 5;

        generalSection.setMinSize(SWT.DEFAULT, tallest);
        generalSection.layout(true, true);

        forceLayout();
    }

    private void localRefreshTabs() {
        if (!firstRefreshSinceSetInput) {
            refreshTabs();
        }
    }

    @Override
    public void dispose() {
        if (isCreated()) {
            for (EventTriggerTypeSection trigType : eventTriggerTypeSections) {
                if (trigType.section != null) {
                    trigType.section.dispose();
                }
            }
            PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                    .removeActivityManagerListener(this);
        }
        super.dispose();
    }

    private void setEventTriggerTypePageInput(
            EventTriggerType eventTriggerType) {
        // Set the input for task type specific page
        // and unset all others (by giving them empty selection.
        StructuredSelection emptySel = new StructuredSelection();

        for (EventTriggerTypeSection trigType : eventTriggerTypeSections) {
            if (trigType.section != null) {
                if (trigType.eventTriggerType.equals(eventTriggerType)) {
                    trigType.section.setInput(getPart(), getSelection());
                } else {
                    trigType.section.setInput(getPart(), emptySel);
                }
            }
        }
    }

    private Control createEventTriggerTypeControls(Composite root,
            XpdFormToolkit f) {

        eventTriggerTypeSpecificPageBook = new PageBook(root, SWT.NONE);
        eventTriggerTypeSpecificPageBook.setBackground(root.getBackground());

        // Create the pages for task type specifics...
        createEventTriggerTypePages(f, eventTriggerTypeSpecificPageBook);

        return eventTriggerTypeSpecificPageBook;
    }

    private void createEventTriggerTypePages(XpdFormToolkit f,
            PageBook pageBook) {
        int minHeight = 0;

        for (EventTriggerTypeSection trigType : eventTriggerTypeSections) {
            trigType.page = f.createComposite(pageBook);
            trigType.page.setLayoutData(new GridData(GridData.FILL_BOTH));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            trigType.page.setLayout(fillLayout);

            if (trigType.section != null) {
                trigType.section.createControls(trigType.page,
                        getPropertySheetPage());

                Point sz = trigType.page.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                if (sz.y > minHeight) {
                    minHeight = sz.y;
                }
            }
        }

        generalSection.setMinHeight(minHeight);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
     * aboutToBeHidden()
     */
    @Override
    public void aboutToBeHidden() {
        super.aboutToBeHidden();
        if (currEventTriggerSection != null
                && currEventTriggerSection.section != null) {
            currEventTriggerSection.section.aboutToBeHidden();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#
     * aboutToBeShown ()
     */
    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        if (currEventTriggerSection != null
                && currEventTriggerSection.section != null) {
            currEventTriggerSection.section.aboutToBeShown();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.activities.IActivityManagerListener#activityManagerChanged
     * (org.eclipse.ui.activities.ActivityManagerEvent)
     */
    @Override
    public void activityManagerChanged(
            ActivityManagerEvent activityManagerEvent) {
        if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
            if (isImplementationEnabled()) {
                showAlignmentButtons();
            } else {
                hideAlignmentButtons();
            }
            doRefreshImplementationSection();
        }
    }

    private void setInputToImplSection(ISection implSection) {
        Object inputModel = getInput();
        if (implSection != null && isImplementationEnabled()
                && implSection instanceof AbstractXpdSection) {
            implSection.setInput(getPart(),
                    new StructuredSelection(inputModel));
            implSection.refresh();
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * @param instrumentationPrefixName
     */
    public void setInstrumentationPrefixName(String instrumentationPrefixName) {
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    /**
     * XPD-7138 - Noticed layotu issue start event event sub-process when
     * changing from message to timer event and back again.
     * 
     * @return The root composite for LHS of general section.
     */
    protected Composite getLHSRootComposite() {
        return generalContent;
    }
}
