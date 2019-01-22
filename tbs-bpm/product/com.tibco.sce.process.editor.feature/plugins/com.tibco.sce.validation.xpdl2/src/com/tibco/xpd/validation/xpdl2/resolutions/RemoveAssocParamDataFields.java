package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to remove all associated parameters that are of data field type
 * from a receive task
 * 
 * @author glewis
 * 
 */
public class RemoveAssocParamDataFields extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof Process || target instanceof Activity) {
            // if activity then get its associated participant and select the
            // first participant
            if (target instanceof Activity) {
                Process process = ((Activity) target).getProcess();
                Activity activity = (Activity) target;
                Implementation implementation = activity.getImplementation();
                if (implementation instanceof Task) {
                    TaskReceive recieveTask =
                            ((Task) implementation).getTaskReceive();
                    if (recieveTask != null) {
                        List<AssociatedParameter> assocParams =
                                ProcessInterfaceUtil
                                        .getActivityAssociatedParameters(activity);
                        if (assocParams != null) {
                            for (AssociatedParameter assoc : assocParams) {
                                if (isDataField(activity.getProcess(), assoc
                                        .getFormalParam())) {
                                    cmd.append(RemoveCommand
                                            .create(editingDomain, assoc));
                                }
                            }
                        }
                    }
                }
            }
        }

        return cmd.unwrap();
    }

    /**
     * Gets the data field (if any) matched to this particular associated
     * parameter
     * 
     * @param process
     * @param paramName
     * @return
     */
    private boolean isDataField(Process process, String paramName) {
        Package xpdlPackage = (Package) process.eContainer();

        // check package data fields
        List<DataField> packageDataFields = xpdlPackage.getDataFields();
        for (Iterator<DataField> iter = packageDataFields.iterator(); iter
                .hasNext();) {
            DataField dataField = iter.next();
            if (dataField.getName().equals(paramName)) {
                return true;
            }
        }

        // check process data fields
        List<DataField> processDataFields = process.getDataFields();
        for (Iterator<DataField> iter = processDataFields.iterator(); iter
                .hasNext();) {
            DataField dataField = iter.next();
            if (dataField.getName().equals(paramName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the participant matched to this particular performer changes it to a
     * System Participant Type.
     * 
     * @param process
     * @param performer
     * @param cmd
     * @param editingDomain
     * @param activity
     * @param wso
     * @return
     */
    private boolean useFirstSystemParticipant(Process process,
            Performer performer, CompoundCommand cmd,
            EditingDomain editingDomain, Activity activity,
            WebServiceOperation wso) {
        Package xpdlPackage = (Package) process.eContainer();

        // check package participants
        List<?> packageParticipants = xpdlPackage.getParticipants();
        for (Iterator<?> iter = packageParticipants.iterator(); iter.hasNext();) {
            Participant partic = (Participant) iter.next();
            if (partic.getId().equals(performer.getValue())) {
                addParticipantAndSetAlias(cmd,
                        editingDomain,
                        partic,
                        activity,
                        wso);
                return true;
            }
        }

        // check process participants
        List<?> processParticipants = process.getParticipants();
        for (Iterator<?> iter = processParticipants.iterator(); iter.hasNext();) {
            Participant partic = (Participant) iter.next();
            if (partic.getId().equals(performer.getValue())) {
                addParticipantAndSetAlias(cmd,
                        editingDomain,
                        partic,
                        activity,
                        wso);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the participant and sets alias on activity after first clearing all
     * participants.
     * 
     * @param cmd
     * @param editingDomain
     * @param partic
     * @param activity
     * @param wso
     */
    private void addParticipantAndSetAlias(CompoundCommand cmd,
            EditingDomain editingDomain, Participant partic, Activity activity,
            WebServiceOperation wso) {
        Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;

        EObject[] emptyArray = new EObject[] {};
        Command command =
                TaskObjectUtil.getSetPerformersCommand(editingDomain,
                        activity,
                        emptyArray);
        if (command != null && command.canExecute()) {
            command.execute();
            List<EObject> perfs = new ArrayList<EObject>();
            perfs.add((EObject) partic);
            command =
                    TaskObjectUtil.getSetPerformersCommand(editingDomain,
                            activity,
                            perfs.toArray(new EObject[0]));
            if (command != null) {
                cmd.append(command);
            }
            if (wso != null) {
                Xpdl2ModelUtil.setOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        partic.getName());
            }
        }
    }

}
