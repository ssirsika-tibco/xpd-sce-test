/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.comparator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.core.test.util.FileComparator;
import com.tibco.xpd.n2.daa.test.Activator;

/**
 * @author kupadhya
 * 
 */
public class ManifestFileComparator extends FileComparator {
    @Override
    public IStatus compareContents(InputStream goldFile,
            InputStream generatedFile, String fileName) {

        IStatus toReturn = Status.OK_STATUS;
        try {

            Manifest goldManifest = new Manifest(goldFile);
            Manifest generatedManifest = new Manifest(generatedFile);
            StringBuilder errorMsg = new StringBuilder();

            Attributes goldAttributes = goldManifest.getMainAttributes();
            Attributes generatedAttributes =
                    generatedManifest.getMainAttributes();
            if (goldAttributes.size() != generatedAttributes.size()) {

                String errMsg =
                        "Number of entries in the gold & generated manifest files do not match"; //$NON-NLS-1$
                toReturn =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID, errMsg);
                return toReturn;
            }
            /*
             * XPD-3825: Saket: Uncommenting to exclude bundle versions from
             * comparison.
             */
            List<String> entryToExclude = getEntryToExclude();
            Set<Entry<Object, Object>> goldES = goldAttributes.entrySet();
            for (Entry<Object, Object> entry : goldES) {

                Attributes.Name key = (Attributes.Name) entry.getKey();
                Object goldAttr = entry.getValue();
                /*
                 * XPD-3825: Saket: Uncommenting to exclude bundle versions from
                 * comparison.
                 */
                if (entryToExclude.contains(key.toString())) {

                    continue;
                } else {
                    /*
                     * XPD-3825: Saket: Uncommented till here.
                     */
                    Object generatedAttr = generatedAttributes.get(key);
                    if (!goldAttr.equals(generatedAttr)) {

                        errorMsg.append("Attributes for entry " + key //$NON-NLS-1$
                                + " do not match"); //$NON-NLS-1$
                    }
                }
            }
            if (errorMsg.length() > 0) {

                toReturn =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                                errorMsg.toString());
            }
        } catch (IOException e) {

            e.printStackTrace();
            toReturn =
                    new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                            e.getMessage());
        }
        return toReturn;
    }

    /*
     * XPD-3825: Saket: Uncommenting to exclude bundle versions from comparison.
     */
    private List<String> getEntryToExclude() {

        List<String> toExclude = new ArrayList<String>();
        toExclude.add("Bundle-Version"); //$NON-NLS-1$
        toExclude.add("Studio-Version"); //$NON-NLS-1$
        return toExclude;
    }

    /*
     * XPD-3825: Saket: Uncommented till here.
     */

    public static void main(String args[]) {

        InputStream goldStream =
                ManifestFileComparator.class.getClassLoader()
                        .getResourceAsStream("MANIFEST.gold.MF"); //$NON-NLS-1$
        InputStream genStream =
                ManifestFileComparator.class.getClassLoader()
                        .getResourceAsStream("MANIFEST.gen.MF"); //$NON-NLS-1$
        ManifestFileComparator comparator = new ManifestFileComparator();
        IStatus compareStatus =
                comparator.compareContents(goldStream, genStream, null);
        if (!compareStatus.isOK()) {
            System.out.println(compareStatus.getMessage());
        } else {

            System.out.println("compare success"); //$NON-NLS-1$
        }
    }
}
