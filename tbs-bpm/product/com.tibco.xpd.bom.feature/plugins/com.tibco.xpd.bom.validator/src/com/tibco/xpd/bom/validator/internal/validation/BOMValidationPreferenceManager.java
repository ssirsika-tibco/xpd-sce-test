/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator.internal.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager;
import com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager.ValidationDestination;

/**
 * BOM validation preference manager implementation. This will get and set the
 * workspace preference and project properties of the
 * {@link ValidationDestination} provided.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("unused")//$NON-NLS-1$
public class BOMValidationPreferenceManager implements
        IBOMValidationPreferenceManager {

    private final IPreferenceStore store;

    private static final String PROJECT_SPECIFIC_NODE =
            "bomXsdWsdlValidationNode"; //$NON-NLS-1$

    public BOMValidationPreferenceManager(IPreferenceStore store) {
        this.store = store;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#
     * getPreferenceSetting
     * (com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager
     * .ValidationDestination)
     */
    public boolean getPreferenceSetting(ValidationDestination destination) {
        return destination != null ? store.getBoolean(destination
                .getPreferenceId()) : false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#
     * setPreferenceSetting
     * (com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager
     * .ValidationDestination, boolean)
     */
    public void setPreferenceSetting(ValidationDestination destination,
            boolean value) {
        if (destination != null) {
            store.setValue(destination.getPreferenceId(), value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#
     * getPropertiesSetting(org.eclipse.core.resources.IProject,
     * com.tibco.xpd.bom
     * .validator.IBOMValidationPreferenceManager.ValidationDestination)
     */
    public boolean getPropertiesSetting(IProject project,
            ValidationDestination destination) {
        if (project != null && destination != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            try {
                if (preferences != null
                        && preferences.nodeExists(PROJECT_SPECIFIC_NODE)) {
                    return preferences.node(PROJECT_SPECIFIC_NODE)
                            .getBoolean(destination.getPreferenceId(), false);
                }
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#
     * setPropertiesSetting(org.eclipse.core.resources.IProject,
     * com.tibco.xpd.bom
     * .validator.IBOMValidationPreferenceManager.ValidationDestination,
     * boolean)
     */
    public void setPropertiesSetting(IProject project,
            ValidationDestination destination, boolean value) {
        if (project != null && destination != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node = preferences.node(PROJECT_SPECIFIC_NODE);
                if (node != null) {
                    node.putBoolean(destination.getPreferenceId(), value);
                }
                try {
                    preferences.flush();
                } catch (BackingStoreException e) {
                    BOMValidatorActivator.getDefault().getLogger().error(e);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#removeProperties
     * (org.eclipse.core.resources.IProject)
     */
    public void removeProperties(IProject project) {
        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            try {
                if (preferences != null
                        && preferences.nodeExists(PROJECT_SPECIFIC_NODE)) {
                    preferences.node(PROJECT_SPECIFIC_NODE).removeNode();
                    preferences.flush();
                }
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.validator.IBOMValidationPreferenceManager#
     * isProjectSettingsSet(org.eclipse.core.resources.IProject)
     */
    public boolean isProjectSettingsSet(IProject project) {
        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            try {
                return preferences != null
                        && preferences.nodeExists(PROJECT_SPECIFIC_NODE);
            } catch (BackingStoreException e) {
                logCannotAccessPreferences(project, e);
            }
        }
        return false;
    }

    /**
     * Get all projects with the preferences set.
     * 
     * @return
     */
    public List<IProject> getProjectsWithSettings() {
        List<IProject> projects = new ArrayList<IProject>();
        for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
                .getProjects()) {
            IEclipsePreferences preference = getProjectPreferences(project);
            try {
                if (preference != null
                        && preference.nodeExists(PROJECT_SPECIFIC_NODE)) {
                    projects.add(project);
                }
            } catch (BackingStoreException e) {
                // Do nothing
            }
        }

        return projects;
    }

    /**
     * Log unable to access preference for the given project.
     * 
     * @param project
     * @param e
     */
    private void logCannotAccessPreferences(IProject project, Exception e) {
        BOMValidatorActivator.getDefault().getLogger().error(e,
                String.format("Cannot access preferences of project %s.",
                        project.getName()));
    }

    /**
     * Get the project preferences of the given project.
     * 
     * @param project
     * @return
     */
    private IEclipsePreferences getProjectPreferences(IProject project) {
        ProjectScope scope = new ProjectScope(project);
        IEclipsePreferences preferences =
                scope.getNode(BOMValidatorActivator.PLUGIN_ID);
        return preferences;
    }

    public boolean isValidationEnabled(IProject project,
            ValidationDestination destination) {

        if (project != null && destination != null) {
            // Check if the project level property is set
            if (isProjectSettingsSet(project)) {
                return getPropertiesSetting(project, destination);
            }

            // Check if workspace preference is set
            return getPreferenceSetting(destination);
        }

        return false;
    }

}
