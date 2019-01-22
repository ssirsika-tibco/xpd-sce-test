/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

/**
 * The toolkit is mainly responsible for creating SWT controls adopted to work
 * with various eclipse UI frameworks and help to keep the uniform look and feel
 * across XPD product. The concrete implementation of this class may serve as a
 * toolkits for creating controls for wizards, property pages, etc.
 * 
 * This is a base implementation which can be used as it is or my serve as a
 * base class to other toolkits.
 * <p>
 * <i>Created: 23 Mar 2009</i>
 * </p>
 * 
 * @since 3.2
 * @author Jan Arciuchiewicz
 * 
 * @see com.tibco.xpd.ui.properties.XpdWizardToolkit
 * @see com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit
 * @see org.eclipse.ui.forms.widgets.FormToolkit
 * 
 * 
 */
public class BaseXpdToolkit implements XpdToolkit {

    /** {@inheritDoc} */
    public void adapt(Composite composite) {
        // do nothing
    }

    /** {@inheritDoc} */
    public void adapt(Control control, boolean trackFocus, boolean trackKeyboard) {
        // do nothing
    }

    /** {@inheritDoc} */
    public Button createButton(Composite parent, String text, int style,
            String instrumentationName) {
        Button button = new Button(parent, style);
        button.setText(text);
        button.setData(INSTRUMENTATION_DATA_NAME, "button" //$NON-NLS-1$
                + instrumentationName);
        adapt(button, true, true);
        return button;
    }

    /** {@inheritDoc} */
    public CCombo createCCombo(Composite parent, int comboStyle,
            String instrumentationName) {
        CCombo combo = new CCombo(parent, comboStyle);
        adapt(combo, true, false);
        // Bugzilla 145837 - workaround for no borders on Windows XP
        if ((comboStyle & SWT.BORDER) == SWT.BORDER) {
            combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        }
        combo.setData(INSTRUMENTATION_DATA_NAME, "combo" + instrumentationName); //$NON-NLS-1$
        adapt(combo, true, true);
        return combo;
    }

    /** {@inheritDoc} */
    public CCombo createCCombo(Composite parent, String instrumentationName) {
        return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY,
                instrumentationName);
    }

    /** {@inheritDoc} */
    public CLabel createCLabel(Composite parent, String text) {
        return createCLabel(parent, text, SWT.NONE);
    }

    /** {@inheritDoc} */
    public CLabel createCLabel(Composite parent, String text, int style) {
        final CLabel label = new CLabel(parent, style);
        label.setBackground(parent.getBackground());
        label.setText(text);
        return label;
    }

    /** {@inheritDoc} */
    public Composite createComposite(Composite parent) {
        return createComposite(parent, SWT.NONE);
    }

    /** {@inheritDoc} */
    public Composite createComposite(Composite parent, int style) {
        Composite composite = new Composite(parent, style);
        adapt(composite);
        return composite;
    }

    /** {@inheritDoc} */
    public Composite createCompositeSeparator(Composite parent) {
        return createComposite(parent);
    }

    /** {@inheritDoc} */
    public Group createGroup(Composite parent, String text) {
        Group group = new Group(parent, SWT.NULL);
        group.setText(text);
        return group;
    }

    /** {@inheritDoc} */
    public Label createLabel(Composite parent, String text) {
        return createLabel(parent, text, SWT.NONE);
    }

    /** {@inheritDoc} */
    public Label createLabel(Composite parent, String text, int style) {
        Label label = new Label(parent, style);
        label.setText(text);
        adapt(label, false, false);
        return label;
    }

    /** {@inheritDoc} */
    public List createList(Composite parent, int style,
            String instrumentationName) {
        List list = new org.eclipse.swt.widgets.List(parent, style);
        adapt(list, true, true);
        list.setData(INSTRUMENTATION_DATA_NAME, "list" + instrumentationName); //$NON-NLS-1$
        return list;

    }

    /** {@inheritDoc} */
    public ScrolledPageBook createPageBook(Composite parent, int style) {
        ScrolledPageBook book = new ScrolledPageBook(parent, style);
        adapt(book, true, true);
        return book;
    }

    /** {@inheritDoc} */
    public Composite createPlainComposite(Composite parent, int style) {
        return createComposite(parent, style);
    }

    /** {@inheritDoc} */
    public ScrolledComposite createScrolledComposite(Composite parent, int style) {
        ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
                style);
        adapt(scrolledComposite);
        return scrolledComposite;
    }

    /** {@inheritDoc} */
    public Label createSeparator(Composite parent, int style) {
        Label label = new Label(parent, SWT.SEPARATOR | style);
        adapt(label, false, false);
        return label;
    }

    /** {@inheritDoc} */
    public Spinner createSpinner(Composite parent, String instrumentationName) {
        Spinner spinner = new Spinner(parent, SWT.BORDER);
        adapt(spinner, true, true);
        spinner.setData(INSTRUMENTATION_DATA_NAME,
                "spinner" + instrumentationName); //$NON-NLS-1$
        return spinner;
    }

    /** {@inheritDoc} */
    public CTabFolder createTabFolder(Composite parent, int style) {
        CTabFolder tabFolder = new CTabFolder(parent, style);
        return tabFolder;
    }

    /** {@inheritDoc} */
    public CTabItem createTabItem(CTabFolder tabFolder, int style) {
        CTabItem tabItem = new CTabItem(tabFolder, style);
        return tabItem;
    }

    /** {@inheritDoc} */
    public Table createTable(Composite parent, int style,
            String instrumentationName) {
        Table table = new Table(parent, style);
        adapt(table, true, true);
        table.setData(INSTRUMENTATION_DATA_NAME, "table" + instrumentationName); //$NON-NLS-1$
        return table;
    }

    /** {@inheritDoc} */
    public Text createText(Composite parent, String defaultText, int style,
            String instrumentationName) {
        Text textField = new Text(parent, style);
        textField.setText(defaultText);
        adapt(textField, true, true);
        textField.setData(INSTRUMENTATION_DATA_NAME,
                "text" + instrumentationName); //$NON-NLS-1$
        return textField;
    }

    /** {@inheritDoc} */
    public Text createText(Composite parent, String defaultText,
            String instrumentationName) {
        return createText(parent, defaultText, SWT.SINGLE | SWT.BORDER,
                instrumentationName);
    }

    /** {@inheritDoc} */
    public Tree createTree(Composite parent, int style,
            String instrumentationName) {
        Tree tree = new Tree(parent, style);
        adapt(tree, true, true);
        tree.setData(INSTRUMENTATION_DATA_NAME, "tree" + instrumentationName); //$NON-NLS-1$
        return tree;
    }

    /** {@inheritDoc} */
    public void dispose() {
        // do nothing
    }

    /**
     * Creates checkbox button with default styles.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return checkbox with default styles.
     */
    public Button createCheckbox(Composite parent, boolean defaultValue,
            String text, String instrumentationName) {
        Button button = createButton(parent, text, SWT.CHECK,
                instrumentationName);
        button.setSelection(defaultValue);
        return button;
    }

    /**
     * Creates push button default style.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return button with default styles.
     */
    public Button createPushButton(Composite parent, String text,
            String instrumentationName) {
        return createButton(parent, text, SWT.PUSH, instrumentationName);
    }

    /**
     * Creates plain combo with read only style.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return read only combo with default styles.
     */
    public Combo createCombo(Composite parent, String instrumentationName) {
        Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
        adapt(combo, true, true);
        combo.setData(INSTRUMENTATION_DATA_NAME, "combo" + instrumentationName); //$NON-NLS-1$
        return combo;
    }

    /**
     * Creates table with default style.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return table with default styles.
     */
    public Table createTable(Composite parent, String instrumentationName) {
        return createTable(parent, SWT.FULL_SELECTION | SWT.SINGLE
                | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER, instrumentationName);
    }

    /**
     * Creates tree with default style.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return tree with default styles.
     */
    public Tree createTree(Composite parent, String instrumentationName) {
        return createTree(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL
                | SWT.BORDER, instrumentationName);
    }

    /**
     * Creates checked tree with default styles.
     * 
     * @param parent
     *            the parent.
     * @param instrumentationName
     *            the name used for testing.
     * @return checked tree with default styles.
     */
    public Tree createCheckedTree(Composite parent, String instrumentationName) {
        return createTree(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL
                | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK,
                instrumentationName);
    }
}
