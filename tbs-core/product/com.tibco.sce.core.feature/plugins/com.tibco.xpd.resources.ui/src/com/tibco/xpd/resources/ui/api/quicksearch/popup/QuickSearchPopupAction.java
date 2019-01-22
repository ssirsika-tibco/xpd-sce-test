/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchContributionHelper;

/**
 * QuickSearchPopupAction.java
 * <p>
 * The quick search popup action can be contributed to any workbench view part
 * in the normal way (to an accelerator or menu or toolbar button etc). It uses
 * the {@link QuickSearchPopup} as the UI for the user to enter search patterns.
 * <p>
 * It is intended to allow the user to perform a 'local' search of items in the
 * current active workbench view part.
 * <p>
 * The searchable elements are contributed for a workbench part via the
 * <code>com.tibco.xpd.resources.ui.quickSearchContribution</code> extension
 * point. This allows searchable content to be contributed to a given view part
 * id.
 * </p>
 * <p>
 * The {@link QuickSearchPopup} UI uses these contributions to gather together
 * all searchable content for a view and then allows the user to filter thru
 * this content in a standard way.
 * </p>
 * 
 * @author aallway
 * @since 3.1
 */
public class QuickSearchPopupAction extends Action {

    private String searchDescription;

    public QuickSearchPopupAction(String searchDescription) {
        this.searchDescription = searchDescription;

        setId(ActionFactory.FIND.getId());

        setImageDescriptor(XpdResourcesUIActivator
                .getImageDescriptor(XpdResourcesUIConstants.IMG_SEARCH_ICON));

        if (searchDescription != null && searchDescription.length() > 0) {
            setToolTipText(searchDescription);
        } else {
            setToolTipText(Messages.QuickSearchPopupAction_QuickSearch_label);
        }
    }

    @Override
    public void run() {
        IWorkbenchPartReference activePart =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getPartService().getActivePartReference();
        if (activePart != null) {
            IWorkbenchPart part = activePart.getPart(false);

            if (part != null) {
                IWorkbenchPartSite site = part.getSite();
                Shell partShell = site.getShell();
                if (partShell != null) {

                    Control focusControl =
                            Display.getCurrent().getFocusControl();
                    Cursor currCursor = null;
                    if (focusControl != null) {
                        currCursor = focusControl.getCursor();
                        focusControl.setCursor(Display.getDefault()
                                .getSystemCursor(SWT.CURSOR_WAIT));
                    }

                    QuickSearchContributionHelper helper =
                            new QuickSearchContributionHelper(activePart);
                    helper.cacheContributedElements();

                    if (focusControl != null && !focusControl.isDisposed()) {
                        focusControl.setCursor(currCursor);
                    }

                    QuickSearchPopup quickSearchPopup =
                            new QuickSearchPopup(partShell, helper,
                                    getPopupLocation(focusControl),
                                    searchDescription);

                    quickSearchPopup.getQuickSearchShell().open();
                }
            }
        }
        return;
    }

    /**
     * @param focusControl
     * 
     * @return Get most appropriate location for the quick search popup - If the
     *         mouse cursor is not in the bounds of the control for the view
     *         we're doing a search in then use closest point on border
     */
    private Point getPopupLocation(Control focusControl) {
        Point cursorLoc = Display.getDefault().getCursorLocation();

        if (focusControl != null) {
            // Bit of a fudge here - FigureCanvas control on GEF editors does
            // not give accurate result for toDisplay() - so use it's parent
            // instead.
            if (focusControl instanceof FigureCanvas) {
                focusControl = focusControl.getParent();
            }

            if (focusControl != null) {
                Rectangle bnds = focusControl.getBounds();

                Point loc = focusControl.toDisplay(0, 0);

                bnds.x = loc.x;
                bnds.y = loc.y;

                if (!bnds.contains(cursorLoc)) {

                    if (cursorLoc.x > (bnds.x + bnds.width)) {
                        cursorLoc.x = (bnds.x + bnds.width) - 32;
                    } else if (cursorLoc.x < bnds.x) {
                        cursorLoc.x = bnds.x;
                    }

                    if (cursorLoc.y > (bnds.y + bnds.height)) {
                        cursorLoc.y = (bnds.y + bnds.height) - 32;
                    } else if (cursorLoc.y < bnds.y) {
                        cursorLoc.y = bnds.y;
                    }
                }
            }
        }

        // By default return the current cursor location
        return cursorLoc;
    }

}
