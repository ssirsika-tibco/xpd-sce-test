package com.tibco.xpd.n2.daa.test.junit.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.n2.daa.test.junit.DaaTestListener;

/**
 * Writes summary (pass / fail) to a summary report file.
 * 
 * @author tstephen
 * 
 */
public class SummaryReporter implements DaaTestListener {
    private static final String EMPTY_COLUMN =
            "                                        "; //$NON-NLS-1$

    private PrintWriter out;

    public SummaryReporter(String fileName) throws IOException {
        // File report = new File("acceptance-test-report2.txt");
        File report = new File(fileName);
        System.err.println("Outputing acceptance test report to: " //$NON-NLS-1$
                + report.getAbsolutePath());
        out = new PrintWriter(report);
        // write header for report file
        out.print(pad("Project")); //$NON-NLS-1$
        out.println("Result"); //$NON-NLS-1$
    }

    public void fire(String projectName, IStatus status, Level level) {
        if (level == Level.SUMMARY) {
            out.print(pad(projectName));
            out.println(status.getMessage());
        }
    }

    public void fireTestComplete() {
        out.close();
    }

    /**
     * Pads the project name to 40 chars to improve formatting.
     * 
     * @param projectName
     * @return
     */
    private String pad(String projectName) {
        return (projectName + EMPTY_COLUMN).substring(0, EMPTY_COLUMN.length());
    }

}
