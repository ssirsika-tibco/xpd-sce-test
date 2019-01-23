/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.ProcessInterfaceGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.Xpdl2ProcessorUtil;
import com.tibco.xpd.fragments.FragmentsActivator;
import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.IFragmentCategory;
import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.ProviderBinding;
import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplate;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplateChildElement;
import com.tibco.xpd.processeditor.xpdl2.pkgtemplates.PackageTemplateLoader;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Template(Content) provider for the Process/Pageflow and Package wizard page
 * trees. This provides the template items having read it from the
 * 
 * @author rsomayaj
 * 
 * 
 */
public abstract class ProcessTemplateProvider extends
        AbstractProcessTemplateProvider {

    /**
     * 
     */
    private static final String PAGE_FLOW_TEMPLATE_PROVIDER =
            "PageFlowTemplateProvider"; //$NON-NLS-1$

    private static final String BUSINESS_SERVICE_TEMPLATE_PROVIDER =
            "BusinessServiceTemplateProvider"; //$NON-NLS-1$

    private static final String CASE_SERVICE_TEMPLATE_PROVIDER =
            "CaseServiceTemplateProvider"; //$NON-NLS-1$

    private static final String SERVICE_PROCESS_TEMPLATE_PROVIDER =
            "ServiceProcessTemplateProvider"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BUSINESS_PROCESS_FRAGEMENTS_PROVIDER =
            "BusinessProcessFragementsProvider"; //$NON-NLS-1$

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public static final String DEFAULT_DISPLAY_FRAGMENT_ID =
            "_default_fragment_id";//$NON-NLS-1$

    private static final String PROCESS_INTERFACE_HEADER =
            Messages.ProcessTemplateProvider_ProcesInterfaceHeader_label;

    protected static final Object PACKAGE_TEMPLATE_HEADER =
            Messages.ProcessTemplateProvider_PackageTemplateHeader_label;

    private Package xpdl2Package;

    private Object selectedObject;

    private final TemplateProviderIdentifier templateProviderId;

    public enum TemplateProviderIdentifier {

        PACKAGE, PROCESS, PAGEFLOW, BUSINESSSERVICE, CASESERIVCE, SERVICEPROCESS;
    }

    public ProcessTemplateProvider(TemplateProviderIdentifier templateProviderId) {
        this.templateProviderId = templateProviderId;
    }

    public ProcessTemplateProvider(Package xpdl2Package,
            TemplateProviderIdentifier templateProviderId) {
        this(templateProviderId);
        this.xpdl2Package = xpdl2Package;

    }

    @Override
    public ITreeContentProvider getContentProvider() {
        return new ITreeContentProvider() {

            @Override
            public Object[] getChildren(Object parentElement) {

                if (parentElement instanceof IFragmentCategory) {

                    IFragmentCategory category =
                            (IFragmentCategory) parentElement;

                    return category.getChildren().toArray();

                } else if (parentElement instanceof ProcessInterfaceGroup) {

                    return ((ProcessInterfaceGroup) parentElement)
                            .getChildren().toArray();
                } else if (parentElement instanceof String
                        && parentElement.equals(PROCESS_INTERFACE_HEADER)) {

                    if (xpdl2Package != null) {

                        /*
                         * XPD-7247: get all the projects in worspace which have
                         * process interface.
                         */
                        Set<IProject> projectsWithProcessInterfaces =
                                getAllWorkspaceProjectsWithProcessInterfaces();
                        /*
                         * XPD-3024: new process wizard must list projects
                         * folder under Process Interfaces header only if
                         * process interfaces exist in that package or any other
                         * package in this project or referenced projects
                         */
                        if (projectsWithProcessInterfaces.size() > 0) {

                            return projectsWithProcessInterfaces.toArray();
                        }
                    }
                    if (parentElement instanceof IProject) {

                        ProjectConfig projectConfig =
                                XpdResourcesPlugin
                                        .getDefault()
                                        .getProjectConfig((IProject) parentElement);
                        SpecialFolders spFolders =
                                projectConfig.getSpecialFolders();
                        return spFolders
                                .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND)
                                .toArray();
                    } else if (selectedObject != null) {

                        IProject project = null;
                        if (selectedObject instanceof IFile) {

                            project = ((IFile) selectedObject).getProject();
                        } else if (selectedObject instanceof SpecialFolder) {

                            project =
                                    ((SpecialFolder) selectedObject)
                                            .getProject();
                        } else if (selectedObject instanceof IProject) {

                            project = (IProject) selectedObject;
                        }
                        if (project != null) {
                            /*
                             * XPD-7247: get all the projects in worspace which
                             * have process interface.
                             */
                            Set<IProject> projectsWithProcessInterfaces =
                                    getAllWorkspaceProjectsWithProcessInterfaces();
                            /*
                             * XPD-3024: new process/package wizard must list
                             * projects folder under Process Interfaces header
                             * only if process interfaces exist in this project
                             * or referenced projects
                             */
                            return projectsWithProcessInterfaces.toArray();
                        }
                    }
                } else if (parentElement instanceof IFolder) {

                    try {

                        List<EObject> packagesList = new ArrayList<EObject>();
                        IResource[] resources =
                                ((IFolder) parentElement).members();
                        for (IResource resource : resources) {

                            if (resource instanceof IFile) {

                                WorkingCopy wc =
                                        XpdResourcesPlugin.getDefault()
                                                .getWorkingCopy(resource);
                                if (wc != null && !wc.isInvalidFile()) {
                                    /*
                                     * XPD-3024: new process/package wizard must
                                     * list process packages folder under
                                     * Process Interfaces header only if process
                                     * interfaces exist in this package
                                     */
                                    EObject rootElement = wc.getRootElement();
                                    if (rootElement instanceof Package) {

                                        Package pckg = (Package) rootElement;
                                        /*
                                         * find if there are any process
                                         * interfaces for this package
                                         */
                                        ProcessInterfaces processInterfaces =
                                                ProcessInterfaceUtil
                                                        .getProcessInterfaces(pckg);
                                        if (null != processInterfaces
                                                && processInterfaces
                                                        .getProcessInterface()
                                                        .size() > 0
                                                && !packagesList.contains(pckg)) {

                                            /*
                                             * XPD-7520: Saket: When we're
                                             * creating a new service process,
                                             * we need to filter out process
                                             * packages containing service
                                             * process interfaces only.
                                             * Otherwise simply add all packages
                                             * which have process interface(s).
                                             */
                                            if (templateProviderId
                                                    .equals(TemplateProviderIdentifier.SERVICEPROCESS)) {

                                                if (hasServiceProcessInteface(processInterfaces)) {

                                                    packagesList.add(pckg);
                                                }

                                            } else {

                                                if (hasNormalProcessInteface(processInterfaces)) {

                                                    packagesList.add(pckg);

                                                }

                                            }

                                        }
                                    }
                                }

                            }
                        }
                        return packagesList.toArray();
                    } catch (CoreException e) {
                        LOG.error(e);
                    }
                } else if (parentElement instanceof Package) {

                    ProcessInterfaceGroup interfaceGroup =
                            new ProcessInterfaceGroup((Package) parentElement);
                    /* XPD-3024: return only if the process interfaces exist */
                    if (interfaceGroup.getChildren().size() > 0) {

                        /*
                         * XPD-7298: Get only the type of interfaces that are
                         * applicable so that only those are displayed in the
                         * template.
                         */
                        List applicableInterfaces =
                                getApplicableInterfaces(interfaceGroup);

                        if (applicableInterfaces != null
                                && !applicableInterfaces.isEmpty()) {

                            return applicableInterfaces.toArray();
                        }
                    }

                } else if (parentElement instanceof IProject) {

                    ProjectConfig projectConfig =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig((IProject) parentElement);
                    if (projectConfig != null) {

                        SpecialFolders spFolders =
                                projectConfig.getSpecialFolders();
                        List<IFolder> res =
                                spFolders
                                        .getEclipseIFoldersOfKind(Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
                        return res.toArray();
                    }
                } else if (parentElement instanceof String
                        && PACKAGE_TEMPLATE_HEADER.equals(parentElement)) {

                    return PackageTemplateLoader.getInstance()
                            .getPackageTemplates().toArray();
                } else if (parentElement instanceof PackageTemplate) {

                    return ((PackageTemplate) parentElement).getChildElements()
                            .toArray();
                }
                return null;
            }

            /**
             * Return <code>true</code> if the specified
             * <code>ProcessInterfaces</code> EObject contains a Service Process
             * Interface, <code>false</code> otherwise.
             * 
             * @param processInterfaces
             * 
             * @return <code>true</code> if the specified
             *         <code>ProcessInterfaces</code> EObject contains a Service
             *         Process Interface, <code>false</code> otherwise.
             */
            private boolean hasServiceProcessInteface(
                    ProcessInterfaces processInterfaces) {

                List<ProcessInterface> allProcIfcs =
                        processInterfaces.getProcessInterface();

                for (ProcessInterface processInterface : allProcIfcs) {

                    if (Xpdl2ModelUtil
                            .isServiceProcessInterface(processInterface)) {

                        return true;
                    }

                }
                return false;
            }

            /**
             * Return <code>true</code> if the specified
             * <code>ProcessInterfaces</code> EObject contains a Normal Process
             * Interface, <code>false</code> otherwise.
             * 
             * @param processInterfaces
             * 
             * @return <code>true</code> if the specified
             *         <code>ProcessInterfaces</code> EObject contains a Normal
             *         Process Interface, <code>false</code> otherwise.
             */
            private boolean hasNormalProcessInteface(
                    ProcessInterfaces processInterfaces) {

                List<ProcessInterface> allProcIfcs =
                        processInterfaces.getProcessInterface();

                for (ProcessInterface processInterface : allProcIfcs) {

                    if (Xpdl2ModelUtil.isProcessInterface(processInterface)) {

                        return true;
                    }

                }
                return false;
            }

            @Override
            public Object getParent(Object element) {

                if (element instanceof IFragmentCategory) {

                    IFragmentCategory category = (IFragmentCategory) element;
                    return category.getParent();
                } else if (element instanceof IFragment) {

                    IFragment frag = (IFragment) element;
                    return frag.getParent();
                } else if (element instanceof ProcessInterface) {

                    return ((ProcessInterface) element).eContainer()
                            .eContainer();
                } else if (element instanceof Package) {

                    WorkingCopy wc =
                            WorkingCopyUtil
                                    .getWorkingCopyFor((EObject) element);
                    return ((IFile) wc.getEclipseResources().get(0))
                            .getParent();
                } else if (element instanceof IFolder) {

                    return ((IFolder) element).getProject();
                } else if (element instanceof IProject) {

                    return PROCESS_INTERFACE_HEADER;
                } else if (element instanceof PackageTemplate) {

                    return PACKAGE_TEMPLATE_HEADER;
                } else if (element instanceof PackageTemplateChildElement) {

                    return ((PackageTemplateChildElement) element).getParent();
                }

                return null;
            }

            @Override
            public boolean hasChildren(Object element) {

                if (element instanceof IFragmentCategory) {

                    IFragmentCategory category = (IFragmentCategory) element;
                    if (category.getChildren().size() > 0) {

                        return true;
                    }
                } else if (element instanceof ProcessInterfaceGroup) {

                    return ((ProcessInterfaceGroup) element).hasChildren();
                } else if (element instanceof IFolder) {

                    try {

                        return ((IFolder) element).members().length > 0;
                    } catch (CoreException e) {

                        LOG.error(e);
                    }
                } else if (element instanceof IFile
                        || element instanceof Package) {

                    return true;
                } else if (element instanceof String
                        && element.equals(PROCESS_INTERFACE_HEADER)) {

                    return true;
                } else if (element instanceof String
                        && PACKAGE_TEMPLATE_HEADER.equals(element)) {

                    return true;
                } else if (element instanceof IProject) {

                    return true;
                } else if (element instanceof PackageTemplate) {

                    return true;
                } else if (element instanceof PackageTemplateChildElement) {

                    return false;
                }

                return false;
            }

            @Override
            public Object[] getElements(Object inputElement) {

                List<IFragmentCategory> processRootCategory =
                        getProcessRootCategory();

                List<IFragmentCategory> pageflowRootCategory =
                        getPageflowRootCategory();

                List<IFragmentCategory> bizServRootCategory =
                        getBizServRootCategory();

                List<IFragmentCategory> caseServRootCategory =
                        getCaseServRootCategory();

                List<IFragmentCategory> serviceProcessRootCategory =
                        getServiceProcessRootCategory();

                if (TemplateProviderIdentifier.PACKAGE
                        .equals(templateProviderId)) {

                    if (Xpdl2ResourcesPlugin.isWmFeatureAvailable()) {

                        List<Object> returnObject = new ArrayList<Object>();
                        returnObject.add(PACKAGE_TEMPLATE_HEADER);
                        returnObject.addAll(processRootCategory);
                        returnObject.addAll(pageflowRootCategory);
                        returnObject.addAll(bizServRootCategory);
                        returnObject.addAll(caseServRootCategory);
                        returnObject.addAll(serviceProcessRootCategory);
                        returnObject.add(PROCESS_INTERFACE_HEADER);
                        return returnObject.toArray();
                    } else {

                        List<Object> returnObject = new ArrayList<Object>();
                        returnObject.add(PACKAGE_TEMPLATE_HEADER);
                        returnObject.addAll(processRootCategory);
                        returnObject.add(PROCESS_INTERFACE_HEADER);
                        return returnObject.toArray();
                    }

                } else if (TemplateProviderIdentifier.PROCESS
                        .equals(templateProviderId)) {
                    List<Object> returnObject = new ArrayList<Object>();
                    returnObject.addAll(processRootCategory);
                    returnObject.add(PROCESS_INTERFACE_HEADER);

                    return returnObject.toArray();

                } else if (TemplateProviderIdentifier.PAGEFLOW
                        .equals(templateProviderId)) {

                    List<Object> returnObject = new ArrayList<Object>();
                    returnObject.addAll(pageflowRootCategory);
                    returnObject.add(PROCESS_INTERFACE_HEADER);
                    return returnObject.toArray();
                } else if (TemplateProviderIdentifier.BUSINESSSERVICE
                        .equals(templateProviderId)) {

                    List<Object> returnObject = new ArrayList<Object>();
                    returnObject.addAll(bizServRootCategory);
                    returnObject.add(PROCESS_INTERFACE_HEADER);
                    return returnObject.toArray();
                } else if (TemplateProviderIdentifier.CASESERIVCE
                        .equals(templateProviderId)) {

                    List<Object> returnObject = new ArrayList<Object>();
                    returnObject.addAll(caseServRootCategory);
                    returnObject.add(PROCESS_INTERFACE_HEADER);
                    return returnObject.toArray();
                } else if (TemplateProviderIdentifier.SERVICEPROCESS
                        .equals(templateProviderId)) {

                    List<Object> returnObject = new ArrayList<Object>();
                    returnObject.addAll(serviceProcessRootCategory);
                    returnObject.add(PROCESS_INTERFACE_HEADER);
                    return returnObject.toArray();
                }

                return new Object[0];
            }

            @Override
            public void dispose() {
                // Nothing to do
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // Nothing to do
            }
        };
    }

    /**
     * XPD-7298: Give a chance to the implementers to decide which interface is
     * applicable based on the type of process for which the template is shown.
     * 
     * @param interfaceGroup
     *            the process interface group of the package in the template.
     * @return List of all the interface in the passed 'interfaceGroup' that are
     *         applicable to be shown in the template of the current process.
     */
    protected abstract List getApplicableInterfaces(
            ProcessInterfaceGroup interfaceGroup);

    /**
     * XPD-7247: Gets List of all the projects in workspace that have process
     * interface defined in them. Note that the method
     * {@link #isApplicableInterfaceType(String)} decides the type of interface
     * we expect the project to have.
     * 
     * @return Set of all the projects in workspace that have process interface
     *         defined in them.
     */
    protected Set<IProject> getAllWorkspaceProjectsWithProcessInterfaces() {
        /*
         * stores the projects that have interfaces
         */
        Set<IProject> projectsWithProcIntf = new LinkedHashSet<IProject>();

        /*
         * Stores the already added project names so that we do not perfrom the
         * operation 'ResourcesPlugin.getWorkspace().getRoot()
         * .getProject(projectPathFromIndexer)' again and agin to get the same
         * project
         */
        Set<String> alreadyAddedProjectNames = new LinkedHashSet<String>();

        Collection<IndexerItem> processIndexerItems =
                ProcessUIUtil.getAllProcessIndexerItems();

        for (IndexerItem indexerItem : processIndexerItems) {

            String type = indexerItem.getType();
            /*
             * get the project path/name from the indexer
             */
            String projectPathFromIndexer =
                    indexerItem.get(ProcessUIUtil.INTERNAL_PROJECT);

            if (projectPathFromIndexer != null
                    && type != null
                    && !alreadyAddedProjectNames
                            .contains(projectPathFromIndexer)) {

                ProcessResourceItemType processResourceType =
                        ProcessResourceItemType.create(type);

                /*
                 * Check if the interface is the one that is applicable.
                 */
                if (processResourceType != null
                        && isApplicableInterfaceType(processResourceType)) {

                    alreadyAddedProjectNames.add(projectPathFromIndexer);
                    /*
                     * get the project from the project path
                     */
                    IProject project =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getProject(projectPathFromIndexer);

                    if (project != null && project.isAccessible()) {
                        projectsWithProcIntf.add(project);
                    }
                }
            }
        }

        return projectsWithProcIntf;
    }

    /**
     * XPD-7298: Gives a chance to the implementers to decide if the type of
     * interface passed is the type they are interested in.
     * 
     * @param processResourceType
     *            the type of interface.
     * @return <code>true</code> if the passed interface type is the one that is
     *         expected, else return <code>false</code>.
     */
    protected abstract boolean isApplicableInterfaceType(
            ProcessResourceItemType processResourceType);

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite.ICheckTreeWithDescriptionInputProvider#getDescription(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    public String getDescription(Object element) {
        return Messages.ProcessTemplateProvider_ProcessTemplateDesc_shortdesc;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.wizards.TreeWithImagePreviewComposite.ICheckTreeWithDescriptionInputProvider#getInitialSelection()
     * 
     * @return
     */
    @Override
    public Object getInitialSelection() {

        List<IFragmentCategory> rootCategories = Collections.emptyList();
        if (TemplateProviderIdentifier.PAGEFLOW.equals(templateProviderId)) {

            rootCategories = getPageflowRootCategory();
        } else if (TemplateProviderIdentifier.BUSINESSSERVICE
                .equals(templateProviderId)) {

            rootCategories = getBizServRootCategory();
        } else if (TemplateProviderIdentifier.CASESERIVCE
                .equals(templateProviderId)) {

            rootCategories = getCaseServRootCategory();
        } else if (TemplateProviderIdentifier.SERVICEPROCESS
                .equals(templateProviderId)) {

            rootCategories = getServiceProcessRootCategory();
        } else {
            rootCategories = getProcessRootCategory();

        }
        return findDefaultFragment(rootCategories);
    }

    /**
     * @param rootCategories
     * @return default fragment from a given list of root categories.
     */
    private Object findDefaultFragment(List<IFragmentCategory> rootCategories) {
        Object defaultFragment = null;
        for (IFragmentCategory fragmentCategory : rootCategories) {
            defaultFragment = getDefaultFragment(fragmentCategory);
            if (null != defaultFragment) {
                return defaultFragment;
            }
        }
        return null;
    }

    /**
     * @param rootCategory
     * @return
     */
    private Object getDefaultFragment(IFragmentCategory category) {
        if (category != null) {
            for (IFragmentElement fragElement : category.getChildren()) {
                if (fragElement instanceof IFragmentCategory) {
                    Object defaultFragment =
                            getDefaultFragment((IFragmentCategory) fragElement);
                    if (defaultFragment != null) {
                        return defaultFragment;
                    }
                } else {
                    Package fragmentPackage =
                            Xpdl2ProcessorUtil
                                    .getFragmentPackage((IFragment) fragElement);

                    if (DEFAULT_DISPLAY_FRAGMENT_ID.equals(fragmentPackage
                            .getId())) {
                        return fragElement;
                    }

                }
            }
        }
        return null;
    }

    @Override
    public Object getInput() {
        return this;
    }

    @Override
    public ILabelProvider getLabelProvider() {
        final ILabelProvider fragmentsLabelProvider =
                FragmentsActivator.getDefault().getFragmentsLabelProvider();
        return new ILabelProvider() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object
             * )
             */
            @Override
            public Image getImage(Object element) {
                Image img = null;

                if (element instanceof IFragmentElement) {
                    Image decorateImg =
                            fragmentsLabelProvider.getImage(element);
                    img =
                            ((ILabelDecorator) fragmentsLabelProvider)
                                    .decorateImage(decorateImg, element);
                    if (img == null) {
                        img = decorateImg;
                    }

                } else if (element instanceof EObject) {
                    img = WorkingCopyUtil.getImage((EObject) element);
                } else if (element instanceof ProcessInterfaceGroup) {
                    img = ((ProcessInterfaceGroup) element).getImage();
                } else if (element instanceof String
                        && (PROCESS_INTERFACE_HEADER.equals(element))) {
                    img =
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_PROCESSINTERFACE);
                } else if (element instanceof IFolder) {
                    img =
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_PROCESS_SPECIAL_FOLDER);

                } else if (element instanceof IProject) {
                    img =
                            Xpdl2ProcessEditorPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(ProcessEditorConstants.IMG_PROJECT);
                } else if (element instanceof String
                        && (PACKAGE_TEMPLATE_HEADER.equals(element))) {
                    img =
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_PROCESS_SPECIAL_FOLDER);
                } else if (element instanceof PackageTemplate) {
                    img =
                            Xpdl2ResourcesPlugin.getDefault()
                                    .getImageRegistry()
                                    .get(Xpdl2ResourcesConsts.ICON_PACKAGE);
                } else if (element instanceof PackageTemplateChildElement) {
                    PackageTemplateChildElement childElement =
                            (PackageTemplateChildElement) element;
                    if (Xpdl2ModelUtil.isCaseService(childElement.getProcess())) {

                        img =
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_CASE_SERVICE_PROCESS);
                    } else if (Xpdl2ModelUtil
                            .isPageflowBusinessService(childElement
                                    .getProcess())) {
                        img =
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_BUSINESS_SERVICE_PAGEFLOW_PROCESS);
                    } else if (Xpdl2ModelUtil.isPageflow(childElement
                            .getProcess())) {
                        img =
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_PAGEFLOW_PROCESS);
                    } else if (Xpdl2ModelUtil.isServiceProcess(childElement
                            .getProcess())) {

                        img =
                                Xpdl2ResourcesPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_SERVICE_PROCESS);
                    } else {
                        img =
                                Xpdl2ResourcesPlugin.getDefault()
                                        .getImageRegistry()
                                        .get(Xpdl2ResourcesConsts.ICON_PROCESS);
                    }
                }

                return img;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object
             * )
             */
            @Override
            public String getText(Object element) {
                if (element instanceof IFragmentElement) {
                    return modifyFragmentToTemplate(fragmentsLabelProvider
                            .getText(element));
                } else if (element instanceof EObject) {
                    return WorkingCopyUtil.getText((EObject) element);
                } else if (element instanceof ProcessInterfaceGroup) {
                    return ((ProcessInterfaceGroup) element).getText();
                } else if (element instanceof IResource) {
                    return ((IResource) element).getName();
                } else if (element instanceof PackageTemplate) {
                    return ((PackageTemplate) element).getName();
                } else if (element instanceof PackageTemplateChildElement) {
                    return ((PackageTemplateChildElement) element).getName();
                } else if (element instanceof String) {
                    /*
                     * Sid: Allow any string so that content provider can use
                     * string error messages for tree content.
                     */
                    String strElement = (String) element;
                    // if (strElement.equals(PROCESS_INTERFACE_HEADER)) {
                    // return (String) element;
                    // } else if (strElement.equals(PACKAGE_TEMPLATE_HEADER)) {
                    // return (String) element;
                    // }
                    return strElement;
                }

                return "Bad Fragment Entry Name"; //$NON-NLS-1$
            }

            private String modifyFragmentToTemplate(String name) {
                String newName =
                        name.replaceAll(Messages.ProcessTemplateProvider_Fragment_label,
                                Messages.ProcessTemplateProvider_Template_label);
                return newName;

            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.
             * eclipse.jface.viewers.ILabelProviderListener)
             */
            @Override
            public void addListener(ILabelProviderListener listener) {
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
             */
            @Override
            public void dispose() {
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(
             * java.lang.Object, java.lang.String)
             */
            @Override
            public boolean isLabelProperty(Object element, String property) {
                return false;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org
             * .eclipse.jface.viewers.ILabelProviderListener)
             */
            @Override
            public void removeListener(ILabelProviderListener listener) {
            }

        };
    }

    /**
     * @return a list of fragment root categories associated with the process
     *         editor.
     */
    private List<IFragmentCategory> getProcessRootCategory() {
        return getProcessEditorRelevantRootCategories(BUSINESS_PROCESS_FRAGEMENTS_PROVIDER);
    }

    /**
     * @return
     */
    private List<IFragmentCategory> getProcessEditorRelevantRootCategories(
            String filteredCategories) {
        List<IFragmentCategory> procRootCategories =
                new ArrayList<IFragmentCategory>();

        Collection<ProviderBinding> providerBindings =
                FragmentsActivator.getDefault()
                        .getProviderBindings(ProcessDiagramEditor.EDITOR_ID);

        for (ProviderBinding providerBinding : providerBindings) {
            IFragmentCategory rootCategory = null;
            try {
                rootCategory =
                        FragmentsActivator
                                .getDefault()
                                .getRootCategory(providerBinding.getProviderId());
                Set<IFilter> filters = providerBinding.getFilters();
                for (IFilter filter : filters) {
                    if (filter.select(filteredCategories)
                            && null != rootCategory) {
                        procRootCategories.add(rootCategory);
                    }
                }
            } catch (CoreException e) {
                LOG.error(e);
            }

        }
        return procRootCategories;
    }

    private List<IFragmentCategory> getPageflowRootCategory() {

        return getProcessEditorRelevantRootCategories(PAGE_FLOW_TEMPLATE_PROVIDER);
    }

    private List<IFragmentCategory> getCaseServRootCategory() {

        return getProcessEditorRelevantRootCategories(CASE_SERVICE_TEMPLATE_PROVIDER);
    }

    private List<IFragmentCategory> getBizServRootCategory() {

        return getProcessEditorRelevantRootCategories(BUSINESS_SERVICE_TEMPLATE_PROVIDER);
    }

    private List<IFragmentCategory> getServiceProcessRootCategory() {

        return getProcessEditorRelevantRootCategories(SERVICE_PROCESS_TEMPLATE_PROVIDER);
    }

    public Object getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        this.selectedObject = selectedObject;
    }

    @Override
    public Package getXpdl2Package() {
        return xpdl2Package;
    }

    @Override
    public void setXpdl2Package(Package xpdl2Package) {
        this.xpdl2Package = xpdl2Package;
    }
}
