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
package com.tibco.xpd.rql.script.preferences.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

import com.tibco.xpd.rql.script.RQLScriptPlugin;
import com.tibco.xpd.rql.script.styles.IStyleConstantsRQL;

public class RQLColorPreferences {
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
    public static TextAttribute taKeywordEntity; // = new TextAttribute(

    public static TextAttribute taKeywordAttribute;

    public static TextAttribute taKeywordOperator;

    private PropertyChangeListener fPreferenceListener =
            new PropertyChangeListener();

    private static final RQLColorPreferences global = new RQLColorPreferences();

    private RQLColorPreferences() {
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

        TextAttribute ta = createTextAttribute(IStyleConstantsRQL.DEFAULT);
        if (ta != null)
            taDefault = ta;

        ta = createTextAttribute(IStyleConstantsRQL.KEYWORDENTITIES);
        if (ta != null)
            taKeywordEntity = ta;

        ta = createTextAttribute(IStyleConstantsRQL.KEYWORDATTRIBUTES);
        if (ta != null)
            taKeywordAttribute = ta;

        ta = createTextAttribute(IStyleConstantsRQL.KEYWORDOPERATORS);
        if (ta != null)
            taKeywordOperator = ta;

        // we could add code here to check if taOld is different than the new
        // value
        // and if so invalidate what's currently on the screen.
    }

    protected static void clearColors() {
        taDefault =
                taKeywordEntity =
                        taKeywordAttribute =
                                taKeywordOperator =
                                        createTextAttribute(null, null, false);
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
        return RQLScriptPlugin.getDefault().getPreferenceStore();
    }

    protected void handlePropertyChange(PropertyChangeEvent event) {
        if (event != null) {
            String prefKey = event.getProperty();
            // check if preference changed is a style preference
            if (IStyleConstantsRQL.DEFAULT.equals(prefKey)) {
                taDefault = createTextAttribute(IStyleConstantsRQL.DEFAULT);
            } else if (IStyleConstantsRQL.KEYWORDENTITIES.equals(prefKey)) {
                taKeywordEntity =
                        createTextAttribute(IStyleConstantsRQL.KEYWORDENTITIES);
            } else if (IStyleConstantsRQL.KEYWORDATTRIBUTES.equals(prefKey)) {
                taKeywordAttribute =
                        createTextAttribute(IStyleConstantsRQL.KEYWORDATTRIBUTES);
            } else if (IStyleConstantsRQL.KEYWORDOPERATORS.equals(prefKey)) {
                taKeywordOperator =
                        createTextAttribute(IStyleConstantsRQL.KEYWORDOPERATORS);
            }
        }
    }

    /**
     * Returns true if the given key is a rql style preference key, false
     * otherwise
     */
    public static boolean isRQLColorPreference(String prefKey) {
        boolean isPreference = false;

        if ((IStyleConstantsRQL.DEFAULT.equals(prefKey))
                || (IStyleConstantsRQL.KEYWORDENTITIES.equals(prefKey))
                || (IStyleConstantsRQL.KEYWORDATTRIBUTES.equals(prefKey))
                || (IStyleConstantsRQL.KEYWORDOPERATORS.equals(prefKey))) {
            isPreference = true;
        }
        return isPreference;
    }
}
