/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.IHyperlinkListener;

/**
 * This class behaves as a viewer. It contains a Scrolled Composite which lists
 * hyperlinks, updates labels and hyperlink listeners when refreshed.
 * 
 * @author rsomayaj
 * @since 3.3 (21 Apr 2010)
 */
public class HyperLinkListViewer {

    private Object input;

    private IStructuredContentProvider contentProvider;

    private ILabelProvider labelProvider;

    private HyperlinkControl hyperlinkControl;

    public static Object SEPARATOR = new Object();

    public HyperLinkListViewer(Composite composite, int style) {
        hyperlinkControl = new HyperlinkControl(composite, style);
    }

    /**
     * @param input
     */
    public void setInput(Object input) {
        this.input = input;

        refresh();
    }

    /**
     * @param contentProvider
     */
    public void setContentProvider(IStructuredContentProvider contentProvider) {
        this.contentProvider = contentProvider;
    }

    /**
     * @param labelProvider
     */
    public void setLabelProvider(ILabelProvider labelProvider) {
        this.labelProvider = labelProvider;
    }

    /**
     * @param hyperLinklistener
     */
    public void setHyperLinkListener(IHyperlinkListener hyperLinklistener) {
        hyperlinkControl.setHyperlinkListener(hyperLinklistener);
    }

    /**
     * @param object
     */
    public void refresh() {
        if (!hyperlinkControl.isDisposed()) {
            hyperlinkControl.setRedraw(false);
            try {

                hyperlinkControl.clearHyperLinks();

                if (contentProvider != null && labelProvider != null
                        && input != null) {

                    Assert.isNotNull(contentProvider.getElements(input));

                    Object[] elements = contentProvider.getElements(input);
                    for (Object obj : elements) {
                        if (obj == SEPARATOR) {
                            hyperlinkControl.addSeparator();
                        } else {

                            hyperlinkControl.addHyperLink(labelProvider
                                    .getText(obj),
                                    labelProvider.getImage(obj),
                                    obj);
                        }
                    }
                }

                // hyperlinkControl.layout();

            } finally {
                if (!hyperlinkControl.isDisposed()) {
                    hyperlinkControl.setRedraw(true);
                    hyperlinkControl.layout(true, true);
                }
            }
        }
    }

    /**
     * @return get the control.
     */
    public Control getControl() {
        return hyperlinkControl;
    }

    /**
     * Dispose any resources created by this viewer.
     */
    public void dispose() {
        hyperlinkControl.dispose();
    }
}
