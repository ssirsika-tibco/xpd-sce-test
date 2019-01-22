/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

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
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

/**
 * The toolkit is mainly responsible for creating SWT controls adopted to work
 * with various eclipse UI frameworks and help to keep the uniform look and feel
 * across XPD product. The concrete implementation of this class may serve as a
 * toolkits for creating controls for wizards, property pages, etc.
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
public interface XpdToolkit {

    /** The key to store feature (EMF) data in the control's data. */
    String FEATURE_DATA = "FEATURE"; //$NON-NLS-1$

    /** The key to store value in the control's data. */
    String VALUE_DATA = "VALUE"; //$NON-NLS-1$

    /**
     * Constant used as a key for storing instrumentation name in the control
     * custom data. <p/>Due to the new requirement that every control needs a
     * setData method applied to it in the form of
     * setData("name",instrumentationName) please provide in all create...
     * methods instrumentationName String as the last parameter.
     */
    String INSTRUMENTATION_DATA_NAME = "name"; //$NON-NLS-1$

    /**
     * Adapts a composite to be used in a style associated with this toolkit.
     * 
     * @param composite
     *            the composite to adapt
     */
    public abstract void adapt(Composite composite);

    /**
     * Adapts a control to be used in a style that is associated with this
     * toolkit. This involves adjusting colors and optionally adding handlers to
     * ensure focus tracking and keyboard management.
     * 
     * @param control
     *            a control to adapt
     * @param trackFocus
     *            if <code>true</code>, form will be scrolled horizontally
     *            and/or vertically if needed to ensure that the control is
     *            visible when it gains focus. Set it to <code>false</code> if
     *            the control is not capable of gaining focus.
     * @param trackKeyboard
     *            if <code>true</code>, the control that is capable of gaining
     *            focus will be tracked for certain keys that are important to
     *            the underlying form (for example, PageUp, PageDown, ScrollUp,
     *            ScrollDown etc.). Set it to <code>false</code> if the control
     *            is not capable of gaining focus or these particular key event
     *            are already used by the control.
     */
    public abstract void adapt(Control control, boolean trackFocus,
            boolean trackKeyboard);

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
     * #createCLabel(org.eclipse.swt.widgets.Composite, java.lang.String)
     */
    CLabel createCLabel(Composite parent, String text);

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
     * #createComposite(org.eclipse.swt.widgets.Composite)
     */
    Composite createComposite(Composite parent);

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
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createCompositeSeparator(org
     * .eclipse.swt.widgets.Composite)
     */
    Composite createCompositeSeparator(Composite parent);

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
     * org.eclipse.ui.forms.widgets.FormToolkit#createLabel(org.eclipse.swt.
     * widgets.Composite, java.lang.String)
     */
    Label createLabel(Composite parent, String text);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.widgets.FormToolkit#createLabel(org.eclipse.swt.
     * widgets.Composite, java.lang.String, int)
     */
    Label createLabel(Composite parent, String text, int style);

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
     * org.eclipse.ui.forms.widgets.FormToolkit#createSeparator(org.eclipse.
     * swt.widgets.Composite, int)
     */
    Label createSeparator(Composite parent, int style);

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

    /**
     * Disposed additional resources (like colors, images, etc) allocated for
     * toolkit.
     */
    void dispose();

}