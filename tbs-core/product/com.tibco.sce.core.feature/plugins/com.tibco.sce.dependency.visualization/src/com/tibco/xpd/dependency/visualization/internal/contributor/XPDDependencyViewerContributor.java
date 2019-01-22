/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.contributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution;
import com.tibco.xpd.dependency.visualization.api.EventTypeEnum;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationActivator;
import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationUtils;
import com.tibco.xpd.dependency.visualization.internal.Messages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Dependency Viewer contribution for XPD resources. This handles {@link IProject} and {@link IResource} objects.
 * @author ssirsika
 * @since 30-Sep-2015
 */
public class XPDDependencyViewerContributor extends DependencyEditorContribution {

	private ILabelProvider labelProvider = WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider();
	/**
	 * Set to store the element before returning from getElements(...)
	 */
	Set<Object> cachedElements = new HashSet<Object>();

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getElements(java.lang.Object)
	 *
	 * 
	 */
	@Override
	public Object[] getElements(Object inputElement, boolean isFilterUnrelatedItems, boolean showAllInWorkspace) {
		cachedElements.clear();
		Object[] elements = getElementsInternal(inputElement, isFilterUnrelatedItems, showAllInWorkspace);
		cachedElements.addAll(Arrays.asList(elements));
		return elements;
	}

	/**
	 * Internal private method to return array of Objects related to passed 'inputElement'
	 * @param inputElement
	 * @param isFilterUnrelatedItems
	 * @param showAllInWorkspace 
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getElements(java.lang.Object)
	 * @return
	 */
	private Object[] getElementsInternal(Object inputElement, boolean isFilterUnrelatedItems, boolean showAllInWorkspace) {
		if (inputElement instanceof IWorkspaceRoot) {
			IWorkspaceRoot workspaceRoot = (IWorkspaceRoot) inputElement;
			IProject[] projects = workspaceRoot.getProjects();
			return filterHiddenXPDProjects(projects);
		} else if (inputElement instanceof IProject) {
			IProject project = (IProject) inputElement;
			if (showAllInWorkspace) {
				IWorkspaceRoot workspaceRoot = project.getWorkspace().getRoot();
				IProject[] projects = workspaceRoot.getProjects();
				return filterHiddenXPDProjects(projects);
			}
			if (!isFilterUnrelatedItems) {
				return filterHiddenXPDProjects(getAllProjectsFromRelationshipGraph(project));
			}
			Set<IProject> projects = new HashSet<IProject>();
			ProjectUtil.getReferencedProjectsHierarchy(project, projects);
			ProjectUtil.getReferencingProjectsHierarchy(project, projects);
			projects.add(project);
			return filterHiddenXPDProjects(projects.toArray(new IProject[0]));
		} else if (inputElement instanceof IResource) {
			IResource resource = (IResource) inputElement;
			if (showAllInWorkspace) {
				Set<IResource> resourcesOfSameType = new HashSet<IResource>();
				try {
					ResourcesPlugin.getWorkspace().getRoot().accept(new ResourceProxyVisitor(resourcesOfSameType, (IResource) inputElement), IResource.NONE);
				} catch (CoreException e) {
					DependencyVisualizationActivator.logError(Messages.XPDDependencyViewerContributor_ErrorReadingWorkspaceMessage, e);
				}
				Set<IResource> result = new HashSet<IResource>();
				resourcesOfSameType.add(resource);
				Set<IResource> visitedResources = new HashSet<IResource>();
				for (IResource res : resourcesOfSameType) {
					recursivePopulateResourcessInRelationship(result, visitedResources, res);
					visitedResources.add(res);
				}
				return result.toArray(new Object[0]);
			}
			if (!isFilterUnrelatedItems) {
				return getAllResourcesFromRelationshipGraph(resource).toArray(new Object[0]);
			}
			Set<IResource> results = WorkingCopyUtil.getDeepDependencies(resource);
			results.addAll(DependencyVisualizationUtils.getDeepAffectedResources(resource));
			results.add(resource);
			return results.toArray();
		}
		return new Object[0];
	}

	/**
	 *  Return all the directly or indirectly dependent and depending resources 
	 * of passed 'resource'.
	 * @param resource whose relationship need to be traversed.
	 * @return {@link Set} or empty set in case of no relationship found. 
	 */
	private Set<IResource> getAllResourcesFromRelationshipGraph(IResource resource) {
		Set<IResource> resources = new HashSet<IResource>();
		Set<IResource> visitedResources = new HashSet<IResource>();
		recursivePopulateResourcessInRelationship(resources, visitedResources, resource);
		return resources;
	}

	/**
	 * Recursively populate {@link Set} 'resource' with dependent and depending resources of passed 'resource' 
	 * @param resources {@link Set} stores the result
	 * @param visitedResources {@link Set} to store visited resources while traversing relationship
	 * @param resource whose relationship need to be traversed.
	 */
	private void recursivePopulateResourcessInRelationship(Set<IResource> resources, Set<IResource> visitedResources, IResource resource) {
		if (visitedResources.contains(resource)) {
			return;
		}
		Set<IResource> resourcessInRelationship = getResourcesInRelationship(resource);
		visitedResources.add(resource);
		resources.addAll(resourcessInRelationship);
		for (IResource iResource : resourcessInRelationship) {
			recursivePopulateResourcessInRelationship(resources, visitedResources, iResource);
		}
	}

	/**
	 * Return {@link Set} of dependent and affected resources.
	 * @param resource {@link IResource}
	 * @return {@link Set} of result otherwise empty set in case of no relationship found.
	 */
	private Set<IResource> getResourcesInRelationship(IResource resource) {
		Set<IResource> results = WorkingCopyUtil.getDeepDependencies(resource);
		results.addAll(WorkingCopyUtil.getAffectedResources(resource));
		results.add(resource);
		return results;
	}

	/**
	 * Return all the directly or indirectly referenced and referencing projects 
	 * of passed 'project'. 
	 * @param project whose relationship need to be traversed.
	 * @return {@link IProject} array or empty array in case of no relations. 
	 */
	private IProject[] getAllProjectsFromRelationshipGraph(IProject project) {
		Set<IProject> projects = new HashSet<IProject>();
		Set<IProject> visitedProjects = new HashSet<IProject>();
		recursivePopulateProjectsFromRelationship(projects, visitedProjects, project);
		return projects.toArray(new IProject[0]);
	}

	/**
	 * Recursively populates 'projects' {@link Set} by traversing relationships of passed 'project'. 
	 * 
	 * @param projects {@link Set} which will carry result of execution. 
	 * @param visitedProjects {@link Set} which contains visited project in the relationship 
	 * @param project whose relationship need to be traversed.
	 */
	private void recursivePopulateProjectsFromRelationship(Set<IProject> projects, Set<IProject> visitedProjects, IProject project) {
		if (visitedProjects.contains(project)) {
			return;
		}
		Set<IProject> projectsInRelationship = getProjectsInRelationship(project);
		visitedProjects.add(project);
		projects.addAll(projectsInRelationship);
		for (IProject iProject : projectsInRelationship) {
			recursivePopulateProjectsFromRelationship(projects, visitedProjects, iProject);
		}
	}

	/**
	 * Return {@link Set} of all the reference and referencing projects
	 * in the hierarchy. 
	 * @param project 
	 * @return {@link Set} of result or empty set if no relationship found.
	 */
	private Set<IProject> getProjectsInRelationship(IProject project) {
		Set<IProject> projects = new HashSet<IProject>();
		ProjectUtil.getReferencedProjectsHierarchy(project, projects);
		ProjectUtil.getReferencingProjectsHierarchy(project, projects);
		projects.add(project);
		return projects;
	}

	/**
	 * Remove all the hidden XPD projects i.e projects whose name starts with '.'
	 * @param projects
	 * @return Object array of filtered result
	 */
	private Object[] filterHiddenXPDProjects(IProject[] projects) {
		List<IProject> result = new ArrayList<IProject>();
		for (IProject project : projects) {
			if (project.getName().startsWith(".")) { //$NON-NLS-1$
				continue;
			}
			result.add(project);
		}
		return result.toArray();
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getDependencies(java.lang.Object)
	 *
	 * @param obj
	 * @return Object array of dependencies on passed 'entry' otherwise empty array
	 */
	@Override
	public Object[] getDependencies(Object entity) {
		if (entity instanceof IProject) {
			IProject project = (IProject) entity;
			Set<IProject> projects;
			projects = DependencyVisualizationUtils.getReferencedXPDProjects(project);
			projects.retainAll(cachedElements);
			projects.remove(project);
			return projects.toArray(new Object[0]);
		} else if (entity instanceof IResource) {
			WorkingCopy wc = WorkingCopyUtil.getWorkingCopy((IResource) entity);
			if (wc != null) {
				List<IResource> dependencies = wc.getDependency();
				dependencies.retainAll(cachedElements);
				dependencies.remove(entity);
				return dependencies.toArray();
			}
		}
		return new Object[0];
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getDependents(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public Object[] getDependents(Object obj) {
		return null;
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getText(java.lang.Object)
	 *
	 * @param obj
	 * @return {@link String} representation of passed 'obj' otherwise empty string
	 */
	@Override
	public String getText(Object obj) {
		if (obj instanceof IProject) {
			return ((IProject) obj).getName();
		} else if (obj instanceof IResource) {
			WorkingCopy wc = WorkingCopyUtil.getWorkingCopy((IResource) obj);
			return wc != null ? wc.getName() : ((IResource) obj).getName();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#getImage()
	 *
	 * @return {@link Image} for passed 'resource'. Return null if resource is not instance of {@link IResource} 
	 */
	@Override
	public Image getImage(Object resource) {
		if (!(resource instanceof IResource)) {
			return null;
		}
		if (resource instanceof EObject) {
			return WorkingCopyUtil.getImage((EObject) resource);
		}
		return labelProvider.getImage(resource);
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#handleElementEvent(java.lang.Object)
	 *
	 *
	 */
	@Override
	public void handleElementEvent(EventTypeEnum type, Object selectedElement) {
		switch (type) {
		case DOUBLECLICK:
			if (selectedElement instanceof IProject) {
				DependencyVisualizationUtils.showInProjectExplorerView(selectedElement, 1);
			} else if (selectedElement instanceof IFile) {
				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(activePage, (IFile) selectedElement);
				} catch (PartInitException e) {
					DependencyVisualizationActivator.logError(Messages.XPDDependencyViewerContributor_ErrorOpeningFileInEditorMessage, e);
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @see com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (labelProvider != null) {
			labelProvider.dispose();
			labelProvider = null;
		}
	}

	/**
	 *
	 * Visitor to find files which matches with extension of passed 'inputElement'.
	 * @author ssirsika
	 * @since 27-Nov-2015
	 */
	class ResourceProxyVisitor implements IResourceProxyVisitor {

		private Set<IResource> fList;
		private final IResource inputElement;

		/**
		 * @param list {@link Set} in which visitors result will be stored
		 * @param inputElement element against which all the extensions will be tested 
		 */
		protected ResourceProxyVisitor(Set<IResource> list, IResource inputElement) {
			fList = list;
			this.inputElement = inputElement;
		}

		/**
		 * @see org.eclipse.core.resources.IResourceProxyVisitor#visit(org.eclipse.core.resources.IResourceProxy)
		 */
		@Override
		public boolean visit(IResourceProxy proxy) {
			if (proxy.getType() == IResource.FILE && proxy.getName() != null) {
				if (inputElement.getFileExtension().equalsIgnoreCase(new Path(proxy.getName()).getFileExtension())) {
					IResource resource = proxy.requestResource();
					fList.add(resource);
				}
				return false;
			}
			return true;
		}
	}
}
