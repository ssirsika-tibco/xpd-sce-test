/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.n2.daa.ProjectDAAGenerator;
import com.tibco.xpd.n2.daa.test.util.DAAUtil;

/**
 * Project DAA Generator for junit purposes mainly to get the time stamp of gold
 * DAA to be used as a replacement in junit test generated DAA
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class JUnitProjectDAAGenerator extends ProjectDAAGenerator {

    /** Shared singleton instance. */
    private static final JUnitProjectDAAGenerator INSTANCE =
            new JUnitProjectDAAGenerator();

    /**
     * @see com.tibco.xpd.n2.daa.ProjectDAAGenerator#getQualifierReplacement(org.eclipse.core.resources.IProject)
     * 
     * @param project
     * @return
     */
    @Override
    protected String getQualifierReplacement(IProject project) {

        IFile goldDAA = DAAUtil.getGoldDAA(project);

        String goldOSPath = goldDAA.getLocation().toString();
        try {

            ZipFile goldDAAZip = new ZipFile(new File(goldOSPath));
            String goldBQ = DAAUtil.getDAABuildQualifier(project, goldDAAZip);
            String timestamp = goldBQ.substring(goldBQ.lastIndexOf(".") + 1, //$NON-NLS-1$
                    goldBQ.length());
            return timestamp;
        } catch (ZipException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return super.getQualifierReplacement(project);
    }

    /**
     * Gets reference to the shared instance.
     * 
     * @return
     */
    public static JUnitProjectDAAGenerator getInstance() {

        return INSTANCE;
    }

}
