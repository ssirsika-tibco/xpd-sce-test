/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

/**
 * HyperLink List control. Composite that manages listing of hyperlinks.
 * 
 * @author rsomayaj
 * @since 3.3 (21 Apr 2010)
 */
public class HyperlinkControl extends ScrolledComposite {

    private Composite hyperLinkControlComposite;

    private final FormToolkit toolkit;

    private IHyperlinkListener hyperlinkListener;

    /**
     * @param parent
     * @param style
     */
    public HyperlinkControl(Composite parent, int style) {
        super(parent, style);
        this.setExpandHorizontal(true);
        this.setExpandVertical(true);

        toolkit = new FormToolkit(getDisplay());

        hyperLinkControlComposite = toolkit.createComposite(this);
        GridLayout gl = new GridLayout(2, false);
        hyperLinkControlComposite.setLayout(gl);
        setContent(hyperLinkControlComposite);
    }

    /**
     * @see org.eclipse.swt.widgets.Control#addMouseListener(org.eclipse.swt.events.MouseListener)
     * 
     * @param listener
     */
    @Override
    public void addMouseListener(MouseListener listener) {
        // Add the listener to the hyperlink composite
        if (hyperLinkControlComposite != null
                && !hyperLinkControlComposite.isDisposed()) {
            hyperLinkControlComposite.addMouseListener(listener);
        }
        super.addMouseListener(listener);
    }

    /**
     * @see org.eclipse.swt.widgets.Control#removeMouseListener(org.eclipse.swt.events.MouseListener)
     * 
     * @param listener
     */
    @Override
    public void removeMouseListener(MouseListener listener) {
        if (!isDisposed()) {
            // Remove the listener to the hyperlink composite
            if (hyperLinkControlComposite != null
                    && !hyperLinkControlComposite.isDisposed()) {
                hyperLinkControlComposite.removeMouseListener(listener);
            }
            super.removeMouseListener(listener);
        }
    }

    /**
     * @param label
     * @param data
     */
    public Hyperlink addHyperLink(String label, Image image, Object data) {
        GridData gd;

        ImageHyperlink imageHyperLink = null;

        if (image != null) {
            imageHyperLink =
                    toolkit.createImageHyperlink(hyperLinkControlComposite,
                            SWT.NONE);
            imageHyperLink.setImage(image);
            imageHyperLink.setHref(data);

        } else {
            Composite blankImage =
                    toolkit.createComposite(hyperLinkControlComposite);

            gd = new GridData();
            gd.heightHint = 1;
            gd.widthHint = 1;
            blankImage.setLayoutData(gd);
        }

        Hyperlink hyperlink =
                toolkit.createHyperlink(hyperLinkControlComposite,
                        label,
                        SWT.NONE);
        hyperlink.setHref(data);

        // The hyper link listener must be set before adding the hyperlink.
        if (hyperlinkListener != null) {
            hyperlink.addHyperlinkListener(hyperlinkListener);
            if (imageHyperLink != null) {
                imageHyperLink.addHyperlinkListener(hyperlinkListener);
            }
        } else {
            throw new IllegalStateException(
                    "Must set HyperlinkListener before adding hyperlinks"); //$NON-NLS-1$
        }

        Point size =
                hyperLinkControlComposite.computeSize(SWT.DEFAULT,
                        SWT.DEFAULT,
                        true);

        this.setMinSize(size);

        return hyperlink;
    }

    /**
     * Add a separator item to list.
     */
    public void addSeparator() {
        Composite filler =
                toolkit.createComposite(hyperLinkControlComposite, SWT.NONE);
        GridData gd = new GridData();
        gd.heightHint = 1;
        gd.widthHint = 1;
        filler.setLayoutData(gd);

        Label separator =
                toolkit.createSeparator(hyperLinkControlComposite,
                        SWT.HORIZONTAL);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 10;
        separator.setLayoutData(gd);

        separator.setEnabled(false);

    }

    /**
     * 
     */
    public void clearHyperLinks() {
        if (hyperLinkControlComposite != null) {
            Control[] children = hyperLinkControlComposite.getChildren();
            for (Control control : children) {
                control.dispose();
            }
        }
    }

    /**
     * @param hyperlinkListener
     *            the hyperlinkListener to set
     */
    public void setHyperlinkListener(IHyperlinkListener hyperlinkListener) {
        this.hyperlinkListener = hyperlinkListener;
    }

    /**
     * @see org.eclipse.swt.widgets.Widget#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
        super.dispose();
    }

}
