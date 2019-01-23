package com.tibco.xpd.script.sourceviewer.internal.util;

import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

public class Consts {
	public static final String CONTEXT_ID = "com.tibco.xpd.js.sourceviewer.jsSourceViewer"; //$NON-NLS-1$

	public final static String MARKER_TYPE = "com.tibco.xpd.js.sourceviewer.jsValidationProblem"; //$NON-NLS-1$

	public final static String PROJECT_NAME_ATTRIB = "com.tibco.xpd.js.sourceviewer.jsProjectName"; //$NON-NLS-1$

	public final static String SCRIPT_CONTAINER_ID = "com.tibco.xpd.js.sourceviewer.jsScriptContainerId"; //$NON-NLS-1$

	public static final String LINE_NUMBER = "com.tibco.xpd.js.sourceviewer.jsSourceViewerLineNumber"; //$NON-NLS-1$

	public static final String ERROR_MESSAGE = "com.tibco.xpd.js.sourceviewer.jsSourceViewerErrorMessage"; //$NON-NLS-1$
	
	public static final String SCRIPT_JOB_FAMILY = "scriptJobFamily"; //$NON-NLS-1$

	/**
	 * Preference key for showing the overview ruler.
	 */
	public final static String OVERVIEW_RULER = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_OVERVIEW_RULER;

	/**
	 * Preference key to get whether the overwrite mode is disabled.
	 * 
	 * @since 3.1
	 */
	public final static String DISABLE_OVERWRITE_MODE = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_DISABLE_OVERWRITE_MODE;

	/**
	 * Preference key for the foreground color of the line numbers.
	 */
	public final static String LINE_NUMBER_COLOR = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER_COLOR;

	/**
	 * Preference key for showing the line number ruler.
	 */
	public final static String LINE_NUMBER_RULER = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER;

	/**
	 * Key used to look up background color system default preference. Value:
	 * <code>AbstractTextEditor.Color.Background.SystemDefault</code>
	 * 
	 * @since 2.0
	 */
	public final static String PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT = "AbstractTextEditor.Color.Background.SystemDefault"; //$NON-NLS-1$

	/**
	 * Key used to look up background color preference. Value:
	 * <code>AbstractTextEditor.Color.Background</code>
	 * 
	 * @since 2.0
	 */
	public final static String PREFERENCE_COLOR_BACKGROUND = "AbstractTextEditor.Color.Background"; //$NON-NLS-1$

	/**
	 * The caret width for the wide (double) caret. See
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=21715. Value: {@value}
	 * 
	 * @since 3.0
	 */
	public static final int WIDE_CARET_WIDTH = 2;

	/**
	 * The caret width for the narrow (single) caret. See
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=21715. Value: {@value}
	 * 
	 * @since 3.0
	 */
	public static final int SINGLE_CARET_WIDTH = 1;

	/**
	 * A named preference that controls whether bracket matching highlighting is
	 * turned on or off.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public final static String MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1$

	/**
	 * A named preference that holds the color used to highlight matching
	 * brackets.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 */
	public final static String MATCHING_BRACKETS_COLOR = "matchingBracketsColor"; //$NON-NLS-1$


}
