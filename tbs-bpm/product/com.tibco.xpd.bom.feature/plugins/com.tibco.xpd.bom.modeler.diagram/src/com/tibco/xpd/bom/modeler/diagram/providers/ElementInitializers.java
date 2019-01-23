/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.providers;

import com.tibco.xpd.bom.modeler.diagram.expressions.UMLAbstractExpression;
import com.tibco.xpd.bom.modeler.diagram.expressions.UMLOCLFactory;

import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class ElementInitializers {

    /**
     * @generated
     */
    public static void init_Property_2001(Property instance) {
        try {
            Object value_0 = UMLOCLFactory
                    .getExpression("AggregationKind::none",
                            UMLPackage.eINSTANCE.getProperty()).evaluate(
                            instance);

            value_0 = UMLAbstractExpression.performCast(value_0,
                    UMLPackage.eINSTANCE.getAggregationKind());
            instance.setAggregation((AggregationKind) value_0);
        } catch (RuntimeException e) {
            BOMDiagramEditorPlugin.getInstance().logError(
                    "Element initialization failed", e); //$NON-NLS-1$						
        }
    }
}
