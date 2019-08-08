/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil;
import com.tibco.xpd.ui.projectexplorer.util.SpecialFolderPresentationUtil.PresentationType;

/**
 * Abstract pipeline tree content provider for the Project Explorer to be used
 * as the base for the content provider set in the extension point
 * <code>org.eclipse.ui.navigator.navigatorContent</code> . This provides
 * special folders support. Navigator content providers that intend to handle
 * special folders should subclass this class.
 * <p>
 * <p>
 * A label provider <code>{@link ProjectExplorerLabelProvider}</code> is also
 * provided to be paired with this content provider in the navigatorContent.
 * This label provider can be used directly or subclassed if there is need to
 * provide labels for additional objects.
 * </p>
 * The following methods can be overridden by the subclass (see the methods for
 * more details):
 * <ul>
 * <li><code>{@link #doInit(ICommonContentExtensionSite)}</code></li>
 * <li><code>{@link #inputChanged(Viewer, Object, Object)}</code></li>
 * <li><code>{@link #doVisit(IResourceDelta)}</code></li>
 * <li><code>{@link #dispose()}</code></li>
 * </ul>
 * </p>
 * <p>
 * The following methods have to be implemented (see the methods for more
 * details):
 * <ul>
 * <li><code>{@link #doGetPipelinedChildren(Object, Set)}</code></li>
 * <li><code>{@link #doGetParent(Object)}</code></li>
 * <li><code>{@link #doHasChildren(Object)}</code></li>
 * <li><code>{@link #doGetChildren(Object)}</code></li>
 * <li><code>{@link #getSpecialFolderKindInclusion()}</code></li>
 * </ul>
 * </p>
 * <p>
 * The following methods are available for accessing data:
 * <ul>
 * <li><code>{@link #getViewer()}</code> - Get the current tree viewer,</li>
 * </ul>
 * </p>
 * <p>
 * Special folders used in the implementation of this class have to be
 * registered using the extension point
 * <code>com.tibco.xpd.resources.specialFolders</code>.
 * </p>
 * <p>
 * <h5>The pre-requisites of using this abstract class in the navigator
 * contribution:</h5>
 * For the extension point
 * <code>org.eclipse.ui.navigator.navigatorContent</code> the following has to
 * be set, as a minimum, to ensure correct handling of special folders:
 * <p>
 * All navigatorContents added to the org.eclipse.ui.navigator.navigatorContent
 * have to matches the id pattern <b>"com.tibco.xpd.projectExplorer.*"</b>.
 * Similarly, all actionProviders added to this extension point have to match
 * the id pattern <b>"com.tibco.xpd.projectExplorer.action.*"</b>.
 * </p>
 * <p>
 * Set the <b>priority</b> of the navigatorContent to <b>High</b> so that it is
 * over the Java content. This is required so that the Special Folders are
 * handled correctly when they replace the IFolders they represent in the tree.<br/>
 * <br/>
 * <b>triggerPoints</b>:
 * <p>
 * The <b>triggerPoints</b> should be set at a minimum to:
 * 
 * <pre>
 * &lt;or&gt;
 *  &lt;and&gt; 
 *      &lt;instanceof value=&quot;org.eclipse.core.resources.IProject&quot;/&gt;
 *      &lt;test property=&quot;org.eclipse.core.resources.projectNature&quot;
 *          value=&quot;com.tibco.xpd.resources.bpmNature&quot;/&gt; 
 *      &lt;test property=&quot;org.eclipse.core.resources.open&quot; 
 *          value=&quot;true&quot;/&gt; 
 *  &lt;/and&gt;
 *  &lt;and&gt; 
 *      &lt;instanceof value=&quot;org.eclipse.core.resources.IFolder&quot;/&gt;
 *      &lt;test property=&quot;com.tibco.xpd.resources.specialfolder.containsSpecialFolder &quot;
 *          value=&quot;[SpecialFolder kind]&quot;/&gt; 
 *  &lt;/and&gt;
 * &lt;/or&gt;
 * </pre>
 * 
 * </p>
 * <p>
 * The <b>possibleChildren</b> should be set at a minimum of:
 * 
 * <pre>
 * &lt;or&gt;
 *  &lt;and&gt; 
 *      &lt;instanceof value=&quot;org.eclipse.core.resources.IResource&quot;/&gt;
 *      &lt;test property =&quot;com.tibco.xpd.resources.specialfolder.inSpecialFolder&quot;
 *          value=&quot;[SpecialFolder kind]&quot;/&gt; 
 *  &lt;/and&gt; 
 *  &lt;and&gt; 
 *      &lt;instanceof value=&quot;com.tibco.xpd.resources.projectconfig.SpecialFolder&quot;/&gt; 
 *      &lt;test property=&quot;com.tibco.xpd.resources.specialfolder.isOfKind&quot;
 *          value=&quot;[SpecialFolder kind]&quot;/&gt; 
 *  &lt;/and&gt;
 * &lt;/or&gt;
 * </pre>
 * 
 * (Replace <i>[SpecialFolder kind]</i> with the actual kind name of the special
 * folder being handled by the content provider.)
 * </p>
 * </p>
 * 
 * @author njpatel
 */
public abstract class AbstractSpecialFoldersContentProvider implements
        IPipelinedTreeContentProvider {

    /**
     * Project Explorer tree viewer.
     */
    private TreeViewer viewer = null;

    /**
     * Filter for the special folders to include in the tree.
     */
    private List<String> specialFolderKindInclusion = null;

    /**
     * Mapping of project to it's corresponding ProjectConfig working copy. This
     * is used to register listeners to listen to changes in the ProjectConfig.
     */
    private static Map<IProject, WorkingCopy> projectConfigWorkingCopyMap =
            new HashMap<IProject, WorkingCopy>();

    /**
     * Working copy property change listener.
     */
    private static WorkingCopyEventListener workingCopyListener = null;

    /**
     * Resource change listener.
     */
    private ResourceChangeListener resourceListener = null;

    /**
     * Instance count of this class. This value is incremented in
     * <code>{@link #init(ICommonContentExtensionSite)}</code> and decremented
     * in <code>{@link #dispose()}</code>
     */
    private static Integer instanceCount = 0;

    /**
     * Boolean to allow/suppress the SpecialFolders update, with the designated
     * IFolders to be updated to Special Folders.
     */
    private final boolean suppressSpecialFolderContent;

    public AbstractSpecialFoldersContentProvider() {
        // By default DO NOT let this contribution replace IFolder with special
        // folder.
        this(true);
    }

    /**
     * The default constructor suppresses the update of IFolders to
     * SpecialFolders. Use this constructor to allow the update of designated
     * IFolders to Special folders. The value false for
     * supressSpecialFolderContent will allow the update.
     * 
     * @param b
     */
    public AbstractSpecialFoldersContentProvider(
            boolean suppressSpecialFolderContent) {
        this.suppressSpecialFolderContent = suppressSpecialFolderContent;
    }

    /**
     * Add special folders to the project explorer.
     * 
     * @see org.eclipse.ui.navigator.IPipelinedTreeContentProvider#getPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     */
    @Override
    public final void getPipelinedChildren(Object aParent,
            Set theCurrentChildren) {

        if (aParent instanceof IProject || aParent instanceof IFolder) {
            IProject project;

            // Get the project
            if (aParent instanceof IProject) {
                project = (IProject) aParent;
            } else {
                project = ((IFolder) aParent).getProject();
            }

            // Only proceed if the project is open
            if (project.isOpen()) {
                updateSpecialFolders(project, aParent, theCurrentChildren);
            }
        }

        // Delegate call to subclasses
        doGetPipelinedChildren(aParent, theCurrentChildren);
    }

    /**
     * Update the folders marked as special folders - this will delete the
     * <code>IFolder</code> marked as a special folder and a special folder
     * inserted at the right location. If the special folder presentation is set
     * at project level then the folders will be inserted under the project,
     * otherwise the special folders will just replace their
     * <code>IFolder</code> objects.
     * 
     * @param project
     * @param parent
     * @param theCurrentChildren
     */
    @SuppressWarnings("unchecked")
    protected void updateSpecialFolders(IProject project, Object parent,
            Set theCurrentChildren) {
        // when suppressSpecialFolderContent, do not process the special
        // folders, this is done to process all special folders in one content
        // provider i.e. CommonSpecialFolderContentProvider.
        if (!suppressSpecialFolderContent && project != null && parent != null
                && theCurrentChildren != null) {

            // If the project level presentation is used then add special
            // folders under the project
            if (isProjectLevelPresentation()) {
                List<SpecialFolder> sf =
                        getSpecialFolders(parent, project, theCurrentChildren);
                // Remove designated special folders from the children list, if
                // any
                removeIFoldersMarkedAsSpecialFolders(project,
                        theCurrentChildren,
                        sf);

                // If this is a project then add the special folders
                if (parent instanceof IProject) {
                    addSpecialFoldersToProject(project, theCurrentChildren, sf);
                }
            } else {
                // Replace the IFolder's marked as special folder with their
                // respective SpecialFolder objects
                Set<Object> newChildrenList = new HashSet<Object>();
                for (Object child : theCurrentChildren) {
                    Object newObj = null;

                    if (child instanceof IFolder) {
                        SpecialFolder sf = getSpecialFolder((IFolder) child);

                        if (sf != null) {
                            newObj = sf;
                        }
                    }

                    // If this child is not a special folder then copy the child
                    // back into the list
                    if (newObj == null) {
                        newObj = child;
                    }

                    newChildrenList.add(newObj);
                }

                // Update the children list
                theCurrentChildren.clear();
                theCurrentChildren.addAll(newChildrenList);
            }

            registerProjectConfigListener(project);
        }
    }

    /**
     * This method is called by
     * <code>{@link #getPipelinedChildren(Object, Set)}</code> to update the
     * children set for the given parent.
     * 
     * @see org.eclipse.ui.navigator.IPipelinedTreeContentProvider#getPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     * 
     * @param aParent
     * @param theCurrentChildren
     */
    protected abstract void doGetPipelinedChildren(Object aParent,
            Set theCurrentChildren);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#getPipelinedElements
     * (java.lang.Object, java.util.Set)
     */
    @Override
    public void getPipelinedElements(Object anInput, Set theCurrentElements) {
        getPipelinedChildren(anInput, theCurrentElements);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#getPipelinedParent
     * (java.lang.Object, java.lang.Object)
     */
    @Override
    public Object getPipelinedParent(Object anObject, Object aSuggestedParent) {
        Object parent = getParent(anObject);

        /*
         * If the parent is a folder and the suggested parent is a special
         * folder that represents this folder then return the special folder as
         * the parent
         */
        if (parent != null
                && aSuggestedParent instanceof SpecialFolder
                && parent
                        .equals(((SpecialFolder) aSuggestedParent).getFolder())) {
            parent = aSuggestedParent;
        }

        return parent != null ? parent : aSuggestedParent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#interceptAdd(org
     * .eclipse.ui.navigator.PipelinedShapeModification)
     */
    @Override
    public PipelinedShapeModification interceptAdd(
            PipelinedShapeModification anAddModification) {
        /*
         * Do nothing as all changes to resources are handled by the
         * ResourceChange event
         */
        return anAddModification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#interceptRefresh
     * (org.eclipse.ui.navigator.PipelinedViewerUpdate)
     */
    @Override
    public boolean interceptRefresh(
            PipelinedViewerUpdate aRefreshSynchronization) {
        /*
         * Do nothing as all changes to resources are handled by the
         * ResourceChange event
         */
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#interceptRemove
     * (org.eclipse.ui.navigator.PipelinedShapeModification)
     */
    @Override
    public PipelinedShapeModification interceptRemove(
            PipelinedShapeModification aRemoveModification) {
        /*
         * Do nothing here. ResourceChange will take care of items being
         * removed. The reason for this is that if, for instance, a folder is
         * being removed then we need to remove any mapping info we have on the
         * packages (if any) contained within this folder. This method will only
         * provide us with the folder but will not allow us to see the contents
         * of the folder as they have already been removed.
         */
        return aRemoveModification;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IPipelinedTreeContentProvider#interceptUpdate
     * (org.eclipse.ui.navigator.PipelinedViewerUpdate)
     */
    @Override
    public boolean interceptUpdate(PipelinedViewerUpdate anUpdateSynchronization) {
        /*
         * Do nothing as all changes to resources are handled by the
         * ResourceChange event
         */
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.ICommonContentProvider#init(org.eclipse.ui.navigator
     * .ICommonContentExtensionSite)
     */
    @Override
    public final void init(ICommonContentExtensionSite aConfig) {

        initialise();

        // Register the resource listener if not already done so
        if (resourceListener == null) {
            resourceListener = new ResourceChangeListener();

            ResourcesPlugin.getWorkspace()
                    .addResourceChangeListener(resourceListener,
                            IResourceChangeEvent.POST_CHANGE);
        }

        updateInclusionList();

        // Delegate call to subclasses if they wish to initialise
        doInit(aConfig);
    }

    /**
     * Update the special folder kind inclusion list
     */
    protected void updateInclusionList() {
        String[] filter = getSpecialFolderKindInclusion();

        if (filter != null) {
            specialFolderKindInclusion = new ArrayList<String>();

            for (String kind : filter) {
                specialFolderKindInclusion.add(kind);
            }
        } else {
            specialFolderKindInclusion = null;
        }
    }

    /**
     * Increment the instance count
     */
    private synchronized void initialise() {
        // Increment instance count
        ++instanceCount;

    }

    /**
     * Called by <code>{@link #init(ICommonContentExtensionSite)}</code> to run
     * any initialisation. Subclasses can override this method to carry out any
     * specific initialisation. Default behaviour of this method is to do
     * nothing.
     * 
     * @see #init(ICommonContentExtensionSite)
     * 
     * @param aConfig
     */
    public void doInit(ICommonContentExtensionSite aConfig) {
        // Do nothing. Subclass can override to run initialisation.
    }

    /**
     * Called in response to a resource change in the workspace. Subclasses can
     * override this method if they wish to respond to a resource change. The
     * default behaviour is to do nothing.
     * <p>
     * NOTE: This method is called from within a <code>WorkspaceJob</code>.
     * </p>
     * 
     * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
     * 
     * @param delta
     * @throws CoreException
     */
    protected void doVisit(IResourceDelta delta) throws CoreException {
        // Do nothing. Subclass can override to respond to resource change.
    }

    /**
     * Remove any listeners and dispose off resources. Subclasses may override
     * this method but they must call this super method.
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {

        if (resourceListener != null) {
            // Remove the resource change listener
            ResourcesPlugin.getWorkspace()
                    .removeResourceChangeListener(resourceListener);

            resourceListener = null;
        }

        uninitialise();
    }

    /**
     * Check if project level presentation is set for special folders
     * 
     * @return <code>true</code> if project level presentation is set,
     *         <code>false</code> otherwise.
     */
    protected boolean isProjectLevelPresentation() {
        return SpecialFolderPresentationUtil.getPreferenceValue() == PresentationType.PROJECTLEVEL;
    }

    /**
     * Called by <code>{@link #dispose()}</code>. This will decrement the
     * instance count and also unregister listeners if this is the last instance
     * of this class.
     */
    private synchronized void uninitialise() {
        // Decrement the instance count
        --instanceCount;

        /*
         * If this is the last instance of this class to be disposed then
         * unregister with all listeners
         */
        if (instanceCount <= 0) {
            if (!projectConfigWorkingCopyMap.isEmpty()
                    && workingCopyListener != null) {
                // Remove all working copy listeners
                for (WorkingCopy wc : projectConfigWorkingCopyMap.values()) {
                    wc.removeListener(workingCopyListener);
                }

                // Clear the map
                projectConfigWorkingCopyMap.clear();

                workingCopyListener = null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
     * Object)
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof SpecialFolder) {
            // This is a special folder
            SpecialFolder sFolder = (SpecialFolder) parentElement;
            IFolder folder = sFolder.getFolder();

            if (folder != null) {
                try {
                    children = folder.members();
                } catch (CoreException e) {
                    // Do nothing
                }
            }
        } else {
            children = doGetChildren(parentElement);
        }

        return children != null ? children : new Object[0];
    }

    /**
     * Get the children of the <i>parentElement</i>.
     * 
     * @param parentElement
     * @return Array of objects representing the children of the
     *         <i>parentElement</i>. If the <i>parentElement</i> has no children
     *         then return <b>null</b>.
     */
    protected abstract Object[] doGetChildren(Object parentElement);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
     * )
     */
    @Override
    public final Object getParent(Object element) {
        Object parent = null;

        if (element instanceof SpecialFolder) {
            // If this is a special folder then the parent is going to be the
            // project
            SpecialFolder sFolder = (SpecialFolder) element;

            if (sFolder.getProject() != null) {
                parent = sFolder.getProject();
            } else {
                IFolder folder = sFolder.getFolder();

                if (folder != null) {
                    parent = folder.getParent();
                }
            }

        } else if (element instanceof IResource) {
            IResource resource = (IResource) element;

            /*
             * If this resource is in a folder then we need to check if this
             * folder is a special folder - if it is then the parent will be the
             * packages folder and not the IFolder
             */
            if (resource.getParent() instanceof IFolder) {
                IFolder folder = (IFolder) resource.getParent();

                if (folder != null) {
                    // Check if the folder is a special folder
                    SpecialFolder sFolder = getSpecialFolder(folder);

                    /*
                     * If we have the special folder then that will be the
                     * parent, otherwise the IFolder will be the parent
                     */
                    if (sFolder != null)
                        parent = sFolder;
                    else
                        /*
                         * XPD-3951: This folder is not a special folder so
                         * return null so that other navigator content providers
                         * get asked too (in case one of them is managing this
                         * folder as a special folder).
                         */
                        parent = null;
                } else {
                    // folder is null or can't get SpecialFolder model for the
                    // project
                    parent = folder;
                }
            } else {
                // Parent is not IFolder
                parent = resource.getParent();
            }
        } else {
            // Get parent from the subclass
            parent = doGetParent(element);
        }

        return parent;
    }

    /**
     * Get the parent of the given <i>element</i>.
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    protected abstract Object doGetParent(Object element);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
     * Object)
     */
    @Override
    public boolean hasChildren(Object element) {
        boolean gotChildren = false;

        if (element instanceof SpecialFolder) {
            gotChildren = (getChildren(element).length > 0);
        } else {
            gotChildren = doHasChildren(element);
        }

        return gotChildren;
    }

    /**
     * Check if the given <i>element</i> has children.
     * 
     * @param element
     * @return
     */
    protected abstract boolean doHasChildren(Object element);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
     * .lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /**
     * Register the <i>viewer</i> when the input changes. Subclasses may
     * override this but they should call this method to register the viewer.
     * The registered viewer can be accessed using the method
     * <code>{@link #getViewer()}</code>.
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // If new input has changed then register the viewer
        if (newInput != null) {
            this.viewer = (TreeViewer) viewer;
        } else {
            this.viewer = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IMementoAware#restoreState(org.eclipse.ui.IMemento
     * )
     */
    @Override
    public void restoreState(IMemento aMemento) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.navigator.IMementoAware#saveState(org.eclipse.ui.IMemento)
     */
    @Override
    public void saveState(IMemento aMemento) {
        // Do nothing
    }

    /**
     * Get the tree viewer
     * 
     * @return
     */
    public final TreeViewer getViewer() {
        return viewer;
    }

    /**
     * Set the <code>SpecialFolder</code> kind inclusion list. If this is set
     * then only the special folders set in the list will be shown in the
     * project explorer.
     * 
     * @return <ul>
     *         <li><b>null or empty array</b> - Don't include any special
     *         folders. This means that any folders marked as special folders
     *         will be shown as regular folders.
     *         <li><b>kind strings</b></li> - Only show the special folders of
     *         the given kind.
     *         </ul>
     */
    public abstract String[] getSpecialFolderKindInclusion();

    /**
     * Remove all folders designated as special folders from theCurrentChildren
     * list
     * 
     * @param project
     * @param theCurrentChildren
     */
    private void removeIFoldersMarkedAsSpecialFolders(IProject project,
            Set theCurrentChildren, List<SpecialFolder> sFolders) {

        List<IFolder> folders = new ArrayList<IFolder>();
        for (SpecialFolder sf : sFolders) {
            IFolder folder = sf.getFolder();
            folders.add(folder);
        }
        if (project != null && theCurrentChildren != null) {
            // Remove any folders designated as special folders
            for (Iterator iter = theCurrentChildren.iterator(); iter.hasNext();) {
                Object child = iter.next();

                if (child instanceof IFolder) {
                    IFolder folder = (IFolder) child;

                    // If this folder is a designated
                    // special folder then remove it from the list
                    if (folders.contains(folder)) {
                        iter.remove();
                    }
                }
            }
        }
    }

    /**
     * Add any special folders in the given <i>project</i> to the root of the
     * project.
     * 
     * @param project
     * @param theCurrentChildren
     */
    @SuppressWarnings("unchecked")
    private void addSpecialFoldersToProject(IProject project,
            Set theCurrentChildren, List<SpecialFolder> sFolders) {
        if (project != null && theCurrentChildren != null) {
            if (sFolders != null && !sFolders.isEmpty()) {
                // Add the special folders to the children list if the folder it
                // represents is accessible
                for (SpecialFolder sf : sFolders) {
                    IFolder folder = sf.getFolder();

                    if (folder != null && folder.isAccessible()) {
                        theCurrentChildren.add(sf);
                    }
                }
            }
        }
    }

    /**
     * Get the special folders list for the given set of folders.
     * 
     * @param parent
     * 
     * @param project
     * @return
     */
    private List<SpecialFolder> getSpecialFolders(Object parent,
            IProject project, Set currentChildren) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        List<SpecialFolder> sFolders = new ArrayList<SpecialFolder>();

        if (config != null && config.getSpecialFolders() != null) {
            EList foldersEList = config.getSpecialFolders().getFolders();
            if (foldersEList != null) {
                /*
                 * If a special folder kind filter is provided then need to
                 * filter on that, for Specific Content Providers
                 */
                if (specialFolderKindInclusion != null) {
                    // If the list is empty then filter out all special folders
                    if (!specialFolderKindInclusion.isEmpty()) {
                        // Only include special folders that have the kind that
                        // is included in this content provider
                        for (Iterator<?> iter = foldersEList.iterator(); iter
                                .hasNext();) {
                            SpecialFolder sf = (SpecialFolder) iter.next();

                            if (sf != null
                                    && specialFolderKindInclusion.contains(sf
                                            .getKind())) {
                                sFolders.add(sf);
                            }
                        }
                    }
                } else {
                    /*
                     * Special folder kind Filter is not provided, this is true
                     * for Common Content Provider which handles all Special
                     * Folders and not of a specific kind.
                     */
                    if (currentChildren != null && !currentChildren.isEmpty()) {
                        // Only include special folders that corresponds to a
                        // folder in the provided list
                        for (Iterator<?> iter = foldersEList.iterator(); iter
                                .hasNext();) {
                            SpecialFolder sf = (SpecialFolder) iter.next();
                            // Skip the folders starting with a Dot
                            if (sf != null
                                    && sf.getFolder() != null
                                    && !sf.getFolder().getName()
                                            .startsWith(".")) { //$NON-NLS-1$

                                // if given special folder is direct child of
                                // the parent container OR indirect child under
                                // sub folder/s.
                                if (currentChildren.contains(sf.getFolder())
                                        || isIndirectChildFitToProcess(currentChildren,
                                                sf.getFolder(),
                                                foldersEList,
                                                parent)) {
                                    sFolders.add(sf);

                                }

                            }
                        }
                    }
                }
            }
        }

        return sFolders;
    }

    /**
     * This method checks if the given special folder is fit to be processed.For
     * Project Node, Special folder under IFolder is allowed to be processed
     * where as Special Folder under a derived folder [like .forms/gwt] is not
     * fit to be processed. For Folder Node, Special folders under an IFolder is
     * fit to be processed.
     * 
     * @param currentChildren
     * @param folder
     * @param foldersEList
     * @param parentNode
     * @return
     */
    private boolean isIndirectChildFitToProcess(Set currentChildren,
            IFolder folder, EList foldersEList, Object parentNode) {
        // get parent of the Special Folder
        IContainer parentContainer = folder.getParent();
        // while parent is not null and it does not satisfies given checks
        while (parentContainer != null) {

            if (parentContainer instanceof IFolder
                    && currentChildren.contains(parentContainer)) {
                /*
                 * For folder node, return true if the given special folder is
                 * child of an IFolder under the project. [Special folder under
                 * normal folders are allowed]
                 */
                if (parentNode instanceof IProject) {
                    // Exclude Special folders which are under derived IFolder
                    // like '.forms/gwt' or '.forms/intermediate'
                    return !parentContainer.getName().startsWith(".");
                }

                /*
                 * For IFolder node return the Special
                 */
                if (parentNode instanceof IFolder) {
                    return true;
                }
            }
            parentContainer = parentContainer.getParent();
        }
        return false;
    }

    /**
     * Get the <code>SpecialFolder</code> object that represents the given
     * <i>folder</i>. If the folder is not a special folder or if it is and is
     * filtered out then <b>null</b> will be returned.
     * 
     * @param folder
     * @return
     */
    protected SpecialFolder getSpecialFolder(IFolder folder) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(folder.getProject());
        SpecialFolder sFolder = null;

        if (config != null && config.getSpecialFolders() != null) {
            sFolder = config.getSpecialFolders().getFolder(folder);

            if (sFolder != null) {
                // If special folder inclusion list is set then validate this
                // special folder with it
                if (specialFolderKindInclusion != null) {
                    // If the list is empty then filter out all special folders
                    // so
                    // return null
                    if (!specialFolderKindInclusion.isEmpty()) {
                        if (!specialFolderKindInclusion.contains(sFolder
                                .getKind())) {
                            sFolder = null;
                        }
                    } else {
                        sFolder = null;
                    }
                }
            }
        }

        return sFolder;
    }

    /**
     * Register a listener with the <code>ProjectConfig</code> working copy to
     * listen for changes to special folders.
     * 
     * @param project
     */
    private synchronized void registerProjectConfigListener(IProject project) {
        if (project != null
                && !projectConfigWorkingCopyMap.containsKey(project)) {

            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            if (config != null) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(config);

                if (wc != null) {
                    // If working copy listener not instantiated yet then do so
                    if (workingCopyListener == null) {
                        workingCopyListener = new WorkingCopyEventListener();
                    }

                    wc.addListener(workingCopyListener);
                    projectConfigWorkingCopyMap.put(project, wc);
                }
            }
        }
    }

    /**
     * Unregister the listener with the <code>ProjectConfig</code> working copy.
     * 
     * @param project
     */
    private synchronized void unregisterProjectConfigListener(IProject project) {
        if (project != null && projectConfigWorkingCopyMap.containsKey(project)
                && workingCopyListener != null) {

            projectConfigWorkingCopyMap.get(project)
                    .removeListener(workingCopyListener);
            projectConfigWorkingCopyMap.remove(project);
        }
    }

    /**
     * Working copy property change listener. This will update the project in
     * the tree when a property change occurs in the <code>ProjectConfig</code>
     * working copy.
     * 
     * @author njpatel
     * 
     */
    private class WorkingCopyEventListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            WorkingCopy wc = (WorkingCopy) evt.getSource();

            /*
             * Only refresh when the working copy is dirty. On save another
             * dirty event will be fired and we can ignore that one (the command
             * stack is executed for change to special folders and immediately
             * saved, thus firing two dirty events). Also, if the working copy
             * is reloaded then refresh.
             */
            if (wc != null && !wc.getEclipseResources().isEmpty()) {
                IResource resource = wc.getEclipseResources().get(0);

                if (resource != null) {
                    final IProject project = resource.getProject();

                    if (wc.isWorkingCopyDirty()
                            || propertyName.equals(WorkingCopy.PROP_RELOADED)) {
                        asyncRefreshProject(viewer, project);
                    }

                    /*
                     * If working copy removed then unregister from listener and
                     * remove the working copy from map.
                     */
                    if (propertyName.equals(WorkingCopy.PROP_REMOVED)) {
                        unregisterProjectConfigListener(project);
                        asyncRefreshProject(viewer, project);
                    }
                }
            }
        }
    }

    /**
     * Refreshes asynchronously project in a tree viewer.
     * 
     * @param viewer
     *            the context viewer.
     * @param project
     *            the project to refresh.
     */
    private void asyncRefreshProject(final TreeViewer viewer, final IProject project) {
        if (viewer != null && !viewer.getControl().isDisposed()) {
            viewer.getControl().getDisplay().asyncExec(new Runnable() {
                @Override
                public void run() {
                    if (viewer != null && !viewer.getControl().isDisposed()) {
                        // Refresh the project
                        viewer.refresh(project);
                    }
                }
            });
        }
    }

    /**
     * Resource change listener. This will listen for changes in the workspace
     * resources.
     * 
     * @author njpatel
     * 
     */
    private class ResourceChangeListener implements IResourceChangeListener,
            IResourceDeltaVisitor {

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
         * (org.eclipse.core.resources.IResourceChangeEvent)
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            final IResourceDelta delta = event.getDelta();

            try {
                delta.accept(ResourceChangeListener.this);
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
         * .core.resources.IResourceDelta)
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            boolean ret = true;
            IResource res = delta.getResource();

            // Call any subclass implementation first
            doVisit(delta);

            if (delta.getFlags() == IResourceDelta.OPEN
                    && res instanceof IProject) {

                if (((IProject) res).isOpen()) {
                    refresh(res);
                }

                return false;
            }

            switch (delta.getKind()) {
            // THIS HAS BEEN MOVED TO THE PROCESS CONFIG WORKING COPY.
            // case IResourceDelta.REMOVED:
            // /*
            // * If a special folder has been deleted from the workspace then
            // * update the project config.
            // */
            // if (res instanceof IFolder) {
            // SpecialFolder sf = getSpecialFolder((IFolder) res);
            //
            // if (sf != null) {
            // // Remove the special folder
            // ProjectConfig config = XpdResourcesPlugin.getDefault()
            // .getProjectConfig(((IFolder) res).getProject());
            //
            // if (config != null
            // && config.getSpecialFolders() != null) {
            // try {
            // config.getSpecialFolders().removeFolder(sf);
            // } catch (IOException e) {
            // throw new CoreException(new Status(
            // IStatus.ERROR, BpmnNavigatorPlugin.ID,
            // IStatus.OK, e.getLocalizedMessage(), e));
            // }
            // }
            //
            // }
            // }
            // break;

            case IResourceDelta.CHANGED:
                if (res instanceof IFolder) {
                    // if the res is a folder, check is it is wraped into
                    // source folder and refresh the source folder as well.
                    if (res.getProject() != null) {
                        SpecialFolder sFolder = getSpecialFolder((IFolder) res);
                        if (sFolder != null) {
                            refresh(sFolder);
                        }
                    }
                }
                break;
            case IResourceDelta.ADDED:

                if (res instanceof IFolder
                        && getSpecialFolder((IFolder) res) != null) {
                    /*
                     * Folder being added is a special folder so refresh project
                     */
                    refresh(res.getProject());
                } else {
                    final IContainer parent = res.getParent();

                    /*
                     * If a resource has been added under a special folder then
                     * refresh the special folder node in the tree
                     */
                    if (parent != null) {
                        if (parent instanceof IFolder) {

                            SpecialFolder sf =
                                    getSpecialFolder((IFolder) parent);

                            // Check if the resource's parent is a special
                            // folder
                            if (sf != null) {
                                refresh(sf);
                                // No need to visit any more changes
                                ret = false;
                            }
                        }
                    }
                }
                break;
            }

            return ret;
        }

        /**
         * Refresh the given object in the tree viewer. This will be done in the
         * UI thread
         * 
         * @param obj
         */
        private void refresh(final Object obj) {
            new UIJob(
                    Messages.AbstractSpecialFoldersContentProvider_refresh_message) {

                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {
                    TreeViewer treeViewer = getViewer();

                    if (treeViewer != null && treeViewer.getControl() != null
                            && !treeViewer.getControl().isDisposed()) {
                        treeViewer.refresh(obj);
                    }
                    return Status.OK_STATUS;
                }

            }.schedule();
        }
    }
}
