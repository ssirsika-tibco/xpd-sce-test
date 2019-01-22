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
import com.tibco.xpd.core.xmlunit.XmlDiffIgnoreNode;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * @author kupadhya
 * 
 */
public class WorkModelFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {
            XmlDiff diff = new XmlDiff(goldIS, generatedIS);
            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            "WorkModel"), new XmlDiffNodePath("@workModelUID")); //$NON-NLS-1$ //$NON-NLS-2$
            diff.addSequenceElementQualifier(sequenceQualifier);

            List<XmlDiffNodePath> qualifierPaths =
                    new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath("@name")); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath("@type")); //$NON-NLS-1$
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("inputs"), //$NON-NLS-1$
                            qualifierPaths);

            diff.addSequenceElementQualifier(sequenceQualifier);

            // use the same list of attribute for outputs element
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(
                            new XmlDiffNodePath("outputs"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // use the same list of attribute for inouts element
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath("inouts"), //$NON-NLS-1$
                            qualifierPaths);
            diff.addSequenceElementQualifier(sequenceQualifier);
            // Qualifier for WorkModelEntity element
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            "WorkModelEntity"), new XmlDiffNodePath( //$NON-NLS-1$
                            "@distributionStrategy")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // Qualifier for WorkModelType element
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            "WorkModelType"), //$NON-NLS-1$
                            new XmlDiffNodePath("@workTypeID")); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // Qualifier for WorkModelMapping element
            qualifierPaths = new ArrayList<XmlDiffNodePath>();
            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "TypeParamName", XmlDiffNodePath.TEXT_CONTENT }, false)); //$NON-NLS-1$
            qualifierPaths.add(new XmlDiffNodePath(new String[] {
                    "ModelParamName", XmlDiffNodePath.TEXT_CONTENT }, false)); //$NON-NLS-1$
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(new XmlDiffNodePath(
                            "WorkModelMapping"), qualifierPaths); //$NON-NLS-1$
            diff.addSequenceElementQualifier(sequenceQualifier);
            // ignore difference in value version attribute
            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@version"))); //$NON-NLS-1$
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
