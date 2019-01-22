/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.IActionBars;

import com.tibco.xpd.resources.util.ElapsedTimerHelper;
import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * Abstract class that defines the sash divided section of a properties.
 * Subclasses will have to provide implementation for the two sub-sections of
 * this section (general and details sections).
 * 
 * @author allway (from original process widget SashDividedSection by njpatel)
 * 
 */
public abstract class SashDividedTransactionalSection extends
        AbstractTransactionalSection {

    // Width hint of all text controls
    public static final int TEXT_WIDTH_HINT = 150;

    public static final float MIN_SIDE_SIZE = 0.001f;

    /**
     * Sash listener - Adjusts the relative size of the general and detail
     * sections on the position of the sash when dragged.
     */
    private final Listener sashListener = new Listener() {

        public void handleEvent(Event event) {

            Rectangle sashRect = sash.getBounds();
            Rectangle shellRect = sectionRoot.getClientArea();

            float newPerc = sashPercent;

            if (sashOrientation == SWT.VERTICAL) {
                // Do nothing unless sash has actually moved some
                if (event.x < (sashRect.x - 1) || event.x > (sashRect.x + 1)) {
                    // Calculate the percent of the sash
                    if (shellRect.width > 0) {
                        newPerc = (float) event.x / (float) shellRect.width;
                    }
                }

            } else /* SWT.HORIZONTAL */{
                // Do nothing unless sash has actually moved some
                if (event.y < (sashRect.y - 1) || event.y > (sashRect.y + 1)) {
                    // Calculate the percent of the sash
                    if (shellRect.height > 0) {
                        newPerc = (float) event.y / (float) shellRect.height;
                    }
                }
            }

            if (newPerc != sashPercent) {
                if (newPerc < MIN_SIDE_SIZE) {
                    newPerc = MIN_SIDE_SIZE;
                } else if (newPerc > (1.0f - MIN_SIDE_SIZE)) {
                    newPerc = (1.0f - MIN_SIDE_SIZE);
                }

                setSashPercent(newPerc);
                sectionRoot.layout();
                PropertiesPlugin
                        .getDefault()
                        .getPreferenceStore()
                        .setValue(horzSashPercentPrefId,
                                (int) (sashPercent * 100));
            }
            return;
        }

    };

    private MouseAdapter dblClickListener = new MouseAdapter() {
        @Override
        public void mouseDoubleClick(MouseEvent e) {
            if (DoubleClickState.LAST_USER_SET.equals(currentDblClickState)) {
                currentDblClickState = DoubleClickState.RIGHT;
                setSashPercent(1.0f - MIN_SIDE_SIZE);
            } else if (DoubleClickState.RIGHT.equals(currentDblClickState)) {
                currentDblClickState = DoubleClickState.LEFT;
                setSashPercent(MIN_SIDE_SIZE);
            } else {
                currentDblClickState = DoubleClickState.LAST_USER_SET;
                setSashPercentToLastUserSelected();
            }
        }
    };

    // Main composite
    private Composite sectionRoot;

    // Sash control
    private Sash sash;

    // Default sash orientation
    private int sashOrientation = SWT.VERTICAL;

    // Sash location relative to the section size
    private float sashPercent = 0.5f;

    // General section
    private Composite generalComposite;

    // Details section
    private Composite detailsComposite;

    // Horizontal alignment action
    private ActionContributionItem hAction = null;

    // Vertical alignment action
    private ActionContributionItem vAction = null;

    /** Use to construct unique action Id layout buttons. */
    private String horizontalLayoutActionId;

    /** Use to construct unique action Id layout buttons. */
    private String verticalLayoutActionId;

    private String isHorzSashLayoutPrefId;

    private String horzSashPercentPrefId;

    private Object currentInput = null;

    // Each time the user double click's the sash then cycle thru states
    private enum DoubleClickState {
        LAST_USER_SET, RIGHT, LEFT
    }

    private DoubleClickState currentDblClickState =
            DoubleClickState.LAST_USER_SET;

    @Override
    public void setInput(Collection<?> items) {
        if (items.size() != 1 || items.iterator().next() != currentInput
                && currentInput != null) {
            ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                    + ": SET Changed INPUT @");
        }

        if (items.size() == 1) {
            currentInput = items.iterator().next();
            ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                    + ": SET  INPUT @");
        }
        super.setInput(items);
    }

    @Override
    protected void doRefresh() {
        ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                + ": SET  INPUT @");

        return;
    }

    /**
     * Constructor defines unique action id prefix for layout actions.
     * 
     * @param actionIdPrefix
     */
    public SashDividedTransactionalSection(String actionIdPrefix) {
        ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                + ": Start Construct @");

        this.horizontalLayoutActionId = actionIdPrefix + ".horizontallayout"; //$NON-NLS-1$
        this.verticalLayoutActionId = actionIdPrefix + ".verticallayout"; //$NON-NLS-1$
        this.isHorzSashLayoutPrefId =
                actionIdPrefix + ".PropertiesSashIsVertical"; //$NON-NLS-1$

        this.horzSashPercentPrefId = actionIdPrefix + ".PropertiesSashPercent"; //$NON-NLS-1$

        if (PropertiesPlugin.getDefault().getPreferenceStore()
                .getBoolean(isHorzSashLayoutPrefId)) {
            sashOrientation = SWT.HORIZONTAL;
        } else {
            sashOrientation = SWT.VERTICAL;
        }

        if (PropertiesPlugin.getDefault().getPreferenceStore()
                .contains(this.horzSashPercentPrefId)) {
            int sPercent =
                    PropertiesPlugin.getDefault().getPreferenceStore()
                            .getInt(this.horzSashPercentPrefId);
            if (sPercent < 1 || sPercent > 100) {
                sPercent = 50;
            }

            sashPercent = (float) sPercent / 100;
        }

    }

    @Override
    public void aboutToBeHidden() {
        if (getPropertySheetPage() != null) {
            hideAlignmentButtons();
        }
    }

    @Override
    public void aboutToBeShown() {

        if (getPropertySheetPage() != null) {
            showAlignmentButtons();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.properties.AbstractWidgetPropertiesSection
     * #dispose()
     */
    @Override
    public void dispose() {
        aboutToBeHidden();
        super.dispose();
    }

    /**
     * Overridthis method to perform any post-layout update activities.
     * 
     */
    public void layoutUpdated() {
        return;
    }

    /**
     * Create the general details of the properties (right hand side if
     * horizontal layout used). Subclasses should not set the LayoutData of the
     * composite being returned.
     * 
     * @param parent
     * @param toolkit
     */
    protected abstract Composite createDetailsSection(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * Create the general section of the properties (left hand side if
     * horizontal layout used). Subclasses should not set the LayoutData of the
     * composite being returned.
     * 
     * @param parent
     * @param toolkit
     */
    protected abstract Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * Create a label with a standard width size
     * 
     * @param parent
     * @param factory
     * @param text
     * @return <code>Label</code>
     */
    @Override
    protected Label createLabel(Composite parent, XpdFormToolkit factory,
            String text) {
        Label lbl = factory.createLabel(parent, text, SWT.WRAP);
        lbl.setToolTipText(text);
        GridData gData = new GridData();
        gData.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(gData);

        return lbl;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        sectionRoot = toolkit.createComposite(parent, SWT.NONE);
        toolkit.adapt(sectionRoot);
        sectionRoot.setLayout(new SashDividedLayout());

        // sectionRoot.setLayout(new FormLayout());

        // Create General section
        generalComposite = toolkit.createComposite(sectionRoot);
        generalComposite.setLayout(new FillLayout());

        createGeneralSection(generalComposite, toolkit);

        createSash();

        // Create the detail section
        detailsComposite = toolkit.createComposite(sectionRoot);
        detailsComposite.setLayout(new FillLayout());

        createDetailsSection(detailsComposite, toolkit);

        // update the layout
        // updateLayout();

        return sectionRoot;
    }

    /**
     * Hide alignment buttons in the Property View toolbar.
     */
    protected void hideAlignmentButtons() {
        IActionBars actionBars =
                getPropertySheetPage().getSite().getActionBars();

        // Remove the horizontal and vertical alignment controls in the
        // toolbar
        if (actionBars != null) {
            IToolBarManager manager = actionBars.getToolBarManager();
            boolean update = false;

            while (manager.remove(horizontalLayoutActionId) != null) {
                update = true;
            }

            while (manager.remove(verticalLayoutActionId) != null) {
                update = true;
            }

            if (update) {
                // Update the action bar as it has changed
                actionBars.updateActionBars();
            }
        }
    }

    protected void setSashPercentToLastUserSelected() {
        float percent = 0.5f;

        if (PropertiesPlugin.getDefault().getPreferenceStore()
                .contains(this.horzSashPercentPrefId)) {
            int sPercent =
                    PropertiesPlugin.getDefault().getPreferenceStore()
                            .getInt(this.horzSashPercentPrefId);
            if (sPercent < 1 || sPercent > 100) {
                sPercent = 50;
            }

            percent = (float) sPercent / 100;
        }

        setSashPercent(percent);
    }

    protected void setSashPercent(float newPercent) {
        if (newPercent == sashPercent) {
            return;
        }

        sashPercent = newPercent;

        if (!sectionRoot.isDisposed()) {
            sectionRoot.layout();
        }

        return;
    }

    /**
     * Show alignemtn buttons in the view toolbar.
     */
    protected void showAlignmentButtons() {
        IActionBars actionBars =
                getPropertySheetPage().getSite().getActionBars();

        // Add the horizontal and vertical alignment controls in the toolbar
        if (actionBars != null) {
            IToolBarManager manager = actionBars.getToolBarManager();
            ActionContributionItem hItem = getHorizontalAlignmentAction();
            ActionContributionItem vItem = getVerticalAlignmentAction();

            // Update the status of the actions - if the sash orientation
            // is vertical then the layout is in horizontal mode
            hItem.getAction().setChecked(sashOrientation == SWT.VERTICAL);
            vItem.getAction().setChecked(sashOrientation == SWT.HORIZONTAL);

            manager.add(hItem);
            manager.add(vItem);
            actionBars.updateActionBars();
        }
    }

    /**
     * Get the horizontal layout action for the toolbar
     * 
     * @return Horizontal Layout <code>ActionContributionItem</code>
     */
    private ActionContributionItem getHorizontalAlignmentAction() {

        if (hAction == null) {

            Action action = new Action("hLayout", IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
                        @Override
                        public void run() {
                            /*
                             * If not already in horizontal layout then update
                             * layout. Horizontal layout is active when the sash
                             * orientation is vertical.
                             */
                            if (sashOrientation != SWT.VERTICAL) {
                                sashOrientation = SWT.VERTICAL;

                                PropertiesPlugin
                                        .getDefault()
                                        .getPreferenceStore()
                                        .setValue(isHorzSashLayoutPrefId, false);

                                recreateSash();
                            }
                        }
                    };
            action.setId(horizontalLayoutActionId);
            action.setToolTipText(Messages.SashDividedEObjectSection_horizLayoutAction_tooltip);
            action.setImageDescriptor(PropertiesPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_HORIZONTAL_LAYOUT));
            action.setDisabledImageDescriptor(PropertiesPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_HORIZONTAL_LAYOUT));

            hAction = new ActionContributionItem(action);
        }

        return hAction;
    }

    /**
     * Get the vertical layout action for the toolbar
     * 
     * @return Vertical Layout <code>ActionContributionItem</code>
     */
    private ActionContributionItem getVerticalAlignmentAction() {

        if (vAction == null) {
            Action action = new Action("vLayout", IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
                        @Override
                        public void run() {
                            /*
                             * If not already in vertical layout then update
                             * layout. Vertical layout is active when the sash
                             * orientation is horizontal.
                             */
                            if (sashOrientation != SWT.HORIZONTAL) {
                                sashOrientation = SWT.HORIZONTAL;

                                PropertiesPlugin.getDefault()
                                        .getPreferenceStore()
                                        .setValue(isHorzSashLayoutPrefId, true);

                                recreateSash();
                            }
                        }
                    };
            action.setId(verticalLayoutActionId);
            action.setToolTipText(Messages.SashDividedEObjectSection_vertLayoutAction_tooltip);
            action.setImageDescriptor(PropertiesPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_VERTICAL_LAYOUT));
            action.setDisabledImageDescriptor(PropertiesPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_VERTICAL_LAYOUT));

            vAction = new ActionContributionItem(action);
        }

        return vAction;
    }

    /**
     * Create the sash.
     */
    private void createSash() {
        sash = new Sash(sectionRoot, sashOrientation);
        sash.addListener(SWT.Selection, sashListener);
        sash.addMouseListener(dblClickListener);
    }

    /**
     * @return the sash control
     */
    protected Sash getSash() {
        return sash;
    }

    /**
     * Dispose the current sash and create a new one
     */
    private void recreateSash() {
        sash.removeListener(SWT.Selection, sashListener);
        sash.dispose();
        createSash();

        sectionRoot.layout();
    }

    /**
     * Used to allow this/subclass to force update layout, now that we use a
     * custom layout rather than FormLayout() this should no longer be
     * necessary.
     * 
     * @deprecated Now does nothing
     */
    @Deprecated
    protected void updateLayout() {
        return;
    }

    private class SashDividedLayout extends Layout {

        @Override
        protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            if (checkControls()) {
                if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                    //
                    // Now we have been given a size, let the children catch up.
                    if (sashOrientation == SWT.HORIZONTAL) {
                        Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

                        int sashOffset = (int) (hHint * getSashPercent());

                        generalComposite.computeSize(SWT.DEFAULT, sashOffset);
                        detailsComposite.computeSize(SWT.DEFAULT, hHint
                                - sashOffset);

                    } else {
                        Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

                        int sashOffset = (int) (wHint * getSashPercent());

                        generalComposite.computeSize(sashOffset, SWT.DEFAULT);
                        detailsComposite.computeSize(wHint - sashOffset,
                                SWT.DEFAULT);
                    }
                }
            }

            // Make sure we're never bigger than the hint (this should prevent
            // us ever causing the section's main scrollbars into appearing).
            // We want our 2 sides to have individual scrolling.
            return new Point(wHint, hHint);
        }

        @Override
        protected void layout(Composite composite, boolean flushCache) {
            if (!checkControls()) {
                return;
            }

            float percent = getSashPercent();

            Rectangle bounds = composite.getBounds();

            //
            // Position the Sash
            if (sashOrientation == SWT.HORIZONTAL) {
                Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

                int sashOffset = (int) (bounds.height * percent);

                sash.setBounds(0, sashOffset, bounds.width, sz.y);

                //
                // Position the general (upper) section.
                generalComposite.setBounds(0, 0, bounds.width, sashOffset
                        - (sz.y));

                //
                // Position the general (lower) section.
                detailsComposite.setBounds(0,
                        sashOffset + (sz.y),
                        bounds.width,
                        bounds.height - (sashOffset - (sz.y)));

            } else {
                Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

                int sashOffset = (int) (bounds.width * percent);
                sash.setBounds(sashOffset, 0, sz.x, bounds.height);
                //
                // Position the general (upper) section.
                generalComposite.setBounds(0,
                        0,
                        sashOffset - (sz.x),
                        bounds.height);

                //
                // Position the general (lower) section.
                detailsComposite.setBounds(sashOffset + (sz.x), 0, bounds.width
                        - (sashOffset - (sz.x)), bounds.height);
            }

            layoutUpdated();

            return;
        }

        private boolean checkControls() {
            if (sash != null && !sash.isDisposed() && generalComposite != null
                    && !generalComposite.isDisposed()
                    && detailsComposite != null
                    && !detailsComposite.isDisposed()) {
                return true;
            }
            return false;
        }

        /**
         * @return
         */
        private float getSashPercent() {
            float percent = sashPercent;
            if (percent <= 0) {
                percent = 1;
            }
            return percent;
        }

    }

}
