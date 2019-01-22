/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.core.test.util;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.xpd.core.xmlunit.IgnorePrefixComparator;
import com.tibco.xpd.core.xmlunit.XmlDiff;
import com.tibco.xpd.core.xmlunit.XmlDiffNodePath;
import com.tibco.xpd.core.xmlunit.xsd.WsdlDiff;

/**
 * @author kupadhya
 * 
 */
public class WSDLFileComparator extends FileComparator {

    private boolean shouldPrintFileContent = false;

    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {
        IStatus toReturn = null;
        InputSource goldIS = new InputSource(goldFile);
        InputSource generatedIS = new InputSource(generatedFile);
        try {
            XmlDiff diff = new WsdlDiff(goldIS, generatedIS);
            diff.setTestFileContent(shouldPrintFileContent);

            /*
             * Ignore prefix on comparisons of part/@element attributes (as
             * imported types name space prefixes can get moved around.
             */
            diff.addCustomComparison(new XmlDiffNodePath("part/@element"),
                    new IgnorePrefixComparator(":"));

            boolean similar = diff.similar();
            if (!similar) {
                String diffString = diff.toString();
                toReturn = new Status(IStatus.ERROR, TestUtilPlugin.PLUGIN_ID,
                        "File being compared:: " + fileName //$NON-NLS-1$
                                + " difference reported::" + diffString); //$NON-NLS-1$
            } else {
                toReturn = new Status(IStatus.OK, TestUtilPlugin.PLUGIN_ID,
                        "File being compared:: " + fileName //$NON-NLS-1$
                                + " compare ok."); //$NON-NLS-1$ )
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public void setTestFileContent(boolean shouldPrintFileContent) {

        this.shouldPrintFileContent = shouldPrintFileContent;
    }
}
