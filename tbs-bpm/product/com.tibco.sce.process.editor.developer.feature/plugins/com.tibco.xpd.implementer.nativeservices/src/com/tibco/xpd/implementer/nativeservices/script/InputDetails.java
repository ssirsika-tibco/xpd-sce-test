package com.tibco.xpd.implementer.nativeservices.script;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;

public class InputDetails {

    private EObject eObject;

    public InputDetails(EObject eObject) {
        this.eObject = eObject;
    }

    /**
     * @return the eObject
     */
    public EObject getEObject() {
        return eObject;
    }

    /**
     * @return the activityName
     */
    public String getScriptContainerId() {
        if (eObject instanceof NamedElement) {
            NamedElement nElement = ((NamedElement) eObject);
            return nElement.getId();
        }
        return null;
    }

    /**
     * @return the activityName
     */
    public String getScriptContainerName() {
        if (eObject instanceof NamedElement) {
            NamedElement nElement = ((NamedElement) eObject);
            return nElement.getName();
        }
        return null;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return getWorkingCopy().getName();
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        Process process = getProcess();
        if (process == null) {
            return null;
        }
        return process.getName();
    }

    public Process getProcess() {
        Process toReturn = null;
        EObject tempInput = eObject;
        while (true) {
            if (tempInput instanceof Process) {
                toReturn = (Process) tempInput;
                break;
            }
            tempInput = tempInput.eContainer();
        }
        return toReturn;
    }

    public List<String> getProcessDestinationList() {
        Process process = getProcess();
        List<String> processDestList =
                new ArrayList<String>(DestinationUtil
                        .getEnabledValidationDestinations(process));
        return processDestList;
    }

    public IResource getInputResource() {
        WorkingCopy workingCopy = getWorkingCopy();
        return workingCopy == null ? null : workingCopy.getEclipseResources()
                .get(0);
    }

    private WorkingCopy getWorkingCopy() {
        WorkingCopy xpdlWorkingCopyFor = null;
        if (eObject != null) {
            xpdlWorkingCopyFor = WorkingCopyUtil.getWorkingCopyFor(eObject);
            if (xpdlWorkingCopyFor == null
                    && eObject instanceof ScriptInformation) {
                ScriptInformation scriptInformation =
                        (ScriptInformation) eObject;
                if (scriptInformation.getActivity() != null) {
                    xpdlWorkingCopyFor =
                            WorkingCopyUtil.getWorkingCopyFor(scriptInformation
                                    .getActivity());
                }
            }
        }
        return xpdlWorkingCopyFor;
    }

}
