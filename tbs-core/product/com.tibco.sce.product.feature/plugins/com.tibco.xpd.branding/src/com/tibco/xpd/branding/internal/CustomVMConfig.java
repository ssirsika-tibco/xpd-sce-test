/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.branding.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.ui.IStartup;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.xpd.branding.BrandingPlugin;

/**
 * Early startup task to initialise
 * "org.eclipse.jdt.launching/org.eclipse.jdt.launching.PREF_VM_XML" instance
 * preference and force custom default VM. This is necessary for MacOS platform
 * to set up custom VM as it doesn't use standard VM type and always initialise
 * with MacOS specific type using Mac provided JRE.
 * <p/>
 * This task depends on "tibco.default.jdt.jre.location" system property to set
 * custom JRE location. (It is also possible to set default JRE name by setting
 * 'tibco.default.jdt.jre.name' system property.)
 * 
 * NOTE! This class is intended as a workaround for MacOS and should be removed
 * when MacOS platform will start to honour "java.home" system property to
 * resolve and set a default JRE (as all other platforms do).
 * 
 * @author jarciuch
 * @since 7 Mar 2013
 */
public class CustomVMConfig implements IStartup {

    /**
     * Property to force default MacOS JRE resolution on MacOS platform. Set
     * value to "true" to use a default MacOS jre instead of one provided in
     * "java.home" property (default case).
     */
    private static final String TIBCO_USE_DEFAULT_MAC_JRE =
            "tibco.use.default mac.jre"; //$NON-NLS-1$

    /**
     * Default jdt installer JRE name.
     */
    private static final String DEFAULT_JRE_NAME = "TIBCO JRE"; //$NON-NLS-1$

    /**
     * Default jdt installer JRE location system property.
     */
    private static final String TIBCO_DEFAULT_JDT_JRE_LOCATION_PROP =
            "tibco.default.jdt.jre.location"; //$NON-NLS-1$

    /**
     * Default jdt installer JRE name system property.
     */
    private static final String TIBCO_DEFAULT_JDT_JRE_NAME_PROP =
            "tibco.default.jdt.jre.name"; //$NON-NLS-1$

    /**
     * JDT launching plug-in ID.
     */
    private static final String JDT_LAUNCHING_PLUGIN_ID =
            "org.eclipse.jdt.launching"; //$NON-NLS-1$

    /**
     * Preference name used in 'vmSpecDefault.properties' file (located on a
     * root level of branding plug-in.) for storing default VM specification in
     * XML format.
     */
    private static final String PREF_VM_XML_PROPERTY = JDT_LAUNCHING_PLUGIN_ID
            + "/" + JDT_LAUNCHING_PLUGIN_ID + ".PREF_VM_XML"; //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * URL of 'vmSpecDefault.properties' file containing default VM
     * specification in XML format.
     */
    private static final String VM_SPEC_DEFAULT =
            "platform:/plugin/" + BrandingPlugin.PLUGIN_ID + "/vmSpecDefault.properties"; //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * 
     */
    private static final String PREF_VM_XML =
            "org.eclipse.jdt.launching.PREF_VM_XML"; //$NON-NLS-1$

    @Override
    public void earlyStartup() {
        initializeDefaultVM();
    }

    /**
     * Initialises
     * "org.eclipse.jdt.launching/org.eclipse.jdt.launching.PREF_VM_XML"
     * instance preference
     * 
     * @see org.eclipse.ui.IStartup#earlyStartup()
     */
    private void initializeDefaultVM() {
        String jreLocation =
                System.getProperty(TIBCO_DEFAULT_JDT_JRE_LOCATION_PROP);
        /*
         * Use JRE from "java.home" on MacOS platform by default. Can be
         * overridden by another property (tibco.use.default mac.jre=true) if
         * necessary.
         */
        if (jreLocation == null && Platform.getOS().equals(Constants.OS_MACOSX)
                && !System.getProperty(TIBCO_USE_DEFAULT_MAC_JRE, "false") //$NON-NLS-1$
                        .equalsIgnoreCase("true")) { //$NON-NLS-1$
            jreLocation = System.getProperty("java.home"); //$NON-NLS-1$
        }
        if (jreLocation != null) {
            IEclipsePreferences jdtLaunchingNode =
                    InstanceScope.INSTANCE.getNode(JDT_LAUNCHING_PLUGIN_ID);
            if (jdtLaunchingNode.get(PREF_VM_XML, "").isEmpty()) { //$NON-NLS-1$
                URL url = null;
                InputStream inputStream = null;
                String vmString = null;
                try {
                    url = new URL(VM_SPEC_DEFAULT);
                    inputStream = url.openConnection().getInputStream();
                    Properties props = new Properties();
                    props.load(inputStream);
                    vmString = props.getProperty(PREF_VM_XML_PROPERTY);
                } catch (IOException e) {
                    logError("Exception while processing default VMs.", e); //$NON-NLS-1$
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            logError("Exception while processing default VMs.", e); //$NON-NLS-1$
                        }
                    }
                }
                if (vmString != null) {
                    String jreName =
                            System.getProperty(TIBCO_DEFAULT_JDT_JRE_NAME_PROP,
                                    DEFAULT_JRE_NAME);
                    vmString =
                            vmString.replace("${tibco.default.jdt.jre.name}", jreName); //$NON-NLS-1$
                    vmString =
                            vmString.replace("${tibco.default.jdt.jre.location}", jreLocation); //$NON-NLS-1$
                    jdtLaunchingNode.put(PREF_VM_XML, vmString);
                    try {
                        jdtLaunchingNode.flush();
                    } catch (BackingStoreException e) {
                        logError("Exception while processing default VMs.", e); //$NON-NLS-1$
                    }
                    /* SCF-181: Compliance options needs to be updated. */
                    updateCompliance(JavaRuntime.getDefaultVMInstall());
                }
            }
        }
    }

    /**
     * Updates compliance options according to JVM version.
     * 
     * @param vm
     *            JVM installation.
     */
    private static void updateCompliance(IVMInstall vm) {
        if (vm instanceof IVMInstall2) {
            String javaVersion = ((IVMInstall2) vm).getJavaVersion();
            if (javaVersion != null) {
                String compliance = null;
                if (javaVersion.startsWith(JavaCore.VERSION_1_5)) {
                    compliance = JavaCore.VERSION_1_5;
                } else if (javaVersion.startsWith(JavaCore.VERSION_1_6)) {
                    compliance = JavaCore.VERSION_1_6;
                } else if (javaVersion.startsWith(JavaCore.VERSION_1_7)) {
                    compliance = JavaCore.VERSION_1_7;
                }
                if (compliance != null) {
                    Hashtable options = JavaCore.getOptions();
                    JavaCore.setComplianceOptions(compliance, options);
                    JavaCore.setOptions(options);
                }
            }
        }
    }

    private void logError(String message, Exception e) {
        BrandingPlugin
                .getDefault()
                .getLog()
                .log(new Status(IStatus.ERROR, BrandingPlugin.PLUGIN_ID,
                        message, e));
    }
}
