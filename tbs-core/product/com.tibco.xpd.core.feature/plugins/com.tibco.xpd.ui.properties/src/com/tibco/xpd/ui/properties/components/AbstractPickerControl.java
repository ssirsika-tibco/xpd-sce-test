/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.properties.components;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.editorHandler.OpenEditorHandler;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * This should be extended if a picker control is required that displays an
 * hyperlink along with a browse and clear button. The browse button should be
 * used to show the user a picker whose selected value will be displayed as a
 * hyperlink. If the hyperlink is set as active then it will respond to clicks
 * which the subclasses can handle.
 * 
 * @author njpatel
 * 
 * @param <T>
 *            object being handled by this picker control.
 */
public abstract class AbstractPickerControl<T> extends Composite {

    /**
     * If this picker control is used from a properties sheet, then hyper link
     * will be used to navigate to the selected element and display the text
     * selected. By default hyper link will be set to visible, and will be
     * toggled based on whether the picker is used from wizard/properties sheet
     */
    private ImageHyperlink link;

    private Button browseBtn;

    private Button clearBtn;

    private ILabelProvider labelProvider;

    private ILabelProvider adapterFactoryLabelProvider;

    private final EditingDomain ed;

    private T value;

    private final IHyperlinkListener linkListener;

    private boolean showClear = true;

    private boolean hyperLinkActive = true;

    private int minimumTextWidth = -1;

    /**
     * This is to specify the marginal gap/space between link/label, browse
     * button and clear button
     */
    int padding = 3;

    /**
     * If this picker control is used from a wizard, then label will be used to
     * display the text for the selected picker element. (Hyper link will be
     * used in properties sheet). By default hyper link will be set to visible,
     * and will be toggled based on whether the picker is used from
     * wizard/properties sheet
     */
    private CLabel linkLabel;

    private Composite controlsContainer;

    /**
     * Abstract picker control to show an hyperlink with a browse and clear
     * button.
     * 
     * @param parent
     *            parent container
     * @param style
     *            {@link SWT} style of this {@link Composite}
     * @param toolkit
     *            form toolkit
     * @param editingDomain
     */
    public AbstractPickerControl(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain) {
        this(parent, style, toolkit, editingDomain, true);
    }

    /**
     * Abstract picker control to show an hyperlink with a browse and clear
     * button.
     * 
     * @param parent
     *            parent container
     * @param style
     *            {@link SWT} style of this {@link Composite}
     * @param toolkit
     *            form toolkit
     * @param editingDomain
     * @param showClear
     *            <code>true</code> if the Clear button should be shown.
     */
    public AbstractPickerControl(Composite parent, int style,
            XpdFormToolkit toolkit, EditingDomain editingDomain,
            boolean showClear) {

        super(parent, style);
        this.ed = editingDomain;
        this.showClear = showClear;
        toolkit.adapt(this);

        adapterFactoryLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());

        linkListener = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                if (value != null) {
                    hyperlinkActivated(value);
                }
            }
        };

        setLayout(new FillLayout());
        createControl(this, toolkit);
    }

    /**
     * @param abstractPickerControl
     * @param toolkit
     * @param containerType
     */
    protected void createControl(Composite parent, XpdFormToolkit toolkit) {

        controlsContainer = toolkit.createComposite(parent);

        controlsContainer.setLayout(new PickerControlLayout());

        link = createHyperlink(toolkit, controlsContainer);
        link.setVisible(true);

        linkLabel = createLabel(toolkit, controlsContainer);

        browseBtn =
                toolkit.createButton(controlsContainer,
                        "...", SWT.NONE, "browse-button"); //$NON-NLS-1$ //$NON-NLS-2$
        browseBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                value = doBrowse(browseBtn);
                if (value != null) {

                    executeCommand(getSetValueCommand(ed, value));
                }
                controlsContainer.computeSize(-1, -1);
                controlsContainer.layout(true, true);
            }
        });

        if (showClear) {
            clearBtn =
                    toolkit.createButton(controlsContainer,
                            Messages.AbstractPickerControl_clear_button,
                            SWT.NONE);
            clearBtn.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    T currValue = value;
                    value = null;
                    setValue(value);
                    executeCommand(getClearValueCommand(ed, currValue));
                }
            });
        }

        setMinimumTextWidth(calculateMinimumTextWidth(getClearText()));

        updateControls();
    }

    /**
     * Set the browse button tooltip.
     * 
     * @param tooltip
     *            new tooltip or <code>null</code> to clear it.
     */
    public void setBrowseTooltip(String tooltip) {
        if (browseBtn != null && !browseBtn.isDisposed()) {
            browseBtn.setToolTipText(tooltip);
        }
    }

    /**
     * Set the clear button tooltip.
     * 
     * @param tooltip
     *            new tooltip or <code>null</code> to clear it.
     */
    public void setClearTooltip(String tooltip) {
        if (clearBtn != null && !clearBtn.isDisposed()) {
            clearBtn.setToolTipText(tooltip);
        }
    }

    /**
     * Set whether the buttons on this control should be enabled or disabled.
     * 
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (browseBtn != null && !browseBtn.isDisposed()) {
            browseBtn.setEnabled(enabled);
        }
        if (clearBtn != null && !clearBtn.isDisposed()) {
            clearBtn.setEnabled(enabled);
        }
    }

    /**
     * Set the error text in this picker control.
     * 
     * @param text
     */
    public void setErrorText(String text) {

        if (text != null) {

            if (link.getVisible()) {

                link.setText(text);
                link.setImage(null);
            } else if (linkLabel.getVisible()) {

                linkLabel.setText(text);
                linkLabel.setImage(null);
            }
        }
    }

    /**
     * Set whether the hyperlink should be active. If active and the link is
     * clicked then {@link #hyperlinkActivated(Object) hyperlinkActivated} will
     * be called.
     * 
     * @param enabled
     */
    public void setHyperlinkActive(boolean enabled) {
        this.hyperLinkActive = enabled;

        updateControls();
    }

    /**
     * Update the link/hyperlink controls according to curent status
     */
    private void updateControls() {
        /*
         * Hyperlink is active is creator has said they want it and we have a
         * value. Otherwise we switch to a plain label control.
         */
        if (hyperLinkActive && value != null) {

            link.addHyperlinkListener(linkListener);
            /*
             * enabled is true if it is properties view; so link is visible and
             * label must be invisible
             */
            link.setVisible(true);

            linkLabel.setVisible(false);
        } else {

            link.removeHyperlinkListener(linkListener);
            /*
             * enabled is false if it is wizard; so link is invisible and label
             * must be visible
             */
            link.setVisible(false);

            linkLabel.setVisible(true);
        }
    }

    @Override
    public void dispose() {

        adapterFactoryLabelProvider.dispose();
        super.dispose();
    }

    public void setLabelProvider(ILabelProvider provider) {
        this.labelProvider = provider;
    }

    /**
     * Set the value of this picker control.
     * 
     * @param value
     *            the value to set or <code>null</code> to unset the value which
     *            will show the {@link #getClearText() unset} message.
     */
    public void setValue(T value) {

        this.value = value;

        updateControls();

        ILabelProvider labelProvider = getLabelProvider();

        if (null != value) {

            link.setText(labelProvider != null ? labelProvider.getText(value)
                    : value.toString());
            link.setImage(labelProvider != null ? labelProvider.getImage(value)
                    : null);

            linkLabel.setText(labelProvider != null ? labelProvider
                    .getText(value) : value.toString());
            linkLabel.setImage(labelProvider != null ? labelProvider
                    .getImage(value) : null);
        } else {

            link.setText(getClearText());
            link.setImage(null);

            linkLabel.setText(getClearText());
            linkLabel.setImage(null);
        }

        controlsContainer.layout();
    }

    /**
     * Get the current value set in this picker control.
     * 
     * @return value or <code>null</code> if no value set.
     */
    public T getValue() {
        return value;
    }

    /**
     * Get the text to set when this picker control has no
     * {@link #setValue(Object) value} set. Subclasses may override to show a
     * customized message.
     * 
     * @return unset text
     */
    protected String getClearText() {
        return Messages.AbstractPickerControl_noValueSet_label;
    }

    /**
     * Hyperlink has been activated by the user by clicking on it (if
     * {@link #setHyperlinkActive(boolean) setHyperlinkActive} is set to
     * <code>true</code>. The default implementation does nothing, subclasses
     * should override to perform link action.
     * <p>
     * Use {@link #selectInProjectExplorer(Object)} to select the activated
     * object in the project explorer.
     * </p>
     * 
     * @param value
     *            currently selected value in this picker control
     */
    protected void hyperlinkActivated(T value) {
        // Do nothing
    }

    /**
     * Called after the {@link Command} is executed. The default implementation
     * does nothing, subclasses may override to perform an operation after the
     * <code>Command</code> execution.
     * 
     * @param cmd
     */
    protected void postCommandExecution(Command cmd) {
        // Do nothing
    }

    /**
     * Called in response to the browse button selected. This should show a
     * picker to the user and return the picker selection.
     * 
     * @param control
     *            button that was clicked
     * @return picker selection
     */
    protected abstract T doBrowse(Control control);

    /**
     * Get the {@link Command} to update the model with the value selected in
     * this picker control.
     * 
     * @param editingDomain
     * @param value
     * @return <code>Command</code>
     */
    protected abstract Command getSetValueCommand(EditingDomain editingDomain,
            T value);

    /**
     * Get the {@link Command} to clear the model of this value (in response to
     * user selecting the clear button).
     * 
     * @param editingDomain
     * @param value
     *            previously set value before clearing.
     * @return <code>Command</code>
     */
    protected abstract Command getClearValueCommand(
            EditingDomain editingDomain, T value);

    /**
     * @param toolkit
     * @param root
     * @return
     */
    private CLabel createLabel(XpdFormToolkit toolkit, Composite root) {

        CLabel label = toolkit.createCLabel(root, ""); //$NON-NLS-1$
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.widthHint = 270;
        label.setLayoutData(data);
        return label;
    }

    /**
     * @param toolkit
     * @param root
     * @return
     */
    protected ImageHyperlink createHyperlink(XpdFormToolkit toolkit,
            Composite root) {

        ImageHyperlink link =
                toolkit.createImageHyperlink(root,
                        SWT.NONE,
                        "type-name-hyperlink"); //$NON-NLS-1$

        /*
         * Make sure we set some text in hyperlink before initial layout
         * otherwise hyperlink doesn't size text space vertically big enough
         */
        link.setText(""); //$NON-NLS-1$

        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.widthHint = 270;
        link.setLayoutData(data);
        return link;
    }

    /**
     * Select the given element in the Project Explorer view if the view is
     * present.
     * 
     * @param element
     */
    protected void selectInProjectExplorer(Object element) {
        if (element != null) {
            IViewReference[] references =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getViewReferences();

            if (references != null) {
                for (IViewReference ref : references) {
                    if (ref.getId().equals(ProjectExplorer.VIEW_ID)) {
                        IViewPart viewPart = ref.getView(false);

                        if (viewPart instanceof IShowInTarget) {
                            ((IShowInTarget) viewPart).show(new ShowInContext(
                                    element, null));
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Open the given element in it's editor.
     * 
     * @param element
     * @return <code>true</code> if the editor was opened, <code>false</code> if
     *         the editor failed to open or the element has no in-editor
     *         representation.
     */
    protected boolean openInEditor(Object element) {
        if (element != null) {
            return OpenEditorHandler.getInstance().openEditorFor(element);
        }
        return false;
    }

    /**
     * Get the label provider. If one is not provided then the adapter factory
     * label provider will be used.
     * 
     * @return label provider
     */
    private ILabelProvider getLabelProvider() {
        return labelProvider != null ? labelProvider
                : adapterFactoryLabelProvider;
    }

    /**
     * Execute the given command
     * 
     * @param cmd
     */
    private void executeCommand(Command cmd) {
        if (cmd != null && ed != null) {
            ed.getCommandStack().execute(cmd);
            postCommandExecution(cmd);
        }
    }

    /**
     * Nominally the layout will align the browse button at end of text width.
     * However in some circumstances it may be useful set a minimum text width
     * to prevent this (for instance, when you have pickers stacked vertically
     * then the browse buttons will not align with each other they will be
     * staggered according to selected text size.
     * <p>
     * Setting min text width will prevent buttons being aligned left of the
     * given width.
     * 
     * @param minimumTextWidth
     *            the minimumTextWidth to set
     */
    public void setMinimumTextWidth(int minimumTextWidth) {

        if (minimumTextWidth != this.minimumTextWidth) {

            this.minimumTextWidth = minimumTextWidth;
            this.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            this.layout();
        }
    }

    /**
     * Calculates the minimum width required for the picker control's text. Sub
     * classes must override this method if the minimum width given by this
     * method is not big enough to display the text
     * 
     * @param minimumText
     * @return <code>int</code> minimum width for the given text
     */
    public int calculateMinimumTextWidth(String minimumText) {

        if (linkLabel == null || linkLabel.isDisposed()) {

            return 0;
        }

        linkLabel.setRedraw(false);
        String oldText = linkLabel.getText();

        linkLabel.setText(minimumText);
        int minWIdth = linkLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;

        linkLabel.setText(oldText);
        linkLabel.setRedraw(true);
        return minWIdth;
    }

    /**
     * 
     * @return the minimum width
     */
    public int getMinimumTextWidth() {

        return minimumTextWidth;
    }

    /**
     * Custom layout that computes the size of the controls, and lays out the
     * controls for this picker in wizard/properties section.
     * 
     * 
     * @author bharge, aallway
     * @since 18 Sep 2014
     */
    private class PickerControlLayout extends Layout {

        /**
         * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite,
         *      int, int, boolean)
         * 
         * @param composite
         * @param wHint
         * @param hHint
         * @param flushCache
         * @return
         */
        @Override
        protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {

            int prefHgt = 0;
            int minWidth = 0;
            int minWidthForLink = Math.max(100, minimumTextWidth);

            if (link.getVisible()) {

                prefHgt =
                        Math.max(prefHgt,
                                link.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

            } else if (linkLabel.getVisible()) {

                prefHgt =
                        Math.max(prefHgt,
                                linkLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
            }
            minWidth += minWidthForLink;

            if (browseBtn != null) {

                Point sz = browseBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                prefHgt = Math.max(prefHgt, sz.y);
                minWidth += sz.x;
            }

            if (clearBtn != null) {

                Point sz = clearBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                prefHgt = Math.max(prefHgt, sz.y);
                minWidth += sz.x;

                if (browseBtn != null) {

                    minWidth += padding;
                }
            }

            Point prefSize = new Point(Math.max(100, minWidth), prefHgt);

            // System.out.println(String
            // .format("computeSize(%d, %d) = Size(%d,%d)",
            // wHint,
            // hHint,
            // prefSize.x,
            // prefSize.y));
            return prefSize;
        }

        /**
         * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite,
         *      boolean)
         * 
         * @param composite
         * @param flushCache
         */
        @Override
        protected void layout(Composite composite, boolean flushCache) {

            Rectangle bounds = composite.getClientArea();

            // System.out.println("layout::" + bounds);
            //
            // System.out.println("parentbounds::"
            // + composite.getParent().getBounds());
            //
            // System.out.println("minimumTExtWidth: " + minimumTextWidth);

            /***
             * This is the total amount of space required for all the buttons in
             * this context
             */
            int spaceForBtns = 0;

            Point clearBtnSz = new Point(0, 0);
            Point browseBtnSz = new Point(0, 0);
            Point textSize = new Point(0, 0);

            if (clearBtn != null) {

                clearBtnSz = clearBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                spaceForBtns += clearBtnSz.x;
            }

            if (browseBtn != null) {

                browseBtnSz = browseBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                if (spaceForBtns != 0) {

                    spaceForBtns += padding;
                }

                spaceForBtns += browseBtnSz.x;
            }

            if (link.getVisible()) {

                textSize = link.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            } else if (linkLabel.getVisible()) {

                textSize = linkLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            }

            /**
             * This is to specify the minimum empty space to be shown between
             * the label and browse button when there is no text displayed
             */
            int spaceForText = 50;

            if (link.getVisible() && link.getText() != null
                    && link.getText().length() > 0) {

                spaceForText = textSize.x;

            } else if (linkLabel.getVisible() && linkLabel.getText() != null
                    && linkLabel.getText().length() > 0) {

                spaceForText = textSize.x;
            }

            if (spaceForText < minimumTextWidth) {

                spaceForText = minimumTextWidth;
            }

            /***
             * This is the starting location/x coordinate to place the button in
             * this context
             */
            int btnOffset;

            if ((spaceForBtns + spaceForText) > bounds.width) {

                /* Not enough space, right-justify btns and squeeze text */
                btnOffset = bounds.width - spaceForBtns;

                spaceForText = bounds.width - spaceForBtns;

            } else {
                /*
                 * Enough space for text and buttons, put buttons at end of
                 * text.
                 */
                btnOffset = spaceForText;
            }

            if (browseBtn != null) {

                browseBtn.setBounds(btnOffset, 0, browseBtnSz.x, browseBtnSz.y);
                btnOffset += browseBtnSz.x;

                btnOffset += padding;
            }

            if (clearBtn != null) {

                clearBtn.setBounds(btnOffset, 0, clearBtnSz.x, clearBtnSz.y);
                btnOffset += clearBtnSz.x;
            }

            if (link.getVisible()) {

                link.setBounds(0, 0, spaceForText, bounds.height);
            } else if (linkLabel.getVisible()) {

                linkLabel.setBounds(0, 0, spaceForText, bounds.height);
            }
        }
    }
}
