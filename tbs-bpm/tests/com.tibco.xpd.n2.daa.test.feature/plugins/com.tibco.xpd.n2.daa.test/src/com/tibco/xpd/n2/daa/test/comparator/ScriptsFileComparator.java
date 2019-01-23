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
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.XmlDiffSequenceQualifier;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * DAA File comparator for
 * DAA\plugins\ProjectXXX\com.tibco.daa\ProjectXXX\.bpm\*.scripts files
 *
 *
 * @author aallway
 * @since 3 Jan 2019
 */
public class ScriptsFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {

            XmlDiff diff = new XmlDiff(goldIS, generatedIS);

            /* Compare script entries in order of processId value */
            XmlDiffSequenceQualifier sequenceQualifier =
                    new XmlDiffSequenceQualifier(
                            new XmlDiffNodePath("scriptdescriptor/script"),
                            new XmlDiffNodePath(
                                    new String[] { "process", "processId",
                                            XmlDiffNodePath.TEXT_CONTENT },
                                    false));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Compare script/factory elements ordered based on scriptingName
             * value
             */
            sequenceQualifier =
                    new XmlDiffSequenceQualifier(
                            new XmlDiffNodePath("script/factory"),
                            new XmlDiffNodePath(
                                    new String[] { "scriptingName",
                                            XmlDiffNodePath.TEXT_CONTENT },
                                    false));
            diff.addSequenceElementQualifier(sequenceQualifier);

            /*
             * Ignore the range/@lower & range/upper timestamp part
             */
            diff.addCustomComparison(
                    new XmlDiffNodePath("scriptdescriptor/@version"),
                    VersionAttributeComparator.COMPARATOR);

            /*
             * Compare!
             */
            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        "File being compared:: " + fileName //$NON-NLS-1$
                                + " difference reported::" + diffString); //$NON-NLS-1$
            }
        } catch (SAXException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                    + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
        } catch (IOException e) {
            e.printStackTrace();
            toReturn = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "[" //$NON-NLS-1$
                    + fileName + "]\n" + e.getMessage()); //$NON-NLS-1$
        }
        return toReturn;
    }
}
