package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.MoveProcessProcessor;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * Abstract Class to assist adding necessary project references when processes
 * is moved to different package.
 * 
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
public abstract class AbstractAddProjectReferencesForProcessMove extends
        AbstractMoveProcessParticipant {

    @Override
    public final String getName() {
        return Messages.AbstractAddProjectReferencesForProcessMove_AddProjectReferenceParticipant_name;
    }

    @Override
    protected RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context, Process processToMove,
            IResource destinationResource,
            MoveProcessProcessor moveProcessProcessor, String newPackageName) {

        pm.beginTask("", 1); //$NON-NLS-1$
        RefactoringStatus status = new RefactoringStatus();
        try {

            /*
             * Get the list of projects that the destination project needs to
             * reference. Then check if such a reference will cause a cyclic
             * dependency, if yes then complain and do not allow refactoring.
             */

            Set<IProject> allProjectsToReference =
                    getAllProjectsThatTargetProjectMustReference(processToMove,
                            destinationResource);

            IProject destinationProject = destinationResource.getProject();

            /*
             * get already referencing projects.
             */
            Set<IProject> allReferencingProjects = new HashSet<IProject>();

            ProjectUtil.getReferencingProjectsHierarchy(destinationProject,
                    allReferencingProjects);

            /*
             * Get the list of projects that need to reference the destination
             * Project. Then check if such a reference will cause cyclic
             * dependency, if yes then complain and do not allow refactoring.
             * project
             */
            Set<IProject> allProjectsThatShouldRefTargetProject =
                    getAllProjectsThatShouldRefTargetProject(processToMove,
                            destinationResource);

            /*
             * get already referenced projects.
             */
            Set<IProject> allReferencedProjects = new HashSet<IProject>();

            ProjectUtil.getReferencedProjectsHierarchy(destinationProject,
                    allReferencedProjects);

            for (IProject eachProj : allProjectsToReference) {
                if (allReferencingProjects.contains(eachProj)) {
                    /*
                     * Do not allow process refactor if it causes cyclic
                     * dependency.
                     */
                    status.addFatalError(String
                            .format(Messages.AbstractAddProjectReferencesForProcessMove_CyclicProjectDependencyError_msg,
                                    eachProj.getName(),
                                    destinationProject.getName()));
                }
            }

            for (IProject eachProj : allProjectsThatShouldRefTargetProject) {
                if (allReferencedProjects.contains(eachProj)) {
                    /*
                     * Do not allow process refactor if it causes cyclic
                     * dependency.
                     */
                    status.addFatalError(String
                            .format(Messages.AbstractAddProjectReferencesForProcessMove_AddProjectReferenceParticipant_name,
                                    eachProj.getName(),
                                    destinationProject.getName()));
                }
            }

            /*
             * Finally check if there are any common projects to reference which
             * may cause cyclic dependency. [i.e. if refactoring requires
             * destination project to reference a project and that project needs
             * to reference the destination project as well.]
             */

            for (IProject eachProj : allProjectsThatShouldRefTargetProject) {

                if (allProjectsToReference.contains(eachProj)) {
                    /*
                     * Do not allow process refactor if it causes cyclic
                     * dependency.
                     */
                    status.addFatalError(String
                            .format(Messages.AbstractAddProjectReferencesForProcessMove_AddProjectReferenceParticipant_name,
                                    eachProj.getName(),
                                    destinationProject.getName()));
                }
            }

        } catch (Exception e) {

        } finally {
            pm.done();
        }
        return status;
    }

    @Override
    protected Change createChange(IProgressMonitor pm, Process processToMove,
            IResource destinationResource,
            MoveProcessProcessor moveProcessProcessor, String newPackageName,
            Map<String, EObject> oldIdToNewEObjectMap) {

        pm.beginTask("", 1); //$NON-NLS-1$
        try {
            CompositeChange compositeChange = new CompositeChange(getName());
            compositeChange.markAsSynthetic();

            /*
             * Change that will add all the necessary Project references.
             */
            Set<IProject> allProjectsToReference =
                    getAllProjectsThatTargetProjectMustReference(processToMove,
                            destinationResource);

            /*
             * get already referenced projects and remove them from the set.
             */
            Set<IProject> alreadyReferencedProjects = new HashSet<IProject>();

            ProjectUtil.getReferencedProjectsHierarchy(destinationResource
                    .getProject(), alreadyReferencedProjects);

            allProjectsToReference.removeAll(alreadyReferencedProjects);

            Set<IProject> allProjectsThatShouldRefTargetProject =
                    getAllProjectsThatShouldRefTargetProject(processToMove,
                            destinationResource);

            /*
             * get already referencing projects and remove them from the set.
             */
            Set<IProject> alreadyReferencingProjects = new HashSet<IProject>();

            ProjectUtil.getReferencingProjectsHierarchy(destinationResource
                    .getProject(), alreadyReferencingProjects);

            allProjectsThatShouldRefTargetProject
                    .removeAll(alreadyReferencingProjects);

            if (!allProjectsToReference.isEmpty()
                    || !allProjectsThatShouldRefTargetProject.isEmpty()) {

                List<AddReferencedProjectChange> projectReferenceChanges =
                        new ArrayList<AddReferencedProjectChange>();

                if (!allProjectsToReference.isEmpty()) {

                    projectReferenceChanges.add(new AddReferencedProjectChange(
                            allProjectsToReference, destinationResource
                                    .getProject()));
                }
                if (!allProjectsThatShouldRefTargetProject.isEmpty()) {

                    for (IProject eachProject : allProjectsThatShouldRefTargetProject) {

                        projectReferenceChanges
                                .add(new AddReferencedProjectChange(Collections
                                        .singleton(destinationResource
                                                .getProject()), eachProject));

                    }
                }

                compositeChange.add(new CompositeChange(getChangeName(),
                        projectReferenceChanges
                                .toArray(new Change[projectReferenceChanges
                                        .size()])));
            }

            return compositeChange;
        } finally {
            pm.done();
        }
    }

    /**
     * 
     * @return gets the name for this change, should not be null.
     */
    protected abstract String getChangeName();

    /**
     * 
     * @param processToMove
     *            the process to move
     * @param destinationResource
     *            the destination resource
     * @return Set of all those projects which the target project (i.e. project
     *         of destinationResource) should be required to reference due to
     *         Process move. If not projects to reference then this method
     *         should reutrn EMPTY Set (not <code>null</code>).
     */
    protected abstract Set<IProject> getAllProjectsThatTargetProjectMustReference(
            Process processToMove, IResource destinationResource);

    /**
     * 
     * @param processToMove
     *            the process to move
     * @param destinationResource
     *            the destination resource
     * @return Set of all those projects which are required to reference the
     *         target project (i.e. project of destinationResource) due to
     *         process move. If not projects to reference then this method
     *         should reutrn EMPTY Set (not <code>null</code>).
     */
    protected abstract Set<IProject> getAllProjectsThatShouldRefTargetProject(
            Process processToMove, IResource destinationResource);
}
