/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.part.custom.utils;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.preferences.IBOMPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;

/**
 * Utility class to assist with class header colours.
 * 
 * Note that this class only uses the EMF model so that it can be called from
 * the migration process.
 *
 * @author nwilson
 * @since 20 Jun 2019
 */
public class ClassHeaderColourUtil {

    /**
     * Checks if the header colour for the given UML class is the default.
     * 
     * @param clazz
     *            The UML class to check.
     * @return true if it uses the default colour.
     */
    public boolean isDefaultColour(Class clazz) {
        Diagram diagram = getDiagramForClass(clazz);
        ShapeGradientStyle sgs = getShapeGradientStyleForClass(clazz, diagram);
        return isDefaultColour(clazz, sgs);
    }

    /**
     * Checks if the header colour for the given UML class is the default. This
     * method allows you to pass the ShapeGradientStle associated with the given
     * class in case you have already looked it up.
     * 
     * @param clazz
     *            The UML class to check.
     * @param sgs
     *            The ShapeGradientStyle associated with the class.
     * @return true if it uses the default colour.
     */
    public boolean isDefaultColour(Class clazz, ShapeGradientStyle sgs) {
        RGB currentColour =
                FigureUtilities.integerToRGB(sgs.getGradStartColor());
        RGB defaultColour = getDefaultColour(clazz);

        return defaultColour.equals(currentColour);
    }

    /**
     * Get the default colour for the given class from the preference store.
     * 
     * @param clazz
     *            The UML class.
     * @return The default header colour.
     */
    public RGB getDefaultColour(Class clazz) {
        // Get all the pre-defined colours that are used for headers
        IPreferenceStore store =
                BOMDiagramEditorPlugin.getInstance().getPreferenceStore();

        RGB localColour = PreferenceConverter.getColor(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR1);
        RGB globalColour = PreferenceConverter.getColor(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR3);
        RGB caseColour = PreferenceConverter.getColor(store,
                IBOMPreferenceConstants.PREF_CLASS_GRAD_COLOR4);

        RGB defaultColour = localColour;
        if (GlobalDataProfileManager.getInstance().isGlobal(clazz)) {
            defaultColour = globalColour;
        } else if (GlobalDataProfileManager.getInstance().isCase(clazz)) {
            defaultColour = caseColour;
        }
        return defaultColour;
    }

    /**
     * Get the GMF Diagram model element for the given UML class.
     * 
     * @param clazz
     *            The UML class.
     * @return The associated Diagram model.
     */
    public Diagram getDiagramForClass(Class clazz) {
        Diagram diagram = null;
        EList<EObject> contents = clazz.eResource().getContents();
        for (EObject content : contents) {
            if (content instanceof Diagram) {
                diagram = (Diagram) content;
            }
        }
        return diagram;
    }

    /**
     * Get the ShapeGradientStyle model associated with the given UML class.
     * 
     * @param clazz
     *            The UML class.
     * @param diagram
     *            The Diagram model.
     */
    public ShapeGradientStyle getShapeGradientStyleForClass(Class clazz,
            Diagram diagram) {
        ShapeGradientStyle sgs = null;
        List<?> children = diagram.getChildren();
        for (Object child : children) {
            if (child instanceof Node) {
                Node node = (Node) child;
                if (clazz.equals(node.getElement())) {
                    List<?> styles = node.getStyles();
                    for (Object style : styles) {
                        if (style instanceof ShapeGradientStyle) {
                            sgs = (ShapeGradientStyle) style;
                        }
                    }
                }
            }
        }
        return sgs;
    }
}
