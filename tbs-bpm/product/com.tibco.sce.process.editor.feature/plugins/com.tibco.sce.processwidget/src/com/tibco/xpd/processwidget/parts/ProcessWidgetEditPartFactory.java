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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.AssociationAdapter;
import com.tibco.xpd.processwidget.adapters.DataObjectAdapter;
import com.tibco.xpd.processwidget.adapters.EventAdapter;
import com.tibco.xpd.processwidget.adapters.GatewayAdapter;
import com.tibco.xpd.processwidget.adapters.GroupAdapter;
import com.tibco.xpd.processwidget.adapters.LaneAdapter;
import com.tibco.xpd.processwidget.adapters.MessageFlowAdapter;
import com.tibco.xpd.processwidget.adapters.NoteAdapter;
import com.tibco.xpd.processwidget.adapters.PoolAdapter;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.adapters.SequenceFlowAdapter;
import com.tibco.xpd.processwidget.adapters.TaskAdapter;

/**
 * Factory for EditParts of Process Diagram, the factory depends on the provided
 * atapter factory, and create EditParts depending oo type of the adapter
 * created by the adapter factory.
 * 
 * @author wzurek
 */
public class ProcessWidgetEditPartFactory implements EditPartFactory {

    private final AdapterFactory adapterFactory;

    /**
     * The Constructor
     * 
     * @param adapterFactory
     */
    public ProcessWidgetEditPartFactory(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    /*
     * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    public EditPart createEditPart(EditPart context, Object model) {

        EditPart part;
        if (model instanceof FlowLabelModel) {
            part = new ConnectionLabelEditPart();
        } else {
            Object adapter = adapterFactory.adapt(model,
                    ProcessWidgetConstants.ADAPTER_TYPE);
            if (adapter instanceof TaskAdapter) {
                part = new TaskEditPart(adapterFactory);
            } else if (adapter instanceof GatewayAdapter) {
                part = new GatewayEditPart(adapterFactory);
            } else if (adapter instanceof DataObjectAdapter) {
                part = new DataObjectEditPart(adapterFactory);
            } else if (adapter instanceof GroupAdapter) {
                part = new GroupEditPart(adapterFactory);
            } else if (adapter instanceof EventAdapter) {
                part = new EventEditPart(adapterFactory);
            } else if (adapter instanceof ProcessDiagramAdapter) {
                part = new ProcessEditPart(adapterFactory);
            } else if (adapter instanceof SequenceFlowAdapter) {
                part = new SequenceFlowEditPart(adapterFactory);
            } else if (adapter instanceof MessageFlowAdapter) {
                part = new MessageFlowEditPart(adapterFactory);
            } else if (adapter instanceof LaneAdapter) {
                part = new LaneEditPart(adapterFactory);
            } else if (adapter instanceof PoolAdapter) {
                part = new PoolEditPart(adapterFactory);
            } else if (adapter instanceof NoteAdapter) {
                part = new NoteEditPart(adapterFactory);
            } else if (adapter instanceof AssociationAdapter) {
                part = new AssociationEditPart(adapterFactory);
            } else {
                throw new IllegalArgumentException(
                        adapter == null ? "No model adapter for: " + model //$NON-NLS-1$
                                : "Unrecognized model adapter: " //$NON-NLS-1$
                                        + adapter.getClass().getName());
            }
        }
        part.setModel(model);
        return part;
    }
}
