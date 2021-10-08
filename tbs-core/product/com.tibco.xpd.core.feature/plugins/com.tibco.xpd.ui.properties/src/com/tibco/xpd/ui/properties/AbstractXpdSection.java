/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.util.ShowViewUtil;
import com.tibco.xpd.resources.util.ElapsedTimerHelper;
import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;

/**
 * This is the abstract class that defines the basic structure of the properties
 * section that will be used by both the EObject directly and the model adapters
 * in the process widget.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractXpdSection extends AbstractPropertySection
        implements CommandProvider, DisposeListener {

    public AbstractXpdSection() {
        super();
        ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                + ": Construct @");
    }

    /**
     * Short width to hint text controls. Without this hint the minimum width is
     * set to the content when the field is created, which could be very large.
     */
    public static final int LAYOUT_DATA_SHORT_WIDTH_HINT = 20;

    private boolean ignoreEvents;

    private Composite parent;

    private boolean created;

    private TabbedPropertySheetPage sheetPage;

    /**
     * Default minimum height
     */
    private int minimumHeight = 80;

    /**
     * Default minimum height
     */
    private boolean shouldUseExtraSpace = true;

    private XpdSectionInputContainerProvider xpdSectionContainerProvider;

    /**
     * Indicate whether this section should be shown in the creation wizard -
     * set to <code>true</code> by default.
     * 
     * @since 3.0
     */
    private boolean showInWizard = true;

    /**
     * XPD-1140: Special verify listener that ignores all key presses except
     * navigation - for use by {@link #disableControlsForReadOnlyInput(Control)}
     */
    private VerifyListener rejectInputVerifyListener = new VerifyListener() {

        public void verifyText(VerifyEvent e) {
            /* Reject all but cursor keys. */
            if (!isIgnoreEvents()) {
                switch (e.keyCode) {
                case SWT.PAGE_UP:
                case SWT.PAGE_DOWN:
                case SWT.HOME:
                case SWT.END:
                case SWT.ARROW_LEFT:
                case SWT.ARROW_RIGHT:
                case SWT.ARROW_UP:
                case SWT.ARROW_DOWN:
                    e.doit = true;
                    break;
                default:
                    e.doit = false;
                    break;
                }
            }
            return;
        }
    };

    /** Main control of the section. See: {@link #getControl()} */
    private Control control;

    /**
     * Parent container type enum. This indicates whether this section is
     * contained in a properties view or a wizard.
     */
    public enum ContainerType {
        PROPERTYVIEW, WIZARD;
    }

    /**
     * Set whether this property section should be shown in a new creation
     * wizard. By default this section will be shown in the wizard.
     * 
     * @param show
     *            <code>true</code> to show the section, <code>false</code>
     *            otherwise.
     * 
     * @since 3.0
     */
    public void setShowInWizard(boolean show) {
        this.showInWizard = show;
    }

    /**
     * Indicates whether this property section can be shown in the new creation
     * wizard. By default the property section will show in the wizard. Use
     * {@link #setShowInWizard(boolean) setShowInWizard} to change this value.
     * 
     * @return <code>true</code> if this section should be shown in the new
     *         wizard, <code>false</code> otherwise.
     * 
     * @since 3.0
     */
    public boolean canShowInWizard() {
        return showInWizard;
    }

    /**
     * Extended the structured selection class to allow us to force the
     * propertysheet page to update the tabs on demand.
     */
    private class XpdSectionStructuredSelection extends StructuredSelection {

        private boolean alwaysNotEqual = false;

        /**
         * @param alwaysNotEqual
         *            the alwaysNotEqual to set
         */
        public void setAlwaysNotEqual(boolean alwaysNotEqual) {
            this.alwaysNotEqual = alwaysNotEqual;
        }

        public XpdSectionStructuredSelection(IStructuredSelection selection) {
            // Copy the contents of the selection parameter into this selection
            super(selection.toList());
        }

        @Override
        public boolean equals(Object o) {
            /*
             * If the object being compared is the original selection then
             * return false as we don't want it to match. This will make sure
             * that when the compare is between this object and the original
             * selection then it returns false, but any other tests are carried
             * out properly
             */
            if (alwaysNotEqual) {
                return false;
            }

            return super.equals(o);
        }
    }

    /**
     * Get the tabbed property sheet page that this section belongs to
     * 
     * @return <code>TabbedPropertySheetPage</code> if one is available,
     *         <b>null</b> otherwise.
     */
    protected TabbedPropertySheetPage getPropertySheetPage() {
        return sheetPage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
     */
    public abstract EditingDomain getEditingDomain();

    /**
     * Check if this section has valid input.
     * 
     * @return <code>true</code> if input is valid.
     */
    protected abstract boolean gotInput();

    /**
     * Set the input for this section
     * 
     * @param items
     */
    public abstract void setInput(Collection<?> items);

    /**
     * Refresh the section. Implementers should be careful not to cause the
     * section to be refreshed (for instance by calling {@link #refreshTabs()}.
     * If you need to refresh the tabs you should find another place to do this,
     * for instance by overriding
     * {@link AbstractEObjectSection#notifyChanged(org.eclipse.emf.common.notify.Notification)}
     * .
     */
    protected abstract void doRefresh();

    /**
     * Create the controls for the section
     * 
     * @param parent
     * @param toolkit
     * @return The root control created
     */
    protected abstract Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit);

    /**
     * Get the command to update the model with the change in the property
     * section.
     * <p>
     * For managed combo controls this method will be called twice - once when a
     * selection is made in the combo and the other when it is modified. This
     * should be taken into consideration when a command is created to change a
     * model. It is recommended that the command is only generated when the
     * combo value is modified.
     * </p>
     * 
     * @param obj
     * @return
     */
    protected abstract Command doGetCommand(Object obj);

    /**
     * Get the container of the section input
     * 
     * @return Container of the input
     */
    protected abstract EObject doGetInputContainer();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.CommandProvider#getCommand(java.lang.Object)
     */
    public Command getCommand(Object obj) {
        if (isIgnoreEvents() || !gotInput()) {
            return null;
        }
        return doGetCommand(obj);
    }

    @Override
    public int getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * Set the minimum height of the section
     * 
     * @param height
     */
    public void setMinimumHeight(int height) {
        minimumHeight = height;
    }

    @Override
    public boolean shouldUseExtraSpace() {
        return shouldUseExtraSpace;
    }

    /**
     * Set use extra space
     * 
     * @param useExtraSpace
     */
    public void setShouldUseExtraSpace(boolean useExtraSpace) {
        shouldUseExtraSpace = useExtraSpace;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt
     * .events.DisposeEvent)
     */
    public void widgetDisposed(DisposeEvent e) {
        dispose();
    }

    /**
     * Return if event triggers should be ignored. Should not normally need to
     * override this method, but if you do be aware of it's use within
     * {@link #refreshTabs()}.
     * 
     * @return
     */
    public boolean isIgnoreEvents() {
        return ignoreEvents;
    }

    /**
     * Set whether events should be ignored.
     * 
     * @param ignore
     *            <code>true</code> if events should be ignored,
     *            <code>false</code> otherwise.
     * 
     * @since 3.0
     */
    protected void setIgnoreEvents(boolean ignore) {
        ignoreEvents = ignore;
    }

    @Override
    public final void createControls(Composite parent,
            TabbedPropertySheetPage aTabbedPropertySheetPage) {

        super.createControls(parent, aTabbedPropertySheetPage);

        try {
            this.sheetPage = aTabbedPropertySheetPage;
            ignoreEvents = true;
            control =
                    createControls(parent, new XpdPropertiesFormToolkit(
                            getWidgetFactory()));
        } finally {
            ignoreEvents = false;
        }
    }

    /**
     * Returns main control of the section.
     * 
     * @return main control of the section.
     */
    public Control getControl() {
        return control;
    }

    /**
     * Create the controls for this section. This will register the dispose
     * listener with the parent composite and then delegate to the abstract
     * method <code>doCreateControls</code> to create the control. If the
     * control is created then setCreated will be set to true to indicate that a
     * control has been created and refreshes to this section will be allowed
     * 
     * @param parentComposite
     * @param toolkit
     * @return
     */
    static int indent = 0;

    public final Control createControls(Composite parentComposite,
            XpdFormToolkit toolkit) {
        String identStr = "";

        for (int i = 0; i < indent; i++) {
            identStr += " ";
        }
        ElapsedTimerHelper.timedMsg(identStr + this.getClass().getSimpleName()
                + ": createControls()"); //$NON-NLS-1$

        indent += 4;

        parent = parentComposite;
        parent.addDisposeListener(this);
        Control control = doCreateControls(parent, toolkit);
        // If control is created then setCreated
        setCreated(control != null);

        indent -= 4;

        ElapsedTimerHelper.timedMsg(identStr + this.getClass().getSimpleName()
                + ": <== createControls()"); //$NON-NLS-1$

        return control;
    }

    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                + ": setInput()"); //$NON-NLS-1$
        super.setInput(part, selection);
        if (selection instanceof IStructuredSelection) {
            setInput(((IStructuredSelection) selection).toList());
        } else {
            setInput(Collections.EMPTY_LIST);
        }
    }

    @Override
    public void refresh() {
        if (isIgnoreEvents()) {
            // Should NOT be allowed to refreshTabs() whilst in the middle of a
            // refresh() or createControls(). This causes refresh() recursion
            // and you could either get a stack overflow (infinite recursion) OR
            // worse still, because it isn't obvious, you will cause
            // ignoreEvents to be unset and after that just setiing text on an
            // edit box will cause changes to model (so you get lots of undo's
            // in stack when the user hasn't actully done anything.
            return;
        }

        if (isCreated()) {
            try {
                ignoreEvents = true;
                ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                        + ": refresh()"); //$NON-NLS-1$
                doRefresh();
                ElapsedTimerHelper.timedMsg(this.getClass().getSimpleName()
                        + ": <== refresh()"); //$NON-NLS-1$
            } finally {
                ignoreEvents = false;
            }
        }
    }

    @Override
    public void dispose() {
        if (parent != null && !parent.isDisposed()) {
            parent.removeDisposeListener(this);
        }

        setCreated(false);
    }

    /**
     * Get the container type of this section. This could be a properties view
     * or a wizard.
     * 
     * @return ContainerType value:<br/>
     *         <ul>
     *         <li><code>PROPERTYVIEW</code> - container is a property view</li>
     *         <li><code>WIZARD</code> - container is a wizard</li>
     *         </ul>
     */
    public ContainerType getSectionContainerType() {
        // Default to property view
        ContainerType type = ContainerType.PROPERTYVIEW;

        // If the section container is a wizard then set
        // parent container to wizard
        if (getXpdSectionContainerProvider() != null
                && getXpdSectionContainerProvider() instanceof IWizard) {
            type = ContainerType.WIZARD;
        }

        return type;
    }

    /**
     * Get the section's input container provider. For a properties view it will
     * be the input itself but for sections that are used in a wizard the wizard
     * will have to provide the container of the input (use
     * <code>setXpdSectionContainerProvider</code> to register the provider).
     * This is because the input will be a new data item added and will not have
     * been linked to an xpdl2 resource yet (this only happens when the wizard
     * completes).
     * 
     * @return
     */
    public XpdSectionInputContainerProvider getXpdSectionContainerProvider() {
        return xpdSectionContainerProvider;
    }

    /**
     * Set the section container. If this is not used
     * 
     * @param container
     */
    public void setXpdSectionContainerProvider(
            XpdSectionInputContainerProvider container) {
        this.xpdSectionContainerProvider = container;
    }

    /**
     * If property sheet is owned by a creation wizard then set the can finish
     * status.
     * 
     */
    public void setCanFinish(boolean canFinish, String cantFinishMessage) {

        if (xpdSectionContainerProvider instanceof IWizard) {
            xpdSectionContainerProvider.setCanFinish(canFinish,
                    cantFinishMessage);
        }
    }

    /**
     * Check if the composite control has been created
     * 
     * @return
     */
    protected boolean isCreated() {
        return created;
    }

    /**
     * Set whether the controls have been created
     * 
     * @param created
     */
    protected void setCreated(boolean created) {
        this.created = created;
        if (created && gotInput()) {
            refresh();
        }
    }

    /**
     * Get Editingdomain for the EObject
     * 
     * @param eo
     * @return Editingdomain or null if not found
     */
    protected EditingDomain getEditingDomain(EObject eo) {
        if (eo == null) {
            return null;
        }
        IEditingDomainProvider ep = null;
        Resource resource = eo.eResource();
        if (resource != null) {
            ep =
                    (IEditingDomainProvider) EcoreUtil.getExistingAdapter(eo
                            .eResource(), IEditingDomainProvider.class);
        }
        return ep != null ? ep.getEditingDomain() : null;
    }

    /**
     * Update text control with given text and preserve caret position
     * 
     * @param text
     * @param string
     */
    protected void updateText(Text text, String string) {
        if (text == null || text.isDisposed()) {
            return;
        }
        string = (string != null ? string : ""); //$NON-NLS-1$
        int position = text.getCaretPosition();
        text.setText(string);
        position = Math.min(position, string.length() + 1);
        text.setSelection(position);
    }

    /**
     * Update styled text control with given text and preserve caret position
     * 
     * @param styled
     *            text
     * @param string
     */
    protected void updateText(StyledText styledText, String string) {
        if (styledText == null || styledText.isDisposed()) {
            return;
        }
        string = (string != null ? string : ""); //$NON-NLS-1$
        int position = styledText.getCaretOffset();
        styledText.setText(string);
        position = Math.min(position, string.length() + 1);
        styledText.setSelection(position);
    }

    /**
     * Updates CCombo control.
     * 
     * @param combo
     *            CCombo object to be updated.
     * @return true if the name was correctly selected.
     */
    protected boolean updateCCombo(CCombo combo, String selectedString) {
        if (combo.isDisposed()) {
            return false;
        }
        int index = selectedString != null ? combo.indexOf(selectedString) : -1;
        if (index != -1) {
            combo.select(index);
            return true;
        }
        combo.setText(""); //$NON-NLS-1$
        return false;

    }

    /**
     * Manage the text control. This will use the delayed event feature for the
     * text control. When a change occurs in this control (ultimately) the
     * <code>doGetCommand</code> will be called to update the model.
     * 
     * @param text
     */
    protected void manageControl(Text text) {
        new CommandTextFieldHandler(text, this);
    }

    /**
     * Manage the text control. This will use the delayed event feature for the
     * text control. When a change occurs in this control (ultimately) the
     * <code>doGetCommand</code> will be called to update the model.
     * <p>
     * <b>This version does not do timed-updates of model, only updates model
     * when control looses focus.</b> <b>When Using on deactivate only you MUST
     * check for change when requested for commands - you may get asked for
     * command even when the field has not changed.
     * 
     * @param text
     */
    protected void manageControlUpdateOnDeactivate(Text text) {
        CommandTextFieldHandler hdlr = new CommandTextFieldHandler(text, this);
        hdlr.setModifyOnDeactivateOnly(true);
    }

    /**
     * Manage the button control. This will use the delayed event feature for
     * the button control. When a change occurs in this control (ultimately) the
     * <code>doGetCommand</code> will be called to update the model.
     * 
     * @param button
     */
    protected void manageControl(Button button) {
        new CommandRadioButtonFieldHandler(button, this);
    }

    /**
     * Manage the table control. This will use the delayed event feature for the
     * table control. When a change occurs in this control (ultimately) the
     * <code>doGetCommand</code> will be called to update the model.
     * 
     * @param destinations
     */
    protected void manageControl(Table destinations) {
        new CommandCheckTableFieldHandler(destinations, this);
    }

    /**
     * Manage the combo control. This will use the delayed event feature for the
     * combo control. When a change occurs in this control (ultimately) the
     * <code>{@link #doGetCommand(Object)}</code> will be called to update the
     * model.
     * <p>
     * NOTE: The <code>{@link #doGetCommand(Object)}</code> will be called
     * during selection and modify combo events so this has to be taken into
     * consideration when returning commands.
     * </p>
     * 
     * @param combo
     */
    protected void manageControl(CCombo combo) {
        new CommandCComboFieldHandler(combo, this);
    }

    /**
     * Manage the form text control. This will use the delayed event feature for
     * the form text control. When a change occurs in this control (ultimately)
     * the <code>doGetCommand</code> will be called to update the model.
     * 
     * @param formText
     */
    protected void manageControl(FormText formText) {
        new CommandHyperlinkHandler(formText, this);
    }

    /**
     * Manage the hyperlink control. This will use the delayed event feature for
     * the hyperlink control. When a change occurs in this control (ultimately)
     * the <code>doGetCommand</code> will be called to update the model.
     * 
     * @param hyperlink
     */
    protected void manageControl(Hyperlink hyperlink) {
        new CommandHyperlinkHandler(hyperlink, this);
    }

    /**
     * Manage the hyperlink control. This will use the delayed event feature for
     * the hyperlink control. When a change occurs in this control (ultimately)
     * the <code>doGetCommand</code> will be called to update the model.
     * 
     * @param spinner
     */
    protected void manageControl(Spinner spinner) {
        CommandSpinnerHandler spnr = new CommandSpinnerHandler(spinner, this);

        // Saket SCF-102: Make all spinners behave as modify-on-deactivate
        // because it is unlikely that model update & validate mid-edit will
        // ever be required (and timed updates can cause some issues too).
        spnr.setModifyOnDeactivateOnly(true);
    }

    /**
     * Manage the Styled Text control. When a change occurs in this control
     * (ultimately) the <code>doGetCommand</code> will be called to update the
     * model.
     * <p>
     * <b>This version does not do timed-updates of model, only updates model
     * when control looses focus.</b> <b>When Using on deactivate only you MUST
     * check for change when requested for commands - you may get asked for
     * command even when the field has not changed.
     * 
     * @param styled
     */
    protected void manageControlUpdateOnDeactivate(StyledText styled) {
        CommandStyledTextFieldHandler hdlr =
                new CommandStyledTextFieldHandler(styled, this);
        hdlr.setModifyOnDeactivateOnly(true);
    }

    /**
     * Manage the Styled Text control. This will use the delayed event feature
     * for the Styled Text control. When a change occurs in this control
     * (ultimately) the <code>doGetCommand</code> will be called to update the
     * model.
     * 
     * @param styledText
     */
    protected void manageControl(StyledText styledText) {
        CommandStyledTextFieldHandler hdlr =
                new CommandStyledTextFieldHandler(styledText, this);
        hdlr.setModifyOnDeactivateOnly(false);
    }

    /**
     * Get the input container of the input. If this section is contained within
     * a wizard and the wizard provides a container provider (set with
     * <code>SetXpdSectionContainerProvider</code>) then this will be used to
     * get the container of the input. Otherwise,
     * <code>doGetInputContainer</code> will be called for the subclass to
     * provide the container from the input.
     * 
     * @return the container of the input
     */
    protected EObject getInputContainer() {
        EObject parent = null;

        // If this is the property view then get the eContainer of the EObject
        // otherwise, if a wizard then get the input container from the wizard
        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            parent = doGetInputContainer();
        } else if (getSectionContainerType() == ContainerType.WIZARD) {
            if (getXpdSectionContainerProvider() != null)
                parent = getXpdSectionContainerProvider().getInputContainer();
        }

        return parent;
    }

    /**
     * Get the workbench site
     * 
     * @return
     */
    protected IWorkbenchSite getSite() {
        if (sheetPage != null) {
            return sheetPage.getSite();
        }
        return null;
    }

    /**
     * Refresh the property sheet page to update the tabs.
     * 
     */
    protected void refreshTabs() {
        // System.out.println("<<<< REFRESH TABS >>>>");
        if (sheetPage != null) {
            if (getSelection() instanceof IStructuredSelection) {
                // Standard selectionChanged() will not force refresh to update
                // tabs because it chaecks whether
                // currentSecection.equals(newSelection).
                //
                // So if we haven't already done so, wrap up the selection in an
                // XpdSectionStructuredSelection which can be force to return
                // NOT equal temporarily here.

                XpdSectionStructuredSelection xpdSelection;
                ISelection sel = getSelection();
                if (sel instanceof XpdSectionStructuredSelection) {
                    xpdSelection = (XpdSectionStructuredSelection) sel;
                } else {
                    xpdSelection =
                            new XpdSectionStructuredSelection(
                                    (IStructuredSelection) sel);
                }

                try {
                    // TabbedPropertySheetpage.selectionChanged() will only
                    // refresh the tabs IF the new selection boils down to
                    // something different so tell our 'special' selection type
                    // to return false from equals until after the
                    // slectionChanged()
                    xpdSelection.setAlwaysNotEqual(true);
                    sheetPage.selectionChanged(getPart(), xpdSelection);
                } finally {
                    xpdSelection.setAlwaysNotEqual(false);
                }

            } else {
                // This is not a structured selection so unset and then set back
                // the given selection
                // to force an update on the tabs
                sheetPage
                        .selectionChanged(getPart(), StructuredSelection.EMPTY);
                sheetPage.selectionChanged(getPart(), getSelection());
            }
        }
    }

    /**
     * Show the selected tab
     * 
     * @param name
     *            Name of the tab
     * 
     * @deprecated Use {@link #showPropertyTab(String)} as that works using the
     *             property tab id not it's translatable label.
     */
    @Deprecated
    protected void showTab(String name) {
        XpdPropertiesUtil._showTab(name);
    }

    /**
     * Activate the property tab identified by it's id (not it's translatable
     * label like {@link #_showTab(String)}
     * <p>
     * The control returned is the root control of the whole property sheet
     * tabbedPropertyComposite control NOT the individual tab.
     * 
     * @param propertyTabId
     * 
     * @return the root control of the whole property sheet or <code>null</code>
     *         if the property tab was not found.
     */
    protected Control showPropertyTab(String propertyTabId) {
        return ShowViewUtil.showPropertyTab(propertyTabId);
    }

    /**
     * Force a re-layout of the section.
     * <p>
     * <b>Use this method with caution</b> - you should make best efforts to
     * ensure that you only force a layout if absolutely necessary (i.e. don't
     * do it on every doRefresh() willy-nilly. Use it if you need to relayout
     * after resizing / hiding a child control BUT only when the control
     * changes, not every time thru a refresh!).
     */
    public void forceLayout() {
        //       System.out.println("<<<< AbstractXpdSection: FORCE LAYOUT >>>>"); //$NON-NLS-1$

        parent.layout(true, true);

        if (getSectionContainerType() == ContainerType.PROPERTYVIEW) {
            if (getPropertySheetPage() != null
                    && getPropertySheetPage().getControl() != null) {
                Control propPageControl = getPropertySheetPage().getControl();

                //
                // Force a complete re-layout by sizing main tab control and
                // back again.
                Point sz = propPageControl.getSize();
                propPageControl.setRedraw(false);
                propPageControl.setSize(sz.x, sz.y + 1);
                propPageControl.setSize(sz.x, sz.y);
                propPageControl.setRedraw(true);
            }

        } else {
            if (parent != null && parent.getShell() != null
                    && !parent.getShell().isDisposed()) {
                Shell wizControl = parent.getShell();
                wizControl.layout(true, true);

            }
        }

        return;
    }

    /**
     * Enable / Disable the given control and it's descendent children (if it
     * has any).
     * 
     * @param c
     * @param enabled
     * @param noDisableComposite
     *            Do not disable controls that are instanceof Composite. When a
     *            composite is disabled then all controls within are disabled
     *            (regardless of their individual enablement - so if you do
     *            disable composite and all its descendents and then re-enable,
     *            say, a button then the button will look enabled BUT still be
     *            disabled. Also, scrolled composites would have not allow the
     *            scroll disabled.
     * @deprecated This method is deprecated because the noDisableComposite
     *             could not be made to function correctly (because some
     *             controls such as CLabel and possibly others are based on
     *             Composite so we couldn't necessarily tell the difference). As
     *             an alternative, if you wish to disable a group of controls
     *             then call setEnabled(Control, bEnabled), if you then wish to
     *             re-enabled a control (such as a button) within that, then
     *             call setEnabled(ctrl, true) for it afterwards - this is
     *             because setEnabled(ctrl, true) will ensure that all
     *             composites in parent hierarchy are also enabled
     */
    @Deprecated
    public void setEnabled(Control c, boolean enabled,
            boolean noDisableComposite) {
        // Set enablement state if we
        // (a) are allowed to disable composites OR
        // (b) we are setting to enabled (always enable composites regardless of
        // flag) OR
        // (c) It's not a composite
        if (!noDisableComposite || enabled
                || !(c.getClass() == Composite.class)) {
            c.setEnabled(enabled);
        }

        if (c instanceof CLabel) {
            // CLabel doesn't grey itself like normal Label so have to do it by
            // hand.
            if (!enabled) {
                c.setForeground(XpdResourcesPlugin.getStandardDisplay()
                        .getSystemColor(SWT.COLOR_GRAY));
            } else {
                c.setForeground(XpdResourcesPlugin.getStandardDisplay()
                        .getSystemColor(SWT.COLOR_LIST_FOREGROUND));
            }
        }

        if (c instanceof Composite) {
            Control[] children = ((Composite) c).getChildren();
            for (int i = 0; i < children.length; i++) {
                setEnabled(children[i], enabled, noDisableComposite);
            }
        }

        return;
    }

    /**
     * Internal recursive set enabled method for setEnabled(ctrl, boolean)
     * below.
     * 
     * @param c
     * @param enabled
     */
    private void internalSetEnabled(Control c, boolean enabled) {
        c.setEnabled(enabled);

        if (c instanceof CLabel) {
            // CLabel doesn't grey itself like normal Label so have to do it by
            // hand.
            if (!enabled) {
                c.setForeground(XpdResourcesPlugin.getStandardDisplay()
                        .getSystemColor(SWT.COLOR_GRAY));
            } else {
                c.setForeground(XpdResourcesPlugin.getStandardDisplay()
                        .getSystemColor(SWT.COLOR_LIST_FOREGROUND));
            }
        }

        if (c instanceof Composite) {
            Control[] children = ((Composite) c).getChildren();
            for (int i = 0; i < children.length; i++) {
                internalSetEnabled(children[i], enabled);
            }
        }

        return;
    }

    /**
     * Enable / Disable the given control and it's descendent children (if it
     * has any).
     * <p>
     * <b>NOTE:</b> When this method is called then all controls INCLUDING
     * composites are disabled. In SWT, if an enabled control has a disabled
     * ascendent then it will appear enabled BUT will actually be disabled.
     * Therefore the following code would not work as anticipated...
     * 
     * <pre>
     * setEnabled(rootParent, false);
     * childButton.setEnabled(true);
     * </pre>
     * 
     * The button would appear enabled but would not respoind to mouse/keyboard.
     * </p>
     * <p>
     * Therefore if you wish, for instance, to disable a complete set of
     * controls but then reenable an individual control within that set, then
     * use this method to reenable the child control. This will support the
     * anticipated behaviour because when enabled=true, all ascendents are
     * enabled.
     * 
     * @param c
     * @param enabled
     */
    public void setEnabled(Control c, boolean enabled) {
        //
        // If we are enabling, then ensure that all ascendents are enabled.
        if (enabled) {
            Composite p = c.getParent();
            while (p != null) {
                p.setEnabled(true);
                p = p.getParent();
            }
        }

        //
        // Then enabled / disabled this control and all it's descendents.
        internalSetEnabled(c, enabled);

        return;
    }

    /**
     * Set the specified icon on the specified CLabel AND also set the specified
     * text as the tooltip text.
     * <p>
     * Please note that to unset an already set image on the CLabel, pass
     * <code>null</code> for 'img'.
     * <p>
     * 
     * @param cLabel
     * @param set
     * @param tooltipText
     */
    public void setCLabelIcon(CLabel cLabel, Image img, String tooltipText) {

        cLabel.setToolTipText(tooltipText);

        if (img != null) {

            if (null == cLabel.getImage() || !img.equals(cLabel.getImage())) {

                cLabel.setImage(img);

                cLabel.getParent().layout(true);

            }

        } else {

            if (cLabel.getImage() != null) {

                cLabel.setImage(null);

                cLabel.getParent().layout(true);
            }
        }
    }

    /**
     * Nominally used for ensuring alignment of separate grid layout's columns
     * you need to be the same width.
     * <p>
     * Given a set of controls - find the one with the largest PREFERRED width
     * and a new GridData(largest, SWT.DEFAULT) on each.
     * <p>
     * You can also optionally provide manual adjustment factors for one or more
     * controls. This can be used (for instance) to make an allowance for a
     * decorated field in the next column with has a leading icon but you wish
     * to align on the edit box after that. Adding a icon-width positive
     * adjustment in this case will cause the icon to be included in the 'width'
     * of the label control in the columnb to left that you pass to this
     * function.
     * 
     * 
     * @param controls
     * @param adjustments
     * @since 3.2
     */
    public void setSameGridDataWidth(Collection<Control> controls,
            Map<Control, Integer> adjustments) {

        int largest = 0;

        for (Control c : controls) {
            Point sz = c.computeSize(SWT.DEFAULT, SWT.DEFAULT);

            if (adjustments != null) {
                Integer adjust = adjustments.get(c);
                if (adjust != null) {
                    sz.x += adjust;
                }
            }

            if (sz.x > largest) {
                largest = sz.x;
            }
        }

        for (Control c : controls) {
            int width = largest;

            if (adjustments != null) {
                Integer adjust = adjustments.get(c);
                if (adjust != null) {
                    width -= adjust;
                }
            }

            c.setLayoutData(new GridData(width, SWT.DEFAULT));
        }
        return;
    }

    /**
     * Given a list of strings, return the width of the widest (in pixels)
     * 
     * @param strings
     * @return Widest (in pixels) string.
     */
    public int getTextWidth(String[] strings) {
        int maxWidth = 0;

        Shell shell = new Shell();
        Text text = new Text(shell, SWT.SINGLE);

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];

            text.setText(string);

            Point sz = text.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
            if (sz.x > maxWidth) {
                maxWidth = sz.x;
            }
        }

        text.dispose();
        shell.dispose();

        return maxWidth;
    }

    /**
     * XPD-1128: When editing a read only resource then disable editing parts of
     * controls.
     * <p>
     * Note that this differs from
     * {@link AbstractXpdSection#setEnabled(Control, boolean)} in that if
     * possible the controls should <b>not</b> be disabled (so that the user can
     * view / scroll etc as normal. But whereever possible the user should be
     * prevented from attempting to make change.
     * <p>
     * Note also that this is further backed-up by
     * {@link AbstractTransactionalSection#getCommand(Object)} which will (a)
     * refuse to build a command and (b) perform refresh() so that in the case
     * of things like checkbox lists the section can put the control back how it
     * should be - this only works for controls that have been passed to
     * manageControl() not controls that section has added it's own action
     * listeners to. That is why we need this method also to disable controls
     * that we know about.
     * <p>
     * Recursive!
     * 
     * @param container
     */
    protected void disableControlsForReadOnlyInput(Control control) {

        if (control == null || control.isDisposed()) {
            return;

        } else if (control instanceof Button) {
            control.setEnabled(false);

        } else if (control instanceof Text) {
            Text text = (Text) control;

            /* Remove and re-add so we don't end up with loads in there. */
            text.removeVerifyListener(rejectInputVerifyListener);
            text.addVerifyListener(rejectInputVerifyListener);

        } else if (control instanceof StyledText) {
            StyledText text = (StyledText) control;

            /* Remove and re-add so we don't end up with loads in there. */
            text.removeVerifyListener(rejectInputVerifyListener);
            text.addVerifyListener(rejectInputVerifyListener);

        } else if (control instanceof ToolBar) {
            ToolBar toolbar = (ToolBar) control;
            toolbar.setEnabled(false);

        } else if (control instanceof CCombo) {
            CCombo combo = (CCombo) control;

            /*
             * Preventing getCommand() (see header comment) will prevent user
             * effecting any change, however we should also do our best to make
             * it look like there nothing can be changed.
             */
            combo.setVisibleItemCount(0);
            combo.setEditable(false);
        }
        /*
         * Finally, if it's a composite then recurs to do children
         */
        else if (control instanceof Composite) {
            /* For composite recurs and do children. */
            Control[] children = ((Composite) control).getChildren();
            if (children != null) {
                for (Control child : children) {
                    disableControlsForReadOnlyInput(child);
                }
            }

        }

        return;
    }

    /**
     * XPD-1140:
     * 
     * @return The composite parent of the section controls.
     */
    public final Composite getControlsContainer() {
        return parent;
    }
}
