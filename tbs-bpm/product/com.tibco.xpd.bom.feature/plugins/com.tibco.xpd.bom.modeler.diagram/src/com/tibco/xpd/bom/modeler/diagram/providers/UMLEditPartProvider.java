/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.providers;

import java.lang.ref.WeakReference;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.View;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.CanvasPackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.UMLEditPartFactory;
import com.tibco.xpd.bom.modeler.diagram.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class UMLEditPartProvider extends AbstractEditPartProvider {

    /**
     * @generated
     */
    private EditPartFactory factory;

    /**
     * @generated
     */
    private boolean allowCaching;

    /**
     * @generated
     */
    private WeakReference cachedPart;

    /**
     * @generated
     */
    private WeakReference cachedView;

    /**
     * @generated
     */
    public UMLEditPartProvider() {
        setFactory(new UMLEditPartFactory());
        setAllowCaching(true);
    }

    /**
     * @generated
     */
    public final EditPartFactory getFactory() {
        return factory;
    }

    /**
     * @generated
     */
    protected void setFactory(EditPartFactory factory) {
        this.factory = factory;
    }

    /**
     * @generated
     */
    public final boolean isAllowCaching() {
        return allowCaching;
    }

    /**
     * @generated
     */
    protected synchronized void setAllowCaching(boolean allowCaching) {
        this.allowCaching = allowCaching;
        if (!allowCaching) {
            cachedPart = null;
            cachedView = null;
        }
    }

    /**
     * @generated
     */
    protected IGraphicalEditPart createEditPart(View view) {
        EditPart part = factory.createEditPart(null, view);
        if (part instanceof IGraphicalEditPart) {
            return (IGraphicalEditPart) part;
        }
        return null;
    }

    /**
     * @generated
     */
    protected IGraphicalEditPart getCachedPart(View view) {
        if (cachedView != null && cachedView.get() == view) {
            return (IGraphicalEditPart) cachedPart.get();
        }
        return null;
    }

    /**
     * @generated
     */
    public synchronized IGraphicalEditPart createGraphicEditPart(View view) {
        if (isAllowCaching()) {
            IGraphicalEditPart part = getCachedPart(view);
            cachedPart = null;
            cachedView = null;
            if (part != null) {
                return part;
            }
        }
        return createEditPart(view);
    }

    /**
     * @generated
     */
    public synchronized boolean provides(IOperation operation) {
        if (operation instanceof CreateGraphicEditPartOperation) {
            View view = ((IEditPartOperation) operation).getView();
            if (!CanvasPackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry
                    .getModelID(view))) {
                return false;
            }
            if (isAllowCaching() && getCachedPart(view) != null) {
                return true;
            }
            IGraphicalEditPart part = createEditPart(view);
            if (part != null) {
                if (isAllowCaching()) {
                    cachedPart = new WeakReference(part);
                    cachedView = new WeakReference(view);
                }
                return true;
            }
        }
        return false;
    }
}
