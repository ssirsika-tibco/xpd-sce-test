/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * A shell for displaying ITransformSections. The shell contains a gradient
 * filled panel and optionally rounded corners. It has a close button and a
 * title provided by the ITransformSection. The shell is disposed of when the
 * user hits the close button or navigates away from the shell.
 * 
 * @author nwilson
 */
public class TransformShell implements ITransformShell {

    /** Identifier for the window title string. */
    private static final String TITLE = "TransformShell_Title"; //$NON-NLS-1$

    /** The SWT shell. */
    private Shell shell;

    /** The region used to support rounded corners. */
    private Region region;

    /** The panel to which sections contribute. */
    private TransformPanelComposite panel;

    /**
     * @return A new instance of the TransformShell.
     */
    public static ITransformShell create() {
        return new TransformShell();
    }

    /**
     * Constructor.
     */
    public TransformShell() {
        String title = Messages.getBundle().getString(TITLE);
        shell = new Shell(SWT.NO_TRIM);
        shell.setText(title);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        shell.setLayout(layout);
        shell.addShellListener(new ShellAdapter() {
            public void shellDeactivated(ShellEvent e) {
                dispose();
            }
        });
        panel = new TransformPanelComposite(shell, SWT.NONE);
        panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        panel.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                dispose();
            }
        });
        final ShellMover mover = new ShellMover();
        panel.addMouseListener(new MouseListener() {
            public void mouseDoubleClick(MouseEvent e) {
            }

            public void mouseDown(MouseEvent e) {
                mover.x = e.x;
                mover.y = e.y;
                panel.addMouseMoveListener(mover);
            }

            public void mouseUp(MouseEvent e) {
                panel.removeMouseMoveListener(mover);
            }

        });
    }

    /**
     * Listens for mouse movement and moves the shell.
     * 
     * @author nwilson
     */
    class ShellMover implements MouseMoveListener {
        /** The x offset of the mouse on the component. */
        private int x;

        /** The y offset of the mouse on the component. */
        private int y;

        /**
         * @param e The mouse event.
         * @see org.eclipse.swt.events.MouseMoveListener#mouseMove(
         *      org.eclipse.swt.events.MouseEvent)
         */
        public void mouseMove(MouseEvent e) {
            Point location = shell.getLocation();
            shell.setLocation(location.x + e.x - x, location.y + e.y - y);
        }
    }

    /**
     * Sets the current section.
     * 
     * @param section The new section.
     * @see com.tibco.xpd.mapper.ITransformShell#setSection(
     *      com.tibco.xpd.mapper.ITransformSection)
     */
    public void setSection(ITransformSection section) {
        panel.setSection(section);
        shell.setImage(section.getTransformImage());
    }

    /**
     * Sets the shell bounds and radius of the rounded corners.
     * 
     * @param x The x position.
     * @param y The y position.
     * @param width The shell width.
     * @param height The shell height.
     * @param radius The radius of the rounded corners.
     * @see com.tibco.xpd.mapper.ITransformShell#setBounds( int, int, int, int,
     *      int)
     */
    public void setBounds(int x, int y, int width, int height, int radius) {
        shell.setBounds(x, y, width, height);
        if (region != null) {
            region.dispose();
        }
        region =
                RegionFactory.createRoundedRectangleRegion(width, height,
                        radius);
        shell.setRegion(region);
    }

    /**
     * Cleans up any used resources and closes the shell.
     */
    public void dispose() {
        panel.close();
        if (region != null) {
            region.dispose();
        }
        shell.dispose();
    }

    /**
     * Opens the shell.
     * 
     * @see com.tibco.xpd.mapper.ITransformShell#open()
     */
    public void open() {
        shell.open();
    }
}
