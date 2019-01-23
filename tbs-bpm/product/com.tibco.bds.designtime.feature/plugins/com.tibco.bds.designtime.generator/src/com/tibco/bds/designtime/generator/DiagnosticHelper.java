package com.tibco.bds.designtime.generator;

import java.util.Iterator;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Utilities to assist with manipulation of EMF Diagnostic objects.
 * @author smorgan
 *
 */
public class DiagnosticHelper {

    /**
     * Builds a String representation of the given diagnostic (and its children)
     * filtered by the supplied severity mask.
     * 
     * @param diag
     * @param severity
     * @return
     */
    public static String toStringBySeverity(Diagnostic diag, int severity) {
        StringBuilder buf = new StringBuilder();
        if ((diag.getSeverity() & severity) != 0) {
            buf.append(diag.getMessage());
        }
        StringBuilder childBuf = new StringBuilder();
        for (Diagnostic child : diag.getChildren()) {
            String childString = toStringBySeverity(child, severity);
            if (childString.length() > 0) {
                if (childBuf.length() > 0) {
                    childBuf.append(", ");
                }
                childBuf.append(childString);
            }
        }
        if (childBuf.length() != 0) {
            buf.append(String.format(" [%s]", childBuf.toString()));
        }
        return buf.toString();
    }

    /**
     * Determines whether the diagnostic or any of its descendants match the
     * given severity mask.
     */
    public static boolean containsSeverity(Diagnostic diagnostic, int severity) {
        boolean result = ((diagnostic.getSeverity() & severity) != 0);
        if (!result) {
            Iterator<Diagnostic> iter =
                diagnostic.getChildren().iterator();
            while (!result && iter.hasNext()) {
                result = containsSeverity(iter.next(), severity);
            }
        }
        return result;
    }
}
