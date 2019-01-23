package com.tibco.xpd.simulation.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class SimulationReportView extends ViewPart {
    private Browser browser;

    public SimulationReportView() {
        super();
    }

    public void createPartControl(Composite parent) {
        browser = new Browser(parent, SWT.NONE);
        GridData browserGridData = new GridData();
        browserGridData.grabExcessVerticalSpace = true;
        browserGridData.grabExcessHorizontalSpace = true;
        browserGridData.verticalAlignment = SWT.FILL;
        browserGridData.horizontalAlignment = SWT.FILL;
        browser.setLayoutData(browserGridData);
    }

    public void setFocus() {
    }

    public void setInput(String url) {
        if (browser != null && !browser.isDisposed()) {
            browser.setUrl(url);
        }
    }

}
