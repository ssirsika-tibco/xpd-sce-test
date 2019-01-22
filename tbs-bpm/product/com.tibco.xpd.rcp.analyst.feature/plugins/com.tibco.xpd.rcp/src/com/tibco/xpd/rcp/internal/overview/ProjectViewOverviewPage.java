/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.TriggeredOperations;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ltk.core.refactoring.ChangeDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringChangeDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourcesDescriptor;
import org.eclipse.ltk.internal.core.refactoring.UndoableOperation2ChangeAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.models.AbstractModelProvider;
import com.tibco.xpd.rcp.internal.models.AbstractModelProvider.IModelProviderChangeListener;
import com.tibco.xpd.rcp.internal.models.BOMModelProvider;
import com.tibco.xpd.rcp.internal.models.OMModelProvider;
import com.tibco.xpd.rcp.internal.models.ProcessModelProvider;
import com.tibco.xpd.rcp.internal.resources.IRCPContainer;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.ProjectResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;
import com.tibco.xpd.rcp.internal.utils.RCPPropertySectionHelper;
import com.tibco.xpd.rcp.ribbon.RibbonConsts;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * The only page of the overview editor.
 * 
 * @author njpatel
 * 
 */
public class ProjectViewOverviewPage extends FormPage implements
        RCPResourceChangeListener, ITabbedPropertySheetPageContributor {

    private IWorkbenchWindow window;

    private SashForm mainForm;

    private PageBook book;

    /**
     * Page used when multiple projects are selected.
     */
    private MultiProjectPage multiProjectPage;

    private Map<IProject, ProjectPage> pages;

    private ProjectViewer projectViewer;

    private final IRCPContainer resource;

    private FormToolkit formToolKit;

    private ISelectionProvider selectionProvider = null;

    private Color gray_color;

    private UndoListener undoListener;

    private PartListener partListener;

    private final WorkspaceListener workspaceListener;

    private final OverviewSelectionManager overviewSelectionManager;

    // private ToolItem renameItem;

    public ProjectViewOverviewPage(FormEditor editor, String id, String title,
            IRCPContainer resource) {
        super(editor, id, title);

        this.resource = resource;
        window = editor.getSite().getWorkbenchWindow();
        if (resource != null && resource.getProjectResources() != null) {
            resource.addChangeListener(this);
        }

        overviewSelectionManager = new OverviewSelectionManager();

        workspaceListener = new WorkspaceListener();

        ResourcesPlugin.getWorkspace().addResourceChangeListener(
                workspaceListener,
                IResourceChangeEvent.POST_CHANGE);

    }

    /**
     * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite,
     *      org.eclipse.ui.IEditorInput)
     * 
     * @param site
     * @param input
     */
    @Override
    public void init(IEditorSite site, IEditorInput input) {
        super.init(site, input);
        selectionProvider = site.getSelectionProvider();

        partListener = new PartListener(site);
        site.getPage().addPartListener(partListener);

    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        ScrolledForm scrolledForm = managedForm.getForm();

        formToolKit = new FormToolkit(scrolledForm.getDisplay());
        scrolledForm.getBody().setLayout(new FillLayout());

        mainForm = new SashForm(scrolledForm.getBody(), SWT.HORIZONTAL);
        formToolKit.adapt(mainForm);
        // Give the sash some colour
        gray_color = new Color(scrolledForm.getDisplay(), 240, 240, 240);
        mainForm.setBackground(gray_color);

        IRCPContainer resource = RCPResourceManager.getResource();

        mainForm.setLayout(new FillLayout());

        Composite projectViewerComposite =
                createProjectViewer(formToolKit, mainForm);
        GridData data = new GridData(SWT.LEFT, SWT.FILL, false, true);
        data.widthHint = 250;
        projectViewerComposite.setLayoutData(data);

        book = new PageBook(mainForm, SWT.NONE);
        formToolKit.adapt(book);

        // Add blank page - this page will be shown when multiple projects are
        // selected
        multiProjectPage = new MultiProjectPage(formToolKit);
        multiProjectPage.createControl(book);

        Collection<ProjectResource> projectResources =
                resource.getProjectResources();
        pages = new HashMap<IProject, ProjectPage>(projectResources.size());

        for (ProjectResource projectResource : projectResources) {
            addProjectSections(formToolKit, projectResource.getProject());
        }

        Entry<IProject, ProjectPage> firstItem =
                pages.entrySet().iterator().next();

        // Do this before creating the Project View Listener
        createDeleteAction();
        createRenameAction();

        ProjectViewSelectionChangeListener pvSelectionChangedListener =
                new ProjectViewSelectionChangeListener(selectionProvider);
        projectViewer.addSelectionChangeListener(pvSelectionChangedListener);
        projectViewer.setSelection(firstItem.getKey());
        // book.showPage(firstItem.getValue().getControl());

        mainForm.setWeights(new int[] { 1, 4 });

        createUndoAction();
    }

    /**
     * @param formToolKit
     * @param projectResource
     */
    private ProjectPage addProjectSections(FormToolkit formToolKit,
            IProject project) {
        ProjectPage page = new ProjectPage(project, formToolKit);
        page.createControl(book);

        pages.put(project, page);

        return page;
    }

    /**
     * Create the project viewer.
     * 
     * @param toolkit
     * @param parent
     * @return
     */
    private Composite createProjectViewer(FormToolkit toolkit,
            final Composite parent) {
        // Add composite to set margins to match the right-hand sash
        Composite root = toolkit.createComposite(parent);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        root.setLayout(layout);
        projectViewer = new ProjectViewer(root, SWT.NONE, toolkit);
        updateProjectViewer();

        overviewSelectionManager.setProjectViewer(projectViewer);

        return root;
    }

    /**
     * Refresh the project viewer.
     */
    private void updateProjectViewer() {
        if (projectViewer != null && !projectViewer.isDisposed()) {
            projectViewer
                    .setProjects(getProjects(resource.getProjectResources()));
        }
    }

    /**
     * Get the {@link IProject}s from the {@link ProjectResource}s.
     * 
     * @param projectResources
     * @return
     */
    private List<IProject> getProjects(
            Collection<ProjectResource> projectResources) {
        List<ProjectResource> prResources =
                new ArrayList<ProjectResource>(projectResources);

        // Sort the projects
        Collections.sort(prResources);

        List<IProject> projects =
                new ArrayList<IProject>(projectResources.size());

        for (ProjectResource res : prResources) {
            projects.add(res.getProject());
        }

        return projects;
    }

    @Override
    public void dispose() {

        ResourcesPlugin.getWorkspace()
                .removeResourceChangeListener(workspaceListener);

        if (partListener != null) {
            getSite().getPage().removePartListener(partListener);
        }

        if (gray_color != null) {
            gray_color.dispose();
        }

        for (ProjectPage page : pages.values()) {
            page.dispose();
        }
        pages.clear();

        projectViewer.dispose();

        unregisterUndoListener();

        IRCPContainer resource = RCPResourceManager.getResource();
        if (resource != null) {
            resource.removeChangeListener(this);
        }

        super.dispose();
    }

    /**
     * Set the delete global action.
     */
    private void createDeleteAction() {
        getEditorSite().getActionBars().setGlobalActionHandler(
                RibbonConsts.ACTION_DELETE.getId(),
                new OverviewDeleteObjectActionHandler(getSite(),
                        selectionProvider, projectViewer));
    }

    /**
     * Set the rename global action.
     */
    private void createRenameAction() {
        getEditorSite().getActionBars().setGlobalActionHandler(
                RibbonConsts.ACTION_RENAME.getId(),
                new OverviewRenameObjectActionHandler(getSite(),
                        selectionProvider));
    }

    /**
     * Set the undo global action.
     */
    private void createUndoAction() {
        IUndoContext workspaceContext =
                ResourcesPlugin.getWorkspace().getAdapter(IUndoContext.class);

        getEditorSite().getActionBars().setGlobalActionHandler(
                ActionFactory.UNDO.getId(),
                new UndoActionHandler(getSite(), workspaceContext));

        registerUndoListener();
    }

    /**
     * Register the undo history listener.
     */
    private void registerUndoListener() {
        IWorkbenchOperationSupport operationSupport =
                PlatformUI.getWorkbench().getOperationSupport();
        if (operationSupport != null) {
            IOperationHistory history = operationSupport.getOperationHistory();
            if (history != null) {
                undoListener = new UndoListener();
                history.addOperationHistoryListener(undoListener);
            }
        }
    }

    /**
     * Unregister the undo history listener.
     */
    private void unregisterUndoListener() {
        if (undoListener != null) {
            IWorkbenchOperationSupport operationSupport =
                    PlatformUI.getWorkbench().getOperationSupport();
            if (operationSupport != null) {
                IOperationHistory history =
                        operationSupport.getOperationHistory();
                if (history != null) {
                    history.removeOperationHistoryListener(undoListener);
                }
            }
        }
    }

    @Override
    public void resourceChanged(IRCPResource resource,
            RCPResourceChangeEvent event) {

        if (mainForm != null && !mainForm.isDisposed()) {
            if (event.eventObj instanceof IProject) {
                // Project affected
                updateProjectViewer();
            }

            if (pages != null) {
                if (event.eventObj instanceof IProject) {
                    /*
                     * If a project has been deleted then dispose its project
                     * page
                     */
                    if (event.eventType == RCPResourceEventType.REMOVED) {
                        final ProjectPage projectPage =
                                pages.get(event.eventObj);
                        if (projectPage != null) {
                            getEditorSite().getShell().getDisplay()
                                    .syncExec(new Runnable() {

                                        @Override
                                        public void run() {
                                            projectPage.dispose();
                                        }
                                    });
                            pages.remove(event.eventObj);
                        }
                    }

                }

                if (event.source instanceof ProjectResource) {
                    IProject project =
                            ((ProjectResource) event.source).getProject();
                    ProjectPage projectPage = pages.get(project);
                    if (projectPage != null) {
                        projectPage.handleResourceChangedEvent(resource, event);
                    }
                }
            }
        }
    }

    /**
     * Selection listener that will update the page in the page book when a
     * project selection has been made.
     * 
     */
    private class ProjectViewSelectionChangeListener
            implements ISelectionChangedListener {

        private final ISelectionProvider selectionProvider;

        public ProjectViewSelectionChangeListener(
                ISelectionProvider selectionProvider) {
            this.selectionProvider = selectionProvider;
        }

        /**
         * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
         * 
         * @param event
         */
        @Override
        public void selectionChanged(SelectionChangedEvent event) {

            IStructuredSelection selection =
                    (IStructuredSelection) event.getSelection();

            if (selection.size() > 1) {
                book.showPage(multiProjectPage.getControl());
            } else if (selection.getFirstElement() instanceof IProject) {
                projectViewer.updateToolBarActions(selection);

                Page page = pages.get(selection.getFirstElement());
                if (page != null) {
                    book.showPage(page.getControl());
                } else {
                    page = addProjectSections(formToolKit,
                            (IProject) selection.getFirstElement());

                    if (page != null) {
                        book.showPage(page.getControl());
                    }
                }

            }

            if (selectionProvider != null) {
                selectionProvider.setSelection(selection);
            }
        }
    }

    /**
     * The page book page for the project content.
     * 
     */
    private class ProjectPage extends Page {

        private final IProject project;

        private Composite root;

        private final FormToolkit toolkit;

        private final Map<AbstractModelProvider, ModelSection> sectionsMap;

        private final IModelProviderChangeListener providerChangeListener;

        private ProcessModelProvider processProvider;

        private BOMModelProvider bomProvider;

        private OMModelProvider omProvider;

        public ProjectPage(IProject project, FormToolkit toolkit) {
            this.project = project;
            this.toolkit = toolkit;
            sectionsMap = new HashMap<AbstractModelProvider, ModelSection>();

            providerChangeListener = new IModelProviderChangeListener() {

                @Override
                public void handleProviderChange(AbstractModelProvider source,
                        Object element, Object event) {
                    ModelSection section = sectionsMap.get(source);
                    if (section != null) {
                        // Update the viewer
                        TreeViewer viewer = section.getViewer();
                        if (viewer != null
                                && !viewer.getControl().isDisposed()) {
                            viewer.refresh(element);
                        }
                    }
                }
            };
        }

        /**
         * @see org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {
            root = toolkit.createComposite(parent);

            processProvider = new ProcessModelProvider(window);
            bomProvider = new BOMModelProvider(window);
            omProvider = new OMModelProvider(window);

            boolean hasProcesses = processProvider.hasActions(project);
            boolean hasBOMs = bomProvider.hasActions(project);
            boolean hasOMs = omProvider.hasActions(project);

            int sectionCount = (hasProcesses ? 1 : 0) + (hasBOMs ? 1 : 0)
                    + (hasOMs ? 1 : 0);

            // If there are going to be more than 1 section then create 2
            // columns
            if (sectionCount > 1) {
                root.setLayout(new GridLayout(2, true));
            } else {
                root.setLayout(new GridLayout());
            }

            Composite column = root;
            if (hasProcesses) {
                Section procSection = createSection(toolkit,
                        root,
                        processProvider,
                        Messages.OverviewEditor_processes_section_title,
                        project);

                procSection.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true));

                // Register change listener
                processProvider.addChangeListener(providerChangeListener);

                /*
                 * Create right column for the other models
                 */
                Composite rightColumn = toolkit.createComposite(root);
                rightColumn.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true));
                GridLayout layout = new GridLayout();
                layout.marginWidth = 0;
                layout.marginHeight = 0;
                rightColumn.setLayout(layout);
                column = rightColumn;
            }

            if (hasBOMs) {
                Section bomSection = createSection(toolkit,
                        column,
                        bomProvider,
                        Messages.OverviewEditor_bom_section_title,
                        project);
                bomSection.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true));

                // Register change listener
                bomProvider.addChangeListener(providerChangeListener);

            }
            if (hasOMs) {
                Section omSection = createSection(toolkit,
                        column,
                        omProvider,
                        Messages.OverviewEditor_om_section_title,
                        project);
                omSection.setLayoutData(
                        new GridData(SWT.FILL, SWT.FILL, true, true));

                // Register change listener
                omProvider.addChangeListener(providerChangeListener);
            }
        }

        /**
         * Create the model section.
         * 
         * @param toolkit
         * @param parent
         * @param provider
         * @param sectionTitle
         * @param project
         * @return
         */
        private Section createSection(FormToolkit toolkit, Composite parent,
                AbstractModelProvider provider, String sectionTitle,
                IProject project) {
            ModelSection modelSection = new ModelSection(window, provider,
                    selectionProvider, toolkit, sectionTitle, project);
            sectionsMap.put(provider, modelSection);

            // Create the section and the viewer
            Section section = modelSection.createSection(parent);
            /*
             * Register the section with the selection manager
             */
            overviewSelectionManager.addModelSection(modelSection);

            return section;
        }

        /**
         * @see org.eclipse.ui.part.Page#dispose()
         * 
         */
        @Override
        public void dispose() {
            if (processProvider != null) {
                processProvider.removeChangeListener(providerChangeListener);
                processProvider.dispose();
            }

            if (bomProvider != null) {
                bomProvider.removeChangeListener(providerChangeListener);
                bomProvider.dispose();
            }

            if (omProvider != null) {
                omProvider.removeChangeListener(providerChangeListener);
                omProvider.dispose();
            }

            for (ModelSection section : sectionsMap.values()) {
                overviewSelectionManager.removeModelSection(section);
            }

            super.dispose();
        }

        /**
         * @see org.eclipse.ui.part.Page#getControl()
         * 
         * @return
         */
        @Override
        public Control getControl() {
            return root;
        }

        /**
         * @see org.eclipse.ui.part.Page#setFocus()
         * 
         */
        @Override
        public void setFocus() {

        }

        /**
         * Refresh the viewer's in this section
         * 
         */
        public void refresh() {
            for (ModelSection section : sectionsMap.values()) {
                TreeViewer viewer = section.getViewer();
                if (viewer != null && !viewer.getControl().isDisposed()) {
                    viewer.refresh();
                }
            }
        }

        /**
         * Handle the resource change event.
         * 
         * @param resource
         * @param event
         */
        public void handleResourceChangedEvent(IRCPResource resource,
                RCPResourceChangeEvent event) {
            for (ModelSection section : sectionsMap.values()) {
                section.resourceChanged(resource,
                        event.eventType,
                        event.eventObj);
            }
        }

        /**
         * Select the given element in this page.
         * 
         * @param element
         */
        public void setSelection(Object element) {
            Object elementToSelect = null;
            if (element instanceof IFile) {
                final WorkingCopy wc =
                        WorkingCopyUtil.getWorkingCopy((IFile) element);
                if (wc != null && wc.getRootElement() != null) {
                    elementToSelect = wc.getRootElement();
                } else if (element instanceof Process
                        || element instanceof OrgModel) {
                    elementToSelect = element;
                }

                if (elementToSelect != null) {
                    final Object objToSelect = elementToSelect;

                    ProjectViewOverviewPage.this.getSite().getShell()
                            .getDisplay().asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    for (Entry<AbstractModelProvider, ModelSection> entry : sectionsMap
                                            .entrySet()) {
                                        if (entry.getKey().isElementOfInterest(
                                                objToSelect)) {
                                            TreeViewer viewer = entry.getValue()
                                                    .getViewer();
                                            if (viewer != null
                                                    && !viewer.getControl()
                                                            .isDisposed()) {
                                                viewer.refresh();
                                                viewer.setSelection(
                                                        new StructuredSelection(
                                                                objToSelect));
                                            }
                                        }
                                    }
                                }
                            });
                }

            }
        }
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
     * 
     * @return
     */
    @Override
    public String getContributorId() {
        if (selectionProvider != null) {
            ISelection selection = selectionProvider.getSelection();
            if (selection instanceof IStructuredSelection
                    && ((IStructuredSelection) selection).size() == 1) {
                Object firstElement =
                        ((IStructuredSelection) selection).getFirstElement();
                return RCPPropertySectionHelper.getContributorId(firstElement);
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.part.MultiPageEditorPart#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IPropertySheetPage.class) {
            TabbedPropertySheetPage p = new TabbedPropertySheetPage(this);
            return p;
        }
        return super.getAdapter(adapter);
    }

    /**
     * Operation history listener that will try to highlight the affected
     * project when an undo is performed.
     * 
     */
    private class UndoListener implements IOperationHistoryListener {

        /**
         * @see org.eclipse.core.commands.operations.IOperationHistoryListener#historyNotification(org.eclipse.core.commands.operations.OperationHistoryEvent)
         * 
         * @param event
         */
        @Override
        public void historyNotification(OperationHistoryEvent event) {

            if (event.getEventType() == OperationHistoryEvent.UNDONE) {
                Object affectedObject = getAffectedObject(event.getOperation());

                if (affectedObject instanceof IResource) {
                    IProject project =
                            ((IResource) affectedObject).getProject();
                    // Select the affected project
                    projectViewer.setSelection(project);
                    ProjectPage page = pages.get(project);
                    if (page != null) {
                        page.setSelection(affectedObject);
                    }
                }
            }
        }

        /**
         * Try and ascertain the affected resource from the undo operation.
         * 
         * @param operation
         * @return
         */
        @SuppressWarnings("restriction")
        private Object getAffectedObject(IUndoableOperation operation) {
            if (operation instanceof TriggeredOperations) {
                IUndoableOperation triggeringOperation =
                        ((TriggeredOperations) operation)
                                .getTriggeringOperation();

                if (triggeringOperation instanceof UndoableOperation2ChangeAdapter) {
                    UndoableOperation2ChangeAdapter undoableOperation2ChangeAdapter =
                            (UndoableOperation2ChangeAdapter) triggeringOperation;

                    ChangeDescriptor descriptor =
                            undoableOperation2ChangeAdapter
                                    .getChangeDescriptor();

                    if (descriptor instanceof RefactoringChangeDescriptor) {
                        RefactoringDescriptor refactoringDescriptor =
                                ((RefactoringChangeDescriptor) descriptor)
                                        .getRefactoringDescriptor();

                        if (refactoringDescriptor instanceof DeleteResourcesDescriptor) {
                            IPath[] resourcePaths =
                                    ((DeleteResourcesDescriptor) refactoringDescriptor)
                                            .getResourcePaths();

                            if (resourcePaths != null
                                    && resourcePaths.length > 0) {
                                return ResourcesPlugin.getWorkspace().getRoot()
                                        .findMember(resourcePaths[0]);
                            }
                        }
                    }
                }
            }

            return null;
        }
    }

    /**
     * Resource workspace listener that will refresh the relevant part of the
     * overview page when validation markers have changed (to update the error
     * marker overlay).
     * 
     */
    private class WorkspaceListener
            implements IResourceChangeListener, IResourceDeltaVisitor {

        private final Set<IProject> projectsToRefresh;

        public WorkspaceListener() {
            projectsToRefresh = new HashSet<IProject>();
        }

        /**
         * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
         * 
         * @param event
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta delta = event.getDelta();
            projectsToRefresh.clear();
            if (delta != null) {
                try {
                    delta.accept(this);
                } catch (CoreException e) {
                    // Log error
                    RCPActivator.getDefault().getLogger().error(e);
                }
            }

            if (!projectsToRefresh.isEmpty()) {

                getSite().getShell().getDisplay().asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        /*
                         * For each project update the affected resource in the
                         * model section and then update the project in the
                         * project viewer.
                         */
                        for (IProject project : projectsToRefresh) {
                            ProjectPage projectPage = pages.get(project);
                            if (projectPage != null) {
                                projectPage.refresh();
                                projectViewer.refresh(project);
                            }
                        }
                    }
                });
            }

        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            /*
             * If markers have been affected then update project viewer
             */
            if ((delta.getFlags() & IResourceDelta.MARKERS) != 0) {
                IResource resource = delta.getResource();

                if (resource != null) {
                    projectsToRefresh.add(resource.getProject());
                }
                return false;
            }

            return true;
        }
    }

    /**
     * Book page shown when multiple projects are selected.
     * 
     */
    private class MultiProjectPage extends Page {

        private final FormToolkit toolkit;

        private Composite composite;

        /**
         * 
         */
        public MultiProjectPage(FormToolkit toolkit) {
            this.toolkit = toolkit;
        }

        /**
         * @see org.eclipse.ui.part.Page#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {
            Composite root = toolkit.createComposite(parent);
            FillLayout layout = new FillLayout();
            layout.marginWidth = 5;
            layout.marginHeight = 5;
            root.setLayout(layout);
            this.composite = root;

            Section section = toolkit.createSection(root,
                    Section.EXPANDED | Section.TITLE_BAR);

            section.setText(
                    Messages.ProjectViewOverviewPage_multipleProjectsSelected_section_title);
            Composite composite = toolkit.createComposite(section);
            layout = new FillLayout();
            layout.marginWidth = 10;
            layout.marginHeight = 10;
            composite.setLayout(layout);

            Label label = toolkit.createLabel(composite,
                    Messages.ProjectViewOverviewPage_multipleProjectsSelected_section_longdesc,
                    SWT.WRAP);

            label.setForeground(
                    parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));

            section.setClient(composite);
        }

        /**
         * @see org.eclipse.ui.part.Page#getControl()
         * 
         * @return
         */
        @Override
        public Control getControl() {
            return composite;
        }

        /**
         * @see org.eclipse.ui.part.Page#setFocus()
         * 
         */
        @Override
        public void setFocus() {
            if (composite != null && !composite.isDisposed()) {
                composite.setFocus();
            }
        }

    }

    /**
     * Manages the selection of items in the Overview page. Only one viewer
     * should have an item selected - by default all viewers will still show a
     * selection even when the control has lost focus.
     */
    private class OverviewSelectionManager extends FocusAdapter {

        private final List<TreeViewer> viewers;

        private Control projectViewerControl;

        public Control focusedControlInEditor = null;

        /**
         * 
         */
        public OverviewSelectionManager() {
            viewers = new ArrayList<TreeViewer>();
        }

        /**
         * @param projectViewer
         *            the projectViewer to set
         */
        public void setProjectViewer(ProjectViewer projectViewer) {
            if (projectViewer != null && projectViewer.getViewer() != null) {
                Control control = projectViewer.getViewer().getControl();
                if (!control.isDisposed()) {
                    projectViewerControl = control;
                    control.addFocusListener(this);
                }
            }
        }

        /**
         * Add the viewer in this model section to this manager.
         * 
         * @param section
         */
        public void addModelSection(ModelSection section) {
            if (section != null) {
                TreeViewer viewer = section.getViewer();
                if (viewer != null && !viewer.getControl().isDisposed()) {
                    viewers.add(viewer);
                    viewer.getControl().addFocusListener(this);
                }
            }
        }

        /**
         * Remove the viewer in this model section from this manager.
         * 
         * @param section
         */
        public void removeModelSection(ModelSection section) {
            if (section != null) {
                TreeViewer viewer = section.getViewer();
                if (viewer != null) {
                    viewers.remove(viewer);
                    if (!viewer.getControl().isDisposed()) {
                        viewer.getControl().removeFocusListener(this);
                    }
                }
            }
        }

        /**
         * @see org.eclipse.swt.events.FocusAdapter#focusGained(org.eclipse.swt.events.FocusEvent)
         * 
         * @param e
         */
        @Override
        public void focusGained(FocusEvent e) {
            Widget inFocus = e.widget;

            /*
             * If a project from the project viewer is selected then unselect
             * all the other viewers, otherwise unselect all viewers that do not
             * have the current focus (project viewer will always have the
             * current project selected).
             */
            if (inFocus == projectViewerControl) {
                inFocus = null;
            }

            for (TreeViewer viewer : viewers) {
                Tree tree = viewer.getTree();

                if (!tree.isDisposed() && tree != inFocus) {
                    tree.deselectAll();
                }
            }
        }

    }

    private class PartListener implements IPartListener {

        private final IEditorSite site;

        private ISelection selectionOnDeactivate;

        /**
         * @param site
         */
        public PartListener(IEditorSite site) {
            this.site = site;
        }

        /**
         * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
         * 
         * @param part
         */
        @Override
        public void partActivated(IWorkbenchPart part) {
            if (part.getSite() == site) {

                /*
                 * Sid XPD-8302. Now that ribbon control is a separate ViewPart
                 * it means that the the OverviewPart gets deactivated when a
                 * ribbon button is pressed.
                 * 
                 * Here we always used to set the selection back to the project
                 * on activation which meant that if the user slects a process
                 * in the overview page and presses delete then the selection
                 * changes to project before the delete action occurs and then
                 * the delete happens on the project instead of the selected
                 * process/bom etc.
                 * 
                 * So Now we track the selection on deactivation so that we can
                 * reselct the right object on activation.
                 */

                if (selectionOnDeactivate != null) {
                    selectionProvider.setSelection(selectionOnDeactivate);

                } else {
                    ISelection selection =
                            projectViewer.getViewer().getSelection();

                    if (selection != null) {
                        selectionProvider.setSelection(selection);
                    }
                }

                projectViewer.setFocus();

            }
        }

        /**
         * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
         * 
         * @param part
         */
        @Override
        public void partBroughtToTop(IWorkbenchPart part) {
        }

        /**
         * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
         * 
         * @param part
         */
        @Override
        public void partClosed(IWorkbenchPart part) {
        }

        /**
         * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
         * 
         * @param part
         */
        @Override
        public void partDeactivated(IWorkbenchPart part) {
            /*
             * Sid XPD-8302. Now that ribbon control is a separate ViewPart it
             * means that the the OverviewPart gets deactivated when a ribbon
             * button is pressed.
             * 
             * Here we always used to set the selection back to the project on
             * activation which meant that if the user slects a process in the
             * overview page and presses delete then the selection changes to
             * project before the delete action occurs and then the delete
             * happens on the project instead of the selected process/bom etc.
             * 
             * So Now we track the selection on deactivation so that we can
             * reselct the right object on activation.
             */

            selectionOnDeactivate = selectionProvider.getSelection();
        }

        /**
         * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
         * 
         * @param part
         */
        @Override
        public void partOpened(IWorkbenchPart part) {
        }

    }

}
