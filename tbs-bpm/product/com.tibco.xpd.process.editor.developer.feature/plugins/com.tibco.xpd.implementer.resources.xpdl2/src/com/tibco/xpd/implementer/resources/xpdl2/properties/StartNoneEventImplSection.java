/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceParameterTable.ParamsData;
import com.tibco.xpd.processeditor.xpdl2.properties.event.EventImplementationSection;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation Section for Start Type None events.
 * 
 * @author kthombar
 * @since Apr 9, 2015
 */
public class StartNoneEventImplSection extends
        AbstractFilteredTransactionalSection implements
        EventImplementationSection {

    /**
     * the REST Service Section.
     */
    private AbstractXpdSection restServiceSection;

    /**
     * Constructor to set adaptor to model.
     */
    public StartNoneEventImplSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        restServiceSection = new StartTypeNoneEventImplSectionStacker();

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {

        super.setInput(part, selection);
        restServiceSection.setInput(part, selection);
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     * 
     * @return
     */
    @Override
    public String getLocalId() {
        return "developer.startNoneEventImpl"; //$NON-NLS-1$;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    @Override
    public String getPluginId() {
        return com.tibco.xpd.implementer.resources.xpdl2.Activator.PLUGIN_ID;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        restServiceSection.refresh();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        // create scrolled composite so the section details never get lost on
        // the page
        ScrolledComposite scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite webServiceDetailsContainer =
                toolkit.createComposite(scrolledContainer);

        webServiceDetailsContainer.setLayout(new FillLayout());
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        webServiceDetailsContainer.setLayoutData(gd);

        restServiceSection.createControls(webServiceDetailsContainer,
                getPropertySheetPage());
        webServiceDetailsContainer.setVisible(true);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(webServiceDetailsContainer);

        scrolledContainer.setMinSize(webServiceDetailsContainer
                .computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return scrolledContainer;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        /*
         * this class does not need to deal with any commands.
         */
        return null;
    }

    public static class Filter implements IFilter {

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @SuppressWarnings("deprecation")
        @Override
        public boolean select(Object toTest) {

            EObject eo = null;

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;

                Process process = activity.getProcess();
                /*
                 * Applicable only for biz processes with BPM destination
                 * enabled.
                 */
                return process != null
                        && Xpdl2ModelUtil.isBusinessProcess(process)
                        && ProcessDestinationUtil
                                .isBPMDestinationSelected(process);

            }
            return false;
        }
    }

    /**
     * REST Service Implementation Stacker Section for Start Type none events.
     * 
     * 
     * @author kthombar
     * @since Apr 10, 2015
     */
    private class StartTypeNoneEventImplSectionStacker extends
            AbstractTransactionalSection implements ISectionContentCreator {

        private AbstractXpdSection restServiceSection;

        private ExpandableSectionStacker expandableHeaderController;

        private Control expandableContainer;

        private ScrolledComposite scrolledContainer;

        private Composite root;

        private final static String REST_SERVICE_SECTION = "RESTserviceSection"; //$NON-NLS-1$

        /**
         * 
         */
        public StartTypeNoneEventImplSectionStacker() {
            restServiceSection = createRestServiceSection();
        }

        /**
         * @see com.tibco.xpd.implementer.resources.xpdl2.properties.APIReqActivitiesWebServiceSectionStacker#createRestServiceSection()
         * 
         * @return
         */

        private AbstractXpdSection createRestServiceSection() {

            return new RestServiceSection() {

                @Override
                protected boolean shouldShowRestOutputParamTable(
                        Activity activity) {
                    /*
                     * There will always be a [ProcessId] output parameter.
                     */
                    return true;
                }

                @Override
                protected Collection<ParamsData> getRestServiceParams(
                        Activity activity, ModeType modeType) {

                    Collection<ParamsData> paramsList =
                            new ArrayList<RestServiceParameterTable.ParamsData>();

                    if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                            .getFlowType(activity))
                            && EventTriggerType.EVENT_NONE_LITERAL
                                    .equals(EventObjectUtil
                                            .getEventTriggerType(activity))) {

                        if (ModeType.OUT_LITERAL.getLiteral()
                                .equals(modeType.getLiteral())) {
                            ParamsData paramData = new ParamsData();
                            paramData.paramName =
                                    com.tibco.xpd.processeditor.xpdl2.internal.Messages.StandardMappingUtil_MappingContentProcessId_label;
                            paramData.paramType =
                                    ProcessDataUtil
                                            .getBasicTypeLabel(BasicTypeType.STRING_LITERAL);

                            paramsList.add(paramData);
                        } else if (ModeType.IN_LITERAL.getLiteral()
                                .equals(modeType.getLiteral())) {
                            /*
                             * XPD-7824: Event though there will be only input
                             * param and output param table, just to be sure
                             * adding an else if statement.
                             */
                            /*
                             * get the activity interface data.
                             */
                            Collection<ActivityInterfaceData> activityInterfaceData =
                                    ActivityInterfaceDataUtil
                                            .getActivityInterfaceData(activity);

                            ParamsData paramData;

                            for (ActivityInterfaceData interfaceData : activityInterfaceData) {
                                /*
                                 * XPD-7824: Both IN and IN-MODE params should
                                 * be shown in the input parameters table.
                                 */
                                if (interfaceData.getMode() != null
                                        && interfaceData
                                                .getMode()
                                                .getLiteral()
                                                .contains(modeType.getLiteral())) {
                                    paramData = new ParamsData();
                                    paramData.paramName =
                                            interfaceData.getName();

                                    Object baseType =
                                            BasicTypeConverterFactory.INSTANCE
                                                    .getBaseType(interfaceData
                                                            .getData(), false);

                                    String paramType = ""; //$NON-NLS-1$

                                    if (baseType instanceof BasicType) {
                                        BasicType dataType =
                                                (BasicType) baseType;

                                        paramType =
                                                ProcessDataUtil
                                                        .getBasicTypeLabel(dataType
                                                                .getType());

                                    } else if (baseType instanceof Classifier) {
                                        /*
                                         * For external reference to BOM
                                         * elements metion the full path of the
                                         * BOM element in the paramType
                                         */
                                        Classifier classifier =
                                                (Classifier) baseType;

                                        IFile file =
                                                WorkingCopyUtil
                                                        .getFile(classifier);

                                        if (file != null && file.isAccessible()) {

                                            paramType =
                                                    classifier
                                                            .getQualifiedName()
                                                            + " (" //$NON-NLS-1$
                                                            + file.getFullPath()
                                                            + ")"; //$NON-NLS-1$
                                        }
                                    }

                                    paramData.paramType = paramType;

                                    paramsList.add(paramData);
                                }
                            }
                        }
                    }
                    return paramsList;
                }
            };
        }

        /**
         * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
         *      org.eclipse.jface.viewers.ISelection)
         * 
         * @param part
         * @param selection
         */
        @Override
        public void setInput(IWorkbenchPart part, ISelection selection) {
            super.setInput(part, selection);
            restServiceSection.setInput(part, selection);
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
            if (REST_SERVICE_SECTION.equals(sectionId)) {
                return createRESTServiceControls(toolkit, container);
            }
            return null;
        }

        /**
         * creates the rest service controls
         * 
         * @param toolkit
         * @param container
         * @return
         */
        private Control createRESTServiceControls(XpdFormToolkit toolkit,
                Composite container) {

            Composite cmp = toolkit.createComposite(container);
            FillLayout fl = new FillLayout();
            fl.marginHeight = 0;
            fl.marginWidth = 0;
            cmp.setLayout(fl);

            restServiceSection.createControls(cmp, getPropertySheetPage());

            return cmp;
        }

        /**
         * adds the expandable sections
         * 
         * @param expandableHeaderController
         */
        protected void addExpandableSections(
                ExpandableSectionStacker headerController) {

            headerController.addSection(REST_SERVICE_SECTION,
                    Messages.REST_service_section_title,
                    SWT.DEFAULT,
                    false,
                    false);
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
         * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
         * 
         */
        @Override
        protected void doRefresh() {
            restServiceSection.refresh();

        }

        /**
         * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
         *      com.tibco.xpd.ui.properties.XpdFormToolkit)
         * 
         * @param parent
         * @param toolkit
         * @return
         */
        @Override
        protected Control doCreateControls(Composite parent,
                XpdFormToolkit toolkit) {
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

            // Expandable section header preferences.
            String sectPrefId =
                    getSectionContainerType()
                            + "." + "ExpandableRestServiceSection"; //$NON-NLS-1$ //$NON-NLS-2$

            expandableHeaderController =
                    new ExpandableSectionStacker(sectPrefId);

            addExpandableSections(expandableHeaderController);

            expandableContainer =
                    expandableHeaderController.createExpandableSections(root,
                            toolkit,
                            this);

            GridData gd =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                            | GridData.GRAB_HORIZONTAL);
            expandableContainer.setLayoutData(gd);

            // set contents of sections on to the scrolled composite
            scrolledContainer.setContent(root);

            Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            scrolledContainer.setMinSize(prefSize);

            return scrolledContainer;
        }

        /**
         * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
         * 
         * @param obj
         * @return
         */
        @Override
        protected Command doGetCommand(Object obj) {
            /*
             * All controls that affect the model are in delegated sections so
             * nothing to do here.
             */

            return null;
        }
    }
}
