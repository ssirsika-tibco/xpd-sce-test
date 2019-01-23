/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.parts;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;

/**
 * Common interface for EditParts that relay on the model adapters, there are
 * two bese implementations:
 * {@see com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart} and
 * {@see com.tibco.xpd.processwidget.parts.BaseConnectionEditPart}. <br>
 * <b>Note:</b> This interface is not inteded to be impemented by clients.
 * 
 * @author wzurek
 */
public interface ModelAdapterEditPart extends EditPart, IEditingDomainProvider,
        IAdaptable {

    /**
     * @return model adapter for this edit part
     */
    BaseProcessAdapter getModelAdapter();

    /**
     * @return adapter factory that can produce model adapters for the diagram
     *         <br>
     *         <b>Note:</b> the factory might not be able to produce EMF
     *         ItemAdapters, it can be designated only for diagram adapters.
     */
    public AdapterFactory getAdapterFactory();

    /**
     * Return the ProcessWidgetColor.XXX_FILL color ID for this diagram object.
     * This specifies the color id to pass to
     * ProcessWidgetColors.getGraphicalNodeColor
     * 
     * @return
     */
    String getFillColorIDForPartType();

    /**
     * Return the ProcessWidgetColor.XXX_LINE color ID for this diagram object.
     * This specifies the color id to pass to
     * ProcessWidgetColor.getGraphicalNodeColor
     * 
     * @return
     */
    String getLineColorIDForPartType();

}
