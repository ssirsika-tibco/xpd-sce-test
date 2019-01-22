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
package com.tibco.xpd.script.sourceviewer.internal.viewer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AnnotationPreference;

import com.tibco.xpd.script.sourceviewer.internal.util.PreferenceUtils;

public class ScriptAnnotationHover extends DefaultAnnotationHover {

    /**
     * The preference store used to initialize this configuration.
     * <p>
     * Note: protected since 3.1
     * </p>
     */
    private IPreferenceStore fPreferenceStore = PreferenceUtils
            .getPreferenceStore();

    @Override
    protected boolean isIncluded(Annotation annotation) {
        return isShowInVerticalRuler(annotation);
    }

    /*
     * @see DefaultAnnotationHover#isIncluded(Annotation)
     * @since 3.2
     */
    protected boolean isShowInVerticalRuler(Annotation annotation) {

        AnnotationPreference preference = getAnnotationPreference(annotation);
        if (preference == null)
            return false;
        String key = preference.getVerticalRulerPreferenceKey();
        // backward compatibility
        if (key != null && !fPreferenceStore.getBoolean(key))
            return false;

        return true;
    }

    /**
     * Returns the annotation preference for the given annotation.
     * 
     * @param annotation
     *            the annotation
     * @return the annotation preference or <code>null</code> if none
     * @since 3.2
     */
    private AnnotationPreference getAnnotationPreference(Annotation annotation) {
        if (annotation == null || fPreferenceStore == null) {
            return null;
        }
        return EditorsUI.getAnnotationPreferenceLookup()
                .getAnnotationPreference(annotation);
    }

    /*
     * @see DefaultAnnotationHover#isIncluded(Annotation)
     * @since 3.2
     */
    protected boolean isShowInOverviewRuler(Annotation annotation) {
        AnnotationPreference preference = getAnnotationPreference(annotation);
        if (preference == null)
            return false;
        String key = preference.getOverviewRulerPreferenceKey();
        if (key == null || !fPreferenceStore.getBoolean(key))
            return false;

        return true;
    }
}
