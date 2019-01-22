/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor change to update the XPDL when a referenced XPDL has been renamed or
 * moved.
 * 
 * @author njpatel
 * @since 3.5.10
 */
public class UpdateXpdlReferenceChange extends UpdateReferenceChange {

    /**
     * 
     */
    public UpdateXpdlReferenceChange() {
        super();
    }

    /**
     * @param wc
     * @param currentFiles
     * @param refactoredFiles
     */
    public UpdateXpdlReferenceChange(Xpdl2WorkingCopyImpl wc,
            IFile[] currentFiles, IFile[] refactoredFiles) {
        super(wc, currentFiles, refactoredFiles);
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

        pm.beginTask(String.format(Messages.UpdateXpdlReferenceChange_updateProcessReference_progress_shortdesc,
                wc.getName()), 1);

        IPath path = getSpecialFolderRelativePath(currentFile);
        if (path != null) {
            oldLocation = path.toString();
        }

        path = getSpecialFolderRelativePath(refactoredFile);
        if (path != null) {
            newLocation = path.toString();
        }

        if (oldLocation != null && newLocation != null) {

            /*
             * Process sub-flow references
             */
            processSubflowReferences(cmds, currentFile, refactoredFile);

            /*
             * Process page-flow references
             */
            /*
             * TODO: NO DEPENDENCY REPORTED FOR PAGE_FLOW REFERENCE
             */
            // processPageFlowReferences(cmds, oldLocation, newLocation);

        }

        pm.done();

        return cmds;
    }

    /**
     * Get the commands to update the subflow references
     * 
     * @param cmds
     */
    private void processSubflowReferences(List<Command> cmds,
            IFile currentFile, IFile refactoredFile) {
        Map<ExternalPackage, Set<SubFlow>> extPackageMap =
                new HashMap<ExternalPackage, Set<SubFlow>>();

        List<SubFlow> subFlows = getAllSubFlows(pkg);

        EList<ExternalPackage> externalPackages = pkg.getExternalPackages();
        if (!externalPackages.isEmpty()) {
            /*
             * Collect all sub flows and all external packages they reference
             */
            for (SubFlow subFlow : subFlows) {
                ExternalPackage extPackage =
                        (ExternalPackage) EMFSearchUtil
                                .findInList(externalPackages,
                                        Xpdl2Package.eINSTANCE
                                                .getExternalPackage_Href(),
                                        subFlow.getPackageRefId());
                if (extPackage != null) {
                    Set<SubFlow> relatedSubFlows =
                            extPackageMap.get(extPackage);
                    if (relatedSubFlows == null) {
                        relatedSubFlows = new HashSet<SubFlow>();
                        extPackageMap.put(extPackage, relatedSubFlows);
                    }
                    relatedSubFlows.add(subFlow);
                }
            }

            if (!extPackageMap.isEmpty()) {
                for (Entry<ExternalPackage, Set<SubFlow>> entry : extPackageMap
                        .entrySet()) {
                    ExternalPackage externalPackage = entry.getKey();
                    Set<SubFlow> flows = entry.getValue();

                    /*
                     * Update the external package
                     */
                    cmds.add(RemoveCommand.create(editingDomain,
                            pkg,
                            Xpdl2Package.eINSTANCE.getExternalPackage(),
                            externalPackage));

                    ExternalPackage refactoredExtPackage =
                            Xpdl2WorkingCopyImpl
                                    .createExternalPackage(refactoredFile);
                    cmds.add(AddCommand.create(editingDomain,
                            pkg,
                            Xpdl2Package.eINSTANCE
                                    .getPackage_ExternalPackages(),
                            refactoredExtPackage));

                    String href = refactoredExtPackage.getHref();
                    for (SubFlow flow : flows) {
                        cmds.add(SetCommand.create(editingDomain,
                                flow,
                                Xpdl2Package.eINSTANCE
                                        .getSubFlow_PackageRefId(),
                                href));
                    }

                }
            }
        }
    }

    /**
     * Get all the sub-flows in the given package.
     * 
     * @param pkg
     * @return
     */
    private List<SubFlow> getAllSubFlows(Package pkg) {
        List<SubFlow> subFlows = new ArrayList<SubFlow>();

        for (Process process : pkg.getProcesses()) {
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {

                Implementation implementation = activity.getImplementation();
                if (implementation instanceof SubFlow) {
                    subFlows.add((SubFlow) implementation);
                }
            }
        }

        return subFlows;

    }

    /**
     * Get the commands to update the page flow references.
     * 
     * @param cmds
     * @param newLocation
     * @param oldLocation
     */
    private void processPageFlowReferences(List<Command> cmds,
            String oldLocation, String newLocation) {
        List<FormImplementation> pageFlowImplementations =
                getAllUserTaskTypesWithPageFlowRef(pkg);

        for (FormImplementation formImplementation : pageFlowImplementations) {
            String UriStr = formImplementation.getFormURI();
            if (UriStr != null) {
                URI uri = URI.createURI(UriStr);
                String fragment = uri.fragment();

                URI newUri = URI.createURI(newLocation);
                if (fragment != null) {
                    newUri = newUri.appendFragment(fragment);
                }

                if (oldLocation.equals(uri.trimFragment().toString())) {
                    cmds.add(SetCommand.create(editingDomain,
                            formImplementation,
                            XpdExtensionPackage.eINSTANCE
                                    .getFormImplementation_FormURI(),
                            newUri.toString()));
                }
            }
        }
    }

    /**
     * Get all user task types with page-flow references.
     * 
     * @param pkg
     * @return
     */
    private List<FormImplementation> getAllUserTaskTypesWithPageFlowRef(
            Package pkg) {
        List<FormImplementation> formImplementations =
                new ArrayList<FormImplementation>();

        for (Process process : pkg.getProcesses()) {
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                FormImplementation formImplementation =
                        TaskObjectUtil.getUserTaskFormImplementation(activity);

                if (formImplementation != null
                        && formImplementation.getFormType() == FormImplementationType.PAGEFLOW) {
                    formImplementations.add(formImplementation);
                }
            }
        }

        return formImplementations;
    }

}
