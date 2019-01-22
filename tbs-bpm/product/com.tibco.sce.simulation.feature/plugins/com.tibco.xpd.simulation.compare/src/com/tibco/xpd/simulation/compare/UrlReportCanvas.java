/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * A canvas for displaying an HTML comaparison report.
 * 
 * @author nwilson
 */
public class UrlReportCanvas extends ComparisonReport {
    /** The browser widget used to display the report. */
    private Browser browser;

    /** The comparison engine used to generate the report. */
    private ComparisonReportEngine engine;

    /**
     * @param parent The parent composite to which this canvas will be added.
     * @param engine The report engine used to generate the report.
     */
    public UrlReportCanvas(final Composite parent,
            final ComparisonReportEngine engine) {
        super(parent);
        this.engine = engine;
        browser = new Browser(this, SWT.NONE);
        GridData browserGridData = new GridData();
        browserGridData.grabExcessVerticalSpace = true;
        browserGridData.grabExcessHorizontalSpace = true;
        browserGridData.verticalAlignment = SWT.FILL;
        browserGridData.horizontalAlignment = SWT.FILL;
        browser.setLayoutData(browserGridData);
    }

    /**
     * @param inputs The resources to use as an input for this report.
     * @see com.tibco.xpd.simulation.compare.ComparisonReport#setInput(
     *      org.eclipse.core.resources.IResource[])
     */
    public void setInput(final IResource[] inputs) {
        final String url = engine.generate(inputs, ReportType.OUTPUT_HTML);
        if (url != null) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    if (browser != null && !browser.isDisposed()) {
                        browser.setUrl(url);
                    }
                }
            });
        }
    }

    /**
     * Disposes of the report engine.
     * 
     * @see org.eclipse.swt.widgets.Widget#dispose()
     */
    public void dispose() {
        engine.dispose();
        super.dispose();
    }

}
