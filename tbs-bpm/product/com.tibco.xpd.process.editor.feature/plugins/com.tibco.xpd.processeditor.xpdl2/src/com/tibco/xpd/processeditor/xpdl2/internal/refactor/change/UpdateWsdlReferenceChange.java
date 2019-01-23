/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor change to update an XPDL when a referenced WSDL has been renamed or
 * moved.
 * 
 * @author njpatel
 */
public class UpdateWsdlReferenceChange extends UpdateReferenceChange {

    /**
     * 
     */
    public UpdateWsdlReferenceChange() {
        super();
    }

    /**
     * @param wc
     * @param currFile
     * @param refactoredFile
     */
    public UpdateWsdlReferenceChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currFiles, IFile[] refactoredFiles) {
        super(wc, currFiles, refactoredFiles);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.UpdateReferenceChange#perform(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IFile, org.eclipse.core.resources.IFile)
     * 
     * @param pm
     * @param currentFile
     * @param refactoredFile
     * @return
     */
    @Override
    protected List<Command> perform(IProgressMonitor pm, IFile currentFile,
            IFile refactoredFile) {
        List<Command> cmds = new ArrayList<Command>();

        String oldLocation = null;
        String newLocation = null;

        IPath path = getSpecialFolderRelativePath(currentFile);
        if (path != null) {
            oldLocation = path.toString();
        }

        path = getSpecialFolderRelativePath(refactoredFile);
        if (path != null) {
            newLocation = path.toString();
        }
        if (oldLocation != null && newLocation != null) {
            pm.beginTask(String.format(Messages.UpdateWsdlReferenceChange_updateReference_progress_shortdesc,
                    wc.getName()),
                    1);
            List<ExternalReference> extRefs =
                    getExternalReferences(oldLocation);
            try {

                if (!extRefs.isEmpty()) {
                    // Replace old location with the new
                    for (ExternalReference ref : extRefs) {
                        if (oldLocation.equals(ref.getLocation())) {
                            cmds.add(SetCommand.create(editingDomain,
                                    ref,
                                    Xpdl2Package.eINSTANCE
                                            .getExternalReference_Location(),
                                    newLocation));
                        }
                    }
                }
            } finally {
                pm.done();
            }
        }

        return cmds;
    }

    /**
     * Get all the ExternalReferences to the WSDL being renamed/moved.
     * 
     * @param oldLocation
     * @return
     */
    private List<ExternalReference> getExternalReferences(String oldLocation) {
        List<ExternalReference> refs = new ArrayList<ExternalReference>();
        Collection<ActivityWebServiceReference> references =
                ProcessUIUtil.getActivityWebServiceReferences(pkg);

        for (ActivityWebServiceReference ref : references) {
            String location = ref.getWsdlFileLocation();

            if (oldLocation.equals(location)) {
                Activity activity = ref.getActivity();

                if (activity != null) {
                    // Check the service end-point
                    WebServiceOperation wso =
                            Xpdl2ModelUtil.getWebServiceOperation(activity);
                    if (wso != null && wso.getService() != null) {
                        EndPoint endPoint = wso.getService().getEndPoint();
                        if (endPoint != null) {
                            ExternalReference reference =
                                    endPoint.getExternalReference();
                            if (reference != null
                                    && oldLocation.equals(reference
                                            .getLocation())) {
                                refs.add(reference);
                            }
                        }
                    }

                    // Check the port type operation
                    PortTypeOperation pto =
                            Xpdl2ModelUtil.getPortTypeOperation(activity);
                    if (pto != null) {
                        ExternalReference reference =
                                pto.getExternalReference();
                        if (reference != null
                                && oldLocation.equals(reference.getLocation())) {
                            refs.add(reference);
                        }
                    }
                }
            }
        }

        return refs;
    }

}
