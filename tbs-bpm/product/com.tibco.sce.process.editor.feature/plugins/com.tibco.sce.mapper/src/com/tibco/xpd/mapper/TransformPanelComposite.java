/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Composite for displaying the transform panel. Initially this just supports
 * sorting when multiple fields are mapped to the same target. Later it will
 * need to support scripting for more complicated transformations.
 * 
 * @author nwilson
 */
public class TransformPanelComposite extends Canvas implements PaintListener {

    /** The panel margin. */
    private static final int MARGIN = 10;

    /** The title. */
    private String title;

    /** Panel close button. */
    private Button close;

    /** Background gradient start colour. */
    private Color gradient1;

    /** Background gradient end colour. */
    private Color gradient2;

    /** Title text colour. */
    private Color text;

    /** The section to display. */
    private ITransformSection section;

    /**
     * @param parent The parent composite.
     * @param style The style bits.
     */
    public TransformPanelComposite(Composite parent, int style) {
        super(parent, style);
        title = Messages.getString("TransformPanelComposite.SortFields"); //$NON-NLS-1$
        ImageCache cache = MapperActivator.getDefault().getImageCache();
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = MARGIN;
        layout.marginWidth = MARGIN;
        setLayout(layout);
        close = new Button(this, SWT.PUSH);
        close
                .setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 2,
                        1));
        close.setImage(cache.getImage(ImageCache.CLOSE));
        close.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                dispose();
            }
        });

        text = getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
        gradient1 = getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
        gradient2 =
                getDisplay()
                        .getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);

        addPaintListener(this);
    }

    /**
     * Paints the background and title.
     * 
     * @param e The paint event.
     * @see org.eclipse.swt.events.PaintListener#paintControl(
     *      org.eclipse.swt.events.PaintEvent)
     */
    public void paintControl(PaintEvent e) {
        GC gc = e.gc;
        Rectangle r = getBounds();
        Color fg = gc.getForeground();
        Color bg = gc.getBackground();
        gc.setForeground(gradient1);
        gc.setBackground(gradient2);
        gc.fillGradientRectangle(r.x, r.y, r.width, r.height, true);
        gc.setForeground(text);
        gc.drawText(title, r.x + MARGIN, r.y + MARGIN, true);
        gc.setForeground(fg);
        gc.setBackground(bg);
        section.contribute(gc);
    }

    /**
     * Sets the section to display.
     * @param section The new section.
     */
    public void setSection(ITransformSection section) {
        this.section = section;
        title = section.getTitle();
        removeChildren();
        section.contribute(this);
        layout();
    }

    /**
     * Removes all child components except the close button.
     */
    private void removeChildren() {
        Control[] children = getChildren();
        for (Control child : children) {
            if (child != close) {
                child.dispose();
            }
        }
    }

    /**
     * Called when the shell is closing.
     */
    public void close() {
        if (section != null) {
            section.close();
            removeChildren();
            section = null;
        }
    }
}
