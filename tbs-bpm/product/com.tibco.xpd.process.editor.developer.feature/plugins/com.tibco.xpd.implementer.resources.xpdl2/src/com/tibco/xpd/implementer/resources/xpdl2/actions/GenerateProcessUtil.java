/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractXpdlProjectSelectPage;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.CostUnit;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class generally used while generating pageflows/business services
 * 
 * 
 * @author bharge
 * @since 3.3 (18 Mar 2010)
 */
public final class GenerateProcessUtil {

    /**
     * Create a process instance with default name, default display name for the
     * given process mode type
     * 
     * @param xpdModelType
     * @return EObject - that represents a xpdl2 process instance
     */
    public static EObject createTemplate(XpdModelType xpdModelType) {

        Process input = Xpdl2Factory.eINSTANCE.createProcess();
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME);
        input.setName(NameUtil
                .getInternalName(Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME,
                        false));
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                xpdModelType);
        return input;
    }

    /**
     * Create a process instance with default name, default display name for the
     * given process mode type
     * 
     * @param xpdModelType
     * @return xpdl2 Process instance
     */
    public static Process createProcess(XpdModelType xpdModelType) {

        Process input = Xpdl2Factory.eINSTANCE.createProcess();
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME);
        input.setName(NameUtil
                .getInternalName(Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME,
                        false));
        Xpdl2ModelUtil.setOtherAttribute(input,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                xpdModelType);
        return input;
    }

    /**
     * create and return xpdl package under a given project
     * 
     * @param xpdlFileName
     * @param iProject
     * @return xpdlPackage
     */
    public static Package createPackage(String xpdlFileName, IProject iProject) {

        Package xpdl2Package = Xpdl2Factory.eINSTANCE.createPackage();
        String pckgName =
                xpdlFileName.substring(0, xpdlFileName
                        .indexOf(Xpdl2ResourcesConsts.XPDL_EXTENSION) - 1);
        xpdl2Package.setName(NameUtil.getInternalName(pckgName, false));
        Xpdl2ModelUtil.setOtherAttribute(xpdl2Package,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                pckgName);

        PackageHeader packageHeader =
                Xpdl2Factory.eINSTANCE.createPackageHeader();
        packageHeader.setVendor(AbstractXpdlProjectSelectPage.VENDOR_NAME);
        Date todayDate = new Date();
        String iso8601Date = LocaleUtils.getISO8601Date(todayDate);
        packageHeader.setCreated(iso8601Date);

        CostUnit costUnit = Xpdl2Factory.eINSTANCE.createCostUnit();
        costUnit.setValue(LocaleUtils.getCurrencyCode(Locale.getDefault()));
        packageHeader.setCostUnit(costUnit);

        String tempLocaleIsaCode =
                LocaleUtils.getLocaleISOFromDisplayName(Locale.getDefault()
                        .getDisplayName());
        Xpdl2ModelUtil.setOtherAttribute(packageHeader,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Language(),
                tempLocaleIsaCode);

        xpdl2Package.setPackageHeader(packageHeader);

        RedefinableHeader redefinableHeader =
                Xpdl2Factory.eINSTANCE.createRedefinableHeader();
        redefinableHeader.setAuthor(System
                .getProperty(AbstractXpdlProjectSelectPage.USER_NAME_PROPERTY));
        redefinableHeader.setVersion(ProjectUtil.getProjectVersion(iProject));
        redefinableHeader
                .setPublicationStatus(PublicationStatusType.UNDER_REVISION_LITERAL);
        xpdl2Package.setRedefinableHeader(redefinableHeader);

        ExtendedAttribute attrib =
                Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        attrib.setName(AbstractXpdlProjectSelectPage.CREATEDBYATTRIB);
        attrib.setValue(AbstractXpdlProjectSelectPage.BUSINESS_STUDIO_CONST);
        xpdl2Package.getExtendedAttributes().add(attrib);

        attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        attrib.setName(XpdlMigrate.FORMAT_VERSION_ATT_NAME);
        attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
        xpdl2Package.getExtendedAttributes().add(attrib);

        /* Store the OriginalFormatVersion that xpdl was created in. */
        attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        attrib.setName(XpdlMigrate.ORIGINAL_FORMAT_VERSION_ATT_NAME);
        attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
        xpdl2Package.getExtendedAttributes().add(attrib);

        return xpdl2Package;
    }

    /**
     * Add project destination to a given process
     * 
     * @param newPageflowProcess
     * @param ed
     * @param wc
     */
    public static void addProjectDestinationsToProcess(
            Process newPageflowProcess, EditingDomain ed, WorkingCopy wc) {

        EList<ExtendedAttribute> extendedAttributes = null;

        IProject project = getProject(wc);

        if (null != newPageflowProcess) {
            extendedAttributes = newPageflowProcess.getExtendedAttributes();
        }
        /*
         * before adding check if no destinations from project have been added
         * yet to the new process
         */
        if (null != extendedAttributes && extendedAttributes.size() == 0) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                if (null != config.getProjectDetails()) {
                    Destinations globalDestinations =
                            config.getProjectDetails().getGlobalDestinations();

                    if (null != globalDestinations) {
                        EList<Destination> destination =
                                globalDestinations.getDestination();

                        Set<String> destinationTypes = new HashSet<String>();

                        /*
                         * changed to create destination ext attrs and add
                         * direct to new process; must not execute command off
                         * of undo stack (as did earlier)
                         */

                        for (Destination destination2 : destination) {
                            destinationTypes.add(destination2.getType());
                        }

                        if (destinationTypes.size() > 0) {
                            DestinationUtil
                                    .addPassedDestinationToProcess(destinationTypes,
                                            newPageflowProcess);
                        }
                    }
                }
            }
        }
    }

    /**
     * get the project for given working copy
     * 
     * @return IProject
     */
    public static IProject getProject(WorkingCopy wc) {

        if (wc != null) {

            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {

                return resource.getProject();
            }
        }
        return null;
    }

    /**
     * Set the process model type for a given xpd process
     * 
     * @param process
     * @param xpdModelType
     * @return process
     */
    public static Process setProcessModelType(Process process,
            XpdModelType xpdModelType) {

        Xpdl2ModelUtil.setOtherAttribute(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType(),
                xpdModelType);
        return process;
    }

    /**
     * Create a command that adds pool and lane
     * 
     * @param input
     * @param workingCopy
     * @return {@link Command} that adds pool and lane
     */
    public static Command createCommand(EObject input, WorkingCopy workingCopy) {

        if (workingCopy != null) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.append(AddCommand.create(workingCopy.getEditingDomain(),
                    workingCopy.getRootElement(),
                    Xpdl2Package.eINSTANCE.getPackage_Processes(),
                    input));
            cmd.setLabel(Messages.NewProcessWizard_AddProcessCmd_label);
            Pool pool =
                    ElementsFactory
                            .createPool(Messages.ProcessPropertySection_DefaultPool_value,
                                    ((Process) input).getId());

            Lane lane = Xpdl2Factory.eINSTANCE.createLane();
            lane.setName(Messages.ProcessPropertySection_DefaultLane_label);
            Xpdl2ModelUtil
                    .setOtherAttribute(lane,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            Messages.ProcessPropertySection_DefaultLane_label);

            NodeGraphicsInfo gNode =
                    Xpdl2ModelUtil.getOrCreateNodeGraphicsInfo(lane);
            gNode.setHeight(300);

            pool.getLanes().add(lane);

            cmd.append(AddCommand.create(workingCopy.getEditingDomain(),
                    workingCopy.getRootElement(),
                    Xpdl2Package.eINSTANCE.getPackage_Pools(),
                    pool));
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Create a new data field to match the given parameter
     * 
     * @param startEventAssocParam
     *            The associated parameter OR null if nothing is implicitly
     *            associated.
     * @param processRelevantData
     *            The source process parameter
     * @return new data field matching the criteria in given association.
     */
    public static DataField createDataFieldForAssociatedData(
            ProcessRelevantData processRelevantData) {

        DataField pageflowDataField = Xpdl2Factory.eINSTANCE.createDataField();

        /* Set datafield name */
        String name = processRelevantData.getName();
        pageflowDataField.setName(name);

        /* Set datafield display name */
        String displayName = Xpdl2ModelUtil.getDisplayName(processRelevantData);
        if (null != displayName) {
            if (displayName.isEmpty()) {
                displayName = name;
            }
            Xpdl2ModelUtil
                    .setOtherAttribute(pageflowDataField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            displayName);
        }
        /* Set Data type */
        if (processRelevantData.getDataType() instanceof BasicType) {
            pageflowDataField
                    .setDataType(processRelevantData.getDataType() == null ? null
                            : (DataType) EcoreUtil.copy(processRelevantData
                                    .getDataType()));
        } else if (processRelevantData.getDataType() instanceof ExternalReference) {
            ExternalReference paramExternalRef =
                    (ExternalReference) processRelevantData.getDataType();

            ExternalReference newDFExtReference =
                    Xpdl2Factory.eINSTANCE.createExternalReference();

            newDFExtReference.setLocation(paramExternalRef.getLocation());
            newDFExtReference.setNamespace(paramExternalRef.getNamespace());
            newDFExtReference.setXref(paramExternalRef.getXref());

            pageflowDataField.setDataType(newDFExtReference);

        } else if (processRelevantData.getDataType() instanceof RecordType) {

            RecordType parameterRecordType =
                    (RecordType) (processRelevantData.getDataType());

            RecordType newDataFieldRecordType =
                    Xpdl2Factory.eINSTANCE.createRecordType();

            Collection<Member> memberCopy =
                    EcoreUtil.copyAll(parameterRecordType.getMember());

            newDataFieldRecordType.getMember().addAll(memberCopy);

            pageflowDataField.setDataType(newDataFieldRecordType);
        }

        /* Is array */
        pageflowDataField.setIsArray(processRelevantData.isIsArray());

        /* Length */
        pageflowDataField
                .setLength(processRelevantData.getLength() == null ? null
                        : (Length) EcoreUtil.copy(processRelevantData
                                .getLength()));

        /* Read only flag */
        pageflowDataField.setReadOnly(processRelevantData.isReadOnly());

        return pageflowDataField;
    }

    /**
     * Set publish business service and business service category on the given
     * process
     * 
     * @param ed
     * @param pageflowProcess
     * @return {@link Command} that sets business service category and publish
     *         business service
     */
    public static CompoundCommand setPublishAsBusinessService(EditingDomain ed,
            Process pageflowProcess) {
        CompoundCommand command = new CompoundCommand();
        /*
         * generated pageflow should default to "Publish as Business Service"
         */
        command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                pageflowProcess,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PublishAsBusinessService(),
                Boolean.TRUE));

        /**
         * XPD-1026: business service category should be defaulted to project
         * name / process package name
         */
        IProject project = WorkingCopyUtil.getProjectFor(pageflowProcess);
        String defaultCategory =
                Xpdl2ModelUtil.getBusinessServiceDefaultCategory(project
                        .getName(), pageflowProcess.getPackage().getName());
        command.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                pageflowProcess,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_BusinessServiceCategory(),
                defaultCategory));
        return command;
    }

    /**
     * Creates a list of formal parameters for the given activity. For derived
     * wsdl, it uses associated parameters of the activity and for user-defined
     * wsdl, creates params based on the wsdl parts
     * 
     * @param bpMsgAct
     * @return list of formal parameters
     * @throws CoreException
     */
    public static List<FormalParameter> getFormalParamForPageflow(
            Activity bpMsgAct) throws CoreException {

        List<FormalParameter> paramsList = new ArrayList<FormalParameter>();
        if (Xpdl2ModelUtil.isGeneratedRequestActivity(bpMsgAct)) {
            /*
             * XPD-1811: If activity is generated then return the actual formal
             * parameters associated with the activity rather than making up new
             * temporary ones from the WSDL input parts (then the extra bits
             * like length of the parameter will be available).
             */
            Collection<ActivityInterfaceData> activityInterfaceData =
                    ActivityInterfaceDataUtil
                            .getActivityInterfaceData(bpMsgAct);
            for (ActivityInterfaceData data : activityInterfaceData) {

                ProcessRelevantData prd = data.getData();
                if (prd instanceof FormalParameter) {

                    paramsList.add((FormalParameter) prd);
                }
            }
        } else {

            ActivityMessageProvider messageProvider =
                    JavaScriptConceptUtil.INSTANCE.getMessageProvider(bpMsgAct);
            if (messageProvider != null) {

                WebServiceOperation wso =
                        messageProvider.getWebServiceOperation(bpMsgAct);
                /*
                 * if the web-service operation refers to a derived WSDL and the
                 * activity is an auto-generated operation
                 */
                Operation op =
                        JavaScriptConceptUtil.INSTANCE.getWSDLOperation(wso);
                Collection<?> inputParts = Collections.EMPTY_LIST;
                if (op != null) {

                    inputParts = JavaScriptConceptUtil.getInputParts(op);
                    /*
                     * XPD-4989- Kapil : Check if the Javascript data mappings
                     * are possible, if not then do not generate the Business
                     * process.
                     */
                    checkJavaScriptMappings(inputParts);
                }

                /*
                 * XPD-4187: Refactored part of this method into a new method
                 * 'createFormalParamForPart()' to make it reusable
                 */
                for (Object inputPart : inputParts) {

                    if (inputPart instanceof Part) {

                        Part part = (Part) inputPart;
                        FormalParameter param =
                                JavaScriptConceptUtil.INSTANCE
                                        .createFormalParamForPart(part,
                                                ModeType.INOUT_LITERAL,
                                                true);
                        paramsList.add(param);
                    }
                }
            }
        }
        return paramsList;
    }

    /**
     * Checks if the Concept path returned by the input part have
     * correct/complete Javascript mappings. If not then notify the user with an
     * error Dialog box and throw CoreException to prevent the Generation of
     * Business service.
     * 
     * @param inputParts
     * @throws CoreException
     */
    public static void checkJavaScriptMappings(Collection<?> inputParts)
            throws CoreException {

        Collection<ConceptPath> conceptPaths =
                JavaScriptConceptUtil.INSTANCE.getConceptPaths(inputParts);
        for (ConceptPath conceptPath : conceptPaths) {

            if (JavaScriptConceptUtil.isInvalidPartParameter(conceptPath)) {
                throw new CoreException(
                        new Status(
                                IStatus.ERROR,
                                Activator.PLUGIN_ID,
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_message1));
            }
        }
    }

    /**
     * Create the data fields for the new process as per the parameters in the
     * activity interface data
     * 
     * @param activity
     *            - whose interface data is used to create the datafields
     * @return list of data fields
     */
    public static List<DataField> createDataFields(Activity activity) {

        List<DataField> dataFieldsList = new ArrayList<>();

        Collection<ActivityInterfaceData> reqActivityAssociatedParameters =
                ActivityInterfaceDataUtil.getActivityInterfaceData(activity);
        for (ActivityInterfaceData activityInterfaceData : reqActivityAssociatedParameters) {

            ProcessRelevantData processRelevantData =
                    activityInterfaceData.getData();

            /*
             * XPD-7706: Saket: We must create data fields for formal parameters
             * only.
             */
            if (processRelevantData instanceof FormalParameter) {

                ModeType mode = activityInterfaceData.getMode();

                if (ModeType.IN_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {

                    DataField dataField =
                            GenerateProcessUtil
                                    .createDataFieldForAssociatedData(processRelevantData);

                    dataFieldsList.add(dataField);
                }
            }
        }
        return dataFieldsList;
    }
}
