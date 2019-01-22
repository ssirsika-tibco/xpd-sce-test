/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
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
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class EventBWMessageAdapter extends EventMessageAdapter {

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

        Participant sysParticipant =
                WSSystemParticSharedResUtil
                        .autoAssignEndpointParticipantCommand(process,
                                activity,
                                key,
                                objectsToCreate);

        if (sysParticipant != null) {
            /*
             * set the sys Partipcant in the activity performers and end point
             * name
             */
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add(sysParticipant);
            Performers performers = Xpdl2Factory.eINSTANCE.createPerformers();
            Performer newPerf = Xpdl2Factory.eINSTANCE.createPerformer();
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
                 * Ensure that when end point participant is set, the IsRemote
                 * flag is also set.
                 */
                if (webServiceOperation.getService() != null) {
                    Xpdl2ModelUtil.setOtherAttribute(webServiceOperation
                            .getService(), XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_IsRemote(), true);
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

        Participant sysParticipant =
                WSSystemParticSharedResUtil
                        .autoAssignEndpointParticipantCommand(editingDomain,
                                process,
                                activity,
                                key,
                                cpdCmd);

        if (sysParticipant != null) {
            /*
             * set the sys Partipcant in the activity performers and end point
             * name
             */
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add(sysParticipant);
            Command perfCommand =
                    TaskObjectUtil.getSetPerformersCommand(editingDomain,
                            activity,
                            perfs.toArray(new EObject[0]));
            if (perfCommand != null) {
                cpdCmd.append(perfCommand);
            }

            cpdCmd.append(new SetWebServiceOperationAliasCommand(editingDomain,
                    activity, sysParticipant.getId()));

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
