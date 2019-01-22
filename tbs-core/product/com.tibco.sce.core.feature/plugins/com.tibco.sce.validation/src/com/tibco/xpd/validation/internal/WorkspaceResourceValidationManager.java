/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator2;
import com.tibco.xpd.validation.internal.engine.Issue;
import com.tibco.xpd.validation.internal.engine.ValidationEngine;
import com.tibco.xpd.validation.internal.engine.ValidationScope;
import com.tibco.xpd.validation.internal.engine.WorkspaceResourceValidatorExt;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * Manager class that manages the workspace resource validator extensions. This
 * will allow the validation of workspace resources.
 * 
 * @author njpatel
 * 
 */
public class WorkspaceResourceValidationManager {
    private final Map<Destination, Collection<WorkspaceResourceValidatorExt>> validators;

    private final IProject project;

    private ValidationEngine engine;

    private boolean isReferencingResourceValidation;

    /**
     * Workspace resource validator manager
     * 
     * @param project
     *            project being built.
     */
    public WorkspaceResourceValidationManager(IProject project) {
        this(project, false);
    }

    /**
     * @param project2
     * @param isReferencingResourceValidation
     *            <code>true</code> if this is a validation being done on a
     *            resource that simply references a changed resource (but is not
     *            necessarily changed itself).
     */
    public WorkspaceResourceValidationManager(IProject project,
            boolean isReferencingResourceValidation) {

        this.project = project;
        this.isReferencingResourceValidation = isReferencingResourceValidation;

        engine = ValidationManager.getInstance().getValidationEngine();
        validators =
                new HashMap<Destination, Collection<WorkspaceResourceValidatorExt>>();

        Collection<Destination> destinations =
                ValidationManager.getInstance().getDestinations();

        for (Destination dest : destinations) {
            if (dest instanceof WorkspaceResourceValidationDestination) {
                WorkspaceResourceValidationDestination workspaceDest =
                        (WorkspaceResourceValidationDestination) dest;
                boolean includeValidators = false;

                String assetId = workspaceDest.getAssetId();

                if (assetId != null) {
                    // Check if the project has the asset installed - if it does
                    // then include the validators
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);

                    includeValidators =
                            config != null && config.hasAssetType(assetId);
                } else {
                    includeValidators = true;
                }

                if (includeValidators) {
                    Collection<String> providers =
                            dest.getValidationProviders();

                    for (String providerId : providers) {
                        Collection<WorkspaceResourceValidatorExt> validatorExts =
                                engine.getResourceValidators(providerId);

                        if (validatorExts != null) {
                            validators.put(workspaceDest, validatorExts);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate the given resource.
     * 
     * @param resource
     */
    public Collection<IIssue> validate(final IResource resource) {
        if (resource != null && resource.exists()) {
            Set<IIssue> issues = new HashSet<IIssue>();

            for (Destination dest : validators.keySet()) {
                IValidationScope scope = new ValidationScope();
                scope.setCurrentMarkerType(ValidationActivator.WORKSPACE_RESOURCE_MARKER_ID);
                scope.setCurrentDestination(dest);

                for (WorkspaceResourceValidatorExt validatorExt : validators
                        .get(dest)) {
                    if (validatorExt.isInterested(resource)) {
                        try {
                            WorkspaceResourceValidator validator =
                                    validatorExt.getValidator();
                            if (validator != null) {
                                validator.setProject(project);

                                if (validator instanceof WorkspaceResourceValidator2) {

                                    ((WorkspaceResourceValidator2) validator)
                                            .validate(scope,
                                                    resource,
                                                    isReferencingResourceValidation);
                                } else {

                                    validator.validate(scope, resource);
                                }
                            }
                        } catch (CoreException e) {
                            ValidationActivator.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }

                Collection<IIssue> newIssues = scope.getIssues();

                if (newIssues != null) {
                    /*
                     * Message must be created in issue before same issue on
                     * same uri (but with diff message params) will be counted
                     * as different from existing marker.
                     */
                    for (IIssue newIssue : newIssues) {
                        IssueInfo info = engine.getIssueInfo(newIssue.getId());
                        newIssue.createMessage(info.getMessage());

                        issues.add(newIssue);
                    }
                }
            }

            // Create markers for the resource if any
            final Set<IIssue> oldIssues = new HashSet<IIssue>();
            final Set<IIssue> newIssues = new HashSet<IIssue>();
            IMarker[] markers = null;

            try {
                markers =
                        resource.findMarkers(ValidationActivator.WORKSPACE_RESOURCE_MARKER_ID,
                                true,
                                IResource.DEPTH_ZERO);
            } catch (CoreException e) {
                deleteMarkers(resource);
            }

            if (markers != null) {
                for (IMarker marker : markers) {
                    oldIssues.add(new Issue(marker));
                }
            }

            for (IIssue issue : issues) {
                IssueInfo info = engine.getIssueInfo(issue.getId());
                if (info == null) {
                    ValidationActivator
                            .getDefault()
                            .getLogger()
                            .error("Missing issue definition for id " + issue.getId()); //$NON-NLS-1$
                } else {
                    issue.createMessage(info.getMessage());
                    if (oldIssues.contains(issue)) {
                        oldIssues.remove(issue);
                    } else {
                        newIssues.add(issue);
                    }
                }
            }

            // Update markers
            try {
                if (!oldIssues.isEmpty() || !newIssues.isEmpty()) {
                    resource.getWorkspace().run(new IWorkspaceRunnable() {

                        public void run(IProgressMonitor monitor)
                                throws CoreException {
                            for (IIssue issue : oldIssues) {
                                IMarker marker = ((Issue) issue).getMarker();
                                if (marker != null) {
                                    marker.delete();
                                }
                            }

                            for (IIssue issue : newIssues) {
                                IssueInfo info =
                                        engine.getIssueInfo(issue.getId());
                                if (info != null) {
                                    String message = issue.getMessage();
                                    Map<String, Object> attributes = null;

                                    if (issue instanceof Issue) {
                                        // Get the marker attributes from the
                                        // issue,
                                        // if set
                                        attributes =
                                                ((Issue) issue).getMarkerAttributes();
                                    }

                                    if (attributes == null) {
                                        attributes =
                                                new HashMap<String, Object>();
                                    }

                                    attributes.put(IMarker.MESSAGE, message);
                                    attributes.put(IMarker.SEVERITY,
                                            issue.getSeverity());
                                    attributes.put(IMarker.LOCATION,
                                            issue.getUri());
                                    attributes.put(IIssue.AFFECTED_URI,
                                            issue.getAffectedUri());
                                    attributes.put(IIssue.ID, issue.getId());
                                    if (issue.getAdditionalInfoString() != null) {
                                        attributes
                                                .put(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO,
                                                        issue.getAdditionalInfoString());
                                    }

                                    IMarker marker =
                                            resource.createMarker(issue
                                                    .getMarkerType());
                                    marker.setAttributes(attributes);
                                }
                            }
                        }

                    },
                            null);
                }
            } catch (CoreException e) {
                deleteMarkers(resource);
            }
            return issues;
        }
        return Collections.emptySet();
    }

    /**
     * Delete the Workspace Resource markers of the given resource
     * 
     * @param resource
     */
    private void deleteMarkers(final IResource resource) {
        if (resource != null && resource.exists()) {
            try {
                resource.getWorkspace().run(new IWorkspaceRunnable() {

                    public void run(IProgressMonitor monitor)
                            throws CoreException {
                        resource.deleteMarkers(ValidationActivator.WORKSPACE_RESOURCE_MARKER_ID,
                                true,
                                IResource.DEPTH_ZERO);
                    }

                },
                        null);
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
            }
        }
    }
}
