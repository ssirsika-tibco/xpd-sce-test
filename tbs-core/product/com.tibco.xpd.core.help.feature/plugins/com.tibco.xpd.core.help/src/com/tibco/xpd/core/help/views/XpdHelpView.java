package com.tibco.xpd.core.help.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

/**
 * Help view to display web help (both online and offline) in a browser control.
 * 
 * @author jarciuch
 * @since 4 Sep 2014
 */
public class XpdHelpView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "com.tibco.xpd.core.help.views.XpdHelpView"; //$NON-NLS-1$

    private static final String LAST_URL = "XpdHelpView.LastUrl"; //$NON-NLS-1$

    private Browser browser;

    private IMemento memento;

    /**
     * The constructor.
     */
    public XpdHelpView() {
    }

    @Override
    public void init(IViewSite site, IMemento memento) throws PartInitException {
        super.init(site, memento);
        this.memento = memento;
    }

    @Override
    public void createPartControl(Composite parent) {
        browser = new Browser(parent, SWT.NONE);
        browser.addLocationListener(new LocationAdapter() {
            @Override
            public void changed(LocationEvent event) {
                String location = event.location;
                IActionBars bars = getViewSite().getActionBars();
                bars.getStatusLineManager().setMessage(location);
            }

        });
        /**
         * Restores the lastly used browser's URL location saved in the memento
         * (if available).
         */
        if (memento == null)
            return;
        String lastUrl = memento.getString(LAST_URL);
        if (lastUrl != null && !lastUrl.trim().isEmpty()) {
            browser.setUrl(lastUrl);
        }
    }

    /**
     * @return the browser control used by this view.
     */
    public Browser getBrowser() {
        return browser;
    }

    @Override
    public void setFocus() {
        browser.setFocus();
    }

    @Override
    public void dispose() {
        if (browser != null && !browser.isDisposed()) {
            browser.dispose();
            browser = null;
        }
    };

    /**
     * Saves last location URL used before view is destroyed so it can be set
     * back when the view is created.
     * 
     * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
     * 
     * @param memento
     *            the memen
     */
    @Override
    public void saveState(IMemento memento) {
        super.saveState(memento);
        if (browser != null && !browser.isDisposed()) {
            memento.putString(LAST_URL, browser.getUrl());
        }
    }
}