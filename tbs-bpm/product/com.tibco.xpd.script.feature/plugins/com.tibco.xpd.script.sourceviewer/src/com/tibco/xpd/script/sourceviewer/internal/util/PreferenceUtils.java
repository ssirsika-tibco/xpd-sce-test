/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.internal.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineChangeHover;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.internal.texteditor.TextChangeHover;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.MarkerAnnotationPreferences;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;

import com.tibco.xpd.script.sourceviewer.Activator;

/**
 * @author rsomayaj
 * 
 */
public class PreferenceUtils {

    /** The editor's preference store. */
    private static IPreferenceStore fPreferenceStore;

    private static ISharedTextColors sharedColors;

    private static List<IPreferenceStore> preferenceStores =
            new ArrayList<IPreferenceStore>();

    /**
     * The annotation preferences.
     */
    private static MarkerAnnotationPreferences fAnnotationPreferences;

    /**
     * Preference key for showing the line number ruler.
     */
    private final static String LINE_NUMBER_RULER =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER;

    /**
     * Returns whether the line number ruler column should be visible according
     * to the preference store settings. Subclasses may override this method to
     * provide a custom preference setting.
     * 
     * @return <code>true</code> if the line numbers should be visible
     */
    public static boolean isLineNumberRulerVisible() {
        IPreferenceStore store = getPreferenceStore();
        return store != null ? store.getBoolean(LINE_NUMBER_RULER) : false;
    }

    public static IPreferenceStore getPreferenceStore() {
        if (fPreferenceStore == null) {
            createCombinedPreferenceStore();
        }
        return fPreferenceStore;
    }

    public static ISharedTextColors getSharedColors() {
        if (sharedColors == null) {
            sharedColors = EditorsPlugin.getDefault().getSharedTextColors();
        }
        return sharedColors;
    }

    /**
     * Creates and returns a <code>LineChangeHover</code> to be used on this
     * editor's change ruler column. This default implementation returns a plain
     * <code>LineChangeHover</code>. Subclasses may override.
     * 
     * @return the change hover to be used by this editors quick diff display
     */
    public static LineChangeHover getChangeHover() {
        return new TextChangeHover();
    }

    /**
     * Returns whether quick diff info should be visible upon opening an editor
     * according to the preference store settings.
     * 
     * @return <code>true</code> if the line numbers should be visible
     */
    public static boolean isPrefQuickDiffAlwaysOn() {
        IPreferenceStore store = getPreferenceStore();
        return store != null ? store
                .getBoolean(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON)
                : false;
    }

    public static MarkerAnnotationPreferences getMarkerAnnotationPreferences() {
        return EditorsPlugin.getDefault().getMarkerAnnotationPreferences();
    }

    /**
     * Create a preference store that combines the source editor preferences
     * with the base editor's preferences.
     * 
     * @return IPreferenceStore
     */
    private static void createCombinedPreferenceStore() {
        preferenceStores.add(Activator.getDefault().getPreferenceStore());
        preferenceStores.add(SSEUIPlugin.getDefault().getPreferenceStore());
        preferenceStores.add(EditorsUI.getPreferenceStore());

        fPreferenceStore =
                new ChainedPreferenceStore(preferenceStores
                        .toArray(new IPreferenceStore[preferenceStores.size()]));

    }

    /**
     * @param preferenceStore
     */
    public static void addPreferenceStore(IPreferenceStore preferenceStore) {
        if (fPreferenceStore == null) {
            createCombinedPreferenceStore();
        }
        if (!preferenceStores.contains(preferenceStore)) {
            preferenceStores.add(preferenceStore);

            fPreferenceStore =
                    new ChainedPreferenceStore(preferenceStores
                            .toArray(new IPreferenceStore[preferenceStores
                                    .size()]));
        }
    }

    /**
     * Returns the property preference key for the editor font. Subclasses may
     * replace this method.
     * 
     * @return a String with the key
     * @since 2.1
     */
    public static String getFontPropertyPreferenceKey() {
        return "org.eclipse.wst.sse.ui.textfont"; //$NON-NLS-1$
    }

    /**
     * Returns the symbolic font name for this editor as defined in XML.
     * 
     * @return a String with the symbolic font name or <code>null</code> if none
     *         is defined
     * @since 2.1
     */
    public static String getSymbolicFontName() {
        return "org.eclipse.wst.sse.ui.textfont"; //$NON-NLS-1$
    }
}
