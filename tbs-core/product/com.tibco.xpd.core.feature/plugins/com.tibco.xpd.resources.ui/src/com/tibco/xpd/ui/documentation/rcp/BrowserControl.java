/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.rcp;

import java.io.IOException;

import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @author mtorres
 */
public class BrowserControl {
    
    // Used to identify the windows platform.
    private static final String WIN_ID = "Windows";//$NON-NLS-1$
    // The default system browser under windows.
    private static final String WIN_PATH = "rundll32"; //$NON-NLS-1$
    // The flag to display a url.
    private static final String WIN_FLAG = "url.dll,FileProtocolHandler"; //$NON-NLS-1$
    // The default browser under unix.
    private static final String UNIX_PATH = "netscape"; //$NON-NLS-1$
    // The flag to display a url.
    private static final String UNIX_FLAG = "-remote openURL"; //$NON-NLS-1$
    
    /**
     * Display a file in the system browser.  If you want to display a
     * file, you must include the absolute path name.
     *
     * @param url the file's url (the url must start with either "http://"
     * or
     * "file://").
     */
    public static void displayURL(String url) {
        boolean windows = isWindowsPlatform();
        String cmd = null;
        try {
            if (windows) {
                // cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
                cmd = WIN_PATH + " " + WIN_FLAG + " " + url; //$NON-NLS-1$ //$NON-NLS-2$
                Process p = Runtime.getRuntime().exec(cmd);
            } else {
                // Under Unix, Netscape has to be running for the "-remote"
                // command to work.  So, we try sending the command and
                // check for an exit value.  If the exit command is 0,
                // it worked, otherwise we need to start the browser.
                // cmd = 'netscape -remote openURL(http://www.java-tips.org)'
                cmd = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                Process p = Runtime.getRuntime().exec(cmd);
                try {
                    // wait for exit code -- if it's 0, command worked,
                    // otherwise we need to start the browser up.
                    int exitCode = p.waitFor();
                    if (exitCode != 0) {
                        // Command failed, start up the browser
                        // cmd = 'netscape http://www.java-tips.org'
                        cmd = UNIX_PATH + " "  + url; //$NON-NLS-1$
                        p = Runtime.getRuntime().exec(cmd);
                    }
                } catch(InterruptedException x) {
                    XpdResourcesPlugin.getDefault().getLogger().error(x);
                }
            }
        } catch(IOException x) {
            // couldn't exec browser
            XpdResourcesPlugin.getDefault().getLogger().error(x);
        }
    }
    /**
     * Try to determine whether this application is running under Windows
     * or some other platform by examing the "os.name" property.
     *
     * @return true if this application is running under a Windows OS
     */
    public static boolean isWindowsPlatform() {
        String os = System.getProperty("os.name");//$NON-NLS-1$
        if ( os != null && os.startsWith(WIN_ID))
            return true;
        else
            return false;
        
    }
}
