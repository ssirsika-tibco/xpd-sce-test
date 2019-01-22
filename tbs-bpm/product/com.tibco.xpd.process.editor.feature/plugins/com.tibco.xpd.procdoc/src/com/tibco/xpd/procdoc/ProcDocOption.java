/**
 * 
 */
package com.tibco.xpd.procdoc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.procdoc.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

public class ProcDocOption {

    public static final String USE_SVG_IMAGES = "useSVGImages"; //$NON-NLS-1$

    public static final String SHOW_PROCESS_IMAGES = "showProcessImages"; //$NON-NLS-1$

    private static final String OPTION_OFF = "off"; //$NON-NLS-1$

    private static final String OPTION_ON = "on"; //$NON-NLS-1$

    private String id;

    private String description;

    private boolean on;

    public static final String PROJECT_SPECIFIC_NODE = "procDocPreferenceNode"; //$NON-NLS-1$

    public static final String HAS_PROJECT_SPECIFIC_PROC_DOC_SETTINGS =
            "hasProjectSpecificProcDocSettings"; //$NON-NLS-1$

    public ProcDocOption(String id, String description, boolean on) {
        this.id = id;
        this.description = description;
        this.on = on;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    /**
     * Load up the default options.
     */
    public static List<ProcDocOption> getDefaultProcDocOptions() {
        List<ProcDocOption> opts = new ArrayList<ProcDocOption>();

        opts.add(new ProcDocOption(SHOW_PROCESS_IMAGES,
                Messages.ProcDocOptionsPage_DiagramImages_label, true));

        /*
         * XPD-6963: Saket: Removing 'Use SVG images' option. There a few things
         * it can't do like attached events and connections to them and
         * associations etc. Also, there seems to be issues with event and
         * gateway display when the SVG is embedded in document (works ok when
         * SVG is opened directly).
         */

        opts.add(new ProcDocOption("showParticipants", //$NON-NLS-1$ 
                Messages.ProcDocOptionsPage_Participants_label, true));

        opts.add(new ProcDocOption("showTypeDeclarations", //$NON-NLS-1$
                Messages.ProcDocOptionsPage_TypeDeclarations_label, true));

        opts.add(new ProcDocOption("showDataFields", //$NON-NLS-1$ 
                Messages.ProcDocOptionsPage_DataFIelds_label, true));

        opts.add(new ProcDocOption("showFormalParameters", //$NON-NLS-1$
                Messages.ProcDocOptionsPage_FormalParams_label, true));

        opts.add(new ProcDocOption("showFlows", //$NON-NLS-1$
                Messages.ProcDocOptionsPage_SeqFlow_label, true));

        opts.add(new ProcDocOption("showImplementations", //$NON-NLS-1$ 
                Messages.ProcDocOptionsPage_Implementations_label, true));

        opts.add(new ProcDocOption("showActivityInterfaces", //$NON-NLS-1$ 
                Messages.ProcDocOptionsPage_ActivityInterfaces_label, true));

        opts.add(new ProcDocOption("showExtendedAttributes", //$NON-NLS-1$
                Messages.ProcDocOptionsPage_ExtAttrs_label, false));

        opts.add(new ProcDocOption("showTokenName", //$NON-NLS-1$
                Messages.ProcDocOption_ShowTokenName_label, false));

        return opts;
    }

    /**
     * Override the default options with those last stored in preferences.
     */
    public static List<ProcDocOption> loadProcDocOptions() {
        List<ProcDocOption> options = getDefaultProcDocOptions();

        IPreferenceStore prefStore =
                ProcdocPlugin.getDefault().getPreferenceStore();

        for (ProcDocOption option : options) {
            String prefName = getOptionPreferenceName(option);

            String opt = prefStore.getString(prefName);
            if (OPTION_ON.equals(opt)) {
                option.setOn(true);
            } else if (OPTION_OFF.equals(opt)) {
                option.setOn(false);
            }
        }

        return options;
    }

    /**
     * Override the default options with those last stored in preferences.
     */
    public static List<ProcDocOption> loadProcDocProjectOptions(IProject project) {
        List<ProcDocOption> options = getDefaultProcDocOptions();

        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node = preferences.node(PROJECT_SPECIFIC_NODE);
                if (node != null) {
                    for (ProcDocOption option : options) {
                        String prefName = getOptionPreferenceName(option);

                        String opt = node.get(prefName, "");//$NON-NLS-1$
                        if (OPTION_ON.equals(opt)) {
                            option.setOn(true);
                        } else if (OPTION_OFF.equals(opt)) {
                            option.setOn(false);
                        }
                    }
                }
            }
        }
        return options;
    }

    public static void saveOptionToPreferences(ProcDocOption option) {
        IPreferenceStore prefStore =
                ProcdocPlugin.getDefault().getPreferenceStore();

        String prefName = getOptionPreferenceName(option);

        // Use strings for prefs as setting to boolean false simply deletes
        // the pref store entry. Which means we can't tell the difference
        // between 'never set' and set to false.
        if (option.isOn()) {
            prefStore.setValue(prefName, OPTION_ON);
        } else {
            prefStore.setValue(prefName, OPTION_OFF);
        }

    }

    public static void saveOptionToProjectPreferences(IProject project,
            ProcDocOption option) {
        if (project != null && option != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node = preferences.node(PROJECT_SPECIFIC_NODE);
                if (node != null) {
                    String prefName = getOptionPreferenceName(option);
                    // Use strings for prefs as setting to boolean false simply
                    // deletes
                    // the pref store entry. Which means we can't tell the
                    // difference
                    // between 'never set' and set to false.
                    if (option.isOn()) {
                        node.put(prefName, OPTION_ON);
                    } else {
                        node.put(prefName, OPTION_OFF);
                    }
                }
                try {
                    preferences.flush();
                } catch (BackingStoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }
    }

    private static String getOptionPreferenceName(ProcDocOption option) {
        return ProcdocPlugin.PLUGIN_ID + "." + option.getId(); //$NON-NLS-1$
    }

    /**
     * Get the project preferences of the given project.
     * 
     * @param project
     * @return
     */
    public static IEclipsePreferences getProjectPreferences(IProject project) {
        ProjectScope scope = new ProjectScope(project);
        IEclipsePreferences preferences =
                scope.getNode(ProcdocPlugin.PLUGIN_ID);
        return preferences;
    }

    public static boolean hasProjectSpecificProcDocSettings(IProject project) {
        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node = preferences.node(PROJECT_SPECIFIC_NODE);
                if (node != null) {
                    return node
                            .getBoolean(HAS_PROJECT_SPECIFIC_PROC_DOC_SETTINGS,
                                    false);
                }
            }
        }
        return false;
    }

    public static void setHasProjectSpecificProcDocSettings(IProject project,
            boolean hasProjectSpecificProcDocSettings) {
        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node = preferences.node(PROJECT_SPECIFIC_NODE);
                if (node != null) {
                    node.putBoolean(HAS_PROJECT_SPECIFIC_PROC_DOC_SETTINGS,
                            hasProjectSpecificProcDocSettings);
                }
            }
        }
    }

}
