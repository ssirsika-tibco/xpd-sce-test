/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.ActivityInvokingProcessIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractXpdlProjectSelectPage;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.CostUnit;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.EndPoint;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.PublicationStatusType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.util.LocaleUtils;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Change that Actually refactors Process to the destination package. Does the
 * following:
 * <p>
 * 1. Moves process to destination package[along with all required/dependent
 * EObjects e.g. artifacts, participants, type declaration]
 * <p>
 * 2. Fixes any references that might break after process refactor
 * <p>
 * 3. Deletes the source process.
 * <p>
 * NOTE: This change does not support UNDO-REDO
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */

public class MoveProcessToPackageChange extends Change {

    /**
     * The object to be moved
     */
    private com.tibco.xpd.xpdl2.Process sourceProcess;

    /**
     * The location/container to which the object should be moved.
     */
    private Package destinationPackage;

    private EditingDomain editingDomain;

    /**
     * the destination Processes Special folder
     */
    private IFolder destinationProcessPackageFolder;

    /**
     * The new package name in case the user wants to create a new package
     */
    private String newPackageName;

    /**
     * Copy of all objects that will be moved along with the process.(i.e. the
     * process itself, the pools, atrifacts, associations,participants, type
     * declarations)
     */
    @SuppressWarnings("rawtypes")
    private Collection copyOfAllObjectsToMove;

    /**
     * Map of Old elements Id's to the new Copied elements with reassigned ID's
     */
    private Map<String, EObject> oldIdToNewEObjectMap;

    private static String WSDL_FILE_EXTENSION = ".wsdl"; //$NON-NLS-1$

    /**
     * Call this constructor if we already have the destination package present.
     * 
     * @param sourceProcess
     * @param destinationPackage
     * @param editingDomain
     * @param oldIdToNewEObjectMap
     * @param copyOfAllObjectsToMove
     */
    @SuppressWarnings("rawtypes")
    public MoveProcessToPackageChange(Process sourceProcess,
            com.tibco.xpd.xpdl2.Package destinationPackage,
            EditingDomain editingDomain, Collection copyOfAllObjectsToMove,
            Map<String, EObject> oldIdToNewEObjectMap) {
        this.sourceProcess = sourceProcess;
        this.destinationPackage = destinationPackage;
        this.editingDomain = editingDomain;
        this.copyOfAllObjectsToMove = copyOfAllObjectsToMove;
        this.oldIdToNewEObjectMap = oldIdToNewEObjectMap;
        this.destinationProcessPackageFolder = null;
        this.newPackageName = null;
    }

    /**
     * Call this constructor if the user wants to create a new Destination
     * package.
     * 
     * @param sourceProcess
     * @param destinationProcessPackageFolder
     * @param newPackageName
     * @param editingDomain
     * @param oldIdToNewEObjectMap
     * @param copyOfAllObjectsToMove
     */
    @SuppressWarnings("rawtypes")
    public MoveProcessToPackageChange(Process sourceProcess,
            IFolder destinationProcessPackageFolder, String newPackageName,
            EditingDomain editingDomain, Collection copyOfAllObjectsToMove,
            Map<String, EObject> oldIdToNewEObjectMap) {
        this.sourceProcess = sourceProcess;
        this.editingDomain = editingDomain;
        this.destinationProcessPackageFolder = destinationProcessPackageFolder;
        this.newPackageName = newPackageName;
        this.copyOfAllObjectsToMove = copyOfAllObjectsToMove;
        this.oldIdToNewEObjectMap = oldIdToNewEObjectMap;
        createAndInitializeNewPackage(destinationProcessPackageFolder,
                newPackageName);
    }

    /**
     * Creates and initializes the new Package.
     * 
     * @param destinationProcessPackageFolder
     *            the Processes Folder under which the new package should be
     *            created.
     * @param newPackageName
     *            the name of the new package to create.
     */
    private void createAndInitializeNewPackage(
            IFolder destinationProcessPackageFolder, String newPackageName) {

        IProject project = destinationProcessPackageFolder.getProject();

        this.destinationPackage = getCreatePackage(newPackageName, project);
    }

    /**
     * 
     * @param xpdlFileName
     *            the new Package name
     * @param iProject
     *            the Project under which the package is to be created.
     * @return the new {@link Package} created
     */
    private Package getCreatePackage(String xpdlFileName, IProject iProject) {

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
     * 
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return String
                .format(Messages.MoveProcessToPackageChange_MoveProcessChange_desc,
                        sourceProcess.getName(),
                        getFullyQualifiedDestinationLocation());

    }

    /**
     * 
     * @return the fully qualified destination Package location.
     */
    private String getFullyQualifiedDestinationLocation() {
        String destinationLocation = ""; //$NON-NLS-1$

        IFile file = WorkingCopyUtil.getFile(destinationPackage);

        if (file != null) {
            destinationLocation = file.getFullPath().toString();
        } else {
            destinationLocation =
                    destinationProcessPackageFolder.getFullPath().toString()
                            + "/" + newPackageName; //$NON-NLS-1$
        }
        return destinationLocation;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        // Do nothing
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        try {
            CompoundCommand refactorProcessCommand =
                    getRefactorProcessCommand();

            RefactoringStatus status = new RefactoringStatus();

            final WorkingCopy wc = getWorkingCopy(destinationPackage);

            RefactoringStatusContext context = new RefactoringStatusContext() {
                @Override
                public Object getCorrespondingElement() {
                    return wc;
                }
            };

            pm.beginTask("", //$NON-NLS-1$
                    2);

            pm.subTask(String
                    .format(Messages.MoveProcessToPackageChange_ValidationMoveProcessChange_msg,
                            getName()));

            if (wc != null) {

                if (!wc.isExist()) {
                    status.addFatalError(String
                            .format(Messages.MoveProcessToPackageChange_ResourceDoesNotExistsError_msg,
                                    wc.getName()),
                            context);
                } else if (wc.isInvalidFile()) {
                    status.addWarning(String
                            .format(Messages.MoveProcessToPackageChange_WorkingCopyInvalidError_msg,
                                    wc.getName()),
                            context);
                } else if (wc.isReadOnly()) {
                    status.addWarning(String
                            .format(Messages.MoveProcessToPackageChange_WorkingCopyReadOnlyError_msg),
                            context);
                }
            }
            pm.worked(1);

            if (refactorProcessCommand == null) {
                status.addFatalError(String
                        .format(Messages.MoveProcessToPackageChange_NoCommandFoundError_msg,
                                getName()),
                        context);
            } else if (!refactorProcessCommand.canExecute()) {
                status.addFatalError(String
                        .format(Messages.MoveProcessToPackageChange_CannotExecuteCommandError_msg,
                                getName()),
                        context);
            }
            pm.worked(1);

            return status;

        } finally {
            pm.done();
        }
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     */
    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {
        pm.beginTask("", 1); //$NON-NLS-1$
        pm.subTask(getName());

        try {

            if (shouldCreateNewPackage()) {
                /* create new package if the user chooses to create new package */
                createNewXpdlResource(pm);
            }

            WorkingCopy destinationWC = getWorkingCopy(destinationPackage);
            WorkingCopy souceWC = getWorkingCopy(sourceProcess);

            CompoundCommand refactorProcessCommand =
                    getRefactorProcessCommand();

            if (refactorProcessCommand.canExecute()) {

                editingDomain.getCommandStack().execute(refactorProcessCommand);

                /**
                 * Save the source the destination packages
                 */
                if (destinationWC != null) {
                    destinationWC.save();
                }
                if (souceWC != null) {
                    souceWC.save();
                }
            }

        } catch (Exception e) {
            /*
             * If refactoring process throws any exception then delete the new
             * Xpdl file create(if it is created).
             */

            if (destinationProcessPackageFolder != null) {
                IFile file =
                        destinationProcessPackageFolder.getFile(newPackageName);

                if (file != null && file.exists()) {
                    file.delete(true, new NullProgressMonitor());
                }

            }

            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        pm.worked(1);

        /*
         * Return null as we dont want to support undo redo
         */
        return null;
    }

    /**
     * 
     * @return <code>true</code> if a new Process Package should be created else
     *         return <code>false</code>
     */
    private boolean shouldCreateNewPackage() {
        return newPackageName != null;
    }

    /**
     * Creates new XPDL Resource
     * 
     * @param pm
     */
    private void createNewXpdlResource(IProgressMonitor pm) {

        String pathName = destinationProcessPackageFolder.getFullPath() + "/" //$NON-NLS-1$
                + newPackageName;

        URI uri = URI.createPlatformResourceURI(pathName, true);
        IFile file = destinationProcessPackageFolder.getFile(newPackageName);

        ResourceSet rset = new ResourceSetImpl();
        Resource resource = rset.createResource(uri);

        DocumentRoot docRoot = Xpdl2Factory.eINSTANCE.createDocumentRoot();

        docRoot.setPackage(destinationPackage);
        resource.getContents().add(docRoot);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {

            resource.save(os, null);
        } catch (IOException e) {
            try {

                throw new InvocationTargetException(e);
            } catch (InvocationTargetException e1) {

                XpdResourcesPlugin.getDefault().getLogger().error(e1);
            }
        }
        try {

            file.create(new ByteArrayInputStream(os.toByteArray()), true, pm);
        } catch (CoreException e1) {

            XpdResourcesPlugin.getDefault().getLogger().error(e1);
        }

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        this.destinationPackage = (Package) wc.getRootElement();

    }

    /**
     * 
     * @return the Command to Refactor Process to destination Package, Fix
     *         references and deleted the old process
     */
    @SuppressWarnings("rawtypes")
    protected CompoundCommand getRefactorProcessCommand() {
        Collection<Pool> processPools =
                Xpdl2ModelUtil.getProcessPools(sourceProcess);

        Collection<Artifact> allArtifactsInProcess =
                Xpdl2ModelUtil.getAllArtifactsInProcess(sourceProcess);

        Collection<Association> allAssociationsInProc =
                Xpdl2ModelUtil.getAllAssociationsInProc(sourceProcess);
        /*
         * Stores all Objects to delete after Refactor
         */
        List<EObject> allEObjectsToDelete = new ArrayList<EObject>();
        allEObjectsToDelete.add(sourceProcess);
        allEObjectsToDelete.addAll(processPools);
        allEObjectsToDelete.addAll(allArtifactsInProcess);
        allEObjectsToDelete.addAll(allAssociationsInProc);

        CompoundCommand cCmd = new CompoundCommand();

        for (Iterator iter = copyOfAllObjectsToMove.iterator(); iter.hasNext();) {

            Object obj = iter.next();
            if (obj instanceof Process) {

                Process processCopy = (Process) obj;

                String processLabel =
                        Xpdl2ModelUtil.getDisplayName(processCopy);

                String uniqueLabelInSet =
                        Xpdl2ModelUtil.getUniqueLabelInSet(processLabel,
                                destinationPackage.getProcesses());

                if (!uniqueLabelInSet.equals(processLabel)) {
                    /*
                     * If process with the same name already exists in the
                     * destination package then provide unique name
                     */
                    cCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    processCopy,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    uniqueLabelInSet));

                    cCmd.append(SetCommand.create(editingDomain,
                            processCopy,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            com.tibco.xpd.ui.util.NameUtil
                                    .getInternalName(uniqueLabelInSet, false)));
                }
                /*
                 * Fix the references which might get broken due to process
                 * refactor
                 */
                fixReferencesCommand(processCopy, oldIdToNewEObjectMap, cCmd);

                /*
                 * Add process to destination package.
                 */
                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE.getPackage_Processes(),
                        processCopy));

            } else if (obj instanceof Pool) {

                Pool pool = (Pool) obj;

                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE.getPackage_Pools(),
                        pool));

            } else if (obj instanceof Artifact) {

                Artifact artifact = (Artifact) obj;

                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                        artifact));

            } else if (obj instanceof Association) {

                Association association = (Association) obj;

                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE.getPackage_Associations(),
                        association));

            } else if (obj instanceof Participant) {

                Participant participant = (Participant) obj;

                String participantLabel =
                        Xpdl2ModelUtil.getDisplayName(participant);

                String uniqueLabelInSet =
                        Xpdl2ModelUtil.getUniqueLabelInSet(participantLabel,
                                destinationPackage.getParticipants());

                if (!uniqueLabelInSet.equals(participantLabel)) {
                    /*
                     * If Participant with the same name already exists in the
                     * destination package then provide unique name
                     */
                    cCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    participant,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    uniqueLabelInSet));

                    cCmd.append(SetCommand.create(editingDomain,
                            participant,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            com.tibco.xpd.ui.util.NameUtil
                                    .getInternalName(uniqueLabelInSet, false)));
                }

                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE
                                .getParticipantsContainer_Participants(),
                        participant));
            } else if (obj instanceof TypeDeclaration) {

                TypeDeclaration typeDeclare = (TypeDeclaration) obj;

                String typeDeclareLabel =
                        Xpdl2ModelUtil.getDisplayName(typeDeclare);

                String uniqueLabelInSet =
                        Xpdl2ModelUtil.getUniqueLabelInSet(typeDeclareLabel,
                                destinationPackage.getTypeDeclarations());

                if (!uniqueLabelInSet.equals(typeDeclareLabel)) {
                    /*
                     * If type declaration with the same name already exists in
                     * the destination package then provide unique name
                     */
                    cCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    typeDeclare,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    uniqueLabelInSet));

                    cCmd.append(SetCommand.create(editingDomain,
                            typeDeclare,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            com.tibco.xpd.ui.util.NameUtil
                                    .getInternalName(uniqueLabelInSet, false)));
                }

                cCmd.append(AddCommand.create(editingDomain,
                        destinationPackage,
                        Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations(),
                        typeDeclare));
            }
        }

        /*
         * Finally delete the elements from the source package.
         */
        cCmd.append(RemoveCommand.create(editingDomain, allEObjectsToDelete));

        return cCmd;
    }

    /**
     * Fixes the references which might break after process refactor.
     * 
     * @param processCopy
     *            the Copied process which will be saved to destination
     * @param oldToNewIdMap
     *            the map which has old EObjects to new EObject ids
     * @param ccmd
     */
    private void fixReferencesCommand(Process processCopy,
            Map<String, EObject> oldToNewIdMap, CompoundCommand ccmd) {
        /*
         * Note: there are two kinds of referneces which need to be fixed. First
         * is the references which break in the process being refactored and
         * second are the references which break in other processes which
         * reference the process being refactored .
         */
        /* Fix references to other Elements which might break */
        fixReferencesToOtherElements(processCopy, ccmd);

        /* Fix references in other elements which might breaak */
        fixReferencesFromOtherElements(processCopy, oldToNewIdMap, ccmd);

    }

    /**
     * Fix references in other elements which might breaak
     * 
     * @param processCopy
     *            the Copied process which will be saved to destination
     * @param oldToNewIdMap
     *            the map which has old EObjects to new EObject ids
     * @param ccmd
     */
    private void fixReferencesFromOtherElements(Process processCopy,
            Map<String, EObject> oldToNewIdMap, CompoundCommand ccmd) {

        /*
         * Use the indexer to get all the activities which reference the process
         * being refactored.
         */
        IndexerItemImpl criteria = new IndexerItemImpl();

        String processId = sourceProcess.getId();

        criteria.set(ActivityInvokingProcessIndexProvider.COLUMN_PROCESS_ID,
                processId);

        Collection<IndexerItem> query =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(ActivityInvokingProcessIndexProvider.ACTIVITY_INVOKING_PROCESS_REFERENCE_INDEXER_ID,
                                criteria);

        for (IndexerItem indexerItem : query) {

            URI uri = URI.createURI(indexerItem.getURI());

            EObject eObjectFrom = ProcessUIUtil.getEObjectFrom(uri);

            if (eObjectFrom instanceof Activity) {
                Activity activity = (Activity) eObjectFrom;

                Implementation impl = activity.getImplementation();

                Package pkg = Xpdl2ModelUtil.getPackage(activity);
                if (impl instanceof SubFlow) {
                    /*
                     * If a Re-usable sub process references the process being
                     * refactored then update the process ID with the new
                     * process id and add Package reference if necessary.
                     */
                    SubFlow subFlow = (SubFlow) impl;

                    ccmd.append(SetCommand.create(editingDomain,
                            subFlow,
                            Xpdl2Package.eINSTANCE.getSubFlow_ProcessId(),
                            processCopy.getId()));

                    boolean shouldAddPackageRef = false;

                    String packageRefId = subFlow.getPackageRefId();
                    /*
                     * Add external package ref only if the process is moved to
                     * different package and the calling sub-process is not
                     * present in the target package.
                     */
                    if (packageRefId == null || packageRefId.isEmpty()) {
                        shouldAddPackageRef = true;

                    } else {

                        if (!pkg.getName().equals(destinationPackage.getName())) {
                            shouldAddPackageRef = true;
                        }
                    }

                    if (shouldAddPackageRef) {
                        /*
                         * add package ref id to sub-process
                         */
                        ccmd.append(SetCommand.create(editingDomain,
                                subFlow,
                                Xpdl2Package.eINSTANCE
                                        .getSubFlow_PackageRefId(),
                                destinationPackage.getName()));

                        EList<ExternalPackage> externalPackages =
                                pkg.getExternalPackages();

                        for (ExternalPackage externalPackage : externalPackages) {
                            if (destinationPackage.getName()
                                    .equals(externalPackage.getHref())) {
                                shouldAddPackageRef = false;
                                break;
                            }
                        }

                        if (shouldAddPackageRef) {
                            /*
                             * Add external package reference only if there is
                             * no external package referernce already present.
                             */
                            ExternalPackage extPackReferences =
                                    createPackageExternalReference(destinationPackage
                                            .getName());

                            ccmd.append(AddCommand.create(editingDomain,
                                    pkg,
                                    Xpdl2Package.eINSTANCE
                                            .getPackage_ExternalPackages(),
                                    extPackReferences));
                        }
                    } else {
                        ccmd.append(SetCommand.create(editingDomain,
                                subFlow,
                                Xpdl2Package.eINSTANCE
                                        .getSubFlow_PackageRefId(),
                                SetCommand.UNSET_VALUE));
                    }

                } else if (impl instanceof Task) {

                    Task task = (Task) impl;
                    TaskSend taskSend = task.getTaskSend();

                    if (taskSend != null) {
                        /*
                         * If a send task references the process being
                         * refactored then update the process ID with the new
                         * process id and add Package reference if necessary.
                         */
                        Object otherElement =
                                Xpdl2ModelUtil
                                        .getOtherElement(taskSend,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_BusinessProcess());
                        if (otherElement instanceof BusinessProcess) {

                            BusinessProcess sendTaskBusinessProc =
                                    (BusinessProcess) otherElement;

                            ccmd.append(SetCommand.create(editingDomain,
                                    sendTaskBusinessProc,
                                    XpdExtensionPackage.eINSTANCE
                                            .getBusinessProcess_ProcessId(),
                                    processCopy.getId()));

                            String activityId =
                                    sendTaskBusinessProc.getActivityId();
                            /*
                             * get the Id of the new catch message event
                             */
                            EObject catchMsgEvent =
                                    oldToNewIdMap.get(activityId);

                            if (catchMsgEvent instanceof Activity) {

                                ccmd.append(SetCommand
                                        .create(editingDomain,
                                                sendTaskBusinessProc,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getBusinessProcess_ActivityId(),
                                                ((Activity) catchMsgEvent)
                                                        .getId()));

                            }

                            boolean shouldAddPackageRef = false;

                            String packageRefId =
                                    sendTaskBusinessProc.getPackageRefId();
                            /*
                             * Add external package ref only if the process is
                             * moved to different package and the invoking send
                             * task is not present in the target package.
                             */
                            if (packageRefId == null || packageRefId.isEmpty()) {
                                shouldAddPackageRef = true;

                            } else {

                                if (!pkg.getName()
                                        .equals(destinationPackage.getName())) {
                                    shouldAddPackageRef = true;
                                }
                            }

                            if (shouldAddPackageRef) {

                                ccmd.append(SetCommand
                                        .create(editingDomain,
                                                sendTaskBusinessProc,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getBusinessProcess_PackageRefId(),
                                                destinationPackage.getName()));

                                EList<ExternalPackage> externalPackages =
                                        pkg.getExternalPackages();

                                for (ExternalPackage externalPackage : externalPackages) {
                                    if (destinationPackage.getName()
                                            .equals(externalPackage.getHref())) {
                                        shouldAddPackageRef = false;
                                        break;
                                    }
                                }
                                /*
                                 * Add external package reference only if there
                                 * is no external package referernce already
                                 * present.
                                 */
                                if (shouldAddPackageRef) {
                                    ExternalPackage extPackReferences =
                                            createPackageExternalReference(destinationPackage
                                                    .getName());

                                    ccmd.append(AddCommand
                                            .create(editingDomain,
                                                    pkg,
                                                    Xpdl2Package.eINSTANCE
                                                            .getPackage_ExternalPackages(),
                                                    extPackReferences));
                                }
                            } else {
                                ccmd.append(SetCommand
                                        .create(editingDomain,
                                                sendTaskBusinessProc,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getBusinessProcess_PackageRefId(),
                                                SetCommand.UNSET_VALUE));
                            }
                            /*
                             * For Send tasks fix the Web-service operations and
                             * the PortType operations as the wsdl might be
                             * re-generated after the invoked process is moved
                             * to a different package.
                             */
                            WebServiceOperation webServiceOperation =
                                    taskSend.getWebServiceOperation();

                            if (webServiceOperation != null) {

                                Service service =
                                        webServiceOperation.getService();

                                if (service != null) {

                                    EndPoint endPoint = service.getEndPoint();

                                    if (endPoint != null) {

                                        ExternalReference externalReference =
                                                endPoint.getExternalReference();

                                        if (externalReference != null) {

                                            String location =
                                                    externalReference
                                                            .getLocation();
                                            /*
                                             * Update the external location ONLY
                                             * if the old wsdl name matches with
                                             * the old package name. This
                                             * indicates that the wsdl was
                                             * generated from the package and
                                             * will be re-generated after move
                                             * to different package.
                                             */
                                            if (location != null
                                                    && location
                                                            .equals(sourceProcess
                                                                    .getPackage()
                                                                    .getName()
                                                                    + WSDL_FILE_EXTENSION)) {

                                                ccmd.append(SetCommand
                                                        .create(editingDomain,
                                                                externalReference,
                                                                Xpdl2Package.eINSTANCE
                                                                        .getExternalReference_Location(),
                                                                destinationPackage
                                                                        .getName()
                                                                        + WSDL_FILE_EXTENSION));
                                            }
                                        }
                                    }
                                }
                            }

                            Object pto =
                                    Xpdl2ModelUtil
                                            .getOtherElement(taskSend,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_PortTypeOperation());

                            if (pto instanceof PortTypeOperation) {

                                PortTypeOperation portTypeOp =
                                        (PortTypeOperation) pto;

                                ExternalReference xRef =
                                        portTypeOp.getExternalReference();

                                if (xRef != null) {

                                    String location2 = xRef.getLocation();
                                    /*
                                     * Update the external location ONLY if the
                                     * old wsdl name matches with the old
                                     * package name. This indicates that the
                                     * wsdl was generated from the package and
                                     * will be re-generated after move to
                                     * different package.
                                     */
                                    if (location2 != null
                                            && location2.equals(sourceProcess
                                                    .getPackage().getName()
                                                    + WSDL_FILE_EXTENSION)) {

                                        ccmd.append(SetCommand
                                                .create(editingDomain,
                                                        xRef,
                                                        Xpdl2Package.eINSTANCE
                                                                .getExternalReference_Location(),
                                                        destinationPackage
                                                                .getName()
                                                                + WSDL_FILE_EXTENSION));
                                    }
                                }
                            }
                        }
                    } else if (task.getTaskUser() != null) {

                        FormImplementation userTaskFormImplementation =
                                TaskObjectUtil
                                        .getUserTaskFormImplementation(activity);

                        if (userTaskFormImplementation != null
                                && FormImplementationType.PAGEFLOW
                                        .equals(userTaskFormImplementation
                                                .getFormType())) {
                            /*
                             * If the user task form type is pageflow and the
                             * pageflow is refactored then update the form URI
                             * accordingly.
                             */
                            String formUri =
                                    destinationPackage.getName()
                                            + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT
                                            + "#" + processCopy.getId(); //$NON-NLS-1$

                            ccmd.append(SetCommand.create(editingDomain,
                                    userTaskFormImplementation,
                                    XpdExtensionPackage.eINSTANCE
                                            .getFormImplementation_FormURI(),
                                    formUri));
                        }
                    }
                }
            }
        }
    }

    /**
     * Fix the references to other elements which might break.
     * 
     * @param processCopy
     *            the Copied process which will be saved to destination
     * 
     * @param ccmd
     */
    private void fixReferencesToOtherElements(Process processCopy,
            CompoundCommand ccmd) {

        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(processCopy);

        Set<ExternalPackage> extPackReferences = new HashSet<ExternalPackage>();

        for (Activity activity : allActivitiesInProc) {

            Implementation impl = activity.getImplementation();

            if (impl instanceof SubFlow) {

                SubFlow subFlow = (SubFlow) impl;

                String packageRefId = subFlow.getPackageRefId();

                ExternalPackage externalPackageRef = null;

                if (packageRefId == null || packageRefId.isEmpty()) {
                    /*
                     * means that the referenced process is in the source xpdl
                     * itself, hence we need to set the Package ref id to the
                     * sub flow and also add external package reference
                     */
                    externalPackageRef =
                            createPackageExternalReference(sourceProcess
                                    .getPackage().getName());

                    subFlow.setPackageRefId(sourceProcess.getPackage()
                            .getName());
                } else if (!packageRefId.equals(destinationPackage.getName())) {
                    /*
                     * means that the referenced process is in some other xpdl,
                     * now we have its package name so we only need to add
                     * external package reference.
                     */
                    externalPackageRef =
                            createPackageExternalReference(packageRefId);
                } else if (packageRefId.equals(destinationPackage.getName())) {
                    /*
                     * If the process is moved to the package where the
                     * referenced sub proces is present then we no more require
                     * the package ref id.
                     */
                    subFlow.setPackageRefId(null);
                }

                if (externalPackageRef != null) {
                    extPackReferences.add(externalPackageRef);
                }

            } else if (impl instanceof Task) {

                Task task = (Task) impl;
                TaskSend taskSend = task.getTaskSend();

                if (taskSend != null) {

                    Object otherElement =
                            Xpdl2ModelUtil.getOtherElement(taskSend,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_BusinessProcess());

                    if (otherElement instanceof BusinessProcess) {

                        BusinessProcess sendTaskBusinessProc =
                                (BusinessProcess) otherElement;

                        String packageRefId =
                                sendTaskBusinessProc.getPackageRefId();

                        ExternalPackage externalPackageRef = null;

                        if (packageRefId == null || packageRefId.isEmpty()) {
                            /*
                             * means that the referenced business process is in
                             * the source xpdl itself, hence we need to set the
                             * Package ref id to the sub flow and also add
                             * external package reference
                             */
                            externalPackageRef =
                                    createPackageExternalReference(sourceProcess
                                            .getPackage().getName());

                            sendTaskBusinessProc.setPackageRefId(sourceProcess
                                    .getPackage().getName());
                        } else if (!packageRefId.equals(destinationPackage
                                .getName())) {
                            /*
                             * means that the referenced business process is in
                             * some other xpdl, now we have its package name so
                             * we only need to add external package reference.
                             */
                            externalPackageRef =
                                    createPackageExternalReference(packageRefId);
                        } else if (packageRefId.equals(destinationPackage
                                .getName())) {
                            /*
                             * If the process is moved to the package where the
                             * referenced business process is present then we no
                             * more require the package ref id.
                             */
                            sendTaskBusinessProc.setPackageRefId(null);
                        }

                        if (externalPackageRef != null) {
                            extPackReferences.add(externalPackageRef);
                        }
                    }
                }
            }
        }

        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(processCopy,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementedInterface());

        if (otherElement instanceof ImplementedInterface) {
            /* go ahead only if the process implements an interface */
            ImplementedInterface implementedIfc =
                    (ImplementedInterface) otherElement;

            String packageRefId = implementedIfc.getPackageRef();

            ExternalPackage externalPackageRef = null;

            if (packageRefId == null || packageRefId.isEmpty()) {
                /*
                 * If the package ref id is not present , it indicates that the
                 * process and interface are in the same package. Hence post
                 * move a reference needs to be added to the source package.
                 */
                externalPackageRef =
                        createPackageExternalReference(sourceProcess
                                .getPackage().getName());

                implementedIfc.setPackageRef(sourceProcess.getPackage()
                        .getName());
            } else if (!packageRefId.equals(destinationPackage.getName())) {
                /*
                 * means that the referenced interface is in some other xpdl,
                 * now we have its package name so we only need to add external
                 * package reference.
                 */
                externalPackageRef =
                        createPackageExternalReference(packageRefId);
            } else if (packageRefId.equals(destinationPackage.getName())) {
                /*
                 * If the process is moved to the package where the referenced
                 * interface is present then we no more require the package ref
                 * id.
                 */
                implementedIfc.setPackageRef(null);
            }

            if (externalPackageRef != null) {
                extPackReferences.add(externalPackageRef);
            }
        }

        if (!extPackReferences.isEmpty()) {
            /*
             * Finally add all the necessary external package references to the
             * destination package.
             */
            ccmd.append(AddCommand.create(editingDomain,
                    destinationPackage,
                    Xpdl2Package.eINSTANCE.getPackage_ExternalPackages(),
                    extPackReferences));
        }
    }

    /**
     * Creates and returns External Package reference
     * 
     * @param packageName
     *            the package name to which external package reference is to be
     *            addded
     * @return the External package reference
     */
    private ExternalPackage createPackageExternalReference(String packageName) {
        ExternalPackage externalPackage =
                Xpdl2Factory.eINSTANCE.createExternalPackage();
        externalPackage.setHref(packageName);

        ExtendedAttribute attrib =
                Xpdl2Factory.eINSTANCE.createExtendedAttribute();
        attrib.setName("location"); //$NON-NLS-1$
        attrib.setValue("/" + packageName + Xpdl2ResourcesConsts.DEFAULT_PACKAGE_FILENAME_EXT); //$NON-NLS-1$

        externalPackage.getExtendedAttributes().add(attrib);
        return externalPackage;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {
        return null;
    }

    /**
     * Gets the WorkingCopy for the passed object
     * 
     * @return
     */
    protected WorkingCopy getWorkingCopy(EObject object) {
        return WorkingCopyUtil.getWorkingCopyFor(object);
    }
}
