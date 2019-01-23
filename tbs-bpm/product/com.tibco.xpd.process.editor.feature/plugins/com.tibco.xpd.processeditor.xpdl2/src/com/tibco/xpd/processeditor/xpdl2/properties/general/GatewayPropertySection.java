/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.SashDividedNamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.properties.gateway.ComplexGatewayDetailsSection;
import com.tibco.xpd.processeditor.xpdl2.properties.gateway.ExclusiveDataGatewaySection;
import com.tibco.xpd.processeditor.xpdl2.properties.gateway.NonComplexGatewayTypeSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.commands.ChangeGatewayTypeCommand;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class GatewayPropertySection extends SashDividedNamedElementSection {
    private static final int EXCLUSIVE_DATA = 0;

    private static final int EXCLUSIVE_EVENT = 1;

    private static final int INCLUSIVE = 2;

    private static final int COMPLEX = 3;

    private static final int PARALLEL = 4;

    private Button[] types;

    protected String instrumentationPrefixName = ""; //$NON-NLS-1$

    private final IActivityManagerListener activityListener;

    /*
     * Type of task specific controls are held in a page book (each type can
     * have its own page, we show correct one for currently selected task).
     */
    private PageBook gatewayTypeSpecificPageBook;

    private ScrolledComposite detailsScrolledContainer;

    private GatewayTypeSection currentGatewaySection = null;

    /**
     * Controls and data for Task Type setting.
     */
    private class GatewayTypeSection {
        public int gatewayType;

        public Composite page = null;

        public ISection section = null;

        GatewayTypeSection(int gatewayType, ISection section) {
            this.gatewayType = gatewayType;
            this.section = section;
        }
    }

    private List<GatewayTypeSection> gatewaySections;

    public GatewayPropertySection() {
        // Basic selection type is activity.
        super(Xpdl2Package.eINSTANCE.getActivity(), Xpdl2ProcessEditorPlugin.ID
                + "GatewayGeneralSection"); //$NON-NLS-1$);
        setMinimumHeight(SWT.DEFAULT);
        setInstrumentationPrefixName("Gateway"); //$NON-NLS-1$

        gatewaySections = new ArrayList<GatewayTypeSection>();

        GatewayTypeSection tt =
                new GatewayTypeSection(GatewayPropertySection.COMPLEX,
                        new ComplexGatewayDetailsSection());
        gatewaySections.add(tt);

        tt =
                new GatewayTypeSection(GatewayPropertySection.INCLUSIVE,
                        new NonComplexGatewayTypeSection());
        gatewaySections.add(tt);

        tt =
                new GatewayTypeSection(GatewayPropertySection.PARALLEL,
                        new NonComplexGatewayTypeSection());
        gatewaySections.add(tt);

        tt =
                new GatewayTypeSection(GatewayPropertySection.EXCLUSIVE_DATA,
                        new ExclusiveDataGatewaySection());
        gatewaySections.add(tt);

        tt =
                new GatewayTypeSection(GatewayPropertySection.EXCLUSIVE_EVENT,
                        new NonComplexGatewayTypeSection());
        gatewaySections.add(tt);

        activityListener = new IActivityManagerListener() {
            @Override
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    doRefreshDetailsSection();
                }

            }
        };
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        int gatewayType = getGatewayType(activity);
        setGatewayTypePageInput(gatewayType);
    }

    /**
     * Create the general section of the properties (left hand side if
     * horizontal layout used). Subclasses should not set the LayoutData of the
     * composite being returned.
     * 
     * @param parent
     * @param factory
     */
    @Override
    protected Composite doCreateGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = 3;
        root.setLayout(gl);

        objectTypeCreateControls(root, toolkit);
        return root;
    }

    protected void objectTypeCreateControls(Composite parent,
            XpdFormToolkit toolkit) {

        Label lab =
                toolkit.createLabel(parent,
                        Messages.GatewayGeneralSection_GATEWAY_TYPE_LABEL);
        lab.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        Composite buttons = toolkit.createComposite(parent);
        buttons.setLayout(new GridLayout(1, false));

        types = new Button[5];

        types[EXCLUSIVE_DATA] =
                toolkit.createButton(buttons,
                        Messages.GatewayGeneralSection_EXCLUSIVE_DATA,
                        SWT.RADIO);
        types[EXCLUSIVE_DATA].setData("name", "buttonIsGatewayXORDataBased"); //$NON-NLS-1$ //$NON-NLS-2$
        types[EXCLUSIVE_DATA].setData(new Integer(EXCLUSIVE_DATA));
        types[EXCLUSIVE_DATA].setLayoutData(new GridData());

        types[INCLUSIVE] =
                toolkit.createButton(buttons,
                        Messages.GatewayGeneralSection_INCLUSIVE,
                        SWT.RADIO);
        types[INCLUSIVE].setData("name", "buttonIsGatewayInclusive"); //$NON-NLS-1$ //$NON-NLS-2$
        types[INCLUSIVE].setData(new Integer(INCLUSIVE));
        types[INCLUSIVE].setLayoutData(new GridData());

        types[EXCLUSIVE_EVENT] =
                toolkit.createButton(buttons,
                        Messages.GatewayGeneralSection_EXCLUSIVE_EVENT,
                        SWT.RADIO);
        types[EXCLUSIVE_EVENT].setData("name", "buttonIsGatewayXOREventBased"); //$NON-NLS-1$ //$NON-NLS-2$
        types[EXCLUSIVE_EVENT].setData(new Integer(EXCLUSIVE_EVENT));
        types[EXCLUSIVE_EVENT].setLayoutData(new GridData());

        types[COMPLEX] =
                toolkit.createButton(buttons,
                        Messages.GatewayGeneralSection_COMPLEX,
                        SWT.RADIO);
        types[COMPLEX].setData("name", "buttonIsGatewayComplex"); //$NON-NLS-1$ //$NON-NLS-2$
        types[COMPLEX].setData(new Integer(COMPLEX));
        types[COMPLEX].setLayoutData(new GridData());

        types[PARALLEL] =
                toolkit.createButton(buttons,
                        Messages.GatewayGeneralSection_PARALLEL,
                        SWT.RADIO);
        types[PARALLEL].setData("name", "buttonIsGatewayParallel"); //$NON-NLS-1$ //$NON-NLS-2$
        types[PARALLEL].setData(new Integer(PARALLEL));
        types[PARALLEL].setLayoutData(new GridData());

        // Manage all buttons
        for (Button btn : types) {
            manageControl(btn);
        }

        return;
    }

    @Override
    protected Composite doCreateDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        detailsScrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        detailsScrolledContainer.setExpandHorizontal(true);
        detailsScrolledContainer.setExpandVertical(true);
        gatewayTypeSpecificPageBook =
                new PageBook(detailsScrolledContainer, SWT.NONE);
        toolkit.adapt(gatewayTypeSpecificPageBook, false, false);
        detailsScrolledContainer.setContent(gatewayTypeSpecificPageBook);
        createGatewayTypePages(toolkit, gatewayTypeSpecificPageBook);
        return detailsScrolledContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetDetailsCommand(Object obj) {
        EditingDomain editingDomain = getEditingDomain();
        NamedElement namedElement = getNamedElement();

        if (editingDomain != null && namedElement != null) {

            // Get object type controls specific command.
            return objectTypeGetCommand(obj);
        }

        return null;
    }

    protected Command objectTypeGetCommand(Object obj) {

        if (obj instanceof Button
                && ((Button) obj).getData() instanceof Integer) {
            int btnType = ((Integer) ((Button) obj).getData()).intValue();

            Activity act = getActivity();
            if (act != null) {
                GatewayType type;
                switch (btnType) {
                case COMPLEX:
                    type = GatewayType.COMPLEX_LITERAL;
                    break;
                case INCLUSIVE:
                    type = GatewayType.INCLUSIVE_LITERAL;
                    break;
                case PARALLEL:
                    type = GatewayType.PARALLEL_LITERAL;
                    break;
                case EXCLUSIVE_EVENT:
                    type = GatewayType.EXLCUSIVE_EVENT_LITERAL;
                    break;
                case EXCLUSIVE_DATA:
                default:
                    type = GatewayType.EXCLUSIVE_DATA_LITERAL;
                    break;
                }

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.GatewayPropertySection_SetGatewayType_menu);
                cmd.append(ChangeGatewayTypeCommand.create(getEditingDomain(),
                        act,
                        type));

                return cmd;
            }
        }

        return null;
    }

    /**
     * Return the input as a NamedElement xpdl2 object.
     * 
     * @return NamedElement or null on error
     */
    public NamedElement getNamedElement() {
        NamedElement namedElement = null;

        if (getInput() instanceof NamedElement) {
            namedElement = (NamedElement) getInput();
        }
        return namedElement;
    }

    public void setInstrumentationPrefixName(String instrumentationPrefixName) {
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    protected String objectTypeGetDescriptor() {
        return Messages.GatewayPropertySection_GatewayActivityTypeName_label;
    }

    /**
     * Return the input as an xpdl2 activity.
     * 
     * @return The property sheet input as an activity.
     */
    public Activity getActivity() {
        EObject act = getInput();
        if (act instanceof Activity) {
            return (Activity) act;
        }
        return null;
    }

    @Override
    protected void doRefreshDetailsSection() {
        Activity act = getActivity();
        if (act != null) {
            Route route = act.getRoute();
            if (route != null) {

                JoinSplitType type = Xpdl2ModelUtil.safeGetGatewayType(act);
                ExclusiveType exclusiveType = route.getExclusiveType();

                for (Button btn : types) {
                    btn.setSelection(false);
                }

                /*
                 * Now we take excluded object type selections into account. So
                 * only show buttons for types that are not selected and the
                 * currently selected type.
                 */
                Button selectButton = null;

                switch (type.getValue()) {
                case JoinSplitType.PARALLEL:
                    selectButton = types[PARALLEL];
                    break;
                case JoinSplitType.INCLUSIVE:
                    selectButton = types[INCLUSIVE];
                    break;
                case JoinSplitType.COMPLEX:
                    selectButton = types[COMPLEX];
                    break;
                case JoinSplitType.EXCLUSIVE:
                default:
                    if (exclusiveType != null) {
                        if (ExclusiveType.EVENT == exclusiveType) {
                            selectButton = types[EXCLUSIVE_EVENT];
                        } else {
                            selectButton = types[EXCLUSIVE_DATA];
                        }
                    }
                    break;
                }

                selectButton.setSelection(true);

                /* Check for excluded types. */
                Set<ProcessEditorObjectType> excludedObjectTypes =
                        ProcessEditorConfigurationUtil
                                .getExcludedObjectTypes(act.getProcess());

                boolean hasAvailableChanged = false;

                if (updateTypeButton(types[PARALLEL],
                        excludedObjectTypes,
                        ProcessEditorObjectType.gateway_parallel,
                        selectButton)) {
                    hasAvailableChanged = true;
                }

                if (updateTypeButton(types[INCLUSIVE],
                        excludedObjectTypes,
                        ProcessEditorObjectType.gateway_inclusive,
                        selectButton)) {
                    hasAvailableChanged = true;
                }

                if (updateTypeButton(types[COMPLEX],
                        excludedObjectTypes,
                        ProcessEditorObjectType.gateway_complex,
                        selectButton)) {
                    hasAvailableChanged = true;
                }

                if (updateTypeButton(types[EXCLUSIVE_EVENT],
                        excludedObjectTypes,
                        ProcessEditorObjectType.gateway_exclusive_event_based,
                        selectButton)) {
                    hasAvailableChanged = true;
                }

                if (updateTypeButton(types[EXCLUSIVE_DATA],
                        excludedObjectTypes,
                        ProcessEditorObjectType.gateway_exclusive_data_based,
                        selectButton)) {
                    hasAvailableChanged = true;
                }

                if (hasAvailableChanged) {
                    types[INCLUSIVE].getParent().layout(true);
                }
            }
        }

        return;
    }

    /**
     * updates the visibility state of the given gateway type button according.
     * <p>
     * If the button is the correct one for actual current gateway type OR it
     * has not been excluded by a preference exztension then make it visible.
     * <p>
     * If the button isn't the one for current gateway type and it has been
     * excluded then hide it.
     * 
     * @param typeButton
     * @param excludedObjectTypes
     * @param gatewayObjectType
     * @param currentGatewayTypeButton
     * 
     * @return <code>true</code> if the button's visibility state has changed.
     */
    private boolean updateTypeButton(Button typeButton,
            Set<ProcessEditorObjectType> excludedObjectTypes,
            ProcessEditorObjectType gatewayObjectType,
            Button currentGatewayTypeButton) {
        boolean hasAvailableChanged = false;

        if (excludedObjectTypes.contains(gatewayObjectType)
                && currentGatewayTypeButton != typeButton) {
            if (typeButton.getVisible()) {
                typeButton.setVisible(false);

                GridData gd = new GridData();
                gd.heightHint = 0;
                typeButton.setLayoutData(gd);

                hasAvailableChanged = true;
            }

        } else {
            if (!typeButton.getVisible()) {

                typeButton.setVisible(true);

                GridData gd = new GridData();
                typeButton.setLayoutData(gd);

                hasAvailableChanged = true;
            }
        }

        return hasAvailableChanged;
    }

    /**
     * @param taskType
     */
    @Override
    protected void doRefreshImplementationSection() {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        int gatewayType = getGatewayType(activity);
        GatewayTypeSection newGatewaySection = null;
        for (GatewayTypeSection gts : gatewaySections) {
            if (gts.gatewayType == gatewayType) {
                newGatewaySection = gts;
                break;
            }
        }
        boolean showDetails = false;
        if (newGatewaySection.section instanceof IPluginContribution) {
            showDetails =
                    showDetails((IPluginContribution) newGatewaySection.section);
        }
        if (showDetails) {
            setSashPercentToLastUserSelected();
            if (gatewayTypeSpecificPageBook != null) {
                // If it has changed then reset the inputs.
                if (newGatewaySection != currentGatewaySection) {
                    setGatewayTypePageInput(gatewayType);

                    if (newGatewaySection != null) {
                        gatewayTypeSpecificPageBook
                                .showPage(newGatewaySection.page);
                    }

                    currentGatewaySection = newGatewaySection;
                }

                // Refresh whatever task type section is now current.
                if (currentGatewaySection != null) {
                    currentGatewaySection.section.refresh();

                    if (detailsScrolledContainer != null
                            && !detailsScrolledContainer.isDisposed()) {
                        /*
                         * Recalculate the size of the details' section scrolled
                         * composite
                         */
                        detailsScrolledContainer
                                .setMinSize(gatewayTypeSpecificPageBook
                                        .computeSize(SWT.DEFAULT, SWT.DEFAULT));
                    }
                }
            }
        } else {
            setSashPercent(1.0f);
        }

    }

    private boolean showDetails(IPluginContribution pluginContribution) {
        if (WorkbenchActivityHelper.filterItem(pluginContribution)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity act = (Activity) getBaseSelectObject(toTest);
            if (act.getRoute() != null) {
                return true;
            }
        }
        return false;
    }

    private int getGatewayType(Activity activity) {
        Route route = activity.getRoute();
        JoinSplitType gatewayType = Xpdl2ModelUtil.safeGetGatewayType(activity);
        int returnValue = -1;
        if (JoinSplitType.COMPLEX_LITERAL.equals(gatewayType)) {
            returnValue = GatewayPropertySection.COMPLEX;
        } else if (JoinSplitType.INCLUSIVE_LITERAL.equals(gatewayType)) {
            returnValue = GatewayPropertySection.INCLUSIVE;
        } else if (JoinSplitType.PARALLEL_LITERAL.equals(gatewayType)) {
            returnValue = GatewayPropertySection.PARALLEL;
        } else if (JoinSplitType.EXCLUSIVE_LITERAL.equals(gatewayType)) {
            ExclusiveType exclusiveType =
                    Xpdl2ModelUtil.safeGetExclusiveType(activity);
            if (exclusiveType != null) {
                if (ExclusiveType.DATA == exclusiveType) {
                    returnValue = GatewayPropertySection.EXCLUSIVE_DATA;
                } else if (ExclusiveType.EVENT == exclusiveType) {
                    returnValue = GatewayPropertySection.EXCLUSIVE_EVENT;
                }
            }
        }
        return returnValue;
    }

    /**
     * Set input for the selected route activity type implementation section
     * 
     * @param taskType
     */
    private void setGatewayTypePageInput(int gatewayType) {
        // Set the input for gateway type specific page
        // and unset all others (by giving them empty selection.
        for (GatewayTypeSection gts : gatewaySections) {
            if (gts.gatewayType == gatewayType) {
                gts.section.setInput(getPart(), getSelection());
            } else {
                gts.section.setInput(getPart(), StructuredSelection.EMPTY);
            }
        }
    }

    @Override
    public void dispose() {
        for (GatewayTypeSection gts : gatewaySections) {
            gts.section.dispose();
        }
        super.dispose();
    }

    /**
     * Create the pages for the implementation part of this properties page
     * 
     * @param toolkit
     * @param pageBook
     */
    private void createGatewayTypePages(XpdFormToolkit toolkit,
            PageBook pageBook) {
        for (GatewayTypeSection gts : gatewaySections) {
            gts.page = toolkit.createComposite(pageBook);
            gts.page.setLayoutData(new GridData(GridData.FILL_BOTH));

            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = new GridLayout().marginHeight;
            // fillLayout.marginWidth = 0;
            gts.page.setLayout(fillLayout);

            gts.section.createControls(gts.page, getPropertySheetPage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeHidden()
     */
    @Override
    public void aboutToBeHidden() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(activityListener);
        super.aboutToBeHidden();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeShown()
     */
    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);
    }

}
