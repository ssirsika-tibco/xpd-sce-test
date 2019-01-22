/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Port;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Performers;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author aallway
 * 
 */
public class TaskBWServiceMessageAdapter extends TaskServiceMessageAdapter {

    /** BW Service identifier. */
    public static final String XPDEXT_BWSVC_LITERAL = "BW Service"; //$NON-NLS-1$

    /**
     * @return The implementation type.
     * @see com.tibco.xpd.implementer.script.TaskServiceMessageAdapter#
     *      getXpdExtImplementationType()
     */
    @Override
    protected String getXpdExtImplementationType() {
        return XPDEXT_BWSVC_LITERAL;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#autoAssignEndpointParticipant(java.util.List,
     *      com.tibco.xpd.xpdl2.Process, com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.wsdl.WsdlServiceKey)
     * 
     * @param objectsToCreate
     * @param process
     * @param activity
     * @param key
     */
    @Override
    protected void autoAssignEndpointParticipant(List<Object> objectsToCreate,
            Process process, Activity activity, WsdlServiceKey key) {

        String serviceName = key.getService();
        String portName = key.getPort();
        boolean isProcessApiActivity = false;

        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            isProcessApiActivity = true;
        }

        if (serviceName != null && portName != null && process != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            IResource res = wc.getEclipseResources().get(0);

            if (res != null) {
                IProject project = res.getProject();
                Port port =
                        WsdlIndexerUtil.getPort(project,
                                serviceName,
                                portName,
                                key.getFilePath(),
                                true,
                                true);
                if (port != null) {
                    String endPointUrl =
                            WSSystemParticSharedResUtil
                                    .getLocalWsdlTargetAddress(port);
                    if (endPointUrl != null && endPointUrl.length() > 0) {
                        Participant sysParticipant =
                                getWSParticipantForEndPoint(process,
                                        endPointUrl,
                                        isProcessApiActivity);
                        if (sysParticipant == null) {
                            sysParticipant =
                                    createWSParticipantForEndPoint(endPointUrl,
                                            process,
                                            activity);
                            if (sysParticipant != null) {
                                objectsToCreate.add(sysParticipant);
                            }
                        }

                        if (sysParticipant != null) {
                            /*
                             * set the sys Partipcant in the activity performers
                             * and end point name
                             */
                            List<EObject> perfs = new ArrayList<EObject>();
                            perfs.add(sysParticipant);
                            Performers performers =
                                    Xpdl2Factory.eINSTANCE.createPerformers();
                            Performer newPerf =
                                    Xpdl2Factory.eINSTANCE.createPerformer();
                            newPerf.setValue(sysParticipant.getId());
                            performers.getPerformers().add(newPerf);
                            activity.setPerformers(performers);
                            WebServiceOperation webServiceOperation =
                                    getWebServiceOperation(activity);
                            if (webServiceOperation != null) {
                                webServiceOperation
                                        .getOtherAttributes()
                                        .set(XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                                                sysParticipant.getId());

                                /*
                                 * Ensure that when end point participant is
                                 * set, the IsRemote flag is also set.
                                 */
                                if (webServiceOperation.getService() != null) {
                                    Xpdl2ModelUtil
                                            .setOtherAttribute(webServiceOperation
                                                    .getService(),
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_IsRemote(),
                                                    true);
                                }

                            }
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @see com.tibco.xpd.implementer.script.AbstractActivityMessageAdapter#autoAssignEndpointParticipantCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Process, com.tibco.xpd.xpdl2.Activity,
     *      com.tibco.xpd.wsdl.WsdlServiceKey,
     *      org.eclipse.emf.common.command.CompoundCommand)
     * 
     * @param editingDomain
     * @param process
     * @param activity
     * @param key
     * @param cpdCmd
     */
    @Override
    public void autoAssignEndpointParticipantCommand(
            EditingDomain editingDomain, Process process, Activity activity,
            WsdlServiceKey key, CompoundCommand cpdCmd) {

        String serviceName = key.getService();
        String portName = key.getPort();
        boolean isProcessApiActivity = false;

        if (Xpdl2ModelUtil.isProcessAPIActivity(activity)) {
            isProcessApiActivity = true;
        }

        if (serviceName != null && portName != null && process != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            IResource res = wc.getEclipseResources().get(0);
            if (res != null) {
                IProject project = res.getProject();
                Port port =
                        WsdlIndexerUtil.getPort(project,
                                serviceName,
                                portName,
                                key.getFilePath(),
                                true,
                                true);
                if (port != null) {
                    String endPointUrl =
                            WSSystemParticSharedResUtil
                                    .getLocalWsdlTargetAddress(port);
                    if (endPointUrl != null && endPointUrl.length() > 0) {
                        /*
                         * Get existing participant for this end point url if
                         * there is one.
                         */
                        Participant sysParticipant =
                                getWSParticipantForEndPoint(process,
                                        endPointUrl,
                                        isProcessApiActivity);
                        if (sysParticipant == null) {
                            sysParticipant =
                                    createWSParticipantForEndPoint(endPointUrl,
                                            process,
                                            activity);

                            if (sysParticipant != null) {
                                cpdCmd.append(AddCommand.create(editingDomain,
                                        process.getPackage(),
                                        Xpdl2Package.eINSTANCE
                                                .getParticipantsContainer_Participants(),
                                        sysParticipant));
                            }
                        }

                        if (sysParticipant != null) {
                            /*
                             * set the sys Partipcant in the activity performers
                             * and end point name
                             */
                            List<EObject> perfs = new ArrayList<EObject>();
                            perfs.add(sysParticipant);
                            Command perfCommand =
                                    TaskObjectUtil
                                            .getSetPerformersCommand(editingDomain,
                                                    activity,
                                                    perfs.toArray(new EObject[0]));
                            if (perfCommand != null) {
                                cpdCmd.append(perfCommand);
                            }

                            cpdCmd.append(new SetWebServiceOperationAliasCommand(
                                    editingDomain, activity, sysParticipant
                                            .getId()));

                        }
                    }
                }
            }
        }

        return;
    }

    protected Participant getWSParticipantForEndPoint(Process process,
            String endPointUrl, boolean wantProcessAPIActivity) {
        /*
         * Traditionally, the auto-assign participant functionality for BW
         * services is such that the participant name itself is the end point
         * url.
         */
        Participant sysParticipant =
                Xpdl2ModelUtil.getParticipant(process, endPointUrl);
        return sysParticipant;
    }

    protected Participant createWSParticipantForEndPoint(String endPointUrl,
            Process process, Activity activity) {
        /*
         * Traditionally, the auto-assign participant functionality for BW
         * services is such that the participant name itself is the end point
         * url.
         */
        Participant sysParticipant = Xpdl2Factory.eINSTANCE.createParticipant();

        sysParticipant.setName(endPointUrl);
        Xpdl2ModelUtil.setOtherAttribute(sysParticipant,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                endPointUrl);

        // Set system participant type
        ParticipantTypeElem typeElem =
                Xpdl2Factory.eINSTANCE.createParticipantTypeElem();
        typeElem.setType(ParticipantType.SYSTEM_LITERAL);
        sysParticipant.setParticipantType(typeElem);

        return sysParticipant;
    }
}
