/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetHandle;
import org.eclipse.pde.core.target.ITargetPlatformService;
import org.eclipse.pde.core.target.LoadTargetDefinitionJob;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.TargetDefinitionManager;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Can attempt initialisation of Target Platform. When Target Platform appears
 * to have been loaded then the preference property records this under the key
 * 'initialized-silver-target-platform'.
 * 
 * This is based upon existing target platform initialisation code of
 * com.tibco.amf.design.core.feature (SetTargetPlatformStartup;
 * TibcoHomeLocatorImpl; TargetPlatformFactory; MachineModelUtil). Version of
 * this was required for com.tibco.xpd.core.feature as custom Ant tasks located
 * in Core needed this ability too.
 * 
 * @author patkinso
 * @since 13 Jun 2013
 */
public class TargetPlatformInitialiser {

    private static TargetPlatformInitialiser targetPlatformInitialiser = null;

    protected static Logger logger = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String INITIALIZED_SILVER_TARGET_PLATFORM =
            "initialized-silver-target-platform"; //$NON-NLS-1$    

    private static final String TIBCO_TARGET_PLATFORM_FILE_LOCATION =
            "com.tibco.target.platform.location"; //$NON-NLS-1$

    /**
     * @deprecated use
     *             {@link SetTargetPlatformStartup#TIBCO_TARGET_PLATFORM_FILE_LOCATION
     */
    @Deprecated
    private static final String TARGET_PLATFORM_FILE_LOCATION_ENV_NAME =
            "TARGET_PLATFORM_FILE_LOCATION"; //$NON-NLS-1$

    private static final String DEFAULT_TARGET_PLATFORM_ID =
            System.getProperty("com.tibco.default.target.platform.id", "silver.runtime"); //$NON-NLS-1$ //$NON-NLS-2$

    private TargetPlatformInitialiser() {
        super();
    }

    public static TargetPlatformInitialiser getInstance() {
        if (targetPlatformInitialiser == null)
            targetPlatformInitialiser = new TargetPlatformInitialiser();
        return targetPlatformInitialiser;
    }

    /**
     * @return <code>true</code> if initialisation appears to have been
     *         successful. <code>false</code> otherwise.
     */
    public boolean initalise() {

        boolean ret = true;

        if (!isInitialised()) {

            /*
             * Determine potential location for target platform in following
             * priority order: 1) as target definition file determined from
             * system properties 2) from TIBCO HOME 3) as target definition file
             * determined from current location
             */
            URL targetFile = getTargetDefinitionURLLocationFromEnv();
            if (targetFile == null) {
                targetFile =
                        getTargetDefinitionURLLocationFromCurrentLocation();
            }

            /*
             * Get Target Platform definition from identified location
             */
            ITargetDefinition targetDefinition = null;

            if (targetFile != null) {

                try {
                    // load from a file
                    ITargetPlatformService ser =
                            PDECore
                            .getDefault()
                            .acquireService(ITargetPlatformService.class);

                    // SDS-6005 targetFile URL is returned with spaces by
                    // TargetDefinitionManager.getResourceURL(symbolicName,
                    // path);
                    // Inside method getTargetFileFromCurrentLocation(), since
                    // this
                    // is an eclipse API this is the only place where we
                    // can parse that URL to encode the spaces otherwise we get
                    // a
                    // URISyntaxException when doing targetFile.toURI()
                    String targetFileURLStr = targetFile.toString();
                    targetFileURLStr = targetFileURLStr.replaceAll(" ", "%20"); //$NON-NLS-1$ //$NON-NLS-2$
                    targetFile = new URL(targetFileURLStr);
                    // ******************************************

                    ITargetHandle target = ser.getTarget(targetFile.toURI());
                    logger.info(MessageFormat
                            .format("Setting target platform: {0}", //$NON-NLS-1$
                                    targetFile.toURI()));

                    targetDefinition = target.getTargetDefinition();
                } catch (Exception ex) {
                    logger.error(ex, "Error setting Target Platform at Startup"); //$NON-NLS-1$
                }

            }

            /* attempt to load target platform definition */
            if (targetDefinition != null) {
                loadTP(targetDefinition, true);
                setInitialised(true);
                ret = true;
            } else {
                ret = false;
            }
        }

        return ret;
    }

    /**
     * @return
     */
    public boolean isInitialised() {
        Preferences preferences =
                InstanceScope.INSTANCE.getNode(XpdResourcesPlugin.ID_PLUGIN);
        return preferences
                .getBoolean(INITIALIZED_SILVER_TARGET_PLATFORM, false);
    }

    /**
     * @param b
     */
    private void setInitialised(boolean value) {
        try {
            Preferences preferences =
                    InstanceScope.INSTANCE
                            .getNode(XpdResourcesPlugin.ID_PLUGIN);
            preferences.putBoolean(INITIALIZED_SILVER_TARGET_PLATFORM, value);
            preferences.flush();
        } catch (BackingStoreException e) {
            logger.error(e,
                    "Error registering the setting of the Target Platform"); //$NON-NLS-1$
        }
    }

    /**
     * @param append
     * @return
     */
    private IPath nullifyInvalidFile(IPath path) {
        if (path != null) {
            File file = path.toFile();
            if (file.exists() && file.isFile()) {
                return path;
            }
        }
        return null;
    }

    /**
     * @param homeLocPath
     * @return
     */
    private IPath nullifyInvalidPath(IPath path) {
        if (path != null) {
            File file = path.toFile();
            if (file.exists() && file.isDirectory()) {
                return path;
            }
        }
        return null;
    }

    /**
     * @return
     */
    private URL getTargetDefinitionURLLocationFromCurrentLocation() {

        URL ret = null;

        IPath homeLoc = getTibcoHomeLocationFromEclipseInstallation();
        if (homeLoc != null) {
            IConfigurationElement target =
                    PDECore.getDefault().getTargetProfileManager()
                            .getTarget(DEFAULT_TARGET_PLATFORM_ID); //$NON-NLS-1$
            if (target != null) {
                String path = target.getAttribute("definition"); //$NON-NLS-1$
                String symbolicName =
                        target.getDeclaringExtension().getNamespaceIdentifier();
                ret =
                        TargetDefinitionManager.getResourceURL(symbolicName,
                                path);
            } else {
                String msgFmt =
                        "Unable to find a target platform extension (extension point: org.eclipse.pde.core.targets) with id: {0}"; //$NON-NLS-1$
                logger.error(MessageFormat.format(msgFmt,
                        DEFAULT_TARGET_PLATFORM_ID));
            }
        } else {
            // the installation is not in a tibco home, so the default target
            // will fail
            // to find its bundles
            String msgFmt =
                    "Unable to locate a tibcohome relative of the eclipse installation at {0}"; //$NON-NLS-1$
            logger.error(MessageFormat.format(msgFmt, getEclipseHome()
                    .toString()));
        }

        return ret;
    }

    /**
     * @return
     */
    private IPath getTibcoHomeLocationFromEclipseInstallation() {

        URI eclipseHome = getEclipseHome();
        if (eclipseHome.segmentCount() > 4) {
            URI installation = eclipseHome.trimSegments(4);
            String rootPath = installation.toFileString();
            IPath path = Path.fromOSString(rootPath);
            return path;
        }

        return null;
    }

    /**
     * This always refers to the directory of the Eclipse being execute the dir
     * that holds eclipse.exe we can also find it by
     * System.getProperty("eclipse.home.location")
     * 
     * @return URI of the eclipse installation.
     */
    public URI getEclipseHome() {
        URI uri = URI.createURI("platform:/base/"); //$NON-NLS-1$
        URI eclipseHome = CommonPlugin.resolve(uri);
        return eclipseHome;
    }

    /**
     * Attempt to determine a target definition URI location from system
     * properties
     * 
     * @return URL for target definition. <code>null</code> if non found.
     */
    private URL getTargetDefinitionURLLocationFromEnv() {

        String targetDefinition = getTargetDefinitionLocationFromEnv();

        if (targetDefinition != null && !targetDefinition.trim().isEmpty()) {

            IPath path = Path.fromOSString(targetDefinition);
            File targetFile = path.toFile();
            if (targetFile.exists()) {
                try {
                    return targetFile.toURI().toURL();
                } catch (MalformedURLException e) {
                    logger.error(e, "Exception while parsing URL for file."); //$NON-NLS-1$
                }
            } else {
                String fmtMsg =
                        "Target Platform specified via {0} environment variable points to a directory that does not exist: ''{1}''"; //$NON-NLS-1$
                String msg =
                        MessageFormat.format(fmtMsg,
                                TIBCO_TARGET_PLATFORM_FILE_LOCATION,
                                targetDefinition);
                logger.error(msg);
            }
        }

        return null;
    }

    /**
     * Attempt to determine a target definition location from system properties
     * 
     * @return OS path for target definition. <code>null</code> if non found.
     */
    private String getTargetDefinitionLocationFromEnv() {

        String sysProp =
                System.getProperty(TIBCO_TARGET_PLATFORM_FILE_LOCATION);
        if (sysProp != null) {
            return sysProp;
        }

        sysProp = System.getProperty(TARGET_PLATFORM_FILE_LOCATION_ENV_NAME);
        if (sysProp != null) {

            boolean isTargetFile = false;
            try {
                IPath path = Path.fromOSString(sysProp);
                isTargetFile = isTargetDefinitionExisting(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isTargetFile) {
                String msgFmt =
                        "''{0}'' is deprecated, please use ''{1}'' to specify the target platform."; //$NON-NLS-1$         
                String msg =
                        MessageFormat.format(msgFmt,
                                TARGET_PLATFORM_FILE_LOCATION_ENV_NAME,
                                TIBCO_TARGET_PLATFORM_FILE_LOCATION);
                logger.warn(msg);
            }
        }

        return sysProp;
    }

    /**
     * @param path
     * @return
     */
    private boolean isTargetDefinitionExisting(IPath path) {

        boolean ret = false;
        if (path != null) {
            File file = path.toFile();
            ret =
                    file.exists() && file.isFile()
                            && "target".equals(path.getFileExtension()); //$NON-NLS-1$
        }

        return ret;
    }

    private void loadTP(ITargetDefinition target, boolean wait) {
        Job.getJobManager().cancel("LoadTargetDefinitionJob"); //$NON-NLS-1$
        Job job = new LoadTargetDefinitionJob(target);
        job.setUser(false);
        job.schedule();
        if (wait) {
            try {
                job.join();
            } catch (InterruptedException e) {
                logger.error(e,
                        "Unable to wait for job for setting of target platform");////$NON-NLS-1$
            }
        }
    }
}
