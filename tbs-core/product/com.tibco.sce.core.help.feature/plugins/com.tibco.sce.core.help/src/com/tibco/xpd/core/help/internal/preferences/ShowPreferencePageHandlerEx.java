package com.tibco.xpd.core.help.internal.preferences;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * <p>
 * This is the extension of:
 * <code>org.eclipse.ui.internal.handlers.ShowPreferencePageHandler</code> which
 * opens a preferences on a specified page, but it additionally allows to show
 * the specified pages to be displayed (by optionally providing the command
 * parameter {@link ShowPreferencePageHandlerEx#PARAM_DISPLAY_IDS} with a comma
 * separated list of pageIds).
 * </p>
 * 
 * @author jarciuch
 * @since 20 Aug 2014
 */
public final class ShowPreferencePageHandlerEx extends AbstractHandler {

    /**
     * Command parameter key for a list of preference page ids to be shown when
     * preference page is open. If the parameter value is provided then it
     * should be a comma separated list (no spaces).
     */
    public static final String PARAM_DISPLAY_IDS = "displayedIds"; //$NON-NLS-1$

    @Override
    public final Object execute(final ExecutionEvent event) {
        final String preferencePageId =
                event.getParameter(IWorkbenchCommandConstants.WINDOW_PREFERENCES_PARM_PAGEID);
        final IWorkbenchWindow activeWorkbenchWindow =
                HandlerUtil.getActiveWorkbenchWindow(event);

        @SuppressWarnings("rawtypes")
        Map params = event.getParameters();
        Object displayIdsParam = params.get(PARAM_DISPLAY_IDS);
        String[] displayIds = null;
        if (displayIdsParam instanceof String) {
            displayIds = ((String) displayIdsParam).split(","); //$NON-NLS-1$
        }
        final Shell shell;
        if (activeWorkbenchWindow == null) {
            shell = null;
        } else {
            shell = activeWorkbenchWindow.getShell();
        }

        final PreferenceDialog dialog =
                PreferencesUtil.createPreferenceDialogOn(shell,
                        preferencePageId,
                        displayIds,
                        null);
        dialog.open();

        return null;
    }
}