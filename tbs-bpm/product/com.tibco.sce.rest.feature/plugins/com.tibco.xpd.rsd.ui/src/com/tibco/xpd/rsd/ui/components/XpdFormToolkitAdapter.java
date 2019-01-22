/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
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

import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * Adapts {@link FormToolkit} to the {@link XpdToolkit} interface.
 * 
 * @author jarciuch
 * @since 7 Jan 2015
 */
public class XpdFormToolkitAdapter extends BaseXpdToolkit implements XpdToolkit {
    private FormToolkit toolkit;

    public XpdFormToolkitAdapter(FormToolkit toolkit) {
        this.toolkit = toolkit;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#adapt(org.eclipse.swt.widgets.Composite)
     *
     * @param composite
     */
    @Override
    public void adapt(Composite composite) {
        toolkit.adapt(composite);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#adapt(org.eclipse.swt.widgets.Control,
     *      boolean, boolean)
     *
     * @param control
     * @param trackFocus
     * @param trackKeyboard
     */
    @Override
    public void adapt(Control control, boolean trackFocus, boolean trackKeyboard) {
        toolkit.adapt(control, trackFocus, trackKeyboard);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createButton(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int, java.lang.String)
     *
     * @param parent
     * @param text
     * @param style
     * @param instrumentationName
     * @return
     */
    @Override
    public Button createButton(Composite parent, String text, int style,
            String instrumentationName) {
        return toolkit.createButton(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      int, java.lang.String)
     *
     * @param parent
     * @param comboStyle
     * @param instrumentationName
     * @return
     */
    @Override
    public CCombo createCCombo(Composite parent, int comboStyle,
            String instrumentationName) {
        return super.createCCombo(parent, comboStyle, instrumentationName);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     *
     * @param parent
     * @param instrumentationName
     * @return
     */
    @Override
    public CCombo createCCombo(Composite parent, String instrumentationName) {
        return super.createCCombo(parent, instrumentationName);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createCLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     *
     * @param parent
     * @param text
     * @return
     */
    @Override
    public CLabel createCLabel(Composite parent, String text) {
        return super.createCLabel(parent, text);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createCLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     *
     * @param parent
     * @param text
     * @param style
     * @return
     */
    @Override
    public CLabel createCLabel(Composite parent, String text, int style) {
        return super.createCLabel(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createComposite(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     * @return
     */
    @Override
    public Composite createComposite(Composite parent) {
        return toolkit.createComposite(parent);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public Composite createComposite(Composite parent, int style) {
        return toolkit.createComposite(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createCompositeSeparator(org.eclipse.swt.widgets.Composite)
     *
     * @param parent
     * @return
     */
    @Override
    public Composite createCompositeSeparator(Composite parent) {
        return toolkit.createCompositeSeparator(parent);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createGroup(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     *
     * @param parent
     * @param text
     * @return
     */
    @Override
    public Group createGroup(Composite parent, String text) {
        return super.createGroup(parent, text);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     *
     * @param parent
     * @param text
     * @return
     */
    @Override
    public Label createLabel(Composite parent, String text) {
        return toolkit.createLabel(parent, text);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     *
     * @param parent
     * @param text
     * @param style
     * @return
     */
    @Override
    public Label createLabel(Composite parent, String text, int style) {
        return toolkit.createLabel(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createList(org.eclipse.swt.widgets.Composite,
     *      int, java.lang.String)
     *
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    @Override
    public List createList(Composite parent, int style,
            String instrumentationName) {
        return super.createList(parent, style, instrumentationName);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createPageBook(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public ScrolledPageBook createPageBook(Composite parent, int style) {
        return toolkit.createPageBook(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createPlainComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public Composite createPlainComposite(Composite parent, int style) {
        return super.createPlainComposite(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createScrolledComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public ScrolledComposite createScrolledComposite(Composite parent, int style) {
        return super.createScrolledComposite(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createSeparator(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public Label createSeparator(Composite parent, int style) {
        return toolkit.createSeparator(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createSpinner(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     *
     * @param parent
     * @param instrumentationName
     * @return
     */
    @Override
    public Spinner createSpinner(Composite parent, String instrumentationName) {
        return super.createSpinner(parent, instrumentationName);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createTabFolder(org.eclipse.swt.widgets.Composite,
     *      int)
     *
     * @param parent
     * @param style
     * @return
     */
    @Override
    public CTabFolder createTabFolder(Composite parent, int style) {
        return super.createTabFolder(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createTabItem(org.eclipse.swt.custom.CTabFolder,
     *      int)
     *
     * @param tabFolder
     * @param style
     * @return
     */
    @Override
    public CTabItem createTabItem(CTabFolder tabFolder, int style) {
        return super.createTabItem(tabFolder, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createTable(org.eclipse.swt.widgets.Composite,
     *      int, java.lang.String)
     *
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    @Override
    public Table createTable(Composite parent, int style,
            String instrumentationName) {
        return toolkit.createTable(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int, java.lang.String)
     *
     * @param parent
     * @param value
     * @param style
     * @param instrumentationName
     * @return
     */
    @Override
    public Text createText(Composite parent, String value, int style,
            String instrumentationName) {
        return toolkit.createText(parent, value, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, java.lang.String)
     *
     * @param parent
     * @param value
     * @param instrumentationName
     * @return
     */
    @Override
    public Text createText(Composite parent, String value,
            String instrumentationName) {
        return toolkit.createText(parent, value);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#createTree(org.eclipse.swt.widgets.Composite,
     *      int, java.lang.String)
     *
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    @Override
    public Tree createTree(Composite parent, int style,
            String instrumentationName) {
        return toolkit.createTree(parent, style);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.XpdToolkit#dispose()
     *
     */
    @Override
    public void dispose() {
        // Do not dispose toolkit as it was not created here, but passed in
        // constructor.
        // toolkit.dispose();
    }

}
