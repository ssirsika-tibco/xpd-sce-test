/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * @author kupadhya
 * 
 */
public class SADFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {
            XmlDiff diff = new XmlDiff(goldIS, generatedIS);
            // Element qualifier for channel element
            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@channelId")); //$NON-NLS-1$
            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(
                            new XmlDiffNodePath("channel"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // Element qualifier for work-type element
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@guid")); //$NON-NLS-1$
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            "work-type"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                                + fileName + "]\n" + diffString); //$NON-NLS-1$
            }
        } catch (SAXException e) {
            e.printStackTrace();
            toReturn =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                            + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
        } catch (IOException e) {
            e.printStackTrace();
            toReturn =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                            + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
        }
        return toReturn;
    }
}
