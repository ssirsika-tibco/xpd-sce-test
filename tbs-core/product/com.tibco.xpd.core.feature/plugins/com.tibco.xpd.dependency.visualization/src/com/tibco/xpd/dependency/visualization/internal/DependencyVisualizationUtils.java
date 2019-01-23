/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.dependency.visualization.api.DependencyEditorContribution;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 *
 * Utility class to hold the common utility methods
 * @author ssirsika
 * @since 30-Sep-2015
 */
public class DependencyVisualizationUtils {

	private static String EXTENSION_POINT_ID = "com.tibco.xpd.dependency.visualization.dependencyContributor"; //$NON-NLS-1$
	private static final String PROJECT_EXPLORER_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

	static final IExtensionRegistry registry = Platform.getExtensionRegistry();

	/**
	 * Return the set of contributors which are supplied using implementing <code> com.tibco.xpd.dependency.visualization.contributor </code> 
	 * extension point.
	 * @return {@link Set} of objects implementing {@link DependencyEditorContribution}
	 */
	public static Set<DependencyEditorContribution> getViewerContentContributors() {
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_POINT_ID);
		Set<DependencyEditorContribution> contributions = new HashSet<DependencyEditorContribution>();
		for (IConfigurationElement element : elements) {
			try {
				Object extension;
				extension = element.createExecutableExtension("contributor"); //$NON-NLS-1$
				contributions.add((DependencyEditorContribution) extension);
			} catch (CoreException e) {
				DependencyVisualizationActivator.logError(Messages.DependencyVisualizationUtils_ErrorReadingExtensionPointMessage, e);
			}
		}
		return contributions;
	}

	/**
	 * Return referencing projects of passed 'project'. This will only return referencing project which has XPD nature. 
	 * @param project
	 * @return {@link Set} of {@link IProject}
	 * @deprecated
	 */
	@Deprecated
	public static Set<IProject> getReferencingXPDProjects(IProject project) {
		Set<IProject> projects = new HashSet<>();
		try {

			IProject[] refProjects;
			refProjects = project.getReferencingProjects();
			for (IProject refProject : refProjects) {
				if (refProject.isAccessible() && refProject.hasNature(XpdConsts.PROJECT_NATURE_ID) && !refProject.isHidden()) {

					projects.add(refProject);

				}
			}

		} catch (CoreException e) {
			DependencyVisualizationActivator.logError(Messages.DependencyVisualizationUtils_ProjectNatureErrorMessage, e);
		}
		return projects;
	}

	/**
	 * Return referenced projects of passed 'project'. This will only return referenced projects which has XPD nature. 
	 * @param project
	 * @return {@link Set} of {@link IProject}
	 * 
	 */
	public static Set<IProject> getReferencedXPDProjects(IProject project) {
		Set<IProject> projects = new HashSet<>();
		try {

			IProject[] refProjects;
			refProjects = project.getReferencedProjects();
			for (IProject refProject : refProjects) {
				if (refProject.isAccessible() && refProject.hasNature(XpdConsts.PROJECT_NATURE_ID) && !refProject.isHidden()) {

					projects.add(refProject);

				}
			}

		} catch (CoreException e) {
			DependencyVisualizationActivator.logError(Messages.DependencyVisualizationUtils_ProjectNatureErrorMessage, e);
		}
		return projects;
	}

	/**
	 * Select the passed element 'toSelect' in the project explorer and expand it to level indicated using 'expandToLevel'.
	 * @param toSelect to be selected. Must be instance of {@link IResource} 
	 * @param expandToLevel describes expand the selected object to what level
	 */
	public static void showInProjectExplorerView(Object toSelect, int expandToLevel) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (activePage != null) {
			if (toSelect instanceof IResource) {
				try {
					CommonNavigator navigator = (CommonNavigator) activePage.showView(PROJECT_EXPLORER_VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);
					if (navigator != null) {
						CommonViewer commonViewer = navigator.getCommonViewer();
						commonViewer.setSelection(StructuredSelection.EMPTY);
						commonViewer.setSelection(new StructuredSelection(toSelect));
						commonViewer.expandToLevel(toSelect, expandToLevel);
					}
				} catch (PartInitException e) {
					DependencyVisualizationActivator.logError(Messages.DependencyVisualizationUtils_ProjectExplorerOpeningErrorMessage, e);
				}
			}
		}
	}

	/**
	 * This method can be moved to WokingCopyUtil
	* Build and returns list of all resources directly or indirectly dependent on given resource. This
	* will search all referencing projects.
	* 
	* @param resource
	*            resource
	* @return all resources dependent on the given resource, empty list if
	*         none.
	*/
	public static Set<IResource> getDeepAffectedResources(final IResource resource) {
		Set<IResource> affectedResources = new HashSet<IResource>();
		Set<IResource> visitedResources = new HashSet<IResource>();
		recursiveGetAffectedResources(affectedResources, visitedResources, resource);
		return affectedResources;
	}

	/**
	 * Gets Deep Dependents by Recursively populating the input Set<>
	 * <b>"affectedResources"</b> with all the IResource's on which the input
	 * <b>"resource"</b> is dependent on.
	 * @param affectedResources {@link Set} which stores the result.
	 * @param visitedResources visited resource during the recursive search
	 * @param resource {@link IResource} whose deep dependents need to be find out. 
	 */
	private static void recursiveGetAffectedResources(Set<IResource> affectedResources, Set<IResource> visitedResources, IResource resource) {
		if (visitedResources.contains(resource)) {
			return;
		}
		Collection<IResource> resources = WorkingCopyUtil.getAffectedResources(resource);
		affectedResources.addAll(resources);
		visitedResources.add(resource);
		for (IResource iResource : resources) {
			recursiveGetAffectedResources(affectedResources, visitedResources, iResource);
		}
	}

}
