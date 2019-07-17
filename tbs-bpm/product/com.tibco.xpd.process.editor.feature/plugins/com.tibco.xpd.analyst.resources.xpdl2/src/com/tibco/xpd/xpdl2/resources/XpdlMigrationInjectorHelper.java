/**
 * 
 */
package com.tibco.xpd.xpdl2.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.osgi.framework.Bundle;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.migrate.IMigrationCommandInjector;
import com.tibco.xpd.xpdl2.Package;

/**
 * <code>com.tibco.analyst.resources.xpdl2.xpdlMigrationInjection</code>
 * extension point helper.
 * 
 * @author aallway
 * @since 3.4.2 (27 Aug 2010)
 */
public class XpdlMigrationInjectorHelper {

    private static final String EXT_POINT_ID = "xpdlMigrationInjector"; //$NON-NLS-1$

    private static final String MIGRATION_INJECTOR_ELEMENT =
            "migrationInjector"; //$NON-NLS-1$

    private static final String COMMAND_INJECTOR_ELEMENT =
            "endOfMigrationCommandInjector"; //$NON-NLS-1$

    private static final String FORMAT_VERSION_ATTRIBUTE = "formatVersion"; //$NON-NLS-1$

    private static final String BEFORE_OR_AFTER_ATTRIBUTE = "beforeOrAfter"; //$NON-NLS-1$

    private static final String XSLT_FILE_ATTRIBUTE = "xsltFile"; //$NON-NLS-1$

    private static final String BEFORE_VALUE = "Before"; //$NON-NLS-1$

    /**
     * @param formatVersion
     *            Format version
     * @param before
     *            <code>true</code> to get xslts to be injected before the
     *            standard migration to this formatVersion - else after.
     * 
     * @return list of injected migration xslts contributed for the given format
     *         version (for before or after).
     */
    public static Collection<InputStream> getInjectedXslts(int formatVersion,
            boolean before) {
        List<InputStream> xslts = new ArrayList<InputStream>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                EXT_POINT_ID);

        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            if (contributions != null) {
                for (IConfigurationElement contribution : contributions) {
                    if (MIGRATION_INJECTOR_ELEMENT.equals(contribution
                            .getName())) {

                        if (getFormatVersion(contribution) == formatVersion
                                && isBefore(contribution) == before) {

                            InputStream xslt = getXsltInputStream(contribution);

                            if (xslt != null) {
                                xslts.add(xslt);
                            }
                        }
                    }
                }
            }
        }

        return xslts;
    }

    /**
     * @return A list of the end of migration command injectors contributed to
     *         the extension point.
     */
    public static Collection<MigrationCommandInjector> getEndOfMigrationCommandInjectors() {

        List<MigrationCommandInjector> commandInjectors =
                new ArrayList<MigrationCommandInjector>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                EXT_POINT_ID);

        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            if (contributions != null) {
                for (IConfigurationElement contribution : contributions) {
                    if (COMMAND_INJECTOR_ELEMENT.equals(contribution.getName())) {

                        Object propClass = null;
                        try {
                            propClass =
                                    contribution
                                            .createExecutableExtension("class"); //$NON-NLS-1$
                        } catch (CoreException ce) {
                            System.err
                                    .println(XpdlMigrationInjectorHelper.class
                                            .getName()
                                            + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                        if (propClass instanceof IMigrationCommandInjector) {
                            String priorityStr =
                                    contribution.getAttribute("priority"); //$NON-NLS-1$
                            int priority;

                            if (priorityStr != null && priorityStr.length() > 0) {
                                try {
                                    priority = Integer.parseInt(priorityStr);
                                } catch (Exception e) {
                                    priority = 9999;
                                }
                            } else {
                                priority = 9999;
                            }

                            commandInjectors.add(new MigrationCommandInjector(
                                    contribution.getContributor().getName(),
                                    (IMigrationCommandInjector) propClass,
                                    priority));
                        } else {
                            String contributerId =
                                    contribution.getContributor().getName();
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(contributerId
                                            + ": " //$NON-NLS-1$
                                            + point.getUniqueIdentifier()
                                            + ": Incorrectly defined extension - class must implement " //$NON-NLS-1$
                                            + IMigrationCommandInjector.class
                                                    .getCanonicalName());
                        }

                    }
                }
            }
        }

        /* Sort the list by priority. */
        Collections.sort(commandInjectors,
                new Comparator<MigrationCommandInjector>() {

                    @Override
                    public int compare(MigrationCommandInjector o1,
                            MigrationCommandInjector o2) {
                        return o1.priority - o2.priority;
                    }
                });

        return commandInjectors;
    }

    /**
     * @param contribution
     * 
     * @return the formatVersion attribute from the migrationInjector element.
     */
    private static int getFormatVersion(IConfigurationElement contribution) {
        String formatVersionAttr =
                contribution.getAttribute(FORMAT_VERSION_ATTRIBUTE);
        if (formatVersionAttr != null) {
            try {
                int formatVersion = Integer.parseInt(formatVersionAttr);
                return formatVersion;

            } catch (Exception e) {
            }
        }

        return -1;
    }

    /**
     * @param contribution
     * 
     * @return <code>true</code> if the migrationInjector element is configured
     *         to be before (<code>false</code> if defined as "After").
     */
    private static boolean isBefore(IConfigurationElement contribution) {
        String beforeOrAfter =
                contribution.getAttribute(BEFORE_OR_AFTER_ATTRIBUTE);
        return BEFORE_VALUE.equalsIgnoreCase(beforeOrAfter);
    }

    /**
     * @param contribution
     * 
     * @return the input stream to the injected xslt from the contribution's
     *         plugin.
     */
    private static InputStream getXsltInputStream(
            IConfigurationElement contribution) {
        InputStream xslt = null;

        String xsltFile = contribution.getAttribute(XSLT_FILE_ATTRIBUTE);
        if (xsltFile != null) {
            Bundle bundle =
                    Platform.getBundle(contribution.getNamespaceIdentifier());
            if (bundle != null) {
                URL bundleEntry = bundle.getEntry(xsltFile);

                try {
                    xslt = bundleEntry.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        if (xslt == null) {
            String msg =
                    String
                            .format("XpdlMigrationInjection Ext Point: Failed to load xsltFile '%s' from contribution: %s", //$NON-NLS-1$
                                    xsltFile != null ? xsltFile : "", //$NON-NLS-1$
                                    contribution.getContributor().getName());
            Xpdl2ResourcesPlugin.getDefault().getLogger().error(msg);
        }

        return xslt;
    }

    /**
     * Wrapper for command injector contribution.
     * 
     * 
     * @author aallway
     * @since 28 Jan 2011
     */
    public static class MigrationCommandInjector {
        private IMigrationCommandInjector injector;

        private int priority;

        private String contributorId;

        /**
         * @param contributorId
         * @param injector
         * @param priority
         */
        public MigrationCommandInjector(String contributorId,
                IMigrationCommandInjector injector, int priority) {
            super();
            this.contributorId = contributorId;
            this.injector = injector;
            this.priority = priority;
        }

        /**
         * @return the contributorId
         */
        public String getContributorId() {
            return contributorId;
        }

        /* Create the command to esxecute. */
        public Command getCommand(EditingDomain editingDomain, Package pkg, int originalFormatVersion) {
            injector.setOriginalFormatVersion(originalFormatVersion);
            return injector.getCommand(editingDomain, pkg);
        }

    }
}
