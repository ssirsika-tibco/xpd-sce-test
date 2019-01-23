/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
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

import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * The toolkit is an extension of {@link XpdToolkit} to support Form controls.
 * 
 * @author wzurek
 * 
 */
public interface XpdFormToolkit extends XpdToolkit {

    /**
     * @param parent
     * @param eo
     * @param attribute
     * @return
     */
    Text createText(Composite parent, EObject eo, EAttribute attribute);

    /**
     * Creates a text control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param instrumentationName
     * @return
     */
    Text createText(Composite parent, EObject eo, EAttribute attribute,
            String instrumentationName);

    /**
     * @param parent
     * @param eo
     * @param attribute
     * @param enumeration
     * @return
     */
    Button createRadioButon(Composite parent, EObject eo, EAttribute attribute,
            EEnumLiteral enumeration);

    /**
     * Creates a button control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param enumeration
     * @param instrumentationName
     * @return
     */
    Button createRadioButon(Composite parent, EObject eo, EAttribute attribute,
            EEnumLiteral enumeration, String instrumentationName);

    /**
     * @param parent
     * @param eo
     * @param attribute
     * @return
     */
    CCombo createCCombo(Composite parent, EObject eo, EAttribute attribute);

    /**
     * Creates a combo control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param eo
     * @param attribute
     * @param instrumentationName
     * @return
     */
    CCombo createCCombo(Composite parent, EObject eo, EAttribute attribute,
            String instrumentationName);

    /**
     * @param parent
     * @return
     */
    Spinner createSpinner(Composite parent);

    /**
     * Creates a spinner control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param instrumentationName
     * @return
     */
    Spinner createSpinner(Composite parent, String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#adapt(org.eclipse.swt.widgets
     * .Composite)
     */
    void adapt(Composite composite);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#adapt(org.eclipse.swt.widgets
     * .Control, boolean, boolean)
     */
    void adapt(Control control, boolean trackFocus, boolean trackKeyboard);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createButton(org.eclipse.swt
     * .widgets.Composite, java.lang.String, int)
     */
    /**
     * @param parent
     * @param text
     * @param style
     * @return
     */
    Button createButton(Composite parent, String text, int style);

    /**
     * Creates a button control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param text
     * @param style
     * @param instrumentationName
     * @return
     */
    Button createButton(Composite parent, String text, int style,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createCCombo(org.eclipse.swt.widgets.Composite, int)
     */
    /**
     * @param parent
     * @param comboStyle
     * @return
     */
    CCombo createCCombo(Composite parent, int comboStyle);

    /**
     * Creates a combo control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param comboStyle
     * @param instrumentationName
     * @return
     */
    CCombo createCCombo(Composite parent, int comboStyle,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createCCombo(org.eclipse.swt.widgets.Composite)
     */
    /**
     * @param parent
     * @return
     */
    CCombo createCCombo(Composite parent);

    /**
     * Creates a combo control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param instrumentationName
     * @return
     */
    CCombo createCCombo(Composite parent, String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createCLabel(org.eclipse.swt.widgets.Composite, java.lang.String, int)
     */
    CLabel createCLabel(Composite parent, String text, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createCLabel(org.eclipse.swt.widgets.Composite, java.lang.String)
     */
    CLabel createCLabel(Composite parent, String text);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createComposite(org.eclipse.swt.widgets.Composite, int)
     */
    Composite createComposite(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createComposite(org.eclipse.swt.widgets.Composite)
     */
    Composite createComposite(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createCompositeSeparator(org
     * .eclipse.swt.widgets.Composite)
     */
    Composite createCompositeSeparator(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createExpandableComposite(org
     * .eclipse.swt.widgets.Composite, int)
     */
    ExpandableComposite createExpandableComposite(Composite parent,
            int expansionStyle);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createFlatFormComposite(org.eclipse.swt.widgets.Composite)
     */
    Composite createFlatFormComposite(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createForm(org.eclipse.swt.widgets
     * .Composite)
     */
    Form createForm(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createFormText(org.eclipse.swt
     * .widgets.Composite, boolean)
     */
    /**
     * @param parent
     * @param trackFocus
     * @return
     */
    FormText createFormText(Composite parent, boolean trackFocus);

    /**
     * Creates a formtext control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param trackFocus
     * @param instrumentationName
     * @return
     */
    FormText createFormText(Composite parent, boolean trackFocus,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createGroup(org.eclipse.swt.widgets.Composite, java.lang.String)
     */
    Group createGroup(Composite parent, String text);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createHyperlink(org.eclipse.
     * swt.widgets.Composite, java.lang.String, int)
     */
    /**
     * @param parent
     * @param text
     * @param style
     * @return
     */
    Hyperlink createHyperlink(Composite parent, String text, int style);

    /**
     * Creates a hyperlink control and sets the instrumentation data on it in
     * the form of setData("name",instrumentationName). This is used by QF-Test
     * and the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param text
     * @param style
     * @param instrumentationName
     * @return
     */
    Hyperlink createHyperlink(Composite parent, String text, int style,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createImageHyperlink(org.eclipse
     * .swt.widgets.Composite, int)
     */
    /**
     * @param parent
     * @param style
     * @return
     */
    ImageHyperlink createImageHyperlink(Composite parent, int style);

    /**
     * Creates an imagehyperlink control and sets the instrumentation data on it
     * in the form of setData("name",instrumentationName). This is used by
     * QF-Test and the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    ImageHyperlink createImageHyperlink(Composite parent, int style,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createLabel(org.eclipse.swt.
     * widgets.Composite, java.lang.String, int)
     */
    Label createLabel(Composite parent, String text, int style);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createLabel(org.eclipse.swt.
     * widgets.Composite, java.lang.String)
     */
    Label createLabel(Composite parent, String text);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createList(org.eclipse.swt.widgets.Composite, int)
     */
    /**
     * @param parent
     * @param style
     * @return
     */
    List createList(Composite parent, int style);

    /**
     * Creates a list control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    List createList(Composite parent, int style, String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createPageBook(org.eclipse.swt
     * .widgets.Composite, int)
     */
    ScrolledPageBook createPageBook(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createPlainComposite(org.eclipse.swt.widgets.Composite, int)
     */
    Composite createPlainComposite(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createScrolledComposite(org.eclipse.swt.widgets.Composite, int)
     */
    ScrolledComposite createScrolledComposite(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createScrolledForm(org.eclipse
     * .swt.widgets.Composite)
     */
    ScrolledForm createScrolledForm(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createSection(org.eclipse.swt
     * .widgets.Composite, int)
     */
    Section createSection(Composite parent, int sectionStyle);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createSeparator(org.eclipse.
     * swt.widgets.Composite, int)
     */
    Label createSeparator(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createTabFolder(org.eclipse.swt.widgets.Composite, int)
     */
    CTabFolder createTabFolder(Composite parent, int style);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.wst.common.ui.properties.internal.provisional.
     * TabbedPropertySheetWidgetFactory
     * #createTabItem(org.eclipse.swt.custom.CTabFolder, int)
     */
    CTabItem createTabItem(CTabFolder tabFolder, int style);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createTable(org.eclipse.swt.
     * widgets.Composite, int)
     */
    /**
     * @param parent
     * @param style
     * @return
     */
    Table createTable(Composite parent, int style);

    /**
     * Creates a table control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    Table createTable(Composite parent, int style, String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createText(org.eclipse.swt.widgets
     * .Composite, java.lang.String, int)
     */
    /**
     * @param parent
     * @param value
     * @param style
     * @return
     */
    Text createText(Composite parent, String value, int style);

    /**
     * Creates a text control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param value
     * @param style
     * @param instrumentationName
     * @return
     */
    Text createText(Composite parent, String value, int style,
            String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createText(org.eclipse.swt.widgets
     * .Composite, java.lang.String)
     */
    /**
     * @param parent
     * @param value
     * @return
     */
    Text createText(Composite parent, String value);

    /**
     * Creates a text control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param value
     * @param instrumentationName
     * @return
     */
    Text createText(Composite parent, String value, String instrumentationName);

    /*
     * 
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createTree(org.eclipse.swt.widgets
     * .Composite, int)
     */

    /**
     * @param parent
     * @param style
     * @return
     */
    Tree createTree(Composite parent, int style);

    /**
     * Creates a tree control and sets the instrumentation data on it in the
     * form of setData("name",instrumentationName). This is used by QF-Test and
     * the instrumentationName is the id used for replay.
     * 
     * @param parent
     * @param style
     * @param instrumentationName
     * @return
     */
    Tree createTree(Composite parent, int style, String instrumentationName);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#dispose()
     */
    void dispose();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#getBorderStyle()
     */
    int getBorderStyle();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#getColors()
     */
    FormColors getColors();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#getHyperlinkGroup()
     */
    HyperlinkGroup getHyperlinkGroup();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#getOrientation()
     */
    int getOrientation();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#paintBordersFor(org.eclipse.
     * swt.widgets.Composite)
     */
    void paintBordersFor(Composite parent);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#refreshHyperlinkColors()
     */
    void refreshHyperlinkColors();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#setBackground(org.eclipse.swt
     * .graphics.Color)
     */
    void setBackground(Color bg);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#setBorderStyle(int)
     */
    void setBorderStyle(int style);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.widgets.FormToolkit#setOrientation(int)
     */
    void setOrientation(int orientation);

    /**
     * Returns the enclosing toolkit
     * 
     * @return
     */
    FormToolkit getFormToolkit();
}
