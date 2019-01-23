/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.CorrelationDataGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessOrgModelUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskObjectUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.FormImplementation;
import com.tibco.xpd.xpdExtension.FormImplementationType;
import com.tibco.xpd.xpdExtension.ImplementedInterface;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.FormalParametersContainer;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantsContainer;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility that is used by the Project Explorer Actions
 * 
 * @author njpatel
 * 
 */
public class ActionUtil {

    /**
     * BW service identifier.
     * 
     * @deprecated Use {@link TaskImplementationTypeDefinitions#BW_SERVICE}
     *             instead
     */
    @Deprecated
    public static final String BW_SERVICE =
            TaskImplementationTypeDefinitions.BW_SERVICE;

    /**
     * Web service identifier.
     * 
     * @deprecated Use {@link TaskImplementationTypeDefinitions#WEB_SERVICE}
     *             instead
     */
    @Deprecated
    public static final String WEB_SERVICE =
            TaskImplementationTypeDefinitions.WEB_SERVICE;

    /**
     * Web service identifier of version 2.0.
     * 
     * @deprecated Use
     *             {@link TaskImplementationTypeDefinitions#WEB_SERVICE_V2_0}
     *             instead
     */
    @Deprecated
    public static final String WEB_SERVICE_V2_0 =
            TaskImplementationTypeDefinitions.WEB_SERVICE_V2_0;

    /**
     * Java service
     * 
     * @deprecated Use {@link TaskImplementationTypeDefinitions#JAVA_SERVICE}
     *             instead
     */
    @Deprecated
    public static final String JAVA_SERVICE =
            TaskImplementationTypeDefinitions.JAVA_SERVICE;

    /**
     * Database
     * 
     * @deprecated Use
     *             {@link TaskImplementationTypeDefinitions#DATABASE_SERVICE}
     *             instead
     */
    @Deprecated
    public static final String DATABASE_SERVICE =
            TaskImplementationTypeDefinitions.DATABASE_SERVICE;

    /**
     * E-Mail
     * 
     * @deprecated Use {@link TaskImplementationTypeDefinitions#EMAIL_SERVICE}
     *             instead
     */
    @Deprecated
    public static final String EMAIL_SERVICE =
            TaskImplementationTypeDefinitions.EMAIL_SERVICE;

    /** Invoke Business Process */
    // XPD-288
    public static final String INVOKE_BUSINESS_PROCESS =
            TaskImplementationTypeDefinitions.INVOKE_BUSINESS_PROCESS;

    /**
     * XPD-996: this qualifier would be used in creating shared resource uri for
     * system participants
     */
    public static final String QUALIFIER = "qualifier"; //$NON-NLS-1$

    /**
     * Check if all items in the list are of the same type (instance of the same
     * class)
     * 
     * @param items
     * @return <code>true</code> if all objects of same type, <code>false</code>
     *         otherwise.
     */
    public static boolean allObjectsOfSameType(List<?> items) {
        boolean ret = true;

        if (items != null && items.size() > 0) {
            // Get the type of the first item
            Object firstItem = items.get(0);
            Class<?> clazz = firstItem.getClass();

            // Compare each item in the list to the class of the first object in
            // the list
            for (Iterator<?> iter = items.iterator(); iter.hasNext() && ret;) {
                Object nextItem = iter.next();
                ret = clazz.equals(nextItem.getClass());
                if (!ret) {
                    // Allow processes and process interfaces as being same type
                    if ((firstItem instanceof Process && nextItem instanceof ProcessInterface)
                            || (firstItem instanceof ProcessInterface && nextItem instanceof Process)) {
                        ret = true;
                    }
                }
            }

        } else {
            ret = false;
        }

        return ret;
    }

    /**
     * Checks if there is a logical node (of type NavigatorGroup) in the
     * selection
     * 
     * @param items
     * @return <code>true</code> if logical node found, <code>false</code>
     *         otherwise
     */
    public static boolean isLogicalNodeInList(List<?> items) {
        boolean bRet = false;

        if (items != null && items.size() > 0) {

            for (Iterator<?> iter = items.iterator(); iter.hasNext() && !bRet;) {
                if (iter.next() instanceof INavigatorGroup) {
                    bRet = true;
                }
            }

        }

        return bRet;
    }

    /**
     * This method will go thru the list and check whether the editing domain of
     * each element in the list is same. This would ensure that all the objects
     * of the list belong to the same parent.
     * 
     * @param eObjectList
     * @return <code>true</code> if all objects have the same parent,
     *         <code>false</code> otherwise
     * 
     */
    public static boolean eObjectsWithSameParent(List<?> eObjectList) {
        boolean bRet = true;

        if (eObjectList != null && eObjectList.size() > 0
                && eObjectList instanceof EObject) {
            Iterator<?> iter = eObjectList.iterator();
            EditingDomain editingDomain = null;
            if (iter.hasNext()) {
                EObject eObject = (EObject) iter.next();
                editingDomain = WorkingCopyUtil.getEditingDomain(eObject);

                iter = eObjectList.iterator();
                while (iter.hasNext() && bRet) {
                    Object next = iter.next();
                    if (next instanceof EObject) {
                        EObject tempEObject = (EObject) next;
                        EditingDomain tempEditingDomain =
                                WorkingCopyUtil.getEditingDomain(tempEObject);
                        if (tempEditingDomain != editingDomain) {
                            bRet = false;
                        }
                    } else {
                        bRet = false;
                    }
                }
            } else {
                bRet = false;
            }
        }

        return bRet;
    }

    public static boolean allEObjects(List list) {
        if (list != null && list.size() > 0) {
            for (Object o : list) {
                if (!(o instanceof EObject)) {
                    return false;
                }
            }
            return true;
        }
        return false;// empty list
    }

    /**
     * Search the given list of objects for an object of the given class type
     * 
     * @param items
     * @param itemTypeToSearch
     * @return <code>true</code> if list contains an item of the given class
     *         type, <code>false</code> otherwise
     */
    public static boolean isClassTypeInList(List items, Class itemTypeToSearch) {
        boolean bRet = false;

        if (items != null && itemTypeToSearch != null) {
            for (Iterator iter = items.iterator(); iter.hasNext() && !bRet;) {
                bRet = itemTypeToSearch.isInstance(iter.next());
            }
        }

        return bRet;
    }

    /**
     * Check if all resources in the list are from the same project
     * 
     * @param items
     * @return The parent IProject if all items are from the same project,
     *         <code>null</code> otherwise.
     */
    public static IProject allResourcesFromSameProject(List items) {
        IProject project = null;

        if (items != null && items.size() > 0) {
            Iterator iter = items.iterator();
            Object next = iter.next();
            IProject nextProject = null;
            IResource resource = null;

            // Check that the first item is an IResource and get it's project
            resource = (IResource) adapt(next, IResource.class);

            if (resource != null) {
                project = resource.getProject();
            }
            // If we have a project then check all the other items
            for (; iter.hasNext() && project != null;) {
                resource = (IResource) adapt(iter.next(), IResource.class);

                if (resource != null) {
                    // If the project don't match then reset project or no
                    // project
                    // then reset project value
                    nextProject = resource.getProject();

                    if (nextProject == null
                            || (nextProject != null && !nextProject
                                    .equals(project))) {
                        project = null;
                    }
                }
            }
        }

        return project;
    }

    /**
     * Adapt the given object to the given class
     * 
     * @param objToAdapt
     * @param adaptToClass
     * @return The adapted object, if failed returns <code>null</code>
     */
    private static Object adapt(final Object objToAdapt,
            final Class adaptToClass) {

        if (objToAdapt != null && adaptToClass != null) {
            // Check if the object is an instanceof the class
            if (adaptToClass.isInstance(objToAdapt)) {
                return objToAdapt;
            }

            // Check if the object can be adapted to the class
            if (objToAdapt instanceof IAdaptable) {
                return ((IAdaptable) objToAdapt).getAdapter(adaptToClass);
            }
        }

        return null;
    }

    /**
     * Return the actual list of elements required for the given set of selected
     * eobjects.
     * <p>
     * The actual list may need extra supporting elements (such as Pools for the
     * processes etc)
     * 
     * @param editingDomain
     * 
     * @param objectList
     * @param wantReferencedProjectEntries
     * @return
     */
    public static Collection<EObject> getCopyElements(
            EditingDomain editingDomain, Collection objectList,
            boolean wantReferencedProjectEntries) {
        Package srcPackage;
        Object firstEl = objectList.iterator().next();
        if (firstEl instanceof Package) {
            srcPackage = (Package) firstEl;
        } else {
            srcPackage = Xpdl2ModelUtil.getPackage((EObject) firstEl);
        }

        // Final copy list.
        Set<EObject> copyList = new HashSet<EObject>(objectList.size());

        // List of package participants referenced by objects in processes.
        HashSet<Participant> extraPartics = new HashSet<Participant>();

        // List of package data fields referenced by objects in processes
        // AND/OR formal parameters associated with selected process interface
        // methods.
        HashSet<ProcessRelevantData> extraDataFieldsAndParams =
                new HashSet<ProcessRelevantData>();

        // List of Package Data Type Declarations ref'd by data fields in copy
        // list.
        HashSet<TypeDeclaration> typeDeclarations =
                new HashSet<TypeDeclaration>();

        HashSet<ProcessRelevantData> allFieldsAndParams =
                new HashSet<ProcessRelevantData>();

        for (Iterator iter = objectList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            // Keep list of type declarations separate so that we can add any
            // ref'd ones and not duplicate them.
            if (obj instanceof TypeDeclaration) {
                typeDeclarations.add((TypeDeclaration) obj);
                continue;
            } else {
                copyList.add((EObject) obj);
            }

            if (obj instanceof Process) {
                Process process = (Process) obj;

                String procId = process.getId();

                Package pkg = process.getPackage();
                if (pkg != null) {

                    //
                    // Find and add pools for process.
                    for (Iterator poolIter = pkg.getPools().iterator(); poolIter
                            .hasNext();) {
                        Pool pool = (Pool) poolIter.next();

                        if (procId.equals(pool.getProcessId())) {
                            // Add the pool to list of objects to copy.
                            copyList.add(pool);
                        }
                    } // Next pool

                    //
                    // Add artifacts in process.
                    copyList.addAll(Xpdl2ModelUtil
                            .getAllArtifactsInProcess(process));

                    //
                    // And associations
                    copyList.addAll(Xpdl2ModelUtil
                            .getAllAssociationsInProc(process));

                    //
                    // And message flows
                    copyList.addAll(Xpdl2ModelUtil
                            .getAllMessageFlowsInProc(process));

                    //
                    // And package-level participants
                    getReferencedPkgParticipants(process, extraPartics);

                    //
                    // And package-data fields participants
                    getReferencedPkgDataFields(process,
                            extraDataFieldsAndParams);

                    //
                    // Keep a list of all fields / formal params
                    allFieldsAndParams.addAll(process.getDataFields());
                    allFieldsAndParams.addAll(process.getFormalParameters());

                    // need to add activity data fields.
                    for (Activity activity : Xpdl2ModelUtil
                            .getAllActivitiesInProc(process)) {
                        allFieldsAndParams.addAll(activity.getDataFields());
                    }

                }

            } else if (obj instanceof ProcessRelevantData) {
                allFieldsAndParams.add((ProcessRelevantData) obj);
            } else if (obj instanceof ProcessInterface) {
                allFieldsAndParams.addAll(((ProcessInterface) obj)
                        .getFormalParameters());

            } else if (obj instanceof StartMethod) {
                // Copy any formal parameters associated with process interface
                // start method.
                List<FormalParameter> assocParams =
                        ProcessInterfaceUtil
                                .getStartMethodAssociatedFormalParameters((StartMethod) obj);
                if (assocParams != null && assocParams.size() > 0) {
                    extraDataFieldsAndParams.addAll(assocParams);
                }
                copyList.addAll(((StartMethod) obj).getErrorMethods());

            } else if (obj instanceof IntermediateMethod) {
                // Copy any formal parameters associated with process interface
                // start method.
                List<FormalParameter> assocParams =
                        ProcessInterfaceUtil
                                .getIntermediateMethodAssociatedFormalParameters((IntermediateMethod) obj);
                if (assocParams != null && assocParams.size() > 0) {
                    extraDataFieldsAndParams.addAll(assocParams);
                }
                copyList.addAll(((IntermediateMethod) obj).getErrorMethods());

            }

        } // Next main selected object to copy.

        // Add the extra package level participants and data fields.
        copyList.addAll(extraPartics);
        copyList.addAll(extraDataFieldsAndParams);

        // Go thru list of all fields within selection (or selected
        // processes) + any referenced package level fields and add the declared
        // types that these fields/params may reference.
        allFieldsAndParams.addAll(extraDataFieldsAndParams);
        getRefdTypeDeclarations(srcPackage,
                allFieldsAndParams,
                typeDeclarations);

        // Add type declarations referenced by the data fields we are copying.
        copyList.addAll(typeDeclarations);

        /**
         * go thru copyList and for each process relevant data check for
         * references to other process relevant data and participants and add
         * those to copyList.
         */
        for (EObject object : copyList) {
            if (object instanceof ProcessRelevantData) {
                ProcessRelevantData processRelevantData =
                        (ProcessRelevantData) object;
                Xpdl2FieldOrParamResolver resolver =
                        new Xpdl2FieldOrParamResolver(processRelevantData);
                Set<ProcessRelevantData> dataReferences =
                        resolver.getDataReferences(processRelevantData);
                if (dataReferences.size() > 0) {
                    copyList.addAll(dataReferences);
                }
                Set<Participant> participantDataReferences =
                        Xpdl2ParticipantReferenceResolver
                                .getParticipantDataReferences(processRelevantData);
                if (participantDataReferences.size() > 0) {
                    copyList.addAll(participantDataReferences);
                }
            }
        }
        // Create copies of the objects
        Command cmd = CopyCommand.create(editingDomain, copyList);
        if (!cmd.canExecute()) {
            return null;
        }

        cmd.execute();
        Collection copied = cmd.getResult();

        //
        // Do any fixing of copied model elements
        postProcessModelCopies(srcPackage, copied);
        // XPD-3033 check requirement for new project references and ask user
        // add required project references for data Fields with ext ref.
        if (wantReferencedProjectEntries) {
            copied.addAll(getExternalProjectReferencesForObjects(copyList,
                    WorkingCopyUtil.getProjectFor(srcPackage)));
        }
        return copied;
    }

    /**
     * Checks each object of the passed collection for possible External
     * reference , collects and returns all such references to external
     * projects.
     * 
     * @param objects
     *            , objects to be checked for External Reference.
     * @return
     */
    // XPD-3033 check requirement for new project references and ask user
    public static Collection<ProjectReference> getExternalProjectReferencesForObjects(
            Collection objects, IProject sourceProject) {

        Collection<ProjectReference> referencedProjects =
                new ArrayList<ProjectReference>();
        for (Object object : objects) {
            if (object instanceof EObject) {
                EObject eObject = (EObject) object;
                // check if the eObject has any external reference.
                ExternalReference extRef = null;
                String specialFolderType = null;
                if (eObject instanceof ProcessRelevantData) { // ProcessRelevantData
                    ProcessRelevantData procRelData =
                            (ProcessRelevantData) eObject;
                    if (procRelData.getDataType() instanceof ExternalReference) {
                        extRef = (ExternalReference) procRelData.getDataType();
                        specialFolderType =
                                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;
                    }
                } else if (eObject instanceof TypeDeclaration) {// TypeDeclaration
                    extRef = ((TypeDeclaration) eObject).getExternalReference();
                    specialFolderType =
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND;

                } else if (eObject instanceof Participant) {// Participant
                    extRef = ((Participant) eObject).getExternalReference();
                    specialFolderType =
                            ProcessOrgModelUtil.OM_SPECIAL_FOLDER_KIND;
                } else if (eObject instanceof Process) {// Process
                    Process process = (Process) eObject;
                    EObject implementedProcessInterface =
                            ProcessInterfaceUtil
                                    .getImplementedProcessInterface(process);
                    // if project implements a process interface , and it is in
                    // another project add reference
                    if (implementedProcessInterface != null) {
                        addProjectReference(referencedProjects,
                                implementedProcessInterface);
                    }
                    // check all activities of the process for possible external
                    // reference

                    referencedProjects
                            .addAll(getExternalProjectReferencesForObjects(Xpdl2ModelUtil
                                    .getAllActivitiesInProc(process),
                                    sourceProject));

                } else if (eObject instanceof Activity) {
                    Activity activity = (Activity) eObject;
                    // Check for External SubProcess in Another Project
                    if (activity.getImplementation() instanceof SubFlow) {
                        SubFlow subFlow =
                                (SubFlow) activity.getImplementation();
                        String subProcID = subFlow.getProcessId();
                        EObject subProcessOrInterface =
                                Xpdl2WorkingCopyImpl.locateProcess(subProcID);
                        if (subProcessOrInterface == null) {
                            subProcessOrInterface =
                                    Xpdl2WorkingCopyImpl
                                            .locateProcessInterface(subProcID);
                        }
                        if (subProcessOrInterface != null) {
                            addProjectReference(referencedProjects,
                                    subProcessOrInterface);
                        }
                        /*
                         * User Task - check Form location in another Project
                         */
                    } else if (activity.getImplementation() instanceof Task
                            && ((Task) activity.getImplementation())
                                    .getTaskUser() != null) {
                        /*
                         * Check the the selected form implementation for
                         * external reference
                         */
                        addFormProjectReferences(activity,
                                referencedProjects,
                                sourceProject);
                    } else // Check for Service defined in another Project
                    if (Xpdl2ModelUtil.getWebServiceOperation(activity) != null) {
                        WebServiceOperation webOperation =
                                Xpdl2ModelUtil.getWebServiceOperation(activity);
                        extRef =
                                webOperation.getService().getEndPoint()
                                        .getExternalReference();
                        specialFolderType =
                                WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND;
                    }

                }
                if (extRef != null && specialFolderType != null) {
                    addProjectForExternalReference(sourceProject,
                            referencedProjects,
                            extRef,
                            specialFolderType);
                }
            }
        }
        return referencedProjects;

    }

    /**
     * @param sourceProject
     * @param projectReferenceList
     * @param extRef
     * @param specialFolderType
     * @param extRefProject
     */
    public static void addProjectForExternalReference(IProject sourceProject,
            Collection<ProjectReference> projectReferenceList,
            ExternalReference extRef, String specialFolderType) {
        IProject extRefProject = null;
        // get the Resource for the external reference.
        if (extRef != null && extRef.getLocation() != null) {
            IFile file =
                    SpecialFolderUtil
                            .resolveSpecialFolderRelativePath(sourceProject,
                                    specialFolderType,
                                    extRef.getLocation(),
                                    true);
            if (file != null) {
                addProjectReference(projectReferenceList, file);
            }
        }

    }

    /**
     * Checks the location of form for the given Activity [forms are applicable
     * to User Task only] adds to the list if the form location is another
     * project.
     * 
     * @param srcProcess
     * @param act
     * @param projectReferencesList
     * @param sourceProject
     */
    public static void addFormProjectReferences(Activity act,
            Collection<ProjectReference> projectReferencesList,
            IProject sourceProject) {
        FormImplementation formImpl =
                TaskObjectUtil.getUserTaskFormImplementation(act);
        // formImpl will be null if it is not a User Task Activity.

        if (formImpl != null) {
            if (FormImplementationType.FORM.equals(formImpl.getFormType())) {
                if (!(formImpl.getFormURI() == null || formImpl.getFormURI()
                        .length() == 0)) {
                    IFile formFile =
                            TaskObjectUtil.getUserTaskFormFile(sourceProject,
                                    act);
                    if (formFile != null && formFile.exists()) {
                        addProjectReference(projectReferencesList, formFile);
                    }
                }

            } else if (FormImplementationType.PAGEFLOW.equals(formImpl
                    .getFormType())) {
                if (!(formImpl.getFormURI() == null || formImpl.getFormURI()
                        .length() == 0)) {
                    Process pageflowProcess =
                            TaskObjectUtil.getUserTaskPageFlowProcess(act,
                                    sourceProject);
                    if (pageflowProcess != null) {
                        addProjectReference(projectReferencesList,
                                pageflowProcess);
                    }
                }

            }
        }

    }

    /**
     * Given a list of all data fields / formal parameters and the existing set
     * of type declarations to be copied, add all type declarations referenced
     * from fields/params and the type declarations them selves.
     * 
     * @param srcPackage
     * 
     * @param allFieldsAndParams
     * @param typeDeclarations
     * @return
     */
    private static void getRefdTypeDeclarations(Package srcPackage,
            HashSet<ProcessRelevantData> allFieldsAndParams,
            HashSet<TypeDeclaration> typeDeclarations) {

        // Add type declarations refd by fields/params first.
        for (Iterator iter = allFieldsAndParams.iterator(); iter.hasNext();) {
            ProcessRelevantData data = (ProcessRelevantData) iter.next();

            if (data.getDataType() instanceof DeclaredType) {
                DeclaredType type = (DeclaredType) data.getDataType();

                String typeId = type.getTypeDeclarationId();
                if (typeId != null) {
                    TypeDeclaration td = srcPackage.getTypeDeclaration(typeId);

                    if (td != null) {
                        typeDeclarations.add(td);
                    }
                }
            }
        }

        // Now check and add type declarations referenced from our type
        // declarations.
        // Basically just go round until the size of the list does n't change
        // (basically this will handle the nesting of types).
        boolean someAdded = false;

        do {
            someAdded = false;

            for (Iterator iter = typeDeclarations.iterator(); iter.hasNext();) {
                TypeDeclaration chkType = (TypeDeclaration) iter.next();

                DeclaredType refType = chkType.getDeclaredType();
                if (refType != null) {
                    // the type we're checking references another type so see if
                    // it's already in list.
                    String typeId = refType.getTypeDeclarationId();
                    if (typeId != null) {
                        TypeDeclaration td =
                                srcPackage.getTypeDeclaration(typeId);

                        if (td != null) {
                            if (!typeDeclarations.contains(td)) {
                                // Don't have this type so add it, break out and
                                // reprocess (until we don't add anymore) - we
                                // could be more complex and efficient about
                                // this but number of type declarations not
                                // likely to be in the 100s and 1000s.
                                typeDeclarations.add(td);
                                someAdded = true;
                                break;
                            }
                        }
                    }

                }

            }

        } while (someAdded);

        return;
    }

    private static void getReferencedPkgDataFields(Process process,
            HashSet<ProcessRelevantData> extraDataFields) {
        Package pkg = process.getPackage();

        EList pkgFields = pkg.getDataFields();

        if (pkgFields.size() > 0) {
            // Use a data field resolver to help us with the chores.
            HashSet<ProcessRelevantData> dataSet =
                    new HashSet<ProcessRelevantData>(pkgFields.size());

            for (Iterator iter = pkgFields.iterator(); iter.hasNext();) {
                DataField df = (DataField) iter.next();
                dataSet.add(df);
            }

            Xpdl2FieldOrParamResolver resolver =
                    new Xpdl2FieldOrParamResolver(dataSet);

            // check for refs in activities.
            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity act : acts) {
                extraDataFields.addAll(resolver.getDataReferences(act));
            }

            // check for refs in transitions.
            Collection<Transition> trans =
                    Xpdl2ModelUtil.getAllTransitionsInProc(process);

            for (Transition tran : trans) {
                extraDataFields.addAll(resolver.getDataReferences(tran));
            }

        }

        return;
    }

    private static void getReferencedPkgParticipants(Process process,
            HashSet<Participant> extraPartics) {
        Package pkg = process.getPackage();

        EList pkgPartics = pkg.getParticipants();

        if (pkgPartics.size() > 0) {
            // Participants only referenced in activity performers.
            Collection<Activity> acts =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Iterator iter = acts.iterator(); iter.hasNext();) {
                Activity act = (Activity) iter.next();

                EList performers = act.getPerformerList();
                for (Iterator performerIter = performers.iterator(); performerIter
                        .hasNext();) {
                    Performer performer = (Performer) performerIter.next();

                    Participant partic =
                            pkg.getParticipant(performer.getValue());

                    if (partic != null) {
                        extraPartics.add(partic);
                    }
                }

            }
        }

        return;
    }

    /**
     * Perform any last minute maintenance / repair of the copied model objects.
     * 
     * @param srcPackage
     * @param copyList
     */
    private static void postProcessModelCopies(Package srcPackage,
            Collection copyList) {
        Set<String> copyProcessAndInterfaceIds = new HashSet<String>();

        for (Object copyObj : copyList) {
            if (copyObj instanceof Process) {
                copyProcessAndInterfaceIds.add(((Process) copyObj).getId());
            } else if (copyObj instanceof ProcessInterface) {
                copyProcessAndInterfaceIds.add(((ProcessInterface) copyObj)
                        .getId());
            }
        }

        // Keep a track of references to external packages.
        Set<ExternalPackage> refdExtPkgs = new HashSet<ExternalPackage>();

        // This will be created if we are copying call to process in this pkg
        // without the process itself
        ExternalPackage addSrcExtPkg = null;

        EList extPkgs = srcPackage.getExternalPackages();

        // Build a quick look up of process and interface ids.
        Set<String> allProcAndIfcIds = new HashSet<String>();

        ProcessInterfaces procIfcContainer =
                (ProcessInterfaces) Xpdl2ModelUtil.getOtherElement(srcPackage,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessInterfaces());

        if (procIfcContainer != null) {
            for (Iterator iterator =
                    procIfcContainer.getProcessInterface().iterator(); iterator
                    .hasNext();) {
                ProcessInterface procIfrc = (ProcessInterface) iterator.next();
                allProcAndIfcIds.add(procIfrc.getId());
            }
        }

        for (Iterator iterator = srcPackage.getProcesses().iterator(); iterator
                .hasNext();) {
            Process proc = (Process) iterator.next();

            allProcAndIfcIds.add(proc.getId());
        }

        for (Object copyObj : copyList) {
            if (copyObj instanceof Process) {
                Process process = (Process) copyObj;

                // When copying process we have to repair references to that
                // process from independent sub-process tasks.

                // We also have to handle references to process interfaces
                ImplementedInterface implementedIfc =
                        (ImplementedInterface) Xpdl2ModelUtil
                                .getOtherElement(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ImplementedInterface());
                if (implementedIfc != null) {
                    // If we are NOT also copying the process interface then we
                    // need to add the source pkg as a pkg reference.
                    // i.e. it will ref back to original package.
                    if (implementedIfc.getPackageRef() == null) {
                        if (!copyProcessAndInterfaceIds.contains(implementedIfc
                                .getProcessInterfaceId())) {
                            if (addSrcExtPkg == null) {
                                addSrcExtPkg =
                                        Xpdl2WorkingCopyImpl
                                                .createExternalPackage(WorkingCopyUtil
                                                        .getWorkingCopyFor(srcPackage));
                            }

                            implementedIfc
                                    .setPackageRef(addSrcExtPkg.getHref());
                        }
                    } else {
                        // For reference to process in another package
                        // then we need to create and add an External
                        // Package reference.
                        for (Iterator iter = extPkgs.iterator(); iter.hasNext();) {
                            ExternalPackage extPkg =
                                    (ExternalPackage) iter.next();

                            if (implementedIfc.getPackageRef()
                                    .equals(extPkg.getHref())) {
                                refdExtPkgs.add(extPkg);
                            }
                        }
                    }
                }

                // Any independent sub-process call task that references a
                // process that is not also being copied must have the package
                // ref added.
                // Then, on paste... if pasted back into original package its
                // pkg id will be removed. If pasted to a different pkg then it
                // will maintain the remote pkg reference to original pkg.

                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity act : activities) {
                    if (act.getImplementation() instanceof SubFlow) {
                        SubFlow subFlow = (SubFlow) act.getImplementation();

                        // If the pkg is null or == src package then check if
                        // ref'd process is also being copied.
                        String subProcId = subFlow.getProcessId();
                        if (subProcId != null && subProcId.length() > 0) {
                            //
                            // Note pkgRef is NOT the package id!
                            String pkgRef = subFlow.getPackageRefId();

                            if (pkgRef == null) {

                                // Make sure that process exists in src package
                                // (maybe "-unknown-")
                                if (allProcAndIfcIds.contains(subProcId)) {
                                    // THis is a ref to process in source pkg.
                                    // If refd process not also copied then add
                                    // pkg id.
                                    if (!copyProcessAndInterfaceIds
                                            .contains(subProcId)) {
                                        // Create an external package element if
                                        // there isn't one yet.
                                        if (addSrcExtPkg == null) {
                                            addSrcExtPkg =
                                                    Xpdl2WorkingCopyImpl
                                                            .createExternalPackage(WorkingCopyUtil
                                                                    .getWorkingCopyFor(srcPackage));
                                        }

                                        if (addSrcExtPkg != null) {
                                            subFlow.setPackageRefId(addSrcExtPkg
                                                    .getHref());
                                        }
                                    }
                                }
                            } else {
                                // For reference to process in another package
                                // then we need to create and add an External
                                // Package reference.
                                for (Iterator iter = extPkgs.iterator(); iter
                                        .hasNext();) {
                                    ExternalPackage extPkg =
                                            (ExternalPackage) iter.next();

                                    if (pkgRef.equals(extPkg.getHref())) {
                                        refdExtPkgs.add(extPkg);
                                    }
                                }
                            }
                        }
                    }
                } // Next activity in process.
            }
        } // next object in copied list.

        // Add any external package references to the copy list
        if (refdExtPkgs.size() > 0) {
            copyList.addAll(refdExtPkgs);
        }

        // If there were references to processes in src package without those
        // processes being copied then add an external package ref for the src
        // package
        if (addSrcExtPkg != null) {
            copyList.add(addSrcExtPkg);
        }

        return;
    }

    public static CommandContainer paste(EObject destEObject,
            Collection clipboardObjects, EditingDomain editingDomain,
            Object target, final boolean handleProjectReferences) {
        // XPD-3033 filter ProjectReference objects from clipboard objects list.
        Collection pasteObjects = new ArrayList();
        final Collection<ProjectReference> projectReferencesList =
                new ArrayList<ProjectReference>();
        filterProjectReferencesAndOtherObjects(pasteObjects,
                projectReferencesList,
                clipboardObjects);

        //
        // Reassign all the unique id's and references to them.
        Xpdl2ModelUtil.reassignUniqueIds(pasteObjects, editingDomain);

        Package pkg;

        if (destEObject instanceof Package) {
            pkg = (Package) destEObject;
        } else {
            pkg = Xpdl2ModelUtil.getPackage(destEObject);
        }

        // Perform any validation on the objects to paste in.
        if (!validateCopyObject(destEObject, pasteObjects)) {
            return null;
        }

        //
        // Sort out type declarations first.
        refactorTypeDeclarations(pkg, pasteObjects);

        //
        // Separate the list into processes and other objects.
        List<Process> processes = new ArrayList<Process>();

        // Objects other than tyope declarations and processes.
        List<EObject> otherObjs = new ArrayList<EObject>();

        List<TypeDeclaration> typeDeclarations =
                new ArrayList<TypeDeclaration>();

        List<ExternalPackage> externalPkgRefs =
                new ArrayList<ExternalPackage>();

        List<ProcessInterface> pasteProcInterfaces =
                new ArrayList<ProcessInterface>();

        List<InterfaceMethod> processInterfaceMethods =
                new ArrayList<InterfaceMethod>();

        List<EObject> processesAndInterfaces = new ArrayList<EObject>();
        List<ErrorMethod> errorMethods = new ArrayList<ErrorMethod>();

        for (Iterator iter = pasteObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Process) {
                processes.add((Process) obj);
                processesAndInterfaces.add((EObject) obj);

            } else if (obj instanceof ProcessInterface) {
                pasteProcInterfaces.add((ProcessInterface) obj);
                processesAndInterfaces.add((EObject) obj);

            } else if (obj instanceof InterfaceMethod) {
                processInterfaceMethods.add((InterfaceMethod) obj);

            } else if (obj instanceof ExternalPackage) {
                externalPkgRefs.add((ExternalPackage) obj);

            } else if (obj instanceof TypeDeclaration) {
                typeDeclarations.add((TypeDeclaration) obj);

            } else if (obj instanceof ErrorMethod) {
                errorMethods.add((ErrorMethod) obj);
            }

            else if (obj instanceof EObject) {
                otherObjs.add((EObject) obj);
            }
        }

        Collection<? extends EObject> mainObjs;
        if (processesAndInterfaces.size() > 0) {
            // Main objects to paste are the processes/process interfaces.
            mainObjs = processesAndInterfaces;

        } else if (processInterfaceMethods.size() > 0) {
            // If we have proc interface methods then these must be top level
            // objects (along with referenced params and type declarations used
            // by those params.
            mainObjs = processInterfaceMethods;

        } else if (otherObjs.size() == 0) {
            // Don't have any main-selection objects that have caused other
            // referenced objects (like fields/params /partic etc) to be added
            // to clipboard during copy.
            // Also, don't have any non-type declaration objects so we must have
            // just type declarations / external package refs.
            mainObjs = typeDeclarations;

        } else {
            // No processes to paste so main objs are all the others
            // (fields/participants)
            mainObjs = otherObjs;
        }

        //
        // See if we are pasting into a container inserting into list
        // (selected object not a container).
        //
        // To do this (copied roughly what was here before) attempt an add
        // of the main objs to paste. If this is not executable then we
        // assume that we are not on a container and perform an insert on
        // selected objects parent container.
        CompoundCommand addMainObjsCmd = new CompoundCommand();

        if (processesAndInterfaces.size() > 0) {
            // If we have any process interfaces in the copy buffer then we must
            // create the ProcessInterfaces container and add those.
            if (pasteProcInterfaces.size() > 0
                    && (destEObject instanceof Package
                            || destEObject instanceof ProcessInterface || destEObject instanceof Process)) {

                ProcessInterfaces processInterfaces =
                        (ProcessInterfaces) Xpdl2ModelUtil.getOtherElement(pkg,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ProcessInterfaces());

                if (processInterfaces == null) {
                    processInterfaces =
                            XpdExtensionFactory.eINSTANCE
                                    .createProcessInterfaces();

                    addMainObjsCmd
                            .append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            pkg,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ProcessInterfaces(),
                                            processInterfaces));
                }

                // There is already a ProcessIntefaces other element.
                for (ProcessInterface procIfc : pasteProcInterfaces) {
                    addMainObjsCmd.append(AddCommand.create(editingDomain,
                            processInterfaces,
                            XpdExtensionPackage.eINSTANCE
                                    .getProcessInterfaces_ProcessInterface(),
                            procIfc));
                }
            }

            //
            // Also - If we have processes to paste then paste them into
            // package.
            if (processes.size() > 0
                    && (destEObject instanceof Package
                            || destEObject instanceof ProcessInterface || destEObject instanceof Process)) {
                addMainObjsCmd.append(AddCommand.create(editingDomain,
                        pkg,
                        null,
                        processes));
            }

        } else if (processInterfaceMethods.size() > 0) {
            // Top-level selected objects are process interface methods, copy
            // them in.
            if (destEObject instanceof InterfaceMethod) {
                destEObject = destEObject.eContainer();
            }

            if (destEObject instanceof ProcessInterface) {
                ProcessInterface procIfc = (ProcessInterface) destEObject;

                for (InterfaceMethod method : processInterfaceMethods) {
                    if (method instanceof StartMethod) {
                        addMainObjsCmd.append(AddCommand.create(editingDomain,
                                procIfc,
                                XpdExtensionPackage.eINSTANCE
                                        .getProcessInterface_StartMethods(),
                                method));

                    } else if (method instanceof IntermediateMethod) {
                        addMainObjsCmd
                                .append(AddCommand
                                        .create(editingDomain,
                                                procIfc,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getProcessInterface_IntermediateMethods(),
                                                method));

                    }
                }
            }
        } else if (errorMethods.size() > 0) {
            if (destEObject instanceof ErrorMethod) {
                destEObject = destEObject.eContainer();
            }

            if (destEObject instanceof InterfaceMethod) {
                InterfaceMethod interfaceMethod = (InterfaceMethod) destEObject;
                for (ErrorMethod method : errorMethods) {
                    addMainObjsCmd.append(AddCommand.create(editingDomain,
                            interfaceMethod,
                            XpdExtensionPackage.eINSTANCE
                                    .getInterfaceMethod_ErrorMethods(),
                            method));
                }

            }
        }

        else {
            // If not pasting processes and interfaces (or process interface
            // methods) then all objects in copy
            // buffer are little individual things like fields/params etc and
            // are NOT there just to support references to them in
            // processes/process interfaces/methods.
            addMainObjsCmd.append(AddCommand.create(editingDomain,
                    destEObject,
                    null,
                    mainObjs));

            setGroupAttributes(addMainObjsCmd, editingDomain, mainObjs, target);

            if (addMainObjsCmd == null || !addMainObjsCmd.canExecute()) {
                // Selected object isn't a container for main objects, insert in
                // objects into the dest object's container.
                addMainObjsCmd = new CompoundCommand();

                if (destEObject.eContainingFeature() != null
                        && destEObject.eContainingFeature().isMany()) {

                    EObject parent = destEObject.eContainer();

                    List lst =
                            (List) parent
                                    .eGet(destEObject.eContainingFeature());
                    int index = lst.indexOf(destEObject);

                    addMainObjsCmd.append(AddCommand.create(editingDomain,
                            parent,
                            destEObject.eContainingFeature(),
                            mainObjs,
                            index));

                    setGroupAttributes(addMainObjsCmd,
                            editingDomain,
                            mainObjs,
                            target);
                }
            }
        }

        if (!addMainObjsCmd.canExecute()) {
            // Can't execute on parent container either - forget it!
            return null;
        }

        //
        // For main objects then we will rename as "Copy of ..." if there is
        // already an object in parent with same name.
        if (processesAndInterfaces.size() > 0) {
            renameCopyOfProcessesAndInterfaces(processesAndInterfaces, pkg);

        } else if (processInterfaceMethods.size() > 0) {
            // When main objects are methods, ensure that they are tagged with
            // "Copy_of" if already exist.
            renameCopyOfProcessInterfaceMethods(processInterfaceMethods,
                    (ProcessInterface) destEObject);

        } else if (errorMethods.size() > 0) {
            renameCopyOfErrorMethods(errorMethods,
                    (InterfaceMethod) destEObject);
        } else if (otherObjs.size() > 0) {
            // For fields/params/participants, we only want to rename as
            // "Copy of " if we are NOT pasting them as a result of being
            // referenced from other top-level objects
            // (process/interface/method)

            // Because for paste of these as ancillaries to process we will say
            // 'don't bother if they already exist in destination container with
            // same name').
            renameCopyOfDataFields(mainObjs, destEObject);
            renameCopyOfFormalParams(mainObjs, destEObject);
            renameCopyOfParticipants(mainObjs, destEObject);
        }

        //
        // Everything seems to be ok.
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(addMainObjsCmd);
        cmd.setLabel(Messages.PasteAction_Paste);

        if (processesAndInterfaces.size() > 0) {
            // If we are pasting processes/interfaces then we need to paste the
            // other
            // objects into their respective containers too.
            addProcessRelatedObjects(editingDomain,
                    pkg,
                    processes,
                    otherObjs,
                    typeDeclarations,
                    cmd);

            // Sort out references to sub-processes from indi-subproc call
            // tasks.
            fixSubProcessReferences(editingDomain,
                    pkg,
                    processes,
                    externalPkgRefs,
                    cmd);

        } else if (processInterfaceMethods.size() > 0) {
            addMethodRelatedObjects(editingDomain,
                    (ProcessInterface) destEObject,
                    otherObjs,
                    typeDeclarations,
                    cmd);

        } else if (otherObjs.size() > 0) {
            // If not pasting whole processes, and not only pasting type
            // declarations (i.e.we are pasting data fields potentially with
            // ref'd type declarations). Then add the type declarations.
            if (typeDeclarations.size() > 0) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations(),
                        typeDeclarations));
            }
        }
        final EObject destObject = destEObject;
        return new CommandContainer(editingDomain, cmd) {

            /**
             * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer#executeCommand()
             * 
             */
            @Override
            public void executeCommand() {
                if (handleProjectReferences) {

                    IProject destProject =
                            WorkingCopyUtil.getProjectFor(destObject);
                    if (checkAndAddProjectReference(destProject,
                            projectReferencesList)) {
                        super.executeCommand();
                    }
                } else {
                    super.executeCommand();
                }
            }

        };
    }

    /**
     * @param destProject
     * @param projectReferencesList
     */
    public static boolean checkAndAddProjectReference(IProject destProject,
            Collection<ProjectReference> projectReferencesList) {
        Collection<IProject> projects = new ArrayList<IProject>();
        for (Object object : projectReferencesList) {
            if (object instanceof ProjectReference) {
                ProjectReference projectReference = (ProjectReference) object;
                projects.add(projectReference.getProject());
            }
        }
        return ProcessUIUtil.checkAndAddProjectReference(Display.getDefault()
                .getActiveShell(), destProject, projects);

    }

    /**
     * Perform any validation of paste objects that's necessary.
     * <p>
     * Nominally, if there are process-related objects in the clipboard then we
     * will try to paste them into the destination object.
     * <p>
     * However, sometimes objects could be pasted 'out of context'. For
     * instance, the user might perform a Copy of objects from the Diagram
     * Editor and then attempt a Paste into a process on the Project Explorer.
     * <p>
     * The clipboard might contain Activities and because AddCommand(process,
     * activities) is permitted, we would try and paste those activities into
     * the process. However the paste is 'out of context' because we make no
     * attempt to resolve the parent lane etc of those activities.
     * <p>
     * We cannot just say 'only allow objects that we display in project
     * explorer' because when we copy/paste a process we need to put extra
     * EObjects in the clipboard (that we consider to be 'within a process' but
     * xpdl2 stores outside of process). Therefore there can VALIDLY be other
     * objects when we copy a process (pools artifacts associations etc).
     * <p>
     * So we ensure that if there ARE objects we can't directly paste that they
     * are there in support of another object they belong to.
     * 
     * @param destEObject
     * @param pasteObjects
     * @return
     */
    private static boolean validateCopyObject(EObject destEObject,
            Collection pasteObjects) {

        // Currently, the only copy object that requires 'other support objects'
        // is Process.
        boolean hasProcess = false;
        boolean hasSupportingObjects = false;

        for (Object obj : pasteObjects) {
            if (obj instanceof Process) {
                hasProcess = true;

            } else {
                // See whether the object is anything else we support directly.
                if (obj instanceof ProcessInterface
                        // Start and Intermediate Interface Event...
                        || obj instanceof InterfaceMethod
                        || obj instanceof Participant
                        || obj instanceof TypeDeclaration
                        // Field and Param...
                        || obj instanceof ProcessRelevantData
                        || obj instanceof ErrorMethod
                        || obj instanceof Activity) {
                    //
                    // These objects can be pasted in there own right.
                    // Nothing to do.
                } else {
                    //
                    // Anything else is here 'in support' of a larger paste
                    // object (i.e. something else that Process references
                    // outside of the Process element itself such as a
                    // Pool/Artifact).
                    hasSupportingObjects = true;
                }
            }

        }

        if (hasSupportingObjects && !hasProcess) {
            return false;
        }

        return true;
    }

    private static void addMethodRelatedObjects(EditingDomain editingDomain,
            ProcessInterface processInterface, List<EObject> otherObjs,
            List<TypeDeclaration> typeDeclarations, CompoundCommand cmd) {

        // Type declarations first.
        if (typeDeclarations.size() > 0) {
            cmd.append(AddCommand.create(editingDomain,
                    Xpdl2ModelUtil.getPackage(processInterface),
                    Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations(),
                    typeDeclarations));
        }

        Set<String> existingParamNames = new HashSet<String>();
        for (Iterator iterator =
                processInterface.getFormalParameters().iterator(); iterator
                .hasNext();) {
            FormalParameter param = (FormalParameter) iterator.next();
            existingParamNames.add(param.getName());
        }

        // Then formal parameters.
        for (EObject obj : otherObjs) {
            if (obj instanceof FormalParameter) {
                FormalParameter param = (FormalParameter) obj;

                if (!existingParamNames.contains(param.getName())) {
                    cmd.append(AddCommand
                            .create(editingDomain,
                                    processInterface,
                                    Xpdl2Package.eINSTANCE
                                            .getFormalParametersContainer_FormalParameters(),
                                    param));
                }
            }
        }

        return;
    }

    /**
     * Type declarations are a complete pain! Because they can reference other
     * type declarations.
     * 
     * If we are only copying type declarations then we will copy the whole
     * hierarchy and if name already exists then rename as copy-of.
     * 
     * If we are copying type declarations as well as other things then we are
     * only creating the type declarations because they are referenced by other
     * things (like data fields / other declarations). In this case we will
     * create them if they don't exist, if same name type already exists then we
     * will not create it andf we will refactor anything that references it to
     * reference the existing type.
     * 
     * @param pkg
     * 
     * @param copyObjects
     */
    private static void refactorTypeDeclarations(Package pkg,
            Collection copyObjects) {

        // First decide what we are going to do with each type declaration in
        // paste buffer. We'll do this by creating a map of paste type id to
        // existing type id.
        boolean typesOnly = true;
        boolean typesFound = false;

        HashMap<String, String> redirectedIds = new HashMap<String, String>();

        EList existingTypes = pkg.getTypeDeclarations();

        for (Iterator iter = copyObjects.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof TypeDeclaration) {
                typesFound = true;

                TypeDeclaration pasteType = (TypeDeclaration) obj;
                TypeDeclaration existingType = null;

                String typeName = pasteType.getName();
                if (typeName != null) {
                    // Find existing type with same name.
                    for (Iterator iterator = existingTypes.iterator(); iterator
                            .hasNext();) {
                        TypeDeclaration td = (TypeDeclaration) iterator.next();

                        if (typeName.equals(td.getName())) {
                            existingType = td;
                            break;
                        }
                    }
                }

                if (existingType != null) {
                    // Re-use existing type declaration.
                    redirectedIds.put(pasteType.getId(), existingType.getId());
                }

            } else {
                typesOnly = false;
            }
        }

        if (typesFound) {
            if (typesOnly) {
                // If we are only copying type declarations then we will copy
                // the whole hierarchy and if name already exists then rename as
                // copy-of.
                for (Iterator iter = copyObjects.iterator(); iter.hasNext();) {
                    TypeDeclaration td = (TypeDeclaration) iter.next();

                    // If there was already same named type then we will have
                    // added
                    // it to map so rename - otherwise keep same name and add
                    // it.
                    if (redirectedIds.containsKey(td.getId())) {
                        String name =
                                Messages.CopyOf_tokenNoSpaces + td.getName();
                        td.setName(NameUtil.getInternalName(name, true));
                        Xpdl2ModelUtil.setOtherAttribute(td,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                name);
                    }
                }

            } else {
                // If we are copying type declarations as well as other things
                // then we are only creating the type declarations because they
                // are referenced by other things (like data fields / other
                // declarations).
                //
                // In this case we will create them if they don't
                // exist, if same name type already exists then we will not
                // create it and we will refactor anything that references it to
                // reference the existing type.

                // First go thru the rest of the copy objects replacing any id's
                for (Iterator iter = copyObjects.iterator(); iter.hasNext();) {
                    Object obj = iter.next();

                    if (obj instanceof TypeDeclaration) {
                        TypeDeclaration td = (TypeDeclaration) obj;

                        // If this type declaration refers to another that isn't
                        // being copied then redirect the reference to existing.
                        DeclaredType dt = td.getDeclaredType();

                        if (dt != null
                                && redirectedIds.containsKey(dt
                                        .getTypeDeclarationId())) {
                            dt.setTypeDeclarationId(redirectedIds.get(dt
                                    .getTypeDeclarationId()));
                        }
                    } else if (obj instanceof ProcessRelevantData) {
                        ProcessRelevantData data = (ProcessRelevantData) obj;

                        if (data.getDataType() instanceof DeclaredType) {
                            DeclaredType dt = (DeclaredType) data.getDataType();

                            if (dt != null
                                    && redirectedIds.containsKey(dt
                                            .getTypeDeclarationId())) {
                                dt.setTypeDeclarationId(redirectedIds.get(dt
                                        .getTypeDeclarationId()));
                            }
                        }
                    } else if (obj instanceof Process) {
                        Process process = (Process) obj;

                        // Do data fields and formal params under process.
                        for (Iterator iterator =
                                process.getDataFields().iterator(); iterator
                                .hasNext();) {
                            DataField data = (DataField) iterator.next();

                            if (data.getDataType() instanceof DeclaredType) {
                                DeclaredType dt =
                                        (DeclaredType) data.getDataType();

                                if (dt != null
                                        && redirectedIds.containsKey(dt
                                                .getTypeDeclarationId())) {
                                    dt.setTypeDeclarationId(redirectedIds
                                            .get(dt.getTypeDeclarationId()));
                                }
                            }

                        }

                        // And formal parameters
                        for (Iterator iterator =
                                process.getFormalParameters().iterator(); iterator
                                .hasNext();) {
                            FormalParameter data =
                                    (FormalParameter) iterator.next();

                            if (data.getDataType() instanceof DeclaredType) {
                                DeclaredType dt =
                                        (DeclaredType) data.getDataType();

                                if (dt != null
                                        && redirectedIds.containsKey(dt
                                                .getTypeDeclarationId())) {
                                    dt.setTypeDeclarationId(redirectedIds
                                            .get(dt.getTypeDeclarationId()));
                                }
                            }
                        }
                    }
                }

                // Then remove any type declarations from the copy list that
                // already exist in dest package
                for (Iterator iter = copyObjects.iterator(); iter.hasNext();) {
                    Object obj = iter.next();

                    if (obj instanceof TypeDeclaration) {
                        TypeDeclaration td = (TypeDeclaration) obj;

                        if (redirectedIds.containsKey(td.getId())) {
                            // Don't want this one it already exists.
                            iter.remove();
                        }

                    }

                }
            }
        }

        return;
    }

    /**
     * Replace all the participant references from given paste participant id to
     * the replacement id (the id of the existing participant with same name).
     * 
     * @param pasteId
     * @param replaceId
     * @param process
     */
    private static void replaceParticIds(String pasteId, String replaceId,
            Process process) {
        Collection<Activity> acts =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);

        for (Activity act : acts) {
            EList performers = act.getPerformerList();

            for (Iterator perfIter = performers.iterator(); perfIter.hasNext();) {
                Performer perf = (Performer) perfIter.next();

                if (pasteId.equals(perf.getValue())) {
                    perf.setValue(replaceId);
                }
            }
            replaceWebServiceOperationAlias(act, pasteId, replaceId);
        }

        String apiEndPointParticId =
                (String) Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ApiEndPointParticipant());
        if (apiEndPointParticId != null && apiEndPointParticId.equals(pasteId)) {
            Xpdl2ModelUtil.setOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ApiEndPointParticipant(),
                    replaceId);
        }

        return;
    }

    /**
     * Replace all the participant references from given paste participant id to
     * the replacement id (the id of the existing participant with same name) in
     * the web service operation.
     * 
     * @param act
     * @param pasteId
     * @param replaceId
     */
    private static void replaceWebServiceOperationAlias(Activity act,
            String pasteId, String replaceId) {
        WebServiceOperation wso = Xpdl2ModelUtil.getWebServiceOperation(act);

        if (wso != null) {
            String aliasId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Alias());
            if (aliasId != null && aliasId.equals(pasteId)) {
                Xpdl2ModelUtil.setOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        replaceId);
            }
        }

        return;
    }

    private static void renameCopyOfProcessesAndInterfaces(
            Collection<?> copyObjs, Package pkg) {

        if (pkg != null) {

            EList<Process> processes = pkg.getProcesses();

            ProcessInterfaces procInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);

            for (Object obj : copyObjs) {

                if (obj instanceof Process) {
                    Process process = ((Process) obj);

                    String name =
                            getCopyOfPasteDisplayName(Xpdl2ModelUtil.getDisplayName(process),
                                    processes);

                    Xpdl2ModelUtil.setOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            name);

                    String token =
                            getCopyOfPasteName(process.getName(), processes);

                    process.setName(token);

                } else if (obj instanceof ProcessInterface) {
                    if (procInterfaces != null) {
                        ProcessInterface processIfc = ((ProcessInterface) obj);

                        String name =
                                getCopyOfPasteName(processIfc.getName(),
                                        procInterfaces.getProcessInterface());
                        EAttribute ea =
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName();
                        String token =
                                getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                        .getOtherAttribute(processIfc, ea),
                                        procInterfaces.getProcessInterface());

                        processIfc.setName(name);
                        Xpdl2ModelUtil.setOtherAttribute(processIfc, ea, token);
                    }
                }
            }
        }

        return;
    }

    private static void renameCopyOfProcessInterfaceMethods(
            List<InterfaceMethod> processInterfaceMethods,
            ProcessInterface destProcessInterface) {
        if (destProcessInterface != null) {

            List<NamedElement> existingMethods = new ArrayList<NamedElement>();

            existingMethods.addAll(destProcessInterface.getStartMethods());
            existingMethods.addAll(destProcessInterface
                    .getIntermediateMethods());

            for (InterfaceMethod method : processInterfaceMethods) {
                String name =
                        getCopyOfPasteName(method.getName(), existingMethods);
                EAttribute ea =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName();
                String token =
                        getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                .getOtherAttribute(method, ea),
                                existingMethods);
                method.setName(name);
                Xpdl2ModelUtil.setOtherAttribute(method, ea, token);
            }
        }

        return;
    }

    /**
     * @param errorMethods
     * @param destEObject
     */
    private static void renameCopyOfErrorMethods(
            List<ErrorMethod> errorMethods, InterfaceMethod destIfcMethod) {
        if (destIfcMethod != null) {

            List<ErrorMethod> existingMethods = new ArrayList<ErrorMethod>();

            existingMethods.addAll(destIfcMethod.getErrorMethods());

            for (ErrorMethod method : errorMethods) {
                String errorCode =
                        getCopyOfErrorPasteName(method.getErrorCode(),
                                existingMethods);
                method.setErrorCode(errorCode);
            }
        }

        return;
    }

    private static String getCopyOfErrorPasteName(String originalName,
            List<ErrorMethod> existingErrorElements) {
        if (originalName == null) {
            originalName = ""; //$NON-NLS-1$
        }

        Set<String> existingErrorCodes = new HashSet<String>();

        for (ErrorMethod el : existingErrorElements) {
            String errorCode = el.getErrorCode();
            if (errorCode == null || errorCode.length() == 0) {
                errorCode = "?"; //$NON-NLS-1$
            }
            existingErrorCodes.add(errorCode);
        }

        String finalName = originalName;
        if (existingErrorCodes.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName = Messages.CopyOf_tokenNoSpaces + finalName;

            int idx = 1;

            while (existingErrorCodes.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyNOf_tokenNoSpaces, idx)
                                + originalName;
            }
        }

        return finalName;
    }

    private static String getCopyOfPasteName(String originalName,
            List<? extends NamedElement> existingElements) {
        if (originalName == null) {
            originalName = ""; //$NON-NLS-1$
        }

        Set<String> existingNames = new HashSet<String>();

        for (NamedElement el : existingElements) {
            String name = el.getName();
            if (name == null || name.length() == 0) {
                name = "?"; //$NON-NLS-1$
            }
            existingNames.add(name);
        }

        String finalName = originalName;
        if (existingNames.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName = Messages.CopyOf_tokenNoSpaces + finalName;

            int idx = 1;

            while (existingNames.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyNOf_tokenNoSpaces, idx)
                                + originalName;
            }
        }

        return finalName;
    }

    public static String getCopyOfPasteDisplayName(String originalName,
            Collection<? extends NamedElement> existingElements) {
        if (originalName == null) {
            originalName = ""; //$NON-NLS-1$
        }

        Set<String> existingNames = new HashSet<String>();

        for (NamedElement el : existingElements) {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(el);
            if (name == null || name.length() == 0) {
                name = "?"; //$NON-NLS-1$
            }
            existingNames.add(name);
        }

        String finalName = originalName;
        if (existingNames.contains(finalName)) {
            // Try Copy_Of_ first.
            finalName = Messages.CopyOf_tokenNoSpaces + finalName;

            int idx = 1;

            while (existingNames.contains(finalName)) {
                // Already a CopyOf... use a sequence number.
                idx++;
                finalName =
                        String.format(Messages.CopyNOf_tokenNoSpaces, idx)
                                + originalName;
            }
        }

        return finalName;
    }

    public static void removeProcesses(Collection copyObjs) {
        Iterator iter = copyObjs.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if (obj instanceof Process) {
                iter.remove();
                break;
            }
        }
    }

    private static void renameCopyOfDataFields(Collection copyObjs,
            EObject destEObject) {
        EObject container = destEObject;

        while (container != null && !(container instanceof DataFieldsContainer)) {
            container = container.eContainer();
        }

        if (container != null) {
            DataFieldsContainer dataContainer = (DataFieldsContainer) container;

            List<NamedElement> fields = new ArrayList<NamedElement>();
            fields.addAll(dataContainer.getDataFields());

            if (dataContainer instanceof Process) {
                fields.addAll(ProcessInterfaceUtil
                        .getAllFormalParameters(((Process) dataContainer)));
                fields.addAll(((Process) dataContainer).getPackage()
                        .getDataFields());
            }

            for (Iterator iter = copyObjs.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof DataField) {
                    DataField dataField = ((DataField) obj);

                    String name =
                            getCopyOfPasteName(dataField.getName(), fields);
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    String token =
                            getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                    .getOtherAttribute(dataField, ea),
                                    fields);

                    dataField.setName(name);
                    Xpdl2ModelUtil.setOtherAttribute(dataField, ea, token);
                }
            }
        }

        return;
    }

    private static void renameCopyOfFormalParams(Collection copyObjs,
            EObject destEObject) {
        EObject container = destEObject;

        while (container != null
                && !(container instanceof FormalParametersContainer)) {
            container = container.eContainer();
        }

        if (container != null) {
            FormalParametersContainer dataContainer =
                    (FormalParametersContainer) container;

            List<NamedElement> fields = new ArrayList<NamedElement>();

            if (dataContainer instanceof Process) {
                fields.addAll(ProcessInterfaceUtil
                        .getAllFormalParameters(((Process) dataContainer)));
                fields.addAll(((Process) dataContainer).getDataFields());
                fields.addAll(((Process) dataContainer).getPackage()
                        .getDataFields());
            } else if (dataContainer instanceof ProcessInterface) {
                fields.addAll(dataContainer.getFormalParameters());
                fields.addAll(Xpdl2ModelUtil.getPackage(dataContainer)
                        .getDataFields());
            }

            for (Iterator iter = copyObjs.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof FormalParameter) {
                    FormalParameter formalParam = ((FormalParameter) obj);
                    String name =
                            getCopyOfPasteName(formalParam.getName(), fields);
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    String token =
                            getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                    .getOtherAttribute(formalParam, ea),
                                    fields);

                    formalParam.setName(name);
                    Xpdl2ModelUtil.setOtherAttribute(formalParam, ea, token);
                }
            }
        }

        return;
    }

    private static void renameCopyOfParticipants(Collection copyObjs,
            EObject destEObject) {
        EObject container = destEObject;

        while (container != null
                && !(container instanceof ParticipantsContainer)) {
            container = container.eContainer();
        }

        if (container != null) {
            ParticipantsContainer dataContainer =
                    (ParticipantsContainer) container;

            List<NamedElement> partics = new ArrayList<NamedElement>();
            partics.addAll(dataContainer.getParticipants());

            if (dataContainer instanceof Process) {
                partics.addAll(((Process) dataContainer).getPackage()
                        .getParticipants());
            }

            for (Iterator iter = copyObjs.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (obj instanceof Participant) {
                    Participant participant = ((Participant) obj);
                    String name =
                            getCopyOfPasteName(participant.getName(), partics);
                    EAttribute ea =
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName();
                    String token =
                            getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                                    .getOtherAttribute(participant, ea),
                                    partics);

                    participant.setName(name);
                    Xpdl2ModelUtil.setOtherAttribute(participant, ea, token);
                }
            }
        }

        return;
    }

    private static void setGroupAttributes(CompoundCommand cmd,
            EditingDomain ed, Collection<? extends EObject> copyObjs,
            Object target) {
        boolean shouldBeCorrelation = target instanceof CorrelationDataGroup;
        if (target instanceof DataField) {
            DataField targetField = (DataField) target;
            shouldBeCorrelation = targetField.isCorrelation();
        }
        for (EObject copyObj : copyObjs) {
            if (copyObj instanceof DataField) {
                DataField field = (DataField) copyObj;
                boolean isCorrelation = field.isCorrelation();
                if (isCorrelation != shouldBeCorrelation) {
                    cmd.append(SetCommand.create(ed,
                            field,
                            Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                            new Boolean(shouldBeCorrelation)));
                }
            }
        }
    }

    /**
     * If we are pasting processes then we need to paste the other objects into
     * their respective containers too.
     * 
     * @param editingDomain
     * @param pkg
     * @param processes
     * @param otherObjs
     * @param typeDeclarations
     * @param cmd
     */
    private static void addProcessRelatedObjects(EditingDomain editingDomain,
            Package pkg, List<Process> processes, List<EObject> otherObjs,
            List<TypeDeclaration> typeDeclarations, CompoundCommand cmd) {
        // Type declarations first.
        if (typeDeclarations.size() > 0) {
            cmd.append(AddCommand.create(editingDomain,
                    pkg,
                    Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations(),
                    typeDeclarations));
        }

        for (EObject eObj : otherObjs) {
            if (eObj instanceof Pool) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE.getPackage_Pools(),
                        eObj));

            } else if (eObj instanceof Artifact) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE.getPackage_Artifacts(),
                        eObj));

            } else if (eObj instanceof Association) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE.getPackage_Associations(),
                        eObj));

            } else if (eObj instanceof MessageFlow) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE.getPackage_MessageFlows(),
                        eObj));

            } else if (eObj instanceof Participant) {
                addPackageParticipant(editingDomain, pkg, processes, cmd, eObj);

            } else if (eObj instanceof DataField) {
                addPackageDataField(editingDomain, pkg, cmd, eObj);
            }

        }
    }

    /**
     * When pasting processes we have to fix sub-process references in
     * indi-subproc call tasks.
     * 
     * ON copy, the CopyAction should have ensured that any indi subproc task
     * that referenced a process that was not also in selection had its package
     * reference set and also added ExternalPackage references for them.
     * 
     * When pasting independent sub-proc tasks, we can check whether we are
     * pasting into the same or a different package as the proces referenced in
     * the task and remove the package reference in task.
     * 
     * @param editingDomain
     * @param targetPkg
     * @param processes
     * @param externalPkgRefs
     * @param cmd
     */
    private static void fixSubProcessReferences(EditingDomain editingDomain,
            Package targetPkg, List<Process> processes,
            List<ExternalPackage> externalPkgRefs, CompoundCommand cmd) {

        // Only have work to do if there are edxt pkg refs. If there aren't then
        // either there are no indi-subproc tasks or all the process ref'd from
        // them were also copied.
        if (externalPkgRefs.size() > 0) {
            String ourPackageLocation =
                    getOurPackageReferenceLocation(targetPkg);

            if (ourPackageLocation != null && ourPackageLocation.length() > 0) {

                // Create a map of sub-process package ref to external package
                // elements.
                HashMap<String, ExternalPackage> extPkgMap =
                        new HashMap<String, ExternalPackage>();
                for (ExternalPackage extPkg : externalPkgRefs) {
                    extPkgMap.put(extPkg.getHref(), extPkg);
                }

                // Keep track of any external packages to add to our package.
                Set<ExternalPackage> addPkgRefs =
                        new HashSet<ExternalPackage>();

                for (Process process : processes) {
                    // If process implements an interface then resolve pkg
                    // references in the 'implements itnerface stuff'
                    ImplementedInterface implementedIfc =
                            (ImplementedInterface) Xpdl2ModelUtil
                                    .getOtherElement(process,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementedInterface());
                    if (implementedIfc != null) {
                        // If implemented ifc has a package reference then it
                        // was either originally from a different package than
                        // source or the ref'd process in same package was
                        // not included in the copy.
                        String pkgRef = implementedIfc.getPackageRef();

                        if (pkgRef != null && pkgRef.length() > 0) {
                            // Either way we check our own package reference
                            // details with those in the copy.
                            ExternalPackage srcPkg = extPkgMap.get(pkgRef);
                            if (srcPkg != null) {

                                String srcPkgLoc =
                                        Xpdl2WorkingCopyImpl
                                                .getExternalPackageLocation(srcPkg);
                                if (ourPackageLocation.equals(srcPkgLoc)) {
                                    // If target package is same as refd
                                    // package then we can remove the
                                    // package reference.
                                    implementedIfc.setPackageRef(null);

                                } else {
                                    // Otherwise we need to add the
                                    // external package reference for it.
                                    addPkgRefs.add(srcPkg);
                                }
                            }
                        }
                    }

                    // Sort out sub-process task poackage references
                    Collection<Activity> activities =
                            Xpdl2ModelUtil.getAllActivitiesInProc(process);

                    for (Activity act : activities) {
                        if (act.getImplementation() instanceof SubFlow) {
                            SubFlow subFlow = (SubFlow) act.getImplementation();

                            // If task has a package reference then it was
                            // either originally from a different package than
                            // source or the ref'd process in same package was
                            // not included in the copy.
                            String pkgRef = subFlow.getPackageRefId();

                            if (pkgRef != null && pkgRef.length() > 0) {
                                // Either way we check our own package reference
                                // details with those in the copy.
                                ExternalPackage srcPkg = extPkgMap.get(pkgRef);
                                if (srcPkg != null) {

                                    String srcPkgLoc =
                                            Xpdl2WorkingCopyImpl
                                                    .getExternalPackageLocation(srcPkg);
                                    if (ourPackageLocation.equals(srcPkgLoc)) {
                                        // If target package is same as refd
                                        // package then we can remove the
                                        // package reference.
                                        subFlow.setPackageRefId(null);

                                    } else {
                                        // Otherwise we need to add the
                                        // external package reference for it.
                                        addPkgRefs.add(srcPkg);
                                    }
                                }
                            }
                        }

                    } // Next activity
                }// Next process.

                // When we're all done, add any external package references
                // ref'd by subproc tasks.
                for (ExternalPackage srcPkg : addPkgRefs) {
                    Xpdl2WorkingCopyImpl
                            .appendAddReferenceCommand(editingDomain,
                                    cmd,
                                    srcPkg,
                                    targetPkg);
                }
            }
        }

        return;
    }

    /**
     * Add package level data field if it does not already exist in destination
     * package.
     * 
     * @param editingDomain
     * @param pkg
     * @param cmd
     * @param eObj
     */
    private static void addPackageDataField(EditingDomain editingDomain,
            Package pkg, CompoundCommand cmd, EObject eObj) {
        // Only add data field if there is not already a
        // field with same name.
        boolean found = false;

        String name = ((DataField) eObj).getName();
        if (name != null) {
            EList fields = pkg.getDataFields();

            for (Iterator iter = fields.iterator(); iter.hasNext();) {
                DataField d = (DataField) iter.next();

                if (name.equals(d.getName())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                cmd.append(AddCommand.create(editingDomain,
                        pkg,
                        Xpdl2Package.eINSTANCE
                                .getDataFieldsContainer_DataFields(),
                        eObj));
            }

        }
    }

    /**
     * Add package level participants but only if they don't exist in
     * destination package (if they do then must replace all the refs by partic
     * id in src objects to existing dest pkg partic id)
     * 
     * @param editingDomain
     * @param pkg
     * @param processes
     * @param cmd
     * @param eObj
     */
    private static void addPackageParticipant(EditingDomain editingDomain,
            Package pkg, List<Process> processes, CompoundCommand cmd,
            EObject eObj) {
        Participant pastePartic = ((Participant) eObj);

        /*
         * 1) If this is a process api participant for one of the pasted
         * processes then we always want to create a new participant in the
         * package.
         */
        Process apiPartticProcess = null;

        for (Process process : processes) {
            String apiParticId =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ApiEndPointParticipant());
            if (pastePartic.getId().equals(apiParticId)) {
                apiPartticProcess = process;
                break;
            }
        }
        /*
         * 2) If used as api partic always add participant BUT change the name
         * to match whatever the process will be called (could be adjusted to
         * Copy_of_ etc) AND change the api URI to match.
         */
        if (apiPartticProcess != null) {

            /* adjusting the participant name to Copy_of_ */
            String pasteParticName =
                    getCopyOfPasteName(pastePartic.getName(),
                            pkg.getParticipants());
            EAttribute ea =
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
            String token =
                    getCopyOfPasteDisplayName((String) Xpdl2ModelUtil
                            .getOtherAttribute(pastePartic, ea),
                            pkg.getParticipants());

            pastePartic.setName(pasteParticName);
            Xpdl2ModelUtil.setOtherAttribute(pastePartic, ea, token);

            /*
             * The name of the process may change to Copy_of_xxx if it is
             * duplicate so we should change the Shared resource URI to the new
             * name.
             */
            final ParticipantSharedResource sharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil
                            .getOtherElement(pastePartic,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ParticipantSharedResource());
            if (null != sharedResource) {
                WsResource wsResource = sharedResource.getWebService();
                WsInbound wsInbound = wsResource.getInbound();
                if (null != wsInbound) {

                    if (null != wsInbound.getSoapHttpBinding()
                            && wsInbound.getSoapHttpBinding().size() > 0) {

                        WsSoapHttpInboundBinding soapHttpBinding =
                                wsInbound.getSoapHttpBinding().get(0);
                        if (null != soapHttpBinding) {

                            String copyOfPasteName =
                                    getCopyOfPasteName(apiPartticProcess.getName(),
                                            pkg.getProcesses());

                            Path newEndpointPath =
                                    new Path("/" + pkg.getName()); //$NON-NLS-1$

                            newEndpointPath =
                                    (Path) newEndpointPath
                                            .append(copyOfPasteName);

                            soapHttpBinding.setEndpointUrlPath(newEndpointPath
                                    .toPortableString());

                            Xpdl2ModelUtil
                                    .setOtherElement(pastePartic,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource(),
                                            sharedResource);
                        }
                        /*
                         * XPD-4389: copy paste for a process with soap over jms
                         * participant fails
                         */
                    } else if (null != wsInbound.getSoapJmsBinding()
                            && wsInbound.getSoapJmsBinding().size() > 0) {

                        WsSoapJmsInboundBinding soapJmsInboundBinding =
                                wsInbound.getSoapJmsBinding().get(0);
                        if (null != soapJmsInboundBinding) {
                            Xpdl2ModelUtil
                                    .setOtherElement(pastePartic,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource(),
                                            sharedResource);
                        }
                    }
                }
            }

            cmd.append(AddCommand.create(editingDomain,
                    pkg,
                    Xpdl2Package.eINSTANCE
                            .getParticipantsContainer_Participants(),
                    pastePartic));

        } else {

            // Only add participant if there is not already a
            // participant with same name.
            String name = pastePartic.getName();
            if (name != null) {
                Participant foundPartic = null;
                EList partics = pkg.getParticipants();
                for (Iterator iter = partics.iterator(); iter.hasNext();) {
                    Participant p = (Participant) iter.next();

                    if (name.equals(p.getName())) {
                        foundPartic = p;
                        break;
                    }
                }

                if (foundPartic == null) {
                    /*
                     * For a non API participant that does not exist in target
                     * we can just copy the paste participant in
                     */
                    cmd.append(AddCommand.create(editingDomain,
                            pkg,
                            Xpdl2Package.eINSTANCE
                                    .getParticipantsContainer_Participants(),
                            eObj));

                } else {
                    // Found existing, re-use it; so we have
                    // refactor the performer references to use the
                    // existing one.
                    String pasteId = pastePartic.getId();
                    String replaceId = foundPartic.getId();

                    for (Iterator iter = processes.iterator(); iter.hasNext();) {
                        Process process = (Process) iter.next();

                        replaceParticIds(pasteId, replaceId, process);
                    }
                }
            }
        }
    }

    private static String getOurPackageReferenceLocation(Package pkg) {
        // Get an external package reference for ourselves so that we can
        // compare like-for-like with those in the copy list
        String ourPackageLocation = null;

        ExternalPackage ourPackageRef =
                Xpdl2WorkingCopyImpl.createExternalPackage(WorkingCopyUtil
                        .getWorkingCopyFor(pkg));
        if (ourPackageRef != null) {
            ourPackageLocation =
                    Xpdl2WorkingCopyImpl
                            .getExternalPackageLocation(ourPackageRef);
        }
        return ourPackageLocation;
    }

    /**
     * This is a useful method for creating an empty package outside the normal
     * wizard i.e. programatically and can be used for load testing etc
     * 
     * @param container
     * @param packageFileName
     * @param packageName
     * @return
     */
    // Ravi-Need to check why this was invoked.
    /*
     * public static EObject createEmptyPackage(IContainer container,final
     * String packageFileName,final String packageName){ EObject emptyPackage =
     * null; JetTemplateWizard wizard = new EmptyPackage();
     * 
     * ITemplateInputProvider templateInputProvider = new
     * ITemplateInputProvider() { public String getInput(String key) { String
     * value = null;
     * 
     * if (key != null) { if (key.equals(PACKAGE_FILENAME)) { value =
     * packageFileName; } else if (key.equals(PACKAGE_NAME)) { value =
     * packageName; } else if (key.equals(PACKAGE_DESCRIPTION)) { value = "";
     * //$NON-NLS-1$ } else if (key.equals(DOCUMENTATION)) { value = "";
     * //$NON-NLS-1$ } else if (key.equals(AUTHOR)) { value =
     * System.getProperty("user.name"); //$NON-NLS-1$ } else if
     * (key.equals(STATUS)) { value = Xpdl2ResourcesConsts.DEFAULT_STATUS; }
     * else if (key.equals(BUSINESS_VERSION)) { value =
     * Xpdl2ResourcesConsts.DEFAULT_BUSINESS_VERSION; } else if
     * (key.equals(CREATED)) { value = DateUtils.getCurrentLocaleDate(); } else
     * if (key.equals(VENDOR)) { value = Messages.NewPackageWizard_VENDOR; }
     * else if (key.equals(FORMAT_VERSION)) { value =
     * XpdlMigrate.FORMAT_VERSION_ATT_VALUE; } }
     * 
     * return value; } };
     * 
     * wizard.setInputProvider(templateInputProvider); try {
     * wizard.performFinish(container);
     * 
     * Object createdObj = wizard.getCreatedObject();
     * 
     * if (createdObj instanceof EObject) { emptyPackage = (EObject)createdObj;
     * } } catch (CoreException e) { e.printStackTrace(); }
     * 
     * return emptyPackage; }
     */

    /**
     * This method wraps the project in an instance of {@link ProjectReference}
     * and adds it to the list , if it does not already contains one.
     * 
     * @param projectReferencesList
     * @param sourceProject
     * @param projectReference
     */
    public static void addProjectReference(
            Collection<ProjectReference> projectReferencesList, Object object) {
        IProject project = null;
        if (object instanceof IFile) {
            project = ((IFile) object).getProject();
        } else if (object instanceof EObject) {
            project = WorkingCopyUtil.getProjectFor((EObject) object);
        }
        if (project != null) {
            ProjectReference projectReference = new ProjectReference(project);
            /*
             * if the list does not already contain the project ref
             */
            if (!projectReferencesList.contains(projectReference)) {
                projectReferencesList.add(projectReference);
            }
        }
    }

    /**
     * @param filteredOtherObjects
     * @param filteredProjectRef
     * @param pasteObjects
     */
    public static void filterProjectReferencesAndOtherObjects(
            Collection<Object> filteredOtherObjects,
            Collection<ProjectReference> filteredProjectRef,
            Collection pasteObjects) {
        /*
         * filter out special project references and paste objects in seperate
         * collections XPD-4442: multiple objects from same project should be
         * listed only once in dialogue for adding project references.
         */
        HashMap<IProject, ProjectReference> projectRefs =
                new HashMap<IProject, ProjectReference>();

        for (Object object : pasteObjects) {
            if (object instanceof ProjectReference) {
                projectRefs.put(((ProjectReference) object).getProject(),
                        (ProjectReference) object);
                // filteredProjectRef.add((ProjectReference) object);
            } else {
                filteredOtherObjects.add(object);
            }
        }
        filteredProjectRef.addAll(projectRefs.values());
    }

    public static class ProjectReference {

        private IProject project;

        /**
         * @param project
         */
        public ProjectReference(IProject project) {
            super();
            this.project = project;
        }

        /**
         * @return the project
         */
        public IProject getProject() {
            return project;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         * 
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            boolean ret = false;
            if (this == obj) {
                return true;
            }
            if (obj instanceof ProjectReference) {
                ProjectReference otherProjectRef = (ProjectReference) obj;
                if (project == null) {
                    ret = (otherProjectRef.getProject() == null);
                } else {
                    ret = project.equals(otherProjectRef.getProject());
                }
            }
            return ret;
        }

        /**
         * @see java.lang.Object#hashCode()
         * 
         * @return
         */
        @Override
        public int hashCode() {
            return project.hashCode();
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return project.toString();
        }
    }
}
