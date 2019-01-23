/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.IOException;
import java.io.InputStream;

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
public class NewXMIFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {
            XmlDiff diff = new XmlDiff(goldIS, generatedIS);

            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@version")));//$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@location")));//$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(
                    new XmlDiffNodePath("@key")));//$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@value")));//$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@rangeLow")));//$NON-NLS-1$

            diff.addIgnoreNode(new XmlDiffIgnoreNode(new XmlDiffNodePath(
                    "@rangeHigh")));//$NON-NLS-1$

            diff.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("installations/releaseUnits"), //$NON-NLS-1$
                    new XmlDiffNodePath("@componentID"))); //$NON-NLS-1$

            diff.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("installations/releaseUnits"), //$NON-NLS-1$
                    new XmlDiffNodePath("@name"))); //$NON-NLS-1$

            diff.addSequenceElementQualifier(new XmlDiffSequenceQualifier(
                    new XmlDiffNodePath("releaseUnits/memberBundles"), //$NON-NLS-1$
                    new XmlDiffNodePath("@componentID"))); //$NON-NLS-1$

            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "["//$NON-NLS-1$
                        + fileName + "]\n" + diffString);//$NON-NLS-1$
            }
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toReturn;
    }
}
