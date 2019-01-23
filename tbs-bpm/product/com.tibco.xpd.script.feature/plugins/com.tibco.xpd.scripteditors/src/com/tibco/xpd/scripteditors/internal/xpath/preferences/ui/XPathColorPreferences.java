/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.scripteditors.internal.xpath.preferences.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;
import com.tibco.xpd.scripteditors.internal.xpath.styles.IStyleConstantsXPath;

public class XPathColorPreferences {
    private class PropertyChangeListener implements IPropertyChangeListener {
        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent event) {
            // have to do it this way so others can override the method
            handlePropertyChange(event);
        }
    }

    // defect 215738 - default needs to be set to null to maintain current
    // system color
    // private static final Color defaultForeground =
    // Display.getCurrent().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
    // private static final Color defaultBackground =
    // Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
    private static final Color defaultForeground = null;

    private static final Color defaultBackground = null;

    public static TextAttribute taDefault; // = new TextAttribute(

    // /*fg:*/colorBlack,
    // /*bg:*/colorWhite, /*bold ?
    // SWT.BOLD :*/ SWT.NORMAL );
    public static TextAttribute taKeyword; // = new TextAttribute(

    // /*fg:*/colorGreen,
    // /*bg:*/colorWhite, /*bold ?*/
    // SWT.BOLD /*: SWT.NORMAL*/ );
    public static TextAttribute taComment; // = new TextAttribute(

    // /*fg:*/colorWhite,
    // /*bg:*/colorGreen, /*bold ?
    // SWT.BOLD :*/ SWT.NORMAL );
    public static TextAttribute taStringLit; // = new TextAttribute(

    // /*fg:*/colorPink,
    // /*bg:*/colorWhite, /*bold ?
    // SWT.BOLD :*/ SWT.NORMAL );
    public static TextAttribute taUnfComment; // = new TextAttribute(

    // /*fg:*/colorWhite,
    // /*bg:*/colorRed, /*bold ?
    // SWT.BOLD :*/ SWT.NORMAL );

    private PropertyChangeListener fPreferenceListener =
            new PropertyChangeListener();

    private static final XPathColorPreferences global =
            new XPathColorPreferences();

    private XPathColorPreferences() {
        IPreferenceStore pref = getColorPreferences();
        if (pref != null) {
            pref.addPropertyChangeListener(fPreferenceListener);
        }
        loadColors();
    }

    /**
     * Looks up the colorKey in the preference store and creates a TextAttribute
     * for it.
     * 
     * @param colorKey
     */
    protected TextAttribute createTextAttribute(String colorKey) {
        TextAttribute ta = null;

        String prefString = getColorPreferences().getString(colorKey);
        String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
        if (stylePrefs != null) {
            RGB foreground = ColorHelper.toRGB(stylePrefs[0]);
            RGB background = ColorHelper.toRGB(stylePrefs[1]);
            boolean bold = Boolean.valueOf(stylePrefs[2]).booleanValue();
            ta = createTextAttribute(foreground, background, bold);
        }

        return ta;
    }

    protected void loadColors() {
        clearColors();

        TextAttribute ta = createTextAttribute(IStyleConstantsXPath.DEFAULT);
        if (ta != null)
            taDefault = ta;

        ta = createTextAttribute(IStyleConstantsXPath.KEYWORD);
        if (ta != null)
            taKeyword = ta;

        ta = createTextAttribute(IStyleConstantsXPath.LITERAL);
        if (ta != null)
            taStringLit = ta;

        ta = createTextAttribute(IStyleConstantsXPath.COMMENT);
        if (ta != null)
            taComment = ta;

        ta = createTextAttribute(IStyleConstantsXPath.UNFINISHED_COMMENT);
        if (ta != null)
            taUnfComment = ta;

        // we could add code here to check if taOld is different than the new
        // value
        // and if so invalidate what's currently on the screen.
    }

    protected static void clearColors() {
        taDefault =
                taKeyword =
                        taStringLit =
                                taComment =
                                        taUnfComment =
                                                createTextAttribute(null,
                                                        null,
                                                        false);
    }

    protected static TextAttribute createTextAttribute(RGB foreground,
            RGB background, boolean bold) {
        return new TextAttribute((foreground != null) ? EditorUtility
                .getColor(foreground) : defaultForeground,
                (background != null) ? EditorUtility.getColor(background)
                        : defaultBackground, bold ? SWT.BOLD : SWT.NORMAL);
    }

    public static void addPropertyChangeListener(IPropertyChangeListener pcl) {
        // todo: we could add a "ColorPreferenceChangeListener" later if we
        // decide the cost of adding an additional interface is justified
        global.getColorPreferences().addPropertyChangeListener(pcl);
    }

    public static void removePropertyChangeListener(IPropertyChangeListener pcl) {
        global.getColorPreferences().removePropertyChangeListener(pcl);
    }

    protected IPreferenceStore getColorPreferences() {
        return ScriptEditorsPlugin.getDefault().getPreferenceStore();
    }

    protected void handlePropertyChange(PropertyChangeEvent event) {
        if (event != null) {
            String prefKey = event.getProperty();
            // check if preference changed is a style preference
            if (IStyleConstantsXPath.DEFAULT.equals(prefKey)) {
                taDefault = createTextAttribute(IStyleConstantsXPath.DEFAULT);
            } else if (IStyleConstantsXPath.KEYWORD.equals(prefKey)) {
                taKeyword = createTextAttribute(IStyleConstantsXPath.KEYWORD);
            } else if (IStyleConstantsXPath.LITERAL.equals(prefKey)) {
                taStringLit = createTextAttribute(IStyleConstantsXPath.LITERAL);
            } else if (IStyleConstantsXPath.COMMENT.equals(prefKey)) {
                taComment = createTextAttribute(IStyleConstantsXPath.COMMENT);
            } else if (IStyleConstantsXPath.UNFINISHED_COMMENT.equals(prefKey)) {
                taUnfComment =
                        createTextAttribute(IStyleConstantsXPath.UNFINISHED_COMMENT);
            }
        }
    }

    /**
     * Returns true if the given key is a javascript style preference key, false
     * otherwise
     */
    public static boolean isXPathColorPreference(String prefKey) {
        boolean isPreference = false;

        if ((IStyleConstantsXPath.DEFAULT.equals(prefKey))
                || (IStyleConstantsXPath.KEYWORD.equals(prefKey))
                || (IStyleConstantsXPath.LITERAL.equals(prefKey))
                || (IStyleConstantsXPath.COMMENT.equals(prefKey))
                || (IStyleConstantsXPath.UNFINISHED_COMMENT.equals(prefKey))) {
            isPreference = true;
        }
        return isPreference;
    }
}
