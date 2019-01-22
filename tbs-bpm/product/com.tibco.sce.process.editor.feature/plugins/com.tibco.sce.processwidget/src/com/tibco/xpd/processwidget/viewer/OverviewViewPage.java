/*
 ** 
 **  MODULE:             $RCSfile: OverviewViewPage.java $ 
 **                      $Revision: 1.1 $ 
 **                      $Date: 2005/05/04 16:26:34Z $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc. All rights reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: OverviewViewPage.java $ 
 **    Revision 1.1  2005/05/04 16:26:34Z  wzurek 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.processwidget.viewer;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.tibco.xpd.processwidget.ProcessWidget;

/**
 * @author wzurek
 */
public class OverviewViewPage extends Page implements IContentOutlinePage {

    private final ProcessWidget widget;

    public OverviewViewPage(ProcessWidget widget) {
        this.widget = widget;
    }

    private Canvas control;

    private ScrollableThumbnail thumbnail;

    private DisposeListener disposeListener;

    public void createControl(Composite parent) {
        control = new Canvas(parent, SWT.NONE);

        LightweightSystem lws = new LightweightSystem(control);
        RootEditPart rep = widget.getGraphicalViewer().getRootEditPart();
        if (rep instanceof ScalableFreeformRootEditPart) {
            ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
            thumbnail = new ScrollableThumbnail((Viewport) root.getFigure());
            thumbnail.setBorder(new MarginBorder(3));
            thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));
            lws.setContents(thumbnail);
            disposeListener = new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    if (thumbnail != null) {
                        thumbnail.deactivate();
                        thumbnail = null;
                    }
                }
            };
            widget.getControl().addDisposeListener(disposeListener);
        }

    }

    public Control getControl() {
        return control;
    }

    public void setFocus() {
    }

    public void init(IPageSite pageSite) {
        super.init(pageSite);

        // IActionBars bars = pageSite.getActionBars();
        // String[] actions = new String[] { ActionFactory.UNDO.getId(),
        // ActionFactory.REDO.getId() };
        // for (int i = 0; i < actions.length; i++) {
        // bars.setGlobalActionHandler(actions[i], editor
        // .getAction(actions[i]));
        // }
    }

    public void addSelectionChangedListener(ISelectionChangedListener listener) {
    }

    public ISelection getSelection() {
        return StructuredSelection.EMPTY;
    }

    public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
    }

    public void setSelection(ISelection selection) {
    }
}