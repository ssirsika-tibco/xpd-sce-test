/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.wst.wsdl.Part;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.wizards.AddExtrasToNewProcessCommand;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class generates data for a pageflow generated for start event type none
 * 1. creates pageflow process level data fields for the corresponding
 * parameters in the start event 2. creates web service implementation
 * information for the send task (present in the generated pageflow) 3. creates
 * destination for the pageflow process
 * 
 * 
 * @author bharge
 * @since 3.3 (26 Feb 2010)
 * @deprecated Not in Use can be deleted.
 */
@Deprecated
public class NewStartEventPageflowProcess {

    /**
     * @param startEventActivity
     */
    private Activity startEventActivity;

    public NewStartEventPageflowProcess(Activity startEventActivity) {
        super();
        this.startEventActivity = startEventActivity;
    }

    protected Command createFinalAddProcessCommand(EditingDomain ed,
            WorkingCopy wc, Process pageflowProcess,
            Command createDefaultProcessCmd) throws CoreException {

        Package pckg = Xpdl2ModelUtil.getPackage(startEventActivity);
        if (null != pckg) {

            String procName =
                    Xpdl2ModelUtil.getDisplayNameOrName(pageflowProcess);
            if (procName == null || procName.length() == 0) {
                procName = Xpdl2ResourcesConsts.DEFAULT_PROCESS_NAME;
            }

            String baseName = procName;
            String finalName = baseName;
            int idx = 1;
            while (Xpdl2ModelUtil.getDuplicateDisplayNameProcess(pckg,
                    pageflowProcess,
                    finalName) != null
                    || Xpdl2ModelUtil.getDuplicateProcess(pckg,
                            pageflowProcess,
                            NameUtil.getInternalName(finalName, false)) != null) {
                idx++;
                finalName = baseName + idx;
            }
            pageflowProcess.setName(NameUtil.getInternalName(finalName, false));
            Xpdl2ModelUtil
                    .setOtherAttribute(pageflowProcess,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            finalName);
        }

        if (null != startEventActivity) {
            createDataFieldsForPageflow(pageflowProcess);
        }
        addProjectDestinationsToProcess(pageflowProcess, ed, wc);

        return new AddExtrasToNewProcessCommand(ed, wc, pageflowProcess,
                createDefaultProcessCmd);
    }

    private void addProjectDestinationsToProcess(Process newPageflowProcess,
            EditingDomain ed, WorkingCopy wc) {

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
     * @return
     */
    private IProject getProject(WorkingCopy wc) {
        if (wc != null) {
            IResource resource = wc.getEclipseResources().get(0);
            if (resource != null) {
                return resource.getProject();
            }
        }
        return null;
    }

    /**
     * Creates Data Field for PageFlow process, if data field cannot be
     * generated, then prevent the generation of PageFlow process by throwing
     * Core exception.
     * 
     * @param pageflowProcess
     * @throws CoreException
     */
    private void createDataFieldsForPageflow(Process pageflowProcess)
            throws CoreException {
        if (null != startEventActivity) {

            Collection<DataField> dataFieldsForBizService =
                    createDataFieldsForBizService(startEventActivity);
            if (dataFieldsForBizService.size() > 0) {
                pageflowProcess.getDataFields().addAll(dataFieldsForBizService);
            }
        }
    }

    /**
     * Creates Data Field for Business Service, if data field cannot be
     * generated, then prevent the generation of Business Service by throwing
     * Core exception.
     * 
     * @param requestActivity
     * @return List of Data field
     * @throws CoreException
     */
    public Collection<DataField> createDataFieldsForBizService(
            Activity requestActivity) throws CoreException {

        List<DataField> dataFieldsList = new ArrayList<DataField>();

        PortTypeOperation reqActivityPortTypeOp =
                Xpdl2ModelUtil.getPortTypeOperation(requestActivity);

        if (null != reqActivityPortTypeOp) {
            /**
             * XPD-957: generate data fields for pageflow as per the parameters
             * in the user defined wsdl configured on message start event
             */
            List<FormalParameter> formalParams =
                    getFormalParamForPageflow(requestActivity);

            for (FormalParameter formalParameter : formalParams) {
                ModeType mode = formalParameter.getMode();
                if (ModeType.IN_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {
                    DataField dataField =
                            GenerateProcessUtil
                                    .createDataFieldForAssociatedData(formalParameter);

                    dataFieldsList.add(dataField);
                }
            }

        } else {
            /**
             * generate data fields for pageflow as per the parameters in the
             * business process (since generated wsdl would have process
             * parameters)
             */
            List<FormalParameter> reqActivityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getAssociatedFormalParameters(requestActivity);

            for (FormalParameter formalParameter : reqActivityAssociatedParameters) {
                /*
                 * Don't create data fields for Out params in business process -
                 * this would end up creating a broken mapping in business
                 * service send task
                 */
                ModeType mode = formalParameter.getMode();
                if (ModeType.IN_LITERAL.equals(mode)
                        || ModeType.INOUT_LITERAL.equals(mode)) {
                    DataField dataField =
                            GenerateProcessUtil
                                    .createDataFieldForAssociatedData(formalParameter);
                    dataFieldsList.add(dataField);
                }
            }
        }
        return dataFieldsList;
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
    public List<FormalParameter> getFormalParamForPageflow(Activity bpMsgAct)
            throws CoreException {
        List<FormalParameter> paramsList = new ArrayList<FormalParameter>();
        if (Xpdl2ModelUtil.isGeneratedRequestActivity(bpMsgAct)) {
            /*
             * XPD-1811: If activity is generated then return the actual formal
             * parameters associated with the activity rather than making up new
             * temporary ones from the WSDL input parts (then the extra bits
             * like length of the parameter will be available).
             */
            paramsList.addAll(ProcessInterfaceUtil
                    .getAssociatedFormalParameters(bpMsgAct));
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
    public void checkJavaScriptMappings(Collection<?> inputParts)
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

}
