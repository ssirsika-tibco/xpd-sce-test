package com.tibco.xpd.script.sourceviewer.viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.text.IFindReplaceTargetExtension;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.ITextViewerExtension6;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.revisions.IRevisionRulerColumn;
import org.eclipse.jface.text.revisions.RevisionInformation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.ChangeRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IChangeRulerColumn;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.jface.text.source.IVerticalRulerExtension;
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.MarkerAnnotationPreferences;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.wst.sse.ui.internal.preferences.EditorPreferenceNames;

import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.util.Consts;
import com.tibco.xpd.script.sourceviewer.internal.util.PreferenceUtils;

public class PropertyChangeListener implements IPropertyChangeListener {

    private ScriptSourceViewer scriptViewer = null;

    private Color fForegroundColor;

    /** The editor's font. */
    private Font fFont;

    /**
     * The editor's foreground color.
     * 
     * @since 2.0
     */

    /**
     * The editor's background color.
     * 
     * @since 2.0
     */
    private Color fBackgroundColor;

    /**
     * The editor's selection foreground color.
     * 
     * @since 3.0
     */
    private Color fSelectionForegroundColor;

    /**
     * The editor's selection background color.
     * 
     * @since 3.0
     */
    private Color fSelectionBackgroundColor;

    /**
     * Whether the overwrite mode can be turned on.
     * 
     * @since 3.0
     */
    private boolean fIsOverwriteModeEnabled = true;

    private boolean fIsOverwriting = false;

    /**
     * The find scope's highlight color.
     * 
     * @since 2.0
     */
    private Color fFindScopeHighlightColor;

    /**
     * Whether quick diff information is displayed, either on a change ruler or
     * the line number ruler.
     * 
     * @since 3.2
     */
    private boolean fIsRevisionInformationShown;

    private boolean fIsChangeInformationShown;

    /**
     * The revision information displayed on the ruler, possibly
     * <code>null</code>.
     * 
     * @since 3.2
     */
    private RevisionInformation fRevisionInfo;

    /**
     * The change ruler column.
     */
    private IChangeRulerColumn fChangeRulerColumn;

    /**
     * The line number column.
     * 
     * <p>
     * This field should not be referenced by subclasses. It is
     * <code>protected</code> for API compatibility reasons and will be made
     * <code>private</code> soon. Use
     * {@link AbstractTextEditor#getVerticalRuler()} to access the vertical bar
     * instead.
     * </p>
     */
    protected LineNumberRulerColumn fLineNumberRulerColumn;

    private enum InsertMode {
        INSERT, SMART_INSERT
    }

    private InsertMode fInsertMode = InsertMode.SMART_INSERT;

    /**
     * The image used in non-default caret.
     * 
     * @since 3.0
     */
    private Image fNonDefaultCaretImage;

    /**
     * The non-default caret.
     * 
     * @since 3.0
     */
    private Caret fNonDefaultCaret;

    /**
     * The styled text's initial caret.
     * 
     * @since 3.0
     */
    private Caret fInitialCaret;

    private AbstractScriptCommonUIPreferenceNames scriptCommonUIPreferenceNames =
            null;

    /**
     * The sequence of legal editor insert modes.
     * 
     * @since 3.0
     */
    private List<InsertMode> fLegalInsertModes = null;

    public PropertyChangeListener(ScriptSourceViewer viewer) {
        this.scriptViewer = viewer;
    }

    public void propertyChange(PropertyChangeEvent event) {
        handlePreferenceStoreChanged(event);
    }

    protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
        handleScriptPreferenceStoreChanged(event);
        try {
            handleAbstractDecoratedPreferenceStoreChanged(event);
        } finally {
            handleAbstractTextPreferenceStoreChanged(event);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.texteditor.AbstractTextEditor#handlePreferenceStoreChanged(org.eclipse.jface.util.PropertyChangeEvent)
     */
    protected void handleScriptPreferenceStoreChanged(PropertyChangeEvent event) {
        String property = event.getProperty();
        if (EditorPreferenceNames.EDITOR_TEXT_HOVER_MODIFIERS.equals(property)) {
            updateHoverBehavior();
        }
        if (getCommonUIPreferenceNames() != null
                && (getCommonUIPreferenceNames().getIndentationChar()
                        .equals(property) || getCommonUIPreferenceNames()
                        .getIndentationSize().equals(property))) {
            updateTabBehavior();
        }
    }

    protected AbstractScriptCommonUIPreferenceNames getCommonUIPreferenceNames() {
        if (scriptCommonUIPreferenceNames == null && scriptViewer != null
                && scriptViewer.getGrammarType() != null) {
            scriptCommonUIPreferenceNames =
                    scriptViewer.getScriptCommonUIPreferenceNames(scriptViewer
                            .getGrammarType());
        }
        return scriptCommonUIPreferenceNames;
    }

    /*
     * @see AbstractTextEditor#handlePreferenceStoreChanged(PropertyChangeEvent)
     */
    protected void handleAbstractDecoratedPreferenceStoreChanged(
            PropertyChangeEvent event) {

        ISourceViewer sourceViewer = getSourceViewer();
        if (sourceViewer == null)
            return;

        String property = event.getProperty();

        if (getDecorationSupport() != null && getOverviewRuler() != null
                && Consts.OVERVIEW_RULER.equals(property)) {
            if (isOverviewRulerVisible()) {
                showOverviewRuler();
            } else {
                hideOverviewRuler();
            }
            return;
        }

        /*
         * Not supported for the time being. if
         * (DISABLE_OVERWRITE_MODE.equals(property)) {
         * enableOverwriteMode(isOverwriteModeEnabled()); return; }
         */

        if (Consts.LINE_NUMBER_RULER.equals(property)) {
            if (PreferenceUtils.isLineNumberRulerVisible())
                showLineNumberRuler();
            else
                hideLineNumberRuler();
            return;
        }

        /*
         * if
         * (AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_ALWAYS_ON
         * .equals(property)) {
         * showChangeInformation(isPrefQuickDiffAlwaysOn()); }
         */

        if (AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH
                .equals(property)) {
            IPreferenceStore store = getPreferenceStore();
            if (store != null)
                sourceViewer
                        .getTextWidget()
                        .setTabs(store
                                .getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH));
            return;
        }

        if (AbstractDecoratedTextEditorPreferenceConstants.EDITOR_UNDO_HISTORY_SIZE
                .equals(property)
                && sourceViewer instanceof ITextViewerExtension6) {
            IPreferenceStore store = getPreferenceStore();
            if (store != null)
                ((ITextViewerExtension6) sourceViewer)
                        .getUndoManager()
                        .setMaximalUndoLevel(store
                                .getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_UNDO_HISTORY_SIZE));
            return;
        }

        if (fLineNumberRulerColumn != null
                && (Consts.LINE_NUMBER_COLOR.equals(property)
                        || Consts.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT
                                .equals(property) || Consts.PREFERENCE_COLOR_BACKGROUND
                        .equals(property))) {

            initializeLineNumberRulerColumn(fLineNumberRulerColumn);
        }

        if (fChangeRulerColumn != null
                && (Consts.LINE_NUMBER_COLOR.equals(property)
                        || Consts.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT
                                .equals(property) || Consts.PREFERENCE_COLOR_BACKGROUND
                        .equals(property))) {

            initializeChangeRulerColumn(fChangeRulerColumn);
        }

        if (fLineNumberRulerColumn instanceof LineNumberChangeRulerColumn
                && AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_CHARACTER_MODE
                        .equals(property)) {
            initializeChangeRulerColumn(getChangeColumn());
        }

        if (AbstractDecoratedTextEditorPreferenceConstants.SHOW_RANGE_INDICATOR
                .equals(property)) {
            if (isRangeIndicatorEnabled()) {
                getSourceViewer().setRangeIndicator(getRangeIndicator());
            } else {
                getSourceViewer().removeRangeIndication();
                getSourceViewer().setRangeIndicator(null);
            }
        }

        AnnotationPreference pref = getAnnotationPreference(property);
        if (pref != null) {
            IChangeRulerColumn column = getChangeColumn();
            if (column != null) {
                Object type = pref.getAnnotationType();
                if (type instanceof String) {
                    String annotationType = (String) type;
                    if (annotationType
                            .startsWith("org.eclipse.ui.workbench.texteditor.quickdiff")) //$NON-NLS-1$
                        initializeChangeRulerColumn(column);
                }
            }
        }

        AnnotationPreference annotationPreference =
                getVerticalRulerAnnotationPreference(property);
        if (annotationPreference != null
                && event.getNewValue() instanceof Boolean) {
            Object type = annotationPreference.getAnnotationType();
            if (((Boolean) event.getNewValue()).booleanValue()) {
                getAnnotationRuleColumn().addAnnotationType(type);
            } else {
                getAnnotationRuleColumn().removeAnnotationType(type);
            }
            getVerticalRuler().update();
        }

    }

    /**
     * Returns the <code>AnnotationPreference</code> corresponding to
     * <code>colorKey</code>.
     * 
     * @param colorKey
     *            the color key.
     * @return the corresponding <code>AnnotationPreference</code>
     */
    private AnnotationPreference getAnnotationPreference(String colorKey) {
        for (Iterator iter =
                getAnnotationPreferences().getAnnotationPreferences()
                        .iterator(); iter.hasNext();) {
            AnnotationPreference pref = (AnnotationPreference) iter.next();
            if (colorKey.equals(pref.getColorPreferenceKey()))
                return pref;
        }
        return null;
    }

    /**
     * Returns the annotation preference for which the given preference matches
     * a vertical ruler preference key.
     * 
     * @param preferenceKey
     *            the preference key string
     * @return the annotation preference or <code>null</code> if none
     */
    private AnnotationPreference getVerticalRulerAnnotationPreference(
            String preferenceKey) {
        if (preferenceKey == null)
            return null;

        Iterator e =
                getAnnotationPreferences().getAnnotationPreferences()
                        .iterator();
        while (e.hasNext()) {
            AnnotationPreference info = (AnnotationPreference) e.next();
            if (info != null
                    && preferenceKey.equals(info
                            .getVerticalRulerPreferenceKey()))
                return info;
        }
        return null;
    }

    /**
     * Returns the <code>IChangeRulerColumn</code> of this editor, or
     * <code>null</code> if there is none. Either the line number bar or a
     * separate change ruler column can be returned.
     * 
     * @return an instance of <code>IChangeRulerColumn</code> or
     *         <code>null</code>.
     */
    private IChangeRulerColumn getChangeColumn() {
        if (fChangeRulerColumn != null)
            return fChangeRulerColumn;
        else if (fLineNumberRulerColumn instanceof IChangeRulerColumn) {
            return (IChangeRulerColumn) fLineNumberRulerColumn;
        } else {
            return null;
        }
    }

    /**
     * Returns whether the range indicator is enabled according to the
     * preference store settings. Subclasses may override this method to provide
     * a custom preference setting.
     * 
     * @return <code>true</code> if overwrite mode is enabled
     * @since 3.1
     */
    private boolean isRangeIndicatorEnabled() {
        IPreferenceStore store = getPreferenceStore();
        return store != null ? store
                .getBoolean(AbstractDecoratedTextEditorPreferenceConstants.SHOW_RANGE_INDICATOR)
                : true;
    }

    /**
     * Handles a property change event describing a change of the editor's
     * preference store and updates the preference related editor properties.
     * <p>
     * Subclasses may extend.
     * </p>
     * 
     * @param event
     *            the property change event
     */
    protected void handleAbstractTextPreferenceStoreChanged(
            PropertyChangeEvent event) {

        String property = event.getProperty();

        if (getSourceViewer() == null) {
            return;
        }

        SourceViewer fSourceViewer = getSourceViewer();
        if (PreferenceUtils.getFontPropertyPreferenceKey().equals(property)) {
            initializeViewerFont(fSourceViewer);
            updateCaret();
            return;
        }
        if (AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND.equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND
                        .equals(property)
                || AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT
                        .equals(property)) {
            initializeViewerColors(fSourceViewer);
        } else if (AbstractTextEditor.PREFERENCE_COLOR_FIND_SCOPE
                .equals(property)) {
            initializeFindScopeColor(fSourceViewer);
        } else if (AbstractTextEditor.PREFERENCE_USE_CUSTOM_CARETS
                .equals(property)) {
            updateCaret();
        } else if (AbstractTextEditor.PREFERENCE_WIDE_CARET.equals(property)) {
            updateCaret();
        }

        if (affectsTextPresentation(event)) {
            fSourceViewer.invalidateTextPresentation();
        }

        if (AbstractTextEditor.PREFERENCE_HYPERLINKS_ENABLED.equals(property)) {
            if (fSourceViewer instanceof ITextViewerExtension6) {
                IHyperlinkDetector[] detectors =
                        getSourceViewerConfiguration()
                                .getHyperlinkDetectors(fSourceViewer);
                int stateMask =
                        getSourceViewerConfiguration()
                                .getHyperlinkStateMask(fSourceViewer);
                ITextViewerExtension6 textViewer6 =
                        (ITextViewerExtension6) fSourceViewer;
                textViewer6.setHyperlinkDetectors(detectors, stateMask);
            }
            return;
        }

        if (AbstractTextEditor.PREFERENCE_HYPERLINK_KEY_MODIFIER
                .equals(property)) {
            if (fSourceViewer instanceof ITextViewerExtension6) {
                ITextViewerExtension6 textViewer6 =
                        (ITextViewerExtension6) fSourceViewer;
                IHyperlinkDetector[] detectors =
                        getSourceViewerConfiguration()
                                .getHyperlinkDetectors(fSourceViewer);
                int stateMask =
                        getSourceViewerConfiguration()
                                .getHyperlinkStateMask(fSourceViewer);
                textViewer6.setHyperlinkDetectors(detectors, stateMask);
            }
            return;
        }
    }

    /**
     * Determines whether the given preference change affects the editor's
     * presentation. This implementation always returns <code>false</code>. May
     * be reimplemented by subclasses.
     * 
     * @param event
     *            the event which should be investigated
     * @return <code>true</code> if the event describes a preference change
     *         affecting the editor's presentation
     * @since 2.0
     */
    protected boolean affectsTextPresentation(PropertyChangeEvent event) {
        return false;
    }

    /**
     * Initializes the background color used for highlighting the document
     * ranges defining search scopes.
     * 
     * @param viewer
     *            the viewer to initialize
     * @since 2.0
     */
    protected void initializeFindScopeColor(ISourceViewer viewer) {

        IPreferenceStore store = getPreferenceStore();
        if (store != null) {

            StyledText styledText = viewer.getTextWidget();

            Color color =
                    createColor(store,
                            AbstractTextEditor.PREFERENCE_COLOR_FIND_SCOPE,
                            styledText.getDisplay());

            IFindReplaceTarget target = viewer.getFindReplaceTarget();
            if (target != null && target instanceof IFindReplaceTargetExtension)
                ((IFindReplaceTargetExtension) target)
                        .setScopeHighlightColor(color);

            if (fFindScopeHighlightColor != null)
                fFindScopeHighlightColor.dispose();

            fFindScopeHighlightColor = color;
        }
    }

    /*
     * Update the hovering behavior depending on the preferences.
     */
    private void updateHoverBehavior() {
        SourceViewerConfiguration configuration =
                getSourceViewerConfiguration();
        String[] types =
                configuration.getConfiguredContentTypes(getSourceViewer());

        for (int i = 0; i < types.length; i++) {

            String t = types[i];

            ISourceViewer sourceViewer = getSourceViewer();
            if (sourceViewer instanceof ITextViewerExtension2) {
                // Remove existing hovers
                ((ITextViewerExtension2) sourceViewer).removeTextHovers(t);

                int[] stateMasks =
                        configuration
                                .getConfiguredTextHoverStateMasks(getSourceViewer(),
                                        t);

                if (stateMasks != null) {
                    for (int j = 0; j < stateMasks.length; j++) {
                        int stateMask = stateMasks[j];
                        ITextHover textHover =
                                configuration.getTextHover(sourceViewer,
                                        t,
                                        stateMask);
                        ((ITextViewerExtension2) sourceViewer)
                                .setTextHover(textHover, t, stateMask);
                    }
                } else {
                    ITextHover textHover =
                            configuration.getTextHover(sourceViewer, t);
                    ((ITextViewerExtension2) sourceViewer)
                            .setTextHover(textHover,
                                    t,
                                    ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
                }
            } else
                sourceViewer.setTextHover(configuration
                        .getTextHover(sourceViewer, t), t);
        }
    }

    /*
     * Update the tab behavior depending on the preferences.
     */
    private void updateTabBehavior() {
        SourceViewerConfiguration configuration =
                getSourceViewerConfiguration();
        if (configuration != null) {
            ISourceViewer sourceViewer = getSourceViewer();
            String[] types =
                    configuration.getConfiguredContentTypes(sourceViewer);

            for (int i = 0; i < types.length; i++) {
                String t = types[i];
                String[] prefixes =
                        configuration.getIndentPrefixes(sourceViewer, t);
                if (prefixes != null && prefixes.length > 0)
                    sourceViewer.setIndentPrefixes(prefixes, types[i]);
            }
        }
    }

    /**
     * Initializes the fore- and background colors of the given viewer for both
     * normal and selected text.
     * 
     * @param viewer
     *            the viewer to be initialized
     * @since 2.0
     */
    protected void initializeViewerColors(ISourceViewer viewer) {

        IPreferenceStore store = getPreferenceStore();
        if (store != null) {

            StyledText styledText = viewer.getTextWidget();

            // ----------- foreground color --------------------
            Color color =
                    store
                            .getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT) ? null
                            : createColor(store,
                                    AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND,
                                    styledText.getDisplay());
            styledText.setForeground(color);

            if (fForegroundColor != null)
                fForegroundColor.dispose();

            fForegroundColor = color;

            // ---------- background color ----------------------
            color =
                    store
                            .getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? null
                            : createColor(store,
                                    AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND,
                                    styledText.getDisplay());
            styledText.setBackground(color);

            if (fBackgroundColor != null)
                fBackgroundColor.dispose();

            fBackgroundColor = color;

            // ----------- selection foreground color --------------------
            color =
                    store
                            .getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND_SYSTEM_DEFAULT) ? null
                            : createColor(store,
                                    AbstractTextEditor.PREFERENCE_COLOR_SELECTION_FOREGROUND,
                                    styledText.getDisplay());
            styledText.setSelectionForeground(color);

            if (fSelectionForegroundColor != null)
                fSelectionForegroundColor.dispose();

            fSelectionForegroundColor = color;

            // ---------- selection background color ----------------------
            color =
                    store
                            .getBoolean(AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND_SYSTEM_DEFAULT) ? null
                            : createColor(store,
                                    AbstractTextEditor.PREFERENCE_COLOR_SELECTION_BACKGROUND,
                                    styledText.getDisplay());
            styledText.setSelectionBackground(color);

            if (fSelectionBackgroundColor != null)
                fSelectionBackgroundColor.dispose();

            fSelectionBackgroundColor = color;
        }
    }

    /**
     * Creates a color from the information stored in the given preference
     * store. Returns <code>null</code> if there is no such information
     * available.
     * 
     * @param store
     *            the store to read from
     * @param key
     *            the key used for the lookup in the preference store
     * @param display
     *            the display used create the color
     * @return the created color according to the specification in the
     *         preference store
     * @since 2.0
     */
    private Color createColor(IPreferenceStore store, String key,
            Display display) {

        RGB rgb = null;

        if (store.contains(key)) {

            if (store.isDefault(key))
                rgb = PreferenceConverter.getDefaultColor(store, key);
            else
                rgb = PreferenceConverter.getColor(store, key);

            if (rgb != null)
                return JFaceResources.getResources(display).createColor(rgb);
        }

        return null;
    }

    /**
     * Tells whether the overview ruler is visible.
     * 
     * @return whether the overview ruler is visible
     */
    public boolean isOverviewRulerVisible() {
        IPreferenceStore store = getPreferenceStore();
        return store != null ? store.getBoolean(Consts.OVERVIEW_RULER) : false;
    }

    /**
     * Shows the overview ruler.
     */
    protected void showOverviewRuler() {
        if (getOverviewRuler() != null) {
            if (getSourceViewer() instanceof ISourceViewerExtension) {
                ((ISourceViewerExtension) getSourceViewer())
                        .showAnnotationsOverview(true);
                getDecorationSupport().updateOverviewDecorations();
            }
        }
    }

    /**
     * Hides the overview ruler.
     */
    protected void hideOverviewRuler() {
        if (getSourceViewer() instanceof ISourceViewerExtension) {
            getDecorationSupport().hideAnnotationOverview();
            ((ISourceViewerExtension) getSourceViewer())
                    .showAnnotationsOverview(false);
        }
    }

    /**
     * Returns whether the overwrite mode is enabled according to the preference
     * store settings. Subclasses may override this method to provide a custom
     * preference setting.
     * 
     * @return <code>true</code> if overwrite mode is enabled
     * @since 3.1
     */
    protected boolean isOverwriteModeEnabled() {
        IPreferenceStore store = getPreferenceStore();
        return store != null ? !store.getBoolean(Consts.DISABLE_OVERWRITE_MODE)
                : true;
    }

    /**
     * Shows the line number ruler column.
     */
    private void showLineNumberRuler() {
        showChangeRuler(false);
        if (getLineNumberRulerColumn() == null) {
            IVerticalRuler v = getVerticalRuler();
            if (v instanceof CompositeRuler) {
                CompositeRuler c = (CompositeRuler) v;
                c.addDecorator(1, createLineNumberRulerColumn());
            }
        }

        if (fIsRevisionInformationShown) {
            IRevisionRulerColumn column = getRevisionColumn();
            column.setRevisionInformation(fRevisionInfo);
        }
    }

    /**
     * Creates a new line number ruler column that is appropriately initialized.
     * 
     * @return the created line number column
     */
    public IVerticalRulerColumn createLineNumberRulerColumn() {
        if (PreferenceUtils.isPrefQuickDiffAlwaysOn()) {
            LineNumberChangeRulerColumn column =
                    new LineNumberChangeRulerColumn(getSharedColors());
            column.setHover(PreferenceUtils.getChangeHover());
            initializeChangeRulerColumn(column);
            fLineNumberRulerColumn = column;
        } else {
            fLineNumberRulerColumn = new LineNumberRulerColumn();
        }
        initializeLineNumberRulerColumn(fLineNumberRulerColumn);
        return fLineNumberRulerColumn;
    }

    /**
     * Hides the line number ruler column.
     */
    private void hideLineNumberRuler() {
        if (fLineNumberRulerColumn != null) {
            IVerticalRuler v = getVerticalRuler();
            if (v instanceof CompositeRuler) {
                CompositeRuler c = (CompositeRuler) v;
                c.removeDecorator(fLineNumberRulerColumn);
            }
            fLineNumberRulerColumn = null;
        }
        if (fIsChangeInformationShown)
            showChangeRuler(true);
    }

    /**
     * Sets the display state of the separate change ruler column (not the quick
     * diff display on the line number ruler column) to <code>show</code>.
     * 
     * @param show
     *            <code>true</code> if the change ruler column should be shown,
     *            <code>false</code> if it should be hidden
     */
    private void showChangeRuler(boolean show) {
        IVerticalRuler v = getVerticalRuler();
        if (v instanceof CompositeRuler) {
            CompositeRuler c = (CompositeRuler) v;
            if (show && getChangeRulerColumn() == null) {
                c.addDecorator(1, createChangeRulerColumn());
                if (fIsRevisionInformationShown) {
                    IRevisionRulerColumn column = getRevisionColumn();
                    column.setRevisionInformation(fRevisionInfo);
                }
            } else if (!show && fChangeRulerColumn != null) {
                c.removeDecorator(fChangeRulerColumn);
                fChangeRulerColumn = null;
            }
        }
    }

    /**
     * Returns the revision ruler column of this editor, creating one if needed.
     * 
     * @return the revision ruler column of this editor
     * @since 3.2
     */
    private IRevisionRulerColumn getRevisionColumn() {
        if (fChangeRulerColumn instanceof IRevisionRulerColumn)
            return (IRevisionRulerColumn) fChangeRulerColumn;

        if (fLineNumberRulerColumn instanceof IRevisionRulerColumn)
            return (IRevisionRulerColumn) fLineNumberRulerColumn;

        return null;
    }

    /**
     * Creates a new change ruler column for quick diff display independent of
     * the line number ruler column
     * 
     * @return a new change ruler column
     */
    protected IChangeRulerColumn createChangeRulerColumn() {
        IChangeRulerColumn column = new ChangeRulerColumn(getSharedColors());
        column.setHover(PreferenceUtils.getChangeHover());
        fChangeRulerColumn = column;
        initializeChangeRulerColumn(fChangeRulerColumn);
        return fChangeRulerColumn;
    }

    /**
     * Initializes the given change ruler column from the preference store.
     * 
     * @param changeColumn
     *            the ruler column to be initialized
     */
    public void initializeChangeRulerColumn(IChangeRulerColumn changeColumn) {
        ISharedTextColors sharedColors = getSharedColors();
        IPreferenceStore store = getPreferenceStore();

        if (store != null) {
            ISourceViewer v = getSourceViewer();
            if (v != null && v.getAnnotationModel() != null) {
                changeColumn.setModel(v.getAnnotationModel());
            }

            Iterator iter =
                    getAnnotationPreferences().getAnnotationPreferences()
                            .iterator();
            while (iter.hasNext()) {
                AnnotationPreference pref = (AnnotationPreference) iter.next();

                if ("org.eclipse.ui.workbench.texteditor.quickdiffChange".equals(pref.getAnnotationType())) { //$NON-NLS-1$
                    RGB rgb = getColorPreference(store, pref);
                    changeColumn.setChangedColor(sharedColors.getColor(rgb));
                } else if ("org.eclipse.ui.workbench.texteditor.quickdiffAddition".equals(pref.getAnnotationType())) { //$NON-NLS-1$
                    RGB rgb = getColorPreference(store, pref);
                    changeColumn.setAddedColor(sharedColors.getColor(rgb));
                } else if ("org.eclipse.ui.workbench.texteditor.quickdiffDeletion".equals(pref.getAnnotationType())) { //$NON-NLS-1$
                    RGB rgb = getColorPreference(store, pref);
                    changeColumn.setDeletedColor(sharedColors.getColor(rgb));
                }
            }

            RGB rgb = null;
            // background color: same as editor, or system default
            if (!store
                    .getBoolean(Consts.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)) {
                if (store.contains(Consts.PREFERENCE_COLOR_BACKGROUND)) {
                    if (store.isDefault(Consts.PREFERENCE_COLOR_BACKGROUND))
                        rgb =
                                PreferenceConverter.getDefaultColor(store,
                                        Consts.PREFERENCE_COLOR_BACKGROUND);
                    else
                        rgb =
                                PreferenceConverter.getColor(store,
                                        Consts.PREFERENCE_COLOR_BACKGROUND);
                }
            }
            changeColumn.setBackground(sharedColors.getColor(rgb));

            if (changeColumn instanceof LineNumberChangeRulerColumn) {
                LineNumberChangeRulerColumn lncrc =
                        (LineNumberChangeRulerColumn) changeColumn;
                lncrc
                        .setDisplayMode(store
                                .getBoolean(AbstractDecoratedTextEditorPreferenceConstants.QUICK_DIFF_CHARACTER_MODE));
            }
        }

        changeColumn.redraw();
    }

    /**
     * Initializes the given line number ruler column from the preference store.
     * 
     * @param rulerColumn
     *            the ruler column to be initialized
     */
    protected void initializeLineNumberRulerColumn(
            LineNumberRulerColumn rulerColumn) {
        ISharedTextColors sharedColors = getSharedColors();
        IPreferenceStore store = getPreferenceStore();
        if (store != null) {

            RGB rgb = null;
            // foreground color
            if (store.contains(Consts.LINE_NUMBER_COLOR)) {
                if (store.isDefault(Consts.LINE_NUMBER_COLOR))
                    rgb =
                            PreferenceConverter.getDefaultColor(store,
                                    Consts.LINE_NUMBER_COLOR);
                else
                    rgb =
                            PreferenceConverter.getColor(store,
                                    Consts.LINE_NUMBER_COLOR);
            }
            if (rgb == null)
                rgb = new RGB(0, 0, 0);
            rulerColumn.setForeground(sharedColors.getColor(rgb));

            rgb = null;
            // background color: same as editor, or system default
            if (!store
                    .getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)) {
                if (store
                        .contains(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)) {
                    if (store
                            .isDefault(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND))
                        rgb =
                                PreferenceConverter
                                        .getDefaultColor(store,
                                                AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
                    else
                        rgb =
                                PreferenceConverter
                                        .getColor(store,
                                                AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
                }
            }
            rulerColumn.setBackground(sharedColors.getColor(rgb));

            rulerColumn.redraw();
        }
    }

    /**
     * Extracts the color preference for the given preference from the given
     * store. If the given store indicates that the default value is to be used,
     * or the value stored in the preferences store is <code>null</code>, the
     * value is taken from the <code>AnnotationPreference</code>'s default color
     * value.
     * <p>
     * The return value is
     * </p>
     * 
     * @param store
     *            the preference store
     * @param pref
     *            the annotation preference
     * @return the RGB color preference, not <code>null</code>
     */
    private RGB getColorPreference(IPreferenceStore store,
            AnnotationPreference pref) {
        RGB rgb = null;
        if (store.contains(pref.getColorPreferenceKey())) {
            if (store.isDefault(pref.getColorPreferenceKey()))
                rgb = pref.getColorPreferenceValue();
            else
                rgb =
                        PreferenceConverter.getColor(store, pref
                                .getColorPreferenceKey());
        }
        if (rgb == null)
            rgb = pref.getColorPreferenceValue();
        return rgb;
    }

    private void updateCaret() {

        SourceViewer fSourceViewer = getSourceViewer();
        if (fSourceViewer == null) {
            return;
        }
        StyledText styledText = fSourceViewer.getTextWidget();

        InsertMode mode = getInsertMode();

        styledText.setCaret(null);
        disposeNonDefaultCaret();
        if (getPreferenceStore() == null
                || !getPreferenceStore()
                        .getBoolean(AbstractTextEditor.PREFERENCE_USE_CUSTOM_CARETS)) {
            Assert.isTrue(fNonDefaultCaret == null);
        } else if (fIsOverwriting) {
            fNonDefaultCaret = createOverwriteCaret(styledText);
        } else if (InsertMode.SMART_INSERT == mode) {
            fNonDefaultCaret = createInsertCaret(styledText);
        } else if (InsertMode.INSERT == mode) {
            fNonDefaultCaret = createRawInsertModeCaret(styledText);
        }
        if (fNonDefaultCaret != null) {
            styledText.setCaret(fNonDefaultCaret);
            fNonDefaultCaretImage = fNonDefaultCaret.getImage();
        } else if (fInitialCaret != styledText.getCaret()) {
            styledText.setCaret(fInitialCaret);
        }
    }

    private void disposeNonDefaultCaret() {
        if (fNonDefaultCaretImage != null) {
            fNonDefaultCaretImage.dispose();
            fNonDefaultCaretImage = null;
        }

        if (fNonDefaultCaret != null) {
            fNonDefaultCaret.dispose();
            fNonDefaultCaret = null;
        }
    }

    private Caret createRawInsertModeCaret(StyledText styledText) {
        // don't draw special raw caret if no smart mode is enabled
        if (!getLegalInsertModes().contains(InsertMode.SMART_INSERT)) {
            return createInsertCaret(styledText);
        }

        Caret caret = new Caret(styledText, SWT.NULL);
        Image image = createRawInsertModeCaretImage(styledText);
        if (image != null) {
            caret.setImage(image);
        } else {
            // XXX: Filed request to get a caret with auto-height:
            // https://bugs.eclipse.org/bugs/show_bug.cgi?id=118612
            caret
                    .setSize(getCaretWidthPreference(), styledText
                            .getLineHeight());
        }
        caret.setFont(styledText.getFont());
        return caret;
    }

    /**
     * Returns the set of legal insert modes. If insert modes are configured all
     * defined insert modes are legal.
     * 
     * @return the set of legal insert modes
     * @since 3.0
     */
    protected List getLegalInsertModes() {
        if (fLegalInsertModes == null) {
            fLegalInsertModes = new ArrayList<InsertMode>();
            InsertMode[] modes = InsertMode.values();
            for (int i = 0; i < modes.length; i++) {
                fLegalInsertModes.add(modes[i]);
            }
        }
        return fLegalInsertModes;
    }

    private Image createRawInsertModeCaretImage(StyledText styledText) {

        PaletteData caretPalette =
                new PaletteData(new RGB[] { new RGB(0, 0, 0),
                        new RGB(255, 255, 255) });
        int width = getCaretWidthPreference();
        int widthOffset = width - 1;

        // XXX: Filed request to get a caret with auto-height:
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=118612
        ImageData imageData =
                new ImageData(4 + widthOffset, styledText.getLineHeight(), 1,
                        caretPalette);

        Display display = styledText.getDisplay();
        Image bracketImage = new Image(display, imageData);
        GC gc = new GC(bracketImage);
        gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
        gc.setLineWidth(1);
        int height = imageData.height / 3;
        // gap between two bars of one third of the height
        // draw boxes using lines as drawing a line of a certain width produces
        // rounded corners.
        for (int i = 0; i < width; i++) {
            gc.drawLine(i, 0, i, height - 1);
            gc.drawLine(i, imageData.height - height, i, imageData.height - 1);
        }

        gc.dispose();

        return bracketImage;
    }

    private Caret createOverwriteCaret(StyledText styledText) {
        Caret caret = new Caret(styledText, SWT.NULL);
        GC gc = new GC(styledText);
        // XXX this overwrite box is not proportional-font aware
        // take 'a' as a medium sized character
        Point charSize = gc.stringExtent("a"); //$NON-NLS-1$        
        // XXX: Filed request to get a caret with auto-height:
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=118612
        caret.setSize(charSize.x, styledText.getLineHeight());
        caret.setFont(styledText.getFont());
        gc.dispose();
        return caret;
    }

    private Caret createInsertCaret(StyledText styledText) {
        Caret caret = new Caret(styledText, SWT.NULL);
        // XXX: Filed request to get a caret with auto-height:
        // https://bugs.eclipse.org/bugs/show_bug.cgi?id=118612
        caret.setSize(getCaretWidthPreference(), styledText.getLineHeight());
        caret.setFont(styledText.getFont());
        return caret;
    }

    private int getCaretWidthPreference() {
        if (getPreferenceStore() != null
                && getPreferenceStore()
                        .getBoolean(AbstractTextEditor.PREFERENCE_WIDE_CARET)) {
            return Consts.WIDE_CARET_WIDTH;
        }
        return Consts.SINGLE_CARET_WIDTH;
    }

    /**
     * Initializes the given viewer's font.
     * 
     * @param viewer
     *            the viewer
     * @since 2.0
     */
    protected void initializeViewerFont(ISourceViewer viewer) {

        boolean isSharedFont = true;
        Font font = null;
        String symbolicFontName = PreferenceUtils.getSymbolicFontName();
        IPreferenceStore fPreferenceStore = getPreferenceStore();
        if (symbolicFontName != null)
            font = JFaceResources.getFont(symbolicFontName);
        else if (fPreferenceStore != null) {
            // Backward compatibility
            if (fPreferenceStore.contains(JFaceResources.TEXT_FONT)
                    && !fPreferenceStore.isDefault(JFaceResources.TEXT_FONT)) {
                FontData data =
                        PreferenceConverter.getFontData(fPreferenceStore,
                                JFaceResources.TEXT_FONT);

                if (data != null) {
                    isSharedFont = false;
                    font = new Font(viewer.getTextWidget().getDisplay(), data);
                }
            }
        }
        if (font == null)
            font = JFaceResources.getTextFont();

        setFont(viewer, font);

        if (fFont != null) {
            fFont.dispose();
            fFont = null;
        }

        if (!isSharedFont)
            fFont = font;
    }

    /**
     * Sets the font for the given viewer sustaining selection and scroll
     * position.
     * 
     * @param sourceViewer
     *            the source viewer
     * @param font
     *            the font
     * @since 2.0
     */
    private void setFont(ISourceViewer sourceViewer, Font font) {
        if (sourceViewer.getDocument() != null) {

            Point selection = sourceViewer.getSelectedRange();
            int topIndex = sourceViewer.getTopIndex();

            StyledText styledText = sourceViewer.getTextWidget();
            Control parent = styledText;
            if (sourceViewer instanceof ITextViewerExtension) {
                ITextViewerExtension extension =
                        (ITextViewerExtension) sourceViewer;
                parent = extension.getControl();
            }

            parent.setRedraw(false);

            styledText.setFont(font);

            if (getVerticalRuler() instanceof IVerticalRulerExtension) {
                IVerticalRulerExtension e =
                        (IVerticalRulerExtension) getVerticalRuler();
                e.setFont(font);
            }

            sourceViewer.setSelectedRange(selection.x, selection.y);
            sourceViewer.setTopIndex(topIndex);

            if (parent instanceof Composite) {
                Composite composite = (Composite) parent;
                composite.layout(true);
            }

            parent.setRedraw(true);

        } else {

            StyledText styledText = sourceViewer.getTextWidget();
            styledText.setFont(font);

            if (getVerticalRuler() instanceof IVerticalRulerExtension) {
                IVerticalRulerExtension e =
                        (IVerticalRulerExtension) getVerticalRuler();
                e.setFont(font);
            }
        }
    }

    public void dispose() {

        if (fFont != null) {
            fFont.dispose();
            fFont = null;
        }

        disposeNonDefaultCaret();
        fInitialCaret = null;

        if (fForegroundColor != null) {
            fForegroundColor.dispose();
            fForegroundColor = null;
        }

        if (fBackgroundColor != null) {
            fBackgroundColor.dispose();
            fBackgroundColor = null;
        }
        if (fSelectionForegroundColor != null) {
            fSelectionForegroundColor.dispose();
            fSelectionForegroundColor = null;
        }

        if (fSelectionBackgroundColor != null) {
            fSelectionBackgroundColor.dispose();
            fSelectionBackgroundColor = null;
        }
        if (fFindScopeHighlightColor != null) {
            fFindScopeHighlightColor.dispose();
            fFindScopeHighlightColor = null;
        }
    }

    /*
     * @see org.eclipse.ui.texteditor.ITextEditorExtension3#getInsertMode()
     * @since 3.0
     */
    public InsertMode getInsertMode() {
        return fInsertMode;
    }

    private ScriptSourceViewer getJScriptSourceViewer() {
        return this.scriptViewer;
    }

    private LineNumberRulerColumn getLineNumberRulerColumn() {
        return this.fLineNumberRulerColumn;
    }

    private IChangeRulerColumn getChangeRulerColumn() {
        return fChangeRulerColumn;
    }

    private SourceViewer getSourceViewer() {
        return getJScriptSourceViewer().getSourceViewer();
    }

    private SourceViewerConfiguration getSourceViewerConfiguration() {
        return getJScriptSourceViewer().getSourceViewerConfiguration();
    }

    private IPreferenceStore getPreferenceStore() {
        return PreferenceUtils.getPreferenceStore();
    }

    private IOverviewRuler getOverviewRuler() {
        return getJScriptSourceViewer().getOverviewRuler();
    }

    protected IVerticalRuler getVerticalRuler() {
        return getJScriptSourceViewer().getVerticalRuler();
    }

    private SourceViewerDecorationSupport getDecorationSupport() {
        return getJScriptSourceViewer().getDecorationSupport();
    }

    private MarkerAnnotationPreferences getAnnotationPreferences() {
        return PreferenceUtils.getMarkerAnnotationPreferences();
    }

    private ISharedTextColors getSharedColors() {
        return PreferenceUtils.getSharedColors();
    }

    private Annotation getRangeIndicator() {
        return getJScriptSourceViewer().getRangeIndicator();
    }

    private AnnotationRulerColumn getAnnotationRuleColumn() {
        return getJScriptSourceViewer().getAnnotationRuleColumn();
    }

}
