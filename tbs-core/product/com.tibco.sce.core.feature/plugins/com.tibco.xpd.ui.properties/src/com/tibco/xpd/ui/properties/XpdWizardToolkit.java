/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.HyperlinkGroup;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.resources.ui.components.BaseXpdToolkit;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit.XpdBorderPainter;

/**
 * The toolkit is mainly responsible for creating SWT controls adopted to work
 * in wizards.
 * 
 * @author wzurek
 * 
 */
public class XpdWizardToolkit extends BaseXpdToolkit implements XpdFormToolkit {

    private final TabbedPropertySheetWidgetFactory toolkit;

    private XpdBorderPainter xpdBorderPainter;

    /**
     * @param parent
     */
    public XpdWizardToolkit(Composite parent) {
        toolkit = new TabbedPropertySheetWidgetFactory();
        toolkit.setBorderStyle(SWT.BORDER);
        toolkit.setBackground(parent.getBackground());
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#adapt(org.eclipse.swt.widgets.Composite)
     * 
     * @param composite
     */
    @Override
    public void adapt(Composite composite) {
        toolkit.adapt(composite);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#adapt(org.eclipse.swt.widgets.Control,
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
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createButton(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     * 
     * @param parent
     * @param text
     * @param style
     * @return
     */
    public Button createButton(Composite parent, String text, int style) {
        return toolkit.createButton(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createButton(org.eclipse.swt.widgets.Composite,
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
        Button button = toolkit.createButton(parent, text, style);
        button.setData(INSTRUMENTATION_DATA_NAME, "button"
                + instrumentationName);
        return button;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createSpinner(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    public Spinner createSpinner(Composite parent) {
        Spinner spinner = new Spinner(parent, SWT.BORDER);
        spinner.setBackground(spinner.getDisplay()
                .getSystemColor(SWT.COLOR_WHITE));
        return spinner;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createSpinner(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     * 
     * @param parent
     * @param instrumentationName
     * @return
     */
    @Override
    public Spinner createSpinner(Composite parent, String instrumentationName) {
        Spinner spinner = createSpinner(parent);
        spinner.setData(INSTRUMENTATION_DATA_NAME, "spinner"
                + instrumentationName);
        spinner.setBackground(spinner.getDisplay()
                .getSystemColor(SWT.COLOR_WHITE));
        return spinner;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param comboStyle
     * @return
     */
    public CCombo createCCombo(Composite parent, int comboStyle) {
        return createCCombo(parent, comboStyle, null);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
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
        CCombo combo = toolkit.createCCombo(parent, (comboStyle) & (~SWT.FLAT));
        combo.setBackground(combo.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        if (instrumentationName != null) {
            combo.setData(INSTRUMENTATION_DATA_NAME, "combo"
                    + instrumentationName);
        }
        return combo;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    public CCombo createCCombo(Composite parent) {
        return createCCombo(parent, SWT.NONE);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     * 
     * @param parent
     * @param instrumentationName
     * @return
     */
    @Override
    public CCombo createCCombo(Composite parent, String instrumentationName) {
        CCombo combo = createCCombo(parent, SWT.NONE, instrumentationName);
        return combo;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     * 
     * @param parent
     * @param text
     * @param style
     * @return
     */
    @Override
    public CLabel createCLabel(Composite parent, String text, int style) {
        return toolkit.createCLabel(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCLabel(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     * 
     * @param parent
     * @param text
     * @return
     */
    @Override
    public CLabel createCLabel(Composite parent, String text) {
        return toolkit.createCLabel(parent, text);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    @Override
    public Composite createComposite(Composite parent, int style) {
        boolean debugLayout = false;

        if (debugLayout) {
            // style |= SWT.BORDER;
        }

        Composite comp = toolkit.createComposite(parent, style);

        if (debugLayout) {
            comp.setBackground(new Color(null, (int) (Math.random() * 255),
                    (int) (Math.random() * 255), (int) (Math.random() * 255)));

        }

        return comp;

    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createComposite(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public Composite createComposite(Composite parent) {
        return createComposite(parent, SWT.NONE);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCompositeSeparator(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public Composite createCompositeSeparator(Composite parent) {
        return toolkit.createCompositeSeparator(parent);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createExpandableComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param expansionStyle
     * @return
     */
    public ExpandableComposite createExpandableComposite(Composite parent,
            int expansionStyle) {
        return toolkit.createExpandableComposite(parent, expansionStyle);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createFlatFormComposite(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    public Composite createFlatFormComposite(Composite parent) {
        return toolkit.createFlatFormComposite(parent);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createForm(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    public Form createForm(Composite parent) {
        return toolkit.createForm(parent);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createFormText(org.eclipse.swt.widgets.Composite,
     *      boolean)
     * 
     * @param parent
     * @param trackFocus
     * @return
     */
    public FormText createFormText(Composite parent, boolean trackFocus) {
        return toolkit.createFormText(parent, trackFocus);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createFormText(org.eclipse.swt.widgets.Composite,
     *      boolean, java.lang.String)
     * 
     * @param parent
     * @param trackFocus
     * @param instrumentationName
     * @return
     */
    public FormText createFormText(Composite parent, boolean trackFocus,
            String instrumentationName) {
        FormText formText = toolkit.createFormText(parent, trackFocus);
        formText.setData(INSTRUMENTATION_DATA_NAME, "formtext"
                + instrumentationName);
        return formText;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createGroup(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     * 
     * @param parent
     * @param text
     * @return
     */
    @Override
    public Group createGroup(Composite parent, String text) {
        return toolkit.createGroup(parent, text);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createHyperlink(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     * 
     * @param parent
     * @param text
     * @param style
     * @return
     */
    public Hyperlink createHyperlink(Composite parent, String text, int style) {
        return toolkit.createHyperlink(parent, text, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createHyperlink(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int, java.lang.String)
     * 
     * @param parent
     * @param text
     * @param style
     * @param instrumentationName
     * @return
     */
    public Hyperlink createHyperlink(Composite parent, String text, int style,
            String instrumentationName) {
        Hyperlink hyperlink = toolkit.createHyperlink(parent, text, style);
        hyperlink.setData(INSTRUMENTATION_DATA_NAME, "hyperlink"
                + instrumentationName);
        return hyperlink;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createImageHyperlink(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    public ImageHyperlink createImageHyperlink(Composite parent, int style) {
        ImageHyperlink imageHyperlink =
                toolkit.createImageHyperlink(parent, style);
        imageHyperlink.setText(" "); //$NON-NLS-1$ //Set to prevent initial layour problem.
        return imageHyperlink;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createImageHyperlink(org.eclipse.swt.widgets.Composite,
     *      int, java.lang.String)
     * 
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    public ImageHyperlink createImageHyperlink(Composite parent, int style,
            String instrumentationName) {
        ImageHyperlink imageHyperlink =
                toolkit.createImageHyperlink(parent, style);
        imageHyperlink.setText(" "); //$NON-NLS-1$ //Set to prevent initial layour problem.
        imageHyperlink.setData(INSTRUMENTATION_DATA_NAME,
                "imagehyperlink" + instrumentationName); //$NON-NLS-1$
        return imageHyperlink;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createLabel(org.eclipse.swt.widgets.Composite,
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
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createLabel(org.eclipse.swt.widgets.Composite,
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
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createList(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    public List createList(Composite parent, int style) {
        return toolkit.createList(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createList(org.eclipse.swt.widgets.Composite,
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
        List list = toolkit.createList(parent, style);
        list.setData(INSTRUMENTATION_DATA_NAME, "list" + instrumentationName);
        list.setBackground(list.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return list;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createPageBook(org.eclipse.swt.widgets.Composite,
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
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createPlainComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    @Override
    public Composite createPlainComposite(Composite parent, int style) {
        return toolkit.createPlainComposite(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createScrolledComposite(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    @Override
    public ScrolledComposite createScrolledComposite(Composite parent, int style) {
        return toolkit.createScrolledComposite(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createScrolledForm(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    public ScrolledForm createScrolledForm(Composite parent) {
        return toolkit.createScrolledForm(parent);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createSection(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param sectionStyle
     * @return
     */
    public Section createSection(Composite parent, int sectionStyle) {
        return toolkit.createSection(parent, sectionStyle);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createSeparator(org.eclipse.swt.widgets.Composite,
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
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTabFolder(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    @Override
    public CTabFolder createTabFolder(Composite parent, int style) {
        return toolkit.createTabFolder(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTabItem(org.eclipse.swt.custom.CTabFolder,
     *      int)
     * 
     * @param tabFolder
     * @param style
     * @return
     */
    @Override
    public CTabItem createTabItem(CTabFolder tabFolder, int style) {
        return toolkit.createTabItem(tabFolder, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTable(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    public Table createTable(Composite parent, int style) {
        return toolkit.createTable(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTable(org.eclipse.swt.widgets.Composite,
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
        Table table = toolkit.createTable(parent, style);
        table.setData(INSTRUMENTATION_DATA_NAME, "table" + instrumentationName);
        table.setBackground(table.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return table;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      java.lang.String, int)
     * 
     * @param parent
     * @param value
     * @param style
     * @return
     */
    public Text createText(Composite parent, String value, int style) {
        Text text = toolkit.createText(parent, value, style);
        text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
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
        Text text = toolkit.createText(parent, value, style);
        text.setData(INSTRUMENTATION_DATA_NAME, "text" + instrumentationName);
        text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      java.lang.String)
     * 
     * @param parent
     * @param value
     * @return
     */
    public Text createText(Composite parent, String value) {
        Text text = toolkit.createText(parent, value);
        text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
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
        Text text = toolkit.createText(parent, value);
        text.setData(INSTRUMENTATION_DATA_NAME, "text" + instrumentationName);
        text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTree(org.eclipse.swt.widgets.Composite,
     *      int)
     * 
     * @param parent
     * @param style
     * @return
     */
    public Tree createTree(Composite parent, int style) {
        return toolkit.createTree(parent, style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createTree(org.eclipse.swt.widgets.Composite,
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
        Tree tree = toolkit.createTree(parent, style);
        tree.setData(INSTRUMENTATION_DATA_NAME, "tree" + instrumentationName);
        tree.setBackground(tree.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return tree;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#dispose()
     * 
     */
    @Override
    public void dispose() {
        toolkit.dispose();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return toolkit.equals(obj);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#getBorderStyle()
     * 
     * @return
     */
    public int getBorderStyle() {
        return toolkit.getBorderStyle();
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#getColors()
     * 
     * @return
     */
    public FormColors getColors() {
        return toolkit.getColors();
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#getHyperlinkGroup()
     * 
     * @return
     */
    public HyperlinkGroup getHyperlinkGroup() {
        return toolkit.getHyperlinkGroup();
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#getOrientation()
     * 
     * @return
     */
    public int getOrientation() {
        return toolkit.getOrientation();
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#paintBordersFor(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     */
    public void paintBordersFor(Composite parent) {
        if (xpdBorderPainter == null)
            xpdBorderPainter = new XpdBorderPainter(toolkit);
        parent.addPaintListener(xpdBorderPainter);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#refreshHyperlinkColors()
     * 
     */
    public void refreshHyperlinkColors() {
        toolkit.refreshHyperlinkColors();
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#setBackground(org.eclipse.swt.graphics.Color)
     * 
     * @param bg
     */
    public void setBackground(Color bg) {
        toolkit.setBackground(bg);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#setBorderStyle(int)
     * 
     * @param style
     */
    public void setBorderStyle(int style) {
        toolkit.setBorderStyle(style);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#setOrientation(int)
     * 
     * @param orientation
     */
    public void setOrientation(int orientation) {
        toolkit.setOrientation(orientation);
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @return
     */
    public Text createText(Composite parent, EObject eo, EAttribute attribute) {
        String string;
        if (eo != null) {
            string = String.valueOf(eo.eGet(attribute));
        } else {
            string = "";
        }
        Text text = createText(parent, string);
        text.setData(FEATURE_DATA, attribute);
        text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createText(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute,
     *      java.lang.String)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param instrumentationName
     * @return
     */
    public Text createText(Composite parent, EObject eo, EAttribute attribute,
            String instrumentationName) {
        Text text = createText(parent, eo, attribute);
        text.setData(INSTRUMENTATION_DATA_NAME, "text" + instrumentationName);
        return text;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createRadioButon(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute,
     *      org.eclipse.emf.ecore.EEnumLiteral)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param value
     * @return
     */
    public Button createRadioButon(Composite parent, EObject eo,
            EAttribute attribute, EEnumLiteral value) {

        EClass eClass = attribute.getEContainingClass();
        EPackage pck = eClass.getEPackage();

        ArrayList registryKey = new ArrayList(2);
        registryKey.add(pck.getNsURI());
        registryKey.add(IEditingDomainItemProvider.class.getName());
        Descriptor desc =
                EMFEditPlugin.getComposedAdapterFactoryDescriptorRegistry()
                        .getDescriptor(registryKey);

        AdapterFactory factory = desc.createAdapterFactory();

        EObject tmp = pck.getEFactoryInstance().create(eClass);
        ItemProviderAdapter ip = (ItemProviderAdapter) factory.adapt(tmp, pck);

        String string = ip.getString("_UI_" + value.getEEnum().getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
                + value.getName() + "_literal"); //$NON-NLS-1$
        Button button = createButton(parent, string, SWT.RADIO);
        button.setData(FEATURE_DATA, attribute);
        button.setData(VALUE_DATA, value);
        return button;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createRadioButon(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute,
     *      org.eclipse.emf.ecore.EEnumLiteral, java.lang.String)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param value
     * @param instrumentationName
     * @return
     */
    public Button createRadioButon(Composite parent, EObject eo,
            EAttribute attribute, EEnumLiteral value, String instrumentationName) {
        Button button = createRadioButon(parent, eo, attribute, value);
        button.setData(INSTRUMENTATION_DATA_NAME, "radio" + instrumentationName);
        return button;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @return
     */
    public CCombo createCCombo(Composite parent, EObject eo,
            EAttribute attribute) {

        EEnum type = (EEnum) attribute.getEType();
        EList lit = type.getELiterals();

        EClass eClass = attribute.getEContainingClass();
        EPackage pck = eClass.getEPackage();

        if (eo == null) {
            eo = pck.getEFactoryInstance().create(eClass);
        }
        ArrayList registryKey = new ArrayList(2);
        registryKey.add(pck.getNsURI());
        registryKey.add(IEditingDomainItemProvider.class.getName());
        Descriptor desc =
                EMFEditPlugin.getComposedAdapterFactoryDescriptorRegistry()
                        .getDescriptor(registryKey);
        AdapterFactory factory = desc.createAdapterFactory();
        ItemProviderAdapter ip = (ItemProviderAdapter) factory.adapt(eo, pck);

        String[] texts = new String[lit.size()];
        CCombo combo = createCCombo(parent, SWT.NONE);
        for (int i = 0; i < lit.size(); i++) {
            String name = (type.getEEnumLiteral(i)).getName();
            texts[i] = ip.getString("_UI_" + type.getName() + "_" //$NON-NLS-1$ 
                    + name + "_literal");
            combo.setData(name, texts[i]);
        }
        combo.setBackground(Display.getDefault()
                .getSystemColor(SWT.COLOR_WHITE));
        combo.setItems(texts);
        combo.setData(FEATURE_DATA, attribute);
        return combo;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#createCCombo(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute,
     *      java.lang.String)
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param instrumentationName
     * @return
     */
    public CCombo createCCombo(Composite parent, EObject eo,
            EAttribute attribute, String instrumentationName) {
        CCombo combo = createCCombo(parent, eo, attribute);
        combo.setData(INSTRUMENTATION_DATA_NAME, "combo" + instrumentationName);
        return combo;
    }

    /**
     * @see com.tibco.xpd.ui.properties.XpdFormToolkit#getFormToolkit()
     * 
     * @return
     */
    public FormToolkit getFormToolkit() {
        return toolkit;
    }
}
