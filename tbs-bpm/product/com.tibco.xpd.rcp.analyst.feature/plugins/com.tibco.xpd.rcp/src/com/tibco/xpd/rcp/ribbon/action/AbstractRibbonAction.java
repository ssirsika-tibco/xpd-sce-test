/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.ribbon.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import com.hexapixel.widgets.ribbon.AbstractRibbonGroupItem;
import com.hexapixel.widgets.ribbon.RibbonButton;
import com.hexapixel.widgets.ribbon.RibbonTooltip;
import com.tibco.xpd.rcp.internal.actions.IRibbonGroupItemAction;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * To be implemented by the action contributor to the Ribbon.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractRibbonAction implements IDisposable {
    private IAction action;

    private AbstractRibbonGroupItem button;

    private final String title;

    private final Image activeImage;

    private final Image disabledImage;

    private final int imgAlignment;

    private final int style;

    private final boolean showToolTip;

    public AbstractRibbonAction(IAction action, String title,
            Image activeImage, Image disabledImage, int imgAlignment,
            int style, boolean showToolTip) {
        this.action = action;
        this.title = title;
        this.activeImage = activeImage;
        this.disabledImage = disabledImage;
        this.imgAlignment = imgAlignment;
        this.style = style;
        this.showToolTip = showToolTip;
    }

    /**
     * Create the button for the action.
     * 
     * @return
     */
    protected abstract AbstractRibbonGroupItem createButton();

    /**
     * Redraw the button.
     */
    protected abstract void redraw();

    /**
     * Get the button.
     * 
     * @return
     */
    public AbstractRibbonGroupItem getButton() {
        return button;
    }

    /**
     * Get the action.
     * 
     * @return
     */
    public IAction getAction() {
        return action;
    }

    /**
     * Get the tooltip context.
     * 
     * @return
     */
    protected String getTooltipText() {
        return action != null ? action.getToolTipText() : null;
    }

    /**
     * Initialize the action.
     */
    protected void init() {
        button = createButton();
        if (button != null) {

            if (action instanceof IRibbonGroupItemAction) {
                ((IRibbonGroupItemAction) action).setRibbonGroupItem(button);
            }

            button.setEnabled(action.isEnabled());
            action.addPropertyChangeListener(new IPropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent event) {
                    if (IAction.ENABLED.equals(event.getProperty())) {
                        if (button.isEnabled() != action.isEnabled()) {
                            button.setEnabled(action.isEnabled());

                            if (button.isEnabled()) {
                                if (showToolTip) {
                                    button.setToolTip(new RibbonTooltip(
                                            getTitle(), action.getDescription()) {
                                        @Override
                                        public String getContent() {
                                            // Get tooltip from the
                                            // action
                                            return getTooltipText();
                                        }
                                    });
                                }
                            } else {
                                // Remove tooltip
                                button.setToolTip(null);
                            }

                            XpdResourcesPlugin.getStandardDisplay()
                                    .asyncExec(new Runnable() {
                                        @Override
                                        public void run() {
                                            redraw();
                                        }
                                    });
                        }
                    }
                }
            });

            button.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {

                    final Display display =
                            XpdResourcesPlugin.getStandardDisplay();

                    BusyIndicator.showWhile(display, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (action.isChecked() != button.isChecked()) {
                                    action.setChecked(button.isChecked());
                                }
                                action.run();
                            } finally {
                                button.setEnabled(action.isEnabled());
                                if (style == RibbonButton.STYLE_NO_DEPRESS) {
                                    button.setSelected(false);
                                }
                                display.asyncExec(new Runnable() {
                                    @Override
                                    public void run() {
                                        redraw();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }

    public String getTitle() {
        return title;
    }

    protected Image getActiveImage() {
        return activeImage;
    }

    protected Image getDisabledImage() {
        return disabledImage;
    }

    protected int getImgAlignment() {
        return imgAlignment;
    }

    protected int getStyle() {
        return style;
    }

    @Override
    public void dispose() {
        if (button != null) {
            button.dispose();
        }
    }
}
