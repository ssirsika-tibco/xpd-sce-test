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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor change to update the XPDL when a referenced BOM has been renamed or
 * moved.
 * 
 * @author njpatel
 */
public class UpdateBOMReferenceChange extends UpdateReferenceChange {

    /**
     * @param wc
     * @param currentFile
     * @param refactoredFile
     */
    public UpdateBOMReferenceChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles) {
        super(wc, currentFiles, refactoredFiles);
    }

    /**
     * 
     */
    public UpdateBOMReferenceChange() {
        super();
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

        /*
         * Calculate the special folder relative paths to the BOM being
         * renamed/moved.
         */
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
            List<ExternalReference> extRefs =
                    new ArrayList<ExternalReference>();

            pm.beginTask(String
                    .format(Messages.UpdateBOMReferenceChange_updateBOMReferences_progress_shortdesc,
                            wc.getName()),
                    1);
            try {
                Collection<ProcessRelevantData> allDataInPackage =
                        ProcessInterfaceUtil.getAllDataInPackage(pkg);

                // Process all data
                for (ProcessRelevantData data : allDataInPackage) {
                    ExternalReference extRef =
                            getDataFieldExternalRefWithLocation(data,
                                    oldLocation);

                    if (extRef != null) {
                        extRefs.add(extRef);
                    }
                }

                // Process all type declarations
                for (TypeDeclaration decl : pkg.getTypeDeclarations()) {
                    if (decl.getExternalReference() != null
                            && oldLocation.equals(decl.getExternalReference()
                                    .getLocation())) {
                        extRefs.add(decl.getExternalReference());
                    }
                }

                /* XPD-6790 Update Case Service */
                EList<Process> processes = pkg.getProcesses();
                for (Process process : processes) {

                    CaseService caseService = null;
                    Object object =
                            Xpdl2ModelUtil.getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_CaseService());

                    if (object instanceof CaseService) {
                        caseService = (CaseService) object;
                    }

                    if (caseService != null
                            && caseService.getCaseClassType() != null) {

                        ExternalReference caseClassExtRef =
                                caseService.getCaseClassType();

                        if (caseClassExtRef != null
                                && oldLocation.equals(caseClassExtRef
                                        .getLocation())) {
                            extRefs.add(caseClassExtRef);
                        }
                    }

                }

                if (!extRefs.isEmpty()) {
                    /*
                     * Replace the old location with the new
                     */
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
     * Returns the {@link ExternalReference} if the given data field is an
     * external reference type, null otherwise.
     * 
     * @param data
     * @param location
     * @return {@link ExternalReference} for this data, null if this data does
     *         not have an external reference.
     */
    private ExternalReference getDataFieldExternalRefWithLocation(
            ProcessRelevantData data, String location) {

        DataType dataType = data.getDataType();
        ExternalReference extRef = null;
        if ((dataType instanceof ExternalReference && location
                .equals(((ExternalReference) dataType).getLocation()))) {

            extRef = (ExternalReference) dataType;

        } else if (dataType instanceof RecordType) {

            RecordType recType = (RecordType) dataType;

            if (recType.getMember() != null && !recType.getMember().isEmpty()) {

                ExternalReference eRef =
                        recType.getMember().get(0).getExternalReference();

                if (location.equals(eRef.getLocation())) {
                    extRef = eRef;
                }
            }

        }

        return extRef;
    }

}
