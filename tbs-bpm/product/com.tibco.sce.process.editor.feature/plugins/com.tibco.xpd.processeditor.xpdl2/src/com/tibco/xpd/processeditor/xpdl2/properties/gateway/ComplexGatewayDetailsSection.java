/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.gateway;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * <p>
 * <i>Created: 30 Mar 2008</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class ComplexGatewayDetailsSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution,
        ISectionContentCreator {

    private CCombo gatewayTypeCombo;

    private String instrumentationPrefixName;

    private Map<String, GatewayImplSection> implSectionsMapById = null;

    public static final int STANDARD_LABEL_WIDTH = 95;

    private static final String UNSPECIFIED_ID = "Unspecified"; //$NON-NLS-1$

    private static final String STRUCTURED_ID = "Structured"; //$NON-NLS-1$

    private PageBook implTypeBook;

    private static final String COMPLEX_GATEWAY_SECTION_ID =
            "complexGatewaySection"; //$NON-NLS-1$

    public ComplexGatewayDetailsSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        init();
    }

    public ComplexGatewayDetailsSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
        init();
    }

    private void init() {
        implSectionsMapById = new HashMap<String, GatewayImplSection>();
        implSectionsMapById
                .put(ComplexGatewayDetailsSection.UNSPECIFIED_ID,
                        new GatewayImplSection(
                                new NonComplexGatewayTypeSection(),
                                Messages.ComplexGatewayDetailsSection_UNSPECIFIED_DISCRIMINATOR_DROP_DOWN_ENRY));
        implSectionsMapById
                .put(ComplexGatewayDetailsSection.STRUCTURED_ID,
                        new GatewayImplSection(
                                new DiscriminatorPatternSection(),
                                Messages.ComplexGatewayDetailsSection_STRUCTURED_DISCRIMINATOR_DROP_DOWN_ENRY));

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite expandables = toolkit.createComposite(parent);
        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {

            expandables.setLayout(new FillLayout());

            ExpandableSectionStacker stacker =
                    new ExpandableSectionStacker(getClass().getName());
            stacker.addSection(COMPLEX_GATEWAY_SECTION_ID,
                    Messages.ComplexGatewayDetailsSection_JOIN_CONFIGURATION,
                    150,
                    true,
                    true);
            stacker.createExpandableSections(expandables, toolkit, this);

        }
        return expandables;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        CompoundCommand cmd = null;
        Activity act = getActivity();
        EditingDomain ed = getEditingDomain();
        Route route = act.getRoute();
        if (act != null && route != null && ed != null) {
            // Handle the impl type combo
            if (obj == gatewayTypeCombo) {
                String selectedComplexGatewayName =
                        (String) gatewayTypeCombo.getData(gatewayTypeCombo
                                .getText());
                String complexGatewayNameInModel =
                        getCurrentSelectedComplexGatewayName();
                if (!selectedComplexGatewayName
                        .equals(complexGatewayNameInModel)) {
                    cmd = new CompoundCommand();
                    cmd
                            .setLabel(Messages.ComplexGatewayDetailsSection_ChangeDiscriminatorTypeCommand);
                    Route newRoute = Xpdl2Factory.eINSTANCE.createRoute();
                    newRoute.setGatewayType(JoinSplitType.COMPLEX_LITERAL);
                    if (STRUCTURED_ID
                            .equals(selectedComplexGatewayName)) {
                        Discriminator discriminator =
                                XpdExtensionFactory.eINSTANCE
                                        .createDiscriminator();
                        StructuredDiscriminator newStructuredDiscriminator =
                                XpdExtensionFactory.eINSTANCE
                                        .createStructuredDiscriminator();
                        discriminator
                                .setDiscriminatorType(selectedComplexGatewayName);
                        discriminator
                                .setStructuredDiscriminator(newStructuredDiscriminator);
                        Xpdl2ModelUtil.setOtherElement(newRoute,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Discriminator(),
                                discriminator);
                        gatewayTypeCombo.setToolTipText(Messages.Join_Type_Control_Structured_Tooltip);
                    } else if (UNSPECIFIED_ID.equals(selectedComplexGatewayName)) {
                        gatewayTypeCombo.setToolTipText(Messages.Join_Type_Control_Unspecified_Tooltip);
                    }
                    Command setNewRoute =
                            SetCommand.create(ed, act, Xpdl2Package.eINSTANCE
                                    .getActivity_Route(), newRoute);
                    cmd.append(setNewRoute);
                }
            }
        }
        return cmd;
    }

    /**
     * Get the currently selected service name from the model
     * 
     * @return
     */
    private String getCurrentSelectedComplexGatewayName() {
        String serviceName = ComplexGatewayDetailsSection.UNSPECIFIED_ID;
        Activity act = getActivity();
        if (act != null) {
            Route route = act.getRoute();
            if (route == null) {
                return serviceName;
            }
            if (JoinSplitType.COMPLEX_LITERAL != route.getGatewayType()) {
                return serviceName;
            }
            Discriminator otherElement =
                    (Discriminator) Xpdl2ModelUtil.getOtherElement(route,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Discriminator());
            if (otherElement != null) {
                serviceName = otherElement.getDiscriminatorType();
            }
        }
        return serviceName;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        Activity act = getActivity();
        if (act != null) {
            // Update the relevant sections
            try {
                updateSectionInput(getCurrentSelectedComplexGatewayName());
            } catch (CoreException e) {
                Logger logger =
                        Xpdl2ProcessEditorPlugin.getDefault().getLogger();
                logger.error(e);
            }
        }
    }

    /**
     * Update the input of all service implementation sections
     * 
     * @param currentSelectedService
     * @throws CoreException
     */
    private void updateSectionInput(String currentGatewayName)
            throws CoreException {
        Object inputModel = getActivity();
        if (inputModel != null) {
            for (Entry<String, GatewayImplSection> eachEntry : implSectionsMapById
                    .entrySet()) {
                String eachSectionName = eachEntry.getKey();
                GatewayImplSection eachSection = eachEntry.getValue();
                if (inputModel != null && currentGatewayName != null
                        && currentGatewayName.equals(eachSectionName)) {
                    eachSection.section.setInput(getPart(),
                            new StructuredSelection(inputModel));

                } else {
                    eachSection.section.setInput(getPart(),
                            new StructuredSelection());
                }
            }
        }
    }

    @Override
    protected void doRefresh() {
        Activity act = getActivity();
        if (gatewayTypeCombo != null && act != null) {
            if (gatewayTypeCombo.isDisposed()) {
                return;
            }
            String currentValue = getCurrentSelectedComplexGatewayName();
            Set<Entry<String, GatewayImplSection>> entrySet =
                    implSectionsMapById.entrySet();
            if (entrySet == null) {
                return;
            }
            GatewayImplSection gatewaySection = null;

            int itemToSelect;
            for (itemToSelect = 0; itemToSelect < gatewayTypeCombo
                    .getItemCount(); itemToSelect++) {
                if (gatewayTypeCombo.getData(gatewayTypeCombo
                        .getItem(itemToSelect)).equals(currentValue)) {
                    break;
                }
            }

            for (Entry<String, GatewayImplSection> eachEntry : entrySet) {
                if (currentValue.equals(eachEntry.getKey())) {
                    gatewaySection = eachEntry.getValue();
                    break;
                }
            }
            if (gatewaySection == null) {
                return;
            }
            gatewayTypeCombo.select(itemToSelect);
            gatewayTypeCombo.pack();
            gatewaySection.section.setInput(getPart(), new StructuredSelection(
                    act));
            gatewaySection.section.aboutToBeShown();
            implTypeBook.showPage(gatewaySection.page);
            gatewaySection.section.refresh();
        }

    }

    public String getLocalId() {
        return "analyst.implSection"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
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

    private class GatewayImplSection {
        Composite page;

        ISection section;

        String localisedName;

        GatewayImplSection(ISection section, String localisedName) {
            this.section = section;
            this.localisedName = localisedName;
            page = null;
        }
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
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        Composite sectionComposite = toolkit.createComposite(container);
        sectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout gridLayout = new GridLayout(2, false);
        // gridLayout.marginHeight--;
        sectionComposite.setLayout(gridLayout);

        Label lab =
                createLabel(sectionComposite,
                        toolkit,
                        Messages.ComplexGatewayDetailsSection_JoinTypeLabel);
        lab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        lab.setToolTipText(Messages.Join_Type_Label_Tooltip);

        gatewayTypeCombo =
                toolkit.createCCombo(sectionComposite,
                        SWT.NONE,
                        instrumentationPrefixName + "JoinTypeCombo"); //$NON-NLS-1$
        gatewayTypeCombo.setLayoutData(new GridData());

        gatewayTypeCombo
                .setData(Messages.ComplexGatewayDetailsSection_UNSPECIFIED_DISCRIMINATOR_DROP_DOWN_ENRY,
                        UNSPECIFIED_ID);
        gatewayTypeCombo
                .setData(Messages.ComplexGatewayDetailsSection_CONTINUE_WHEN_PARALLEL_JOINTYPE_DROP_DOWN_ENRY,
                        STRUCTURED_ID);
        gatewayTypeCombo
                .add(Messages.ComplexGatewayDetailsSection_UNSPECIFIED_DISCRIMINATOR_DROP_DOWN_ENRY);
        gatewayTypeCombo
                .add(Messages.ComplexGatewayDetailsSection_CONTINUE_WHEN_PARALLEL_JOINTYPE_DROP_DOWN_ENRY);
        gatewayTypeCombo.setEditable(false);
        manageControl(gatewayTypeCombo);

        implTypeBook = new PageBook(sectionComposite, SWT.NONE);
        implTypeBook.setBackground(sectionComposite.getBackground());
        GridData gData = new GridData(GridData.FILL_BOTH);
        gData.horizontalSpan = 2;
        implTypeBook.setLayoutData(gData);

        Collection<GatewayImplSection> values = implSectionsMapById.values();
        for (GatewayImplSection gatewaySection : values) {
            gatewaySection.page =
                    toolkit.createComposite(implTypeBook, SWT.NONE);
            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            gatewaySection.page.setLayout(fillLayout);
            gatewaySection.section.createControls(gatewaySection.page,
                    getPropertySheetPage());
        }

        return sectionComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    public void expandableSectionStateChanged(String sectionId) {
        // TODO Auto-generated method stub

    }

}
