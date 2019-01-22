/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies.custom;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.TextCellEditorEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * A customised version of the {@link LabelDirectEditPolicy}. This is required
 * for the cardinality (source/target) edit parts that have the
 * {@link Association} view as their model view BUT one of its member ends
 * {@link Property} as the semantic element - unfortunately the original version
 * could not handle this usage.
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
public class CardinalityLabelDirectEditPolicy extends DirectEditPolicy {

    /**
     * We need an adapter that will be able to hold both a model and an view
     */
    class EObjectAdapterEx extends EObjectAdapter {

        private View view = null;

        /**
         * constructor
         * 
         * @param element
         *            element to be wrapped
         * @param view
         *            view to be wrapped
         */
        public EObjectAdapterEx(EObject element, View view) {
            super(element);
            this.view = view;
        }

        public Object getAdapter(Class adapter) {
            Object o = super.getAdapter(adapter);
            if (o != null)
                return o;
            if (adapter.equals(View.class)) {
                return view;
            }
            return null;
        }
    }

    /**
     * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
     */
    protected Command getDirectEditCommand(DirectEditRequest edit) {
        if (edit.getCellEditor() instanceof TextCellEditorEx)
            if (!((TextCellEditorEx) edit.getCellEditor()).hasValueChanged())
                return null;

        String labelText = (String) edit.getCellEditor().getValue();

        // for CellEditor, null is always returned for invalid values
        if (labelText == null) {
            return null;
        }

        ITextAwareEditPart compartment = (ITextAwareEditPart) getHost();
        EObject model = (EObject) compartment.getModel();
        EObjectAdapter elementAdapter = null;
        if (model instanceof View) {
            View view = (View) model;
            elementAdapter =
                    new EObjectAdapterEx(((IGraphicalEditPart) compartment)
                            .resolveSemanticElement(), view);
        } else
            elementAdapter = new EObjectAdapterEx(model, null);
        // check to make sure an edit has occurred before returning a command.
        String prevText =
                compartment.getParser().getEditString(elementAdapter,
                        compartment.getParserOptions().intValue());
        if (!prevText.equals(labelText)) {
            ICommand iCommand =
                    compartment.getParser().getParseCommand(elementAdapter,
                            labelText,
                            0);
            return new ICommandProxy(iCommand);
        }

        return null;
    }

    /**
     * @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest)
     */
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String) request.getCellEditor().getValue();
        ((ITextAwareEditPart) getHost()).setLabelText(value);
    }

}