/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.IActionBars;

import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * Abstract class that defines the sash divided section of a properties.
 * Subclasses will have to provide implementation for the two sub-sections of
 * this section (general and details sections).
 * 
 * @author allway (from original process widget SashDividedSection by njpatel)
 * 
 */
public abstract class SashDividedEObjectSection extends AbstractEObjectSection {

    /**
     * Section Container that is needed to overwrite the ComputeSize of the
     * section to calculate the width/height correctly depending on the
     * orientation of the layout. SashForms were originally used but they kept
     * screwing the layout whenever the properties section was resized.
     * 
     */
    private class SectionComposite extends Composite {

        public SectionComposite(Composite parent, int style) {
            super(parent, style);
        }

        @Override
        public Point computeSize(int wHint, int hHint, boolean changed) {

            // We never want to kick the tabbed property view's scroll bar into
            // action. It is up to the details / general section to have
            // scroll bars if they want them.

            // So just return our action root size as whatever size parent
            // wants us to be.
            Point point = new Point(wHint, hHint);

            return point;
        }

    }

    // Width hint of all text controls
    protected static final int TEXT_WIDTH_HINT = 150;;

    /**
     * Sash listener - Adjusts the relative size of the general and detail
     * sections on the position of the sash when dragged.
     */
    private final Listener sashListener = new Listener() {

        // Point where the sash stops horizontally at either extremes
        private static final int HORIZONTAL_LIMIT = 150;

        // Point where the sash stops vertically at either extremes
        private static final int VERTICAL_LIMIT = 50;

        public void handleEvent(Event event) {

            Rectangle sashRect = sash.getBounds();
            Rectangle shellRect = sectionRoot.getClientArea();
            FormData sashData = (FormData) sash.getLayoutData();

            if (sashData != null) {
                if (sashOrientation == SWT.VERTICAL) {
                    int right =
                            shellRect.width - sashRect.width - HORIZONTAL_LIMIT;
                    event.x =
                            Math
                                    .max(Math.min(event.x, right),
                                            HORIZONTAL_LIMIT);
                    if (event.x != sashRect.x) {
                        sashData.left = new FormAttachment(0, event.x);

                        // Calculate the percent of the sash
                        sashPercent = (float) event.x / (float) shellRect.width;
                        sectionRoot.layout();
                    }
                } else /* SWT.HORIZONTAL */{
                    int top =
                            shellRect.height - sashRect.height - VERTICAL_LIMIT;
                    event.y = Math.max(Math.min(event.y, top), VERTICAL_LIMIT);
                    if (event.y != sashRect.y) {
                        sashData.top = new FormAttachment(0, event.y);

                        // Calculate the percent of the sash
                        sashPercent =
                                (float) event.y / (float) shellRect.height;

                        sectionRoot.layout();
                    }
                }
            }

            PropertiesPlugin.getDefault().getPreferenceStore()
                    .setValue(horzSashPercentPrefId, (int) (sashPercent * 100));

            layoutUpdated();
        }

    };

    // Main composite
    private SectionComposite sectionRoot;

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

    /**
     * Constructor defines unique action id prefix for layout actions.
     * 
     * @param actionIdPrefix
     */
    public SashDividedEObjectSection(EClass selectionFilterClass,
            String actionIdPrefix) {
        super(selectionFilterClass);
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
        /*
         * Update the position of the sash when the properties page is resized.
         * The sash should, roughly, be at the same relative position on the
         * page
         */
        if (getPropertySheetPage() != null) {
            getPropertySheetPage().getControl()
                    .addControlListener(new ControlAdapter() {

                        public void controlResized(ControlEvent e) {

                            if (!sectionRoot.isDisposed()) {
                                if (sectionRoot.getLayout() instanceof FormLayout) {
                                    Rectangle shellRect =
                                            sectionRoot.getClientArea();
                                    FormData fData =
                                            (FormData) sash.getLayoutData();

                                    // Horizontal layout
                                    if (sashOrientation == SWT.VERTICAL) {
                                        int sashX =
                                                (int) (shellRect.width * sashPercent);

                                        fData.left =
                                                new FormAttachment(0, sashX);
                                        sash.setLayoutData(fData);

                                    } else /* Vertical Layout */{
                                        int sashY =
                                                (int) (shellRect.height * sashPercent);

                                        fData.top =
                                                new FormAttachment(0, sashY);
                                        sash.setLayoutData(fData);
                                    }
                                }

                                updateLayout();
                            }
                        }

                    });
        }

        sectionRoot = new SectionComposite(parent, SWT.NONE);
        toolkit.adapt(sectionRoot);

        // sectionRoot.setLayout(new FormLayout());

        // Create General section
        generalComposite = toolkit.createComposite(sectionRoot);
        generalComposite.setLayout(new FillLayout());

        createGeneralSection(generalComposite, toolkit);

        // Create the sash
        sash = new Sash(sectionRoot, sashOrientation);
        sash.addListener(SWT.Selection, sashListener);
        sash.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                setSashPercent(1.0f);
                updateLayout();
            }
        });

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
        if (newPercent == sashPercent
                || !(sectionRoot.getLayout() instanceof FormLayout)) {
            return;
        }
        sashPercent = newPercent;
        int percent = (int) (100 * sashPercent);
        Assert.isLegal(percent >= 0 && percent <= 100,
                "Percent must be in <0, 100> range. Actual percent value was: " //$NON-NLS-1$
                        + percent);
        boolean created = false;
        FormData formData = (FormData) sash.getLayoutData();
        if (formData == null) {
            formData = new FormData();
            created = true;
        }

        if (sashOrientation == SWT.HORIZONTAL) {
            formData.left = new FormAttachment(0, 0);
            formData.top = new FormAttachment(percent, 0);
            formData.right = new FormAttachment(100, 0);
        } else /* SWT.VERTICAL */{
            formData.left = new FormAttachment(percent, 0);
            formData.top = new FormAttachment(0, 0);
            formData.bottom = new FormAttachment(100, 0);
        }
        if (created) {
            sash.setLayoutData(formData);
        }

        updateLayout();
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
        updateLayout();
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

                                updateLayout();
                            }
                        }
                    };
            action.setId(horizontalLayoutActionId);
            action
                    .setToolTipText(Messages.SashDividedEObjectSection_horizLayoutAction_tooltip);
            action
                    .setImageDescriptor(PropertiesPlugin
                            .getDefault()
                            .getImageRegistry()
                            .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_HORIZONTAL_LAYOUT));
            action
                    .setDisabledImageDescriptor(PropertiesPlugin
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
                                updateLayout();
                            }
                        }
                    };
            action.setId(verticalLayoutActionId);
            action
                    .setToolTipText(Messages.SashDividedEObjectSection_vertLayoutAction_tooltip);
            action
                    .setImageDescriptor(PropertiesPlugin
                            .getDefault()
                            .getImageRegistry()
                            .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_VERTICAL_LAYOUT));
            action
                    .setDisabledImageDescriptor(PropertiesPlugin
                            .getDefault()
                            .getImageRegistry()
                            .getDescriptor(PropertiesPluginConstants.IMG_TOOLBAR_VERTICAL_LAYOUT));

            vAction = new ActionContributionItem(action);
        }

        return vAction;
    }

    /**
     * Update the layout of this section. This will layout the controls
     * dependent on the layout orientation selected
     */
    private void updateLayout() {

        // Used to get problems when show properties was used on sash divided
        // items section when props view wasn't open.
        // Sash used to be positioned at left edge. This was caused by the fact
        // that the first couple of times thru here, sectionRoot size was 0,0.
        // This meant that FormAttachments got added with this in mind and when
        // recalculated when sectionRoot did have a size, it was too late.
        // Recreating the formdata / form attachments everytime caused other
        // problems (corruption of general section when resizing main eclipse
        // window.
        // 
        // The fix below ensures that the form layout and form data /
        // attachments are not created and set until we actually have a size to
        // work with. We use a fill layout until we have been given a proper
        // size.

        // Don't do anything until the control has a size of some kind!
        Rectangle b = getPropertySheetPage().getControl().getBounds();
        if (b.width < 1 || b.height < 1) {
            // System.out.println("SashDividedSection() ZERO size");
            // //$NON-NLS-1$
            sectionRoot.setLayout(new FillLayout());
            return;
        } else {
            // System.out.println("SashDividedSection() with size");
            // //$NON-NLS-1$

            if (!(sectionRoot.getLayout() instanceof FormLayout)) {
                // System.out.println("SashDividedSection() adding form
                // layout"); //$NON-NLS-1$

                sectionRoot.setLayout(new FormLayout());
            }
        }

        FormData fData;

        // Udpate the style of the sash
        if ((sash.getStyle() & sashOrientation) == 0) {
            // Dispose the current sash and create a new one
            sash.removeListener(SWT.Selection, sashListener);
            sash.dispose();
            sash = new Sash(sectionRoot, sashOrientation);
            sash.addListener(SWT.Selection, sashListener);

            // NOTE: Was getting pronblems with refresh when kept resetting
            // formData in control laypout data. So now is coded to reassign
            // formdata ONLY on re-orientation or initial setup
            sash.setLayoutData(null);
            detailsComposite.setLayoutData(null);
            generalComposite.setLayoutData(null);
        }

        // Update the sash
        FormData newFData = null;

        newFData = null;

        fData = (FormData) sash.getLayoutData();
        if (fData == null) {
            fData = new FormData();
            newFData = fData;

            int percent = (int) (100 * sashPercent);

            if (sashOrientation == SWT.HORIZONTAL) {
                fData.left = new FormAttachment(0, 0);
                fData.top = new FormAttachment(percent, 0);
                fData.right = new FormAttachment(100, 0);
            } else /* SWT.VERTICAL */{
                fData.left = new FormAttachment(percent, 0);
                fData.top = new FormAttachment(0, 0);
                fData.bottom = new FormAttachment(100, 0);
            }

            if (newFData != null) {
                sash.setLayoutData(newFData);
            }
        }

        // Update the general section

        fData = (FormData) generalComposite.getLayoutData();
        if (fData == null) {
            fData = new FormData();
            newFData = fData;
        }
        fData.left = new FormAttachment(0, 0);
        fData.top = new FormAttachment(0, 0);

        if (sashOrientation == SWT.HORIZONTAL) {
            fData.right = new FormAttachment(100, 0);
            fData.bottom = new FormAttachment(sash, 0);
        } else /* SWT.VERTICAL */{
            fData.right = new FormAttachment(sash, 0);
            fData.bottom = new FormAttachment(100, 0);
        }

        if (newFData != null) {
            generalComposite.setLayoutData(newFData);
        }

        // Update the implementation section
        newFData = null;

        fData = (FormData) detailsComposite.getLayoutData();
        if (fData == null) {
            fData = new FormData();
            newFData = fData;
        }
        fData.right = new FormAttachment(100, 0);
        fData.bottom = new FormAttachment(100, 0);

        if (sashOrientation == SWT.HORIZONTAL) {
            fData.left = new FormAttachment(0, 0);
            fData.top = new FormAttachment(sash, 0);
        } else /* SWT.VERTICAL */{
            fData.left = new FormAttachment(sash, 0);
            fData.top = new FormAttachment(0, 0);
        }

        if (newFData != null) {
            detailsComposite.setLayoutData(newFData);
        }

        sectionRoot.getParent().layout(true, true);

        getPropertySheetPage().getControl().redraw();
        getPropertySheetPage().getControl().update();

        layoutUpdated();
    }
}
