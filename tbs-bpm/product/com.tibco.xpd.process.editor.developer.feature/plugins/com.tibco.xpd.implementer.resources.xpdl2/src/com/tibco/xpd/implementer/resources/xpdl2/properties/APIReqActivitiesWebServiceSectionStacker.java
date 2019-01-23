/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.wsdl.Operation;
import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
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
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceParameterTable.ParamsData;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The web service section stacker responsible for showing up on the Start
 * event, receive task and intermediate catch event. It creates two expandable
 * sections for web service implementation details and REST service details
 * 
 * @author agondal
 * @since 28 Nov 2012
 */
public class APIReqActivitiesWebServiceSectionStacker extends
        AbstractTransactionalSection implements ISectionContentCreator {

    private AbstractXpdSection apiReqWebServiceSection;

    private AbstractXpdSection restServiceSection;

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandableContainer;

    private ScrolledComposite scrolledContainer;

    private Composite root;

    public final static String WS_IMPLEMENTATION_DETAILS_SECTION =
            "wsImplementationDetailsSection"; //$NON-NLS-1$

    public final static String REST_SERVICE_SECTION = "RESTserviceSection"; //$NON-NLS-1$

    /**
     * 
     */
    public APIReqActivitiesWebServiceSectionStacker() {
        apiReqWebServiceSection = new APIReqActivitiesWebServiceSection();

        restServiceSection = createRestServiceSection();
    }

    /**
     * Creates and returns the REST Service Section.
     * 
     * @return the created REST Service Section.
     */
    private AbstractXpdSection createRestServiceSection() {

        return new RestServiceSection() {
            /**
             * Get rest service parameters from wsdl parts of the given activity
             * 
             * @param activity
             * @param modeType
             * 
             * @return List of <code>ParamsData</code>
             */
            @Override
            protected Collection<ParamsData> getRestServiceParams(
                    Activity activity, ModeType modeType) {

                Collection<ParamsData> paramsList =
                        new ArrayList<RestServiceParameterTable.ParamsData>();

                boolean isGeneratedRequestActivity =
                        Xpdl2ModelUtil.isGeneratedRequestActivity(activity);

                /*
                 * In case of generated request activities, input to the
                 * parameter table needs to be the process parameters directly.
                 * For user defined WSDLs, we need to get the input parts from
                 * the web service operation.
                 */

                if (isGeneratedRequestActivity) {

                    /*
                     * The input should be process parameters in case of
                     * generated request activities.
                     */

                    if (ModeType.OUT_LITERAL.getLiteral()
                            .equals(modeType.getLiteral())) {

                        paramsList
                                .addAll(getOutParametersForGeneratedRequestActivity(activity));

                    } else {

                        /*
                         * Mode type IN.
                         */

                        paramsList
                                .addAll(getInputParametersForGeneratedRequestActivity(activity));
                    }

                    return paramsList;

                } else {

                    ActivityMessageProvider messageProvider =
                            JavaScriptConceptUtil.INSTANCE
                                    .getMessageProvider(activity);

                    if (messageProvider != null) {

                        WebServiceOperation wso =
                                messageProvider
                                        .getWebServiceOperation(activity);

                        Operation op =
                                JavaScriptConceptUtil.INSTANCE
                                        .getWSDLOperation(wso);

                        if (wso != null && op != null) {

                            if (modeType.equals(ModeType.IN_LITERAL)) {

                                paramsList
                                        .addAll(getInputParametersFromOperation(op));

                            } else if (modeType.equals(ModeType.OUT_LITERAL)) {

                                paramsList
                                        .addAll(getOutputParametersFromOperation(op));
                            }
                        }
                    }

                    return paramsList;
                }
            }

            /**
             * Get output parameters from the specified operation.
             * 
             * @param op
             *            Operation from which output params are to be fetched.
             * 
             * @return Output parameters from the specified operation.
             */
            private Collection<ParamsData> getOutputParametersFromOperation(
                    Operation op) {

                Collection<ParamsData> paramsList =
                        new ArrayList<RestServiceParameterTable.ParamsData>();

                ParamsData paramData;

                Collection<Part> outputParts =
                        JavaScriptConceptUtil.getOutputParts(op);

                for (Part part : outputParts) {

                    paramData = createParamFromPart(part);

                    paramData.paramName = "Out" + part.getName(); //$NON-NLS-1$

                    paramsList.add(paramData);

                }

                return paramsList;
            }

            /**
             * Get input parameters from the specified operation.
             * 
             * @param op
             *            Operation from which input params are to be fetched.
             * 
             * @return Input parameters from the specified operation.
             */
            private Collection<ParamsData> getInputParametersFromOperation(
                    Operation op) {

                Collection<ParamsData> paramsList =
                        new ArrayList<RestServiceParameterTable.ParamsData>();

                ParamsData paramData;

                Collection<Part> inputParts =
                        JavaScriptConceptUtil.getInputParts(op);

                for (Part part : inputParts) {

                    paramData = createParamFromPart(part);

                    if (paramData != null) {

                        paramData.paramName = "In" + part.getName(); //$NON-NLS-1$

                        paramsList.add(paramData);
                    }
                }

                return paramsList;
            }

            /**
             * Get IN/ IN-OUT mode parameters needed by the generated request
             * activity.
             * 
             * @param activity
             * 
             * @return IN IN/OUT mode parameters needed by the generated request
             *         activity.
             */
            private Collection<ParamsData> getInputParametersForGeneratedRequestActivity(
                    Activity activity) {

                java.util.Set<ParamsData> allInParams =
                        new HashSet<ParamsData>();
                /*
                 * XPD-7824: Add the IN mode params
                 */
                allInParams
                        .addAll(getActivityInterfaceDataOfMode(ModeType.IN_LITERAL,
                                activity));
                /*
                 * XPD-7824: Add the IN/OUT mode params
                 */
                allInParams
                        .addAll(getActivityInterfaceDataOfMode(ModeType.INOUT_LITERAL,
                                activity));

                return allInParams;
            }

            /**
             * Get OUT mode parameters needed by a Generated request activity.
             * 
             * @param activity
             * 
             * @return OUT mode params needed by a Generated request activity.
             */
            private Collection<ParamsData> getOutParametersForGeneratedRequestActivity(
                    Activity activity) {

                Collection<ParamsData> paramsList =
                        new ArrayList<RestServiceParameterTable.ParamsData>();

                if (ReplyActivityUtil
                        .isStartMessageWithReplyImmediate(activity)) {

                    ParamsData paramData = new ParamsData();

                    /*
                     * Parameter name would be [ProcessId].
                     */
                    paramData.paramName =
                            com.tibco.xpd.processeditor.xpdl2.internal.Messages.StandardMappingUtil_MappingContentProcessId_label;

                    /*
                     * Parameter type would be String.
                     */
                    paramData.paramType =
                            ProcessDataUtil
                                    .getBasicTypeLabel(BasicTypeType.STRING_LITERAL);

                    paramsList.add(paramData);

                } else {

                    paramsList
                            .addAll(getActivityInterfaceDataOfMode(ModeType.OUT_LITERAL,
                                    activity));

                }

                return paramsList;
            }

            /**
             * Get activity interface data of the specified mode from the
             * specified activity.
             * 
             * @param mode
             * @param activity
             * 
             * @return Activity interface data of the specified mode from the
             *         specified activity.
             */
            private Collection<ParamsData> getActivityInterfaceDataOfMode(
                    ModeType mode, Activity activity) {

                Collection<ParamsData> paramsList =
                        new ArrayList<RestServiceParameterTable.ParamsData>();

                ParamsData paramData;

                /*
                 * Get parameters explicitly/implicity associated with the
                 * activity.
                 */
                Collection<ActivityInterfaceData> activityInterfaceData =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(activity);

                /*
                 * Go through all these parameters.
                 */
                for (ActivityInterfaceData interfaceData : activityInterfaceData) {

                    if (interfaceData.getMode().getLiteral()
                            .equals(mode.getLiteral())) {

                        /*
                         * Build the new param data.
                         */

                        paramData = new ParamsData();

                        paramData.paramName = interfaceData.getName();

                        Object baseType =
                                BasicTypeConverterFactory.INSTANCE
                                        .getBaseType(interfaceData.getData(),
                                                false);

                        String paramType = ""; //$NON-NLS-1$

                        if (baseType instanceof BasicType) {

                            BasicType dataType = (BasicType) baseType;

                            paramType =
                                    ProcessDataUtil.getBasicTypeLabel(dataType
                                            .getType());

                        } else if (baseType instanceof Classifier) {

                            /*
                             * For external reference to BOM elements mention
                             * the full path of the BOM element in the
                             * paramType.
                             */

                            Classifier classifier = (Classifier) baseType;

                            IFile file = WorkingCopyUtil.getFile(classifier);

                            if (file != null && file.isAccessible()) {

                                paramType =
                                        classifier.getQualifiedName() + " (" //$NON-NLS-1$
                                                + file.getFullPath() + ")"; //$NON-NLS-1$
                            }
                        }

                        paramData.paramType = paramType;

                        paramsList.add(paramData);
                    }
                }

                return paramsList;

            }

            /**
             * Creates a <code>ParamsData</code> object from wsdl part
             * 
             * @param part
             * @return ParamsData
             */
            private ParamsData createParamFromPart(Part part) {

                QName qName = null;

                if (part.getTypeName() != null) {

                    qName = part.getTypeName();

                } else if (part.getElement() != null) {

                    qName = part.getElementName();

                }

                if (qName != null) {

                    String type =
                            qName.getLocalPart()
                                    + " (" + qName.getNamespaceURI() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
                    ParamsData param = new ParamsData();
                    param.paramName = part.getName();
                    param.paramType = type;

                    return param;
                }

                return null;
            }

            @Override
            protected boolean shouldShowRestOutputParamTable(Activity activity) {

                return ReplyActivityUtil.isRequestActivityWithReply(activity);
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

        apiReqWebServiceSection.setInput(part, selection);
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
        if (WS_IMPLEMENTATION_DETAILS_SECTION.equals(sectionId)) {
            return createWsImplementationControls(toolkit, container);
        } else if (REST_SERVICE_SECTION.equals(sectionId)) {
            return createRESTServiceControls(toolkit, container);
        }
        return null;
    }

    /**
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
     * @param toolkit
     * @param container
     * @return
     */
    private Control createWsImplementationControls(XpdFormToolkit toolkit,
            Composite container) {

        Composite cmp = toolkit.createComposite(container);
        FillLayout fl = new FillLayout();
        fl.marginHeight = 0;
        fl.marginWidth = 0;
        cmp.setLayout(fl);

        apiReqWebServiceSection.createControls(cmp, getPropertySheetPage());

        return cmp;
    }

    /**
     * @param expandableHeaderController
     */
    protected void addExpandableSections(
            ExpandableSectionStacker headerController) {

        headerController.addSection(WS_IMPLEMENTATION_DETAILS_SECTION,
                Messages.WS_implementation_details_section_title,
                SWT.DEFAULT,
                true,
                true);

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

        // REST service section is only visible for BPM destination
        Activity activity = (Activity) getInput();

        if (activity != null) {

            boolean bpmDestinationEnabled =
                    ProcessDestinationUtil.isBPMDestinationSelected(activity
                            .getProcess());

            if (expandableHeaderController != null) {
                if (bpmDestinationEnabled) {
                    expandableHeaderController
                            .setSectionVisible(REST_SERVICE_SECTION, true);
                } else {
                    expandableHeaderController
                            .setSectionVisible(REST_SERVICE_SECTION, false);
                }
            }
        }

        // delegate refresh to two sections
        apiReqWebServiceSection.refresh();
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
                getSectionContainerType() + "." + "ExpandableWebServiceSection"; //$NON-NLS-1$ //$NON-NLS-2$

        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

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
