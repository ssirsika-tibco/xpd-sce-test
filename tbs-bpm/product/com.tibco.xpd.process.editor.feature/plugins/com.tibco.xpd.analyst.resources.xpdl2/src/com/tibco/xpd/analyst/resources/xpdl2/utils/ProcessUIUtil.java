package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.ProcessParticipantResourceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.indexing.ProcessToBOMIndexerProvider.ProcessToBomType;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.BaseFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.WsdlWorkingCopy;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskManual;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskReference;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ProcessUIUtil {

    /** Extension for the WSDL file. */
    private static final String WSDL_EXTENSION = Activator.WSDL_FILE_EXTENSION;

    private static final String BOM_SPECIAL_FOLDER_KIND =
            BOMResourcesPlugin.BOM_FILE_EXTENSION;

    public static final String PROCESS_PACKAGE_SEPARATOR = "/"; //$NON-NLS-1$    

    private static ComplexDataTypesMergedInfo _complexTypesInfo;

    public static final String INTERNAL_PROJECT = "internal_project"; //$NON-NLS-1$

    private static final String URI_DELIMITER = "?"; //$NON-NLS-1$

    /**
     * @param modelElement
     * @return
     */
    public static String getURIString(EObject modelElement,
            boolean appendFragment) {
        URI uri = ProcessUIUtil.getURI(modelElement, appendFragment);
        String uriToString = null;
        if (uri != null) {
            uriToString = uri.toString();
        }
        return uriToString;
    }

    /**
     * @param modelElement
     * @param appendFragment
     * @return
     */
    public static URI getURI(EObject modelElement, boolean appendFragment) {
        URI uri = null;
        if (modelElement != null) {
            Resource modelElementResource = modelElement.eResource();
            uri = modelElementResource.getURI();
            if (uri != null && appendFragment) {
                uri =
                        uri.appendFragment(modelElementResource
                                .getURIFragment(modelElement));
            }
        }
        return uri;
    }

    /**
     * This method generated the qualified name for an activity
     * 
     * @param eo
     * @return
     */
    public static String getActivityQualifiedName(Activity act) {
        String activityQualifiedName = act.getName();
        Process process = Xpdl2ModelUtil.getProcess(act);
        if (process != null) {
            activityQualifiedName =
                    ProcessUIUtil.getProcessQualifiedName(process)
                            + PROCESS_PACKAGE_SEPARATOR + act.getName();
        }
        return activityQualifiedName;
    }

    /**
     * This method generated the qualified name for a process
     * 
     * @param eo
     * @return
     */
    public static String getProcessQualifiedName(EObject eo) {
        String containerFolder = Messages.ProcessGroup_TITLE;
        return getQualifiedName(eo, containerFolder);
    }

    /**
     * This method generated the qualified name for a process interface
     * 
     * @param eo
     * @return
     */
    public static String getProcessInterfaceQualifiedName(EObject eo) {
        String containerFolder = Messages.ProcessInterfaceGroup_Group_title;
        return getQualifiedName(eo, containerFolder);
    }

    /**
     * This method generated the qualified name for a data field
     * 
     * @param eo
     * @return
     */
    public static String getDataFieldQualifiedName(EObject eo) {
        String containerFolder = Messages.DataFieldGroup_TITLE;
        return getQualifiedName(eo, containerFolder);
    }

    /**
     * This method generated the qualified name for a participant
     * 
     * @param eo
     * @return
     */
    public static String getParticipantQualifiedName(EObject eo) {
        String containerFolder = Messages.ParticipantGroup_TITLE;
        return getQualifiedName(eo, containerFolder);
    }

    /**
     * This method generated the qualified name for a formal parameter
     * 
     * @param eo
     * @return
     */
    public static String getFormalParameterQualifiedName(EObject eo) {
        String containerFolder = Messages.FormalParameterGroup_TITLE;
        return getQualifiedName(eo, containerFolder);
    }

    /**
     * This method generated the qualified name for an eObject
     * 
     * @param eo
     * @param containerFolder
     * @return
     */
    public static String getQualifiedName(EObject eo, String containerFolder) {

        StringBuffer qualifiedName = new StringBuffer();
        if (eo != null && eo instanceof NamedElement) {
            qualifiedName.append(((NamedElement) eo).getName());
            if (containerFolder != null) {
                qualifiedName.insert(0, PROCESS_PACKAGE_SEPARATOR);
                qualifiedName.insert(0, containerFolder);
            }
        }
        EObject eContainer = eo.eContainer();
        while (eContainer instanceof EObject) {
            if (eContainer instanceof NamedElement) {
                qualifiedName.insert(0, PROCESS_PACKAGE_SEPARATOR);
                qualifiedName.insert(0, ((NamedElement) eContainer).getName());
            }
            eContainer = eContainer.eContainer();
        }
        return qualifiedName.toString();
    }

    /**
     * This method returns the eObject associated with the passed URI.
     * 
     * 
     * @param uri
     *            URI
     * @return the EObject
     */
    public static EObject getEObjectFrom(URI uri) {
        EObject eObject = null;
        if (uri != null) {
            if (uri.isFile()) {
                eObject = getEObjectFrom(uri.toFileString(), uri.fragment());
            } else {
                eObject =
                        getEObjectFrom(uri.toPlatformString(true),
                                uri.fragment());
            }
        }
        return eObject;
    }

    /**
     * This method returns the eObject associated with the passed URI and the
     * given fragment.
     * 
     * 
     * @param uri
     *            URI
     * @param fragment
     *            fragment
     * @return the EObject
     */
    public static EObject getEObjectFrom(String uri, String fragment) {
        // Get the resource
        IResource resource =
                ResourcesPlugin.getWorkspace().getRoot().findMember(uri);
        WorkingCopy wc = null;
        if (resource != null) {
            wc = XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (wc != null) {
                Package aPackage = (Package) wc.getRootElement();
                if (aPackage != null) {
                    return aPackage.findNamedElement(fragment);
                }
            }
        }
        return null;
    }

    /**
     * This method returns the eObject associated with the passed URI and in the
     * given scope.
     * 
     * 
     * @param eo
     *            EObject
     * @return editing domain
     */
    public static EObject getEObjectFrom(URI uri, EObject scope) {
        EObject eObject = null;
        if (scope != null && uri != null) {
            Package xpdlPackage = Xpdl2ModelUtil.getPackage(scope);
            if (xpdlPackage != null) {
                eObject = xpdlPackage.findNamedElement(uri.fragment());
            }
        }
        return eObject;
    }

    /**
     * @param wc
     * @return
     */
    public static String createPath(WorkingCopy wc) {
        IResource resource = wc.getEclipseResources().get(0);
        String path =
                resource.getProject().getName()
                        + "_" + resource.getProjectRelativePath().toPortableString(); //$NON-NLS-1$
        return path;
    }

    public static List<IResource> getResourcesForLocation(IProject project,
            String location, String specialFolderKind) {
        List<IResource> resources = new ArrayList<IResource>();
        // Look first in the local project
        if (project != null) {
            IResource resource =
                    SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                            specialFolderKind,
                            location);
            if (resource != null) {
                resources.add(resource);
            }

            try {
                Set<IProject> refProjects =
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                true);

                for (IProject refProject : refProjects) {
                    resource =
                            SpecialFolderUtil
                                    .resolveSpecialFolderRelativePath(refProject,
                                            specialFolderKind,
                                            location);
                    if (resource != null) {
                        resources.add(resource);
                    }
                }
            } catch (CyclicDependencyException e) {
                // Do nothing
            }
        }
        return resources;
    }

    /**
     * Get the selected element from the picker. If the parent project of the
     * element is not the same project as the host project then it will check
     * the project reference.
     * 
     * @return <code>EObject</code> if an element was selected and the project
     *         it's from is the same as host project or is referenced.
     */
    public static EObject getResultFromPicker(BaseFilterPicker picker,
            Shell shell, EObject context) {
        EObject theResult = null;

        if (context == null) {
            throw new NullPointerException("input is null"); //$NON-NLS-1$
        }

        // ProcessFilterPicker picker = getPicker();
        if (picker == null) {
            throw new NullPointerException("picker is null"); //$NON-NLS-1$
        }

        // Show picker
        if (picker.open() == ProcessFilterPicker.OK) {
            Object result = picker.getFirstResult();

            if (result instanceof EObject) {
                theResult = (EObject) result;

                if (!checkAndAddProjectReference(shell, context, theResult)) {
                    theResult = null;
                }
            }
        }
        return theResult;
    }

    /**
     * If the given referencedObject is not in same project as contextObject and
     * not in a project already referenced by the contextObject's project then
     * ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param contextObject
     * @param referencedObject
     * 
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            EObject contextObject, EObject referencedObject) {

        return ProjectUtil.checkAndAddProjectReference(shell,
                contextObject,
                referencedObject);
    }

    /**
     * If the given referencedObject is not in same project as contextObject and
     * not in a project already referenced by the contextObject's project then
     * ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param ret
     * @param contextProject
     * @param elementProject
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            IProject contextProject, IProject elementProject) {

        return ProjectUtil.checkAndAddProjectReference(shell,
                contextProject,
                elementProject);
    }

    /**
     * If the given project in the required reference list, is not already
     * referenced by contextProject then ask user if they wish to create the
     * project reference.Show a warning if any of the required references can
     * lead to cyclic dependency.
     * 
     * If the user selected "Yes" then the project reference is added.
     * 
     * @param shell
     * @param ret
     * @param contextProject
     * @param projectRefListToAdd
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReference(Shell shell,
            IProject contextProject, Collection<IProject> projectRefListToAdd) {

        return ProjectUtil.checkAndAddProjectReference(shell,
                contextProject,
                projectRefListToAdd);

    }

    /**
     * If the given referencedObjects are not in same project as contextObject
     * and not in a project already referenced by the contextObject's project
     * then ask user if they wish to create the project reference.
     * <p>
     * If the user selected "Yes" then the project reference is added.
     * <p>
     * The user is asked a maximum of once per referenced project.
     * 
     * @param shell
     * @param contextObject
     * @param referencedObject
     * 
     * @return false if project reference required and user declined to create
     *         it true if all ok.
     */
    public static boolean checkAndAddProjectReferences(Shell shell,
            EObject contextObject, Collection<EObject> referencedObjects) {

        return ProjectUtil.checkAndAddProjectReferences(shell,
                contextObject,
                referencedObjects);
    }

    /**
     * This method uses the indexer to return the process packages in the
     * indexer that match the given Id
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     * 
     */
    public static List<EObject> getAllProcesses(String id) {
        List<EObject> elements = new ArrayList<EObject>();
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);
        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessResourceItemType.PROCESS.toString(), null,
                        additionalAttributes);
        elements =
                getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                        criteria);
        return elements;
    }

    /**
     * This method uses the indexer to return the process matching the given
     * process id
     * 
     * @return Process or <code>null</code> if process not found.
     * 
     */
    public static Process getProcesById(String id) {

        List<EObject> procsForId = ProcessUIUtil.getAllProcesses(id);

        if (procsForId.size() > 0) {
            EObject process = procsForId.get(0);
            if (process instanceof Process) {
                return (Process) process;
            }
        }

        return null;
    }

    /**
     * This method uses the indexer to return the process packages in the
     * indexer that match the given Id
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     * 
     */
    public static List<EObject> getAllProcessPackages(String id) {
        List<EObject> elements = new ArrayList<EObject>();
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);
        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessResourceItemType.PROCESSPACKAGE.toString(),
                        null, additionalAttributes);
        elements =
                getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                        criteria);
        return elements;
    }

    /**
     * This method uses the indexer to return the elements in the indexer that
     * match the given Id
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     * 
     */
    public static List<EObject> getAllElements(String id) {
        List<EObject> elements = new ArrayList<EObject>();
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        elements =
                getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                        criteria);
        return elements;
    }

    public static List<EObject> getAllProcessIndexedElements(
            ProcessResourceItemType type) {
        List<EObject> elements = new ArrayList<EObject>();

        IndexerItem criteria = new IndexerItemImpl(null, null, null, null);

        elements =
                getIndexedElements(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                        criteria);
        return elements;
    }

    /**
     * @return The list of indexed process related items (currently
     *         processes/process interfaces/packages but later may be expanded
     *         to include data fields and so on).
     */
    public static Collection<IndexerItem> getAllProcessIndexerItems() {
        IndexerItem criteria = new IndexerItemImpl(null, null, null, null);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_INDEXER_ID,
                                criteria);

        if (result == null) {
            result = Collections.EMPTY_LIST;
        }

        return result;
    }

    /**
     * @return The list of indexed task library related items (currently Task
     *         Libraries / Task Sets / Tasks but later may be expanded to
     *         include data fields and so on).
     */
    public static Collection<IndexerItem> getAllTaskLibraryIndexerItems() {
        IndexerItem criteria = new IndexerItemImpl(null, null, null, null);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);

        if (result == null) {
            result = Collections.EMPTY_LIST;
        }

        return result;
    }

    /**
     * Get the display label name for the given process related indexerItem
     * 
     * @param indexerItem
     * @return
     */
    public static String getLabelForIndexedItem(IndexerItem indexerItem) {
        String label = null;

        if (indexerItem != null) {
            label =
                    indexerItem
                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);

            if (label == null || label.length() == 0) {
                label = indexerItem.getName();
            }
        }

        return label == null ? "" : label; //$NON-NLS-1$
    }

    /**
     * Get the icon image for the given process related indexerItem
     * 
     * @param indexerItem
     * @return
     */
    public static Image getImageForIndexedItem(IndexerItem indexerItem) {
        Image img = null;

        if (indexerItem != null) {
            String uri =
                    indexerItem.get(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI);

            if (uri != null) {
                URI imageUri = URI.createURI(uri);

                if (imageUri != null) {
                    img =
                            ExtendedImageRegistry.getInstance()
                                    .getImage(imageUri);
                }
            }
        }

        return img;
    }

    /**
     * This method uses the indexer to return the elements in the indexer that
     * match the given criteria
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     * 
     */
    public static List<EObject> getIndexedElements(String indexId,
            IndexerItem criteria) {
        List<EObject> elements = new ArrayList<EObject>();
        if (criteria == null) {
            criteria = new IndexerItemImpl(null, null, null, null);
        }
        // Get the index items from the indexer
        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(indexId, criteria);
        if (result != null && !result.isEmpty()) {
            // Get the eObjects for the indexer items
            for (IndexerItem item : result) {
                if (item != null) {
                    String struri = item.getURI();
                    URI uri = URI.createURI(struri);

                    if (uri != null) {
                        EObject eo = ProcessUIUtil.getEObjectFrom(uri);
                        if (eo != null) {
                            elements.add(eo);
                        }
                    }
                }
            }
        }
        return elements;
    }

    public static URI getImageURI(Activity activity) {
        String imageKey = Xpdl2ResourcesConsts.IMG_TASK_ICON;
        Event event = activity.getEvent();
        if (event != null) {
            if (event instanceof StartEvent
                    || event instanceof IntermediateEvent) {
                TriggerType trigger;
                if (event instanceof StartEvent) {
                    trigger = ((StartEvent) event).getTrigger();
                } else {
                    trigger = ((IntermediateEvent) event).getTrigger();
                }

                switch (trigger.getValue()) {
                case TriggerType.CANCEL:
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_CANCEL_INTERMEDIATE_EVENT_ICON;
                    break;
                case TriggerType.COMPENSATION:
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_CONPENSATION_INTERMEDIATE_EVENT_ICON;
                    break;
                case TriggerType.ERROR:
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_ERROR_INTERMEDIATE_EVENT_ICON;
                    break;
                case TriggerType.LINK:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_LINK_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_LINK_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                case TriggerType.MESSAGE:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_MESSAGE_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_MESSAGE_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                case TriggerType.MULTIPLE:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_MULTIPLE_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_MULTIPLE_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                case TriggerType.CONDITIONAL:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_RULE_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_RULE_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                case TriggerType.TIMER:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_TIMER_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_TIMER_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                case TriggerType.SIGNAL:
                    if (event instanceof StartEvent) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_START_EVENT_SIGNAL_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_INTERMEDIATE_EVENT_ICON;
                    }
                    break;
                default:
                    if (event instanceof StartEvent) {
                        imageKey = Xpdl2ResourcesConsts.IMG_START_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_INTERMEDIATE_EVENT_ICON;
                    }
                }

            } else if (event instanceof EndEvent) {
                ResultType result = ((EndEvent) event).getResult();

                switch (result.getValue()) {
                case ResultType.CANCEL:
                    imageKey = Xpdl2ResourcesConsts.IMG_CANCEL_END_EVENT_ICON;
                    break;
                case ResultType.COMPENSATION:
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_CONPENSATION_END_EVENT_ICON;
                    break;
                case ResultType.ERROR:
                    imageKey = Xpdl2ResourcesConsts.IMG_ERROR_END_EVENT_ICON;
                    break;
                case ResultType.SIGNAL:
                    if (isGlobalSignalEvent(activity)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_END_EVENT_SIGNAL_ICON;
                    } else {
                        imageKey = Xpdl2ResourcesConsts.IMG_LINK_END_EVENT_ICON;
                    }
                    break;
                case ResultType.MESSAGE:
                    imageKey = Xpdl2ResourcesConsts.IMG_MESSAGE_END_EVENT_ICON;
                    break;
                case ResultType.MULTIPLE:
                    imageKey = Xpdl2ResourcesConsts.IMG_MULTIPLE_END_EVENT_ICON;
                    break;
                case ResultType.TERMINATE:
                    imageKey =
                            Xpdl2ResourcesConsts.IMG_TERMINATE_END_EVENT_ICON;
                    break;
                default:
                    imageKey = Xpdl2ResourcesConsts.IMG_END_EVENT_ICON;
                }
            }
        } else if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();
            TaskUser taskUser = task.getTaskUser();
            if (taskUser != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_USER_ICON;
            }
            TaskManual taskManual = task.getTaskManual();
            if (taskManual != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_MANUAL_ICON;
            }
            TaskService taskService = task.getTaskService();
            if (taskService != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_SERVICE_ICON;
            }
            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_SEND_ICON;
            }
            TaskReceive taskReceive = task.getTaskReceive();
            if (taskReceive != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_RECEIVE_ICON;
            }
            TaskReference taskReference = task.getTaskReference();
            if (taskReference != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_REFERENCE_ICON;
            }
            TaskScript taskScript = task.getTaskScript();
            if (taskScript != null) {
                imageKey = Xpdl2ResourcesConsts.IMG_TASK_SCRIPT_ICON;
            }
        } else if (activity.getImplementation() instanceof Reference) {
            imageKey = Xpdl2ResourcesConsts.IMG_TASK_REFERENCE_ICON;
        } else if (activity.getImplementation() instanceof SubFlow) {
            imageKey = Xpdl2ResourcesConsts.IMG_SUBPROC_ICON;
        } else if (activity.getBlockActivity() != null) {
            /* XPD-6799 : Update to use icon for Event Subprocess */
            boolean isEventSubProcess =
                    Xpdl2ModelUtil.isEventSubProcess(activity);

            if (isEventSubProcess) {
                imageKey = Xpdl2ResourcesConsts.IMG_EVENT_SUBPROC_ENBEDDED_ICON;
            } else {
                imageKey = Xpdl2ResourcesConsts.IMG_SUBPROC_ENBEDDED_ICON;
            }

        } else {
            Route route = activity.getRoute();
            if (route != null) {
                Xpdl2ModelUtil.safeGetGatewayType(activity);

                JoinSplitType type =
                        Xpdl2ModelUtil.safeGetGatewayType(activity);
                ExclusiveType exclusiveType =
                        Xpdl2ModelUtil.safeGetExclusiveType(activity);
                switch (type.getValue()) {
                case JoinSplitType.EXCLUSIVE:
                    if (ExclusiveType.EVENT.equals(exclusiveType)) {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_GATEWAY_XOR_EVENT_ICON;
                    } else {
                        imageKey =
                                Xpdl2ResourcesConsts.IMG_GATEWAY_XOR_DATA_ICON;
                    }
                    break;
                case JoinSplitType.INCLUSIVE:
                    imageKey = Xpdl2ResourcesConsts.IMG_GATEWAY_OR_ICON;
                    break;
                case JoinSplitType.COMPLEX:
                    imageKey = Xpdl2ResourcesConsts.IMG_GATEWAY_COMPLEX_ICON;
                    break;
                case JoinSplitType.PARALLEL:
                    imageKey = Xpdl2ResourcesConsts.IMG_GATEWAY_PARALLEL_ICON;
                    break;
                }
            }
        }
        URI imageURI =
                URI.createPlatformPluginURI(Xpdl2ResourcesPlugin.PLUGIN_ID
                        + "/" //$NON-NLS-1$
                        + imageKey, true);
        return imageURI;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an is an Global
     *         signal event.
     */
    private static boolean isGlobalSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {
                    SignalType sigType = (SignalType) otherAttribute;
                    return SignalType.GLOBAL.equals(sigType) ? true : false;
                }
            }
        }
        return false;
    }

    /**
     * This method returns BOM external references for a process element,
     * Duplicates are eliminated
     * 
     * @param element
     * @return List of external references
     **/
    public static List<ExternalReference> getBOMExternalReferences(
            EObject element) {

        if (element != null) {
            List<ProcessRelevantData> externalPrdList =
                    ProcessInterfaceUtil
                            .getAllExternalProcessRelevantData(element);

            if (externalPrdList != null && !externalPrdList.isEmpty()) {
                List<ExternalReference> externalReferences =
                        new ArrayList<ExternalReference>();

                for (ProcessRelevantData externalPrd : externalPrdList) {

                    ExternalReference externalReference = null;

                    if (externalPrd.getDataType() instanceof ExternalReference) {

                        externalReference =
                                (ExternalReference) externalPrd.getDataType();

                    } else if (externalPrd.getDataType() instanceof RecordType) {

                        RecordType recordType =
                                (RecordType) externalPrd.getDataType();
                        EList<Member> memberList = recordType.getMember();
                        Member member = memberList.get(0);

                        externalReference = member.getExternalReference();
                    }

                    if (null != externalReference) {

                        if (isValidExternalReference(externalReference)
                                && !ProcessUIUtil
                                        .containsExternalReference(externalReferences,
                                                externalReference)) {
                            externalReferences.add(externalReference);
                        }
                    }
                }
                return externalReferences;
            }
        }
        return Collections.emptyList();
    }

    /**
     * This method checks if the external reference has the correct information,
     * Note that it does not verify the external reference.
     * 
     * @param externalReference
     * @return if the external reference contains correct information
     **/
    public static boolean isValidExternalReference(
            ExternalReference externalReference) {
        if (externalReference != null
                && externalReference.getXref() != null
                && !externalReference.getXref().equals("")//$NON-NLS-1$
                && externalReference.getLocation() != null
                && !externalReference.getLocation().equals("")) {//$NON-NLS-1$ 
            return true;
        }
        return false;
    }

    /**
     * This method checks if the external reference has the correct information,
     * Note that it does not verify the external reference.
     * 
     * @param xref
     * @param location
     * @return if the external reference contains correct information
     **/
    public static boolean isValidExternalReference(String xref,
            String location, String namespace) {
        if (xref != null && !xref.equals("")//$NON-NLS-1$ 
                && location != null && !location.equals("")//$NON-NLS-1$
                && namespace != null && !namespace.equals("")) {//$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * This method checks if the passed list of existingExternalReferences
     * contain the newExternalReference.
     * 
     * @param xref
     * @param location
     * @return if the list of existingExternalReferences contain the
     *         newExternalReference
     **/
    private static boolean containsExternalReference(
            List<ExternalReference> existingExternalReferences,
            ExternalReference newExternalReference) {
        if (existingExternalReferences != null
                && !existingExternalReferences.isEmpty()
                && newExternalReference != null) {
            for (ExternalReference externalReference : existingExternalReferences) {
                if (isValidExternalReference(externalReference)) {
                    if (externalReference.getXref()
                            .equals(newExternalReference.getXref())
                            && externalReference.getLocation()
                                    .equals(newExternalReference.getLocation())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method gives a list of resolved referenced classifiers for a given
     * xpdl element.
     * 
     * @param element
     * 
     * @return Set<Classifier>
     **/
    public static Set<Classifier> getReferencedClassifiers(EObject element) {
        if (element != null) {
            IProject project = WorkingCopyUtil.getProjectFor(element);
            Set<IProject> referencedProjects = null;
            if (project != null) {
                Set<Classifier> referencedClasifiers =
                        new HashSet<Classifier>();
                List<ExternalReference> externalReferences =
                        ProcessUIUtil.getBOMExternalReferences(element);
                if (externalReferences != null && !externalReferences.isEmpty()) {
                    for (ExternalReference externalReference : externalReferences) {
                        if (_complexTypesInfo == null) {
                            // We only call this once, as it's relatively
                            // expensive.
                            _complexTypesInfo =
                                    ComplexDataTypeExtPointHelper
                                            .getAllComplexDataTypesMergedInfo();
                        }
                        ComplexDataTypeReference complexDataTypeRef =
                                xpdl2RefToComplexDataTypeRef(externalReference);
                        if (complexDataTypeRef != null) {
                            Object objectForDataType =
                                    _complexTypesInfo
                                            .getComplexDataTypeFromReference(complexDataTypeRef,
                                                    project);
                            if (objectForDataType == null) {
                                // It might be in an external project
                                referencedProjects =
                                        ProjectUtil
                                                .getReferencedProjectsHierarchy(project,
                                                        new HashSet<IProject>());
                                if (referencedProjects != null
                                        && !referencedProjects.isEmpty()) {
                                    for (IProject referencedProject : referencedProjects) {
                                        if (referencedProject != null) {
                                            objectForDataType =
                                                    _complexTypesInfo
                                                            .getComplexDataTypeFromReference(complexDataTypeRef,
                                                                    referencedProject);
                                            if (objectForDataType instanceof Classifier) {
                                                referencedClasifiers
                                                        .add((Classifier) objectForDataType);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (objectForDataType instanceof Classifier) {
                                referencedClasifiers
                                        .add((Classifier) objectForDataType);
                            }
                        }
                    }
                    return referencedClasifiers;
                }
            }
        }
        return Collections.emptySet();
    }

    /**
     * This method converts a xpdl2 ExternalReference to complex data type
     * extension point ComplexDataTypeReference
     * 
     * @param extRef
     * 
     * @return ComplexDataTypeReference
     **/
    public static ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {
        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some info defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }
        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    /**
     * This method is used to get a uri combined of a process model element and
     * a BOM external reference
     * 
     * @param modelElement
     * @return
     */
    public static String getProcessToBOMURIString(EObject processModelElement,
            ExternalReference externalReferenceElement) {
        String processToBOMUriString = null;
        URI uri = ProcessUIUtil.getURI(processModelElement, true);
        String processUriToString = null;
        if (uri != null) {
            processUriToString = uri.toString();
        }
        String bomUriToString =
                externalReferenceElement.getLocation()
                        + externalReferenceElement.getXref();
        if (processUriToString != null && bomUriToString != null) {
            processToBOMUriString = processUriToString + "_" + bomUriToString;//$NON-NLS-1$
        }
        return processToBOMUriString;
    }

    public static boolean isValidComplexDataType(EObject element) {
        if (element != null) {
            if (_complexTypesInfo == null) {
                // We only call this once, as it's relatively
                // expensive.
                _complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();
            }
            return _complexTypesInfo.isValidComplexDataType(element);
        }
        return false;
    }

    public static ComplexDataTypeReference resolveComplexDataTypeReference(
            EObject element, IProject project) {
        ComplexDataTypeReference reference = null;
        if (element != null && project != null) {
            if (_complexTypesInfo == null) {
                // We only call this once, as it's relatively
                // expensive.
                _complexTypesInfo =
                        ComplexDataTypeExtPointHelper
                                .getAllComplexDataTypesMergedInfo();
            }
            reference =
                    _complexTypesInfo.getComplexDataTypeReference(element,
                            project);
        }
        return reference;
    }

    public static Collection<IndexerItem> getAllProcessToBomIndexerItems() {
        IndexerItem criteria = new IndexerItemImpl(null, null, null, null);
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                criteria);
        return result;
    }

    public static String resolveExternalBomClassName(String refId,
            String location, String namespace) {
        // Query the indexer
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID,
                refId);
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                location);
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE,
                namespace);
        IndexerItem criteria =
                new IndexerItemImpl(null, ResourceItemType.CLASS.toString(),
                        null, additionalAttributes);
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                criteria);
        if (result != null && result.iterator().hasNext()) {
            IndexerItem selectedItem = result.iterator().next();
            String fqName = selectedItem.getName();
            if (fqName != null) {
                int dotIndex = fqName.lastIndexOf(".");//$NON-NLS-1$
                if (dotIndex == -1) {
                    dotIndex = 0;
                } else {
                    dotIndex = dotIndex + 1;
                }
                if (fqName.length() > dotIndex) {
                    String name = fqName.substring(dotIndex, fqName.length());
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * This method is to get all the bom files that are referenced by a given
     * xpdl file
     * 
     * @param xpdlFileLocation
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    public static Set<String> queryReferencedBoms(String xpdlFileLocation,
            boolean includeUnresolvedReferences) {
        if (xpdlFileLocation != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION,
                            xpdlFileLocation);
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            Set<String> allReferencedBoms = new HashSet<String>();
            for (IndexerItem indexerItem : xpdlResults) {
                if (indexerItem != null
                        && indexerItem
                                .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION) != null) {
                    String bomUri =
                            indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                    allReferencedBoms.add(bomUri);
                }
            }
            if (includeUnresolvedReferences) {
                return allReferencedBoms;
            } else {
                Set<String> referencedBoms = new HashSet<String>();
                for (String referencedBomLocation : allReferencedBoms) {
                    if (referencedBomLocation != null) {
                        // Query the indexer
                        additionalAttributes = new HashMap<String, String>();
                        additionalAttributes
                                .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                        referencedBomLocation);
                        additionalAttributes
                                .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                        ProcessToBomType.BOM.name());
                        criteria =
                                new IndexerItemImpl(null, null, null,
                                        additionalAttributes);
                        Collection<IndexerItem> bomResults =
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getIndexerService()
                                        .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                                criteria);
                        if (bomResults != null && !bomResults.isEmpty()) {
                            referencedBoms.add(referencedBomLocation);
                        }
                    }
                }
                return referencedBoms;
            }
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the bom files that are referenced by a given
     * process
     * 
     * @param process
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    public static Set<String> queryReferencedBoms(Process process,
            boolean includeUnresolvedReferences) {
        URI processUri = getURI(process, true);
        if (processUri != null) {
            return queryReferencedBoms(processUri, includeUnresolvedReferences);
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the bom files that are referenced by a given
     * package
     * 
     * @param xpdlPackage
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    public static Set<String> queryReferencedBoms(Package xpdlPackage,
            boolean includeUnresolvedReferences) {
        URI packageUri = getURI(xpdlPackage, true);
        if (packageUri != null) {
            return queryReferencedBoms(packageUri, includeUnresolvedReferences);
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the bom files that are referenced by a given
     * xpdl Element uri
     * 
     * @param xpdlElementUri
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    private static Set<String> queryReferencedBoms(URI xpdlElementUri,
            boolean includeUnresolvedReferences) {
        if (xpdlElementUri != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI,
                            xpdlElementUri.toString());
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            Set<String> allReferencedBoms = new HashSet<String>();
            for (IndexerItem indexerItem : xpdlResults) {
                if (indexerItem != null
                        && indexerItem
                                .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION) != null) {
                    String bomUri =
                            indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                    allReferencedBoms.add(bomUri);
                }
            }
            if (includeUnresolvedReferences) {
                return allReferencedBoms;
            } else {
                Set<String> referencedBoms = new HashSet<String>();
                for (String referencedBomLocation : allReferencedBoms) {
                    if (referencedBomLocation != null) {
                        // Query the indexer
                        additionalAttributes = new HashMap<String, String>();
                        additionalAttributes
                                .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                        referencedBomLocation);
                        additionalAttributes
                                .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                        ProcessToBomType.BOM.name());
                        criteria =
                                new IndexerItemImpl(null, null, null,
                                        additionalAttributes);
                        Collection<IndexerItem> bomResults =
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getIndexerService()
                                        .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                                criteria);
                        if (bomResults != null && !bomResults.isEmpty()) {
                            referencedBoms.add(referencedBomLocation);
                        }
                    }
                }
                return referencedBoms;
            }
        }
        return Collections.emptySet();
    }

    /**
     * Returns a set of BOM {@link IResource}s that are referenced by a given
     * XPDL file.
     * 
     * @param xpdlFileLocation
     *            XPDL file location
     * @param includeUnresolvedReferences
     * @return
     */
    public static Set<IResource> queryReferencingBomResources(IProject project,
            String xpdlFileLocation, boolean includeUnresolvedReferences) {

        String platformSpecificXpdlFileLocation =
                getPlatformSpecificXpdlFileLocation(xpdlFileLocation);
        Set<String> queryReferencedBoms =
                queryReferencedBoms(platformSpecificXpdlFileLocation,
                        includeUnresolvedReferences);

        Set<IResource> bomResources = new HashSet<IResource>();
        for (String bomLocation : queryReferencedBoms) {
            IResource bomResource =
                    getResourceInSpecialFolder(project,
                            bomLocation,
                            BOM_SPECIAL_FOLDER_KIND);
            if (bomResource == null) {
                Set<IProject> referencedProjects = new HashSet<IProject>();
                try {
                    /*
                     * SDA-166 and SDA-167: instead of getting the referenced
                     * projects from IProject get it from the util
                     */
                    referencedProjects =
                            ProjectUtil2
                                    .getReferencedProjectsHierarchy(project,
                                            true);
                } catch (CyclicDependencyException e) {
                    // do nothing
                }
                for (IProject referencedProject : referencedProjects) {
                    bomResource =
                            getResourceInSpecialFolder(referencedProject,
                                    bomLocation,
                                    BOM_SPECIAL_FOLDER_KIND);
                    if (bomResource != null) {
                        break;
                    }
                }
            }
            if (bomResource != null) {
                bomResources.add(bomResource);
            }
        }
        return bomResources;
    }

    /**
     * Returns IResource form project's special folder for specified special
     * folder relative path or <code>null</code> if it doesn't exist.
     * 
     * @param project
     *            the context project.
     * @param sfLocation
     *            special folder relative location.
     * @param kind
     *            the kind of special folder to search.
     * @return IResource from special folder or <code>null</code> if it doesn't
     *         exist.
     */
    private static IResource getResourceInSpecialFolder(IProject project,
            String sfLocation, String kind) {
        List<SpecialFolder> sFolders =
                SpecialFolderUtil.getAllSpecialFoldersOfKind(project, kind);
        if (sFolders != null) {
            for (SpecialFolder spFolder : sFolders) {
                IFolder resFolder = spFolder.getFolder();
                if (resFolder == null || !resFolder.isAccessible()) {
                    continue;
                }
                IResource member = resFolder.findMember(sfLocation);
                if (member != null && member.exists()) {
                    return member;
                }
            }
        }
        return null;
    }

    /**
     * Returns WSDL dependencies for a given XPDL file.
     * 
     * @param xpdlIResource
     *            context XPDL resource
     * @param includeAutoGeneratedWSDLs
     *            if the auto-generated WSDL's should be included.
     * @return WSDL dependencies for a given XPDL file.
     */
    public static Collection<IResource> queryReferencedWSDLResources(
            IResource xpdlIResource, boolean includeAutoGeneratedWSDLs) {
        ArrayList<IResource> wsdlDependences = new ArrayList<IResource>();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlIResource);
        if (wc instanceof Xpdl2WorkingCopyImpl) {
            for (IResource r : wc.getDependency()) {
                WorkingCopyUtil.getWorkingCopy(r);
                if (WSDL_EXTENSION.equalsIgnoreCase(r.getFileExtension())
                        && (!includeAutoGeneratedWSDLs || !r.isDerived())
                        && WorkingCopyUtil.getWorkingCopy(r) instanceof WsdlWorkingCopy) {
                    wsdlDependences.add(r);
                }
            }
        }
        return wsdlDependences;
    }

    /**
     * @param xpdlFileLocation
     * @return
     */
    private static String getPlatformSpecificXpdlFileLocation(
            String xpdlFileLocation) {
        String xpdlPlatformLocation = null;
        if (!xpdlFileLocation.startsWith("platform:/resource")) { //$NON-NLS-1$
            xpdlPlatformLocation = "platform:/resource" + xpdlFileLocation; //$NON-NLS-1$
        } else {
            xpdlPlatformLocation = xpdlFileLocation;
        }

        return xpdlPlatformLocation.replace(" ", "%20"); //$NON-NLS-1$//$NON-NLS-2$

    }

    /**
     * This method is to get all the xpdl files that reference a given bom file
     * 
     * 
     * @param project
     * @param bomFileLocation
     * @param includeUnresolvedReferences
     * @return
     */
    public static Set<String> queryReferencingXpdls(IProject project,
            String bomFileLocation, boolean includeUnresolvedReferences) {

        Set<IProject> projectsHierarchy = new HashSet<IProject>();
        projectsHierarchy.add(project);

        Set<IProject> referencingProjectsHierarchy =
                ProjectUtil.getReferencingProjectsHierarchy(project,
                        projectsHierarchy);

        if (bomFileLocation != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                            bomFileLocation);
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            if (includeUnresolvedReferences) {
                Set<String> referencingXpdls = new HashSet<String>();
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String processUri =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                        referencingXpdls.add(processUri);
                    }
                }
                return referencingXpdls;
            } else {
                Set<String> referencingXpdls = new HashSet<String>();
                // Query the indexer
                additionalAttributes = new HashMap<String, String>();
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                bomFileLocation);
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                ProcessToBomType.BOM.name());
                criteria =
                        new IndexerItemImpl(null, null, null,
                                additionalAttributes);
                Collection<IndexerItem> bomResults =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                        criteria);
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String bomRefId =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID);
                        String bomLocation =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                        String bomNamespace =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE);
                        if (ProcessUIUtil.isResolvedBOMReference(bomRefId,
                                bomLocation,
                                bomNamespace,
                                bomResults)) {
                            String processUri =
                                    indexerItem
                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                            String xpdlProjectName =
                                    indexerItem.get(INTERNAL_PROJECT);
                            IProject xpdlProject =
                                    ResourcesPlugin.getWorkspace().getRoot()
                                            .getProject(xpdlProjectName);

                            if (referencingProjectsHierarchy
                                    .contains(xpdlProject)) {
                                referencingXpdls.add(processUri);
                            }
                        }
                    }
                }
                return referencingXpdls;
            }
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the xpdl files that reference a given bom file
     * 
     * @param bomFileLocation
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     * 
     * @deprecated
     * @see com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil.
     *      queryReferencingXpdls(IProject, String, boolean)
     **/
    @Deprecated
    public static Set<String> queryReferencingXpdls(String bomFileLocation,
            boolean includeUnresolvedReferences) {
        if (bomFileLocation != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                            bomFileLocation);
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            if (includeUnresolvedReferences) {
                Set<String> referencingXpdls = new HashSet<String>();
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String processUri =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                        referencingXpdls.add(processUri);
                    }
                }
                return referencingXpdls;
            } else {
                Set<String> referencingXpdls = new HashSet<String>();
                // Query the indexer
                additionalAttributes = new HashMap<String, String>();
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                bomFileLocation);
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                ProcessToBomType.BOM.name());
                criteria =
                        new IndexerItemImpl(null, null, null,
                                additionalAttributes);
                Collection<IndexerItem> bomResults =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                        criteria);
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String bomRefId =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID);
                        String bomLocation =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                        String bomNamespace =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE);
                        if (ProcessUIUtil.isResolvedBOMReference(bomRefId,
                                bomLocation,
                                bomNamespace,
                                bomResults)) {
                            String processUri =
                                    indexerItem
                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                            referencingXpdls.add(processUri);
                        }
                    }
                }
                return referencingXpdls;
            }
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the xpdl files that reference a given bom file
     * 
     * @param bomFileLocation
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    public static Set<IResource> queryReferencingXpdlResources(
            String bomFileLocation, boolean includeUnresolvedReferences) {
        if (bomFileLocation != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                            bomFileLocation);
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            if (includeUnresolvedReferences) {
                Set<IResource> referencingXpdls = new HashSet<IResource>();
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String processUri =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                        IFile xpdlFile =
                                ResourcesPlugin
                                        .getWorkspace()
                                        .getRoot()
                                        .getFile(new Path(
                                                processUri.replace("%20", " ")) //$NON-NLS-1$//$NON-NLS-2$
                                                .removeFirstSegments(1));

                        referencingXpdls.add(xpdlFile);
                    }
                }
                return referencingXpdls;
            } else {
                Set<IResource> referencingXpdls = new HashSet<IResource>();
                // Query the indexer
                additionalAttributes = new HashMap<String, String>();
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                bomFileLocation);
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                ProcessToBomType.BOM.name());
                criteria =
                        new IndexerItemImpl(null, null, null,
                                additionalAttributes);
                Collection<IndexerItem> bomResults =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                        criteria);
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String bomRefId =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID);
                        String bomLocation =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                        String bomNamespace =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE);
                        if (ProcessUIUtil.isResolvedBOMReference(bomRefId,
                                bomLocation,
                                bomNamespace,
                                bomResults)) {
                            String processUri =
                                    indexerItem
                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION);
                            URI uri = URI.createURI(processUri, true);
                            Resource resource = new ResourceImpl(uri);
                            IFile xpdlFile =
                                    WorkspaceSynchronizer.getFile(resource);
                            referencingXpdls.add(xpdlFile);
                        }
                    }
                }
                return referencingXpdls;
            }
        }
        return Collections.emptySet();
    }

    /**
     * This method is to get all the process URI strings that has reference to a
     * given BOM file
     * 
     * @param bomFileLocation
     * @param projectName
     * @param includeUnresolvedReferences
     * 
     * @return Set<String>
     **/
    public static Set<String> queryReferencingProcesses(String projectName,
            String bomFileLocation, boolean includeUnresolvedReferences) {
        if (bomFileLocation != null) {
            // Query the indexer
            Map<String, String> additionalAttributes =
                    new HashMap<String, String>();
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                            bomFileLocation);

            additionalAttributes.put(IndexerServiceImpl.ATTRIBUTE_PROJECT,
                    projectName);
            additionalAttributes
                    .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                            ProcessToBomType.PROCESS.name());
            IndexerItem criteria =
                    new IndexerItemImpl(null, null, null, additionalAttributes);
            Collection<IndexerItem> xpdlResults =
                    XpdResourcesPlugin
                            .getDefault()
                            .getIndexerService()
                            .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                    criteria);
            if (includeUnresolvedReferences) {
                Set<String> processIds = new HashSet<String>();
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI) != null) {
                        String processUri =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI);
                        URI uri = URI.createURI(processUri);
                        processIds.add(uri.fragment());
                    }
                }

                return processIds;
            } else {
                Set<String> processIds = new HashSet<String>();
                // Query the indexer
                additionalAttributes = new HashMap<String, String>();
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                                bomFileLocation);
                additionalAttributes
                        .put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                                ProcessToBomType.BOM.name());
                criteria =
                        new IndexerItemImpl(null, null, null,
                                additionalAttributes);
                Collection<IndexerItem> bomResults =
                        XpdResourcesPlugin
                                .getDefault()
                                .getIndexerService()
                                .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                        criteria);
                for (IndexerItem indexerItem : xpdlResults) {
                    if (indexerItem != null
                            && indexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION) != null) {
                        String bomRefId =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID);
                        String bomLocation =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                        String bomNamespace =
                                indexerItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE);
                        if (ProcessUIUtil.isResolvedBOMReference(bomRefId,
                                bomLocation,
                                bomNamespace,
                                bomResults)) {
                            String processUri =
                                    indexerItem
                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI);
                            URI uri = URI.createURI(processUri);
                            processIds.add(uri.fragment());
                        }
                    }
                }
                return processIds;
            }
        }
        return Collections.emptySet();
    }

    private static boolean isResolvedBOMReference(String bomRefId,
            String bomLocation, String bomNamespace,
            Collection<IndexerItem> bomIndexerItems) {
        if (bomRefId != null && bomLocation != null && bomNamespace != null
                && bomIndexerItems != null) {
            for (IndexerItem bomIndexerItem : bomIndexerItems) {
                if (bomIndexerItem != null) {
                    String refId =
                            bomIndexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID);
                    String location =
                            bomIndexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION);
                    String namespace =
                            bomIndexerItem
                                    .get(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE);
                    if (refId != null && location != null && namespace != null) {
                        if (refId.equals(bomRefId)
                                && location.equals(bomLocation)
                                && namespace.equals(bomNamespace)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param taskLibraryId
     * @return The indexer item for the task library.
     */
    public static IndexerItem getTaskLibraryIndexItem(String taskLibraryId) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID,
                taskLibraryId);

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEX_TYPE, null,
                        additionalAttributes);

        Collection<IndexerItem> indexedLibrarys =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);

        if (indexedLibrarys != null && !indexedLibrarys.isEmpty()) {
            return indexedLibrarys.iterator().next();
        }

        return null;
    }

    /**
     * @param taskLibraryTaskId
     * @return The indexerItem for the given task library task.
     */
    public static IndexerItem getTaskLibraryTaskIndexItem(
            String taskLibraryTaskId) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID,
                taskLibraryTaskId);

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE,
                        null, additionalAttributes);

        Collection<IndexerItem> indexedLibrarys =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEXER_ID,
                                criteria);

        if (indexedLibrarys != null && !indexedLibrarys.isEmpty()) {
            return indexedLibrarys.iterator().next();

        }

        return null;
    }

    /**
     * Returns if an {@link ExternalReference} is unresolved. This method relies
     * on the data that a BOM class name whose name is null is an external
     * reference.
     * 
     * @param externalReference
     * @return
     */

    public static boolean isUnresolvedExternalReference(
            ExternalReference externalReference) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID,
                externalReference.getXref());
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION,
                externalReference.getLocation());
        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE,
                externalReference.getNamespace());
        additionalAttributes.put("process_to_bom_type", "BOM"); //$NON-NLS-1$//$NON-NLS-2$
        IndexerItem criteria =
                new IndexerItemImpl(null, null, null, additionalAttributes);
        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(Xpdl2ResourcesPlugin.PROCESS_TO_BOM_INDEXER_ID,
                                criteria);
        if (result != null && result.iterator().hasNext()) {
            return false;
        }
        return true;
    }

    /**
     * This method returns BOM external references for a process element,
     * Duplicates are eliminated
     * 
     * @param element
     * @return List of external references
     **/
    public static List<ExternalReference> getBOMExternalReferencesUsed(
            EObject element) {
        if (element != null) {
            List<ProcessRelevantData> externalPrdList =
                    ProcessInterfaceUtil
                            .getAllExternalProcessRelevantData(element);
            if (externalPrdList != null && !externalPrdList.isEmpty()) {
                removeUnusedData(externalPrdList, element);
                List<ExternalReference> externalReferences =
                        new ArrayList<ExternalReference>();
                for (ProcessRelevantData externalPrd : externalPrdList) {
                    if (externalPrd != null) {
                        ExternalReference externalReference =
                                (ExternalReference) externalPrd.getDataType();
                        if (isValidExternalReference(externalReference)
                                && !ProcessUIUtil
                                        .containsExternalReference(externalReferences,
                                                externalReference)) {
                            externalReferences.add(externalReference);
                        }
                    }
                }
                return externalReferences;
            }
        }
        return Collections.emptyList();
    }

    public static void removeUnusedData(
            List<ProcessRelevantData> externalPrdList, EObject element) {
        Collection<Activity> activities = null;
        Collection<Transition> transitions = null;
        if (element instanceof Process) {
            Process process = (Process) element;
            activities = Xpdl2ModelUtil.getAllActivitiesInProc(process);
            transitions = Xpdl2ModelUtil.getAllTransitionsInProc(process);
        } else if (element instanceof Package) {
            Package tmpPackage = (Package) element;
            EList<Process> processes = tmpPackage.getProcesses();
            for (Process process : processes) {
                if (activities == null) {
                    activities = Xpdl2ModelUtil.getAllActivitiesInProc(process);
                    transitions =
                            Xpdl2ModelUtil.getAllTransitionsInProc(process);
                } else {
                    activities.addAll(Xpdl2ModelUtil
                            .getAllActivitiesInProc(process));
                    transitions.addAll(Xpdl2ModelUtil
                            .getAllTransitionsInProc(process));
                }
            }
        }
        // check each Process Relevant Data in turn
        for (ProcessRelevantData prData : externalPrdList) {
            Iterator<Activity> activityIter = activities.iterator();
            while (activityIter.hasNext()) {
                Activity act = activityIter.next();
                AssociatedParameters parameters =
                        (AssociatedParameters) act
                                .getOtherElement(XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters()
                                        .getName());

                boolean prDataFound = false;
                if (parameters == null
                        || parameters.getAssociatedParameter().isEmpty()) {
                    prDataFound = true;
                } else if (parameters != null) {
                    for (AssociatedParameter param : parameters
                            .getAssociatedParameter()) {
                        if (param.getFormalParam().equals(prData.getName())) {
                            prDataFound = true;
                            break;
                        }
                    }
                }

                if (prDataFound) {
                    // add to script stuff
                }
            }

            Iterator<Transition> transitionsIter = transitions.iterator();

        }
    }

    /**
     * This method gives a list of resolved referenced classifiers for a given
     * xpdl element.
     * 
     * @param element
     * 
     * @return Set<Classifier>
     **/
    public static EObject getReferencedClassifier(
            ExternalReference externalReference, IProject project) {
        Set<IProject> referencedProjects = null;
        if (_complexTypesInfo == null) {
            // We only call this once, as it's relatively
            // expensive.
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        ComplexDataTypeReference complexDataTypeRef =
                xpdl2RefToComplexDataTypeRef(externalReference);
        return getReferencedClassifier(complexDataTypeRef, project);

    }

    /**
     * @param complexDataTypeRef
     * @param project
     */
    public static Classifier getReferencedClassifier(
            ComplexDataTypeReference complexDataTypeRef, IProject project) {
        Set<IProject> referencedProjects;

        if (_complexTypesInfo == null) {
            // We only call this once, as it's relatively
            // expensive.
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        if (complexDataTypeRef != null) {
            Object objectForDataType =
                    _complexTypesInfo
                            .getComplexDataTypeFromReference(complexDataTypeRef,
                                    project);
            if (objectForDataType == null) {
                // It might be in an external project
                referencedProjects =
                        ProjectUtil.getReferencedProjectsHierarchy(project,
                                new HashSet<IProject>());
                if (referencedProjects != null && !referencedProjects.isEmpty()) {
                    for (IProject referencedProject : referencedProjects) {
                        if (referencedProject != null) {
                            objectForDataType =
                                    _complexTypesInfo
                                            .getComplexDataTypeFromReference(complexDataTypeRef,
                                                    referencedProject);
                            if (objectForDataType instanceof Classifier) {
                                return (Classifier) objectForDataType;
                            }
                        }
                    }
                }
            } else if (objectForDataType instanceof Classifier) {
                return (Classifier) objectForDataType;
            }
        }
        return null;
    }

    /**
     * This method uses the indexer to return the process package and process
     * participants in the indexer that match the given Id
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     * 
     */
    public static List<EObject> getAllParticipantsWithId(String id) {
        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);

        IndexerItem criteria =
                new IndexerItemImpl(
                        null,
                        ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEX_TYPE,
                        null, additionalAttributes);
        return getIndexedElements(ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEXER_ID,
                criteria);
    }

    /**
     * @return The list of indexed participants indexerItems.
     */
    public static Collection<IndexerItem> getAllParticipantIndexerItems() {
        IndexerItem criteria = new IndexerItemImpl(null, null, null, null);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEXER_ID,
                                criteria);

        if (result == null) {
            result = Collections.EMPTY_LIST;
        }

        return result;
    }

    /**
     * This method uses the indexer to return the process package and process
     * participants in the indexer that match the given Id
     * 
     * This method returns an empty list if no element in the indexer matches
     * the given id.
     * 
     * @return <code>EObject</code> the eObjects that match the passed id .
     */
    public static List<EObject> getAllParticipants() {
        return getIndexedElements(ProcessParticipantResourceIndexProvider.PROCESS_PARTICIPANT_INDEXER_ID,
                null);
    }

    /**
     * Returns WsdlServiceKey for an abstract operation (even if activity
     * references a concrete one) that is referenced from the given activity.
     * 
     * @param activity
     * 
     * @return The WsdlServiceKey for the given activity (or null if operation
     *         not found).
     */
    public static WsdlServiceKey getWsdlServiceKey(Activity activity) {
        PortTypeOperation pto = Xpdl2ModelUtil.getPortTypeOperation(activity);
        if (pto != null && pto.getExternalReference() != null) {
            String wsdlLocation = pto.getExternalReference().getLocation();
            String portTypeOpName = pto.getOperationName();
            String portType = pto.getPortTypeName();

            if (wsdlLocation != null && wsdlLocation.length() > 0
                    && portTypeOpName != null && portTypeOpName.length() > 0
                    && portType != null && portType.length() > 0) {

                String serviceName = null;
                String operationName = null;
                String portName = null;

                WebServiceOperation webServiceOperation =
                        Xpdl2ModelUtil.getWebServiceOperation(activity);
                if (webServiceOperation != null) {
                    if (webServiceOperation.getService() != null) {
                        serviceName =
                                webServiceOperation.getService()
                                        .getServiceName();
                        portName =
                                webServiceOperation.getService().getPortName();
                    }

                    operationName = webServiceOperation.getOperationName();
                }

                if (serviceName != null && serviceName.length() > 0
                        && operationName != null && operationName.length() != 0
                        && portName != null && portName.length() != 0) {
                    return new WsdlServiceKey(serviceName, portName,
                            operationName, portType, portTypeOpName,
                            wsdlLocation);
                } else {
                    return new WsdlServiceKey(portType, portTypeOpName,
                            wsdlLocation);
                }
            }
        }
        return null;
    }

    /**
     * This method will return WsdlServiceKey for a concrete operation if it is
     * referenced from the given activity. It will still return WsdlServiceKey
     * for abstract operation if activity references an abstract operation.
     * 
     * @param activity
     *            the activity to check.
     * 
     * @return The WsdlServiceKey for the given activity (or null if operation
     *         not found).
     */
    public static WsdlServiceKey getSpecificWsdlServiceKey(Activity activity) {
        PortTypeOperation pto = Xpdl2ModelUtil.getPortTypeOperation(activity);
        if (pto != null && pto.getExternalReference() != null) {
            String wsdlLocation = pto.getExternalReference().getLocation();
            String operation = pto.getOperationName();
            String portType = pto.getPortTypeName();
            if (notEmpty(wsdlLocation) && notEmpty(operation)
                    && notEmpty(portType)) {
                WebServiceOperation wso =
                        Xpdl2ModelUtil.getWebServiceOperation(activity);
                if (wso != null && wso.getService() != null) {
                    String service = wso.getService().getServiceName();
                    String port = wso.getService().getPortName();
                    if (notEmpty(service) && notEmpty(port)) {
                        return new WsdlServiceKey(service, port, operation,
                                portType, operation, wsdlLocation);
                    }
                }
                return new WsdlServiceKey(portType, operation, wsdlLocation);
            }
        }
        return null;
    }

    /**
     * Checks if the parameter in not 'null' and not an empty string.
     */
    private static boolean notEmpty(String s) {
        return s != null && s.length() > 0;
    }


    /**
     * @param caseClass
     * @return case state attribute for the given case class or null
     */
    public static Property getCaseClassCaseState(Class caseClass) {

        Property caseState = null;
        if (null != caseClass) {

            for (Property property : caseClass.getAllAttributes()) {
                /*
                 * If the property is a case state, then use it as we only allow
                 * one case state per case class
                 */
                if (GlobalDataProfileManager.getInstance()
                        .isCaseState(property)) {
                    caseState = property;
                    break;
                }
            }
        }
        return caseState;
    }

    /**
     * @param caseService
     * @param itemEnum
     * @return External Reference element for the given Enumeration Literal
     */
    public static ExternalReference getExternalRefForEnumLit(
            CaseService caseService, EnumerationLiteral itemEnumLit) {

        /*
         * Get the external reference for the case service and change its ID to
         * that of the enum literal
         */

        URI enumLitUri = EcoreUtil.getURI(itemEnumLit);
        if (enumLitUri != null) {
            ExternalReference externalRef =
                    EcoreUtil.copy(caseService.getCaseClassType());

            String uriFragment = enumLitUri.fragment();
            String xRef;
            if (uriFragment != null && uriFragment.contains(URI_DELIMITER)) {
                xRef =
                        uriFragment.substring(0,
                                uriFragment.indexOf(URI_DELIMITER));
            } else {
                xRef = uriFragment;
            }

            externalRef.setXref(xRef);
            return externalRef;
        }
        return null;
    }

}
